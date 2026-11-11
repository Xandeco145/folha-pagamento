package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import exception.DadoInvalidoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FuncionarioCLTTest {

    private FuncionarioCLT funcionario;

    @BeforeEach
    void preparar() {
        funcionario = new FuncionarioCLT(1, "Ana Souza", "529.982.247-25", 3000.0, 500.0);
    }

    @Test
    void deveCalcularRemuneracaoComBeneficios() {
        assertEquals(3500.0, funcionario.calcularRemuneracao(), 0.001);
    }

    @Test
    void deveCalcularTributosSobreSalarioBase() {
        assertEquals(330.0, funcionario.calcularTributos(), 0.001);
    }

    @Test
    void deveCalcularSalarioLiquido() {
        assertEquals(3170.0, funcionario.calcularSalarioLiquido(), 0.001);
    }

    @Test
    void deveAtualizarBeneficiosERecalcular() {
        funcionario.setAdicionalBeneficios(1000.0);
        assertEquals(1000.0, funcionario.getAdicionalBeneficios(), 0.001);
        assertEquals(4000.0, funcionario.calcularRemuneracao(), 0.001);
    }

    @Test
    void deveRejeitarBeneficiosNegativos() {
        assertThrows(DadoInvalidoException.class,
                () -> new FuncionarioCLT(1, "Ana", "529.982.247-25", 3000, -1));
        assertThrows(DadoInvalidoException.class, () -> funcionario.setAdicionalBeneficios(-1));
        assertEquals(500.0, funcionario.getAdicionalBeneficios(), 0.001);
    }

    @Test
    void deveRejeitarSalarioNegativo() {
        assertThrows(DadoInvalidoException.class,
                () -> new FuncionarioCLT(1, "Ana", "529.982.247-25", -3000, 0));
    }
}
