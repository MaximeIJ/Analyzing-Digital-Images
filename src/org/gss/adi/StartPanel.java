 package org.gss.adi;
 
 import java.awt.Font;
 import java.awt.Insets;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import javax.swing.JButton;
 import javax.swing.JPanel;
 import javax.swing.JTextArea;
 import javax.swing.JTextField;
 
 public class StartPanel extends JPanel
 {
   private static final long serialVersionUID = 339625940460934284L;
   private JTextField txtOpenAnImage;
   private JTextField txtOpenImages;
   private Entrance entrance;
 
   public StartPanel(Entrance e)
   {
     setLayout(null);
     this.entrance = e;
     setup();
   }
   private void setup() {
     JTextArea txtrThisSoftwareProvides = new JTextArea();
     txtrThisSoftwareProvides.setFont(new Font("SansSerif", 1, 14));
     txtrThisSoftwareProvides.setEditable(false);
     txtrThisSoftwareProvides.setText("This software provides tools for visualizing and measuring spatial and spectral (color) qualities and relationships in digital images.");
     txtrThisSoftwareProvides.setWrapStyleWord(true);
     txtrThisSoftwareProvides.setLineWrap(true);
     txtrThisSoftwareProvides.setOpaque(false);
     txtrThisSoftwareProvides.setBounds(10, 11, 386, 58);
     add(txtrThisSoftwareProvides);
 
     JTextArea txtrMoveTheCursor = new JTextArea();
     txtrMoveTheCursor.setFont(new Font("SansSerif", 0, 13));
     txtrMoveTheCursor.setEditable(false);
     txtrMoveTheCursor.setText("Move the cursor over the buttons below for more information about the selection.");
     txtrMoveTheCursor.setWrapStyleWord(true);
     txtrMoveTheCursor.setLineWrap(true);
     txtrMoveTheCursor.setOpaque(false);
     txtrMoveTheCursor.setBounds(20, 80, 361, 34);
     add(txtrMoveTheCursor);
 
     this.txtOpenAnImage = new JTextField();
     this.txtOpenAnImage.setEditable(false);
     this.txtOpenAnImage.setFont(new Font("Tahoma", 2, 14));
     this.txtOpenAnImage.setText("Open an Image and Analyze in One of These Windows");
     this.txtOpenAnImage.setBounds(49, 125, 344, 20);
     this.txtOpenAnImage.setOpaque(false);
     this.txtOpenAnImage.setBorder(null);
     add(this.txtOpenAnImage);
     this.txtOpenAnImage.setColumns(10);
 
     JButton btnNewButton = new JButton("Spatial Analysis");
     btnNewButton.setToolTipText("Measure the length, area, position, and color of interest.");
     btnNewButton.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         new SpatialAnalysisPanel(StartPanel.this.entrance);
       }
     });
     btnNewButton.setFont(new Font("SansSerif", 0, 24));
     btnNewButton.setBounds(49, 156, 294, 154);
     add(btnNewButton);
 
     JButton btnEnhanceColors = new JButton("Enhance Colors");
     btnEnhanceColors.setToolTipText("<html>Change how the colors of the image are displayed on the computer screen - you <br/>may see new features within the image that may relate to physical or chemical <br/>properties of that object.");
     btnEnhanceColors.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         new EnhanceColorsPanel(StartPanel.this.entrance);
       }
     });
     btnEnhanceColors.setFont(new Font("SansSerif", 0, 24));
     btnEnhanceColors.setBounds(353, 156, 294, 154);
     add(btnEnhanceColors);
 
     JButton btnMaskColor = new JButton("Mask Color");
     btnMaskColor.setToolTipText("<html>Select a range or relationship of colors to highlight. Pixels meeting these color <br/>conditions become black; those that don't become white.");
     btnMaskColor.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         new MaskColorsPanel(StartPanel.this.entrance);
       }
     });
     btnMaskColor.setFont(new Font("SansSerif", 0, 24));
     btnMaskColor.setBounds(657, 156, 294, 154);
     add(btnMaskColor);
 
     JButton btnTimeSeries = new JButton("Time Series");
     btnTimeSeries.setToolTipText("<html>Measure the length, area, position, and color of features of interest within a series <br/>of images.");
     btnTimeSeries.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         StartPanel.this.entrance.launchTimeSeries();
       }
     });
     btnTimeSeries.setFont(new Font("SansSerif", 0, 24));
     btnTimeSeries.setBounds(49, 352, 294, 154);
     add(btnTimeSeries);
 
     JButton btnExampleOldGrowth = new JButton("<html><center>Example: <br/>Old Growth Forest</center>");
     btnExampleOldGrowth.setToolTipText("<html>Explore a time series of aps showing the loation of old growth forests across the <br/>U.S. over the past 300+ years.");
     btnExampleOldGrowth.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent arg0) {
         new OldGrowthForestPanel(StartPanel.this.entrance, false);
       }
     });
     btnExampleOldGrowth.setMargin(new Insets(2, 0, 2, 0));
     btnExampleOldGrowth.setFont(new Font("SansSerif", 0, 24));
     btnExampleOldGrowth.setBounds(353, 352, 294, 154);
     add(btnExampleOldGrowth);
 
     JButton btnAboutAdi = new JButton("About ADI");
     btnAboutAdi.setToolTipText("<html>Review the basics of this software. For more information,<br/>see the information in the help menu.");
     btnAboutAdi.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         new AboutADIPanel(StartPanel.this.entrance);
       }
     });
     btnAboutAdi.setFont(new Font("SansSerif", 0, 24));
     btnAboutAdi.setBounds(657, 352, 294, 154);
     add(btnAboutAdi);
 
     this.txtOpenImages = new JTextField();
     this.txtOpenImages.setEditable(false);
     this.txtOpenImages.setText("Open 2-3 Images to Analyze");
     this.txtOpenImages.setFont(new Font("Tahoma", 2, 14));
     this.txtOpenImages.setColumns(10);
     this.txtOpenImages.setBounds(49, 321, 188, 20);
     this.txtOpenImages.setOpaque(false);
     this.txtOpenImages.setBorder(null);
     add(this.txtOpenImages);
 
     JTextArea txtrAdditionalToolsAnd = new JTextArea();
     txtrAdditionalToolsAnd.setFont(new Font("SansSerif", 0, 13));
     txtrAdditionalToolsAnd.setEditable(false);
     txtrAdditionalToolsAnd.setWrapStyleWord(true);
     txtrAdditionalToolsAnd.setLineWrap(true);
     txtrAdditionalToolsAnd.setText("Additional tools and options are available in the Menu Bar.  Those that work with single-image analysis (Spatial Analysis, Enhance Colors, and Mask Colors) are marked with an icon of a single image.  Those that work with the time series of images are marked with an icon of multiple images.  Some options work with both single and multime images and so are indicated by having both icons.\r\n\r\nIf you select a menu option that doesn't work for your type of analysis, nothing will happen.");
     txtrAdditionalToolsAnd.setBounds(49, 517, 902, 119);
     txtrAdditionalToolsAnd.setOpaque(false);
     add(txtrAdditionalToolsAnd);
   }
 }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.StartPanel
 * JD-Core Version:    0.6.2
 */