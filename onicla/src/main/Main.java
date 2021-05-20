package src.main;

import src.execptions.OniclaLexicalException;
import src.execptions.OniclaSintaticoException;
import src.sintatico.OniclaSintatico;

public class Main {
    public static void main(String[] args) {
        try {
            //String test = "/home/jose/Ufal/5_periodo/Compiladores/Compiladores/onicla/src/main/ola.txt";
            //OniclaLexico lexico = new OniclaLexico(test);
            OniclaSintatico sintatico = new OniclaSintatico(args[0]);

            System.out.println("Sucesso ");
        } catch (OniclaLexicalException ex) {
            System.out.println("Lexico erro " + ex.getMessage());
        } catch (OniclaSintaticoException ex){
            System.out.println("Syntax Error " +ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Erro generico");
        }
    }
}