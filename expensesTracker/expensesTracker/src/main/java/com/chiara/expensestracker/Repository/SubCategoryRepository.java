package com.chiara.expensestracker.Repository;

import com.chiara.expensestracker.Entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Integer> {

    List<SubCategory> getAllByIsCustom(Boolean isCustom);

    List<SubCategory> getAllBySubCategoryNameContainsIgnoreCase(String textSearch);

    Boolean existsByIdSubCategoryAndIsCustomIsTrue(Integer idSubCategory);

    Boolean existsAllByIdSubCategoryInAndIsCustomIsTrue(List<Integer> idSubCategory);

    SubCategory getByIdSubCategory(Integer id);
}
