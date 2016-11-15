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

public class PaymentDialog extends BaseDialog {

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
