package Models.Roles;

import Models.Cuentas.GestorCuentas;

public class NuevoRol extends Rol {

    public NuevoRol(String nombre, GestorCuentas gestorCuentas, GestorRoles gestorRoles) {
        super(nombre, false, gestorCuentas, gestorRoles);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void permisosIniciales() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'permisosIniciales'");
    }

}
