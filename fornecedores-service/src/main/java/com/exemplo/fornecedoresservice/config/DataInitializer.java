package com.exemplo.fornecedoresservice.config;

import com.exemplo.fornecedoresservice.model.Fornecedor;
import com.exemplo.fornecedoresservice.repository.FornecedorRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements ApplicationRunner {

    private final FornecedorRepository fornecedorRepository;

    public DataInitializer(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (fornecedorRepository.count() == 0) {
            fornecedorRepository.saveAll(List.of(
                    new Fornecedor("Fornecedor Alpha", "12.345.678/0001-90"),
                    new Fornecedor("Fornecedor Beta", "98.765.432/0001-10"),
                    new Fornecedor("Fornecedor Gama", "11.222.333/0001-44"),
                    new Fornecedor("Fornecedor Delta", "55.666.777/0001-88"),
                    new Fornecedor("Fornecedor Épsilon", "77.888.999/0001-00")
            ));
        }
    }
}
