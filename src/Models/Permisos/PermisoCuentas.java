package Models.Permisos;

public class PermisoCuentas extends Permiso {

    public PermisoCuentas(boolean lectura, boolean escritura) {
        super(lectura, escritura);
        this.nombreMenu = "Gestión de cuentas";
        this.nombre = "Cuentas";
        this.basico = true;
    }

}
