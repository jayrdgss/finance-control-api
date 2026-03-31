# Finance Control API

API REST para controle de gastos pessoais, construída com Java, Spring Boot, Spring Security, JWT e JPA.

## Tecnologias

- Java 21
- Spring Boot
- Spring Security
- JWT
- Spring Data JPA
- H2 Database
- Springdoc OpenAPI / Swagger

## Funcionalidades atuais

- cadastro de usuário
- login com JWT
- categorias por usuário autenticado
- transações por usuário autenticado
- validação com Bean Validation
- tratamento global de erros
- documentação com Swagger

## Como rodar o projeto

### 1. Clonar o repositório

```bash
git clone https://github.com/jayrdgss/finance-control-api.git
cd finance-control-api
```

### 2. Configurar o JWT

No arquivo [application.properties](/home/jayane/Área%20de%20Trabalho/1000DEVS/api-controle-gastos/controle-gastos/src/main/resources/application.properties), substitua:

```properties
jwt.secret=<SECRET>
```

por uma chave Base64 válida com pelo menos 32 bytes.

Exemplo:

```properties
jwt.secret=c2VncmVkb19tdWl0b19zZWd1cm9fY29tXzMyX2J5dGVzX21pbmltbw==
jwt.expiration=3600000
```

### 3. Executar a aplicação

Com Maven Wrapper:

```bash
./mvnw spring-boot:run
```

Ou com Maven instalado:

```bash
mvn spring-boot:run
```

## Acesso local

- API: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- H2 Console: `http://localhost:8080/h2-console`

## Autenticação

Rotas públicas:

- `POST /users`
- `POST /auth/login`
- Swagger
- H2 Console

Rotas protegidas:

- `GET /categories`
- `POST /categories`
- `GET /transactions`
- `GET /transactions/user/{userId}`
- `POST /transactions`

Após fazer login, envie o token no header:

```http
Authorization: Bearer SEU_TOKEN
```

## Endpoints

| Método | Rota | Autenticação | Descrição |
| --- | --- | --- | --- |
| `POST` | `/users` | Não | Cadastra um novo usuário |
| `POST` | `/auth/login` | Não | Realiza login e retorna token JWT |
| `GET` | `/categories` | Sim | Lista apenas as categorias do usuário autenticado |
| `POST` | `/categories` | Sim | Cria uma categoria para o usuário autenticado |
| `GET` | `/transactions` | Sim | Lista apenas as transações do usuário autenticado |
| `GET` | `/transactions/user/{userId}` | Sim | Lista transações do usuário autenticado, validando acesso |
| `POST` | `/transactions` | Sim | Cria uma transação para o usuário autenticado |

## Fluxo recomendado de uso

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

Resposta esperada:

```json
{
  "mensagem": "Login realizado com sucesso.",
  "token": "eyJ..."
}
```

### 3. Criar categoria

`POST /categories`

```json
{
  "nome": "Salario",
  "tipo": "RECEITA"
}
```

### 4. Listar categorias do usuário autenticado

`GET /categories`

### 5. Criar transação

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

### 6. Listar transações do usuário autenticado

`GET /transactions`

## Regras importantes

- cada categoria pertence a um usuário
- cada transação pertence a um usuário
- uma transação só pode usar categoria do próprio usuário autenticado
- o tipo da transação deve ser compatível com o tipo da categoria

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

## Próximos passos sugeridos

- adicionar update e delete
- adicionar filtros por período, tipo e categoria
- criar testes unitários e de integração
- mover segredos para variáveis de ambiente
- configurar banco relacional para produção
