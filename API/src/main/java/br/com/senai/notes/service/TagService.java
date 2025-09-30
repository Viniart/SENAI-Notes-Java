package br.com.senai.notes.service;

import br.com.senai.notes.dto.tag.CadastroTagDTO;
import br.com.senai.notes.model.Tag;
import br.com.senai.notes.model.Usuario;
import br.com.senai.notes.repository.TagRepository;
import br.com.senai.notes.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;
    private final UsuarioRepository usuarioRepository;

    public TagService(TagRepository tagRepository, UsuarioRepository usuarioRepository) {
        this.tagRepository = tagRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Tag> listarTodos() {
        return tagRepository.findAll();
    }

    public Tag buscarPorId(Integer id) {
        return tagRepository.findById(id).orElse(null);
    }

    public List<Tag> listarPorEmailUsuario(String email) {
        return tagRepository.findByUsuarioEmailIgnoreCase(email);
    }

    public List<Tag> listarPorUsuarioId(Integer id) {
        return tagRepository.findByUsuarioId(id);
    }

//    public Tag cadastrar(CadastroTagDTO dto) {
//        // Criar uma tag
//        Tag tag = new Tag();
//
//        tag.setNomeTag(dto.getNomeTag());
//
//        return tagRepository.save(tag);
//    }
    public Tag cadastrar(CadastroTagDTO tagDto) {
        Integer usuarioId = tagDto.getUsuarioId();
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o ID: " + usuarioId));

        Tag novaTag = new Tag();
        novaTag.setNomeTag(tagDto.getNomeTag());
        novaTag.setUsuario(usuario);

        return tagRepository.save(novaTag);
    }

    public Tag atualizar(Integer id, CadastroTagDTO tag) {
        Tag tagExistente = tagRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tag não encontrada com o ID: " + id));

        Integer usuarioId = tag.getUsuarioId();
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o ID: " + usuarioId));

        tagExistente.setNomeTag(tag.getNomeTag());
        tagExistente.setUsuario(usuario);

        return tagRepository.save(tagExistente);
    }

    public Tag deletar(Integer id) {
        Tag tagParaDeletar = buscarPorId(id);

        if (tagParaDeletar == null) {
            throw new EntityNotFoundException("Tag não encontrada com o ID: " + id);
        }

        tagRepository.delete(tagParaDeletar);
        return tagParaDeletar;
    }

}
