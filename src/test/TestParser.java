package test;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import parser.Parser;
import scanner.Scanner;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@Testable
class TestParser {

    @Test
    void testParser() throws IOException {

        Scanner scan = new Scanner("src/test/data/testParser/testParserCorretto1.txt");
        Parser parse = new Parser(scan);
        assertDoesNotThrow(() -> parse.parse());

    }
}
