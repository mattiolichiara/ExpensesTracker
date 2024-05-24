package com.chiara.expensestracker.Service;

import com.chiara.expensestracker.DTOs.Category.CategoryDTO;
import com.chiara.expensestracker.DTOs.Category.CustomCategoryInsert;
import com.chiara.expensestracker.DTOs.Category.CustomCategoryUpdate;
import com.chiara.expensestracker.Entity.Category;
import com.chiara.expensestracker.Exceptions.BadRequestException;
import com.chiara.expensestracker.Exceptions.HTTPRunTimeException;
import com.chiara.expensestracker.Exceptions.InternalServerErrorException;
import com.chiara.expensestracker.Exceptions.NotFoundException;
import com.chiara.expensestracker.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> getAllbyCustom(Boolean isCustom) throws NotFoundException, BadRequestException {

        if(isCustom==null) {
            throw new BadRequestException("Null Request");
        }

        List<Category> categories = categoryRepository.getAllByIsCustom(isCustom);
        List<CategoryDTO> categoryDTOs = categories.stream().map(c -> new CategoryDTO(c)).collect(Collectors.toList());

        if(categories.isEmpty() || categoryDTOs.isEmpty()) {
            throw new NotFoundException("No categories found");
        }

        return categoryDTOs;
    }

    public CategoryDTO getOnebyId(Integer id) throws NotFoundException, BadRequestException {
        CategoryDTO categoryDTO;

        if(id==null) {
            throw new BadRequestException("Null Request");
        }

        Optional<Category> category = categoryRepository.findById(id);
        if(category.isPresent()) {
            categoryDTO = new CategoryDTO(category.get());
        } else {
            throw new NotFoundException("No Category Found");
        }

        return categoryDTO;
    }

    public List<CategoryDTO> getbyNameSearch(String textSearch) throws NotFoundException, BadRequestException {

        if(textSearch == null || textSearch.isEmpty()) {
            throw new BadRequestException("Null Request");
        }

        List<Category> categories = categoryRepository.getAllByCategoryNameContainsIgnoreCase(textSearch);
        List<CategoryDTO> categoryDTOs = categories.stream().map(c -> new CategoryDTO(c)).collect(Collectors.toList());

        if(categories.isEmpty() || categoryDTOs.isEmpty()) {
            throw new NotFoundException("No Category Found");
        }

        return categoryDTOs;
    }

    public List<CategoryDTO> getAllCategories() throws NotFoundException {

        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOs = categories.stream().map(c -> new CategoryDTO(c)).collect(Collectors.toList());

        if(categories.isEmpty() || categoryDTOs.isEmpty()) {
            throw  new NotFoundException("No categories found");
        }


        return categoryDTOs;
    }

    public List<CategoryDTO> addCustomCategories(List<CustomCategoryInsert> categoryDTOS) throws BadRequestException, InternalServerErrorException {
        List<Category> newCustomCategories;

        if(categoryDTOS==null) {
            throw new BadRequestException("Null Request");
        }

        if(categoryDTOS.isEmpty()) {
            throw new BadRequestException("No data received");
        }

        for(CustomCategoryInsert cat: categoryDTOS) {
            if(cat.getCategoryName() == null || cat.getCategoryName().equals("")) {
                throw new BadRequestException("Category name can't be empty");
            }
        }

        try {
            newCustomCategories = categoryDTOS.stream().map(c -> new Category(c)).collect(Collectors.toList());
            newCustomCategories = categoryRepository.saveAll(newCustomCategories);
        } catch(HTTPRunTimeException e) {
            throw new InternalServerErrorException("There was an error while saving new Categories");
        }

        return newCustomCategories.stream().map(c -> new CategoryDTO(c)).collect(Collectors.toList());
    }

    public List<CategoryDTO> deleteCustomCategory(Integer categoryToDelete) throws NotFoundException, InternalServerErrorException, BadRequestException {

        if(categoryToDelete==null) {
            throw new BadRequestException("Null Request");
        }

        if(categoryRepository.existsByIdCategoryAndIsCustomIsTrue(categoryToDelete)) {

            try {
                categoryRepository.deleteById(categoryToDelete);
            } catch(HTTPRunTimeException e) {
                throw new InternalServerErrorException("There was an error during deletion");
            }

        } else {
            throw new NotFoundException("Category can't be deleted because it either doesn't exist or it's a default category");
        }

        return getAllbyCustom(true);
    }

    public List<CategoryDTO> deleteCustomCategories(List<Integer> categoriesToDelete) throws NotFoundException, InternalServerErrorException, BadRequestException {

        if(categoriesToDelete==null) {
            throw new BadRequestException("Null Request");
        }

        if(categoryRepository.existsAllByIdCategoryInAndIsCustomIsTrue(categoriesToDelete)) {

            try {
                categoryRepository.deleteAllById(categoriesToDelete);
            } catch(HTTPRunTimeException e) {
                throw new InternalServerErrorException("There was an error during deletion");
            }

        } else {
            throw new NotFoundException("One or More Categories can't be deleted because they either don't exist or there's a default category");
        }

        return getAllCategories();
    }

    public CategoryDTO updateCustomCategory(CustomCategoryUpdate categoryToUpdate) throws NotFoundException, InternalServerErrorException, BadRequestException {
        Category category = null;

        if(categoryToUpdate==null) {
            throw new BadRequestException("Null Request");
        }

        if(categoryRepository.existsById(categoryToUpdate.getIdCategory())) {
            category = categoryRepository.getByIdCategory(categoryToUpdate.getIdCategory());
        } else {
            throw new NotFoundException("This Category doesn't exist");
        }

        if(categoryToUpdate.getCategoryName() == null || categoryToUpdate.getCategoryName().equals("") || categoryToUpdate.getCategoryName().isEmpty()) {
            throw new BadRequestException("An empty category name can't be added");
        }

        if(category.getIsCustom()) {

            try {
                category.setCategoryName(categoryToUpdate.getCategoryName());
                categoryRepository.save(category);
            } catch(HTTPRunTimeException e) {
                throw new InternalServerErrorException("There was an error during update");
            }

        } else {
            throw new BadRequestException("Default Categories cannot be modified");
        }

        return new CategoryDTO(category);
    }

}
