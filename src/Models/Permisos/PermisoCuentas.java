package Models.Permisos;

public class PermisoCuentas extends Permiso {

    public PermisoCuentas(boolean lectura, boolean escritura) {
        super(lectura, escritura);
        this.cabeceraAcceso = ") Gestion de cuentas";
    }

}
