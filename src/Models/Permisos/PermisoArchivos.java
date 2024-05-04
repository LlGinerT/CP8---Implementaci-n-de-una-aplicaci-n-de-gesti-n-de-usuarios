package Models.Permisos;

public class PermisoArchivos extends Permiso {

    public PermisoArchivos(boolean lectura, boolean escritura) {
        super(lectura, escritura);
        this.nombreMenu = "Transacciones";
    }

}
