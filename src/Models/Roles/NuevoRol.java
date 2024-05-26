package Models.Roles;

/* 
 * Se agregó, por si se añadía la opción de crear nuevos roles fuera de los predefinidos.
 * Al final no se añadió por falta de tiempo.
 */
public class NuevoRol extends Rol {

    public NuevoRol(String nombre) {
        super(nombre, false);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void permisosIniciales() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'permisosIniciales'");
    }

}
