package gramma;

import lex.Lexer;

public class MulExp {
    public Lexer lexer;
    public MulExp(Lexer lexer) {
        this.lexer = lexer;
    }

    public int analysis(String outputFile) {
        return new UnaryExp(lexer).analysis(outputFile);
    }
}
