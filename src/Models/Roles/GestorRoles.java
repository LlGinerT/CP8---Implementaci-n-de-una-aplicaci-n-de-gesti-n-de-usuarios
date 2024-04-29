package Models.Roles;

import java.util.ArrayList;

import Excepciones.NoPermisoException;
import Excepciones.OpcionNoDisponibleException;
import Models.GestorAbstracto;
import Models.Cuentas.GestorCuentas;

public class GestorRoles extends GestorAbstracto<Rol> {

    public GestorRoles(GestorCuentas gestor) {
        super("PermisoRoles");
        this.gestor = gestor;
        lista = new ArrayList<>();
        lista.add(new Admin());
        lista.add(new Privilegiado());
        lista.add(new Estandar());
    }

    @Override
    public void menu() throws NoPermisoException, OpcionNoDisponibleException {
        boolean atras = false;
        System.out.println("ADMINISTRAR ROLES");
        while (!atras) {

            System.out.println("-------------------");

        }
    }
}
