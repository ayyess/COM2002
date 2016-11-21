/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.common;

import javax.swing.*;
import java.awt.*;

/**
 * This class creates a basic dialog box to which other panels and components
 * can be added to.
 * <br>
 *
 * @author Group 4
 * @version 1.0
 * @since 08/11/2016
 */
abstract public class BaseDialog extends JDialog {

	/**
	 * This stores whether the dialog has been closed/cancelled.
	 */
	private boolean canceled = false;

	/**
	 * This stores the number of rows in the dialog.
	 */
	private int row = 0;

	/**
	 * This constructor takes a component and a title to make a dialog.
	 *
	 * @param c     - a component
	 * @param title - a title for the dialog
	 */
	public BaseDialog(Component c, String title) {
		this((Frame) SwingUtilities.getWindowAncestor(c).getOwner(), title);
	}

	/**
	 * This constructor takes a frame and a title to make a dialog.
	 *
	 * @param owner - a frame
	 * @param title - a title for the dialog
	 */
	public BaseDialog(Frame owner, String title) {
		super(owner, title, true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Container contentPane = rootPane.getContentPane();
		contentPane.setLayout(new GridBagLayout());
	}

	/**
	 * This will add another row to the component.
	 *
	 * @return the value of the next row
	 */
	protected int nextRow() {
		return row++;
	}

	/**
	 * This gets the constraints of the GridBag.
	 *
	 * @return the GridBagConstraints
	 */
	protected GridBagConstraints getBaseConstraints() {
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
		c.gridy = row;
		return c;
	}

	/**
	 * This function adds a component to this base component.
	 *
	 * @param label - a label for the component
	 * @param input - a new component
	 */
	protected void addLabeledComponent(String label, JComponent input) {
		Container contentPane = rootPane.getContentPane();
		GridBagConstraints c = getBaseConstraints();

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.EAST;
		contentPane.add(new JLabel(label + ":"), c);
		c.anchor = GridBagConstraints.WEST;
		contentPane.add(input, c);

		nextRow();
	}

	/**
	 * This function adds buttons to this base component.
	 *
	 * @param buttons - a number of JButtons
	 */
	protected void addButtons(JButton... buttons) {
		Container contentPane = rootPane.getContentPane();
		GridBagConstraints c = getBaseConstraints();
		c.gridx = -1;
		c.gridwidth = 2;
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		c.fill = GridBagConstraints.HORIZONTAL;
		for (JButton button : buttons) {
			buttonPanel.add(button);
		}

		contentPane.add(buttonPanel, c);

		nextRow();
	}

	@Override
	public void setVisible(boolean visible) {
		//this makes it open centered
		setLocationRelativeTo(null);
		super.setVisible(visible);
	}

	/**
	 * This gets whether the dialog has been cancelled or not.
	 *
	 * @return a Boolean- true if it has been cancelled
	 */
	public boolean wasCanceled() {
		return canceled;
	}

	/**
	 * This cancels and disposes the dialog.
	 */
	public void cancel() {
		canceled = true;
		dispose();
	}
}
