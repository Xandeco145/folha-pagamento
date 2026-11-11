package model;

import java.util.Locale;

public abstract class Funcionario implements Identificavel {

    private static final Locale PT_BR = Locale.forLanguageTag("pt-BR");

    private final int codigo;
    private String nome;
    private String cpf;
    private double salarioBase;

    protected Funcionario(int codigo, String nome, String cpf, double salarioBase) {
        this.codigo = Validacao.codigoPositivo(codigo);
        this.nome = Validacao.textoObrigatorio(nome, "Nome");
        this.cpf = Validacao.cpf(cpf);
        this.salarioBase = Validacao.naoNegativo(salarioBase, "Salário base");
    }

    public abstract double calcularRemuneracao();

    public double calcularSalarioLiquido() {
        double remuneracao = calcularRemuneracao();
        if (this instanceof Tributavel tributavel) {
            return remuneracao - tributavel.calcularTributos();
        }
        return remuneracao;
    }

    @Override
    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = Validacao.textoObrigatorio(nome, "Nome");
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = Validacao.cpf(cpf);
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(double salarioBase) {
        this.salarioBase = Validacao.naoNegativo(salarioBase, "Salário base");
    }

    public String getTipo() {
        return getClass().getSimpleName().replace("Funcionario", "");
    }

    public static String formatarValor(double valor) {
        return String.format(PT_BR, "R$ %,.2f", valor);
    }

    protected String detalhes() {
        return "Salário base: " + formatarValor(salarioBase);
    }

    @Override
    public String toString() {
        return String.format("Código: %d | Nome: %s | CPF: %s | Tipo: %s | %s | Remuneração: %s | Líquido: %s",
                codigo, nome, cpf, getTipo(), detalhes(),
                formatarValor(calcularRemuneracao()), formatarValor(calcularSalarioLiquido()));
    }
}
