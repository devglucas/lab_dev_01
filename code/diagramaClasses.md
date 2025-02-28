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
    -limOptativas: int = 2
    -limObrigatorias: int = 4
    - disciplinasMatriculadas: List<Disciplina>
    + solicitarMatricula(disciplina: Disciplina, data: LocalDate): void
    + solicitarCancelamento(disciplina: Disciplina, data: LocalDate): void
    + realizarPagamento(): void
    + consultarDisciplinas(disciplinas: List<Disciplina>): void
}

class Professor {
    - nome: String
    - matricula: String
    - disciplinasMinistradas: List<Disciplina>
    + consultarAlunosMatriculados(disciplina: Disciplina): void
}

class Secretaria {
    -List<Solicitacoes>: String
    +cadatrarUsuario(): void
    + adicionarProfessores(): void
    + removerProfessores(matricula: String): void
    + editarProfessores(matricula: String): void
    + adicionarDisciplinas(): void
    + removerDisciplinas(id: int): void
    + editarDisciplinas(id: int): void
    + adicionarAlunos(): void
    + removerAlunos(matricula: String): void
    + editarAlunos(matricula: String): void
    + aprovarMatricula(aluno: Aluno, disciplina: Disciplina): Aluno
    + reprovarMatricula(aluno: Aluno, disciplina: Disciplina): void
    + cancelarDisciplina(disciplina: Disciplina): void
    + gerarCurriculo(): void
}

class GerenteFinanceiro {
    -id: int
    -nome: String
    + calcularValor(disciplinas: List<Disciplina>): double
    + emitirNotaFiscal(aluno: Aluno, valor: double): NotaFiscal
}

class Disciplina {
    - nome: String
    - id: int
    - credito: int
    - estaDisponivel: boolean
    - limAcima: int = 60
    - limBaixo: int = 3
    - alunosMatriculados: List<Aluno>
    + tipoDisciplina: TIPODISCIPLINA
    + verificarDisponibilidade(): Boolean
    + gerarCurriculo(): void
}

class Curso {
    - nome: String
    - creditosNecessarios: int
    - disciplinas: List<Disciplina>
    + getDisciplinas(): List<Disciplina>
    + incluirDisciplina(): disciplina: Disciplina
    + encontrarDisciplina(id: int, disciplinas: List<Disciplina>): void
}

class NotaFiscal {
    - id: int
    - valor: double
    - dataEmissao: LocalDate
    - aluno: Aluno
    + emitirNotaFiscal(aluno: Aluno, valor: double): NotaFiscal
}

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
