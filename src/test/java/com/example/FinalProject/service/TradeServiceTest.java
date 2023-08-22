package com.example.FinalProject.service;

import com.example.FinalProject.exceptions.StockNotFoundExcetion;
import com.example.FinalProject.model.*;
import com.example.FinalProject.repository.AccountRepository;
import com.example.FinalProject.repository.StockRepository;
import com.example.FinalProject.repository.TradeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class TradeServiceTest {

    @Mock
    TradeRepository tradeRepository;
    @Mock
    StockService stockService;
    @Mock
    AccountRepository accountRepository;
    @Mock
    StockRepository stockRepository;
    @Mock
    AlphaVantageAPI alphaVantageAPI;

    @Test
    public void testSaveTrade_responseSuccessfully() throws StockNotFoundExcetion {
        Account account = new Account(4L, "LHV", BigDecimal.TEN, "EUR",
                BigDecimal.ZERO, new MyUser());
        Stock stock = new Stock(7L, "AAPL", "Apple Inc.", account, BigDecimal.TEN,
                BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);

        Trade trade = new Trade(1L, "BUY", stock, LocalDate.now(), BigDecimal.valueOf(10),
                BigDecimal.valueOf(2), BigDecimal.valueOf(1), BigDecimal.valueOf(21), "Good price");


        when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));
        when(stockRepository.findById(stock.getId())).thenReturn(Optional.of(stock));
        when(tradeRepository.save(trade)).thenReturn(trade);

        TradeService tradeService = new TradeService(tradeRepository, stockService, accountRepository,
                stockRepository, alphaVantageAPI);

        Trade testTrade = tradeService.save(account.getId(), stock.getId(), trade);

        assertEquals(testTrade.getTradeType(), "BUY");
        assertEquals(testTrade.getTradeSum(), BigDecimal.valueOf(21));
    }

    @Test
    public void testSaveTrade_exception(){
        Account account = new Account(4L, "LHV", BigDecimal.TEN, "EUR",
                BigDecimal.ZERO, new MyUser());
        Stock stock = new Stock(7L, "AAPL", "Apple Inc.", account, BigDecimal.TEN,
                BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);

        Trade trade = new Trade(1L, "BUY", stock, LocalDate.now(), BigDecimal.valueOf(10),
                BigDecimal.valueOf(2), BigDecimal.valueOf(1), BigDecimal.valueOf(21), "Good price");

        TradeService tradeService = new TradeService(tradeRepository, stockService, accountRepository,
                stockRepository, alphaVantageAPI);
        assertThrows(StockNotFoundExcetion.class, () -> tradeService.save(stock, trade));
        verifyNoInteractions(tradeRepository);
        verifyNoInteractions(stockService);

    }



}
