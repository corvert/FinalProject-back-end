package com.example.FinalProject.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.With;
import yahoofinance.Stock;

@Getter
@With
@AllArgsConstructor
public class StockWrapper {
    private final Stock stock;
    @Setter
    private final LocalDateTime lastAccessed;

   public  StockWrapper(Stock stock )
   {
       this.stock = stock;
       lastAccessed = LocalDateTime.now();
   }

}
