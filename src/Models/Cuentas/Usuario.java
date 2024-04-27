package Models.Cuentas;

import Models.Roles.Rol;

class Usuario {

    private String nombre;
    private String apellido;
    private String email;
    private String contrasenyaCodificada;
    private byte[] salt;
    private Rol rol;

    public Usuario(Rol rol) {
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenyaCodificada() {
        return contrasenyaCodificada;
    }

    public void setContrasenyaCodificada(String contraseña) {
        this.contrasenyaCodificada = contraseña;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}