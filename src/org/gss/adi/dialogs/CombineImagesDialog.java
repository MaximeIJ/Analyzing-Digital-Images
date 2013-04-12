/*     */ package org.gss.adi.dialogs;
/*     */ 
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import javax.imageio.ImageIO;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JCheckBoxMenuItem;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuBar;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JSlider;
/*     */ import javax.swing.JTextArea;
/*     */ import org.gss.adi.Entrance;
/*     */ import org.gss.adi.SWTFile;
/*     */ import org.gss.adi.ZoomPanLabel;
/*     */ import org.gss.adi.tools.ColorTools;
/*     */ 
/*     */ public class CombineImagesDialog extends JDialog
/*     */ {
/*     */   private static final long serialVersionUID = 3739461426406892628L;
/*     */   private ZoomPanLabel label;
/*     */   private JMenuItem mntmSelectRedLayer;
/*     */   private JMenuItem mntmSelectGreenLayer;
/*     */   private JMenuItem mntmShowCombinedColor;
/*     */   private JMenu mnGreenLayer;
/*     */   private JMenu mnRedLayer;
/*     */   private JMenu mnBlueLayer;
/*     */   private JMenuItem mntmSelectBlueLayer;
/*     */   private JMenuItem mntmRemoveRedLayer;
/*     */   private JMenuItem mntmRemoveGreenLayer;
/*     */   private JMenuItem mntmRemoveBlueLayer;
/*     */   private JCheckBoxMenuItem showGreenLayer;
/*     */   private JCheckBoxMenuItem showRedLayer;
/*     */   private JCheckBoxMenuItem showBlueLayer;
/*     */   private JCheckBoxMenuItem subRed;
/*     */   private JCheckBoxMenuItem subGreen;
/*     */   private JCheckBoxMenuItem subBlue;
/*     */   private JMenuItem mntmNewMenuItem;
/*     */   private JMenuItem swapSubs;
/*     */   private JMenuItem removeSub1;
/*     */   private JMenuItem removeSub2;
/*     */   private JMenuItem mntmSelectImage_1;
/*     */   private JMenuItem mntmSelectImage;
/*     */   private JCheckBoxMenuItem subGray;
/*     */   private JMenuItem mntmAddNewImage;
/*     */   private JMenuItem mntmShowAverageOf;
/*     */   private BufferedImage redLayer;
/*     */   private BufferedImage greenLayer;
/*     */   private BufferedImage blueLayer;
/*     */   private BufferedImage sub1;
/*     */   private BufferedImage sub2;
/*  67 */   private ArrayList<BufferedImage> avgImages = new ArrayList();
/*     */   private JMenu mnAvgImgs;
/*     */   private Entrance entrance;
/*  71 */   private CombineImagesDialog me = this;
/*     */ 
/*     */   public CombineImagesDialog(Entrance e)
/*     */   {
/*  77 */     setBounds(100, 100, 1004, 546);
/*  78 */     getContentPane().setLayout(null);
/*  79 */     this.entrance = e;
/*  80 */     JTextArea txtrCombiningImagesHelps = new JTextArea();
/*  81 */     txtrCombiningImagesHelps.setOpaque(false);
/*  82 */     txtrCombiningImagesHelps.setText("Combining images helps identify similarities and differences between the images. Use these three options to combine two or more images and use the analysis tools to explore the new image.\r\n\r\n1)Put color layers from different images into a new image to compare up to three images at once.\r\n    TIP: Use Enhancement tools to create\r\n    grayscale images of colors first so that\r\n    identical areas are in black and white\r\n    and the differences will be colorized.\r\n\r\n2) Select two pictures and subtract their color intensities.\r\n\r\n3)Average a set of images into one image.\r\n\r\nUse the menu above to combine images.\r\n\r\nNote: each image must have the same dimensions.");
/*  83 */     txtrCombiningImagesHelps.setEditable(false);
/*  84 */     txtrCombiningImagesHelps.setFont(new Font("SansSerif", 0, 13));
/*  85 */     txtrCombiningImagesHelps.setWrapStyleWord(true);
/*  86 */     txtrCombiningImagesHelps.setLineWrap(true);
/*  87 */     txtrCombiningImagesHelps.setBounds(12, 16, 309, 339);
/*  88 */     getContentPane().add(txtrCombiningImagesHelps);
/*     */ 
/*  90 */     this.label = new ZoomPanLabel(new JSlider());
/*  91 */     this.label.setBounds(333, 2, 640, 480);
/*  92 */     getContentPane().add(this.label);
/*     */ 
/*  94 */     JButton btnUseImageWith = new JButton("Use Image with Analysis Tools");
/*  95 */     btnUseImageWith.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/*  97 */         CombineImagesDialog.this.entrance.setImage(CombineImagesDialog.this.label.getOriginal());
/*     */       }
/*     */     });
/* 100 */     btnUseImageWith.setBounds(12, 368, 309, 25);
/* 101 */     getContentPane().add(btnUseImageWith);
/*     */ 
/* 103 */     JButton btnClose = new JButton("Close");
/* 104 */     btnClose.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 106 */         CombineImagesDialog.this.me.dispose();
/*     */       }
/*     */     });
/* 109 */     btnClose.setBounds(12, 406, 309, 25);
/* 110 */     getContentPane().add(btnClose);
/*     */ 
/* 112 */     JMenuBar menuBar = new JMenuBar();
/* 113 */     setJMenuBar(menuBar);
/*     */ 
/* 115 */     JMenu mnColorLayers = new JMenu("Color Layers");
/* 116 */     menuBar.add(mnColorLayers);
/*     */ 
/* 118 */     this.mntmShowCombinedColor = new JMenuItem("Show Combined Color Layers");
/* 119 */     this.mntmShowCombinedColor.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 121 */         CombineImagesDialog.this.combineColorLayers();
/*     */       }
/*     */     });
/* 124 */     mnColorLayers.add(this.mntmShowCombinedColor);
/*     */ 
/* 126 */     this.mnRedLayer = new JMenu("Red Layer");
/* 127 */     mnColorLayers.add(this.mnRedLayer);
/*     */ 
/* 129 */     this.mntmSelectRedLayer = new JMenuItem("Select Red Layer");
/* 130 */     this.mnRedLayer.add(this.mntmSelectRedLayer);
/*     */ 
/* 132 */     this.mntmRemoveRedLayer = new JMenuItem("Remove Red Layer");
/* 133 */     this.mntmRemoveRedLayer.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 135 */         CombineImagesDialog.this.redLayer = null;
/* 136 */         CombineImagesDialog.this.mntmRemoveRedLayer.setVisible(false);
/* 137 */         CombineImagesDialog.this.showRedLayer.setVisible(false);
/* 138 */         CombineImagesDialog.this.combineColorLayers();
/*     */       }
/*     */     });
/* 141 */     this.mntmRemoveRedLayer.setVisible(false);
/* 142 */     this.mnRedLayer.add(this.mntmRemoveRedLayer);
/*     */ 
/* 144 */     this.showRedLayer = new JCheckBoxMenuItem("Show");
/* 145 */     this.showRedLayer.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 147 */         CombineImagesDialog.this.combineColorLayers();
/*     */       }
/*     */     });
/* 150 */     this.showRedLayer.setVisible(false);
/* 151 */     this.showRedLayer.setSelected(true);
/* 152 */     this.mnRedLayer.add(this.showRedLayer);
/* 153 */     this.mntmSelectRedLayer.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/*     */         try {
/* 156 */           BufferedImage img = ImageIO.read(SWTFile.openImage("Open Red Layer", CombineImagesDialog.this.entrance));
/* 157 */           if ((CombineImagesDialog.this.greenLayer != null) && (
/* 158 */             (img.getWidth() != CombineImagesDialog.this.greenLayer.getWidth()) || (img.getHeight() != CombineImagesDialog.this.greenLayer.getHeight()))) {
/* 159 */             JOptionPane.showMessageDialog(null, "All layers must be the same size.", null, -1);
/* 160 */             return;
/*     */           }
/*     */ 
/* 163 */           if ((CombineImagesDialog.this.blueLayer != null) && (
/* 164 */             (img.getWidth() != CombineImagesDialog.this.blueLayer.getWidth()) || (img.getHeight() != CombineImagesDialog.this.blueLayer.getHeight()))) {
/* 165 */             JOptionPane.showMessageDialog(null, "All layers must be the same size.", null, -1);
/* 166 */             return;
/*     */           }
/*     */ 
/* 169 */           for (int i = 0; i < img.getWidth(); i++) {
/* 170 */             for (int j = 0; j < img.getHeight(); j++) {
/* 171 */               img.setRGB(i, j, ColorTools.toRGB(new int[] { ColorTools.rgb(img.getRGB(i, j))[0] }));
/*     */             }
/*     */           }
/* 174 */           CombineImagesDialog.this.redLayer = img;
/* 175 */           CombineImagesDialog.this.mntmRemoveRedLayer.setVisible(true);
/* 176 */           CombineImagesDialog.this.showRedLayer.setVisible(true);
/* 177 */           CombineImagesDialog.this.combineColorLayers();
/*     */         }
/*     */         catch (IOException localIOException)
/*     */         {
/*     */         }
/*     */       }
/*     */     });
/* 182 */     this.mnGreenLayer = new JMenu("Green Layer");
/* 183 */     mnColorLayers.add(this.mnGreenLayer);
/*     */ 
/* 185 */     this.mntmSelectGreenLayer = new JMenuItem("Select Green Layer");
/* 186 */     this.mntmSelectGreenLayer.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/*     */         try {
/* 189 */           BufferedImage img = ImageIO.read(SWTFile.openImage("Open Green Layer", CombineImagesDialog.this.entrance));
/* 190 */           if ((CombineImagesDialog.this.redLayer != null) && (
/* 191 */             (img.getWidth() != CombineImagesDialog.this.redLayer.getWidth()) || (img.getHeight() != CombineImagesDialog.this.redLayer.getHeight()))) {
/* 192 */             JOptionPane.showMessageDialog(null, "All layers must be the same size.", null, -1);
/* 193 */             return;
/*     */           }
/*     */ 
/* 196 */           if ((CombineImagesDialog.this.blueLayer != null) && (
/* 197 */             (img.getWidth() != CombineImagesDialog.this.blueLayer.getWidth()) || (img.getHeight() != CombineImagesDialog.this.blueLayer.getHeight()))) {
/* 198 */             JOptionPane.showMessageDialog(null, "All layers must be the same size.", null, -1);
/* 199 */             return;
/*     */           }
/*     */ 
/* 202 */           for (int i = 0; i < img.getWidth(); i++) {
/* 203 */             for (int j = 0; j < img.getHeight(); j++) {
/* 204 */               img.setRGB(i, j, ColorTools.toRGB(new int[] { 0, ColorTools.rgb(img.getRGB(i, j))[1] }));
/*     */             }
/*     */           }
/* 207 */           CombineImagesDialog.this.greenLayer = img;
/* 208 */           CombineImagesDialog.this.mntmRemoveGreenLayer.setVisible(true);
/* 209 */           CombineImagesDialog.this.showGreenLayer.setVisible(true);
/* 210 */           CombineImagesDialog.this.combineColorLayers();
/*     */         }
/*     */         catch (IOException localIOException)
/*     */         {
/*     */         }
/*     */       }
/*     */     });
/* 214 */     this.mnGreenLayer.add(this.mntmSelectGreenLayer);
/*     */ 
/* 216 */     this.mntmRemoveGreenLayer = new JMenuItem("Remove Green Layer");
/* 217 */     this.mntmRemoveGreenLayer.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 219 */         CombineImagesDialog.this.greenLayer = null;
/* 220 */         CombineImagesDialog.this.mntmRemoveGreenLayer.setVisible(false);
/* 221 */         CombineImagesDialog.this.showGreenLayer.setVisible(false);
/* 222 */         CombineImagesDialog.this.combineColorLayers();
/*     */       }
/*     */     });
/* 225 */     this.mntmRemoveGreenLayer.setVisible(false);
/* 226 */     this.mnGreenLayer.add(this.mntmRemoveGreenLayer);
/*     */ 
/* 228 */     this.showGreenLayer = new JCheckBoxMenuItem("Show");
/* 229 */     this.showGreenLayer.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 231 */         CombineImagesDialog.this.combineColorLayers();
/*     */       }
/*     */     });
/* 234 */     this.showGreenLayer.setVisible(false);
/* 235 */     this.showGreenLayer.setSelected(true);
/* 236 */     this.mnGreenLayer.add(this.showGreenLayer);
/*     */ 
/* 238 */     this.mnBlueLayer = new JMenu("Blue Layer");
/* 239 */     mnColorLayers.add(this.mnBlueLayer);
/*     */ 
/* 241 */     this.mntmSelectBlueLayer = new JMenuItem("Select Blue Layer");
/* 242 */     this.mntmSelectBlueLayer.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/*     */         try {
/* 245 */           BufferedImage img = ImageIO.read(SWTFile.openImage("Open Blue Layer", CombineImagesDialog.this.entrance));
/* 246 */           if ((CombineImagesDialog.this.greenLayer != null) && (
/* 247 */             (img.getWidth() != CombineImagesDialog.this.greenLayer.getWidth()) || (img.getHeight() != CombineImagesDialog.this.greenLayer.getHeight()))) {
/* 248 */             JOptionPane.showMessageDialog(null, "All layers must be the same size.", null, -1);
/* 249 */             return;
/*     */           }
/*     */ 
/* 252 */           if ((CombineImagesDialog.this.blueLayer != null) && (
/* 253 */             (img.getWidth() != CombineImagesDialog.this.blueLayer.getWidth()) || (img.getHeight() != CombineImagesDialog.this.blueLayer.getHeight()))) {
/* 254 */             JOptionPane.showMessageDialog(null, "All layers must be the same size.", null, -1);
/* 255 */             return;
/*     */           }
/*     */ 
/* 258 */           for (int i = 0; i < img.getWidth(); i++) {
/* 259 */             for (int j = 0; j < img.getHeight(); j++) {
/* 260 */               img.setRGB(i, j, ColorTools.toRGB(new int[] { 0, 0, ColorTools.rgb(img.getRGB(i, j))[2] }));
/*     */             }
/*     */           }
/* 263 */           CombineImagesDialog.this.blueLayer = img;
/* 264 */           CombineImagesDialog.this.mntmRemoveBlueLayer.setVisible(true);
/* 265 */           CombineImagesDialog.this.showBlueLayer.setVisible(true);
/* 266 */           CombineImagesDialog.this.combineColorLayers();
/*     */         }
/*     */         catch (IOException localIOException)
/*     */         {
/*     */         }
/*     */       }
/*     */     });
/* 270 */     this.mnBlueLayer.add(this.mntmSelectBlueLayer);
/*     */ 
/* 272 */     this.mntmRemoveBlueLayer = new JMenuItem("Remove Blue Layer");
/* 273 */     this.mntmRemoveBlueLayer.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 275 */         CombineImagesDialog.this.blueLayer = null;
/* 276 */         CombineImagesDialog.this.mntmRemoveBlueLayer.setVisible(false);
/* 277 */         CombineImagesDialog.this.showBlueLayer.setVisible(false);
/* 278 */         CombineImagesDialog.this.combineColorLayers();
/*     */       }
/*     */     });
/* 281 */     this.mntmRemoveBlueLayer.setVisible(false);
/* 282 */     this.mnBlueLayer.add(this.mntmRemoveBlueLayer);
/*     */ 
/* 284 */     this.showBlueLayer = new JCheckBoxMenuItem("Show");
/* 285 */     this.showBlueLayer.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 287 */         CombineImagesDialog.this.combineColorLayers();
/*     */       }
/*     */     });
/* 290 */     this.showBlueLayer.setVisible(false);
/* 291 */     this.showBlueLayer.setSelected(true);
/* 292 */     this.mnBlueLayer.add(this.showBlueLayer);
/*     */ 
/* 294 */     JMenu mnSubtractImages = new JMenu("Subtract Images");
/* 295 */     menuBar.add(mnSubtractImages);
/*     */ 
/* 297 */     this.mntmNewMenuItem = new JMenuItem("Show Subtracted Images");
/* 298 */     this.mntmNewMenuItem.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 300 */         CombineImagesDialog.this.subtractImages();
/*     */       }
/*     */     });
/* 303 */     mnSubtractImages.add(this.mntmNewMenuItem);
/*     */ 
/* 305 */     this.mntmSelectImage = new JMenuItem("Select Image 1");
/* 306 */     this.mntmSelectImage.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/*     */         try {
/* 309 */           BufferedImage img = ImageIO.read(SWTFile.openImage("Select Image to subtract from", CombineImagesDialog.this.entrance));
/* 310 */           if (CombineImagesDialog.this.sub2 != null) {
/* 311 */             if ((img.getWidth() != CombineImagesDialog.this.sub2.getWidth()) || (img.getHeight() != CombineImagesDialog.this.sub2.getHeight())) {
/* 312 */               JOptionPane.showMessageDialog(null, "Both images must be the same size.", null, -1);
/* 313 */               return;
/*     */             }
/* 315 */             CombineImagesDialog.this.swapSubs.setVisible(true);
/*     */           }
/* 317 */           CombineImagesDialog.this.sub1 = img;
/* 318 */           CombineImagesDialog.this.subtractImages();
/* 319 */           CombineImagesDialog.this.removeSub1.setVisible(true);
/*     */         }
/*     */         catch (Exception localException)
/*     */         {
/*     */         }
/*     */       }
/*     */     });
/* 323 */     mnSubtractImages.add(this.mntmSelectImage);
/*     */ 
/* 325 */     this.mntmSelectImage_1 = new JMenuItem("Select Image 2");
/* 326 */     this.mntmSelectImage_1.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/*     */         try {
/* 329 */           BufferedImage img = ImageIO.read(SWTFile.openImage("Select Image to subtract", CombineImagesDialog.this.entrance));
/* 330 */           if (CombineImagesDialog.this.sub1 != null) {
/* 331 */             if ((img.getWidth() != CombineImagesDialog.this.sub1.getWidth()) || (img.getHeight() != CombineImagesDialog.this.sub1.getHeight())) {
/* 332 */               JOptionPane.showMessageDialog(null, "Both images must be the same size.", null, -1);
/* 333 */               return;
/*     */             }
/* 335 */             CombineImagesDialog.this.swapSubs.setVisible(true);
/*     */           }
/* 337 */           CombineImagesDialog.this.sub2 = img;
/* 338 */           CombineImagesDialog.this.subtractImages();
/* 339 */           CombineImagesDialog.this.removeSub2.setVisible(true);
/*     */         }
/*     */         catch (Exception localException)
/*     */         {
/*     */         }
/*     */       }
/*     */     });
/* 343 */     mnSubtractImages.add(this.mntmSelectImage_1);
/*     */ 
/* 345 */     this.removeSub1 = new JMenuItem("Remove Image 1");
/* 346 */     this.removeSub1.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 348 */         CombineImagesDialog.this.sub1 = null;
/* 349 */         CombineImagesDialog.this.removeSub1.setVisible(false);
/* 350 */         CombineImagesDialog.this.swapSubs.setVisible(false);
/* 351 */         CombineImagesDialog.this.clear();
/*     */       }
/*     */     });
/* 354 */     this.removeSub1.setVisible(false);
/* 355 */     mnSubtractImages.add(this.removeSub1);
/*     */ 
/* 357 */     this.removeSub2 = new JMenuItem("Remove Image 2");
/* 358 */     this.removeSub2.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 360 */         CombineImagesDialog.this.sub2 = null;
/* 361 */         CombineImagesDialog.this.removeSub2.setVisible(false);
/* 362 */         CombineImagesDialog.this.swapSubs.setVisible(false);
/* 363 */         CombineImagesDialog.this.clear();
/*     */       }
/*     */     });
/* 366 */     this.removeSub2.setVisible(false);
/* 367 */     mnSubtractImages.add(this.removeSub2);
/*     */ 
/* 369 */     this.swapSubs = new JMenuItem("Swap Images");
/* 370 */     this.swapSubs.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 372 */         BufferedImage temp = CombineImagesDialog.this.sub1;
/* 373 */         CombineImagesDialog.this.sub1 = CombineImagesDialog.this.sub2;
/* 374 */         CombineImagesDialog.this.sub2 = temp;
/* 375 */         CombineImagesDialog.this.subtractImages();
/*     */       }
/*     */     });
/* 378 */     mnSubtractImages.add(this.swapSubs);
/*     */ 
/* 380 */     JMenu mnShowColors = new JMenu("Show Colors");
/* 381 */     mnSubtractImages.add(mnShowColors);
/*     */ 
/* 383 */     this.subRed = new JCheckBoxMenuItem("Red");
/* 384 */     this.subRed.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 386 */         CombineImagesDialog.this.subtractImages();
/*     */       }
/*     */     });
/* 389 */     this.subRed.setSelected(true);
/* 390 */     mnShowColors.add(this.subRed);
/*     */ 
/* 392 */     this.subGreen = new JCheckBoxMenuItem("Green");
/* 393 */     this.subGreen.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 395 */         CombineImagesDialog.this.subtractImages();
/*     */       }
/*     */     });
/* 398 */     this.subGreen.setSelected(true);
/* 399 */     mnShowColors.add(this.subGreen);
/*     */ 
/* 401 */     this.subBlue = new JCheckBoxMenuItem("Blue");
/* 402 */     this.subBlue.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 404 */         CombineImagesDialog.this.subtractImages();
/*     */       }
/*     */     });
/* 407 */     this.subBlue.setSelected(true);
/* 408 */     mnShowColors.add(this.subBlue);
/*     */ 
/* 410 */     this.subGray = new JCheckBoxMenuItem("Average Colors to Gray Scale");
/* 411 */     this.subGray.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 413 */         CombineImagesDialog.this.subtractImages();
/*     */       }
/*     */     });
/* 416 */     mnShowColors.add(this.subGray);
/*     */ 
/* 418 */     this.mnAvgImgs = new JMenu("Average Images");
/* 419 */     menuBar.add(this.mnAvgImgs);
/*     */ 
/* 421 */     this.mntmShowAverageOf = new JMenuItem("Show Average of Images");
/* 422 */     this.mntmShowAverageOf.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 424 */         CombineImagesDialog.this.averageImages();
/*     */       }
/*     */     });
/* 427 */     this.mnAvgImgs.add(this.mntmShowAverageOf);
/*     */ 
/* 429 */     this.mntmAddNewImage = new JMenuItem("Add New Image");
/* 430 */     this.mntmAddNewImage.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/*     */         try {
/* 433 */           File f = SWTFile.openImage("Open an Image to Average", CombineImagesDialog.this.entrance);
/* 434 */           final BufferedImage img = ImageIO.read(f);
/* 435 */           if ((CombineImagesDialog.this.avgImages.size() > 0) && (
/* 436 */             (img.getWidth() != ((BufferedImage)CombineImagesDialog.this.avgImages.get(0)).getWidth()) || (img.getHeight() != ((BufferedImage)CombineImagesDialog.this.avgImages.get(0)).getHeight()))) {
/* 437 */             JOptionPane.showMessageDialog(null, "All images must be the same size.", null, -1);
/* 438 */             return;
/*     */           }
/*     */ 
/* 441 */           CombineImagesDialog.this.avgImages.add(img);
/* 442 */           final JMenuItem mi = new JMenuItem("Remove " + f.getName());
/* 443 */           mi.addActionListener(new ActionListener()
/*     */           {
/*     */             public void actionPerformed(ActionEvent arg0) {
/* 446 */               CombineImagesDialog.this.avgImages.remove(img);
/* 447 */               CombineImagesDialog.this.mnAvgImgs.remove(mi);
/* 448 */               CombineImagesDialog.this.averageImages();
/* 449 */               if (CombineImagesDialog.this.avgImages.size() == 0)
/* 450 */                 CombineImagesDialog.this.clear();
/*     */             }
/*     */           });
/* 453 */           CombineImagesDialog.this.mnAvgImgs.add(mi);
/* 454 */           CombineImagesDialog.this.averageImages();
/*     */         }
/*     */         catch (Exception localException)
/*     */         {
/*     */         }
/*     */       }
/*     */     });
/* 458 */     this.mnAvgImgs.add(this.mntmAddNewImage);
/*     */   }
/*     */ 
/*     */   private void clear() {
/* 462 */     this.label.setImage(new BufferedImage(1, 1, 1));
/*     */   }
/*     */ 
/*     */   private void combineColorLayers()
/*     */   {
/*     */     int height;
              int width;
/* 466 */     if (this.redLayer != null) {
/* 467 */       width = this.redLayer.getWidth();
/* 468 */       height = this.redLayer.getHeight();
/*     */     }
/*     */     else
/*     */     {
/* 469 */       if (this.greenLayer != null) {
/* 470 */         width = this.greenLayer.getWidth();
/* 471 */         height = this.greenLayer.getHeight();
/*     */       }
/*     */       else
/*     */       {
/* 472 */         if (this.blueLayer != null) {
/* 473 */           width = this.blueLayer.getWidth();
/* 474 */           height = this.blueLayer.getHeight();
/*     */         }
/*     */         else
/*     */         {
/*     */           return;
/*     */         }
/*     */       }
/*     */     }
/* 478 */     BufferedImage img = new BufferedImage(width, height, 1);
/* 479 */     boolean b = false;
/*     */     boolean g;
/* 480 */     boolean r = g = b;
/* 481 */     if ((this.redLayer != null) && (this.showRedLayer.isSelected()))
/* 482 */       r = true;
/* 483 */     if ((this.greenLayer != null) && (this.showGreenLayer.isSelected()))
/* 484 */       g = true;
/* 485 */     if ((this.blueLayer != null) && (this.showBlueLayer.isSelected()))
/* 486 */       b = true;
/* 487 */     for (int i = 0; i < width; i++) {
/* 488 */       for (int j = 0; j < height; j++) {
/* 489 */         int rgb = 0;
/* 490 */         if (r)
/* 491 */           rgb += this.redLayer.getRGB(i, j);
/* 492 */         if (g)
/* 493 */           rgb += this.greenLayer.getRGB(i, j);
/* 494 */         if (b)
/* 495 */           rgb += this.blueLayer.getRGB(i, j);
/* 496 */         img.setRGB(i, j, rgb);
/*     */       }
/*     */     }
/* 499 */     this.label.setImage(img);
/*     */   }
/*     */ 
/*     */   private void subtractImages() {
/* 503 */     if ((this.sub1 == null) || (this.sub2 == null)) {
/* 504 */       return;
/*     */     }
/* 506 */     BufferedImage img = new BufferedImage(this.sub1.getWidth(), this.sub1.getHeight(), 1);
/* 507 */     for (int i = 0; i < img.getWidth(); i++) {
/* 508 */       for (int j = 0; j < img.getHeight(); j++) {
/* 509 */         int[] rgb1 = ColorTools.rgb(this.sub1.getRGB(i, j));
/* 510 */         int[] rgb2 = ColorTools.rgb(this.sub2.getRGB(i, j));
/* 511 */         int r = 0; int g = 0; int b = 0;
/* 512 */         int numColors = 0;
/* 513 */         if (this.subRed.isSelected()) {
/* 514 */           r = rgb1[0] - rgb2[0];
/* 515 */           numColors++;
/*     */         }
/* 517 */         if (this.subGreen.isSelected()) {
/* 518 */           g = rgb1[1] - rgb2[1];
/* 519 */           numColors++;
/*     */         }
/* 521 */         if (this.subBlue.isSelected()) {
/* 522 */           b = rgb1[2] - rgb2[2];
/* 523 */           numColors++;
/*     */         }
/* 525 */         if (numColors == 0)
/* 526 */           return;
/* 527 */         if (this.subGray.isSelected())
/* 528 */           r = g = b = (r + g + b) / numColors;
/* 529 */         int rgb = ColorTools.toRGB(new int[] { r, g, b });
/* 530 */         img.setRGB(i, j, rgb);
/*     */       }
/*     */     }
/* 533 */     this.label.setImage(img);
/*     */   }
/*     */   private void averageImages() {
/* 536 */     int num = this.avgImages.size();
/* 537 */     if (num == 0)
/* 538 */       return;
/* 539 */     BufferedImage img = new BufferedImage(((BufferedImage)this.avgImages.get(0)).getWidth(), ((BufferedImage)this.avgImages.get(0)).getHeight(), 1);
/* 540 */     for (int i = 0; i < ((BufferedImage)this.avgImages.get(0)).getWidth(); i++) {
/* 541 */       for (int j = 0; j < ((BufferedImage)this.avgImages.get(0)).getHeight(); j++) {
/* 542 */         int r = 0; int g = 0; int b = 0;
/* 543 */         for (int k = 0; k < num; k++) {
/* 544 */           int[] rgb = ColorTools.rgb(((BufferedImage)this.avgImages.get(k)).getRGB(i, j));
/* 545 */           r += rgb[0];
/* 546 */           g += rgb[1];
/* 547 */           b += rgb[2];
/*     */         }
/* 549 */         img.setRGB(i, j, ColorTools.toRGB(new int[] { r / num, g / num, b / num }));
/*     */       }
/*     */     }
/* 552 */     this.label.setImage(img);
/*     */   }
/*     */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.dialogs.CombineImagesDialog
 * JD-Core Version:    0.6.2
 */