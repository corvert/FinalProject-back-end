package com.example.FinalProject.controller;

import com.example.FinalProject.exceptions.StockNotFoundExcetion;
import com.example.FinalProject.model.Account;
import com.example.FinalProject.model.Dividend;
import com.example.FinalProject.model.Stock;
import com.example.FinalProject.model.Trade;
import com.example.FinalProject.service.AccountService;
import com.example.FinalProject.service.DividendService;
import com.example.FinalProject.service.StockService;
import com.example.FinalProject.service.TradeService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TradeService tradeService;
    @Autowired
    private DividendService dividendService;

    @GetMapping
    public List<Stock> getFullPortfolio() {
        return stockService.getFullPortfolio();
    }

    @GetMapping("/symbol/{symbol}")
    public Stock getStockByStockSymbol(@PathVariable("symbol") String symbol) {
        Stock stock = stockService.getStockByStockSymbol(symbol);
        return stock;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable("id") Long id) {
        Stock stock = stockService.findStockById(id);
        return ResponseEntity.ok(stock);
    }

    @GetMapping("/{id}/trade-list")
    public ResponseEntity<List<Trade>> getTradeListByStockId(@PathVariable("id") Long id) {
        List<Trade> tradeListByStockId = tradeService.getTradeListByStockId(id);
        return ResponseEntity.ok(tradeListByStockId);
    }

    @GetMapping("/{id}/dividend-list")
    public ResponseEntity<List<Dividend>> getDividendListByStockId(@PathVariable("id") Long id) {
        List<Dividend> dividendListByStockId = dividendService.getDividendListByStockId(id);
        return ResponseEntity.ok(dividendListByStockId);
    }

    @PostMapping("/add-stock")
    public ResponseEntity<Stock> addStock(@RequestBody Stock stock) {
        stockService.save(stock);
        return ResponseEntity.ok(stock);
    }

    @PostMapping("/{id}/createTrade")
    public ResponseEntity<Trade> createTrade(@PathVariable("id") Long stockId, @RequestBody Trade trade) throws StockNotFoundExcetion {
        Stock stock = stockService.findStockById(stockId);
        Trade savedTrade = tradeService.save(stock, trade);
        return ResponseEntity.ok(savedTrade);
    }

    @PostMapping("/{id}/createDividend")
    public ResponseEntity<Dividend> createDividend(@PathVariable("id") Long stockId, @RequestBody Dividend dividend) {
        Stock stock = stockService.findStockById(stockId);
        Dividend savedDividend = dividendService.save(dividend, stock);
        return ResponseEntity.ok(savedDividend);
    }


}
