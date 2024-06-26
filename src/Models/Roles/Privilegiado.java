package Models.Roles;

import Models.Permisos.Permiso;
import Models.Permisos.PermisoCuentas;
import Models.Permisos.PermisoRoles;

public class Privilegiado extends Rol {

    public Privilegiado() {
        super("Privilegiado", false);
    }

    @Override
    protected void permisosIniciales() {
        permisos = new Permiso[2];
        permisos[0] = new PermisoCuentas(true, false);
        permisos[1] = new PermisoRoles(true, false);
    }
}
