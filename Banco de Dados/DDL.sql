CREATE SCHEMA senainotes;

CREATE TABLE senainotes.usuario(
	id_usuario INT PRIMARY KEY generated always as IDENTITY,
	nome TEXT,
	email TEXT NOT NULL UNIQUE,
	senha TEXT NOT NULL
);

CREATE TABLE senainotes.anotacao (
    id_anotacao INT primary key generated always as IDENTITY,
    titulo TEXT NOT NULL,
    descricao TEXT NOT NULL,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_edicao TIMESTAMP,
    imagem_anotacao TEXT NOT NULL,
    anotacao_arquivada BOOLEAN NOT NULL,
    id_usuario INT REFERENCES senainotes.usuario(id_usuario)
);

CREATE TABLE senainotes.tag (
    id_tag SERIAL PRIMARY KEY,
    nome_tag TEXT NOT NULL,
    id_usuario INT REFERENCES senainotes.usuario(id_usuario)
);


CREATE TABLE senainotes.tag_anotacao (
    id_anotacao INT REFERENCES senainotes.anotacao(id_anotacao),
    id_tag INT REFERENCES senainotes.tag(id_tag),
    primary key (id_anotacao, id_tag)
);


