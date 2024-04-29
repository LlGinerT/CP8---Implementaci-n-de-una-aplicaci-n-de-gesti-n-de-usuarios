package Excepciones;

public class FormatoEmailException extends Exception {
    public FormatoEmailException() {
        super("Email incorrecto. Debe tener formato XXXXX@XXXX.com|.es|.org|.net");
    }
}
