import java.util.ArrayList;

import Excepciones.MaxIntentosException;
import Excepciones.OpcionNoDisponibleException;
import Excepciones.UsuarioNoEncontradoException;
import Models.Cuentas.GestorCuentas;
import Models.Permisos.Permiso;
import Utils.Input;

public class App {

    static boolean fin = false;
    static GestorCuentas gestor = new GestorCuentas();

    public static void main(String[] args) throws Exception {
        System.out.println("BIENVENIDO");
        while (!fin) {
            while (!fin && gestor.getUsuarioActivo() == null) {
                try {
                    menuInicial();
                } catch (OpcionNoDisponibleException | NumberFormatException e) {
                    System.out.println(e.getMessage() + ": Elija una opción valida");
                    System.out.println("-----------------------------------------");
                }
            }
            while (!fin && gestor.getUsuarioActivo() != null) {
                try {
                    menuPrincipal();
                } catch (OpcionNoDisponibleException | NumberFormatException e) {
                    System.out.println(e.getMessage() + ": Elija una opción valida");
                    System.out.println("-----------------------------------------");
                }
            }
        }
    }

    private static void menuInicial() throws OpcionNoDisponibleException, NumberFormatException {
        System.out.println("------------------");
        System.out.println("1) Iniciar Sesión");
        System.out.println("2) Crear nuevo usuario");
        System.out.println("3) Salir");

        int eleccion = Input.comprobarEntero();

        switch (eleccion) {
            case 1:
                try {
                    gestor.inicioSesion();
                } catch (MaxIntentosException | UsuarioNoEncontradoException e) {
                    System.out.println("Error al iniciar sesión: " + e.getMessage());
                    System.out.println("---------------------------------------");
                }
                break;

            case 2:
                try {
                    gestor.crearCuenta();
                } catch (MaxIntentosException e) {
                    System.out.println("Error al crear cuenta: " + e.getMessage());
                    System.out.println("---------------------------------------");
                }
                break;

            case 3:
                fin = true;
                break;

            default:
                throw new OpcionNoDisponibleException();

        }
    }

    private static void menuPrincipal() throws OpcionNoDisponibleException, NumberFormatException {
        System.out.println("MENU PRINCIPAL");
        ArrayList<Permiso> permisosActivos = gestor.getUsuarioActivo().getRol().getPermisos();
        for (int i = 0; i < (permisosActivos.size()); i++) {
            System.out.println((i + 1) + permisosActivos.get(i).getCabeceraAcceso());
            permisosActivos.get(i).setGestor(gestor);
        }
        int Acceso = permisosActivos.size();
        System.out.println((Acceso + 1) + ") Cerrar sesión");
        System.out.println((Acceso + 2) + ") Salir");

        int eleccion = Input.comprobarEntero();

        if (eleccion >= 1 && eleccion <= permisosActivos.size()) {
            permisosActivos.get(eleccion - 1).accesoMenu();
        } else if (eleccion == Acceso + 1) {
            gestor.delUsuarioActivo(); // Cerrar sesión
        } else if (eleccion == Acceso + 2) {
            fin = true; // Salir
        } else {
            throw new OpcionNoDisponibleException();
        }
    }

}
/*
 * [] Gestion Permisos y roles
 * [] Archivos
 * [] acabar permisos
 * [] no pueden haber 2 mails iguales quizas excepcion
 * [x] excepcion path Opcion no disponible
 */
