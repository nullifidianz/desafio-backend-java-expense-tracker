package com.nullifidianz.cli;

import picocli.CommandLine;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "expense-tracker", mixinStandardHelpOptions = true, version = " 1.0", description = "cli expense tracker", subcommands = {
        AddCommand.class,
        ListCommand.class,
        DeleteCommand.class,
        SummaryCommand.class,
        UpdateCommand.class
})
public class ExpenseTrackerApp implements Callable<Integer> {

    public static void main(String[] args) {
        int exitCode = new CommandLine(new ExpenseTrackerApp()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() {
        System.out.println("Use --help para ver os comandos disponíveis.");
        return 0;
    }
}