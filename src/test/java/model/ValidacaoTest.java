package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import exception.DadoInvalidoException;
import org.junit.jupiter.api.Test;

class ValidacaoTest {

    @Test
    void deveAceitarCpfValidoComOuSemMascara() {
        assertEquals("52998224725", Validacao.cpf("529.982.247-25"));
        assertEquals("52998224725", Validacao.cpf("52998224725"));
    }

    @Test
    void deveRejeitarCpfInvalido() {
        assertThrows(DadoInvalidoException.class, () -> Validacao.cpf("529.982.247-24"));
        assertThrows(DadoInvalidoException.class, () -> Validacao.cpf("111.111.111-11"));
        assertThrows(DadoInvalidoException.class, () -> Validacao.cpf("123"));
        assertThrows(DadoInvalidoException.class, () -> Validacao.cpf(null));
        assertThrows(DadoInvalidoException.class, () -> Validacao.cpf("  "));
    }

    @Test
    void deveValidarTextoObrigatorio() {
        assertEquals("Ana", Validacao.textoObrigatorio("  Ana ", "Nome"));
        assertThrows(DadoInvalidoException.class, () -> Validacao.textoObrigatorio(null, "Nome"));
        assertThrows(DadoInvalidoException.class, () -> Validacao.textoObrigatorio(" ", "Nome"));
    }

    @Test
    void deveValidarCodigo() {
        assertEquals(1, Validacao.codigoPositivo(1));
        assertThrows(DadoInvalidoException.class, () -> Validacao.codigoPositivo(0));
        assertThrows(DadoInvalidoException.class, () -> Validacao.codigoPositivo(-5));
    }

    @Test
    void deveValidarValoresNaoNegativos() {
        assertEquals(0.0, Validacao.naoNegativo(0, "Valor"), 0.001);
        assertThrows(DadoInvalidoException.class, () -> Validacao.naoNegativo(-0.01, "Valor"));
        assertThrows(DadoInvalidoException.class, () -> Validacao.naoNegativo(Double.NaN, "Valor"));
        assertEquals(3, Validacao.quantidadeNaoNegativa(3, "Quantidade"));
        assertThrows(DadoInvalidoException.class, () -> Validacao.quantidadeNaoNegativa(-1, "Quantidade"));
    }

    @Test
    void deveValidarPercentual() {
        assertEquals(0.0, Validacao.percentual(0, "Percentual"), 0.001);
        assertEquals(100.0, Validacao.percentual(100, "Percentual"), 0.001);
        assertThrows(DadoInvalidoException.class, () -> Validacao.percentual(-1, "Percentual"));
        assertThrows(DadoInvalidoException.class, () -> Validacao.percentual(100.1, "Percentual"));
    }
}
