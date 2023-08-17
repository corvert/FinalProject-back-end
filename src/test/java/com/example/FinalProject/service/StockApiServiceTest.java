package com.example.FinalProject.service;

import com.example.FinalProject.model.StockWrapper;
import com.example.FinalProject.service.StockApiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
 class StockApiServiceTest {

    @Autowired
    private StockApiService stockApiService;

    @Test
    void invoke() throws IOException
    {
        final StockWrapper stockWrapper = stockApiService.findStock("AMZN");
        System.out.println(stockWrapper.getStock());

        final BigDecimal price = stockApiService.findPrice(stockWrapper);
        System.out.println(price);
    }

    @Test
    void multiple() throws IOException, InterruptedException
    {
        final List<StockWrapper> stockWrappers = stockApiService.findStocks(Arrays.asList("GOOG", "AMZN"));
        findPrices(stockWrappers);

        Thread.sleep(16000);
        final  StockWrapper aa = stockApiService.findStock("AA.L");
        stockWrappers.add(aa);

        System.out.println(stockApiService.findPrice(aa));
        findPrices(stockWrappers);
    }
    private void  findPrices(List<StockWrapper> stockWrappers)
    {
        stockWrappers.forEach(stock -> {
            try {
                System.out.println(stockApiService.findPrice(stock));
            }
            catch (IOException e){
                System.out.println("e");
            }
        });
    }
}
