package gramma;

import lex.Lexer;

public class AddExp {
    public Lexer lexer;
    public AddExp(Lexer lexer) {
        this.lexer = lexer;
    }

    public int analysis(String outputFile) {
        return new MulExp(lexer).analysis(outputFile);
    }
}
