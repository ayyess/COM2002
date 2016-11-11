package uk.ac.shef.com2002.grp4;

import java.awt.*;
import java.util.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.*;
import uk.ac.shef.com2002.grp4.data.Address;

public class FindAddressDialog extends BaseDialog implements ActionListener{
	private final JButton cancelButton;
	private final JButton selectButton;
	private JTextField postcodeField;
	private JList<Address> searchResults;
	private Optional<Address> address = Optional.empty();

	public FindAddressDialog(Component owner){
		super(owner,"Find address");

		Container contentPane = rootPane.getContentPane();

		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		selectButton = new JButton("Select");
		selectButton.addActionListener(this);

		postcodeField = new JTextField();
		postcodeField.addActionListener(this);
		addLabeledInput("Postcode",postcodeField);

		searchResults = new JList<>(new DefaultListModel<>());
		GridBagConstraints c = getBaseConstraints();
		c.gridwidth = 2;
		
		contentPane.add(new JScrollPane(searchResults),c);

		nextRow();

		addButtons(cancelButton,selectButton);

		pack();
	}

	@Override
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == postcodeField){
			List<Address> addresses = Address.findByPostcode(postcodeField.getText());
			DefaultListModel<Address> model = (DefaultListModel<Address>) searchResults.getModel();
			model.clear();
			for(Address a : addresses){
				model.addElement(a);
			}
		}else if(e.getSource() == selectButton){
			address = Optional.of(searchResults.getSelectedValue());
			setVisible(false);
		}else if(e.getSource() == cancelButton){
			setVisible(false);
		}
	}

	public Optional<Address> getAddress() {
		return address;
	}
}
