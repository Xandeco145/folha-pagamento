# Sistema de Gerenciamento de Funcionários e Folha de Pagamento

Projeto da atividade de Programação Orientada a Objetos em Java: cadastro de funcionários (CLT, PJ e Comissionado), cálculo de remuneração, tributos e relatório da folha. Armazenamento em memória, interface de console.

## Tecnologias

Java 27, Maven, JUnit 6.

## Estrutura

```
src/main/java/
├── Main.java
├── model/        Funcionario (abstrata), FuncionarioCLT, FuncionarioPJ, FuncionarioComissionado,
│                 Tributavel, Identificavel, Validacao
├── repository/   FuncionarioRepository
├── service/      FuncionarioService
├── view/         FuncionarioView
└── exception/    FuncionarioNaoEncontradoException, FuncionarioJaCadastradoException, DadoInvalidoException
src/test/java/    uma classe de teste para cada classe de produção, nos mesmos pacotes
```

## Regras de remuneração

| Tipo | Remuneração | Tributos (`Tributavel`) |
|---|---|---|
| CLT | salário-base + adicional de benefícios | 11% do salário-base |
| PJ | valor por projeto × quantidade de projetos | não se aplica |
| Comissionado | salário-base + vendas × percentual / 100 | 11% da remuneração |

As alíquotas são simplificadas, para fins didáticos.

## Como executar

Requisitos: JDK 17+ e Maven.

```
mvn compile exec:java
```

Pelo IntelliJ: abrir a classe `Main` e clicar no botão de execução ao lado de `main`.

## Como executar os testes

```
mvn test
```

## Funcionalidades (menu)

Cadastrar, listar, buscar (por código, CPF ou nome, exibindo os dados completos), remover, relatório da folha e total da folha.

## Tratamento de exceções

- `FuncionarioNaoEncontradoException` (verificada): busca ou remoção de funcionário inexistente.
- `FuncionarioJaCadastradoException` (verificada): código ou CPF já cadastrado.
- `DadoInvalidoException` (não verificada): CPF inválido, valores negativos, percentuais fora de 0–100 e campos obrigatórios vazios.
