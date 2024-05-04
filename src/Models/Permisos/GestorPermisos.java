package Models.Permisos;

import Excepciones.NoPermisoException;
import Excepciones.OpcionNoDisponibleException;
import Models.GestorAbstracto;
import Models.Roles.Rol;
import Utils.Input;

public class GestorPermisos extends GestorAbstracto<Permiso> {

    @Override
    public void menu() throws OpcionNoDisponibleException, NoPermisoException, NumberFormatException {
        // Sobrescribimos el método abstracto menu() de la superclase
        // Pero lo dejamos sin implementar, ya que no nos interesa en este caso,
        // por que necesitamos que reciba un parámetro,
        // pero no queremos que nos de error por no "implementarlo".
        throw new UnsupportedOperationException("Unimplemented method 'menu'");
    }

    // A cambio lo sobrecargamos, añadiéndole el parámetro que necesitamos que
    // reciba y su implementación.
    public void menu(Rol rol) throws OpcionNoDisponibleException, NumberFormatException {
        boolean atras = false;
        for (Permiso permiso : rol.getPermisos()) {
            System.out.println("   -Permiso: " + permiso.getNombre());
            System.out.println("   -Permiso de lectura: " + permiso.getLectura());
            System.out.println("   -Permiso de escritura: " + permiso.getEscritura());
            System.out.println("---------------------------------------");
        }
        System.out.println("¿Que permiso quieres modificar?");
        System.out.println("---------------------------------------");
        while (!atras) {
            for (int i = 0; i < rol.getPermisos().length; i++) {
                System.out.println((i + 1) + ") " + rol.getPermisos()[i]);
            }
            int indiceSalidas = rol.getPermisos().length;
            System.out.println((indiceSalidas + 1) + ") Atrás");
            System.out.println("---------------------------------------");
            int eleccion = Input.comprobarEntero();
            switch (eleccion) {
                case 1:
                    System.out.println("---------------------------------------");
                    System.out.println("   -Permiso: " + rol.getPermisos()[0].getNombre());
                    System.out.println("   -Permiso de lectura: " + rol.getPermisos()[0].getLectura());
                    System.out.println("   -Permiso de escritura: " + rol.getPermisos()[0].getEscritura());
                    System.out.println("---------------------------------------");
                    modificarPermisos(rol.getPermisos()[0]);
                    break;

                case 2:
                    System.out.println("---------------------------------------");
                    System.out.println("   -Permiso: " + rol.getPermisos()[1].getNombre());
                    System.out.println("   -Permiso de lectura: " + rol.getPermisos()[1].getLectura());
                    System.out.println("   -Permiso de escritura: " + rol.getPermisos()[1].getEscritura());
                    System.out.println("---------------------------------------");
                    modificarPermisos(rol.getPermisos()[1]);
                    break;

                case 3:
                    atras = true;
                    break;

                default:
                    throw new OpcionNoDisponibleException();
            }
        }

    }

    public void modificarPermisos(Permiso permiso) throws OpcionNoDisponibleException {
        boolean atras = false;
        while (!atras) {
            System.out.println("1) Modificar permiso de lectura");
            System.out.println("2) Modificar permiso de escritura");
            System.out.println("3) Atrás");
            System.out.println("------------------");
            int eleccion = Input.comprobarEntero();
            System.out.println("------------------");
            switch (eleccion) {
                case 1:
                    if (!permiso.getBasico()) {
                        if (permiso.getLectura()) {
                            System.out.println("Quitar permiso de Lectura?(S/N)");
                            System.out.println("------------------");
                            String continuar = Input.comprobarSoloLetras();
                            if (continuar.equalsIgnoreCase("s")) {
                                permiso.setLectura(false);
                                System.out.println("------------------");
                                System.out.println("Se ha quitado el permiso de lectura correctamente");
                                System.out.println("------------------");
                                atras = true;
                            } else if (continuar.equalsIgnoreCase("n")) {
                                System.out.println("------------------");
                                atras = true;
                                break;
                            } else {
                                System.out.println("------------------");

                                System.out.println(" No es una respuesta valida. Vuelva a introducir una respuesta.");
                                System.out.println("------------------");
                            }
                        } else {
                            System.out.println("Añadir permiso de lectura en (S/N)");
                            String continuar = Input.comprobarSoloLetras();
                            if (continuar.equalsIgnoreCase("S")) {
                                permiso.setLectura(true);
                                atras = true;
                                System.out.println("------------------");
                                System.out.println("Se ha añadido el permiso de escritura correctamente");
                                System.out.println("------------------");
                            } else if (continuar.equalsIgnoreCase("n")) {
                                atras = true;
                                break;
                            } else {
                                System.out.println("------------------");
                                System.out.println(" No es una respuesta valida. Vuelva a introducir una respuesta.");
                                System.out.println("------------------");
                            }
                        }
                    } else {
                        System.out.println("------------------");
                        System.out.println("Esto es un permiso básico, no se puede modificar.");
                        System.out.println("------------------");
                    }
                    break;

                case 2:
                    if (permiso.getEscritura()) {
                        System.out.println("Quitar permiso de escritura?(S/N)");
                        System.out.println("------------------");
                        String continuar = Input.comprobarSoloLetras();
                        if (continuar.equalsIgnoreCase("s")) {
                            permiso.setEscritura(false);
                            System.out.println("------------------");
                            System.out.println("Se ha quitado el permiso de escritura correctamente");
                            System.out.println("------------------");
                            atras = true;
                        } else if (continuar.equalsIgnoreCase("n")) {
                            atras = true;
                            break;
                        } else {
                            System.out.println("------------------");
                            System.out.println(" No es una respuesta valida. Vuelva a introducir una respuesta.");
                            System.out.println("------------------");
                        }
                    } else {
                        System.out.println("Añadir permiso de escritura en (S/N)");
                        System.out.println("------------------");
                        String continuar = Input.comprobarSoloLetras();
                        if (continuar.equalsIgnoreCase("S")) {
                            permiso.setEscritura(true);
                            atras = true;
                            System.out.println("------------------");
                            System.out.println("Se ha añadido el permiso de escritura correctamente");
                            System.out.println("------------------");
                        } else if (continuar.equalsIgnoreCase("n")) {
                            atras = true;
                            break;
                        } else {
                            System.out.println("------------------");
                            System.out.println(" No es una respuesta valida. Vuelva a introducir una respuesta.");
                            System.out.println("------------------");
                        }
                    }
                    break;
                case 3:
                    atras = true;
                    break;

                default:
                    throw new OpcionNoDisponibleException();

            }
        }
    }
}
