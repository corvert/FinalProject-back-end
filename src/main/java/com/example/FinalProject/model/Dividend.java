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
public class Dividend implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate date;
    @ManyToOne
    private Stock stock;
    private BigDecimal grossAmount = BigDecimal.ZERO;
    private BigDecimal withholdingTax = BigDecimal.ZERO;
    private BigDecimal netAmount = BigDecimal.ZERO;

    public Dividend(LocalDate date, Stock stock, Account account, BigDecimal grossAmount, BigDecimal withholdingTax, BigDecimal netAmount) {
        this.date = date;
        this.stock = stock;
        this.grossAmount = grossAmount;
        this.withholdingTax = withholdingTax;
        this.netAmount = netAmount;
    }
}
