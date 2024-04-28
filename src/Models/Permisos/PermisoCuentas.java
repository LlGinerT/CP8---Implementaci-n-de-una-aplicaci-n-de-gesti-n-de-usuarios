package Models.Permisos;

import Excepciones.NoPersmisoException;
import Excepciones.UsuarioNoEncontradoException;

public class PermisoCuentas extends Permiso {

    public PermisoCuentas(boolean lectura, boolean escritura) {
        super(lectura, escritura);
        this.cabeceraAcceso = ") Gestion de cuentas";
    }

    public void accesoMenu() {
        try {
            gestor.menuUsuarios();
        } catch (NoPersmisoException e) {
            System.out.println(e);
        } catch (UsuarioNoEncontradoException e) {
            System.out.println(e);
        }
    }
}
