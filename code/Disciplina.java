package code;

import java.util.List;

public class Disciplina {
    private String nome;
    private int id;
    private int credito;
    private boolean estaDisponivel;
    private final int limCima = 60;
    private final int limBaixo = 3;
    public TIPODISCIPLINA tipoDisciplina;
    private List<Aluno> alunosMatriculados;
    
    public boolean verificarDisponibilidade() { return false; }
    public void gerarCurriculo() {}
}
