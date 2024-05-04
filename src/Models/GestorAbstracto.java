package Models;

import java.util.HashSet;

import Excepciones.NoPermisoException;
import Excepciones.OpcionNoDisponibleException;
import Models.Cuentas.GestorCuentas;
import Models.Permisos.GestorPermisos;
import Models.Permisos.Permiso;

public abstract class GestorAbstracto<T> {

    protected boolean permisoLectura;
    protected boolean permisoEscritura;
    protected Permiso permisoNecesario;
    protected GestorCuentas gestorCuentas;
    protected GestorPermisos gestorPermisos;
    protected HashSet<T> lista;

    protected GestorAbstracto() {

    }

    protected GestorAbstracto(Permiso permiso) {
        this.permisoNecesario = permiso;
        this.lista = new HashSet<T>();
    }

    public HashSet<T> getLista() {
        return lista;
    }

    public Permiso getPermisoNecesario() {
        return permisoNecesario;
    }

    public abstract void menu() throws OpcionNoDisponibleException, NoPermisoException, NumberFormatException;

    protected Boolean tienePermiso() throws NoPermisoException {
        int contador = 0;
        while (!permisoLectura && contador < gestorCuentas.getUsuarioActivo().getRol().getPermisos().length) {
            Permiso permisoUsuario = gestorCuentas.getUsuarioActivo().getRol().getPermisos()[contador];
            if (permisoUsuario.getClass().getSimpleName().equals(permisoNecesario)) {
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

}
