package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import exception.DadoInvalidoException;
import exception.FuncionarioJaCadastradoException;
import exception.FuncionarioNaoEncontradoException;
import model.Funcionario;
import model.FuncionarioCLT;
import model.FuncionarioComissionado;
import model.FuncionarioPJ;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.FuncionarioRepository;

import java.util.List;

class FuncionarioServiceTest {

    private FuncionarioService service;
    private Funcionario clt;
    private Funcionario pj;
    private Funcionario comissionado;

    @BeforeEach
    void preparar() throws FuncionarioJaCadastradoException {
        service = new FuncionarioService(new FuncionarioRepository());
        clt = new FuncionarioCLT(1, "Ana Souza", "529.982.247-25", 3000, 500);
        pj = new FuncionarioPJ(2, "Ana Paula", "111.444.777-35", 1500, 4);
        comissionado = new FuncionarioComissionado(3, "Diego Lima", "168.995.350-09", 2000, 10, 10000);
        service.cadastrar(clt);
        service.cadastrar(pj);
        service.cadastrar(comissionado);
    }

    @Test
    void deveCadastrarEListar() {
        assertEquals(List.of(clt, pj, comissionado), service.listar());
    }

    @Test
    void deveRejeitarCodigoDuplicado() {
        Funcionario duplicado = new FuncionarioCLT(1, "Outro", "390.533.447-05", 1000, 0);
        assertThrows(FuncionarioJaCadastradoException.class, () -> service.cadastrar(duplicado));
    }

    @Test
    void deveRejeitarFuncionarioNulo() {
        assertThrows(DadoInvalidoException.class, () -> service.cadastrar(null));
    }

    @Test
    void deveBuscarPorCodigo() throws FuncionarioNaoEncontradoException {
        assertSame(pj, service.buscarFuncionario(2));
    }

    @Test
    void deveLancarExcecaoAoBuscarCodigoInexistente() {
        assertThrows(FuncionarioNaoEncontradoException.class, () -> service.buscarFuncionario(99));
    }

    @Test
    void deveBuscarPorCpfComOuSemMascara() throws FuncionarioNaoEncontradoException {
        assertSame(comissionado, service.buscarFuncionario("168.995.350-09"));
        assertSame(comissionado, service.buscarFuncionario("16899535009"));
    }

    @Test
    void deveLancarExcecaoAoBuscarCpfInexistenteOuVazio() {
        assertThrows(FuncionarioNaoEncontradoException.class, () -> service.buscarFuncionario("390.533.447-05"));
        assertThrows(DadoInvalidoException.class, () -> service.buscarFuncionario(" "));
    }

    @Test
    void deveBuscarPorNomeExato() throws FuncionarioNaoEncontradoException {
        assertEquals(List.of(clt), service.buscarFuncionario("ana souza", true));
    }

    @Test
    void deveBuscarPorNomeParcial() throws FuncionarioNaoEncontradoException {
        assertEquals(List.of(clt, pj), service.buscarFuncionario("ana", false));
    }

    @Test
    void deveLancarExcecaoAoBuscarNomeInexistente() {
        assertThrows(FuncionarioNaoEncontradoException.class, () -> service.buscarFuncionario("ana", true));
        assertThrows(FuncionarioNaoEncontradoException.class, () -> service.buscarFuncionario("zzz", false));
    }

    @Test
    void deveRemoverFuncionario() throws FuncionarioNaoEncontradoException {
        service.remover(1);
        assertEquals(2, service.listar().size());
        assertThrows(FuncionarioNaoEncontradoException.class, () -> service.buscarFuncionario(1));
    }

    @Test
    void deveLancarExcecaoAoRemoverInexistente() {
        assertThrows(FuncionarioNaoEncontradoException.class, () -> service.remover(99));
    }

    @Test
    void deveCalcularTotalDaFolhaPolimorficamente() {
        assertEquals(3500.0 + 6000.0 + 3000.0, service.calcularTotalFolha(), 0.001);
    }

    @Test
    void totalDaFolhaVazioDeveSerZero() {
        assertEquals(0.0, new FuncionarioService(new FuncionarioRepository()).calcularTotalFolha(), 0.001);
    }

    @Test
    void deveGerarRelatorioComFuncionariosETotal() {
        String relatorio = service.gerarRelatorio();
        assertTrue(relatorio.contains("Ana Souza"));
        assertTrue(relatorio.contains("Ana Paula"));
        assertTrue(relatorio.contains("Diego Lima"));
        assertTrue(relatorio.contains("Total da folha: R$ 12.500,00"));
    }
}
