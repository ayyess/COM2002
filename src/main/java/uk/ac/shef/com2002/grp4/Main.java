package uk.ac.shef.com2002.grp4;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main() {
        //TODO add a practitioner ui that can be switched out for use in the treatment rooms
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(800,600));
        pack();
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Appointments", null, new AppointmentPanel(), null);
        tabbedPane.addTab("Customers", null, new CustomerPanel(), null);
        tabbedPane.addTab("Payment", null, new PaymentPanel(), null);
        add(tabbedPane);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName());
                    //hack for dpi scaling, really only because 4k screens are annoying
                    float dpiScaling =Toolkit.getDefaultToolkit().getScreenResolution()/86;
                    for(Object key: UIManager.getLookAndFeelDefaults().keySet()) {
                        if(key != null && key.toString().toLowerCase().contains("font")) {
                            Font font = UIManager.getDefaults().getFont(key);
                            if(font != null) {
                                int size = font.getSize();
                                float scaledSize = size*dpiScaling;
                                font = font.deriveFont(scaledSize);
                                UIManager.put(key,font);
                            }
                        }
                    }
                } catch (Exception e) {
                    //we can ignore any exceptions here as they won't change the functionality of the program, it just won't look native
                }

                new Main();
            }
        });
    }
}
