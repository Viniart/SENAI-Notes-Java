package br.com.senai.notes.dto.anotacao;

import br.com.senai.notes.dto.tag.ListarTagDTO;
import br.com.senai.notes.dto.usuario.ListarUsuarioDTO;
import br.com.senai.notes.model.Usuario;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.List;

@Data
public class ListarAnotacaoDTO {
    private Integer id;
    private String titulo;
    private String descricao;
    private Instant dataCriacao;
    private Instant dataEdicao;
    private String imagemAnotacao;
    private Boolean anotacaoArquivada = false;
    private ListarUsuarioDTO usuario;
    private List<ListarTagDTO> tags;
}
