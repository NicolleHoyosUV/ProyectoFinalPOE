package Proyecto.Controlador;

import Proyecto.Modelo.Resultado;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

public class GestorResultados {
    private ArrayList<Resultado> listaResultados = new ArrayList<>();

    public void guardarResultado(Resultado r){
        listaResultados.add(r);
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
