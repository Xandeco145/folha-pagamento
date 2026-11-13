package service;

import exception.DadoInvalidoException;
import exception.FuncionarioJaCadastradoException;
import exception.FuncionarioNaoEncontradoException;
import model.Funcionario;
import model.Validacao;
import repository.FuncionarioRepository;

import java.util.List;

public class FuncionarioService {

    private final FuncionarioRepository repository;

    public FuncionarioService(FuncionarioRepository repository) {
        this.repository = repository;
    }

    public void cadastrar(Funcionario funcionario) throws FuncionarioJaCadastradoException {
        if (funcionario == null) {
            throw new DadoInvalidoException("Funcionário é obrigatório.");
        }
        repository.salvar(funcionario);
    }

    public List<Funcionario> listar() {
        return repository.listarTodos();
    }

    public Funcionario buscarFuncionario(int codigo) throws FuncionarioNaoEncontradoException {
        return repository.buscarPorCodigo(codigo).orElseThrow(() ->
                new FuncionarioNaoEncontradoException("Funcionário com código " + codigo + " não encontrado."));
    }

    public Funcionario buscarFuncionario(String cpf) throws FuncionarioNaoEncontradoException {
        String digitos = Validacao.textoObrigatorio(cpf, "CPF").replaceAll("\\D", "");
        return repository.buscarPorCpf(digitos).orElseThrow(() ->
                new FuncionarioNaoEncontradoException("Funcionário com o CPF informado não encontrado."));
    }

    public List<Funcionario> buscarFuncionario(String nome, boolean buscaExata)
            throws FuncionarioNaoEncontradoException {
        String termo = Validacao.textoObrigatorio(nome, "Nome").toLowerCase();
        List<Funcionario> encontrados = repository.listarTodos().stream()
                .filter(f -> buscaExata ? f.getNome().equalsIgnoreCase(termo)
                        : f.getNome().toLowerCase().contains(termo))
                .toList();
        if (encontrados.isEmpty()) {
            throw new FuncionarioNaoEncontradoException("Nenhum funcionário encontrado para o nome informado.");
        }
        return encontrados;
    }

    public void remover(int codigo) throws FuncionarioNaoEncontradoException {
        if (!repository.remover(codigo)) {
            throw new FuncionarioNaoEncontradoException("Funcionário com código " + codigo + " não encontrado.");
        }
    }

    public double calcularTotalFolha() {
        return repository.listarTodos().stream().mapToDouble(Funcionario::calcularRemuneracao).sum();
    }

    public String gerarRelatorio() {
        StringBuilder relatorio = new StringBuilder("RELATÓRIO DE FOLHA DE PAGAMENTO\n");
        for (Funcionario f : repository.listarTodos()) {
            relatorio.append(String.format("%d | %s | %s | %s%n",
                    f.getCodigo(), f.getNome(), f.getTipo(), Funcionario.formatarValor(f.calcularRemuneracao())));
        }
        relatorio.append("Total da folha: ").append(Funcionario.formatarValor(calcularTotalFolha())).append('\n');
        return relatorio.toString();
    }
}
