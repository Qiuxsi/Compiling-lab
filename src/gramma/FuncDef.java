package gramma;

import lex.Lexer;
import java.io.FileWriter;
import java.io.IOException;

public class FuncDef {

    public Lexer lexer;
    public FuncDef(Lexer lexer) {
        this.lexer = lexer;
    }

    public void analysis(String outputFile) {
        try {
            FileWriter fw = new FileWriter(outputFile, true);

            new FuncType(lexer).analysis(outputFile);
            new Ident(lexer).analysis(outputFile);
            if (lexer.t.equals("(")) {
                fw.write("(");
                fw.flush();
                lexer.getsym();
                if (lexer.t.equals(")")) {
                    fw.write(")");
                    fw.flush();
                    lexer.getsym();
                    new Block(lexer).analysis(outputFile);
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
