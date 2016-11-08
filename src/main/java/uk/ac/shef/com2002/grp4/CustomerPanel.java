package uk.ac.shef.com2002.grp4;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Panel for customer record interaction workflow
 *
 * Created on 28/10/2016.
 */
public class CustomerPanel extends JPanel implements DocumentListener, ActionListener{

	private String searchText;

	//TODO searching customers
	//TODO adding customers
	public CustomerPanel(){
		super(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		JTextField firstNameField = new JTextField(20);
		firstNameField.getDocument().addDocumentListener(this);
		firstNameField.addActionListener(this);
		c.gridx = 0;
		add(new JLabel("First Name:"),c);
		c.gridx = 1;
		add(firstNameField,c);
	}

	private void setSearchText(String search){
		searchText = search;
	}

	@Override
	public void actionPerformed(ActionEvent ev){
		//doSearch();
		System.out.println(ev);
	}

	@Override
	public void changedUpdate(DocumentEvent ev){
		try{
			int len = ev.getDocument().getLength();
			System.out.println(ev.getDocument().getText(0,len));
		}catch(Exception e){
			e.printStackTrace();//FIXME
		}
	}

	@Override
	public void insertUpdate(DocumentEvent ev){
		try{
			int len = ev.getDocument().getLength();
			System.out.println(ev.getDocument().getText(0,len));
		}catch(Exception e){
			e.printStackTrace();//FIXME
		}
	}

	@Override
	public void removeUpdate(DocumentEvent ev){
		try{
			int len = ev.getDocument().getLength();
			System.out.println(ev.getDocument().getText(0,len));
		}catch(Exception e){
			e.printStackTrace();//FIXME
		}
	}
}
