package gramma;

import lex.Lexer;

import java.io.FileWriter;
import java.io.IOException;

public class Block {
    public Lexer lexer;
    public Block(Lexer lexer) {
        this.lexer = lexer;
    }

    public void analysis(String outputFile) {
        try {
            FileWriter fw = new FileWriter(outputFile, true);

            if (lexer.t.equals("{")) {
                fw.write("{\n");
                fw.flush();
                lexer.getsym();
                new Stmt(lexer).analysis(outputFile);
                if (lexer.t.equals("}")) {
                    fw.write("}");
                    fw.flush();
                    // lexer.getsym(); 这句加上就错了，返回值会为1
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
