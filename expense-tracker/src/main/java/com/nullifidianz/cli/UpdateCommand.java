package com.nullifidianz.cli;

import picocli.CommandLine;
import com.nullifidianz.service.ExpenseService;
import com.nullifidianz.repository.ExpenseRepository;
import com.nullifidianz.model.Expense;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "update", description = "Atualiza uma despesa")
public class UpdateCommand implements Callable<Integer> {

    @CommandLine.Option(names = "--id", required = true)
    int id;
    @CommandLine.Option(names = "--description")
    String description;
    @CommandLine.Option(names = "--amount")
    Double amount;

    @Override
    public Integer call() {
        ExpenseService service = new ExpenseService(new ExpenseRepository());
        try {
            Expense updated = service.updateExpense(id, description, amount);
            System.out.printf("Despesa atualizada (ID: %d)%n", updated.getId());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
}
