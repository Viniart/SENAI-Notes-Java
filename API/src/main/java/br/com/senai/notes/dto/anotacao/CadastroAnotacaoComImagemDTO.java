package br.com.senai.notes.dto.anotacao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class CadastroAnotacaoComImagemDTO {
    @NotBlank(message = "O título não pode estar em branco.")
    @Size(min = 3, max = 150, message = "O título deve ter entre 3 e 150 caracteres.")
    private String titulo;

    @NotBlank(message = "A descrição não pode estar em branco.")
    private String descricao;

    @URL(message = "A URL da imagem é inválida.")
    private String imagemUrl; // Pode ser nulo, mas se for preenchido, deve ser uma URL válida.

    @NotNull(message = "O ID do usuário é obrigatório.")
    @Positive(message = "O ID do usuário deve ser um número positivo.")
    private Integer usuarioId;

    private MultipartFile imagem;

    private List<String> tags;
}
