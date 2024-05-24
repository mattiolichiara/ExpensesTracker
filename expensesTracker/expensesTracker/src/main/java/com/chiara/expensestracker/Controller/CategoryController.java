package com.chiara.expensestracker.Controller;

import com.chiara.expensestracker.DTOs.Category.CategoryDTO;
import com.chiara.expensestracker.DTOs.Category.CustomCategoryInsert;
import com.chiara.expensestracker.DTOs.Category.CustomCategoryUpdate;
import com.chiara.expensestracker.DTOs.GeneralResponses.GenericResponse;
import com.chiara.expensestracker.Exceptions.BadRequestException;
import com.chiara.expensestracker.Exceptions.InternalServerErrorException;
import com.chiara.expensestracker.Exceptions.NotFoundException;
import com.chiara.expensestracker.Service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/category", name = "Category")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Get all categories by custom or default")
    @RequestMapping(value = "/getAllCategoriesByCustom", method = RequestMethod.GET, produces = "application/json")
    public GenericResponse<List<CategoryDTO>> getAllCategoriesByCustom(@RequestParam Boolean isCustom) throws NotFoundException, BadRequestException {

        return new GenericResponse<>(200, HttpStatus.OK, "Successful", categoryService.getAllbyCustom(isCustom));
    }

    @Operation(summary = "Get all categories by name search")
    @RequestMapping(value = "/getCategoriesByNameSearch", method = RequestMethod.GET, produces = "application/json")
    public GenericResponse<List<CategoryDTO>> getCategoriesByNameSearch(@RequestParam String textSearch) throws NotFoundException, BadRequestException {

        return new GenericResponse<>(200, HttpStatus.OK, "Successful", categoryService.getbyNameSearch(textSearch));
    }

    @Operation(summary = "Get category by id")
    @RequestMapping(value = "/getOneCategoryById", method = RequestMethod.GET, produces = "application/json")
    public GenericResponse<CategoryDTO> getOneCategorysById(@RequestParam Integer id) throws Exception {

        return new GenericResponse<>(200, HttpStatus.OK, "Successful", categoryService.getOnebyId(id));
    }

    @Operation(summary = "Get all categories")
    @RequestMapping(value = "/getAllCategories", method = RequestMethod.GET, produces = "application/json")
    public GenericResponse<List<CategoryDTO>> getAllCategories() throws NotFoundException {

        return new GenericResponse<>(200, HttpStatus.OK, "Successful", categoryService.getAllCategories());
    }

    @Operation(summary = "Add new custom Categories")
    @RequestMapping(value = "/addCustomCategories", method = RequestMethod.POST, produces = "application/json")
    public GenericResponse<List<CategoryDTO>> addCustomCategories(@RequestBody List<CustomCategoryInsert> categoryDTOS) throws BadRequestException, InternalServerErrorException {

        return new GenericResponse<>(200, HttpStatus.OK, "Successful", categoryService.addCustomCategories(categoryDTOS));
    }

    @Operation(summary = "Delete Custom Category")
    @RequestMapping(value = "/deleteCustomCategory", method = RequestMethod.DELETE, produces = "application/json")
    public GenericResponse<List<CategoryDTO>> deleteCustomCategory(@RequestParam Integer toDelete) throws NotFoundException, InternalServerErrorException, BadRequestException {

        return new GenericResponse<>(200, HttpStatus.OK, "Successful", categoryService.deleteCustomCategory(toDelete));
    }

    @Operation(summary = "Delete Custom Categories")
    @RequestMapping(value = "/deleteCustomCategories", method = RequestMethod.DELETE, produces = "application/json")
    public GenericResponse<List<CategoryDTO>> deleteCustomCategories(@RequestBody List<Integer> categoriesToDelete) throws NotFoundException, InternalServerErrorException, BadRequestException {

        return new GenericResponse<>(200, HttpStatus.OK, "Successful", categoryService.deleteCustomCategories(categoriesToDelete));
    }

    @Operation(summary = "Update Custom Categoory")
    @RequestMapping(value = "/updateCustomCategory", method = RequestMethod.PUT, produces = "application/json")
    public GenericResponse<CategoryDTO> updateCustomCategory(@RequestBody CustomCategoryUpdate categoryToUpdate) throws NotFoundException, InternalServerErrorException, BadRequestException {

        return new GenericResponse<>(200, HttpStatus.OK, "Successful", categoryService.updateCustomCategory(categoryToUpdate));
    }

}
