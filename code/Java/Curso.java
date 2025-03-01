package code.Java;

import java.util.List;

public class Curso {
    private String nome;
    private int creditosNecessarios;
    private List<Disciplina> disciplinas;
    
    public Curso(String nome, int creditosNecessarios, List<Disciplina> disciplinas){
        this.nome = nome;
        this.creditosNecessarios = creditosNecessarios;
        this.disciplinas = disciplinas;
    }

    public List<Disciplina> getDisciplinas() { 
        return disciplinas; 
    }
    
    public void incluirDisciplina(Disciplina disciplina) {
        disciplinas.add(disciplina);
    }
    public Disciplina encontrarDisciplina(int id) {
        for (Disciplina disciplina : disciplinas) {
            if (disciplina.getId() == id) {
                return disciplina;
            }
        }
        return null;
    }
}
