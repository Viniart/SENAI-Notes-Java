package br.com.senai.notes.controller;

import br.com.senai.notes.dto.anotacao.CadastroAnotacaoDTO;
import br.com.senai.notes.dto.anotacao.ListarAnotacaoDTO;
import br.com.senai.notes.service.AnotacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/anotacao")
@Tag(name = "Anotações", description = "Endpoints para gerenciamento de anotações")
@SecurityRequirement(name = "bearerAuth")
public class AnotacaoController {

    private final AnotacaoService anotacaoService;

    public AnotacaoController(AnotacaoService anotacaoService) {
        this.anotacaoService = anotacaoService;
    }

    @GetMapping
    @Operation(summary = "Lista todas as anotações", description = "Retorna uma lista com todas as anotações cadastradas no sistema.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida")
    public ResponseEntity<List<ListarAnotacaoDTO>> listarTodasAnotacoes() {
        return ResponseEntity.ok(anotacaoService.listarAnotacoes());
    }

    @GetMapping("/{email}")
    @PreAuthorize("#email == authentication.getName()")
    @Operation(summary = "Lista as anotações de um usuário", description = "Retorna todas as anotações de um usuário específico, buscando pelo e-mail.")
    @ApiResponse(responseCode = "200", description = "Operação bem-sucedida (pode retornar uma lista vazia se o usuário não tiver anotações)")
    public ResponseEntity<List<ListarAnotacaoDTO>> listarTodasAnotacoes(@PathVariable String email, Authentication authentication) {
        return ResponseEntity.ok(anotacaoService.listarAnotacoesPorEmail(email));
    }

    @PostMapping
    @Operation(summary = "Cadastra uma nova anotação", description = "Adiciona uma nova anotação ao banco de dados, associando-a a um usuário e a tags. Se uma tag informada não existir para o usuário, ela será criada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Anotação cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos na requisição (ex: usuarioId não existe)")
    })
    public ResponseEntity<CadastroAnotacaoDTO> cadastrarAnotacao(@Valid @RequestBody CadastroAnotacaoDTO dto) {
        CadastroAnotacaoDTO anotacaoDTO = anotacaoService.cadastrarAnotacao(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(anotacaoDTO);
    }
}
