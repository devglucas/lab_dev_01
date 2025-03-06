package code.Java;

import java.util.List;

class GerenteFinanceiro extends Usuario {

    public GerenteFinanceiro(String email, String senha) {
            super(email, senha, "GERENTE_FINANCEIRO");
        }
    
        public double calcularValor(List<Disciplina> disciplinas) {
        return 0;
    }

    public NotaFiscal emitirNotaFiscal(Aluno aluno, double valor) {
        return null;
    }
}