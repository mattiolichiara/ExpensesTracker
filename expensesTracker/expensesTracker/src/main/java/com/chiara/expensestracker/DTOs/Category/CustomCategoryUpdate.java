package com.chiara.expensestracker.DTOs.Category;

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
public class CustomCategoryUpdate {

    @JsonProperty("idCategory")
    private Integer idCategory;
    @JsonProperty("categoryName")
    private String categoryName;
    @JsonIgnore
    private Boolean isCustom = true;

    public CustomCategoryUpdate(Category c) {
        this.idCategory = c.getIdCategory();
        this.categoryName = c.getCategoryName();
        this.isCustom = c.getIsCustom();
    }
}
