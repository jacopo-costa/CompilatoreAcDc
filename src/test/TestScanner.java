package test;

import exception.LexicalException;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import scanner.Scanner;
import token.TokenType;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@Testable
class TestScanner {

    @Test
    void erroriIdNumbers() throws IOException, LexicalException {

        Scanner scan = new Scanner("/home/jacopo/Git/CompilatoreAcDc/src/test/data/testScanner/erroriIdNumbers.txt");

        //123. r1
        LexicalException exc = assertThrows(LexicalException.class, () -> scan.nextToken());
        assertEquals("Trovato un carattere illegale dopo un punto float", exc.getMessage());

        //asd r1
        assertEquals("<ID,r:1,asd>", scan.nextToken().toString());

        //. r2
        assertNull(scan.nextToken());

        //123 r2
        assertEquals("<INT,r:2,123>", scan.nextToken().toString());

        //asd r3
        assertEquals("<ID,r:3,asd>", scan.nextToken().toString());

        //. r3
        assertNull(scan.nextToken());

        //123 r3
        assertEquals("<INT,r:3,123>", scan.nextToken().toString());

        //int r4
        assertEquals(TokenType.TYINT, scan.nextToken().getTipo());

        //. r4
        assertNull(scan.nextToken());

        //123. r4
        LexicalException exc2 = assertThrows(LexicalException.class, () -> scan.nextToken());
        assertEquals("Trovato un carattere illegale dopo un punto float", exc2.getMessage());

        //123 r5
        LexicalException exc3 = assertThrows(LexicalException.class, () -> scan.nextToken());
        assertEquals("Numero seguito da un carattere illegale", exc3.getMessage());

        //asd r5
        assertEquals("<ID,r:5,asd>", scan.nextToken().toString());

        //abcd6 r6
        assertEquals("<ID,r:6,abcd6>", scan.nextToken().toString());

    }

    @Test
    void testEOF() throws LexicalException, IOException {

        Scanner scan = new Scanner("src/test/data/testScanner/testEOF.txt");

        assertEquals(TokenType.EOF, scan.nextToken().getTipo());

    }

    @Test
    void testFLOAT() throws IOException, LexicalException {

        Scanner scan = new Scanner("src/test/data/testScanner/testFLOAT.txt");

        assertEquals("<FLOAT,r:1,098.895>", scan.nextToken().toString());

        LexicalException exc = assertThrows(LexicalException.class, () -> scan.nextToken());
        assertEquals("Trovato un carattere illegale dopo un punto float", exc.getMessage());

        LexicalException exc2 = assertThrows(LexicalException.class, () -> scan.nextToken());
        assertEquals("Eccessivi decimali dopo la virgola", exc2.getMessage());

    }

    @Test
    void testGenerale() throws LexicalException, IOException {

        Scanner scan = new Scanner("src/test/data/testScanner/testGenerale.txt");

        assertEquals(TokenType.TYINT, scan.nextToken().getTipo());
        assertEquals("<ID,r:1,temp>", scan.nextToken().toString());
        assertEquals(TokenType.SEMI, scan.nextToken().getTipo());

        assertEquals("<ID,r:2,temp>", scan.nextToken().toString());
        assertEquals(TokenType.ASSIGN, scan.nextToken().getTipo());
        LexicalException exc = assertThrows(LexicalException.class, () -> scan.nextToken());
        assertEquals("Trovato un carattere illegale dopo un punto float", exc.getMessage());
        assertEquals(TokenType.SEMI, scan.nextToken().getTipo());

        assertEquals(TokenType.TYFLOAT, scan.nextToken().getTipo());
        assertEquals("<ID,r:4,b>", scan.nextToken().toString());
        assertEquals(TokenType.SEMI, scan.nextToken().getTipo());

        assertEquals("<ID,r:5,b>", scan.nextToken().toString());
        assertEquals(TokenType.ASSIGN, scan.nextToken().getTipo());
        assertEquals("<ID,r:5,temp>", scan.nextToken().toString());
        assertEquals(TokenType.PLUS, scan.nextToken().getTipo());
        assertEquals("<FLOAT,r:5,3.2>", scan.nextToken().toString());
        assertEquals(TokenType.SEMI, scan.nextToken().getTipo());

        assertEquals(TokenType.PRINT, scan.nextToken().getTipo());
        assertEquals("<ID,r:6,b>", scan.nextToken().toString());
        assertEquals(TokenType.SEMI, scan.nextToken().getTipo());

    }

    @Test
    void testID() throws LexicalException, IOException {

        Scanner scan = new Scanner("src/test/data/testScanner/testID.txt");

        assertEquals("<ID,r:1,jskjdsfhkjdshkf>", scan.nextToken().toString());
        assertEquals("<ID,r:2,printl>", scan.nextToken().toString());
        assertEquals("<ID,r:4,hhhjj>", scan.nextToken().toString());

    }

    @Test
    void testINT() throws LexicalException, IOException {

        Scanner scan = new Scanner("src/test/data/testScanner/testINT.txt");

        assertEquals("<INT,r:2,698>", scan.nextToken().toString());

    }

    @Test
    void testKeywords() throws LexicalException, IOException {

        Scanner scan = new Scanner("src/test/data/testScanner/testKeywords.txt");

        assertEquals(TokenType.PRINT, scan.nextToken().getTipo());
        assertEquals(TokenType.TYFLOAT, scan.nextToken().getTipo());
        assertEquals(TokenType.TYINT, scan.nextToken().getTipo());

    }

    @Test
    void testOperators() throws LexicalException, IOException {

        Scanner scan = new Scanner("src/test/data/testScanner/testOperators.txt");

        assertEquals(TokenType.PLUS, scan.nextToken().getTipo());
        assertEquals(TokenType.MINUS, scan.nextToken().getTipo());
        assertEquals(TokenType.TIMES, scan.nextToken().getTipo());
        assertEquals(TokenType.DIV, scan.nextToken().getTipo());
        assertEquals(TokenType.ASSIGN, scan.nextToken().getTipo());
        assertEquals(TokenType.SEMI, scan.nextToken().getTipo());

    }

    @Test
    void testScanId() throws LexicalException, IOException {

        Scanner scan = new Scanner("src/test/data/testScanner/testScanId.txt");

        assertEquals(TokenType.TYINT, scan.nextToken().getTipo());
        assertEquals(TokenType.TYFLOAT, scan.nextToken().getTipo());
        assertEquals(TokenType.PRINT, scan.nextToken().getTipo());
        assertEquals("<ID,r:4,nome>", scan.nextToken().toString());
        assertEquals("<ID,r:5,intnome>", scan.nextToken().toString());
        assertEquals(TokenType.TYINT, scan.nextToken().getTipo());
        assertEquals("<ID,r:6,nome>", scan.nextToken().toString());

    }

}
