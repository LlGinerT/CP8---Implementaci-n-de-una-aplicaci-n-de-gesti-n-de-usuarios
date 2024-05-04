package Models.Roles;

import Models.Cuentas.GestorCuentas;
import Models.Permisos.Permiso;
import Models.Permisos.PermisoCuentas;
import Models.Permisos.PermisoRoles;

public class Admin extends Rol {

    public Admin(GestorCuentas gestorCuentas, GestorRoles gestorRoles) {
        super("Admin", true, gestorCuentas, gestorRoles);
    }

    @Override
    protected void permisosIniciales() {
        permisos = new Permiso[2];
        permisos[0] = new PermisoCuentas(true, true, gestorCuentas);
        permisos[1] = new PermisoRoles(true, true, gestorRoles);
    }
}
