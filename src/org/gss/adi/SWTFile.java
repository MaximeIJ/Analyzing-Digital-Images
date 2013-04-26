 package org.gss.adi;
 
 import java.awt.Frame;
 import java.awt.Graphics;
 import java.awt.Graphics2D;
 import java.awt.image.BufferedImage;
 import java.awt.print.PageFormat;
 import java.awt.print.Printable;
 import java.awt.print.PrinterException;
 import java.awt.print.PrinterJob;
 import java.io.File;
 import java.io.FilenameFilter;
 import javax.swing.JOptionPane;
 import org.eclipse.swt.widgets.Display;
 import org.eclipse.swt.widgets.Shell;
 import org.gss.adi.dialogs.ImageSaveDialog;
 
 public class SWTFile
 {
   public static File openImage(String title, Entrance entrance)
   {
     try
     {
       Display display = new Display();
       Shell sh = new Shell(display);
       org.eclipse.swt.widgets.FileDialog fc = new org.eclipse.swt.widgets.FileDialog(sh);
       String[] fileTypes = { "Images *.jpg; *.png; *.jpeg; *.bmp; *.gif; *.wbmp" };
       String[] fileExtensions = { "*.jpg; *.png; *.jpeg; *.bmp; *.gif; *.wbmp" };
       fc.setFilterExtensions(fileExtensions);
       fc.setFilterNames(fileTypes);
       fc.setOverwrite(true);
       fc.setText(title);
       String path = fc.open();
       if (path == null)
         return null;
       File f = new File(path);
       sh.close();
       while (!sh.isDisposed()) {
         if (!display.readAndDispatch()) display.sleep();
       }
       display.dispose();
       return f;
     } catch (Throwable t) {
       java.awt.FileDialog fc = new java.awt.FileDialog(entrance.getMainFrame());
       String lastDir = entrance.getLastDirectory();
       if (lastDir != null)
         fc.setDirectory(lastDir);
       fc.setFilenameFilter(new ImageFilter());
       fc.setFile("*.jpg; *.jpeg; *.png; *.gif; *.bmp; *.wbmp");
       fc.setVisible(true);
       String s = fc.getFile();
       if (s == null) {
         return null;
       }
       File f = new File(fc.getDirectory() + s);
       fc.dispose();
       entrance.setLastDirectory(f.getParent());
       return f;
     }
   }
 
   public static void saveImage(Entrance entrance)
   {
     if (entrance.getImage() == null)
       JOptionPane.showMessageDialog(null, "There is no image to save.", null, -1);
     else
       new ImageSaveDialog(entrance).setVisible(true);
   }
 
   public static File saveFile(String title, String initialname)
   {
     try
     {
       org.eclipse.swt.widgets.FileDialog fc = new org.eclipse.swt.widgets.FileDialog(new Shell(), 8192);
       String[] fileTypes = { "Text Files *.txt" };
       String[] fileExtensions = { "*.txt" };
       fc.setFilterExtensions(fileExtensions);
       fc.setFilterNames(fileTypes);
       fc.setFileName(initialname);
       fc.setOverwrite(false);
       fc.setText(title);
       String path = fc.open();
       if (path == null)
         return null;
       return new File(path);
     }
     catch (Throwable t) {
       java.awt.FileDialog fc = new java.awt.FileDialog(new Frame());
       fc.setFilenameFilter(new FilenameFilter()
       {
         public boolean accept(File f, String arg1) {
           if (f.getName().substring(f.getName().lastIndexOf('.') + 1).equals("txt")) {
             return true;
           }
           return false;
         }
       });
       fc.setFile(initialname + ".txt");
       fc.setMode(1);
       fc.setVisible(true);
       String s = fc.getFile();
       if (s == null)
         return null;
       File f = new File(fc.getDirectory() + s);
       fc.dispose();
       return f;
     }
   }
 
   public static void print(final BufferedImage img, Entrance e)
   {
     PrinterJob pj = e.getPrinterJob();
     pj.setPrintable(new Printable()
     {
       public int print(Graphics graphics, PageFormat pf, int pageIndex) throws PrinterException
       {
         if (pageIndex > 0) {
           return 1;
         }
         Graphics2D g2d = (Graphics2D)graphics;
         g2d.translate(pf.getImageableX(), pf.getImageableY());
         g2d.drawImage(img, 0, 0, null);
         return 0;
       }
     });
     if (pj.printDialog())
       try {
         pj.print();
       } catch (PrinterException e1) {
         e1.printStackTrace();
       }
   }
 
   public static void pageSetup(Entrance e)
   {
     e.getPrinterJob().pageDialog(e.getPrinterJob().defaultPage());
   }
 }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.SWTFile
 * JD-Core Version:    0.6.2
 */