package code.Java;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class app {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Login ===");
        Usuario usuario = Login.fazerLogin();

        if (usuario == null) {
            System.out.println("Email ou senha incorretos. Encerrando o sistema.");
            scanner.close();
            return;
        }

        if (usuario.getTipo().equals("ALUNO")) {
            // Lógica do usuário aluno aqui junto com seu switch e ETC
        }

        if (usuario.getTipo().equals("PROFESSOR")) {
            Professor professor = new Professor(usuario.getEmail(), usuario.getSenha());
            int opcao = -1;
            do {
                System.out.println("\n=== Sistema dos professores ===");
                System.out.println("1. Verificar alunos");
                System.out.println("0. Sair");
                System.out.print("Escolha uma opção: ");

                if (scanner.hasNextInt()) {
                    opcao = scanner.nextInt();
                    scanner.nextLine();
                } else {
                    System.out.println("Entrada inválida! Digite um número.");
                    scanner.next();
                    continue;
                }

                switch (opcao) {
                    case 1:
                        
                        break;
                    case 0:
                        System.out.println("Encerrando a aplicação.");
                        break;
                    default:
                        System.out.println("Opção inválida, tente novamente.");
                }
            } while (opcao != 0);
        }
    

        if (usuario.getTipo().equals("SECRETARIA")) {
            Secretaria secretaria = new Secretaria(usuario.getEmail(), usuario.getSenha());
            int opcao = -1;

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
                System.out.println("10. Gerar Currículo");
                System.out.println("0. Sair");
                System.out.print("Escolha uma opção: ");

                if (scanner.hasNextInt()) {
                    opcao = scanner.nextInt();
                    scanner.nextLine(); // Limpa o buffer do Scanner
                } else {
                    System.out.println("Entrada inválida! Digite um número.");
                    scanner.next(); // Descarta entrada inválida
                    continue;
                }

                switch (opcao) {
                    case 1:
                        System.out.print("Nome do professor: ");
                        String nomeProf = scanner.nextLine();
                        System.out.print("ID do professor: ");
                        String idProf = scanner.nextLine();
                        System.out.print("Email do professor: ");
                        String emailProf = scanner.nextLine();
                        System.out.print("Senha do professor: ");
                        String senhaProf = scanner.nextLine();
                        secretaria.adicionarProfessor(new Professor(emailProf, senhaProf, nomeProf, idProf));
                        Secretaria.salvarUsuario(new Usuario(emailProf, senhaProf, "PROFESSOR"));
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
                    System.out.print("Email do aluno: ");
                    String emailAlun = scanner.nextLine();
                    System.out.print("Senha do aluno: ");
                    String senhaAlun = scanner.nextLine();

                    System.out.println("Disciplinas disponíveis:");
                    List<Disciplina> disciplinasDisponiveis = secretaria.listarDisciplinas();
                    for (Disciplina disciplina : disciplinasDisponiveis) {
                        System.out.println("ID: " + disciplina.getId() + " - " + disciplina.getNome());
                    }

                    List<Disciplina> disciplinasSelecionadas = new ArrayList<>();
                    System.out.print("Digite os IDs das disciplinas que deseja se matricular (separados por vírgula): ");
                    String idsDisciplinas = scanner.nextLine();
                    String[] ids = idsDisciplinas.split(",");
                    for (String id : ids) {
                        int idDisciplina = Integer.parseInt(id.trim());
                        Disciplina disciplinaSelecionada = disciplinasDisponiveis.stream()
                                .filter(d -> d.getId() == idDisciplina)
                                .findFirst()
                                .orElse(null);
                        if (disciplinaSelecionada != null) {
                            disciplinasSelecionadas.add(disciplinaSelecionada);
                        } else {
                            System.out.println("Disciplina com ID " + idDisciplina + " não encontrada.");
                        }
                    }

                    // Criar o aluno com as disciplinas selecionadas
                    Aluno aluno = new Aluno(emailAlun, senhaAlun, nomeAluno, idAluno);
                    aluno.setDisciplinasMatriculadas(disciplinasSelecionadas);

                    // Adicionar o aluno ao banco de dados
                    secretaria.adicionarAluno(aluno);
                    Secretaria.salvarUsuario(new Usuario(emailAlun, senhaAlun, "ALUNO"));
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
                        System.out.print("Nome da disciplina: ");
                        String nomeDisciplina = scanner.nextLine();
                        System.out.print("ID da disciplina: ");
                        int idDisciplina = scanner.nextInt();
                        scanner.nextLine(); // Limpa o buffer
                        System.out.print("Créditos da disciplina: ");
                        int credito = scanner.nextInt();
                        scanner.nextLine(); // Limpa o buffer
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
                        secretaria.adicionarDisciplina(new Disciplina(nomeDisciplina, idDisciplina, credito, tipoDisciplina, alunosMatriculados));
                        break;
                    case 8:
                        System.out.print("ID da disciplina a editar: ");
                        int idDisciplinaEdit = scanner.nextInt();
                        scanner.nextLine(); // Limpa o buffer
                        System.out.print("Novo nome: ");
                        String novoNomeDisciplina = scanner.nextLine();
                        System.out.print("Novo crédito: ");
                        int novoCredito = scanner.nextInt();
                        scanner.nextLine(); // Limpa o buffer
                        secretaria.editarDisciplina(idDisciplinaEdit, novoNomeDisciplina, novoCredito);
                        break;
                    case 9:
                        System.out.print("ID da disciplina a remover: ");
                        int idDisciplinaRemover = scanner.nextInt();
                        scanner.nextLine(); // Limpa o buffer
                        secretaria.removerDisciplina(idDisciplinaRemover);
                        break;
                    case 10:
                        System.out.println("Gerando currículo...");
                        secretaria.gerarCurriculo();
                        System.out.println("Currículo gerado em formato txt.");
                        break;
                    case 0:
                        System.out.println("Encerrando a aplicação.");
                        break;
                    default:
                        System.out.println("Opção inválida, tente novamente.");
                }
            } while (opcao != 0);
        }

        scanner.close();
    }
}