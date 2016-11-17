/* This file is part of Grp4 Dental Care System.
 * To ensure compliance with the GNU General Public License. This System
 * is for private, educational use. It will not be released publicly and will
 * solely be viewed by those marking the COM2002 assignment.
 *
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4;

import javax.swing.*;
import java.awt.*;
import java.time.*;

abstract public class BaseDialog extends JDialog {
	private boolean canceled = false;
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
		c.gridx = -1;
		c.gridwidth = 2;
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		c.fill=GridBagConstraints.HORIZONTAL;
		for(JButton button: buttons) {
			buttonPanel.add(button);
		}
		
		contentPane.add(buttonPanel, c);

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

	public boolean wasCanceled() {
		return canceled;
	}

	public void cancel(){
		canceled = true;
		dispose();
	}
}
