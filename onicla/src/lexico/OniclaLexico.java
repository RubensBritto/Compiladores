package lexico;

import java.io.FileNotFoundException;

public class OniclaLexico {

    LeitorDeArquivoText ldat;
    public OniclaLexico(String archive){
        ldat = new LeitorDeArquivoText(archive);
    }

    public Token proxToken(){
        Token proximo = null;
        spaceAndComments();
        ldat.confirmar();

        proximo = endOfFiel();
        if(proximo == null) {
            ldat.zerar();
        } else {
            ldat.confirmar();
            return proximo;
        }

        proximo = keyWords();
        if(proximo == null) {
            ldat.zerar();
        } else {
            ldat.confirmar();
            return proximo;
        }

        proximo = identificadores();
        if (proximo == null) {
            ldat.zerar();
        } else {
            ldat.confirmar();
            return proximo;
        }

        proximo = numeros();
        if (proximo == null) {
            ldat.zerar();
        } else {
            ldat.confirmar();
            return proximo;
        }

        proximo = operadoresAritmetico();
        if (proximo == null) {
            ldat.zerar();
        } else {
            ldat.confirmar();
            return proximo;
        }
        proximo = operadoresRelacionais();
        if (proximo == null) {
            ldat.zerar();
        } else {
            ldat.confirmar();
            return proximo;
        }

        proximo = operadoresBooleanos();
        if (proximo == null) {
            ldat.zerar();
        } else {
            ldat.confirmar();
            return proximo;
        }

        proximo = delimitador();
        if (proximo == null) {
            ldat.zerar();
        } else {
            ldat.confirmar();
            return proximo;
        }

        System.err.println("Erro léxico!");
        System.err.println(ldat.toString());
        return null;
    }

    private Token operadoresAritmetico(){
        int caracterLido = ldat.lerProxCaractere();
        char c = (char) caracterLido;
        if(c == '*') {
            return new Token(TipoToken.OP_MULT, "*");
        } else if(c == '/') {
            return new Token(TipoToken.OP_DIV, "/");
        } else if(c == '+') {
            return new Token(TipoToken.OP_AD, "+");
        } else if(c == '-') {
            return new Token(TipoToken.OP_SUB, "-");
        } else if(c == '%') {
            return new Token(TipoToken.OP_RES, "%");
        } else {
            return null;
        }
    }

    private Token delimitador() {
        int caracterLido = ldat.lerProxCaractere();
        char c = (char) caracterLido;
        if (c == ',') {
            return new Token(TipoToken.SEP, ",");
        } else if(c == '(') {
            return new Token(TipoToken.AB_PAR, "(");
        } else if(c == ')') {
            return new Token(TipoToken.FEC_PAR, ")");
        }  else if(c == '[') {
            return new Token(TipoToken.AB_COL, "[");
        } else if(c == ']') {
            return new Token(TipoToken.FEC_COL, "]");
        } else if(c == ';') {
            return new Token(TipoToken.TERMINAL, ";");
        } else {
            return null;
        }
    }
    private Token operadoresRelacionais() {
        int caracterLido = ldat.lerProxCaractere();
        char c = (char) caracterLido;
        if(c == '<') {
            c = (char) ldat.lerProxCaractere();
            if(c == '=') {
                return new Token(TipoToken.OP_LESSEQ, "<=");
            } else {
                ldat.rectroceder();
                return new Token(TipoToken.OP_LESS, "<");
            }
        } else if(c == '>') {
            c = (char) ldat.lerProxCaractere();
            if(c == '=') {
                return new Token(TipoToken.OP_GREATEREQ, ">=");
            } else {
                ldat.rectroceder();
                return new Token(TipoToken.OP_GREATER, ">");
            }
        } else {
            return null;
        }
    }

    private Token operadoresBooleanos(){
        int caracterLido = ldat.lerProxCaractere();
        char c = (char) caracterLido;
        if(c == '=') {
            c = (char) ldat.lerProxCaractere();
            if(c == '=') {
                return new Token(TipoToken.OP_REL, "==");
            } else if(c == '/') {
                c = (char) ldat.lerProxCaractere();
                if(c == '=') {
                    return new Token(TipoToken.OP_REL, "=/=");
                }
            } else {
                ldat.rectroceder();
                return new Token(TipoToken.OP_ATR, "=");
            }
        } else if(c == '!') {
            return new Token(TipoToken.OP_NOT, "!");
        }
        return null;
    }

    private Token numeros(){
        //falta olhar o sinal
        int estado = 1;
        while (true){
            char c = (char) ldat.lerProxCaractere();
            if(estado == 1){
                if (Character.isDigit(c)){
                    estado = 2;
                }else{
                    return null;
                }
            } else if(estado == 2){
                if(c == '.'){
                    c = (char) ldat.lerProxCaractere();
                    if (Character.isDigit(c)){
                        estado = 3;
                    }else{
                        return null;
                    }
                }else if(!Character.isDigit(c)){
                    ldat.rectroceder();
                    return new Token(TipoToken.CTE_INT, ldat.getLexema());
                }
            } else if(estado == 3){
                if (!Character.isDigit(c)){
                    ldat.rectroceder();
                    return new Token(TipoToken.CTE_FLOAT, ldat.getLexema());
                }
            }
        }
    }

    private Token identificadores(){
        int estado = 1;
        while (true){
            char c = (char) ldat.lerProxCaractere();
            if (estado == 1) {
                if(Character.isLowerCase(c)) {
                    estado = 2;
                } else {
                    return null;
                }
            } else if(estado == 2) {
                if (!Character.isLetterOrDigit(c)) {
                    ldat.rectroceder();
                    return new Token(TipoToken.ID, ldat.getLexema());
                }
            }
        }
    }
/*
    private Token cadeiaCharacter(){
        //COLOCA PARA RECONHECER CHAR

    }

 */

    private void spaceAndComments(){
        int estado = 1;
        while (true) {
            char c = (char) ldat.lerProxCaractere();
            if(estado == 1){
                if(Character.isWhitespace(c) || c == ' ') {
                    estado = 2;
                }else if(c == '#') {
                    estado = 3;
                }else {
                    ldat.rectroceder();
                    return;
                }
            } else if(estado == 2) {
                if(c == '#'){
                    estado = 3;
                } else if(!(Character.isWhitespace(c) || c == ' ')) {
                    ldat.rectroceder();
                    return;
                }
            } else if(estado == 3){
                if (c == '\n'){
                    return;
                }
            }
        }
    }

    private Token keyWords(){
        while (true){
            char c = (char) ldat.lerProxCaractere();
            if(!Character.isLetter(c)){
                ldat.rectroceder();
                String lexema = ldat.getLexema();
                if(lexema.equals("And")){
                    return new Token(TipoToken.PR_AND, lexema);
                } else if (lexema.equals("Or")) {
                    return new Token(TipoToken.PR_OR, lexema);
                } else if (lexema.equals("Function")) {
                    return new Token(TipoToken.PR_FUNCTION, lexema);
                } else if (lexema.equals("Refound")) {
                    return new Token(TipoToken.PR_REFOUND, lexema);
                } else if (lexema.equals("If")) {
                    return new Token(TipoToken.PR_IF, lexema);
                } else if (lexema.equals("Else")) {
                    return new Token(TipoToken.PR_ELSE, lexema);
                } else if (lexema.equals("While")) {
                    return new Token(TipoToken.PR_WHILE, lexema);
                } else if (lexema.equals("Forrepeat")) {
                    return new Token(TipoToken.PR_FORREPEAT, lexema);
                } else if (lexema.equals("Integer")) {
                    return new Token(TipoToken.PR_INTEGER, lexema);
                } else if (lexema.equals("Float")) {
                    return new Token(TipoToken.PR_FLOAT, lexema);
                } else if (lexema.equals("Characterarray")) {
                    return new Token(TipoToken.PR_CHARACTERARRAY, lexema);
                } else if (lexema.equals("Character")) {
                    return new Token(TipoToken.PR_CHARACTER, lexema);
                } else if (lexema.equals("Bool")) {
                    return new Token(TipoToken.PR_BOOL, lexema);
                } else if (lexema.equals("Input")) {
                    return new Token(TipoToken.PR_INPUT, lexema);
                } else if (lexema.equals("Print")) {
                    return new Token(TipoToken.PR_PRINT, lexema);
                } else if (lexema.equals("Printnl")) {
                    return new Token(TipoToken.PR_PRINTNL, lexema);
                } else if (lexema.equals("True")) {
                    return new Token(TipoToken.PR_TRUE, lexema);
                } else if (lexema.equals("False")) {
                    return new Token(TipoToken.PR_FALSE, lexema);
                } else if (lexema.equals("Null")) {
                    return new Token(TipoToken.PR_NULL, lexema);
                } else if (lexema.equals("BEGIN")) {
                    return new Token(TipoToken.PR_BEGIN, lexema);
                } else if (lexema.equals("END")) {
                    return new Token(TipoToken.PR_END, lexema);
                } else if (lexema.equals("Void")) {
                    return new Token(TipoToken.PR_VOID, lexema);
                } else if (lexema.equals("Main")) {
                    return new Token(TipoToken.PR_MAIN, lexema);
                }else{
                    return null;
                }
            }
        }
    }

    private Token endOfFiel(){
        int caracterLido = ldat.lerProxCaractere();
        if(caracterLido == -1){
            return new Token(TipoToken.FIM, "FIM");
        } else {
            return null;
        }
    }
}
