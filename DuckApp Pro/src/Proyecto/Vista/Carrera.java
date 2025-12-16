package Proyecto.Vista;

import Proyecto.Controlador.GestorCarreras;
import Proyecto.Modelo.Carreras;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;   //para colocar la fecha por defecto
import java.util.ArrayList;
import java.util.Date;

public class Carrera extends JFrame{
    private JPanel carreraPanel;
    private JTextField NombCarrera;
    private JComboBox comboBox1;
    private JTextField textField1;
    private JButton crearCarreraButton;
    private JTable table1;
    private JButton eliminarCarreraButton;
    private JButton volverAlMenuPrincipalButton;
    private JLabel lblTitulo;
    private JLabel fondoLabel;

    private DefaultTableModel modeloTabla;
    private GestorCarreras gestorCarreras;

    // Variable para mantener referencia única
    private static MenuPrincipal instance;




    public Carrera() {
        // Cargar imagen
        ImageIcon icon = new ImageIcon(getClass().getResource("/carrera2.jpg"));
        Image img = icon.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
        fondoLabel.setIcon(new ImageIcon(img));

        // Usar el fondo como contenedor
        setContentPane(fondoLabel);
        fondoLabel.setLayout(new BorderLayout());
        fondoLabel.add(carreraPanel);

        // Panel transparente
        carreraPanel.setOpaque(false);


        setTitle("Gestión de Carreras");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //inicializar gestor
        gestorCarreras = new GestorCarreras();


        establecerFechaActual();

        //configurar tabla
        modeloTabla = new DefaultTableModel(new Object[]{"Nombre", "Categoria", "Fecha"},0);
        table1.setModel(modeloTabla);

        //Accion crear carrea
        crearCarreraButton.addActionListener(e -> crearCarrera());

        lblTitulo.setText("🏁  CARRERA  🏁 ");

        //Accion eliminar carrera
        eliminarCarreraButton.addActionListener(e -> eliminarCarrera());

        cargarTabla();

        //Boton para volver al menu
        volverAlMenuPrincipalButton.addActionListener(e -> {
            MenuPrincipal.getInstancia().volverAlMenuPrincipal();
            dispose();
        });

    }



    // Método para establecer fecha actual
    private void establecerFechaActual() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fechaActual = sdf.format(new Date());
        textField1.setText(fechaActual);
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
