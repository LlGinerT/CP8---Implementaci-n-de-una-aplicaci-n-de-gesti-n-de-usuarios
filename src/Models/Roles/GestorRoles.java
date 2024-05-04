package Models.Roles;

import java.util.HashSet;

import Excepciones.NoPermisoException;
import Excepciones.OpcionNoDisponibleException;
import Models.GestorAbstracto;
import Models.Cuentas.GestorCuentas;
import Models.Cuentas.Usuario;
import Models.Permisos.GestorPermisos;
import Models.Permisos.Permiso;
import Models.Permisos.PermisoCuentas;
import Utils.Input;

public class GestorRoles extends GestorAbstracto<Rol> {

    public GestorRoles(GestorCuentas gestorCuentas) {
        super(new PermisoCuentas());
        this.gestorCuentas = gestorCuentas;
        this.gestorPermisos = new GestorPermisos();
        lista = new HashSet<>();
        lista.add(new Admin(gestorCuentas, this));
        lista.add(new Privilegiado(gestorCuentas, this));
        lista.add(new Regular(gestorCuentas, this));
    }

    @Override
    public void menu() throws NoPermisoException, OpcionNoDisponibleException, NumberFormatException {
        boolean atras = false;
        while (!atras && tienePermiso()) {
            System.out.println("ADMINISTRAR ROLES");
            if (!permisoEscritura) {
                System.out.println("-------------------:");
                System.out.println("1) Mostrar Roles");
                System.out.println("2) atrás");
                System.out.println("-------------------:");
            }
            if (permisoEscritura) {
                System.out.println("-------------------:");
                System.out.println("1) Mostrar Roles");
                System.out.println("2) Modificar Roles");
                System.out.println("3) Eliminar Rol");
                System.out.println("4) atrás");
                System.out.println("-------------------:");
            }
            int eleccion = Input.comprobarEntero();
            System.out.println("-------------------:");
            switch (eleccion) {
                case 1:
                    mostrarRoles();
                    break;
                case 2:
                    if (permisoEscritura) {
                        modificarRol();
                    } else {
                        atras = true;
                    }
                    break;
                case 3:
                    if (permisoEscritura) {
                        eliminarRol();
                    } else {
                        throw new OpcionNoDisponibleException();
                    }
                    break;

                case 4:
                    if (permisoEscritura) {
                        atras = true;
                    } else {
                        throw new OpcionNoDisponibleException();
                    }
                    break;
                default:
                    throw new OpcionNoDisponibleException();

            }
        }
    }

    public void mostrarRoles() {
        System.out.println("---------------------------------------");
        for (Rol rol : lista) {
            System.out.println("Rol: " + rol.getNombre());
            System.out.println("Permisos: ");
            for (Permiso permiso : rol.getPermisos()) {
                System.out.println("   -Permiso: " + permiso.getNombre());
                System.out.println("   -Permiso de lectura: " + permiso.getLectura());
                System.out.println("   -Permiso de escritura: " + permiso.getEscritura());
                System.out.println("---------------------------------------");
            }
        }
    }

    public void modificarRol() throws OpcionNoDisponibleException, NumberFormatException, NoPermisoException {
        boolean atras = false;
        System.out.println("¿Que rol quieres modificar?");
        String inputUsuario = Input.scanner();
        Rol tempRol = buscarRol(inputUsuario);
        if (tempRol == null) {
            System.out.println("-------------------");
            System.out.println("El rol no existe;");
            System.out.println("-------------------");
            atras = true;
        } else if (tempRol.getBasico()) {
            System.out.println("-------------------");
            System.out.println("Este rol no se puede modificar.");
            System.out.println("-------------------");
        } else if (tempRol != null) {
            System.out.println("-------------------");
            System.out.println("MODIFICAR ROL: ");
            System.out.println(tempRol.getNombre());
            System.out.println("-------------------");
            while (!atras && tienePermiso()) {
                System.out.println("1) Cambiar nombre");
                System.out.println("2) Modificar permisos");
                System.out.println("3) atrás");
                System.out.println("-------------------");
                int eleccion = Input.comprobarEntero();
                System.out.println("-------------------");
                switch (eleccion) {
                    case 1:
                        String nuevoNombre = Input.comprobarSoloLetras();
                        tempRol.setNombre(nuevoNombre);
                        System.out.println("Nombre cambiado");
                        System.out.println("-------------------");
                        break;

                    case 2:
                        gestorPermisos.menu(tempRol);
                        break;

                    case 3:
                        atras = true;
                        break;

                    default:
                        throw new OpcionNoDisponibleException();
                }
            }
        }
    }

    public Rol buscarRol(String nombreRol) {
        for (Rol rol : lista) {
            if (rol.getNombre().equalsIgnoreCase(nombreRol)) {
                return rol;
            }
        }
        return null; // Si el rol no se encuentra
    }

    public Rol buscarRol(Class<?> tipo) {
        for (Rol rol : lista) {
            if (rol.getClass().equals(tipo)) {
                return rol;
            }
        }
        return null; // Si el rol no se encuentra
    }

    public void eliminarRol() {
        boolean atras = false;
        System.out.println("-------------------");
        System.out.println("¿Que rol quieres eliminar?");
        System.out.println("-------------------");
        String inputUsuario = Input.scanner();
        Rol tempRol = buscarRol(inputUsuario);
        if (tempRol == null) {
            System.out.println("-------------------");
            System.out.println("El rol no existe");
            System.out.println("-------------------");
            atras = true;
        } else if (!tempRol.getBasico()) {
            System.out.println("-------------------");
            System.out.println("No se puede eliminar este rol");
            System.out.println("-------------------");
            atras = true;
        }
        while (!atras && tempRol != null) {
            System.out.println("¿Seguro que quieres eliminar " + tempRol.getNombre() + "(S/N)");
            String continuar = Input.comprobarSoloLetras();
            if (continuar.equalsIgnoreCase("s")) {
                System.out.println("-------------------");
                System.out.println("Se ha eliminado el rol" + tempRol.getNombre() + "correctamente");
                System.out.println("-------------------");
                for (Usuario cuenta : gestorCuentas.getLista()) {
                    if (cuenta.getRol().equals(tempRol)) {
                        cuenta.setRol(
                                buscarRol("regular"));
                        System.out.println("");
                    }
                }
                lista.remove(tempRol);
                atras = true;
                break;
            } else if (continuar.equalsIgnoreCase("n")) {
                atras = true;
                break;
            } else {
                System.out.println(continuar
                        + " no es una respuesta valida. Vuelva a introducir una respuesta.");
            }
        }
    }
}
