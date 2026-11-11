package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import exception.DadoInvalidoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FuncionarioTest {

    private static final String CPF = "529.982.247-25";

    private Funcionario funcionario;

    @BeforeEach
    void preparar() {
        funcionario = new Funcionario(1, "Ana Souza", CPF, 2000.0) {
            @Override
            public double calcularRemuneracao() {
                return getSalarioBase();
            }
        };
    }

    @Test
    void deveArmazenarDadosNormalizandoCpf() {
        assertEquals(1, funcionario.getCodigo());
        assertEquals("Ana Souza", funcionario.getNome());
        assertEquals("52998224725", funcionario.getCpf());
        assertEquals(2000.0, funcionario.getSalarioBase(), 0.001);
    }

    @Test
    void deveRejeitarDadosInvalidosNoConstrutor() {
        assertThrows(DadoInvalidoException.class, () -> new FuncionarioCLT(0, "Ana", CPF, 1000, 0));
        assertThrows(DadoInvalidoException.class, () -> new FuncionarioCLT(1, "", CPF, 1000, 0));
        assertThrows(DadoInvalidoException.class, () -> new FuncionarioCLT(1, "Ana", "123", 1000, 0));
        assertThrows(DadoInvalidoException.class, () -> new FuncionarioCLT(1, "Ana", CPF, -1, 0));
    }

    @Test
    void deveAtualizarDadosPeloSetter() {
        funcionario.setNome("Maria");
        funcionario.setCpf("111.444.777-35");
        funcionario.setSalarioBase(3000);
        assertEquals("Maria", funcionario.getNome());
        assertEquals("11144477735", funcionario.getCpf());
        assertEquals(3000.0, funcionario.getSalarioBase(), 0.001);
    }

    @Test
    void deveRejeitarDadosInvalidosNosSetters() {
        assertThrows(DadoInvalidoException.class, () -> funcionario.setNome(" "));
        assertThrows(DadoInvalidoException.class, () -> funcionario.setCpf("000.000.000-00"));
        assertThrows(DadoInvalidoException.class, () -> funcionario.setSalarioBase(-10));
        assertEquals("Ana Souza", funcionario.getNome());
        assertEquals(2000.0, funcionario.getSalarioBase(), 0.001);
    }

    @Test
    void salarioLiquidoSemTributosDeveSerIgualARemuneracao() {
        assertEquals(2000.0, funcionario.calcularSalarioLiquido(), 0.001);
    }

    @Test
    void deveFormatarValorEmReais() {
        assertEquals("R$ 1.234,50", Funcionario.formatarValor(1234.5));
    }

    @Test
    void toStringDeveConterDadosCompletos() {
        String texto = funcionario.toString();
        assertTrue(texto.contains("Ana Souza"));
        assertTrue(texto.contains("52998224725"));
        assertTrue(texto.contains("R$ 2.000,00"));
    }

    @Test
    void deveExecutarCalculoEspecificoDeCadaSubclasse() {
        Funcionario clt = new FuncionarioCLT(2, "Bruno", "111.444.777-35", 3000, 500);
        Funcionario pj = new FuncionarioPJ(3, "Carla", "390.533.447-05", 1500, 4);
        Funcionario comissionado = new FuncionarioComissionado(4, "Diego", "168.995.350-09", 2000, 10, 10000);

        assertEquals(3500.0, clt.calcularRemuneracao(), 0.001);
        assertEquals(6000.0, pj.calcularRemuneracao(), 0.001);
        assertEquals(3000.0, comissionado.calcularRemuneracao(), 0.001);
        assertNotEquals(clt.calcularRemuneracao(), pj.calcularRemuneracao());
    }

    @Test
    void deveRetornarTipoSemPrefixo() {
        assertEquals("CLT", new FuncionarioCLT(2, "Bruno", "111.444.777-35", 3000, 500).getTipo());
        assertEquals("PJ", new FuncionarioPJ(3, "Carla", "390.533.447-05", 1500, 4).getTipo());
        assertEquals("Comissionado",
                new FuncionarioComissionado(4, "Diego", "168.995.350-09", 2000, 10, 10000).getTipo());
    }
}
