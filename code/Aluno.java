package code;

import java.time.LocalDate;
import java.util.List;

public class Aluno extends Usuario{
    private String nome;
    private Curso curso;
    private String matricula;
    private List<Disciplina> disciplinasMatriculadas;
    
    public void solicitarMatricula(Disciplina disciplina, LocalDate data) {}
    public void solicitarCancelamento(Disciplina disciplina, LocalDate data) {}
    public void realizarPagamento() {}
    public void consultarDisciplinas(List<Disciplina> disciplinas){}
}
