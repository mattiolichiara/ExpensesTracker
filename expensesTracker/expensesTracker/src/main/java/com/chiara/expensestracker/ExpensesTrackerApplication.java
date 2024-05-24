package com.chiara.expensestracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExpensesTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExpensesTrackerApplication.class, args);
    }

} //TODO gestire custom categories in base all'utente, mettere praticamente tutto in relazione all'utente, tranne le categorie di default

