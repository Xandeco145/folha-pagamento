package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import exception.DadoInvalidoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FuncionarioComissionadoTest {

    private FuncionarioComissionado funcionario;

    @BeforeEach
    void preparar() {
        funcionario = new FuncionarioComissionado(1, "Diego Lima", "168.995.350-09", 2000.0, 10.0, 10000.0);
    }

    @Test
    void deveCalcularRemuneracaoComComissao() {
        assertEquals(3000.0, funcionario.calcularRemuneracao(), 0.001);
    }

    @Test
    void deveCalcularRemuneracaoSemVendas() {
        funcionario.setTotalVendas(0);
        assertEquals(2000.0, funcionario.calcularRemuneracao(), 0.001);
    }

    @Test
    void deveCalcularTributosESalarioLiquido() {
        assertEquals(330.0, funcionario.calcularTributos(), 0.001);
        assertEquals(2670.0, funcionario.calcularSalarioLiquido(), 0.001);
    }

    @Test
    void deveAtualizarComissaoERecalcular() {
        funcionario.setPercentualComissao(20.0);
        assertEquals(20.0, funcionario.getPercentualComissao(), 0.001);
        assertEquals(10000.0, funcionario.getTotalVendas(), 0.001);
        assertEquals(4000.0, funcionario.calcularRemuneracao(), 0.001);
    }

    @Test
    void deveRejeitarPercentuaisInvalidos() {
        assertThrows(DadoInvalidoException.class,
                () -> new FuncionarioComissionado(1, "Diego", "168.995.350-09", 2000, -1, 0));
        assertThrows(DadoInvalidoException.class,
                () -> new FuncionarioComissionado(1, "Diego", "168.995.350-09", 2000, 101, 0));
        assertThrows(DadoInvalidoException.class, () -> funcionario.setPercentualComissao(150));
    }

    @Test
    void deveRejeitarValoresNegativos() {
        assertThrows(DadoInvalidoException.class,
                () -> new FuncionarioComissionado(1, "Diego", "168.995.350-09", -2000, 10, 0));
        assertThrows(DadoInvalidoException.class,
                () -> new FuncionarioComissionado(1, "Diego", "168.995.350-09", 2000, 10, -1));
        assertThrows(DadoInvalidoException.class, () -> funcionario.setTotalVendas(-1));
    }
}
