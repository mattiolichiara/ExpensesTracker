package com.chiara.expensestracker.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "expenses", schema = "tracker")
@Entity
public class Expenses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_expenses", nullable = false)
    private Integer idExpenses;
    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;
    @Column(name = "comment")
    private String comment;
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
    @Column(name = "transaction_type", nullable = false)
    private String transactionType;
    @JoinColumn(name = "id_sub_category", nullable = false)
    @ManyToOne
    private SubCategory idSubCategory;
    @JoinColumn(name = "id_income", nullable = false)
    @ManyToOne
    private Income idIncome;



}
