GlasshFish 8.0.0

java 21

----------

Criação do banco(MYSQL):

CREATE DATABASE IF NOT EXISTS sistemadetarefas;
USE sistemadetarefas;

-- Tabela de Usuários
CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    login VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    perfil ENUM('ADMIN', 'NORMAL') NOT NULL
);

-- Tabela de Categorias
CREATE TABLE categoria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL UNIQUE
);

-- Tabela de Tarefas
CREATE TABLE tarefa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    descricao VARCHAR(255),
    data DATE,
    id_usuario INT NOT NULL,
    id_categoria INT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id) ON DELETE CASCADE,
    FOREIGN KEY (id_categoria) REFERENCES categoria(id)
);

-- CUMPRINDO A REGRA: Inserindo o primeiro administrador fixo
INSERT INTO usuario (nome, login, senha, perfil) 
VALUES ('Rafael Admin', 'admin', 'admin123', 'ADMIN');

-- (Opcional) Inserindo categorias iniciais para o sistema não nascer vazio
INSERT INTO categoria (nome) VALUES ('CASA'), ('TRABALHO'), ('ESTUDOS');






REVISAR CODIGO + AQUILO QUE O PROF PEDIU
