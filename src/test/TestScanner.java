package test;

import exception.LexicalException;
import org.junit.jupiter.api.Test;
import scanner.Scanner;
import token.Token;
import token.TokenType;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TestScanner {

    @Test
    void erroriIdNumbers() throws FileNotFoundException {

        Scanner scan = new Scanner("/home/jacopo/Git/CompilatoreAcDc/src/test/data/testScanner/erroriIdNumbers.txt");

        try {
            assertThrows(LexicalException.class, () -> scan.nextToken());
            assertNull(scan.nextToken());
            assertThrows(LexicalException.class, () -> scan.nextToken());
            assertThrows(LexicalException.class, () -> scan.nextToken());
            assertThrows(LexicalException.class, () -> scan.nextToken());
            System.out.println(scan.nextToken());
            System.out.println(scan.nextToken());
            assertThrows(LexicalException.class, () -> scan.nextToken());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (LexicalException e) {
            throw new RuntimeException(e);
        }

        //.123
//        try {
//            assertNull(scan.nextToken());
//        } catch (IOException | LexicalException e ) {
//            throw new RuntimeException(e);
//        }

        //asd.123
//        try {
//            System.out.println(scan.nextToken().toString());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (LexicalException e) {
//            throw new RuntimeException(e);
//        }
//        LexicalException exc2 = assertThrows(LexicalException.class, () -> scan.nextToken());
//        assertEquals("Carattere illegale dopo un ID", exc2.getMessage());

    }

    @Test
    void testFLOAT() throws FileNotFoundException {

        Scanner scan = new Scanner("/home/jacopo/Git/CompilatoreAcDc/src/test/data/testScanner/erroriIdNumbers.txt");

        System.out.println();

    }
}
