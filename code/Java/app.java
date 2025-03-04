package code.Java;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class app {
    public static void main(String[] args) {
        Secretaria secretaria = new Secretaria();
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n=== Sistema de Gestão ===");
            System.out.println("1. Adicionar Professor");
            System.out.println("2. Editar Professor");
            System.out.println("3. Remover Professor");
            System.out.println("4. Adicionar Aluno");
            System.out.println("5. Editar Aluno");
            System.out.println("6. Remover Aluno");
            System.out.println("7. Adicionar Disciplina");
            System.out.println("8. Editar Disciplina");
            System.out.println("9. Remover Disciplina");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcao) {
                case 1:
                    System.out.print("Nome do professor: ");
                    String nomeProf = scanner.nextLine();
                    System.out.print("ID do professor: ");
                    String idProf = scanner.nextLine();
                    secretaria.adicionarProfessor(new Professor(nomeProf, idProf));
                    break;
                case 2:
                    System.out.print("ID do professor a editar: ");
                    String idProfEdit = scanner.nextLine();
                    System.out.print("Novo nome: ");
                    String novoNomeProf = scanner.nextLine();
                    secretaria.editarProfessor(idProfEdit, novoNomeProf);
                    break;
                case 3:
                    System.out.print("ID do professor a remover: ");
                    String idProfRemover = scanner.nextLine();
                    secretaria.removerProfessor(idProfRemover);
                    break;
                case 4:
                    System.out.print("Nome do aluno: ");
                    String nomeAluno = scanner.nextLine();
                    System.out.print("ID do aluno: ");
                    String idAluno = scanner.nextLine();
                    secretaria.adicionarAluno(new Aluno(nomeAluno, idAluno));
                    break;
                case 5:
                    System.out.print("ID do aluno a editar: ");
                    String idAlunoEdit = scanner.nextLine();
                    System.out.print("Novo nome: ");
                    String novoNomeAluno = scanner.nextLine();
                    secretaria.editarAluno(idAlunoEdit, novoNomeAluno);
                    break;
                case 6:
                    System.out.print("ID do aluno a remover: ");
                    String idAlunoRemover = scanner.nextLine();
                    secretaria.removerAluno(idAlunoRemover);
                    break;
                case 7:
                int quantAlunos = 0;
                    System.out.print("Nome da disciplina: ");
                    String nomeDisciplina = scanner.nextLine();
                    System.out.print("ID da disciplina: ");
                    int idDisciplina = scanner.nextInt();
                    System.out.print("Créditos da disciplina: ");
                    int credito = scanner.nextInt();
                    System.out.print("Está disponível (true/false): ");
                    scanner.nextLine(); //limpando o troço
                    System.out.print("Tipo da disciplina (OBRIGATORIA/OPTATIVA): ");
                    String tipoDisciplinaStr = scanner.nextLine().toUpperCase();
                    TIPODISCIPLINA tipoDisciplina;
                    try {
                        tipoDisciplina = TIPODISCIPLINA.valueOf(tipoDisciplinaStr);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Tipo inválido. Definindo como OBRIGATORIA por padrão.");
                        tipoDisciplina = TIPODISCIPLINA.OBRIGATORIA;
                    }
                    List<Aluno> alunosMatriculados = new ArrayList<>();
                    secretaria.adicionarDisciplina(new Disciplina(nomeDisciplina, idDisciplina, credito, quantAlunos, tipoDisciplina, alunosMatriculados));
                    break;
                case 8:
                    System.out.print("ID da disciplina a editar: ");
                    int idDisciplinaEdit = scanner.nextInt();
                    scanner.nextLine(); // Limpar buffer
                    System.out.print("Novo nome: ");
                    String novoNomeDisciplina = scanner.nextLine();
                    System.out.print("Novo credito: ");
                    int novoCredito = scanner.nextInt();
                    secretaria.editarDisciplina(idDisciplinaEdit, novoNomeDisciplina, novoCredito);
                    break;
                case 9:
                    System.out.print("ID da disciplina a remover: ");
                    int idDisciplinaRemover = scanner.nextInt();
                    secretaria.removerDisciplina(idDisciplinaRemover);
                    break;
                case 0:
                    System.out.println("Encerrando o sistema...");
                    break;
                default:
                    System.out.println("Opção inválida, tente novamente.");
            }
        } while (opcao != 0);

        scanner.close();
    }
}
