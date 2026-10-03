package com.exemplo.fornecedoresservice.controller;

import com.exemplo.fornecedoresservice.dto.ProdutoDTO;
import com.exemplo.fornecedoresservice.interfaces.ProdutoInterface;
import com.exemplo.fornecedoresservice.model.Fornecedor;
import com.exemplo.fornecedoresservice.service.FornecedorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

    private final FornecedorService service;
    private final ProdutoInterface produtoInterface;

    public FornecedorController(FornecedorService service, ProdutoInterface produtoInterface) {
        this.service = service;
        this.produtoInterface = produtoInterface;
    }

    @GetMapping
    public List<Fornecedor> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fornecedor> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Fornecedor> salvar(@RequestBody Fornecedor fornecedor) {
        Fornecedor novoFornecedor = service.salvar(fornecedor);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoFornecedor);
    }

    @GetMapping("/produtos")
    public ResponseEntity<List<ProdutoDTO>> buscarProdutos() {
        List<ProdutoDTO> produtos = produtoInterface.buscarTodos();
        return ResponseEntity.ok(produtos);
    }
}