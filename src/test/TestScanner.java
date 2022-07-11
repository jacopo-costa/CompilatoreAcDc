package test;

import exception.ScannerException;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import scanner.Scanner;
import token.TokenType;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@Testable
class TestScanner {

    @Test
    void erroriIdNumbers() throws ScannerException, FileNotFoundException {

        Scanner scan = new Scanner("/home/jacopo/Git/CompilatoreAcDc/src/test/data/testScanner/erroriIdNumbers.txt");

        //123. r1
        ScannerException exc = assertThrows(ScannerException.class, () -> scan.nextToken());
        assertEquals("Illegal character after float point", exc.getMessage());

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
        ScannerException exc2 = assertThrows(ScannerException.class, () -> scan.nextToken());
        assertEquals("Illegal character after float point", exc2.getMessage());

        //123 r5
        ScannerException exc3 = assertThrows(ScannerException.class, () -> scan.nextToken());
        assertEquals("Illegal character after int number", exc3.getMessage());

        //asd r5
        assertEquals("<ID,r:5,asd>", scan.nextToken().toString());

        //abcd6 r6
        assertEquals("<ID,r:6,abcd6>", scan.nextToken().toString());

    }

    @Test
    void testEOF() throws ScannerException, IOException {

        Scanner scan = new Scanner("src/test/data/testScanner/testEOF.txt");

        assertEquals(TokenType.EOF, scan.nextToken().getTipo());

    }

    @Test
    void testFLOAT() throws IOException, ScannerException {

        Scanner scan = new Scanner("src/test/data/testScanner/testFLOAT.txt");

        assertEquals("<FLOAT,r:1,098.895>", scan.nextToken().toString());

        ScannerException exc = assertThrows(ScannerException.class, () -> scan.nextToken());
        assertEquals("Illegal character after float point", exc.getMessage());

        ScannerException exc2 = assertThrows(ScannerException.class, () -> scan.nextToken());
        assertEquals("Excessive decimal numbers in float", exc2.getMessage());

    }

    @Test
    void testGenerale() throws ScannerException, IOException {

        Scanner scan = new Scanner("src/test/data/testScanner/testGenerale.txt");

        assertEquals(TokenType.TYINT, scan.nextToken().getTipo());
        assertEquals("<ID,r:1,temp>", scan.nextToken().toString());
        assertEquals(TokenType.SEMI, scan.nextToken().getTipo());

        assertEquals("<ID,r:2,temp>", scan.nextToken().toString());
        assertEquals(TokenType.ASSIGN, scan.nextToken().getTipo());
        ScannerException exc = assertThrows(ScannerException.class, () -> scan.nextToken());
        assertEquals("Illegal character after float point", exc.getMessage());
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
    void testID() throws ScannerException, IOException {

        Scanner scan = new Scanner("src/test/data/testScanner/testID.txt");

        assertEquals("<ID,r:1,jskjdsfhkjdshkf>", scan.nextToken().toString());
        assertEquals("<ID,r:2,printl>", scan.nextToken().toString());
        assertEquals("<ID,r:4,hhhjj>", scan.nextToken().toString());

    }

    @Test
    void testINT() throws ScannerException, IOException {

        Scanner scan = new Scanner("src/test/data/testScanner/testINT.txt");

        assertEquals("<INT,r:2,698>", scan.nextToken().toString());

    }

    @Test
    void testKeywords() throws ScannerException, IOException {

        Scanner scan = new Scanner("src/test/data/testScanner/testKeywords.txt");

        assertEquals(TokenType.PRINT, scan.nextToken().getTipo());
        assertEquals(TokenType.TYFLOAT, scan.nextToken().getTipo());
        assertEquals(TokenType.TYINT, scan.nextToken().getTipo());

    }

    @Test
    void testOperators() throws ScannerException, IOException {

        Scanner scan = new Scanner("src/test/data/testScanner/testOperators.txt");

        assertEquals(TokenType.PLUS, scan.nextToken().getTipo());
        assertEquals(TokenType.MINUS, scan.nextToken().getTipo());
        assertEquals(TokenType.TIMES, scan.nextToken().getTipo());
        assertEquals(TokenType.DIV, scan.nextToken().getTipo());
        assertEquals(TokenType.ASSIGN, scan.nextToken().getTipo());
        assertEquals(TokenType.SEMI, scan.nextToken().getTipo());

    }

    @Test
    void testScanId() throws ScannerException, IOException {

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
