package com.nullifidianz.service;

import com.nullifidianz.model.Expense;
import com.nullifidianz.repository.ExpenseRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private List<Expense> expenses;

    public ExpenseService(ExpenseRepository expenseRepository, List<Expense> expenses) {
        this.expenseRepository = expenseRepository;
        this.expenses = expenses;
    }

    public Expense createExpense(String description, Double amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Amount must be greater than 0");
        Long id = expenses.stream().mapToLong(Expense::getId).max().orElse(0L) + 1L;
        Expense expense = new Expense(id, description, amount, LocalDate.now());
        expenses.add(expense);
        expenseRepository.save(expenses);
        return expense;
    }

    public List<Expense> getExpenses() {
        return List.copyOf(expenses);
    }

    public Expense updateExpense(Long id, String newDescription, Double newAmmount) {
        Expense expense = expenses.stream().filter(e -> e.getId() == id).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("expense not found"));

        if (newDescription != null & !newDescription.isBlank()) {
            expense = new Expense(expense.getId(), newDescription,
                    newAmmount != null ? newAmmount : expense.getAmount(), expense.getDate());
        } else if (newAmmount != null) {
            expense = new Expense(expense.getId(), expense.getDescription(), newAmmount, expense.getDate());
        }
        expenses.removeIf(e -> e.getId() == id);
        expenses.add(expense);
        expenseRepository.save(expenses);
        return expense;
    }
}
