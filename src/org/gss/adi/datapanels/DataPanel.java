/*    */ package org.gss.adi.datapanels;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.text.DecimalFormat;
/*    */ import javax.swing.JPanel;
/*    */ 
/*    */ public abstract class DataPanel extends JPanel
/*    */ {
/*    */   private static final long serialVersionUID = 1119986578239584021L;
/* 12 */   final Color red1 = new Color(16711680);
/* 13 */   final Color red2 = new Color(16732240);
/* 14 */   final Color red3 = new Color(16748688);
/* 15 */   final Color green1 = new Color(43520);
/* 16 */   final Color green2 = new Color(5286480);
/* 17 */   final Color green3 = new Color(9489552);
/* 18 */   final Color blue1 = new Color(255);
/* 19 */   final Color blue2 = new Color(5263615);
/* 20 */   final Color blue3 = new Color(9474303);
/* 21 */   final Color black1 = new Color(0);
/* 22 */   final Color black2 = new Color(4210752);
/* 23 */   final Color black3 = new Color(7368816);
/*    */ 
/* 25 */   DecimalFormat df = new DecimalFormat("###.##");
/*    */ 
/*    */   public DataPanel(BufferedImage img1, BufferedImage img2, BufferedImage img3, int rgb)
/*    */   {
/* 30 */     setLayout(null);
/* 31 */     setLocation(0, 0);
/* 32 */     setSize(450, 0);
/*    */   }
/*    */ 
/*    */   public abstract void update1(Integer[] paramArrayOfInteger1, Integer[] paramArrayOfInteger2);
/*    */ 
/*    */   public abstract void update2(Integer[] paramArrayOfInteger1, Integer[] paramArrayOfInteger2);
/*    */ 
/*    */   public abstract void update3(Integer[] paramArrayOfInteger1, Integer[] paramArrayOfInteger2);
/*    */ 
/*    */   public abstract void threePics();
/*    */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.datapanels.DataPanel
 * JD-Core Version:    0.6.2
 */