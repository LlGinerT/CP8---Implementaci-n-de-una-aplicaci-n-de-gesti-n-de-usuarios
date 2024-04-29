import java.util.ArrayList;

import Excepciones.MaxIntentosException;
import Excepciones.OpcionNoDisponibleException;
import Excepciones.UsuarioNoEncontradoException;
import Models.GestorAbstracto;
import Models.Cuentas.GestorCuentas;
import Models.Permisos.GestorPermisos;
import Models.Permisos.Permiso;
import Models.Roles.GestorRoles;
import Models.Roles.Rol;
import Utils.Input;

public class App {

    static boolean fin = false;
    static GestorCuentas gestorCuentas = new GestorCuentas();
    static GestorPermisos gestorPermisos = new GestorPermisos();
    static GestorRoles gestorRoles = new GestorRoles(gestorCuentas, gestorPermisos);
    @SuppressWarnings("rawtypes")
    static ArrayList<GestorAbstracto> gestores = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        gestores.add(gestorCuentas);
        gestores.add(gestorPermisos);
        gestores.add(gestorRoles);
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
                    ActualizarRoles();
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

    @SuppressWarnings("rawtypes")
    private static void menuPrincipal() throws OpcionNoDisponibleException, NumberFormatException {
        System.out.println("MENU PRINCIPAL");
        ArrayList<Permiso> permisosUsuarioActivo = gestorCuentas.getUsuarioActivo().getRol().getPermisos();
        for (int i = 0; i < (permisosUsuarioActivo.size()); i++) {
            Permiso permisoActivo = permisosUsuarioActivo.get(i);
            System.out.println((i + 1) + ") " + permisoActivo.getNombreMenu());
            for (GestorAbstracto gestor : gestores) {
                if (permisoActivo.getClass().getSimpleName().equals(gestor.getNombrePermiso())) {
                    permisosUsuarioActivo.get(i).setGestor(gestor);
                }
            }
        }
        int Acceso = permisosUsuarioActivo.size();
        System.out.println((Acceso + 1) + ") Cerrar sesión");
        System.out.println((Acceso + 2) + ") Salir");

        int eleccion = (Input.comprobarEntero());

        if (eleccion >= 1 && eleccion <= permisosUsuarioActivo.size()) {
            permisosUsuarioActivo.get(eleccion - 1).accesoMenu();
        } else if (eleccion == Acceso + 1) {
            gestorCuentas.delUsuarioActivo(); // Cerrar sesión
        } else if (eleccion == Acceso + 2) {
            fin = true; // Salir
        } else {
            throw new OpcionNoDisponibleException();
        }
    }

    public static void ActualizarRoles() {
        ArrayList<Rol> rolesActualizados = gestorRoles.getLista();
        int contador = 0;
        Rol rolUsuario = gestorCuentas.getUsuarioActivo().getRol();
        while (contador < rolesActualizados.size()) {
            if (rolesActualizados.get(contador).getNombre().equals(rolUsuario.getNombre())) {
                gestorCuentas.getUsuarioActivo().setRol(rolesActualizados.get(contador));
            }
            contador++;
        }
    }

}
/*
 * [x] Gestion Permisos y roles
 * [] Archivos NO
 * [x] acabar permisos
 * [x] no pueden haber 2 mails iguales quizas excepcion
 * [x] excepcion path Opcion no disponible
 * [] asignar regular al crear cuenta
 * [] No poder eliminar Admin, ni basico, y meter eliminar rol en modificar rol
 * y cambiar a gestionar roles
 * [] refactorizar
 */
