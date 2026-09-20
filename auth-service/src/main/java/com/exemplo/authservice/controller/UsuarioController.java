package com.exemplo.authservice.controller;

import com.exemplo.authservice.dto.LoginRequest;
import com.exemplo.authservice.dto.UsuarioRequest;
import com.exemplo.authservice.model.Usuario;
import com.exemplo.authservice.service.JwtToken;
import com.exemplo.authservice.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;
    private final JwtToken jwtService;

    @PostMapping
    public ResponseEntity<Long> cadastrar(@RequestBody UsuarioRequest request) {
        Usuario usuario = service.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario.getId());
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        Usuario usuario = this.service.autenticar(request);
        String token = jwtService.gerarToken(usuario);
        return respostaComToken(token);
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refresh(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        try {
            String token = extrairToken(authorization);
            String email = jwtService.validarToken(token);
            Usuario usuario = service.buscarPorEmail(email);
            return respostaComToken(jwtService.gerarToken(usuario));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token inválido");
        }
    }

    private ResponseEntity<String> respostaComToken(String token) {
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(token);
    }

    private String extrairToken(String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")
                || authorization.length() <= 7) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Token inválido");
        }
        return authorization.substring(7);
    }
}
