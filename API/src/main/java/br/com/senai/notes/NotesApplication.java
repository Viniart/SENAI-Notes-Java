package br.com.senai.notes;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "API do Projeto SENAI Notes",
        version = "1.0",
        description = "API responsável pelo gerenciamento de usuários, tags, anotações e processo de Login do Projeto SENAI Notes"
))
@SecurityScheme(
        name = "bearerAuth", // 1. Um nome para referenciar este esquema de segurança.
        type = SecuritySchemeType.HTTP, // 2. O tipo de segurança. HTTP é usado para Bearer, Basic Auth, etc.
        scheme = "bearer", // 3. O esquema específico. "bearer" para JWT.
        bearerFormat = "JWT" // 4. Um "hint" para o formato do token.
)
public class NotesApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotesApplication.class, args);
	}

}
