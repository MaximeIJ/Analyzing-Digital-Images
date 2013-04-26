 package org.gss.adi;
 
 import java.awt.Font;
 import javax.swing.ImageIcon;
 import javax.swing.JLabel;
 import javax.swing.JPanel;
 import javax.swing.JTextArea;
 
 public class AboutPanel extends JPanel
 {
   private static final long serialVersionUID = 7561149987238718240L;
 
   public AboutPanel()
   {
     setLayout(null);
 
     JTextArea txtrUpdatedVersionCode = new JTextArea();
     txtrUpdatedVersionCode.setFont(new Font("SansSerif", 0, 12));
     txtrUpdatedVersionCode.setOpaque(false);
     txtrUpdatedVersionCode.setEditable(false);
     txtrUpdatedVersionCode.setText("Adapted from Real Basic to Java with some revisions by Jordan Bull, Lawrence Hall of Science, in July 2012.\r\n\r\nCode Changes by Dan Gullage and John Pickle, late 2010 to mid 2011.  Funding by National Science Foundation of University of Massachusetts, Amhest's \"STEM Digital\" project, led by Mort Sternheim.\r\n\r\nThe original software components were created by John Pickle and Jacqueline Kirtley, Museum of Science, Boston, MA in support of the Lawrence Hall of Science's Global Systems Science student series in 2002 with NASA funding.\r\n\r\nThese revisions were created to support the NASA-funded project, Digital Earth Watch, http://www.lawrencehallofscience.org/gss/dew/index.html.  This educational project was a collaboration between seven institutions (logos have rollover urls) to develop learning activities, technologies, and software to measure environmental health by monitoring plants:\r\n\r\n   - Museum of Science, Boston, MA (lead institution) \r\n\r\n   - Global Systems Science, Lawrence Hall of Science, Berkeley, CA (co-lead) \r\n\r\n   - Forest Watch, University of New Hampshire, Durham, NH (co-lead)\r\n\r\n   - EOS-Webster, University of New Hampshire, Durham, NH \r\n\r\n   - Remote Sensing and GIS Laboratory, Indiana State University, Terre Haute, IN\r\n\r\n   - Blue Hill Observatory, Milton, MA – \r\n\r\n   - College of Education and Human Development, University of Southern Maine, \r\n      Portland, ME\r\n\r\nJohn Pickle programmed these revisions, which reflect invaluable feedback and input from the DEW team and years of working with teachers and informal science educators.  Unless otherwise credited, photos by John Pickle.\r\n\r\nThis software may be freely copied and used for all educational applications.\r\n\r\nCopyright 2003, 2009, 2012 by the Regents of the University of California\r\nCopyright 2007, 2008 Museum of Science, Boston, MA.");
     txtrUpdatedVersionCode.setWrapStyleWord(true);
     txtrUpdatedVersionCode.setLineWrap(true);
     txtrUpdatedVersionCode.setBounds(10, 11, 597, 609);
     add(txtrUpdatedVersionCode);
 
     JLabel lblNewLabel = new JLabel("");
     lblNewLabel.setIcon(new ImageIcon(AboutPanel.class.getResource("/resources/AboutLogos.png")));
     lblNewLabel.setBounds(617, 15, 479, 605);
     add(lblNewLabel);
   }
 }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.AboutPanel
 * JD-Core Version:    0.6.2
 */