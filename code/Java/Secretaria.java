package code.Java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Secretaria extends Usuario {

    public Secretaria(String email, String senha) {
        super(email, senha, "SECRETARIA");
    }

    private static final String FILE_PATH = "code/Java/DB/";
    private static final String FILE_PROF = FILE_PATH + "Professores.csv";
    private static final String FILE_DISC = FILE_PATH + "Disciplinas.csv";
    private static final String FILE_ALUN = FILE_PATH + "Alunos.csv";
    private static final String FILE_USU = FILE_PATH + "Usuarios.csv";
    private static final String FILE_CURSO = FILE_PATH + "Cursos.csv";

    public static void salvarUsuario(Usuario usuario) {
        try (FileWriter fw = new FileWriter(FILE_USU, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
            out.println(usuario.getEmail() + "," + usuario.getSenha() + "," + usuario.getTipo());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void adicionarProfessor(Professor professor) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PROF, true))) {
            writer.write(professor.getNome() + "," + professor.getMatricula() + "," + professor.getEmail() + ","
                    + professor.getSenha() + "\n");
        } catch (IOException e) {
            System.out.println("Erro ao adicionar professor: " + e.getMessage());
        }
    }

    public void adicionarDisciplinaAoProfessor(String matricula, int idDisciplina) {
        Disciplina disciplina = Disciplina.buscarDisciplinaPorId(idDisciplina);
        if (disciplina == null) {
            System.out.println("Disciplina não encontrada.");
            return;
        }

        List<String> linhasAtualizadas = new ArrayList<>();
        boolean professorEncontrado = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PROF))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length >= 4 && dados[3].equals(matricula)) {
                    professorEncontrado = true;
                    Professor professor = new Professor(dados[0], dados[1], dados[2], dados[3]);

                    if (dados.length > 4 && !dados[4].isEmpty()) {
                        String[] idsDisciplinas = dados[4].split(";");
                        for (String id : idsDisciplinas) {
                            int idDisciplinaExistente = Integer.parseInt(id.trim());
                            Disciplina disciplinaExistente = Disciplina.buscarDisciplinaPorId(idDisciplinaExistente);
                            if (disciplinaExistente != null) {
                                professor.adicionarDisciplina(disciplinaExistente);
                            }
                        }
                    }

                    if (professor.getDisciplinas().stream().anyMatch(d -> d.getId() == idDisciplina)) {
                        System.out.println("O professor já possui essa disciplina.");
                        return;
                    }

                    professor.adicionarDisciplina(disciplina);
                    linha = professor.toString();
                }
                linhasAtualizadas.add(linha);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
            return;
        }

        if (!professorEncontrado) {
            System.out.println("Professor com matrícula " + matricula + " não encontrado.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PROF))) {
            for (String linha : linhasAtualizadas) {
                writer.write(linha + "\n");
            }
            System.out.println("Disciplina adicionada ao professor com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao atualizar arquivo: " + e.getMessage());
        }
    }

    public void removerProfessor(String matricula) {
        List<String> professores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PROF))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (!linha.contains(matricula)) {
                    professores.add(linha);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PROF))) {
            for (String professor : professores) {
                writer.write(professor + "\n");
            }
        } catch (IOException e) {
            System.out.println("Erro ao atualizar arquivo: " + e.getMessage());
        }
    }

    public void editarProfessor(String matricula, String novoNome) {
        List<String> professores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PROF))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length >= 4 && dados[1].equals(matricula)) {
                    String novaLinha = novoNome + "," + dados[1] + "," + dados[2] + "," + dados[3];
                    if (dados.length > 4) {
                        novaLinha += "," + dados[4];
                    }
                    professores.add(novaLinha);
                } else {
                    professores.add(linha);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PROF))) {
            for (String professor : professores) {
                writer.write(professor + "\n");
            }
        } catch (IOException e) {
            System.out.println("Erro ao atualizar arquivo: " + e.getMessage());
        }
    }

    public static void atualizarAlunoNoCSV(Aluno aluno) {
        String filePath = FILE_ALUN;
        List<String> linhas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String linha;
            boolean primeiraLinha = true;
            while ((linha = reader.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    linhas.add(linha);
                    continue;
                }

                if (linha.contains(aluno.getEmail())) {
                    StringBuilder disciplinasStr = new StringBuilder();
                    for (Disciplina disciplina : aluno.getDisciplinasMatriculadas()) {
                        disciplinasStr.append(disciplina.getId()).append(";");
                    }
                    if (disciplinasStr.length() > 0) {
                        disciplinasStr.deleteCharAt(disciplinasStr.length() - 1);
                    }
                    linha = aluno.getEmail() + "," + aluno.getSenha() + "," + aluno.getNome() + ","
                            + aluno.getMatricula()
                            + "," + disciplinasStr.toString();
                }
                linhas.add(linha);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de alunos: " + e.getMessage());
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String linha : linhas) {
                writer.write(linha + "\n");
            }
        } catch (IOException e) {
            System.out.println("Erro ao atualizar o arquivo de alunos: " + e.getMessage());
        }
    }

    public void adicionarAluno(Aluno aluno) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_ALUN, true))) {

            StringBuilder disciplinasStr = new StringBuilder();

            for (Disciplina disciplina : aluno.getDisciplinasMatriculadas()) {
                disciplinasStr.append(disciplina.getId()).append(";");
            }
            writer.write(aluno.getEmail() + "," + aluno.getSenha() + "," + aluno.getNome() + "," + aluno.getMatricula()
                    + "," + aluno.getCurso().toString() + "," + disciplinasStr.toString() + "\n");
        } catch (IOException e) {
            System.out.println("Erro ao adicionar aluno: " + e.getMessage());
        }
    }

    public void removerAluno(String matricula) {
        List<String> alunos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_ALUN))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (!linha.contains(matricula)) {
                    alunos.add(linha);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_ALUN))) {
            for (String aluno : alunos) {
                writer.write(aluno + "\n");
            }
        } catch (IOException e) {
            System.out.println("Erro ao atualizar arquivo: " + e.getMessage());
        }
    }

    public void editarAluno(String matricula, String novoNome) {
        List<String> alunos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_ALUN))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length >= 4 && dados[3].equals(matricula)) {
                    String novaLinha = dados[0] + "," + dados[1] + "," + novoNome + "," + dados[3];
                    if (dados.length > 4) {
                        novaLinha += "," + dados[4];
                        if (dados.length > 5) {
                            novaLinha += "," + dados[5];
                        }
                    }
                    alunos.add(novaLinha);
                } else {
                    alunos.add(linha);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_ALUN))) {
            for (String aluno : alunos) {
                writer.write(aluno + "\n");
            }
        } catch (IOException e) {
            System.out.println("Erro ao atualizar arquivo: " + e.getMessage());
        }
    }

    public void adicionarDisciplina(Disciplina disciplina) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_DISC, true))) {
            StringBuilder alunosStr = new StringBuilder();
            for (Aluno aluno : disciplina.getAlunosMatriculados()) {
                alunosStr.append(aluno.getMatricula()).append(";");
            }
            if (alunosStr.length() > 0) {
                alunosStr.deleteCharAt(alunosStr.length() - 1);
            }
            writer.write(disciplina.getNome() + "," + disciplina.getId() + "," + disciplina.getCredito() + "," +
                    disciplina.getTipoDisciplina() + "," + disciplina.isEstaDisponivel() + "," + alunosStr.toString()
                    + "\n");
        } catch (IOException e) {
            System.out.println("Erro ao adicionar disciplina: " + e.getMessage());
        }
    }

    public static void atualizarDisciplinaNoCSV(Disciplina disciplina) {
        List<String> linhas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_DISC))) {
            String linha;
            boolean primeiraLinha = true;
            while ((linha = reader.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    linhas.add(linha);
                    continue;
                }

                String[] dados = linha.split(",");
                if (dados.length > 1 && Integer.parseInt(dados[1].trim()) == disciplina.getId()) {
                    StringBuilder alunosStr = new StringBuilder();
                    for (Aluno aluno : disciplina.getAlunosMatriculados()) {
                        alunosStr.append(aluno.getMatricula()).append(";");
                    }
                    if (alunosStr.length() > 0) {
                        alunosStr.deleteCharAt(alunosStr.length() - 1);
                    }
                    linha = disciplina.getNome() + "," + disciplina.getId() + "," + disciplina.getCredito() + "," +
                            disciplina.getTipoDisciplina() + "," + disciplina.isEstaDisponivel() + ","
                            + alunosStr.toString();
                }
                linhas.add(linha);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de disciplinas: " + e.getMessage());
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_DISC))) {
            for (String linha : linhas) {
                writer.write(linha + "\n");
            }
        } catch (IOException e) {
            System.out.println("Erro ao atualizar o arquivo de disciplinas: " + e.getMessage());
        }
    }

    public void removerDisciplina(int id) {
        List<String> disciplinas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_DISC))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (!linha.contains(String.valueOf(id))) {
                    disciplinas.add(linha);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_DISC))) {
            for (String disciplina : disciplinas) {
                writer.write(disciplina + "\n");
            }
        } catch (IOException e) {
            System.out.println("Erro ao atualizar arquivo: " + e.getMessage());
        }
    }

    public void editarDisciplina(int id, String novoNome, int novoCredito) {
        List<String> disciplinas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_DISC))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length >= 2) {
                    try {
                        int disciplinaId = Integer.parseInt(dados[1].trim());
                        if (disciplinaId == id) {
                            String novaLinha = novoNome + "," + dados[1] + "," + novoCredito + "," + dados[3] + ","
                                    + dados[4];
                            if (dados.length > 5) {
                                novaLinha += "," + dados[5];
                            }
                            disciplinas.add(novaLinha);
                        } else {
                            disciplinas.add(linha);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Erro ao converter ID da disciplina: " + e.getMessage());
                        disciplinas.add(linha);
                    }
                } else {
                    disciplinas.add(linha);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_DISC))) {
            for (String disciplina : disciplinas) {
                writer.write(disciplina + "\n");
            }
        } catch (IOException e) {
            System.out.println("Erro ao atualizar arquivo: " + e.getMessage());
        }
    }

    public void adicionarCurso(Curso curso) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_CURSO, true))) {
            StringBuilder disciplinasStr = new StringBuilder();
            for (Disciplina disciplina : curso.getDisciplinas()) {
                disciplinasStr.append(disciplina.getId()).append(";");
            }
            writer.write(
                    curso.getNome() + "," + curso.getCreditosNecessarios() + "," + disciplinasStr.toString() + "\n");
        } catch (IOException e) {
            System.out.println("Erro ao adicionar curso: " + e.getMessage());
        }
    }

    public void removerCurso(String nome) {
        List<String> cursos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_CURSO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (!linha.contains(nome)) {
                    cursos.add(linha);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_CURSO))) {
            for (String curso : cursos) {
                writer.write(curso + "\n");
            }
        } catch (IOException e) {
            System.out.println("Erro ao atualizar arquivo: " + e.getMessage());
        }
    }

    public void gerarCurriculo(String idAluno) {
        Aluno aluno = Aluno.buscarAlunoPorId(idAluno);
        if (aluno == null) {
            System.out.println("Aluno não encontrado.");
            return;
        }
        Curso curso = aluno.getCurso();
        if (curso == null) {
            System.out.println("Curso não encontrado para o aluno.");
            return;
        }
        List<Disciplina> disciplinasDoCurso = curso.getDisciplinas();
        if (disciplinasDoCurso.isEmpty()) {
            System.out.println("Nenhuma disciplina encontrada para o curso.");
            return;
        }
        System.out.println("=== Currículo do Aluno ===");
        System.out.println("Nome do Aluno: " + aluno.getNome());
        System.out.println("Curso: " + curso.getNome());
        System.out.println("Disciplinas:");

        for (Disciplina disciplina : disciplinasDoCurso) {
            System.out.println(" - " + disciplina.getNome());
            List<Professor> professoresDaDisciplina = Professor.buscarProfessoresPorDisciplina(disciplina.getId());
            if (!professoresDaDisciplina.isEmpty()) {
                System.out.println("   Professores:");
                for (Professor professor : professoresDaDisciplina) {
                    System.out.println("    - " + professor.getNome());
                }
            } else {
                System.out.println("   Nenhum professor encontrado para esta disciplina.");
            }
        }
    }

}