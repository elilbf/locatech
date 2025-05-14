CREATE TABLE veiculos
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    marca       VARCHAR(255),
    modelo      VARCHAR(255),
    placa       VARCHAR(255),
    ano         INT,
    cor         VARCHAR(255),
    valor_diaria DECIMAL(10, 2)
);

CREATE TABLE pessoas
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome     VARCHAR(255),
    cpf      VARCHAR(255),
    telefone VARCHAR(255),
    email    VARCHAR(255)
);

INSERT INTO veiculos (marca, modelo, placa, ano, cor, valor_diaria)
VALUES ('Fiat', 'Uno', 'ABC1234', 2010, 'Azul', 50.00);

INSERT INTO pessoas (nome, cpf, telefone, email)
VALUES ('Fulano da Silva', '12345678', '81999999999', 'teste@email.com');