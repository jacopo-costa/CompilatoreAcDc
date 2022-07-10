package test;

import ast.NodeProgram;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import parser.Parser;
import scanner.Scanner;
import visitor.TypeCheckingVisitor;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@Testable
public class TestVisitor {

    @Test
    void testDicRipetute() throws Exception {

        Parser parse = new Parser(new Scanner("src/test/data/testTypeChecking/1_dicRipetute.txt"));

        NodeProgram np = parse.parse();
        np.accept(new TypeCheckingVisitor());

    }

    @Test
    void testFileCorretto() throws Exception {

        Parser parse = new Parser(new Scanner("src/test/data/testTypeChecking/2_fileCorrect.txt"));

        NodeProgram np = parse.parse();
        assertDoesNotThrow(() -> np.accept(new TypeCheckingVisitor()));

    }
}
