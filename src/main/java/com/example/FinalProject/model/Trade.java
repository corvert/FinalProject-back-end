package com.example.FinalProject.model;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Trade implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String tradeType;
    @ManyToOne
    private Stock stock;
    private LocalDate date;
    private BigDecimal amount = BigDecimal.ZERO;
    private BigDecimal unitPrice = BigDecimal.ZERO;
    private BigDecimal commission = BigDecimal.ZERO;
    private BigDecimal tradeSum = BigDecimal.ZERO;
    private String comment;

    public Trade(String tradeType, Stock stock, LocalDate date, BigDecimal amount, BigDecimal unitPrice, BigDecimal commission, BigDecimal tradeSum, String comment) {
        this.tradeType = tradeType;
        this.stock = stock;
        this.date = date;
        this.amount = amount;
        this.unitPrice = unitPrice;
        this.commission = commission;
        this.tradeSum = tradeSum;
        this.comment = comment;
    }
}
