package Proyecto.Vista;

import Proyecto.Controlador.GestorCarreras;
import Proyecto.Controlador.GestorResultados;
import Proyecto.Modelo.Carreras;
import Proyecto.Modelo.Resultado;

import javax.swing.*;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

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

    private GestorCarreras gestorCarreras;
    private GestorResultados gestorResultados;
    private DefaultTableModel modeloTabla;

    public Resultados(){
        setContentPane(ResultadosPanel);
        setTitle("Resultados de Carreras");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        gestorCarreras = new GestorCarreras();
        gestorResultados = new GestorResultados();

        modeloTabla = new DefaultTableModel(new Object[]{"Carrera", "Ganador", "Tiempo", "Fecha"}, 0);

        resultadosTabla.setModel(modeloTabla);

        cargarCarrerasEnCombo();

        cargarResultadoButton.addActionListener(e -> cargarResultado());
        exportarATxtButton.addActionListener(e -> gestorResultados.exportarResultados());

        cargarTabla();
    }

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

        // Mostrar ganador
        ganadorNombreField.setText(r.getGanador());
        ganadorCategoriaField.setText(r.getCategoria());
        ganadorTiempoField.setText(r.getTiempo() + " seg");

        // Mostrar podio
        primerLugarField.setText(r.getPodio().get(0));
        segundoLugarField.setText(r.getPodio().get(1));
        tercerLugarField.setText(r.getPodio().get(2));

    }

    private void cargarTabla(){
        for (Resultado r : gestorResultados.listarResultados()) {
            modeloTabla.addRow(new Object[]{
                    r.getCarrera(),
                    r.getGanador(),
                    r.getTiempo(),
                    r.getFecha()
            });
        }
    }
}
