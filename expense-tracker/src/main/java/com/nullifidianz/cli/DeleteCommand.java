package com.nullifidianz.cli;

import picocli.CommandLine;
import com.nullifidianz.service.ExpenseService;
import com.nullifidianz.repository.ExpenseRepository;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "delete", description = "Remove uma despesa")
public class DeleteCommand implements Callable<Integer> {

    @CommandLine.Option(names = "--id", required = true)
    int id;

    @Override
    public Integer call() {
        ExpenseService service = new ExpenseService(new ExpenseRepository());
        if (service.delete(id))
            System.out.println("Despesa deletada");
        else
            System.out.println("Despesa não encontrada");
        return 0;
    }
}
