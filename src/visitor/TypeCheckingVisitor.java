package visitor;

import ast.*;
import exception.VisitorException;
import symbolTable.Attributes;
import symbolTable.SymbolTable;

public class TypeCheckingVisitor implements IVisitor {

    private StringBuilder log = new StringBuilder();

    public String getLog() {
        return log.toString();
    }

    @Override
    public void visit(NodeProgram node) {
        SymbolTable.init();
        for(NodeDecSt n : node){
            n.accept(this);
        }
//        if(!log.isEmpty()){
//            try {
//                throw new VisitorException(log.toString());
//            } catch (VisitorException e) {
//                throw new RuntimeException(e);
//            }
//        }
        System.out.println(SymbolTable.toStr());
        System.out.println(log.toString());

    }

    @Override
    public void visit(NodePrint node) {
        NodeId id = node.getId();
        String name = id.getName();
        id.accept(this);
        if (SymbolTable.lookup(name) == null) {
            node.setResType(TypeDescriptor.ERROR);
            }
        else {
            node.setResType(TypeDescriptor.VOID);
        }

    }

    @Override
    public void visit(NodeId node) {

        node.setDescription(SymbolTable.lookup(node.getName()));

        if(node.getDescription() == null){
            log.append(node.toString() + " not present in Symbol Table");
        }

    }

    @Override
    public void visit(NodeDeref node) {

    }

    @Override
    public void visit(NodeDecl node) {
        if(!SymbolTable.enter(node.getId().getName(), new Attributes(node.getType()))){
            log.append(node.getId() + " already present");
        }
    }

    @Override
    public void visit(NodeCost node) {

    }

    @Override
    public void visit(NodeBinOp node) {

        node.getLeft().accept(this);
        node.getRight().accept(this);


    }

    @Override
    public void visit(NodeAssign node) {


    }

    @Override
    public void visit(NodeConvert node) {

    }
}
