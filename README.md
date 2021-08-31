# Carango Bom API

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=graysoncunha_carango-bom-api-base&metric=alert_status)](https://sonarcloud.io/dashboard?id=graysoncunha_carango-bom-api-base)

Projeto para cadastro e venda de carros.

## Grupo

- Bruno Bacelar
- Grayson Cunha
- Laura Oliveira

## Stack

- Java
- Spring Boot
- Postgres
- Docker
- Heroku

## Github actions

### Build and Deploy

- Action para executar os testes da app e fazer o build, push e release da imagem docker para o Heroku.
- Caso os testes falhem o deploy nao será feito.

### Sonar Action

- Action para realizar a análise do código através da ferramenta Sonar cloud.
- Action provida pelo próprio Sonar Cloud.

### Testes

- Para executar os testes basta executar `mvn test`

### Build

- Para gerar o jar da app basta executar `mvn package`
