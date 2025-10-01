package br.com.senai.notes.dto.usuario;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class ListarUsuarioDTO {
    private Integer id;
    private String nome;
    private String email;
}
