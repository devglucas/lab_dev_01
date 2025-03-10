package code.Java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Disciplina {
    private String nome;
    private int id;
    private int credito;
    private boolean estaDisponivel;
    private static final int limCima = 60;
    private static final int limBaixo = 3;
    public TIPODISCIPLINA tipoDisciplina;
    private List<Aluno> alunosMatriculados;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCredito() {
        return credito;
    }

    public void setCredito(int credito) {
        this.credito = credito;
    }

    public boolean isEstaDisponivel() {
        return estaDisponivel;
    }

    public void setEstaDisponivel(boolean estaDisponivel) {
        this.estaDisponivel = estaDisponivel;
    }

    public TIPODISCIPLINA getTipoDisciplina() {
        return tipoDisciplina;
    }

    public void setTipoDisciplina(TIPODISCIPLINA tipoDisciplina) {
        this.tipoDisciplina = tipoDisciplina;
    }

    public void setAlunosMatriculados(List<Aluno> alunosMatriculados) {
        this.alunosMatriculados = alunosMatriculados;
        this.verificarDisponibilidade();
    }

    public void adicionarAluno(Aluno aluno) {
        if (alunosMatriculados.size() < limCima) {
            alunosMatriculados.add(aluno);
            this.verificarDisponibilidade();
        } else {
            throw new IllegalStateException("Limite máximo de alunos atingido para esta disciplina.");
        }
    }

    public void removerAluno(Aluno aluno) {
        alunosMatriculados.remove(aluno);
        this.verificarDisponibilidade();
    }

    public boolean verificarDisponibilidade() {
        int numAlunos = alunosMatriculados.size();
        estaDisponivel = numAlunos >= limBaixo && numAlunos <= limCima;
        return estaDisponivel;
    }

    public List<Aluno> getAlunosMatriculados() {
        return alunosMatriculados;
    }

    public Disciplina(String nome, int id, int credito, TIPODISCIPLINA tipoDisciplina, List<Aluno> alunosMatriculados) {
        this.nome = nome;
        this.id = id;
        this.credito = credito;
        this.tipoDisciplina = tipoDisciplina;
        this.alunosMatriculados = alunosMatriculados;
        this.verificarDisponibilidade();
    }

    public int getId() {
        return id;
    }

    public static Disciplina buscarDisciplinaPorId(int id) {

        List<Disciplina> disciplinas = Disciplina.listarDisciplinas();
        for (Disciplina disciplina : disciplinas) {
            if (disciplina.getId() == id) {
                return disciplina;
            }
        }
        return null;
    }

    public List<Aluno> getAlunosMatriculadosProfessor() {
        String FILE_PATH = "code/Java/DB/";
        String FILE_DISC = FILE_PATH + "Disciplinas.csv";
        List<Aluno> alunosMatriculados = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_DISC))) {
            String linha;
            boolean primeiraLinha = true;

            while ((linha = reader.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }

                String[] dados = linha.split(",");
                if (dados.length > 5 && Integer.parseInt(dados[1].trim()) == this.id) {
                    String idsAlunosStr = dados[5].trim();
                    if (!idsAlunosStr.isEmpty()) {
                        String[] idsAlunos = idsAlunosStr.split(";");
                        for (String idAluno : idsAlunos) {
                            try {
                                Aluno aluno = Aluno.buscarAlunoPorId(idAluno);
                                if (aluno != null) {
                                    alunosMatriculados.add(aluno);
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Erro ao converter ID do aluno: " + idAluno);
                            }
                        }
                    }
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de disciplinas: " + e.getMessage());
        }

        return alunosMatriculados;
    }

    public static List<Disciplina> listarDisciplinas() {
        String FILE_PATH = "code/Java/DB/";
        String FILE_DISC = FILE_PATH + "Disciplinas.csv";

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

}