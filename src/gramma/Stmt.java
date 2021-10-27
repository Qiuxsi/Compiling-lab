package gramma;

import lex.Lexer;

import java.io.FileWriter;
import java.io.IOException;

public class Stmt {
    public Lexer lexer;
    public Stmt(Lexer lexer) {
        this.lexer = lexer;
    }

    public void analysis(String outputFile) {
        try {
            FileWriter fw = new FileWriter(outputFile, true);

            if (lexer.t.equals("return")) {
                //System.out.print("    ret i32 ");
                fw.write("    ret i32 ");
                fw.flush();
                lexer.getsym();
                new Number(lexer).analysis(outputFile);
                if (lexer.t.equals(";")) {
                    lexer.getsym();
                } else {
                    System.exit(1);
                }
            } else {
                System.exit(1);
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
}
