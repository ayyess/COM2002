/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.common.util;

import javax.swing.*;
import java.awt.*;

/**
 * Used to scale the outputs when the system is outputting to a high definition screen.
 * <br>
 * @author  Group 4
 * @version 1.0
 * @since   03/11/2016
 */
public class DPIScaling {

    /** This is a normal level DPI which an outputs DPI will be compared to. */
    public static final float NOMINAL_DPI = 86;

    /**
     * @return A scale factor relative to `NOMINAL_DPI`, ~1.0 on normal dpi screens
     */
    public static float get(){
        return Toolkit.getDefaultToolkit().getScreenResolution()/NOMINAL_DPI;
    }

    /**
     * Hack for dpi scaling, really only because 4k screens are annoying.
     * Causes the fonts of the current `LookAndFeel` to be rescaled by a factor depending on the system dpi
     */
    public static void rescaleFonts(){
        float dpiScaling = get();
        // Scale the font size of each object which is in the UI
        for(Object key: UIManager.getLookAndFeelDefaults().keySet()) {
            if(key != null && key.toString().toLowerCase().contains("font")) {
                Font font = UIManager.getDefaults().getFont(key);
                if(font != null) {
                    int size = font.getSize();
                    float scaledSize = size*dpiScaling;
                    font = font.deriveFont(scaledSize);
                    UIManager.put(key,font);
                }
            }
        }
    }
}
