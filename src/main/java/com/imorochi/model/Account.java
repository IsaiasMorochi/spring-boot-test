package com.imorochi.model;

import com.imorochi.exception.InsufficientMoneyException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String person;
    private BigDecimal saldo;

    public void debit(BigDecimal amount) {
        BigDecimal newSaldo = this.saldo.subtract(amount);
        if (newSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientMoneyException("Insufficient Money in Account!!!");
        }
        this.saldo = newSaldo;
    }

    public void credit(BigDecimal amount) {
        this.saldo = this.saldo.add(amount);
    }

}
