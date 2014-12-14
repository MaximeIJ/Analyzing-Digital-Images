package org.gss.adi.datapanels;

import java.awt.Dimension;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import net.sourceforge.jheader.*;
import net.sourceforge.jheader.gui.TagTable;

/*
 * Example that shows how to use the TagTable GUI class.
 */
public class ExifPanel
{
	public static void show(String filename){

		try
		{
			// Initializes static data - not necessary as it is done
			// automatically the first time it is needed.  However doing
			// it explicitly means the first call to the library won't be
			// slower than subsequent calls.
			JpegHeaders.preheat();

			// Parse the JPEG file
			JpegHeaders headers = new JpegHeaders(filename);
			
			// Fetch the EXIF (APP1) header if there is one.
			App1Header app1Header = headers.getApp1Header();
			// Construct a JFrame to hold the tag table
			File file = new File(filename);

			JFrame frame = new JFrame("EXIF Headers: " + file.getName());
			frame.setSize(350, 350);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			if (app1Header == null)
			{
				JTextArea errorMessage = new JTextArea(3, 50);
				errorMessage.setFont(errorMessage.getFont().deriveFont(16f)); 
				errorMessage.setText("\n" + filename + " does not contain any Exif data.\n");
				frame.add(errorMessage);
				//System.exit(0);
			}
			else{
				// construct an editable tag table
				TagTable table = new TagTable(frame, true);
				table.loadData(app1Header);

				// construct a scroller
				JScrollPane scroller
				= new JScrollPane(table,
						ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
						ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				scroller.setPreferredSize(new Dimension(400,200));
				frame.setContentPane(scroller);


			}
			// display the frame
			frame.pack();
			frame.setVisible(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}    
}

