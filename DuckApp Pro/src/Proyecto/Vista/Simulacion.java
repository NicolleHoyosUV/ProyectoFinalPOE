package Proyecto.Vista;

import Proyecto.Controlador.GestorCarreras;
import Proyecto.Controlador.GestorParticipantes;
import Proyecto.Controlador.GestorResultados;
import Proyecto.Modelo.Carreras;
import Proyecto.Modelo.Participantes;
import Proyecto.Modelo.Resultado;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;


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
    private JLabel meta;

    private Timer timer;
    private final int META = 700;

    //Gestores
    private GestorResultados gestorResultados;
    private GestorCarreras gestorCarreras;
    private GestorParticipantes gestorParticipantes;

    // Variables para almacenar información real
    private Carreras ultimaCarrera;
    private ArrayList<Participantes> participantesCarrera;
    private String nombreCarreraActual;
    private String categoriaCarreraActual;

    //Tiempos
    private long tiempoInicio;
    private long tiempoFin;

    public Simulacion() {

        setContentPane(simulacionPanel);
        setSize(900, 700);
        setLocationRelativeTo(null);

        // Inicializar gestores
        gestorResultados = new GestorResultados();
        gestorCarreras = new GestorCarreras();
        gestorParticipantes = new GestorParticipantes();


        cargarInformacionCarrera();
        cargarParticipantes();

        // Crea y pasa al controlador
        JLabel[] patos = new JLabel[]{pato1, pato2, pato3, pato4, pato5, pato6};

        // Cargar imágenes de los patos
        cargarImagen(pato1, "/pato1.png");
        cargarImagen(pato2, "/pato2.png");
        cargarImagen(pato3, "/pato3.png");
        cargarImagen(pato4, "/pato1.png");
        cargarImagen(pato5, "/pato2.png");
        cargarImagen(pato6, "/pato3.png");

        // Cargar imagen de la META
        cargarMeta();

        // Asegurar que los patos estén delante de la meta
        pistaPanel.setComponentZOrder(pato1, 0);
        pistaPanel.setComponentZOrder(pato2, 0);
        pistaPanel.setComponentZOrder(pato3, 0);
        pistaPanel.setComponentZOrder(pato4, 0);
        pistaPanel.setComponentZOrder(pato5, 0);
        pistaPanel.setComponentZOrder(pato6, 0);

        //posiciones iniciales
        resetPosiciones();

        // Acción de iniciar la carrera
        iniciarButton.addActionListener(e -> iniciarCarrera());

        // Acción de reiniciar todo
        reiniciarButton.addActionListener(e -> resetPosiciones());

        //Boton paraa volver al menu principal
        volverAlMenuPrincipalButton.addActionListener(e -> {
            MenuPrincipal.getInstancia().volverAlMenuPrincipal();
            dispose();
        });

        //Boton para ver resultados
        verResultadosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verResultados();
            }
        });
    }

    // ---------- CARGAR META ---------- //
    private void cargarMeta() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/ImgMeta.png"));
        Image img = icon.getImage().getScaledInstance(60, 550, Image.SCALE_SMOOTH);
        meta.setIcon(new ImageIcon(img));
    }

    // Cargar informacion de la última carrera creada
    private void cargarInformacionCarrera() {
        ultimaCarrera = gestorCarreras.obtenerUltimaCarrera();

        if (ultimaCarrera != null) {
            nombreCarreraActual = ultimaCarrera.getNombre();
            categoriaCarreraActual = ultimaCarrera.getCategoria();
        } else {
            nombreCarreraActual = "Carrera de Exhibición";
            categoriaCarreraActual = "General";
        }
    }

    // Cargar participantes (Ultimos 6 registrados)
    private void cargarParticipantes() {
        // Obtener los ultimos 6 participantes registrados
        participantesCarrera = gestorParticipantes.obtenerUltimosParticipantes(6);

        // Si hay menos de 6, completar con participantes ficticios
        if (participantesCarrera.size() < 6) {
            int faltantes = 6 - participantesCarrera.size();
            for (int i = 0; i < faltantes; i++) {
                // Crear participante ficticio
                participantesCarrera.add(new Participantes(
                        "Participante " + (i + 1),
                        20 + i,
                        "FICT" + (1000 + i),
                        100 + i,
                        "General"
                ));
            }
        }
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
        tiempoInicio = System.currentTimeMillis(); // ⏱️ INICIO REAL

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
        int avance = (int) (Math.random() * 10) + 1;
        pato.setLocation(pato.getX() + avance, pato.getY());

        if (pato.getX() + pato.getWidth() >= meta.getX()) {
            timer.stop();
            tiempoFin = System.currentTimeMillis(); // ⏱️ FIN REAL
            JOptionPane.showMessageDialog(null, "¡¡Ha ganado la carrera!!  " + nombre);
            mostrarPodio();
        }
    }

    // Obtener tiempo real en segundos
    private double obtenerTiempoReal() {
        return (tiempoFin - tiempoInicio) / 1000.0;
    }


    // Mostrar podio y guardar resultados
    private void mostrarPodio() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }

        JLabel[] patosArray = {pato1, pato2, pato3, pato4, pato5, pato6};

        // Ordenar por posiciones (mayor a menor)
        Arrays.sort(patosArray, new Comparator<JLabel>() {
            @Override
            public int compare(JLabel p1, JLabel p2) {
                return Integer.compare(p2.getX(), p1.getX());
            }
        });

        // Obtener índices de los patos en el podio
        int[] indicesPodio = new int[3];
        for (int i = 0; i < 3; i++) {
            indicesPodio[i] = obtenerIndicePato(patosArray[i]);
        }

        // Obtener nombres reales de los participantes en el podio
        String primerLugar = obtenerNombreParticipante(indicesPodio[0]);
        String segundoLugar = obtenerNombreParticipante(indicesPodio[1]);
        String tercerLugar = obtenerNombreParticipante(indicesPodio[2]);

        // Calcular tiempo simulado basado en la distancia recorrida
        //double tiempoGanador = calcularTiempo(patosArray[0].getX());
        double tiempoGanador = obtenerTiempoReal();

        // Crear ArrayList para el podio
        ArrayList<String> podio = new ArrayList<>();
        podio.add(primerLugar);
        podio.add(segundoLugar);
        podio.add(tercerLugar);

        // Obtener fecha actual
        String fechaActual = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        // Crear objeto Resultado con información real
        Resultado resultado = new Resultado(
                nombreCarreraActual,      // Nombre de la última carrera
                primerLugar,              // Ganador (participante real)
                categoriaCarreraActual,   // Categoría de la última carrera
                tiempoGanador,           // Tiempo calculado
                podio,                   // Podio completo
                fechaActual              // Fecha de la simulación
        );

        // Guardar en gestor (serializara automáticamente en .dat)
        gestorResultados.guardarResultado(resultado);

        // Mostrar mensaje del podio con información REAL
        mostrarMensajePodio(resultado, patosArray);
    }

    // Obtener indice del pato en el arreglo
    private int obtenerIndicePato(JLabel pato) {
        JLabel[] patos = {pato1, pato2, pato3, pato4, pato5, pato6};
        for (int i = 0; i < patos.length; i++) {
            if (patos[i] == pato) {
                return i;
            }
        }
        return 0;
    }

    // Obtener nombre del participante real
    private String obtenerNombreParticipante(int index) {
        if (index < participantesCarrera.size()) {
            Participantes p = participantesCarrera.get(index);
            return p.getNombre() + " (Pato #" + p.getNumeroPato() + ")";
        }
        return "Participante " + (index + 1) + " (Genérico)";
    }

    // Mostrar mensaje del podio
    private void mostrarMensajePodio(Resultado resultado, JLabel[] patosArray) {
        StringBuilder mensaje = new StringBuilder();

        mensaje.append("🏆 **RESULTADOS OFICIALES** 🏆\n\n");
        mensaje.append("📌 Carrera: ").append(resultado.getCarrera()).append("\n");
        mensaje.append("📋 Categoría: ").append(resultado.getCategoria()).append("\n");
        mensaje.append("📅 Fecha: ").append(resultado.getFecha()).append("\n\n");

        mensaje.append("🥇 **PRIMER LUGAR:**\n");
        mensaje.append("   ").append(resultado.getGanador()).append("\n\n");

        mensaje.append("🥈 **SEGUNDO LUGAR:**\n");
        mensaje.append("   ").append(resultado.getPodio().get(1)).append("\n\n");

        mensaje.append("🥉 **TERCER LUGAR:**\n");
        mensaje.append("   ").append(resultado.getPodio().get(2)).append("\n\n");

        mensaje.append("⏱️  Tiempo del ganador: ").append(String.format("%.2f", resultado.getTiempo())).append(" segundos\n\n");

        mensaje.append("✅ **Información guardada en el sistema**\n");
        mensaje.append("📁 Archivo: resultados.dat\n\n");

        mensaje.append("🏁 **RESULTADOS COMPLETOS** 🏁\n");
        String[] posiciones = {"1°", "2°", "3°", "4°", "5°", "6°"};

        for (int i = 0; i < patosArray.length; i++) {
            int index = obtenerIndicePato(patosArray[i]);
            String nombre = obtenerNombreParticipante(index);
            mensaje.append(posiciones[i]).append(" - ").append(nombre)
                    .append(" (Distancia: ").append(patosArray[i].getX()).append(" px)\n");
        }

        mensaje.append("\n🎉 ¡Felicidades a los ganadores! 🎉");

        JOptionPane.showMessageDialog(
                this,
                mensaje.toString(),
                "🏁 ¡Carrera Finalizada!",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
    //cuando se oprime le boton ver resultados entonces cierra la vista simulacion y abre la vista resultados
    private void verResultados() {
        this.dispose(); // Cierra la vista actual
        Resultados vr = new Resultados();
        vr.setVisible(true);
        vr.setLocationRelativeTo(null);
    }
}
