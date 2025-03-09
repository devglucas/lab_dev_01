package code.Java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Aluno extends Usuario {
    private String nome;
    private Curso curso;
    private String matricula;
    public static final int limOptativas = 2;
    public static final int limObrigatorias = 4;
    private List<Disciplina> disciplinasMatriculadas;
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
    
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public List<Disciplina> getDisciplinasMatriculadas() {
        return disciplinasMatriculadas;
    }

    public void setDisciplinasMatriculadas(List<Disciplina> disciplinasMatriculadas) {
        this.disciplinasMatriculadas = disciplinasMatriculadas;
    }

    public Aluno(String email, String senha, String nome, Curso curso, String matricula, List<Disciplina> disciplinasMatriculadas) {
        super(email, senha, "ALUNO");
        this.nome = nome;
        this.curso = curso;
        this.matricula = matricula;
        this.disciplinasMatriculadas = disciplinasMatriculadas;
    }

    public Aluno(String email, String senha, String nome, String matricula) {
        super(email, senha, "ALUNO");
        this.nome = nome;
        this.matricula = matricula;
        this.curso = null; 
        this.disciplinasMatriculadas = new ArrayList<>(); 
    }

    public Aluno(String email, String senha) {
        super(email, senha, "ALUNO");
    }

    public static Aluno buscarAlunoPorId(String idAluno) {
        String FILE_PATH = "code/Java/DB/";
        String FILE_ALUN = FILE_PATH + "Alunos.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_ALUN))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length >= 4 && dados[3].equals(idAluno)) {
                    String email = dados[0];
                    String senha = dados[1];
                    String nome = dados[2];
                    String matricula = dados[3];
                    String nomeCurso = dados[4];
                    Curso curso = Curso.buscarCursoPorNome(nomeCurso);
                    List<Disciplina> disciplinas = new ArrayList<>();
                    if (dados.length > 5) {
                        String[] idsDisciplinas = dados[5].split(";");
                        for (String id : idsDisciplinas) {
                            Disciplina disciplina = Disciplina.buscarDisciplinaPorId(Integer.parseInt(id.trim()));
                            if (disciplina != null) {
                                disciplinas.add(disciplina);
                            }
                        }
                    }
                    return new Aluno(email, senha, nome, curso, matricula, disciplinas);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao buscar aluno: " + e.getMessage());
        }
        return null;
    }

    public static Aluno buscarAlunoPorEmail(String email) {
        String filePath = "code/Java/DB/Alunos.csv";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados[0].equals(email)) {
                    String senha = dados[1];
                    String nome = dados[2];
                    String matricula = dados[3];
                    String nomeCurso = dados[4];
                    List<Disciplina> disciplinasMatriculadas = new ArrayList<>();
    
                    if (dados.length > 4 && !dados[5].isEmpty()) {
                        String[] disciplinasIds = dados[5].split(";");
                        for (String id : disciplinasIds) {
                            Disciplina disciplina = Disciplina.buscarDisciplinaPorId(Integer.parseInt(id.trim()));
                            if (disciplina != null) {
                                disciplinasMatriculadas.add(disciplina);
                            }
                        }
                    }
    
                    Curso curso = Curso.listarCursos().stream()
                    .filter(c -> c.getNome().equals(nomeCurso))
                    .findFirst()
                    .orElse(null);

            return new Aluno(email, senha, nome, curso, matricula, disciplinasMatriculadas);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de alunos: " + e.getMessage());
        }
        return null;
    }
    public static List<Disciplina> listarDisciplinasDisponiveis() {
        List<Disciplina> disciplinasDisponiveis = new ArrayList<>();
        for (Disciplina disciplina : Disciplina.listarDisciplinas()) {
            if (disciplina.isEstaDisponivel()) {
                disciplinasDisponiveis.add(disciplina);
            }
        }
        return disciplinasDisponiveis;
    }

    public void solicitarMatricula(Disciplina disciplina) {
        if (disciplinasMatriculadas.contains(disciplina)) {
            System.out.println("Você já está matriculado nesta disciplina.");
            return;
        }
        long obrigatorias = disciplinasMatriculadas.stream()
                .filter(d -> d.getTipoDisciplina() == TIPODISCIPLINA.OBRIGATORIA)
                .count();
        long optativas = disciplinasMatriculadas.stream()
                .filter(d -> d.getTipoDisciplina() == TIPODISCIPLINA.OPTATIVA)
                .count();
        if (disciplina.getTipoDisciplina() == TIPODISCIPLINA.OBRIGATORIA && obrigatorias >= limObrigatorias) {
            System.out.println("Limite de disciplinas obrigatórias atingido.");
            return;
        }
        if (disciplina.getTipoDisciplina() == TIPODISCIPLINA.OPTATIVA && optativas >= limOptativas) {
            System.out.println("Limite de disciplinas optativas atingido.");
            return;
        }

        disciplinasMatriculadas.add(disciplina);
        disciplina.adicionarAluno(this); 
        Secretaria.atualizarDisciplinaNoCSV(disciplina); 
        System.out.println("Matrícula na disciplina " + disciplina.getNome() + " realizada com sucesso.");
    
        Secretaria.atualizarAlunoNoCSV(this); 
    }

    public void cancelarMatricula(Disciplina disciplina) {
        if (!disciplinasMatriculadas.contains(disciplina)) {
            System.out.println("Você não está matriculado nesta disciplina.");
            return;
        }
        disciplinasMatriculadas.remove(disciplina);
        disciplina.removerAluno(this);
        Secretaria.atualizarDisciplinaNoCSV(disciplina);
        Secretaria.atualizarAlunoNoCSV(this);
        System.out.println("Matrícula na disciplina " + disciplina.getNome() + " cancelada com sucesso.");
    }


    public void realizarPagamento() {
        // Implementação do método de pagamento
    }

}