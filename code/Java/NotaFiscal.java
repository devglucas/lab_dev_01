package code.Java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NotaFiscal {
    private int id;
    private double valor;
    private boolean estahPago;
    private boolean estahVerificado;
    private Aluno aluno;
    
    public NotaFiscal(int id, double valor, boolean estahPago, boolean estahVerificado,
            Aluno aluno) {
        this.id = id;
        this.valor = valor;
        this.estahPago = estahPago;
        this.estahVerificado = estahVerificado;
        this.aluno = aluno;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public double getValor() {
        return valor;
    }


    public void setValor(double valor) {
        this.valor = valor;
    }

    public boolean isEstahPago() {
        return estahPago;
    }


    public void setEstahPago(boolean estahPago) {
        this.estahPago = estahPago;
    }


    public boolean isEstahVerificado() {
        return estahVerificado;
    }


    public void setEstahVerificado(boolean estahVerificado) {
        this.estahVerificado = estahVerificado;
    }


    public Aluno getAluno() {
        return aluno;
    }


    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }


    public NotaFiscal emitirNotaFiscal(Aluno aluno, double valor) { return null; }


public static List<NotaFiscal> listarNotasFiscais() {
    String FILE_PATH = "code/Java/DB/";
    String FILE_NOTA_FISCAL = FILE_PATH + "NotaFiscal.csv";

    List<NotaFiscal> notasFiscais = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NOTA_FISCAL))) {
        String linha;
        boolean primeiraLinha = true;
        while ((linha = reader.readLine()) != null) {
            if (primeiraLinha) {
                primeiraLinha = false;
                continue;
            }

            String[] dados = linha.split(",");

            if (dados.length >= 5) {
                try {
                    int id = Integer.parseInt(dados[0].trim());
                    double valor = Double.parseDouble(dados[1].trim());
                    boolean estahPago = Boolean.parseBoolean(dados[2].trim());
                    boolean estahVerificado = Boolean.parseBoolean(dados[3].trim());
                    String idAluno = dados[4].trim();
                    Aluno aluno = Aluno.buscarAlunoPorId(idAluno);
                    if (!estahVerificado && aluno != null) {
                        notasFiscais.add(new NotaFiscal(id, valor, estahPago, estahVerificado, aluno));
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Erro ao converter número na linha: " + linha);
                }
            } else {
                System.out.println("Linha inválida no arquivo de notas fiscais: " + linha);
            }
        }
    } catch (IOException e) {
        System.out.println("Erro ao ler notas fiscais: " + e.getMessage());
    }
    return notasFiscais;
}

}
