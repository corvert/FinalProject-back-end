package com.example.FinalProject.model;


import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@DynamicInsert
public class Stock implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String symbol;
    private String stockName;
    @ManyToOne
    private Account account;
    private BigDecimal currentPrice;
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private BigDecimal averagePrice = BigDecimal.ZERO;
    private BigDecimal totalBuyValue = BigDecimal.ZERO;
    private BigDecimal profitLoss = BigDecimal.ZERO;
    private BigDecimal totalMarketValue = BigDecimal.ZERO;
    private BigDecimal totalMarketValueInEur = BigDecimal.ZERO;

    public Stock(String symbol, String stockName, Account account, BigDecimal currentPrice,
                 BigDecimal totalAmount, BigDecimal averagePrice, BigDecimal totalBuyValue,
                 BigDecimal profitLoss, BigDecimal totalMarketValue, BigDecimal totalMarketValueInEur) {
        this.symbol = symbol;
        this.stockName = stockName;
        this.account = account;
        this.currentPrice = currentPrice;
        this.totalAmount = totalAmount;
        this.averagePrice = averagePrice;
        this.totalBuyValue = totalBuyValue;
        this.profitLoss = profitLoss;
        this.totalMarketValue = totalMarketValue;
        this.totalMarketValueInEur = totalMarketValueInEur;
    }
}
