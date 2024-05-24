package com.chiara.expensestracker.Entity;

import com.chiara.expensestracker.DTOs.Income.IncomeDTO;
import com.chiara.expensestracker.DTOs.SubCategory.CustomSubCategoryInsert;
import com.chiara.expensestracker.DTOs.SubCategory.SubCategoryDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "subcategory", schema = "tracker")
@Entity
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sub_category", nullable = false)
    private Integer idSubCategory;
    @Column(name = "is_custom", nullable = false)
    private Boolean isCustom;
    @Column(name = "sub_category_name", nullable = false)
    private String subCategoryName;
    @JoinColumn(name = "id_category", nullable = false)
    @ManyToOne
    private Category idCategory;

    public SubCategory(SubCategoryDTO s) {
        this.idSubCategory = s.getIdSubCategory();
        this.isCustom = s.getIsCustom();
        this.subCategoryName = s.getSubCategoryName();
        this.idCategory = new Category(s.getIdCategory().getIdCategory(), s.getIdCategory().getCategoryName(), s.getIdCategory().getIsCustom(), s.getIdCategory().getIdIncome());
    }

    public SubCategory(CustomSubCategoryInsert s) {
        this.idSubCategory = s.getIdSubCategory();
        this.isCustom = s.getIsCustom();
        this.subCategoryName = s.getSubCategoryName();
        this.idCategory = new Category(s.getIdCategory().getIdCategory(), s.getIdCategory().getCategoryName(), s.getIdCategory().getIsCustom(), new Income(s.getIdCategory().getIdIncome()));
    }
}

