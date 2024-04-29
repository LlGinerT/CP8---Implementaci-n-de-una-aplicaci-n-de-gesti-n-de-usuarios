package Excepciones;

public class NoPermisoException extends Exception {
    public NoPermisoException() {
        super("No dispones de ningún permiso.");
    }
}
