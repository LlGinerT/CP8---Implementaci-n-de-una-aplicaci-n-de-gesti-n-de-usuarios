package Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/* 
 * Método que concatena una cadena de 16 bytes aleatorios a la contraseña del usuario
 * y la transforma en un hash utilizando el algoritmo SHA-256
 * PD: Lo mas seguro es que el salt y la contraseña no se almacenen en el mismo lugar,
 * para el ejercicio ambos están almacenados en el usuario.
 */
public class EncoderContrasenyas {

    // Método para generar un salt aleatorio
    public static byte[] generateSalt() {
        byte[] salt = new byte[16]; // Tamaño del salt en bytes
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        return salt;
    }

    // Método para codificar una contraseña con salt utilizando SHA-256
    public static String encodeContrasenya(String password, byte[] salt) {
        try {

            // Concatenar el salt a la contraseña
            String saltedPassword = password + Base64.getEncoder().encodeToString(salt);

            // Crear una instancia de MessageDigest para SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // Aplicar el algoritmo de hash a la contraseña con salt
            byte[] encodedPassword = digest.digest(saltedPassword.getBytes());

            // Convertir el resultado del hash a una cadena hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedPassword) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Manejar la excepción si no se encuentra el algoritmo de hash
            e.printStackTrace();
            return null;
        }
    }
}