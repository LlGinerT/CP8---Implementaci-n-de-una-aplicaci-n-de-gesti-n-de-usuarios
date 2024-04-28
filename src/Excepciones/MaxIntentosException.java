package Excepciones;

public class MaxIntentosException extends Exception {
    public MaxIntentosException() {
        super("Máximo de intentos alcanzado.");
    }
}
