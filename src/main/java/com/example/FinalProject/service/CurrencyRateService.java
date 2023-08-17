package com.example.FinalProject.service;

import com.example.FinalProject.model.CurrencyRate;
import com.example.FinalProject.repository.CurrencyRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyRateService {
    @Autowired
    private CurrencyRateRepository currencyRateRepository;

    public List<CurrencyRate> getAllCurrencys() {
        return currencyRateRepository.findAll();
    }


    public CurrencyRate save(CurrencyRate currencyRate) {
        CurrencyRate savedCurrencyRate = toCurrencyRate(currencyRate);
        return currencyRateRepository.save(savedCurrencyRate);
    }

    private CurrencyRate toCurrencyRate(CurrencyRate currencyRate) {
        return CurrencyRate.builder()
                .id(currencyRate.getId())
                .currency(currencyRate.getCurrency())
                .rate(currencyRate.getRate())
                .build();
    }

    public CurrencyRate updateCurrencyRate(CurrencyRate currencyRate){
        return currencyRateRepository.save(currencyRate);
    }

    public CurrencyRate findCurrencyRateById(Long id) {
        return currencyRateRepository.findById(id).orElseThrow();
    }

    public CurrencyRate findCurrencyRateByCurrency(String currency) {
        return currencyRateRepository.findByCurrency(currency);
    }
}
