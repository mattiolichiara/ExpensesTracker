package com.chiara.expensestracker.Controller;

import com.chiara.expensestracker.DTOs.GeneralResponses.GenericResponse;
import com.chiara.expensestracker.DTOs.Income.*;
import com.chiara.expensestracker.Exceptions.BadRequestException;
import com.chiara.expensestracker.Exceptions.InternalServerErrorException;
import com.chiara.expensestracker.Exceptions.NotFoundException;
import com.chiara.expensestracker.Service.IncomeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/income", name = "Income")
public class IncomeController {

    private IncomeService incomeService;

    @Autowired
    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @Operation(summary = "Get income by id")
    @RequestMapping(value = "/getIncomeById", method = RequestMethod.GET, produces = "application/json")
    public GenericResponse<IncomeDTO> getIncomebyId(@RequestParam Integer idIncome) throws NotFoundException, BadRequestException {

        return new GenericResponse<>(200, HttpStatus.OK, "Successful", incomeService.getIncomebyId(idIncome));
    }

    @Operation(summary = "Add new Income")
    @RequestMapping(value = "/addIncome", method = RequestMethod.POST, produces = "application/json")
    public GenericResponse<IncomeDTO> addIncome(@RequestBody InsertIncome income) throws BadRequestException, InternalServerErrorException {

        return new GenericResponse<>(200, HttpStatus.OK, "Successful", incomeService.addIncome(income));
    }

    @Operation(summary = "Update Income")
    @RequestMapping(value = "/updateIncome", method = RequestMethod.PUT, produces = "application/json")
    public GenericResponse<IncomeDTO> updateIncome(@RequestBody UpdateIncome income) throws BadRequestException, InternalServerErrorException, NotFoundException {

        return new GenericResponse<>(200, HttpStatus.OK, "Successful", incomeService.updateIncome(income));
    }

    @Operation(summary = "Update Cash Income")
    @RequestMapping(value = "/updateCashIncome", method = RequestMethod.PUT, produces = "application/json")
    public GenericResponse<IncomeDTO> updateCashIncome(@RequestBody UpdateCashIncome income) throws BadRequestException, InternalServerErrorException, NotFoundException {

        return new GenericResponse<>(200, HttpStatus.OK, "Successful", incomeService.updateCashIncome(income));
    }

    @Operation(summary = "Update Card Income")
    @RequestMapping(value = "/updateCardIncome", method = RequestMethod.PUT, produces = "application/json")
    public GenericResponse<IncomeDTO> updateCardIncome(@RequestBody UpdateCardIncome income) throws BadRequestException, InternalServerErrorException, NotFoundException {

        return new GenericResponse<>(200, HttpStatus.OK, "Successful", incomeService.updateCardIncome(income));
    }
}
