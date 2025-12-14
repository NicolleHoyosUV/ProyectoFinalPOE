package Proyecto.Vista;

import Proyecto.Controlador.GestorResultados;
import Proyecto.Modelo.Resultado;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;        //SE USA PARA MANIPULAR ARCHIVOS DEL SISTEMA (En este caso los .dat)
import java.io.FileWriter;  // Escribir texto en archivos
import java.util.ArrayList;
import java.util.List;

public class Estadisticas extends JFrame{
    private JPanel mainPanel;
    private JButton exportarResultadosATxtButton;
    private JButton volverAlMenuPrincipalButton;
    private JLabel LblParticipanteConMasPodios;
    private JLabel LblPatoMasRapido;
    private JLabel lblTitulo;
    private GestorResultados gestorResultados;


    public Estadisticas() {
        setContentPane(mainPanel);
        setTitle("Estadísticas - DuckApp Pro");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Inicializar gestor
        gestorResultados = new GestorResultados();

        // Configurar título
        lblTitulo.setText(" ESTADÍSTICAS DE CARRERAS 📊");

        // Botón para volver al menú
        volverAlMenuPrincipalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuPrincipal.getInstancia().volverAlMenuPrincipal();
                dispose();
            }
        });

        // Calcular y mostrar estadísticas automáticamente al abrir
        calcularYMostrarEstadisticas();

        exportarResultadosATxtButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportarATxt();
            }
        });
    }

    //METODO PARA QUE CUANDO INICIE SECCION EN LA VENTANA YA APAREZCAN LOS RESULTADOS EN LOS JLABEL

    private void calcularYMostrarEstadisticas(){
        List<Resultado> resultados = gestorResultados.listarResultados();

        if (resultados.isEmpty()) {
            LblParticipanteConMasPodios.setText(" No hay resultados");
            LblPatoMasRapido.setText(" No hay resultados");
            return;
        }

        // CALCULAR PARTICIPANTE CON MAS PODIOS -- mediante listas para almacenar participantes unicos
        List<String> participantes = new ArrayList<>();
        List<Integer> conteoPodios = new ArrayList<>();

        for (Resultado r : resultados) {
            List<String> podio = r.getPodio(); //getPodio() devuelve List<String>

            for (String participante : podio) {
                // Buscar si el participante ya encuentra en la lista
                boolean encontrado = false;
                for (int i = 0; i < participantes.size(); i++) {
                    if (participantes.get(i).equals(participante)) {
                        // Incrementar conteo
                        conteoPodios.set(i, conteoPodios.get(i) + 1);
                        encontrado = true;
                        break;
                    }
                }

                // Si no se encontro, agregar nuevo
                if (!encontrado) {
                    participantes.add(participante);
                    conteoPodios.add(1);
                }
            }
        }

        // Encontrar el participante con mas podios
        String mejorParticipante = "No hay datos";
        int maxPodios = 0;

        for (int i = 0; i < participantes.size(); i++) {
            if (conteoPodios.get(i) > maxPodios) {
                maxPodios = conteoPodios.get(i);
                mejorParticipante = participantes.get(i) + " (" + maxPodios + " podios)";
            }
        }

        // Buscar el pato mas rapido

        List<String> patosGanadores = new ArrayList<>();
        List<Double> sumaTiempos = new ArrayList<>();
        List<Integer> conteoVictorias = new ArrayList<>();

        for (Resultado r : resultados) {
            String ganador = r.getGanador();
            double tiempo = r.getTiempo();

            // Buscar si el pato ya esta en la lista
            boolean encontrado = false;
            for (int i = 0; i < patosGanadores.size(); i++) {
                if (patosGanadores.get(i).equals(ganador)) {
                    // Actualizar suma y conteo
                    sumaTiempos.set(i, sumaTiempos.get(i) + tiempo);
                    conteoVictorias.set(i, conteoVictorias.get(i) + 1);
                    encontrado = true;
                    break;
                }
            }

            // Si no se encontro, agregar nuevo
            if (!encontrado) {
                patosGanadores.add(ganador);
                sumaTiempos.add(tiempo);
                conteoVictorias.add(1);
            }
        }

        // Calcular promedios
        String patoMasRapido = "No hay datos";
        double mejorPromedio = Double.MAX_VALUE;

        //Este bucle encuentra el pato mas rapido
        for (int i = 0; i < patosGanadores.size(); i++) {
            double promedio = sumaTiempos.get(i) / conteoVictorias.get(i);

            if (promedio < mejorPromedio) {
                mejorPromedio = promedio;
                patoMasRapido = patosGanadores.get(i) + " (" + String.format("%.2f", promedio) + " seg promedio)";
            }
        }

        // mostrar resultados
        LblParticipanteConMasPodios.setText("🥇 " + mejorParticipante);
        LblPatoMasRapido.setText("⚡ " + patoMasRapido);


        // Mostrar lista completa de participantes y sus podios
        System.out.println("\n--- Lista completa de podios ---");
        for (int i = 0; i < participantes.size(); i++) {
            System.out.println(participantes.get(i) + ": " + conteoPodios.get(i) + " podios");
        }

        System.out.println("\n--- Tiempos promedio por pato ---");
        for (int i = 0; i < patosGanadores.size(); i++) {
            double promedio = sumaTiempos.get(i) / conteoVictorias.get(i);
            System.out.println(patosGanadores.get(i) + ": " + String.format("%.2f", promedio) +
                    " seg (" + conteoVictorias.get(i) + " victorias)");
        }
    }

    //Metodo para exportar a txt (Obteniendo datos con FileWriter)
    private void exportarATxt() {
        try {
            List<Resultado> resultados = gestorResultados.listarResultados();

            //esto solamente se jecuta si no hay resultados
            if (resultados.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No hay resultados para exportar.",
                        "Exportación Cancelada",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Crear nombre de archivo con fecha por defecto
            String fecha = new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date());
            String nombreArchivo = "estadisticas_" + fecha + ".txt";

            // Guardar en la carpeta actual del proyecto
            File archivo = new File(nombreArchivo);

            // Escribir archivo
            FileWriter writer = new FileWriter(archivo);

            writer.write("=============================================\n");
            writer.write("       ESTADÍSTICAS DE CARRERAS\n");
            writer.write("=============================================\n\n");

            writer.write("Fecha de generación: " + new java.util.Date() + "\n");
            writer.write("Total carreras: " + resultados.size() + "\n\n");

            writer.write("🏆 PARTICIPANTE CON MÁS PODIOS:\n");
            writer.write(LblParticipanteConMasPodios.getText() + "\n\n");

            writer.write("⚡ PATO MÁS RÁPIDO:\n");
            writer.write(LblPatoMasRapido.getText() + "\n\n");

            writer.write("=============================================\n");

            writer.close();

            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(this,
                    "✅ Estadísticas exportadas exitosamente!\n" +
                            "📄 Archivo: " + nombreArchivo + "\n" +
                            "📂 Ubicación: " + archivo.getAbsolutePath(),
                    "Exportación Completada",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "❌ Error al exportar: " + e.getMessage(),
                    "Error de Exportación",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setVisible(boolean b) {
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
