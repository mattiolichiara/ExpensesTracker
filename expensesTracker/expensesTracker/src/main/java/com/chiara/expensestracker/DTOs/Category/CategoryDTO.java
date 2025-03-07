package com.chiara.expensestracker.DTOs.Category;

import com.chiara.expensestracker.Entity.Category;
import com.chiara.expensestracker.Entity.Income;
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
public class CategoryDTO {

    @JsonProperty("idCategory")
    private Integer idCategory;
    @JsonProperty("categoryName")
    private String categoryName;
    @JsonProperty("isCustom")
    private Boolean isCustom;
    @JsonIgnore
    private Income idIncome;

    public CategoryDTO(Category c) {
        this.idCategory = c.getIdCategory();
        this.categoryName = c.getCategoryName();
        this.isCustom = c.getIsCustom();
        this.idIncome = c.getIdIncome();
    }

}
