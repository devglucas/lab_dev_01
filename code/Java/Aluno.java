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

    public void solicitarMatricula(Disciplina disciplina, LocalDate data) {
        long obrigatorias = disciplinasMatriculadas.stream().filter(d -> d.getTipoDisciplina() == TIPODISCIPLINA.OBRIGATORIA).count();
        long optativas = disciplinasMatriculadas.stream().filter(d -> d.getTipoDisciplina() == TIPODISCIPLINA.OPTATIVA).count();
        
        
    }

    public void solicitarCancelamento(Disciplina disciplina, LocalDate data) {
    
    }
    
    public void realizarPagamento() {

    }

    public void consultarDisciplinas(List<Disciplina> disciplinas) {

    }

}