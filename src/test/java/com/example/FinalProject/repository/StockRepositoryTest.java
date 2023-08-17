package com.example.FinalProject.repository;

import com.example.FinalProject.exceptions.StockNotFoundExcetion;
import com.example.FinalProject.model.Account;
import com.example.FinalProject.model.Stock;
import com.example.FinalProject.service.StockService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class StockRepositoryTest {

    @Mock
    StockRepository stockRepository;

    @SneakyThrows
    @Test
    public void testFindStockBySymbol_returnSuccessfully() throws StockNotFoundExcetion{
      Stock testStock = new Stock(
              1L,
              "MSFT",
              "Microsoft INC",
              new Account(),
              BigDecimal.TEN,
              BigDecimal.ZERO,
              BigDecimal.ZERO,
              BigDecimal.ZERO,
              BigDecimal.ZERO,
              BigDecimal.ZERO,
              BigDecimal.ZERO
      );

      when(stockRepository.findStockBySymbol(anyString())).thenReturn(testStock);
      StockService stockService = new StockService(stockRepository);

      Stock selectedStock = stockService.getStockByStockSymbol("TestStock");

      assertNotNull(selectedStock);
      assertEquals("MSFT", selectedStock.getSymbol());
      assertEquals(BigDecimal.TEN, selectedStock.getCurrentPrice());


    }
//    @Test
//    public void testFindStockByAccountId_returnSuccessfully() throws StockNotFoundExcetion{
//        Account account = new Account(1L, "LHV", BigDecimal.TEN, "EUR", true,
//                BigDecimal.ZERO, BigDecimal.ZERO);
//        Stock testStock = new Stock( 1L,"MSFT","Microsoft INC",account, BigDecimal.TEN,
//                BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
//        Stock testStock1 = new Stock( 2L,"MSFT","Microsoft INC",account, BigDecimal.TEN,
//                BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
//
//        when(stockRepository.findAllByAccountId(anyLong())).thenReturn((List<Stock>) testStock);
//        StockService stockService = new StockService(stockRepository);
//
//        List<Stock> selectedStock = stockService.getStocksListByAccountId(1L);
//        assertEquals(1L, selectedStock.get(1));
//    }

}
