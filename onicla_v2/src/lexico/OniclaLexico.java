package lexico;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class OniclaLexico {
    private char[] content; //ler o arquivo inteiro
    private int state;
    private int position;

    public OniclaLexico(String arquivo){
        try {
            String conteudo;
            conteudo = new String(Files.readAllBytes(Paths.get(arquivo)), StandardCharsets.UTF_8);
            System.out.println("DEBUG ----------");
            content = conteudo.toCharArray();
            System.out.println(conteudo);
            System.out.println("--------------");
            position = 0;
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public Token nextToken(){
        char currentChar;
        if (isEOF()){
            return null;
        }
        state = 0;
        while (true){
            currentChar = nextChar();
            switch (state){
                case 0:
                    if (isChar(currentChar)){
                        state = 1;

                    }else if(isDigit(currentChar)){
                        state = 3;
                    }else if(isSpace(currentChar)){
                        state = 0;
                    }
                    break;
                case 1:
                    if (isChar(currentChar) || isDigit(currentChar)){
                        state = 1;
                    }else{
                        state = 2;
                    }
                    break;
                case 2:
                    new Token(TipoToken.ID,)
            }


        }
    }
    private boolean isDigit(char c){
        return Character.isDigit(c);
    }

    private boolean isChar(char c){
        return Character.isLetter(c);
    }

    private boolean isOperator(char c){
        return c == '>' || c == '<' || c == '=' || c == '/' || c == '!';
    }

    private boolean isSpace(char c){
        return Character.isWhitespace(c) || c == '\t' || c == '\n' || c == '\r';
    }

    private char nextChar(){
        return content[position++];
    }

    private boolean isEOF(){
        return position == content.length;
    }

    private boolean back(){
        position--;
    }

}
