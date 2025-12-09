package Proyecto.Vista;

import Proyecto.Controlador.GestorCarreras;
import Proyecto.Modelo.Carreras;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Carrera extends JFrame{
    private JPanel carreraPanel;
    private JTextField NombCarrera;
    private JComboBox comboBox1;
    private JTextField textField1;
    private JButton crearCarreraButton;
    private JTable table1;
    private JButton eliminarCarreraButton;
    private JButton volverAlMenuPrincipalButton;

    private DefaultTableModel modeloTabla;
    private GestorCarreras gestorCarreras;

    // Variable para mantener referencia única
    private static MenuPrincipal instance;




    public Carrera() {
        setContentPane(carreraPanel);
        setTitle("Gestión de Carreras");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //inicializar gestor
        gestorCarreras = new GestorCarreras();



        //configurar tabla
        modeloTabla = new DefaultTableModel(new Object[]{"Nombre", "Categoria", "Fecha"},0);
        table1.setModel(modeloTabla);

        //Accion crear carrea
        crearCarreraButton.addActionListener(e -> crearCarrera());

        //Accion eliminar carrera

        eliminarCarreraButton.addActionListener(e -> eliminarCarrera());

        cargarTabla();

        //Boton para volver al menu
        volverAlMenuPrincipalButton.addActionListener(e -> {
            MenuPrincipal.getInstancia().volverAlMenuPrincipal();
            dispose();
        });
    }

    private void crearCarrera(){
        String nombre = NombCarrera.getText();
        String categoria = comboBox1.getSelectedItem().toString();
        String fecha = textField1.getText();

        if (nombre.isEmpty() || categoria.equals("Seleccione") || fecha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos.");
            return;
        }

        gestorCarreras.crearCarrera(nombre, categoria, fecha);
        cargarTabla();
        limpiarCampos();
    }

    private void eliminarCarrera(){
        int fila = table1.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una carrera a eliminar.");
            return;
        }

        String nombre = table1.getValueAt(fila, 0).toString();
        gestorCarreras.eliminarCarrera(nombre);
        cargarTabla();
    }

    private void cargarTabla(){
        modeloTabla.setRowCount(0);

        ArrayList<Carreras> lista = gestorCarreras.listarCarreras();

        for (Carreras c : lista) {
            modeloTabla.addRow(new Object[]{
                    c.getNombre(),
                    c.getCategoria(),
                    c.getFecha()
            });
        }
    }

    private void limpiarCampos(){
        NombCarrera.setText("");
        comboBox1.setSelectedIndex(0);
        textField1.setText("");
    }


}
