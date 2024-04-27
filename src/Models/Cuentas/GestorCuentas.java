package Models.Cuentas;

import java.util.ArrayList;

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

    public Usuario encontrarUsuario(String email) {
        for (Usuario usuario : usuariosList) {
            if (email == usuario.getEmail()) {
                return usuario;
            }
        }
        return null;
    }

    public Boolean inicioSesion() {
        boolean atras = false;

        System.out.println("INICIAR SESIÓN:");
        System.out.println("---------------");
        while (!atras && usuarioActivo == null) {
            System.out.println("Introduzca correo electrónico:");
            String email = Input.validarEmail(Input.inputUsuario());

            if (email != null) {
                Usuario encontrado = encontrarUsuario(email);
                if (encontrado != null) {
                    usuarioActivo = encontrado;
                    return true;
                } else {
                    System.out.println("El usuario no existe. ¿Desea intentarlo de nuevo? (S/N)");
                    String respuesta = Input.inputUsuario();
                    if (!respuesta.equalsIgnoreCase("S")) {
                        atras = true;
                    }
                }
            }
        }
        return false;
    }

}
