package com.example.FinalProject.model;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String accountName;
    private BigDecimal balance = BigDecimal.ZERO;
    private String currency;
    private Boolean status;
    private BigDecimal balanceInEur = BigDecimal.ZERO;
    private BigDecimal currencyRate = BigDecimal.ZERO;


    public Account(String accountName, BigDecimal balance, String currency, Boolean status, BigDecimal balanceInEur, BigDecimal currencyRate) {
        this.accountName = accountName;
        this.balance = balance;
        this.currency = currency;
        this.status = status;
        this.balanceInEur = balanceInEur;
        this.currencyRate = currencyRate;
    }
}
