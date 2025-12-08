package Proyecto.Controlador;

import Proyecto.Modelo.Carreras;
import java.util.ArrayList;

public class GestorCarreras {
    private ArrayList<Carreras> listaCarreras = new ArrayList<>();

    public void crearCarrera(String nombre, String categoria, String fecha) {
        listaCarreras.add(new Carreras(nombre, categoria, fecha));
    }

    public void eliminarCarrera(String nombre) {
        listaCarreras.removeIf(c -> c.getNombre().equals(nombre));
    }

    public ArrayList<Carreras> listarCarreras() {
        return listaCarreras;
    }
}
