# language: pt

Funcionalidade: Autorizar Venda

  Cenario de Fundo:
    Dado que o sistema seja executado na seguinte data e hora
      | Ano  | Mes | Dia | Hora | Minuto | Segundo |
      | 2022 | 11  | 11  | 15   | 47     | 10      |

  Cenario: 01 - Realizar autorizacao venda com todos os dados validos - Sucesso
    Dado que seja informado os dados de venda request
      | Canal | Empresa | Loja | Pdv | Total Itens | Quantidade Itens |
      | APP   | 00001   | 0001 | 501 | 38744       | 6                |
    E que seja informado os dados de ordem pedido
      | Numero Pedido | Numero Ordem Externo | Data Autorizacao        |
      | 200010710363  | 2312529489023-1      | 2022-11-11T15:37:56.194 |
    E que seja informado os dados de cliente
      | Id     | Nome                       | Documento   | Tipo Documento | Tipo Pessoa | Endereco                  | Numero Endereco | Complemento Endereco | Bairro            | Cidade | Estado | Pais | Cep      | Codigo Ibge | Telefone        | Email                                |
      | 123456 | Givaldo Santos Vasconcelos | 70420816097 | CPF            | F           | Travessa Francisco Vieira | 11              | Apto 405             | Trapiche da Barra | Maceió | AL     | BR   | 57010460 | 7162435     | (82) 36774-7713 | givaldo.santos.vasconcelos@gmail.com |
    E que seja informado os dados dos itens
      | Sku       | Quantidade | Valor |
      | 547170100 | 3          | 5691  |
      | 557882194 | 2          | 7990  |
      | 557282711 | 1          | 5691  |
    Quando autorizar venda
    Entao deveria receber os dados de venda response
      | Status           | Data Resposta       |
      | EM_PROCESSAMENTO | 2022-11-11T15:47:10 |
    E deveria publicar o Json Venda Request na fila "autorizar-venda-queue"

  Cenario: 02 - Realizar autorizacao venda com todos os dados nao informados - Fluxo excepcional
    Dado que seja informado os dados de venda request
      | Canal | Empresa | Loja | Pdv | Total Itens | Quantidade Itens |
      |       |         |      |     |             |                  |
    Quando autorizar venda
    Entao deveria retornar as seguintes mensagens
      | Http Status | Message                                                                                                                                                                                                                                                                                         |
      | BAD_REQUEST | O campo canal não foi informado, O campo cliente não foi informado, O campo empresa não foi informado, O campo itens não foi informado, O campo loja não foi informado, O campo ordemPedido não foi informado, O campo quantidadeItens não foi informado, O campo totalItens não foi informado. |
    E nao deveria publicar nenhum JSON na fila
      | Nome Fila             |
      | autorizar-venda-queue |

  Cenario: 03 - Realizar autorizacao venda com todos os dados de ordem pedido nao informados - Fluxo excepcional
    Dado que seja informado os dados de venda request
      | Canal | Empresa | Loja | Pdv | Total Itens | Quantidade Itens |
      | APP   | 00001   | 0001 | 501 | 38744       | 6                |
    E que seja informado os dados de ordem pedido
      | Numero Pedido | Numero Ordem Externo | Data Autorizacao |
      |               |                      |                  |
    E que seja informado os dados de cliente
      | Id     | Nome                       | Documento   | Tipo Documento | Tipo Pessoa | Endereco                  | Numero Endereco | Complemento Endereco | Bairro            | Cidade | Estado | Pais | Cep      | Codigo Ibge | Telefone        | Email                                |
      | 123456 | Givaldo Santos Vasconcelos | 70420816097 | CPF            | F           | Travessa Francisco Vieira | 11              | Apto 405             | Trapiche da Barra | Maceió | AL     | BR   | 57010460 | 7162435     | (82) 36774-7713 | givaldo.santos.vasconcelos@gmail.com |
    E que seja informado os dados dos itens
      | Sku       | Quantidade | Valor |
      | 547170100 | 3          | 5691  |
      | 557882194 | 2          | 7990  |
      | 557282711 | 1          | 5691  |
    Quando autorizar venda
    Entao deveria retornar as seguintes mensagens
      | Http Status | Message                                                                                                                                                              |
      | BAD_REQUEST | O campo ordemPedido.dataAutorizacao não foi informado, O campo ordemPedido.numeroOrdemExterno não foi informado, O campo ordemPedido.numeroPedido não foi informado. |
    E nao deveria publicar nenhum JSON na fila
      | Nome Fila             |
      | autorizar-venda-queue |

  Cenario: 04 - Realizar autorizacao venda com todos os dados de cliente nao informados - Fluxo excepcional
    Dado que seja informado os dados de venda request
      | Canal | Empresa | Loja | Pdv | Total Itens | Quantidade Itens |
      | APP   | 00001   | 0001 | 501 | 38744       | 6                |
    E que seja informado os dados de ordem pedido
      | Numero Pedido | Numero Ordem Externo | Data Autorizacao        |
      | 200010710363  | 2312529489023-1      | 2022-11-11T15:37:56.194 |
    E que seja informado os dados de cliente
      | Id | Nome | Documento | Tipo Documento | Tipo Pessoa | Endereco | Numero Endereco | Complemento Endereco | Bairro | Cidade | Estado | Pais | Cep | Codigo Ibge | Telefone | Email |
      |    |      |           |                |             |          |                 |                      |        |        |        |      |     |             |          |       |
    E que seja informado os dados dos itens
      | Sku       | Quantidade | Valor |
      | 547170100 | 3          | 5691  |
      | 557882194 | 2          | 7990  |
      | 557282711 | 1          | 5691  |
    Quando autorizar venda
    Entao deveria retornar as seguintes mensagens
      | Http Status | Message                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     |
      | BAD_REQUEST | O campo cliente.bairro não foi informado, O campo cliente.cep não foi informado, O campo cliente.cidade não foi informado, O campo cliente.codigoIbge não foi informado, O campo cliente.documento não foi informado, O campo cliente.email não foi informado, O campo cliente.endereco não foi informado, O campo cliente.estado não foi informado, O campo cliente.id não foi informado, O campo cliente.nome não foi informado, O campo cliente.numeroEndereco não foi informado, O campo cliente.pais não foi informado, O campo cliente.tipoDocumento não foi informado, O campo cliente.tipoPessoa não foi informado. |
    E nao deveria publicar nenhum JSON na fila
      | Nome Fila             |
      | autorizar-venda-queue |

  Cenario: 05 - Realizar autorizacao venda com todos os dados de item nao informados - Fluxo excepcional
    Dado que seja informado os dados de venda request
      | Canal | Empresa | Loja | Pdv | Total Itens | Quantidade Itens |
      | APP   | 00001   | 0001 | 501 | 38744       | 6                |
    E que seja informado os dados de ordem pedido
      | Numero Pedido | Numero Ordem Externo | Data Autorizacao        |
      | 200010710363  | 2312529489023-1      | 2022-11-11T15:37:56.194 |
    E que seja informado os dados de cliente
      | Id     | Nome                       | Documento   | Tipo Documento | Tipo Pessoa | Endereco                  | Numero Endereco | Complemento Endereco | Bairro            | Cidade | Estado | Pais | Cep      | Codigo Ibge | Telefone        | Email                                |
      | 123456 | Givaldo Santos Vasconcelos | 70420816097 | CPF            | F           | Travessa Francisco Vieira | 11              | Apto 405             | Trapiche da Barra | Maceió | AL     | BR   | 57010460 | 7162435     | (82) 36774-7713 | givaldo.santos.vasconcelos@gmail.com |
    E que seja informado os dados dos itens
      | Sku | Quantidade | Valor |
      |     |            |       |
    Quando autorizar venda
    Entao deveria retornar as seguintes mensagens
      | Http Status | Message                                                                                                                          |
      | BAD_REQUEST | O campo itens[0].quantidade não foi informado, O campo itens[0].sku não foi informado, O campo itens[0].valor não foi informado. |
    E nao deveria publicar nenhum JSON na fila
      | Nome Fila             |
      | autorizar-venda-queue |

  Cenario: 06 - Realizar autorizacao venda com todos os dados de ordem pedido nao informados - Fluxo excepcional
    Dado que seja informado os dados de venda request
      | Canal | Empresa | Loja | Pdv | Total Itens | Quantidade Itens |
      | APP   | 00001   | 0001 | 501 | 38744       | 6                |
    E que seja informado os dados de ordem pedido
      | Numero Pedido | Numero Ordem Externo | Data Autorizacao        |
      | 200010710363  | 231252a9489023-1     | 2022-11-11T15:37:56.194 |
    E que seja informado os dados de cliente
      | Id     | Nome                       | Documento   | Tipo Documento | Tipo Pessoa | Endereco                  | Numero Endereco | Complemento Endereco | Bairro            | Cidade | Estado | Pais | Cep      | Codigo Ibge | Telefone        | Email                                |
      | 123456 | Givaldo Santos Vasconcelos | 70420816097 | CPF            | F           | Travessa Francisco Vieira | 11              | Apto 405             | Trapiche da Barra | Maceió | AL     | BR   | 57010460 | 7162435     | (82) 36774-7713 | givaldo.santos.vasconcelos@gmail.com |
    E que seja informado os dados dos itens
      | Sku       | Quantidade | Valor |
      | 547170100 | 3          | 5691  |
      | 557882194 | 2          | 7990  |
      | 557282711 | 1          | 5691  |
    Quando autorizar venda
    Entao deveria retornar as seguintes mensagens
      | Http Status | Message                                                                                                                      |
      | BAD_REQUEST | O campo ordemPedido.numeroOrdemExterno deve segui o padrão numerico positivo com dígito separador (exemplo, 123456789101-1). |
    E nao deveria publicar nenhum JSON na fila
      | Nome Fila             |
      | autorizar-venda-queue |