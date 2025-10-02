package br.com.senai.notes.dto.anotacao;

import lombok.Data;

import java.util.List;

@Data
public class CadastroAnotacaoDTO {
    private String titulo;
    private String descricao;
    private String imagemUrl;
    private Integer usuarioId;
    private List<String> tags;
}
