# Projeto To-Do (CRUD) - Backend

## Sobre o Projeto

Este projeto consiste em uma API backend simples para o gerenciamento de tarefas do dia a dia. Ele permite criar, ler, atualizar e deletar tarefas, fornecendo uma base para futuras extensões.

## Entidades

A aplicação gerencia as seguintes entidades:

* **Tarefa:** Representa uma atividade a ser realizada.
    * **Nome:** Título da tarefa (String).
    * **Descrição:** Detalhes adicionais sobre a tarefa (String).
    * **Status:** Estado atual da tarefa (Ex: "Pendente", "Em Andamento", "Concluída") (String).
    * **Observações:** Notas ou comentários sobre a tarefa (String).
    * **Data de Criação:** Timestamp da criação da tarefa (Timestamp).
    * **Data de Atualização:** Timestamp da última atualização da tarefa (Timestamp).

## Requisitos

A API backend permite as seguintes operações (CRUD - Create, Read, Update, Delete):

* **Criar Tarefa:** Adiciona uma nova tarefa ao sistema.
* **Alterar Tarefa:** Modifica os detalhes de uma tarefa existente.
* **Deletar Tarefa:** Remove uma tarefa do sistema.

## Regras de Negócio

* **Sem Autenticação:** O sistema não implementa um sistema de login ou autenticação de usuários.
* **Sem Usuários:** Não há o conceito de usuários distintos associados às tarefas. Todas as tarefas são gerenciadas de forma global.

## Tecnologias Utilizadas

* **Linguagem de Programação:** Java
* **Framework Backend:** Java Spring Boot
* **Banco de Dados:** PostgreSQL

***

## Etapas do Desenvolvimento

### 1. Criação do Banco de Dados

Configuraremos o banco de dados PostgreSQL para armazenar as informações das tarefas.

1.  **Instalação do PostgreSQL:** Foi realizado a instalação do PostgreSQL no ambiente de desenvolvimento.

2.  **Criação do Banco de Dados:** Através da ferramenta de administração do banco de dados, foi criado um novo banco de dados chamado `todo_db`.

    ```sql
    CREATE DATABASE todo_db;
    ```

3.  **Criação da Tabela `tasks` (tarefas):** Foi definido a seguinte estrutura de tabela para armazenar as tarefas:

    ```sql
    CREATE TABLE tasks (
        id SERIAL PRIMARY KEY,
        name VARCHAR(255) NOT NULL,
        description TEXT,
        status VARCHAR(50) NOT NULL,
        observations TEXT,
        criated_date TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
        updated_date TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
    );
    ```

### 2. Criação do Projeto Spring Boot

Foi utilizado o [Spring Initializr](https://start.spring.io/) para criar um novo projeto Spring Boot com as configurações: 

```yaml
- Project: Maven
- Language: Java
- Spring Boot: 3.4.4
- Project Metadata:
    - Group: com.app
    - Artifact: todo
    - Name: todo
    - Description: A daily task manager app
    - Package name: com.app.todo
    - Packaging: Jar
    - Java: 17
- Dependencies: |
    - Spring Web
    - Spring Data JPA
    - PostgreSQL Driver
```

Foi adicionado [Task.java](./src/main/java/com/app/todo/model/Task.java)

Foi configurado o Spring Boot para se conectar ao banco de dados PostgreSQL criado.
Por questões de segurança as informações de conexões não foram adicionadas, mas você pode encontrar o modelo em [application.properties.example](./src/main/resources/application.properties.example).

```properties
spring.datasource.url=                      : URL de conexão com o banco de dados PostgreSQL.
spring.datasource.username=                 : Nome de usuário do banco de dados.
spring.datasource.password=                 : Senha do banco de dados.
spring.datasource.driver-class-name=        : Driver JDBC do PostgreSQL.
spring.jpa.hibernate.ddl-auto=update        : Permite que o Hibernate atualize o schema do banco de dados com base nas entidades (use com cautela em produção).
spring.jpa.properties.hibernate.dialect=    : Dialeto do Hibernate para o PostgreSQL.
spring.jpa.show-sql=true                    : Exibe as queries SQL geradas pelo Hibernate no console.
spring.jpa.format_sql=true                  : Formata as queries SQL exibidas no console.
```

Foi criado uma interface que estende `JpaRepository` para interagir com a tabela `tasks`:

[TaskRepository.java](./src/main/java/com/app/todo/repository/TaskRepository.java)

Foi criado os serviços de implementação da lógica de negócios:

Interface: [TaskService.java](./src/main/java/com/app/todo/service/TaskService.java)

Implementação: [TaskServiceImpl.java](./src/main/java/com/app/todo/service/TaskServiceImpl.java)

