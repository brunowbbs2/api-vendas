package com.exemplo.fornecedoresservice.dto;

public class ProdutoDTO {

    private Long id;
    private String nome;
    private java.math.BigDecimal preco;

    public ProdutoDTO() {
    }

    public ProdutoDTO(Long id, String nome, java.math.BigDecimal preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public java.math.BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(java.math.BigDecimal preco) {
        this.preco = preco;
    }
}
