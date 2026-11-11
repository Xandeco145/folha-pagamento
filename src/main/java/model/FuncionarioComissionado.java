package model;

public class FuncionarioComissionado extends Funcionario implements Tributavel {

    /** Alíquota simplificada de desconto (didática), aplicada sobre a remuneração total. */
    private static final double ALIQUOTA_DESCONTO = 0.11;

    private double percentualComissao;
    private double totalVendas;

    public FuncionarioComissionado(int codigo, String nome, String cpf, double salarioBase,
                                   double percentualComissao, double totalVendas) {
        super(codigo, nome, cpf, salarioBase);
        this.percentualComissao = Validacao.percentual(percentualComissao, "Percentual de comissão");
        this.totalVendas = Validacao.naoNegativo(totalVendas, "Total de vendas");
    }

    public double getPercentualComissao() {
        return percentualComissao;
    }

    public void setPercentualComissao(double percentualComissao) {
        this.percentualComissao = Validacao.percentual(percentualComissao, "Percentual de comissão");
    }

    public double getTotalVendas() {
        return totalVendas;
    }

    public void setTotalVendas(double totalVendas) {
        this.totalVendas = Validacao.naoNegativo(totalVendas, "Total de vendas");
    }

    @Override
    public double calcularRemuneracao() {
        return getSalarioBase() + totalVendas * percentualComissao / 100;
    }

    @Override
    public double calcularTributos() {
        return calcularRemuneracao() * ALIQUOTA_DESCONTO;
    }

    @Override
    protected String detalhes() {
        return super.detalhes() + " | Comissão: " + percentualComissao + "% | Vendas: " + formatarValor(totalVendas);
    }
}
