package visitor;

import ast.*;
import symbolTable.Attributes;
import symbolTable.SymbolTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CodeGeneratorVisitor implements IVisitor{

    private static final StringBuilder log = new StringBuilder();

    private final StringBuilder code = new StringBuilder();

    private static int iterator;
    private static final List<Character> register = new ArrayList<>(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'));

    private static char newRegister() {
        char reg = register.get(iterator);
        iterator++;
        if(iterator > 26){
            log.append("Iterator out of bounds");
        }
        return reg;
    }

    public StringBuilder getLog() {
        return log;
    }

    public StringBuilder getCode() {
        return code;
    }

    @Override
    public void visit(NodeProgram node) {
        for(NodeDecSt n : node){
            n.accept(this);
        }
    }

    @Override
    public void visit(NodePrint node) {

        Attributes attr = SymbolTable.lookup(node.getId().getName());
        if(attr == null){
            log.append("Cannot print ").append(node.getId().getName()).append(", is not initialized\n");
        } else if(!attr.isInitialized()){
            log.append("Cannot print ").append(node.getId().getName()).append(", is not initialized\n");
        } else {
            code.append("l")
                    .append(SymbolTable.lookup(node.getId().getName()).getRegistro())
                    .append(" p P ");
        }
    }

    @Override
    public void visit(NodeId node) {
    }

    @Override
    public void visit(NodeDeref node) {

        Attributes attr = SymbolTable.lookup(node.getId().getName());
        if(!attr.isInitialized()){
            log.append("Cannot load ").append(node.getId().getName()).append(", is not initialized\n");
        } else {
            code.append("l").append(SymbolTable.lookup(node.getId().getName()).getRegistro()).append(" ");
        }
    }

    @Override
    public void visit(NodeDecl node) {
        Attributes attr = SymbolTable.lookup(node.getId().getName());
        attr.setRegistro(newRegister());
        node.getId().setDescription(attr);
    }

    @Override
    public void visit(NodeCost node) {

        code.append(node.getValue()).append(" ");

    }

    @Override
    public void visit(NodeBinOp node) {

        node.getLeft().accept(this);
        node.getRight().accept(this);

        switch (node.getOp()){
            case DIV -> code.append("/ ");
            case PLUS -> code.append("+ ");
            case MINUS -> code.append("- ");
            case TIMES -> code.append("* ");
        }

    }

    @Override
    public void visit(NodeAssign node) {

        node.getId().accept(this);
        node.getExpr().accept(this);

        Attributes attr = SymbolTable.lookup(node.getId().getName());
        attr.setInitialized(true);
        node.getId().setDescription(attr);

        code.append("s").append(SymbolTable.lookup(node.getId().getName()).getRegistro()).append(" ");
        code.append("0 k ");
    }

    @Override
    public void visit(NodeConvert node) {

        node.getExpr().accept(this);
        code.append("5 k ");

    }
}
