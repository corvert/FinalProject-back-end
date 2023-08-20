package com.example.FinalProject.service;

import com.example.FinalProject.exceptions.StockNotFoundExcetion;
import com.example.FinalProject.model.Account;
import com.example.FinalProject.model.Stock;
import com.example.FinalProject.model.Trade;
import com.example.FinalProject.repository.AccountRepository;
import com.example.FinalProject.repository.StockRepository;
import com.example.FinalProject.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TradeService {
    @Autowired
    private TradeRepository tradeRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private StockService stockService;

    public TradeService(TradeRepository tradeRepository, StockService stockService, AccountRepository accountRepository,
                        StockRepository stockRepository) {
        this.tradeRepository = tradeRepository;
        this.stockService = stockService;
        this.accountRepository = accountRepository;
        this.stockRepository = stockRepository;
      ;
    }

    public List<Trade> getFullTradeList() {
        List<Trade> tradeList = tradeRepository.findAll();
        return  tradeList.stream()
                .sorted((d1, d2) -> d1.getDate().compareTo(d2.getDate()))
                .collect(Collectors.toList());
    }

    public Trade save(Trade trade) {
        Trade savedTrade = toTrade(trade);
        return tradeRepository.save(trade);
    }

    public Trade save(Stock stock, Trade trade) throws StockNotFoundExcetion {
        trade.setStock(stock);
        updateStockBalanceByTradeType(stock.getAccount().getId(), stock.getId(), trade);
        return tradeRepository.save(trade);
    }

    public Trade save(Long accountId, Long stockId, Trade trade) throws StockNotFoundExcetion {
        updateStockBalanceByTradeType(accountId, stockId, trade);
        return tradeRepository.save(trade);
    }

    private Trade toTrade(Trade trade) {
        return Trade.builder()
                .id(trade.getId())
                .tradeType(trade.getTradeType())
                .stock(trade.getStock())
                .date(trade.getDate())
                .amount(trade.getAmount())
                .unitPrice(trade.getUnitPrice())
                .commission(trade.getCommission())
                .tradeSum(trade.getTradeSum())
                .comment(trade.getComment())
                .build();
    }

    public List<Trade> getTradeListByStockSymbol(String symbol) {
        List<Trade> tradeList = tradeRepository.findTradeByStockSymbol(symbol);
        return tradeList.stream()
                .sorted((d1, d2) -> d1.getDate().compareTo(d2.getDate()))
                .collect(Collectors.toList());
    }

    private Trade updateStockBalanceByTradeType(Long accountId, Long stockId, Trade trade) throws StockNotFoundExcetion {
        Account account = accountRepository.findById(accountId).orElseThrow(()-> new StockNotFoundExcetion("Stock not found " + stockId, 1));
        Stock stock = stockRepository.findById(stockId).orElseThrow();
        BigDecimal totalStockAmount;

        if (trade.getTradeType().equalsIgnoreCase("BUY")) {
            totalStockAmount = tradeBuyCalculations(trade, account, stock);
        } else {
            totalStockAmount = tradeSellCalculations(trade, account, stock);
        }

        Trade newTrade = new Trade();
        newTrade.setTradeType(trade.getTradeType());
        newTrade.setAmount(trade.getAmount());
        newTrade.setStock(stock);
        newTrade.setDate(LocalDate.now());
        newTrade.setUnitPrice(trade.getUnitPrice());
        newTrade.setCommission(trade.getCommission());
        newTrade.setComment(trade.getComment());
        newTrade.setTradeSum(trade.getTradeSum());

        stockCalculation(stockId, trade, account, stock, totalStockAmount);

        return trade;
    }

    private static BigDecimal tradeSellCalculations(Trade trade, Account account, Stock stock) {
        BigDecimal totalStockAmount;
        if (trade.getTradeType().equalsIgnoreCase("SELL") && trade.getAmount()
                .compareTo(stock.getTotalAmount()) > 0) {
            throw new IllegalStateException("Quantity of transaction exceeds available stock amount");
        }
        totalStockAmount = stock.getTotalAmount().subtract(trade.getAmount());
        account.setBalance(account.getBalance().add(trade.getAmount().multiply(trade.getUnitPrice())
                .subtract(trade.getCommission())));
        trade.setTradeSum(trade.getAmount().multiply(trade.getUnitPrice()).subtract(trade.getCommission()));
        stock.setTotalBuyValue(stock.getTotalBuyValue().subtract(trade.getAmount().multiply(trade.getUnitPrice())
                .subtract(trade.getCommission())));
        return totalStockAmount;
    }

    private static BigDecimal tradeBuyCalculations(Trade trade, Account account, Stock stock) {
        BigDecimal totalStockAmount;
        totalStockAmount = trade.getAmount().add(stock.getTotalAmount());
        account.setBalance(account.getBalance().subtract(trade.getAmount().multiply(trade.getUnitPrice())
                .add(trade.getCommission())));
        trade.setTradeSum(trade.getAmount().multiply(trade.getUnitPrice()).add(trade.getCommission()));
        stock.setTotalBuyValue(stock.getTotalBuyValue());
        stock.setTotalBuyValue(stock.getTotalBuyValue().add(trade.getAmount().multiply(trade.getUnitPrice())
                .add(trade.getCommission())));
        return totalStockAmount;
    }

    private static void stockCalculation(Long stockId, Trade trade, Account account, Stock stock, BigDecimal totalStockAmount) {
        stock.setTotalAmount(totalStockAmount);
        stock.setAccount(account);
        stock.setId(stockId);
        stock.setCurrentPrice(trade.getUnitPrice());//
        stock.setProfitLoss(stock.getCurrentPrice().multiply(stock.getTotalAmount()).subtract(stock.getTotalBuyValue()));
        if (totalStockAmount.compareTo(BigDecimal.ZERO) == 0) {
            stock.setAveragePrice(BigDecimal.ZERO);
        } else if (!stock.getTotalAmount().equals(BigDecimal.ZERO)) {
            stock.setAveragePrice(stock.getTotalBuyValue().divide(stock.getTotalAmount(), 2, RoundingMode.HALF_UP));
        }
        stock.setTotalMarketValue(stock.getCurrentPrice().multiply(stock.getTotalAmount()));
    }

    public List<Trade> getTradeListByStockId(Long id) {
        return tradeRepository.findAllByStockId(id);
    }
}
