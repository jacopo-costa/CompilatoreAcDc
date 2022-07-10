package test;

import ast.NodeProgram;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import parser.Parser;
import scanner.Scanner;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@Testable
class TestAST {

    @Test
    void testParserCorretto1() throws Exception {

        Scanner scan = new Scanner("src/test/data/testParser/testParserCorretto1.txt");
        Parser parse = new Parser(scan);

        NodeProgram np = parse.parse();
        while(np.hasNext()){
            System.out.println(np.next().toString());
        }

    }

}
