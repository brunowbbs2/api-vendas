package com.exemplo.fornecedorservice.config;

import com.exemplo.fornecedorservice.model.Fornecedor;
import com.exemplo.fornecedorservice.repository.FornecedorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner carregarFornecedores(FornecedorRepository repository) {
        return args -> {

            repository.save(new Fornecedor(
                    "Fornecedor A",
                    "11111111000111"));

            repository.save(new Fornecedor(
                    "Fornecedor B",
                    "22222222000122"));

            repository.save(new Fornecedor(
                    "Fornecedor C",
                    "33333333000133"));

            repository.save(new Fornecedor(
                    "Fornecedor D",
                    "44444444000144"));

            repository.save(new Fornecedor(
                    "Fornecedor E",
                    "55555555000155"));
        };
    }
}