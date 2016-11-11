package uk.ac.shef.com2002.grp4;

import org.jdatepicker.JDatePicker;
import uk.ac.shef.com2002.grp4.databases.PatientUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Calendar;
import java.time.*;

abstract public class BaseDialog extends JDialog implements ActionListener {
	private int row = 0;

	protected int nextRow(){
		return row++;
	}

	protected GridBagConstraints getBaseConstraints(){
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5,5,5,5);
		c.gridy = row;
		return c;
	}
	protected void addLabeledInput(String label,JComponent input){
		Container contentPane = rootPane.getContentPane();
		GridBagConstraints c = getBaseConstraints();

		c.fill=GridBagConstraints.HORIZONTAL;
		c.anchor=GridBagConstraints.EAST;
		contentPane.add(new JLabel(label+":"),c);
		c.anchor=GridBagConstraints.WEST;
		contentPane.add(input,c);

		nextRow();
	}
	public BaseDialog(JFrame owner,String title){
		super(owner,title,true);
		Container contentPane = rootPane.getContentPane();
		contentPane.setLayout(new GridBagLayout());
	}

}
