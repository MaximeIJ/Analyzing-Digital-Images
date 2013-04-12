/*    */ package org.gss.adi;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FilenameFilter;
/*    */ import java.io.PrintStream;
/*    */ import javax.swing.filechooser.FileFilter;
/*    */ 
/*    */ public class ImageFilter extends FileFilter
/*    */   implements FilenameFilter
/*    */ {
/*    */   public boolean accept(File f)
/*    */   {
/* 14 */     if (f.isDirectory())
/* 15 */       return true;
/* 16 */     String ext = getExtension(f);
/* 17 */     System.out.println(ext);
/* 18 */     if ((ext.equals("jpg")) || (ext.equals("jpeg")) || (ext.equals("gif")) || (ext.equals("tif")) || (ext.equals("pic")) || 
/* 19 */       (ext.equals("png")) || (ext.equals("pdf")) || (ext.equals("bmp")) || (ext.equals("dib")) || (ext.equals("fit"))) {
/* 20 */       System.out.println("accepted");
/* 21 */       return true;
/*    */     }
/* 23 */     System.out.println("rejected");
/* 24 */     return false;
/*    */   }
/*    */   private String getExtension(File f) {
/* 27 */     String name = f.getName();
/* 28 */     int i = name.lastIndexOf('.');
/* 29 */     if (i > -1) {
/* 30 */       return name.substring(i);
/*    */     }
/* 32 */     return "";
/*    */   }
/*    */ 
/*    */   public String getDescription() {
/* 36 */     return "Images";
/*    */   }
/*    */ 
/*    */   public boolean accept(File dir, String name) {
/* 40 */     if (dir.isDirectory())
/* 41 */       return true;
/* 42 */     String ext = getExtension(new File(name));
/* 43 */     System.out.println("ext");
/* 44 */     if ((ext.equals("jpg")) || (ext.equals("jpeg")) || (ext.equals("gif")) || (ext.equals("tif")) || (ext.equals("pic")) || 
/* 45 */       (ext.equals("png")) || (ext.equals("pdf")) || (ext.equals("bmp")) || (ext.equals("dib")) || (ext.equals("fit"))) {
/* 46 */       System.out.println("accepted");
/* 47 */       return true;
/*    */     }
/* 49 */     System.out.println("rejected");
/* 50 */     return false;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.ImageFilter
 * JD-Core Version:    0.6.2
 */