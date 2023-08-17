package com.example.FinalProject.service;

import com.example.FinalProject.model.StockWrapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class StockApiService
{
    private final RefreshService refreshService;

    public StockWrapper findStock(final String ticker) {
        try {

            Stock stock = YahooFinance.get(ticker);
            if (stock == null) {
                System.out.println("Stock is null for ticker: " + ticker);
                return null;
            }
            return new StockWrapper(stock);
        } catch (IOException e) {
            System.out.println("Error fetching stock data: " + e.getMessage());
        }
        return null;
    }

    public List<StockWrapper> findStocks(final List<String> tickers) {
        return tickers.stream().map(this::findStock).filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public BigDecimal findPrice(final StockWrapper stockWrapper) throws IOException {
        return stockWrapper.getStock().getQuote(refreshService.shouldRefresh(stockWrapper)).getPrice();
    }

    public BigDecimal findLastChangePrecent(final StockWrapper stockWrapper) throws IOException
    {
return stockWrapper.getStock().getQuote(refreshService.shouldRefresh(stockWrapper)).getChangeInPercent();
    }
    public BigDecimal find200Mean(final StockWrapper stockWrapper) throws IOException
    {
        return stockWrapper.getStock().getQuote(refreshService.shouldRefresh(stockWrapper)).getChangeFromAvg200InPercent();
    }
}
