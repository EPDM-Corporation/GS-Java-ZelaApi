package br.com.zela.api.controller;

import br.com.zela.api.dto.ComentarioResponse;
import br.com.zela.api.dto.CriarComentarioRequest;
import br.com.zela.api.dto.CriarPontoRequest;
import br.com.zela.api.dto.PontoResponse;
import br.com.zela.api.service.ComentarioService;
import br.com.zela.api.service.PontoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pontos")
@RequiredArgsConstructor
public class PontoController {

    private final PontoService pontos;
    private final ComentarioService comentarios;

    @GetMapping
    public List<PontoResponse> listar() {
        return pontos.listar();
    }

    @GetMapping("/{id}")
    public PontoResponse buscar(@PathVariable Long id) {
        return pontos.buscar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PontoResponse criar(@Valid @RequestBody CriarPontoRequest req) {
        return pontos.criar(req);
    }

    @PatchMapping("/{id}/resolvido")
    public PontoResponse resolver(@PathVariable Long id) {
        return pontos.marcarResolvido(id);
    }

    @GetMapping("/{id}/comentarios")
    public List<ComentarioResponse> listarComentarios(@PathVariable Long id) {
        return comentarios.listar(id);
    }

    @PostMapping("/{id}/comentarios")
    @ResponseStatus(HttpStatus.CREATED)
    public ComentarioResponse comentar(@PathVariable Long id,
                                       @Valid @RequestBody CriarComentarioRequest req) {
        return comentarios.criar(id, req);
    }
}