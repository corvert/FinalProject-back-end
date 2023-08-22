package com.example.FinalProject.service;

import com.example.FinalProject.model.Account;
import com.example.FinalProject.model.AlphaVantageAPI;
import com.example.FinalProject.model.Stock;
import com.example.FinalProject.repository.StockRepository;
import com.example.FinalProject.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockService {
    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private TradeRepository tradeRepository;
    @Autowired
    private AlphaVantageAPI alphaVantageAPI;


    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public List<Stock> getFullPortfolio() {
        List<Stock> stockList = stockRepository.findAll();
        return stockList.stream()
                .sorted((stock1, stock2) -> stock1.getStockName().compareTo(stock2.getStockName()))
                .collect(Collectors.toList());
    }

    public Stock findStockById(Long id) {
        return stockRepository.findById(id).orElseThrow();
    }

    public Stock save(Stock stock) {
        Stock savedStock = toStock(stock);
        return stockRepository.save(stock);
    }

    public Stock save(Account account, Stock stock) {
        BigDecimal currentPrice = BigDecimal.valueOf(alphaVantageAPI.getStockPrice(stock.getSymbol()));
        stock.setCurrentPrice(currentPrice);
        stock.setAccount(account);
        return stockRepository.save(stock);
    }

    private Stock toStock(Stock stock) {
        return Stock.builder()
                .id(stock.getId())
                .symbol(stock.getSymbol())
                .stockName(stock.getStockName())
                .account(stock.getAccount())
                .totalAmount(stock.getTotalAmount())
                .averagePrice(stock.getAveragePrice())
                .totalBuyValue(stock.getTotalBuyValue())
                .profitLoss(stock.getProfitLoss())
                .build();
    }

    public Stock getStockByStockSymbol(String symbol) {
        Stock stock = stockRepository.findStockBySymbol(symbol);
        return stock;
    }

    public List<Stock> getStocksListByAccountId(Long id) {
        List<Stock> stockList = stockRepository.findAllByAccountId(id);
        return stockList.stream()
                .sorted((stock1, stock2) -> stock1.getStockName().compareTo(stock2.getStockName()))
                .collect(Collectors.toList());
    }


}
