package code;

import java.util.List;
import java.util.ArrayList;

class GerenteFinanceiro extends Usuario {
    public GerenteFinanceiro(String nome, String senha) {
        super(nome, senha);
    }

    public double calcularValor(List<Disciplina> disciplinas) {
        double total = 0;
        for (Disciplina disciplina : disciplinas) {
            total += disciplina.getCredito() * 100;
        }
        return total;
    }

    public NotaFiscal emitirNotaFiscal(Aluno aluno, double valor) {
        return new NotaFiscal(aluno, valor);
    }
}