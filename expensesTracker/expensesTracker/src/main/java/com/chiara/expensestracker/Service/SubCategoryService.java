package com.chiara.expensestracker.Service;

import com.chiara.expensestracker.DTOs.Category.CategoryDTO;
import com.chiara.expensestracker.DTOs.Category.FindCategory;
import com.chiara.expensestracker.DTOs.Income.IncomeDTO;
import com.chiara.expensestracker.DTOs.SubCategory.CustomSubCategoryInsert;
import com.chiara.expensestracker.DTOs.SubCategory.CustomSubCategoryUpdate;
import com.chiara.expensestracker.DTOs.SubCategory.SubCategoryDTO;
import com.chiara.expensestracker.Entity.Category;
import com.chiara.expensestracker.Entity.SubCategory;
import com.chiara.expensestracker.Exceptions.BadRequestException;
import com.chiara.expensestracker.Exceptions.HTTPRunTimeException;
import com.chiara.expensestracker.Exceptions.InternalServerErrorException;
import com.chiara.expensestracker.Exceptions.NotFoundException;
import com.chiara.expensestracker.Repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;
    private final CategoryService categoryService;

    @Autowired
    public SubCategoryService(SubCategoryRepository subCategoryRepository, CategoryService categoryService) {
        this.subCategoryRepository = subCategoryRepository;
        this.categoryService = categoryService;
    }

    public List<SubCategoryDTO> getAllbyCustom(Boolean isCustom) throws NotFoundException, BadRequestException {

        if(isCustom==null) {
            throw new BadRequestException("Null Request");
        }

        List<SubCategory> subCategories = subCategoryRepository.getAllByIsCustom(isCustom);
        List<SubCategoryDTO> subCategoryDTOs = subCategories.stream().map(s -> new SubCategoryDTO(s)).collect(Collectors.toList());

        if (subCategories.isEmpty() || subCategoryDTOs.isEmpty()) {
            throw new NotFoundException("No Sub Sategories found");
        }

        return subCategoryDTOs;
    }

    public SubCategoryDTO getOnebyId(Integer id) throws NotFoundException, BadRequestException {
        SubCategoryDTO subCategoryDTO;

        if(id==null) {
            throw new BadRequestException("Null Request");
        }

        Optional<SubCategory> subCategory = subCategoryRepository.findById(id);
        if (subCategory.isPresent()) {
            subCategoryDTO = new SubCategoryDTO(subCategory.get());
        } else {
            throw new NotFoundException("No Sub Sategory found");
        }

        return subCategoryDTO;
    }

    public List<SubCategoryDTO> getbyNameSearch(String textSearch) throws NotFoundException, BadRequestException {

        if(textSearch == null || textSearch.isEmpty()) {
            throw new BadRequestException("Null Request");
        }

        List<SubCategory> subCategories = subCategoryRepository.getAllBySubCategoryNameContainsIgnoreCase(textSearch);
        List<SubCategoryDTO> subCategoryDTOs = subCategories.stream().map(s -> new SubCategoryDTO(s)).collect(Collectors.toList());

        if (subCategories.isEmpty() || subCategoryDTOs.isEmpty()) {
            throw new NotFoundException("No Sub Sategory found");
        }

        return subCategoryDTOs;
    }

    public List<SubCategoryDTO> getAllCategories() throws NotFoundException {

        List<SubCategory> subCategories = subCategoryRepository.findAll();
        List<SubCategoryDTO> subCategoryDTOs = subCategories.stream().map(s -> new SubCategoryDTO(s)).collect(Collectors.toList());

        if (subCategories.isEmpty() || subCategoryDTOs.isEmpty()) {
            throw new NotFoundException("No Sub Sategory found");
        }


        return subCategoryDTOs;
    }

    public List<SubCategoryDTO> addCustomSubCategories(List<CustomSubCategoryInsert> subcategoryDTOs) throws BadRequestException, NotFoundException, InternalServerErrorException {
        List<SubCategory> newCustomSubCategories;
        CategoryDTO category = null;

        if(subcategoryDTOs==null) {
            throw new BadRequestException("Null Request");
        }

        if(subcategoryDTOs.isEmpty()) {
            throw new BadRequestException("No data received");
        }

        for(CustomSubCategoryInsert subcat : subcategoryDTOs) {
            category = categoryService.getOnebyId(subcat.getIdCategory().getIdCategory());
            subcat.setIdCategory(new FindCategory(category.getIdCategory(), category.getCategoryName(), category.getIsCustom(), new IncomeDTO(category.getIdIncome())));
            if(subcat.getSubCategoryName().equals("") || subcat.getSubCategoryName()==null) {
                throw new BadRequestException("Sub Category name can't be empty");
            }
        }

        try {
            newCustomSubCategories = subcategoryDTOs.stream().map(s -> new SubCategory(s)).collect(Collectors.toList());
            newCustomSubCategories = subCategoryRepository.saveAll(newCustomSubCategories);
        } catch (HTTPRunTimeException e) {
            throw new InternalServerErrorException("There was an error while saving new Sub Categories");
        }

        return newCustomSubCategories.stream().map(s -> new SubCategoryDTO(s)).collect(Collectors.toList());
    }

    public List<SubCategoryDTO> deleteCustomSubCategory(Integer idSubCategory) throws NotFoundException, InternalServerErrorException, BadRequestException {

        if(idSubCategory==null) {
            throw new BadRequestException("Null Request");
        }

        if(subCategoryRepository.existsByIdSubCategoryAndIsCustomIsTrue(idSubCategory)) {

            try {
                subCategoryRepository.deleteById(idSubCategory);
            } catch(HTTPRunTimeException e) {
                throw new InternalServerErrorException("There was an error during deletion");
            }

        } else {
            throw new NotFoundException("Sub Category can't be deleted because it either doesn't exist or it's a default Sub Category");
        }

        return getAllbyCustom(true);
    }

    public List<SubCategoryDTO> deleteCustomSubCategories(List<Integer> idSubCategories) throws NotFoundException, InternalServerErrorException, BadRequestException {

        if(idSubCategories==null) {
            throw new BadRequestException("Null Request");
        }

        if(subCategoryRepository.existsAllByIdSubCategoryInAndIsCustomIsTrue(idSubCategories)) {
            try {
                subCategoryRepository.deleteAllById(idSubCategories);
            } catch(HTTPRunTimeException e) {
                throw new InternalServerErrorException("There was an error during deletion");
            }
        } else {
            throw new NotFoundException("One or More Sub Categories can't be deleted because they either don't exist or there's a default sub category");
        }

        return getAllCategories();
    }

    public SubCategoryDTO updateCustomSubCategory(CustomSubCategoryUpdate subcategoryToUpdate) throws NotFoundException, InternalServerErrorException, BadRequestException {
        CategoryDTO category;
        SubCategory subCategory;

        if(subcategoryToUpdate==null) {
            throw new BadRequestException("Null Request");
        }

        if(subcategoryToUpdate.getSubCategoryName() == null || subcategoryToUpdate.getSubCategoryName().equals("") || subcategoryToUpdate.getSubCategoryName().isEmpty()) {
            throw new BadRequestException("An empty sub category name can't be added");
        }

        if(subCategoryRepository.existsById(subcategoryToUpdate.getIdSubCategory())) {
            category = categoryService.getOnebyId(subcategoryToUpdate.getIdCategory().getIdCategory());
            subCategory = subCategoryRepository.getByIdSubCategory(subcategoryToUpdate.getIdSubCategory());
        } else {
            throw new NotFoundException("This Category doesn't exist");
        }

        if(subCategory.getIsCustom()) {

            try {
                subCategory.setIdCategory(new Category(category.getIdCategory(), category.getCategoryName(), category.getIsCustom(), category.getIdIncome()));
                subCategory.setSubCategoryName(subcategoryToUpdate.getSubCategoryName());
                subCategoryRepository.save(subCategory);
            } catch(HTTPRunTimeException e) {
                throw new InternalServerErrorException("There was an error during update");
            }

        } else {
            throw new BadRequestException("Default Sub Categories cannot be modified");
        }

        return new SubCategoryDTO(subCategory);
    }
}
