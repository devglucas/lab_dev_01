@startuml
left to right direction

actor Aluno
actor Professor
actor Secretaria
actor Usuario
actor "Gerente Financeiro"

Usuario <|-- Aluno
Usuario <|-- Professor
Usuario <|-- Secretaria

"Gerente Financeiro" --|> Usuario

rectangle "Sistema Acadêmico" {
    usecase "Solicitar Matrícula Obrigatória" as UC1O
    usecase "Solicitar Matrícula Optativa" as UC1P
    usecase "Cancelar Matrícula" as UC2
    usecase "Consultar Disciplinas" as UC3
    usecase "Validar Login" as UC4
    usecase "Gerar Currículo do Semestre" as UC6
    usecase "Consultar Alunos Matriculados" as UC7
    usecase "Cancelar Disciplina por Baixa Demanda" as UC9
    usecase "Aprovar Matrícula" as UC10
    usecase "Realizar pagamento" as UC11
    usecase "Solicitar cancelamento de matrícula" as UC12
    usecase "Emitir cobrança do boleto bancário" as UC13
    usecase "Gerenciar Disciplinas" as UC14
    usecase "Gerenciar Professores" as UC15
    usecase "Gerenciar Alunos" as UC16
}

Aluno --> UC1O
Aluno --> UC1P
Aluno --> UC3
Aluno --> UC11
Aluno --> UC12

Usuario --> UC4

Professor --> UC7

Secretaria --> UC2
Secretaria --> UC6
Secretaria --> UC9
Secretaria --> UC10
Secretaria --> UC14
Secretaria --> UC15
Secretaria --> UC16

"Gerente Financeiro" --> UC13

UC6 --> Aluno
UC1O --> Secretaria
UC1P --> Secretaria
UC12 --> Secretaria

note right of UC1O
Matrículas só serão aceitas no período proposto.
end note

note right of UC12
Matrículas só serão canceladas no período proposto.
end note
@enduml
