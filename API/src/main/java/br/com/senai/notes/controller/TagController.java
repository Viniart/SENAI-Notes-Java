package br.com.senai.notes.controller;

import br.com.senai.notes.dto.tag.CadastroTagDTO;
import br.com.senai.notes.dto.tag.ListarTagDTO;
import br.com.senai.notes.model.Tag;
import br.com.senai.notes.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tag")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Tags", description = "Endpoints para gerenciamento de tags")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    @Operation(summary = "Lista todas as tags", description = "Retorna uma lista com todas as tags cadastradas.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida")
    public ResponseEntity<List<ListarTagDTO>> listarTags() {
        List<ListarTagDTO> tags = tagService.listarTodos();
        return ResponseEntity.ok(tags);
    }

    @GetMapping("/usuario/{email}")
    @Operation(summary = "Lista as tags de um usuário", description = "Retorna todas as tags de um usuário específico, buscando pelo e-mail.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida (pode retornar uma lista vazia)")
    public ResponseEntity<List<ListarTagDTO>> listarTagsPorEmailUsuario(@PathVariable String email) {
        List<ListarTagDTO> tags = tagService.listarPorEmailUsuario(email);
        return ResponseEntity.ok(tags);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma tag por ID", description = "Retorna uma tag específica com base no seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tag encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tag não encontrada para o ID informado")
    })
    public ResponseEntity<Tag> buscarTagPorId(@PathVariable Integer id) {
        // A lógica de "não encontrado" foi movida para o GlobalExceptionHandler
        Tag tag = tagService.buscarPorId(id);
        return ResponseEntity.ok(tag);
    }

    @PostMapping
    @Operation(summary = "Cadastra uma nova tag", description = "Adiciona uma nova tag ao banco de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tag cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos (ex: usuarioId não existe)")
    })
    public ResponseEntity<Tag> inserirTag(@RequestBody CadastroTagDTO tagDto) {
        Tag novaTag = tagService.cadastrar(tagDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaTag);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma tag existente", description = "Altera os dados de uma tag com base no seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tag atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tag ou Usuário não encontrado para o ID informado")
    })
    public ResponseEntity<Tag> atualizarTag(@PathVariable Integer id, @RequestBody CadastroTagDTO tagDto) {
        Tag tagAtualizada = tagService.atualizar(id, tagDto);
        return ResponseEntity.ok(tagAtualizada);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui uma tag", description = "Remove uma tag do banco de dados com base no seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tag excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tag não encontrada para o ID informado")
    })
    public ResponseEntity<Void> deletarTag(@PathVariable Integer id) {
        tagService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
