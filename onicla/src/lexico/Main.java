package lexico;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        //String path = "C:\\Users\\ramon\\Downloads\\Compiladores-main\\onicla\\src\\ola.txt";
        //String path2 = "D:\\Rubens_HD\\Intellij\\teste\\testes\\ola.txt";
        LeitorDeArquivoText l = new LeitorDeArquivoText();
        try {
            l.executar();
        } catch (Exception e){
            System.err.println(e);
        }
    }
}
