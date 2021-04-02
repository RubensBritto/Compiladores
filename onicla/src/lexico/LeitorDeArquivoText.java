package lexico;

import java.io.*;

public class LeitorDeArquivoText {

    public void executar() throws IOException {

        String path = "D:\\Rubens_HD\\Intellij\\teste\\testes\\ola.txt";
        String path2 = "C:\\Users\\ramon\\Downloads\\Compiladores-main\\onicla\\src\\ola.txt";

        OniclaLexico lex = new OniclaLexico();
        FileReader arq = new FileReader(path);
        BufferedReader lerArq = new BufferedReader(arq);

        while(true){
            String linha = lerArq.readLine();
            if(linha == null){
                arq.close();
                break;
            }
            lex.proxToken(linha);
        }
    }
}
