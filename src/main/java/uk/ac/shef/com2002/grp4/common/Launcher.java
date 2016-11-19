package uk.ac.shef.com2002.grp4.common;

import uk.ac.shef.com2002.grp4.common.util.DPIScaling;
import uk.ac.shef.com2002.grp4.partnerview.PartnerApp;
import uk.ac.shef.com2002.grp4.secretaryview.SecretaryApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <Doc here>
 * <p>
 * Created on 19/11/2016.
 */
public class Launcher extends JFrame implements ActionListener {

	private final JButton secretaryViewButton;
	private final JButton dentistViewButton;
	private final JButton hygenistViewButton;

	public Launcher() {
		super();
		setLayout(new GridBagLayout());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		secretaryViewButton = new JButton("Secretary View");
		secretaryViewButton.addActionListener(this);
		dentistViewButton = new JButton("Partner View - Dentist");
		dentistViewButton.addActionListener(this);
		hygenistViewButton = new JButton("Partner View - Hygenist");
		hygenistViewButton.addActionListener(this);

		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = GridBagConstraints.RELATIVE;
		add(secretaryViewButton,c);
		add(dentistViewButton,c);
		add(hygenistViewButton,c);

		pack();

		//this makes it open centered
		setLocationRelativeTo(null);

		setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(
					UIManager.getSystemLookAndFeelClassName());

					DPIScaling.rescaleFonts();
				} catch (Exception e) {
					//we can ignore any exceptions here as they won't change the functionality of the program, it just won't look native
				}

				new Launcher();
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == secretaryViewButton){
			dispose();
			new SecretaryApp();
		}else if(e.getSource() == dentistViewButton){
			dispose();
			new PartnerApp(Partner.DENTIST);
		}else if(e.getSource() == hygenistViewButton){
			dispose();
			new PartnerApp(Partner.HYGIENIST);
		}
	}
}
