/*     */ package org.gss.adi;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.JTextField;
/*     */ 
/*     */ public class StartPanel extends JPanel
/*     */ {
/*     */   private static final long serialVersionUID = 339625940460934284L;
/*     */   private JTextField txtOpenAnImage;
/*     */   private JTextField txtOpenImages;
/*     */   private Entrance entrance;
/*     */ 
/*     */   public StartPanel(Entrance e)
/*     */   {
/*  23 */     setLayout(null);
/*  24 */     this.entrance = e;
/*  25 */     setup();
/*     */   }
/*     */   private void setup() {
/*  28 */     JTextArea txtrThisSoftwareProvides = new JTextArea();
/*  29 */     txtrThisSoftwareProvides.setFont(new Font("SansSerif", 1, 14));
/*  30 */     txtrThisSoftwareProvides.setEditable(false);
/*  31 */     txtrThisSoftwareProvides.setText("This software provides tools for visualizing and measuring spatial and spectral (color) qualities and relationships in digital images.");
/*  32 */     txtrThisSoftwareProvides.setWrapStyleWord(true);
/*  33 */     txtrThisSoftwareProvides.setLineWrap(true);
/*  34 */     txtrThisSoftwareProvides.setOpaque(false);
/*  35 */     txtrThisSoftwareProvides.setBounds(10, 11, 386, 58);
/*  36 */     add(txtrThisSoftwareProvides);
/*     */ 
/*  38 */     JTextArea txtrMoveTheCursor = new JTextArea();
/*  39 */     txtrMoveTheCursor.setFont(new Font("SansSerif", 0, 13));
/*  40 */     txtrMoveTheCursor.setEditable(false);
/*  41 */     txtrMoveTheCursor.setText("Move the cursor over the buttons below for more information about the selection.");
/*  42 */     txtrMoveTheCursor.setWrapStyleWord(true);
/*  43 */     txtrMoveTheCursor.setLineWrap(true);
/*  44 */     txtrMoveTheCursor.setOpaque(false);
/*  45 */     txtrMoveTheCursor.setBounds(20, 80, 361, 34);
/*  46 */     add(txtrMoveTheCursor);
/*     */ 
/*  48 */     this.txtOpenAnImage = new JTextField();
/*  49 */     this.txtOpenAnImage.setEditable(false);
/*  50 */     this.txtOpenAnImage.setFont(new Font("Tahoma", 2, 14));
/*  51 */     this.txtOpenAnImage.setText("Open an Image and Analyze in One of These Windows");
/*  52 */     this.txtOpenAnImage.setBounds(49, 125, 344, 20);
/*  53 */     this.txtOpenAnImage.setOpaque(false);
/*  54 */     this.txtOpenAnImage.setBorder(null);
/*  55 */     add(this.txtOpenAnImage);
/*  56 */     this.txtOpenAnImage.setColumns(10);
/*     */ 
/*  58 */     JButton btnNewButton = new JButton("Spatial Analysis");
/*  59 */     btnNewButton.setToolTipText("Measure the length, area, position, and color of interest.");
/*  60 */     btnNewButton.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/*  62 */         new SpatialAnalysisPanel(StartPanel.this.entrance);
/*     */       }
/*     */     });
/*  65 */     btnNewButton.setFont(new Font("SansSerif", 0, 24));
/*  66 */     btnNewButton.setBounds(49, 156, 294, 154);
/*  67 */     add(btnNewButton);
/*     */ 
/*  69 */     JButton btnEnhanceColors = new JButton("Enhance Colors");
/*  70 */     btnEnhanceColors.setToolTipText("<html>Change how the colors of the image are displayed on the computer screen - you <br/>may see new features within the image that may relate to physical or chemical <br/>properties of that object.");
/*  71 */     btnEnhanceColors.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/*  73 */         new EnhanceColorsPanel(StartPanel.this.entrance);
/*     */       }
/*     */     });
/*  76 */     btnEnhanceColors.setFont(new Font("SansSerif", 0, 24));
/*  77 */     btnEnhanceColors.setBounds(353, 156, 294, 154);
/*  78 */     add(btnEnhanceColors);
/*     */ 
/*  80 */     JButton btnMaskColor = new JButton("Mask Color");
/*  81 */     btnMaskColor.setToolTipText("<html>Select a range or relationship of colors to highlight. Pixels meeting these color <br/>conditions become black; those that don't become white.");
/*  82 */     btnMaskColor.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/*  84 */         new MaskColorsPanel(StartPanel.this.entrance);
/*     */       }
/*     */     });
/*  87 */     btnMaskColor.setFont(new Font("SansSerif", 0, 24));
/*  88 */     btnMaskColor.setBounds(657, 156, 294, 154);
/*  89 */     add(btnMaskColor);
/*     */ 
/*  91 */     JButton btnTimeSeries = new JButton("Time Series");
/*  92 */     btnTimeSeries.setToolTipText("<html>Measure the length, area, position, and color of features of interest within a series <br/>of images.");
/*  93 */     btnTimeSeries.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/*  95 */         StartPanel.this.entrance.launchTimeSeries();
/*     */       }
/*     */     });
/*  98 */     btnTimeSeries.setFont(new Font("SansSerif", 0, 24));
/*  99 */     btnTimeSeries.setBounds(49, 352, 294, 154);
/* 100 */     add(btnTimeSeries);
/*     */ 
/* 102 */     JButton btnExampleOldGrowth = new JButton("<html><center>Example: <br/>Old Growth Forest</center>");
/* 103 */     btnExampleOldGrowth.setToolTipText("<html>Explore a time series of aps showing the loation of old growth forests across the <br/>U.S. over the past 300+ years.");
/* 104 */     btnExampleOldGrowth.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent arg0) {
/* 106 */         new OldGrowthForestPanel(StartPanel.this.entrance, false);
/*     */       }
/*     */     });
/* 109 */     btnExampleOldGrowth.setMargin(new Insets(2, 0, 2, 0));
/* 110 */     btnExampleOldGrowth.setFont(new Font("SansSerif", 0, 24));
/* 111 */     btnExampleOldGrowth.setBounds(353, 352, 294, 154);
/* 112 */     add(btnExampleOldGrowth);
/*     */ 
/* 114 */     JButton btnAboutAdi = new JButton("About ADI");
/* 115 */     btnAboutAdi.setToolTipText("<html>Review the basics of this software. For more information,<br/>see the information in the help menu.");
/* 116 */     btnAboutAdi.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 118 */         new AboutADIPanel(StartPanel.this.entrance);
/*     */       }
/*     */     });
/* 121 */     btnAboutAdi.setFont(new Font("SansSerif", 0, 24));
/* 122 */     btnAboutAdi.setBounds(657, 352, 294, 154);
/* 123 */     add(btnAboutAdi);
/*     */ 
/* 125 */     this.txtOpenImages = new JTextField();
/* 126 */     this.txtOpenImages.setEditable(false);
/* 127 */     this.txtOpenImages.setText("Open 2-3 Images to Analyze");
/* 128 */     this.txtOpenImages.setFont(new Font("Tahoma", 2, 14));
/* 129 */     this.txtOpenImages.setColumns(10);
/* 130 */     this.txtOpenImages.setBounds(49, 321, 188, 20);
/* 131 */     this.txtOpenImages.setOpaque(false);
/* 132 */     this.txtOpenImages.setBorder(null);
/* 133 */     add(this.txtOpenImages);
/*     */ 
/* 135 */     JTextArea txtrAdditionalToolsAnd = new JTextArea();
/* 136 */     txtrAdditionalToolsAnd.setFont(new Font("SansSerif", 0, 13));
/* 137 */     txtrAdditionalToolsAnd.setEditable(false);
/* 138 */     txtrAdditionalToolsAnd.setWrapStyleWord(true);
/* 139 */     txtrAdditionalToolsAnd.setLineWrap(true);
/* 140 */     txtrAdditionalToolsAnd.setText("Additional tools and options are available in the Menu Bar.  Those that work with single-image analysis (Spatial Analysis, Enhance Colors, and Mask Colors) are marked with an icon of a single image.  Those that work with the time series of images are marked with an icon of multiple images.  Some options work with both single and multime images and so are indicated by having both icons.\r\n\r\nIf you select a menu option that doesn't work for your type of analysis, nothing will happen.");
/* 141 */     txtrAdditionalToolsAnd.setBounds(49, 517, 902, 119);
/* 142 */     txtrAdditionalToolsAnd.setOpaque(false);
/* 143 */     add(txtrAdditionalToolsAnd);
/*     */   }
/*     */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.StartPanel
 * JD-Core Version:    0.6.2
 */