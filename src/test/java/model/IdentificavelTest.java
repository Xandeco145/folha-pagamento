package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class IdentificavelTest {

    @Test
    void todasAsClassesConcretasDevemSerIdentificaveis() {
        Identificavel clt = new FuncionarioCLT(1, "Ana", "529.982.247-25", 1000, 0);
        Identificavel pj = new FuncionarioPJ(2, "Bia", "111.444.777-35", 100, 1);
        Identificavel comissionado = new FuncionarioComissionado(3, "Caio", "390.533.447-05", 1000, 5, 0);

        assertEquals(1, clt.getCodigo());
        assertEquals(2, pj.getCodigo());
        assertEquals(3, comissionado.getCodigo());
    }

    @Test
    void funcionarioDeveImplementarIdentificavel() {
        assertTrue(Identificavel.class.isAssignableFrom(Funcionario.class));
    }
}
