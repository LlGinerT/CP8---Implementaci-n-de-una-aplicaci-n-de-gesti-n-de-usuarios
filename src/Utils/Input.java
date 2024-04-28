package Utils;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Input {
    static Scanner scanner = new Scanner(System.in);

    public static String scanner() {
        return scanner.nextLine();
    }

    public static int comprobarEntero(String inputUsuario) {
        while (true) {
            try {
                return Integer.parseInt(inputUsuario);
            } catch (Exception e) {
                System.out.println("Valor erróneo. Introduce un número entero válido.");
                inputUsuario = scanner.nextLine();
            }
        }
    }

    public static String comprobarSoloLetras(String inputUsuario) {
        while (true) {

            if (inputUsuario.matches("[a-zA-Z]+")) {
                return inputUsuario;
            } else {
                System.out.println("Solo puede contener Letras");
                inputUsuario = scanner.nextLine();
            }
        }
    }

    public static String validarEmail(String inputUsuario) {
        // Expresión regular para validar emails
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+(\\.com|\\.es|\\.org|\\.net)$";

        // Compilar la expresión regular en un patrón
        Pattern pattern = Pattern.compile(regex);

        // Crear un objeto Matcher para comparar el email con el patrón
        Matcher matcher = pattern.matcher(inputUsuario);

        if (matcher.matches()) {
            return inputUsuario;
        } else {
            System.out.println("Email incorrecto. Debe tener formato XXXXX@XXXX.com|.es|.org|.net");
            return null;
        }
    }
}
