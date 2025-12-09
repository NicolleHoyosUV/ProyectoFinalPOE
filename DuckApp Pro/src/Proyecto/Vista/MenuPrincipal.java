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


    //instancia para usar metodo volver al menu pincipal en todas las vistas
    private static MenuPrincipal instancia;

    public MenuPrincipal(){
        setContentPane(menuPanel);
        setTitle("Duck App Pro");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        instancia = this;

        //Se enlazan cada uno de los botones con sus respectivas ventanas
        //Ventana Gestionar carrera
        gestionarCarrerasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Carrera ventana = new Carrera();
                ventana.setVisible(true);
            }
        });
        gestionarParticipantesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Registro ventana = new Registro();
                ventana.setVisible(true);
            }
        });
        simularCarreraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Simulacion ventana = new Simulacion();
                ventana.setVisible(true);
            }
        });
        resultadosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Resultados ventana = new Resultados();
                ventana.setVisible(true);
            }
        });
        estadisticasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }


    //ir menu principal
    public static MenuPrincipal getInstancia() {
        return instancia;
    }

    //metodo par volver al menu principal
    public void volverAlMenuPrincipal() {
        // Asegurarse de mostrar y centrar
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.toFront();
    }

    public static void main(String[] args) {
        new MenuPrincipal().setVisible(true);
    }
}
