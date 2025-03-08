import java.io.*;
import java.util.*;

public class GerenciadorDisciplinas {

    private static final String CAMINHO_CSV = "code/Java/DB/Disciplinas.csv";

    public static void solicitarCancelamento(String nomeDisciplina) {
        List<String> linhasAtualizadas = new ArrayList<>();
        boolean disciplinaEncontrada = false;
        try (BufferedReader br = new BufferedReader(new FileReader(CAMINHO_CSV))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados[0].equalsIgnoreCase(nomeDisciplina)) {
                    disciplinaEncontrada = true;
                    continue; // Pula essa linha
                }
                linhasAtualizadas.add(linha);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo CSV: " + e.getMessage());
            return;
        }
        if (!disciplinaEncontrada) {
            System.out.println("Disciplina não encontrada para cancelamento.");
            return;
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CAMINHO_CSV))) {
            for (String linha : linhasAtualizadas) {
                bw.write(linha);
                bw.newLine();
            }
            System.out.println("Disciplina '" + nomeDisciplina + "' cancelada com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao atualizar o arquivo CSV: " + e.getMessage());
        }
    }

    public static List<Disciplina> consultarDisciplinas() {
        List<Disciplina> disciplinas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CAMINHO_CSV))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");

                if (dados.length < 4) continue; 

                String nome = dados[0];
                int id = Integer.parseInt(dados[1]);
                int creditos = Integer.parseInt(dados[2]);
                TipoDisciplina tipo = TipoDisciplina.valueOf(dados[3].toUpperCase());

                disciplinas.add(new Disciplina(nome, id, creditos, tipo));
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo CSV: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Erro: Tipo de disciplina inválido no arquivo CSV.");
        }

        return disciplinas;
    }
}
