/* This file is part of Grp4 Dental Care System.
 * To ensure compliance with the GNU General Public License. This System
 * is for private, educational use. It will not be released publicly and will
 * solely be viewed by those marking the COM2002 assignment.
 *
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

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

abstract public class BaseInfoComponent extends JPanel {
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
		GridBagConstraints c = getBaseConstraints();

		c.fill=GridBagConstraints.HORIZONTAL;
		c.anchor=GridBagConstraints.EAST;
		add(new JLabel(label+":"),c);
		c.anchor=GridBagConstraints.WEST;
		add(input,c);

		nextRow();
	}

	public BaseInfoComponent(){
		super(new GridBagLayout());
	}

}
