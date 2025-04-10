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