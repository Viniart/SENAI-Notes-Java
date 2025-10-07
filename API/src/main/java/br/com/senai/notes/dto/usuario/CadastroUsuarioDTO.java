package br.com.senai.notes.dto.usuario;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CadastroUsuarioDTO {
    @NotBlank(message = "O nome não pode estar em branco.")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
    private String nome;
    @NotBlank(message = "O e-mail não pode estar em branco.")
    @Email(message = "O formato do e-mail é inválido.")
    private String email;
    @NotBlank(message = "A senha não pode estar em branco.")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres.")
    private String senha;
}
