/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.payment;

import java.awt.Frame;
import java.util.Arrays;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;

import uk.ac.shef.com2002.grp4.BaseDialog;
import uk.ac.shef.com2002.grp4.data.Patient;
import uk.ac.shef.com2002.grp4.data.Treatment;
import uk.ac.shef.com2002.grp4.databases.TreatmentUtils;

/**
 * Used to manage a patient's payment.
 * <br>
 * @author  Group 4
 * @version 1.0
 * @since   14/11/2016
 */
public class PaymentDialog extends BaseDialog {

	/**
	 * This creates a new PaymentDialog.
	 *
	 * @param f - a Frame
	 * @param p - a Patient
	 */
	public PaymentDialog(Frame f, Patient p) {
		super(f, "Payment");
		
		addLabeledComponent("Patient", new JLabel(p.getName()));

		Treatment[] treatments = TreatmentUtils.getPatientTreatments(p);
		DefaultListModel<Treatment> model = new DefaultListModel<Treatment>();
		for (int i = 0; i < treatments.length; i++) {
			model.add(i, treatments[i]);
		}
		
		JList<Treatment> treatmentList = new JList<Treatment>();
		treatmentList.setModel(model);
		
		addLabeledComponent("Treatments", treatmentList);
		
		JTextField payAmount = new JTextField(4);
		addLabeledComponent("Amount to pay", payAmount);
		
		JButton pay = new JButton("Pay");
		pay.addActionListener((e) -> {
			//Print dialog
			Receipt r = new Receipt(p, Arrays.asList(treatments), Integer.valueOf(payAmount.getText()));
		});
		
		JButton close = new JButton("Close");
		close.addActionListener((e) -> {
			dispose();
		});
		
		addButtons(close);
	}

}
