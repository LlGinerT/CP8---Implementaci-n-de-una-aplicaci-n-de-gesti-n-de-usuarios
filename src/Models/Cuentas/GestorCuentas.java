package Models.Cuentas;

import java.util.ArrayList;

import Excepciones.FormatoEmailException;
import Excepciones.MaxIntentosException;
import Excepciones.NoPermisoException;
import Excepciones.OpcionNoDisponibleException;
import Excepciones.UsuarioNoEncontradoException;
import Models.GestorAbstracto;
import Models.Permisos.Permiso;
import Models.Roles.GestorRoles;
import Utils.EncoderContrasenyas;
import Utils.Input;

public class GestorCuentas extends GestorAbstracto<Usuario> {

    private GestorRoles gestorRoles = new GestorRoles(this);
    private Usuario usuarioActivo;

    public GestorCuentas() {
        super("PermisoCuentas");
        this.lista = new ArrayList<>();
        this.gestorRoles = new GestorRoles(this);
        // usuario test a@a.com | b@b.com pass a.
        lista.add(new Usuario("a", "a", "a@a.com"));
        lista.add(new Usuario("b", "a", "b@b.com"));
        lista.get(0).setContrasenyaCodificada("a");
        lista.get(0).setRol(gestorRoles.getLista().get(0));
        lista.get(1).setContrasenyaCodificada("a");
        lista.get(1).setRol(gestorRoles.getLista().get(2));
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
    protected Boolean tienePermiso() throws NoPermisoException {
        int contador = 0;
        while (!permisoLectura && contador < usuarioActivo.getRol().getPermisos().length) {
            Permiso permisoUsuario = usuarioActivo.getRol().getPermisos()[contador];
            if (permisoUsuario.getClass().getSimpleName().equals(nombrePermiso)) {
                permisoLectura = true;
                permisoEscritura = permisoUsuario.getEscritura();
            } else {
                throw new NoPermisoException();
            }
            contador++;
        }
        return permisoLectura;
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
            int eleccion = Input.comprobarEntero();
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
                        modificarUsuarios();
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

    private Usuario buscarEmail() {
        Usuario usuarioEncontrado = null;
        boolean emailValido = false;

        while (!emailValido) {
            try {
                String email = Input.validarEmail();
                // La entrada es un email válido, buscar en la lista de usuarios
                for (Usuario usuario : lista) {
                    if (usuario.getEmail().equals(email)) {
                        usuarioEncontrado = usuario;
                        emailValido = true;
                        break;
                    }
                }
                if (usuarioEncontrado == null) {
                    // El email no se encontró en la lista de usuarios
                    System.out.println("Email no encontrado. Inténtelo de nuevo.");
                }
            } catch (FormatoEmailException e) {
                // Maneja la excepción cuando se detecta un correo electrónico incorrecto
                System.out.println(e.getMessage());
            }
        }
        return usuarioEncontrado;
    }

    private Usuario buscarUsuario() throws UsuarioNoEncontradoException, NumberFormatException {
        boolean finBusqueda = false;
        Usuario usuarioEncontrado = null;
        while (!finBusqueda) {
            System.out.println("1) Buscar por Email");
            System.out.println("2) Buscar por Nombre o Apellido");
            System.out.println("3) Atrás");
            int eleccion = Input.comprobarEntero();
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

    public boolean inicioSesion() throws MaxIntentosException, UsuarioNoEncontradoException {
        boolean sesionIniciada = false;
        Usuario usuarioValido = null;
        boolean contrasenyaValida = false;
        int intentos = 0;

        System.out.println("INICIAR SESIÓN:");
        System.out.println("---------------");

        while (!sesionIniciada && usuarioValido == null) {
            System.out.println("Introduzca correo electrónico:");
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
            nombre = Input.comprobarSoloLetras();
        }
        while (!atras && apellido == null) {
            System.out.println("Introduzca el apellido:");
            apellido = Input.comprobarSoloLetras();
        }
        while (!atras && email == null) {
            System.out.println("Introduzca el correo electrónico:");
            try {
                email = Input.validarEmail();
            } catch (FormatoEmailException e) {
                System.out.println(e.getMessage());
            }
            for (Usuario usuario : lista) {
                if (email != null && usuario.getEmail().contains(email)) {
                    intentos++;
                    if (intentos >= 3) {
                        throw new MaxIntentosException();
                    } else {
                        System.out.println("Ya existe una cuenta con ese correo electrónico");
                        email = null;
                    }
                }
            }
        }
        tempCuenta = new Usuario(nombre, apellido, email);
        tempCuenta.setRol(gestorRoles.buscarRol("regular"));
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
                lista.add(tempCuenta);
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

    private void modificarUsuarios() throws OpcionNoDisponibleException, NumberFormatException {
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

            int eleccion = Input.comprobarEntero();
            switch (eleccion) {
                case 1:
                    tempUsuario.setNombre(Input.comprobarSoloLetras());
                    break;
                case 2:
                    tempUsuario.setApellido(Input.comprobarSoloLetras());
                    break;
                case 3:
                    tempUsuario.setEmail(Input.comprobarSoloLetras());
                    break;
                case 4:
                    System.out.println("Contraseña reiniciada");
                    System.out
                            .println("Se ha enviado un correo para crear una nueva contraseña a " + '"'
                                    + tempUsuario.getEmail() + '"');
                    tempUsuario.reiniciarContrasenya();
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
        System.out.println("Elige un usuario:");

        try {
            while (true) {
                Usuario tempUsuario;
                tempUsuario = buscarUsuario();
                if (!tempUsuario.equals(usuarioActivo)) {
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
                        break;
                    } else if (continuar.equalsIgnoreCase("n")) {
                        break;
                    } else {
                        System.out.println(continuar
                                + " no es una respuesta valida. Vuelva a introducir una respuesta.");
                    }
                } else {
                    System.out.println("¿Seguro que quieres borrar tu cuenta?(S/N)");
                    String continuar = Input.comprobarSoloLetras();
                    if (continuar.equalsIgnoreCase("s")) {
                        usuarioActivo = null;
                        lista.remove(tempUsuario);
                        break;
                    } else if (continuar.equalsIgnoreCase("n")) {
                        break;
                    } else {
                        System.out.println(continuar
                                + " no es una respuesta valida. Vuelva a introducir una respuesta.");
                    }
                }
            }
        } catch (UsuarioNoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }
}
