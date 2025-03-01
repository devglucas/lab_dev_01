package code.Java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Secretaria {
    
    private static final String FILE_PROF = "Professores.txt";
    private static final String FILE_DISC = "Disciplinas.txt";
    private static final String FILE_ALUN = "Alunos.txt";

    public void adicionarProfessor(Professor professor) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PROF, true))) {
            writer.write(professor.getNome() + "," + professor.getMatricula() + "\n");
        } catch (IOException e) {
            System.out.println("Erro ao adicionar professor: " + e.getMessage());
        }
    }

    public void removerProfessor(String matricula) {
        List<String> professores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PROF))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (!linha.contains(matricula)) {
                    professores.add(linha);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PROF))) {
            for (String professor : professores) {
                writer.write(professor + "\n");
            }
        } catch (IOException e) {
            System.out.println("Erro ao atualizar arquivo: " + e.getMessage());
        }
    }

    public void editarProfessor(String matricula, String novoNome) {
        List<String> professores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PROF))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.contains(matricula)) {
                    professores.add(novoNome + "," + matricula);
                } else {
                    professores.add(linha);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PROF))) {
            for (String professor : professores) {
                writer.write(professor + "\n");
            }
        } catch (IOException e) {
            System.out.println("Erro ao atualizar arquivo: " + e.getMessage());
        }
    }
    
    public void adicionarDisciplinas() {
        // Implementação 
    }
    
    public void removerDisciplinas(int id) {
        // Implementação 
    }
    
    public void editarDisciplinas(int id) {
        // Implementação
    }
    
    public void adicionarAlunos() {
        // Implementação 
    }
    
    public void removerAlunos(String matricula) {
        // Implementação 
    }
    
    public void editarAlunos(String matricula) {
        // Implementação
    }
    
    public Aluno aprovarMatricula(Aluno aluno, Disciplina disciplina) {
        // Implementação 
        return aluno;
    }
    
    public void reprovarMatricula(Aluno aluno, Disciplina disciplina) {
        // Implementação 
    }
    
    public void cancelarDisciplina(Disciplina disciplina) {
        // Implementação 
    }
    
    public void gerarCurriculo() {
        // Implementação 
    }
}