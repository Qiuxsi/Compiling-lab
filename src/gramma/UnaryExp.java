package gramma;

import lex.Lexer;

import java.io.FileWriter;
import java.io.IOException;

public class UnaryExp {
    public Lexer lexer;
    public UnaryExp(Lexer lexer) {
        this.lexer = lexer;
    }

    public int analysis(String outputFile) {
        try {
            FileWriter fw = new FileWriter(outputFile, true);
            if (lexer.t.equals("-")) {
                new UnaryOp(lexer).analysis(outputFile);
                int flag = new UnaryExp(lexer).analysis(outputFile);
                if (flag != -1) {
                    fw.write("    %" + Stmt.index + " = sub i32 0, " + flag + "\n");
                    fw.flush();
                } else {
                    Stmt.index++;
                    fw.write("    %" + Stmt.index + " = sub i32 0, %" + (Stmt.index - 1) + "\n");
                    fw.flush();
                }
            } else if (lexer.t.equals("+")) {
                new UnaryOp(lexer).analysis(outputFile);
                int flag = new UnaryExp(lexer).analysis(outputFile);
                if (flag != -1) {
                    fw.write("    %" + Stmt.index + " = add i32 0, " + flag + "\n");
                    fw.flush();
                } else {
                    Stmt.index++;
                    fw.write("    %" + Stmt.index + " = add i32 0, %" + (Stmt.index - 1) + "\n");
                    fw.flush();
                }
            } else {
                return new PrimaryExp(lexer).analysis(outputFile);
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        return -1;
    }
}

