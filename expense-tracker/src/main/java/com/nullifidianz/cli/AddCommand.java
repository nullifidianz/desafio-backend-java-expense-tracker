package com.nullifidianz.cli;

import picocli.CommandLine;
import com.nullifidianz.service.ExpenseService;
import com.nullifidianz.repository.ExpenseRepository;
import com.nullifidianz.model.Expense;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "add", description = "Adiciona uma despesa")
public class AddCommand implements Callable<Integer> {

    @CommandLine.Option(names = "--description", required = true)
    String description;
    @CommandLine.Option(names = "--amount", required = true)
    double amount;

    @Override
    public Integer call() {
        ExpenseService service = new ExpenseService(new ExpenseRepository());
        Expense e = service.createExpense(description, amount);
        System.out.printf("Despesa adicionada (ID: %d)%n", e.getId());
        return 0;
    }
}
