package lexico;

public class OniclaLexema {
    public static void main(String[] args) {
        String path = "D:\\Rubens_HD\\Intellij\\onicla\\src\\lexico\\ola.txt";
        OniclaLexico lex = new OniclaLexico(path);
        Token t = null;

        while((t = lex.proxToken()) != null){
            System.out.println(t);
        }
    }
}
