package ui;

import dtc.isw.client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class JReserva extends JFrame {

    //Constantes
    public static ArrayList<String> HORAS = new ArrayList<String>(Arrays.asList("08:00","08:30","09:00","09:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00","14:30","15:00"));

    //Variables
    JComboBox biblioteca;
    JComboBox planta;
    JComboBox mesa;
    JComboBox horaIn;
    JComboBox horaFin;
    JButton salir;
    JButton reservar;
    JLabel b;
    JLabel p;
    JLabel m;
    JLabel hi;
    JLabel hf;
    HashMap<String,Object> h = new HashMap<String,Object>();
    ArrayList<Object> noRepeat = new ArrayList<Object>();

    public void main(String argv[])
    {
        new JReserva("default");
    }

    public JReserva(String usuario)
    {
        super("Reserva");

        //Instanciar variables
        biblioteca = new JComboBox();
        planta = new JComboBox();
        mesa = new JComboBox();
        horaIn = new JComboBox();
        horaFin = new JComboBox();
        salir = new JButton("Salir");
        reservar = new JButton("Reservar el asiento");
        b = new JLabel("Biblioteca:");
        p = new JLabel("Planta:");
        m = new JLabel("Mesa:");
        hi = new JLabel("Horario Inicial:");
        hf = new JLabel("Horario Final:");
        Font fontTexto = new Font("Arial", Font.BOLD, 12);

        //Paneles
        JPanel pnlNorth = new JPanel();
        JPanel pnlCenter = new JPanel();
        JPanel pnlSouth = new JPanel();
        JPanel pnlEast = new JPanel();

        //Modificacion fuentes
        biblioteca.setFont(fontTexto);
        planta.setFont(fontTexto);
        mesa.setFont(fontTexto);
        horaIn.setFont(fontTexto);
        horaFin.setFont(fontTexto);

        //Configuracion ComboBoxes
        //Valores nulos
        biblioteca.addItem("");
        planta.addItem("");
        mesa.addItem("");
        horaIn.addItem("");
        horaFin.addItem("");

        //biblioteca
        biblioteca.addItem("Calle de Alberto Aguilera 25");
        biblioteca.addItem("Otro");

        //planta
        //planta.addItem("3ª Planta");
        //planta.addItem("4ª Planta");
        //planta.addItem("5ª Planta");

        biblioteca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Previous options eliminated and replaced
                switch(planta.getItemCount())
                {
                    case 1:
                        System.out.println("No hay valores guardados");
                        break;

                    default:
                        planta.removeAllItems();
                        planta.addItem("");
                        break;
                }

                switch(mesa.getItemCount())
                {
                    case 1:
                        System.out.println("No hay valores guardados");
                        break;

                    default:
                        mesa.removeAllItems();
                        mesa.addItem("");
                        break;
                }

                Client client = new Client();
                noRepeat = new ArrayList<Object>();
                HashMap<String,Object> session = new HashMap<>();
                session.put("table","listaasientos");
                session.put("condicion","ocupado=false AND biblioteca='"+ biblioteca.getSelectedItem().toString()+"'");
                session.put("columna", 2);
                client.enviar("/getColumnInfo",session);
                h = (HashMap<String,Object>) session.get("Respuesta");
                for(Integer i = 0; i< h.size();i++)
                {
                    String s = (String) h.get(i.toString());
                    if(!noRepeat.contains(s))
                    {
                        planta.addItem(s);
                        noRepeat.add(s);
                        System.out.println(noRepeat.contains(h.get(i.toString())));
                    }
                }
            }
        });

        //mesa
        planta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client client = new Client();
                HashMap<String,Object> session = new HashMap<>();
                session.put("table","listaasientos");
                if(biblioteca.getSelectedItem() != null && planta.getSelectedItem() != null)
                {
                    session.put("condicion","ocupado=false AND biblioteca='" + biblioteca.getSelectedItem().toString() + "' AND planta='" + planta.getSelectedItem().toString() + "'");
                }
                else
                {
                    session.put("condicion","ocupado = false AND ocupado = true"); // Resultado nulo
                }
                session.put("columna", 3);
                client.enviar("/getColumnInfo",session);
                h = (HashMap<String,Object>) session.get("Respuesta");
                for(Integer i = 0; i< h.size();i++)
                {
                    String s = (String) h.get(i.toString());
                    if(!noRepeat.contains(s))
                    {
                        mesa.addItem(s);
                        noRepeat.add(s);
                        System.out.println(noRepeat.contains(h.get(i.toString())));
                    }
                }
            }
        });

        //Norte
        pnlNorth.setLayout(new GridLayout(2, 4));
        pnlNorth.add(b);
        pnlNorth.add(p);
        pnlNorth.add(m);
        pnlNorth.add(salir);
        pnlNorth.add(biblioteca);
        pnlNorth.add(planta);
        pnlNorth.add(mesa);
        this.add(pnlNorth, BorderLayout.NORTH);

        //Sur
        pnlSouth.add(hi);
        pnlSouth.add(horaIn);
        pnlSouth.add(hf);
        pnlSouth.add(horaFin);
        this.add(pnlSouth, BorderLayout.SOUTH);

        //Este
        pnlEast.add(reservar);
        this.add(pnlEast,BorderLayout.EAST);

        //Ventana
        this.setSize(800, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);




        //Funciones botones
        salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
                new JOpciones(usuario);
            }
        });
    }
}
