package gramma;

import lex.Lexer;

public class CompUnit {

    public Lexer lexer;
    public CompUnit(Lexer lexer) {
        this.lexer = lexer;
    }

    public void analysis(String outputFile) {
        new FuncDef(lexer).analysis(outputFile);
    }

}
