package model;

public class FuncionarioCLT extends Funcionario implements Tributavel {

    /** Alíquota simplificada de desconto (didática), aplicada sobre o salário-base. */
    private static final double ALIQUOTA_DESCONTO = 0.11;

    private double adicionalBeneficios;

    public FuncionarioCLT(int codigo, String nome, String cpf, double salarioBase, double adicionalBeneficios) {
        super(codigo, nome, cpf, salarioBase);
        this.adicionalBeneficios = Validacao.naoNegativo(adicionalBeneficios, "Adicional de benefícios");
    }

    public double getAdicionalBeneficios() {
        return adicionalBeneficios;
    }

    public void setAdicionalBeneficios(double adicionalBeneficios) {
        this.adicionalBeneficios = Validacao.naoNegativo(adicionalBeneficios, "Adicional de benefícios");
    }

    @Override
    public double calcularRemuneracao() {
        return getSalarioBase() + adicionalBeneficios;
    }

    @Override
    public double calcularTributos() {
        return getSalarioBase() * ALIQUOTA_DESCONTO;
    }

    @Override
    protected String detalhes() {
        return super.detalhes() + " | Benefícios: " + formatarValor(adicionalBeneficios);
    }
}
