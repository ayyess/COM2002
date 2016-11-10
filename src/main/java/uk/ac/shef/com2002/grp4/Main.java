package uk.ac.shef.com2002.grp4;

import uk.ac.shef.com2002.grp4.util.DPIScaling;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main() {
        //TODO add a practitioner ui that can be switched out for use in the treatment rooms
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(800,600));
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Appointments", null, new AppointmentPanel(), null);
        tabbedPane.addTab("Patients", null, new PatientPanel(), null);
        getContentPane().add(tabbedPane);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName());

                    DPIScaling.rescaleFonts();
                } catch (Exception e) {
                    //we can ignore any exceptions here as they won't change the functionality of the program, it just won't look native
                }

                new Main();
            }
        });
    }
}
