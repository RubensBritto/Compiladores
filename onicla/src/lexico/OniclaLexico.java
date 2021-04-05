package src.lexico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class OniclaLexico {
    KeyWordsHash hash = new KeyWordsHash();
    private char[] content;
    private int state;
    private int position;
    private String lineTxt = " ";
    private int line, column;
    private BufferedReader reader;
    Token token;


    public OniclaLexico(String arquivo) {
        try {
            line = 1;
            column = 1;
            reader = new BufferedReader(new FileReader(new File(arquivo)));
            nextLine();
            content = lineTxt.toCharArray();
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
            if(isEOF()) {
                if(nextLine()) {
                    content = lineTxt.toCharArray();
                } else {
                    return new Token(TipoToken.EOF, "EOF", line, column);
                }
            }
            currentChar = nextChar();
    
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
                    } else if(isCharUpper(currentChar)) {
                        term += currentChar;
                        state = 11;
                    } else if(currentChar == '#') {
                        state = 13;
                    } else if(currentChar == '+') {
                        term += currentChar;
                        column++;
                        return new Token(TipoToken.OP_AD, term,line,column);
                    } else if(currentChar == '-') {
                        term += currentChar;
                        column++;
                        return new Token(TipoToken.OP_SUB, term,line,column);
                    } else if(currentChar == '*') {
                        term += currentChar;
                        column++;
                        return new Token(TipoToken.OP_MULT, term,line,column);
                    } else if(currentChar == '/') {
                        term += currentChar;
                        column++;
                        return new Token(TipoToken.OP_DIV, term,line,column);
                    } else if(currentChar == '%') {
                        term += currentChar;
                        column++;
                        return new Token(TipoToken.OP_RES, term,line,column);
                    } else if(currentChar == '(') {
                        term += currentChar;
                        column++;
                        return new Token(TipoToken.AB_PAR, term,line,column);
                    } else if(currentChar == ')') {
                        term += currentChar;
                        column++;
                        return new Token(TipoToken.FEC_PAR, term,line,column);
                    } else if(currentChar == '[') {
                        term += currentChar;
                        column++;
                        return new Token(TipoToken.AB_COL, term,line,column);
                    } else if(currentChar == ']') {
                        term += currentChar;
                        column++;
                        return new Token(TipoToken.FEC_COL, term,line,column);
                    } else if(currentChar == ';') {
                        term += currentChar;
                        column++;
                        return new Token(TipoToken.TERMINAL, term,line,column);
                    } else if(currentChar == ',') {
                        term += currentChar;
                        column++;
                        return new Token(TipoToken.SEP, term,line,column);
                    } else if(currentChar == '~') {
                        term += currentChar;
                        column++;
                        return new Token(TipoToken.OP_NOTUN, term,line,column);
                    } else if(currentChar == '^') {
                        term += currentChar;
                        column++;
                        return new Token(TipoToken.OP_CONCAT, term,line,column);
                    } else {
                        new Token(TipoToken.ER_SYMBOL, term,line,column);
                    }
                    break;
                case 1:
                    if(isCharLower(currentChar) || isCharUpper(currentChar) || isDigit(currentChar)) {
                        term += currentChar;

                    } else if(isSpace(currentChar) || isOperator(currentChar) || !Character.isLetterOrDigit(currentChar)) {
                        back();
                        state = 2;
                    } else {
                        column++;
                        new Token(TipoToken.ER_ID, term,line,column);
                    }
                    break;
                case 2:
                    back();
                    column++;
                    return new Token(TipoToken.ID, term,line,column);
                case 3:
                    if(isDigit(currentChar)) {
                        term += currentChar;

                    } else if(currentChar == '.') {
                        term += currentChar;
                        state = 4;
                        column++;
                    } else if(!Character.isLetterOrDigit(currentChar) || isSpace(currentChar) || isOperator(currentChar)) {
                        back();
                        state = 5;
                    } else {
                        column++;
                        new Token(TipoToken.ER_NUMBER, term,line,column);
                    }
                    break;
                case 4:
                    if(isDigit(currentChar)) {
                        term += currentChar;
                    } else if(currentChar == '.') {
                        column++;
                        new Token(TipoToken.ER_NUMBER, term,line,column);
                    } else if(!Character.isLetterOrDigit(currentChar) || isSpace(currentChar) || isOperator(currentChar)) {
                        back();
                        state = 6;
                    } else {
                        column++;
                        new Token(TipoToken.ER_NUMBER, term,line,column);
                    }
                    break;
                case 5:
                    back();
                    column++;
                    return new Token(TipoToken.CTE_INT, term,line,column);
                case 6:
                    back();
                    column++;
                    return new Token(TipoToken.CTE_FLOAT, term,line,column);
                case 7:
                    back();
                    back();
                    currentChar = nextChar();
                    if(currentChar == '>') {
                        currentChar = nextChar();
                        if(currentChar == '=') {
                            term += currentChar;
                            column++;
                            return new Token(TipoToken.OP_GREATEQ, term,line,column);
                        } else {
                            back();
                            column++;
                            return new Token(TipoToken.OP_GREATER, term,line,column);
                        }
                    } else if(currentChar == '<') {
                        currentChar = nextChar();
                        if(currentChar == '=') {
                            term += currentChar;
                            column++;
                            return new Token(TipoToken.OP_LESSEQ, term,line,column);
                        } else {
                            back();
                            column++;
                            return new Token(TipoToken.OP_LESS, term,line,column);
                        }
                    } else if(currentChar == '=') {
                        currentChar = nextChar();
                        if(currentChar == '=') {
                            term += currentChar;
                            column++;
                            return new Token(TipoToken.OP_REL, term,line,column);
                        } else if(currentChar == '/') {
                            term += currentChar;
                            currentChar = nextChar();
                            if(currentChar == '=') {
                                term += currentChar;
                                column++;
                                return new Token(TipoToken.OP_REL, term,line,column);
                            } else {
                                column++;
                                new Token(TipoToken.ER_SYMBOL, term,line,column);
                            }
                        } else {
                            back();
                            column++;
                            return new Token(TipoToken.OP_ATR, term,line,column);
                        }
                    } else if(currentChar == '!') {
                        return new Token(TipoToken.OP_NOT, term,line,column);
                    } else {
                        column++;
                        new Token(TipoToken.ER_SYMBOL, term,line,column);
                    }
                case 8:
                    if(currentChar >= (char) 32 && currentChar <= (char) 126) {
                        term += currentChar;
                        currentChar = nextChar();
                        if(currentChar == '\'') {
                            term += currentChar;
                            state = 9;
                        } else {
                            back();
                            state = 10;
                        }
                    } else {
                        column++;
                        new Token(TipoToken.ER_CHAR, term,line,column);
                    }
                    break;

                case 9:
                    back();
                    column++;
                    return new Token(TipoToken.CTE_CHAR, term,line,column);
                case 10:
                    if(currentChar >= (char) 32 && currentChar <= (char) 126) {
                        term += currentChar;
                        if(currentChar == '\'') {
                            column++;
                            return new Token(TipoToken.CTE_CADCHA, term,line,column);
                        }
                    } else {
                        column++;
                        System.out.println("ulitmo " + term);
                        new Token(TipoToken.ER_CHAR, term,line,column);
                    }
                    break;
                case 11:
                    if(isCharLower(currentChar)) {
                        term += currentChar;
                    } else if(!isCharLower(currentChar) || isSpace(currentChar)) {
                        back();
                        state = 12;
                    }
                    break;
                case 12:
                    back();
                    if(hash.keyWords.get(term) != null) {
                        column++;
                       return new Token(hash.keyWords.get(term), term, line, column);
                    } else {
                        column++;
                        return new Token(TipoToken.ER_KEYWORD, term, line, column);
                    }
                case 13:
                    if(isSpace(currentChar)) {
                        state = 0;
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
        //column++;
        return content[position++];
    }

    private boolean isEOF() {
        return position == content.length;
    }

    private void back() {
        //column--;
        position--;
    }

    public void printCodeLine(String conteudo) {
        String format = "%4d  %s";
        System.out.println(String.format(format, line, conteudo));
    }

    private boolean nextLine() {
        String contetTemp = " ";
        try {
            contetTemp = reader.readLine();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if(contetTemp != null) {
            lineTxt = contetTemp;
            printCodeLine(lineTxt);
            lineTxt += " ";
            line++;
            position = 0;
            column = 0;

            return true;
        }
        return false;
    }
}
