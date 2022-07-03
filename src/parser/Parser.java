package parser;

import ast.*;
import exception.SyntaxException;
import scanner.Scanner;
import token.Token;
import token.TokenType;

import java.util.ArrayList;

public class Parser {

    private final Scanner scanner;

    public Parser(Scanner scanner) {
        this.scanner = scanner;
    }

    public NodeProgram parse() throws Exception {

        return parsePrg();

    }

    private NodeProgram parsePrg() throws Exception {
        Token tk = scanner.peekToken();
        ArrayList<NodeDecSt> decSts = new ArrayList<>();
        System.out.println("ParsePrg " + tk.toString());

        switch (tk.getTipo()) {
            case TYFLOAT, TYINT, PRINT, ID, EOF -> {
                NodeProgram np = new NodeProgram(parseDSs(decSts));
                match(TokenType.EOF);
                return np;
            }
            default -> throw new SyntaxException("Token inaspettato parsePrg");
        }
    }

    private ArrayList<NodeDecSt> parseDSs(ArrayList<NodeDecSt> decSts) throws Exception {
        Token tk = scanner.peekToken();
        System.out.println("ParseDSs " + tk.toString());

        switch (tk.getTipo()) {
            case TYINT, TYFLOAT -> {
                decSts.add(parseDcl());
                parseDSs(decSts);
            }
            case ID, PRINT -> {
                decSts.add(parseStm());
                parseDSs(decSts);
            }
            case EOF -> {
                match(TokenType.EOF);
                return decSts;
            }
            default -> throw new SyntaxException("Unexpected value parseDSs: " + tk.getTipo());
        }
        return decSts;
    }

    private NodeStm parseStm() throws Exception {
        Token tk = scanner.peekToken();
        NodeStm stm = null;

        System.out.println("ParseStm " + tk.toString());

        switch (tk.getTipo()) {
            case PRINT -> {
                match(TokenType.PRINT);
                String id = match(TokenType.ID).getVal();
                match(TokenType.SEMI);
                return new NodePrint(new NodeId(id));
            }
            case ID -> {
                match(TokenType.ID);
                match(TokenType.ASSIGN);
                parseExp();
                match(TokenType.SEMI);
                return stm;
            }
            default -> throw new SyntaxException("Unexpected value parseStm: " + tk.getTipo());
        }
    }

    private NodeDecl parseDcl() throws Exception {
        Token tk = scanner.peekToken();
        System.out.println("ParseDcl " + tk.toString());

        switch (tk.getTipo()) {
            case TYFLOAT -> {
                match(TokenType.TYFLOAT);
                NodeDecl nf = new NodeDecl(new NodeId(scanner.peekToken().getVal()), LangType.FLOAT);
                match(TokenType.ID);
                match(TokenType.SEMI);
                return nf;
            }
            case TYINT -> {
                match(TokenType.TYINT);
                NodeDecl ni = new NodeDecl(new NodeId(scanner.peekToken().getVal()), LangType.INT);
                match(TokenType.ID);
                match(TokenType.SEMI);
                return ni;
            }
            default -> throw new SyntaxException("Unexpected value parseDcl: " + tk.getTipo());
        }
    }

    private NodeExpr parseExp() throws Exception {
        Token tk = scanner.peekToken();
        System.out.println("ParseExp " + tk.toString());

        switch (tk.getTipo()) {
            case INT, FLOAT, ID -> {
                parseTr();
                parseExpP();
            }
            default -> throw new SyntaxException("Unexpected value parseExp: " + tk.getTipo());
        }
        return null;
    }

    private NodeExpr parseExpP() throws Exception {
        Token tk = scanner.peekToken();
        System.out.println("ParseExpP " + tk.toString());

        switch (tk.getTipo()) {
            case PLUS:
                match(TokenType.PLUS);
                parseTr();
                parseExpP();
                break;
            case MINUS:
                match(TokenType.MINUS);
                parseTr();
                parseExpP();
                break;
            case SEMI:
                break;
            default:
                throw new SyntaxException("Unexpected value parseExpP: " + tk.getTipo());
        }
        return null;
    }

    private NodeExpr parseTr() throws Exception {
        Token tk = scanner.peekToken();
        System.out.println("ParseTr " + tk.toString());

        switch (tk.getTipo()) {
            case INT, FLOAT, ID -> {
                parseVal();
                parseTrP();
            }
            default -> throw new SyntaxException("Unexpected value parseTr: " + tk.getTipo());
        }
        return null;
    }

    private NodeExpr parseTrP() throws Exception {
        Token tk = scanner.peekToken();
        System.out.println("ParseTrP " + tk.toString());

        switch (tk.getTipo()) {
            case TIMES:
                match(TokenType.TIMES);
                parseVal();
                parseTrP();
                break;
            case DIV:
                match(TokenType.DIV);
                parseVal();
                parseTrP();
                break;
            case PLUS:
            case MINUS:
            case SEMI:
                break;
            default:
                throw new SyntaxException("Unexpected value parseTrP: " + tk.getTipo());
        }
        return null;
    }

    private NodeExpr parseVal() throws Exception {
        Token tk = scanner.peekToken();
        System.out.println("ParseVal " + tk.toString());

        switch (tk.getTipo()) {
            case INT -> match(TokenType.INT);
            case FLOAT -> match(TokenType.FLOAT);
            case ID -> match(TokenType.ID);
            default -> throw new SyntaxException("Unexpected value parseVal: " + tk.getTipo());
        }
        return null;
    }

    private Token match(TokenType type) throws Exception {
        Token tk = scanner.peekToken();

        if (type.equals(tk.getTipo())) {
            return scanner.nextToken();
        } else throw new Exception("Expected: " + type + " Got: " + tk.getTipo() + " At: " + tk.getRiga());
        // aspettato "type" token invece di tk alla riga tk.getRiga()
    }
}
