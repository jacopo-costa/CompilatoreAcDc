import ast.NodeProgram;
import ast.TypeDescriptor;
import parser.Parser;
import scanner.Scanner;
import symbolTable.SymbolTable;
import visitor.CodeGeneratorVisitor;
import visitor.TypeCheckingVisitor;

public class main {

    public static void main(String[] args) {

        if (args.length > 1) {
            throw new IllegalArgumentException("Too many arguments");
        } else if (args.length == 0) {
            throw new IllegalArgumentException("Missing input filename");
        }

        try {
            //Inizializza albero AST dopo il controllo del parser e scanner
            NodeProgram np = new Parser(new Scanner(args[0])).parse();

            //Inizializza Symbol Table
            SymbolTable.init();

            //Controllo del TypeChecking
            TypeCheckingVisitor visitor = new TypeCheckingVisitor();
            np.accept(visitor);
            // Se si sono presentati errori li stampa
            if(np.getResType() == TypeDescriptor.ERROR){
                throw new Exception("Error in TypeCheckingVisitor: " + visitor.getLog());
            }

            //Generazione del codice
            CodeGeneratorVisitor cgv = new CodeGeneratorVisitor();
            np.accept(cgv);
            // Se si sono presentati errori li mostra altrimenti stampa il codice prodotto
            if(!cgv.getLog().isEmpty()){
                throw new Exception("Error in Code Generation: " + cgv.getLog());
            } else {
                System.out.println(cgv.getCode());
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}