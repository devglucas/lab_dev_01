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
    
    public void adicionarAluno(Aluno aluno) {
    if (aluno.getCurso() == null) { 
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_ALUN, true))) {
            writer.write(aluno.getNome() + "," + aluno.getMatricula() + "\n");
        } catch (IOException e) {
            System.out.println("Erro ao adicionar aluno: " + e.getMessage());
        }
    } else {
        System.out.println("Aluno já está matriculado em um curso e não pode ser registrado.");
    }
}

public void removerAluno(String matricula) {
    List<String> alunos = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(FILE_ALUN))) {
        String linha;
        while ((linha = reader.readLine()) != null) {
            if (!linha.contains(matricula)) {
                alunos.add(linha);
            }
        }
    } catch (IOException e) {
        System.out.println("Erro ao ler arquivo: " + e.getMessage());
    }

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_ALUN))) {
        for (String aluno : alunos) {
            writer.write(aluno + "\n");
        }
    } catch (IOException e) {
        System.out.println("Erro ao atualizar arquivo: " + e.getMessage());
    }
}

public void editarAluno(String matricula, String novoNome) {
    List<String> alunos = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(FILE_ALUN))) {
        String linha;
        while ((linha = reader.readLine()) != null) {
            if (linha.contains(matricula)) {
                alunos.add(novoNome + "," + matricula);
            } else {
                alunos.add(linha);
            }
        }
    } catch (IOException e) {
        System.out.println("Erro ao ler arquivo: " + e.getMessage());
    }

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_ALUN))) {
        for (String aluno : alunos) {
            writer.write(aluno + "\n");
        }
    } catch (IOException e) {
        System.out.println("Erro ao atualizar arquivo: " + e.getMessage());
    }
}

public void adicionarDisciplina(Disciplina disciplina) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_DISC, true))) {
        writer.write(disciplina.getNome() + "," + disciplina.getId() + "," + disciplina.getCredito() + "\n");
    } catch (IOException e) {
        System.out.println("Erro ao adicionar disciplina: " + e.getMessage());
    }
}

public void removerDisciplina(int id) {
    List<String> disciplinas = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(FILE_DISC))) {
        String linha;
        while ((linha = reader.readLine()) != null) {
            if (!linha.contains(String.valueOf(id))) {
                disciplinas.add(linha);
            }
        }
    } catch (IOException e) {
        System.out.println("Erro ao ler arquivo: " + e.getMessage());
    }

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_DISC))) {
        for (String disciplina : disciplinas) {
            writer.write(disciplina + "\n");
        }
    } catch (IOException e) {
        System.out.println("Erro ao atualizar arquivo: " + e.getMessage());
    }
}

public void editarDisciplina(int id, String novoNome, int novoCredito) {
    List<String> disciplinas = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(FILE_DISC))) {
        String linha;
        while ((linha = reader.readLine()) != null) {
            if (linha.contains(String.valueOf(id))) {
                disciplinas.add(novoNome + "," + id + "," + novoCredito);
            } else {
                disciplinas.add(linha);
            }
        }
    } catch (IOException e) {
        System.out.println("Erro ao ler arquivo: " + e.getMessage());
    }

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_DISC))) {
        for (String disciplina : disciplinas) {
            writer.write(disciplina + "\n");
        }
    } catch (IOException e) {
        System.out.println("Erro ao atualizar arquivo: " + e.getMessage());
    }
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