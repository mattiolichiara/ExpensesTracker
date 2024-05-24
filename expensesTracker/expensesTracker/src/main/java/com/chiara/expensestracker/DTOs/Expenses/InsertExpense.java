package com.chiara.expensestracker.DTOs.Expenses;

import com.chiara.expensestracker.Entity.Expenses;
import com.chiara.expensestracker.Utils.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class InsertExpense {

    @JsonIgnore
    private Integer idExpenses;
    @JsonProperty("creationDate")
    private LocalDate creationDate;
    @JsonProperty("comment")
    private String comment;
    @JsonProperty("amount")
    private BigDecimal amount;
    @JsonProperty("transactionType")
    private TransactionType transactionType;
    @JsonProperty("idSubCategory")
    private Integer idSubCategory;
    @JsonProperty("idIncome")
    private Integer idIncome;

    public InsertExpense(Expenses e) {
        this.idExpenses = e.getIdExpenses();
        this.creationDate = e.getCreationDate();
        this.comment = e.getComment();
        this.amount = e.getAmount();
        this.transactionType = e.getTransactionType();
        this.idSubCategory = e.getIdSubCategory().getIdSubCategory();
        this.idIncome = e.getIdIncome().getIdIncome();
    }
}
