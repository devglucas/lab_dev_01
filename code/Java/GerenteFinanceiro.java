package code.Java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class GerenteFinanceiro extends Usuario {

    public GerenteFinanceiro(String email, String senha) {
        super(email, senha, "GERENTE_FINANCEIRO");
    }

    public static List<NotaFiscal> lerNotasFiscais() {
        String FILE_PATH = "code/Java/DB/";
        String FILE_NOTA = FILE_PATH + "NotaFiscal.csv";

        List<NotaFiscal> notasFiscais = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NOTA))) {
            String linha;
            boolean primeiraLinha = true;

            while ((linha = reader.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }

                String[] dados = linha.split(",");
                int id = Integer.parseInt(dados[0]);
                double valor = Double.parseDouble(dados[1]);
                boolean estaPago = Boolean.parseBoolean(dados[2]);
                boolean estaVerificadoPeloFinanceiro = Boolean.parseBoolean(dados[3]);
                String idAluno = dados[4];
                Aluno aluno = Aluno.buscarAlunoPorId(idAluno);

                if (aluno != null) {
                    NotaFiscal notaFiscal = new NotaFiscal(id, valor, estaPago, estaVerificadoPeloFinanceiro, aluno);
                    notasFiscais.add(notaFiscal);
                } else {
                    System.out.println("Aluno com ID " + idAluno + " não encontrado para a nota fiscal ID " + id);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return notasFiscais;
    }

    public static void escreverNotasFiscais(List<NotaFiscal> notasFiscais) {
        String FILE_PATH = "code/Java/DB/";
        String FILE_NOTA = FILE_PATH + "NotaFiscal.csv";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NOTA))) {

            bw.write("id,valor,estaPago,estaVerificadoPeloFinanceiro,idAluno");
            bw.newLine();

            for (NotaFiscal notaFiscal : notasFiscais) {
                String linha = notaFiscal.getId() + "," +
                        notaFiscal.getValor() + "," +
                        notaFiscal.isEstahPago() + "," +
                        notaFiscal.isEstahVerificado() + "," +
                        notaFiscal.getAluno().getMatricula();
                bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void verificarNotaFiscal(int idNotaFiscal) {
        List<NotaFiscal> notasFiscais = lerNotasFiscais();
        for (NotaFiscal notaFiscal : notasFiscais) {
            if (notaFiscal.getId() == idNotaFiscal) {
                notaFiscal.setEstahVerificado(true);
                ;
                System.out.println("Nota fiscal ID " + idNotaFiscal + " verificada com sucesso.");
                escreverNotasFiscais(notasFiscais);
                return;
            }
        }

        System.out.println("Nota fiscal com ID " + idNotaFiscal + " não encontrada.");
    }
}