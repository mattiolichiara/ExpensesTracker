package com.chiara.expensestracker.DTOs.SubCategory;

import com.chiara.expensestracker.DTOs.Category.CategoryDTO;
import com.chiara.expensestracker.Entity.SubCategory;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SubCategoryDTO {

    @JsonProperty("idSubCategory")
    private Integer idSubCategory;

    @JsonProperty("isCustom")
    private Boolean isCustom;

    @JsonProperty("subCategoryName")
    private String subCategoryName;;

    @JsonProperty("idCategory")
    private CategoryDTO idCategory;

    public SubCategoryDTO(SubCategory s) {
        this.idSubCategory = s.getIdSubCategory();
        this.isCustom = s.getIsCustom();
        this.subCategoryName = s.getSubCategoryName();
        this.idCategory = new CategoryDTO(s.getIdCategory().getIdCategory(), s.getIdCategory().getCategoryName(), s.getIdCategory().getIsCustom());
    }

}
