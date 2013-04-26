 package org.gss.adi.datapanels;
 
 import java.awt.Color;
 import java.awt.image.BufferedImage;
 import java.text.DecimalFormat;
 import javax.swing.JPanel;
 
 public abstract class DataPanel extends JPanel
 {
   private static final long serialVersionUID = 1119986578239584021L;
   final Color red1 = new Color(16711680);
   final Color red2 = new Color(16732240);
   final Color red3 = new Color(16748688);
   final Color green1 = new Color(43520);
   final Color green2 = new Color(5286480);
   final Color green3 = new Color(9489552);
   final Color blue1 = new Color(255);
   final Color blue2 = new Color(5263615);
   final Color blue3 = new Color(9474303);
   final Color black1 = new Color(0);
   final Color black2 = new Color(4210752);
   final Color black3 = new Color(7368816);
 
   DecimalFormat df = new DecimalFormat("###.##");
 
   public DataPanel(BufferedImage img1, BufferedImage img2, BufferedImage img3, int rgb)
   {
     setLayout(null);
     setLocation(0, 0);
     setSize(450, 0);
   }
 
   public abstract void update1(Integer[] paramArrayOfInteger1, Integer[] paramArrayOfInteger2);
 
   public abstract void update2(Integer[] paramArrayOfInteger1, Integer[] paramArrayOfInteger2);
 
   public abstract void update3(Integer[] paramArrayOfInteger1, Integer[] paramArrayOfInteger2);
 
   public abstract void threePics();
 }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.datapanels.DataPanel
 * JD-Core Version:    0.6.2
 */