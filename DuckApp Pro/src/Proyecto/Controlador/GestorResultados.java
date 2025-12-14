package Proyecto.Controlador;

import Proyecto.Modelo.Resultado;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.io.*;

public class GestorResultados {
    private ArrayList<Resultado> listaResultados = new ArrayList<>();
    private static final String ARCHIVO_DAT = "resultados.dat";

    public GestorResultados() {
        cargarDesdeArchivo();
    }

    public void guardarResultado(Resultado r){
        listaResultados.add(r);
        guardarEnArchivo();
    }
    public void guardarEnArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(ARCHIVO_DAT))) {
            oos.writeObject(listaResultados);
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
            listaResultados = (ArrayList<Resultado>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Resultado buscarResultado(String carrera){
        for(Resultado r : listaResultados) {
            if (r.getCarrera().equals(carrera)) return r;
        }
        return null;
    }

    public ArrayList<Resultado> listarResultados() {
        return listaResultados;
    }

    public void exportarResultados() {
        try(FileWriter writer = new FileWriter("resultados.txt")){
            for(Resultado r : listaResultados){
                writer.write("Carrera: "+ r.getCarrera() + "\n");
                writer.write("Ganador: "+ r.getGanador() + "\n");
                writer.write("Tiempo: "+ r.getTiempo() + "\n");
                writer.write("Fecha: "+ r.getFecha() + "\n\n");
            }
            writer.write("******* FINAL DEL ARCHIVO *******");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
