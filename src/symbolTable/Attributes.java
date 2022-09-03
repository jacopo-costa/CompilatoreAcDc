package symbolTable;

import ast.LangType;

public class Attributes {

    private LangType type;

    private char register;

    private boolean initialized = false;

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public char getRegister() {
        return register;
    }

    public void setRegister(char register) {
        this.register = register;
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
