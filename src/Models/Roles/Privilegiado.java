package Models.Roles;

import Models.Cuentas.GestorCuentas;
import Models.Permisos.Permiso;
import Models.Permisos.PermisoCuentas;
import Models.Permisos.PermisoRoles;

public class Privilegiado extends Rol {

    public Privilegiado(GestorCuentas gestorCuentas, GestorRoles gestorRoles) {
        super("Privilegiado", false, gestorCuentas, gestorRoles);
    }

    @Override
    protected void permisosIniciales() {
        permisos = new Permiso[2];
        permisos[0] = new PermisoCuentas(true, false, gestorCuentas);
        permisos[1] = new PermisoRoles(true, false, gestorRoles);
    }
}
