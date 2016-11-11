package uk.ac.shef.com2002.grp4;

import java.util.*;
import java.awt.GridBagConstraints;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import uk.ac.shef.com2002.grp4.data.Address;
import uk.ac.shef.com2002.grp4.databases.AddressUtils;

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
			List<Address> addresses = AddressUtils.findAddresses(postcodeField.getText());
			DefaultListModel<Address> model = (DefaultListModel<Address>) searchResults.getModel();
			model.clear();
			for(Address a : addresses){
				model.addElement(a);
			}
		}
	}
}
