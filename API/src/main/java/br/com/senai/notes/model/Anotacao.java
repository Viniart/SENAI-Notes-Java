package br.com.senai.notes.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "anotacao", schema = "senainotes")
public class Anotacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_anotacao", nullable = false)
    private Integer id;

    @Column(name = "titulo", nullable = false, length = Integer.MAX_VALUE)
    private String titulo;

    @Column(name = "descricao", nullable = false, length = Integer.MAX_VALUE)
    private String descricao;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "data_criacao", nullable = false)
    private Instant dataCriacao;

    @Column(name = "data_edicao")
    private Instant dataEdicao;

    @Column(name = "imagem_anotacao", nullable = false, length = Integer.MAX_VALUE)
    private String imagemAnotacao;

    @Column(name = "anotacao_arquivada", nullable = false)
    private Boolean anotacaoArquivada = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

}