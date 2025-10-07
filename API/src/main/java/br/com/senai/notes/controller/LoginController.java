package br.com.senai.notes.controller;

import br.com.senai.notes.dto.login.LoginDTO;
import br.com.senai.notes.dto.login.LoginResponseDTO;
import br.com.senai.notes.dto.usuario.ListarUsuarioDTO;
import br.com.senai.notes.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Login", description = "Endpoint para Login no Sistema")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    private final UsuarioService usuarioService;

    public LoginController(AuthenticationManager authenticationManager, JwtEncoder jwtEncoder, UsuarioService usuarioService) {
        this.authenticationManager = authenticationManager;
        this.jwtEncoder = jwtEncoder;
        this.usuarioService = usuarioService;
    }

    @PostMapping()
    @Operation(summary = "Efetua Login", description = "Retorna o token e usuário que fez o login.")
    @ApiResponses(value = { // Usamos @ApiResponses para agrupar múltiplas respostas
            @ApiResponse(responseCode = "200", description = "Login bem-sucedido",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponseDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Credenciais inválidas ou usuário não encontrado",
                    content = @Content) // Resposta sem Corpo
    })
    public ResponseEntity<?> login(@RequestBody LoginDTO loginRequest) {
        var authToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getSenha());

        ListarUsuarioDTO usuario = usuarioService.buscarPorEmailDTO(loginRequest.getEmail());

        if(usuario == null) {
            return ResponseEntity.badRequest().build();
        }

        Authentication auth = authenticationManager.authenticate(authToken);

        Instant now = Instant.now();
        long validade = 3600L; // 1 Hora em segundos.

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("urbanswift-api") // Quem emitiu o token.
                .issuedAt(now) // Quando foi emitido.
                .expiresAt(now.plusSeconds(validade)) // Quando expira.
                .subject(auth.getName())
                .build();

        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();

        String token = this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();

        return ResponseEntity.ok(new LoginResponseDTO(token, usuario));
    }
}
