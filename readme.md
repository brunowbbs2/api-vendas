# Aluno: José Marco Ribeiro de Menezes

# API de Vendas

Sistema de vendas baseado em microsservicos Spring Boot. O projeto separa
autenticacao, cadastro de clientes, produtos e vendas, usando um API Gateway
como porta de entrada.

## Arquitetura da solucao

```text
										+----------------+
										| Cliente / curl |
										+-------+--------+
														|
														v
										+---------------+
										| Gateway :8085 |
										| JWT Filter    |
										+-------+-------+
														|
	   +-----------------+--------------+---------------+
	   |				 |              |               |
	   v				 v              v               v
Fornecedor-service		auth-service  produtos-service  clientes-service
	   :8086			:8084           :8081          :8083
	   |				 |              |
	   +-----------------+--------------v---------------+
										vendas-service :8082

			eureka-server :8761  -> descoberta de servicos
			config-server :8888  -> configuracoes centralizadas
```

Os servicos se registram no Eureka e recebem configuracoes do Config Server.
Em uso normal, as requisicoes externas passam pelo Gateway. O Gateway remove
o prefixo do nome do servico ao encaminhar a requisicao.

## Tecnologia de autenticacao

Foi escolhido **JWT (JSON Web Token)**, e nao Keycloak.

- O `auth-service` autentica o usuario e assina o token com uma chave HMAC.
- O token possui o e-mail do usuario no `subject`.
- O Gateway valida a assinatura do token no header `Authorization` antes de
	encaminhar requisicoes protegidas.
- O login e o refresh retornam o token no corpo e no header `Authorization`.
- A chave JWT deve ser a mesma no `auth-service`, no Gateway e nas
	configuracoes centralizadas.

## Como executar com Docker Compose

Requisitos: Docker e Docker Compose.

Na raiz do projeto:

```bash
docker compose up --build
```

Depois que os servicos estiverem registrados no Eureka, o Gateway estara
disponivel em `http://localhost:8085`.

Para parar os containers:

```bash
docker compose down
```

Portas principais:

| Componente | Porta |
|---|---:|
| Eureka Server | 8761 |
| Config Server | 8888 |
| Produtos | 8081 |
| Vendas | 8082 |
| Clientes | 8083 |
| Auth | 8084 |
| Gateway | 8085 |

## Como executar localmente

Cada servico possui seu proprio `pom.xml`. O Java 17 ou superior e Maven sao
necessarios. Inicie nesta ordem, em terminais separados:

1. `eureka-server`
2. `config-server`
3. `produtos-service`, `vendas-service`, `clientes-service` e `auth-service`
4. `gateway`

Exemplo:

```bash
cd eureka-server && mvn spring-boot:run
cd config-server && mvn spring-boot:run
cd auth-service && mvn spring-boot:run
cd gateway && mvn spring-boot:run
```

Inicie tambem os demais servicos antes de testar seus endpoints. O acesso
recomendado continua sendo pelo Gateway em `http://localhost:8085`.

## Como realizar autenticacao

### 1. Cadastrar usuario

```bash
curl -i -X POST http://localhost:8085/auth-service/auth \
	-H "Content-Type: application/json" \
	-d '{"nome":"Maria Silva","email":"maria@example.com","senha":"senha123"}'
```

### 2. Fazer login

```bash
curl -i -X POST http://localhost:8085/auth-service/auth/login \
	-H "Content-Type: application/json" \
	-d '{"email":"maria@example.com","senha":"senha123"}'
```

A resposta contem o JWT no corpo e tambem em:

```text
Authorization: Bearer <token>
```

Guarde o valor do token para as requisicoes protegidas.

## Endpoint de refresh

O refresh valida o token atual e gera um novo JWT. Envie o token no header:

```bash
curl -i -X POST http://localhost:8085/auth-service/auth/refresh \
	-H "Authorization: Bearer <token-atual>"
```

O novo token sera devolvido no corpo e no header `Authorization`. Embora a
rota seja liberada pelo filtro do Gateway para permitir a renovacao, o
controller exige um header `Authorization` valido. Sem ele, a requisicao nao
e autenticada.

## Endpoints publicos

No Gateway, estes endpoints nao passam pela validacao inicial do filtro JWT:

| Metodo | Endpoint | Observacao |
|---|---|---|
| `POST` | `/auth-service/auth` | Cadastro de usuario |
| `POST` | `/auth-service/auth/login` | Gera o primeiro token |
| `POST` | `/auth-service/auth/refresh` | Exige token no header para renovar |

O acesso direto ao `auth-service` usa os mesmos caminhos sem o prefixo
`/auth-service`, por exemplo `http://localhost:8084/auth/login`.

## Endpoints protegidos

Todos os demais endpoints publicados pelo Gateway exigem:

```text
Authorization: Bearer <token>
```

Endpoints disponiveis atualmente:

| Metodo | Endpoint | Servico |
|---|---|---|
| `GET` | `/produtos-service/produtos` | Lista produtos |
| `GET` | `/produtos-service/produtos/{id}` | Busca produto por ID |
| `GET` | `/clientes-service/clientes` | Lista clientes |
| `GET` | `/vendas-service/vendas` | Consulta vendas |
| `POST` | `/vendas-service/vendas` | Cria uma venda |

## Exemplos de requisicoes protegidas

Listar produtos:

```bash
curl -i http://localhost:8085/produtos-service/produtos \
	-H "Authorization: Bearer <token>"
```

Buscar um produto:

```bash
curl -i http://localhost:8085/produtos-service/produtos/1 \
	-H "Authorization: Bearer <token>"
```

Listar clientes:

```bash
curl -i http://localhost:8085/clientes-service/clientes \
	-H "Authorization: Bearer <token>"
```

Criar uma venda:

```bash
curl -i -X POST http://localhost:8085/vendas-service/vendas \
	-H "Content-Type: application/json" \
	-H "Authorization: Bearer <token>" \
	-d '{"idProduto":1,"quantidade":2}'
```

Uma requisicao protegida sem token, com token expirado ou com assinatura
invalida retorna `401 Unauthorized` no Gateway.

## Como executar no Kubernetes

Com Docker, `kubectl` e Kind instalados:

```bash
kind create cluster --name ecommerce

for s in eureka-server config-server produtos-service vendas-service clientes-service auth-service gateway; do
	docker build -t $s:1.0 ./$s
	kind load docker-image $s:1.0 --name ecommerce
done

kubectl apply -f k8s/
kubectl get pods -n ecommerce -w
```

Para acessar o Gateway localmente:

```bash
kubectl port-forward -n ecommerce svc/gateway 8085:8085
```

Os exemplos deste README funcionarao usando `http://localhost:8085` apos o
port-forward.

