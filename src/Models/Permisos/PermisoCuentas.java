package Models.Permisos;

public class PermisoCuentas extends Permiso {

    public PermisoCuentas(boolean escritura) {
        super(escritura);
        this.nombreMenu = "Gestión de cuentas";
        this.nombre = "Cuentas";
    }

}
