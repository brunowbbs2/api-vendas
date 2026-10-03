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
        if (fornecedorRepository.count() == 0) {
            fornecedorRepository.save(new Fornecedor("Empresa 1", "11.111.111/0001-11"));
            fornecedorRepository.save(new Fornecedor("Empresa 2", "22.222.222/0001-22"));
            fornecedorRepository.save(new Fornecedor("Empresa 3", "33.333.333/0001-33"));
            fornecedorRepository.save(new Fornecedor("Empresa 4", "44.444.444/0001-44"));
            fornecedorRepository.save(new Fornecedor("Empresa 5", "55.555.555/0001-55"));
        }
    }
}