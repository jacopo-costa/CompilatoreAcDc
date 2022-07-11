package parser;

import ast.*;
import exception.ParserException;
import exception.ScannerException;
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

    private NodeProgram parsePrg() throws ScannerException, ParserException {
        Token tk = scanner.peekToken();
        ArrayList<NodeDecSt> decSts = new ArrayList<>();

        switch (tk.getTipo()) {
            case TYFLOAT, TYINT, PRINT, ID, EOF -> {
                NodeProgram np = new NodeProgram(parseDSs(decSts));
                match(TokenType.EOF);
                return np;
            }
            default -> throw new ParserException("Unexpected token in parsePrg");
        }
    }

    private ArrayList<NodeDecSt> parseDSs(ArrayList<NodeDecSt> decSts) throws ParserException, ScannerException {
        Token tk = scanner.peekToken();

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
                return decSts;
            }
            default -> throw new ParserException("Unexpected value parseDSs: " + tk.getTipo());
        }
        return decSts;
    }

    private NodeStm parseStm() throws ScannerException, ParserException {
        Token tk = scanner.peekToken();

        switch (tk.getTipo()) {
            case PRINT -> {
                match(TokenType.PRINT);
                String id = match(TokenType.ID).getVal();
                match(TokenType.SEMI);
                return new NodePrint(new NodeId(id));
            }
            case ID -> {
                String id = match(TokenType.ID).getVal();
                match(TokenType.ASSIGN);
                NodeExpr exp = parseExp();
                match(TokenType.SEMI);
                return new NodeAssign(new NodeId(id), exp);
            }
            default -> throw new ParserException("Unexpected value parseStm: " + tk.getTipo());
        }
    }

    private NodeDecl parseDcl() throws ScannerException, ParserException {
        Token tk = scanner.peekToken();

        switch (tk.getTipo()) {
            case TYFLOAT -> {
                match(TokenType.TYFLOAT);
                String id = match(TokenType.ID).getVal();
                match(TokenType.SEMI);
                return new NodeDecl(new NodeId(id), LangType.FLOAT);
            }
            case TYINT -> {
                match(TokenType.TYINT);
                String id = match(TokenType.ID).getVal();
                match(TokenType.SEMI);
                return new NodeDecl(new NodeId(id), LangType.INT);
            }
            default -> throw new ParserException("Unexpected value parseDcl: " + tk.getTipo());
        }
    }

    private NodeExpr parseExp() throws ParserException, ScannerException {
        Token tk = scanner.peekToken();

        switch (tk.getTipo()) {
            case INT, FLOAT, ID -> {
                NodeExpr ter = parseTr();
                return parseExpP(ter);
            }
            default -> throw new ParserException("Unexpected value parseExp: " + tk.getTipo());
        }
    }

    private NodeExpr parseExpP(NodeExpr left) throws ScannerException, ParserException {
        Token tk = scanner.peekToken();

        switch (tk.getTipo()) {
            case PLUS:
                match(TokenType.PLUS);
                NodeExpr trP = parseTr();
                return parseExpP(new NodeBinOp(LangOper.PLUS, left, trP));
            case MINUS:
                match(TokenType.MINUS);
                NodeExpr trM = parseTr();
                return parseExpP(new NodeBinOp(LangOper.MINUS, left, trM));
            case SEMI:
                return left;
            default:
                throw new ParserException("Unexpected value parseExpP: " + tk.getTipo());
        }
    }

    private NodeExpr parseTr() throws ParserException, ScannerException {
        Token tk = scanner.peekToken();

        switch (tk.getTipo()) {
            case INT, FLOAT, ID -> {
                NodeExpr cost = parseVal();
                return parseTrP(cost);
            }
            default -> throw new ParserException("Unexpected value parseTr: " + tk.getTipo());
        }
    }

    private NodeExpr parseTrP(NodeExpr left) throws ScannerException, ParserException {
        Token tk = scanner.peekToken();

        switch (tk.getTipo()) {
            case TIMES:
                match(TokenType.TIMES);
                NodeExpr terT = parseVal();
                return parseTrP(new NodeBinOp(LangOper.TIMES, left, terT));
            case DIV:
                match(TokenType.DIV);
                NodeExpr terD = parseVal();
                return parseTrP(new NodeBinOp(LangOper.DIV, left, terD));
            case PLUS:
                match(TokenType.PLUS);
                NodeExpr terP = parseVal();
                return parseTrP(new NodeBinOp(LangOper.PLUS, left, terP));
            case MINUS:
                match(TokenType.MINUS);
                NodeExpr terM = parseVal();
                return parseTrP(new NodeBinOp(LangOper.MINUS, left, terM));
            case SEMI:
                return left;
            default:
                throw new ParserException("Unexpected value parseTrP: " + tk.getTipo());
        }
    }

    private NodeExpr parseVal() throws ScannerException, ParserException {
        Token tk = scanner.peekToken();

        switch (tk.getTipo()) {
            case INT:
                match(TokenType.INT);
                return new NodeCost(tk.getVal(), LangType.INT);
            case FLOAT:
                match(TokenType.FLOAT);
                return new NodeCost(tk.getVal(), LangType.FLOAT);
            case ID:
                return new NodeDeref(new NodeId(match(TokenType.ID).getVal()));
            default:
                throw new ParserException("Unexpected value parseVal: " + tk.getTipo());
        }
    }

    private Token match(TokenType type) throws ScannerException, ParserException {
        Token tk = scanner.peekToken();

        if (type.equals(tk.getTipo())) {
            return scanner.nextToken();
        } else throw new ParserException("Expected: " + type + " Got: " + tk.getTipo() + " At: " + tk.getRiga());
        // aspettato "type" token invece di tk alla riga tk.getRiga()
    }
}
