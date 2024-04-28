package Models.Cuentas;

import java.util.ArrayList;

import Excepciones.MaxIntentosException;
import Utils.EncoderContrasenyas;
import Utils.Input;

public class GestorCuentas {

    private ArrayList<Usuario> usuariosList;
    private Usuario usuarioActivo;

    public GestorCuentas() {
        this.usuariosList = new ArrayList<>();
    }

    public ArrayList<Usuario> getUsuariosList() {
        return usuariosList;
    }

    public Usuario getUsuarioActivo() {
        return usuarioActivo;
    }

    public Usuario buscarEmail(String email) {
        Usuario usuarioEncontrado = null;
        int contador = 0;
        while (usuarioEncontrado == null && contador < usuariosList.size()) {
            if (usuariosList.get(contador).getEmail().equals(email)) {
                usuarioEncontrado = usuariosList.get(contador);
            }
            contador++;
        }
        return usuarioEncontrado;
    }

    public Boolean inicioSesion() throws MaxIntentosException {
        boolean atras = false;
        Usuario usuarioValido = null;
        boolean contrasenyaValida = false;
        int intentos = 0;

        System.out.println("INICIAR SESIÓN:");
        System.out.println("---------------");

        while (!atras && usuarioValido == null) {
            System.out.println("Introduzca correo electrónico:");
            String email = Input.validarEmail(Input.scanner());

            if (email != null) {
                usuarioValido = buscarEmail(email);
                if (usuarioValido == null) {
                    System.out.println("El usuario no existe.");
                    atras = true;
                }
            }
        }
        while (!atras && usuarioValido != null && !contrasenyaValida) {

            System.out.println("Introduzca contraseña;");

            // Aunque sea mas ilegible, creo que es mas seguro, que las contraseñas
            // codificadas
            // no se almacenen en memoria, y comprobarla simplemente con un boolean
            contrasenyaValida = EncoderContrasenyas.encodeContrasenya(Input.scanner(), usuarioValido.getSalt())
                    .equals(usuarioValido.getContrasenyaCodificada());

            if (!contrasenyaValida) {
                System.out.println("Contraseña incorrecta.");
                intentos++;
                if (intentos >= 3) {
                    throw new MaxIntentosException();
                }
            }
        }

        if (usuarioValido != null && contrasenyaValida) {
            usuarioActivo = usuarioValido;
            return true;
        } else {
            return false;
        }
    }

    public void crearCuenta() throws MaxIntentosException {
        boolean atras = false;
        String nombre = null;
        String apellido = null;
        String email = null;
        String contrasenya = null;
        String repetirContrasenya;
        Usuario tempCuenta;
        int intentos = 0;

        System.out.println("CREAR USUARIO:");
        System.out.println("--------------");
        while (!atras && nombre == null) {
            System.out.println("Introduzca el nombre:");
            nombre = Input.comprobarSoloLetras(Input.scanner());
        }
        while (!atras && apellido == null) {
            System.out.println("Introduzca el apellido:");
            apellido = Input.comprobarSoloLetras(Input.scanner());
        }
        while (!atras && email == null) {
            System.out.println("Introduzca el correo electrónico:");
            email = Input.validarEmail(Input.scanner());
        }
        tempCuenta = new Usuario(nombre, apellido, email);
        while (!atras && contrasenya == null) {
            System.out.println("Introduzca la contrasenya:");
            contrasenya = Input.scanner();
            System.out.println("Repita la contrasenya:");
            repetirContrasenya = Input.scanner();
            if (contrasenya.equals(repetirContrasenya)) {
                tempCuenta.setContrasenyaCodificada(contrasenya);
                contrasenya = null;
                repetirContrasenya = null;
                System.out.println("Cuenta creada correctamente.");
                usuariosList.add(tempCuenta);
                atras = true;
            } else {
                intentos++;
                if (intentos >= 3) {
                    throw new MaxIntentosException();
                }
                System.out.println("Las contraseñas no coinciden. Inténtelo de nuevo.");
                contrasenya = null;
            }

        }
    }
}
