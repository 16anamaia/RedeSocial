CREATE TABLE tbstatusconexao (
  id SERIAL PRIMARY KEY,
  descricao VARCHAR(50) NOT NULL
);

CREATE TABLE tbusuario (
  nome VARCHAR(100) PRIMARY KEY,
  genero VARCHAR(30) NOT NULL,
  senha VARCHAR(50) NOT NULL,
  data_nascimento DATE,
  email VARCHAR(100),
  naturalidade VARCHAR(50) NOT NULL
);

CREATE TABLE tbconexao (
  id SERIAL PRIMARY KEY,
  id_usuario_origem VARCHAR(100) NOT NULL, -- Alterado para ser do mesmo tipo que o campo 'nome' na tabela 'tbusuario'
  id_usuario_destino VARCHAR(100) NOT NULL, -- Alterado para ser do mesmo tipo que o campo 'nome' na tabela 'tbusuario'
  id_status INT NOT NULL,
  FOREIGN KEY (id_status) REFERENCES tbstatusconexao (id) ON UPDATE CASCADE,
  FOREIGN KEY (id_usuario_destino) REFERENCES tbusuario (nome) ON UPDATE CASCADE,
  FOREIGN KEY (id_usuario_origem) REFERENCES tbusuario (nome) ON UPDATE CASCADE
);

CREATE TABLE tbmensagem (
  id SERIAL PRIMARY KEY,
  id_usuario_origem VARCHAR(100) NOT NULL, -- Alterado para ser do mesmo tipo que o campo 'nome' na tabela 'tbusuario'
  id_usuario_destino VARCHAR(100) NOT NULL, -- Alterado para ser do mesmo tipo que o campo 'nome' na tabela 'tbusuario'
  conteudo TEXT,
  data_envio DATE DEFAULT CURRENT_DATE,
  FOREIGN KEY (id_usuario_destino) REFERENCES tbusuario (nome) ON UPDATE CASCADE,
  FOREIGN KEY (id_usuario_origem) REFERENCES tbusuario (nome) ON UPDATE CASCADE
);

CREATE TABLE tbamizade (
  id SERIAL PRIMARY KEY,
  id_usuario1 VARCHAR(100) NOT NULL,
  id_usuario2 VARCHAR(100) NOT NULL,
  data_conexao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (id_usuario1) REFERENCES tbusuario (nome) ON DELETE CASCADE,
  FOREIGN KEY (id_usuario2) REFERENCES tbusuario (nome) ON DELETE CASCADE
);



