# language: pt

Funcionalidade: Processar Autorizacao Venda

  Cenario de Fundo:
    Dado que o sistema seja executado na seguinte data e hora
      | Year | Month | Day | Hour | Minute | Second |
      | 2025 | 01    | 30  | 13   | 48     | 06     |

  Cenario: 01 - Processar autorizacao venda valida com pagamento Credito - Sucesso
    Dado que seja informado os dados de Sale Request
      | Channel Code | Company Code | Store Code | Pos | Number Order | Total Value | Freight Value |
      | APP          | 001          | 100        | 105 | 987654322    | 105.04      | 5.05          |
    E que seja informado os dados de Customer
      | Name              | Document    | Document Type | Address           | Address Number | Address Complement | Neighborhood | City   | State | Country | Zip Code | Phone       | Email                        |
      | Martin Kauê Lopes | 60778532402 | CPF           | Rua Projetada 913 | 622            | Apt 202            | Antares      | Maceió | AL    | Brasil  | 57048434 | 82992344475 | martin_lopes@rafaelmarin.net |
    E que seja informado os dados de Shopping Cart Item
      | Code         | Quantity | Value |
      | 100231933559 | 1        | 7.09  |
      | 874631202305 | 2        | 17.68 |
      | 392084657819 | 3        | 19.18 |
    E que seja informado os dados de Payment
      | Payment Method | Payment Date        | Authorization Code | Card Number      | Pix Key | Value  |
      | CREDIT         | 2025-01-30T13:45:01 | 270606             | 3556777163651312 |         | 105.04 |
    E que existam os seguintes responses disponiveis no endpoint findByCode
      | Status | Response                                   |
      | OK     | /fixtures/TaxResponseDto-100231933559.json |
      | OK     | /fixtures/TaxResponseDto-392084657819.json |
      | OK     | /fixtures/TaxResponseDto-874631202305.json |
    E que existam os seguintes responses disponiveis no endpoint authorize
      | Status | Response                                          |
      | OK     | /fixtures/AuthorizeSaleResponseDto-987654322.json |
    Quando processar autorizacao venda
    Entao deveria existir as seguintes Sale Order na base
      | Id | ChannelCode | Company Code | Store Code | Pos | Number Order | Total Value | Freight Value | Invoice Key                                   | Invoice Number | Issuance Date           | Invoice Base64                                                            | Status    | Error Reason | Created Date            | Updated Date            |
      | 1  | APP         | 001          | 100        | 105 | 987654322    | 105.04      | 5.05          | 272502123456785500100000000112345678987654321 | 000000001      | 2025-01-30T13:48:00.000 | /fixtures/InvoiceBase64-272502123456785500100000000112345678987654321.txt | PROCESSED |              | 2025-01-30T13:48:06.000 | 2025-01-30T13:48:06.000 |
    E deveria enviar para o endpoint authorize os requests esperados
      | Request                                          |
      | /fixtures/AuthorizeSaleRequestDto-987654322.json |
    E deveria publicar o JSON esperado na fila
      | Queue Name          | Json Key  |
      | sale-callback-queue | 987654322 |