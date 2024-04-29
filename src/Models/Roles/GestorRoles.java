package Models.Roles;

import java.util.ArrayList;

import Excepciones.NoPermisoException;
import Excepciones.OpcionNoDisponibleException;
import Models.GestorAbstracto;
import Models.Cuentas.GestorCuentas;
import Models.Permisos.GestorPermisos;
import Models.Permisos.Permiso;
import Utils.Input;

public class GestorRoles extends GestorAbstracto<Rol> {

    public GestorRoles(GestorCuentas gestorCuentas, GestorPermisos gestorAuxiliar) {
        super("PermisoRoles");
        this.gestorCuentas = gestorCuentas;
        this.gestorPermisos = gestorAuxiliar;
        lista = new ArrayList<>();
        lista.add(new Admin());
        lista.add(new Privilegiado());
        lista.add(new Estandar());
    }

    @Override
    public void menu() throws NoPermisoException, OpcionNoDisponibleException {
        boolean atras = false;
        boolean permiso = tienePermiso();
        while (!atras && permiso) {
            System.out.println("ADMINISTRAR ROLES");
            if (!permisoEscritura) {
                System.out.println("-------------------:");
                System.out.println("1) Mostrar Roles");
                System.out.println("2) atrás");
            }
            if (permisoEscritura) {
                System.out.println("-------------------:");
                System.out.println("1) Mostrar Roles");
                System.out.println("2) Modificar Roles");
                System.out.println("3) Eliminar Rol");
                System.out.println("4) atrás");
            }
            int eleccion = Input.comprobarEntero();
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
                    atras = true;
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
                System.out.println("   -Permiso de escritura: " + permiso.getEscritura());
                System.out.println("---------------------------------------");
            }
        }
    }

    public void modificarRol() throws OpcionNoDisponibleException {
        boolean atras = false;
        System.out.println("¿Que rol quieres modificar?");
        String inputUsuario = Input.scanner();
        Rol tempRol = null;
        int contador = 0;
        while (tempRol == null && contador < lista.size()) {
            Rol rolComparar = lista.get(contador);
            if (rolComparar.getNombre().equalsIgnoreCase(inputUsuario)) {
                tempRol = rolComparar;
            }
            contador++;
        }
        if (tempRol == null) {
            System.out.println("El rol no existe;");
            atras = true;
        }
        if (tempRol != null) {
            System.out.println("-------------------");
            System.out.println("MODIFICAR ROL: ");
            System.out.println(tempRol.getNombre());
            System.out.println("-------------------");
            while (!atras) {
                System.out.println("1) Cambiar nombre");
                System.out.println("2) Gestionar permisos");
                System.out.println("3) atrás");
                int eleccion = Input.comprobarEntero();
                switch (eleccion) {
                    case 1:
                        String nuevoNombre = Input.comprobarSoloLetras();
                        tempRol.setNombre(nuevoNombre);
                        System.out.println("Nombre cambiado");
                        break;

                    case 2:
                        gestionarPermisos(tempRol);
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

    public void eliminarRol() {
        boolean atras = false;
        System.out.println("¿Que rol quieres eliminar?");
        String inputUsuario = Input.scanner();
        Rol tempRol = null;
        int contador = 0;
        while (tempRol == null && contador < lista.size()) {
            Rol rolComparar = lista.get(contador);
            if (rolComparar.getNombre().equalsIgnoreCase(inputUsuario)) {
                tempRol = rolComparar;
            }
            contador++;
        }
        if (tempRol == null) {
            System.out.println("El rol no existe;");
            atras = true;
        }
        while (!atras && tempRol != null) {
            System.out.println("¿Seguro que quieres eliminar " + tempRol.getNombre() + "(S/N)");
            String continuar = Input.comprobarSoloLetras();
            if (continuar.equalsIgnoreCase("s")) {
                System.out.println("Se ha eliminado el rol" + tempRol.getNombre() + "correctamente");
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

    public void gestionarPermisos(Rol rol) throws OpcionNoDisponibleException {
        boolean atras = false;
        for (Permiso permiso : rol.getPermisos()) {
            System.out.println("   -Permiso: " + permiso.getNombre());
            System.out.println("   -Permiso de escritura: " + permiso.getEscritura());
            System.out.println("---------------------------------------");
        }
        while (!atras) {
            System.out.println("1) Añadir permiso");
            System.out.println("2) Modificar permiso");
            System.out.println("3) Atrás");
            int eleccion = Input.comprobarEntero();
            System.out.println("---------------------------------------");
            switch (eleccion) {
                case 1:
                    for (Permiso permiso : rol.getPermisos()) {
                        System.out.println("   -Permiso: " + permiso.getNombre());
                        System.out.println("   -Permiso de escritura: " + permiso.getEscritura());
                        System.out.println("---------------------------------------");
                    }
                    System.out.println("¿Que permiso quieres añadir?");
                    String inputUsuario = Input.scanner();
                    Permiso tempPermiso = null;
                    int contador = 0;
                    while (tempPermiso == null && contador < gestorPermisos.getLista().size()) {
                        Permiso permisoComparar = gestorPermisos.getLista().get(contador);
                        if (permisoComparar.getNombre().equalsIgnoreCase(inputUsuario)
                                && !rol.getPermisos().contains(permisoComparar)) {
                            tempPermiso = gestorPermisos.getLista().get(contador);
                            System.out.println(tempPermiso.getNombre() + " añadido correctamente");
                        }
                        contador++;
                        if (tempPermiso == null && rol.getPermisos().contains(permisoComparar)) {
                            System.out.println("El rol ya tiene ese permiso");
                            break;
                        }
                    }
                    if (tempPermiso != null) {
                        rol.getPermisos().add(tempPermiso);
                    } else {
                        System.out.println("No se encuentra el permiso");
                    }
                    break;

                case 2:
                    for (Permiso permiso : rol.getPermisos()) {
                        System.out.println("   -Permiso: " + permiso.getNombre());
                        System.out.println("   -Permiso de escritura: " + permiso.getEscritura());
                        System.out.println("---------------------------------------");
                    }
                    System.out.println("¿Que permiso quieres modificar?");
                    inputUsuario = Input.scanner();
                    tempPermiso = null;
                    contador = 0;
                    while (tempPermiso == null && contador < rol.getPermisos().size()) {
                        Permiso permisoComparar = rol.getPermisos().get(contador);
                        if (permisoComparar.getNombre().equalsIgnoreCase(inputUsuario)) {
                            tempPermiso = permisoComparar;
                        }
                        contador++;
                    }
                    if (tempPermiso == null) {
                        System.out.println("No se encuentra el permiso");
                    }
                    modificarPermisos(tempPermiso, rol);
                    break;

                case 3:
                    atras = true;
                    break;

                default:
                    throw new OpcionNoDisponibleException();
            }
        }

    }

    public void modificarPermisos(Permiso permiso, Rol rol) throws OpcionNoDisponibleException {
        boolean atras = false;
        System.out.println("   -Permiso: " + permiso.getNombre());
        System.out.println("   -Permiso de escritura: " + permiso.getEscritura());
        System.out.println("---------------------------------------");
        while (!atras) {
            System.out.println("1) Modificar permiso escritura");
            System.out.println("2) Quitar permiso");
            System.out.println("3) Atrás");
            int eleccion = Input.comprobarEntero();
            switch (eleccion) {
                case 1:
                    if (permiso.getEscritura()) {
                        System.out.println("Quitar permiso de escritura en " + permiso.getNombre() + "? (S/N)");
                        String continuar = Input.comprobarSoloLetras();
                        if (continuar.equalsIgnoreCase("s")) {
                            permiso.setEscritura(false);
                            System.out.println("Se ha quitado el permiso de escritura correctamente");
                            atras = true;
                        } else if (continuar.equalsIgnoreCase("n")) {
                            atras = true;
                            break;
                        } else {
                            System.out.println(continuar
                                    + " no es una respuesta valida. Vuelva a introducir una respuesta.");
                        }
                    } else {
                        System.out.println("Añadir permiso de escritura en " + permiso.getNombre() + "? (S/N)");
                        String continuar = Input.comprobarSoloLetras();
                        if (continuar.equalsIgnoreCase("S")) {
                            permiso.setEscritura(false);
                            atras = true;
                            System.out.println("Se ha añadido el permiso de escritura correctamente");
                        } else if (continuar.equalsIgnoreCase("n")) {
                            atras = true;
                            break;
                        } else {
                            System.out.println(continuar
                                    + " no es una respuesta valida. Vuelva a introducir una respuesta.");
                        }
                    }
                    break;

                case 2:
                    System.out.println("¿Seguro que quieres quitar el permiso?(S/N)");
                    String continuar = Input.comprobarSoloLetras();
                    if (continuar.equalsIgnoreCase("S")) {
                        System.out.println("Se ha quitado el permiso " + permiso.getNombre() + " correctamente");
                        rol.getPermisos().remove(permiso);
                        atras = true;
                    } else if (continuar.equalsIgnoreCase("n")) {
                        atras = true;
                        break;
                    } else {
                        System.out.println(continuar
                                + " no es una respuesta valida. Vuelva a introducir una respuesta.");
                    }
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
