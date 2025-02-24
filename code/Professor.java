package code;

import java.util.List;
import java.util.ArrayList;

public class Professor extends Usuario {
    private String matricula;
    private List<Disciplina> disciplinasMinistradas;

    public Professor(String nome, String senha, String matricula) {
        super(nome, senha);
        this.matricula = matricula;
        this.disciplinasMinistradas = new ArrayList<>();
    }

    public void consultarAlunosMatriculados(Disciplina disciplina) {
        System.out.println("Alunos matriculados na disciplina " + disciplina.getNome() + ":");
        for (Aluno aluno : disciplina.getAlunosMatriculados()) {
            System.out.println(aluno.getNome());
        }
    }
}
