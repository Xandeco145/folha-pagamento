package model;

import exception.DadoInvalidoException;

public final class Validacao {

    private Validacao() {
    }

    public static int codigoPositivo(int codigo) {
        if (codigo <= 0) {
            throw new DadoInvalidoException("Código deve ser maior que zero.");
        }
        return codigo;
    }

    public static String textoObrigatorio(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new DadoInvalidoException(campo + " é obrigatório.");
        }
        return valor.trim();
    }

    public static double naoNegativo(double valor, String campo) {
        if (Double.isNaN(valor) || valor < 0) {
            throw new DadoInvalidoException(campo + " não pode ser negativo.");
        }
        return valor;
    }

    public static int quantidadeNaoNegativa(int valor, String campo) {
        if (valor < 0) {
            throw new DadoInvalidoException(campo + " não pode ser negativa.");
        }
        return valor;
    }

    public static double percentual(double valor, String campo) {
        if (Double.isNaN(valor) || valor < 0 || valor > 100) {
            throw new DadoInvalidoException(campo + " deve estar entre 0 e 100.");
        }
        return valor;
    }

    /** Valida o CPF (dígitos verificadores) e devolve apenas os 11 dígitos. */
    public static String cpf(String cpf) {
        String digitos = textoObrigatorio(cpf, "CPF").replaceAll("\\D", "");
        boolean valido = digitos.length() == 11
                && digitos.chars().distinct().count() > 1
                && digitoVerificador(digitos, 9) == digitos.charAt(9) - '0'
                && digitoVerificador(digitos, 10) == digitos.charAt(10) - '0';
        if (!valido) {
            throw new DadoInvalidoException("CPF inválido.");
        }
        return digitos;
    }

    private static int digitoVerificador(String digitos, int quantidade) {
        int soma = 0;
        for (int i = 0; i < quantidade; i++) {
            soma += (digitos.charAt(i) - '0') * (quantidade + 1 - i);
        }
        int resto = (soma * 10) % 11;
        return resto == 10 ? 0 : resto;
    }
}
