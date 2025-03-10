# language: pt

Funcionalidade: Processar Autorizacao Venda

  Cenario de Fundo:
    Dado que o sistema seja executado na seguinte data e hora
      | Year | Month | Day | Hour | Minute | Second |
      | 2025 | 01    | 30  | 13   | 48     | 06     |

  Cenario: 01 - Processar autorizacao venda valida com pagamento credito - Sucesso
    Dado que seja informado os dados de Sale Request
      | Channel Code | Company Code | Store Code | Pos | Order Number | Total Value | Freight Value |
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
      | Key          | Status | Response                                   |
      | 100231933559 | OK     | /fixtures/TaxResponseDto-100231933559.json |
      | 392084657819 | OK     | /fixtures/TaxResponseDto-392084657819.json |
      | 874631202305 | OK     | /fixtures/TaxResponseDto-874631202305.json |
    E que existam os seguintes responses disponiveis no endpoint authorize
      | Status | Response                                          |
      | OK     | /fixtures/AuthorizeSaleResponseDto-987654322.json |
    Quando processar autorizacao venda
    Entao deveria aguadar "1000" milisegundos
    E deveria existir as seguintes Sale Order na base
      | Id | ChannelCode | Company Code | Store Code | Pos | Order Number | Total Value | Freight Value | Invoice Key                                  | Invoice Number | Issuance Date           | Invoice Base64                                                           | Status    | Error Reason | Created Date            | Updated Date            |
      | 1  | APP         | 001          | 100        | 105 | 987654322    | 105.04      | 5.05          | 27250212345678550010000000011234567898765432 | 000000001      | 2025-01-30T13:48:00.000 | /fixtures/InvoiceBase64-27250212345678550010000000011234567898765432.txt | PROCESSED |              | 2025-01-30T13:48:06.000 | 2025-01-30T13:48:06.000 |
    E deveria enviar para o endpoint authorize os requests esperados
      | Request                                          |
      | /fixtures/AuthorizeSaleRequestDto-987654322.json |
    E deveria publicar o JSON esperado no topico
      | Topic Name          | Json Key  |
      | sale-callback-topic | 987654322 |

  Cenario: 02 - Processar autorizacao venda valida com pagamento debito - Sucesso
    Dado que seja informado os dados de Sale Request
      | Channel Code | Company Code | Store Code | Pos | Order Number | Total Value | Freight Value |
      | APP          | 001          | 100        | 105 | 987654323    | 105.04      | 5.05          |
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
      | DEBIT          | 2025-01-30T13:45:01 | 270606             | 3556777163651312 |         | 105.04 |
    E que existam os seguintes responses disponiveis no endpoint findByCode
      | Key          | Status | Response                                   |
      | 100231933559 | OK     | /fixtures/TaxResponseDto-100231933559.json |
      | 392084657819 | OK     | /fixtures/TaxResponseDto-392084657819.json |
      | 874631202305 | OK     | /fixtures/TaxResponseDto-874631202305.json |
    E que existam os seguintes responses disponiveis no endpoint authorize
      | Status | Response                                          |
      | OK     | /fixtures/AuthorizeSaleResponseDto-987654323.json |
    Quando processar autorizacao venda
    Entao deveria aguadar "1000" milisegundos
    E deveria existir as seguintes Sale Order na base
      | Id | ChannelCode | Company Code | Store Code | Pos | Order Number | Total Value | Freight Value | Invoice Key                                  | Invoice Number | Issuance Date           | Invoice Base64                                                           | Status    | Error Reason | Created Date            | Updated Date            |
      | 1  | APP         | 001          | 100        | 105 | 987654323    | 105.04      | 5.05          | 27250212345678550010000000011234567898765432 | 000000001      | 2025-01-30T13:48:00.000 | /fixtures/InvoiceBase64-27250212345678550010000000011234567898765432.txt | PROCESSED |              | 2025-01-30T13:48:06.000 | 2025-01-30T13:48:06.000 |
    E deveria enviar para o endpoint authorize os requests esperados
      | Request                                          |
      | /fixtures/AuthorizeSaleRequestDto-987654323.json |
    E deveria publicar o JSON esperado no topico
      | Topic Name          | Json Key  |
      | sale-callback-topic | 987654323 |

  Cenario: 03 - Processar autorizacao venda valida com pagamento dinheiro - Sucesso
    Dado que seja informado os dados de Sale Request
      | Channel Code | Company Code | Store Code | Pos | Order Number | Total Value | Freight Value |
      | APP          | 001          | 100        | 105 | 987654324    | 105.04      | 5.05          |
    E que seja informado os dados de Customer
      | Name              | Document    | Document Type | Address           | Address Number | Address Complement | Neighborhood | City   | State | Country | Zip Code | Phone       | Email                        |
      | Martin Kauê Lopes | 60778532402 | CPF           | Rua Projetada 913 | 622            | Apt 202            | Antares      | Maceió | AL    | Brasil  | 57048434 | 82992344475 | martin_lopes@rafaelmarin.net |
    E que seja informado os dados de Shopping Cart Item
      | Code         | Quantity | Value |
      | 100231933559 | 1        | 7.09  |
      | 874631202305 | 2        | 17.68 |
      | 392084657819 | 3        | 19.18 |
    E que seja informado os dados de Payment
      | Payment Method | Payment Date        | Authorization Code | Card Number | Pix Key | Value  |
      | CASH           | 2025-01-30T13:45:01 | 270606             |             |         | 105.04 |
    E que existam os seguintes responses disponiveis no endpoint findByCode
      | Key          | Status | Response                                   |
      | 100231933559 | OK     | /fixtures/TaxResponseDto-100231933559.json |
      | 392084657819 | OK     | /fixtures/TaxResponseDto-392084657819.json |
      | 874631202305 | OK     | /fixtures/TaxResponseDto-874631202305.json |
    E que existam os seguintes responses disponiveis no endpoint authorize
      | Status | Response                                          |
      | OK     | /fixtures/AuthorizeSaleResponseDto-987654324.json |
    Quando processar autorizacao venda
    Entao deveria aguadar "1000" milisegundos
    E deveria existir as seguintes Sale Order na base
      | Id | ChannelCode | Company Code | Store Code | Pos | Order Number | Total Value | Freight Value | Invoice Key                                  | Invoice Number | Issuance Date           | Invoice Base64                                                           | Status    | Error Reason | Created Date            | Updated Date            |
      | 1  | APP         | 001          | 100        | 105 | 987654324    | 105.04      | 5.05          | 27250212345678550010000000011234567898765432 | 000000001      | 2025-01-30T13:48:00.000 | /fixtures/InvoiceBase64-27250212345678550010000000011234567898765432.txt | PROCESSED |              | 2025-01-30T13:48:06.000 | 2025-01-30T13:48:06.000 |
    E deveria enviar para o endpoint authorize os requests esperados
      | Request                                          |
      | /fixtures/AuthorizeSaleRequestDto-987654324.json |
    E deveria publicar o JSON esperado no topico
      | Topic Name          | Json Key  |
      | sale-callback-topic | 987654324 |

  Cenario: 04 - Processar autorizacao venda valida com pagamento PIX - Sucesso
    Dado que seja informado os dados de Sale Request
      | Channel Code | Company Code | Store Code | Pos | Order Number | Total Value | Freight Value |
      | APP          | 001          | 100        | 105 | 987654325    | 105.04      | 5.05          |
    E que seja informado os dados de Customer
      | Name              | Document    | Document Type | Address           | Address Number | Address Complement | Neighborhood | City   | State | Country | Zip Code | Phone       | Email                        |
      | Martin Kauê Lopes | 60778532402 | CPF           | Rua Projetada 913 | 622            | Apt 202            | Antares      | Maceió | AL    | Brasil  | 57048434 | 82992344475 | martin_lopes@rafaelmarin.net |
    E que seja informado os dados de Shopping Cart Item
      | Code         | Quantity | Value |
      | 100231933559 | 1        | 7.09  |
      | 874631202305 | 2        | 17.68 |
      | 392084657819 | 3        | 19.18 |
    E que seja informado os dados de Payment
      | Payment Method | Payment Date        | Authorization Code | Card Number | Pix Key     | Value  |
      | PIX            | 2025-01-30T13:45:01 | 270606             |             | 82992344475 | 105.04 |
    E que existam os seguintes responses disponiveis no endpoint findByCode
      | Key          | Status | Response                                   |
      | 100231933559 | OK     | /fixtures/TaxResponseDto-100231933559.json |
      | 392084657819 | OK     | /fixtures/TaxResponseDto-392084657819.json |
      | 874631202305 | OK     | /fixtures/TaxResponseDto-874631202305.json |
    E que existam os seguintes responses disponiveis no endpoint authorize
      | Status | Response                                          |
      | OK     | /fixtures/AuthorizeSaleResponseDto-987654325.json |
    Quando processar autorizacao venda
    Entao deveria aguadar "1000" milisegundos
    E deveria existir as seguintes Sale Order na base
      | Id | ChannelCode | Company Code | Store Code | Pos | Order Number | Total Value | Freight Value | Invoice Key                                  | Invoice Number | Issuance Date           | Invoice Base64                                                           | Status    | Error Reason | Created Date            | Updated Date            |
      | 1  | APP         | 001          | 100        | 105 | 987654325    | 105.04      | 5.05          | 27250212345678550010000000011234567898765432 | 000000001      | 2025-01-30T13:48:00.000 | /fixtures/InvoiceBase64-27250212345678550010000000011234567898765432.txt | PROCESSED |              | 2025-01-30T13:48:06.000 | 2025-01-30T13:48:06.000 |
    E deveria enviar para o endpoint authorize os requests esperados
      | Request                                          |
      | /fixtures/AuthorizeSaleRequestDto-987654325.json |
    E deveria publicar o JSON esperado no topico
      | Topic Name          | Json Key  |
      | sale-callback-topic | 987654325 |

  Cenario: 05 - Processar autorizacao venda qualquer ja processada com sucesso - Fluxo alternativo
    Dado que seja informado os dados de Sale Request
      | Channel Code | Company Code | Store Code | Pos | Order Number | Total Value | Freight Value |
      | APP          | 001          | 100        | 105 | 987654326    | 105.04      | 5.05          |
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
      | Key          | Status | Response                                   |
      | 100231933559 | OK     | /fixtures/TaxResponseDto-100231933559.json |
      | 392084657819 | OK     | /fixtures/TaxResponseDto-392084657819.json |
      | 874631202305 | OK     | /fixtures/TaxResponseDto-874631202305.json |
    E que existam as Sale Order cadastradas
      | ChannelCode | Company Code | Store Code | Pos | Order Number | Total Value | Freight Value | Invoice Key                                  | Invoice Number | Issuance Date           | Invoice Base64                                                           | Status    | Error Reason | Created Date            | Updated Date            |
      | APP         | 001          | 100        | 105 | 987654326    | 105.04      | 5.05          | 27250212345678550010000000011234567898765432 | 000000001      | 2025-01-30T13:47:46.000 | /fixtures/InvoiceBase64-27250212345678550010000000011234567898765432.txt | PROCESSED |              | 2025-01-30T13:47:26.000 | 2025-01-30T13:47:26.000 |
    Quando processar autorizacao venda
    Entao deveria aguadar "1000" milisegundos
    E deveria existir as seguintes Sale Order na base
      | Id | ChannelCode | Company Code | Store Code | Pos | Order Number | Total Value | Freight Value | Invoice Key                                  | Invoice Number | Issuance Date           | Invoice Base64                                                           | Status    | Error Reason | Created Date            | Updated Date            |
      | 1  | APP         | 001          | 100        | 105 | 987654326    | 105.04      | 5.05          | 27250212345678550010000000011234567898765432 | 000000001      | 2025-01-30T13:47:46.000 | /fixtures/InvoiceBase64-27250212345678550010000000011234567898765432.txt | PROCESSED |              | 2025-01-30T13:47:26.000 | 2025-01-30T13:47:26.000 |
    E deveria publicar o JSON esperado no topico
      | Topic Name          | Json Key  |
      | sale-callback-topic | 987654326 |
    E nao deveria enviar nenhum request para o endpoint authorize

  Cenario: 06 - (Re)Processar autorizacao venda qualquer ja processada com erro - Sucesso
    Dado que seja informado os dados de Sale Request
      | Channel Code | Company Code | Store Code | Pos | Order Number | Total Value | Freight Value |
      | APP          | 001          | 100        | 105 | 987654327    | 105.04      | 5.05          |
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
      | Key          | Status | Response                                   |
      | 100231933559 | OK     | /fixtures/TaxResponseDto-100231933559.json |
      | 392084657819 | OK     | /fixtures/TaxResponseDto-392084657819.json |
      | 874631202305 | OK     | /fixtures/TaxResponseDto-874631202305.json |
    E que existam os seguintes responses disponiveis no endpoint authorize
      | Status | Response                                          |
      | OK     | /fixtures/AuthorizeSaleResponseDto-987654327.json |
    E que existam as Sale Order cadastradas
      | ChannelCode | Company Code | Store Code | Pos | Order Number | Total Value | Freight Value | Invoice Key                                  | Invoice Number | Issuance Date           | Invoice Base64                                                           | Status | Error Reason                                                    | Created Date            | Updated Date            |
      | APP         | 001          | 100        | 105 | 987654327    | 105.04      | 5.05          | 27250212345678550010000000011234567898765432 | 000000001      | 2025-01-30T13:47:46.000 | /fixtures/InvoiceBase64-27250212345678550010000000011234567898765432.txt | ERROR  | O valor total dos pagamentos está diferente do total informado. | 2025-01-30T13:47:26.000 | 2025-01-30T13:47:26.000 |
    Quando processar autorizacao venda
    Entao deveria aguadar "1000" milisegundos
    E deveria existir as seguintes Sale Order na base
      | Id | ChannelCode | Company Code | Store Code | Pos | Order Number | Total Value | Freight Value | Invoice Key                                  | Invoice Number | Issuance Date           | Invoice Base64                                                           | Status    | Error Reason                                                    | Created Date            | Updated Date            |
      | 1  | APP         | 001          | 100        | 105 | 987654327    | 105.04      | 5.05          | 27250212345678550010000000011234567898765432 | 000000001      | 2025-01-30T13:47:46.000 | /fixtures/InvoiceBase64-27250212345678550010000000011234567898765432.txt | ERROR     | O valor total dos pagamentos está diferente do total informado. | 2025-01-30T13:47:26.000 | 2025-01-30T13:47:26.000 |
      | 2  | APP         | 001          | 100        | 105 | 987654327    | 105.04      | 5.05          | 27250212345678550010000000011234567898765432 | 000000001      | 2025-01-30T13:48:00.000 | /fixtures/InvoiceBase64-27250212345678550010000000011234567898765432.txt | PROCESSED |                                                                 | 2025-01-30T13:48:06.000 | 2025-01-30T13:48:06.000 |
    E deveria enviar para o endpoint authorize os requests esperados
      | Request                                          |
      | /fixtures/AuthorizeSaleRequestDto-987654327.json |
    E deveria publicar o JSON esperado no topico
      | Topic Name          | Json Key  |
      | sale-callback-topic | 987654327 |

  Cenario: 07 - Processar autorizacao venda qualquer com valor de pagamento divergente - Fluxo excepcional
    Dado que seja informado os dados de Sale Request
      | Channel Code | Company Code | Store Code | Pos | Order Number | Total Value | Freight Value |
      | APP          | 001          | 100        | 105 | 987654328    | 105.04      | 5.05          |
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
      | CREDIT         | 2025-01-30T13:45:01 | 270606             | 3556777163651312 |         | 105.03 |
    E que existam os seguintes responses disponiveis no endpoint findByCode
      | Key          | Status | Response                                   |
      | 100231933559 | OK     | /fixtures/TaxResponseDto-100231933559.json |
      | 392084657819 | OK     | /fixtures/TaxResponseDto-392084657819.json |
      | 874631202305 | OK     | /fixtures/TaxResponseDto-874631202305.json |
    Quando processar autorizacao venda
    Entao deveria aguadar "1000" milisegundos
    E deveria existir as seguintes Sale Order na base
      | Id | ChannelCode | Company Code | Store Code | Pos | Order Number | Total Value | Freight Value | Invoice Key | Invoice Number | Issuance Date | Invoice Base64 | Status | Error Reason                                                    | Created Date            | Updated Date            |
      | 1  | APP         | 001          | 100        | 105 | 987654328    | 105.04      | 5.05          |             |                |               |                | ERROR  | O valor total dos pagamentos está diferente do total informado. | 2025-01-30T13:48:06.000 | 2025-01-30T13:48:06.000 |
    E deveria publicar o JSON esperado no topico
      | Topic Name          | Json Key  |
      | sale-callback-topic | 987654328 |
    E nao deveria enviar nenhum request para o endpoint authorize

  Cenario: 08 - Processar autorizacao venda qualquer com valor de item divergente - Fluxo excepcional
    Dado que seja informado os dados de Sale Request
      | Channel Code | Company Code | Store Code | Pos | Order Number | Total Value | Freight Value |
      | APP          | 001          | 100        | 105 | 987654329    | 105.04      | 5.05          |
    E que seja informado os dados de Customer
      | Name              | Document    | Document Type | Address           | Address Number | Address Complement | Neighborhood | City   | State | Country | Zip Code | Phone       | Email                        |
      | Martin Kauê Lopes | 60778532402 | CPF           | Rua Projetada 913 | 622            | Apt 202            | Antares      | Maceió | AL    | Brasil  | 57048434 | 82992344475 | martin_lopes@rafaelmarin.net |
    E que seja informado os dados de Shopping Cart Item
      | Code         | Quantity | Value |
      | 100231933559 | 1        | 7.09  |
      | 874631202305 | 2        | 17.68 |
      | 392084657819 | 3        | 19.17 |
    E que seja informado os dados de Payment
      | Payment Method | Payment Date        | Authorization Code | Card Number      | Pix Key | Value  |
      | CREDIT         | 2025-01-30T13:45:01 | 270606             | 3556777163651312 |         | 105.04 |
    E que existam os seguintes responses disponiveis no endpoint findByCode
      | Key          | Status | Response                                   |
      | 100231933559 | OK     | /fixtures/TaxResponseDto-100231933559.json |
      | 392084657819 | OK     | /fixtures/TaxResponseDto-392084657819.json |
      | 874631202305 | OK     | /fixtures/TaxResponseDto-874631202305.json |
    Quando processar autorizacao venda
    Entao deveria aguadar "1000" milisegundos
    E deveria existir as seguintes Sale Order na base
      | Id | ChannelCode | Company Code | Store Code | Pos | Order Number | Total Value | Freight Value | Invoice Key | Invoice Number | Issuance Date | Invoice Base64 | Status | Error Reason                                               | Created Date            | Updated Date            |
      | 1  | APP         | 001          | 100        | 105 | 987654329    | 105.04      | 5.05          |             |                |               |                | ERROR  | O valor total dos itens está diferente do total informado. | 2025-01-30T13:48:06.000 | 2025-01-30T13:48:06.000 |
    E deveria publicar o JSON esperado no topico
      | Topic Name          | Json Key  |
      | sale-callback-topic | 987654329 |
    E nao deveria enviar nenhum request para o endpoint authorize

  Cenario: 09 - Processar autorizacao venda qualquer com impostos nao encontrados - Fluxo excepcional
    Dado que seja informado os dados de Sale Request
      | Channel Code | Company Code | Store Code | Pos | Order Number | Total Value | Freight Value |
      | APP          | 001          | 100        | 105 | 987654330    | 105.04      | 5.05          |
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
      | Key          | Status      | Response                                   |
      | 100231933559 | OK          | /fixtures/TaxResponseDto-100231933559.json |
      | 392084657819 | OK          | /fixtures/TaxResponseDto-392084657819.json |
      | 874631202305 | BAD_REQUEST | Imposto não encontrado.                    |
    Quando processar autorizacao venda
    Entao deveria aguadar "2500" milisegundos
    E deveria existir as seguintes Sale Order na base
      | Id | ChannelCode | Company Code | Store Code | Pos | Order Number | Total Value | Freight Value | Invoice Key | Invoice Number | Issuance Date | Invoice Base64 | Status | Error Reason            | Created Date            | Updated Date            |
      | 1  | APP         | 001          | 100        | 105 | 987654330    | 105.04      | 5.05          |             |                |               |                | ERROR  | Imposto não encontrado. | 2025-01-30T13:48:06.000 | 2025-01-30T13:48:06.000 |
    E deveria publicar o JSON esperado no topico
      | Topic Name          | Json Key  |
      | sale-callback-topic | 987654330 |
    E nao deveria enviar nenhum request para o endpoint authorize

  Cenario: 10 - Processar autorizacao venda qualquer com erro inesperado ao consultar Sale Order - Fluxo excepcional
    Dado que seja informado os dados de Sale Request
      | Channel Code | Company Code | Store Code | Pos | Order Number | Total Value | Freight Value |
      | APP          | 001          | 100        | 105 | 987654331    | 105.04      | 5.05          |
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
      | Key          | Status | Response                                   |
      | 100231933559 | OK     | /fixtures/TaxResponseDto-100231933559.json |
      | 392084657819 | OK     | /fixtures/TaxResponseDto-392084657819.json |
      | 874631202305 | OK     | /fixtures/TaxResponseDto-874631202305.json |
    Quando processar autorizacao venda
    Entao deveria aguadar "1000" milisegundos
    E nao deveria existir nenhuma Sale Order na base
    E nao deveria enviar nenhum request para o endpoint authorize
    E nao deveria publicar nenhum JSON no topico
      | Topic Name           |
      | authorize-sale-topic |

  Cenario: 11 - Processar autorizacao venda qualquer ja em processamento - Fluxo alternativo
    Dado que seja informado os dados de Sale Request
      | Channel Code | Company Code | Store Code | Pos | Order Number | Total Value | Freight Value |
      | APP          | 001          | 100        | 105 | 987654332    | 105.04      | 5.05          |
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
      | Key          | Status | Response                                   |
      | 100231933559 | OK     | /fixtures/TaxResponseDto-100231933559.json |
      | 392084657819 | OK     | /fixtures/TaxResponseDto-392084657819.json |
      | 874631202305 | OK     | /fixtures/TaxResponseDto-874631202305.json |
    E que existam as Sale Order cadastradas
      | ChannelCode | Company Code | Store Code | Pos | Order Number | Total Value | Freight Value | Invoice Key | Invoice Number | Issuance Date | Invoice Base64 | Status        | Error Reason | Created Date            | Updated Date            |
      | APP         | 001          | 100        | 105 | 987654332    | 105.04      | 5.05          |             |                |               |                | IN_PROCESSING |              | 2025-01-30T13:47:26.000 | 2025-01-30T13:47:26.000 |
    Quando processar autorizacao venda
    Entao deveria aguadar "1000" milisegundos
    E deveria existir as seguintes Sale Order na base
      | Id | ChannelCode | Company Code | Store Code | Pos | Order Number | Total Value | Freight Value | Invoice Key | Invoice Number | Issuance Date | Invoice Base64 | Status        | Error Reason | Created Date            | Updated Date            |
      | 1  | APP         | 001          | 100        | 105 | 987654332    | 105.04      | 5.05          |             |                |               |                | IN_PROCESSING |              | 2025-01-30T13:47:26.000 | 2025-01-30T13:47:26.000 |
    E nao deveria enviar nenhum request para o endpoint authorize
    E nao deveria publicar nenhum JSON no topico
      | Topic Name           |
      | authorize-sale-topic |