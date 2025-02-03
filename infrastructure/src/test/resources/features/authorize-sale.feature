# language: pt

Funcionalidade: Autorizar Venda

  Cenario de Fundo:
    Dado que o sistema seja executado na seguinte data e hora
      | Year | Month | Day | Hour | Minute | Second |
      | 2025 | 01    | 30  | 13   | 47     | 26     |

  Cenario: 01 - Autorizar venda com todos os dados validos informados - Sucesso
    Dado que seja informado os dados de Sale Request
      | Channel Code | Company Code | Store Code | Pos | Number Order | Total Value | Freight Value |
      | APP          | 001          | 100        | 105 | 987654321    | 105.04      | 5.05          |
    E que seja informado os dados de Customer
      | Name              | Document    | Document Type | Address           | Address Number | Address Complement | Neighborhood | City   | State | Country | Zip Code | Phone       | Email                        |
      | Martin Kauê Lopes | 60778532402 | CPF           | Rua Projetada 913 | 622            | Apt 202            | Antares      | Maceió | AL    | Brasil  | 57048434 | 82992344475 | martin_lopes@rafaelmarin.net |
    E que seja informado os dados de Shopping Cart Item
      | Code         | Quantity | Value |
      | 100231933559 | 1        | 7.09  |
      | 874631202305 | 2        | 17.68 |
      | 392084657819 | 3        | 19.18 |
    E que seja informado os dados de Payment
      | Payment Method | Payment Date            | Authorization Code | Card Number      | Pix Key | Value  |
      | CREDIT         | 2025-01-30T13:45:01.450 | 270606             | 3556777163651312 |         | 105.04 |
    Quando autorizar venda
    Entao deveria receber os dados de Sale Response
      | Status        | Date                |
      | IN_PROCESSING | 2025-01-30T13:47:26 |
    E deveria publicar o JSON esperado na fila
      | Queue Name           | Json Key  |
      | authorize-sale-queue | 987654321 |

  Cenario: 02 - Autorizar venda com todos os dados nao informados - Fluxo excepcional
    Dado que seja informado os dados de Sale Request
      | Channel Code | Company Code | Store Code | Pos | Number Order | Total Value | Freight Value |
      |              |              |            |     |              |             |               |
    Quando autorizar venda
    Entao deveria receber os dados de Error Response
      | Http Status | Message                                                                                                                                                                                                                                                                                                                                                                           |
      | BAD_REQUEST | O campo channelCode não foi informado, O campo companyCode não foi informado, O campo customer não foi informado, O campo freightAmount não foi informado, O campo items não foi informado, O campo numberOrder não foi informado, O campo payments não foi informado, O campo pos não foi informado, O campo storeCode não foi informado, O campo totalAmount não foi informado. |
    E nao deveria publicar nenhum JSON na fila
      | Queue Name           |
      | authorize-sale-queue |

  Cenario: 03 - Autorizar venda com os dados Customer nao informados - Fluxo excepcional
    Dado que seja informado os dados de Sale Request
      | Channel Code | Company Code | Store Code | Pos | Number Order | Total Value | Freight Value |
      | APP          | 001          | 100        | 105 | 987654321    | 105.04      | 5.05          |
    E que seja informado os dados de Customer
      | Name | Document | Document Type | Address | Address Number | Address Complement | Neighborhood | City | State | Country | Zip Code | Phone | Email |
      |      |          |               |         |                |                    |              |      |       |         |          |       |       |
    E que seja informado os dados de Shopping Cart Item
      | Code         | Quantity | Value |
      | 100231933559 | 1        | 7.09  |
      | 874631202305 | 2        | 17.68 |
      | 392084657819 | 3        | 19.18 |
    E que seja informado os dados de Payment
      | Payment Method | Payment Date            | Authorization Code | Card Number      | Pix Key | Value  |
      | CREDIT         | 2025-01-30T13:45:01.450 | 270606             | 3556777163651312 |         | 105.04 |
    Quando autorizar venda
    Entao deveria receber os dados de Error Response
      | Http Status | Message                                                                                                                                                                                                                                                                                                                                                                                                                                                          |
      | BAD_REQUEST | O campo customer.address não foi informado, O campo customer.addressNumber não foi informado, O campo customer.city não foi informado, O campo customer.country não foi informado, O campo customer.document não foi informado, O campo customer.documentType não foi informado, O campo customer.name não foi informado, O campo customer.neighborhood não foi informado, O campo customer.state não foi informado, O campo customer.zipCode não foi informado. |
    E nao deveria publicar nenhum JSON na fila
      | Queue Name           |
      | authorize-sale-queue |

  Cenario: 04 - Autorizar venda com os dados Shopping Cart Item nao informados - Fluxo excepcional
    Dado que seja informado os dados de Sale Request
      | Channel Code | Company Code | Store Code | Pos | Number Order | Total Value | Freight Value |
      | APP          | 001          | 100        | 105 | 987654321    | 105.04      | 5.05          |
    E que seja informado os dados de Customer
      | Name              | Document    | Document Type | Address           | Address Number | Address Complement | Neighborhood | City   | State | Country | Zip Code | Phone       | Email                        |
      | Martin Kauê Lopes | 60778532402 | CPF           | Rua Projetada 913 | 622            | Apt 202            | Antares      | Maceió | AL    | Brasil  | 57048434 | 82992344475 | martin_lopes@rafaelmarin.net |
    E que seja informado os dados de Shopping Cart Item
      | Code | Quantity | Value |
      |      |          |       |
    E que seja informado os dados de Payment
      | Payment Method | Payment Date            | Authorization Code | Card Number      | Pix Key | Value  |
      | CREDIT         | 2025-01-30T13:45:01.450 | 270606             | 3556777163651312 |         | 105.04 |
    Quando autorizar venda
    Entao deveria receber os dados de Error Response
      | Http Status | Message                                                                                                                         |
      | BAD_REQUEST | O campo items[0].code não foi informado, O campo items[0].quantity não foi informado, O campo items[0].value não foi informado. |
    E nao deveria publicar nenhum JSON na fila
      | Queue Name           |
      | authorize-sale-queue |

  Cenario: 05 - Autorizar venda com os dados Payment nao informados - Fluxo excepcional
    Dado que seja informado os dados de Sale Request
      | Channel Code | Company Code | Store Code | Pos | Number Order | Total Value | Freight Value |
      | APP          | 001          | 100        | 105 | 987654321    | 105.04      | 5.05          |
    E que seja informado os dados de Customer
      | Name              | Document    | Document Type | Address           | Address Number | Address Complement | Neighborhood | City   | State | Country | Zip Code | Phone       | Email                        |
      | Martin Kauê Lopes | 60778532402 | CPF           | Rua Projetada 913 | 622            | Apt 202            | Antares      | Maceió | AL    | Brasil  | 57048434 | 82992344475 | martin_lopes@rafaelmarin.net |
    E que seja informado os dados de Shopping Cart Item
      | Code         | Quantity | Value |
      | 100231933559 | 1        | 7.09  |
      | 874631202305 | 2        | 17.68 |
      | 392084657819 | 3        | 19.18 |
    E que seja informado os dados de Payment
      | Payment Method | Payment Date | Authorization Code | Card Number | Pix Key | Value |
      |                |              |                    |             |         |       |
    Quando autorizar venda
    Entao deveria receber os dados de Error Response
      | Http Status | Message                                                                                                                                                                                                       |
      | BAD_REQUEST | O campo payments[0].authorizationCode não foi informado, O campo payments[0].paymentDate não foi informado, O campo payments[0].paymentMethod não foi informado, O campo payments[0].value não foi informado. |
    E nao deveria publicar nenhum JSON na fila
      | Queue Name           |
      | authorize-sale-queue |