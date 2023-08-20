package com.example.FinalProject.service;

import com.example.FinalProject.exceptions.StockNotFoundExcetion;
import com.example.FinalProject.model.Account;
import com.example.FinalProject.model.Stock;
import com.example.FinalProject.repository.AccountRepository;
import com.example.FinalProject.repository.StockRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StockServiceTest {

    @Mock
    StockRepository stockRepository;


    @SneakyThrows
    @Test
    public void testFindStockByAccountId_returnSuccessfully() throws StockNotFoundExcetion {
        Account account = new Account(1L, "LHV", BigDecimal.TEN, "EUR",
                BigDecimal.ZERO);
        Stock secondStock = new Stock(2L, "AAPL", "Apple Inc.", account, BigDecimal.TEN,
                BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        Stock firstStock = new Stock(1L, "MSFT", "Microsoft INC", account, BigDecimal.TEN,
                BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);

        when(stockRepository.findAllByAccountId(anyLong())).thenReturn(List.of(firstStock, secondStock));
        StockService stockService = new StockService(stockRepository);

        List<Stock> selectedStock = stockService.getStocksListByAccountId(account.getId());

        assert selectedStock.size()==2;
        assertEquals(selectedStock.get(1).getStockName(), "Microsoft INC");
        assertEquals(selectedStock.get(0).getId(), 2L);
        assertEquals(selectedStock.get(1).getAccount().getId(), 1L);
        assertEquals(selectedStock.get(0).getSymbol(), "AAPL");
    }
}
