package Models.Roles;

import Models.Permisos.PermisoCuentas;

public class Estandar extends Rol {

    public Estandar() {
        super("Estándar");
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void permisosIniciales() {
        permisos.add(new PermisoCuentas(false));
    }

}
