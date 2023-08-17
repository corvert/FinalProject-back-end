package com.example.FinalProject.controller;

import com.example.FinalProject.model.CurrencyRate;
import com.example.FinalProject.service.CurrencyRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping(path = "currency")
public class CurrencyRateController {
    @Autowired
    private CurrencyRateService currencyRateService;

    @GetMapping
    public List<CurrencyRate> getAllCurrencys() {
        return currencyRateService.getAllCurrencys();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CurrencyRate> getCurrencyRateById(@PathVariable("id") Long id) {
        CurrencyRate currencyRate = currencyRateService.findCurrencyRateById(id);
        return ResponseEntity.ok(currencyRate);
    }

        @GetMapping("/currency/{code}")
    public ResponseEntity<CurrencyRate> getCurrencyRateByCurrency(@PathVariable("code") String code) {
        CurrencyRate currencyRateByCurrency = currencyRateService.findCurrencyRateByCurrency(code);
        return ResponseEntity.ok(currencyRateByCurrency);
    }

    @PostMapping("/add-currency")
    public ResponseEntity<CurrencyRate> addAccount(@RequestBody CurrencyRate currencyRate) {
        currencyRateService.save(currencyRate);
        return ResponseEntity.ok(currencyRate);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<CurrencyRate> updateCurrencyRate(
            @RequestBody CurrencyRate currencyRate){
        CurrencyRate updateCurrencyRate = currencyRateService.updateCurrencyRate(currencyRate);
        return ResponseEntity.ok(updateCurrencyRate);
    }


}
