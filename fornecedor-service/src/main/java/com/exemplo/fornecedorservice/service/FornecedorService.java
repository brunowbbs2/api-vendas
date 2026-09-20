package com.exemplo.fornecedorservice.service;

import com.exemplo.fornecedorservice.client.ProdutoClient;
import com.exemplo.fornecedorservice.dto.ProdutoDTO;
import com.exemplo.fornecedorservice.model.Fornecedor;
import com.exemplo.fornecedorservice.repository.FornecedorRepository;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FornecedorService {
    private final FornecedorRepository repository;
    private final ProdutoClient produtoClient;

    public FornecedorService(FornecedorRepository clienteRepository, ProdutoClient produtoClient) {
        this.repository = clienteRepository;
        this.produtoClient = produtoClient;
    }

    public List<Fornecedor> listarTodos() {
        return repository.findAll();
    }

    public Fornecedor salvar(Fornecedor fornecedor) {
        return repository.save(fornecedor);
    }

    public List<ProdutoDTO> listarProdutos() {
        return produtoClient.listarTodos();
    }

    public Optional<Fornecedor> buscarPorId(Long id) {
        return repository.findById(id);
    }
}