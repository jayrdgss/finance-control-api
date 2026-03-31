# Finance Control API

API REST de controle de gastos pessoais com autenticação JWT, isolamento multiusuário e documentação Swagger.

## Resumo

Este projeto foi construído com foco em uma base moderna para APIs Java com Spring Boot. Ele permite:

- cadastro de usuários
- login com JWT
- criação de categorias por usuário autenticado
- criação de transações por usuário autenticado
- isolamento de dados entre usuários
- validação de entrada e tratamento padronizado de erros

## Stack

- Java 21
- Spring Boot
- Spring Security
- JWT
- Spring Data JPA
- Bean Validation
- H2 Database
- Springdoc OpenAPI / Swagger

## Destaques da implementação

- autenticação stateless com JWT
- senhas protegidas com BCrypt
- categorias isoladas por usuário
- transações isoladas por usuário
- categoria validada contra o usuário autenticado ao criar transação
- responses de erro padronizadas com `error`, `message`, `timestamp` e `fields`

## Como rodar

### 1. Clonar o repositório

```bash
git clone https://github.com/jayrdgss/finance-control-api.git
cd finance-control-api
```

### 2. Configurar o JWT

No arquivo [application.properties](/home/jayane/Área%20de%20Trabalho/1000DEVS/api-controle-gastos/controle-gastos/src/main/resources/application.properties), troque:

```properties
jwt.secret=<SECRET>
```

por uma chave Base64 válida.

Exemplo:

```properties
jwt.secret=c2VncmVkb19tdWl0b19zZWd1cm9fY29tXzMyX2J5dGVzX21pbmltbw==
jwt.expiration=3600000
```

### 3. Subir a aplicação

Com Maven Wrapper:

```bash
./mvnw spring-boot:run
```

Com Maven instalado:

```bash
mvn spring-boot:run
```

## Acessos locais

- API: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- H2 Console: `http://localhost:8080/h2-console`

## Como autenticar

Rotas públicas:

- `POST /users`
- `POST /auth/login`
- `/swagger-ui/**`
- `/v3/api-docs/**`
- `/h2-console/**`

Rotas protegidas:

- `GET /categories`
- `POST /categories`
- `GET /transactions`
- `GET /transactions/user/{userId}`
- `POST /transactions`

Header esperado nas rotas protegidas:

```http
Authorization: Bearer SEU_TOKEN
```

## Endpoints

| Método | Rota | Auth | Descrição |
| --- | --- | --- | --- |
| `POST` | `/users` | Não | Cadastra um usuário |
| `POST` | `/auth/login` | Não | Autentica e retorna JWT |
| `GET` | `/categories` | Sim | Lista apenas categorias do usuário autenticado |
| `POST` | `/categories` | Sim | Cria categoria para o usuário autenticado |
| `GET` | `/transactions` | Sim | Lista apenas transações do usuário autenticado |
| `GET` | `/transactions/user/{userId}` | Sim | Lista transações do usuário autenticado validando acesso |
| `POST` | `/transactions` | Sim | Cria transação usando categoria do próprio usuário |

## Exemplos

### 1. Cadastrar usuário

`POST /users`

```json
{
  "nome": "Jayane",
  "email": "jayane@email.com",
  "senha": "123456"
}
```

### 2. Fazer login

`POST /auth/login`

```json
{
  "email": "jayane@email.com",
  "senha": "123456"
}
```

Resposta:

```json
{
  "mensagem": "Login realizado com sucesso.",
  "token": "eyJ..."
}
```

### 3. Criar categoria autenticada

`POST /categories`

```json
{
  "nome": "Salario",
  "tipo": "RECEITA"
}
```

### 4. Criar transação autenticada

`POST /transactions`

```json
{
  "descricao": "Salario de marco",
  "valor": 2500.00,
  "tipo": "RECEITA",
  "data": "2026-03-30T21:00:00",
  "categoryId": 1
}
```

### 5. Listar dados do usuário autenticado

- `GET /categories`
- `GET /transactions`

## Regras de negócio atuais

- cada categoria pertence a um usuário
- cada transação pertence a um usuário
- um usuário não pode acessar categorias de outro
- um usuário não pode acessar transações de outro
- uma transação só pode usar categoria do próprio usuário autenticado
- o tipo da transação deve ser compatível com o tipo da categoria

## Exemplos de erro

### 400 Bad Request

```json
{
  "error": "MethodArgumentNotValidException",
  "message": "Erro de validacao nos campos informados.",
  "timestamp": "2026-03-30T22:00:00",
  "fields": [
    {
      "field": "email",
      "message": "O email deve ser valido."
    }
  ]
}
```

### 401 Unauthorized

```json
{
  "timestamp": "2026-03-30T22:00:00",
  "status": 401,
  "error": "Unauthorized",
  "path": "/transactions"
}
```

### 403 Forbidden

```json
{
  "error": "AccessDeniedException",
  "message": "Voce nao tem permissao para acessar os dados de outro usuario.",
  "timestamp": "2026-03-30T22:00:00",
  "fields": null
}
```

## Estrutura do projeto

```text
src/main/java/controle_gastos
├── config
├── controller
├── dto
├── entity
├── enums
├── exception
├── repository
├── security
└── service
```

## Melhorias futuras

- update e delete para categorias e transações
- filtros por período, tipo e categoria
- testes unitários e de integração
- uso de variáveis de ambiente para segredos
- banco relacional para produção
- migrations com Flyway ou Liquibase
