 package org.gss.adi;
 
 import java.awt.Color;
 import java.awt.Desktop;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.awt.event.MouseAdapter;
 import java.awt.event.MouseEvent;
 import java.awt.image.BufferedImage;
 import java.io.BufferedWriter;
 import java.io.File;
 import java.io.FileWriter;
 import java.io.IOException;
 import java.io.PrintStream;
 import java.util.ArrayList;
 import java.util.Scanner;
 import javax.imageio.ImageIO;
 import javax.swing.ButtonGroup;
 import javax.swing.JComboBox;
 import javax.swing.JFileChooser;
 import javax.swing.JMenu;
 import javax.swing.JMenuBar;
 import javax.swing.JMenuItem;
 import javax.swing.JOptionPane;
 import javax.swing.JPanel;
 import javax.swing.JRadioButtonMenuItem;
 import javax.swing.JSeparator;
 import javax.swing.KeyStroke;
 import javax.swing.filechooser.FileFilter;
 import org.eclipse.swt.widgets.FileDialog;
 import org.eclipse.swt.widgets.Shell;
 import org.gss.adi.dialogs.AnnotateImageDialog;
 import org.gss.adi.dialogs.CalibratePixelDialog;
 import org.gss.adi.dialogs.ColorHistogramDialog;
 import org.gss.adi.dialogs.ColorQualityDialog;
 import org.gss.adi.dialogs.CombineImagesDialog;
 import org.gss.adi.dialogs.OpenImageFromURL;
 import org.gss.adi.dialogs.SaveNewMaskDialog;
 import org.gss.adi.dialogs.SelectTimeSeriesDialog;
 import org.gss.adi.dialogs.ShowOriginalDialog;
 import org.gss.adi.dialogs.TrimDialog;
 import org.gss.adi.tools.ColorMask;
 import org.gss.adi.tools.ConfigFileManager;
 import org.gss.adi.tools.MeasurementSaver;
 
 public class ADIMenu extends JMenuBar
 {
   private static final long serialVersionUID = 4271322241772486643L;
   public static final byte BLACK = 0;
   public static final byte BLUE = 1;
   public static final byte CYAN = 2;
   public static final byte GRAY = 3;
   public static final byte GREEN = 4;
   public static final byte MAGENTA = 5;
   public static final byte ORANGE = 6;
   public static final byte RED = 7;
   public static final byte WHITE = 8;
   public static final byte YELLOW = 9;
   private JRadioButtonMenuItem black;
   private JRadioButtonMenuItem blue;
   private JRadioButtonMenuItem cyan;
   private JRadioButtonMenuItem gray;
   private JRadioButtonMenuItem green;
   private JRadioButtonMenuItem magenta;
   private JRadioButtonMenuItem orange;
   private JRadioButtonMenuItem red;
   private JRadioButtonMenuItem white;
   private JRadioButtonMenuItem yellow;
   JRadioButtonMenuItem styleCirc;
   JRadioButtonMenuItem styleFilledCirc;
   JRadioButtonMenuItem styleRect;
   JRadioButtonMenuItem styleFilledRect;
   private Entrance entrance;
   private JMenu mnOpenRecent;
   JRadioButtonMenuItem width3;
   JRadioButtonMenuItem width2;
   JRadioButtonMenuItem width1;
   private JMenu mnMeasurements;
   private JMenuItem saveMeas;
   private JMenuItem showMeas;
   private JMenu saveMask;
   protected JMenu applyMask;
   JMenuItem saveNewMask;
   private JRadioButtonMenuItem styleNone;
 
   ADIMenu(Entrance e)
   {
     this.entrance = e;
     JMenuBar menuBar = this;
     JMenu mnFile = new JMenu("File");
     mnFile.addMouseListener(new MouseAdapter()
     {
       public void mouseEntered(MouseEvent arg0) {
         String[] s = ConfigFileManager.getRecentImages();
         ADIMenu.this.mnOpenRecent.removeAll();
         for (int i = 0; (i < s.length) && (s[i] != null) && (!s[i].equals("")) && (i < 10); i++) {
           final String si = s[i];
           JMenuItem item = new JMenuItem(si.substring(si.lastIndexOf("\\") + 1));
           ADIMenu.this.mnOpenRecent.add(item);
           item.addActionListener(new ActionListener()
           {
             public void actionPerformed(ActionEvent arg0) {
               try {
                 ADIMenu.this.entrance.setImage(ImageIO.read(new File(si)));
                 ADIMenu.this.entrance.updatePic(); } catch (IOException e) {
                 e.printStackTrace();
               }
             }
           });
         }
       }
     });
     mnFile.addActionListener(new ActionListener()
     {
       public void actionPerformed(ActionEvent arg0)
       {
       }
     });
     mnFile.setMnemonic('F');
     menuBar.add(mnFile);
 
     JMenuItem mntmOpenPicture = new JMenuItem("Open Picture");
     mntmOpenPicture.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         ADIMenu.this.openPicture();
       }
     });
     mntmOpenPicture.setAccelerator(KeyStroke.getKeyStroke(79, 2));
     mnFile.add(mntmOpenPicture);
 
     this.mnOpenRecent = new JMenu("Open Recent");
 
     JMenuItem mntmOpenPictureFrom = new JMenuItem("Open Picture from URL");
     mntmOpenPictureFrom.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         new OpenImageFromURL(ADIMenu.this.entrance, (byte)0).setVisible(true);
         ADIMenu.this.entrance.updatePic();
       }
     });
     mnFile.add(mntmOpenPictureFrom);
     mnFile.add(this.mnOpenRecent);
 
     JMenuItem mntmOpenTimeSeries = new JMenuItem("Open Time Series Images");
     mntmOpenTimeSeries.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent a) {
         new SelectTimeSeriesDialog(ADIMenu.this.entrance).setVisible(true);
       }
     });
     mntmOpenTimeSeries.setAccelerator(KeyStroke.getKeyStroke(61, 2));
     mnFile.add(mntmOpenTimeSeries);
 
     JSeparator separator = new JSeparator();
     mnFile.add(separator);
 
     JMenuItem mntmCalibratePixelSize = new JMenuItem("Calibrate Pixel Size");
     mntmCalibratePixelSize.addActionListener(new ActionListener()
     {
       public void actionPerformed(ActionEvent e)
       {
         CalibratePixelDialog dialog;
         if ((ADIMenu.this.entrance.getMainFrame().getPane() instanceof TimeSeriesPanel))
           dialog = new CalibratePixelDialog(ADIMenu.this.entrance, Boolean.valueOf(true));
         else
           dialog = new CalibratePixelDialog(ADIMenu.this.entrance, Boolean.valueOf(false));
         dialog.setVisible(true);
         if ((ADIMenu.this.entrance.getPane() instanceof SpatialAnalysisPanel))
           ((SpatialAnalysisPanel)ADIMenu.this.entrance.getPane()).updatePixSize();
         else if ((ADIMenu.this.entrance.getPane() instanceof TimeSeriesPanel))
           ((TimeSeriesPanel)ADIMenu.this.entrance.getPane()).updatePixSize();
       }
     });
     mntmCalibratePixelSize.setAccelerator(KeyStroke.getKeyStroke(67, 2));
     mnFile.add(mntmCalibratePixelSize);
 
     JSeparator separator_1 = new JSeparator();
     mnFile.add(separator_1);
 
     JMenuItem mntmGraphColors = new JMenuItem("Graph Colors");
     mntmGraphColors.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         String tool = "";
         Integer[] x = null;
         Integer[] y = null;
         byte t = 0;
         JPanel panel = ADIMenu.this.entrance.getMainFrame().getPane();
         if ((panel instanceof SpatialAnalysisPanel)) {
           tool = (String)((SpatialAnalysisPanel)panel).comboBox.getSelectedItem();
           x = ((SpatialAnalysisPanel)panel).x;
           y = ((SpatialAnalysisPanel)panel).y;
         } else if ((panel instanceof EnhanceColorsPanel)) {
           ((EnhanceColorsPanel)panel).closingSequence();
           t = 1;
         } else if ((panel instanceof MaskColorsPanel)) {
           ((MaskColorsPanel)panel).closingSequence();
           t = 2;
         }
         new ColorHistogramDialog(ADIMenu.this.entrance, tool, x, y, t).setVisible(true);
       }
     });
     mntmGraphColors.setAccelerator(KeyStroke.getKeyStroke(71, 2));
     mnFile.add(mntmGraphColors);
 
     JMenuItem mntmExportColorsTo = new JMenuItem("Export Colors to Text File");
     mntmExportColorsTo.setAccelerator(KeyStroke.getKeyStroke(69, 2));
     mnFile.add(mntmExportColorsTo);
 
     JSeparator separator_2 = new JSeparator();
     mnFile.add(separator_2);
 
     JMenuItem mntmSavePicture = new JMenuItem("Save Picture");
     mntmSavePicture.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         SWTFile.saveImage(ADIMenu.this.entrance);
       }
     });
     mntmSavePicture.setAccelerator(KeyStroke.getKeyStroke(83, 2));
     mnFile.add(mntmSavePicture);
 
     JMenuItem mntmShowhideOriginal = new JMenuItem("Show/Hide Original");
     mntmShowhideOriginal.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         if (ADIMenu.this.entrance.getImage() != null)
           new ShowOriginalDialog(ADIMenu.this.entrance).setVisible(true);
         else
           JOptionPane.showMessageDialog(null, "There is no image to show.", null, -1);
       }
     });
     mntmShowhideOriginal.setAccelerator(KeyStroke.getKeyStroke(72, 2));
     mnFile.add(mntmShowhideOriginal);
 
     JSeparator separator_3 = new JSeparator();
     mnFile.add(separator_3);
 
     JMenuItem mntmPageSetup = new JMenuItem("Page Setup");
     mntmPageSetup.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         SWTFile.pageSetup(ADIMenu.this.entrance);
       }
     });
     mnFile.add(mntmPageSetup);
 
     JMenu mnPrintPicture = new JMenu("Print...");
     mnFile.add(mnPrintPicture);
 
     JMenuItem mntmPrintPicture = new JMenuItem("Print Original Image");
     mnPrintPicture.add(mntmPrintPicture);
     mntmPrintPicture.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         ADIMenu.this.print(ADIMenu.this.entrance.getImage());
       }
     });
     mntmPrintPicture.setAccelerator(KeyStroke.getKeyStroke(80, 2));
 
     JMenuItem mntmPrintEnhanced = new JMenuItem("Print Enhanced Image");
     mntmPrintEnhanced.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent arg0) {
         ADIMenu.this.print(ADIMenu.this.entrance.getEnhancedImage());
       }
     });
     mntmPrintEnhanced.setAccelerator(KeyStroke.getKeyStroke(80, 10));
     mnPrintPicture.add(mntmPrintEnhanced);
 
     JMenuItem mntmPrintMaskedImage = new JMenuItem("Print Masked Image");
     mntmPrintMaskedImage.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         ADIMenu.this.print(ADIMenu.this.entrance.getMaskedImage());
       }
     });
     mntmPrintMaskedImage.setAccelerator(KeyStroke.getKeyStroke(80, 3));
     mnPrintPicture.add(mntmPrintMaskedImage);
 
     JSeparator separator_4 = new JSeparator();
     mnFile.add(separator_4);
 
     JMenuItem mntmQuit = new JMenuItem("Quit");
     mntmQuit.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         System.exit(0);
       }
     });
     mntmQuit.setAccelerator(KeyStroke.getKeyStroke(81, 2));
     mnFile.add(mntmQuit);
 
     JMenu mnNavigation = new JMenu("Navigation");
     menuBar.add(mnNavigation);
 
     JMenuItem mntmGoToOverview = new JMenuItem("Go to Overview");
     mntmGoToOverview.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         new OverviewPanel(ADIMenu.this.entrance);
       }
     });
     mnNavigation.add(mntmGoToOverview);
 
     JMenuItem mntmGoToIntroduction = new JMenuItem("Go to Introduction");
     mntmGoToIntroduction.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         ADIMenu.this.entrance.setPane(new StartPanel(ADIMenu.this.entrance));
       }
     });
     mnNavigation.add(mntmGoToIntroduction);
 
     JMenuItem mntmGoToSpatial = new JMenuItem("Go to Spatial Analysis");
     mntmGoToSpatial.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent arg0) {
         new SpatialAnalysisPanel(ADIMenu.this.entrance);
       }
     });
     mnNavigation.add(mntmGoToSpatial);
 
     JMenuItem mntmGoToEnhance = new JMenuItem("Go to Enhance Colors");
     mntmGoToEnhance.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent arg0) {
         new EnhanceColorsPanel(ADIMenu.this.entrance);
       }
     });
     mnNavigation.add(mntmGoToEnhance);
 
     JMenuItem mntmGoToMask = new JMenuItem("Go to Mask Colors");
     mntmGoToMask.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         new MaskColorsPanel(ADIMenu.this.entrance);
       }
     });
     mnNavigation.add(mntmGoToMask);
 
     JMenuItem mntmGoToTime = new JMenuItem("Go to Time Series");
     mntmGoToTime.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent arg0) {
         ADIMenu.this.entrance.launchTimeSeries();
       }
     });
     mnNavigation.add(mntmGoToTime);
 
     JMenuItem mntmGoToOld = new JMenuItem("Go to Old Growth Forest Time Series");
     mntmGoToOld.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent arg0) {
         new OldGrowthForestPanel(ADIMenu.this.entrance, false);
       }
     });
     mntmGoToOld.setAccelerator(KeyStroke.getKeyStroke(70, 2));
     mnNavigation.add(mntmGoToOld);
 
     this.mnMeasurements = new JMenu("Measurements");
     menuBar.add(this.mnMeasurements);
 
     this.saveMeas = new JMenuItem("Save Measurement");
     this.saveMeas.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent arg0) {
         try {
           SpatialAnalysisPanel p = (SpatialAnalysisPanel)ADIMenu.this.entrance.getMainFrame().getPane();
           MeasurementSaver.saveMeasurement(ADIMenu.this.entrance, p.x, p.y, p.getTool(), ADIMenu.this.entrance.getImage(), p.getColorScheme());
         }
         catch (Exception localException)
         {
         }
       }
     });
     this.saveMeas.setVisible(false);
     this.saveMeas.setAccelerator(KeyStroke.getKeyStroke(77, 2));
     this.mnMeasurements.add(this.saveMeas);
 
     this.showMeas = new JMenuItem("Show Last Measurement");
     this.showMeas.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         MeasurementSaver.readMeasurement(ADIMenu.this.entrance.getMeasurementFile());
       }
     });
     this.showMeas.setVisible(false);
     this.showMeas.setAccelerator(KeyStroke.getKeyStroke(76, 2));
     this.mnMeasurements.add(this.showMeas);
 
     JSeparator separator_5 = new JSeparator();
     this.mnMeasurements.add(separator_5);
 
     JMenuItem mntmNewMeasurementFile = new JMenuItem("New Measurement File");
     mntmNewMeasurementFile.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         MeasurementSaver.createNewMeasurementFile(ADIMenu.this.entrance);
       }
     });
     mntmNewMeasurementFile.setAccelerator(KeyStroke.getKeyStroke(78, 2));
     this.mnMeasurements.add(mntmNewMeasurementFile);
 
     JMenu mnUtilities = new JMenu("Utilities");
     menuBar.add(mnUtilities);
 
     JMenuItem mntmTrimImage = new JMenuItem("Trim Image");
     mntmTrimImage.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent arg0) {
         if (ADIMenu.this.entrance.getImage() == null)
           JOptionPane.showMessageDialog(null, "No image has been selected. Cannot trim image.", null, -1);
         else
           new TrimDialog(ADIMenu.this.entrance).setVisible(true);
       }
     });
     mntmTrimImage.setAccelerator(KeyStroke.getKeyStroke(84, 2));
     mnUtilities.add(mntmTrimImage);
 
     JMenuItem mntmCombineImages = new JMenuItem("Combine Images");
     mntmCombineImages.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         new CombineImagesDialog(ADIMenu.this.entrance).setVisible(true);
       }
     });
     mntmCombineImages.setAccelerator(KeyStroke.getKeyStroke(67, 3));
     mnUtilities.add(mntmCombineImages);
 
     JMenuItem mntmCheckColorQuality = new JMenuItem("Check Color Quality");
     mntmCheckColorQuality.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         new ColorQualityDialog().setVisible(true);
       }
     });
     mnUtilities.add(mntmCheckColorQuality);
 
     JMenuItem mntmAnnotateImage = new JMenuItem("Annotate Image");
     mntmAnnotateImage.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         if (ADIMenu.this.entrance.getImage() != null)
           new AnnotateImageDialog(ADIMenu.this.entrance).setVisible(true);
       }
     });
     mnUtilities.add(mntmAnnotateImage);
 
     JMenu mnPreferences = new JMenu("Preferences");
     add(mnPreferences);
 
     JMenu mnLineWidth = new JMenu("Line Width");
     mnPreferences.add(mnLineWidth);
 
     ButtonGroup widths = new ButtonGroup();
 
     this.width1 = new JRadioButtonMenuItem("1");
     this.width1.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         ADIMenu.this.entrance.setLineWidth(1.0F);
         ADIMenu.this.entrance.updateTool();
       }
     });
     this.width1.setSelected(true);
     mnLineWidth.add(this.width1);
     widths.add(this.width1);
 
     this.width2 = new JRadioButtonMenuItem("2");
     this.width2.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         ADIMenu.this.entrance.setLineWidth(2.0F);
         ADIMenu.this.entrance.updateTool();
       }
     });
     mnLineWidth.add(this.width2);
     widths.add(this.width2);
 
     this.width3 = new JRadioButtonMenuItem("3");
     this.width3.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         ADIMenu.this.entrance.setLineWidth(3.0F);
         ADIMenu.this.entrance.updateTool();
       }
     });
     mnLineWidth.add(this.width3);
     widths.add(this.width3);
 
     JMenu mnCursorStyle = new JMenu("Cursor Style");
     mnPreferences.add(mnCursorStyle);
 
     this.styleCirc = new JRadioButtonMenuItem("Circle");
     this.styleCirc.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         ADIMenu.this.entrance.setCursorStyle((byte)0);
         ADIMenu.this.entrance.updateTool();
       }
     });
     this.styleCirc.setSelected(true);
     mnCursorStyle.add(this.styleCirc);
 
     this.styleFilledCirc = new JRadioButtonMenuItem("Filled Circle");
     this.styleFilledCirc.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         ADIMenu.this.entrance.setCursorStyle((byte)1);
         ADIMenu.this.entrance.updateTool();
       }
     });
     mnCursorStyle.add(this.styleFilledCirc);
 
     this.styleRect = new JRadioButtonMenuItem("Rectangle");
     this.styleRect.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         ADIMenu.this.entrance.setCursorStyle((byte)2);
         ADIMenu.this.entrance.updateTool();
       }
     });
     mnCursorStyle.add(this.styleRect);
 
     this.styleFilledRect = new JRadioButtonMenuItem("Filled Rectangle");
     this.styleFilledRect.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         ADIMenu.this.entrance.setCursorStyle((byte)3);
         ADIMenu.this.entrance.updateTool();
       }
     });
     mnCursorStyle.add(this.styleFilledRect);
 
     ButtonGroup styles = new ButtonGroup();
     styles.add(this.styleCirc);
     styles.add(this.styleFilledCirc);
     styles.add(this.styleRect);
     styles.add(this.styleFilledRect);
 
     this.styleNone = new JRadioButtonMenuItem("None");
     this.styleNone.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent arg0) {
         ADIMenu.this.entrance.setCursorStyle((byte)4);
         ADIMenu.this.entrance.updateTool();
       }
     });
     mnCursorStyle.add(this.styleNone);
     styles.add(this.styleNone);
 
     JMenu mnChangeColorOf = new JMenu("Change Color of Spatial Tool");
     mnPreferences.add(mnChangeColorOf);
 
     this.black = new JRadioButtonMenuItem("Black");
     this.black.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent arg0) {
         ADIMenu.this.entrance.setColor(Color.BLACK);
         ADIMenu.this.entrance.updateTool();
       }
     });
     mnChangeColorOf.add(this.black);
 
     this.blue = new JRadioButtonMenuItem("Blue");
     this.blue.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         ADIMenu.this.entrance.setColor(Color.BLUE);
         ADIMenu.this.entrance.updateTool();
       }
     });
     mnChangeColorOf.add(this.blue);
 
     this.cyan = new JRadioButtonMenuItem("Cyan");
     this.cyan.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         ADIMenu.this.entrance.setColor(Color.CYAN);
         ADIMenu.this.entrance.updateTool();
       }
     });
     mnChangeColorOf.add(this.cyan);
 
     this.gray = new JRadioButtonMenuItem("Gray");
     this.gray.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         ADIMenu.this.entrance.setColor(Color.GRAY);
         ADIMenu.this.entrance.updateTool();
       }
     });
     mnChangeColorOf.add(this.gray);
 
     this.green = new JRadioButtonMenuItem("Green");
     this.green.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         ADIMenu.this.entrance.setColor(Color.GREEN);
         ADIMenu.this.entrance.updateTool();
       }
     });
     mnChangeColorOf.add(this.green);
 
     JSeparator separator_15 = new JSeparator();
     mnChangeColorOf.add(separator_15);
 
     this.magenta = new JRadioButtonMenuItem("Magenta");
     this.magenta.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         ADIMenu.this.entrance.setColor(Color.MAGENTA);
         ADIMenu.this.entrance.updateTool();
       }
     });
     mnChangeColorOf.add(this.magenta);
 
     this.orange = new JRadioButtonMenuItem("Orange");
     this.orange.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         ADIMenu.this.entrance.setColor(Color.ORANGE);
         ADIMenu.this.entrance.updateTool();
       }
     });
     mnChangeColorOf.add(this.orange);
 
     this.red = new JRadioButtonMenuItem("Red");
     this.red.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         ADIMenu.this.entrance.setColor(Color.RED);
         ADIMenu.this.entrance.updateTool();
       }
     });
     mnChangeColorOf.add(this.red);
 
     this.white = new JRadioButtonMenuItem("White");
     this.white.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         ADIMenu.this.entrance.setColor(Color.WHITE);
         ADIMenu.this.entrance.updateTool();
       }
     });
     mnChangeColorOf.add(this.white);
 
     this.yellow = new JRadioButtonMenuItem("Yellow");
     this.yellow.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         ADIMenu.this.entrance.setColor(Color.YELLOW);
         ADIMenu.this.entrance.updateTool();
       }
     });
     this.yellow.setSelected(true);
     mnChangeColorOf.add(this.yellow);
 
     ButtonGroup colors = new ButtonGroup();
     colors.add(this.black);
     colors.add(this.blue);
     colors.add(this.cyan);
     colors.add(this.gray);
     colors.add(this.green);
     colors.add(this.magenta);
     colors.add(this.orange);
     colors.add(this.red);
     colors.add(this.white);
     colors.add(this.yellow);
 
     this.saveMask = new JMenu("Save Color Masks");
     menuBar.add(this.saveMask);
 
     this.saveNewMask = new JMenuItem("Save New Color Mask");
     this.saveNewMask.setVisible(false);
     this.saveNewMask.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         new SaveNewMaskDialog(ADIMenu.this.entrance).setVisible(true);
       }
     });
     this.saveMask.add(this.saveNewMask);
 
     JSeparator separator_7 = new JSeparator();
     this.saveMask.add(separator_7);
 
     JMenuItem mntmSaveColorMasks = new JMenuItem("Save Color Masks to File");
     mntmSaveColorMasks.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         String s = "";
         ArrayList al = ADIMenu.this.entrance.getColorMasks();
         for (int i = 0; i < al.size(); i++) {
           s = s + ((ColorMask)al.get(i)).toString();
         }
         File f = SWTFile.saveFile("Save Mask", "ColorMask");
         try {
           if (!f.exists())
             f.createNewFile();
           FileWriter outstream = new FileWriter(f);
           BufferedWriter out = new BufferedWriter(outstream);
           out.write(s);
           out.close();
         }
         catch (Exception localException)
         {
         }
       }
     });
     this.saveMask.add(mntmSaveColorMasks);
 
     JMenuItem mntmRecallColorMasks = new JMenuItem("Recall Color Masks from File");
     mntmRecallColorMasks.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         File f;
         try {
           FileDialog fc = new FileDialog(new Shell(), 4096);
           String[] fileTypes = { "Text Files *.txt" };
           String[] fileExtensions = { "*.txt" };
           fc.setFilterExtensions(fileExtensions);
           fc.setFilterNames(fileTypes);
           fc.setText("Open Color Masks");
           String path = fc.open();
           if (path == null)
             return;
           f = new File(path);
         }
         catch (Throwable t)
         {
           JFileChooser fc = new JFileChooser();
           fc.setFileFilter(new FileFilter()
           {
             public boolean accept(File f) {
               if (f.getName().substring(f.getName().lastIndexOf('.') + 1).equals("txt")) {
                 return true;
               }
               return false;
             }
 
             public String getDescription() {
               return "Text File *.txt";
             }
           });
           int i = fc.showOpenDialog(null);
           if (i == 1)
             return;
           f = fc.getSelectedFile();
         }
         ArrayList masks = new ArrayList();
         try {
           Scanner scan = new Scanner(f);
           String s = "";
           while ((s = scan.nextLine()) != null) {
             try {
               masks.add(new ColorMask(s));
             } catch (Exception x) {
               x.printStackTrace();
               JOptionPane.showMessageDialog(null, "File is not a valid Color Mask File.", null, -1);
               return;
             }
           }
           scan.close(); } catch (Exception localException1) {
         }
         for (int i = 0; i < masks.size(); i++)
           ADIMenu.this.addMask((ColorMask)masks.get(i), ((ColorMask)masks.get(i)).title);
       }
     });
     mntmRecallColorMasks.setAccelerator(KeyStroke.getKeyStroke(82, 2));
     this.saveMask.add(mntmRecallColorMasks);
 
     this.applyMask = new JMenu("Apply Color Masks");
     this.applyMask.setVisible(false);
     add(this.applyMask);
 
     JMenu mnHelp = new JMenu("Help");
     menuBar.add(mnHelp);
 
     JMenuItem mntmAboutTheSoftware = new JMenuItem("About the Software");
     mntmAboutTheSoftware.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         ADIMenu.this.entrance.setPane(new AboutPanel());
       }
     });
     mnHelp.add(mntmAboutTheSoftware);
 
     JSeparator separator_14 = new JSeparator();
     mnHelp.add(separator_14);
 
     JMenuItem mntmOpenManualAs = new JMenuItem("Open Manual as PDF");
     mntmOpenManualAs.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent arg0) {
         try {
           File pdfFile = new File("resources/manual.pdf");
           if (pdfFile.exists()) {
             if (Desktop.isDesktopSupported())
               Desktop.getDesktop().open(pdfFile);
             else {
               System.out.println("Awt Desktop is not supported!");
             }
           }
           else {
             System.out.println("File does not exist!");
           }
 
           System.out.println("Done");
         }
         catch (Exception ex) {
           ex.printStackTrace();
         }
       }
     });
     mnHelp.add(mntmOpenManualAs);
 
     JMenuItem mntmOpenHelpManual = new JMenuItem("Open Help Manual in Browser");
     mntmOpenHelpManual.setVisible(false);
     mntmOpenHelpManual.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         try {
           File htmFile = new File("resources/manual.htm");
           if (htmFile.exists()) {
             if (Desktop.isDesktopSupported())
               Desktop.getDesktop().open(htmFile);
             else
               System.out.println("Awt Desktop is not supported!");
           }
           else {
             System.out.println("File does not exist!");
           }
           System.out.println("Done");
         } catch (Exception ex) {
           ex.printStackTrace();
         }
       }
     });
     mnHelp.add(mntmOpenHelpManual);
     setCursorStyle();
     setColor();
     setLineWidth();
   }
 
   protected void setColor(byte color)
   {
     switch (color) {
     case 0:
       this.black.setSelected(true);
       break;
     case 1:
       this.blue.setSelected(true);
       break;
     case 2:
       this.cyan.setSelected(true);
       break;
     case 3:
       this.gray.setSelected(true);
       break;
     case 4:
       this.green.setSelected(true);
       break;
     case 5:
       this.magenta.setSelected(true);
       break;
     case 6:
       this.orange.setSelected(true);
       break;
     case 7:
       this.red.setSelected(true);
       break;
     case 8:
       this.white.setSelected(true);
       break;
     case 9:
       this.yellow.setSelected(true);
     }
   }
 
   private void print(BufferedImage img)
   {
     SWTFile.print(img, this.entrance);
   }
 
   private void openPicture()
   {
     File f = this.entrance.openImage("Open an Image");
     if (f == null)
       return;
     try {
       this.entrance.setImage(ImageIO.read(f));
       this.entrance.updatePic();
     } catch (IOException e1) {
       e1.printStackTrace();
     }
   }
 
   public void setColor()
   {
     Entrance e = this.entrance;
     if (e.getColor().equals(Color.BLACK))
       this.black.setSelected(true);
     else if (e.getColor().equals(Color.BLUE))
       this.blue.setSelected(true);
     else if (e.getColor().equals(Color.CYAN))
       this.cyan.setSelected(true);
     else if (e.getColor().equals(Color.GRAY))
       this.gray.setSelected(true);
     else if (e.getColor().equals(Color.GREEN))
       this.green.setSelected(true);
     else if (e.getColor().equals(Color.MAGENTA))
       this.magenta.setSelected(true);
     else if (e.getColor().equals(Color.ORANGE))
       this.orange.setSelected(true);
     else if (e.getColor().equals(Color.RED))
       this.red.setSelected(true);
     else if (e.getColor().equals(Color.WHITE))
       this.white.setSelected(true);
     else if (e.getColor().equals(Color.YELLOW))
       this.yellow.setSelected(true);
   }
 
   public void setLineWidth()
   {
     float f = this.entrance.getLineWidth();
     if (f == 1.0F)
       this.width1.setSelected(true);
     else if (f == 2.0F)
       this.width2.setSelected(true);
     else if (f == 3.0F)
       this.width3.setSelected(true);
   }
 
   public void setCursorStyle()
   {
     byte b = this.entrance.getCursorStyle();
     if (b == 0)
       this.styleCirc.setSelected(true);
     else if (b == 1)
       this.styleFilledCirc.setSelected(true);
     else if (b == 2)
       this.styleRect.setSelected(true);
     else if (b == 3)
       this.styleFilledRect.setSelected(true);
     else if (b == 4)
       this.styleNone.setSelected(true);
   }
 
   public void showMeasurement(boolean show) {
     this.showMeas.setVisible(show);
   }
 
   public void showSaveMeas(boolean show)
   {
     this.saveMeas.setVisible(show);
   }
 
   public void addMask(final ColorMask mask, String title)
   {
     mask.setTitle(title);
     this.entrance.addColorMask(mask);
     final JMenuItem msk = new JMenuItem("Apply " + title);
     msk.addActionListener(new ActionListener()
     {
       public void actionPerformed(ActionEvent e) {
         ((MaskColorsPanel)ADIMenu.this.entrance.getPane()).applyMask(mask);
       }
     });
     this.applyMask.add(msk);
     final JMenuItem mi = new JMenuItem("Remove " + title);
     mi.addActionListener(new ActionListener()
     {
       public void actionPerformed(ActionEvent arg0) {
         ADIMenu.this.applyMask.remove(msk);
         ADIMenu.this.saveMask.remove(mi);
         ADIMenu.this.entrance.removeColorMask(mask);
       }
     });
     this.saveMask.add(mi);
     if ((this.entrance.getPane() instanceof MaskColorsPanel))
       this.applyMask.setVisible(true);
   }
 }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.ADIMenu
 * JD-Core Version:    0.6.2
 */