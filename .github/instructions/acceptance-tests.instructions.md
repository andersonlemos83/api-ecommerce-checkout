# Instruções - Testes de Aceitação (Cucumber BDD)

applyTo: ["infrastructure/src/test/**/*.java", "infrastructure/src/test/resources/features/**/*.feature", "alternative-infrastructure/src/test/**/*.java", "alternative-infrastructure/src/test/resources/features/**/*.feature", "core/src/test/**/*.java"]

## 📋 Visão Geral

Os testes de aceitação do projeto utilizam **Cucumber** com **BDD (Behavior-Driven Development)** em linguagem **Gherkin (Português)**. Testam o fluxo completo de orquestração de vendas, desde autorização até processamento e persistência.

- **Localização**: `src/test/java/.../cucumber/` e `src/test/resources/features/`
- **Suite de Execução**: `RunCucumberTest.java`
- **Profile**: `test`
- **Ferramentas**: Spring Boot Test, Testcontainers, WireMock, Cucumber

### **Quando é Ativada**
✅ Editando **StepDefs** (dado, quando, então)  
✅ Criando/Editando **DataTables** (mapeamento de dados)  
✅ Criando/Editando **Verifiers** (asserções)  
✅ Criando/Editando **Contexts** (gerenciamento de BD)  
✅ Modificando **Feature files** (.feature)  
✅ Editando **application-test.yml** (configuração de testes)  
✅ Trabalhando em **RunCucumberTest** ou **SpringContextStepDefs**  

### **Quando NÃO é Ativada**
❌ Arquivos de produção (`src/main/java/`)  
❌ Configurações Maven (POM.xml)  
❌ Documentação geral do projeto  
❌ Testes de unidade do Core (quando não relacionados a fixtures BDD)

## 🏷️ Tags Cucumber (@apply-to)

```
src/test/java/br/com/alc/ecommerce/checkout/infrastructure/cucumber/
├── RunCucumberTest.java              # Suite de execução
├── SpringContextStepDefs.java        # Configuração do contexto Spring + Data Transformers
├── stepdefs/
│   ├── StepDefs.java                 # Classe base com utilidades compartilhadas
│   ├── authorizesale/
│   │   └── AuthorizeSaleStepDefs.java
│   ├── processsaleauthorization/
│   │   └── ProcessSaleAuthorizationStepsDefs.java
│   ├── sale/
│   │   ├── SaleRequestStepDefs.java
│   │   ├── SaleResponseStepDefs.java
│   │   └── ...
│   ├── error/
│   │   └── ErrorResponseStepDefs.java
│   ├── messaging/
│   │   └── MessagingStepDefs.java
│   ├── externalservices/
│   │   └── ExternalServicesStepDefs.java
│   └── saleorder/
│       └── SaleOrderStepDefs.java
├── datatable/
│   ├── sale/
│   │   ├── SaleRequestDataTable.java
│   │   ├── CustomerDataTable.java
│   │   ├── ShoppingCartItemDataTable.java
│   │   └── PaymentDataTable.java
│   ├── order/
│   │   └── SaleOrderDataTable.java
│   ├── error/
│   │   └── ErrorResponseDataTable.java
│   ├── messaging/
│   │   └── MessagingDataTable.java
│   ├── externalservices/
│   │   ├── JsonRequestDataTable.java
│   │   └── JsonResponseDataTable.java
│   └── support/
│       └── TransitionDataTable.java
├── context/
│   └── SaleOrderContext.java          # Gerencia dados persistidos em BD
├── verifier/
│   ├── SaleResponseVerifier.java
│   ├── SaleOrderVerifier.java
│   ├── ErrorResponseVerifier.java
│   ├── MessagingVerifier.java
│   └── MidClientVerifier.java
├── feature/
│   ├── AuthorizeSaleFeature.java
│   └── ProcessSaleAuthorizationFeature.java
└── configurator/
    └── EnvironmentConfigurator.java  # Setup inicial de ambiente

src/test/resources/
├── features/
│   ├── authorize-sale.feature         # 5 cenários
│   └── process-sale-authorization.feature  # 11 cenários
├── fixtures/                          # JSONs mock
├── schema.sql                         # DDL para testes
├── application-test.yml              # Configuração teste
└── cucumber.properties               # Configuração Cucumber
```

## 🎭 Features e Cenários

### 1. **authorize-sale.feature** (5 cenários)

Responsável por testar a autorização inicial de vendas via API REST.

| # | Cenário | Tipo | Validação |
|---|---------|------|-----------|
| 01 | Todos dados válidos | ✅ Happy Path | Response 202 + Mensagem RabbitMQ |
| 02 | Todos dados inválidos | ❌ Fluxo Excepcional | Response 400 + Validações |
| 03 | Customer inválido | ❌ Fluxo Excepcional | Response 400 + Validações aninhadas |
| 04 | Shopping Cart inválido | ❌ Fluxo Excepcional | Response 400 + Validações de array |
| 05 | Payment inválido | ❌ Fluxo Excepcional | Response 400 + Validações de array |

**Exemplo de Cenário (Gherkin)**:
```gherkin
Cenario: 01 - Autorizar venda com todos os dados validos - Sucesso
  Dado que seja informado os dados de Sale Request
    | Channel Code | Company Code | ... |
    | APP          | 001          | ... |
  E que seja informado os dados de Customer
    | Name | Document | ... |
  Quando autorizar venda
  Entao deveria receber os dados de Sale Response
    | Status | Date |
    | IN_PROCESSING | 2025-01-30T13:47:26 |
  E deveria publicar o JSON esperado na fila authorize-sale-queue
```

### 2. **process-sale-authorization.feature** (11 cenários)

Responsável por testar o processamento assíncrono de autorização (listener de mensagens).

| # | Cenário | Tipo | Validação |
|---|---------|------|-----------|
| 01-04 | Diferentes métodos pagamento (CREDIT/DEBIT/CASH/PIX) | ✅ Happy Path | BD + MidClient + Callback |
| 05 | Venda já processada com sucesso | 🔄 Alternativo | Sem chamar MidClient |
| 06 | Venda com erro anterior, reprocessar | ✅ Retry | Chama MidClient + Atualiza |
| 07 | Valor pagamento divergente | ❌ Fluxo Excepcional | Status ERROR, sem MidClient |
| 08 | Valor item divergente | ❌ Fluxo Excepcional | Status ERROR, sem MidClient |
| 09 | Impostos não encontrados | ❌ Fluxo Excepcional | Status ERROR, sem MidClient |
| 10 | Erro inesperado (no BD) | ❌ Fluxo Excepcional | Sem persistir nada |
| 11 | Venda em processamento | 🔄 Alternativo | Sem processar novamente |

## 🔧 Componentes Principais

### **1. SpringContextStepDefs** (Configuração)
- **@CucumberContextConfiguration**: Inicializa Spring Boot com profile `test`
- **Data Transformers**: Converte DataTables em objetos Java via Jackson
- **@BeforeAll/@AfterAll**: Gerencia Testcontainers (Oracle, RabbitMQ, Redis)
- **handleEmptyValues()**: Converte strings `"<empty>"` em valores nulos

### **2. StepDefs e Especializações** (Implementação)

```java
// Padrão: Cada domínio tem sua classe de StepDefs
public class SaleRequestStepDefs extends StepDefs {
    @Given("que seja informado os dados de Sale Request")
    public void givenSaleRequestData(List<SaleRequestDataTable> data) {
        // Arrange: Prepara dados de entrada
        transitionDataTable.setSaleRequest(data.get(0));
    }
    
    @When("autorizar venda")
    public void whenAuthorizeSale() {
        // Act: Executa a ação
        ResponseEntity<?> response = restTemplate.postForEntity(
            "/authorize-sale",
            transitionDataTable.getSaleRequest(),
            SaleResponse.class
        );
        transitionDataTable.setSaleResponse(response);
    }
    
    @Then("deveria receber os dados de Sale Response")
    public void thenReceiveSaleResponse(List<SaleResponseDataTable> expected) {
        // Assert: Verifica resultado
        SaleResponseVerifier.verify(transitionDataTable.getSaleResponse(), expected.get(0));
    }
}
```

### **3. DataTables** (DTO para Gherkin)

Mapeiam dados das tabelas Gherkin para objetos Java com suporte a fixtures:

```java
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class SaleOrderDataTable {
    private Long id;
    private String channelCode;
    private BigDecimal totalValue;
    
    // Suporte a fixtures: "/fixtures/InvoiceBase64-xxx.txt"
    public String getInvoiceBase64() {
        if (invoiceBase64.startsWith("/fixtures/")) {
            return ResourceFixture.getContentFromResource(invoiceBase64);
        }
        return invoiceBase64;
    }
}
```

### **4. Verifiers** (Asserções Customizadas)

Centralizam validações complexas:

```java
public class SaleResponseVerifier {
    public static void verify(SaleResponse actual, SaleResponseDataTable expected) {
        assertThat(actual.getStatus()).isEqualTo(expected.getStatus());
        assertThat(actual.getDate()).isEqualTo(expected.getDate());
    }
}

public class MessagingVerifier {
    public static void verifyMessagePublished(String queue, String jsonKey) {
        Message message = rabbitTemplate.receive(queue, 5000);
        assertThat(message).isNotNull();
        assertThat(message.toString()).contains(jsonKey);
    }
}
```

### **5. Contexts** (Gerenciamento de Estado)

Gerenciam operações de BD durante testes:

```java
@Component
public class SaleOrderContext {
    private final SaleOrderRepositoryHelper repositoryHelper;
    
    public void insert(List<SaleOrderDataTable> orders) {
        List<SaleOrderEntity> entities = orders.stream()
            .map(this::buildEntity).toList();
        repositoryHelper.saveAll(entities);
    }
}
```

### **6. TransitionDataTable** (Shared State)

Mantém dados compartilhados entre steps:

```java
@Data @Builder
public class TransitionDataTable {
    private SaleRequestDataTable saleRequest;
    private SaleResponseDataTable saleResponse;
    private List<SaleOrderDataTable> expectedOrders;
    private ResponseEntity<?> httpResponse;
    private ErrorResponseDataTable errorResponse;
    // ... mais campos
}
```

## 🔄 Padrão de Execução

### **Arrange-Act-Assert (AAA)**

1. **@Given**: Arrange - Prepara dados iniciais
   - Define Sale Request com validações
   - Insere dados em BD (opcional)
   - Configura mocks WireMock para serviços externos
   - Define respostas esperadas para filas

2. **@When**: Act - Executa ação
   - Envia requisição HTTP via TestRestTemplate
   - Aguarda/dispara listener de mensagem
   - Executa operações de BD

3. **@Then/@And**: Assert - Valida resultado
   - Verifica Response HTTP
   - Valida dados em BD
   - Confirma mensagens em filas/tópicos
   - Valida chamadas a serviços externos

## 🚀 Como Rodar Testes

### **Pré-requisitos**
```bash
# Subir infraestrutura necessária
docker-compose -f script/docker/wiremock.yml up -d
docker-compose -f script/docker/redis.yml up -d
docker-compose -f script/docker/rabbitmq.yml up -d
docker-compose -f script/docker/oracledb-12c-ee.yml up -d

# Executar migrations
# Execute script/db/oracle.sql no banco de dados
```

### **CLI - Maven**
```bash
# Todos os testes de aceitação
mvn test -Dtest=RunCucumberTest

# Específico: Infrastructure
mvn test -pl infrastructure -Dtest=RunCucumberTest

# Específico: Alternative-Infrastructure
mvn test -pl alternative-infrastructure -Dtest=RunCucumberTest

# Com saída detalhada
mvn test -Dtest=RunCucumberTest -X
```

### **IDE**
```
Clique direito em RunCucumberTest.java → Run 'RunCucumberTest'
```

### **Relatorios**
```
target/cucumber-reports/cucumber-report.html   # HTML visual
target/cucumber-reports/cucumber-report.json   # JSON para integrações
```

## 🎯 Boas Práticas

### ✅ **Do's**
1. Use **DataTables** para dados estruturados (nunca strings soltas em @Given)
2. Mantenha **um Given/When/Then por step** - não concatene multiplas ações
3. Use **fixtures de arquivo** para JSONs complexos (`/fixtures/something.json`)
4. Valide **comportamento de negócio** (not HTTP details)
5. Use **@Before/@After** para limpeza de estado entre cenários
6. Documente **por quê** um cenário existe em comentários Gherkin
7. Reutilize **steps existentes** via @And/@E ao invés de duplicar

### ❌ **Don'ts**
1. ❌ Não use **hardcoding** de valores em steps - use DataTables
2. ❌ Não trate **detalhes técnicos** em Gherkin (SQL, JSON paths)
3. ❌ Não crie steps com **lógica condicional** complexa
4. ❌ Não dependa da **ordem de execução** de cenários
5. ❌ Não ignore **exceções em steps** - sempre faça assert ou log
6. ❌ Não esqueça de **limpar BD/filas** entre testes (@BeforeEach)

### 🎨 **Padrões de Nomes**
```gherkin
# ✅ Bom
Dado que seja informado os dados de Sale Request
Quando autorizar venda
Entao deveria receber resposta com status IN_PROCESSING

# ❌ Ruim
Dado o cliente faz requisição
Quando a api é chamada
Entao tudo funciona
```

## 📊 Configuração do Ambiente

### **application-test.yml** (Profile: `test`)
```yaml
spring:
  datasource:
    url: jdbc:oracle:thin:@//localhost:1521/XEPDB1
    username: ECOMMERCE_CHECKOUT_OWNER
    password: ECOMMERCE_CHECKOUT_OWNER
  rabbitmq:
    host: localhost
    username: ecommerce-checkout
    password: ecommerce-checkout
  redis:
    host: localhost
    port: 6379

client:
  tax:
    url: http://localhost:9090/      # WireMock
  mid-client:
    url: http://localhost:9090/      # WireMock
```

### **cucumber.properties**
```ini
cucumber.plugin=pretty, summary, html:target/cucumber-reports/cucumber-report.html
cucumber.monochrome=true
```

## 🔄 Fluxos de Teste

### **Fluxo 1: Autorizar Venda (authorize-sale.feature)**
```
Cliente → [POST /authorize-sale] → Sistema
Sistema → [Valida dados] → ErrorResponse ou Response 202
Sistema → [Publica fila] → RabbitMQ (authorize-sale-queue)
```

### **Fluxo 2: Processar Autorização (process-sale-authorization.feature)**
```
RabbitMQ → [Listener] → SaleAuthorizationService
Service → [Busca impostos] → WireMock (TaxService)
Service → [Autoriza pagamento] → WireMock (MidService)
Service → [Persiste] → Oracle/PostgreSQL
Service → [Publica callback] → RabbitMQ (sale-callback-queue)
```

## 🧪 TestContainers

Gerenciados automaticamente via `SpringContextStepDefs`:

```java
@BeforeAll
static void initializeContainers() {
    new ContainerFactoryImpl().getInstances().forEach(ContainerManager::start);
}

// Containers iniciados automaticamente:
// - Oracle XE/PostgreSQL + Schema DDL
// - RabbitMQ/Kafka + Queues/Topics
// - Redis (cache)
// - WireMock (mocking de serviços)
```

## 📝 Adicionando Novo Cenário

1. **Adicione passo em `.feature`**:
   ```gherkin
   Cenario: XX - Descrição clara - Tipo resultado
     Dado que...
     E que...
     Quando...
     Entao...
   ```

2. **Crie/Atualize StepDefs**:
   ```java
   @Given("pattern matching gherkin")
   public void givenStep(List<DataTable> data) { }
   ```

3. **Crie DataTable se necessário**:
   ```java
   @Data @Builder @NoArgsConstructor @AllArgsConstructor
   public class MyDataTable { }
   ```

4. **Crie Verifier para asserções**:
   ```java
   public class MyVerifier {
       public static void verify(...) { }
   }
   ```

5. **Execute**: `mvn test -Dtest=RunCucumberTest`

---

**Versão**: 1.0 | **Última Atualização**: 2026-05-07

