package ast;

import java.util.ArrayList;
import java.util.Iterator;

public class NodeProgram extends NodeAST implements Iterator<NodeDecSt> {

    private int i = 0;

    private ArrayList<NodeDecSt> decSts;

    public NodeProgram(ArrayList<NodeDecSt> decSts) {
        this.decSts = decSts;
    }

    @Override
    public boolean hasNext() {
        if(decSts.get(i) == null){
            return false;
        }
        return true;
    }

    @Override
    public NodeDecSt next() {
        NodeDecSt ret = decSts.get(i);
        i++;
        return ret;
    }

    @Override
    public String toString() {
        return "Prg: " + decSts.toString();
    }

}
