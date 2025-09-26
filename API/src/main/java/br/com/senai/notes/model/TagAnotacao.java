package br.com.senai.notes.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tag_anotacao", schema = "senainotes")
public class TagAnotacao {
    @EmbeddedId
    private TagAnotacaoId id;

    @MapsId("idAnotacao")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_anotacao", nullable = false)
    private Anotacao idAnotacao;

    @MapsId("idTag")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_tag", nullable = false)
    private Tag idTag;

}