package Models.Permisos;

import Models.Cuentas.GestorCuentas;

public abstract class Permiso {
    protected String cabeceraAcceso;
    protected boolean lectura;
    protected boolean escritura;
    protected GestorCuentas gestor;

    public Permiso(boolean lectura, boolean escritura) {
        this.lectura = lectura;
        this.escritura = escritura;

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

    public String getCabeceraAcceso() {
        return cabeceraAcceso;
    }

    public void setGestor(GestorCuentas gestor) {
        this.gestor = gestor;
    }

    public abstract void accesoMenu();
}
