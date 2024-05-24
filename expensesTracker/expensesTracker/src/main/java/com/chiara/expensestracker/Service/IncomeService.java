package com.chiara.expensestracker.Service;

import com.chiara.expensestracker.DTOs.Income.*;
import com.chiara.expensestracker.Entity.Income;
import com.chiara.expensestracker.Exceptions.BadRequestException;
import com.chiara.expensestracker.Exceptions.HTTPRunTimeException;
import com.chiara.expensestracker.Exceptions.InternalServerErrorException;
import com.chiara.expensestracker.Exceptions.NotFoundException;
import com.chiara.expensestracker.Repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IncomeService {

    private IncomeRepository incomeRepository;

    @Autowired
    public IncomeService(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    public IncomeDTO getIncomebyId(Integer idIncome) throws NotFoundException, BadRequestException {
        IncomeDTO incomeDTO;

        if(idIncome==null) {
            throw new BadRequestException("Null Request");
        }

        Optional<Income> incomeOpt = incomeRepository.findById(idIncome);
        if(incomeOpt.isPresent()) {
            return new IncomeDTO(incomeOpt.get());
        } else {
            throw new NotFoundException("The income requested doesn't exist");
        }
    }

    public IncomeDTO addIncome(InsertIncome insertIncome) throws BadRequestException, InternalServerErrorException {
        Income income;

        if(insertIncome == null || insertIncome.getCardIncome()==null || insertIncome.getCashIncome()==null ) {
            throw new BadRequestException("Null Request");
        }

        if(insertIncome.getCardIncome().signum() < 0 || insertIncome.getCashIncome().signum() < 0) {
            throw new BadRequestException("Incomes can't be negative.");
        }

        try {
            insertIncome.setTotalIncome(insertIncome.getCardIncome().add(insertIncome.getCashIncome()));
            income = new Income(insertIncome.getIdIncome(), insertIncome.getCashIncome(), insertIncome.getCardIncome(), insertIncome.getTotalIncome());
            income = incomeRepository.save(income);
        } catch(HTTPRunTimeException e) {
            throw new InternalServerErrorException("There was an error while saving a new Income.");
        }

        return new IncomeDTO(income);
    }

    public IncomeDTO updateIncome(UpdateIncome updateIncome) throws BadRequestException, InternalServerErrorException, NotFoundException {
        Income income;

        if(updateIncome == null || updateIncome.getCardIncome()==null || updateIncome.getCashIncome()==null || updateIncome.getIdIncome()==null) {
            throw new BadRequestException("Null Request");
        }

        if(updateIncome.getCardIncome().signum() < 0 || updateIncome.getCashIncome().signum() < 0) {
            throw new BadRequestException("Incomes can't be negative.");
        }

        Optional<Income> opt = incomeRepository.findById(updateIncome.getIdIncome());
        if(opt.isPresent()) {
            try {
                income = opt.get();
                income.setTotalIncome(updateIncome.getCardIncome().add(updateIncome.getCashIncome())); //TODO check if sum went right
                income.setCardIncome(updateIncome.getCardIncome());
                income.setCashIncome(updateIncome.getCashIncome());
                income = incomeRepository.save(income);
            } catch(HTTPRunTimeException e) {
                throw new InternalServerErrorException("There was an error while saving a new Income.");
            }
        } else {
            throw new NotFoundException("Income doesn't exist");
        }

        //assert income != null : "";
        return new IncomeDTO(income);
    }

    public IncomeDTO updateCashIncome(UpdateCashIncome updateIncome) throws BadRequestException, InternalServerErrorException {
        Income income;

        if(updateIncome == null || updateIncome.getCashIncome()==null || updateIncome.getIdIncome()==null) {
            throw new BadRequestException("Null Request");
        }

//        if(updateIncome.getCashIncome().signum() < 0) {
//            throw new BadRequestException("Incomes can't be negative.");
//        }

        Optional<Income> opt = incomeRepository.findById(updateIncome.getIdIncome());
        if(opt.isPresent()) {
            try {
                income = opt.get();
                income.setCashIncome(income.getCashIncome().add(updateIncome.getCashIncome()));
                income.setTotalIncome(income.getCardIncome().add(income.getCashIncome()));
                //income.setCardIncome(updateIncome.getCardIncome());

                income = incomeRepository.save(income);
            } catch(HTTPRunTimeException e) {
                throw new InternalServerErrorException("There was an error while updating Income.");
            }
        } else {
            throw new BadRequestException("Income doesn't exist");
        }

        //assert income != null : "";
        return new IncomeDTO(income);
    }

    public IncomeDTO updateCardIncome(UpdateCardIncome updateIncome) throws BadRequestException, InternalServerErrorException {
        Income income;

        if(updateIncome == null || updateIncome.getCardIncome()==null || updateIncome.getIdIncome()==null) {
            throw new BadRequestException("Null Request");
        }

//        if(updateIncome.getCardIncome().signum() < 0) {
//            throw new BadRequestException("Incomes can't be negative.");
//        }

        Optional<Income> opt = incomeRepository.findById(updateIncome.getIdIncome());
        if(opt.isPresent()) {
            try {
                income = opt.get();
                income.setCardIncome(income.getCardIncome().add(updateIncome.getCardIncome()));
                income.setTotalIncome(income.getCashIncome().add(income.getCardIncome()));
//                income.setCashIncome(updateIncome.getCashIncome());
                income = incomeRepository.save(income);
            } catch(HTTPRunTimeException e) {
                throw new InternalServerErrorException("There was an error while updating Income.");
            }
        } else {
            throw new BadRequestException("Income doesn't exist");
        }

        //assert income != null : "";
        return new IncomeDTO(income);
    }


}
