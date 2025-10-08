# SENAI Notes API

API RESTful para um sistema de anotações, permitindo que usuários cadastrem, gerenciem e categorizem suas notas com tags personalizadas.

## Visão Geral do Projeto

O SENAI Notes é um sistema para gerenciamento de anotações pessoais. A API serve como o backend para uma aplicação frontend (não inclusa neste repositório), provendo todos os endpoints necessários para as operações de CRUD de anotações, tags e usuários, com um sistema de autenticação baseado em Tokens JWT.

### Telas da Aplicação (Frontend)

Abaixo seguem imagens do Frontend atendido por esse projeto:

<img width="1917" height="941" alt="front-end-senai-notes" src="https://github.com/user-attachments/assets/c7ee6325-9098-4e75-84e9-7512e8acb2ca" />
<img width="1920" height="943" alt="image" src="https://github.com/user-attachments/assets/b05b7c7d-af7a-435a-9e13-0e129b5939f7" />

### Arquitetura e Modelagem do Banco de Dados

A arquitetura segue os padrões de projetos REST, utilizando DTOs (Data Transfer Object) para a comunicação entre cliente e servidor, e estrutura de camadas (Controller, Service, Repository).

<img width="853" height="536" alt="image" src="https://github.com/user-attachments/assets/adcdb9ff-8c09-4b8c-b3f0-fccd4ee036f3" />

### Documentação

A API foi documentada utilizando o Swagger:

<img width="1820" height="797" alt="image" src="https://github.com/user-attachments/assets/7566aa74-19f5-4862-81d4-a2738a4b3751" />


#### Tabelas do Banco de Dados

O projeto é centrado no cadastro de anotações que podem possuir diversas tags. A estrutura do banco de dados é composta por 4 tabelas principais:

  * **`Usuario`**: Armazena as informações dos usuários, como nome, email e senha (criptografada), essenciais para a autenticação e para vincular a propriedade das anotações e tags.
  * **`Anotacao`**: Tabela principal que guarda o conteúdo das notas, incluindo título, descrição, data de criação e a referência ao usuário proprietário.
  * **`Tag`**: Armazena as tags (etiquetas) que os usuários podem criar para categorizar suas anotações. Cada tag também pertence a um usuário.
  * **`TagAnotacao`**: Tabela intermediária (ou de junção) que estabelece a relação **Muitos-para-Muitos** entre `Anotacao` e `Tag`, permitindo que uma anotação tenha várias tags e uma tag seja usada em várias anotações.

## Funcionalidades Principais

  - [x] **Autenticação:** Login de usuários com Spring Security e geração de Token JWT para autorização das requisições.
  - [x] **Gerenciamento de Usuários:** Cadastro de novos usuários com criptografia de senha.
  - [x] **CRUD de Anotações:** Funcionalidades completas para Criar, Ler, Atualizar e Deletar anotações.
  - [x] **Upload e Exibição de Imagens**: Permite o upload de imagens locais para serem associadas a uma anotação (através do endpoint /api/anotacao/imagem) e a sua posterior exibição (via /api/anotacao/imagem/{nomeImagem}).
  - [x] **Redefinição de Senha via E-mail**: Funcionalidade para que usuários que esqueceram a senha possam solicitar uma nova. O sistema gera uma senha temporária e a envia para o e-mail cadastrado do usuário utilizando o serviço SMTP do Gmail.
  - [x] **Sistema de Tags Dinâmico:** Um usuário pode criar novas tags durante o cadastro de uma anotação, e o sistema as reutiliza de forma inteligente.
  - [x] **Listagem de Anotação por Usuário:** Endpoints para listar anotações por usuário.
  - [x] **Padrão DTO:** Utilização de Data Transfer Objects para garantir a segurança e a integridade dos dados trafegados.
  - [x] **Tratamento de Exceções:** ControllerAdvice centralizado para tratar erros de forma padronizada e retornar respostas amigáveis para o cliente.
  - [x] **Documentação com Swagger:** Geração automática de documentação da API, facilitando os testes e a integração.
  - [x] **Publicação na Nuvem:** Aplicação configurada para deploy na AWS.

## Tecnologias Utilizadas

  * **Linguagem:** Java 21
  * **Framework:** Spring Boot 3
  * **Segurança:** Spring Security 6, JSON Web Tokens (JWT)
  * **Banco de Dados:** PostgreSQL
  * **ORM:** Spring Data JPA (Hibernate)
  * **Documentação:** Springdoc OpenAPI (Swagger)
  * **Testes:** Postman

## Estrutura do Repositório

Este repositório está organizado nas seguintes pastas:

  * **/Modelagem**: Contém os artefatos de modelagem do banco de dados (conceitual, lógico e físico).
  * **/Banco de Dados**: Contém o script DDL (`database.sql`) para criação das tabelas no PostgreSQL.
  * **/API**: Contém o código-fonte completo do projeto Java Spring Boot.

## Como Rodar o Projeto Localmente

Siga os passos abaixo para configurar e executar a API em seu ambiente de desenvolvimento.

### Requisitos

  * **Java JDK 21** ou superior.
  * **PostgreSQL 14** ou superior.
  * Uma IDE de sua preferência (IntelliJ, VSCode, Eclipse).

### Configuração

1.  **Clone o repositório:**

    ```bash
    git clone <url-do-seu-repositorio>
    cd <nome-do-repositorio>
    ```

2.  **Crie o Banco de Dados:**

      * Abra seu cliente PostgreSQL (utilizando DBeaver ou pgAdmin).
      * Execute o script DDL para criar as tabelas, no Banco de Dados de sua preferência.

3.  **Configure a Conexão:**

      * Navegue até a pasta `/API`.
      * Abra o arquivo `src/main/resources/application.properties`.
      * Altere as propriedades de conexão com o banco de dados conforme sua configuração local:
        ```properties
        spring.datasource.url=jdbc:postgresql://localhost:5432/senai_notes_db
        spring.datasource.username=seu_usuario_postgres
        spring.datasource.password=sua_senha_postgres
        ```
      * Configure também o segredo do JWT:
        ```properties
        api.security.token.secret=${JWT_SECRET:meu-secret-padrao}
        ```

### Execução

1.  **Via Linha de Comando (Maven):**

      * Navegue até a pasta raiz do projeto `/API`.
      * Execute o comando:
        ```bash
        mvn spring-boot:run
        ```

2.  **Via IDE:**

      * Importe o projeto como um projeto Maven.
      * Encontre a classe principal `NotesApplication.java` (geralmente em `src/main/java/br/com/senai/notes`).
      * Execute o método `main` desta classe.

A aplicação estará disponível em `http://localhost:8080`.

## Documentação da API (Swagger)

Com a aplicação em execução, a documentação completa dos endpoints gerada pelo Swagger pode ser acessada no seguinte endereço:

**http://localhost:8080/swagger-ui.html**

Lá você encontrará todos os endpoints disponíveis, seus parâmetros, exemplos de requisição e resposta, e poderá testá-los diretamente pelo navegador.

-----
