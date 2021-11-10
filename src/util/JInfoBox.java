package util;

import javax.swing.JOptionPane;

public class JInfoBox {
    public static void infoBox(String titleBar, String info)
    {
        JOptionPane.showMessageDialog(null,info,titleBar,JOptionPane.INFORMATION_MESSAGE);
    }
}
