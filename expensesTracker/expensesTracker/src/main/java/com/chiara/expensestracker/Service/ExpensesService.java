package com.chiara.expensestracker.Service;

import com.chiara.expensestracker.DTOs.Expenses.ExpensesDTO;
import com.chiara.expensestracker.DTOs.Expenses.InsertExpense;
import com.chiara.expensestracker.DTOs.Expenses.UpdateExpense;
import com.chiara.expensestracker.DTOs.Income.IncomeDTO;
import com.chiara.expensestracker.DTOs.Income.UpdateCardIncome;
import com.chiara.expensestracker.DTOs.Income.UpdateCashIncome;
import com.chiara.expensestracker.DTOs.SubCategory.SubCategoryDTO;
import com.chiara.expensestracker.Entity.Expenses;
import com.chiara.expensestracker.Entity.Income;
import com.chiara.expensestracker.Entity.SubCategory;
import com.chiara.expensestracker.Exceptions.BadRequestException;
import com.chiara.expensestracker.Exceptions.HTTPRunTimeException;
import com.chiara.expensestracker.Exceptions.InternalServerErrorException;
import com.chiara.expensestracker.Exceptions.NotFoundException;
import com.chiara.expensestracker.Repository.ExpensesRepository;
import com.chiara.expensestracker.Utils.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpensesService {

    private ExpensesRepository expensesRepository;
    private IncomeService incomeService;
    private SubCategoryService subCategoryService;

    @Autowired
    public ExpensesService(ExpensesRepository expensesRepository, IncomeService incomeService, SubCategoryService subCategoryService) {
        this.expensesRepository = expensesRepository;
        this.incomeService = incomeService;
        this.subCategoryService = subCategoryService;
    }

    public ExpensesDTO addExpense(InsertExpense insertExpense) throws BadRequestException, NotFoundException, InternalServerErrorException {
        Expenses expense;

        if(insertExpense==null) {
            throw new BadRequestException("Null request");
        }

        if(insertExpense.getAmount()==null || insertExpense.getIdSubCategory()==null || insertExpense.getTransactionType()==null ||
            insertExpense.getCreationDate()==null || insertExpense.getIdIncome()==null) {
            throw new BadRequestException("All fields must be filled.");
        }

        if(insertExpense.getAmount().signum() <= 0) {
            throw new BadRequestException("Expense amount must be bigger than 0");
        } else {
            insertExpense.setAmount(insertExpense.getAmount().negate());
        }

        if(insertExpense.getCreationDate().isAfter(LocalDate.now())) {
            throw new BadRequestException("Expense date can't be after the current date");
        }

        IncomeDTO incomeDTO = incomeService.getIncomebyId(insertExpense.getIdIncome());
        SubCategoryDTO subCategoryDTO = subCategoryService.getOnebyId(insertExpense.getIdSubCategory());

        try {

            if(insertExpense.getTransactionType() == TransactionType.CARD) {
                incomeDTO = incomeService.updateCardIncome(new UpdateCardIncome(incomeDTO.getIdIncome(), null, insertExpense.getAmount(), null));
            } else {
                incomeDTO = incomeService.updateCashIncome(new UpdateCashIncome(incomeDTO.getIdIncome(), insertExpense.getAmount(), null, null));
            }

            expense = new Expenses(insertExpense.getIdExpenses(), insertExpense.getCreationDate(),
                    insertExpense.getComment(), insertExpense.getAmount(), insertExpense.getTransactionType(),
                    new SubCategory(subCategoryDTO), new Income(incomeDTO));
            expensesRepository.save(expense);
        } catch (HTTPRunTimeException e) {
            throw  new InternalServerErrorException("There was an error while saving the expense.");
        }

        return new ExpensesDTO(expense);
    }

    public List<ExpensesDTO> deleteExpensebyId(Integer idExpense) throws BadRequestException, NotFoundException, InternalServerErrorException {
        Expenses expense;

        if(idExpense==null) {
            throw new BadRequestException("Null Request");
        }

        Optional<Expenses> expOpt = expensesRepository.findById(idExpense);
        if(expOpt.isPresent()) {
            expense = expOpt.get();

            if(expense.getTransactionType() == TransactionType.CARD) {
                incomeService.updateCardIncome(new UpdateCardIncome(expense.getIdIncome().getIdIncome(), null, expense.getAmount(), null));
            } else {
                incomeService.updateCashIncome(new UpdateCashIncome(expense.getIdIncome().getIdIncome(), expense.getAmount(), null, null));
            }

            try {
                expensesRepository.deleteById(idExpense);
            } catch(HTTPRunTimeException e) {
                throw new InternalServerErrorException("There was an error during deletion");
            }

        } else {
            throw new NotFoundException("Expense can't be deleted because it doesn't exist");
        }

        LocalDate d = LocalDate.now();
        return getAllByDate(d.withDayOfMonth(1), d);
    }

    public List<ExpensesDTO> deleteExpenses(List<Integer> expensesToDelete) throws BadRequestException, NotFoundException, InternalServerErrorException {

        if(expensesToDelete==null) {
            throw new BadRequestException("Null Request");
        }

        if(expensesRepository.existsAllByIdExpensesIn(expensesToDelete)) {
            for(Integer i: expensesToDelete) {
                deleteExpensebyId(i);
            }

        } else {
            throw new NotFoundException("One or More Expenses can't be deleted because they don't exist");
        }

        LocalDate d = LocalDate.now();
        return getAllByDate(d.withDayOfMonth(1), d);
    }

    public ExpensesDTO updateExpense(UpdateExpense updateExpense) throws BadRequestException, NotFoundException, InternalServerErrorException {
        Expenses expense;

        if(updateExpense==null) {
            throw new BadRequestException("Null Request");
        }

        if(updateExpense.getTransactionType()==null || updateExpense.getCreationDate()==null ||
                updateExpense.getAmount()==null || updateExpense.getIdSubCategory()==null
                || updateExpense.getIdIncome()==null) {
            throw new BadRequestException("All fields must be filled.");
        }

        if(expensesRepository.existsById(updateExpense.getIdExpenses())) {
            expense = expensesRepository.getByIdExpenses(updateExpense.getIdExpenses());
        } else {
            throw new NotFoundException("This Expense doesn't exist");
        }

        if(updateExpense.getCreationDate().isAfter(LocalDate.now())) {
            throw new BadRequestException("Expense date can't be after the current date");
        }

        if(updateExpense.getAmount().signum()<=0) {
            throw new BadRequestException("Expense amount must be bigger than 0");
        }

        SubCategoryDTO subCategoryDTO = subCategoryService.getOnebyId(updateExpense.getIdSubCategory());
        IncomeDTO incomeDTO = incomeService.getIncomebyId(updateExpense.getIdIncome());
        if(!incomeDTO.getIdIncome().equals(updateExpense.getIdIncome())) {
            throw new BadRequestException("Transaction can't be moved from an income to another");
        }

        if(updateExpense.getTransactionType()!=expense.getTransactionType()) {
            if((expense.getTransactionType() == TransactionType.CARD) && (updateExpense.getTransactionType() == TransactionType.CASH)) {
                incomeService.updateCashIncome(new UpdateCashIncome(updateExpense.getIdIncome(),
                        incomeDTO.getCashIncome().add(updateExpense.getAmount().negate()), incomeDTO.getCardIncome().add(expense.getAmount()), null));
            }
            if(expense.getTransactionType() == TransactionType.CASH && updateExpense.getTransactionType() == TransactionType.CARD) {
                incomeService.updateCardIncome(new UpdateCardIncome(updateExpense.getIdIncome(), incomeDTO.getCashIncome().add(expense.getAmount()),
                        incomeDTO.getCardIncome().add(updateExpense.getAmount().negate()), null));
            }
        } else {
            if(updateExpense.getTransactionType() == TransactionType.CARD) {
                incomeService.updateCardIncome(new UpdateCardIncome(updateExpense.getIdIncome(), null,
                        incomeDTO.getCardIncome().add(expense.getAmount().add(updateExpense.getAmount().negate())), null));
            } else {
                incomeService.updateCashIncome(new UpdateCashIncome(updateExpense.getIdIncome(),
                         incomeDTO.getCashIncome().add(expense.getAmount().add(updateExpense.getAmount().negate())), null, null));
            }
        }


        try {
            expense.setComment(updateExpense.getComment());
            expense.setCreationDate(updateExpense.getCreationDate());
            expense.setTransactionType(updateExpense.getTransactionType());
            expense.setAmount(updateExpense.getAmount());
            expense.setIdSubCategory(new SubCategory(subCategoryDTO));
            expense.setIdIncome(new Income(incomeDTO));
            expensesRepository.save(expense);
        } catch (HTTPRunTimeException e) {
            throw  new InternalServerErrorException("There was an error while saving the expense.");
        }

        return new ExpensesDTO(expense);
    }

    public ExpensesDTO getOnebyId(Integer id) throws BadRequestException, NotFoundException {
        ExpensesDTO expensesDTO;

        if(id==null) {
            throw new BadRequestException("Null Request");
        }

        Optional<Expenses> expenseOpt = expensesRepository.findById(id);
        if(expenseOpt.isPresent()) {
            return new ExpensesDTO(expenseOpt.get());
        } else {
            throw new NotFoundException("The income requested doesn't exist");
        }
    }

    public List<ExpensesDTO> getAllByDate(LocalDate startDate, LocalDate endDate) throws BadRequestException, NotFoundException {

        if(startDate==null || endDate==null) {
            throw new BadRequestException("Null Request");
        }

        if(endDate.isAfter(LocalDate.now())) {
            throw new BadRequestException("No trasactions are allowed for days after the current date");
        }

        List<Expenses> expenses = expensesRepository.findAllByCreationDateIsGreaterThanEqualAndCreationDateIsLessThanEqualAndIdIncome(startDate, endDate,null ); //TODO fix
        List<ExpensesDTO> expensesDTO = expenses.stream().map(e-> new ExpensesDTO(e)).collect(Collectors.toList());
        if(expenses.isEmpty() || expensesDTO.isEmpty()) {
            throw new  NotFoundException("No expenses found");
        }

        return expensesDTO;
    }
}
