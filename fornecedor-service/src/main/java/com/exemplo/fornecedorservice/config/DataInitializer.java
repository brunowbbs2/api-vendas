package com.exemplo.fornecedorservice.config;

import com.exemplo.fornecedorservice.model.Fornecedor;
import com.exemplo.fornecedorservice.repository.FornecedorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    private final FornecedorRepository fornecedorRepository;

    public DataInitializer(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    @Override
    public void run(String... args) {
        fornecedorRepository.save(new Fornecedor("Alimentos Brasil Ltda.", "12.345.678/0001-90"));
        fornecedorRepository.save(new Fornecedor("Distribuidora Central Ltda.", "23.456.789/0001-01"));
        fornecedorRepository.save(new Fornecedor("Mercantil Ltda.", "34.567.890/0001-12"));
        fornecedorRepository.save(new Fornecedor("Logistica Norte Ltda.", "45.678.901/0001-23"));
        fornecedorRepository.save(new Fornecedor("Comercial Horizonte Ltda.", "56.789.012/0001-34"));
    }
}