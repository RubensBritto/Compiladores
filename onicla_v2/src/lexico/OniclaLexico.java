package lexico;

import exceptions.OniclaLexicalException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class OniclaLexico {
    private char[] content; //ler o arquivo inteiro
    private int state;
    private int position;

    public OniclaLexico(String arquivo) {
        try {
            String conteudo;
            conteudo = new String(Files.readAllBytes(Paths.get(arquivo)), StandardCharsets.UTF_8) +"  ";
            System.out.println("DEBUG ----------");
            content = conteudo.toCharArray();
            System.out.println(conteudo);
            System.out.println("----------------");
            position = 0;
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public Token nextToken() {
        char currentChar;
        String term = "";
        state = 0;
        while(true) {
            currentChar = nextChar();
            if(isEOF()) {
                return new Token(TipoToken.EOF, "EOF");
            }
            switch(state) {
                case 0:
                    if(isCharLower(currentChar)) {
                        term += currentChar;
                        state = 1;
                    } else if(isDigit(currentChar)) {
                        term += currentChar;
                        state = 3;
                    } else if(isSpace(currentChar)) {
                        state = 0;
                    } else if(isOperator(currentChar)) {
                        term += currentChar;
                        state = 7;
                    } else if(currentChar == '\'') {
                        term += currentChar;
                        state = 8;
                    } else if(currentChar == '+') {
                        term += currentChar;
                        return new Token(TipoToken.OP_AD, term);
                    } else if(currentChar == '-') {
                        term += currentChar;
                        return new Token(TipoToken.OP_SUB, term);
                    } else if(currentChar == '*') {
                        term += currentChar;
                        return new Token(TipoToken.OP_MULT, term);
                    } else if(currentChar == '/') {
                        term += currentChar;
                        return new Token(TipoToken.OP_DIV, term);
                    } else if(currentChar == '%') {
                        term += currentChar;
                        return new Token(TipoToken.OP_RES, term);
                    } else if(currentChar == '(') {
                        term += currentChar;
                        return new Token(TipoToken.AB_PAR, term);
                    } else if(currentChar == ')') {
                        term += currentChar;
                        return new Token(TipoToken.FEC_PAR, term);
                    } else if(currentChar == '[') {
                        term += currentChar;
                        return new Token(TipoToken.AB_COL, term);
                    } else if(currentChar == ']') {
                        term += currentChar;
                        return new Token(TipoToken.FEC_COL, term);
                    } else if(currentChar == ';') {
                        term += currentChar;
                        return new Token(TipoToken.TERMINAL, term);
                    } else if(currentChar == ',') {
                        term += currentChar;
                        return new Token(TipoToken.SEP, term);
                    } else {
                        throw new OniclaLexicalException("Unrecognized Symbol");
                    }
                    break;
                case 1:
                    if(isCharLower(currentChar) || isCharUpper(currentChar) || isDigit(currentChar)) {
                        term += currentChar;

                    } else if(isSpace(currentChar) || isOperator(currentChar) || !Character.isLetterOrDigit(currentChar)) {
                        back();
                        state = 2;
                    } else {
                        throw new OniclaLexicalException("Malformed Identifier");
                    }
                    break;
                case 2:
                    back();
                    return new Token(TipoToken.ID, term);
                case 3:
                    if(isDigit(currentChar)) {
                        term += currentChar;

                    } else if(currentChar == '.') {
                        term += currentChar;
                        state = 4;
                    } else if(!Character.isLetterOrDigit(currentChar) || isSpace(currentChar) || isOperator(currentChar)){
                        back();
                        state = 5;
                    } else {
                        throw new OniclaLexicalException("Unrecognized Number");
                    }
                    break;
                case 4:
                    if(isDigit(currentChar)) {
                        term += currentChar;

                    } else if(currentChar == '.') {
                        throw new OniclaLexicalException("Unrecognized Number");

                    } else if(!Character.isLetterOrDigit(currentChar) || isSpace(currentChar) || isOperator(currentChar)) {
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
                    return new Token(TipoToken.CTE_FLOAT, term);
                case 7:
                    back();
                    back();
                    currentChar = nextChar();
                    if(currentChar == '>') {
                        currentChar = nextChar();
                        if(currentChar == '=') {
                            term += currentChar;
                            return new Token(TipoToken.OP_GREATEREQ, term);
                        } else {
                            back();
                            return new Token(TipoToken.OP_GREATER, term);
                        }
                    } else if(currentChar == '<') {
                        currentChar = nextChar();
                        if(currentChar == '=') {
                            term += currentChar;
                            return new Token(TipoToken.OP_LESSEQ, term);
                        } else {
                            back();
                            return new Token(TipoToken.OP_LESS, term);
                        }
                    } else if(currentChar == '=') {
                        currentChar = nextChar();
                        if(currentChar == '=') {
                            term += currentChar;
                            return new Token(TipoToken.OP_REL, term);
                        } else if(currentChar == '/') {
                            term += currentChar;
                            currentChar = nextChar();
                            if(currentChar == '=') {
                                term += currentChar;
                                return new Token(TipoToken.OP_REL, term);
                            } else {
                                throw new OniclaLexicalException("Unrecognized Symbol");
                            }
                        } else {
                            back();
                            return new Token(TipoToken.OP_ATR, term);
                        }
                    } else if(currentChar == '!') {
                        return new Token(TipoToken.OP_NOT, term);
                    } else {
                        throw new OniclaLexicalException("Unrecognized Symbol");
                    }
                case 8:
                    if(currentChar >= (char) 32 && currentChar <= (char) 126) {
                        term += currentChar;
                        currentChar = nextChar();
                        if(currentChar == '\'') {
                            term += currentChar;
                            state = 9;
                        } else {
                            term += currentChar;
                            state = 10;
                        }
                    } else {
                        throw new OniclaLexicalException("Unrecognized Character");
                    }
                    break;
                case 9:
                    back();
                    return new Token(TipoToken.CTE_CHAR, term);
                case 10:
                    if(currentChar >= (char) 32 && currentChar <= (char) 126) {
                        term += currentChar;
                        if(currentChar == '\'') {
                            return new Token(TipoToken.CTE_CAD_CHARAC, term);
                        }
                    } else {
                        throw new OniclaLexicalException("Unrecognized CharacterArray");
                    }
            }
        }
    }
    private boolean isDigit(char c) {
        return Character.isDigit(c);
    }

    private boolean isCharUpper(char c) {
        return Character.isUpperCase(c);
    }

    private boolean isCharLower(char c) {
        return Character.isLowerCase(c);
    }

    private boolean isOperator(char c) {
        return c == '>' || c == '<' || c == '=' || c == '!';
    }

    private boolean isSpace(char c) {
        return Character.isWhitespace(c) || c == '\t' || c == '\n' || c == '\r' || c == '\f' || c == '\0' || c == '\b';
    }

    private char nextChar() {
        return content[position++];
    }

    private boolean isEOF() {
        return position == content.length;
    }

    private void back() {
        position--;
    }

}
