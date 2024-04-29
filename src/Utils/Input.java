package Utils;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Excepciones.FormatoEmailException;

public class Input {
    static Scanner scanner = new Scanner(System.in);

    public static String scanner() {
        return scanner.nextLine();
    }

    public static int comprobarEntero() {
        while (true) {
            try {
                String tempInput = scanner();
                return Integer.parseInt(tempInput);
            } catch (NumberFormatException e) {
                System.out.println("Valor erróneo. Introduce un número entero válido.");
            }
        }
    }

    public static String comprobarSoloLetras() {
        while (true) {
            String tempInput = scanner();
            if (tempInput.matches("[a-zA-Z]+")) {
                return tempInput;
            } else {
                System.out.println("Solo puede contener Letras");
            }
        }
    }

    public static String validarEmail() throws FormatoEmailException {
        String tempInput = scanner();
        // Expresión regular para validar emails
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+(\\.com|\\.es|\\.org|\\.net)$";

        // Compilar la expresión regular en un patrón
        Pattern pattern = Pattern.compile(regex);

        // Crear un objeto Matcher para comparar el email con el patrón
        Matcher matcher = pattern.matcher(tempInput);

        if (matcher.matches()) {
            return tempInput;
        } else {
            // Lanza una excepción si el email no es válido
            throw new FormatoEmailException();
        }
    }
}
