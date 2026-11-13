package view;

import exception.DadoInvalidoException;
import exception.FuncionarioJaCadastradoException;
import exception.FuncionarioNaoEncontradoException;
import model.Funcionario;
import model.FuncionarioCLT;
import model.FuncionarioComissionado;
import model.FuncionarioPJ;
import service.FuncionarioService;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class FuncionarioView {

    private static final String MENU = """

            ===== FOLHA DE PAGAMENTO =====
            1 - Cadastrar funcionário
            2 - Listar funcionários
            3 - Buscar funcionário (dados completos)
            4 - Remover funcionário
            5 - Relatório da folha
            6 - Total da folha
            0 - Sair""";

    private final FuncionarioService service;
    private final Scanner entrada;
    private final PrintStream saida;

    public FuncionarioView(FuncionarioService service, Scanner entrada, PrintStream saida) {
        this.service = service;
        this.entrada = entrada;
        this.saida = saida;
    }

    public void iniciar() {
        String opcao;
        do {
            saida.println(MENU);
            opcao = ler("Opção");
            try {
                executar(opcao);
            } catch (DadoInvalidoException | FuncionarioNaoEncontradoException
                     | FuncionarioJaCadastradoException e) {
                saida.println("Erro: " + e.getMessage());
            } catch (NumberFormatException e) {
                saida.println("Erro: valor numérico inválido.");
            }
        } while (!opcao.equals("0"));
    }

    private void executar(String opcao) throws FuncionarioJaCadastradoException, FuncionarioNaoEncontradoException {
        switch (opcao) {
            case "1" -> cadastrar();
            case "2" -> listar();
            case "3" -> buscar();
            case "4" -> remover();
            case "5" -> saida.print(service.gerarRelatorio());
            case "6" -> saida.println("Total da folha: " + Funcionario.formatarValor(service.calcularTotalFolha()));
            case "0" -> { }
            default -> saida.println("Opção inválida.");
        }
    }

    private void cadastrar() throws FuncionarioJaCadastradoException {
        String tipo = ler("Tipo (1-CLT, 2-PJ, 3-Comissionado)");
        int codigo = lerInteiro("Código");
        String nome = ler("Nome");
        String cpf = ler("CPF");
        Funcionario funcionario = switch (tipo) {
            case "1" -> new FuncionarioCLT(codigo, nome, cpf,
                    lerDecimal("Salário base"), lerDecimal("Adicional de benefícios"));
            case "2" -> new FuncionarioPJ(codigo, nome, cpf,
                    lerDecimal("Valor por projeto"), lerInteiro("Quantidade de projetos"));
            case "3" -> new FuncionarioComissionado(codigo, nome, cpf,
                    lerDecimal("Salário base"), lerDecimal("Percentual de comissão (0-100)"),
                    lerDecimal("Total de vendas"));
            default -> throw new DadoInvalidoException("Tipo de funcionário inválido.");
        };
        service.cadastrar(funcionario);
        saida.println("Funcionário cadastrado.");
    }

    private void listar() {
        List<Funcionario> funcionarios = service.listar();
        if (funcionarios.isEmpty()) {
            saida.println("Nenhum funcionário cadastrado.");
            return;
        }
        funcionarios.forEach(f -> saida.printf("%d - %s (%s): %s%n",
                f.getCodigo(), f.getNome(), f.getTipo(), Funcionario.formatarValor(f.calcularRemuneracao())));
    }

    private void buscar() throws FuncionarioNaoEncontradoException {
        switch (ler("Buscar por (1-Código, 2-CPF, 3-Nome)")) {
            case "1" -> saida.println(service.buscarFuncionario(lerInteiro("Código")));
            case "2" -> saida.println(service.buscarFuncionario(ler("CPF")));
            case "3" -> service.buscarFuncionario(ler("Nome"), ler("Busca exata (s/n)").equalsIgnoreCase("s"))
                    .forEach(saida::println);
            default -> throw new DadoInvalidoException("Critério de busca inválido.");
        }
    }

    private void remover() throws FuncionarioNaoEncontradoException {
        service.remover(lerInteiro("Código"));
        saida.println("Funcionário removido.");
    }

    private String ler(String rotulo) {
        saida.print(rotulo + ": ");
        return entrada.nextLine().trim();
    }

    private int lerInteiro(String rotulo) {
        return Integer.parseInt(ler(rotulo));
    }

    private double lerDecimal(String rotulo) {
        return Double.parseDouble(ler(rotulo).replace(',', '.'));
    }
}
