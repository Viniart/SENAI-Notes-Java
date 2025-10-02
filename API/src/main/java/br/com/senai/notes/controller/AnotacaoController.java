package br.com.senai.notes.controller;

import br.com.senai.notes.dto.anotacao.CadastroAnotacaoDTO;
import br.com.senai.notes.dto.anotacao.ListarAnotacaoDTO;
import br.com.senai.notes.service.AnotacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/anotacao")
public class AnotacaoController {

    private final AnotacaoService anotacaoService;

    public AnotacaoController(AnotacaoService anotacaoService) {
        this.anotacaoService = anotacaoService;
    }

    @GetMapping
    public ResponseEntity<List<ListarAnotacaoDTO>> listarTodasAnotacoes() {
        return ResponseEntity.ok(anotacaoService.listarAnotacoes());
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<ListarAnotacaoDTO>> listarTodasAnotacoes(@PathVariable String email) {
        return ResponseEntity.ok(anotacaoService.listarAnotacoesPorEmail(email));
    }

    @PostMapping
    public ResponseEntity<CadastroAnotacaoDTO> cadastrarAnotacao(@RequestBody CadastroAnotacaoDTO dto) {
        CadastroAnotacaoDTO anotacaoDTO = anotacaoService.cadastrarAnotacao(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(anotacaoDTO);
    }
}
