package br.com.senai.notes.dto.tag;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CadastroTagDTO {
    @NotBlank(message = "O nome da tag não pode estar em branco.")
    @Size(min = 2, max = 50, message = "O nome da tag deve ter entre 2 e 50 caracteres.")
    private String nomeTag;

    @NotNull(message = "O ID do usuário é obrigatório.")
    @Positive(message = "O ID do usuário deve ser um número positivo.")
    private Integer usuarioId;
}
