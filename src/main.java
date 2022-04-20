import java.io.FileNotFoundException;
import java.io.IOException;

import scanner.Scanner;
import token.Token;
import token.TokenType;

public class main {

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub

        Scanner scan = null;

        try {
            scan = new Scanner("src/test/data/testScanner/testGenerale.txt");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println(scan.peekToken().toString());
        System.out.println(scan.nextToken().toString());
        System.out.println(scan.peekToken().toString());
        System.out.println(scan.nextToken().toString());
        System.out.println(scan.peekToken().toString());
        System.out.println(scan.nextToken().toString());

    }

}