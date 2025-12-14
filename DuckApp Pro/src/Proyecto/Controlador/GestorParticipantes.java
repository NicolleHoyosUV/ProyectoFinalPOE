package Proyecto.Controlador;

import Proyecto.Modelo.Participantes;
import java.util.ArrayList;
import java.io.*;

public class GestorParticipantes {

    private ArrayList<Participantes> lista = new ArrayList<>();
    private static final String ARCHIVO_DAT = "participantes.dat";  // Archivo serializado

    public GestorParticipantes(){
        cargarDesdeArchivo();
    }

    public boolean registrarParticipante(String nombre, int edad, String documento,
                                         int numeroPato, String categoria) {

        // Validar documento único
        for (Participantes p : lista) {
            if (p.getDocumento().equals(documento) || p.getNumeroPato() == numeroPato) {
                return false; // Documento o pato repetido
            }
        }

        lista.add(new Participantes(nombre, edad, documento, numeroPato, categoria));
        guardarEnArchivo();//para que se guarde automaticamente en el .dat
        return true;
    }

    public void eliminarParticipante(String documento) {
        lista.removeIf(p -> p.getDocumento().equals(documento));
    }

    //Guardar lista en archivo .dat
    public void guardarEnArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(ARCHIVO_DAT))) {
            oos.writeObject(lista);
            System.out.println("✅ Participantes guardados en " + ARCHIVO_DAT);
        } catch (IOException e) {
            System.err.println("❌ Error al guardar participantes: " + e.getMessage());
        }
    }
    // Cargar lista desde archivo .dat
    @SuppressWarnings("unchecked")
    public void cargarDesdeArchivo() {
        File archivo = new File(ARCHIVO_DAT);
        if (!archivo.exists()) {
            System.out.println("ℹ️ No existe archivo previo de participantes.");
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(ARCHIVO_DAT))) {
            lista = (ArrayList<Participantes>) ois.readObject();
            System.out.println("✅ " + lista.size() + " participantes cargados desde " + ARCHIVO_DAT);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("❌ Error al cargar participantes: " + e.getMessage());
        }
    }

    public ArrayList<Participantes> listarParticipantes() {
        return lista;
    }

    public ArrayList<Participantes> obtenerParticipantesPorCategoria(String categoria) {
        ArrayList<Participantes> filtrados = new ArrayList<>();
        for (Participantes p : lista) {
            if (p.getCategoria().equalsIgnoreCase(categoria)) {
                filtrados.add(p);
            }
        }
        return filtrados;
    }

    //Obtiene los ultimos participantes para la carrera

    public ArrayList<Participantes> obtenerUltimosParticipantes(int cantidad) {
        ArrayList<Participantes> ultimos = new ArrayList<>();
        int startIndex = Math.max(0, lista.size() - cantidad);

        for (int i = startIndex; i < lista.size(); i++) {
            ultimos.add(lista.get(i));
        }
        return ultimos;
    }
}

