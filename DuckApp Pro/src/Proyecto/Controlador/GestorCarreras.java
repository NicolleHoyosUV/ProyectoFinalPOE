package Proyecto.Controlador;

import Proyecto.Modelo.Carreras;
import java.util.ArrayList;
import java.io.*;

public class GestorCarreras {
    private ArrayList<Carreras> listaCarreras = new ArrayList<>();
    private static final String ARCHIVO_DAT = "carreras.dat";

    public GestorCarreras() {
        cargarDesdeArchivo();
        obtenerUltimaCarrera();
    }

    //guarda automaticamente
    public void crearCarrera(String nombre, String categoria, String fecha) {
        listaCarreras.add(new Carreras(nombre, categoria, fecha));
        guardarEnArchivo();
    }

    public void eliminarCarrera(String nombre) {
        listaCarreras.removeIf(c -> c.getNombre().equals(nombre));
        guardarEnArchivo();
    }

    //serializarion
    public void guardarEnArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(ARCHIVO_DAT))) {
            oos.writeObject(listaCarreras);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void cargarDesdeArchivo() {
        File archivo = new File(ARCHIVO_DAT);
        if (!archivo.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(ARCHIVO_DAT))) {
            listaCarreras = (ArrayList<Carreras>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Carreras obtenerUltimaCarrera() {
        if (listaCarreras.isEmpty()) {
            return null;
        }
        // Devuelve el último elemento de la lista
        return listaCarreras.get(listaCarreras.size() - 1);
    }

    public ArrayList<Carreras> listarCarreras() {
        return listaCarreras;
    }
}
