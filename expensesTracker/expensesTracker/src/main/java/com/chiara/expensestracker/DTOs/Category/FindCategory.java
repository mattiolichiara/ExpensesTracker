package com.chiara.expensestracker.DTOs.Category;

import com.chiara.expensestracker.DTOs.Income.IncomeDTO;
import com.chiara.expensestracker.Entity.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class FindCategory {

    @JsonProperty("idCategory")
    private Integer idCategory;
    @JsonIgnore
    private String categoryName;
    @JsonIgnore
    private Boolean isCustom = true;
    @JsonIgnore
    private IncomeDTO idIncome;

    public FindCategory(Category c) {
        this.idCategory = c.getIdCategory();
        this.categoryName = c.getCategoryName();
        this.isCustom = c.getIsCustom();
        this.idIncome = new IncomeDTO(c.getIdIncome());
    }
}
