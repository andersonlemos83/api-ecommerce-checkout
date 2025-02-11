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
- **Testes e Qualidade de Código**: JUnit 5, Mockito, Cucumber, WireMock, Testcontainers, Instancio, JaCoCo e SonarQube
- **Banco de Dados**: Oracle e PostgreSQL (com Hibernate e HikariCP)
- **Cache e Tolerância a Falhas**: Redis e Resilience4j
- **Documentação da API**: Swagger/OpenAPI
- **Comunicação entre Serviços**: OpenFeign
- **Containerização**: Docker
- **Logging e Monitoramento**: Log4j2 e Spring Boot Actuator
- **Gerenciamento de Dependências**: Maven
- **Controle de Versão**: Git

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

<img src="./script/diagrams/architecture.png" alt="Arquitetura Limpa" width="70%" height="70%">

[Ver em tela cheia](./script/diagrams/architecture.png)