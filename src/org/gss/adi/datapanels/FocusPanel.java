 package org.gss.adi.datapanels;
 
 import java.awt.image.BufferedImage;
 
 public abstract class FocusPanel extends DataPanel
 {
   private static final long serialVersionUID = -1095217569372482840L;
 
   public FocusPanel(BufferedImage img1, BufferedImage img2, BufferedImage img3, int rgb)
   {
     super(img1, img2, img3, rgb);
   }
 
   public abstract void clear();
 
   public abstract void draw1();
 
   public abstract void draw2();
 
   public abstract void draw3();
 }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.datapanels.FocusPanel
 * JD-Core Version:    0.6.2
 */