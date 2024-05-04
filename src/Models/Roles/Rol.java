package Models.Roles;

import Models.Cuentas.GestorCuentas;

import Models.Permisos.Permiso;

public abstract class Rol {

    private String nombre;
    private Boolean basico;
    protected Permiso[] permisos;
    protected GestorCuentas gestorCuentas;
    protected GestorRoles gestorRoles;

    public Rol(String nombre, boolean basico, GestorCuentas gestorCuentas, GestorRoles gestorRoles) {
        this.gestorCuentas = gestorCuentas;
        this.gestorRoles = gestorRoles;
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
