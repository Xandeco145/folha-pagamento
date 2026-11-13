package repository;

import exception.FuncionarioJaCadastradoException;
import model.Funcionario;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FuncionarioRepository {

    private final Map<Integer, Funcionario> funcionarios = new LinkedHashMap<>();

    public void salvar(Funcionario funcionario) throws FuncionarioJaCadastradoException {
        if (funcionarios.containsKey(funcionario.getCodigo())) {
            throw new FuncionarioJaCadastradoException(
                    "Já existe funcionário com o código " + funcionario.getCodigo() + ".");
        }
        if (buscarPorCpf(funcionario.getCpf()).isPresent()) {
            throw new FuncionarioJaCadastradoException("Já existe funcionário com o CPF informado.");
        }
        funcionarios.put(funcionario.getCodigo(), funcionario);
    }

    public Optional<Funcionario> buscarPorCodigo(int codigo) {
        return Optional.ofNullable(funcionarios.get(codigo));
    }

    public Optional<Funcionario> buscarPorCpf(String cpf) {
        return funcionarios.values().stream()
                .filter(funcionario -> funcionario.getCpf().equals(cpf))
                .findFirst();
    }

    public List<Funcionario> listarTodos() {
        return List.copyOf(funcionarios.values());
    }

    public boolean remover(int codigo) {
        return funcionarios.remove(codigo) != null;
    }
}
