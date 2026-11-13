package repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import exception.FuncionarioJaCadastradoException;
import model.Funcionario;
import model.FuncionarioCLT;
import model.FuncionarioPJ;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class FuncionarioRepositoryTest {

    private FuncionarioRepository repository;
    private Funcionario ana;
    private Funcionario bruno;

    @BeforeEach
    void preparar() throws FuncionarioJaCadastradoException {
        repository = new FuncionarioRepository();
        ana = new FuncionarioCLT(1, "Ana", "529.982.247-25", 3000, 0);
        bruno = new FuncionarioPJ(2, "Bruno", "111.444.777-35", 1000, 2);
        repository.salvar(ana);
        repository.salvar(bruno);
    }

    @Test
    void deveBuscarPorCodigo() {
        assertSame(ana, repository.buscarPorCodigo(1).orElseThrow());
        assertTrue(repository.buscarPorCodigo(99).isEmpty());
    }

    @Test
    void deveBuscarPorCpf() {
        assertSame(bruno, repository.buscarPorCpf("11144477735").orElseThrow());
        assertTrue(repository.buscarPorCpf("39053344705").isEmpty());
    }

    @Test
    void deveListarNaOrdemDeCadastro() {
        assertEquals(List.of(ana, bruno), repository.listarTodos());
    }

    @Test
    void listaRetornadaNaoDevePermitirAlteracao() {
        List<Funcionario> lista = repository.listarTodos();
        assertThrows(UnsupportedOperationException.class, () -> lista.remove(0));
    }

    @Test
    void deveRejeitarCodigoDuplicado() {
        Funcionario outro = new FuncionarioCLT(1, "Carla", "390.533.447-05", 1000, 0);
        assertThrows(FuncionarioJaCadastradoException.class, () -> repository.salvar(outro));
        assertEquals(2, repository.listarTodos().size());
    }

    @Test
    void deveRejeitarCpfDuplicado() {
        Funcionario outro = new FuncionarioCLT(3, "Carla", "529.982.247-25", 1000, 0);
        assertThrows(FuncionarioJaCadastradoException.class, () -> repository.salvar(outro));
    }

    @Test
    void deveRemoverFuncionarioExistente() {
        assertTrue(repository.remover(1));
        assertTrue(repository.buscarPorCodigo(1).isEmpty());
        assertEquals(1, repository.listarTodos().size());
    }

    @Test
    void deveRetornarFalsoAoRemoverInexistente() {
        assertFalse(repository.remover(99));
    }
}
