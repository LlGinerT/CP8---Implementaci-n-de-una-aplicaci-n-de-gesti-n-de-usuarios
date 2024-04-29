package Models;

import java.util.ArrayList;

import Excepciones.NoPermisoException;
import Excepciones.OpcionNoDisponibleException;
import Models.Cuentas.GestorCuentas;
import Models.Permisos.Permiso;

public abstract class GestorAbstracto<T> {
    protected boolean permisoLectura;
    protected boolean permisoEscritura;
    protected String nombrePermiso;
    protected GestorCuentas gestor;
    protected ArrayList<T> lista;

    protected GestorAbstracto(String nombrePermiso) {
        this.permisoLectura = false;
        this.permisoEscritura = false;
        this.nombrePermiso = nombrePermiso;
        this.lista = new ArrayList<T>();
    }

    public ArrayList<T> getLista() {
        return lista;
    }

    protected Boolean tienePermiso() throws NoPermisoException {
        int contador = 0;
        while (!permisoLectura && contador < gestor.getUsuarioActivo().getRol().getPermisos().size()) {
            Permiso permisoUsuario = gestor.getUsuarioActivo().getRol().getPermisos().get(contador);
            if (permisoUsuario.getClass().getSimpleName().equals(nombrePermiso)) {
                permisoLectura = true;
                permisoEscritura = permisoUsuario.getEscritura();
            } else {
                throw new NoPermisoException();
            }
            contador++;
        }
        return permisoLectura;
    }

    public abstract void menu() throws OpcionNoDisponibleException, NoPermisoException, NumberFormatException;
}
