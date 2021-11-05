package gramma;

import lex.Lexer;

import java.io.FileWriter;
import java.io.IOException;

public class UnaryOp {
    public Lexer lexer;
    public UnaryOp(Lexer lexer) {
        this.lexer = lexer;
    }

    public void analysis(String outputFile) {
        try {
            FileWriter fw = new FileWriter(outputFile, true);
            if (lexer.t.equals("+") || lexer.t.equals("-")) {
                lexer.getsym();
            } else {
                System.exit(1);
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
}
