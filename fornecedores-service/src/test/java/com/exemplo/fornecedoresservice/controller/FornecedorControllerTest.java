package com.exemplo.fornecedoresservice.controller;

import com.exemplo.fornecedoresservice.dto.ProdutoDTO;
import com.exemplo.fornecedoresservice.model.Fornecedor;
import com.exemplo.fornecedoresservice.service.FornecedorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FornecedorController.class)
class FornecedorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FornecedorService fornecedorService;

    @Test
    void deveListarFornecedores() throws Exception {
        Fornecedor fornecedor = fornecedor(1L, "Alfa Distribuidora", "12.345.678/0001-90");
        when(fornecedorService.listarTodos()).thenReturn(List.of(fornecedor));

        mockMvc.perform(get("/fornecedores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Alfa Distribuidora"));
    }

    @Test
    void deveRetornar404QuandoFornecedorNaoExistir() throws Exception {
        when(fornecedorService.buscarPorId(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/fornecedores/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveCadastrarFornecedorComStatus201() throws Exception {
        when(fornecedorService.cadastrar(any(Fornecedor.class)))
                .thenReturn(fornecedor(6L, "Zeta Componentes", "67.890.123/0001-45"));

        mockMvc.perform(post("/fornecedores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nome": "Zeta Componentes",
                                  "cnpj": "67.890.123/0001-45"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(6));
    }

    @Test
    void deveListarProdutosViaFeign() throws Exception {
        when(fornecedorService.listarProdutos())
                .thenReturn(List.of(new ProdutoDTO(1L, "Notebook", new BigDecimal("3500.00"))));

        mockMvc.perform(get("/fornecedores/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Notebook"));
    }

    private Fornecedor fornecedor(Long id, String nome, String cnpj) {
        Fornecedor fornecedor = new Fornecedor(nome, cnpj);
        fornecedor.setId(id);
        return fornecedor;
    }
}
