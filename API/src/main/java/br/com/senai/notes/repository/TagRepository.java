package br.com.senai.notes.repository;

import br.com.senai.notes.model.Anotacao;
import br.com.senai.notes.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    // Procura tags do Usuário pelo id
    List<Tag> findByUsuarioId(Integer id);

    // ALTERNATIVA (JPQL):
    // @Query("SELECT t FROM Tag t WHERE t.usuario.id = :idDoUsuario")
    // List<Tag> findByIdUsuarioId(@Param("idDoUsuario") Integer usuarioId);

    // Procura tags do Usuário pelo email
    List<Tag> findByUsuarioEmail(String email);
    // ALTERNATIVA (JPQL):
    // @Query("SELECT t FROM Tag t JOIN t.usuario u WHERE u.email = :emailDoUsuario")
    // List<Tag> findByIdUsuarioEmail(@Param("emailDoUsuario") String email);
}
