package ast;

import symbolTable.Attributes;
import visitor.IVisitor;

public class NodeId extends NodeAST {

    private final String name;
    private Attributes description;

    public NodeId(String name) {
        this.name = name;
    }

    public Attributes getDescription() {
        return description;
    }

    public void setDescription(Attributes description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ID: " + name;
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
