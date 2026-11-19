package br.com.zela.api.dto;

import br.com.zela.api.domain.Comentario;

import java.time.LocalDateTime;

public record ComentarioResponse(Long id, String texto, String autor, LocalDateTime criadoEm) {

    public static ComentarioResponse de(Comentario c) {
        return new ComentarioResponse(c.getId(), c.getTexto(), c.getUsuario().getUsername(), c.getCriadoEm());
    }
}