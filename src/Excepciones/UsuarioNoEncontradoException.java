package Excepciones;

public class UsuarioNoEncontradoException extends Exception {
    public UsuarioNoEncontradoException() {
        super("Usuario no encontrado");
    }
}
