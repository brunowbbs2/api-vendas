package com.exemplo.fornecedoresservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FornecedorRepository extends JpaRepository<com.exemplo.fornecedoresservice.model.Fornecedor, Long> {
}
