/*    */ package org.gss.adi;
/*    */ 
/*    */ import java.awt.Font;
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.ActionListener;
/*    */ import javax.swing.ImageIcon;
/*    */ import javax.swing.JButton;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JPanel;
/*    */ import javax.swing.JTextArea;
/*    */ 
/*    */ public class OverviewPanel extends JPanel
/*    */ {
/*    */   private static final long serialVersionUID = -5861809198438359759L;
/*    */   private Entrance entrance;
/*    */ 
/*    */   public OverviewPanel(Entrance ent)
/*    */   {
/* 24 */     setLayout(null);
/* 25 */     this.entrance = ent;
/* 26 */     setup();
/* 27 */     this.entrance.setPane(this);
/*    */   }
/*    */ 
/*    */   private void setup() {
/* 31 */     JTextArea txtrThisSoftwareProvides = new JTextArea();
/* 32 */     txtrThisSoftwareProvides.setFont(new Font("SansSerif", 0, 13));
/* 33 */     txtrThisSoftwareProvides.setText("This software provides tools for visualizing and measuring spatial and spectral (color) qualities and relationships in digital images.\r\n\r\n(1) Spatial Analysis tools measure size and colors of features.  Spatial measurements may be saved to a text file for analysis with spreadsheet software.\r\n\r\n(2) Enhance Colors tools let you change how the image is displayed.  \r\n\r\n(3) Mask Color tools lets you count the number of pixels within a range of colors within the original or a color enhanced image.\r\n\r\n(4) Use spatial tools and a few enhancement tools on up to 3 images in a time series.\r\n\r\nImages transfer fluently to each toolset, and modified images may be saved or printed.\r\n\r\nTwo utilities are in the Utilities Menu: \r\n1) Trim images to speed up processing time, and \r\n2) Combine two or more images into one for\r\n    comparison (merge color layers from separate\r\n    images, subtract images, or average images).\r\n\r\nOther useful tools in the File Menu:\r\n1) Plot color histograms of the image & \r\n    selected areas (Rectangle or Polygon).\r\n2) Plot colors along a selected Line or Path.\r\n\r\nAlthough these tools were not designed to create artistic images, beautiful and exotic looking images are possible - so save and share!");
/* 34 */     txtrThisSoftwareProvides.setWrapStyleWord(true);
/* 35 */     txtrThisSoftwareProvides.setLineWrap(true);
/* 36 */     txtrThisSoftwareProvides.setEditable(false);
/* 37 */     txtrThisSoftwareProvides.setBounds(10, 11, 399, 649);
/* 38 */     txtrThisSoftwareProvides.setOpaque(false);
/* 39 */     add(txtrThisSoftwareProvides);
/*    */ 
/* 41 */     JButton btnNewButton = new JButton("Spatial Analysis");
/* 42 */     btnNewButton.addActionListener(new ActionListener() {
/*    */       public void actionPerformed(ActionEvent arg0) {
/* 44 */         new SpatialAnalysisPanel(OverviewPanel.this.entrance);
/*    */       }
/*    */     });
/* 47 */     btnNewButton.setBounds(419, 11, 156, 49);
/* 48 */     add(btnNewButton);
/*    */ 
/* 50 */     JButton btnEnhanceColors = new JButton("Enhance Colors");
/* 51 */     btnEnhanceColors.addActionListener(new ActionListener() {
/*    */       public void actionPerformed(ActionEvent e) {
/* 53 */         new EnhanceColorsPanel(OverviewPanel.this.entrance);
/*    */       }
/*    */     });
/* 56 */     btnEnhanceColors.setBounds(585, 11, 156, 49);
/* 57 */     add(btnEnhanceColors);
/*    */ 
/* 59 */     JButton btnTimeSeries = new JButton("Time Series");
/* 60 */     btnTimeSeries.addActionListener(new ActionListener() {
/*    */       public void actionPerformed(ActionEvent e) {
/* 62 */         OverviewPanel.this.entrance.launchTimeSeries();
/*    */       }
/*    */     });
/* 65 */     btnTimeSeries.setBounds(917, 11, 156, 49);
/* 66 */     add(btnTimeSeries);
/*    */ 
/* 68 */     JButton btnMaskColors = new JButton("Mask Colors");
/* 69 */     btnMaskColors.addActionListener(new ActionListener() {
/*    */       public void actionPerformed(ActionEvent e) {
/* 71 */         new MaskColorsPanel(OverviewPanel.this.entrance);
/*    */       }
/*    */     });
/* 74 */     btnMaskColors.setBounds(751, 11, 156, 49);
/* 75 */     add(btnMaskColors);
/*    */ 
/* 77 */     JLabel lblNewLabel = new JLabel();
/* 78 */     lblNewLabel.setIcon(new ImageIcon(OverviewPanel.class.getResource("/resources/OverviewPanelImage.png")));
/* 79 */     lblNewLabel.setBounds(442, 70, 613, 430);
/* 80 */     add(lblNewLabel);
/*    */ 
/* 82 */     JTextArea txtrFlowDiagramOf = new JTextArea();
/* 83 */     txtrFlowDiagramOf.setFont(new Font("SansSerif", 0, 13));
/* 84 */     txtrFlowDiagramOf.setText("Flow diagram of how to use these tools with a digital image of a leaf.");
/* 85 */     txtrFlowDiagramOf.setEditable(false);
/* 86 */     txtrFlowDiagramOf.setWrapStyleWord(true);
/* 87 */     txtrFlowDiagramOf.setLineWrap(true);
/* 88 */     txtrFlowDiagramOf.setOpaque(false);
/* 89 */     txtrFlowDiagramOf.setBounds(585, 538, 322, 64);
/* 90 */     add(txtrFlowDiagramOf);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.OverviewPanel
 * JD-Core Version:    0.6.2
 */