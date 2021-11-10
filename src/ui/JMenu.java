package ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JMenu extends JFrame
{
    //Variables
    JButton login;
    JLabel titulo;

    public static void main(String argv[])
    {
        new JMenu();
    }

    public JMenu()
    {
        super("ComillasLibrary");

        //Instanciar variables
        login = new JButton("Login");
        titulo = new JLabel("Menu Principal");
        Font fontTitulo = new Font("Arial",Font.BOLD,20);

        //Paneles
        JPanel pnlNorth = new JPanel();

        //Modificacion fuentes
        titulo.setFont(fontTitulo);

        //Norte
        pnlNorth.setLayout(new GridLayout(2,1));
        pnlNorth.add(titulo);
        pnlNorth.add(login);
        this.add(pnlNorth, BorderLayout.NORTH);

        //Ventana
        this.setSize(800,800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        //Funciones botones
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                dispose();
                new JLogin();
            }
        });
    }
    }


