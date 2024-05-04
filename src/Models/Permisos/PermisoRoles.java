package Models.Permisos;

public class PermisoRoles extends Permiso {

    public PermisoRoles(boolean lectura, boolean escritura) {
        super(lectura, escritura);
        this.nombreMenu = "Gestión de roles";
        this.nombre = "Roles";
    }

}
