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
    usecase "Solicitar Matrícula (1ª opção - obrigatória)" as UC1A
    usecase "Solicitar Matrícula (Alternativa 1)" as UC1B
    usecase "Solicitar Matrícula (Alternativa 2)" as UC1C
    usecase "Cancelar Matrícula" as UC2
    usecase "Consultar Disciplinas" as UC3
    usecase "Validar Login" as UC4
    usecase "Gerar Currículo do Semestre" as UC6
    usecase "Consultar Alunos Matriculados" as UC7
    usecase "Cancelar Disciplina por Baixa Demanda" as UC9
    usecase "Aprovar matrícula" as UC10
    usecase "Realizar pagamento" as UC11
    usecase "Solicitar cancelamento de matrícula" as UC12
    usecase "Emitir cobrança do boleto bancário" as UC13
    usecase "Gerenciar Disciplinas" as UC14
    usecase "Gerenciar Professores" as UC15
    usecase "Gerenciar Alunos" as UC16
}

Aluno --> UC1A
Aluno --> UC1B
Aluno --> UC1C
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
UC1A --> Secretaria
UC1B --> Secretaria
UC1C --> Secretaria
UC12 --> Secretaria

note right of UC1A
Matrículas só serão aceitas no período proposto.
end note

note right of UC12
Matrículas só serão canceladas no período proposto.
end note
@enduml