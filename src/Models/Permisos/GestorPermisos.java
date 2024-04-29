package Models.Permisos;

import java.util.ArrayList;

import Excepciones.NoPermisoException;
import Excepciones.OpcionNoDisponibleException;
import Models.GestorAbstracto;

public class GestorPermisos extends GestorAbstracto<Permiso> {

    protected GestorPermisos() {
        super("PermisoRoles");
    }

    @Override
    public ArrayList<Permiso> getLista() {
        return lista;
    }

    @Override
    public void menu() throws NoPermisoException, OpcionNoDisponibleException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'menu'");
    }

}
