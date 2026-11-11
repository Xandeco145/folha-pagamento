package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import exception.DadoInvalidoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FuncionarioPJTest {

    private FuncionarioPJ funcionario;

    @BeforeEach
    void preparar() {
        funcionario = new FuncionarioPJ(1, "Carla Dias", "390.533.447-05", 1500.0, 4);
    }

    @Test
    void deveCalcularRemuneracaoPorProjetos() {
        assertEquals(6000.0, funcionario.calcularRemuneracao(), 0.001);
    }

    @Test
    void deveManterSalarioBaseZerado() {
        assertEquals(0.0, funcionario.getSalarioBase(), 0.001);
    }

    @Test
    void salarioLiquidoDeveSerIgualARemuneracao() {
        assertEquals(6000.0, funcionario.calcularSalarioLiquido(), 0.001);
    }

    @Test
    void deveAtualizarDadosERecalcular() {
        funcionario.setValorPorProjeto(2000.0);
        funcionario.setQuantidadeProjetos(3);
        assertEquals(2000.0, funcionario.getValorPorProjeto(), 0.001);
        assertEquals(3, funcionario.getQuantidadeProjetos());
        assertEquals(6000.0, funcionario.calcularRemuneracao(), 0.001);
    }

    @Test
    void devePermitirZeroProjetos() {
        funcionario.setQuantidadeProjetos(0);
        assertEquals(0.0, funcionario.calcularRemuneracao(), 0.001);
    }

    @Test
    void deveRejeitarValoresInvalidos() {
        assertThrows(DadoInvalidoException.class,
                () -> new FuncionarioPJ(1, "Carla", "390.533.447-05", -1, 1));
        assertThrows(DadoInvalidoException.class,
                () -> new FuncionarioPJ(1, "Carla", "390.533.447-05", 100, -1));
        assertThrows(DadoInvalidoException.class, () -> funcionario.setValorPorProjeto(-5));
        assertThrows(DadoInvalidoException.class, () -> funcionario.setQuantidadeProjetos(-5));
    }
}
