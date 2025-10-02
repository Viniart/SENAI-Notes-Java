package br.com.senai.notes.repository;

import br.com.senai.notes.model.Anotacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnotacaoRepository extends JpaRepository<Anotacao, Integer> {
    @Query("SELECT t FROM Anotacao t JOIN t.usuario u WHERE LOWER(u.email) = LOWER(:emailDoUsuario)")
    List<Anotacao> findByUsuarioEmail(@Param("emailDoUsuario") String email);

    @Query("SELECT DISTINCT a FROM Anotacao a " +
    "LEFT JOIN FETCH a.usuario " +
    "LEFT JOIN FETCH a.tagAnotacao ta " +
    "LEFT JOIN FETCH ta.idTag t")
    List<Anotacao> findAllCompleto();

    @Query("SELECT DISTINCT a FROM Anotacao a " +
            "LEFT JOIN FETCH a.usuario u " +
            "LEFT JOIN FETCH a.tagAnotacao ta " +
            "LEFT JOIN FETCH ta.idTag t" +
    " WHERE LOWER(u.email) = LOWER(:emailDoUsuario)")
    List<Anotacao> findByUsuarioEmailCompleto(@Param("emailDoUsuario") String email);
}
