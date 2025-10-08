package br.com.senai.notes.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ArmazenamentoService {
    private final Path localArmazenamento;

    public ArmazenamentoService(@Value("${file.upload-dir}") String dir) {

        this.localArmazenamento = Paths.get(dir);

        try {
            Files.createDirectories(localArmazenamento);
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível criar a pasta de uploads.");
        }
    }

    public String salvarArquivo(MultipartFile arquivo) {
        try {
            String nomeOriginal = arquivo.getOriginalFilename();

            String extensao = nomeOriginal.substring(nomeOriginal.lastIndexOf("."));

            String nomeUnico = UUID.randomUUID().toString() + extensao;

            Path caminhoDestino = localArmazenamento.resolve(nomeUnico);

            try (InputStream inputStream = arquivo.getInputStream()) {
                Files.copy(inputStream, caminhoDestino, StandardCopyOption.REPLACE_EXISTING);
            }

            return nomeUnico;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Resource carregarArquivo(String nomeArquivo) {
        try {
            Path arquivo = localArmazenamento.resolve(nomeArquivo);

            Resource resource = new UrlResource(arquivo.toUri());

            if(resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new RuntimeException("Não foi possível ler o arquivo: " + nomeArquivo);
            }
        }
        catch (MalformedURLException e) {
            throw new RuntimeException("Erro ao tentar carregar o arquivo: " + nomeArquivo);
        }
    }

}
