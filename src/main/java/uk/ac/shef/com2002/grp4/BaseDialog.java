package uk.ac.shef.com2002.grp4;

import org.jdatepicker.JDatePicker;
import uk.ac.shef.com2002.grp4.databases.PatientUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.util.Calendar;
import java.time.*;

abstract public class BaseDialog extends JDialog {
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
	protected void addLabeledComponent(String label,JComponent input){
		Container contentPane = rootPane.getContentPane();
		GridBagConstraints c = getBaseConstraints();

		c.fill=GridBagConstraints.HORIZONTAL;
		c.anchor=GridBagConstraints.EAST;
		contentPane.add(new JLabel(label+":"),c);
		c.anchor=GridBagConstraints.WEST;
		contentPane.add(input,c);

		nextRow();
	}

	protected void addButtons(JButton... buttons) {
		Container contentPane = rootPane.getContentPane();
		GridBagConstraints c = getBaseConstraints();

		c.fill=GridBagConstraints.HORIZONTAL;
		for(JButton button: buttons) {
			contentPane.add(button,c);
		}

		nextRow();
	}

	public BaseDialog(Component c, String title){
		this((Frame)SwingUtilities.getWindowAncestor(c).getOwner(),title);
	}

	public BaseDialog(Frame owner,String title){
		super(owner,title,true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Container contentPane = rootPane.getContentPane();
		contentPane.setLayout(new GridBagLayout());
	}

}
