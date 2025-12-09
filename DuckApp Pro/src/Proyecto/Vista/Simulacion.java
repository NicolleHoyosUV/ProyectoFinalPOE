package Proyecto.Vista;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Comparator;


public class Simulacion extends JFrame {
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
    private JButton volverAlMenuPrincipalButton;
    private JButton verResultadosButton;

    private Timer timer;
    private final int META = 700;


    public Simulacion() {

        setContentPane(simulacionPanel);
        setSize(900, 700);
        setLocationRelativeTo(null);

        // crea y pasa al controlador
        JLabel[] patos = new JLabel[]{pato1, pato2, pato3, pato4, pato5, pato6};




        // Cargar imágenes de los patos
        cargarImagen(pato1, "/pato1.png");
        cargarImagen(pato2, "/pato2.png");
        cargarImagen(pato3, "/pato3.png");
        cargarImagen(pato4, "/pato4.png");
        cargarImagen(pato5, "/pato1.png");
        cargarImagen(pato6, "/pato2.png");

        //posiciones iniciales
        resetPosiciones();

        //nuevo para la meta


        // Acción de iniciar la carrera
        iniciarButton.addActionListener(e -> iniciarCarrera());

        // Acción de reiniciar todo
        reiniciarButton.addActionListener(e -> resetPosiciones());

        //Boton paraa volver al menu principal
        volverAlMenuPrincipalButton.addActionListener(e -> {
            MenuPrincipal.getInstancia().volverAlMenuPrincipal();
            dispose();
        });
        verResultadosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verResultados();
            }
        });
    }

    //-------Metodo para cargas las imagenes
    private void cargarImagen(JLabel pato, String ruta) {
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
        int avance = (int) (Math.random() * 8) + 4;
        pato.setLocation(pato.getX() + avance, pato.getY());

        if (pato.getX() >= META) {
            timer.stop();
            JOptionPane.showMessageDialog(null, "¡¡Ha ganado la carrera!!  " + nombre);
            mostrarPodio();
        }
    }


    private void mostrarPodio() {

        if (timer != null && timer.isRunning()) {
            timer.stop();
        }

        JLabel[] patosArray = {pato1, pato2, pato3, pato4, pato5, pato6};

        // Ordenar por posición X (mayor a menor)
        Arrays.sort(patosArray, new Comparator<JLabel>() {
            @Override
            public int compare(JLabel p1, JLabel p2) {
                return Integer.compare(p2.getX(), p1.getX());
            }
        });

        // Construir mensaje tipo PODIO
        StringBuilder mensaje = new StringBuilder();

        mensaje.append("🏆 **PODIO DE LA CARRERA** 🏆\n\n");

        mensaje.append("        🥈  Segundo lugar: " + obtenerNombre(patosArray[1]) + "\n");
        mensaje.append("🥇  Primer lugar:  " + obtenerNombre(patosArray[0]) + "\n");
        mensaje.append("        🥉  Tercer lugar:  " + obtenerNombre(patosArray[2]) + "\n\n");

        mensaje.append("-----------------------------------------\n");
        mensaje.append("   🏁 RESULTADOS COMPLETOS 🏁\n\n");

        String[] posiciones = {"1°", "2°", "3°", "4°", "5°", "6°"};

        for (int i = 0; i < patosArray.length; i++) {
            mensaje.append(posiciones[i] + " - " + obtenerNombre(patosArray[i]) +
                    " (Distancia: " + patosArray[i].getX() + " px)\n");
        }

        JOptionPane.showMessageDialog(
                this,
                mensaje.toString(),
                "🎉 ¡Carrera Finalizada!",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    //metodo para obtner los nombres de los patos
    private String obtenerNombre(JLabel pato) {
        if (pato == pato1) return "Pato 1";
        if (pato == pato2) return "Pato 2";
        if (pato == pato3) return "Pato 3";
        if (pato == pato4) return "Pato 4";
        if (pato == pato5) return "Pato 5";
        return "Pato 6";
    }

    //cuando se oprime le boton ver resultados entonces cierra la vista simulacion y abre la vista resultados
    private void verResultados() {
        this.dispose(); // Cierra la vista actual
        Resultados vr = new Resultados();
        vr.setVisible(true);
        vr.setLocationRelativeTo(null);
    }





}
