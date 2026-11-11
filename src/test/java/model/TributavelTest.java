package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TributavelTest {

    @Test
    void cltEComissionadoDevemSerTributaveis() {
        assertTrue(Tributavel.class.isAssignableFrom(FuncionarioCLT.class));
        assertTrue(Tributavel.class.isAssignableFrom(FuncionarioComissionado.class));
    }

    @Test
    void pjNaoDeveSerTributavel() {
        assertFalse(Tributavel.class.isAssignableFrom(FuncionarioPJ.class));
    }

    @Test
    void deveCalcularTributosPelaInterface() {
        Tributavel clt = new FuncionarioCLT(1, "Ana", "529.982.247-25", 1000, 200);
        Tributavel comissionado = new FuncionarioComissionado(2, "Caio", "390.533.447-05", 1000, 10, 5000);

        assertEquals(110.0, clt.calcularTributos(), 0.001);
        assertEquals(165.0, comissionado.calcularTributos(), 0.001);
    }
}
