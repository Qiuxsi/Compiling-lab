package gramma;

import lex.Lexer;

import java.io.FileWriter;
import java.io.IOException;

public class Stmt {
    public Lexer lexer;
    static public int index = 1;
    public Stmt(Lexer lexer) {
        this.lexer = lexer;
    }

    public void analysis(String outputFile) {
        try {
            FileWriter fw = new FileWriter(outputFile, true);

            if (lexer.t.equals("return")) {
                lexer.getsym();
                int n = new Exp(lexer).analysis(outputFile);
                if (n == -1) {
                    fw.write("    ret i32 %" + Stmt.index + "\n");
                } else {
                    fw.write("    ret i32 " + n + "\n");
                }
                fw.flush();
                if (lexer.t.equals(";")) {
                    //fw.write("\n");
                    //fw.flush();
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
