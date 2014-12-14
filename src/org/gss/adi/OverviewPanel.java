 package org.gss.adi;
 
 import java.awt.Font;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import javax.swing.ImageIcon;
 import javax.swing.JButton;
 import javax.swing.JLabel;
 import javax.swing.JPanel;
 import javax.swing.JTextArea;
 
 public class OverviewPanel extends JPanel
 {
   private static final long serialVersionUID = -5861809198438359759L;
   private Entrance entrance;
 
   public OverviewPanel(Entrance ent)
   {
     setLayout(null);
     this.entrance = ent;
     setup();
     this.entrance.setPane(this);
   }
 
   private void setup() {
     JTextArea txtrThisSoftwareProvides = new JTextArea();
     txtrThisSoftwareProvides.setFont(new Font("SansSerif", 0, 13));
     txtrThisSoftwareProvides.setText("This software provides tools for visualizing and measuring spatial and spectral (color) qualities and relationships in digital images.\r\n\r\n(1) Spatial Analysis tools measure size and colors of features.  Spatial measurements may be saved to a text file for analysis with spreadsheet software.\r\n\r\n(2) Enhance Colors tools let you change how the image is displayed.  \r\n\r\n(3) Mask Color tools lets you count the number of pixels within a range of colors within the original or a color enhanced image.\r\n\r\n(4) Use spatial tools and a few enhancement tools on up to 3 images in a time series.\r\n\r\nImages transfer fluently to each toolset, and modified images may be saved or printed.\r\n\r\nTwo utilities are in the Utilities Menu: \r\n1) Trim images to speed up processing time, and \r\n2) Combine two or more images into one for\r\n    comparison (merge color layers from separate\r\n    images, subtract images, or average images).\r\n\r\nOther useful tools in the File Menu:\r\n1) Plot color histograms of the image & \r\n    selected areas (Rectangle or Polygon).\r\n2) Plot colors along a selected Line or Path.\r\n\r\nAlthough these tools were not designed to create artistic images, beautiful and exotic looking images are possible - so save and share!");
     txtrThisSoftwareProvides.setWrapStyleWord(true);
     txtrThisSoftwareProvides.setLineWrap(true);
     txtrThisSoftwareProvides.setEditable(false);
     txtrThisSoftwareProvides.setBounds(10, 11, 399, 649);
     txtrThisSoftwareProvides.setOpaque(false);
     add(txtrThisSoftwareProvides);
 
     JButton btnNewButton = new JButton("Spatial Analysis");
     btnNewButton.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent arg0) {
         System.out.println("Here");
    	   new SpatialAnalysisPanel(OverviewPanel.this.entrance);
       }
     });
     btnNewButton.setBounds(419, 11, 156, 49);
     add(btnNewButton);
 
     JButton btnEnhanceColors = new JButton("Enhance Colors");
     btnEnhanceColors.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         new EnhanceColorsPanel(OverviewPanel.this.entrance);
       }
     });
     btnEnhanceColors.setBounds(585, 11, 156, 49);
     add(btnEnhanceColors);
 
     JButton btnTimeSeries = new JButton("Time Series");
     btnTimeSeries.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         OverviewPanel.this.entrance.launchTimeSeries();
       }
     });
     btnTimeSeries.setBounds(917, 11, 156, 49);
     add(btnTimeSeries);
 
     JButton btnMaskColors = new JButton("Mask Colors");
     btnMaskColors.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         new MaskColorsPanel(OverviewPanel.this.entrance);
       }
     });
     btnMaskColors.setBounds(751, 11, 156, 49);
     add(btnMaskColors);
 
     JLabel lblNewLabel = new JLabel();
     lblNewLabel.setIcon(new ImageIcon(OverviewPanel.class.getResource("/resources/OverviewPanelImage.png")));
     lblNewLabel.setBounds(442, 70, 613, 430);
     add(lblNewLabel);
 
     JTextArea txtrFlowDiagramOf = new JTextArea();
     txtrFlowDiagramOf.setFont(new Font("SansSerif", 0, 13));
     txtrFlowDiagramOf.setText("Flow diagram of how to use these tools with a digital image of a leaf.");
     txtrFlowDiagramOf.setEditable(false);
     txtrFlowDiagramOf.setWrapStyleWord(true);
     txtrFlowDiagramOf.setLineWrap(true);
     txtrFlowDiagramOf.setOpaque(false);
     txtrFlowDiagramOf.setBounds(585, 538, 322, 64);
     add(txtrFlowDiagramOf);
   }
 }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.OverviewPanel
 * JD-Core Version:    0.6.2
 */