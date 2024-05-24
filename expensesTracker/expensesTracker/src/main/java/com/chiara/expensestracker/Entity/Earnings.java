package com.chiara.expensestracker.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "earnings", schema = "tracker")
@Entity
public class Earnings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_earning", nullable = false)
    private Integer idEarning;
    @Column(name = "creation_date", nullable = false)
    private Date creationDate;
    @Column(name = "comment")
    private String comment;
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
    @Column(name = "transaction_type", nullable = false)
    private BigDecimal transactionType;
    @JoinColumn(name = "id_sub_category", nullable = false)
    @OneToOne
    private SubCategory idSubCategory;
    @JoinColumn(name = "id_income", nullable = false)
    @OneToOne
    private Income idIncome;
}
