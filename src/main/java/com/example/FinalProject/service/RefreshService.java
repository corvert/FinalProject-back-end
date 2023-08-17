package com.example.FinalProject.service;

import com.example.FinalProject.model.Stock;
import com.example.FinalProject.model.StockWrapper;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;

@Service
public class RefreshService {

    private final Map<StockWrapper, Boolean> stockToRefresh;
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private static final Duration refreshPeriod = Duration.ofSeconds(15);

    public RefreshService() {
        stockToRefresh = new HashMap<>();
        setRefreshEvery15Minutes();

    }

    public  boolean shouldRefresh(final StockWrapper stockWrapper)
    {
        if(!stockToRefresh.containsKey(stockWrapper))
        {
            stockToRefresh.put(stockWrapper, false);
            return  true;
        }
        return stockToRefresh.get(stockWrapper);
    }

    private void setRefreshEvery15Minutes() {
        scheduler.scheduleAtFixedRate(() ->
                stockToRefresh.forEach((stock, value) -> {
                    if (stock.getLastAccessed().isBefore(LocalDateTime.now().minus(refreshPeriod))) {
                        System.out.println("Setting shloud refresh " + stock.getStock().getSymbol());
                        stockToRefresh.remove(stock);
                        stockToRefresh.put(stock.withLastAccessed(LocalDateTime.now()), true);
                    }
                }), 0, 15, SECONDS);
    }
}
