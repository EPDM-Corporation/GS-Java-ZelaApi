package br.com.zela.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CadastroRequest(
        @NotBlank(message = "O usuário é obrigatório.")
        @Size(min = 3, max = 50, message = "O usuário deve ter entre 3 e 50 caracteres.")
        String username,

        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 6, max = 72, message = "A senha deve ter entre 6 e 72 caracteres.")
        String password) {
}