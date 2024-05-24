package com.chiara.expensestracker.DTOs.Expenses;

import com.chiara.expensestracker.Entity.Expenses;
import com.chiara.expensestracker.Entity.Income;
import com.chiara.expensestracker.Entity.SubCategory;
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
public class ExpensesDTO {

    @JsonProperty("idExpenses")
    private Integer idExpenses;
    @JsonProperty("creationDate")
    private LocalDate creationDate;
    @JsonProperty("comment")
    private String comment;
    @JsonProperty("amount")
    private BigDecimal amount;
    @JsonProperty("transactionType")
    private Boolean transactionType;
    @JsonProperty("idSubCategory")
    private SubCategory idSubCategory;
    @JsonProperty("idIncome")
    private Income idIncome;

    public ExpensesDTO(Expenses e) {
        this.idExpenses = e.getIdExpenses();
        this.creationDate = e.getCreationDate();
        this.comment = e.getComment();
        this.amount = e.getAmount();
        this.transactionType = e.getTransactionType();
        this.idSubCategory = e.getIdSubCategory();
        this.idIncome = e.getIdIncome();
    }
}
