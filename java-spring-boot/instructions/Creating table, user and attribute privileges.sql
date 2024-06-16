-- Criar banco de dados
CREATE DATABASE 1sti;

-- Criar usuário MainUser
CREATE USER 'MainUser' IDENTIFIED BY 'MainPassword';

-- Atribuir permissões totais ao usuário MainUser sobre o banco de dados 1sti
GRANT ALL PRIVILEGES ON 1sti.* TO 'MainUser'@'%';

-- Dar permissão para o usuário MainUser criar tabelas, inserir dados, etc.
GRANT CREATE, INSERT, UPDATE, DELETE, DROP, INDEX, ALTER ON 1sti.* TO 'MainUser'@'%';

-- Atualizar privilégios
FLUSH PRIVILEGES;

-- Cria usuário administrador, não há rota para tronar um usuário administrador via Api;
INSERT INTO users (id, cpf, password, name, date_of_birth, role, street, number, complement, neighborhood, city, state, status, zip_code, created_at, created_by, updated_at, updated_by, deleted_at, deleted_by)
VALUES (1, '73553655500', '$2a$12$UI31aDSVDESajV3h.ASAsO2O0x.Ldt./7cOoPE6yI4ghkBpnjD9La', 'Rebeca Sara Barbosa', '1990-02-20T10:00:00','ROLE_ADMIN', 'Rua da Praia', '456', 'Casa 2', 'Praia Grande', 'Santos', 'SP', 0, '11015-000', '2024-06-13T10:00:00', 'SQL Teste Banco H2', null, null, null, null);