package code.Java;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorUsuarios {
    private static final String ARQUIVO_CSV = "code/Java/DB/Usuarios.csv";

    public static List<Usuario> carregarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_CSV))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                String email = dados[0];
                String senha = dados[1];
                String tipo = dados[2];
                switch (tipo) {
                    case "SECRETARIA":
                        usuarios.add(new Secretaria(email, senha));
                        break;
                    case "ALUNO":
                        usuarios.add(new Aluno(email, senha));
                        break;
                    case "PROFESSOR":
                        usuarios.add(new Professor(email, senha));
                        break;
                    case "GERENTE_FINANCEIRO":
                        usuarios.add(new GerenteFinanceiro(email, senha));
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usuarios;
    }
}