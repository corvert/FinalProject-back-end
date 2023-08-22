package com.example.FinalProject.model;


import lombok.*;

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
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String accountName;
    private BigDecimal balance = BigDecimal.ZERO;
    private String currency;
    private BigDecimal balanceInEur = BigDecimal.ZERO;
    @ManyToOne
    private MyUser myUser;

    public Account(String accountName, BigDecimal balance, String currency, BigDecimal balanceInEur, MyUser myUser) {
        this.accountName = accountName;
        this.balance = balance;
        this.currency = currency;
        this.balanceInEur = balanceInEur;
        this.myUser = myUser;
    }
}
