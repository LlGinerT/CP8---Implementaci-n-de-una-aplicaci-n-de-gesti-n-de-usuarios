package Models.Permisos;

import Excepciones.NoPermisoException;
import Excepciones.OpcionNoDisponibleException;
import Models.GestorAbstracto;

public abstract class Permiso {
    protected String nombreMenu;
    protected String nombre;
    protected boolean escritura;
    @SuppressWarnings("rawtypes")
    protected GestorAbstracto gestor;

    public Permiso(boolean escritura) {
        this.escritura = escritura;

    }

    public boolean getEscritura() {
        return escritura;
    }

    public void setEscritura(boolean escritura) {
        this.escritura = escritura;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNombreMenu() {
        return nombreMenu;
    }

    @SuppressWarnings("rawtypes")
    public void setGestor(GestorAbstracto gestor) {
        this.gestor = gestor;
    }

    public void accesoMenu() {
        try {
            gestor.menu();
        } catch (NoPermisoException | OpcionNoDisponibleException | NumberFormatException e) {
            System.out.println(e.getMessage());
            System.out.println("-------------------");
        }
    }
}
