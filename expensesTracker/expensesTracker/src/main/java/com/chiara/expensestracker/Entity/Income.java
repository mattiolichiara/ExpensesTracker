package com.chiara.expensestracker.Entity;

import com.chiara.expensestracker.DTOs.Income.IncomeDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "income", schema = "tracker")
@Entity
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_income", nullable = false)
    private Integer idIncome;
    @Column(name = "cash_income", nullable = false)
    private BigDecimal cashIncome;
    @Column(name = "card_income", nullable = false)
    private BigDecimal cardIncome;
    @Column(name = "total_income", nullable = false)
    private BigDecimal totalIncome;
//    @JoinColumn(name = "id_user", nullable = false)
//    @ManyToOne
//    private User idUser;

    public Income(IncomeDTO i) {
        this.idIncome = i.getIdIncome();
        this.cashIncome = i.getCashIncome();
        this.cardIncome = i.getCardIncome();
        this.totalIncome = i.getTotalIncome();
    }

}
