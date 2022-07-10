package symbolTable;

import ast.LangType;

public class Attributes {
	
	private LangType type;
	
	public Attributes(LangType type) {
		this.type = type;
	}

	public LangType getType() {
		return type;
	}
	
	public void setType(LangType t) {
		type=t;
	}


}
