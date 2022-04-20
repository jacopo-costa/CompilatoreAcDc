package scanner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import token.Token;
import token.TokenType;

public class Scanner {
    final char EOF = (char) -1; // int 65535
    private int riga;
    private final PushbackReader buffer;

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

        skipChars = new ArrayList<>(
                Arrays.asList(' ', '\n', '\t', '\r', EOF));

        letters = new ArrayList<>(
                Arrays.asList('a', 'b', 'c', 'd', 'e', 'f',
                        'g', 'h', 'i', 'j', 'k', 'l', 'm',
                        'n', 'o', 'p', 'q', 'r', 's', 't',
                        'u', 'v', 'w', 'x', 'y', 'z'));

        numbers = new ArrayList<>(
                Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));

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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return currentToken;

    }

    public Token nextToken() throws IOException {

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
            return scanNumber();
        }

        // Se nextChar e' in letters
        //                return scanId()
        // che legge tutte le lettere minuscole e ritorna un Token ID o
        // il Token associato Parola Chiave (per generare i Token per le
        // parole chiave usate l'HaskMap di corrispondenza
        if (letters.contains(nextChar)) {
            return scanId();
        }

        // Se nextChar e' in operators
        // ritorna il Token associato con l'operatore o il delimitatore
        if (operatorsMap.containsKey(nextChar)) {
            readChar();
            return new Token(operatorsMap.get(nextChar), riga);
        }


        // Altrimenti il carattere NON E' UN CARATTERE LEGALE

        return null;

    }

    private char peekChar() throws IOException {
        char c = (char) buffer.read();
        buffer.unread(c);
        return c;
    }

    private char readChar() throws IOException {
        return ((char) this.buffer.read());
    }


    private Token scanId() throws IOException {

        char nextChar = peekChar();
        StringBuilder id = new StringBuilder();

        while (letters.contains(nextChar)) {
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

    private Token scanNumber() throws IOException {

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
            //Legge al più 5 numeri dopo la virgola
            for (int i = 0; i <= 4; i++) {
                if (numbers.contains(nextChar)) {
                    number.append(nextChar);
                    readChar();
                    nextChar = peekChar();
                } else {
                    return new Token(type, riga, number.toString());
                }
            }
        }

        return new Token(type, riga, number.toString());

    }
}