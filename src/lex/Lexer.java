package lex;
import java.io.*;

public class Lexer {

    public char[] buffer = new char[1024];  // 存文件字符的数组
    public int k;
    public String t;

    public Lexer(String fileName) {
        try {
            k = -1;
            FileReader fr = new FileReader(fileName);
            fr.read(buffer);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    public boolean isNondigit(char c) {
        return (c <= 90 && c >= 65) || (c <= 122 && c >= 97) || c == '_';
    }
    public boolean isDigit(char c) {
        return c <= '9' && c >= '0';
    }
    public boolean isDelimiter(char c) {
        return c == '(' || c == ')' || c == '{' || c == '}' || c == ';';
    }

    public void getsym() {
        char c;
        StringBuffer token = new StringBuffer();

        k++;

        while (buffer[k] == ' ' || buffer[k] == '\t' || buffer[k] == '\n' || buffer[k] == '\r' || buffer[k] == '/') {
            if (buffer[k] == '/') {
                if ((++k) == buffer.length)
                    System.exit(1);
                if (buffer[k] == '/') {
                    while (buffer[k] != '\n' && buffer[k] != '\r')
                        if ((++k) == buffer.length)
                            return;
                } else if (buffer[k] == '*') {
                    while (true) {
                        if ((++k) == buffer.length)
                            System.exit(1);
                        if (buffer[k] == '*') {
                            if (buffer[++k] == '/') {
                                k++;
                                break;
                            }
                            else
                                k--;
                        }
                    }
                } else {
                    System.exit(1);
                }
            } else {
                if ((++k) == buffer.length)
                    return;
            }
        }

        if (isNondigit(buffer[k])) {
            while (isNondigit(buffer[k]) || isDigit(buffer[k])) {
                token.append(buffer[k]);
                if ((++k) == buffer.length)
                    break;
            }
            k--;
        } else if(isDigit(buffer[k])) {
            while(isDigit(buffer[k])) {
                token.append(buffer[k]);
                if ((++k) == buffer.length)
                    break;
            }
            k--;
        } else if (isDelimiter(buffer[k])) {
            token.append(buffer[k]);
        } else {
            System.exit(1);
        }

        t = token.toString();
    }
}
