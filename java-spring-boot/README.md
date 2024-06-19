**Desafio: Cadastro de Usuários com Operações**

**Requisitos**

- Utilizar o padrão de projeto Restful para os micro serviços
- Utilizar o padrão de projeto JWT para implementação da camada de segurança no processo de autenticação e autorização
- Utilizar na camada de persistência o banco de dados relacional MySQL

**Modelo**

- Entidade Usuário

  - Identificadores únicos

    - Chave artificial administrada pelo sistema
    - Chave de negócio, por exemplo: cpf

  - Campos obrigatórios

    - Nome
    - Data de nascimento
    - Endereço (Rua, número, complemento, bairro, cidade, estado, cep)

  - Campos não funcionais

    - Status do registro: Ativo ou Removido
    - Data e hora de criação do registro
    - Usuário utilizado na criação do registro
    - Data e hora de atualização do registro
    - Usuário utilizado na atualização do registro
    - Data e hora de remoção do registro
    - Usuário utilizado na remoção do registro

**Checkpoints**

- **[X] 1: Criação do repositório Git**

  - Criar um repositório Git público para o desafio
  - Adicionar o arquivo README.md com as instruções e requisitos do desafio

- **[X] 2: Definição do modelo de dados**

  - Definir o modelo de dados para a entidade Usuário
  - Criar as tabelas necessárias no banco de dados MySQL

- **[X] 3: Implementação da camada de persistência**

  - Implementar a camada de persistência utilizando o banco de dados relacional MySQL
  - Criar endpoints para CRUD (Create, Read, Update, Delete) de usuários

  - Criar endpoints para realizar operações de cadastro de usuários

- **[X] 4: Implementação da lógica de negócio**

  - Implementar a lógica de negócios para a entidade Usuário

  - Adicionar Dtos e validar rotas
  - [x] Rota - Criar usuário;

  - [x] Rota - Atualizar senha;

  - [x] Rota - Usuário por id;

  - [x] Rota - Todos usuários;

  - [x] Rota - Atualizar usuário;

  - [x] Padronizar e tratar Exceções

  - [x] Padronizar exceções de atualizar senha

  - [x] Padronizar excessão de Bad Request e Method Not Allowed...

  - [x] Auth - Validar se usuário que vai autenticar esta deletado;

  - [ ] Delete - Capturar dados do usuário que esta fazendo request para anotar no deleteBy;

- **[X] 5: Documentar API**

  - [x] Adicionar SpringDoc
  - [x] Documentar Rota - Criar usuário;

  - [x] Documentar Rota - Atualizar senha;

  - [x] Documentar Rota - Usuário por id;

  - [x] Documentar Rota - Todos usuários;

  - [x] Documentar Rota - Atualizar usuário;

  - [x] Documentar Rota - Deletar usuário;

- **[X] 6: Implementação de testes**

  - Criar testes para a aplicação utilizando frameworks de teste adequados
  - Realizar testes ponta a ponta nas rotas abaixo

  - [x] CreateUser, CPF duplicado, CPF válido; Senha vazia, Senha inferior ou superior a 8 digitos
  - [x] UpdatePassowordenha, validar se senha é diferente de 8 digitos com id existente e não existente

  - [x] GetUserById, com id existente e não existente;

  - [x] GetAll;

  - [x] UpdateUser;

  - [x] Incluir nos testes autenticação para as rotas necessárias;

- **[X] 7: Implementação da camada de segurança**

  - [x] Implementar a camada de segurança utilizando o padrão de projeto JWT
  - [x] Criar endpoints para autenticação

  - [x] Implementar Auditoria

  - Regras adicionais com base na Role (função)

  - [x] Um usuário com a função "User" apenas consultará e alterará recursos pertencentes ao mesmo;

  - [x] Um usuário com a função "Admin" consultará todos usuários da base, individualmente ou listando todos, mas só alterara os dados pertecentes a ele;

  - [x] Desbloquear rota do Swagger, bloqueada após implementação da autenticação;

  - [x] Quando o usuário for excluído atualizar campos virtais e soft delete na mão (Campo status)

- **[ ] 8: Entrega do código fonte**

  - [x] Criar instruções para iniciar ambiente

  - Entregar o código fonte em um repositório Git público
  - Incluir instruções claras para montar o ambiente, rodar os testes e rodar a aplicação

**Avaliação**

- Organização e estrutura do código
- Adesão às melhores práticas de desenvolvimento
- Eficiência na resolução dos desafios propostos
- Cobertura de testes adequada
- Documentação clara e completa

**Instruções para montar o ambiente**

- **1: Execute o docker-compose.yml via cmd para levantar um servidor Mysql**

```bash
$ docker-compose up -d
```

- **2: Execute o sql no seu gerenciador de banco de dados**

```bash
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
```

Obs: execute um bloco por vez. Na imagem abaixo fiz a configuração com o Azure Data Studio;

  !Connection Instructions

## Configurações do banco 

  Configurações do banco de dados estão no arquivo ./target/classes/com/application.properties

![Connection Instructions](./instructions/banco.png)

## Testes

Para rodar os testes navegue da raiz do projeto para src/test/java/com.ageurdo.demo_user_auth.
Clique com o botão direito do mouse sobre o arquivo UserIt e ir em Run UserIt:


![Connection Instructions](./instructions/tests.png)


## Insomnia
Na pasta ./Instructions há um arquivo com o nome de Insomnia, você pode importar ele para o mesmo, todas rotas estarão configuradas neste arquivo.

## Swagger
Para abrir o swagger com o projeto já iniciado abra no navegador esta url: http://localhost:8080/swagger-ui/index.html.

