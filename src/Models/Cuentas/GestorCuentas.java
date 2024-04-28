package Models.Cuentas;

import java.util.ArrayList;

import Excepciones.MaxIntentosException;
import Excepciones.NoPersmisoException;
import Excepciones.OpcionNoDisponible;
import Excepciones.UsuarioNoEncontradoException;
import Models.Permisos.Permiso;
import Utils.EncoderContrasenyas;
import Utils.Input;

public class GestorCuentas {

    private ArrayList<Usuario> usuariosList;
    private Usuario usuarioActivo;

    public GestorCuentas() {
        this.usuariosList = new ArrayList<>();
    }

    public ArrayList<Usuario> getUsuariosList() {
        return usuariosList;
    }

    public Usuario getUsuarioActivo() {
        return usuarioActivo;
    }

    public Usuario buscarEmail(String email) {
        Usuario usuarioEncontrado = null;
        int contador = 0;
        while (usuarioEncontrado == null && contador < usuariosList.size()) {
            if (usuariosList.get(contador).getEmail().equals(email)) {
                usuarioEncontrado = usuariosList.get(contador);
            }
            contador++;
        }
        return usuarioEncontrado;
    }

    public Usuario buscarUsuario() throws UsuarioNoEncontradoException {
        boolean finBusqueda = false;
        Usuario usuarioEncontrado = null;
        while (!finBusqueda) {
            System.out.println("1) Buscar por Email");
            System.out.println("2) Buscar por Nombre o Apellido");
            System.out.println("3) Atrás");
            int eleccion = Input.comprobarEntero(Input.scanner());
            switch (eleccion) {
                case 1:
                    String email = Input.scanner();
                    usuarioEncontrado = buscarEmail(email);
                    if (usuarioEncontrado == null) {
                        throw new UsuarioNoEncontradoException();
                    } else {
                        finBusqueda = true;
                    }
                    break;
                case 2:
                    while (usuarioEncontrado == null) {
                        System.out.println("Introduzca nombre, apellido o ambos;");
                        String nombreApellido = Input.scanner();
                        ArrayList<Usuario> coincidencias = new ArrayList<>();
                        for (Usuario usuario : usuariosList) {
                            if (nombreApellido.equalsIgnoreCase(usuario.getNombre())
                                    || nombreApellido.equalsIgnoreCase(usuario.getApellido())
                                    || nombreApellido
                                            .equalsIgnoreCase(usuario.getNombre() + " " + usuario.getApellido())
                                    || nombreApellido
                                            .equalsIgnoreCase(usuario.getApellido() + " " + usuario.getNombre())) {
                                coincidencias.add(usuario);
                            }
                        }
                        if (coincidencias.size() > 1) {
                            System.out.println("Hay varias coincidencias:");
                            for (Usuario usuario2 : coincidencias) {
                                System.out.println(usuario2.getNombre() + " " + usuario2.getApellido());
                            }
                            System.out.println("---------------------------");

                        } else if (coincidencias.size() == 1) {
                            usuarioEncontrado = coincidencias.get(0);
                            finBusqueda = true;

                        } else {
                            throw new UsuarioNoEncontradoException();
                        }
                    }
                    break;
                case 3:
                    finBusqueda = true;
                    break;
                default:
                    throw new UsuarioNoEncontradoException();
            }
        }
        return usuarioEncontrado;
    }

    public boolean inicioSesion() throws MaxIntentosException, UsuarioNoEncontradoException {
        boolean sesionIniciada = false;
        Usuario usuarioValido = null;
        boolean contrasenyaValida = false;
        int intentos = 0;

        System.out.println("INICIAR SESIÓN:");
        System.out.println("---------------");

        while (!sesionIniciada && usuarioValido == null) {
            System.out.println("Introduzca correo electrónico:");
            String email = Input.validarEmail(Input.scanner());

            if (email != null) {
                usuarioValido = buscarEmail(email);
                if (usuarioValido == null) {
                    throw new UsuarioNoEncontradoException();
                }
            }
        }
        while (!sesionIniciada && !contrasenyaValida) {

            System.out.println("Introduzca contraseña;");

            // Aunque sea mas ilegible, creo que es mas seguro, que las contraseñas
            // codificadas
            // no se almacenen en memoria, y comprobarla simplemente con un boolean
            contrasenyaValida = EncoderContrasenyas.encodeContrasenya(Input.scanner(), usuarioValido.getSalt())
                    .equals(usuarioValido.getContrasenyaCodificada());

            if (!contrasenyaValida) {
                System.out.println("Contraseña incorrecta.");
                intentos++;
                if (intentos >= 3) {
                    throw new MaxIntentosException();
                }
            }
        }

        if (usuarioValido != null && contrasenyaValida) {
            usuarioActivo = usuarioValido;
            sesionIniciada = true;
        }
        return sesionIniciada;
    }

    public void crearCuenta() throws MaxIntentosException {
        boolean atras = false;
        String nombre = null;
        String apellido = null;
        String email = null;
        String contrasenya = null;
        String repetirContrasenya;
        Usuario tempCuenta;
        int intentos = 0;

        System.out.println("CREAR USUARIO:");
        System.out.println("--------------");
        while (!atras && nombre == null) {
            System.out.println("Introduzca el nombre:");
            nombre = Input.comprobarSoloLetras(Input.scanner());
        }
        while (!atras && apellido == null) {
            System.out.println("Introduzca el apellido:");
            apellido = Input.comprobarSoloLetras(Input.scanner());
        }
        while (!atras && email == null) {
            System.out.println("Introduzca el correo electrónico:");
            email = Input.validarEmail(Input.scanner());
        }
        tempCuenta = new Usuario(nombre, apellido, email);
        while (!atras && contrasenya == null) {
            System.out.println("Introduzca la contrasenya:");
            contrasenya = Input.scanner();
            System.out.println("Repita la contrasenya:");
            repetirContrasenya = Input.scanner();
            if (contrasenya.equals(repetirContrasenya)) {
                tempCuenta.setContrasenyaCodificada(contrasenya);
                contrasenya = null;
                repetirContrasenya = null;
                System.out.println("Cuenta creada correctamente.");
                usuariosList.add(tempCuenta);
                atras = true;
            } else {
                intentos++;
                if (intentos >= 3) {
                    throw new MaxIntentosException();
                }
                System.out.println("Las contraseñas no coinciden. Inténtelo de nuevo.");
                contrasenya = null;
            }

        }
    }

    public void mostrarUsuarios() {
        System.out.println("-------------------");
        for (Usuario usuario : usuariosList) {
            System.out.println("Email: " + usuario.getEmail());
            System.out.println("Nombre: " + usuario.getNombre());
            System.out.println("Apellido: " + usuario.getApellido());
            System.out.println("Rol: " + usuario.getRol().getNombre());
            System.out.println("-------------------");
        }
    }

    public void menuUsuarios() throws NoPersmisoException, UsuarioNoEncontradoException {
        boolean atras = false;
        boolean permiso = false;
        boolean permisoEscritura = false;
        int contador = 0;
        while (!permiso && contador < usuarioActivo.getRol().getPermisos().size()) {
            Permiso permisoUsuario = usuarioActivo.getRol().getPermisos().get(contador);
            if (permisoUsuario.getClass().getSimpleName().equals("PermisoCuentas")) {
                permiso = true;
                permisoEscritura = permisoUsuario.getEscritura();
            } else {
                throw new NoPersmisoException();
            }
            contador++;
        }
        while (!atras && permiso) {
            System.out.println("GESTIÓN DE USUARIOS:");
            System.out.println("-------------------:");
            if (!permisoEscritura) {
                System.out.println("1) Mostrar Usuarios");
                System.out.println("2) Buscar Usuario");
                System.out.println("3) atras");
            }
            if (permisoEscritura) {
                System.out.println("1) Mostrar Usuarios");
                System.out.println("2) Buscar Usuario");
                System.out.println("3) Modificar Usuario");
                System.out.println("4) Eliminar Usuario");
                System.out.println("5) atras");
            }
            int eleccion = Input.comprobarEntero(Input.scanner());
            switch (eleccion) {
                case 1:
                    mostrarUsuarios();
                    break;
                case 2:
                    Usuario tempUsuario;
                    try {
                        tempUsuario = buscarUsuario();
                        System.out.println("-------------------");
                        System.out.println("Email: " + tempUsuario.getEmail());
                        System.out.println("Nombre: " + tempUsuario.getNombre());
                        System.out.println("Apellido: " + tempUsuario.getApellido());
                        System.out.println("Rol: " + tempUsuario.getRol().getNombre());
                        System.out.println("-------------------");
                    } catch (UsuarioNoEncontradoException e) {
                        System.out.println(e);
                    }
                    break;
                case 3:
                    if (permisoEscritura) {
                        mostrarUsuarios();
                        System.out.println("Elige un usuario:");

                        try {
                            tempUsuario = buscarUsuario();
                            System.out.println("-------------------");
                            System.out.println("Email: " + tempUsuario.getEmail());
                            System.out.println("Nombre: " + tempUsuario.getNombre());
                            System.out.println("Apellido: " + tempUsuario.getApellido());
                            System.out.println("Rol: " + tempUsuario.getRol().getNombre());
                            System.out.println("-------------------");
                            modificarUsuarios(tempUsuario);
                        } catch (UsuarioNoEncontradoException | OpcionNoDisponible e) {
                            System.out.println(e);
                        }
                    } else {
                        atras = true;
                    }
                    break;
                case 4:
                    if (permisoEscritura) {

                    } else {
                        System.out.println("Opción no disponible");
                    }
                    break;
                case 5:
                    if (permisoEscritura) {
                        atras = true;
                    } else {
                        System.out.println("Opción no disponible");
                    }
                    break;
                default:
                    System.out.println("Opción no disponible");
                    break;
            }

        }

    }

    public void modificarUsuarios(Usuario usuario) throws OpcionNoDisponible {
        boolean atras = false;

        System.out.println("-------------------");
        System.out.println("MODIFICAR USUARIO: ");
        System.out.println(usuario.getEmail());
        System.out.println("-------------------");
        while (!atras) {
            System.out.println("1) Cambiar nombre");
            System.out.println("2) Cambiar apellido");
            System.out.println("3) Cambiar Email");
            System.out.println("4) Cambiar contraseña");
            System.out.println("5) Atrás");

            int eleccion = Input.comprobarEntero(Input.scanner());
            switch (eleccion) {
                case 1:
                    usuario.setNombre(Input.comprobarSoloLetras(Input.scanner()));
                    break;
                case 2:
                    usuario.setApellido(Input.comprobarSoloLetras(Input.scanner()));
                    break;
                case 3:
                    usuario.setEmail(Input.comprobarSoloLetras(Input.scanner()));
                    break;
                case 4:
                    System.out.println("Contraseña reiniciada");
                    System.out
                            .println("Se ha enviado un correo para crear una nueva contraseña a " + '"'
                                    + usuario.getEmail() + '"');
                    usuario.reiniciarContrasenya();
                default:
                    throw new OpcionNoDisponible();
            }
        }
    }
}
