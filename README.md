# ObservaAção — Ouvidoria Cidadã Digital

> Sistema de ouvidoria cidadã desenvolvido em Java 17 com Spring Boot, expondo uma API REST.

**AEP — Análise e Desenvolvimento de Sistemas · 1º Semestre 2026**  
Disciplinas: Interação Humano-Computador · Programação Orientada a Objetos · Manutenção de Software  
ODS relacionados: [ODS 10](https://brasil.un.org/pt-br/sdgs/10) · [ODS 11](https://brasil.un.org/pt-br/sdgs/11) · [ODS 16](https://brasil.un.org/pt-br/sdgs/16)

---

## Sumário

- [Contexto e Problema](#contexto-e-problema)
- [O que é o ObservaAção](#o-que-é-o-observaação)
- [Funcionalidades](#funcionalidades)
- [Regras de Negócio](#regras-de-negócio)
- [Arquitetura e Organização](#arquitetura-e-organização)
- [Princípios Aplicados](#princípios-aplicados)
- [Stack Tecnológica](#stack-tecnológica)
- [Como Executar](#como-executar)
- [API Endpoints](#api-endpoints)
- [Estrutura de Arquivos](#estrutura-de-arquivos)
- [Frontend](#frontend)
- [Entregas](#entregas)

---

## Contexto e Problema

Em muitas cidades brasileiras, a população enfrenta barreiras significativas para interagir com o poder público:

- Dificuldade em entender **como solicitar serviços** (iluminação, buracos, saúde, zeladoria)
- **Falta de retorno** após protocolos registrados — reclamações que "somem"
- Ausência de **transparência sobre prazos e prioridades**
- **Receio de se identificar** em denúncias, especialmente em áreas de vulnerabilidade

Esse cenário enfraquece a confiança nas instituições, amplia desigualdades sociais — quem tem "contatos" resolve mais rápido — e gera sensação de abandono.

---

## O que é o ObservaAção

O **ObservaAção** é um sistema de ouvidoria cidadã digital desenvolvido para uma govtech fictícia homônima que venceu um edital municipal. O sistema permite que:

- **Cidadãos** registrem solicitações de serviços públicos de forma identificada ou anônima, acompanhem o andamento por protocolo e visualizem prazos e histórico de atendimento
- **Atendentes e gestores** tratem as demandas com rastreabilidade, organização por prioridade e responsabilidade documentada em cada movimentação

O objetivo não é "fazer um app bonito", mas reduzir barreiras, aumentar transparência e melhorar a capacidade de resposta do poder público — com alinhamento direto aos ODS 10, 11 e 16 da ONU.

---

## Funcionalidades

### Cidadão

| Funcionalidade | Detalhe |
|---|---|
| Registrar solicitação identificada | Com nome, e-mail, telefone (opcional), categoria, prioridade, localização, descrição e anexo |
| Registrar solicitação anônima | Sem dados pessoais; exige descrição detalhada e motivo do anonimato |
| Consultar por protocolo | Exibe status atual, histórico completo de movimentações e prazo SLA |

### Atendente / Gestor

| Funcionalidade | Detalhe |
|---|---|
| Listar todas as solicitações | Visão resumida com protocolo, categoria, prioridade, status e indicador de prazo |
| Filtrar demandas | Por status, categoria ou prioridade |
| Atualizar status | Com comentário obrigatório e registro do responsável |
| Ver detalhes completos | Histórico cronológico de todas as movimentações |

---

## Regras de Negócio

| Regra | Detalhe |
|---|---|
| **Protocolo** | Gerado automaticamente no formato `OBS-AAAA-NNNNN` (ex.: `OBS-2026-00001`) |
| **Fluxo de status** | `ABERTO → TRIAGEM → EM_EXECUCAO → RESOLVIDO → ENCERRADO` — progressão linear, sem retrocesso |
| **SLA Verde** | Prioridade baixa — prazo de 7 dias |
| **SLA Amarelo** | Prioridade média — prazo de 3 dias |
| **SLA Vermelho** | Prioridade alta — prazo de 1 dia |
| **Solicitação anônima** | Exige mínimo de 20 caracteres na descrição e motivo do anonimato; nenhum dado pessoal é armazenado |
| **Solicitação identificada** | Exige nome completo (mín. 3 chars), e-mail válido; telefone é opcional |
| **Atualização de status** | Comentário e identificação do responsável são obrigatórios em toda movimentação |
| **Histórico imutável** | Cada movimentação registra status anterior, status novo, comentário, responsável e data/hora |

---

## Arquitetura e Organização

O sistema segue uma **arquitetura em camadas** com Spring Boot, garantindo separação de responsabilidades e baixo acoplamento:

```
┌─────────────────────────────────────────────┐
│         Controllers (REST API)              │  ← Apresentação: recebe HTTP, devolve JSON
│   CidadaoController │ GestorController      │
├─────────────────────────────────────────────┤
│              SolicitacaoService             │  ← Regras de negócio: validação, SLA, protocolo
├─────────────────────────────────────────────┤
│          SolicitacaoRepository (interface)  │  ← Contrato de acesso a dados
│       SolicitacaoRepositoryEmMemoria        │  ← Implementação: ConcurrentHashMap em memória
├─────────────────────────────────────────────┤
│         Domain: Solicitacao (abstrata)      │
│   SolicitacaoIdentificada │ SolicitacaoAnonima │  ← Modelo de domínio
│   Movimentacao │ Status │ Categoria │ Prioridade │
└─────────────────────────────────────────────┘
```

**Hierarquia de classes (POO):**

```
Solicitacao (abstrata)
├── SolicitacaoIdentificada   → adiciona: nomeCompleto, email, telefone
└── SolicitacaoAnonima        → adiciona: motivoAnonimato
```

Cada `Solicitacao` mantém uma lista imutável de `Movimentacao`, registrando o histórico completo de transições de status.

---

## Princípios Aplicados

### SOLID

| Princípio | Onde foi aplicado |
|---|---|
| **SRP** — Responsabilidade Única | `Validador` só valida; `GeradorProtocolo` só gera protocolos; controllers só orquestram HTTP |
| **OCP** — Aberto/Fechado | `Status`: cada constante do enum encapsula sua própria regra de transição — adicionar um novo status não exige modificar código existente |
| **LSP** — Substituição de Liskov | `isAnonima()` abstrato em `Solicitacao`; `SolicitacaoIdentificada` retorna `false`, `SolicitacaoAnonima` retorna `true` — subclasses substituíveis sem surpresas |
| **ISP** — Segregação de Interfaces | `SolicitacaoRepository` expõe apenas os métodos necessários, sem sobrecarregar os consumidores |
| **DIP** — Inversão de Dependência | `SolicitacaoService` depende da interface `SolicitacaoRepository`; injeção via construtor em todas as camadas |

### Clean Code

| Prática | Onde foi aplicado |
|---|---|
| Nomes que revelam intenção | DTOs com nomes completos: `SolicitacaoIdentificadaRequest`, `SolicitacaoDetalhadaResponse` |
| Sem comentários redundantes | Nomes de métodos e classes comunicam a intenção sem comentários auxiliares |
| Sem magic numbers | Constantes nomeadas para SLAs (`DIAS_SLA_BAIXA = 7`) e limites de validação |
| Funções fazem uma coisa | Cada método do service executa uma responsabilidade isolada |
| DRY | `filtrar(Predicate<Solicitacao>)` elimina triplicação dos métodos de filtro no repositório |

---

## Stack Tecnológica

| Camada | Tecnologia | Justificativa |
|---|---|---|
| Linguagem | Java 17 | LTS estável, records, sealed classes |
| Framework | Spring Boot 3 | Convenção sobre configuração, ecossistema maduro |
| API | Spring Web (REST) | Exposição via HTTP/JSON |
| Persistência | H2 Database (arquivo) + Spring Data JPA | Dados persistentes entre reinicializações; schema gerado automaticamente |
| Build | Maven (Maven Wrapper) | Reprodutibilidade sem instalação prévia |
| Controle de versão | Git + GitHub | — |

---

## Como Executar

### Pré-requisito

- **JDK 17** instalado ([download Adoptium](https://adoptium.net/))
- `java` disponível no PATH (Maven Wrapper baixa o Maven automaticamente)

Para verificar:
```bash
java -version
```

---

### Windows

```bat
mvnw.cmd spring-boot:run
```

### Linux / macOS

```bash
./mvnw spring-boot:run
```

A aplicação sobe na porta **8080**.

---

### Passo a passo completo

```bash
# 1. Clone o repositório
git clone https://github.com/Leocm123/aep-observaacao.git
cd aep-observaacao

# 2. Suba a aplicação
./mvnw spring-boot:run        # Linux/macOS
mvnw.cmd spring-boot:run      # Windows
```

Acesse `http://localhost:8080/` para abrir o frontend

> **Console H2** (visualizar o banco): `http://localhost:8080/h2-console`  
> JDBC URL: `jdbc:h2:file:./data/observaacao` · Usuário: `sa` · Senha: _(vazia)_, ou `http://localhost:8080/api/gestor/solicitacoes` para confirmar que a API está no ar.

---

## API Endpoints

### Cidadão — `/api/cidadao`

| Método | Endpoint | Body | Resposta |
|--------|----------|------|----------|
| `POST` | `/api/cidadao/solicitacoes/identificada` | `SolicitacaoIdentificadaRequest` | `SolicitacaoDetalhadaResponse` — 201 |
| `POST` | `/api/cidadao/solicitacoes/anonima` | `SolicitacaoAnonimaRequest` | `SolicitacaoDetalhadaResponse` — 201 |
| `GET` | `/api/cidadao/solicitacoes/{protocolo}` | — | `SolicitacaoDetalhadaResponse` — 200 ou 404 |

### Gestor — `/api/gestor`

| Método | Endpoint | Params | Resposta |
|--------|----------|--------|----------|
| `GET` | `/api/gestor/solicitacoes` | `?status=`, `?categoria=`, `?prioridade=` (opcionais) | `List<SolicitacaoResumoResponse>` — 200 |
| `GET` | `/api/gestor/solicitacoes/{protocolo}` | — | `SolicitacaoDetalhadaResponse` — 200 ou 404 |
| `PATCH` | `/api/gestor/solicitacoes/{protocolo}/status` | — | `SolicitacaoDetalhadaResponse` — 200, 404 ou 422 |

### Códigos de resposta

| Código | Situação |
|--------|----------|
| 201 | Solicitação criada com sucesso |
| 200 | Consulta ou atualização bem-sucedida |
| 400 | Dados de entrada inválidos (`ValidacaoException`) |
| 404 | Protocolo não encontrado (`SolicitacaoNaoEncontradaException`) |
| 422 | Transição de status inválida (`TransicaoStatusInvalidaException`) |

---

## Estrutura de Arquivos

```
aep-observaacao/
├── src/
│   └── main/
│       ├── java/
│       │   └── br/com/observaacao/
│       │       ├── ObservacaoApplication.java         # Ponto de entrada Spring Boot
│       │       ├── controller/
│       │       │   ├── CidadaoController.java         # Endpoints do cidadão
│       │       │   └── GestorController.java          # Endpoints do gestor
│       │       ├── service/
│       │       │   └── SolicitacaoService.java        # Lógica de negócio central
│       │       ├── repository/
│       │       │   ├── SolicitacaoRepository.java     # Interface de acesso a dados (DIP)
│       │       │   └── SolicitacaoRepositoryEmMemoria.java  # Implementação ConcurrentHashMap
│       │       ├── domain/
│       │       │   ├── model/
│       │       │   │   ├── Solicitacao.java           # Classe abstrata base
│       │       │   │   ├── SolicitacaoIdentificada.java  # Com dados do cidadão
│       │       │   │   ├── SolicitacaoAnonima.java    # Sem dados pessoais
│       │       │   │   └── Movimentacao.java          # Registro imutável de histórico
│       │       │   └── enums/
│       │       │       ├── Status.java                # Fluxo de estados com OCP
│       │       │       ├── Categoria.java             # Tipos de problema urbano
│       │       │       └── Prioridade.java            # Níveis de SLA
│       │       ├── dto/
│       │       │   ├── SolicitacaoIdentificadaRequest.java
│       │       │   ├── SolicitacaoAnonimaRequest.java
│       │       │   ├── AtualizacaoStatusRequest.java
│       │       │   ├── SolicitacaoResumoResponse.java
│       │       │   ├── SolicitacaoDetalhadaResponse.java
│       │       │   └── MovimentacaoResponse.java
│       │       ├── exception/
│       │       │   ├── GlobalExceptionHandler.java    # @RestControllerAdvice centralizado
│       │       │   ├── SolicitacaoNaoEncontradaException.java
│       │       │   ├── TransicaoStatusInvalidaException.java
│       │       │   └── ValidacaoException.java
│       │       └── util/
│       │           ├── GeradorProtocolo.java          # Formato OBS-AAAA-NNNNN
│       │           └── Validador.java                 # Validações de entrada
│       └── resources/
│           ├── static/
│           │   └── index.html                        # Frontend — SPA completo
│           └── application.properties
├── docs/
│   ├── perfil-persona-aep.pdf                        # Perfis e personas (entrega IHC)
│   └── relatorio_clean_code.pdf                      # Relatório Clean Code
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

---


## Frontend

O sistema conta com um frontend completo em HTML/CSS/JS puro, sem dependências externas,
servido diretamente pelo Spring Boot como recurso estático.

### Como acessar

1. Suba a aplicação: `./mvnw spring-boot:run`
2. Acesse: `http://localhost:8080/`

### Telas disponíveis

| Tela | Rota | API |
|------|------|-----|
| Seleção de Perfil | `/` | — |
| Registrar Solicitação | Cidadão | `POST /api/cidadao/solicitacoes/{identificada\|anonima}` |
| Confirmação de Registro | Cidadão | Response 201 |
| Consultar por Protocolo | Cidadão | `GET /api/cidadao/solicitacoes/{protocolo}` |
| Lista de Solicitações | Atendente | `GET /api/gestor/solicitacoes` |
| Detalhe + Atualizar Status | Atendente | `GET` + `PATCH /api/gestor/solicitacoes/{protocolo}/status` |

---

## Entregas

### 1º Bimestre ✅

- ✅ Versão Beta funcional em CLI (Java 17, sem framework)
- ✅ Perfis e personas (IHC) — `docs/perfil-persona-aep.pdf`
- ✅ Relatório Clean Code (3 funções) — `docs/relatorio_clean_code.pdf`

### 2º Bimestre

- ✅ Migração para Spring Boot (controller / service / repository / DTOs)
- ✅ Wireframes das telas essenciais (IHC) — 6 telas mapeadas aos endpoints da API
- ✅ Frontend completo integrado — `src/main/resources/static/index.html`
- ✅ Persistência em banco H2 (arquivo) — dados sobrevivem ao restart
- ✅ Relatório de métricas (SonarCloud)
