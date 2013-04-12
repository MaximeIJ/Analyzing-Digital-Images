/*     */ package org.gss.adi.tools;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import org.gss.adi.Entrance;
/*     */ 
/*     */ public class ConfigFileManager
/*     */ {
/*     */   private static final String HISTORY_FILE = "history.cfg";
/*     */   private static final String PREFENCES_FILE = "prefs.cfg";
/*     */ 
/*     */   public static void createRecentImages()
/*     */   {
/*  27 */     File f = new File("history.cfg");
/*  28 */     if (!f.exists())
/*     */       try {
/*  30 */         f.createNewFile(); } catch (IOException localIOException) {
/*     */       }
/*     */   }
/*     */ 
/*  34 */   public static void createPrefs() { File f = new File("prefs.cfg");
/*  35 */     if (!f.exists())
/*     */       try {
/*  37 */         f.createNewFile(); } catch (IOException localIOException) {
/*     */       } }
/*     */ 
/*     */   public static void setPrefs(Entrance e) {
/*     */     try {
/*  42 */       FileWriter outstream = new FileWriter("prefs.cfg");
/*  43 */       BufferedWriter out = new BufferedWriter(outstream);
/*  44 */       String color = "";
/*  45 */       if (e.getColor().equals(Color.BLACK))
/*  46 */         color = "black";
/*  47 */       else if (e.getColor().equals(Color.BLUE))
/*  48 */         color = "blue";
/*  49 */       else if (e.getColor().equals(Color.CYAN))
/*  50 */         color = "cyan";
/*  51 */       else if (e.getColor().equals(Color.GRAY))
/*  52 */         color = "gray";
/*  53 */       else if (e.getColor().equals(Color.GREEN))
/*  54 */         color = "green";
/*  55 */       else if (e.getColor().equals(Color.MAGENTA))
/*  56 */         color = "magenta";
/*  57 */       else if (e.getColor().equals(Color.ORANGE))
/*  58 */         color = "orange";
/*  59 */       else if (e.getColor().equals(Color.RED))
/*  60 */         color = "red";
/*  61 */       else if (e.getColor().equals(Color.WHITE))
/*  62 */         color = "white";
/*  63 */       else if (e.getColor().equals(Color.YELLOW)) {
/*  64 */         color = "yellow";
/*     */       }
/*  66 */       String s = color + "\n" + new Float(e.getLineWidth()).toString() + "\n" + new Byte(e.getCursorStyle()).toString() + "\n" + e.getLastDirectory();
/*  67 */       out.write(s);
/*  68 */       out.close(); } catch (Exception localException) {
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void usePrefs(Entrance e) {
/*     */     try { FileInputStream fstream = new FileInputStream("prefs.cfg");
/*  74 */       DataInputStream in = new DataInputStream(fstream);
/*  75 */       BufferedReader br = new BufferedReader(new InputStreamReader(in));
/*  76 */       String color = br.readLine();
/*  77 */       if (color.toLowerCase().equals("black"))
/*  78 */         e.setColor(Color.BLACK);
/*  79 */       else if (color.toLowerCase().equals("blue"))
/*  80 */         e.setColor(Color.BLUE);
/*  81 */       else if (color.toLowerCase().equals("cyan"))
/*  82 */         e.setColor(Color.CYAN);
/*  83 */       else if (color.toLowerCase().equals("gray"))
/*  84 */         e.setColor(Color.GRAY);
/*  85 */       else if (color.toLowerCase().equals("green"))
/*  86 */         e.setColor(Color.GREEN);
/*  87 */       else if (color.toLowerCase().equals("magenta"))
/*  88 */         e.setColor(Color.MAGENTA);
/*  89 */       else if (color.toLowerCase().equals("orange"))
/*  90 */         e.setColor(Color.ORANGE);
/*  91 */       else if (color.toLowerCase().equals("red"))
/*  92 */         e.setColor(Color.RED);
/*  93 */       else if (color.toLowerCase().equals("white"))
/*  94 */         e.setColor(Color.WHITE);
/*  95 */       else if (color.toLowerCase().equals("yellow")) {
/*  96 */         e.setColor(Color.YELLOW);
/*     */       }
/*  98 */       String width = br.readLine();
/*  99 */       e.setLineWidth(new Float(width).floatValue());
/* 100 */       String cursor = br.readLine();
/* 101 */       e.setCursorStyle(new Byte(cursor).byteValue());
/* 102 */       e.setLastDirectory(br.readLine());
/* 103 */       br.close(); } catch (Exception localException) {
/*     */     }
/*     */   }
/*     */ 
/* 107 */   public static void addRecentImage(File f) { createRecentImages();
/*     */     try {
/* 109 */       FileInputStream fstream = new FileInputStream("history.cfg");
/* 110 */       DataInputStream in = new DataInputStream(fstream);
/* 111 */       BufferedReader br = new BufferedReader(new InputStreamReader(in));
/* 112 */       String[] images = new String[10];
/*     */ 
/* 114 */       images[0] = f.getAbsolutePath();
/* 115 */       int i = 1;
/*     */       String s;
/* 116 */       while ((i < 10) && ((s = br.readLine()) != null))
/*     */       {
/* 117 */         images[i] = s;
/* 118 */         i++;
/*     */       }
/* 120 */       br.close();
/*     */ 
/* 122 */       FileWriter outstream = new FileWriter("history.cfg");
/* 123 */       BufferedWriter out = new BufferedWriter(outstream);
/* 124 */       s = "";
/* 125 */       if (i == 10);
/* 126 */       for (i = 9; 
/* 127 */         i >= 0; i--) {
/* 128 */         s = images[i] + "\n" + s;
/*     */       }
/* 130 */       if (s.contains("\nnull"))
/* 131 */         s = s.substring(0, s.lastIndexOf("\nnull"));
/* 132 */       out.write(s);
/* 133 */       out.close();
/*     */     } catch (Exception localException) {
/*     */     } }
/*     */ 
/*     */   public static String[] getRecentImages() {
/* 138 */     String[] images = new String[10];
/*     */     try {
/* 140 */       FileInputStream fstream = new FileInputStream("history.cfg");
/* 141 */       DataInputStream in = new DataInputStream(fstream);
/* 142 */       BufferedReader br = new BufferedReader(new InputStreamReader(in));
/*     */ 
/* 144 */       int i = 0;
/*     */       String s;
/* 145 */       while ((i < 10) && ((s = br.readLine()) != null))
/*     */       {
/* 146 */         images[i] = s;
/* 147 */         i++;
/*     */       }
/* 149 */       br.close(); } catch (Exception localException) {
/*     */     }
/* 151 */     return images;
/*     */   }
/*     */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.tools.ConfigFileManager
 * JD-Core Version:    0.6.2
 */