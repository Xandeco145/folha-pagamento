package model;

/** Prestador PJ: não possui salário-base (fixado em zero) e não implementa Tributavel, pois recolhe seus próprios tributos. */
public class FuncionarioPJ extends Funcionario {

    private double valorPorProjeto;
    private int quantidadeProjetos;

    public FuncionarioPJ(int codigo, String nome, String cpf, double valorPorProjeto, int quantidadeProjetos) {
        super(codigo, nome, cpf, 0.0);
        this.valorPorProjeto = Validacao.naoNegativo(valorPorProjeto, "Valor por projeto");
        this.quantidadeProjetos = Validacao.quantidadeNaoNegativa(quantidadeProjetos, "Quantidade de projetos");
    }

    public double getValorPorProjeto() {
        return valorPorProjeto;
    }

    public void setValorPorProjeto(double valorPorProjeto) {
        this.valorPorProjeto = Validacao.naoNegativo(valorPorProjeto, "Valor por projeto");
    }

    public int getQuantidadeProjetos() {
        return quantidadeProjetos;
    }

    public void setQuantidadeProjetos(int quantidadeProjetos) {
        this.quantidadeProjetos = Validacao.quantidadeNaoNegativa(quantidadeProjetos, "Quantidade de projetos");
    }

    @Override
    public double calcularRemuneracao() {
        return valorPorProjeto * quantidadeProjetos;
    }

    @Override
    protected String detalhes() {
        return "Valor por projeto: " + formatarValor(valorPorProjeto) + " | Projetos: " + quantidadeProjetos;
    }
}
