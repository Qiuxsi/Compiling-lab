package gramma;

import lex.Lexer;

import java.io.FileWriter;
import java.io.IOException;

public class PrimaryExp {
    public Lexer lexer;
    public PrimaryExp(Lexer lexer) {
        this.lexer = lexer;
    }

    public int analysis(String outputFile) {
        try {
            FileWriter fw = new FileWriter(outputFile, true);
            if (lexer.t.equals("(")) {
                lexer.getsym();
                new Exp(lexer).analysis(outputFile);
                if (lexer.t.equals(")")) {
                    lexer.getsym();
                    return -1;
                } else {
                    System.exit(1);
                }
            } else {
                return new Number(lexer).analysis(outputFile);
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        return -1;
    }
}
