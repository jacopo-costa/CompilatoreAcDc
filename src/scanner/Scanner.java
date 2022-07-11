package scanner;

import exception.ScannerException;
import token.Token;
import token.TokenType;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Scanner {
    final char EOF = (char) -1; // int 65535
    private final PushbackReader buffer;
    private int riga;
    private Token currentToken;

    private List<Character> skipChars; // ' ', '\n', '\t', '\r', EOF
    private List<Character> letters; // 'a',...'z'
    private List<Character> numbers; // '0',...'9'

    private HashMap<Character, TokenType> operatorsMap;  //'+', '-', '*', '/', '=', ';'
    private HashMap<String, TokenType> keyWordsMap;  //"print", "float", "int"


    public Scanner(String fileName) throws FileNotFoundException {
        this.buffer = new PushbackReader(new FileReader(fileName));
        riga = 1;

        //inizializzare le liste e HashMaps
        inizializza();
    }


    private void inizializza() {

        currentToken = null;

        skipChars = new ArrayList<>(Arrays.asList(' ', '\n', '\t', '\r', EOF));

        letters = new ArrayList<>(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'));

        numbers = new ArrayList<>(Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));

        operatorsMap = new HashMap<>();
        keyWordsMap = new HashMap<>();


        operatorsMap.put('+', TokenType.PLUS);
        operatorsMap.put('-', TokenType.MINUS);
        operatorsMap.put('*', TokenType.TIMES);
        operatorsMap.put('/', TokenType.DIV);
        operatorsMap.put('=', TokenType.ASSIGN);
        operatorsMap.put(';', TokenType.SEMI);

        keyWordsMap.put("print", TokenType.PRINT);
        keyWordsMap.put("float", TokenType.TYFLOAT);
        keyWordsMap.put("int", TokenType.TYINT);

    }

    public Token peekToken() {

        if (currentToken == null) {
            try {
                currentToken = nextToken();
            } catch (ScannerException e) {
                e.printStackTrace();
            }
        }

        return currentToken;

    }

    public Token nextToken() throws ScannerException {

        // Se c'è un valore in currentToken lo riporta a null
        // e ritorna il valore precedente
        if (currentToken != null) {
            Token temp = currentToken;
            currentToken = null;
            return temp;
        }

        // nextChar contiene il prossimo carattere dell'input.
        char nextChar = peekChar();

        // Avanza nel buffer leggendo i carattere in skipChars
        // incrementando riga se leggi '\n'.
        // Se raggiungi la fine del file ritorna il Token EOF
        while (skipChars.contains(nextChar)) {
            if (nextChar == '\n') {
                riga++;
            }
            if (nextChar == EOF) {
                return new Token(TokenType.EOF, riga);
            }
            readChar();
            nextChar = peekChar();
        }


        // Se nextChar e' in numbers
        //                return scanNumber()
        // che legge sia un intero che un float e ritorna il Token INUM o FNUM
        // i caratteri che leggete devono essere accumulati in una stringa
        // che verra' assegnata al campo valore del Token
        if (numbers.contains(nextChar)) {
            try {
                return scanNumber();
            } catch (Exception e) {
                throw new ScannerException(e.getMessage());
            }
        }

        // Se nextChar e' in letters
        //                return scanId()
        // che legge tutte le lettere minuscole e ritorna un Token ID o
        // il Token associato Parola Chiave (per generare i Token per le
        // parole chiave usate l'HaskMap di corrispondenza
        if (letters.contains(nextChar)) {
            try {
                return scanId();
            } catch (Exception e) {
                throw new ScannerException(e.getMessage());
            }
        }

        // Se nextChar e' in operators
        // ritorna il Token associato con l'operatore o il delimitatore
        if (operatorsMap.containsKey(nextChar)) {
            readChar();
            return new Token(operatorsMap.get(nextChar), riga);
        }


        // Altrimenti il carattere NON E' UN CARATTERE LEGALE

        readChar();
        return null;

    }

    private char peekChar() {
        char c;
        try {
            c = (char) buffer.read();
            buffer.unread(c);
            return c;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private char readChar() {
        try {
            return ((char) this.buffer.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private Token scanId() {

        char nextChar = peekChar();
        StringBuilder id = new StringBuilder();

        //Accetta solo ID che iniziano con una lettera e poi possono avere
        //anche numeri
        while (letters.contains(nextChar) || numbers.contains(nextChar)) {
            id.append(nextChar);
            readChar();
            nextChar = peekChar();
        }

        if (keyWordsMap.containsKey(id.toString())) {
            if (id.toString().equals("int")) {
                return new Token(TokenType.TYINT, riga);
            } else if (id.toString().equals("float")) {
                return new Token(TokenType.TYFLOAT, riga);
            } else {
                return new Token(TokenType.PRINT, riga);
            }
        }

        return new Token(TokenType.ID, riga, id.toString());
    }

    private Token scanNumber() throws ScannerException {

        char nextChar = peekChar();
        TokenType type = TokenType.INT;
        StringBuilder number = new StringBuilder();

        while (numbers.contains(nextChar)) {
            number.append(nextChar);
            readChar();
            nextChar = peekChar();

        }

        if (nextChar == '.') {
            number.append('.');
            type = TokenType.FLOAT;
            readChar();
            nextChar = peekChar();

            if (!numbers.contains(nextChar)) {
                throw new ScannerException("Illegal character after float point");
            }
            //Legge al più 5 numeri dopo la virgola
            for (int i = 0; i <= 4; i++) {
                if (numbers.contains(nextChar)) {
                    number.append(nextChar);
                    readChar();
                    nextChar = peekChar();
                } else if (skipChars.contains(nextChar) || operatorsMap.containsKey(nextChar)) {
                    return new Token(type, riga, number.toString());
                } else {
                    throw new ScannerException("Illegal character after float point");
                }
            }
            if (numbers.contains(nextChar)) {
                throw new ScannerException("Excessive decimal numbers in float");
            }
        } else if (!skipChars.contains(nextChar) && !operatorsMap.containsKey(nextChar)) {
            throw new ScannerException("Illegal character after int number");
        }

        return new Token(type, riga, number.toString());

    }
}