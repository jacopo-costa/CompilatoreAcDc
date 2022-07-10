package test;

import exception.SyntaxException;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import parser.Parser;
import scanner.Scanner;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@Testable
class TestParser {

    @Test
    void testParserCorretto1() throws IOException {

        Scanner scan = new Scanner("src/test/data/testParser/testParserCorretto1.txt");
        Parser parse = new Parser(scan);
        assertDoesNotThrow(() -> parse.parse());

    }

    @Test
    void testParserCorretto2() throws IOException {

        Scanner scan = new Scanner("src/test/data/testParser/testParserCorretto2.txt");
        Parser parse = new Parser(scan);
        assertDoesNotThrow(() -> parse.parse());

    }

    @Test
    void testParserEcc_0() throws IOException {

        Scanner scan = new Scanner("src/test/data/testParser/testParserEcc_0.txt");
        Parser parse = new Parser(scan);

        SyntaxException exc = assertThrows(SyntaxException.class, () -> parse.parse());
        assertEquals("Unexpected token in parsePrg", exc.getMessage());

    }

    @Test
    void testParserEcc_1() throws IOException {

        Scanner scan = new Scanner("src/test/data/testParser/testParserEcc_1.txt");
        Parser parse = new Parser(scan);

        SyntaxException exc = assertThrows(SyntaxException.class, () -> parse.parse());
        assertEquals("Unexpected value parseTr: TIMES", exc.getMessage());

    }

    @Test
    void testParserEcc_2() throws IOException {

        Scanner scan = new Scanner("src/test/data/testParser/testParserEcc_2.txt");
        Parser parse = new Parser(scan);

        SyntaxException exc = assertThrows(SyntaxException.class, () -> parse.parse());
        assertEquals("Unexpected token in parsePrg", exc.getMessage());

    }

    @Test
    void testParserEcc_3() throws IOException {

        Scanner scan = new Scanner("src/test/data/testParser/testParserEcc_3.txt");
        Parser parse = new Parser(scan);

        SyntaxException exc = assertThrows(SyntaxException.class, () -> parse.parse());
        assertEquals("Expected: ASSIGN Got: PLUS At: 2", exc.getMessage());

    }

    @Test
    void testParserEcc_4() throws IOException {

        Scanner scan = new Scanner("src/test/data/testParser/testParserEcc_4.txt");
        Parser parse = new Parser(scan);

        SyntaxException exc = assertThrows(SyntaxException.class, () -> parse.parse());
        assertEquals("Expected: ID Got: INT At: 2", exc.getMessage());

    }

    @Test
    void testParserEcc_5() throws IOException {

        Scanner scan = new Scanner("src/test/data/testParser/testParserEcc_5.txt");
        Parser parse = new Parser(scan);

        SyntaxException exc = assertThrows(SyntaxException.class, () -> parse.parse());
        assertEquals("Expected: ID Got: INT At: 3", exc.getMessage());

    }

}
