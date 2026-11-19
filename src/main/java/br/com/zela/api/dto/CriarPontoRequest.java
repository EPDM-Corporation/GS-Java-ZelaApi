package br.com.zela.api.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CriarPontoRequest(
        @NotNull(message = "usuarioId é obrigatório.")
        Long usuarioId,

        @NotBlank(message = "A categoria é obrigatória.")
        String categoria,

        @NotNull(message = "A latitude é obrigatória.")
        @DecimalMin(value = "-90", message = "Latitude mínima: -90.")
        @DecimalMax(value = "90", message = "Latitude máxima: 90.")
        BigDecimal latitude,

        @NotNull(message = "A longitude é obrigatória.")
        @DecimalMin(value = "-180", message = "Longitude mínima: -180.")
        @DecimalMax(value = "180", message = "Longitude máxima: 180.")
        BigDecimal longitude,

        @NotBlank(message = "A descrição é obrigatória.")
        @Size(min = 10, max = 500, message = "A descrição deve ter entre 10 e 500 caracteres.")
        String descricao) {
}