package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import ast.NodeDecSt;
import ast.NodeDecl;
import ast.NodeProgram;
import exception.LexicalException;
import org.junit.jupiter.api.Test;

import parser.Parser;
import scanner.Scanner;
import token.Token;
import token.TokenType;

class TestParser {

    @Test
    void testParser() throws IOException {

        Scanner scan = new Scanner("src/test/data/testParser/testParserCorretto1.txt");
        Parser parse = new Parser(scan);
        assertDoesNotThrow(() -> parse.parse());

    }

    @Test
    void testAST () throws Exception {
        Scanner scan = new Scanner("src/test/data/testParser/testParserCorretto1.txt");
        Parser parse = new Parser(scan);

        NodeProgram test = parse.parse();
        ArrayList<NodeDecSt> dec = test.getDecSts();

        for (NodeDecSt st : dec) {
            System.out.println(st.toString());
        }
    }
}
