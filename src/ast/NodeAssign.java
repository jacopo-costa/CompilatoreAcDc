package ast;

import visitor.IVisitor;

public class NodeAssign extends NodeStm {

    private final NodeId id;

    private NodeExpr expr;

    public NodeAssign(NodeId id, NodeExpr expr) {
        this.id = id;
        this.expr = expr;
    }

    public NodeId getId() {
        return id;
    }

    public NodeExpr getExpr() {
        return expr;
    }

    public void setExpr(NodeExpr expr) {
        this.expr = expr;
    }

    @Override
    public String toString() {
        return "Assign: <" + id.toString() + ", " + expr + ">";
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
