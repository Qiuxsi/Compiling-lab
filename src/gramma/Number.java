package gramma;

import lex.Lexer;

import java.io.FileWriter;
import java.io.IOException;

public class Number {
    public Lexer lexer;
    public Number(Lexer lexer) {
        this.lexer = lexer;
    }

    public void analysis(String outputFile) {
        try {
            FileWriter fw = new FileWriter(outputFile, true);

            if (lexer.t.equals("0")) {  // 十六进制或八进制的'0'
                lexer.getsym();
                char[] str = lexer.t.toCharArray();
                int n = 0, len = str.length;
                if (str[0] == 'x' || str[0] == 'X') {
                    if (len == 1) {
                        System.exit(1);
                    } else {
                        for (int i = 1; i < len; i++) {
                            if (str[i] >= '0' && str[i] <= '9') {
                                n += (str[i] - '0') * Math.pow(16, (len - 1 - i));
                            } else if (str[i] >= 'a' && str[i] <= 'f') {
                                n += (str[i] - 'a' + 10) * Math.pow(16, (len - 1 - i));
                            } else if (str[i] >= 'A' && str[i] <= 'F') {
                                n += (str[i] - 'A' + 10) * Math.pow(16, (len - 1 - i));
                            } else {
                                System.exit(1);
                            }
                        }
                        fw.write(n + "\n");
                        fw.flush();
                        lexer.getsym();
                    }
                } else if(lexer.t.equals(";")) {    // 八进制中'0'的特殊情况
                    fw.write("0\n");
                    fw.flush();
                    lexer.getsym();
                }
            } else if (lexer.t.toCharArray()[0] == '0') {   // 八进制
                char[] str = lexer.t.toCharArray();
                int n = 0, len = str.length;
                for (int i = 1; i < len; i++) {
                    if (str[i] >= '0' && str[i] <= '7') {
                        n += (str[i] - '0') * Math.pow(8, (len - 1 - i));
                    } else {
                        System.exit(1);
                    }
                }
                fw.write(n + "\n");
                fw.flush();
                lexer.getsym();
            } else if(lexer.t.toCharArray()[0] >= '1' && lexer.t.toCharArray()[0] <= '9') {
                fw.write(lexer.t + "\n");
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
