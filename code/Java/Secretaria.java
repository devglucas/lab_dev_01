package code.Java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Secretaria extends Usuario{

    public Secretaria(String email, String senha){
        super(email, senha, "SECRETARIA");
    }

    private static final String FILE_PATH = "code/Java/DB/";
    private static final String FILE_PROF = FILE_PATH + "Professores.csv";
    private static final String FILE_DISC = FILE_PATH + "Disciplinas.csv";
    private static final String FILE_ALUN = FILE_PATH + "Alunos.csv";
    private static final String FILE_USU = FILE_PATH + "Usuarios.csv";
    private static final String FILE_CURSO = FILE_PATH + "Cursos.csv"; 

   public static void salvarUsuario(Usuario usuario) {
    try (FileWriter fw = new FileWriter(FILE_USU, true);
         BufferedWriter bw = new BufferedWriter(fw);
         PrintWriter out = new PrintWriter(bw)) {
        out.println(usuario.getEmail() + "," + usuario.getSenha() + "," + usuario.getTipo());
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    public void adicionarProfessor(Professor professor) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PROF, true))) {
            writer.write(professor.getNome() + "," + professor.getMatricula() + "," + professor.getEmail() + "," + professor.getSenha() + "\n");
        } catch (IOException e) {
            System.out.println("Erro ao adicionar professor: " + e.getMessage());
        }
    }

    // ADICIONA DISCIPLINA AO PROF!!!!!!
    // ADICIONA DISCIPLINA AO PROF!!!!!!
    // ADICIONA DISCIPLINA AO PROF!!!!!!
    // ADICIONA DISCIPLINA AO PROF!!!!!!
    // ADICIONA DISCIPLINA AO PROF!!!!!!
    // ADICIONA DISCIPLINA AO PROF!!!!!!
    // ADICIONA DISCIPLINA AO PROF!!!!!!

    public void adicionarDisciplinaAoProfessor(String matricula, int idDisciplina) {
        Disciplina disciplina = buscarDisciplinaPorId(idDisciplina);
        if (disciplina == null) {
            System.out.println("Disciplina não encontrada.");
            return;
        }
    
        List<String> linhasAtualizadas = new ArrayList<>();
        boolean professorEncontrado = false;
    
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PROF))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length >= 4 && dados[3].equals(matricula)) { 
                    professorEncontrado = true;
                    Professor professor = new Professor(dados[0], dados[1], dados[2], dados[3]);
    
                    if (dados.length > 4 && !dados[4].isEmpty()) {
                        String[] idsDisciplinas = dados[4].split(";");
                        for (String id : idsDisciplinas) {
                            int idDisciplinaExistente = Integer.parseInt(id.trim());
                            Disciplina disciplinaExistente = buscarDisciplinaPorId(idDisciplinaExistente);
                            if (disciplinaExistente != null) {
                                professor.adicionarDisciplina(disciplinaExistente);
                            }
                        }
                    }
    
                    if (professor.getDisciplinas().stream().anyMatch(d -> d.getId() == idDisciplina)) {
                        System.out.println("O professor já possui essa disciplina.");
                        return; 
                    }
    
                    professor.adicionarDisciplina(disciplina);
                    linha = professor.toString();
                }
                linhasAtualizadas.add(linha);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
            return;
        }
    
        if (!professorEncontrado) {
            System.out.println("Professor com matrícula " + matricula + " não encontrado.");
            return;
        }
    
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PROF))) {
            for (String linha : linhasAtualizadas) {
                writer.write(linha + "\n");
            }
            System.out.println("Disciplina adicionada ao professor com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao atualizar arquivo: " + e.getMessage());
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
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_ALUN, true))) {

        StringBuilder disciplinasStr = new StringBuilder();

        for (Disciplina disciplina : aluno.getDisciplinasMatriculadas()) {
            disciplinasStr.append(disciplina.getId()).append(";");
        }
        writer.write(aluno.getEmail()  + "," + aluno.getSenha()  + "," + aluno.getNome()  + "," + aluno.getMatricula() + "," + disciplinasStr.toString() + "\n");
    } catch (IOException e) {
        System.out.println("Erro ao adicionar aluno: " + e.getMessage());
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

   public static List<Disciplina> listarDisciplinas() {
    List<Disciplina> disciplinas = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(FILE_DISC))) {
        String linha;
        boolean primeiraLinha = true; 
        while ((linha = reader.readLine()) != null) {
            if (primeiraLinha) {
                primeiraLinha = false; 
                continue;
            }

            String[] dados = linha.split(",");
            
            if (dados.length >= 4) { 
                try {
                    int id = Integer.parseInt(dados[1].trim());
                    String nome = dados[0].trim();
                    int credito = Integer.parseInt(dados[2].trim());
                    TIPODISCIPLINA tipo = TIPODISCIPLINA.valueOf(dados[3].trim());
                    disciplinas.add(new Disciplina(nome, id, credito, tipo, new ArrayList<>()));
                } catch (NumberFormatException e) {
                    System.out.println("Erro ao converter número na linha: " + linha);
                } catch (IllegalArgumentException e) {
                    System.out.println("Tipo de disciplina inválido na linha: " + linha);
                }
            } else {
                System.out.println("Linha inválida no arquivo de disciplinas: " + linha);
            }
        }
    } catch (IOException e) {
        System.out.println("Erro ao ler disciplinas: " + e.getMessage());
    }
    return disciplinas;
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

    public static Disciplina buscarDisciplinaPorId(int id) {

        List<Disciplina> disciplinas = listarDisciplinas();
         for (Disciplina disciplina : disciplinas) {
            if (disciplina.getId() == id) {
                return disciplina;
            }
         }
        return null; 
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

//PARTE DO CURSO!!!!!!!!!
//PARTE DO CURSO!!!!!!!!!
//PARTE DO CURSO!!!!!!!!!
//PARTE DO CURSO!!!!!!!!!
//PARTE DO CURSO!!!!!!!!!
//PARTE DO CURSO!!!!!!!!!
//PARTE DO CURSO!!!!!!!!!


        public void adicionarCurso(Curso curso) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_CURSO, true))) {
                StringBuilder disciplinasStr = new StringBuilder();
                for (Disciplina disciplina : curso.getDisciplinas()) {
                    disciplinasStr.append(disciplina.getId()).append(";");
                }
                writer.write(curso.getNome() + "," + curso.getCreditosNecessarios() + "," + disciplinasStr.toString() + "\n");
            } catch (IOException e) {
                System.out.println("Erro ao adicionar curso: " + e.getMessage());
            }
        }

        public List<Curso> listarCursos() {
            List<Curso> cursos = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_CURSO))) {
                String linha;
                while ((linha = reader.readLine()) != null) {
                    String[] dados = linha.split(",");
                    if (dados.length >= 3) {
                        String nome = dados[0].trim();
                        int creditosNecessarios = Integer.parseInt(dados[1].trim());
                        String[] idsDisciplinas = dados[2].split(";");
                        List<Disciplina> disciplinas = new ArrayList<>();
                        for (String id : idsDisciplinas) {
                            Disciplina disciplina = buscarDisciplinaPorId(Integer.parseInt(id.trim()));
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

        public void removerCurso(String nome) {
            List<String> cursos = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_CURSO))) {
                String linha;
                while ((linha = reader.readLine()) != null) {
                    if (!linha.contains(nome)) {
                        cursos.add(linha);
                    }
                }
            } catch (IOException e) {
                System.out.println("Erro ao ler arquivo: " + e.getMessage());
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_CURSO))) {
                for (String curso : cursos) {
                    writer.write(curso + "\n");
                }
            } catch (IOException e) {
                System.out.println("Erro ao atualizar arquivo: " + e.getMessage());
            }
        }
    
    public void cancelarDisciplina(Disciplina disciplina) {
        
    }
    
    public void gerarCurriculo() {
    
    }

}