package com.nullifidianz.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nullifidianz.model.Expense;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExpenseRepository {
    private final File storage = new File("expenses.json");
    private final ObjectMapper mapper;

    public ExpenseRepository() {
        this.mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public List<Expense> load() {
        if (!storage.exists() || storage.length() == 0) {
            return new ArrayList<>(); // retorna lista vazia se arquivo não existe ou está vazio
        }
        try {
            return mapper.readValue(storage,
                    mapper.getTypeFactory().constructCollectionType(List.class, Expense.class));
        } catch (IOException e) {
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
