package Proyecto.Vista;

import Proyecto.Controlador.GestorParticipantes;
import Proyecto.Modelo.Participantes;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Registro extends JFrame{   //La clase herada del Jframe
    private JPanel registroPanel;
    private JTextField NombreField;
    private JTextField EdadField;
    private JTextField DocumentoField;
    private JTextField N_PatosField;
    private JComboBox CategoriacomboBox;
    private JButton registrarPerticipanteButton;
    private JTable ParticipantesTabla;
    private JButton eliminarParticipanteButton;
    private JButton volverAlMenuPrincipalButton;
    private JLabel lblTitulo;

    private DefaultTableModel modeloTabla;
    private GestorParticipantes gestorParticipantes;

    public Registro (){
        setContentPane(registroPanel);
        setTitle("Registro de Participantes");
        setSize(750, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        gestorParticipantes = new GestorParticipantes();

        //Se crea el modelo de la tabla
        modeloTabla = new DefaultTableModel(
                new Object[]{"Nombre", "Edad", "Documento", "N° Pato", "Categoría"}, 0);

        ParticipantesTabla.setModel(modeloTabla);
        registrarPerticipanteButton.addActionListener(e -> registrarParticipante());
        eliminarParticipanteButton.addActionListener(e -> eliminarParticipante());
        cargarTabla();

        lblTitulo.setText("🗒️ REGISTRO DE CARRERAS 🗒️");

        //Boton para volver al menu principal
        volverAlMenuPrincipalButton.addActionListener(e -> {
            MenuPrincipal.getInstancia().volverAlMenuPrincipal();
            dispose();
        });
    }

    //Metodo para registrar el participante

    private void registrarParticipante(){
        String nombre = NombreField.getText();
        String edadTxt = EdadField.getText();
        String documento = DocumentoField.getText();
        String numeroPatoTxt = N_PatosField.getText();
        String categoria = CategoriacomboBox.getSelectedItem().toString();

        if (nombre.isEmpty() || edadTxt.isEmpty() || documento.isEmpty() || numeroPatoTxt.isEmpty() || categoria.equals("Seleccione")) {

            JOptionPane.showMessageDialog(this, "Complete todos los campos.");
            return;
        }

        int edad;
        int numeroPato;
        try {
            edad = Integer.parseInt(edadTxt);
            numeroPato = Integer.parseInt(numeroPatoTxt);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Edad y número de pato deben ser números.");
            return;
        }

        boolean exito = gestorParticipantes.registrarParticipante(
                nombre, edad, documento, numeroPato, categoria
        );

        if (!exito) {
            JOptionPane.showMessageDialog(this, "Documento o número de pato ya registrados.");
            return;
        }

        cargarTabla();
        limpiarCampos();

        // Opcional: Mensaje de confirmación
        JOptionPane.showMessageDialog(this,
                "✅ Participante registrado exitosamente.\n" +
                        "Datos guardados automáticamente en 'participantes.dat'");
    }

    //Eliminar un participante por seleccion del usuario

    private void eliminarParticipante(){
        int fila = ParticipantesTabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un participante.");
            return;
        }

        String documento = ParticipantesTabla.getValueAt(fila, 2).toString();
        gestorParticipantes.eliminarParticipante(documento);

        cargarTabla();
    }

    //cargar la informacion en la tabla

    private void cargarTabla(){
        modeloTabla.setRowCount(0);

        ArrayList<Participantes> lista = gestorParticipantes.listarParticipantes();

        for (Participantes p : lista) {
            modeloTabla.addRow(new Object[]{
                    p.getNombre(),
                    p.getEdad(),
                    p.getDocumento(),
                    p.getNumeroPato(),
                    p.getCategoria()
            });
        }
    }

    //limpiar campos una vez creado el registro

    private void limpiarCampos(){
        NombreField.setText("");
        EdadField.setText("");
        DocumentoField.setText("");
        N_PatosField.setText("");
        CategoriacomboBox.setSelectedIndex(0);
    }
}
