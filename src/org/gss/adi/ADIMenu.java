/*     */ package org.gss.adi;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Desktop;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Scanner;
/*     */ import javax.imageio.ImageIO;
/*     */ import javax.swing.ButtonGroup;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JFileChooser;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuBar;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JRadioButtonMenuItem;
/*     */ import javax.swing.JSeparator;
/*     */ import javax.swing.KeyStroke;
/*     */ import javax.swing.filechooser.FileFilter;
/*     */ import org.eclipse.swt.widgets.FileDialog;
/*     */ import org.eclipse.swt.widgets.Shell;
/*     */ import org.gss.adi.dialogs.AnnotateImageDialog;
/*     */ import org.gss.adi.dialogs.CalibratePixelDialog;
/*     */ import org.gss.adi.dialogs.ColorHistogramDialog;
/*     */ import org.gss.adi.dialogs.ColorQualityDialog;
/*     */ import org.gss.adi.dialogs.CombineImagesDialog;
/*     */ import org.gss.adi.dialogs.OpenImageFromURL;
/*     */ import org.gss.adi.dialogs.SaveNewMaskDialog;
/*     */ import org.gss.adi.dialogs.SelectTimeSeriesDialog;
/*     */ import org.gss.adi.dialogs.ShowOriginalDialog;
/*     */ import org.gss.adi.dialogs.TrimDialog;
/*     */ import org.gss.adi.tools.ColorMask;
/*     */ import org.gss.adi.tools.ConfigFileManager;
/*     */ import org.gss.adi.tools.MeasurementSaver;
/*     */ 
/*     */ public class ADIMenu extends JMenuBar
/*     */ {
/*     */   private static final long serialVersionUID = 4271322241772486643L;
/*     */   public static final byte BLACK = 0;
/*     */   public static final byte BLUE = 1;
/*     */   public static final byte CYAN = 2;
/*     */   public static final byte GRAY = 3;
/*     */   public static final byte GREEN = 4;
/*     */   public static final byte MAGENTA = 5;
/*     */   public static final byte ORANGE = 6;
/*     */   public static final byte RED = 7;
/*     */   public static final byte WHITE = 8;
/*     */   public static final byte YELLOW = 9;
/*     */   private JRadioButtonMenuItem black;
/*     */   private JRadioButtonMenuItem blue;
/*     */   private JRadioButtonMenuItem cyan;
/*     */   private JRadioButtonMenuItem gray;
/*     */   private JRadioButtonMenuItem green;
/*     */   private JRadioButtonMenuItem magenta;
/*     */   private JRadioButtonMenuItem orange;
/*     */   private JRadioButtonMenuItem red;
/*     */   private JRadioButtonMenuItem white;
/*     */   private JRadioButtonMenuItem yellow;
/*     */   JRadioButtonMenuItem styleCirc;
/*     */   JRadioButtonMenuItem styleFilledCirc;
/*     */   JRadioButtonMenuItem styleRect;
/*     */   JRadioButtonMenuItem styleFilledRect;
/*     */   private Entrance entrance;
/*     */   private JMenu mnOpenRecent;
/*     */   JRadioButtonMenuItem width3;
/*     */   JRadioButtonMenuItem width2;
/*     */   JRadioButtonMenuItem width1;
/*     */   private JMenu mnMeasurements;
/*     */   private JMenuItem saveMeas;
/*     */   private JMenuItem showMeas;
/*     */   private JMenu saveMask;
/*     */   protected JMenu applyMask;
/*     */   JMenuItem saveNewMask;
/*     */   private JRadioButtonMenuItem styleNone;
/*     */ 
/*     */   ADIMenu(Entrance e)
/*     */   {
/* 107 */     this.entrance = e;
/* 108 */     JMenuBar menuBar = this;
/* 109 */     JMenu mnFile = new JMenu("File");
/* 110 */     mnFile.addMouseListener(new MouseAdapter()
/*     */     {
/*     */       public void mouseEntered(MouseEvent arg0) {
/* 113 */         String[] s = ConfigFileManager.getRecentImages();
/* 114 */         ADIMenu.this.mnOpenRecent.removeAll();
/* 115 */         for (int i = 0; (i < s.length) && (s[i] != null) && (!s[i].equals("")) && (i < 10); i++) {
/* 116 */           final String si = s[i];
/* 117 */           JMenuItem item = new JMenuItem(si.substring(si.lastIndexOf("\\") + 1));
/* 118 */           ADIMenu.this.mnOpenRecent.add(item);
/* 119 */           item.addActionListener(new ActionListener()
/*     */           {
/*     */             public void actionPerformed(ActionEvent arg0) {
/*     */               try {
/* 123 */                 ADIMenu.this.entrance.setImage(ImageIO.read(new File(si)));
/* 124 */                 ADIMenu.this.entrance.updatePic(); } catch (IOException e) {
/* 125 */                 e.printStackTrace();
/*     */               }
/*     */             }
/*     */           });
/*     */         }
/*     */       }
/*     */     });
/* 131 */     mnFile.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent arg0)
/*     */       {
/*     */       }
/*     */     });
/* 136 */     mnFile.setMnemonic('F');
/* 137 */     menuBar.add(mnFile);
/*     */ 
/* 139 */     JMenuItem mntmOpenPicture = new JMenuItem("Open Picture");
/* 140 */     mntmOpenPicture.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 142 */         ADIMenu.this.openPicture();
/*     */       }
/*     */     });
/* 145 */     mntmOpenPicture.setAccelerator(KeyStroke.getKeyStroke(79, 2));
/* 146 */     mnFile.add(mntmOpenPicture);
/*     */ 
/* 148 */     this.mnOpenRecent = new JMenu("Open Recent");
/*     */ 
/* 150 */     JMenuItem mntmOpenPictureFrom = new JMenuItem("Open Picture from URL");
/* 151 */     mntmOpenPictureFrom.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 153 */         new OpenImageFromURL(ADIMenu.this.entrance, (byte)0).setVisible(true);
/* 154 */         ADIMenu.this.entrance.updatePic();
/*     */       }
/*     */     });
/* 157 */     mnFile.add(mntmOpenPictureFrom);
/* 158 */     mnFile.add(this.mnOpenRecent);
/*     */ 
/* 160 */     JMenuItem mntmOpenTimeSeries = new JMenuItem("Open Time Series Images");
/* 161 */     mntmOpenTimeSeries.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent a) {
/* 163 */         new SelectTimeSeriesDialog(ADIMenu.this.entrance).setVisible(true);
/*     */       }
/*     */     });
/* 166 */     mntmOpenTimeSeries.setAccelerator(KeyStroke.getKeyStroke(61, 2));
/* 167 */     mnFile.add(mntmOpenTimeSeries);
/*     */ 
/* 169 */     JSeparator separator = new JSeparator();
/* 170 */     mnFile.add(separator);
/*     */ 
/* 172 */     JMenuItem mntmCalibratePixelSize = new JMenuItem("Calibrate Pixel Size");
/* 173 */     mntmCalibratePixelSize.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e)
/*     */       {
/*     */         CalibratePixelDialog dialog;
/* 176 */         if ((ADIMenu.this.entrance.getMainFrame().getPane() instanceof TimeSeriesPanel))
/* 177 */           dialog = new CalibratePixelDialog(ADIMenu.this.entrance, Boolean.valueOf(true));
/*     */         else
/* 179 */           dialog = new CalibratePixelDialog(ADIMenu.this.entrance, Boolean.valueOf(false));
/* 180 */         dialog.setVisible(true);
/* 181 */         if ((ADIMenu.this.entrance.getPane() instanceof SpatialAnalysisPanel))
/* 182 */           ((SpatialAnalysisPanel)ADIMenu.this.entrance.getPane()).updatePixSize();
/* 183 */         else if ((ADIMenu.this.entrance.getPane() instanceof TimeSeriesPanel))
/* 184 */           ((TimeSeriesPanel)ADIMenu.this.entrance.getPane()).updatePixSize();
/*     */       }
/*     */     });
/* 188 */     mntmCalibratePixelSize.setAccelerator(KeyStroke.getKeyStroke(67, 2));
/* 189 */     mnFile.add(mntmCalibratePixelSize);
/*     */ 
/* 191 */     JSeparator separator_1 = new JSeparator();
/* 192 */     mnFile.add(separator_1);
/*     */ 
/* 194 */     JMenuItem mntmGraphColors = new JMenuItem("Graph Colors");
/* 195 */     mntmGraphColors.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 197 */         String tool = "";
/* 198 */         Integer[] x = null;
/* 199 */         Integer[] y = null;
/* 200 */         byte t = 0;
/* 201 */         JPanel panel = ADIMenu.this.entrance.getMainFrame().getPane();
/* 202 */         if ((panel instanceof SpatialAnalysisPanel)) {
/* 203 */           tool = (String)((SpatialAnalysisPanel)panel).comboBox.getSelectedItem();
/* 204 */           x = ((SpatialAnalysisPanel)panel).x;
/* 205 */           y = ((SpatialAnalysisPanel)panel).y;
/* 206 */         } else if ((panel instanceof EnhanceColorsPanel)) {
/* 207 */           ((EnhanceColorsPanel)panel).closingSequence();
/* 208 */           t = 1;
/* 209 */         } else if ((panel instanceof MaskColorsPanel)) {
/* 210 */           ((MaskColorsPanel)panel).closingSequence();
/* 211 */           t = 2;
/*     */         }
/* 213 */         new ColorHistogramDialog(ADIMenu.this.entrance, tool, x, y, t).setVisible(true);
/*     */       }
/*     */     });
/* 216 */     mntmGraphColors.setAccelerator(KeyStroke.getKeyStroke(71, 2));
/* 217 */     mnFile.add(mntmGraphColors);
/*     */ 
/* 219 */     JMenuItem mntmExportColorsTo = new JMenuItem("Export Colors to Text File");
/* 220 */     mntmExportColorsTo.setAccelerator(KeyStroke.getKeyStroke(69, 2));
/* 221 */     mnFile.add(mntmExportColorsTo);
/*     */ 
/* 223 */     JSeparator separator_2 = new JSeparator();
/* 224 */     mnFile.add(separator_2);
/*     */ 
/* 226 */     JMenuItem mntmSavePicture = new JMenuItem("Save Picture");
/* 227 */     mntmSavePicture.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 229 */         SWTFile.saveImage(ADIMenu.this.entrance);
/*     */       }
/*     */     });
/* 232 */     mntmSavePicture.setAccelerator(KeyStroke.getKeyStroke(83, 2));
/* 233 */     mnFile.add(mntmSavePicture);
/*     */ 
/* 235 */     JMenuItem mntmShowhideOriginal = new JMenuItem("Show/Hide Original");
/* 236 */     mntmShowhideOriginal.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 238 */         if (ADIMenu.this.entrance.getImage() != null)
/* 239 */           new ShowOriginalDialog(ADIMenu.this.entrance).setVisible(true);
/*     */         else
/* 241 */           JOptionPane.showMessageDialog(null, "There is no image to show.", null, -1);
/*     */       }
/*     */     });
/* 244 */     mntmShowhideOriginal.setAccelerator(KeyStroke.getKeyStroke(72, 2));
/* 245 */     mnFile.add(mntmShowhideOriginal);
/*     */ 
/* 247 */     JSeparator separator_3 = new JSeparator();
/* 248 */     mnFile.add(separator_3);
/*     */ 
/* 250 */     JMenuItem mntmPageSetup = new JMenuItem("Page Setup");
/* 251 */     mntmPageSetup.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 253 */         SWTFile.pageSetup(ADIMenu.this.entrance);
/*     */       }
/*     */     });
/* 256 */     mnFile.add(mntmPageSetup);
/*     */ 
/* 258 */     JMenu mnPrintPicture = new JMenu("Print...");
/* 259 */     mnFile.add(mnPrintPicture);
/*     */ 
/* 261 */     JMenuItem mntmPrintPicture = new JMenuItem("Print Original Image");
/* 262 */     mnPrintPicture.add(mntmPrintPicture);
/* 263 */     mntmPrintPicture.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 265 */         ADIMenu.this.print(ADIMenu.this.entrance.getImage());
/*     */       }
/*     */     });
/* 268 */     mntmPrintPicture.setAccelerator(KeyStroke.getKeyStroke(80, 2));
/*     */ 
/* 270 */     JMenuItem mntmPrintEnhanced = new JMenuItem("Print Enhanced Image");
/* 271 */     mntmPrintEnhanced.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent arg0) {
/* 273 */         ADIMenu.this.print(ADIMenu.this.entrance.getEnhancedImage());
/*     */       }
/*     */     });
/* 276 */     mntmPrintEnhanced.setAccelerator(KeyStroke.getKeyStroke(80, 10));
/* 277 */     mnPrintPicture.add(mntmPrintEnhanced);
/*     */ 
/* 279 */     JMenuItem mntmPrintMaskedImage = new JMenuItem("Print Masked Image");
/* 280 */     mntmPrintMaskedImage.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 282 */         ADIMenu.this.print(ADIMenu.this.entrance.getMaskedImage());
/*     */       }
/*     */     });
/* 285 */     mntmPrintMaskedImage.setAccelerator(KeyStroke.getKeyStroke(80, 3));
/* 286 */     mnPrintPicture.add(mntmPrintMaskedImage);
/*     */ 
/* 288 */     JSeparator separator_4 = new JSeparator();
/* 289 */     mnFile.add(separator_4);
/*     */ 
/* 291 */     JMenuItem mntmQuit = new JMenuItem("Quit");
/* 292 */     mntmQuit.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 294 */         System.exit(0);
/*     */       }
/*     */     });
/* 297 */     mntmQuit.setAccelerator(KeyStroke.getKeyStroke(81, 2));
/* 298 */     mnFile.add(mntmQuit);
/*     */ 
/* 300 */     JMenu mnNavigation = new JMenu("Navigation");
/* 301 */     menuBar.add(mnNavigation);
/*     */ 
/* 303 */     JMenuItem mntmGoToOverview = new JMenuItem("Go to Overview");
/* 304 */     mntmGoToOverview.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 306 */         new OverviewPanel(ADIMenu.this.entrance);
/*     */       }
/*     */     });
/* 309 */     mnNavigation.add(mntmGoToOverview);
/*     */ 
/* 311 */     JMenuItem mntmGoToIntroduction = new JMenuItem("Go to Introduction");
/* 312 */     mntmGoToIntroduction.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 314 */         ADIMenu.this.entrance.setPane(new StartPanel(ADIMenu.this.entrance));
/*     */       }
/*     */     });
/* 317 */     mnNavigation.add(mntmGoToIntroduction);
/*     */ 
/* 319 */     JMenuItem mntmGoToSpatial = new JMenuItem("Go to Spatial Analysis");
/* 320 */     mntmGoToSpatial.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent arg0) {
/* 322 */         new SpatialAnalysisPanel(ADIMenu.this.entrance);
/*     */       }
/*     */     });
/* 325 */     mnNavigation.add(mntmGoToSpatial);
/*     */ 
/* 327 */     JMenuItem mntmGoToEnhance = new JMenuItem("Go to Enhance Colors");
/* 328 */     mntmGoToEnhance.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent arg0) {
/* 330 */         new EnhanceColorsPanel(ADIMenu.this.entrance);
/*     */       }
/*     */     });
/* 333 */     mnNavigation.add(mntmGoToEnhance);
/*     */ 
/* 335 */     JMenuItem mntmGoToMask = new JMenuItem("Go to Mask Colors");
/* 336 */     mntmGoToMask.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 338 */         new MaskColorsPanel(ADIMenu.this.entrance);
/*     */       }
/*     */     });
/* 341 */     mnNavigation.add(mntmGoToMask);
/*     */ 
/* 343 */     JMenuItem mntmGoToTime = new JMenuItem("Go to Time Series");
/* 344 */     mntmGoToTime.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent arg0) {
/* 346 */         ADIMenu.this.entrance.launchTimeSeries();
/*     */       }
/*     */     });
/* 349 */     mnNavigation.add(mntmGoToTime);
/*     */ 
/* 351 */     JMenuItem mntmGoToOld = new JMenuItem("Go to Old Growth Forest Time Series");
/* 352 */     mntmGoToOld.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent arg0) {
/* 354 */         new OldGrowthForestPanel(ADIMenu.this.entrance, false);
/*     */       }
/*     */     });
/* 357 */     mntmGoToOld.setAccelerator(KeyStroke.getKeyStroke(70, 2));
/* 358 */     mnNavigation.add(mntmGoToOld);
/*     */ 
/* 360 */     this.mnMeasurements = new JMenu("Measurements");
/* 361 */     menuBar.add(this.mnMeasurements);
/*     */ 
/* 363 */     this.saveMeas = new JMenuItem("Save Measurement");
/* 364 */     this.saveMeas.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent arg0) {
/*     */         try {
/* 367 */           SpatialAnalysisPanel p = (SpatialAnalysisPanel)ADIMenu.this.entrance.getMainFrame().getPane();
/* 368 */           MeasurementSaver.saveMeasurement(ADIMenu.this.entrance, p.x, p.y, p.getTool(), ADIMenu.this.entrance.getImage(), p.getColorScheme());
/*     */         }
/*     */         catch (Exception localException)
/*     */         {
/*     */         }
/*     */       }
/*     */     });
/* 372 */     this.saveMeas.setVisible(false);
/* 373 */     this.saveMeas.setAccelerator(KeyStroke.getKeyStroke(77, 2));
/* 374 */     this.mnMeasurements.add(this.saveMeas);
/*     */ 
/* 376 */     this.showMeas = new JMenuItem("Show Last Measurement");
/* 377 */     this.showMeas.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 379 */         MeasurementSaver.readMeasurement(ADIMenu.this.entrance.getMeasurementFile());
/*     */       }
/*     */     });
/* 382 */     this.showMeas.setVisible(false);
/* 383 */     this.showMeas.setAccelerator(KeyStroke.getKeyStroke(76, 2));
/* 384 */     this.mnMeasurements.add(this.showMeas);
/*     */ 
/* 386 */     JSeparator separator_5 = new JSeparator();
/* 387 */     this.mnMeasurements.add(separator_5);
/*     */ 
/* 389 */     JMenuItem mntmNewMeasurementFile = new JMenuItem("New Measurement File");
/* 390 */     mntmNewMeasurementFile.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 392 */         MeasurementSaver.createNewMeasurementFile(ADIMenu.this.entrance);
/*     */       }
/*     */     });
/* 395 */     mntmNewMeasurementFile.setAccelerator(KeyStroke.getKeyStroke(78, 2));
/* 396 */     this.mnMeasurements.add(mntmNewMeasurementFile);
/*     */ 
/* 398 */     JMenu mnUtilities = new JMenu("Utilities");
/* 399 */     menuBar.add(mnUtilities);
/*     */ 
/* 401 */     JMenuItem mntmTrimImage = new JMenuItem("Trim Image");
/* 402 */     mntmTrimImage.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent arg0) {
/* 404 */         if (ADIMenu.this.entrance.getImage() == null)
/* 405 */           JOptionPane.showMessageDialog(null, "No image has been selected. Cannot trim image.", null, -1);
/*     */         else
/* 407 */           new TrimDialog(ADIMenu.this.entrance).setVisible(true);
/*     */       }
/*     */     });
/* 411 */     mntmTrimImage.setAccelerator(KeyStroke.getKeyStroke(84, 2));
/* 412 */     mnUtilities.add(mntmTrimImage);
/*     */ 
/* 414 */     JMenuItem mntmCombineImages = new JMenuItem("Combine Images");
/* 415 */     mntmCombineImages.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 417 */         new CombineImagesDialog(ADIMenu.this.entrance).setVisible(true);
/*     */       }
/*     */     });
/* 420 */     mntmCombineImages.setAccelerator(KeyStroke.getKeyStroke(67, 3));
/* 421 */     mnUtilities.add(mntmCombineImages);
/*     */ 
/* 423 */     JMenuItem mntmCheckColorQuality = new JMenuItem("Check Color Quality");
/* 424 */     mntmCheckColorQuality.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 426 */         new ColorQualityDialog().setVisible(true);
/*     */       }
/*     */     });
/* 429 */     mnUtilities.add(mntmCheckColorQuality);
/*     */ 
/* 431 */     JMenuItem mntmAnnotateImage = new JMenuItem("Annotate Image");
/* 432 */     mntmAnnotateImage.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 434 */         if (ADIMenu.this.entrance.getImage() != null)
/* 435 */           new AnnotateImageDialog(ADIMenu.this.entrance).setVisible(true);
/*     */       }
/*     */     });
/* 438 */     mnUtilities.add(mntmAnnotateImage);
/*     */ 
/* 440 */     JMenu mnPreferences = new JMenu("Preferences");
/* 441 */     add(mnPreferences);
/*     */ 
/* 443 */     JMenu mnLineWidth = new JMenu("Line Width");
/* 444 */     mnPreferences.add(mnLineWidth);
/*     */ 
/* 446 */     ButtonGroup widths = new ButtonGroup();
/*     */ 
/* 448 */     this.width1 = new JRadioButtonMenuItem("1");
/* 449 */     this.width1.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 451 */         ADIMenu.this.entrance.setLineWidth(1.0F);
/* 452 */         ADIMenu.this.entrance.updateTool();
/*     */       }
/*     */     });
/* 455 */     this.width1.setSelected(true);
/* 456 */     mnLineWidth.add(this.width1);
/* 457 */     widths.add(this.width1);
/*     */ 
/* 459 */     this.width2 = new JRadioButtonMenuItem("2");
/* 460 */     this.width2.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 462 */         ADIMenu.this.entrance.setLineWidth(2.0F);
/* 463 */         ADIMenu.this.entrance.updateTool();
/*     */       }
/*     */     });
/* 466 */     mnLineWidth.add(this.width2);
/* 467 */     widths.add(this.width2);
/*     */ 
/* 469 */     this.width3 = new JRadioButtonMenuItem("3");
/* 470 */     this.width3.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 472 */         ADIMenu.this.entrance.setLineWidth(3.0F);
/* 473 */         ADIMenu.this.entrance.updateTool();
/*     */       }
/*     */     });
/* 476 */     mnLineWidth.add(this.width3);
/* 477 */     widths.add(this.width3);
/*     */ 
/* 479 */     JMenu mnCursorStyle = new JMenu("Cursor Style");
/* 480 */     mnPreferences.add(mnCursorStyle);
/*     */ 
/* 482 */     this.styleCirc = new JRadioButtonMenuItem("Circle");
/* 483 */     this.styleCirc.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 485 */         ADIMenu.this.entrance.setCursorStyle((byte)0);
/* 486 */         ADIMenu.this.entrance.updateTool();
/*     */       }
/*     */     });
/* 489 */     this.styleCirc.setSelected(true);
/* 490 */     mnCursorStyle.add(this.styleCirc);
/*     */ 
/* 492 */     this.styleFilledCirc = new JRadioButtonMenuItem("Filled Circle");
/* 493 */     this.styleFilledCirc.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 495 */         ADIMenu.this.entrance.setCursorStyle((byte)1);
/* 496 */         ADIMenu.this.entrance.updateTool();
/*     */       }
/*     */     });
/* 499 */     mnCursorStyle.add(this.styleFilledCirc);
/*     */ 
/* 501 */     this.styleRect = new JRadioButtonMenuItem("Rectangle");
/* 502 */     this.styleRect.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 504 */         ADIMenu.this.entrance.setCursorStyle((byte)2);
/* 505 */         ADIMenu.this.entrance.updateTool();
/*     */       }
/*     */     });
/* 508 */     mnCursorStyle.add(this.styleRect);
/*     */ 
/* 510 */     this.styleFilledRect = new JRadioButtonMenuItem("Filled Rectangle");
/* 511 */     this.styleFilledRect.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 513 */         ADIMenu.this.entrance.setCursorStyle((byte)3);
/* 514 */         ADIMenu.this.entrance.updateTool();
/*     */       }
/*     */     });
/* 517 */     mnCursorStyle.add(this.styleFilledRect);
/*     */ 
/* 519 */     ButtonGroup styles = new ButtonGroup();
/* 520 */     styles.add(this.styleCirc);
/* 521 */     styles.add(this.styleFilledCirc);
/* 522 */     styles.add(this.styleRect);
/* 523 */     styles.add(this.styleFilledRect);
/*     */ 
/* 525 */     this.styleNone = new JRadioButtonMenuItem("None");
/* 526 */     this.styleNone.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent arg0) {
/* 528 */         ADIMenu.this.entrance.setCursorStyle((byte)4);
/* 529 */         ADIMenu.this.entrance.updateTool();
/*     */       }
/*     */     });
/* 532 */     mnCursorStyle.add(this.styleNone);
/* 533 */     styles.add(this.styleNone);
/*     */ 
/* 535 */     JMenu mnChangeColorOf = new JMenu("Change Color of Spatial Tool");
/* 536 */     mnPreferences.add(mnChangeColorOf);
/*     */ 
/* 538 */     this.black = new JRadioButtonMenuItem("Black");
/* 539 */     this.black.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent arg0) {
/* 541 */         ADIMenu.this.entrance.setColor(Color.BLACK);
/* 542 */         ADIMenu.this.entrance.updateTool();
/*     */       }
/*     */     });
/* 545 */     mnChangeColorOf.add(this.black);
/*     */ 
/* 547 */     this.blue = new JRadioButtonMenuItem("Blue");
/* 548 */     this.blue.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 550 */         ADIMenu.this.entrance.setColor(Color.BLUE);
/* 551 */         ADIMenu.this.entrance.updateTool();
/*     */       }
/*     */     });
/* 554 */     mnChangeColorOf.add(this.blue);
/*     */ 
/* 556 */     this.cyan = new JRadioButtonMenuItem("Cyan");
/* 557 */     this.cyan.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 559 */         ADIMenu.this.entrance.setColor(Color.CYAN);
/* 560 */         ADIMenu.this.entrance.updateTool();
/*     */       }
/*     */     });
/* 563 */     mnChangeColorOf.add(this.cyan);
/*     */ 
/* 565 */     this.gray = new JRadioButtonMenuItem("Gray");
/* 566 */     this.gray.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 568 */         ADIMenu.this.entrance.setColor(Color.GRAY);
/* 569 */         ADIMenu.this.entrance.updateTool();
/*     */       }
/*     */     });
/* 572 */     mnChangeColorOf.add(this.gray);
/*     */ 
/* 574 */     this.green = new JRadioButtonMenuItem("Green");
/* 575 */     this.green.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 577 */         ADIMenu.this.entrance.setColor(Color.GREEN);
/* 578 */         ADIMenu.this.entrance.updateTool();
/*     */       }
/*     */     });
/* 581 */     mnChangeColorOf.add(this.green);
/*     */ 
/* 583 */     JSeparator separator_15 = new JSeparator();
/* 584 */     mnChangeColorOf.add(separator_15);
/*     */ 
/* 586 */     this.magenta = new JRadioButtonMenuItem("Magenta");
/* 587 */     this.magenta.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 589 */         ADIMenu.this.entrance.setColor(Color.MAGENTA);
/* 590 */         ADIMenu.this.entrance.updateTool();
/*     */       }
/*     */     });
/* 593 */     mnChangeColorOf.add(this.magenta);
/*     */ 
/* 595 */     this.orange = new JRadioButtonMenuItem("Orange");
/* 596 */     this.orange.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 598 */         ADIMenu.this.entrance.setColor(Color.ORANGE);
/* 599 */         ADIMenu.this.entrance.updateTool();
/*     */       }
/*     */     });
/* 602 */     mnChangeColorOf.add(this.orange);
/*     */ 
/* 604 */     this.red = new JRadioButtonMenuItem("Red");
/* 605 */     this.red.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 607 */         ADIMenu.this.entrance.setColor(Color.RED);
/* 608 */         ADIMenu.this.entrance.updateTool();
/*     */       }
/*     */     });
/* 611 */     mnChangeColorOf.add(this.red);
/*     */ 
/* 613 */     this.white = new JRadioButtonMenuItem("White");
/* 614 */     this.white.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 616 */         ADIMenu.this.entrance.setColor(Color.WHITE);
/* 617 */         ADIMenu.this.entrance.updateTool();
/*     */       }
/*     */     });
/* 620 */     mnChangeColorOf.add(this.white);
/*     */ 
/* 622 */     this.yellow = new JRadioButtonMenuItem("Yellow");
/* 623 */     this.yellow.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 625 */         ADIMenu.this.entrance.setColor(Color.YELLOW);
/* 626 */         ADIMenu.this.entrance.updateTool();
/*     */       }
/*     */     });
/* 629 */     this.yellow.setSelected(true);
/* 630 */     mnChangeColorOf.add(this.yellow);
/*     */ 
/* 632 */     ButtonGroup colors = new ButtonGroup();
/* 633 */     colors.add(this.black);
/* 634 */     colors.add(this.blue);
/* 635 */     colors.add(this.cyan);
/* 636 */     colors.add(this.gray);
/* 637 */     colors.add(this.green);
/* 638 */     colors.add(this.magenta);
/* 639 */     colors.add(this.orange);
/* 640 */     colors.add(this.red);
/* 641 */     colors.add(this.white);
/* 642 */     colors.add(this.yellow);
/*     */ 
/* 645 */     this.saveMask = new JMenu("Save Color Masks");
/* 646 */     menuBar.add(this.saveMask);
/*     */ 
/* 648 */     this.saveNewMask = new JMenuItem("Save New Color Mask");
/* 649 */     this.saveNewMask.setVisible(false);
/* 650 */     this.saveNewMask.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 652 */         new SaveNewMaskDialog(ADIMenu.this.entrance).setVisible(true);
/*     */       }
/*     */     });
/* 655 */     this.saveMask.add(this.saveNewMask);
/*     */ 
/* 657 */     JSeparator separator_7 = new JSeparator();
/* 658 */     this.saveMask.add(separator_7);
/*     */ 
/* 660 */     JMenuItem mntmSaveColorMasks = new JMenuItem("Save Color Masks to File");
/* 661 */     mntmSaveColorMasks.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 663 */         String s = "";
/* 664 */         ArrayList al = ADIMenu.this.entrance.getColorMasks();
/* 665 */         for (int i = 0; i < al.size(); i++) {
/* 666 */           s = s + ((ColorMask)al.get(i)).toString();
/*     */         }
/* 668 */         File f = SWTFile.saveFile("Save Mask", "ColorMask");
/*     */         try {
/* 670 */           if (!f.exists())
/* 671 */             f.createNewFile();
/* 672 */           FileWriter outstream = new FileWriter(f);
/* 673 */           BufferedWriter out = new BufferedWriter(outstream);
/* 674 */           out.write(s);
/* 675 */           out.close();
/*     */         }
/*     */         catch (Exception localException)
/*     */         {
/*     */         }
/*     */       }
/*     */     });
/* 679 */     this.saveMask.add(mntmSaveColorMasks);
/*     */ 
/* 681 */     JMenuItem mntmRecallColorMasks = new JMenuItem("Recall Color Masks from File");
/* 682 */     mntmRecallColorMasks.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/*     */         File f;
/*     */         try {
/* 686 */           FileDialog fc = new FileDialog(new Shell(), 4096);
/* 687 */           String[] fileTypes = { "Text Files *.txt" };
/* 688 */           String[] fileExtensions = { "*.txt" };
/* 689 */           fc.setFilterExtensions(fileExtensions);
/* 690 */           fc.setFilterNames(fileTypes);
/* 691 */           fc.setText("Open Color Masks");
/* 692 */           String path = fc.open();
/* 693 */           if (path == null)
/* 694 */             return;
/* 695 */           f = new File(path);
/*     */         }
/*     */         catch (Throwable t)
/*     */         {
/* 697 */           JFileChooser fc = new JFileChooser();
/* 698 */           fc.setFileFilter(new FileFilter()
/*     */           {
/*     */             public boolean accept(File f) {
/* 701 */               if (f.getName().substring(f.getName().lastIndexOf('.') + 1).equals("txt")) {
/* 702 */                 return true;
/*     */               }
/* 704 */               return false;
/*     */             }
/*     */ 
/*     */             public String getDescription() {
/* 708 */               return "Text File *.txt";
/*     */             }
/*     */           });
/* 711 */           int i = fc.showOpenDialog(null);
/* 712 */           if (i == 1)
/* 713 */             return;
/* 714 */           f = fc.getSelectedFile();
/*     */         }
/* 716 */         ArrayList masks = new ArrayList();
/*     */         try {
/* 718 */           Scanner scan = new Scanner(f);
/* 719 */           String s = "";
/* 720 */           while ((s = scan.nextLine()) != null) {
/*     */             try {
/* 722 */               masks.add(new ColorMask(s));
/*     */             } catch (Exception x) {
/* 724 */               x.printStackTrace();
/* 725 */               JOptionPane.showMessageDialog(null, "File is not a valid Color Mask File.", null, -1);
/* 726 */               return;
/*     */             }
/*     */           }
/* 729 */           scan.close(); } catch (Exception localException1) {
/*     */         }
/* 731 */         for (int i = 0; i < masks.size(); i++)
/* 732 */           ADIMenu.this.addMask((ColorMask)masks.get(i), ((ColorMask)masks.get(i)).title);
/*     */       }
/*     */     });
/* 736 */     mntmRecallColorMasks.setAccelerator(KeyStroke.getKeyStroke(82, 2));
/* 737 */     this.saveMask.add(mntmRecallColorMasks);
/*     */ 
/* 739 */     this.applyMask = new JMenu("Apply Color Masks");
/* 740 */     this.applyMask.setVisible(false);
/* 741 */     add(this.applyMask);
/*     */ 
/* 743 */     JMenu mnHelp = new JMenu("Help");
/* 744 */     menuBar.add(mnHelp);
/*     */ 
/* 746 */     JMenuItem mntmAboutTheSoftware = new JMenuItem("About the Software");
/* 747 */     mntmAboutTheSoftware.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 749 */         ADIMenu.this.entrance.setPane(new AboutPanel());
/*     */       }
/*     */     });
/* 752 */     mnHelp.add(mntmAboutTheSoftware);
/*     */ 
/* 754 */     JSeparator separator_14 = new JSeparator();
/* 755 */     mnHelp.add(separator_14);
/*     */ 
/* 757 */     JMenuItem mntmOpenManualAs = new JMenuItem("Open Manual as PDF");
/* 758 */     mntmOpenManualAs.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent arg0) {
/*     */         try {
/* 761 */           File pdfFile = new File("resources/manual.pdf");
/* 762 */           if (pdfFile.exists()) {
/* 763 */             if (Desktop.isDesktopSupported())
/* 764 */               Desktop.getDesktop().open(pdfFile);
/*     */             else {
/* 766 */               System.out.println("Awt Desktop is not supported!");
/*     */             }
/*     */           }
/*     */           else {
/* 770 */             System.out.println("File does not exist!");
/*     */           }
/*     */ 
/* 773 */           System.out.println("Done");
/*     */         }
/*     */         catch (Exception ex) {
/* 776 */           ex.printStackTrace();
/*     */         }
/*     */       }
/*     */     });
/* 780 */     mnHelp.add(mntmOpenManualAs);
/*     */ 
/* 782 */     JMenuItem mntmOpenHelpManual = new JMenuItem("Open Help Manual in Browser");
/* 783 */     mntmOpenHelpManual.setVisible(false);
/* 784 */     mntmOpenHelpManual.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/*     */         try {
/* 787 */           File htmFile = new File("resources/manual.htm");
/* 788 */           if (htmFile.exists()) {
/* 789 */             if (Desktop.isDesktopSupported())
/* 790 */               Desktop.getDesktop().open(htmFile);
/*     */             else
/* 792 */               System.out.println("Awt Desktop is not supported!");
/*     */           }
/*     */           else {
/* 795 */             System.out.println("File does not exist!");
/*     */           }
/* 797 */           System.out.println("Done");
/*     */         } catch (Exception ex) {
/* 799 */           ex.printStackTrace();
/*     */         }
/*     */       }
/*     */     });
/* 803 */     mnHelp.add(mntmOpenHelpManual);
/* 804 */     setCursorStyle();
/* 805 */     setColor();
/* 806 */     setLineWidth();
/*     */   }
/*     */ 
/*     */   protected void setColor(byte color)
/*     */   {
/* 814 */     switch (color) {
/*     */     case 0:
/* 816 */       this.black.setSelected(true);
/* 817 */       break;
/*     */     case 1:
/* 819 */       this.blue.setSelected(true);
/* 820 */       break;
/*     */     case 2:
/* 822 */       this.cyan.setSelected(true);
/* 823 */       break;
/*     */     case 3:
/* 825 */       this.gray.setSelected(true);
/* 826 */       break;
/*     */     case 4:
/* 828 */       this.green.setSelected(true);
/* 829 */       break;
/*     */     case 5:
/* 831 */       this.magenta.setSelected(true);
/* 832 */       break;
/*     */     case 6:
/* 834 */       this.orange.setSelected(true);
/* 835 */       break;
/*     */     case 7:
/* 837 */       this.red.setSelected(true);
/* 838 */       break;
/*     */     case 8:
/* 840 */       this.white.setSelected(true);
/* 841 */       break;
/*     */     case 9:
/* 843 */       this.yellow.setSelected(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void print(BufferedImage img)
/*     */   {
/* 849 */     SWTFile.print(img, this.entrance);
/*     */   }
/*     */ 
/*     */   private void openPicture()
/*     */   {
/* 854 */     File f = this.entrance.openImage("Open an Image");
/* 855 */     if (f == null)
/* 856 */       return;
/*     */     try {
/* 858 */       this.entrance.setImage(ImageIO.read(f));
/* 859 */       this.entrance.updatePic();
/*     */     } catch (IOException e1) {
/* 861 */       e1.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setColor()
/*     */   {
/* 869 */     Entrance e = this.entrance;
/* 870 */     if (e.getColor().equals(Color.BLACK))
/* 871 */       this.black.setSelected(true);
/* 872 */     else if (e.getColor().equals(Color.BLUE))
/* 873 */       this.blue.setSelected(true);
/* 874 */     else if (e.getColor().equals(Color.CYAN))
/* 875 */       this.cyan.setSelected(true);
/* 876 */     else if (e.getColor().equals(Color.GRAY))
/* 877 */       this.gray.setSelected(true);
/* 878 */     else if (e.getColor().equals(Color.GREEN))
/* 879 */       this.green.setSelected(true);
/* 880 */     else if (e.getColor().equals(Color.MAGENTA))
/* 881 */       this.magenta.setSelected(true);
/* 882 */     else if (e.getColor().equals(Color.ORANGE))
/* 883 */       this.orange.setSelected(true);
/* 884 */     else if (e.getColor().equals(Color.RED))
/* 885 */       this.red.setSelected(true);
/* 886 */     else if (e.getColor().equals(Color.WHITE))
/* 887 */       this.white.setSelected(true);
/* 888 */     else if (e.getColor().equals(Color.YELLOW))
/* 889 */       this.yellow.setSelected(true);
/*     */   }
/*     */ 
/*     */   public void setLineWidth()
/*     */   {
/* 897 */     float f = this.entrance.getLineWidth();
/* 898 */     if (f == 1.0F)
/* 899 */       this.width1.setSelected(true);
/* 900 */     else if (f == 2.0F)
/* 901 */       this.width2.setSelected(true);
/* 902 */     else if (f == 3.0F)
/* 903 */       this.width3.setSelected(true);
/*     */   }
/*     */ 
/*     */   public void setCursorStyle()
/*     */   {
/* 911 */     byte b = this.entrance.getCursorStyle();
/* 912 */     if (b == 0)
/* 913 */       this.styleCirc.setSelected(true);
/* 914 */     else if (b == 1)
/* 915 */       this.styleFilledCirc.setSelected(true);
/* 916 */     else if (b == 2)
/* 917 */       this.styleRect.setSelected(true);
/* 918 */     else if (b == 3)
/* 919 */       this.styleFilledRect.setSelected(true);
/* 920 */     else if (b == 4)
/* 921 */       this.styleNone.setSelected(true);
/*     */   }
/*     */ 
/*     */   public void showMeasurement(boolean show) {
/* 925 */     this.showMeas.setVisible(show);
/*     */   }
/*     */ 
/*     */   public void showSaveMeas(boolean show)
/*     */   {
/* 932 */     this.saveMeas.setVisible(show);
/*     */   }
/*     */ 
/*     */   public void addMask(final ColorMask mask, String title)
/*     */   {
/* 940 */     mask.setTitle(title);
/* 941 */     this.entrance.addColorMask(mask);
/* 942 */     final JMenuItem msk = new JMenuItem("Apply " + title);
/* 943 */     msk.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e) {
/* 946 */         ((MaskColorsPanel)ADIMenu.this.entrance.getPane()).applyMask(mask);
/*     */       }
/*     */     });
/* 949 */     this.applyMask.add(msk);
/* 950 */     final JMenuItem mi = new JMenuItem("Remove " + title);
/* 951 */     mi.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent arg0) {
/* 954 */         ADIMenu.this.applyMask.remove(msk);
/* 955 */         ADIMenu.this.saveMask.remove(mi);
/* 956 */         ADIMenu.this.entrance.removeColorMask(mask);
/*     */       }
/*     */     });
/* 959 */     this.saveMask.add(mi);
/* 960 */     if ((this.entrance.getPane() instanceof MaskColorsPanel))
/* 961 */       this.applyMask.setVisible(true);
/*     */   }
/*     */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.ADIMenu
 * JD-Core Version:    0.6.2
 */