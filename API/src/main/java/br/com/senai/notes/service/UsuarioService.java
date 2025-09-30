package br.com.senai.notes.service;

import br.com.senai.notes.model.Usuario;
import br.com.senai.notes.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
//    private final PasswordEncoder encoder;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
//        this.encoder = encoder;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario cadastrar(Usuario usuario) {
//        String senha = encoder.encode(usuario.getSenha());
//        usuario.setSenha(senha);

        return usuarioRepository.save(usuario);
    }

    public Usuario atualizar(Integer id, Usuario usuario) {
        Usuario antigo = buscarPorId(usuario.getId());

        if (antigo == null) {
            return null;
        }

        antigo.setNome(usuario.getNome());
        antigo.setEmail(usuario.getEmail());
//        String senha  = encoder.encode(usuario.getSenha());
//        antigo.setSenha(senha);

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
}
