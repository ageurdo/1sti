**Desafio: Cadastro de Usuários com Operações**

**Requisitos**

*   Utilizar o padrão de projeto Restful para os micro serviços
    
*   Utilizar o padrão de projeto JWT para implementação da camada de segurança no processo de autenticação e autorização
    
*   Utilizar na camada de persistência o banco de dados relacional MySQL
    

**Modelo**

*   Entidade Usuário
    
    *   Identificadores únicos
        
        *   Chave artificial administrada pelo sistema
            
        *   Chave de negócio, por exemplo: cpf
            
    *   Campos obrigatórios
        
        *   Nome
            
        *   Data de nascimento
            
        *   Endereço (Rua, número, complemento, bairro, cidade, estado, cep)
            
    *   Campos não funcionais
        
        *   Status do registro: Ativo ou Removido
            
        *   Data e hora de criação do registro
            
        *   Usuário utilizado na criação do registro
            
        *   Data e hora de atualização do registro
            
        *   Usuário utilizado na atualização do registro
            
        *   Data e hora de remoção do registro
            
        *   Usuário utilizado na remoção do registro
            

**Checkpoints**

*   **[X] 1: Criação do repositório Git**
    
    *   Criar um repositório Git público para o desafio
        
    *   Adicionar o arquivo README.md com as instruções e requisitos do desafio
        
*   **[X] 2: Definição do modelo de dados**
    
    *   Definir o modelo de dados para a entidade Usuário
        
    *   Criar as tabelas necessárias no banco de dados MySQL
              
*   **[X] 3: Implementação da camada de persistência**
    
    *   Implementar a camada de persistência utilizando o banco de dados relacional MySQL
        
    *   Criar endpoints para CRUD (Create, Read, Update, Delete) de usuários

    *   Criar endpoints para realizar operações de cadastro de usuários

*   **[] 4: Implementação da lógica de negócios**
    
    *   Implementar a lógica de negócios para a entidade Usuário

    *   Adicionar Dtos e validar rotas
        
    *  [x] Rota - Criar usuário;

    *  [x] Rota - Atualizar senha;

    *  [x] Rota - Usuário por id;
    
    *  [x] Rota - Todos usuários;

    *  [ ] Rota - Atualizar usuário;
    
    *  [x] Padronizar e tratar Exceções

    *  [x] Padronizar exceções de atualizar senha

    *  [x] Padronizar excessão de Bad Request e Method Not Allowed...


    **[] 5: Documentar API**
    
    *  [x] Adicionar SpringDoc
        
    *  [x] Documentar Rota - Criar usuário;

    *  [x] Documentar Rota - Atualizar senha;

    *  [x] Documentar Rota - Usuário por id;
    
    *  [x] Documentar Rota - Todos usuários;
    
    *  [x] Documentar Rota - Atualizar usuário;
        

*   **[] 6: Implementação da camada de segurança**
    
    *   Implementar a camada de segurança utilizando o padrão de projeto JWT
        
    *   Criar endpoints para autenticação e autorização
        
        
*   **[] 7: Implementação de testes**
    
    *   Criar testes para a aplicação utilizando frameworks de teste adequados
        
    *   Realizar testes de unidade e integração para a aplicação
        
*   **[] 8: Entrega do código fonte**
    
    *   Entregar o código fonte em um repositório Git público
        
    *   Incluir instruções claras para montar o ambiente, rodar os testes e rodar a aplicação
        

**Avaliação**

*   Organização e estrutura do código
    
*   Adesão às melhores práticas de desenvolvimento
    
*   Eficiência na resolução dos desafios propostos
    
*   Cobertura de testes adequada
    
*   Documentação clara e completa
    

**Histórico de Commits**

*   \[Commit 1: Criação do repositório Git\](link para o commit)
    
*   \[Commit 2: Definição do modelo de dados\](link para o commit)
    
*   \[Commit 3: Implementação da camada de segurança\](link para o commit)
    
*   \[Commit 4: Implementação da camada de persistência\](link para o commit)
    
*   \[Commit 5: Implementação da lógica de negócios\](link para o commit)
    
*   \[Commit 6: Implementação de testes\](link para o commit)
    
*   \[Commit 7: Entrega do código fonte\](link para o commit)
    

**Instruções para montar o ambiente**

*   \[Instruções para montar o ambiente\](link para as instruções)
    

**Instruções para rodar os testes**

*   \[Instruções para rodar os testes\](link para as instruções)
    

**Instruções para rodar a aplicação**

*   \[Instruções para rodar a aplicação\](link para as instruções)