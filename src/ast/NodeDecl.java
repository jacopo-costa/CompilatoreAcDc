package ast;

import visitor.IVisitor;

public class NodeDecl extends NodeDecSt {

    private final NodeId id;
    private final LangType type;

    public NodeDecl(NodeId id, LangType type) {
        this.id = id;
        this.type = type;
    }

    public NodeId getId() {
        return id;
    }

    public LangType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Decl: <" + id.toString() + ", " + type.toString() + ">";
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
