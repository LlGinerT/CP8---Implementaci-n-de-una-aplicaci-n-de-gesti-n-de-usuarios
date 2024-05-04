package Models;

import java.util.ArrayList;

import Excepciones.NoPermisoException;
import Excepciones.OpcionNoDisponibleException;
import Models.Cuentas.GestorCuentas;
import Models.Permisos.GestorPermisos;
import Models.Permisos.Permiso;

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

    protected Boolean tienePermiso() throws NoPermisoException {
        int contador = 0;
        while (!permisoLectura && contador < gestorCuentas.getUsuarioActivo().getRol().getPermisos().length) {
            Permiso permisoUsuario = gestorCuentas.getUsuarioActivo().getRol().getPermisos()[contador];
            if (permisoUsuario.getClass().getSimpleName().equals(nombrePermiso)) {
                permisoLectura = true;
                permisoEscritura = permisoUsuario.getEscritura();
            }
            contador++;
        }
        if (!permisoLectura) {
            throw new NoPermisoException();
        }
        return permisoLectura;
    }

    public abstract void menu() throws OpcionNoDisponibleException, NoPermisoException, NumberFormatException;
}
