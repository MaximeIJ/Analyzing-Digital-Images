/*     */ package org.gss.adi;
/*     */ 
/*     */ import java.awt.Frame;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.print.PageFormat;
/*     */ import java.awt.print.Printable;
/*     */ import java.awt.print.PrinterException;
/*     */ import java.awt.print.PrinterJob;
/*     */ import java.io.File;
/*     */ import java.io.FilenameFilter;
/*     */ import javax.swing.JOptionPane;
/*     */ import org.eclipse.swt.widgets.Display;
/*     */ import org.eclipse.swt.widgets.Shell;
/*     */ import org.gss.adi.dialogs.ImageSaveDialog;
/*     */ 
/*     */ public class SWTFile
/*     */ {
/*     */   public static File openImage(String title, Entrance entrance)
/*     */   {
/*     */     try
/*     */     {
/*  35 */       Display display = new Display();
/*  36 */       Shell sh = new Shell(display);
/*  37 */       org.eclipse.swt.widgets.FileDialog fc = new org.eclipse.swt.widgets.FileDialog(sh);
/*  38 */       String[] fileTypes = { "Images *.jpg; *.png; *.jpeg; *.bmp; *.gif; *.wbmp" };
/*  39 */       String[] fileExtensions = { "*.jpg; *.png; *.jpeg; *.bmp; *.gif; *.wbmp" };
/*  40 */       fc.setFilterExtensions(fileExtensions);
/*  41 */       fc.setFilterNames(fileTypes);
/*  42 */       fc.setOverwrite(true);
/*  43 */       fc.setText(title);
/*  44 */       String path = fc.open();
/*  45 */       if (path == null)
/*  46 */         return null;
/*  47 */       File f = new File(path);
/*  48 */       sh.close();
/*  49 */       while (!sh.isDisposed()) {
/*  50 */         if (!display.readAndDispatch()) display.sleep();
/*     */       }
/*  52 */       display.dispose();
/*  53 */       return f;
/*     */     } catch (Throwable t) {
/*  55 */       java.awt.FileDialog fc = new java.awt.FileDialog(entrance.getMainFrame());
/*  56 */       String lastDir = entrance.getLastDirectory();
/*  57 */       if (lastDir != null)
/*  58 */         fc.setDirectory(lastDir);
/*  59 */       fc.setFilenameFilter(new ImageFilter());
/*  60 */       fc.setFile("*.jpg; *.jpeg; *.png; *.gif; *.bmp; *.wbmp");
/*  61 */       fc.setVisible(true);
/*  62 */       String s = fc.getFile();
/*  63 */       if (s == null) {
/*  64 */         return null;
/*     */       }
/*  66 */       File f = new File(fc.getDirectory() + s);
/*  67 */       fc.dispose();
/*  68 */       entrance.setLastDirectory(f.getParent());
/*  69 */       return f;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void saveImage(Entrance entrance)
/*     */   {
/*  76 */     if (entrance.getImage() == null)
/*  77 */       JOptionPane.showMessageDialog(null, "There is no image to save.", null, -1);
/*     */     else
/*  79 */       new ImageSaveDialog(entrance).setVisible(true);
/*     */   }
/*     */ 
/*     */   public static File saveFile(String title, String initialname)
/*     */   {
/*     */     try
/*     */     {
/*  87 */       org.eclipse.swt.widgets.FileDialog fc = new org.eclipse.swt.widgets.FileDialog(new Shell(), 8192);
/*  88 */       String[] fileTypes = { "Text Files *.txt" };
/*  89 */       String[] fileExtensions = { "*.txt" };
/*  90 */       fc.setFilterExtensions(fileExtensions);
/*  91 */       fc.setFilterNames(fileTypes);
/*  92 */       fc.setFileName(initialname);
/*  93 */       fc.setOverwrite(false);
/*  94 */       fc.setText(title);
/*  95 */       String path = fc.open();
/*  96 */       if (path == null)
/*  97 */         return null;
/*  98 */       return new File(path);
/*     */     }
/*     */     catch (Throwable t) {
/* 101 */       java.awt.FileDialog fc = new java.awt.FileDialog(new Frame());
/* 102 */       fc.setFilenameFilter(new FilenameFilter()
/*     */       {
/*     */         public boolean accept(File f, String arg1) {
/* 105 */           if (f.getName().substring(f.getName().lastIndexOf('.') + 1).equals("txt")) {
/* 106 */             return true;
/*     */           }
/* 108 */           return false;
/*     */         }
/*     */       });
/* 111 */       fc.setFile(initialname + ".txt");
/* 112 */       fc.setMode(1);
/* 113 */       fc.setVisible(true);
/* 114 */       String s = fc.getFile();
/* 115 */       if (s == null)
/* 116 */         return null;
/* 117 */       File f = new File(fc.getDirectory() + s);
/* 118 */       fc.dispose();
/* 119 */       return f;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void print(final BufferedImage img, Entrance e)
/*     */   {
/* 126 */     PrinterJob pj = e.getPrinterJob();
/* 127 */     pj.setPrintable(new Printable()
/*     */     {
/*     */       public int print(Graphics graphics, PageFormat pf, int pageIndex) throws PrinterException
/*     */       {
/* 131 */         if (pageIndex > 0) {
/* 132 */           return 1;
/*     */         }
/* 134 */         Graphics2D g2d = (Graphics2D)graphics;
/* 135 */         g2d.translate(pf.getImageableX(), pf.getImageableY());
/* 136 */         g2d.drawImage(img, 0, 0, null);
/* 137 */         return 0;
/*     */       }
/*     */     });
/* 140 */     if (pj.printDialog())
/*     */       try {
/* 142 */         pj.print();
/*     */       } catch (PrinterException e1) {
/* 144 */         e1.printStackTrace();
/*     */       }
/*     */   }
/*     */ 
/*     */   public static void pageSetup(Entrance e)
/*     */   {
/* 152 */     e.getPrinterJob().pageDialog(e.getPrinterJob().defaultPage());
/*     */   }
/*     */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.SWTFile
 * JD-Core Version:    0.6.2
 */