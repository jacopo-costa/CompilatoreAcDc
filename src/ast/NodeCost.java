package ast;

import visitor.IVisitor;

public class NodeCost extends NodeExpr {

    private final String value;
    private final LangType type;

    public NodeCost(String value, LangType type) {
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public LangType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "<" + value + " Type : " + type.toString() + ">";
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
