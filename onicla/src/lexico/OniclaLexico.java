package lexico;

public class OniclaLexico {

    private String name = "";
    private int state;

    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }
    public String getName() {
        return name;
    }
    public void setName(char c) {
        this.name += c;
    }
    public void clearName(){
        this.name = "";
    }

    public void proxToken(String linha) {
        int j;
        char[] d = new char[1000];
        d = linha.toCharArray();
        setState(1);
        for(int i = 0; i < d.length; i++) {
            if(Character.isWhitespace(d[i]))
                i++;
            if(Character.isUpperCase(d[0]) && Character.isLetter(d[i]))
                keyWords(d[i]);
            if(Character.isLowerCase(d[0]) && Character.isLetterOrDigit(d[i])) {
                if(!Character.isLetterOrDigit(d[i+1])) {
                    setState(2);
                }
                identificadores(d[i]);
            }
            if(Character.isDigit(d[i]) || (d[i] == '.' && Character.isDigit(d[i+1]))) {
                if (d[i] == '.')
                    setState(3);
                if (d[i + 1] == ';' && getState() == 3)
                    setState(4);
                else if (d[i + 1] == ';' && getState() == 1)
                    setState(2);
                numeros(d[i]);
            }
            if(d[i] == '*' || d[i] == '/' || d[i] == '-' || d[i] == '+' || d[i] == '%')
                operadoresAritmetico(d[i]);
            if(d[i] == ',' || d[i] == '(' || d[i] == ')' || d[i] == '[' || d[i] == ']' || d[i] == ';')
                delimitador(d[i]);
            if(d[i] == '>' || d[i] == '<'){
                if(d[i] == '>' && d[i+1] == '=') {
                    i++;
                    operadoresRelacionais(1);
                }
                else if(d[i] == '<' && d[i+1] == '=') {
                    i++;
                    operadoresRelacionais(2);
                }
                else if(d[i] == '>')
                    operadoresRelacionais(3);
                else
                    operadoresRelacionais(4);
            }
            if (d[i] == '=' || d[i] == '!') {
                if(d[i+1] == '=') {
                    i++;
                    operadoresBooleanos(1);
                } else if(d[i+1] == '/') {
                    i += 2;
                    operadoresBooleanos(2);
                } else if(d[i] == '=') {
                    operadoresBooleanos(3);
                } else {
                    operadoresBooleanos(4);
                }
            }
            if (d[i] == '\'')
                cadeiaCharacter(d[i]);
            if (d[i] == '\n' || d[i] == '\t' || d[i] == '\r' || Character.isWhitespace(d[i]) || d[i] == '#')
                spaceAndComments(d[i]);
        }
        return;
    }

    private Token operadoresAritmetico(char c) {
        if(c == '*') {
            System.out.println(new Token(TipoToken.OP_MULT, "*"));
        } else if(c == '/') {
            System.out.println(new Token(TipoToken.OP_DIV, "/"));
        } else if(c == '+') {
            System.out.println(new Token(TipoToken.OP_AD, "+"));
        } else if(c == '-') {
            System.out.println(new Token(TipoToken.OP_SUB, "-"));
        } else if(c == '%') {
            System.out.println(new Token(TipoToken.OP_RES, "%"));
        }
        return null;
    }

    private Token delimitador(char c) {
        if (c == ',') {
            System.out.println(new Token(TipoToken.SEP, ","));
        } else if(c == '(') {
            System.out.println(new Token(TipoToken.AB_PAR, "("));
        } else if(c == ')') {
            System.out.println(new Token(TipoToken.FEC_PAR, ")"));
        }  else if(c == '[') {
            System.out.println(new Token(TipoToken.AB_COL, "["));
        } else if(c == ']') {
            System.out.println(new Token(TipoToken.FEC_COL, "]"));
        } else if(c == ';') {
           System.out.println( new Token(TipoToken.TERMINAL, ";"));
        }
        return null;
    }

    private Token operadoresRelacionais(int c) {
        if(c == 1) {
            System.out.println(new Token(TipoToken.OP_GREATEREQ, ">="));
        } else if(c == 2) {
            System.out.println(new Token(TipoToken.OP_LESSEQ, "<="));
        } else if(c == 3) {
            System.out.println(new Token(TipoToken.OP_GREATER, ">"));
        } else {
            System.out.println(new Token(TipoToken.OP_LESS, "<"));
        }
        return null;
    }

    private Token operadoresBooleanos(int c){
        if(c == 1) {
            System.out.println(new Token(TipoToken.OP_REL, "=="));
        } else if(c == 2) {
            System.out.println(new Token(TipoToken.OP_REL, "=/="));
        } else if(c == 3) {
            System.out.println(new Token(TipoToken.OP_ATR, "="));
        } else {
            System.out.println(new Token(TipoToken.OP_NOT, "!"));
        }
        return null;
    }

    private Token numeros(char c) {
        //falta olhar o sinal
        if(getState() == 1) {
            setName(c);
        } else if(getState() == 2) {
            setName(c);
            System.out.println(new Token(TipoToken.CTE_INT, getName()));
            clearName();
            setState(1);
        } else if(getState() == 3) {
            setName(c);
        } else if(getState() == 4) {
            setName(c);
            System.out.println(new Token(TipoToken.CTE_FLOAT, getName()));
            clearName();
            setState(1);
        }
        return null;
    }

    private Token identificadores(char c){
        if(getState() == 1) {
            setName(c);
        } else if(getState() == 2) {
            setName(c);
            System.out.println(new Token(TipoToken.ID,getName()));
            clearName();
            setState(1);
        }
        return null;
    }

    private Token cadeiaCharacter(char c){
        int estado = 1;
        int cont = 0;
        while (true) {
            if (estado == 1) {
                if (Character.isWhitespace(c) || c == ' ') {
                    estado = 1;
                } else if (c == '\'') {
                    cont++;
                    estado = 2;
                } else {
                    return null;
                }
            } else if(estado == 2){
                if(Character.isWhitespace(c) || c == ' ') {
                    cont--;
                } else if(cont == 2 && c == '\''){
                    return new Token(TipoToken.CTE_CHAR, getName());
                } else if (c == '\'') {
                    return new Token(TipoToken.CTE_CAD_CHARAC, getName());
                }
                cont++;
            }
        }
    }

    private void spaceAndComments(char c) {
        int estado = 1;
        while (true) {
            if(estado == 1){
                if(Character.isWhitespace(c) || c == ' ') {
                    estado = 2;
                } else if(c == '#') {
                    estado = 3;
                } else{
                    return;
                }
            } else if(estado == 2) {
                if(c == '#') {
                    estado = 3;
                } else if(!(Character.isWhitespace(c) || c == ' ')) {
                    return;
                }
            } else if(estado == 3){
                if (c == '\n'){
                    return;
                }
            }
        }
    }

    private Token keyWords(char c) {
        //String lexema += c
        while (true) {
            //char c = (char) ldat.lerProxCaractere();
            if(!Character.isLetter(c)) {
                String lexema =getName();
                if(lexema.equals("And")) {
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
}
