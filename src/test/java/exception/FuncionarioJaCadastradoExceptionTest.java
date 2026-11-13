package exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FuncionarioJaCadastradoExceptionTest {

    @Test
    void deveGuardarMensagem() {
        assertEquals("duplicado", new FuncionarioJaCadastradoException("duplicado").getMessage());
    }

    @Test
    void deveSerExcecaoVerificada() {
        assertFalse(RuntimeException.class.isAssignableFrom(FuncionarioJaCadastradoException.class));
    }

    @Test
    void deveSerLancavel() {
        assertThrows(FuncionarioJaCadastradoException.class, () -> {
            throw new FuncionarioJaCadastradoException("erro");
        });
    }
}
