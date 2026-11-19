package br.com.zela.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CriarComentarioRequest(
        @NotNull(message = "usuarioId é obrigatório.")
        Long usuarioId,

        @NotBlank(message = "O comentário não pode ser vazio.")
        @Size(max = 500, message = "O comentário deve ter no máximo 500 caracteres.")
        String texto) {
}