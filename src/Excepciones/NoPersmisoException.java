package Excepciones;

public class NoPersmisoException extends Exception {
    public NoPersmisoException() {
        super("No dispones de ningún permiso.");
    }
}
