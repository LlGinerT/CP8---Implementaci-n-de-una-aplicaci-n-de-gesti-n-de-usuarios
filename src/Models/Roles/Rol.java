package Models.Roles;

import Models.Permisos.Permiso;

/* 
 * Clase abstracta del rol, cada rol tendrá unos permisos iniciales diferentes.
 */
public abstract class Rol {

    private String nombre;
    private Boolean basico;
    protected Permiso[] permisos;

    public Rol(String nombre, boolean basico) {
        this.nombre = nombre;
        this.basico = basico;
        this.permisos = new Permiso[2];
        permisosIniciales();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean getBasico() {
        return basico;
    }

    public Permiso[] getPermisos() {
        return permisos;
    }

    protected abstract void permisosIniciales();

}
