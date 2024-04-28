package Models.Permisos;

public abstract class LecturaEscritura implements Permiso {
    protected String cabeceraPermiso;
    protected boolean lectura;
    protected boolean escritura;

    public LecturaEscritura(boolean lectura, boolean escritura) {
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

    @Override
    public String getCabeceraPermiso() {
        return cabeceraPermiso;
    }

    @Override
    public abstract boolean menuPermiso();
}
