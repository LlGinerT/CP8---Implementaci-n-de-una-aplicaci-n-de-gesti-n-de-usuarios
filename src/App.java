import Excepciones.MaxIntentosException;
import Excepciones.NoPersmisoException;
import Models.Cuentas.GestorCuentas;
import Models.Cuentas.Usuario;
import Utils.Input;

public class App {
    static Usuario sesionIniciada;
    static boolean fin = false;
    static GestorCuentas gestor = new GestorCuentas();

    public static void main(String[] args) throws Exception {
        System.out.println("Bienvenido");
        System.out.println("-----------");
        while (!fin && sesionIniciada != null) {
            fin = menuInicial();
        }
        while (!fin) {
            fin = menuInicial();
        }
    }

    private static boolean menuInicial() {
        System.out.println("1) Iniciar Sesión");
        System.out.println("2) Crear nuevo usuario");
        System.out.println("3) Salir");

        int eleccion = Input.comprobarEntero(Input.scanner());

        switch (eleccion) {
            case 1:
                try {
                    sesionIniciada = gestor.inicioSesion();
                } catch (MaxIntentosException e) {
                    System.out.println("Error al iniciar sesión: " + e.getMessage());
                }
                break;

            case 2:
                try {
                    gestor.crearCuenta();
                } catch (MaxIntentosException e) {
                    System.out.println("Error al crear cuenta: " + e.getMessage());
                }
                break;
            case 3:
                try {
                    gestor.gestionUsuarios();
                } catch (NoPersmisoException e) {
                    System.out.println(e);
                }
                break;
            case 4:
                fin = true;
                break;

            default:
                System.out.println("Opción no disponible");
                break;
        }
        return fin;
    }

}
