package uk.ac.shef.com2002.grp4.util;

import javax.swing.*;
import java.awt.*;

/**
 * HiDpi support methods
 * Created on 03/11/2016.
 */
public class DPIScaling {
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
