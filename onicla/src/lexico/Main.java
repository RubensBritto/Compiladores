package lexico;

public class Main {
    public static void main(String[] args) throws Exception {
        String path = "C:\\Users\\ramon\\Downloads\\Compiladores-main\\onicla\\src\\ola.txt";
        String path2 = "D:\\Rubens_HD\\Intellij\\onicla\\testes\\ola.txt";
        OniclaLexico lex = new OniclaLexico(path2);
        Token t = null;

        while ((t = lex.proxToken()).nome != TipoToken.FIM) {
            System.out.print(t);
        }
    }
}
