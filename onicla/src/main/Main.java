package main;

import exceptions.OniclaLexicalException;
import lexico.OniclaLexico;
import lexico.Token;

public class Main {
    public static void main(String[] args) {
        String path = "D:\\Rubens_HD\\Intellij\\onicla_v2\\src\\main\\ola3.txt";
        String path2 = "C:\\Users\\ramon\\Downloads\\Compiladores-main\\onicla\\src\\ola.txt";
        OniclaLexico lexico = new OniclaLexico(path);
        Token token = null;
        try {
            do {
                token = lexico.nextToken();
                if (token != null) {
                    System.out.println(token);
                }
            } while (token.lexema != "EOF");
        } catch (OniclaLexicalException ex) {
            System.out.println("Lexico erro " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Erro generico");
        }
    }
}
