 package org.gss.adi;
 
 import java.io.File;
 import java.io.FilenameFilter;
 import java.io.PrintStream;
 import javax.swing.filechooser.FileFilter;
 
 public class ImageFilter extends FileFilter
   implements FilenameFilter
 {
   public boolean accept(File f)
   {
     if (f.isDirectory())
       return true;
     String ext = getExtension(f);
     System.out.println(ext);
     if ((ext.equals("jpg")) || (ext.equals("jpeg")) || (ext.equals("gif")) || (ext.equals("tif")) || (ext.equals("pic")) || 
       (ext.equals("png")) || (ext.equals("pdf")) || (ext.equals("bmp")) || (ext.equals("dib")) || (ext.equals("fit"))) {
       System.out.println("accepted");
       return true;
     }
     System.out.println("rejected");
     return false;
   }
   private String getExtension(File f) {
     String name = f.getName();
     int i = name.lastIndexOf('.');
     if (i > -1) {
       return name.substring(i);
     }
     return "";
   }
 
   public String getDescription() {
     return "Images";
   }
 
   public boolean accept(File dir, String name) {
     if (dir.isDirectory())
       return true;
     String ext = getExtension(new File(name));
     System.out.println("ext");
     if ((ext.equals("jpg")) || (ext.equals("jpeg")) || (ext.equals("gif")) || (ext.equals("tif")) || (ext.equals("pic")) || 
       (ext.equals("png")) || (ext.equals("pdf")) || (ext.equals("bmp")) || (ext.equals("dib")) || (ext.equals("fit"))) {
       System.out.println("accepted");
       return true;
     }
     System.out.println("rejected");
     return false;
   }
 }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.ImageFilter
 * JD-Core Version:    0.6.2
 */