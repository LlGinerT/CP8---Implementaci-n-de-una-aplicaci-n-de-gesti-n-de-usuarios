package Models.Permisos;

public class PermisoTransacciones extends Permiso {

    public PermisoTransacciones(boolean escritura) {
        super(escritura);
        this.nombreMenu = "Transacciones";
    }

}
