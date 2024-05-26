package Models;

import java.util.ArrayList;

import Excepciones.NoPermisoException;
import Excepciones.OpcionNoDisponibleException;
import Models.Cuentas.GestorCuentas;
import Models.Permisos.GestorPermisos;
import Models.Permisos.Permiso;

/**
 * Clase abstracta de la que heredan los diferentes gestores.
 * cuenta con una lista que se adapta a cada tipo de gestor.
 */
public abstract class GestorAbstracto<T> {

    protected boolean permisoLectura;
    protected boolean permisoEscritura;
    protected String nombrePermiso;
    protected GestorCuentas gestorCuentas;
    protected GestorPermisos gestorPermisos;
    protected ArrayList<T> lista;

    protected GestorAbstracto(String nombrePermiso) {

        this.nombrePermiso = nombrePermiso;
        this.lista = new ArrayList<T>();
    }

    public ArrayList<T> getLista() {
        return lista;
    }

    public String getNombrePermiso() {
        return nombrePermiso;
    }

    public abstract void menu() throws OpcionNoDisponibleException, NoPermisoException, NumberFormatException;

    /**
     * Método para comprobar si un rol tiene un permiso en especifico para leer y
     * modificar el gestor en especifico.
     * 
     * @return Retorna el permiso de lectura
     * @throws NoPermisoException
     */
    protected Boolean tienePermiso() throws NoPermisoException {
        int contador = 0;
        while (!permisoLectura && contador < gestorCuentas.getUsuarioActivo().getRol().getPermisos().length) {
            Permiso permisoUsuario = gestorCuentas.getUsuarioActivo().getRol().getPermisos()[contador];
            if (permisoUsuario.getClass().getSimpleName().equals(nombrePermiso)) {
                permisoLectura = permisoUsuario.getLectura();
                permisoEscritura = permisoUsuario.getEscritura();
            }
            contador++;
        }
        if (!permisoLectura) {
            throw new NoPermisoException();
        }
        return permisoLectura;
    }

}
