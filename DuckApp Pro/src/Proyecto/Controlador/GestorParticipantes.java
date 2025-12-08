package Proyecto.Controlador;

import Proyecto.Modelo.Participantes;
import java.util.ArrayList;

public class GestorParticipantes {

    private ArrayList<Participantes> lista = new ArrayList<>();

    public boolean registrarParticipante(String nombre, int edad, String documento,
                                         int numeroPato, String categoria) {

        // Validar documento único
        for (Participantes p : lista) {
            if (p.getDocumento().equals(documento) || p.getNumeroPato() == numeroPato) {
                return false; // Documento o pato repetido
            }
        }

        lista.add(new Participantes(nombre, edad, documento, numeroPato, categoria));
        return true;
    }

    public void eliminarParticipante(String documento) {
        lista.removeIf(p -> p.getDocumento().equals(documento));
    }

    public ArrayList<Participantes> listarParticipantes() {
        return lista;
    }
}
