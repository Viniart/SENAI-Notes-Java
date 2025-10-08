package br.com.senai.notes.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetarSenhaDTO {
    @NotBlank(message = "O campo e-mail é obrigatório.")
    @Email(message = "Formato de e-mail inválido.")
    private String email;
}
