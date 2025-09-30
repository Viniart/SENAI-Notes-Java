package br.com.senai.notes.dto.tag;

import br.com.senai.notes.model.Usuario;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class ListarTagDTO {
    private Integer id;
    private String nomeTag;
    // TODO: Trocar para DTO de Usuario
    private Usuario usuario;
}
