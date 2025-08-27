package com.nullifidianz.cli;

import picocli.CommandLine;
import com.nullifidianz.service.ExpenseService;
import com.nullifidianz.repository.ExpenseRepository;
import com.nullifidianz.model.Expense;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "list", description = "Lista todas despesas")
public class ListCommand implements Callable<Integer> {

    @Override
    public Integer call() {
        ExpenseService service = new ExpenseService(new ExpenseRepository());
        System.out.println("ID  Date       Description  Amount");
        for (Expense e : service.listAll()) {
            System.out.printf("%-3d %-10s %-12s $%.2f%n",
                    e.getId(), e.getDate(), e.getDescription(), e.getAmount());
        }
        return 0;
    }
}
