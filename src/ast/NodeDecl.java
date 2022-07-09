package ast;

public class NodeDecl extends NodeDecSt {

    private NodeId id;
    private LangType type;

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
        return "ID = " + id +
                "\nType = " + type.toString();
    }
}
