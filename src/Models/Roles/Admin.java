package Models.Roles;

import java.util.ArrayList;

import Models.Permisos.PermisoCuentas;
import Models.Permisos.PermisoRoles;

public class Admin extends Rol {

    public Admin() {
        super("Admin");
    }

    @Override
    protected void permisosIniciales() {
        permisos = new ArrayList<>();
        permisos.add(new PermisoCuentas(true));
        permisos.add(new PermisoRoles(true));
    }
}
