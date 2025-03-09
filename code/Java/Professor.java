package code.Java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Professor extends Usuario {
    private String nome;
    private String matricula;
    private List<Disciplina> disciplinas;

    public Professor(String email, String senha, String nome, String matricula) {
        super(email, senha, "PROFESSOR");
        this.nome = nome;
        this.matricula = matricula;
        this.disciplinas = new ArrayList<>();
    }

    public Professor(String email, String senha) {
        super(email, senha, "PROFESSOR");
    }

    public String getNome() {
        return nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void adicionarDisciplina(Disciplina disciplina) {
        this.disciplinas.add(disciplina);
    }

    public static List<Professor> buscarProfessoresPorDisciplina(int idDisciplina) {
        String FILE_PATH = "code/Java/DB/";
        String FILE_PROF = FILE_PATH + "Professores.csv";

        List<Professor> professores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PROF))) {
            String linha;
            boolean primeiraLinha = true;

            while ((linha = reader.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }
                String[] dados = linha.split(",");
                if (dados.length > 4 && !dados[4].isEmpty()) {
                    String[] idsDisciplinas = dados[4].split(";");
                    for (String id : idsDisciplinas) {
                        try {
                            int idDisciplinaProfessor = Integer.parseInt(id.trim());
                            if (idDisciplinaProfessor == idDisciplina) {
                                String nome = dados[0];
                                String matricula = dados[1];
                                String email = dados[2];
                                String senha = dados[3];
                                professores.add(new Professor(nome, matricula, email, senha));
                                break;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Erro ao converter ID da disciplina: " + id);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao buscar professores: " + e.getMessage());
        }
        return professores;
    }

    @Override
    public String toString() {
        String idsDisciplinas = disciplinas.stream()
                .map(disciplina -> String.valueOf(disciplina.getId()))
                .reduce((id1, id2) -> id1 + ";" + id2)
                .orElse("");
        return getEmail() + "," + getSenha() + "," + nome + "," + matricula + "," + idsDisciplinas;
    }

    public static Professor buscarProfessorPorEmail(String email) {
        String FILE_PATH = "code/Java/DB/";
        String FILE_PROF = FILE_PATH + "Professores.csv";
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PROF))) {
            String linha;
            boolean primeiraLinha = true;

            while ((linha = reader.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }

                String[] dados = linha.split(",");
                if (dados.length > 2 && dados[2].equals(email)) {
                    String nome = dados[0];
                    String matricula = dados[1];
                    String senha = dados[3];
                    Professor professor = new Professor(email, senha, nome, matricula);
                    if (dados.length > 4 && !dados[4].isEmpty()) {
                        String[] disciplinasIds = dados[4].split(";");
                        for (String id : disciplinasIds) {
                            try {
                                int idDisciplina = Integer.parseInt(id.trim());
                                Disciplina disciplina = Disciplina.buscarDisciplinaPorId(idDisciplina);
                                if (disciplina != null) {
                                    professor.adicionarDisciplina(disciplina);
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Erro ao converter ID da disciplina: " + id);
                            }
                        }
                    }

                    return professor;
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de professores: " + e.getMessage());
        }
        return null;
    }

    
    public void listarAlunosDasDisciplinas() {
        if (this.disciplinas.isEmpty()) {
            System.out.println("Você não está associado a nenhuma disciplina.");
            return;
        }
    
        System.out.println("Alunos matriculados nas suas disciplinas:");
        for (Disciplina disciplina : this.disciplinas) {
            System.out.println("\nDisciplina: " + disciplina.getNome() + " (ID: " + disciplina.getId() + ")");
            List<Aluno> alunosMatriculados = disciplina.getAlunosMatriculados();
    
            if (alunosMatriculados.isEmpty()) {
                System.out.println("Nenhum aluno matriculado nesta disciplina.");
            } else {
                for (Aluno aluno : alunosMatriculados) {
                    System.out.println("- " + aluno.getNome() + " (Matrícula: " + aluno.getMatricula() + ")");
                }
            }
        }
    }


}