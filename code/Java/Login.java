package code.Java;

import java.util.List;
import java.util.Scanner;

public class Login {
    public static Usuario fazerLogin() {
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        List<Usuario> usuarios = GerenciadorUsuarios.carregarUsuarios();
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(email) && usuario.getSenha().equals(senha)) {
                return usuario;
            }
        }
        return null;
    }
}