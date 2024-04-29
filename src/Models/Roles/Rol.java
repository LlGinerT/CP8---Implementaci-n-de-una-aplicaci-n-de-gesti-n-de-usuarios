package Models.Roles;

import java.util.ArrayList;

import Models.Permisos.Permiso;

public abstract class Rol {

    private String nombre;
    protected ArrayList<Permiso> permisos;

    public Rol(String nombre) {
        this.nombre = nombre;
        this.permisos = new ArrayList<>();
        permisosIniciales();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Permiso> getPermisos() {
        return permisos;
    }

    protected abstract void permisosIniciales();

}
