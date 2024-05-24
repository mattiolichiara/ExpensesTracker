package com.chiara.expensestracker.DTOs.SubCategory;

import com.chiara.expensestracker.DTOs.Category.FindCategory;
import com.chiara.expensestracker.DTOs.Income.IncomeDTO;
import com.chiara.expensestracker.Entity.Income;
import com.chiara.expensestracker.Entity.SubCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomSubCategoryInsert {

    @JsonIgnore
    private Integer idSubCategory = null;
    @JsonIgnore
    private Boolean isCustom = true;
    @JsonProperty("subCategoryName")
    private String subCategoryName;
    @JsonProperty("idCategory")
    private FindCategory idCategory;
    @JsonIgnore
    private IncomeDTO idIncome;

    public CustomSubCategoryInsert(SubCategory s) {
        this.idSubCategory = s.getIdSubCategory();
        this.isCustom = s.getIsCustom();
        this.subCategoryName = s.getSubCategoryName();
        this.idCategory = new FindCategory(s.getIdCategory());
        this.idIncome = new IncomeDTO(s.getIdCategory().getIdIncome());
    }
}
