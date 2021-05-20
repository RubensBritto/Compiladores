package src.lexico;

public class Token {
    public TipoToken tipoToken;
    public String lexema;
    public int line, columm;


    public Token(TipoToken tipoToken, String lexema, int line, int columm) {
        this.tipoToken = tipoToken;
        this.lexema = lexema;
        this.line = line;
        this.columm = columm;
    }

    public Token() {
        super();
    }


    @Override
    public String toString() {
        String format = "          [%04d, %04d] (%04d, %20s) {%s}";
        return String.format(format, line-1, columm, tipoToken.ordinal(), tipoToken.toString(), lexema);
    }
}