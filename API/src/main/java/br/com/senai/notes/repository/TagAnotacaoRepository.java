package br.com.senai.notes.repository;

import br.com.senai.notes.model.TagAnotacao;
import br.com.senai.notes.model.TagAnotacaoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagAnotacaoRepository extends JpaRepository<TagAnotacao, TagAnotacaoId> {
}
