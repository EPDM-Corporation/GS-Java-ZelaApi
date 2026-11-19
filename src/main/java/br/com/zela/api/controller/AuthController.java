package br.com.zela.api.controller;

import br.com.zela.api.dto.CadastroRequest;
import br.com.zela.api.dto.UsuarioResponse;
import br.com.zela.api.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService auth;

    @PostMapping("/cadastro")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse cadastro(@Valid @RequestBody CadastroRequest req) {
        return auth.cadastrar(req);
    }

    @PostMapping("/login")
    public UsuarioResponse login(@Valid @RequestBody CadastroRequest req) {
        return auth.login(req);
    }
}