[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=andersonlemos83_api-ecommerce-checkout&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=andersonlemos83_api-ecommerce-checkout)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=andersonlemos83_api-ecommerce-checkout&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=andersonlemos83_api-ecommerce-checkout)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=andersonlemos83_api-ecommerce-checkout&metric=coverage)](https://sonarcloud.io/summary/new_code?id=andersonlemos83_api-ecommerce-checkout)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=andersonlemos83_api-ecommerce-checkout&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=andersonlemos83_api-ecommerce-checkout)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=andersonlemos83_api-ecommerce-checkout&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=andersonlemos83_api-ecommerce-checkout)

# Sobre o projeto api-ecommerce-checkout

Este é um projeto fictício criado exclusivamente para estudo e validação de novas tecnologias.

## Tecnologias e Conceitos

Os principais conceitos e tecnologias que desejo validar incluem:

- **Arquitetura**: Arquitetura Limpa e Arquitetura Hexagonal
- **Testes de Aceitação**: Cucumber, WireMock e Testcontainers
- **Mensageria e Processamento Assíncrono**: RabbitMQ e Kafka
- **Cache e Tolerância a Falhas**: Redis e Resilience4j

## Inspiração Bibliográfica

📖 *Arquitetura Limpa: O Guia do Artesão Para Estrutura e Design de Software* – Robert C. Martin  
📖 *Desenvolvimento Ágil Limpo: De Volta às Origens* – Robert C. Martin  
📖 *Código Limpo: Habilidades Práticas do Agile Software* – Robert C. Martin  
📖 *Desenvolvimento de Software Orientado a Objetos Guiado por Testes* – Steve Freeman  
📖 *Refatoração: Aperfeiçoando o Design de Códigos Existentes* – Martin Fowler

🎓 *[Formação de Especialista em Microservices Java Spring](https://e-certificado.com/login/visualizar?c=2343053A8150F7A015193380)* – Decoder Project

## Principais Tecnologias e Ferramentas Utilizadas

- **Linguagem**: Java 21 LTS, Gherkin (para BDD com Cucumber)
- **Framework**: Spring Boot 3.1.4 (Web, Undertow, Validation, Data JPA, AMQP, Data Redis, Actuator, entre outros)
- **Mensageria e Processamento Assíncrono**: RabbitMQ e Kafka
- **Testes de Unidade e Aceitação**: JUnit 5, Mockito, Cucumber, WireMock, Testcontainers e Instancio
- **Banco de Dados**: Oracle e PostgreSQL (com Hibernate e HikariCP)
- **Cache e Tolerância a Falhas**: Redis e Resilience4j
- **Documentação da API**: Swagger/OpenAPI
- **Comunicação entre Serviços**: OpenFeign
- **Containerização**: Docker
- **Logging e Monitoramento**: Log4j2 e Spring Boot Actuator
- **Gerenciamento de Dependências**: Maven
- **Controle de Versão**: Git
- **Integração Contínua (CI)**: GitHub Actions
- **Qualidade de Código**: SonarQube

## Domínio

Um e-commerce fictício realiza centenas de milhares de vendas por múltiplos canais, incluindo site, aplicativo, loja física e caixa de autoatendimento. 
Para garantir a correta tributação, autorização e registro das vendas, torna-se essencial a criação de um sistema orquestrador. 
Esse orquestrador será responsável por compilar a matriz tributária dos itens da sacola, validar as formas de pagamento, 
autorizar as vendas junto ao MidClient de vendas, notificar os canais e clientes sobre a emissão da nota fiscal e registrar todas as transações no banco de dados.

## Features

## authorize-sale.feature
### Cenário Base - Autorizar venda com todos os dados válidos informados
**Dado** que todos os dados válidos de uma venda tenham sido informados  
**Quando** a venda for autorizada por meio do endpoint `/authorize-sale`  
**Então** deverá publicar uma mensagem contendo os dados da venda na fila (ou tópico) `authorize-sale`  
**E** deverá retornar uma resposta indicando que o processamento está em andamento

## process-sale-authorization.feature
### Cenário Base - Processar autorização de venda com todos os dados válidos informados
**Dado** que todos os dados válidos de uma venda tenham sido informados  
**E** que os dados da matriz tributária estejam disponíveis no endpoint `/findByCode` do serviço TaxClient  
**E** que os dados da nota fiscal estejam disponíveis no endpoint `/authorize` do serviço MidClient  
**Quando** a autorização da venda for processada por meio do listener `authorize-sale`  
**Então** o sistema deverá autorizar a venda com sua matriz tributária junto ao endpoint `/authorize` do serviço MidClient  
**E** deverá registrar a venda na base de dados  
**E** deverá publicar uma mensagem contendo os dados da venda processada e sua nota fiscal na fila (ou tópico) `sale-callback`

## Fluxo de Orquestração de Vendas

<img src="script/diagrams/sales-orchestration-flow.png" alt="Fluxo de orquestração de vendas" width="100%" height="100%">

[Ver em tela cheia](./script/diagrams/sales-orchestration-flow.png)

## Arquitetura

<img src="./script/diagrams/architecture.png" alt="Arquitetura (Limpa + Hexagonal)" width="70%" height="70%">

[Ver em tela cheia](./script/diagrams/architecture.png)

## Requisitos

- Java JDK 21
- Maven 3.6.2 ou superior
- Docker (Necessário para o Testcontainer e para subir a aplicação localmente)

## Primeiros Passos

- **Baixar todas as dependências do projeto**:
  ```
    mvn dependency:resolve -U
  ```
- **Executar o build do projeto**: 
  ```
    mvn -U -B clean install -Dmaven.test.skip=true
  ```
- **Executar o build do projeto executando todos os testes**: 
  ```
    mvn -U -B clean install
  ```

## Sobre os testes

Para organizar os testes de acordo com seu tipo e função, eles foram agrupados em três grandes suítes:

- **RunCucumberTest**: Contém todos os testes de aceitação implementados com Cucumber e BDD. Essa suíte possui uma execução mais lenta, pois exige a inicialização do contexto e da infraestrutura.
- **UnitTests**: Reúne todos os testes de unidade do projeto. Por não possuir dependências externas, a sua execução é rápida.
- **AllTests**: Agrupa todos os testes implementados, combinando os testes de aceitação (RunCucumberTest) e os testes de unidade (UnitTests).

## Sobre a aplicação

O projeto Ecommerce Checkout foi desenvolvido seguindo os princípios da arquitetura limpa e hexagonal, visando isolar as regras de negócio em um módulo Core e permitir a implementação de diferentes infraestruturas.

Atualmente, foram implementados os seguintes módulos de infraestrutura:

- **Módulo de Infraestrutura Padrão**: Utiliza Oracle como banco de dados e RabbitMQ como mensageria.
- **Módulo de Infratrutura Alternativa**: Utiliza PostgreSQL como banco de dados e Kafka como mensageria.

## Começando com a aplicação padrão

- **Wiremock**:
1. Subir uma instância do Wiremock:
  ```
    docker-compose -f .\script\docker\wiremock.yml up -d
  ```

2. Testar a instância do Wiremock:
  ```
    curl 'http://localhost:8443/findByCode?code=100231933559'
  ```
  [Testar Wiremock](http://localhost:8443/findByCode?code=100231933559)

- **Redis**:
1. Subir uma instância do Redis:
  ```
    docker-compose -f .\script\docker\redis.yml up -d
  ```

2. Testar a instância do Redis:
  ```
    1. docker exec -it redis /bin/bash
    2. redis-cli
    3. KEYS "*"
    4. exit
    5. exit
  ```

- **RabbitMQ**:
1. Subir uma instância do RabbitMQ:
  ```
    docker-compose -f .\script\docker\rabbitmq.yml up -d
  ```

2. Testar a instância do RabbitMQ:
   [Testar Rabbit](http://localhost:15672/)

3. Logar no RabbitMQ Admin com guest:
  ```
    username: guest
    password: guest
  ```

4. Criar um novo usuário ecommerce-checkout:
  ```
    1. Acessar /Admin/User
    2. Preencha Username: ecommerce-checkout, Password: ecommerce-checkout e Tags: administrator
    3. Aperte "Add user" 
  ```

5. Criar um novo virtual host ecommerce-checkout:
  ```
    1. Acessar /Admin/Virtual Hosts
    2. Preencha Name: ecommerce-checkout e Default Queue Type: Classic
    3. Aperte "Add virtual host" 
  ```

6. Adicionar permissão do usuário ecommerce-checkout ao virtual host ecommerce-checkout:
  ```
    1. Acessar /Admin/Virtual Hosts/ecommerce-checkout
    2. Preencha User: ecommerce-checkout, Configure regexp: .*, Write regexp: .* e Read regexp: .*
    3. Aperte "Set permissions" 
  ```

7. Logar no RabbitMQ Admin com ecommerce-checkout:
  ```
    username: ecommerce-checkout
    password: ecommerce-checkout
  ```

8. Criar um nova fila sale-callback-queue:
  ```
    1. Acessar /Queues and Streams
    2. Preencha Virtual host: ecommerce-checkout, Type: Default fo virtual host, Name: sale-callback-queue e Durability: Durable
    3. Aperte "Add queue" 
  ```

- **Oracle**:
1. Subir uma instância do Oracle:
  ```
    docker-compose -f .\script\docker\oracledb-12c-ee.yml up -d
  ```

2. Testar a instância através de algum client Oracle (recomendo SQL Developer ou DBeaver):
   ```
   database: ORCL

   host: localhost
   port: 1521
   service name: ORCL
   username: SYSTEM
   password: oracle

   jdbc:oracle:thin:@//localhost:1521/ORCL
   
   OBS: As vezes a instância do Oracle demora para subir!
   ```

3. Criar objetos do esquema ECOMMERCE_CHECKOUT_OWNER:
[Script oracle.sql](./script/db/oracle.sql)
   
- **Execute EcommerceCheckoutInfrastructureApplication application-local.yml**: Java 21 LTS, Gherkin (para BDD com Cucumber)
- **Framework**: Spring Boot 3.1.4 (Web, Undertow, Validation, Data JPA, AMQP, Data Redis, Actuator, entre outros)
- **Mensageria e Processamento Assíncrono**: RabbitMQ e Kafka