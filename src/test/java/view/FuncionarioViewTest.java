package view;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import repository.FuncionarioRepository;
import service.FuncionarioService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

class FuncionarioViewTest {

    private static final String CADASTRO_CLT = "1\n1\n1\nAna Souza\n529.982.247-25\n3000\n500\n";

    private String executar(String entrada) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        FuncionarioService service = new FuncionarioService(new FuncionarioRepository());
        new FuncionarioView(service, new Scanner(entrada),
                new PrintStream(buffer, true, StandardCharsets.UTF_8)).iniciar();
        return buffer.toString(StandardCharsets.UTF_8);
    }

    @Test
    void deveCadastrarEListarFuncionario() {
        String saida = executar(CADASTRO_CLT + "2\n0\n");
        assertTrue(saida.contains("Funcionário cadastrado."));
        assertTrue(saida.contains("1 - Ana Souza (CLT): R$ 3.500,00"));
    }

    @Test
    void deveCadastrarPjEComissionado() {
        String saida = executar("1\n2\n2\nBia\n111.444.777-35\n1500\n4\n"
                + "1\n3\n3\nCaio\n168.995.350-09\n2000\n10\n10000\n"
                + "6\n0\n");
        assertTrue(saida.contains("Total da folha: R$ 9.000,00"));
    }

    @Test
    void deveExibirDadosCompletosNaBuscaPorCodigo() {
        String saida = executar(CADASTRO_CLT + "3\n1\n1\n0\n");
        assertTrue(saida.contains("CPF: 52998224725"));
        assertTrue(saida.contains("Líquido: R$ 3.170,00"));
    }

    @Test
    void deveBuscarPorCpfENome() {
        String saida = executar(CADASTRO_CLT + "3\n2\n529.982.247-25\n3\n3\nana\nn\n0\n");
        assertTrue(saida.indexOf("Nome: Ana Souza") != saida.lastIndexOf("Nome: Ana Souza"));
    }

    @Test
    void deveInformarFuncionarioNaoEncontrado() {
        assertTrue(executar("3\n1\n99\n0\n").contains("não encontrado"));
    }

    @Test
    void deveInformarCpfInvalido() {
        assertTrue(executar("1\n1\n1\nAna\n123\n3000\n500\n0\n").contains("Erro: CPF inválido."));
    }

    @Test
    void deveInformarCodigoDuplicado() {
        assertTrue(executar(CADASTRO_CLT + CADASTRO_CLT + "0\n").contains("Já existe funcionário"));
    }

    @Test
    void deveRemoverFuncionario() {
        String saida = executar(CADASTRO_CLT + "4\n1\n2\n0\n");
        assertTrue(saida.contains("Funcionário removido."));
        assertTrue(saida.contains("Nenhum funcionário cadastrado."));
    }

    @Test
    void deveGerarRelatorio() {
        assertTrue(executar(CADASTRO_CLT + "5\n0\n").contains("RELATÓRIO DE FOLHA DE PAGAMENTO"));
    }

    @Test
    void deveTratarEntradasInvalidas() {
        assertTrue(executar("9\n0\n").contains("Opção inválida."));
        assertTrue(executar("4\nabc\n0\n").contains("valor numérico inválido"));
        assertTrue(executar("1\n7\n1\nAna\n529.982.247-25\n0\n").contains("Tipo de funcionário inválido."));
        assertTrue(executar("3\n9\n0\n").contains("Critério de busca inválido."));
    }
}
