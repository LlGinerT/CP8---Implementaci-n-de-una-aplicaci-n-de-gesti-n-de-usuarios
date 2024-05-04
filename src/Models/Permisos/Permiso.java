package Models.Permisos;

import java.util.Objects;

import Excepciones.NoPermisoException;
import Excepciones.OpcionNoDisponibleException;
import Models.GestorAbstracto;

public abstract class Permiso {
    protected String nombreMenu;
    protected String nombre;
    protected boolean lectura;
    protected boolean escritura;
    protected boolean basico = false;
    @SuppressWarnings("rawtypes")
    protected GestorAbstracto gestor;

    public Permiso(boolean lectura, boolean escritura) {
        this.lectura = lectura;
        this.escritura = escritura;
    }

    public Permiso() {

    }

    public boolean getBasico() {
        return basico;
    }

    public boolean getLectura() {
        return lectura;
    }

    public void setLectura(boolean lectura) {
        this.lectura = lectura;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Permiso other = (Permiso) obj;
        return this.getClass().getSimpleName().equals(other.getClass().getSimpleName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass().getSimpleName());
    }

}
