package com.exemplo.fornecedorservice.repository;

import com.exemplo.fornecedorservice.model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
}