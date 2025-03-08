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
                    List<Disciplina> disciplinasMatriculadas = new ArrayList<>();
    
                    if (dados.length > 4 && !dados[4].isEmpty()) {
                        String[] disciplinasIds = dados[4].split(";");
                        for (String id : disciplinasIds) {
                            Disciplina disciplina = Secretaria.buscarDisciplinaPorId(Integer.parseInt(id.trim()));
                            if (disciplina != null) {
                                disciplinasMatriculadas.add(disciplina);
                            }
                        }
                    }
    
                    return new Aluno(email, senha, nome, null, matricula, disciplinasMatriculadas);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de alunos: " + e.getMessage());
        }
        return null;
    }
    public static List<Disciplina> listarDisciplinasDisponiveis() {
        List<Disciplina> disciplinasDisponiveis = new ArrayList<>();
        for (Disciplina disciplina : Secretaria.listarDisciplinas()) {
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
        //LOGICA DE ATUALIZACAO DO CSV QUANDO FAZ MATRICULA::
         //LOGICA DE ATUALIZACAO DO CSV QUANDO FAZ MATRICULA::
          //LOGICA DE ATUALIZACAO DO CSV QUANDO FAZ MATRICULA::
           //LOGICA DE ATUALIZACAO DO CSV QUANDO FAZ MATRICULA::
            //LOGICA DE ATUALIZACAO DO CSV QUANDO FAZ MATRICULA::
        disciplinasMatriculadas.add(disciplina);
        disciplina.adicionarAluno(this); 
        Secretaria.atualizarDisciplinaNoCSV(disciplina); 
        System.out.println("Matrícula na disciplina " + disciplina.getNome() + " realizada com sucesso.");
    
        Secretaria.atualizarAlunoNoCSV(this); 
    }

    public void cancelarMatricula(Disciplina disciplina) {
        if (disciplinasMatriculadas == null || disciplinasMatriculadas.isEmpty()) {
            System.out.println("Você não está matriculado em nenhuma disciplina.");
            return;
        }
    
        if (!disciplinasMatriculadas.contains(disciplina)) {
            System.out.println("Você não está matriculado nesta disciplina.");
            return;
        }
    
        // Remove a disciplina da lista de disciplinas matriculadas do aluno
        disciplinasMatriculadas.remove(disciplina);
    
        // Remove o aluno da lista de alunos matriculados na disciplina
        disciplina.removerAluno(this);
    
        // Atualiza a disciplina no CSV
        Secretaria.atualizarDisciplinaNoCSV(disciplina);
    
        // Atualiza o aluno no CSV
        Secretaria.atualizarAlunoNoCSV(this);
    
        System.out.println("Matrícula na disciplina " + disciplina.getNome() + " cancelada com sucesso.");
    }

    public void realizarPagamento() {
        // Implementação do método de pagamento
    }

}