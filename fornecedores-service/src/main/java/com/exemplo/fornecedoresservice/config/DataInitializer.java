package com.exemplo.fornecedoresservice.config;

import com.exemplo.fornecedoresservice.model.Fornecedor;
import com.exemplo.fornecedoresservice.repository.FornecedorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final FornecedorRepository fornecedorRepository;

    public DataInitializer(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    @Override
    public void run(String... args) {
        if (fornecedorRepository.count() == 0) {
            fornecedorRepository.saveAll(List.of(
                    new Fornecedor("Alfa Distribuidora", "12.345.678/0001-90"),
                    new Fornecedor("Beta Alimentos", "23.456.789/0001-01"),
                    new Fornecedor("Gama Tecnologia", "34.567.890/0001-12"),
                    new Fornecedor("Delta Logistica", "45.678.901/0001-23"),
                    new Fornecedor("Epsilon Embalagens", "56.789.012/0001-34")
            ));
        }
    }
}
