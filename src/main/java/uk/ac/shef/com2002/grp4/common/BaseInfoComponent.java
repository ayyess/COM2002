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
 * This class is the base of the GUI and will contain other components
 * <br>
 * @author  Group 4
 * @version 1.0
 * @since   02/11/2016
 */
abstract public class BaseInfoComponent extends JPanel {

	/** This stores the number of rows required in the component. */
	private int row = 0;

	/**
	 * This will add another row to the component.
	 *
	 * @return the value of the next row
	 */
	protected int nextRow(){
		return row++;
	}

	/**
	 * This gets the constraints of the GridBag.
	 *
	 * @return the GridBagConstraints
	 */
	protected GridBagConstraints getBaseConstraints(){
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5,5,5,5);
		c.gridy = row;
		return c;
	}

	/**
	 * This function adds a component to this base component.
	 *
	 * @param label - a label for the component
	 * @param input - a new component
	 */
	protected void addLabeledComponent(String label,JComponent input){
		GridBagConstraints c = getBaseConstraints();

		c.fill=GridBagConstraints.HORIZONTAL;
		c.anchor=GridBagConstraints.EAST;
		add(new JLabel(label+":"),c);
		c.anchor=GridBagConstraints.WEST;
		add(input,c);

		nextRow();
	}

	/**
	 * This constructor creates a new BaseInfoComponent.
	 */
	public BaseInfoComponent(){
		super(new GridBagLayout());
	}

}
