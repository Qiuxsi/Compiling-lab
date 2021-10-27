import lex.Lexer;
import gramma.*;

public class Test {
    public static void main(String[] args) {
        Lexer lexer = new Lexer(args[0]);
        lexer.getsym();
        new CompUnit(lexer).analysis(args[1]);
    }
}
