package Models.Cuentas;

import Models.Roles.Rol;
import Utils.EncoderContrasenyas;

public class Usuario {

    private String nombre;
    private String apellido;
    private String email;
    private Rol rol;
    private String contrasenyaCodificada;
    private byte[] salt;

    public Usuario(String nombre, String apellido, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;

    }

    public Usuario(String nombre, String apellido, String email, Rol rol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
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

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getContrasenyaCodificada() {
        return contrasenyaCodificada;
    }

    public void setContrasenyaCodificada(String contrasenya) {
        this.salt = EncoderContrasenyas.generateSalt();
        this.contrasenyaCodificada = EncoderContrasenyas.encodeContrasenya(contrasenya, salt);
    }

    public byte[] getSalt() {
        return salt;
    }

    public void reiniciarContrasenya() {
        this.contrasenyaCodificada = null;
        this.salt = null;
    }
}