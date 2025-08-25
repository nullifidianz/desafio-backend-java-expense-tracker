package com.nullifidianz.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nullifidianz.model.Expense;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ExpenseRepository {
    private final File storage = new File("expenses.json");
    private final ObjectMapper mapper = new ObjectMapper();

    public List<Expense> load() {
        try {
            if (!storage.exists())
                return new ArrayList<>();
            return mapper.readValue(storage, new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar dados", e);
        }
    }

    public void save(List<Expense> expenses) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(storage, expenses);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar dados", e);
        }
    }

}
