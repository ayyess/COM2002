package uk.ac.shef.com2002.grp4;

import uk.ac.shef.com2002.grp4.data.Patient;
import uk.ac.shef.com2002.grp4.databases.PatientUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Panel for customer record interaction workflow
 *
 * Created on 28/10/2016.
 */
public class CustomerPanel extends JPanel implements DocumentListener, ActionListener{
	private JTextField firstNameField;
	private String searchText;
	private DefaultTableModel searchResults;
	private JButton addCustomerButton;

	//TODO searching customers: should mostly work now, but untested
	//TODO adding customers
	public CustomerPanel(){
		super(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		firstNameField = new JTextField(20);
		firstNameField.getDocument().addDocumentListener(this);
		firstNameField.addActionListener(this);
		c.gridx = 0;
		add(new JLabel("First Name:"),c);
		c.gridx = 1;
		c.anchor = GridBagConstraints.EAST;
		add(firstNameField,c);

		String[] columnNames = {"Title","First Name","Last Name","DoB","Phone Number"};
		searchResults = new DefaultTableModel();
		for(String column : columnNames) {
			searchResults.addColumn(column);
		}
		searchResults.setColumnIdentifiers(columnNames);
		JTable searchResultsDisplay = new JTable(searchResults);
		c.anchor = GridBagConstraints.CENTER;
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridwidth = 2;
		c.gridy = 1;
		c.fill = GridBagConstraints.BOTH;
		add(new JScrollPane(searchResultsDisplay),c);

		addCustomerButton = new JButton("New Patient");
		addCustomerButton.addActionListener(this);

		c.weightx = 0;
		c.weighty = 0;
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 2;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.EAST;
		add(addCustomerButton,c);

	}

	private void setSearchText(String text){
		searchText = text;
	}

	@Override
		public void changedUpdate(DocumentEvent ev){
		try{
			int len = ev.getDocument().getLength();
			setSearchText(ev.getDocument().getText(0,len));
		}catch(Exception e){
			e.printStackTrace();//FIXME
		}
	}

	@Override
	public void insertUpdate(DocumentEvent ev){
		try{
			int len = ev.getDocument().getLength();
			setSearchText(ev.getDocument().getText(0,len));
		}catch(Exception e){
			e.printStackTrace();//FIXME
		}
	}

	@Override
	public void removeUpdate(DocumentEvent ev){
		try{
			int len = ev.getDocument().getLength();
			setSearchText(ev.getDocument().getText(0,len));
		}catch(Exception e){
			e.printStackTrace();//FIXME
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == firstNameField) {
			doSearch();
		}else if(e.getSource() == addCustomerButton){
			CreatePatientDialog createDialog = new CreatePatientDialog((JFrame)SwingUtilities.getWindowAncestor(this));
			createDialog.setVisible(true);
		}
	}

	private void doSearch() {
		List<Patient> found = PatientUtils.findPatientByFirstName(searchText);
		while(searchResults.getRowCount() > 0){
			searchResults.removeRow(0);
		}
		for(Patient patient:found) {
			searchResults.addRow(new Object[]{patient.getTitle(),patient.getForename(),patient.getSurname(),patient.getDob(),patient.getPhoneNumber()});
		}
	}
}
