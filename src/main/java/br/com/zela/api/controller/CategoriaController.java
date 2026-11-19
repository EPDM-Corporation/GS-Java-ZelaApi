package br.com.zela.api.controller;

import br.com.zela.api.domain.Categoria;
import br.com.zela.api.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaRepository categorias;

    @GetMapping
    public List<Categoria> listar() {
        return categorias.findAll();
    }
}