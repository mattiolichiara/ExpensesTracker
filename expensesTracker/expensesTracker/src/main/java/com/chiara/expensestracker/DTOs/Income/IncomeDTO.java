package com.chiara.expensestracker.DTOs.Income;

import com.chiara.expensestracker.Entity.Income;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IncomeDTO {

    @JsonProperty("idIncome")
    private Integer idIncome;

    @JsonProperty("cashIncome")
    private BigDecimal cashIncome;

    @JsonProperty("cardIncome")
    private BigDecimal cardIncome;

    @JsonProperty("totalIncome")
    private BigDecimal totalIncome;

    public IncomeDTO(Income i) {
        this.idIncome = i.getIdIncome();
        this.cashIncome = i.getCashIncome();
        this.cardIncome = i.getCardIncome();
        this.totalIncome = i.getTotalIncome();
    }
}
