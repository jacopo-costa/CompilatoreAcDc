package test;

import ast.NodeProgram;
import org.junit.jupiter.api.Test;
import parser.Parser;
import scanner.Scanner;
import symbolTable.SymbolTable;
import visitor.CodeGeneratorVisitor;
import visitor.TypeCheckingVisitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCodeGenerator {

    @Test
    void testGenerator() throws Exception {

        NodeProgram np = new Parser(new Scanner("src/test/data/testTypeChecking/testGenerator.txt")).parse();
        SymbolTable.init();
        np.accept(new TypeCheckingVisitor());
        CodeGeneratorVisitor cgv = new CodeGeneratorVisitor();
        np.accept(cgv);

        assertEquals("1.0 6 5 k / sb 0 k lb p P 1 6 / sa 0 k la p P ", cgv.getCode().toString());
        assertEquals("", cgv.getLog().toString());

    }

}
