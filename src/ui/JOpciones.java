package ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JOpciones extends JFrame
{
    //Variables
    JButton reservar;
    JButton cancelar;
    JButton perfil;
    JButton salir;

    public static void main(String argv[])
    {
        new JOpciones("default");
    }

    public JOpciones(String usuario)
    {
        super("ComillasLibrary: Menu Principal");

        //Instanciar variables
        reservar = new JButton("Reserva un asiento");
        cancelar = new JButton("Cancelar la reserva");
        perfil = new JButton("Perfil");
        salir = new JButton("Salir");
        Font fontTitulo = new Font("Arial",Font.BOLD,20);

        //Paneles
        JPanel pnlNorth = new JPanel();
        JPanel pnlSouth = new JPanel();

        //Modificacion fuentes
        //titulo.setFont(fontTitulo);

        //Norte
        pnlNorth.setLayout(new GridLayout(1, 3));
        pnlNorth.add(reservar);
        pnlNorth.add(cancelar);
        pnlNorth.add(perfil);
        this.add(pnlNorth, BorderLayout.NORTH);

        //Sur
        pnlSouth.add(salir);
        this.add(pnlSouth, BorderLayout.SOUTH);

        //Ventana
        this.setSize(800,800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        //Funciones botones

        reservar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new JReserva(usuario);
            }
        });

        // Added this to go to the user's profile
        perfil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
                new JPerfil(usuario);
            }
        });


        salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
                new JLogin();
            }
        });


    }
}