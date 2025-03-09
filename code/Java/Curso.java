package code.Java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Curso {
    private String nome;
    private int creditosNecessarios;
    private List<Disciplina> disciplinas;
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCreditosNecessarios() {
        return creditosNecessarios;
    }

    public void setCreditosNecessarios(int creditosNecessarios) {
        this.creditosNecessarios = creditosNecessarios;
    }

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

    public static Curso buscarCursoPorNome(String nomeCurso) {
        List<Curso> cursos = Curso.listarCursos();
        for (Curso curso : cursos) {
            if (curso.getNome().equalsIgnoreCase(nomeCurso)) {
                return curso;
            }
        }
        return null;
    }

    public static List<Disciplina> listarDisciplinasPorCurso(String nomeCurso) {
        List<Disciplina> disciplinasDoCurso = new ArrayList<>();
        List<Curso> cursos = Curso.listarCursos();
        Curso cursoSelecionado = cursos.stream()
                .filter(curso -> curso.getNome().equalsIgnoreCase(nomeCurso))
                .findFirst()
                .orElse(null);
    
        if (cursoSelecionado != null) {
            disciplinasDoCurso = cursoSelecionado.getDisciplinas();
        } else {
            throw new IllegalAccessError("Nenhum curso encontrado com o nome especificado.");
        }
    
        return disciplinasDoCurso;
    }

    public static List<Curso> listarCursos() {
        String FILE_PATH = "code/Java/DB/";
        String FILE_CURSO = FILE_PATH + "Cursos.csv"; 

            List<Curso> cursos = new ArrayList<>();
            List<String> linhas = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_CURSO))) {
                String linha;
            boolean primeiraLinha = true; 
            while ((linha = reader.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false; 
                    linhas.add(linha); 
                    continue;
                }
                    String[] dados = linha.split(",");
                    if (dados.length >= 3) {
                        String nome = dados[0].trim();
                        int creditosNecessarios = Integer.parseInt(dados[1].trim());
                        String[] idsDisciplinas = dados[2].split(";");
                        List<Disciplina> disciplinas = new ArrayList<>();
                        for (String id : idsDisciplinas) {
                            Disciplina disciplina = Disciplina.buscarDisciplinaPorId(Integer.parseInt(id.trim()));
                            if (disciplina != null) {
                                disciplinas.add(disciplina);
                            }
                        }
                        cursos.add(new Curso(nome, creditosNecessarios, disciplinas));
                    }
                }
            } catch (IOException e) {
                System.out.println("Erro ao ler cursos: " + e.getMessage());
            }
            return cursos;
        }

    @Override
    public String toString() {
        return this.nome;
    }
}
