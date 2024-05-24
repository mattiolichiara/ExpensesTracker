package com.chiara.expensestracker.Entity;

import com.chiara.expensestracker.DTOs.Category.CategoryDTO;
import com.chiara.expensestracker.DTOs.Category.CustomCategoryInsert;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "category", schema = "tracker")
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_category", nullable = false)
    private Integer idCategory;
    @Column(name = "category_name", nullable = false)
    private String categoryName;
    @Column(name = "is_custom", nullable = false)
    private Boolean isCustom;
    @JoinColumn(name = "id_income")
    @ManyToOne
    private Income idIncome;

    public Category(CategoryDTO c) {
        this.categoryName = c.getCategoryName();
        this.idCategory = c.getIdCategory();
        this.isCustom = c.getIsCustom();
        this.idIncome = c.getIdIncome();
    }

    public Category(CustomCategoryInsert c) {
        this.categoryName = c.getCategoryName();
        this.idCategory = c.getIdCategory();
        this.isCustom = c.getIsCustom();
        this.idIncome = c.getIdIncome();
    }

}
