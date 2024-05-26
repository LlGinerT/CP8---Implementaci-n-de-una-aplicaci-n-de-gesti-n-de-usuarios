package Models.Roles;

import Models.Permisos.Permiso;
import Models.Permisos.PermisoCuentas;
import Models.Permisos.PermisoRoles;

/* 
 * Rol básico, no se puede eliminar.
 */
public class Regular extends Rol {

    public Regular() {
        super("regular", true);

    }

    @Override
    protected void permisosIniciales() {
        permisos = new Permiso[2];
        permisos[0] = new PermisoCuentas(true, false);
        permisos[1] = new PermisoRoles(false, false);
    }

}
