package gramma;

import lex.Lexer;

import java.io.FileWriter;
import java.io.IOException;

public class FuncType {
    public Lexer lexer;
    public FuncType(Lexer lexer) {
        this.lexer = lexer;
    }

    public void analysis(String outputFile) {
        try {
            FileWriter fw = new FileWriter(outputFile, true);

            if (lexer.t.equals("int")) {
                fw.write("define dso_local i32 ");
                fw.flush();
                lexer.getsym();
            } else {
                System.exit(1);
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
}
