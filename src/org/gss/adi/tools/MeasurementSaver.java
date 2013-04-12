/*     */ package org.gss.adi.tools;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.awt.Polygon;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Scanner;
/*     */ import javax.swing.JOptionPane;
/*     */ import org.gss.adi.ADIMenu;
/*     */ import org.gss.adi.Entrance;
/*     */ import org.gss.adi.MainFrame;
/*     */ import org.gss.adi.SWTFile;
/*     */ import org.gss.adi.dialogs.MeasurementDialog;
/*     */ 
/*     */ public class MeasurementSaver
/*     */ {
/*  26 */   private static DecimalFormat df = new DecimalFormat("#.##");
/*     */ 
/*     */   public static void createNewMeasurementFile(Entrance entrance) {
/*  29 */     JOptionPane.showMessageDialog(null, "Choosing an existing file will not overwrite the file.\nIt will append new measurements to the existing file.", null, -1);
/*  30 */     File f = SWTFile.saveFile("Create New Measurement File", "Measurement");
/*  31 */     if (f == null)
/*  32 */       return;
/*  33 */     if (!f.exists())
/*     */       try {
/*  35 */         f.createNewFile();
/*     */       } catch (IOException localIOException) {
/*     */       }
/*  38 */     entrance.setMeasurementFile(f);
/*  39 */     entrance.getMainFrame().getMenu().showMeasurement(true);
/*     */   }
/*     */   public static void readMeasurement(File f) {
/*     */     try {
/*  43 */       FileInputStream fstream = new FileInputStream(f);
/*  44 */       DataInputStream in = new DataInputStream(fstream);
/*  45 */       BufferedReader br = new BufferedReader(new InputStreamReader(in));
/*  46 */       String s = "";
/*  47 */       String str = "";
/*  48 */       while ((str = br.readLine()) != null) {
/*  49 */         s = str;
/*     */       }
/*  51 */       br.close();
/*  52 */       Scanner scan = new Scanner(s);
/*  53 */       scan.useDelimiter("\t");
/*  54 */       String scalingfactor = scan.next();
/*  55 */       String scalingunit = scan.next();
/*  56 */       String[] usrData = { scan.next() };
/*  57 */       String[] usrComment = { scan.next() };
/*  58 */       String selectedTool = scan.next();
/*  59 */       String la = scan.next();
/*  60 */       String num = scan.next();
/*  61 */       String[] coords = { scan.next(), scan.next(), scan.next(), scan.next() };
/*  62 */       String scheme = scan.next();
/*  63 */       String[] reds = { scan.next(), scan.next(), scan.next(), scan.next() };
/*  64 */       String[] greens = { scan.next(), scan.next(), scan.next(), scan.next() };
/*  65 */       String[] blues = { scan.next(), scan.next(), scan.next(), scan.next() };
/*  66 */       new MeasurementDialog(false, scalingfactor, scalingunit, usrData, usrComment, selectedTool, 
/*  67 */         la, num, coords, scheme, reds, greens, blues).setVisible(true);
/*  68 */       scan.close(); } catch (Exception e) {
/*  69 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*  73 */   public static void saveMeasurement(Entrance entrance, Integer[] x, Integer[] y, String tool, BufferedImage img, String colorScheme) { if (tool.contains("Select"))
/*  74 */       return;
/*  75 */     String[] userData = { "" };
/*  76 */     String[] comment = { "" };
/*     */ 
/*  78 */     String s = "";
/*  79 */     if ((entrance.getMeasurementFile() == null) || (!entrance.getMeasurementFile().exists())) {
/*  80 */       createNewMeasurementFile(entrance);
/*     */       try
/*     */       {
/*  83 */         BufferedReader buff = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(entrance.getMeasurementFile()))));
/*  84 */         if ((buff.readLine() == null) || (buff.readLine().length() < 2)) {
/*  85 */           s = "Image Name\tScaling Factor\tScaling Unit\tUser Data\tUser Comments\tAnalysis Type\tLength/Area\tNumber of Pixels\tX1\tY1\tX2\tY2\tColor Scheme\tAverage Red\tMax Red\tMin Red\tStDev Red\tAverage Green\tMax Green\tMin Green\tStDev Green\tAverage Blue\tMax Blue\tMin Blue\tStDev Blue\r\n";
/*     */         }
/*     */ 
/*  89 */         buff.close();
/*     */       } catch (Exception e) {
/*  91 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */     try
/*     */     {
/*  96 */       File f = entrance.getMeasurementFile();
/*  97 */       FileInputStream fstream = new FileInputStream(f);
/*  98 */       DataInputStream in = new DataInputStream(fstream);
/*  99 */       BufferedReader br = new BufferedReader(new InputStreamReader(in));
/* 100 */       String str = "";
/* 101 */       while ((str = br.readLine()) != null) {
/* 102 */         s = s + str + "\r\n";
/*     */       }
/* 104 */       br.close();
/* 105 */       String cur = "";
/* 106 */       String temp = entrance.getTitle() + "\t";
/* 107 */       Measurement m = entrance.getMeasurement();
/* 108 */       String unitOfMsr = "";
/* 109 */       Double distPerPix = Double.valueOf(0.0D);
/* 110 */       if (m != null) {
/* 111 */         unitOfMsr = m._unitOfMsr;
/* 112 */         distPerPix = new Double(m._distPerPix.doubleValue());
/* 113 */         temp = temp + distPerPix + "\t" + unitOfMsr + "\t";
/*     */       } else {
/* 115 */         temp = temp + "\t\t";
/*     */       }
/* 117 */       double length = 0.0D;
/* 118 */       Integer maxR = Integer.valueOf(0); Integer maxB = Integer.valueOf(0); Integer maxG = Integer.valueOf(0); Integer minR = Integer.valueOf(256); Integer minG = Integer.valueOf(256); Integer minB = Integer.valueOf(256); Integer totalR = Integer.valueOf(0); Integer totalG = Integer.valueOf(0); Integer totalB = Integer.valueOf(0);
/* 119 */       Double meanR = Double.valueOf(0.0D); Double meanG = Double.valueOf(0.0D); Double meanB = Double.valueOf(0.0D);
/* 120 */       Double[] stds = { Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(0.0D) };
/* 121 */       if (tool.contains("Pixel")) {
/* 122 */         int[] rgb = ColorTools.rgb(img.getRGB(x[0].intValue(), y[0].intValue()));
/* 123 */         maxR = minR = Integer.valueOf(rgb[0]);
/* 124 */         maxG = minG = Integer.valueOf(rgb[1]);
/* 125 */         maxB = minB = Integer.valueOf(rgb[2]);
/* 126 */         meanR = new Double(rgb[0]);
/* 127 */         meanG = new Double(rgb[1]);
/* 128 */         meanB = new Double(rgb[2]);
/* 129 */         cur = cur + "Pixel\tn/a\t1\t" + x[0] + "\t" + y[0] + "\tn/a\tn/a\t" + colorScheme + "\t" + rgb[0] + "\t" + rgb[0] + "\t" + rgb[0] + "\tn/a\t" + rgb[1] + "\t" + rgb[1] + "\t" + rgb[1] + "\tn/a\t" + rgb[2] + "\t" + rgb[2] + "\t" + rgb[2] + "\tn/a\n";
/* 130 */       } else if (tool.contains("Line")) {
/* 131 */         ArrayList points = ColorTools.getLinePixels(x, y);
/* 132 */         for (int i = 0; i < points.size(); i++) {
/* 133 */           Point p = (Point)points.get(i);
/* 134 */           int[] rgb = ColorTools.rgb(img.getRGB(p.x, p.y));
/* 135 */           totalR = Integer.valueOf(totalR.intValue() + rgb[0]);
/* 136 */           if (rgb[0] > maxR.intValue()) maxR = Integer.valueOf(rgb[0]);
/* 137 */           if (rgb[0] < minR.intValue()) minR = Integer.valueOf(rgb[0]);
/* 138 */           totalG = Integer.valueOf(totalG.intValue() + rgb[1]);
/* 139 */           if (rgb[1] > maxG.intValue()) maxG = Integer.valueOf(rgb[1]);
/* 140 */           if (rgb[1] < minG.intValue()) minG = Integer.valueOf(rgb[1]);
/* 141 */           totalB = Integer.valueOf(totalB.intValue() + rgb[2]);
/* 142 */           if (rgb[2] > maxB.intValue()) maxB = Integer.valueOf(rgb[2]);
/* 143 */           if (rgb[2] < minB.intValue()) minB = Integer.valueOf(rgb[2]);
/*     */         }
/* 145 */         meanR = Double.valueOf(1.0D * totalR.intValue() / points.size());
/* 146 */         meanG = Double.valueOf(1.0D * totalG.intValue() / points.size());
/* 147 */         meanB = Double.valueOf(1.0D * totalB.intValue() / points.size());
/* 148 */         stds = stdDev(points, img, new double[] { meanR.doubleValue(), meanG.doubleValue(), meanB.doubleValue() });
/* 149 */         length = ColorTools.linearDist(x[0].intValue(), y[0].intValue(), x[1].intValue(), y[1].intValue()).doubleValue();
/* 150 */         cur = cur + "Line\t" + distPerPix.doubleValue() * length + "\t" + length + "\t" + x[0] + "\t" + y[0] + "\t" + x[1] + "\t" + y[1] + "\t" + colorScheme + "\t" + meanR + "\t" + maxR + "\t" + minR + "\t" + stds[0] + "\t" + meanG + "\t" + maxG + "\t" + minG + "\t" + stds[1] + "\t" + meanB + "\t" + maxB + "\t" + minB + "\t" + stds[2] + "\n";
/* 151 */       } else if (tool.contains("Rectangle"))
/*     */       {
/*     */         int xmax;
/*     */         int xmin;
/* 153 */         if (x[0].intValue() <= x[1].intValue()) {
/* 154 */           xmin = x[0].intValue();
/* 155 */           xmax = x[1].intValue();
/*     */         } else {
/* 157 */           xmin = x[1].intValue();
/* 158 */           xmax = x[0].intValue();
/*     */         }
/*     */         int ymax;
/*     */         int ymin;
/* 160 */         if (y[0].intValue() <= y[1].intValue()) {
/* 161 */           ymin = y[0].intValue();
/* 162 */           ymax = y[1].intValue();
/*     */         } else {
/* 164 */           ymin = y[1].intValue();
/* 165 */           ymax = y[0].intValue();
/*     */         }
/* 167 */         int total = 0;
/* 168 */         ArrayList points = new ArrayList();
/* 169 */         for (int i = xmin; i < xmax; i++) {
/* 170 */           for (int j = ymin; j < ymax; j++) {
/* 171 */             total++;
/* 172 */             points.add(new Point(i, j));
/* 173 */             int[] rgb = ColorTools.rgb(img.getRGB(i, j));
/* 174 */             totalR = Integer.valueOf(totalR.intValue() + rgb[0]);
/* 175 */             if (rgb[0] > maxR.intValue()) maxR = Integer.valueOf(rgb[0]);
/* 176 */             if (rgb[0] < minR.intValue()) minR = Integer.valueOf(rgb[0]);
/* 177 */             totalG = Integer.valueOf(totalG.intValue() + rgb[1]);
/* 178 */             if (rgb[1] > maxG.intValue()) maxG = Integer.valueOf(rgb[1]);
/* 179 */             if (rgb[1] < minG.intValue()) minG = Integer.valueOf(rgb[1]);
/* 180 */             totalB = Integer.valueOf(totalB.intValue() + rgb[2]);
/* 181 */             if (rgb[2] > maxB.intValue()) maxB = Integer.valueOf(rgb[2]);
/* 182 */             if (rgb[2] < minB.intValue()) minB = Integer.valueOf(rgb[2]);
/*     */           }
/*     */         }
/* 185 */         meanR = Double.valueOf(1.0D * totalR.intValue() / total);
/* 186 */         meanG = Double.valueOf(1.0D * totalG.intValue() / total);
/* 187 */         meanB = Double.valueOf(1.0D * totalB.intValue() / total);
/* 188 */         stds = stdDev(points, img, new double[] { meanR.doubleValue(), meanG.doubleValue(), meanB.doubleValue() });
/* 189 */         length = ColorTools.linearDist(x[0].intValue(), y[0].intValue(), x[1].intValue(), y[1].intValue()).doubleValue();
/* 190 */         cur = cur + "Rectangle\t" + distPerPix.doubleValue() * length + "\t" + length + "\t" + x[0] + "\t" + y[0] + "\t" + x[1] + "\t" + y[1] + "\t" + colorScheme + "\t" + meanR + "\t" + maxR + "\t" + minR + "\t" + stds[0] + "\t" + meanG + "\t" + maxG + "\t" + minG + "\t" + stds[1] + "\t" + meanB + "\t" + maxB + "\t" + minB + "\t" + stds[2] + "\n";
/* 191 */       } else if (tool.contains("Path")) {
/* 192 */         ArrayList points = new ArrayList();
/* 193 */         for (int i = 1; i < x.length; i++) {
/* 194 */           points.addAll(ColorTools.getLinePixels(new Integer[] { x[(i - 1)], x[i] }, new Integer[] { y[(i - 1)], y[i] }));
/*     */         }
/* 196 */         for (int i = 0; i < points.size(); i++) {
/* 197 */           Point p = (Point)points.get(i);
/* 198 */           int[] rgb = ColorTools.rgb(img.getRGB(p.x, p.y));
/* 199 */           totalR = Integer.valueOf(totalR.intValue() + rgb[0]);
/* 200 */           if (rgb[0] > maxR.intValue()) maxR = Integer.valueOf(rgb[0]);
/* 201 */           if (rgb[0] < minR.intValue()) minR = Integer.valueOf(rgb[0]);
/* 202 */           totalG = Integer.valueOf(totalG.intValue() + rgb[1]);
/* 203 */           if (rgb[1] > maxG.intValue()) maxG = Integer.valueOf(rgb[1]);
/* 204 */           if (rgb[1] < minG.intValue()) minG = Integer.valueOf(rgb[1]);
/* 205 */           totalB = Integer.valueOf(totalB.intValue() + rgb[2]);
/* 206 */           if (rgb[2] > maxB.intValue()) maxB = Integer.valueOf(rgb[2]);
/* 207 */           if (rgb[2] < minB.intValue()) minB = Integer.valueOf(rgb[2]);
/*     */         }
/* 209 */         meanR = Double.valueOf(1.0D * totalR.intValue() / points.size());
/* 210 */         meanG = Double.valueOf(1.0D * totalG.intValue() / points.size());
/* 211 */         meanB = Double.valueOf(1.0D * totalB.intValue() / points.size());
/* 212 */         stds = stdDev(points, img, new double[] { meanR.doubleValue(), meanG.doubleValue(), meanB.doubleValue() });
/* 213 */         length = ColorTools.linearDist(x[0].intValue(), y[0].intValue(), x[1].intValue(), y[1].intValue()).doubleValue();
/* 214 */         cur = cur + "Path\t" + distPerPix.doubleValue() * length + "\t" + length + "\tn/a\tn/a\tn/a\tn/a\t" + colorScheme + "\t" + meanR + "\t" + maxR + "\t" + minR + "\t" + stds[0] + "\t" + meanG + "\t" + maxG + "\t" + minG + "\t" + stds[1] + "\t" + meanB + "\t" + maxB + "\t" + minB + "\t" + stds[2] + "\n";
/* 215 */       } else if (tool.contains("Polygon")) {
/* 216 */         int total = 0;
/* 217 */         ArrayList points = new ArrayList();
/* 218 */         int[] X = new int[x.length]; int[] Y = new int[y.length];
/* 219 */         for (int i = 0; i < x.length; i++) {
/* 220 */           X[i] = x[i].intValue();
/* 221 */           Y[i] = y[i].intValue();
/*     */         }
/* 223 */         Polygon poly = new Polygon(X, Y, X.length);
/* 224 */         for (int i = 0; i < img.getWidth(); i++) {
/* 225 */           for (int j = 0; j < img.getHeight(); j++) {
/* 226 */             if (poly.contains(i, j)) {
/* 227 */               total++;
/* 228 */               points.add(new Point(i, j));
/* 229 */               int[] rgb = ColorTools.rgb(img.getRGB(i, j));
/* 230 */               totalR = Integer.valueOf(totalR.intValue() + rgb[0]);
/* 231 */               if (rgb[0] > maxR.intValue()) maxR = Integer.valueOf(rgb[0]);
/* 232 */               if (rgb[0] < minR.intValue()) minR = Integer.valueOf(rgb[0]);
/* 233 */               totalG = Integer.valueOf(totalG.intValue() + rgb[1]);
/* 234 */               if (rgb[1] > maxG.intValue()) maxG = Integer.valueOf(rgb[1]);
/* 235 */               if (rgb[1] < minG.intValue()) minG = Integer.valueOf(rgb[1]);
/* 236 */               totalB = Integer.valueOf(totalB.intValue() + rgb[2]);
/* 237 */               if (rgb[2] > maxB.intValue()) maxB = Integer.valueOf(rgb[2]);
/* 238 */               if (rgb[2] < minB.intValue()) minB = Integer.valueOf(rgb[2]);
/*     */             }
/*     */           }
/*     */         }
/* 242 */         meanR = Double.valueOf(1.0D * totalR.intValue() / total);
/* 243 */         meanG = Double.valueOf(1.0D * totalG.intValue() / total);
/* 244 */         meanB = Double.valueOf(1.0D * totalB.intValue() / total);
/* 245 */         stds = stdDev(points, img, new double[] { meanR.doubleValue(), meanG.doubleValue(), meanB.doubleValue() });
/* 246 */         length = ColorTools.linearDist(x[0].intValue(), y[0].intValue(), x[1].intValue(), y[1].intValue()).doubleValue();
/* 247 */         cur = cur + "Polygon\t" + distPerPix.doubleValue() * length + "\t" + length + "\tn/a\tn/a\tn/a\tn/a\t" + colorScheme + "\t" + meanR + "\t" + maxR + "\t" + minR + "\t" + stds[0] + "\t" + meanG + "\t" + maxG + "\t" + minG + "\t" + stds[1] + "\t" + meanB + "\t" + maxB + "\t" + minB + "\t" + stds[2] + "\n";
/* 248 */       } else if (tool.contains("Angle")) {
/* 249 */         cur = cur + "Angle\t" + ColorTools.angle(x, y) + "\tdegrees\n";
/*     */       }
/* 251 */       new MeasurementDialog(true, df.format(distPerPix), unitOfMsr, userData, comment, tool, 
/* 252 */         df.format(distPerPix.doubleValue() * length), df.format(length), new String[] { x[0].toString(), y[0].toString(), x[1].toString(), y[1].toString() }, colorScheme, 
/* 253 */         new String[] { df.format(meanR), maxR.toString(), minR.toString(), df.format(stds[0]) }, new String[] { df.format(meanG), maxG.toString(), minG.toString(), df.format(stds[1]) }, 
/* 254 */         new String[] { df.format(meanB), maxB.toString(), minB.toString(), df.format(stds[2]) }).setVisible(true);
/* 255 */       if (comment[0].equals("~cancelled~")) {
/* 256 */         return;
/*     */       }
/* 258 */       FileWriter outstream = new FileWriter(f);
/* 259 */       BufferedWriter out = new BufferedWriter(outstream);
/* 260 */       temp = temp + userData[0] + " \t" + comment[0] + " \t";
/* 261 */       cur = temp + cur;
/* 262 */       s = s + cur;
/* 263 */       out.write(s);
/* 264 */       out.close(); } catch (Exception e) {
/* 265 */       e.printStackTrace();
/*     */     } } 
/*     */   private static Double[] stdDev(ArrayList<Point> points, BufferedImage img, double[] means) {
/* 268 */     int sumR = 0; int sumG = 0; int sumB = 0;
/* 269 */     for (int i = 0; i < points.size(); i++) {
/* 270 */       Point p = (Point)points.get(i);
/* 271 */       int[] rgb = ColorTools.rgb(img.getRGB(p.x, p.y));
/* 272 */       sumR = (int)(sumR + Math.pow(rgb[0] - means[0], 2.0D));
/* 273 */       sumG = (int)(sumG + Math.pow(rgb[1] - means[1], 2.0D));
/* 274 */       sumB = (int)(sumB + Math.pow(rgb[2] - means[2], 2.0D));
/*     */     }
/* 276 */     double stdR = Math.sqrt(1.0D * sumR / points.size());
/* 277 */     double stdG = Math.sqrt(1.0D * sumG / points.size());
/* 278 */     double stdB = Math.sqrt(1.0D * sumB / points.size());
/* 279 */     return new Double[] { Double.valueOf(stdR), Double.valueOf(stdG), Double.valueOf(stdB) };
/*     */   }
/*     */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.tools.MeasurementSaver
 * JD-Core Version:    0.6.2
 */