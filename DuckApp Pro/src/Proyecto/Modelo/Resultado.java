package Proyecto.Modelo;

import java.util.ArrayList;
import java.io.Serializable;

public class Resultado implements Serializable {
    private static final long serialVersionUID = 1L;

    private String carrera;
    private String ganador;
    private String categoria;
    private double tiempo;
    private ArrayList<String> podio;
    private String fecha;

    public Resultado(String carrera, String ganador, String categoria, double tiempo, ArrayList<String> podio, String fecha) {
        this.carrera = carrera;
        this.ganador = ganador;
        this.categoria = categoria;
        this.tiempo = tiempo;
        this.podio = podio;
        this.fecha = fecha;
    }

    public String getCarrera() { return carrera; }
    public String getGanador() { return ganador; }
    public String getCategoria() { return categoria; }
    public double getTiempo() { return tiempo; }
    public ArrayList<String> getPodio() { return podio; }
    public String getFecha() { return fecha; }
}
