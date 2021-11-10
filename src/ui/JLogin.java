package ui;

import dtc.isw.client.Client;
import dtc.isw.domain.Customer;
import util.JInfoBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class JLogin extends JFrame {
    //Variables
    JTextField usuario0;
    JTextField password0;
    JLabel usuario1;
    JLabel password1;
    JButton confirmar;

    public static void main(String argv[]) {
        new JLogin();
    }

    public JLogin() {
        super("Login");

        //Instanciar variables
        usuario0 = new JTextField(50);
        usuario1 = new JLabel("Usuario: ");
        password0 = new JTextField(50);
        password1 = new JLabel("Password: ");
        confirmar = new JButton("Confirmar");
        Font fontTexto = new Font("Arial", Font.BOLD, 12);

        //Paneles
        JPanel pnlCenter = new JPanel();
        JPanel pnlSouth = new JPanel();

        //Modificacion fuentes
        usuario1.setFont(fontTexto);
        password1.setFont(fontTexto);

        //Centro
        pnlCenter.setLayout(new GridLayout(2, 2));
        pnlCenter.add(usuario1);
        pnlCenter.add(usuario0);
        pnlCenter.add(password1);
        pnlCenter.add(password0);
        this.add(pnlCenter, BorderLayout.CENTER);

        //Sur
        pnlSouth.add(confirmar);
        this.add(pnlSouth, BorderLayout.SOUTH);

        //Ventana
        this.setSize(800, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        //Funciones botones
        confirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Client client = new Client();
                HashMap<String,Object> session = new HashMap<>();
                String u = usuario0.getText();
                String p = password0.getText();
                Customer user = new Customer(u,p);
                session.put("id",user);
                client.enviar("/loginUser",session);

                int resultado = (Integer) session.get("Respuesta");
                //System.out.println(resultado);
                if(resultado == 1) // Ningun problema
                {
                    new JOpciones(u);
                    dispose();
                }
                else //Username/Password incorrecto
                {
                    password0.setText("");
                    JInfoBox.infoBox("Error","Error: Incorrect Login or Password.");

                }
            }
        });
    }
}
