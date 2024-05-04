package Models.Permisos;

import Models.Cuentas.GestorCuentas;

public class PermisoCuentas extends Permiso {

    public PermisoCuentas(boolean lectura, boolean escritura, GestorCuentas gestor) {
        super(lectura, escritura);
        this.gestor = gestor;
        this.nombre = "Cuentas";
        this.basico = true;
    }

    public PermisoCuentas() {

    }

}
