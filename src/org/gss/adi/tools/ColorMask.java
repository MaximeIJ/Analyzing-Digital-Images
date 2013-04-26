 package org.gss.adi.tools;
 
 import java.util.Scanner;
 
 public class ColorMask
 {
   public int redMin = 0;
   public int redMax = 255;
   public int greenMin = 0;
   public int greenMax = 255;
   public int blueMin = 0;
   public int blueMax = 255;
   public boolean red = true;
   public boolean green = true;
   public boolean blue = true;
   public String title = "";
 
   public ColorMask(String s) { Scanner scan = new Scanner(s);
     scan.useDelimiter("\t");
     this.redMin = new Integer(scan.next()).intValue();
     this.redMax = new Integer(scan.next()).intValue();
     this.greenMin = new Integer(scan.next()).intValue();
     this.greenMax = new Integer(scan.next()).intValue();
     this.blueMin = new Integer(scan.next()).intValue();
     this.blueMax = new Integer(scan.next()).intValue();
     this.red = new Boolean(scan.next()).booleanValue();
     this.green = new Boolean(scan.next()).booleanValue();
     this.blue = new Boolean(scan.next()).booleanValue();
     this.title = scan.next(); }
 
   public ColorMask(int rMin, int rMax, int gMin, int gMax, int bMin, int bMax, boolean r, boolean g, boolean b)
   {
     this.redMin = rMin;
     this.redMax = rMax;
     this.greenMin = gMin;
     this.greenMax = gMax;
     this.blueMin = bMin;
     this.blueMax = bMax;
     this.red = r;
     this.green = g;
     this.blue = b;
   }
   public void setTitle(String titl) {
     this.title = titl;
   }
   public String toString() {
     return this.redMin + "\t" + this.redMax + "\t" + this.greenMin + "\t" + this.greenMax + "\t" + this.blueMin + "\t" + 
       this.blueMax + "\t" + this.red + "\t" + this.green + "\t" + this.blue + "\t" + this.title + "\r\n";
   }
 }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.tools.ColorMask
 * JD-Core Version:    0.6.2
 */