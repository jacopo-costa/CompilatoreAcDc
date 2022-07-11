package symbolTable;

import ast.LangType;

public class Attributes {

    private LangType type;

    private char registro;

    private boolean initialized = false;

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public char getRegistro() {
        return registro;
    }

    public void setRegistro(char registro) {
        this.registro = registro;
    }

    public Attributes(LangType type) {
        this.type = type;
    }

    public LangType getType() {
        return type;
    }

    public void setType(LangType t) {
        type = t;
    }


}
