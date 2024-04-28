package Models.Permisos;

public class PermisoTransacciones extends Permiso {

    public PermisoTransacciones(boolean escritura) {
        super(true, escritura);
        this.cabeceraAcceso = ") Transacciones";
    }

    @Override
    public void setLectura(boolean lectura) {
        this.lectura = true;
        System.out.println("La lectura de transacciones es un permiso básico, no se puede modificar");
    }

}
