# Zela API


---
Backend da plataforma Zela, uma solução de zeladoria colaborativa que permite aos moradores registrar, visualizar e acompanhar problemas encontrados nos espaços públicos de seus bairros.

A API do Zela é responsável por gerenciar os dados da aplicação, permitindo o cadastro de usuários, a organização das categorias de problemas urbanos e o registro de ocorrências geolocalizadas, além de possibilitar a interação entre moradores por meio de comentários.
## Ficha da CitizenTech

| Campo | Resposta |
|---|---|
| **Nome da CitizenTech** | Zela |
| **Cidadão beneficiado** | Moradores que convivem com problemas no espaço público e não têm um canal simples para registrá-los e acompanhá-los.|
| **Problema, em uma frase** | Problemas urbanos do bairro ficam sem registro e sem acompanhamento por falta de um canal simples para reportá-los. |
| **Como funciona** | O morador marca no mapa do app o local e a categoria do problema; a API guarda os pontos e os comentários no Oracle e permite acompanhar o status (resolvido ou não). |


## Equipe

| Nome              | RM |
|-------------------|---|
| Erik K. Suguiyama | 559273 |
| Guilherme Vital   | 560073 |
| Kayque Carvalho   | 561189 |
 
---

## Tecnologias

- Java 21 e Spring Boot 4.1.2 (Web MVC, Data JPA, Validation)
- Banco de dados **Oracle** (Oracle 19c da FIAP, acessado com o SQL Developer)
- **Flyway** para migrações versionadas (`spring-boot-starter-flyway` + `flyway-database-oracle`)
- BCrypt (`spring-security-crypto`) para o hash de senha
- Lombok
- Insomnia para os testes manuais
## Arquitetura

```
br.com.zela.api
  controller/   endpoints REST
  service/      regras de negócio
  repository/   acesso a dados (Spring Data JPA)
  domain/       entidades JPA
  dto/          objetos de entrada e saída, com validação
  exception/    erros de negócio e tratador global
  config/       configuração (CORS)
```

Fluxo de uma requisição: `controller → service → repository → Oracle`.

- Os controllers só recebem e devolvem DTOs.
- As regras de negócio ficam nos services.
- As entidades nunca são expostas diretamente, então o hash da senha não sai da API.
## Modelo de dados

| Tabela | Campos principais |
|---|---|
| `USUARIO` | id, username (único), senha_hash |
| `CATEGORIA` | id, codigo (único), nome, descricao, prioridade |
| `PONTO` | id, categoria_id, usuario_id, latitude, longitude, descricao, resolvido, criado_em |
| `COMENTARIO` | id, ponto_id, usuario_id, texto, criado_em |

Relações:

- um usuário cria vários pontos e comentários;
- uma categoria tem vários pontos;
- um ponto tem vários comentários, removidos em cascata junto com o ponto.
## Migrações (Flyway)

Em `src/main/resources/db/migration/`:

| Versão | O que faz |
|---|---|
| `V1__criar_tabelas.sql` | Cria as quatro tabelas, chaves e restrições |
| `V2__inserir_categorias.sql` | Insere as 7 categorias usadas no app |
| `V3__criar_indices.sql` | Índices em `PONTO(CATEGORIA_ID)` e `COMENTARIO(PONTO_ID)` |

O Flyway executa as migrações na subida da aplicação e registra o histórico na tabela `flyway_schema_history`. O Hibernate não altera o esquema (`ddl-auto=none`), então o banco só muda por meio das migrações versionadas.

## Como rodar

**Pré-requisitos:** JDK 21+, Maven (ou o Maven do IntelliJ) e acesso ao banco Oracle da FIAP.

1. Clone o repositório.
2. Defina as variáveis de ambiente:
```
DB_URL=URL_DO_AMBIENTE
DB_USER=seu_rm
DB_PASSWORD=sua_senha
```

Conexão por **service name** (alternativa):

```
DB_HOST=...
DB_PORT=1521
DB_SERVICE=...
DB_USER=...
DB_PASSWORD=...
```

No IntelliJ: **Run → Edit Configurations → Modify options → Environment variables**.

3. Execute a aplicação:
```bash
mvn spring-boot:run
```

Ou rode a classe `ApiApplication` pelo IntelliJ.

A API sobe em `http://localhost:8080/api`, e as migrações rodam automaticamente na primeira execução.

**Atenção:** o schema precisa estar livre de tabelas com os mesmos nomes (`USUARIO`, `CATEGORIA`, `PONTO`, `COMENTARIO`).

## Endpoints

| Método | Rota | Descrição | Status |
|---|---|---|---|
| POST | `/api/auth/cadastro` | Cria usuário (senha com BCrypt) | 201, 400, 409 |
| POST | `/api/auth/login` | Valida as credenciais | 200, 401 |
| GET | `/api/categorias` | Lista as categorias | 200 |
| GET | `/api/pontos` | Lista os pontos | 200 |
| POST | `/api/pontos` | Cria um ponto | 201, 400 |
| GET | `/api/pontos/{id}` | Detalha um ponto | 200, 404 |
| PATCH | `/api/pontos/{id}/resolvido` | Marca como resolvido | 200, 404 |
| GET | `/api/pontos/{id}/comentarios` | Lista os comentários | 200, 404 |
| POST | `/api/pontos/{id}/comentarios` | Cria um comentário | 201, 400, 404 |

**Exemplo: criar um ponto**

```json
POST /api/pontos
{
  "usuarioId": 1,
  "categoria": "alagamentos",
  "latitude": -23.5329,
  "longitude": -46.7917,
  "descricao": "Bueiro entupido na esquina, a rua alaga quando chove."
}
```

**Códigos de erro**

- `400`: dados inválidos (retorna os campos com problema) ou categoria/usuário inexistente
- `401`: usuário ou senha incorretos
- `404`: recurso não encontrado
- `409`: usuário já cadastrado
## Testes manuais (Insomnia)
Todos os teste manuais estão no EVIDENCIAS.pdf

## Segurança

- Senhas guardadas com **hash BCrypt**, nunca em texto.
- Acesso ao banco por **JPA/Spring Data**, com consultas parametrizadas, o que mitiga SQL Injection.
- Entradas validadas com Bean Validation e respostas por DTO, sem expor dados internos.
- Credenciais do banco por variáveis de ambiente, fora do repositório.
  **Limitações:**

- A API ainda não emite token de sessão: o login apenas valida as credenciais.
- Em produção, seria necessário JWT, controle de acesso por usuário e HTTPS.
## Limitações e próximos passos

- Autenticação com token (JWT) e autorização por usuário.
- Integração do app mobile com a API (hoje o app guarda os dados localmente).
- Endpoint para receber leituras do sensor IoT de nível de água.
- Filtros e paginação na listagem de pontos.
 