package br.com.senai.notes.controller;

import br.com.senai.notes.dto.anotacao.CadastroAnotacaoComImagemDTO;
import br.com.senai.notes.dto.anotacao.CadastroAnotacaoDTO;
import br.com.senai.notes.dto.anotacao.ListarAnotacaoDTO;
import br.com.senai.notes.service.AnotacaoService;
import br.com.senai.notes.service.ArmazenamentoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/anotacao")
@Tag(name = "Anotações", description = "Endpoints para gerenciamento de anotações")
@SecurityRequirement(name = "bearerAuth")
public class AnotacaoController {

    private final AnotacaoService anotacaoService;
    private final ArmazenamentoService armazenamentoService;
    private final ObjectMapper objectMapper;

    public AnotacaoController(AnotacaoService anotacaoService, ArmazenamentoService armazenamentoService, ObjectMapper objectMapper) {
        this.anotacaoService = anotacaoService;
        this.armazenamentoService = armazenamentoService;
        this.objectMapper = objectMapper;
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
    public ResponseEntity<CadastroAnotacaoDTO> cadastrarAnotacaoSemImagem(@Valid @RequestBody CadastroAnotacaoDTO dto) {
        CadastroAnotacaoDTO anotacaoDTO = anotacaoService.cadastrarAnotacaoSemImagem(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(anotacaoDTO);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, path = "/imagem")
    @Operation(summary = "Cadastra uma nova anotação com imagem", description = "Adiciona uma nova anotação ao banco de dados, associando-a a um usuário e a tags, e salvando a imagem junto da API. Se uma tag informada não existir para o usuário, ela será criada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Anotação cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos na requisição (ex: usuarioId não existe)")
    })
    public ResponseEntity<CadastroAnotacaoDTO> cadastrarAnotacaoComImagem(@Valid @ModelAttribute CadastroAnotacaoComImagemDTO dto) {
        CadastroAnotacaoDTO anotacaoDTO = anotacaoService.cadastrarAnotacaoComImagem(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(anotacaoDTO);
    }

    @GetMapping("/imagens/{nomeDoArquivo}")
    public ResponseEntity<Resource> servirImagem(@PathVariable String nomeDoArquivo) {

        // Usa nosso serviço para carregar o arquivo do disco.
        Resource arquivo = armazenamentoService.carregarArquivo(nomeDoArquivo);

        // Retorna o arquivo como resposta. O navegador saberá como exibir a imagem.
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE) // Dica para o navegador que é uma imagem
                .body(arquivo);
    }
}
