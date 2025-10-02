package br.com.senai.notes.service;

import br.com.senai.notes.dto.anotacao.CadastroAnotacaoDTO;
import br.com.senai.notes.dto.anotacao.ListarAnotacaoDTO;
import br.com.senai.notes.dto.tag.ListarTagDTO;
import br.com.senai.notes.dto.usuario.ListarUsuarioDTO;
import br.com.senai.notes.model.*;
import br.com.senai.notes.repository.AnotacaoRepository;
import br.com.senai.notes.repository.TagAnotacaoRepository;
import br.com.senai.notes.repository.TagRepository;
import br.com.senai.notes.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnotacaoService {

    private final AnotacaoRepository anotacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final TagRepository tagRepository;
    private final TagAnotacaoRepository tagAnotacaoRepository;

    public AnotacaoService(AnotacaoRepository anotacaoRepository, UsuarioRepository usuarioRepository, TagRepository tagRepository, TagAnotacaoRepository tagAnotacaoRepository) {
        this.anotacaoRepository = anotacaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.tagRepository = tagRepository;
        this.tagAnotacaoRepository = tagAnotacaoRepository;
    }

    public List<ListarAnotacaoDTO> listarAnotacoes(){
        List<Anotacao> anotacoes = anotacaoRepository.findAll();

        return anotacoes.stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public List<ListarAnotacaoDTO> listarAnotacoesPorEmail(String email){
        List<Anotacao> anotacoes = anotacaoRepository.findByUsuarioEmailCompleto(email);

        return anotacoes.stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public CadastroAnotacaoDTO cadastrarAnotacao(CadastroAnotacaoDTO anotacao) {
        // 1. Busco o Usuário
        Usuario usuario = usuarioRepository.findById(anotacao.getUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado!"));

        // 2. Cadastro a Anotação
        Anotacao novaAnotacao = new Anotacao();
        novaAnotacao.setUsuario(usuario);
        novaAnotacao.setTitulo(anotacao.getTitulo());
        novaAnotacao.setDescricao(anotacao.getDescricao());
        novaAnotacao.setImagemAnotacao(anotacao.getImagemUrl());
        novaAnotacao.setDataCriacao(Instant.now());
        novaAnotacao.setDataEdicao(Instant.now());
        novaAnotacao.setAnotacaoArquivada(false);

        // Salvo a anotação no banco e guardo o resultado (contém o Id)
        Anotacao anotacaoSalva = anotacaoRepository.save(novaAnotacao);

        // 3. Processo as tags
        for (String nomeTag : anotacao.getTags()) {
            Tag tag = tagRepository.findByNomeTagAndUsuarioId(nomeTag, usuario.getId())
                    .orElseGet(() -> {
                        Tag novaTag = new Tag();
                        novaTag.setNomeTag(nomeTag);
                        novaTag.setUsuario(usuario);

                        return tagRepository.save(novaTag);
                    });

            // 4. Cadastro na tabela intermediária
            // Criamos a chave composta para a nossa tabela intermediária.
            TagAnotacaoId tagAnotacaoId = new TagAnotacaoId();
            tagAnotacaoId.setIdAnotacao(anotacaoSalva.getId());
            tagAnotacaoId.setIdTag(tag.getId());

            TagAnotacao associacao = new TagAnotacao();
            associacao.setId(tagAnotacaoId);
            associacao.setIdAnotacao(anotacaoSalva);
            associacao.setIdTag(tag);

            tagAnotacaoRepository.save(associacao);
        }

        return anotacao;
    }

    private ListarAnotacaoDTO converterParaDTO(Anotacao anotacao) {
        ListarAnotacaoDTO dto = new ListarAnotacaoDTO();

        dto.setId(anotacao.getId());
        dto.setTitulo(anotacao.getTitulo());
        dto.setDescricao(anotacao.getDescricao());
        dto.setImagemAnotacao(anotacao.getImagemAnotacao());
        dto.setDataCriacao(anotacao.getDataCriacao());
        dto.setDataEdicao(anotacao.getDataEdicao());

        dto.setUsuario(convertUsuarioToDto(anotacao.getUsuario()));

        List<ListarTagDTO> tagsDto = anotacao.getTagAnotacao().stream()
                .map(tagAnotacao -> convertTagToDto(tagAnotacao.getIdTag()))
                .collect(Collectors.toList());
        dto.setTags(tagsDto);

        return dto;
    }

    private ListarUsuarioDTO convertUsuarioToDto(Usuario usuario) {
        ListarUsuarioDTO dto = new ListarUsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        return dto;
    }

    private ListarTagDTO convertTagToDto(Tag tag) {
        ListarTagDTO dto = new ListarTagDTO();
        dto.setId(tag.getId());
        dto.setNomeTag(tag.getNomeTag());
        return dto;
    }
}
