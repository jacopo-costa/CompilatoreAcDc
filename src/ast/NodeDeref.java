package ast;

import visitor.IVisitor;

public class NodeDeref extends NodeExpr {
    private final NodeId id;

    public NodeDeref(NodeId id) {
        this.id = id;
    }

    public NodeId getId() {
        return id;
    }

    @Override
    public String toString() {
        return id.toString();
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
