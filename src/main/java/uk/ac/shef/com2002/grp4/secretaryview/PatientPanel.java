/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.secretaryview;

import uk.ac.shef.com2002.grp4.common.CreatePatientDialog;
import uk.ac.shef.com2002.grp4.common.data.Patient;
import uk.ac.shef.com2002.grp4.common.databases.PatientUtils;
import uk.ac.shef.com2002.grp4.common.util.DPIScaling;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Panel for patient record interaction workflow
 * <p>
 * Created on 28/10/2016.
 */
public class PatientPanel extends JPanel implements DocumentListener, ActionListener {
	private static final String[] columnNames = {"Title", "First Name", "Last Name", "DoB", "Phone Number"};
	private JTextField firstNameField;
	private String searchText = "";
	private PatientTableModel searchResults;
	private JButton addPatientButton;

	public PatientPanel() {
		super(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		firstNameField = new JTextField(20);
		firstNameField.getDocument().addDocumentListener(this);
		firstNameField.addActionListener(this);
		c.gridx = 0;
		add(new JLabel("First Name:"), c);
		c.gridx = 1;
		c.anchor = GridBagConstraints.EAST;
		add(firstNameField, c);

		searchResults = new PatientTableModel();
		JTable searchResultsDisplay = new JTable(searchResults);
		int dpiScaling = (int) DPIScaling.get();
		searchResultsDisplay.setRowHeight(searchResultsDisplay.getRowHeight() * dpiScaling);
		searchResultsDisplay.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		searchResultsDisplay.getSelectionModel().addListSelectionListener((ev) -> {
			//the index values on the event change oddly, so just get the row t=from the table instead
			int row = searchResultsDisplay.getSelectedRow();
			if (row < 0) {
				//this can apparently happen
				//so just refresh the search results and return
				doSearch();
				return;
			}
			Patient selected = searchResults.getValueAt(row);
			PatientDetailsDialog details = new PatientDetailsDialog(selected, this);
			details.setVisible(true);
			if (details.getModified()) {
				doSearch();
			}
			//TODO: check if we need to refresh here
		});
		c.anchor = GridBagConstraints.CENTER;
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridwidth = 2;
		c.gridy = 1;
		c.fill = GridBagConstraints.BOTH;
		add(new JScrollPane(searchResultsDisplay), c);

		addPatientButton = new JButton("New Patient");
		addPatientButton.addActionListener(this);

		c.weightx = 0;
		c.weighty = 0;
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 2;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.EAST;
		add(addPatientButton, c);
		doSearch();
	}

	private void setSearchText(String text) {
		searchText = text;
	}

	@Override
	public void changedUpdate(DocumentEvent ev) {
		try {
			int len = ev.getDocument().getLength();
			setSearchText(ev.getDocument().getText(0, len));
		} catch (Exception e) {
			e.printStackTrace();//FIXME
		}
	}

	@Override
	public void insertUpdate(DocumentEvent ev) {
		try {
			int len = ev.getDocument().getLength();
			setSearchText(ev.getDocument().getText(0, len));
		} catch (Exception e) {
			e.printStackTrace();//FIXME
		}
	}

	@Override
	public void removeUpdate(DocumentEvent ev) {
		try {
			int len = ev.getDocument().getLength();
			setSearchText(ev.getDocument().getText(0, len));
		} catch (Exception e) {
			e.printStackTrace();//FIXME
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == firstNameField) {
			doSearch();
		} else if (e.getSource() == addPatientButton) {
			CreatePatientDialog createDialog = new CreatePatientDialog(this);
			createDialog.setVisible(true);
			if (!createDialog.wasCanceled()) {
				//we think a patient was created, so refresh search results
				doSearch();
			}
		}
	}

	private void doSearch() {
		List<Patient> found = PatientUtils.fuzzyFindPatientByFirstName(searchText);
		searchResults.clear();
		searchResults.addAll(found);
	}

	class PatientTableModel extends AbstractTableModel {
		private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		private List<Patient> data = new ArrayList<>();

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public String getColumnName(int columnIndex) {
			return columnNames[columnIndex];
		}

		@Override
		public int getRowCount() {
			return data.size();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Patient p = data.get(rowIndex);
			switch (columnIndex) {
				case 0:
					return p.getTitle();
				case 1:
					return p.getForename();
				case 2:
					return p.getSurname();
				case 3:
					return p.getDob().format(DATE_FORMAT);
				case 4:
					return p.getPhoneNumber();
				default:
					throw new IllegalArgumentException();
			}
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return false;
		}

		public void removeRow(int index) {
			data.remove(index);
			fireTableRowsDeleted(index, index);
		}

		public void clear() {
			int length = data.size();
			data.clear();
			fireTableRowsDeleted(0, length);
		}

		public void addAll(List<Patient> patients) {
			data = patients;
			fireTableRowsInserted(0, data.size());
		}

		public Patient getValueAt(int index) {
			return data.get(index);
		}
	}

}
