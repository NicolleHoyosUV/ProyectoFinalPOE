package Proyecto.Vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipal extends JFrame{
    private JButton gestionarCarrerasButton;
    private JButton gestionarParticipantesButton;
    private JButton simularCarreraButton;
    private JButton estadisticasButton;
    private JButton resultadosButton;
    private JPanel menuPanel;
    private JButton salirButton;


    //Instancia para usar metodo volver al menu pincipal en todas las vistas
    private static MenuPrincipal instancia;

    public MenuPrincipal(){
        setContentPane(menuPanel);
        setTitle("Duck App Pro");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        instancia = this;

        //Se enlazan cada uno de los botones con sus respectivas ventanas
        gestionarCarrerasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Carrera ventana = new Carrera();
                ventana.setVisible(true);
            }
        });

        //Boton gestionar participantes
        gestionarParticipantesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Registro ventana = new Registro();
                ventana.setVisible(true);
            }
        });

        //Boton simular carrera
        simularCarreraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Simulacion ventana = new Simulacion();
                ventana.setVisible(true);
            }
        });

        //Boton de mostrar resultados
        resultadosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Resultados ventana = new Resultados();
                ventana.setVisible(true);
            }
        });

        //Boton de mostrar estadisticas
        estadisticasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VistaEstadistica();
            }
        });

        //Boton para salir de la ventana principal
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SalirDeLaApp();
            }
        });

    }


    //Ir menu principal
    public static MenuPrincipal getInstancia() {
        return instancia;
    }

    //Metodo par volver al menu principal
    public void volverAlMenuPrincipal() {
        // Asegurarse de mostrar y centrar
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.toFront();
    }

    public static void main(String[] args) {
        new MenuPrincipal().setVisible(true);
    }

    //Muestra la vista de estadistica
    private void VistaEstadistica(){


        JFrame frame = new JFrame("Estadisticas");
        frame.setContentPane(new Estadisticas().getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

    }

    //Salir dela ventana principal
    private void SalirDeLaApp(){

        System.exit(0);
        //dispose();  //tambien se pude usar
    }
}
