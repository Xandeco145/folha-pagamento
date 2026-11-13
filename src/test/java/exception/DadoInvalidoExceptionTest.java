package exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class DadoInvalidoExceptionTest {

    @Test
    void deveGuardarMensagem() {
        assertEquals("inválido", new DadoInvalidoException("inválido").getMessage());
    }

    @Test
    void deveSerExcecaoNaoVerificada() {
        assertTrue(RuntimeException.class.isAssignableFrom(DadoInvalidoException.class));
    }

    @Test
    void deveSerLancavel() {
        assertThrows(DadoInvalidoException.class, () -> {
            throw new DadoInvalidoException("erro");
        });
    }
}
