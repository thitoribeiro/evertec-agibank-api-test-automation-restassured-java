# Evertec Agibank API Test Automation - RestAssured Java

![Java](https://img.shields.io/badge/Java-17-blue)
![RestAssured](https://img.shields.io/badge/RestAssured-5.4.0-green)
![JUnit5](https://img.shields.io/badge/JUnit-5.10.2-orange)
![Allure](https://img.shields.io/badge/Allure-2.25.0-yellow)
![Docker](https://img.shields.io/badge/Docker-enabled-blue)
![CI/CD](https://img.shields.io/badge/GitHub_Actions-active-brightgreen)

Projeto de automação de testes de API para o desafio da Evertec/Agibank, utilizando a **Dog API** (https://dog.ceo/api). O foco deste projeto é demonstrar práticas modernas de engenharia de software aplicadas a testes automatizados.

---

## 🚀 Escopo do Projeto

O projeto automatiza os principais fluxos da Dog API, incluindo:
- **Listagem de Raças:** Validação de retorno completo de todas as raças de cães.
- **Imagens Aleatórias:** Verificação de geração de imagens randômicas.
- **Imagens por Raça:** Busca de imagens específicas filtradas por raça e sub-raça.
- **Validação de Contrato:** Uso de JSON Schema para garantir a integridade das respostas.
- **Relatórios Detalhados:** Integração com Allure Report para análise visual de falhas e sucessos.

---

## 🛠️ Tecnologias e Ferramentas

- **Linguagem:** Java 17
- **Automação:** RestAssured 5.4.0
- **Framework de Teste:** JUnit Jupiter 5.10.2
- **Relatórios:** Allure Report 2.25.0
- **Serialização:** Jackson Databind 2.16.1
- **Gerenciamento de Dependências:** Maven
- **Containerização:** Docker & Docker Compose
- **CI/CD:** GitHub Actions (Full Suite & Smoke Tests)

---

## 📂 Estrutura do Projeto

```text
src/test/java/com/evertec/agibank/dogapi/
├── base/       # Configuração base e setup de especificação (BaseTest)
├── model/      # POJOs/DTOs para mapeamento de JSON (Lombok)
├── service/    # Camada de abstração das chamadas de API (DogApiService)
├── tests/      # Classes de teste organizadas por funcionalidade
└── utils/      # Helpers (SchemaValidator, AllureTestListener, TestDataFactory)

src/test/resources/
├── schemas/               # Arquivos .json para validação de contrato
├── testdata/              # Dados de teste (breeds.json)
├── allure.properties      # Configuração do diretório de resultados do Allure
├── categories.json        # Categorização de falhas no Allure
└── environment.properties # Informações do ambiente exibidas no relatório
```

---

## 💻 Como Baixar, Instalar e Executar Localmente

### Pré-requisitos
- **Java 17** ou superior instalado.
- **Maven 3.8+** instalado.
- **Git** para clonar o repositório.

### Passo 1: Clonar o Repositório
```bash
git clone https://github.com/thitoribeiro/evertec-agibank-api-test-automation-restassured-java.git
cd evertec-agibank-api-test-automation-restassured-java
```

### Passo 2: Instalar Dependências
```bash
mvn clean install -DskipTests
```

### Passo 3: Executar os Testes
Você pode rodar todos os testes via terminal:
```bash
mvn test
```

### Passo 4: Gerar e Ver o Relatório Allure
Após a execução, os resultados estarão em `target/allure-results`. Para visualizar o relatório formatado:
```bash
# Gera e abre o relatório no navegador padrão
mvn allure:serve
```

---

## 🐳 Como Executar via Docker

O projeto já possui suporte nativo ao Docker para garantir consistência entre ambientes.

### Rodar todos os testes (Suite Completa)
```bash
docker-compose up --build
```
Os resultados serão gerados na pasta `target` local, graças ao volume configurado no `docker-compose.yml`.

---

## ⚙️ Execução via GitHub Actions (CI/CD)

O projeto possui dois workflows configurados no GitHub Actions:

1.  **Dog API — Automated Tests (`api-tests.yml`):**
    - Executado automaticamente em cada `push` ou `pull_request` na branch `main`.
    - Realiza o checkout do código, instala o Java 17, faz cache das dependências do Maven, roda os testes e gera os artefatos de relatório.
    - **GitHub Pages:** O relatório Allure é publicado automaticamente no GitHub Pages do repositório após a conclusão.

2.  **Dog API — Smoke Tests (`smoke-tests.yml`):**
    - Executado periodicamente via cron (a cada 6 horas).
    - Pode ser disparado manualmente (Workflow Dispatch).
    - Roda apenas os testes anotados com `@Tag("smoke")`.

---

## 📊 Categorização de Falhas no Allure

Configuramos o Allure para agrupar erros automaticamente em categorias:
- **Falhas de Produto:** Respostas inesperadas (Asserts).
- **Falhas de Ambiente:** Erros de conexão ou timeouts.
- **Falhas de Contrato:** Incompatibilidade de JSON Schema.
- **Testes Ignorados:** Testes skipados.

---
