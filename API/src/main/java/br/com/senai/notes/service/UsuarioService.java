package br.com.senai.notes.service;

import br.com.senai.notes.dto.usuario.CadastroUsuarioDTO;
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

    public ListarUsuarioDTO buscarPorIdDTO(Integer id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);

        if(usuario == null) {
            return null;
        }
        return converterParaListagemDTO(usuario);
    }

    public ListarUsuarioDTO buscarPorEmailDTO(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);

        if(usuario == null) {
            return null;
        }
        return converterParaListagemDTO(usuario);
    }

    public CadastroUsuarioDTO cadastrar(CadastroUsuarioDTO dto) {
        Usuario usuario = new Usuario();

        String senha = encoder.encode(dto.getSenha());
        usuario.setSenha(senha);
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());

        usuarioRepository.save(usuario);

        return dto;
    }

    public CadastroUsuarioDTO atualizar(Integer id, CadastroUsuarioDTO dto) {
        Usuario antigo = buscarPorId(id);

        if (antigo == null) {
            return null;
        }

        antigo.setNome(dto.getNome());
        antigo.setEmail(dto.getEmail());
        String senha  = encoder.encode(dto.getSenha());
        antigo.setSenha(senha);

        usuarioRepository.save(antigo);

        return dto;
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
