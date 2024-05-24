package com.chiara.expensestracker.Controller;

import com.chiara.expensestracker.DTOs.GeneralResponses.GenericResponse;
import com.chiara.expensestracker.DTOs.SubCategory.CustomSubCategoryInsert;
import com.chiara.expensestracker.DTOs.SubCategory.CustomSubCategoryUpdate;
import com.chiara.expensestracker.DTOs.SubCategory.SubCategoryDTO;
import com.chiara.expensestracker.Exceptions.BadRequestException;
import com.chiara.expensestracker.Exceptions.InternalServerErrorException;
import com.chiara.expensestracker.Exceptions.NotFoundException;
import com.chiara.expensestracker.Service.SubCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/subCategory", name = "SubCategory")
public class SubCategoryController {

    private SubCategoryService subCategoryService;

    @Autowired
    public SubCategoryController(SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }

    @Operation(summary = "Get all sub categories by custom or default")
    @RequestMapping(value = "/getAllSubCategoriesByCustom", method = RequestMethod.GET, produces = "application/json")
    public GenericResponse<List<SubCategoryDTO>> getAllSubCategoriesByCustom(@RequestParam Boolean isCustom) throws NotFoundException, BadRequestException {

        return new GenericResponse<>(200, HttpStatus.OK, "Successful", subCategoryService.getAllbyCustom(isCustom));
    }

    @Operation(summary = "Get all sub categories by name search")
    @RequestMapping(value = "/getSubCategoriesByNameSearch", method = RequestMethod.GET, produces = "application/json")
    public GenericResponse<List<SubCategoryDTO>> getSubCategoriesByNameSearch(@RequestParam String textSearch) throws NotFoundException, BadRequestException {

        return new GenericResponse<>(200, HttpStatus.OK, "Successful", subCategoryService.getbyNameSearch(textSearch));
    }

    @Operation(summary = "Get sub category by id")
    @RequestMapping(value = "/getOneSubCategoryById", method = RequestMethod.GET, produces = "application/json")
    public GenericResponse<SubCategoryDTO> getOneSubCategorysById(@RequestParam Integer id) throws Exception {

        return new GenericResponse<>(200, HttpStatus.OK, "Successful", subCategoryService.getOnebyId(id));
    }

    @Operation(summary = "Get all sub categories")
    @RequestMapping(value = "/getAllSubCategories", method = RequestMethod.GET, produces = "application/json")
    public GenericResponse<List<SubCategoryDTO>> getAllCategories() throws NotFoundException {

        return new GenericResponse<>(200, HttpStatus.OK, "Successful", subCategoryService.getAllCategories());
    }

    @Operation(summary = "Add new custom Sub Categories")
    @RequestMapping(value = "/addCustomCategories", method = RequestMethod.POST, produces = "application/json")
    public GenericResponse<List<SubCategoryDTO>> addCustomSubCategories(@RequestBody List<CustomSubCategoryInsert> subCategoryDTOS) throws BadRequestException, InternalServerErrorException, NotFoundException {

        return new GenericResponse<>(200, HttpStatus.OK, "Successful", subCategoryService.addCustomSubCategories(subCategoryDTOS));
    }

    @Operation(summary = "Delete Custom Sub Category")
    @RequestMapping(value = "/deleteCustomSubCategory", method = RequestMethod.DELETE, produces = "application/json")
    public GenericResponse<List<SubCategoryDTO>> deleteCustomCategory(@RequestParam Integer toDelete) throws NotFoundException, InternalServerErrorException, BadRequestException {

        return new GenericResponse<>(200, HttpStatus.OK, "Successful", subCategoryService.deleteCustomSubCategory(toDelete));
    }

    @Operation(summary = "Delete Custom Sub Categories")
    @RequestMapping(value = "/deleteCustomSubCategories", method = RequestMethod.DELETE, produces = "application/json")
    public GenericResponse<List<SubCategoryDTO>> deleteCustomCategories(@RequestBody List<Integer> subCategoriesToDelete) throws NotFoundException, InternalServerErrorException, BadRequestException {

        return new GenericResponse<>(200, HttpStatus.OK, "Successful", subCategoryService.deleteCustomSubCategories(subCategoriesToDelete));
    }

    @Operation(summary = "Update Custom Sub Categoory")
    @RequestMapping(value = "/updateCustomSubCategory", method = RequestMethod.PUT, produces = "application/json")
    public GenericResponse<SubCategoryDTO> updateCustomSubCategory(@RequestBody CustomSubCategoryUpdate subCategoryToUpdate) throws NotFoundException, InternalServerErrorException, BadRequestException {

        return new GenericResponse<>(200, HttpStatus.OK, "Successful", subCategoryService.updateCustomSubCategory(subCategoryToUpdate));
    }
}
