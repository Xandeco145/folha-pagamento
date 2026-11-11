import repository.FuncionarioRepository;
import service.FuncionarioService;
import view.FuncionarioView;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        FuncionarioService service = new FuncionarioService(new FuncionarioRepository());
        new FuncionarioView(service, new Scanner(System.in), System.out).iniciar();
    }
}
