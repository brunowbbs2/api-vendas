# API de Vendas — Microsserviços

Guilherme de Morais China Costa - Matrícula: 44671579804

Projeto de microsserviços com Spring Boot e Spring Cloud, composto por serviços de descoberta, configuração centralizada, gateway e APIs de domínio.

## Serviços

| Serviço | Porta |
| --- | ---: |
| Eureka Server | 8761 |
| Config Server | 8888 |
| produtos-service | 8081 |
| vendas-service | 8082 |
| clientes-service | 8083 |
| fornecedores-service | 8084 |
| Gateway | 8085 |
| auth-service | 8086 |

## Execução com Docker

```bash
docker compose up --build
```

O painel do Eureka fica disponível em `http://localhost:8761`. A lista de fornecedores pode ser acessada diretamente em `http://localhost:8084/fornecedores` ou pelo Gateway em `http://localhost:8085/fornecedores-service/fornecedores`.

## Testes do fornecedores-service

```bash
cd fornecedores-service
mvn test
```
