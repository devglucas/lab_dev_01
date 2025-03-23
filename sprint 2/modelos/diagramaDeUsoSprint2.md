@startuml
actor Cliente
actor "Agente (Empresa/Banco)" as Agente
actor Servidor

rectangle "Sistema de Aluguel de Automóveis" {
    usecase "Cadastro de Usuário" as UC1
    usecase "Introduzir Pedido de Aluguel" as UC2
    usecase "Modificar Pedido de Aluguel" as UC3
    usecase "Consultar Pedido de Aluguel" as UC4
    usecase "Cancelar Pedido de Aluguel" as UC5
    usecase "Avaliar Pedido" as UC6
    usecase "Modificar Pedido (Agente)" as UC7
    usecase "Analisar Pedido Financeiramente" as UC8
    usecase "Executar Contrato" as UC9
    usecase "Registrar Dados do Cliente" as UC10
    usecase "Registrar Dados do Automóvel" as UC11
    usecase "Gerenciar Contrato de Crédito" as UC12
    usecase "Gerenciar Páginas Web" as UC13
}

Cliente --> UC1
Cliente --> UC2
Cliente --> UC3
Cliente --> UC4
Cliente --> UC5

Agente --> UC6
Agente --> UC7
Agente --> UC8
Agente --> UC9

Servidor --> UC10
Servidor --> UC11
Servidor --> UC12
Servidor --> UC13
@enduml