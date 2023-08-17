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
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private BigDecimal amount = BigDecimal.ZERO;
    private String transactionType;
    @ManyToOne
    private Account account;
    private LocalDate transactionDate;

    public Transaction(BigDecimal amount, String transactionType, Account account, LocalDate transactionDate) {
        this.amount = amount;
        this.transactionType = transactionType;
        this.account = account;
        this.transactionDate = transactionDate;
    }
}
