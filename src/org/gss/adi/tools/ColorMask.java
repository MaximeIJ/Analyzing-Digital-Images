/*    */ package org.gss.adi.tools;
/*    */ 
/*    */ import java.util.Scanner;
/*    */ 
/*    */ public class ColorMask
/*    */ {
/*  9 */   public int redMin = 0;
/* 10 */   public int redMax = 255;
/* 11 */   public int greenMin = 0;
/* 12 */   public int greenMax = 255;
/* 13 */   public int blueMin = 0;
/* 14 */   public int blueMax = 255;
/* 15 */   public boolean red = true;
/* 16 */   public boolean green = true;
/* 17 */   public boolean blue = true;
/* 18 */   public String title = "";
/*    */ 
/* 20 */   public ColorMask(String s) { Scanner scan = new Scanner(s);
/* 21 */     scan.useDelimiter("\t");
/* 22 */     this.redMin = new Integer(scan.next()).intValue();
/* 23 */     this.redMax = new Integer(scan.next()).intValue();
/* 24 */     this.greenMin = new Integer(scan.next()).intValue();
/* 25 */     this.greenMax = new Integer(scan.next()).intValue();
/* 26 */     this.blueMin = new Integer(scan.next()).intValue();
/* 27 */     this.blueMax = new Integer(scan.next()).intValue();
/* 28 */     this.red = new Boolean(scan.next()).booleanValue();
/* 29 */     this.green = new Boolean(scan.next()).booleanValue();
/* 30 */     this.blue = new Boolean(scan.next()).booleanValue();
/* 31 */     this.title = scan.next(); }
/*    */ 
/*    */   public ColorMask(int rMin, int rMax, int gMin, int gMax, int bMin, int bMax, boolean r, boolean g, boolean b)
/*    */   {
/* 35 */     this.redMin = rMin;
/* 36 */     this.redMax = rMax;
/* 37 */     this.greenMin = gMin;
/* 38 */     this.greenMax = gMax;
/* 39 */     this.blueMin = bMin;
/* 40 */     this.blueMax = bMax;
/* 41 */     this.red = r;
/* 42 */     this.green = g;
/* 43 */     this.blue = b;
/*    */   }
/*    */   public void setTitle(String titl) {
/* 46 */     this.title = titl;
/*    */   }
/*    */   public String toString() {
/* 49 */     return this.redMin + "\t" + this.redMax + "\t" + this.greenMin + "\t" + this.greenMax + "\t" + this.blueMin + "\t" + 
/* 50 */       this.blueMax + "\t" + this.red + "\t" + this.green + "\t" + this.blue + "\t" + this.title + "\r\n";
/*    */   }
/*    */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.tools.ColorMask
 * JD-Core Version:    0.6.2
 */