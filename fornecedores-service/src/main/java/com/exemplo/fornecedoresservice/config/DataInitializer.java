package com.exemplo.fornecedoresservice.config;

import com.exemplo.fornecedoresservice.model.Fornecedor;
import com.exemplo.fornecedoresservice.repository.FornecedorRepository;
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
        fornecedorRepository.save(new Fornecedor("Ana Eliza", "111.111.111-11"));
        fornecedorRepository.save(new Fornecedor("Romulo Augustos", "222.222.222-22"));
        fornecedorRepository.save(new Fornecedor("Eduarda Lima", "333.333.333-33"));
        fornecedorRepository.save(new Fornecedor("Diogo Dias", "444.444.444-44"));
        fornecedorRepository.save(new Fornecedor("Marilia Carmo", "555.555.555-55"));
    }
}
