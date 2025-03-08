package code.Java;

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

    //BGL PRA ESCREVER NO ARQUIVO CSV SE NAO VAI O OBJETO INTEIRO
    @Override
public String toString() {
    String idsDisciplinas = disciplinas.stream()
            .map(disciplina -> String.valueOf(disciplina.getId()))
            .reduce((id1, id2) -> id1 + ";" + id2)
            .orElse("");
    return getEmail() + "," + getSenha() + "," + nome + "," + matricula + "," + idsDisciplinas;
}
}