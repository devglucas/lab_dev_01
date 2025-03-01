package code.Java;

public class app {
public static void main(String[] args) {
        Secretaria secretaria = new Secretaria();
        
        // Criando e adicionando professores
        Professor professor1 = new Professor("Jooo Silva", "12345");
        Professor professor2 = new Professor("Maria Souza", "67890");
        
        secretaria.adicionarProfessor(professor1);
        secretaria.adicionarProfessor(professor2);
        
        // Editando professor
        secretaria.editarProfessor("12345", "Jooo Santos");
        
        // Removendo professor
        secretaria.removerProfessor("67890");
    }
}
