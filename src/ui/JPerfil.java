package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import dtc.isw.client.Client;

/**
 * JPerfil class : displays the user's profile
 */
public class JPerfil extends JFrame {
    JButton modificar;
    JButton volver;
    int numVars = 4;
    JLabel[] labels = new JLabel[numVars];
    JLabel[] variables = new JLabel[numVars];
    ArrayList<String> listVars = new ArrayList<>();


    /**
     * Constructor of the Perfil.java class
     * @param usuario user who logged in previously
     */
    public JPerfil (String usuario) {
        super("Perfil");

        // Initialize variables
        labels[0] = new JLabel("Username");
        labels[1] = new JLabel("Password");
        labels[2] = new JLabel("Sitios reserved");
        labels[3] = new JLabel("E-mail address");

        // Make the labels go bold
        for (JLabel label : labels) {
            Font f = label.getFont();
            label.setFont(f.deriveFont(f.getStyle() ^ Font.BOLD));
        }


        Client cl = new Client();

        // Make the requests to get all the columns we want from the database
        for (int i = 0; i <= numVars ; ++i) {
            HashMap<String, Object> map = new HashMap<>();

            map.put("table", "listausuarios");
            map.put("condicion", "username='" + usuario + "'");
            map.put("columna", i);

            cl.enviar("/getColumnInfo", map);

            // Parse the response so that it fits to our requirements
            String respuestaUsername = map.get("Respuesta").toString();
            String formatted_resp = respuestaUsername.substring(respuestaUsername.indexOf("=")+1, respuestaUsername.indexOf('}'));

            if (!formatted_resp.equals("{")){
                listVars.add(formatted_resp);
            }

        }

        // Create the label fields (second column)
        for (int i=0;i < numVars; ++i){
            variables[i] = new JLabel();
        }

        // Put the values for the selected user to form the labels
        for (int i = 0; i < listVars.size(); ++i){

            variables[i] = new JLabel(listVars.get(i));

            if (variables[i].getText().equals("null")) {
                variables[i] = new JLabel("Not provided");
            }
            else if (labels[i].getText().equals("Password")) {
                variables[i] = new JLabel("******");
            }

        }


        // Associate each label with its text field
        for (int i = 0; i < numVars; ++i){
            labels[i].setLabelFor(variables[i]);
        }


        // Initialize the panel and the corresponding layout
        JPanel title = new JPanel();
        JPanel pnlSouth = new JPanel();

        // Call the method that does all the layout work
        JPanel form = createForm(labels, variables,10,10,10,10);


        volver = new JButton("Volver atrás");

        volver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
                new JOpciones(usuario);
            }
        });
        pnlSouth.add(volver);

        JLabel titlePage = new JLabel("Perfil de " + usuario);

        title.add(titlePage);
        title.setBackground(Color.cyan);

        this.pack();
        getContentPane().add(title, BorderLayout.NORTH);
        getContentPane().add(form, BorderLayout.CENTER);
        getContentPane().add(pnlSouth,BorderLayout.SOUTH);


        //Ventana
        this.setSize(400,400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);


    }

    /**
     * Main method of the Perfil.java class
     * Uses the default user to build the JFrame
     * @param args arguments to start the main method of the class
     */
    public static void main(String[] args) {
        new JPerfil("default");
    }

    /**
     * Creates a container that uses a SpringLayout to present
     * pairs of components. The resulting layout is similar to
     * that of a form.  For example:
     * <pre>
     * LLLL  RRR
     * LL    RRR
     * L     RRR
     * LLLLL RRR
     * </pre>
     * where the max of the widths of the L (left) components dictates the
     * x location of the R (right) components. The width of the Rs is
     * locked to that of the container so that all extra space is given
     * to them.
     *
     * @param leftComponents the components in the left column
     *                (the first item of each pair);
     *
     * @param rightComponents the components in the right column
     *                (the second item of each pair)
     *
     */
    private static JPanel createForm(Component[] leftComponents,
                                     Component[] rightComponents,
                                     int initialX, int initialY,
                                     int xPad, int yPad) {
        SpringLayout layout = new SpringLayout();
        int numRows = Math.max(leftComponents.length, rightComponents.length);

        // The constant springs we'll use to enforce spacing.
        Spring xSpring = Spring.constant(initialX);
        Spring ySpring = Spring.constant(initialY);
        Spring xPadSpring = Spring.constant(xPad);
        Spring yPadSpring = Spring.constant(yPad);
        Spring negXPadSpring = Spring.constant(-xPad);

        // Create the container and add the components to it.
        JPanel parent = new JPanel(layout);
        for (int i = 0; i < numRows; i++) {
            parent.add(leftComponents[i]);
            parent.add(rightComponents[i]);
        }

        // maxEastSpring will contain the highest min/pref/max values
        // for the right edges of the components in the first column
        // (i.e. the largest X coordinate in a first-column component).
        // We use layout.getConstraint instead of layout.getConstraints
        // (layout.getConstraints(comp).getConstraint("East"))
        // because we need a proxy -- not the current Spring.
        // Otherwise, it won't take the revised X position into account
        // for the initial layout.
        Spring maxEastSpring = layout.getConstraint("East", leftComponents[0]);
        for (int row = 1; row < numRows; row++) {
            maxEastSpring = Spring.max(maxEastSpring,
                    layout.getConstraint("East",
                            leftComponents[row]));
        }

        // Lay out each pair. The left column's x is constrained based on
        // the passed x location. The y for each component in the left column
        // is the max of the previous pair's height. In the right column, x is
        // constrained by the max width of the left column (maxEastSpring),
        // y is constrained as in the left column, and the width is
        // constrained to be the x location minus the width of the
        // parent container. This last constraint makes the right column fill
        // all extra horizontal space.
        SpringLayout.Constraints lastConsL = null;
        SpringLayout.Constraints lastConsR = null;
        Spring parentWidth = layout.getConstraint("East", parent);
        Spring rWidth = null;
        Spring maxHeightSpring = null;
        Spring rX = Spring.sum(maxEastSpring, xPadSpring); //right col location
        Spring negRX = Spring.minus(rX); //negative of rX

        for (int row = 0; row < numRows; row++) {
            SpringLayout.Constraints consL = layout.getConstraints(
                    leftComponents[row]);
            SpringLayout.Constraints consR = layout.getConstraints(
                    rightComponents[row]);

            consL.setX(xSpring);
            consR.setX(rX);

            rWidth = consR.getWidth(); //get the spring that tracks this
            //component's min/pref/max width after
            //setting the X spring but before
            //setting the width spring (to avoid
            //a circularity); we really only
            //need to do this once for the
            //textfield case, since they have the
            //same size info
            //XXX To account for other cases,
            //XXX we should probably take the max
            //XXX of the widths.
            //This is used to set the container's
            //width after this loop.
            consR.setWidth(Spring.sum(Spring.sum(parentWidth, negRX),
                    negXPadSpring));
            if (row == 0) {
                consL.setY(ySpring);
                consR.setY(ySpring);
                maxHeightSpring = Spring.sum(ySpring,
                        Spring.max(consL.getHeight(),
                                consR.getHeight()));
            } else {  // row > 0
                Spring y = Spring.sum(Spring.max(
                                lastConsL.getConstraint("South"),
                                lastConsR.getConstraint("South")),
                        yPadSpring);

                consL.setY(y);
                consR.setY(y);
                maxHeightSpring = Spring.sum(yPadSpring,
                        Spring.sum(maxHeightSpring,
                                Spring.max(
                                        consL.getHeight(),
                                        consR.getHeight())));
            }
            lastConsL = consL;
            lastConsR = consR;
        }  // end of for loop


        // Wire up the east/south of the container so that the its preferred
        // size is valid.  The east spring is the distance to the right
        // column (rX) + the right component's width (rWidth) + the final
        // padding (xPadSpring).
        // The south side is maxHeightSpring + the final padding (yPadSpring).
        SpringLayout.Constraints consParent = layout.getConstraints(parent);
        consParent.setConstraint("East",
                Spring.sum(rX, Spring.sum(rWidth,
                        xPadSpring)));
        consParent.setConstraint("South",
                Spring.sum(maxHeightSpring, yPadSpring));

        return parent;
    }

}
