package code.Java;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Aluno extends Usuario{
    private String nome;
    private Curso curso;
    private String matricula;
    private List<Disciplina> disciplinasMatriculadas;

    public Aluno(String nome,Curso curso,String matricula,List<Disciplina> disciplinasMatriculadas){
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

    
    public void solicitarMatricula(Disciplina disciplina, LocalDate data) {}
    public void solicitarCancelamento(Disciplina disciplina, LocalDate data) {}
    public void realizarPagamento() {}
    public void consultarDisciplinas(List<Disciplina> disciplinas){}
}
