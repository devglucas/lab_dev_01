package code.Java;

import java.time.LocalDate;
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

    public Aluno(String nome, Curso curso, String matricula, List<Disciplina> disciplinasMatriculadas) {
        this.nome = nome;
        this.curso = curso;
        this.matricula = matricula;
        this.disciplinasMatriculadas = disciplinasMatriculadas;
    }

    public Aluno(String nome, String matricula) {
        this.nome = nome;
        this.matricula = matricula;
        this.curso = null; 
        this.disciplinasMatriculadas = new ArrayList<>(); 
    }

    public void solicitarMatricula(Disciplina disciplina, LocalDate data) {
        long obrigatorias = disciplinasMatriculadas.stream().filter(d -> d.getTipoDisciplina() == TIPODISCIPLINA.OBRIGATORIA).count();
        long optativas = disciplinasMatriculadas.stream().filter(d -> d.getTipoDisciplina() == TIPODISCIPLINA.OPTATIVA).count();
        
        if (disciplina.getTipoDisciplina() == TIPODISCIPLINA.OBRIGATORIA && obrigatorias >= limObrigatorias) {
            System.out.println("Erro: Limite de disciplinas obrigatórias atingido.");
            return;
        }
        
        if (disciplina.getTipoDisciplina() == TIPODISCIPLINA.OPTATIVA && optativas >= limOptativas) {
            System.out.println("Erro: Limite de disciplinas optativas atingido.");
            return;
        }
        
        try {
            if (disciplina.adicionarAluno(this)) {
                this.disciplinasMatriculadas.add(disciplina);
                System.out.println("Matrícula na disciplina " + disciplina.getNome() + " realizada com sucesso!");
            } else {
                throw new IllegalStateException("A disciplina " + disciplina.getNome() + " já atingiu o limite máximo de alunos.");
            }
        } catch (IllegalStateException e) {
            System.out.println("Erro ao matricular: " + e.getMessage());
        }
    }

    public void solicitarCancelamento(Disciplina disciplina, LocalDate data) {
        if (disciplinasMatriculadas.contains(disciplina)) {
            disciplinasMatriculadas.remove(disciplina);
            System.out.println("Cancelamento da disciplina " + disciplina.getNome() + " realizado com sucesso!");
        } else {
            System.out.println("Erro: O aluno não está matriculado nesta disciplina.");
        }
    }
    
    public void realizarPagamento() {}
    public void consultarDisciplinas(List<Disciplina> disciplinas) {}
}