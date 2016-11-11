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
	private JTextField displayField;
	private JButton findAddressButton;
	private JButton createAddressButton;

	private Optional<Address> address = Optional.empty();

	public AddressSelector(){
		super(new FlowLayout(FlowLayout.LEADING,0,0));
		displayField = new JTextField(address.toString());
		displayField.setEnabled(false);
		add(displayField);
		findAddressButton = new JButton("Find");
		findAddressButton.addActionListener(this);
		add(findAddressButton);
		createAddressButton = new JButton("Create");
		createAddressButton.addActionListener(this);
		add(createAddressButton);
	}

	public void setAddress(Optional<Address> address){
		this.address = address;
		displayField.setText(address.toString());
	}

	public Optional<Address> getAddress(){
		return address;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == findAddressButton){
			FindAddressDialog finder = new FindAddressDialog(this);
			finder.setVisible(true);
		}else if(e.getSource() == createAddressButton){
			CreateAddressDialog creator = new CreateAddressDialog(this);
			creator.setVisible(true);
		}
	}
}
