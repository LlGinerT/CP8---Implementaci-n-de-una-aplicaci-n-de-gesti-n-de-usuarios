package Models.Permisos;

public class PermisoRoles extends Permiso {

    public PermisoRoles(boolean escritura) {
        super(escritura);
        this.nombreMenu = "Gestión de roles";
        this.nombre = "Roles";
    }

}
