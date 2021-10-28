package lex;
import java.io.*;

public class Lexer {

    public char[] reader_buffer = new char[1024];  // 读取文件字符的数组
    public char[] buffer;
    public int k;
    public String t;

    public Lexer(String fileName) {
        try {
            k = -1;
            FileReader fr = new FileReader(fileName);
            int len = fr.read(reader_buffer);
            buffer = new char[len]; // 真的可以这么写吗？
            System.arraycopy(reader_buffer, 0, buffer, 0, len);
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
        StringBuilder token = new StringBuilder();

        if ((++k) == buffer.length) // 已读取到文件末尾
            return;

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
