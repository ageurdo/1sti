
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