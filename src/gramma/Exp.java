package gramma;

import lex.Lexer;

public class Exp {
    public Lexer lexer;
    public Exp(Lexer lexer) {
        this.lexer = lexer;
    }

    public int analysis(String outputFile) {
        return new AddExp(lexer).analysis(outputFile);
    }
}
