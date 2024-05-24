package com.chiara.expensestracker.Repository;

import com.chiara.expensestracker.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    List<Category> getAllByIsCustom(Boolean isCustom);

    Category getByIdCategory(Integer id);

    List<Category> getAllByCategoryNameContainsIgnoreCase(String searchText);

    Boolean existsByIdCategoryAndIsCustomIsTrue(Integer id);

    Boolean existsAllByIdCategoryInAndIsCustomIsTrue(List<Integer> ids);


}
