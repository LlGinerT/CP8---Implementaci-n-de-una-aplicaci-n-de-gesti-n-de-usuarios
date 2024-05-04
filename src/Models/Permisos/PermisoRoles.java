package Models.Permisos;

import Models.Roles.GestorRoles;

public class PermisoRoles extends Permiso {

    public PermisoRoles(boolean lectura, boolean escritura, GestorRoles gestor) {
        super(lectura, escritura);
        this.gestor = gestor;
        this.nombreMenu = "Gestión de roles";
        this.nombre = "Roles";
    }

    public PermisoRoles() {

    }

}
