# Agências API

Agências API é uma aplicação **Spring Boot** que fornece endpoints REST para gerenciar agências e consultar a agência mais próxima com base em coordenadas geográficas. Este projeto foi desenvolvido como estudo de boas práticas de **Java**, **Spring Boot**, **JPA/Hibernate**, **Spring Security** e integração com bancos de dados.

## Funcionalidades

* Listar todas as agências cadastradas.
* Buscar a agência mais próxima com base nas coordenadas fornecidas.
* Cadastrar novas agências.
* Deletar agências pelo ID.

## Arquitetura

O projeto segue a arquitetura **MVC (Model-View-Controller)** com pacotes organizados da seguinte forma:

* **models**: Entidades JPA que representam a estrutura do banco de dados.
* **repository**: Interfaces de persistência utilizando Spring Data JPA.
* **service**: Regras de negócio, como cálculo de distância entre coordenadas.
* **controllers**: Endpoints REST que expõem a API.
* **handler**: Tratamento de exceções customizadas.

## Fórmula de Cálculo da Distância

A distância entre duas coordenadas `(X1, Y1)` e `(X2, Y2)` é calculada usando o **Teorema de Pitágoras**:

```
distancia = √((X2 - X1)² + (Y2 - Y1)²)
```

## Endpoints

| Método | Endpoint                             | Descrição                      |
| ------ | ------------------------------------ | ------------------------------ |
| GET    | `/desafio`                           | Lista todas as agências        |
| GET    | `/desafio/distancia?coordX=&coordY=` | Retorna a agência mais próxima |
| POST   | `/desafio/cadastrar`                 | Cadastra uma nova agência      |
| DELETE | `/desafio/{id}`                      | Deleta uma agência pelo ID     |

## Documentação

A API está documentada com **Swagger/OpenAPI 3**, disponível em:

```
http://localhost:8090/swagger-ui/index.html
```

## Execução

1. Clone o repositório:

```bash
git clone <repo-url>
```

2. Build e execução com Maven:

```bash
mvn clean install
mvn spring-boot:run
```

3. Acesse a API em `http://localhost:8090/desafio`.

## Testes

O projeto utiliza **JUnit 5** e **Mockito** para testes unitários:

```bash
mvn test
```

## Próximos Passos

* Implementar **Spring Security** para autenticação.
* Configurar **MySQL** para ambiente de produção.
* Refatorar para **Arquitetura Hexagonal**.
* Melhorar o tratamento de erros e validação de dados.
