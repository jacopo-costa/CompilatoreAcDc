package visitor;

import ast.*;
import symbolTable.Attributes;
import symbolTable.SymbolTable;

import java.util.Iterator;

public class TypeCheckingVisitor implements IVisitor {

    private final StringBuilder log = new StringBuilder();

    public String getLog() {
        return log.toString();
    }

    @Override
    public void visit(NodeProgram node) {
        boolean err = false;
        Iterator<NodeDecSt> it = node.iterator();

        while(it.hasNext()){
            NodeDecSt n = it.next();
            n.accept(this);
            if (n.getResType() == TypeDescriptor.ERROR) {
                err = true;
            }
        }

        if(err){
            node.setResType(TypeDescriptor.ERROR);
        } else {
            node.setResType(TypeDescriptor.VOID);
        }
    }

    @Override
    public void visit(NodePrint node) {
        NodeId id = node.getId();
        id.accept(this);
        if (id.getResType() == TypeDescriptor.ERROR) {
            node.setResType(TypeDescriptor.ERROR);
        } else {
            node.setResType(TypeDescriptor.VOID);
        }
    }

    @Override
    public void visit(NodeId node) {

        if (SymbolTable.lookup(node.getName()) == null) {
            node.setResType(TypeDescriptor.ERROR);
            log.append(node).append(" not present in Symbol Table\n");
        } else {
            Attributes attr = new Attributes(SymbolTable.lookup(node.getName()).getType());
            node.setResType(TypeDescriptor.valueOf(attr.getType().toString()));
            node.setDescription(attr);
        }
    }

    @Override
    public void visit(NodeDeref node) {
        NodeId id = node.getId();
        id.accept(this);
        if (id.getResType() == TypeDescriptor.ERROR) {
            node.setResType(TypeDescriptor.ERROR);
        } else {
            node.setResType(id.getResType());
        }
    }

    @Override
    public void visit(NodeDecl node) {
        if (!SymbolTable.enter(node.getId().getName(), new Attributes(node.getType()))) {
            node.setResType(TypeDescriptor.ERROR);
            log.append(node.getId()).append(" already present");
        } else {
            node.setResType(TypeDescriptor.valueOf(node.getType().toString()));
        }
    }

    @Override
    public void visit(NodeCost node) {
        node.setResType(TypeDescriptor.valueOf(node.getType().toString()));
    }

    @Override
    public void visit(NodeBinOp node) {

        node.getLeft().accept(this);
        node.getRight().accept(this);

        if (node.getLeft().getResType() == TypeDescriptor.ERROR || node.getRight().getResType() == TypeDescriptor.ERROR) {
            node.setResType(TypeDescriptor.ERROR);
            log.append("Illegal binary operation: ").append(node).append("\n");
        } else if (node.getRight().getResType() == node.getLeft().getResType()) {
            node.setResType(node.getLeft().getResType());
        } else if (compatible(node.getLeft().getResType(), node.getRight().getResType())) {
            NodeExpr conv = convert(node.getRight());
            node.setRight(conv);
            node.setResType(conv.getResType());
        }
    }

    @Override
    public void visit(NodeAssign node) {
        node.getId().accept(this);
        node.getExpr().accept(this);

        if (node.getExpr().getResType() == TypeDescriptor.ERROR || !compatible(node.getId().getResType(), node.getExpr().getResType())) {
            node.setResType(TypeDescriptor.ERROR);
            log.append("Illegal assignment: ").append(node).append("\n");
        } else if (node.getId().getResType() == TypeDescriptor.FLOAT && node.getExpr().getResType() == TypeDescriptor.INT) {
            NodeExpr conv = convert(node.getExpr());
            node.setExpr(conv);
        } else if (node.getId().getResType() == node.getExpr().getResType()) {
            node.setResType(node.getId().getResType());
        } else {
            node.setResType(TypeDescriptor.VOID);
        }
    }

    @Override
    public void visit(NodeConvert node) {
    }

    private boolean compatible(TypeDescriptor t1, TypeDescriptor t2) {

        if (t1 != TypeDescriptor.ERROR && t2 != TypeDescriptor.ERROR) {
            if (t1 == t2) {
                return true;
            }
        }

        return t1 == TypeDescriptor.FLOAT && t2 == TypeDescriptor.INT;
    }

    private NodeExpr convert(NodeExpr node) {
        if (node.getResType() == TypeDescriptor.FLOAT) {
            return node;
        }

        NodeConvert convert = new NodeConvert();
        convert.setResType(TypeDescriptor.FLOAT);
        convert.setExpr(node);
        return convert;
    }
}
