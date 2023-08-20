package com.example.FinalProject.controller;

import com.example.FinalProject.model.Account;
import com.example.FinalProject.model.Dividend;
import com.example.FinalProject.model.Stock;
import com.example.FinalProject.model.Trade;
import com.example.FinalProject.service.AccountService;
import com.example.FinalProject.service.StockService;
import com.example.FinalProject.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trade")
public class TradeController {

    @Autowired
    private TradeService tradeService;
    @Autowired
    private StockService stockService;
    @Autowired
    private AccountService accountService;

    @GetMapping
    public List<Trade> getFullTradeList() {
        return tradeService.getFullTradeList();
    }

    @GetMapping(path = "/{symbol}")
    public ResponseEntity<List<Trade>> getTradeListByStockSymbol(@PathVariable("symbol") String symbol) {
        Stock stock = stockService.getStockByStockSymbol(symbol);
        List<Trade> tradeList = tradeService.getTradeListByStockSymbol(stock.getSymbol());
        return ResponseEntity.ok(tradeList);
    }

    @PostMapping("/add-trade")
    public ResponseEntity<Trade> addTrade(@RequestBody Trade trade) {
        tradeService.save(trade);
        return ResponseEntity.ok(trade);
    }


}
