package br.com.zela.api.dto;

import br.com.zela.api.domain.Ponto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PontoResponse(
        Long id,
        String categoria,
        String nomeCategoria,
        BigDecimal latitude,
        BigDecimal longitude,
        String descricao,
        boolean resolvido,
        LocalDateTime criadoEm,
        String autor) {

    public static PontoResponse de(Ponto p) {
        return new PontoResponse(
                p.getId(),
                p.getCategoria().getCodigo(),
                p.getCategoria().getNome(),
                p.getLatitude(),
                p.getLongitude(),
                p.getDescricao(),
                p.getResolvido() != null && p.getResolvido() == 1,
                p.getCriadoEm(),
                p.getUsuario().getUsername());
    }
}