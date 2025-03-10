@startuml
enum TIPODISCIPLINA { 
    OBRIGATORIA 
    OPTATIVA 
}

abstract Usuario { 
    - email: String 
    - senha: String 
    + validarLogin(senha: String): Boolean 
}

class Aluno {
    - nome: String
    - curso: Curso
    - matricula: String
    - limOptativas: int = 2
    - limObrigatorias: int = 4
    - disciplinasMatriculadas: List<Disciplina>

    + buscarAlunoPorId(idAluno: String): Aluno
    + buscarAlunoPorEmail(email: String): Aluno
    + listarDisciplinasDisponiveis(): List<Disciplina>
    + solicitarMatricula(disciplina: Disciplina): void
    + cancelarMatricula(disciplina: Disciplina): void
    + realizarPagamento(idNotaSelecionada: int): void
    + toString(): String
}

class Professor {
    - nome: String
    - matricula: String
    - disciplinas: List<Disciplina>

    + buscarProfessoresPorDisciplina(idDisciplina: int): List<Professor>
    + buscarProfessorPorEmail(email: String): Professor
    + adicionarDisciplina(disciplina: Disciplina): void
    + listarDisciplinasProfessor(): void
    + listarAlunosDasDisciplinas(): void
    + toString(): String
}

class Secretaria {
    - FILE_PATH: String = "code/Java/DB/"
    - FILE_PROF: String = FILE_PATH + "Professores.csv"
    - FILE_DISC: String = FILE_PATH + "Disciplinas.csv"
    - FILE_ALUN: String = FILE_PATH + "Alunos.csv"
    - FILE_USU: String = FILE_PATH + "Usuarios.csv"
    - FILE_CURSO: String = FILE_PATH + "Cursos.csv"

    + salvarUsuario(usuario: Usuario): void
    + adicionarProfessor(professor: Professor): void
    + adicionarDisciplinaAoProfessor(matricula: String, idDisciplina: int): void
    + removerProfessor(matricula: String): void
    + editarProfessor(matricula: String, novoNome: String): void
    + atualizarAlunoNoCSV(aluno: Aluno): void
    + adicionarAluno(aluno: Aluno): void
    + removerAluno(matricula: String): void
    + editarAluno(matricula: String, novoNome: String): void
    + adicionarDisciplina(disciplina: Disciplina): void
    + atualizarDisciplinaNoCSV(disciplina: Disciplina): void
    + removerDisciplina(id: int): void
    + editarDisciplina(id: int, novoNome: String, novoCredito: int): void
    + adicionarCurso(curso: Curso): void
    + removerCurso(nome: String): void
    + gerarCurriculo(idAluno: String): void
    + gerarNotaFiscal(notaFiscal: NotaFiscal): void
}
class GerenteFinanceiro {
    + lerNotasFiscais(): List<NotaFiscal>
    + escreverNotasFiscais(notasFiscais: List<NotaFiscal>): void
    + verificarNotaFiscal(idNotaFiscal: int): void
}

class Disciplina {
    - nome: String
    - id: int
    - credito: int
    - estaDisponivel: boolean
    - alunosMatriculados: List<Aluno>
    - static limCima: int = 60
    - static limBaixo: int = 3
    - tipoDisciplina: TIPODISCIPLINA

    + adicionarAluno(aluno: Aluno): void
    + removerAluno(aluno: Aluno): void
    + verificarDisponibilidade(): boolean
    + listarDisciplinas(): List<Disciplina>
    + buscarDisciplinaPorId(id: int): Disciplina
    + getAlunosMatriculadosProfessor(): List<Aluno>
}


class Curso {
    - nome: String
    - creditosNecessarios: int
    - disciplinas: List<Disciplina>

    + incluirDisciplina(disciplina: Disciplina): void
    + encontrarDisciplina(id: int): Disciplina
    + buscarCursoPorNome(nomeCurso: String): Curso
    + listarDisciplinasPorCurso(nomeCurso: String): List<Disciplina>
    + listarCursos(): List<Curso>
    + toString(): String
}

class NotaFiscal {
    - id: int
    - valor: double
    - estahPago: boolean
    - estahVerificado: boolean
    - aluno: Aluno

    + listarNotasFiscais(): List<NotaFiscal>
}

class GerenciadorUsuarios {
   - ARQUIVO_CSV: String = "code/Java/DB/Usuarios.csv"
    +carregarUsuarios(): List<Usuario>
}

class Login {
    + fazerLogin(): Usuario
}
Usuario <-- GerenciadorUsuarios
Usuario <-- Login

Usuario <|-- Aluno 
Usuario <|-- Professor 
Usuario <|-- Secretaria 
Usuario <|-- GerenteFinanceiro

Aluno "0..N" -- "1" Secretaria 
Aluno "1" -- "0..N" Curso 
Aluno "1" -- "3..60" Disciplina 
Professor "1" -- "0..N" Disciplina 
Curso "1" *-- "1..N" Disciplina

GerenteFinanceiro "1" --> "0..N" NotaFiscal : emite 
Aluno "1" o-- "0..N" NotaFiscal
@enduml