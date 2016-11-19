/* This file is part of Grp4 Dental Care System.
 * To ensure compliance with the GNU General Public License. This System
 * is for private, educational use. It will not be released publicly and will
 * solely be viewed by those marking the COM2002 assignment.
 *
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.secretaryview;

import uk.ac.shef.com2002.grp4.common.util.DPIScaling;

import javax.swing.*;
import java.awt.*;

public class SecretaryApp extends JFrame {

    public SecretaryApp() {
        //TODO add a partner ui that can be switched out for use in the treatment rooms
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

                new SecretaryApp();
            }
        });
    }
}
