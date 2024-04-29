package Models.Roles;

import Models.Permisos.PermisoCuentas;

public class Privilegiado extends Rol {

    public Privilegiado() {
        super("Privilegiado");
    }

    @Override
    protected void permisosIniciales() {
        permisos.add(new PermisoCuentas(false));
    }
}
