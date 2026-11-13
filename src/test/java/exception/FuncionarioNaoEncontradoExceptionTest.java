package exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FuncionarioNaoEncontradoExceptionTest {

    @Test
    void deveGuardarMensagem() {
        assertEquals("não encontrado", new FuncionarioNaoEncontradoException("não encontrado").getMessage());
    }

    @Test
    void deveSerExcecaoVerificada() {
        assertFalse(RuntimeException.class.isAssignableFrom(FuncionarioNaoEncontradoException.class));
    }

    @Test
    void deveSerLancavel() {
        assertThrows(FuncionarioNaoEncontradoException.class, () -> {
            throw new FuncionarioNaoEncontradoException("erro");
        });
    }
}
