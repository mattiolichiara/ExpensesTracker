package com.chiara.expensestracker.Controller;

import com.chiara.expensestracker.DTOs.Expenses.ExpensesDTO;
import com.chiara.expensestracker.DTOs.Expenses.InsertExpense;
import com.chiara.expensestracker.DTOs.Expenses.UpdateExpense;
import com.chiara.expensestracker.DTOs.GeneralResponses.GenericResponse;
import com.chiara.expensestracker.Exceptions.BadRequestException;
import com.chiara.expensestracker.Exceptions.InternalServerErrorException;
import com.chiara.expensestracker.Exceptions.NotFoundException;
import com.chiara.expensestracker.Service.ExpensesService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/api/expenses", name = "Expenses")
public class ExpensesController {

    private ExpensesService expensesService;

    @Autowired
    public ExpensesController(ExpensesService expensesService) {
        this.expensesService = expensesService;
    }

    @Operation(summary = "Add new Expenses")
    @RequestMapping(value = "/addExpense", method = RequestMethod.POST, produces = "application/json")
    public GenericResponse<ExpensesDTO> addExpense(@RequestBody InsertExpense insertExpense) throws BadRequestException, NotFoundException, InternalServerErrorException {

        return new GenericResponse<>(200, HttpStatus.OK, "Successful", expensesService.addExpense(insertExpense));
    }

    @Operation(summary = "Delete Expense by Id")
    @RequestMapping(value = "/deleteExpensebyId", method = RequestMethod.DELETE, produces = "application/json")
    public GenericResponse<List<ExpensesDTO>> deleteExpensebyId(@RequestParam Integer id) throws BadRequestException, NotFoundException, InternalServerErrorException {

        return new GenericResponse<>(200, HttpStatus.OK, "Successful", expensesService.deleteExpensebyId(id));
    }

    @Operation(summary = "Delete Expenses")
    @RequestMapping(value = "/deleteExpenses", method = RequestMethod.DELETE, produces = "application/json")
    public GenericResponse<List<ExpensesDTO>> deleteExpenses(@RequestBody List<Integer> ids) throws BadRequestException, NotFoundException, InternalServerErrorException {

        return new GenericResponse<>(200, HttpStatus.OK, "Successful", expensesService.deleteExpenses(ids));
    }

    @Operation(summary = "Update Expense")
    @RequestMapping(value = "/updateExpense", method = RequestMethod.PUT, produces = "application/json")
    public GenericResponse<ExpensesDTO> updateExpenses(@RequestBody UpdateExpense updateExpense) throws BadRequestException, NotFoundException, InternalServerErrorException {

        return new GenericResponse<>(200, HttpStatus.OK, "Successful", expensesService.updateExpense(updateExpense));
    }

    @Operation(summary = "Get Expense by Id")
    @RequestMapping(value = "/getExpensebyId", method = RequestMethod.GET, produces = "application/json")
    public GenericResponse<ExpensesDTO> getExpensebyId(@RequestParam Integer id) throws BadRequestException, NotFoundException, InternalServerErrorException {

        return new GenericResponse<>(200, HttpStatus.OK, "Successful", expensesService.getOnebyId(id));
    }

    @Operation(summary = "Get Expense by Time Frame")
    @RequestMapping(value = "/getExpensebyDate", method = RequestMethod.GET, produces = "application/json")
    public GenericResponse<List<ExpensesDTO>> getExpensebyDate(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) throws BadRequestException, NotFoundException, InternalServerErrorException {

        return new GenericResponse<>(200, HttpStatus.OK, "Successful", expensesService.getAllByDate(startDate, endDate));
    }


}
