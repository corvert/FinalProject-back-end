package com.example.FinalProject.service;

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

@Service
public class TradeService {
    @Autowired
    private TradeRepository tradeRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private StockRepository stockRepository;

    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    public List<Trade> getFullTradeList() {
        return tradeRepository.findAll();
    }

    public Trade save(Trade trade) {
        Trade savedTrade = toTrade(trade);
        return tradeRepository.save(trade);
    }

    public Trade save(Stock stock, Trade trade) {
        trade.setStock(stock);
        updateStockBalanceByTradeType(stock.getAccount().getId(), stock.getId(), trade);
        return tradeRepository.save(trade);
    }

    public Trade save(Long accountId, Long stockId, Trade trade) {
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
        return tradeList;
    }

    private Trade updateStockBalanceByTradeType(Long accountId, Long stockId, Trade trade) {
        Account account = accountRepository.findById(accountId).orElse(null);
        Stock stock = stockRepository.findById(stockId).orElseThrow();
        BigDecimal totalStockAmount;

        if (trade.getTradeType().equalsIgnoreCase("BUY")) {
            totalStockAmount = trade.getAmount().add(stock.getTotalAmount());
            account.setBalance(account.getBalance().subtract(trade.getAmount().multiply(trade.getUnitPrice())
                    .add(trade.getCommission())));
            trade.setTradeSum(trade.getAmount().multiply(trade.getUnitPrice()).add(trade.getCommission()));
            stock.setTotalBuyValue(stock.getTotalBuyValue());
            stock.setTotalBuyValue(stock.getTotalBuyValue().add(trade.getAmount().multiply(trade.getUnitPrice())
                    .add(trade.getCommission())));
        } else {
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
        }

        Trade newTrade = new Trade();
        newTrade.setTradeType(trade.getTradeType());
        newTrade.setAmount(trade.getAmount());
        newTrade.setStock(stock);
        newTrade.setDate(LocalDate.now());
        newTrade.setUnitPrice(trade.getUnitPrice());
        newTrade.setCommission(trade.getCommission());
        newTrade.setComment(trade.getComment());
        newTrade.setTradeSum(trade.getTradeSum());//

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

        return trade;
    }

    public List<Trade> getTradeListByStockId(Long id) {
        return tradeRepository.findAllByStockId(id);
    }
}
