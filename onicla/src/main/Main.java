package src.main;

import src.execptions.OniclaLexicalException;
import src.lexico.OniclaLexico;
import src.lexico.Token;

public class Main {
    public static void main(String[] args) {
        OniclaLexico lexico = new OniclaLexico(args[0]);
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