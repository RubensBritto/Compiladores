package src.main;

import src.execptions.OniclaLexicalException;
import src.execptions.OniclaSintaticoException;
import src.sintatico.OniclaSintatico;

public class Main {
    public static void main(String[] args) {
        try {
            String test = "C:\\Users\\ramon\\Desktop\\Compiladores\\onicla\\src\\main\\fibonacci.txt";
            //OniclaLexico lexico = new OniclaLexico(test);
            OniclaSintatico sintatico = new OniclaSintatico(test);

            System.out.println("Success ");
        } catch (OniclaLexicalException ex) {
            System.out.println("Lexical Error " + ex.getMessage());
        } catch (OniclaSintaticoException ex){
            System.out.println("Syntax Error " +ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Generic Error");
        }
    }
}