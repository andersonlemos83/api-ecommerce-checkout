# language: pt

Funcionalidade: Autorizar Venda

  Cenario de Fundo:
    Dado que o sistema seja executado na seguinte data e hora
      | Year | Month | Day | Hour | Minute | Second |
      | 2025 | 01    | 30  | 13   | 47     | 26     |

  Cenario: 01 - Autorizar venda com todos os dados validos - Sucesso
    Dado que seja informado os dados de Sale Request
      | Channel Code | Company Code | Store Code | Pos | Number Order | Total Amount | Freight Amount |
      | APP          | 001          | 100        | 105 | 987654321-1  | 100.01       | 5.05           |
    E que seja informado os dados de Customer
      | Name              | Document    | Document Type | Address           | Address Number | Address Complement | Neighborhood | City   | State | Country | Zip Code | Phone       | Email                        |
      | Martin Kauê Lopes | 60778532402 | CPF           | Rua Projetada 913 | 622            | Apt 202            | Antares      | Maceió | AL    | Brasil  | 57048434 | 82992344475 | martin_lopes@rafaelmarin.net |
    E que seja informado os dados de Shopping Cart Item
      | Code         | Quantity | Value |
      | 100231933559 | 1        | 7.09  |
      | 874631202305 | 2        | 17.68 |
      | 392084657819 | 3        | 19.18 |
    E que seja informado os dados de Payment
      | Payment Method | Payment Date            | Authorization Code | Card Number      | Nsu    | Value  |
      | CREDIT         | 2025-01-30T13:45:01.450 | 270606             | 3556777163651312 | 123456 | 105.06 |
    Quando autorizar venda
    Entao deveria receber os dados de Sale Response
      | Status        | Date                |
      | IN_PROCESSING | 2025-01-30T13:47:26 |
    E deveria publicar o JSON esperado na fila
      | Queue Name           | Json Key    |
      | authorize-sale-queue | 987654321-1 |

#  Cenario: 02 - Realizar autorizacao venda com todos os dados nao informados - Fluxo excepcional
#    Dado que seja informado os dados de venda request
#      | Canal | Empresa | Loja | Pdv | Total Itens | Quantidade Itens |
#      |       |         |      |     |             |                  |
#    Quando autorizar venda
#    Entao deveria retornar as seguintes mensagens
#      | Http Status | Message                                                                                                                                                                                                                                                                                         |
#      | BAD_REQUEST | O campo canal não foi informado, O campo cliente não foi informado, O campo empresa não foi informado, O campo itens não foi informado, O campo loja não foi informado, O campo ordemPedido não foi informado, O campo quantidadeItens não foi informado, O campo totalItens não foi informado. |
#    E nao deveria publicar nenhum JSON na fila
#      | Nome Fila             |
#      | autorizar-venda-queue |
#
#  Cenario: 03 - Realizar autorizacao venda com todos os dados de ordem pedido nao informados - Fluxo excepcional
#    Dado que seja informado os dados de venda request
#      | Canal | Empresa | Loja | Pdv | Total Itens | Quantidade Itens |
#      | APP   | 00001   | 0001 | 501 | 38744       | 6                |
#    E que seja informado os dados de ordem pedido
#      | Numero Pedido | Numero Ordem Externo | Data Autorizacao |
#      |               |                      |                  |
#    E que seja informado os dados de cliente
#      | Id     | Nome                       | Documento   | Tipo Documento | Tipo Pessoa | Endereco                  | Numero Endereco | Complemento Endereco | Bairro            | Cidade | Estado | Pais | Cep      | Codigo Ibge | Telefone        | Email                                |
#      | 123456 | Givaldo Santos Vasconcelos | 70420816097 | CPF            | F           | Travessa Francisco Vieira | 11              | Apto 405             | Trapiche da Barra | Maceió | AL     | BR   | 57010460 | 7162435     | (82) 36774-7713 | givaldo.santos.vasconcelos@gmail.com |
#    E que seja informado os dados dos itens
#      | Sku       | Quantidade | Valor |
#      | 547170100 | 3          | 5691  |
#      | 557882194 | 2          | 7990  |
#      | 557282711 | 1          | 5691  |
#    Quando autorizar venda
#    Entao deveria retornar as seguintes mensagens
#      | Http Status | Message                                                                                                                                                              |
#      | BAD_REQUEST | O campo ordemPedido.dataAutorizacao não foi informado, O campo ordemPedido.numeroOrdemExterno não foi informado, O campo ordemPedido.numeroPedido não foi informado. |
#    E nao deveria publicar nenhum JSON na fila
#      | Nome Fila             |
#      | autorizar-venda-queue |
#
#  Cenario: 04 - Realizar autorizacao venda com todos os dados de cliente nao informados - Fluxo excepcional
#    Dado que seja informado os dados de venda request
#      | Canal | Empresa | Loja | Pdv | Total Itens | Quantidade Itens |
#      | APP   | 00001   | 0001 | 501 | 38744       | 6                |
#    E que seja informado os dados de ordem pedido
#      | Numero Pedido | Numero Ordem Externo | Data Autorizacao        |
#      | 200010710363  | 2312529489023-1      | 2022-11-11T15:37:56.194 |
#    E que seja informado os dados de cliente
#      | Id | Nome | Documento | Tipo Documento | Tipo Pessoa | Endereco | Numero Endereco | Complemento Endereco | Bairro | Cidade | Estado | Pais | Cep | Codigo Ibge | Telefone | Email |
#      |    |      |           |                |             |          |                 |                      |        |        |        |      |     |             |          |       |
#    E que seja informado os dados dos itens
#      | Sku       | Quantidade | Valor |
#      | 547170100 | 3          | 5691  |
#      | 557882194 | 2          | 7990  |
#      | 557282711 | 1          | 5691  |
#    Quando autorizar venda
#    Entao deveria retornar as seguintes mensagens
#      | Http Status | Message                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     |
#      | BAD_REQUEST | O campo cliente.bairro não foi informado, O campo cliente.cep não foi informado, O campo cliente.cidade não foi informado, O campo cliente.codigoIbge não foi informado, O campo cliente.documento não foi informado, O campo cliente.email não foi informado, O campo cliente.endereco não foi informado, O campo cliente.estado não foi informado, O campo cliente.id não foi informado, O campo cliente.nome não foi informado, O campo cliente.numeroEndereco não foi informado, O campo cliente.pais não foi informado, O campo cliente.tipoDocumento não foi informado, O campo cliente.tipoPessoa não foi informado. |
#    E nao deveria publicar nenhum JSON na fila
#      | Nome Fila             |
#      | autorizar-venda-queue |
#
#  Cenario: 05 - Realizar autorizacao venda com todos os dados de item nao informados - Fluxo excepcional
#    Dado que seja informado os dados de venda request
#      | Canal | Empresa | Loja | Pdv | Total Itens | Quantidade Itens |
#      | APP   | 00001   | 0001 | 501 | 38744       | 6                |
#    E que seja informado os dados de ordem pedido
#      | Numero Pedido | Numero Ordem Externo | Data Autorizacao        |
#      | 200010710363  | 2312529489023-1      | 2022-11-11T15:37:56.194 |
#    E que seja informado os dados de cliente
#      | Id     | Nome                       | Documento   | Tipo Documento | Tipo Pessoa | Endereco                  | Numero Endereco | Complemento Endereco | Bairro            | Cidade | Estado | Pais | Cep      | Codigo Ibge | Telefone        | Email                                |
#      | 123456 | Givaldo Santos Vasconcelos | 70420816097 | CPF            | F           | Travessa Francisco Vieira | 11              | Apto 405             | Trapiche da Barra | Maceió | AL     | BR   | 57010460 | 7162435     | (82) 36774-7713 | givaldo.santos.vasconcelos@gmail.com |
#    E que seja informado os dados dos itens
#      | Sku | Quantidade | Valor |
#      |     |            |       |
#    Quando autorizar venda
#    Entao deveria retornar as seguintes mensagens
#      | Http Status | Message                                                                                                                          |
#      | BAD_REQUEST | O campo itens[0].quantidade não foi informado, O campo itens[0].sku não foi informado, O campo itens[0].valor não foi informado. |
#    E nao deveria publicar nenhum JSON na fila
#      | Nome Fila             |
#      | autorizar-venda-queue |
#
#  Cenario: 06 - Realizar autorizacao venda com todos os dados de ordem pedido nao informados - Fluxo excepcional
#    Dado que seja informado os dados de venda request
#      | Canal | Empresa | Loja | Pdv | Total Itens | Quantidade Itens |
#      | APP   | 00001   | 0001 | 501 | 38744       | 6                |
#    E que seja informado os dados de ordem pedido
#      | Numero Pedido | Numero Ordem Externo | Data Autorizacao        |
#      | 200010710363  | 231252a9489023-1     | 2022-11-11T15:37:56.194 |
#    E que seja informado os dados de cliente
#      | Id     | Nome                       | Documento   | Tipo Documento | Tipo Pessoa | Endereco                  | Numero Endereco | Complemento Endereco | Bairro            | Cidade | Estado | Pais | Cep      | Codigo Ibge | Telefone        | Email                                |
#      | 123456 | Givaldo Santos Vasconcelos | 70420816097 | CPF            | F           | Travessa Francisco Vieira | 11              | Apto 405             | Trapiche da Barra | Maceió | AL     | BR   | 57010460 | 7162435     | (82) 36774-7713 | givaldo.santos.vasconcelos@gmail.com |
#    E que seja informado os dados dos itens
#      | Sku       | Quantidade | Valor |
#      | 547170100 | 3          | 5691  |
#      | 557882194 | 2          | 7990  |
#      | 557282711 | 1          | 5691  |
#    Quando autorizar venda
#    Entao deveria retornar as seguintes mensagens
#      | Http Status | Message                                                                                                                      |
#      | BAD_REQUEST | O campo ordemPedido.numeroOrdemExterno deve segui o padrão numerico positivo com dígito separador (exemplo, 123456789101-1). |
#    E nao deveria publicar nenhum JSON na fila
#      | Nome Fila             |
#      | autorizar-venda-queue |