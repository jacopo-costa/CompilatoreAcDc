package test;

import ast.NodeProgram;
import ast.TypeDescriptor;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import parser.Parser;
import scanner.Scanner;
import visitor.TypeCheckingVisitor;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testable
public class TestVisitor {

    @Test
    void testDicRipetute() throws Exception {

        NodeProgram np = new Parser(new Scanner("src/test/data/testTypeChecking/1_dicRipetute.txt")).parse();
        TypeCheckingVisitor visitor = new TypeCheckingVisitor();
        np.accept(visitor);

        String log = "ID: a already present";

        assertEquals(log, visitor.getLog());

    }

    @Test
    void testFileCorretto() throws Exception {

        NodeProgram np = new Parser(new Scanner("src/test/data/testTypeChecking/2_fileCorrect.txt")).parse();
        np.accept(new TypeCheckingVisitor());

        assertEquals(TypeDescriptor.VOID, np.getResType());

    }

    @Test
    void testIdNotDeclared() throws Exception {

        NodeProgram np = new Parser(new Scanner("src/test/data/testTypeChecking/3_IdNotDeclare.txt")).parse();
        TypeCheckingVisitor visitor = new TypeCheckingVisitor();
        np.accept(visitor);

        String log = "ID: b not present in Symbol Table\nID: c not present in Symbol Table\n";

        assertEquals(log, visitor.getLog());

    }

    @Test
    void testErrorAssignConvert() throws Exception {

        NodeProgram np = new Parser(new Scanner("src/test/data/testTypeChecking/errorAssignConvert.txt")).parse();
        TypeCheckingVisitor visitor = new TypeCheckingVisitor();
        np.accept(visitor);

        //Prima rileva che a non è presente poi dice che alla riga 3
        //c'è un assign illegale con due variabili non inizializzate.
        //Alla riga 7 viene assegnato un float ad un int.
        String log = """
                ID: a not present in Symbol Table
                Illegal assignment: Assign: <ID: a, ID: b>
                Illegal assignment: Assign: <ID: a, ID: b>
                """;

        assertEquals(log, visitor.getLog());

    }

    @Test
    void testErrorOperations() throws Exception {

        NodeProgram np = new Parser(new Scanner("src/test/data/testTypeChecking/errorOp.txt")).parse();
        TypeCheckingVisitor visitor = new TypeCheckingVisitor();
        np.accept(visitor);

        //b non può eseguire una operazione su se stesso non ancora inizializzato
        //e poichè INT non può essere moltiplicato per un FLOAT
        //Alla riga 5 invece viene assegnata una operazione tra due variabili non dichiarate
        String log = """
                Illegal assignment: Assign: <ID: b, (Left: ID: b TIMES Right: <0.1 Type : FLOAT>)>
                ID: c not present in Symbol Table
                ID: d not present in Symbol Table
                Illegal binary operation: (Left: ID: c PLUS Right: ID: d)
                Illegal assignment: Assign: <ID: b, (Left: ID: c PLUS Right: ID: d)>
                """;

        assertEquals(log, visitor.getLog());

    }

    @Test
    void testFileCorretto2() throws Exception {

        NodeProgram np = new Parser(new Scanner("src/test/data/testTypeChecking/fileCorrect2.txt")).parse();
        np.accept(new TypeCheckingVisitor());

        assertEquals(TypeDescriptor.VOID, np.getResType());

    }

    @Test
    void testGenerale() throws Exception {

        NodeProgram np = new Parser(new Scanner("src/test/data/testTypeChecking/testGenerale.txt")).parse();
        TypeCheckingVisitor visitor = new TypeCheckingVisitor();
        np.accept(visitor);

        //Assegnamento illegale poichè alla riga 4 viene eseguita una operazione binaria su b
        //che non è ancora stato inizializzato
        String log = "Illegal assignment: Assign: <ID: b, (Left: (Left: ID: a MINUS Right: <3.2 Type : FLOAT>) DIV Right: ID: b)>\n";

        assertEquals(log, visitor.getLog());

    }

    @Test
    void testGenerale2() throws Exception {

        NodeProgram np = new Parser(new Scanner("src/test/data/testTypeChecking/testGenerale2.txt")).parse();
        np.accept(new TypeCheckingVisitor());

        assertEquals(TypeDescriptor.VOID, np.getResType());

    }

    @Test
    void testGenerator() throws Exception {

        NodeProgram np = new Parser(new Scanner("src/test/data/testTypeChecking/testGenerator.txt")).parse();
        np.accept(new TypeCheckingVisitor());

        assertEquals(TypeDescriptor.VOID, np.getResType());

    }
}
