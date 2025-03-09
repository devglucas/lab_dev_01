package code.Java;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class app {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("");
        System.out.println("=== Login ===");
        Usuario usuario = Login.fazerLogin();

        if (usuario == null) {
            System.out.println("Email ou senha incorretos. Encerrando o sistema.");
            scanner.close();
            return;
        }

        if (usuario.getTipo().equals("GERENTE_FINANCEIRO")) {

            int opcao = -1;
            do {
                System.out.println("\n=== Sistema do gerente financeiro ===");
                System.out.println("1. Verificar Nota Fiscal");
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
                    List<NotaFiscal> notasFiscais = NotaFiscal.listarNotasFiscais();
                    for (NotaFiscal notaFiscal : notasFiscais) {
                        System.out.println("ID: " + notaFiscal.getId() + " - " + notaFiscal.getValor() + " - " + notaFiscal.getAluno().toString());
                    }
                    System.out.print("Digite o ID da nota fiscal a ser verificada: ");
                        int idNotaFiscal = scanner.nextInt();
                        scanner.nextLine(); 

                        for (NotaFiscal notaFiscal : notasFiscais) {
                            if (notaFiscal.getId() == idNotaFiscal) {
                                notaFiscal.setEstahVerificado(true);
                                System.out.println("Nota fiscal ID " + idNotaFiscal + " verificada com sucesso.");
                                break;
                            }
                        }
                        GerenteFinanceiro.escreverNotasFiscais(notasFiscais);
                        break;
                    case 0:
                        System.out.println("Encerrando a aplicação.");
                        break;
                    default:
                        System.out.println("Opção inválida, tente novamente.");
                }
            } while (opcao != 0);

        }

        if (usuario.getTipo().equals("ALUNO")) {
    Aluno aluno = Aluno.buscarAlunoPorEmail(usuario.getEmail());
    int opcao = -1;
    do {
        System.out.println("\n=== Sistema do Aluno ===");
        System.out.println("1. Solicitar matrícula");
        System.out.println("2. Solicitar cancelamento de matrícula");
        System.out.println("3. Ver Disciplinas Matriculadas");
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
                System.out.println("Disciplinas disponíveis:");
                List<Disciplina> disciplinasDisponiveis = Curso.listarDisciplinasPorCurso(aluno.getCurso().toString());
                for (Disciplina disciplina : disciplinasDisponiveis) {
                    System.out.println("ID: " + disciplina.getId() + " - " + disciplina.getNome() + " (" + disciplina.getTipoDisciplina() + ")");
                }

                System.out.print("Digite o ID da disciplina que deseja se matricular: ");
                int idDisciplina = scanner.nextInt();
                scanner.nextLine();
                Disciplina disciplina = Disciplina.buscarDisciplinaPorId(idDisciplina);
                if (disciplina == null) {
                    System.out.println("Disciplina não encontrada.");
                } else {
                    aluno.solicitarMatricula(disciplina);
                }
                break;

                case 2:
                System.out.println("Disciplinas disponíveis para o cancelamento:");
                if (aluno.getDisciplinasMatriculadas() == null || aluno.getDisciplinasMatriculadas().isEmpty()) {
                    System.out.println("Você não está matriculado em nenhuma disciplina.");
                } else {
                    for (Disciplina disciplinaMatriculada : aluno.getDisciplinasMatriculadas()) {
                        System.out.println("ID: " + disciplinaMatriculada.getId() + " - " + disciplinaMatriculada.getNome() + " (" + disciplinaMatriculada.getTipoDisciplina() + ")");
                    }
            
                    System.out.print("Digite o ID da disciplina que deseja cancelar: ");
                    int idDisciplinaParaCancelar = scanner.nextInt();
                    scanner.nextLine(); // Limpa o buffer
                    Disciplina disciplinaParaCancelar = Disciplina.buscarDisciplinaPorId(idDisciplinaParaCancelar);
                    if (disciplinaParaCancelar == null) {
                        System.out.println("Disciplina não encontrada.");
                    } else {
                        aluno.cancelarMatricula(disciplinaParaCancelar);
                    }
                }
                break;
            case 3:
                System.out.println("Disciplinas matriculadas:");
                if(aluno.getDisciplinasMatriculadas() == null){
                        throw new IllegalArgumentException("O aluno nao possui nenhuma disciplina matriculada.");
                }

                for (Disciplina disciplinaMatriculada : aluno.getDisciplinasMatriculadas()) {
                    System.out.println("ID: " + disciplinaMatriculada.getId() + " - " + disciplinaMatriculada.getNome() + " (" + disciplinaMatriculada.getTipoDisciplina() + ")");
                }
                break;
            case 0:
                System.out.println("Encerrando a aplicação.");
                break;
            default:
                System.out.println("Opção inválida, tente novamente.");
        }
    } while (opcao != 0);
}

        if (usuario.getTipo().equals("PROFESSOR")) {
            Professor professor = Professor.buscarProfessorPorEmail(usuario.getEmail());
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
                    professor.listarAlunosDasDisciplinas();
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
                System.out.println("2. Adicionar Disciplina a Professor");
                System.out.println("3. Editar Professor");
                System.out.println("4. Remover Professor");
                System.out.println("5. Adicionar Aluno");
                System.out.println("6. Editar Aluno");
                System.out.println("7. Remover Aluno");
                System.out.println("8. Adicionar Disciplina");
                System.out.println("9. Editar Disciplina");
                System.out.println("10. Remover Disciplina");
                System.out.println("11. Gerar Currículo");
                System.out.println("12. Adicionar Curso");
                System.out.println("13. Listar todo os cursos");
                System.out.println("14. Remover Curso");
                System.out.println("15. Gerar Nota Fiscal");
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
                        System.out.print("Nome do professor: ");
                        String nomeProf = scanner.nextLine();
                        System.out.print("Matricula do professor: ");
                        String idMatProf = scanner.nextLine();
                        System.out.print("Email do professor: ");
                        String emailProf = scanner.nextLine();
                        System.out.print("Senha do professor: ");
                        String senhaProf = scanner.nextLine();
                        secretaria.adicionarProfessor(new Professor(emailProf, senhaProf, nomeProf, idMatProf));
                        Secretaria.salvarUsuario(new Usuario(emailProf, senhaProf, "PROFESSOR"));
                        break;


                        case 2:
                        System.out.println("Disciplinas disponíveis:");
                        List<Disciplina> disciplinasDisponiveis = Disciplina.listarDisciplinas();
                        for (Disciplina disciplina : disciplinasDisponiveis) {
                            System.out.println("ID: " + disciplina.getId() + " - " + disciplina.getNome());
                        }
                        System.out.print("Matrícula do professor: ");
                        String matriculaProf = scanner.nextLine();
                        System.out.print("ID da disciplina a adicionar: ");
                        int idDisciplinaProf = scanner.nextInt();

                        scanner.nextLine(); 
                        secretaria.adicionarDisciplinaAoProfessor(matriculaProf, idDisciplinaProf);
                        break;


                    case 3:
                        System.out.print("ID do professor a editar: ");
                        String idProfEdit = scanner.nextLine();
                        System.out.print("Novo nome: ");
                        String novoNomeProf = scanner.nextLine();
                        secretaria.editarProfessor(idProfEdit, novoNomeProf);
                        break;


                    case 4:
                        System.out.print("ID do professor a remover: ");
                        String idProfRemover = scanner.nextLine();
                        secretaria.removerProfessor(idProfRemover);
                        break;


                    case 5:
                    System.out.print("Nome do aluno: ");
                    String nomeAluno = scanner.nextLine();
                    System.out.print("ID do aluno: ");
                    String idAluno = scanner.nextLine();
                    System.out.print("Email do aluno: ");
                    String emailAlun = scanner.nextLine();
                    System.out.print("Senha do aluno: ");
                    String senhaAlun = scanner.nextLine();
                    System.out.print("Nome do curso do aluno: ");
                    String nomeCurso1 = scanner.nextLine();

                    Curso cursoAluno = Curso.listarCursos().stream()
                            .filter(c -> c.getNome().equals(nomeCurso1))
                            .findFirst()
                            .orElse(null);

                    System.out.println("Disciplinas disponíveis:");
                    List<Disciplina> disciplinasDisponiveis2 = Curso.listarDisciplinasPorCurso(nomeCurso1);
                    for (Disciplina disciplina : disciplinasDisponiveis2) {
                        System.out.println("ID: " + disciplina.getId() + " - " + disciplina.getNome());
                    }

                    List<Disciplina> disciplinasSelecionadas = new ArrayList<>();
                    System.out.print("Digite os IDs das disciplinas que deseja se matricular (separados por vírgula): ");
                    String idsDisciplinas = scanner.nextLine();
                    String[] ids = idsDisciplinas.split(",");
                    for (String id : ids) {
                        int idDisciplina = Integer.parseInt(id.trim());
                        Disciplina disciplinaSelecionada = disciplinasDisponiveis2.stream()
                                .filter(d -> d.getId() == idDisciplina)
                                .findFirst()
                                .orElse(null);
                        if (disciplinaSelecionada != null) {
                            disciplinasSelecionadas.add(disciplinaSelecionada);
                        } else {
                            System.out.println("Disciplina com ID " + idDisciplina + " não encontrada.");
                        }
                    }

                    Aluno aluno = new Aluno(emailAlun, senhaAlun, nomeAluno, cursoAluno, idAluno, disciplinasSelecionadas);
                    aluno.setDisciplinasMatriculadas(disciplinasSelecionadas);

                    secretaria.adicionarAluno(aluno);
                    Secretaria.salvarUsuario(new Usuario(emailAlun, senhaAlun, "ALUNO"));
                    break;


                    case 6:
                        System.out.print("ID do aluno a editar: ");
                        String idAlunoEdit = scanner.nextLine();
                        System.out.print("Novo nome: ");
                        String novoNomeAluno = scanner.nextLine();
                        secretaria.editarAluno(idAlunoEdit, novoNomeAluno);
                        break;


                    case 7:
                        System.out.print("ID do aluno a remover: ");
                        String idAlunoRemover = scanner.nextLine();
                        secretaria.removerAluno(idAlunoRemover);
                        break;


                    case 8:
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


                    case 9:
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


                    case 10:
                        System.out.print("ID da disciplina a remover: ");
                        int idDisciplinaRemover = scanner.nextInt();
                        scanner.nextLine(); // Limpa o buffer
                        secretaria.removerDisciplina(idDisciplinaRemover);
                        break;


                    case 11:
                        System.out.print("Digite o ID do aluno para gerar o currículo: ");
                        String idAlunoCurriculo = scanner.nextLine();
                        secretaria.gerarCurriculo(idAlunoCurriculo);
                        break;

                    case 12:
                        System.out.print("Nome do curso: ");
                        String nomeCurso = scanner.nextLine();
                        System.out.print("Créditos necessários: ");
                        int creditosNecessarios = scanner.nextInt();
                        scanner.nextLine(); 

                        System.out.println("Disciplinas disponíveis:");
                        List<Disciplina> disciplinasDisponiveisCurso = Disciplina.listarDisciplinas();
                        for (Disciplina disciplina : disciplinasDisponiveisCurso) {
                            System.out.println("ID: " + disciplina.getId() + " - " + disciplina.getNome());
                        }
                    
                        List<Disciplina> disciplinasSelecionadasCurso = new ArrayList<>();
                        System.out.print("Digite os IDs das disciplinas que deseja incluir no curso (separados por vírgula): ");
                        String idsDisciplinasCurso = scanner.nextLine();
                        String[] idsCurso = idsDisciplinasCurso.split(",");
                        for (String id : idsCurso) {
                            int idDisciplinaCurso = Integer.parseInt(id.trim());
                            Disciplina disciplinaSelecionada = disciplinasDisponiveisCurso.stream()
                                    .filter(d -> d.getId() == idDisciplinaCurso)
                                    .findFirst()
                                    .orElse(null);
                            if (disciplinaSelecionada != null) {
                                disciplinasSelecionadasCurso.add(disciplinaSelecionada);
                            } else {
                                System.out.println("Disciplina com ID " + idDisciplinaCurso + " não encontrada.");
                            }
                        }

                        secretaria.adicionarCurso(new Curso(nomeCurso, creditosNecessarios, disciplinasSelecionadasCurso));
                        break;
                    
                    case 13:
                        System.out.println("Cursos disponíveis:");
                        List<Curso> cursosDisponiveis = Curso.listarCursos();
                        for (Curso curso : cursosDisponiveis) {
                            System.out.println("Nome: " + curso.getNome() + " - Créditos Necessários: " + curso.getCreditosNecessarios());
                        }
                        break;
                    
                    case 14:
                        System.out.print("Nome do curso a remover: ");
                        String nomeCursoRemover = scanner.nextLine();
                        secretaria.removerCurso(nomeCursoRemover);
                        break;

                        case 15:
                        // System.out.print("Id da nota fiscal: ");
                        // String nomeDisciplina = scanner.nextLine();
                        // System.out.print("ID da disciplina: ");
                        // int idDisciplina = scanner.nextInt();
                        // scanner.nextLine(); // Limpa o buffer
                        // System.out.print("Créditos da disciplina: ");
                        // int credito = scanner.nextInt();
                        // scanner.nextLine(); // Limpa o buffer

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