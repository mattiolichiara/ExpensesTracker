package com.chiara.expensestracker.DTOs.SubCategory;

import com.chiara.expensestracker.DTOs.Category.FindCategory;
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
public class CustomSubCategoryUpdate {

    @JsonProperty("idSubCategory")
    private Integer idSubCategory;

    @JsonIgnore
    private Boolean isCustom = true;

    @JsonProperty("subCategoryName")
    private String subCategoryName;

    @JsonProperty("idCategory")
    private FindCategory idCategory;

    public CustomSubCategoryUpdate(SubCategory s) {
        this.idSubCategory = s.getIdSubCategory();
        this.isCustom = s.getIsCustom();
        this.subCategoryName = s.getSubCategoryName();
        this.idCategory = new FindCategory(s.getIdCategory().getIdCategory(), s.getIdCategory().getCategoryName(), s.getIdCategory().getIsCustom());
    }
}
