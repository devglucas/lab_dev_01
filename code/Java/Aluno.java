package code.Java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Aluno extends Usuario {
    private String nome;
    private Curso curso;
    private String matricula;
    public static final int limOptativas = 2;
    public static final int limObrigatorias = 4;
    private List<Disciplina> disciplinasMatriculadas;
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
    
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public List<Disciplina> getDisciplinasMatriculadas() {
        return disciplinasMatriculadas;
    }

    public void setDisciplinasMatriculadas(List<Disciplina> disciplinasMatriculadas) {
        this.disciplinasMatriculadas = disciplinasMatriculadas;
    }

    public Aluno(String email, String senha, String nome, Curso curso, String matricula, List<Disciplina> disciplinasMatriculadas) {
        super(email, senha, "ALUNO");
        this.nome = nome;
        this.curso = curso;
        this.matricula = matricula;
        this.disciplinasMatriculadas = disciplinasMatriculadas;
    }

    public Aluno(String email, String senha, String nome, String matricula) {
        super(email, senha, "ALUNO");
        this.nome = nome;
        this.matricula = matricula;
        this.curso = null; 
        this.disciplinasMatriculadas = new ArrayList<>(); 
    }

    public Aluno(String email, String senha) {
        super(email, senha, "ALUNO");
    }

       public static Aluno buscarAlunoPorEmail(String email) {
        String filePath = "code/Java/DB/Alunos.csv";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados[0].equals(email)) {
                    String senha = dados[1];
                    String nome = dados[2];
                    String matricula = dados[3];
                    List<Disciplina> disciplinasMatriculadas = new ArrayList<>();
                    if (dados.length > 4) {
                        String[] disciplinasIds = dados[4].split(";");
                        for (String id : disciplinasIds) {
                            Disciplina disciplina = Secretaria.buscarDisciplinaPorId(Integer.parseInt(id));
                            if (disciplina != null) {
                                disciplinasMatriculadas.add(disciplina);
                            }
                        }
                    }
                    return new Aluno(email, senha, nome, null, matricula, disciplinasMatriculadas);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de alunos: " + e.getMessage());
        }
        return null;
    }

    public static void listarDisciplinasDisponiveis() {
        List<Disciplina> disciplinas = Secretaria.listarDisciplinas();
        System.out.println("Disciplinas disponíveis:");
        for (Disciplina disciplina : disciplinas) {
            System.out.println("ID: " + disciplina.getId() + " - " + disciplina.getNome() + " ("
                    + disciplina.getTipoDisciplina() + ")");
        }
    }

    private void atualizarCSVAluno() {
        String filePath = "code/Java/DB/Alunos.csv";
        List<String> linhas = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.contains(this.getEmail())) {
                    StringBuilder disciplinasStr = new StringBuilder();
                    for (Disciplina disciplina : disciplinasMatriculadas) {
                        disciplinasStr.append(disciplina.getId()).append(";");
                    }
                    if (disciplinasStr.length() > 0) {
                        disciplinasStr.deleteCharAt(disciplinasStr.length() - 1);
                    }
                    linha = this.getNome() + "," + this.getMatricula() + "," + this.getEmail() + "," + this.getSenha()
                            + "," + disciplinasStr.toString();
                }
                linhas.add(linha);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de alunos: " + e.getMessage());
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String linha : linhas) {
                writer.write(linha + "\n");
            }
        } catch (IOException e) {
            System.out.println("Erro ao atualizar o arquivo de alunos: " + e.getMessage());
        }
    }

    public void solicitarMatricula(Disciplina disciplina) {
        if (disciplinasMatriculadas.contains(disciplina)) {
            System.out.println("Você já está matriculado nesta disciplina.");
            return;
        }

        long obrigatorias = disciplinasMatriculadas.stream()
                .filter(d -> d.getTipoDisciplina() == TIPODISCIPLINA.OBRIGATORIA)
                .count();
        long optativas = disciplinasMatriculadas.stream()
                .filter(d -> d.getTipoDisciplina() == TIPODISCIPLINA.OPTATIVA)
                .count();

        if (disciplina.getTipoDisciplina() == TIPODISCIPLINA.OBRIGATORIA && obrigatorias >= limObrigatorias) {
            System.out.println("Limite de disciplinas obrigatórias atingido.");
            return;
        }

        if (disciplina.getTipoDisciplina() == TIPODISCIPLINA.OPTATIVA && optativas >= limOptativas) {
            System.out.println("Limite de disciplinas optativas atingido.");
            return;
        }

        disciplinasMatriculadas.add(disciplina);
        System.out.println("Matrícula na disciplina " + disciplina.getNome() + " realizada com sucesso.");

        atualizarCSVAluno();
    }


    public void solicitarCancelamento(Disciplina disciplina, LocalDate data) {
        GerenciadorDisciplinas.solicitarCancelamento(disciplina.getNome());
    }
    
    public void realizarPagamento() {
        double valorTotal = 0.0;
        for (Disciplina disciplina : disciplinasMatriculadas) {
            valorTotal += disciplina.getValor();
        }
        if (valorTotal == 0) {
            System.out.println("Nenhuma disciplina matriculada. Nada a pagar.");
            return;
        }
        String filePath = "code/Java/DB/Pagamentos.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(getMatricula() + "," + valorTotal + "," + LocalDate.now() + "\n");
        } catch (IOException e) {
            System.out.println("Erro ao registrar pagamento: " + e.getMessage());
            return;
        }
        System.out.println("Pagamento de R$" + valorTotal + " registrado com sucesso para o aluno " + getNome() + ".");
    }

    public void consultarDisciplinas(List<Disciplina> disciplinas) {
            GerenciadorDisciplinas.consultarDisciplinas();
            if (disciplinas.isEmpty()) {
                System.out.println("Nenhuma disciplina encontrada.");
            } else {
                System.out.println("Disciplinas disponíveis:");
                for (Disciplina disciplina : disciplinas) {
                    System.out.println(disciplina);
                }
            }
        }

    }


