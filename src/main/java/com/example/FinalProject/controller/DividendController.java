package com.example.FinalProject.controller;


import com.example.FinalProject.model.Dividend;
import com.example.FinalProject.service.AccountService;
import com.example.FinalProject.service.DividendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dividend")
public class DividendController {

    @Autowired
    private DividendService dividendService;
    private AccountService accountService;

    @GetMapping
    public List<Dividend> getFullDividendList() {
        return dividendService.getFullDividendList();
    }

    @PostMapping("/add-dividend")
    public ResponseEntity<Dividend> addDividend(@RequestBody Dividend dividend) {
        dividendService.save(dividend);
        return ResponseEntity.ok(dividend);
    }

}
