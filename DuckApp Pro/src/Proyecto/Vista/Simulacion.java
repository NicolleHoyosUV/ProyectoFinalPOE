package Proyecto.Vista;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Simulacion extends JFrame{
    private JPanel simulacionPanel;
    private JButton iniciarButton;
    private JButton reiniciarButton;
    private JPanel pistaPanel;
    private JLabel pato1;
    private JLabel pato2;
    private JLabel pato3;
    private JLabel pato4;
    private JLabel pato5;
    private JLabel pato6;

    private Timer timer;
    private final int META = 700;

    public Simulacion(){

        setContentPane(simulacionPanel);
        setSize(900,700);
        setLocationRelativeTo(null);


        // Cargar imágenes de los patos
        cargarImagen(pato1, "/pato1.png");
        cargarImagen(pato2, "/pato2.png");
        cargarImagen(pato3, "/pato3.png");
        cargarImagen(pato4, "/pato4.png");
        cargarImagen(pato5, "/pato1.png");
        cargarImagen(pato6, "/pato2.png");

        //posiciones iniciales
        resetPosiciones();

        // Acción de iniciar la carrera
        iniciarButton.addActionListener(e -> iniciarCarrera());

        // Acción de reiniciar todo
        reiniciarButton.addActionListener(e -> resetPosiciones());
    }

    //-------Metodo para cargas las imagenes
    private void cargarImagen(JLabel pato,String ruta){
        ImageIcon icon = new ImageIcon(getClass().getResource(ruta));
        Image img = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        pato.setIcon(new ImageIcon(img));
    }

    // ---------- POSICIÓN INICIAL DE CADA PATO ---------- //
    private void resetPosiciones() {
        pato1.setLocation(8, 15);
        pato2.setLocation(8, 110);
        pato3.setLocation(8, 205);
        pato4.setLocation(8, 300);
        pato5.setLocation(8, 395);
        pato6.setLocation(8, 500);
    }

    // ---------- INICIAR CARRERA ---------- //
    private void iniciarCarrera() {

        timer = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                moverPato(pato1, "Pato 1");
                moverPato(pato2, "Pato 2");
                moverPato(pato3, "Pato 3");
                moverPato(pato4, "Pato 4");
                moverPato(pato5, "Pato 5");
                moverPato(pato6, "Pato 6");
            }
        });
        timer.start();
    }

    // ---------- MOVER CADA PATO ---------- //
    private void moverPato(JLabel pato, String nombre) {
        int avance = (int)(Math.random() * 8) + 4;
        pato.setLocation(pato.getX() + avance, pato.getY());

        if (pato.getX() >= META) {
            timer.stop();
            JOptionPane.showMessageDialog(null, "Ganador: " + nombre);
        }
    }
}
