package code.Java;

import java.util.ArrayList;
import java.util.List;

public class Professor extends Usuario {
    private String nome;
    private String matricula;
    private List<Disciplina> disciplinasMinistradas;

    public Professor(String nome, String matricula, List<Disciplina> disciplinasMinistradas) {
        this.nome = nome;
        this.matricula = matricula;
        this.disciplinasMinistradas = disciplinasMinistradas;
    }
    
    public Professor(String nome, String matricula) {
        this.nome = nome;
        this.matricula = matricula;
        this.disciplinasMinistradas = new ArrayList<>();
    }

    public String getNome(){
        return this.nome;
    }
    public String getMatricula(){
        return this.matricula;
    }

    public void consultarAlunosMatriculados(Disciplina disciplina) {

        }
    }

