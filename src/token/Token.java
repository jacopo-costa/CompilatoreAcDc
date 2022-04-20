package token;

public class Token {

    private int riga;
    private TokenType tipo;
    private String val;

    public Token(TokenType tipo, int riga, String val) {
        this.tipo = tipo;
        this.riga = riga;
        this.val = val;
    }

    public Token(TokenType tipo, int riga) {
        this.tipo = tipo;
        this.riga = riga;
    }

    // Getters per i campi
    public TokenType getTipo() {
        return tipo;
    }

    public int getRiga() {
        return riga;
    }

    public String getVal() {
        return val;
    }

    public String toString() {

        String token;

        if (val == null) {
            token = "<" + this.tipo.toString() + ",r:" + this.riga + ">";
        } else {
            token = "<" + this.tipo.toString() + ",r:" + this.riga + "," + val + ">";
        }

        return token;

    }

}
