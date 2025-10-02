package br.com.senai.notes.controller;

import br.com.senai.notes.dto.usuario.CadastroUsuarioDTO;
import br.com.senai.notes.dto.usuario.ListarUsuarioDTO;
import br.com.senai.notes.model.Usuario;
import br.com.senai.notes.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @Operation(summary = "Lista todos os usuários", description = "Retorna uma lista com todos os usuários cadastrados.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida")
    public ResponseEntity<List<ListarUsuarioDTO>> listarUsuarios() {
        List<ListarUsuarioDTO> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um usuário por ID", description = "Retorna um usuário específico com base no seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado para o ID informado")
    })
    public ResponseEntity<?> buscarUsuarioPorId(@PathVariable Integer id) {
        ListarUsuarioDTO usuario = usuarioService.buscarPorIdDTO(id);

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuário não encontrado!");
        }

        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    @Operation(summary = "Cadastra um novo usuário", description = "Adiciona um novo usuário ao banco de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos para o cadastro")
    })
    public ResponseEntity<CadastroUsuarioDTO> inserirUsuario(@RequestBody CadastroUsuarioDTO usuario) {
        CadastroUsuarioDTO novoUsuario = usuarioService.cadastrar(usuario);

        if (novoUsuario == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um usuário existente", description = "Altera os dados de um usuário com base no seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado para o ID informado")
    })
    public ResponseEntity<?> atualizarUsuario(@PathVariable Integer id, @RequestBody CadastroUsuarioDTO usuario) {
        CadastroUsuarioDTO usuarioAtualizado = usuarioService.atualizar(id, usuario);

        if (usuarioAtualizado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuário não encontrado!");
        }

        return ResponseEntity.ok(usuarioAtualizado);

    }

    // Deletar
    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um usuário", description = "Remove um usuário do banco de dados com base no seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado para o ID informado")
    })
    public ResponseEntity<?> deletarUsuario(@PathVariable Integer id) {
        Usuario usuarioDeletado = usuarioService.deletar(id);

        if (usuarioDeletado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Não foi possível excluir, pois o usuário não foi encontrado.");
        }

        return ResponseEntity.ok(usuarioDeletado);
    }

}
