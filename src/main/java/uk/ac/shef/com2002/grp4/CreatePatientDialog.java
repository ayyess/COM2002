package uk.ac.shef.com2002.grp4;

import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Container;
import org.jdatepicker.*;
import uk.ac.shef.com2002.grp4.util.DPIScaling;

public class CreatePatientDialog extends JDialog{
	public CreatePatientDialog(JFrame owner){
		super(owner,"Create Patient",false);
		Container contentPane = rootPane.getContentPane();
		contentPane.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();

		c.gridy+=1;
		contentPane.add(new JLabel("Title"),c);
		contentPane.add(new JTextField(5),c);
		
		c.gridy+=1;
		contentPane.add(new JLabel("Forename"),c);
		contentPane.add(new JTextField(10),c);

		c.gridy+=1;
		contentPane.add(new JLabel("Surname"),c);
		contentPane.add(new JTextField(10),c);

		int dpiScaling = (int)DPIScaling.get();
		JDatePicker datePicker = new JDatePicker();
		JDatePanel datePanel  = (JDatePanel) datePicker.getJDateInstantPanel();
		datePanel.setPreferredSize(new java.awt.Dimension(200*dpiScaling,180*dpiScaling));

		c.gridy+=1;
		contentPane.add(new JLabel("DoB"),c);
		contentPane.add(new JDatePicker(),c);

	}
}
