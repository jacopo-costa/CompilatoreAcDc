package ast;

import visitor.IVisitor;

public class NodeConvert extends NodeExpr {

    private NodeExpr expr;

    public NodeExpr getExpr() {
        return expr;
    }

    public void setExpr(NodeExpr expr) {
        this.expr = expr;
    }

    @Override
    public String toString() {
        return "expr=" + expr;
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
