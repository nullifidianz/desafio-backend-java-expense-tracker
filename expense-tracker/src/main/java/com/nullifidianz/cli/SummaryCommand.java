package com.nullifidianz.cli;

import picocli.CommandLine;
import com.nullifidianz.service.ExpenseService;
import com.nullifidianz.repository.ExpenseRepository;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "summary", description = "Mostra resumo das despesas")
public class SummaryCommand implements Callable<Integer> {

    @CommandLine.Option(names = "--month", description = "Mês específico (1-12)")
    Integer month;

    @Override
    public Integer call() {
        ExpenseService service = new ExpenseService(new ExpenseRepository());
        if (month != null) {
            System.out.printf("Total despesas do mês %d: $%.2f%n", month, service.summaryByMonth(month));
        } else {
            System.out.printf("Total geral: $%.2f%n", service.summary());
        }
        return 0;
    }
}
