/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.secretaryview;

import javax.swing.*;
import java.awt.*;

public class SecretaryApp extends JFrame {

    public SecretaryApp() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(800, 600));

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Appointments", null, new AppointmentPanel(), null);
		tabbedPane.addTab("Patients", null, new PatientPanel(), null);
		getContentPane().add(tabbedPane);
		pack();

		//this makes it open centered
		setLocationRelativeTo(null);

		setVisible(true);
	}
}
