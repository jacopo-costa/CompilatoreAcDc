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

        switch (tk.getTipo()) {
            case TYFLOAT, TYINT, PRINT, ID, EOF -> {
                NodeProgram np = new NodeProgram(parseDSs(decSts));
                match(TokenType.EOF);
                return np;
            }
            default -> throw new SyntaxException("Unexpected token in parsePrg");
        }
    }

    private ArrayList<NodeDecSt> parseDSs(ArrayList<NodeDecSt> decSts) throws Exception {
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
            default -> throw new SyntaxException("Unexpected value parseDSs: " + tk.getTipo());
        }
        return decSts;
    }

    private NodeStm parseStm() throws Exception {
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
            default -> throw new SyntaxException("Unexpected value parseStm: " + tk.getTipo());
        }
    }

    private NodeDecl parseDcl() throws Exception {
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
            default -> throw new SyntaxException("Unexpected value parseDcl: " + tk.getTipo());
        }
    }

    private NodeExpr parseExp() throws Exception {
        Token tk = scanner.peekToken();

        switch (tk.getTipo()) {
            case INT, FLOAT, ID -> {
                NodeExpr ter = parseTr();
                return parseExpP(ter);
            }
            default -> throw new SyntaxException("Unexpected value parseExp: " + tk.getTipo());
        }
    }

    private NodeExpr parseExpP(NodeExpr left) throws Exception {
        Token tk = scanner.peekToken();

        switch (tk.getTipo()) {
            case PLUS:
                match(TokenType.PLUS);
                NodeExpr trP = parseTr();
                NodeExpr expP = parseExpP(trP);
                return new NodeBinOp(LangOper.PLUS, left, expP);
            case MINUS:
                match(TokenType.MINUS);
                NodeExpr trM = parseTr();
                NodeExpr expM = parseExpP(trM);
                return new NodeBinOp(LangOper.MINUS, left, expM);
            case SEMI:
                return left;
            default:
                throw new SyntaxException("Unexpected value parseExpP: " + tk.getTipo());
        }
    }

    private NodeExpr parseTr() throws Exception {
        Token tk = scanner.peekToken();

        switch (tk.getTipo()) {
            case INT, FLOAT, ID -> {
                NodeExpr cost = parseVal();
                return parseTrP(cost);
            }
            default -> throw new SyntaxException("Unexpected value parseTr: " + tk.getTipo());
        }
    }

    private NodeExpr parseTrP(NodeExpr left) throws Exception {
        Token tk = scanner.peekToken();

        switch (tk.getTipo()) {
            case TIMES:
                match(TokenType.TIMES);
                NodeExpr terT = parseVal();
                NodeExpr trpT = parseTrP(terT);
                return new NodeBinOp(LangOper.TIMES, left, trpT);
            case DIV:
                match(TokenType.DIV);
                NodeExpr terD = parseVal();
                NodeExpr trpD = parseTrP(terD);
                return new NodeBinOp(LangOper.DIV, left, trpD);
            case PLUS:
                match(TokenType.PLUS);
                NodeExpr terP = parseVal();
                NodeExpr trpP = parseTrP(terP);
                return new NodeBinOp(LangOper.PLUS, left, trpP);
            case MINUS:
                match(TokenType.MINUS);
                NodeExpr terM = parseVal();
                NodeExpr trpM = parseTrP(terM);
                return new NodeBinOp(LangOper.MINUS, left, trpM);
            case SEMI:
                return left;
            default:
                throw new SyntaxException("Unexpected value parseTrP: " + tk.getTipo());
        }
    }

    private NodeExpr parseVal() throws Exception {
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
                throw new SyntaxException("Unexpected value parseVal: " + tk.getTipo() + " " + tk.getVal());
        }
    }

    private Token match(TokenType type) throws Exception {
        Token tk = scanner.peekToken();

        if (type.equals(tk.getTipo())) {
            return scanner.nextToken();
        } else throw new SyntaxException("Expected: " + type + " Got: " + tk.getTipo() + " At: " + tk.getRiga());
        // aspettato "type" token invece di tk alla riga tk.getRiga()
    }
}
