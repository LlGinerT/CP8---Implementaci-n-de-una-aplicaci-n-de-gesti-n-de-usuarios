import Models.Cuentas.GestorCuentas;
import Utils.Input;

public class App {
    static boolean sesionIniciada = false;

    public static void main(String[] args) throws Exception {
        boolean fin = false;
        System.out.println("Bienvenido");
        System.out.println("-----------");
        while (!fin && !sesionIniciada) {
            menuInicial();
        }
    }

    private static boolean menuInicial() {
        GestorCuentas gestor = new GestorCuentas();
        boolean fin = false;

        System.out.println("1) Iniciar Sesión");
        System.out.println("2) Crear nuevo usuario");
        System.out.println("3) Salir");

        int eleccion = Input.comprobarEntero(Input.inputUsuario());

        switch (eleccion) {
            case 1:
                sesionIniciada = gestor.inicioSesion();
                break;

            case 2:
                break;

            case 3:
                fin = true;
                break;

            default:
                System.out.println("Opción no valida");
                break;
        }
        return fin;
    }

    public static void setSesionIniciada(boolean inicioSesion) {
        sesionIniciada = inicioSesion;
    }
}
