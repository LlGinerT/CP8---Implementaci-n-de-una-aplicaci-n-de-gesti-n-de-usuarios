package Models;

import java.util.ArrayList;

import Excepciones.NoPermisoException;
import Excepciones.OpcionNoDisponible;

public interface GestorInterface<T> {
    public ArrayList<T> getLista();

    public void menu() throws NoPermisoException, OpcionNoDisponible;
}
