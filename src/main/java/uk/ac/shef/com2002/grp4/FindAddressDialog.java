package uk.ac.shef.com2002.grp4;

import java.awt.*;
import java.util.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.naming.directory.SearchResult;
import javax.swing.*;
import uk.ac.shef.com2002.grp4.data.Address;
import uk.ac.shef.com2002.grp4.databases.AddressUtils;

public class FindAddressDialog extends BaseDialog implements ActionListener{
	private final JTextField postcodeField;
	private final JList<Address> searchResults;
	private Optional<Address> address = Optional.empty();
	private final JButton cancelButton;
	private final JButton selectButton;

	public FindAddressDialog(Component owner){
		super(owner,"Find address");

		Container contentPane = rootPane.getContentPane();

		postcodeField = new JTextField();
		postcodeField.addActionListener(this);
		addLabeledComponent("Postcode",postcodeField);

		searchResults = new JList<>(new DefaultListModel<>());
		GridBagConstraints c = getBaseConstraints();
		c.gridwidth = 2;
		
		contentPane.add(new JScrollPane(searchResults),c);

		nextRow();

		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		selectButton = new JButton("Select");
		selectButton.addActionListener(this);

		addButtons(cancelButton,selectButton);

		pack();
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
		}else if(e.getSource() == selectButton){
			address = Optional.ofNullable(searchResults.getSelectedValue());
			dispose();
		}else if(e.getSource() == cancelButton){
			dispose();
		}
	}

	public Optional<Address> getAddress() {
		return address;
	}
}
