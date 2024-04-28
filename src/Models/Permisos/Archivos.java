package Models.Permisos;

import Utils.Input;

public class Archivos extends LecturaEscritura {

    public Archivos(boolean escritura) {
        super(true, escritura);
        this.cabeceraPermiso = ") Archivos";
    }

    @Override
    public void setLectura(boolean lectura) {
        this.lectura = true;
        System.out.println("La lectura de transacciones es un permiso básico, no se puede modificar");
    }

    @Override
    public boolean menuPermiso() {
        boolean atras = false;
        boolean cerrar = false;
        int eleccion;
        while (!atras) {
            if (!escritura) {
                System.out.println("1) Mostrar Transacciones");
            } else {
                System.out.println("1) Mostrar Transacciones");
                System.out.println("2) Añadir Transacciones");
                System.out.println("3) Borrar Transacción");
                System.out.println("4) Atrás");
                System.out.println("5) Cerrar programa");
            }
            eleccion = Input.comprobarEntero(Input.scanner());
            switch (eleccion) {
                case 1:

                    break;

                case 2:

                    break;

                case 3:

                    break;

                case 4:
                    atras = true;
                    break;

                case 5:
                    atras = true;
                    cerrar = true;
                    break;

                default:
                    System.out.println("Opción no disponible");
                    break;
            }
        }
        return cerrar;
    }

}
