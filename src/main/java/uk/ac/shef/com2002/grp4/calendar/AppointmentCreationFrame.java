/* This file is part of Grp4 Dental Care System.
 * To ensure compliance with the GNU General Public License. This System
 * is for private, educational use. It will not be released publicly and will
 * solely be viewed by those marking the COM2002 assignment.
 *
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.calendar;

import uk.ac.shef.com2002.grp4.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.*;

import uk.ac.shef.com2002.grp4.PatientSelector;
import uk.ac.shef.com2002.grp4.databases.AppointmentUtils;
import uk.ac.shef.com2002.grp4.data.Patient;

public class AppointmentCreationFrame extends BaseDialog {

	private final LocalDate date;
	private final LocalTime time;
	private final JComboBox<String> practitionerCombo;
	private final JComboBox<Integer> lengthCombo;
	private final PatientSelector patientSelector;

	public AppointmentCreationFrame(Component owner, LocalDate date, LocalTime time, Partner partner) {
		super(owner,"Create Appointment");
		this.date = date;
		this.time = time;

		String[] items = {"Dentist", "Hygienist"};
		practitionerCombo = new JComboBox<>(items);
		practitionerCombo.setSelectedIndex(partner.ordinal());
		
		addLabeledComponent("Practitioner",practitionerCombo);

		patientSelector = new PatientSelector();
		addLabeledComponent("Patient",patientSelector);

		Integer[] lengthItems = {20, 40, 60};
		lengthCombo = new JComboBox<>(lengthItems);
		addLabeledComponent("Duration",lengthCombo);

	JButton cancel_button = new JButton("Cancel");
		cancel_button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae){
                    close();
                }
            });
		JButton save_button = new JButton("Save");
		save_button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae){
					try {
						saveDetails();
						close();
					} catch (UserFacingException e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
					}
                }
            });
		addButtons(cancel_button,save_button);
		pack();

	}
	void close(){
		setModal(true);
		//getOwner().setEnabled(true);
		AppointmentCreationFrame.this.dispose();
	}

	void saveDetails() {
		AppointmentUtils.insertAppointment(date, (String)practitionerCombo.getSelectedItem(), patientSelector.getPatient().map(Patient::getID).get(), time, Duration.ofMinutes(((Integer)lengthCombo.getSelectedItem())));
	}
}
