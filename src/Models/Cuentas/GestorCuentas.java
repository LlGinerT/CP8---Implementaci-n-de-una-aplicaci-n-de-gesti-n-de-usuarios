package Models.Cuentas;

import java.util.ArrayList;
import java.util.HashSet;

import Excepciones.FormatoEmailException;
import Excepciones.MaxIntentosException;
import Excepciones.NoPermisoException;
import Excepciones.OpcionNoDisponibleException;
import Excepciones.UsuarioNoEncontradoException;
import Models.GestorAbstracto;
import Models.Permisos.Permiso;
import Models.Permisos.PermisoCuentas;
import Models.Roles.Admin;
import Models.Roles.GestorRoles;
import Models.Roles.Regular;
import Utils.EncoderContrasenyas;
import Utils.Input;

public class GestorCuentas extends GestorAbstracto<Usuario> {

    private GestorRoles gestorRoles = new GestorRoles(this);
    private Usuario usuarioActivo;

    public GestorCuentas() {
        super(new PermisoCuentas());
        this.lista = new HashSet<>();
        this.gestorRoles = new GestorRoles(this);
        // usuario test a@a.com | b@b.com pass a.
        lista.add(new Usuario("a", "a", "a@a.com", gestorRoles.buscarRol(Admin.class)));
        lista.add(new Usuario("b", "a", "b@b.com", gestorRoles.buscarRol(Regular.class)));

    }

    public GestorRoles getGestorRoles() {
        return gestorRoles;
    }

    public Usuario getUsuarioActivo() {
        return usuarioActivo;
    }

    public void delUsuarioActivo() {
        this.usuarioActivo = null;
    }

    @Override
    public void menu() throws OpcionNoDisponibleException, NoPermisoException, NumberFormatException {
        boolean atras = false;
        boolean permiso = tienePermiso();
        while (!atras && permiso) {
            System.out.println("GESTIÓN DE USUARIOS:");
            if (!permisoEscritura) {
                System.out.println("-------------------:");
                System.out.println("1) Mostrar Usuarios");
                System.out.println("2) Buscar Usuario");
                System.out.println("3) atras");
            }
            if (permisoEscritura) {
                System.out.println("-------------------:");
                System.out.println("1) Mostrar Usuarios");
                System.out.println("2) Buscar Usuario");
                System.out.println("3) Modificar Usuario");
                System.out.println("4) Eliminar Usuario");
                System.out.println("5) atras");
            }
            System.out.println("-------------------:");
            int eleccion = Input.comprobarEntero();
            System.out.println("-------------------:");
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
                        System.out.println(e.getMessage());
                        System.out.println("-------------------");
                    }
                    break;
                case 3:
                    if (permisoEscritura) {
                        try {
                            modificarUsuarios();
                        } catch (NumberFormatException | OpcionNoDisponibleException | MaxIntentosException e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        atras = true;
                    }
                    break;
                case 4:
                    if (permisoEscritura) {
                        eliminarUsuario();
                    } else {
                        throw new OpcionNoDisponibleException();
                    }
                case 5:
                    if (permisoEscritura) {
                        atras = true;
                    } else {
                        throw new OpcionNoDisponibleException();
                    }
                    break;
                default:
                    throw new OpcionNoDisponibleException();

            }

        }

    }

    public boolean inicioSesion() throws MaxIntentosException, UsuarioNoEncontradoException {
        boolean sesionIniciada = false;
        Usuario usuarioValido = null;
        boolean contrasenyaValida = false;
        int intentos = 0;

        System.out.println("INICIAR SESIÓN:");

        while (!sesionIniciada && usuarioValido == null) {
            System.out.println("---------------");
            System.out.println("Introduzca correo electrónico:");
            System.out.println("---------------");
            usuarioValido = buscarEmail();
            if (usuarioValido == null) {
                intentos++;
                throw new UsuarioNoEncontradoException();
            } else if (intentos >= 3) {
                throw new MaxIntentosException();
            } else if (usuarioValido.getRol() == null) {
                System.out.println();
            }

        }
        intentos = 0;
        while (!sesionIniciada && !contrasenyaValida) {

            System.out.println("---------------");
            System.out.println("Introduzca contraseña;");
            System.out.println("---------------");

            // Aunque sea mas ilegible, creo que es mas seguro, que las contraseñas
            // codificadas
            // no se almacenen en memoria, y comprobarla simplemente con un boolean
            contrasenyaValida = EncoderContrasenyas.encodeContrasenya(Input.scanner(), usuarioValido.getSalt())
                    .equals(usuarioValido.getContrasenyaCodificada());

            if (!contrasenyaValida) {
                System.out.println("---------------");
                System.out.println("Contraseña incorrecta.");
                System.out.println("---------------");
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
        Usuario tempCuenta;
        int intentos = 0;

        System.out.println("CREAR USUARIO:");
        System.out.println("--------------------");
        while (!atras && nombre == null) {
            System.out.println("Introduzca el nombre:");
            System.out.println("--------------------");
            nombre = Input.comprobarSoloLetras();
        }
        while (!atras && apellido == null) {
            System.out.println("--------------------");
            System.out.println("Introduzca el apellido:");
            System.out.println("--------------------");
            apellido = Input.comprobarSoloLetras();
            System.out.println("--------------------");
        }
        while (!atras && email == null) {
            System.out.println("Introduzca el correo electrónico:");
            System.out.println("--------------------");
            try {
                email = Input.validarEmail();
                System.out.println("--------------------");
            } catch (FormatoEmailException e) {
                System.out.println(e.getMessage());
                System.out.println("--------------------");
            }
            for (Usuario usuario : lista) {
                if (email != null && usuario.getEmail().contains(email)) {
                    intentos++;
                    if (intentos >= 3) {
                        throw new MaxIntentosException();
                    } else {
                        System.out.println("--------------------");
                        System.out.println("Ya existe una cuenta con ese correo electrónico");
                        System.out.println("--------------------");
                        email = null;
                    }
                }
            }
        }
        tempCuenta = new Usuario(nombre, apellido, email);
        tempCuenta.setRol(gestorRoles.buscarRol("regular"));
        crearContrasenya(tempCuenta, false);
        if (tempCuenta.getContrasenyaCodificada() != null) {
            lista.add(tempCuenta);
        }
    }

    @Override
    protected Boolean tienePermiso() throws NoPermisoException {
        int contador = 0;
        while (!permisoLectura && contador < usuarioActivo.getRol().getPermisos().length) {
            Permiso permisoUsuario = usuarioActivo.getRol().getPermisos()[contador];
            if (permisoUsuario.getClass().equals(permisoNecesario)) {
                permisoLectura = true;
                permisoEscritura = permisoUsuario.getEscritura();
            } else {
                throw new NoPermisoException();
            }
            contador++;
        }
        return permisoLectura;
    }

    private Usuario buscarEmail() {
        Usuario usuarioEncontrado = null;

        while (usuarioEncontrado == null) {
            try {
                String email = Input.validarEmail();
                usuarioEncontrado = lista.stream()
                        .filter(usuario -> usuario.getEmail().equals(email))
                        .findFirst()
                        .orElse(null);
                if (usuarioEncontrado == null) {
                    System.out.println("Email no encontrado. Inténtelo de nuevo.");
                }
            } catch (FormatoEmailException e) {
                System.out.println(e.getMessage());
            }
        }

        return usuarioEncontrado;
    }

    private Usuario buscarUsuario() throws UsuarioNoEncontradoException, NumberFormatException {
        boolean finBusqueda = false;
        Usuario usuarioEncontrado = null;
        System.out.println("BUSCAR USUARIO:");
        System.out.println("-------------------:");
        while (!finBusqueda) {
            System.out.println("1) Buscar por Email");
            System.out.println("2) Buscar por Nombre o Apellido");
            System.out.println("3) Atrás");
            System.out.println("-------------------:");
            int eleccion = Input.comprobarEntero();
            System.out.println("-------------------:");
            switch (eleccion) {
                case 1:
                    usuarioEncontrado = buscarEmail();
                    if (usuarioEncontrado == null) {
                        throw new UsuarioNoEncontradoException();
                    }
                    finBusqueda = true;
                case 2:
                    while (usuarioEncontrado == null) {
                        System.out.println("Introduzca nombre, apellido o ambos;");
                        System.out.println("-------------------:");
                        String nombreApellido = Input.scanner();
                        ArrayList<Usuario> coincidencias = new ArrayList<>();
                        for (Usuario usuario : lista) {
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
                            System.out.println("-------------------");
                            for (Usuario usuario2 : coincidencias) {
                                System.out.println("Email: " + usuario2.getEmail());
                                System.out.println("Nombre: " + usuario2.getNombre());
                                System.out.println("Apellido: " + usuario2.getApellido());
                                System.out.println("Rol: " + usuario2.getRol().getNombre());
                                System.out.println("-------------------");
                            }

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

    private void mostrarUsuarios() {
        System.out.println("-------------------");
        for (Usuario usuario : lista) {
            System.out.println("Email: " + usuario.getEmail());
            System.out.println("Nombre: " + usuario.getNombre());
            System.out.println("Apellido: " + usuario.getApellido());
            System.out.println("Rol: " + usuario.getRol().getNombre());
            System.out.println("-------------------");
        }
    }

    private void modificarUsuarios() throws OpcionNoDisponibleException, NumberFormatException, MaxIntentosException {
        boolean atras = false;
        Usuario tempUsuario = null;
        mostrarUsuarios();
        System.out.println("Elige un usuario:");
        while (tempUsuario == null) {
            try {
                tempUsuario = buscarUsuario();
                System.out.println("-------------------");
                System.out.println("Email: " + tempUsuario.getEmail());
                System.out.println("Nombre: " + tempUsuario.getNombre());
                System.out.println("Apellido: " + tempUsuario.getApellido());
                System.out.println("Rol: " + tempUsuario.getRol().getNombre());
                System.out.println("-------------------");
            } catch (UsuarioNoEncontradoException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("-------------------");
        System.out.println("MODIFICAR USUARIO: ");
        System.out.println(tempUsuario.getEmail());
        System.out.println("-------------------");
        while (!atras) {
            System.out.println("1) Cambiar nombre");
            System.out.println("2) Cambiar apellido");
            System.out.println("3) Cambiar Email");
            System.out.println("4) Cambiar contraseña");
            System.out.println("5) Atrás");
            System.out.println("-------------------");

            int eleccion = Input.comprobarEntero();
            System.out.println("-------------------");
            switch (eleccion) {
                case 1:
                    tempUsuario.setNombre(Input.comprobarSoloLetras());
                    System.out.println("-------------------");
                    break;
                case 2:
                    tempUsuario.setApellido(Input.comprobarSoloLetras());
                    System.out.println("-------------------");
                    break;
                case 3:
                    tempUsuario.setEmail(Input.comprobarSoloLetras());
                    System.out.println("-------------------");
                    break;
                case 4:
                    System.out.println("-------------------");
                    if (tempUsuario.equals(usuarioActivo)) {
                        crearContrasenya(usuarioActivo, true);
                    } else {
                        System.out.println("Contraseña reiniciada");
                        System.out
                                .println("Se ha enviado un correo para crear una nueva contraseña a " + '"'
                                        + tempUsuario.getEmail() + '"');
                        System.out.println("-------------------");
                        tempUsuario.reiniciarContrasenya();
                    }
                    break;
                case 5:
                    atras = true;
                    break;
                default:
                    throw new OpcionNoDisponibleException();
            }
        }
    }

    private void eliminarUsuario() throws NumberFormatException {
        mostrarUsuarios();
        System.out.println("-------------------");
        System.out.println("Elige un usuario:");
        System.out.println("-------------------");

        try {
            while (true) {
                Usuario tempUsuario;
                tempUsuario = buscarUsuario();
                if (!tempUsuario.equals(usuarioActivo) && tempUsuario.getRol().getNombre().equalsIgnoreCase("admin")) {
                    System.out.println("-------------------");
                    System.out.println("No puedes eliminar a un administrador, solo el usuario root tiene permiso.");
                    System.out.println("-------------------");
                } else if (!tempUsuario.equals(usuarioActivo)) {
                    System.out.println("-------------------");
                    System.out.println("Email: " + tempUsuario.getEmail());
                    System.out.println("Nombre: " + tempUsuario.getNombre());
                    System.out.println("Apellido: " + tempUsuario.getApellido());
                    System.out.println("Rol: " + tempUsuario.getRol().getNombre());
                    System.out.println("-------------------");
                    System.out.println("¿Estas seguro de querer eliminar este usuario?(S/N)");
                    String continuar = Input.comprobarSoloLetras();
                    if (continuar.equalsIgnoreCase("s")) {
                        lista.remove(tempUsuario);
                        System.out.println("-------------------");
                        System.out.println("Usuario eliminado correctamente");
                        break;
                    } else if (continuar.equalsIgnoreCase("n")) {
                        break;
                    } else {
                        System.out.println("------------------");
                        System.out.println(" No es una respuesta valida. Vuelva a introducir una respuesta.");
                    }
                    System.out.println("-------------------");
                } else {
                    System.out.println("¿Seguro que quieres borrar tu cuenta?(S/N)");
                    String continuar = Input.comprobarSoloLetras();
                    System.out.println("-------------------");
                    if (continuar.equalsIgnoreCase("s")) {
                        usuarioActivo = null;
                        lista.remove(tempUsuario);
                        break;
                    } else if (continuar.equalsIgnoreCase("n")) {
                        break;
                    } else {
                        System.out.println(" No es una respuesta valida. Vuelva a introducir una respuesta.");
                        System.out.println("------------------");
                    }
                }
            }
        } catch (UsuarioNoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }

    private void crearContrasenya(Usuario usuario, boolean reiniciada) throws MaxIntentosException {
        String contrasenya = null;
        String repetirContrasenya = null;
        int intentos = 0;
        while (contrasenya == null) {
            if (reiniciada) {
                System.out.println("Introduzca una nueva contrasenya:");
                System.out.println("--------------------");
            } else {
                System.out.println("Introduzca la contrasenya:");
                System.out.println("--------------------");
            }
            contrasenya = Input.scanner();
            System.out.println("--------------------");
            System.out.println("Repita la contrasenya:");
            System.out.println("--------------------");
            repetirContrasenya = Input.scanner();
            System.out.println("--------------------");
            if (contrasenya.equals(repetirContrasenya)) {
                usuario.setContrasenyaCodificada(contrasenya);
            } else {
                intentos++;
                if (intentos >= 3) {
                    throw new MaxIntentosException();
                }
                System.out.println("Las contraseñas no coinciden. Inténtelo de nuevo.");
                System.out.println("--------------------");
                contrasenya = null;
            }
        }
    }
}
