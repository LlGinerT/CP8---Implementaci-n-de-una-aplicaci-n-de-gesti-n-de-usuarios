
import Excepciones.MaxIntentosException;
import Excepciones.OpcionNoDisponibleException;
import Excepciones.UsuarioNoEncontradoException;
import Models.GestorAbstracto;
import Models.Cuentas.GestorCuentas;
import Models.Permisos.Permiso;
import Utils.Input;

public class App {

    static boolean fin = false;
    static GestorCuentas gestorCuentas = new GestorCuentas();
    @SuppressWarnings("rawtypes")
    static GestorAbstracto[] gestores = { gestorCuentas, gestorCuentas.getGestorRoles() };

    public static void main(String[] args) throws Exception {
        System.out.println("BIENVENIDO");
        while (!fin) {
            while (!fin && gestorCuentas.getUsuarioActivo() == null) {
                try {
                    menuInicial();
                } catch (OpcionNoDisponibleException | NumberFormatException e) {
                    System.out.println(e.getMessage() + ": Elija una opción valida");
                    System.out.println("-----------------------------------------");
                }
            }
            while (!fin && gestorCuentas.getUsuarioActivo() != null) {
                try {
                    menuPrincipal();
                } catch (OpcionNoDisponibleException | NumberFormatException e) {
                    System.out.println(e.getMessage() + ": Elija una opción valida");
                    System.out.println("-----------------------------------------");
                }
            }
        }
    }

    /**
     * Menu inicial que se muestra hasta que se inicia sesión.
     * 
     * @throws OpcionNoDisponibleException
     * @throws NumberFormatException
     */
    private static void menuInicial() throws OpcionNoDisponibleException, NumberFormatException {
        System.out.println("------------------");
        System.out.println("1) Iniciar Sesión");
        System.out.println("2) Crear nuevo usuario");
        System.out.println("3) Salir");

        int eleccion = Input.comprobarEntero();

        switch (eleccion) {
            case 1:
                try {
                    gestorCuentas.inicioSesion();
                } catch (MaxIntentosException | UsuarioNoEncontradoException e) {
                    System.out.println("Error al iniciar sesión: " + e.getMessage());
                    System.out.println("---------------------------------------");
                }
                break;

            case 2:
                try {
                    gestorCuentas.crearCuenta();
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

    /**
     * Menu principal del usuario, muestra las opciones dinamicamente según los
     * permisos que tenga el usuario "Activo"
     * 
     * @throws OpcionNoDisponibleException
     * @throws NumberFormatException
     */
    @SuppressWarnings("rawtypes")
    private static void menuPrincipal() throws OpcionNoDisponibleException, NumberFormatException {
        int contadorIndices = 0;
        System.out.println("------------------");
        System.out.println("MENU PRINCIPAL");
        System.out.println("------------------");
        Permiso[] permisosUsuarioActivo = gestorCuentas.getUsuarioActivo().getRol().getPermisos();
        /*
         * Imprimimos la cabecera del nombre del menu de cada permiso y a su vez,
         * vinculamos cada permiso
         * con su gestor correspondiente para poder acceder a su menu.
         * PD: Creo que aquí me he liado un poco y estoy seguro que hay alguna forma mas
         * sencilla de hacerlo.
         */
        for (int i = 0; i < permisosUsuarioActivo.length; i++) {
            Permiso permisoActivo = permisosUsuarioActivo[i];
            if (permisoActivo.getLectura()) {
                System.out.println((i + 1) + ") " + permisoActivo.getNombreMenu());
                contadorIndices += 1;
                // Sumando 1 por cada permiso nos permite mover dinamicamente las opciones
                // 'cerrar sesión' y 'salir' según el numero de permisos
                for (GestorAbstracto gestor : gestores) {
                    if (permisoActivo.getClass().getSimpleName().equals(gestor.getNombrePermiso())) {
                        permisosUsuarioActivo[i].setGestor(gestor);
                    }
                }
            }
        }
        System.out.println((contadorIndices + 1) + ") Cerrar sesión");
        System.out.println((contadorIndices + 2) + ") Salir");
        System.out.println("------------------");
        int eleccion = (Input.comprobarEntero());
        System.out.println("------------------");

        if (eleccion >= 1 && eleccion <= permisosUsuarioActivo.length) {
            permisosUsuarioActivo[(eleccion - 1)].accesoMenu();
        } else if (eleccion == contadorIndices + 1) {
            System.out.println("------------------");
            System.out.println("Sesión cerrada");
            System.out.println("------------------");
            gestorCuentas.delUsuarioActivo();
        } else if (eleccion == contadorIndices + 2) {
            fin = true; // Salir
        } else {
            throw new OpcionNoDisponibleException();
        }
    }

}
