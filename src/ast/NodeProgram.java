package ast;

import visitor.IVisitor;

import java.util.ArrayList;
import java.util.Iterator;

public class NodeProgram extends NodeAST implements Iterable<NodeDecSt> {

    private final ArrayList<NodeDecSt> decSts;

    public NodeProgram(ArrayList<NodeDecSt> decSts) {
        this.decSts = decSts;
    }

    @Override
    public String toString() {
        return "Prg: " + decSts.toString();
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public Iterator<NodeDecSt> iterator() {
        return decSts.iterator();
    }
}
