# Instruções para GitHub Copilot - API E-commerce Checkout

## 📋 Visão Geral do Projeto

Este é um projeto de estudo e validação de tecnologias para um sistema de orquestração de vendas de e-commerce, baseado em **Clean Architecture** e **Hexagonal Architecture**. A aplicação processa autorizações de vendas, gerencia tributação, comunica com serviços externos e utiliza mensageria assíncrona.

- **Linguagem**: Java 21 LTS
- **Framework**: Spring Boot 3.4.3
- **Build**: Maven (multi-módulo)
- **Versão**: 1.0.1
- **Grupo**: `br.com.alc`

## 🏗️ Arquitetura do Projeto

### Estrutura de Módulos

O projeto é dividido em **3 módulos Maven**:

#### 1. **`core`** (Módulo de Negócio)
- **Responsabilidade**: Contém as regras de negócio isoladas de dependências externas
- **Sem dependências** de: Spring Web, Banco de dados, Mensageria
- **Dependências**: apenas Spring Boot base, Lombok, Log4j2, Commons-Lang3
- **Estrutura**:
  ```
  domain/        → Entidades e objetos de negócio
  exception/     → Exceções customizadas
  port/          → Interfaces (contratos) para adaptadores
  service/       → Serviços/casos de uso de negócio
  util/          → Utilitários
  ```

#### 2. **`infrastructure`** (Infraestrutura Padrão)
- **Banco de dados**: Oracle 21g
- **Mensageria**: RabbitMQ
- **Cache**: Redis
- **Responsabilidade**: Implementar as portas definidas no `core`
- **Estrutura**:
  ```
  adapter/       → Implementação das portas do core
  client/        → Clientes HTTP (OpenFeign)
  configuration/ → Configurações Spring
  dto/           → Data Transfer Objects
  messaging/     → Configuração e listeners de RabbitMQ
  persistence/   → Repositórios e entidades JPA
  util/          → Utilitários específicos
  web/           → Controllers REST
  ```

#### 3. **`alternative-infrastructure`** (Infraestrutura Alternativa)
- **Banco de dados**: PostgreSQL
- **Mensageria**: Kafka
- **Cache**: Redis (compartilhado com infrastructure)
- **Responsabilidade**: Implementação alternativa com diferentes tecnologias
- **Estrutura**: Idêntica ao `infrastructure`

### Padrão Hexagonal

```
   ┌──────────────────────────────────────┐
   │          CORE (Business Rules)       │
   │  ┌─────────────────────────────────┐ │
   │  │  Domain Service                 │ │
   │  │  Entities & Value Objects       │ │
   │  │  Business Exceptions            │ │
   │  └─────────────────────────────────┘ │
   │  ▲     ▲                    ▲     ▲  │
   └──┼─────┼────────────────────┼─────┼──┘
      │     │ (Ports)           │     │
      │     └────────────────────┘     │
   ┌──┴────────────────────────────────┴───┐
   │      INFRASTRUCTURE (Adapters)        │
   │  ┌─────────────┐         ┌─────────┐  │
   │  │ Controllers │         │ Database│  │
   │  │ Messaging   │         │ Clients │  │
   │  │ Caching     │         │ etc     │  │
   │  └─────────────┘         └─────────┘  │
   └───────────────────────────────────────┘
```

## 📚 Stack Tecnológico

### Core Dependencies
- **Java**: 21 LTS
- **Spring Boot**: 3.4.3
- **Lombok**: 1.18.36
- **Apache Log4j2**: 2.24.3
- **Apache Commons Lang3**: 3.17.0
- **Jackson**: Para serialização de datas

### Infrastructure (Default/Alternative Compartilhado)
- **Spring Boot Starters**:
  - `spring-boot-starter-web` (com Undertow em vez de Tomcat)
  - `spring-boot-starter-log4j2`
  - `spring-boot-starter-validation`
  - `spring-boot-starter-data-jpa`
  - `spring-boot-starter-data-redis`
  - `spring-boot-starter-actuator`

### Infrastructure (Específico)
- **Default**:
  - RabbitMQ (spring-boot-starter-amqp)
  - Oracle JDBC 11 (com dependências de segurança)
  - Hibernate com dialeto Oracle

- **Alternative**:
  - Kafka
  - PostgreSQL
  - Hibernate com dialeto PostgreSQL

### Spring Cloud & Patterns
- **OpenFeign**: Comunicação com serviços externos
- **Resilience4j**: Circuit breaker e tolerância a falhas
- **SpringDoc OpenAPI**: Documentação Swagger/OpenAPI

### Testing
- **JUnit 5**: Framework de testes
- **Mockito**: Mocking
- **Cucumber**: BDD e testes de aceitação
- **WireMock**: Mock de APIs HTTP
- **Testcontainers**: Containers para testes de integração
- **Instancio**: Geração de dados para testes

### Código e Build Quality
- **Jacoco**: Cobertura de testes
- **SonarQube**: Análise de código
- **Git Commit ID Maven Plugin**: Rastreamento de versão

## 🎯 Padrões de Código

### Conventions de Nomeclatura

#### Serviços (Core)
```java
// Padrão: {NomeEntidade}Service
public class SaleAuthorizationService {
    // Métodos com nomes descritivos
    void authorizeSale(SaleData saleData) { }
    SaleOrder processSaleAuthorization(SaleAuthorizationMessage message) { }
}
```

#### Adaptadores (Infrastructure)
```java
// Padrão: {NomePort}Adapter ou {Nome}Repository/Client/etc
public class SaleOrderRepositoryAdapter implements SaleOrderPort { }
public class TaxClientAdapter implements TaxPort { }
public class SaleAuthorizationMessageListener { }
```

#### DTOs (Infrastructure)
```java
// Padrão: {Nome}Request ou {Nome}Response
public class AuthorizeSaleRequest { }
public class SaleOrderResponse { }
```

#### Controllers (Infrastructure)
```java
// Padrão: {Recurso}Controller
@RestController
@RequestMapping("/api/v1/sales")
public class SalesController { }
```

#### Entidades JPA (Infrastructure)
```java
// Padrão: {Nome}Entity
@Entity
@Table(name = "SALE_ORDER")
public class SaleOrderEntity { }
```

### Padrões de Desenvolvimento

#### 1. **Camadas e Responsabilidades**
- **Controller**: Recebe requisições HTTP, valida, delega para adapters
- **Adapter**: Converte de/para o domínio, chama serviços de negócio
- **Service (Core)**: Lógica pura de negócio, sem dependências
- **Port (Core)**: Interface que define o contrato com adaptadores
- **Repository/Client**: Acesso a dados e APIs externas

#### 2. **Mapeamento Objeto**
```java
// Use ModelMapper para mapear entre camadas
@Service
public class SaleAdapter {
    @Autowired
    private ModelMapper modelMapper;
    
    public SaleOrderResponse toResponse(SaleOrder saleOrder) {
        return modelMapper.map(saleOrder, SaleOrderResponse.class);
    }
}
```

#### 3. **Tratamento de Exceções**
```java
// Crie exceções customizadas herdando de RuntimeException
public class InvalidSaleDataException extends RuntimeException {
    public InvalidSaleDataException(String message) {
        super(message);
    }
}

// Use @ExceptionHandler para centralizar tratamento
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidSaleDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidSale(InvalidSaleDataException ex) {
        return ResponseEntity.badRequest()
            .body(new ErrorResponse(ex.getMessage()));
    }
}
```

#### 4. **Mensageria**
- **RabbitMQ** (Infrastructure):
  ```yaml
  spring:
    rabbitmq:
      host: localhost
      port: 5672
      username: ecommerce-checkout
      password: ecommerce-checkout
      virtual-host: /ecommerce-checkout
  ```

- **Kafka** (Alternative-Infrastructure):
  ```yaml
  spring:
    kafka:
      bootstrap-servers: localhost:9092
      consumer:
        group-id: ecommerce-checkout
  ```

- **Listener Pattern**:
  ```java
  @Component
  public class SaleAuthorizationMessageListener {
      @RabbitListener(queues = "authorize-sale-queue")
      // ou: @KafkaListener(topics = "authorize-sale", groupId = "ecommerce-checkout")
      public void processSaleAuthorization(SaleAuthorizationMessage message) {
          // Processar mensagem
      }
  }
  ```

#### 5. **Cache (Redis)**
```java
@Component
public class TaxCacheAdapter implements TaxCachePort {
    @Autowired
    private RedisTemplate<String, TaxMatrix> redisTemplate;
    
    @Cacheable(value = "taxMatrix", key = "#code")
    public TaxMatrix findByCode(String code) {
        // Implementação
    }
}
```

#### 6. **Circuit Breaker (Resilience4j)**
```java
@Service
public class MidClientAdapter implements PaymentAuthorizationPort {
    @CircuitBreaker(name = "midService", fallbackMethod = "fallbackAuthorize")
    @Retry(name = "midService")
    public Authorization authorize(Sale sale) {
        // Chamar serviço
    }
    
    public Authorization fallbackAuthorize(Sale sale, Exception ex) {
        // Tratamento de falha
        throw new PaymentServiceUnavailableException("Service temporarily unavailable", ex);
    }
}
```

## 📝 Testes

### Estrutura de Testes

#### 1. **Unit Tests** (Core)
- Localização: `src/test/java/br/com/alc/ecommerce/checkout/core/**`
- Executar: Testes de unidade rápidos, sem dependências
- Suite: `UnitTests` (coleta testes com padrão específico)

```java
@ExtendWith(MockitoExtension.class)
public class SaleAuthorizationServiceTest {
    
    @InjectMocks
    private SaleAuthorizationService service;
    
    @Mock
    private SaleOrderPort saleOrderPort;
    
    @Test
    void givenValidSaleData_whenAuthorize_thenPublishMessage() {
        // Arrange
        SaleData saleData = Instancio.create(SaleData.class);
        
        // Act
        service.authorizeSale(saleData);
        
        // Assert
        verify(saleOrderPort).save(any(SaleOrder.class));
    }
}
```

#### 2. **Acceptance Tests** (Cucumber BDD)
- Localização: `src/test/java/br/com/alc/ecommerce/checkout/infrastructure/**`
- Padrão: `src/test/resources/features/`.feature
- Executar: `RunCucumberTest` (inicializa contexto Spring e infraestrutura)
- Escopo: Testes de integração com Testcontainers

```gherkin
Feature: Authorize Sale
  Scenario: Authorize sale with valid data
    Given all valid sales data has been provided
    When the sale is authorized via the /authorize-sale endpoint
    Then a message containing the sale data must be published in the authorize-sale queue
    And a response must be returned indicating that processing is in progress
```

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthorizeSaleSteps {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @And("all valid sales data has been provided")
    public void validSalesDataProvided() {
        // Setup
    }
    
    @When("the sale is authorized via the /authorize-sale endpoint")
    public void authorizeSaleViaEndpoint() {
        // Ação
    }
}
```

#### 3. **Test Suites**
- **AllTests**: Combina UnitTests + Cucumber (lento)
- **UnitTests**: Apenas testes de unidade (rápido)
- **RunCucumberTest**: Apenas acceptance tests

### Testcontainers

```java
@SpringBootTest
@Testcontainers
public class SaleAuthorizationAcceptanceTest {
    
    @Container
    static OracleContainer oracle = new OracleContainer("gvenzl/oracle-xe:slim-23")
        .withUsername("SYSTEM")
        .withPassword("oracle");
    
    @Container
    static RabbitMQContainer rabbitmq = new RabbitMQContainer("rabbitmq:latest");
    
    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", oracle::getJdbcUrl);
        registry.add("spring.rabbitmq.host", rabbitmq::getHost);
    }
}
```

## 🛠️ Desenvolvimento Local

### Pré-requisitos
- Java JDK 21
- Maven 3.6.2+
- Docker (para Testcontainers e infraestrutura local)

### Primeiro Build
```bash
# Baixar dependências
mvn dependency:resolve -U

# Build sem testes
mvn -U -B clean install -Dmaven.test.skip=true

# Build com testes
mvn -U -B clean install
```

### Rodando com Default Infrastructure (Oracle + RabbitMQ)

```bash
# 1. Subir dependências
docker-compose -f ./script/docker/wiremock.yml up -d
docker-compose -f ./script/docker/redis.yml up -d
docker-compose -f ./script/docker/rabbitmq.yml up -d
docker-compose -f ./script/docker/oracledb-12c-ee.yml up -d

# 2. Executar migrations (oracle.sql em ./script/db/)

# 3. Build e run
mvn package
docker-compose -f ./script/docker/api-ecommerce-checkout-infrastructure.yml up -d

# Ou executar direto no IDE
mvn spring-boot:run -pl infrastructure -Dspring-boot.run.arguments="--spring.profiles.active=local"

# Acessar
# Swagger: http://localhost:8181/swagger-ui.html
# Actuator: http://localhost:8181/actuator
# RabbitMQ Admin: http://localhost:15672/ (guest:guest)
```

### Rodando com Alternative Infrastructure (PostgreSQL + Kafka)

```bash
# 1. Subir dependências
docker-compose -f ./script/docker/wiremock.yml up -d
docker-compose -f ./script/docker/redis.yml up -d
docker-compose -f ./script/docker/kafka.yml up -d
docker-compose -f ./script/docker/postgresdb.yml up -d

# 2. Executar migrations (postgres.sql em ./script/db/)

# 3. Build e run
mvn package
docker-compose -f ./script/docker/api-ecommerce-checkout-alt-infrastructure.yml up -d

# Ou no IDE
mvn spring-boot:run -pl alternative-infrastructure -Dspring-boot.run.arguments="--spring.profiles.active=local"

# Acessar
# Swagger: http://localhost:8282/swagger-ui.html
# Kafka Admin: http://localhost:8787/
```

### Profiles Disponíveis
- `local`: Desenvolvimento local com containers
- `docker`: Quando rodando em container
- `test`: Para testes

## 🎯 Dicas para Trabalhar com Copilot

### Do's ✅
1. **Manter arquitetura limpa**: Peça para respeitar as camadas (core não pode depender de infrastructure)
2. **Usar convenções de nome**: Especifique padrões (Service, Adapter, Repository, etc.)
3. **Referenciar exemplos**: Aponte para classes existentes similares como padrão
4. **Ser específico sobre a camada**: Indique se é Core, Infrastructure ou Alternative-Infrastructure
5. **Mencionar patterns**: Circuit breaker, Event-Driven, Repository Pattern, etc.
6. **Validar com testes**: Sempre peça para incluir testes junto com a implementação
7. **Respeitar tipos de dados**: Use LocalDateTime para datas, enum para estados
8. **Usar Lombok**: Aproveite @Data, @Slf4j, @RequiredArgsConstructor

### Don'ts ❌
1. **Não misturar camadas**: Core não deve conhecer Infrastructure
2. **Não hardcoding**: Use propriedades via `@Value` ou `@ConfigurationProperties`
3. **Não ignorar exceções**: Trate ou re-lance com contexto
4. **Não esquecer de logging**: Use `@Slf4j` e registre operações importantes
5. **Não duplicar código**: Refatore primeiro
6. **Não ignorar tipos genéricos**: Evite raw types

### Exemplos de Prompts Efetivos

```
Gere um adaptador no módulo infrastructure que:
- Implemente a porta SaleOrderPort do core
- Use Spring Data JPA com entidade SaleOrderEntity (@Table("SALE_ORDER"))
- Mapeie de SaleOrder (domain) para SaleOrderEntity
- Inclua paginação com findAll(Pageable)
- Adicione busca customizada findBySaleStatus(SaleStatus status)
- Siga o padrão ModelMapper já existente no projeto
```

```
Crie um cliente HTTP no módulo infrastructure:
- Use OpenFeign para TaxService
- Implemente circuit breaker com Resilience4j
- Configure fallback para indisponibilidade
- Use @CircuitBreaker(name="taxService")
- Inclua timeout de 5 segundos
- Crie teste com WireMock em src/test/java
```

## 📊 Fluxo de Orquestração

```
Request (POST /authorize-sale)
    ↓
[Controller] → Valida request
    ↓
[Adapter] → Converte para SaleData (domain)
    ↓
[SaleAuthorizationService] → Regra de negócio
    ↓
[Publisher] → Publica "authorize-sale" (RabbitMQ/Kafka)
    ↓
Response (202 Accepted - Processing)
    ↓
[Listener] → Consome "authorize-sale"
    ↓
[SaleAuthorizationService] → Processar autorização
    ↓
[TaxClient] → Busca matriz tributária (Circuit breaker)
    ↓
[PaymentClient] → Autoriza pagamento (MidClient)
    ↓
[Repository] → Persiste em BD
    ↓
[Publisher] → Publica "sale-callback"
```

## 📖 Convenções de Mensagens

### authorize-sale Queue/Topic
```java
public class SaleAuthorizationMessage {
    private String saleId;
    private LocalDateTime authorizedAt;
    private SaleData saleData;
    // getters/setters
}
```

### sale-callback Queue/Topic
```java
public class SaleCallbackMessage {
    private String saleId;
    private SaleStatus status;
    private Invoice invoice;
    private LocalDateTime processedAt;
    // getters/setters
}
```

## 🔍 Qualidade de Código

### SonarQube
- Projetos analisados:
  - `api-ecommerce-checkout-core`
  - `api-ecommerce-checkout-infrastructure`
  - `api-ecommerce-checkout-alt-infrastructure`
- Exclusões: Testes e classes de aplicação
- Cobertura: Rastreada com Jacoco

### GitHub Actions
- Build automático em push
- Análise SonarQube
- Testes executados

### Boas Práticas
- Mantenha cobertura de testes > 80%
- Use `@Slf4j` para logging
- Documente endpoints públicos com Swagger/OpenAPI
- Valide entrada com `@Valid` e `@Validated`
- Use `@ConfigurationProperties` para configurações complexas

## 🚀 Deploy

### Build Docker (Infrastructure)
```bash
mvn clean package -DskipTests -P docker-build -pl infrastructure
```

### Build Docker (Alternative-Infrastructure)
```bash
mvn clean package -DskipTests -P docker-build -pl alternative-infrastructure
```

## 📚 Referências

- Clean Architecture: https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html
- Hexagonal Architecture: https://alistair.cockburn.us/hexagonal-architecture/
- Spring Boot Docs: https://docs.spring.io/spring-boot/docs/3.4.3/reference/html/
- Spring Cloud Docs: https://docs.spring.io/spring-cloud/docs/2024.0.0/reference/html/
- Cucumber: https://cucumber.io/docs/cucumber/
- Testcontainers: https://www.testcontainers.org/

---

**Versão**: 1.0  
**Última atualização**: 2026-05-07  
**Mantido por**: Desenvolvimento Pessoal
