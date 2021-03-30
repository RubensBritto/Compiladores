package lexico;

import java.io.FileNotFoundException;

public class OniclaLexico {

    LeitorDeArquivoText ldat;
    public OniclaLexico(String archive){
        try {
            ldat = new LeitorDeArquivoText(archive);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Token proxToken() {
        int characterLido = -1;

        while ((characterLido = ldat.lerProxCaractere()) != -1) {
            char c = (char) characterLido;
            if (c == ' ' || c == '\n') continue;
            if (c == ',') {
                return new Token(TipoToken.SEP, ",");
            } else if (c == '*') {
                return new Token(TipoToken.OP_MULT, "*");
            } else if (c == '/') {
                return new Token(TipoToken.OP_DIV, "/");
            } else if (c == '+') {
                return new Token(TipoToken.OP_AD, "+");
            } else if (c == '-') {
                return new Token(TipoToken.OP_SUB, "-");
            } else if (c == '(') {
                return new Token(TipoToken.AB_PAR, "(");
            } else if (c == ')') {
                return new Token(TipoToken.FEC_PAR, ")");
            } else if(c == ';'){
                return new Token(TipoToken.TERMINAL, ";");
            }
        }
        return null;
    }
}
