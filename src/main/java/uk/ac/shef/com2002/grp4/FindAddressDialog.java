package uk.ac.shef.com2002.grp4;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import uk.ac.shef.com2002.grp4.data.Address;

public class FindAddressDialog extends BaseDialog implements ActionListener{
	private JTextField postcodeField;
	private JList<Address> searchResults;
	public FindAddressDialog(JFrame owner){
		super(owner,"Find address");

		Container contentPane = rootPane.getContentPane();

		postcodeField = new JTextField();
		addLabeledInput("Postcode",postcodeField);

		searchResults = new JList<>();
		GridBagConstraints c = getBaseConstraints();
		
		contentPane.add(searchResults,c);

		nextRow();
	}

	@Override
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == postcodeField){
			
		}
	}
}
