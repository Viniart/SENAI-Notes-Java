package br.com.senai.notes.service;

import br.com.senai.notes.dto.usuario.ListarUsuarioDTO;
import br.com.senai.notes.model.Usuario;
import br.com.senai.notes.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder encoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder encoder) {
        this.usuarioRepository = usuarioRepository;
        this.encoder = encoder;
    }

    public List<ListarUsuarioDTO> listarTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        return usuarios.stream()
                .map(this::converterParaListagemDTO)
                .collect(Collectors.toList());
    }

    public Usuario buscarPorId(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario cadastrar(Usuario usuario) {
        String senha = encoder.encode(usuario.getSenha());
        usuario.setSenha(senha);

        return usuarioRepository.save(usuario);
    }

    public Usuario atualizar(Integer id, Usuario usuario) {
        Usuario antigo = buscarPorId(usuario.getId());

        if (antigo == null) {
            return null;
        }

        antigo.setNome(usuario.getNome());
        antigo.setEmail(usuario.getEmail());
        String senha  = encoder.encode(usuario.getSenha());
        antigo.setSenha(senha);

        return usuarioRepository.save(antigo);
    }

    public Usuario deletar(Integer id) {
        Usuario usuario = buscarPorId(id);

        if (usuario == null) {
            return null;
        }

        usuarioRepository.delete(usuario);
        return usuario;
    }

    private ListarUsuarioDTO converterParaListagemDTO(Usuario usuario) {
        ListarUsuarioDTO dto = new ListarUsuarioDTO();

        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setId(usuario.getId());

        return dto;
    }
}
