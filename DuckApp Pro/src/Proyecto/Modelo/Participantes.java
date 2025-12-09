package Proyecto.Modelo;

public class Participantes {
    private String nombre;
    private int edad;
    private String documento;
    private int numeroPato;
    private String categoria;


    public Participantes(String nombre, int edad, String documento, int numeroPato, String categoria) {
        this.nombre = nombre;
        this.edad = edad;
        this.documento = documento;
        this.numeroPato = numeroPato;
        this.categoria = categoria;

    }

    public String getNombre() { return nombre; }
    public int getEdad() { return edad; }
    public String getDocumento() { return documento; }
    public int getNumeroPato() { return numeroPato; }
    public String getCategoria() { return categoria; }

}
