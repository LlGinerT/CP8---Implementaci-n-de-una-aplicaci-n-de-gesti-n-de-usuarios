package Models.Roles;

import Models.Permisos.Permiso;
import Models.Permisos.PermisoCuentas;
import Models.Permisos.PermisoRoles;

public class Admin extends Rol {

    public Admin() {
        super("Admin", true);
    }

    @Override
    protected void permisosIniciales() {
        permisos = new Permiso[2];
        permisos[0] = new PermisoCuentas(true, true);
        permisos[1] = new PermisoRoles(true, true);
    }
}
