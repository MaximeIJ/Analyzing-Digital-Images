/*    */ package org.gss.adi;
/*    */ 
/*    */ import java.awt.Font;
/*    */ import java.io.IOException;
/*    */ import javax.imageio.ImageIO;
/*    */ import javax.swing.JTextArea;
/*    */ 
/*    */ public class AboutADIPanel extends ImagePanel
/*    */ {
/*    */   private static final long serialVersionUID = 1495849072889976059L;
/* 15 */   private final String OVERVIEW_IMAGE = "/resources/OverviewPanelImage.png";
/*    */ 
/*    */   public AboutADIPanel(Entrance ent)
/*    */   {
/* 20 */     super(ent, true);
/* 21 */     setLayout(null);
/* 22 */     setup();
/*    */     try {
/* 24 */       this.label.setImage(ImageIO.read(AboutADIPanel.class.getResource("/resources/OverviewPanelImage.png")));
/*    */     } catch (IOException e) {
/* 26 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */   public void updateTool() {
/*    */   }
/*    */ 
/*    */   protected void closingSequence() {
/*    */   }
/*    */ 
/* 35 */   byte getType() { return 3; }
/*    */ 
/*    */   private void setup() {
/* 38 */     JTextArea txtrThisSoftwareProvides = new JTextArea();
/* 39 */     txtrThisSoftwareProvides.setOpaque(false);
/* 40 */     txtrThisSoftwareProvides.setEditable(false);
/* 41 */     txtrThisSoftwareProvides.setText("This software provides tools for visualizing and measuring spatial and spectral (color) qualities and relationships in digital images.\r\n\r\n(1) Spatial Analysis tools measure size and colors of features.  Spatial measurements may be saved to a text file for analysis with spreadsheet software.\r\n\r\n(2) Enhance Colors tools let you change how the image is displayed.  \r\n\r\n(3) Mask Color tools lets you count the number of pixels within a range of colors within the original or a color enhanced image.\r\n\r\n(4) Use spatial tools and a few enhancement tools on up to 3 images in a time series.\r\n\r\nImages transfer fluently to each toolset, and modified images may be saved or printed.\r\n\r\nTwo utilities are in the Utilities Menu: \r\n1) Trim images to speed up processing time, and \r\n2) Combine two or more images into one for\r\n    comparison (merge color layers from separate\r\n    images, subtract images, or average images).\r\n\r\nOther useful tools in the File Menu:\r\n1) Plot color histograms of the image & \r\n    selected areas (Rectangle or Polygon).\r\n2) Plot colors along a selected Line or Path.\r\n\r\nAlthough these tools were not designed to create artistic images, beautiful and exotic looking images are possible - so save and share!");
/* 42 */     txtrThisSoftwareProvides.setFont(new Font("SansSerif", 0, 12));
/* 43 */     txtrThisSoftwareProvides.setLineWrap(true);
/* 44 */     txtrThisSoftwareProvides.setWrapStyleWord(true);
/* 45 */     txtrThisSoftwareProvides.setBounds(10, 11, 320, 650);
/* 46 */     add(txtrThisSoftwareProvides);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.AboutADIPanel
 * JD-Core Version:    0.6.2
 */