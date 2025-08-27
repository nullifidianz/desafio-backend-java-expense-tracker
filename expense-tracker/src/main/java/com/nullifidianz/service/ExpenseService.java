package com.nullifidianz.service;

import com.nullifidianz.model.Expense;
import com.nullifidianz.repository.ExpenseRepository;

import java.time.LocalDate;
import java.util.List;

public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private List<Expense> expenses;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
        this.expenses = expenseRepository.load();
    }

    public Expense createExpense(String description, Double amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Amount must be greater than 0");
        int id = expenses.stream().mapToInt(Expense::getId).max().orElse(0) + 1;
        Expense expense = new Expense(id, description, amount, LocalDate.now());
        expenses.add(expense);
        expenseRepository.save(expenses);
        return expense;
    }

    public List<Expense> listAll() {
        return List.copyOf(expenses);
    }

    public Expense getExpenseById(int id) {
        Expense expense = expenses.stream().filter(e -> e.getId() == id).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("expense not found"));

        return expense;
    }

    public Expense updateExpense(int id, String newDescription, Double newAmmount) {
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

    public double summary() {
        return expenses.stream().mapToDouble(Expense::getAmount).sum();
    }

    public double summaryByMonth(int month) {
        return expenses.stream().filter(e -> e.getDate().getMonthValue() == month).mapToDouble(Expense::getAmount)
                .sum();
    }

    public boolean delete(int id) {
        boolean removed = expenses.removeIf(e -> e.getId() == id);
        expenseRepository.save(expenses);
        return removed;
    }
}
