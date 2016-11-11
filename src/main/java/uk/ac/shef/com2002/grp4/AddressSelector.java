package uk.ac.shef.com2002.grp4;

import uk.ac.shef.com2002.grp4.data.Address;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

/**
 * <Doc here>
 * <p>
 * Created on 11/11/2016.
 */
public class AddressSelector extends JPanel implements ActionListener {
	private JTextArea displayField;
	private JButton findAddressButton;
	private JButton createAddressButton;

	private Optional<Address> address = Optional.empty();

	public AddressSelector(){
		super(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0,0,0,0);
		c.gridheight = 2;
		c.gridy = 0;
		c.gridx = 0;
		c.weightx = 1;
		c.fill = GridBagConstraints.BOTH;
		displayField = new JTextArea("");
		displayField.setEnabled(false);
		add(displayField,c);

		c.weightx = 0;
		c.gridheight = 1;
		c.gridx = 1;
		findAddressButton = new JButton("Find");
		findAddressButton.addActionListener(this);
		add(findAddressButton,c);
		c.gridy = 1;
		createAddressButton = new JButton("Create");
		createAddressButton.addActionListener(this);
		add(createAddressButton,c);
	}

	public void setAddress(Optional<Address> address){
		this.address = address;
		if(address.isPresent()) {
			displayField.setText(address.get().formatted());
		}else{
			displayField.setText("");
		}
		//FIXME there has to be a better way to get the dialog to resize if needed
		SwingUtilities.getWindowAncestor(this).pack();
	}

	public Optional<Address> getAddress(){
		return address;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == findAddressButton){
			FindAddressDialog finder = new FindAddressDialog(this);
			finder.setVisible(true);
			setAddress(finder.getAddress());
		}else if(e.getSource() == createAddressButton){
			CreateAddressDialog creator = new CreateAddressDialog(this);
			creator.setVisible(true);
			setAddress(creator.getAddress());
		}
	}
}
