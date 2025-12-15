package Proyecto.Vista;

import Proyecto.Controlador.GestorCarreras;
import Proyecto.Controlador.GestorResultados;
import Proyecto.Modelo.Carreras;
import Proyecto.Modelo.Resultado;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Resultados extends JFrame{
    private JButton cargarResultadoButton;
    private JComboBox CarreraComboBox;
    private JTable resultadosTabla;
    private JButton exportarATxtButton;
    private JTextField ganadorNombreField;
    private JTextField ganadorCategoriaField;
    private JTextField ganadorTiempoField;
    private JTextField primerLugarField;
    private JTextField segundoLugarField;
    private JTextField tercerLugarField;
    private JPanel ResultadosPanel;
    private JButton volverAlMenuPrincipalButton;
    private JTextArea textAreaGanador;
    private JButton mostrarGanadorButton;
    private JTextArea textAreaPodio;
    private JButton mostrarPodioButton;
    private JLabel lblTitulo;

    private GestorCarreras gestorCarreras;
    private GestorResultados gestorResultados;
    private DefaultTableModel modeloTabla;

    public Resultados(){
        setContentPane(ResultadosPanel);
        setTitle("Resultados de Carreras");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //inicializacion de gestores

        gestorCarreras = new GestorCarreras();
        gestorResultados = new GestorResultados();
        gestorCarreras = new GestorCarreras();
        gestorResultados = new GestorResultados();

        //Cargar info en tabla
        modeloTabla = new DefaultTableModel(new Object[]{"Carrera", "Ganador", "Tiempo", "Fecha y hora"}, 0);
        resultadosTabla.setModel(modeloTabla);
        cargarCarrerasEnCombo();
        cargarResultadoButton.addActionListener(e -> cargarResultado());
        exportarATxtButton.addActionListener(e -> gestorResultados.exportarResultados());

        cargarTabla();
        lblTitulo.setText("🏆 RESULTADOS DE CARRERAS 🏆");

        //Boton volver al menu principal
        volverAlMenuPrincipalButton.addActionListener(e -> {
            MenuPrincipal.getInstancia().volverAlMenuPrincipal();
            dispose();
        });


        //Boton mostrar ganador
        mostrarGanadorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarGanador();
            }
        });



        //Boton mostar podio
        mostrarPodioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarPodio();
            }
        });

        // Boton para exportar los resultados de la carrera a un txt
        exportarATxtButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportarATxt();
            }
        });


    }

    //Cargar las carreras creadas a un txt

    private void cargarCarrerasEnCombo(){
        CarreraComboBox.removeAllItems();

        for (Carreras c : gestorCarreras.listarCarreras()) {
            CarreraComboBox.addItem(c.getNombre());
        }
    }


    private void cargarResultado(){
        String carreraSeleccionada = (String) CarreraComboBox.getSelectedItem();
        if (carreraSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una carrera.");
            return;
        }
        Resultado r = gestorResultados.buscarResultado(carreraSeleccionada);
        if (r == null) {
            JOptionPane.showMessageDialog(this, "Aún no hay resultado para esta carrera.");
            return;
        }

        // Mostrar ganador con tiempo formateado
        ganadorNombreField.setText(r.getGanador());
        ganadorCategoriaField.setText(r.getCategoria());
        ganadorTiempoField.setText(String.format("%.2f seg", r.getTiempo()));  // Formateado aquí también

        // Mostrar podio
        primerLugarField.setText(r.getPodio().get(0));
        segundoLugarField.setText(r.getPodio().get(1));
        tercerLugarField.setText(r.getPodio().get(2));
    }





    //Cargar la informacion de los resultados a la tabla
    private void cargarTabla(){
        // Limpiar la tabla antes de cargar
        modeloTabla.setRowCount(0);

        for (Resultado r : gestorResultados.listarResultados()) {
            // Formatear el tiempo a 2 decimales y agrega "seg" al final
            String tiempoFormateado = String.format("%.2f seg", r.getTiempo());

            modeloTabla.addRow(new Object[]{
                    r.getCarrera(),
                    r.getGanador(),
                    tiempoFormateado,  // formatea el  tiempo a 2 decimales y agrega el (seg)
                    r.getFecha()
            });
        }
    }



    //Mostrar el ganador

    private void mostrarGanador() {
        String carreraSeleccionada = (String) CarreraComboBox.getSelectedItem();
        if (carreraSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una carrera primero.");
            return;
        }

        Resultado r = gestorResultados.buscarResultado(carreraSeleccionada);
        if (r == null) {
            JOptionPane.showMessageDialog(this, "No hay resultados para esta carrera.");
            return;
        }

        // Limpiar y mostrar en el TextArea
        textAreaGanador.setText("");
        textAreaGanador.append("🏆 **INFORMACIÓN DEL GANADOR** 🏆\n");
        textAreaGanador.append("═══════════════════════════════\n\n");
        textAreaGanador.append("📌 Carrera: " + r.getCarrera() + "\n");
        textAreaGanador.append("👑 Ganador: " + r.getGanador() + "\n");
        textAreaGanador.append("📋 Categoría: " + r.getCategoria() + "\n");
        textAreaGanador.append("⏱️  Tiempo: " + String.format("%.2f", r.getTiempo()) + " segundos\n");
        textAreaGanador.append("📅 Fecha: " + r.getFecha() + "\n\n");
        textAreaGanador.append("✨ ¡Felicidades al campeón! ✨");

        // También actualizar los campos de texto
        ganadorNombreField.setText(r.getGanador());
        ganadorCategoriaField.setText(r.getCategoria());
        ganadorTiempoField.setText(String.format("%.2f seg", r.getTiempo()));
    }



    //Muestra el podio al final de la carrera

    private void mostrarPodio() {
        String carreraSeleccionada = (String) CarreraComboBox.getSelectedItem();
        if (carreraSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una carrera primero.");
            return;
        }

        Resultado r = gestorResultados.buscarResultado(carreraSeleccionada);
        if (r == null) {
            JOptionPane.showMessageDialog(this, "No hay resultados para esta carrera.");
            return;
        }

        // Limpiar y mostrar en el TextArea
        textAreaPodio.setText("");
        textAreaPodio.append("🏅 **PODIO COMPLETO** 🏅\n");
        textAreaPodio.append("═════════════════════════════════════════════════\n\n");
        textAreaPodio.append("📌 Carrera: " + r.getCarrera() + "\n");
        textAreaPodio.append("📅 Fecha: " + r.getFecha() + "\n");
        textAreaPodio.append("⏱️  Tiempo del ganador: " + String.format("%.2f", r.getTiempo()) + " seg\n\n");

        textAreaPodio.append("-----------------------------------------------------------------------------\n");
        textAreaPodio.append("                           🥇 **PRIMER LUGAR:**\n");
        textAreaPodio.append("                        " + r.getPodio().get(0) + " - Ganador\n\n\n");

        textAreaPodio.append("🥈 **SEGUNDO LUGAR:**                               "+"🥉**TERCER LUGAR:**\n");
        textAreaPodio.append("   " + r.getPodio().get(1) + "                      "+ r.getPodio().get(2) + "\n\n");



        textAreaPodio.append("══════════════════════════════════════════════════════\n");
        textAreaPodio.append("🎉 ¡Felicidades a los ganadores! 🎉");

        // También actualizar los campos de texto
        primerLugarField.setText(r.getPodio().get(0));
        segundoLugarField.setText(r.getPodio().get(1));
        tercerLugarField.setText(r.getPodio().get(2));
    }

    //Exportar resultados a un archivo txt

    private void exportarATxt() {
        String carreraSeleccionada = (String) CarreraComboBox.getSelectedItem();
        if (carreraSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una carrera primero.");
            return;
        }

        Resultado resultado = gestorResultados.buscarResultado(carreraSeleccionada);
        if (resultado == null) {
            JOptionPane.showMessageDialog(this, "No hay resultados para esta carrera.");
            return;
        }

        try {
            // Crear nombre de archivo basado en la carrera (reemplazar espacios y caracteres especiales)
            String nombreArchivo = "resultado_" + carreraSeleccionada.replaceAll("[^a-zA-Z0-9]", "_") + ".txt";

            java.io.FileWriter writer = new java.io.FileWriter(nombreArchivo);

            // Escribir encabezado
            writer.write("=============================================\n");
            writer.write("        RESULTADOS DE CARRERA - DUCK APP PRO\n");
            writer.write("=============================================\n\n");

            // Información general de la carrera
            writer.write("🏁 **INFORMACIÓN DE LA CARRERA** 🏁\n");
            writer.write("--------------------------------------\n");
            writer.write("Carrera: " + resultado.getCarrera() + "\n");
            writer.write("Categoría: " + resultado.getCategoria() + "\n");
            writer.write("Fecha: " + resultado.getFecha() + "\n\n");

            // Información del ganador
            writer.write("🏆 **GANADOR DE LA CARRERA** 🏆\n");
            writer.write("--------------------------------------\n");
            writer.write("Nombre: " + resultado.getGanador() + "\n");
            writer.write("Tiempo: " + String.format("%.2f", resultado.getTiempo()) + " segundos\n");
            writer.write("Posición: 1° Lugar\n\n");

            // Podio completo
            writer.write("🏅 **PODIO OFICIAL** 🏅\n");
            writer.write("--------------------------------------\n");
            writer.write("🥇 PRIMER LUGAR: " + resultado.getPodio().get(0) + "\n");
            writer.write("🥈 SEGUNDO LUGAR: " + resultado.getPodio().get(1) + "\n");
            writer.write("🥉 TERCER LUGAR: " + resultado.getPodio().get(2) + "\n\n");

            // Información adicional
            writer.write("📊 **INFORMACIÓN ADICIONAL** 📊\n");
            writer.write("--------------------------------------\n");
            writer.write("Fecha de exportación: " + new java.text.SimpleDateFormat("yyyy-MM-dd --- Hora: HH:mm:ss")
                    .format(new java.util.Date()) + "\n");
            writer.write("Archivo generado por: DuckApp Pro\n");
            writer.write("Sistema de gestión de carreras de patos\n\n");

            // Separador final
            writer.write("=============================================\n");
            writer.write("        ¡FELICIDADES A LOS GANADORES!        \n");
            writer.write("=============================================\n");

            writer.close();

            // Mostrar mensaje de exito
            JOptionPane.showMessageDialog(this,
                    "✅ Resultados exportados exitosamente!\n" +
                            "📄 Archivo: " + nombreArchivo + "\n" +
                            "📂 Ubicación: " + new java.io.File(".").getAbsolutePath(),
                    "Exportación Completada",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (java.io.IOException e) {
            JOptionPane.showMessageDialog(this,
                    "❌ Error al exportar archivo:\n" + e.getMessage(),
                    "Error de Exportación",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

}
