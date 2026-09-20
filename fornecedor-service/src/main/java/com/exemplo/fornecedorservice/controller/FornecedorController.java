package com.exemplo.fornecedorservice.controller;

import com.exemplo.fornecedorservice.model.Fornecedor;
import com.exemplo.fornecedorservice.dto.ProdutoDTO;
import com.exemplo.fornecedorservice.service.FornecedorService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {
    private final FornecedorService service;

    public FornecedorController(FornecedorService service) {
        this.service = service;
    }

    @GetMapping()
    public List<Fornecedor> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/produtos")
    public List<ProdutoDTO> listarProdutos() {
        return service.listarProdutos();
    }

    @PostMapping
    public ResponseEntity<Fornecedor> cadastrar(@RequestBody Fornecedor fornecedor) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(fornecedor));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fornecedor> listarPorId(@PathVariable Long id) {
        return service.buscarPorId(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}