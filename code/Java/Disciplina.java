package code.Java;

import java.util.List;

public class Disciplina {
    private String nome;
    private int id;
    private int credito;
    private boolean estaDisponivel;
    private static final int limCima = 60;
    private static final int limBaixo = 3;
    public TIPODISCIPLINA tipoDisciplina;
    private List<Aluno> alunosMatriculados;
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCredito() {
        return credito;
    }

    public void setCredito(int credito) {
        this.credito = credito;
    }

    public boolean isEstaDisponivel() {
        return estaDisponivel;
    }

    public void setEstaDisponivel(boolean estaDisponivel) {
        this.estaDisponivel = estaDisponivel;
    }

    public TIPODISCIPLINA getTipoDisciplina() {
        return tipoDisciplina;
    }

    public void setTipoDisciplina(TIPODISCIPLINA tipoDisciplina) {
        this.tipoDisciplina = tipoDisciplina;
    }

    public List<Aluno> getAlunosMatriculados() {
        return alunosMatriculados;
    }

    public void setAlunosMatriculados(List<Aluno> alunosMatriculados) {
        this.alunosMatriculados = alunosMatriculados;
    }

    public void adicionarAluno(Aluno aluno) {
        alunosMatriculados.add(aluno);
    }

    public Disciplina(String nome, int id, int credito, TIPODISCIPLINA tipoDisciplina, List<Aluno> alunosMatriculados) {
        
        if(alunosMatriculados.size() > limCima && alunosMatriculados.size() < limBaixo){
            throw new IllegalStateException("limite de alunos estourado");
        }

        this.nome = nome;
        this.id = id;
        this.credito = credito;
        this.tipoDisciplina = tipoDisciplina;
        this.alunosMatriculados = alunosMatriculados;
    }

    public int getId() {
        return id;
    }

    public boolean verificarDisponibilidade() {
        return estaDisponivel; 
    }
    
    public void gerarCurriculo() {}
}
