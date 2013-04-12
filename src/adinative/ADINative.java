/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adinative;

import javafx.embed.swing.JFXPanel;
import javax.swing.JApplet;
import org.gss.adi.Entrance;

/**
 *
 * @author Jordan
 */
public class ADINative extends JApplet {
    
    private static final int JFXPANEL_WIDTH_INT = 300;
    private static final int JFXPANEL_HEIGHT_INT = 250;
    private static JFXPanel fxContainer;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        Entrance.main(args);
    }
}
