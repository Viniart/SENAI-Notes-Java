package br.com.senai.notes.dto.login;

import br.com.senai.notes.dto.usuario.ListarUsuarioDTO;

public record LoginResponseDTO (String token, ListarUsuarioDTO usuarioDTO) {

}
