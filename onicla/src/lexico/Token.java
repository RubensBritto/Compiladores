package lexico;

public class Token {
    public TipoToken nome;
    public String lemexa;

    public Token(TipoToken nome, String lemexa) {
        this.nome = nome;
        this.lemexa = lemexa;
    }

    @Override
    public String toString() {
        return "<"+nome+","+lemexa+">";
    }
}
