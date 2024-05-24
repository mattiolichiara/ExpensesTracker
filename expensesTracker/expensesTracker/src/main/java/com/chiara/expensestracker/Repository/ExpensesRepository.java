package com.chiara.expensestracker.Repository;

import com.chiara.expensestracker.Entity.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpensesRepository extends JpaRepository<Expenses, Integer> {

    Boolean existsAllByIdExpensesIn(List<Integer> expensesToDelete);
    Expenses getByIdExpenses(Integer idExpense);
    List<Expenses> findAllByCreationDateIsGreaterThanEqualAndCreationDateIsLessThanEqualAndIdIncome(LocalDate startDate, LocalDate endDate, Integer idIncome);

}
