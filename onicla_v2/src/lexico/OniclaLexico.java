package lexico;

import exceptions.OniclaLexicalException;

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
            System.out.println("----------------");
            position = 0;
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public Token nextToken(){
        char currentChar;
        String term = "";
        if (isEOF()){
            return null;
        }
        state = 0;
        while (true){
            currentChar = nextChar();
            switch (state){
                case 0:
                    if (isCharLower(currentChar)){
                        term+= currentChar;
                        state = 1;
                    }else if(isDigit(currentChar)) {
                        term += currentChar;
                        state = 3;
                    } else if(isSpace(currentChar)) {
                        state = 0;
                    } else if(isOperator(currentChar)) {
                        state = 5;
                    } else{
                        throw new OniclaLexicalException("Unrecognized SYMBOL");
                    }
                    break;
                case 1:
                    if (isCharLower(currentChar) || isCharUpper(currentChar) || isDigit(currentChar)) {
                        term += currentChar;
                        state = 1;
                    } else if(isSpace(currentChar) || isOperator(currentChar)) {
                        state = 2;
                    } else {
                        throw new OniclaLexicalException("Malformed Intifier");
                    }
                    break;
                case 2:
                    back();
                    return new Token(TipoToken.ID,term);
                case 3:
                    if(isDigit(currentChar)){
                        term+= currentChar;
                        state = 3;
                    } else if(currentChar == '.'){
                        term+= currentChar;
                        state = 4;
                    } else if(!Character.isLetterOrDigit(currentChar)) {
                        state = 5;
                    }else {
                        throw new OniclaLexicalException("Unrecognized Number");
                    }
                    break;
                case 4:
                    if(isDigit(currentChar)){
                        term+= currentChar;
                        state = 4;

                    } else if (currentChar == '.'){
                        throw new OniclaLexicalException("Unrecognized Number");

                    } else if(!Character.isLetterOrDigit(currentChar)) {
                        back();
                        state = 6;
                    } else {
                        throw new OniclaLexicalException("Unrecognized Number");
                    }
                    break;
                case 5:
                    back();
                    return new Token(TipoToken.CTE_INT, term);
                case 6:
                    back();
                    term+= currentChar;
                    return new Token(TipoToken.CTE_FLOAT, term);


            }


        }
    }
    private boolean isDigit(char c){
        return Character.isDigit(c);
    }

    private boolean isCharUpper(char c){
        return Character.isUpperCase(c);
    }

    private boolean isCharLower(char c) {
        return Character.isLowerCase(c);
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

    private void back(){
        position--;
    }

}
