package test;

import ast.NodeDecSt;
import ast.NodeProgram;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import parser.Parser;
import scanner.Scanner;

@Testable
class TestAST {

    @Test
    void testParserCorretto1() throws Exception {

        Scanner scan = new Scanner("src/test/data/testParser/testParserCorretto1.txt");
        Parser parse = new Parser(scan);

        NodeProgram np = parse.parse();
        for (NodeDecSt n : np) {
            System.out.println(n.toString());
        }

    }

}
