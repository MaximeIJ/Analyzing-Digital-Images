/*     */ package org.gss.adi;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.ItemEvent;
/*     */ import java.awt.event.ItemListener;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.PrintStream;
/*     */ import javax.swing.ButtonGroup;
/*     */ import javax.swing.DefaultComboBoxModel;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JRadioButton;
/*     */ import javax.swing.JSlider;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.border.LineBorder;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
/*     */ import org.gss.adi.tools.ColorEnhances;
/*     */ 
/*     */ public class EnhanceColorsPanel extends ImagePanel
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private JTextField txtEnhanceRed;
/*     */   private JTextField txtMin;
/*     */   private JTextField txtMax;
/*     */   private JTextField textField;
/*     */   private JTextField textField_1;
/*     */   private JTextField txtEnhanceGreen;
/*     */   private JTextField textField_3;
/*     */   private JTextField textField_4;
/*     */   private JTextField txtEnhanceBlue;
/*     */   private Entrance entrance;
/*     */   private JComboBox comboBox;
/*     */   private JRadioButton redLimit;
/*     */   private JRadioButton redOff;
/*     */   private JRadioButton redStretch;
/*     */   private JCheckBox redInvert;
/*     */   private JSlider redMax;
/*     */   private JSlider redMin;
/*     */   private JSlider greenMax;
/*     */   private JRadioButton greenStretch;
/*     */   private JRadioButton greenLimit;
/*     */   private JRadioButton greenOff;
/*     */   private JSlider greenMin;
/*     */   private JCheckBox greenInvert;
/*     */   private JSlider blueMax;
/*     */   private JRadioButton blueStretch;
/*     */   private JRadioButton blueLimit;
/*     */   private JRadioButton blueOff;
/*     */   private JCheckBox blueInvert;
/*     */   private JSlider blueMin;
/*     */   private JLabel redColorDist;
/*     */   private JLabel greenColorDist;
/*     */   private JLabel blueColorDist;
/*     */   private JTextField rmin;
/*     */   private JTextField rmax;
/*     */   private JTextField gmin;
/*     */   private JTextField gmax;
/*     */   private JTextField bmin;
/*     */   private JTextField bmax;
/*     */ 
/*     */   public EnhanceColorsPanel(Entrance ent)
/*     */   {
/*  86 */     super(ent, false);
/*  87 */     this.entrance = ent;
/*  88 */     if (this.entrance.getImage() == null) {
/*  89 */       return;
/*     */     }
/*  91 */     setLayout(null);
/*  92 */     setup();
/*  93 */     this.label.setImage(this.entrance.getImage());
/*  94 */     setColorDists();
/*     */   }
/*     */   public void updateTool() {
/*     */   }
/*     */ 
/*     */   public void updatePic() {
/* 100 */     super.updatePic();
/* 101 */     setColorDists();
/*     */   }
/*     */ 
/*     */   protected void closingSequence() {
/* 105 */     this.label.qualityZoom(100);
/* 106 */     comboBoxChange();
/* 107 */     if (this.label.getZoomedTool() == null)
/* 108 */       this.entrance.setEnhancedImage(this.label.getZoomedOriginal());
/*     */     else
/* 110 */       this.entrance.setEnhancedImage(this.label.getZoomedTool());
/*     */   }
/*     */ 
/*     */   byte getType()
/*     */   {
/* 115 */     return 1;
/*     */   }
/*     */ 
/*     */   void slideComplete() {
/* 119 */     super.slideComplete();
/* 120 */     comboBoxChange();
/*     */   }
/*     */ 
/*     */   private void setColorDists()
/*     */   {
/* 128 */     BufferedImage[] dists = ColorEnhances.getColorDistributions(this.label.getZoomedOriginal());
/* 129 */     this.redColorDist.setIcon(new ImageIcon(dists[0]));
/* 130 */     this.greenColorDist.setIcon(new ImageIcon(dists[1]));
/* 131 */     this.blueColorDist.setIcon(new ImageIcon(dists[2]));
/*     */   }
/*     */ 
/*     */   private void enhance()
/*     */   {
/* 138 */     long startTime = System.currentTimeMillis();
/*     */ 
/* 140 */     BufferedImage enhanced = ZoomPanLabel.resize(this.slider.getValue(), invert(off(limit(stretch(this.label.getOriginal())))));
/* 141 */     System.out.println("new enhancement took " + (System.currentTimeMillis() - startTime) + " milliseconds to complete.");
/* 142 */     this.label.setZoomedOriginal(enhanced);
/*     */   }
/*     */ 
/*     */   private BufferedImage invert(BufferedImage img)
/*     */   {
/* 150 */     boolean[] colors = new boolean[3];
/* 151 */     if (this.redInvert.isSelected())
/* 152 */       colors[0] = true;
/*     */     else
/* 154 */       colors[0] = false;
/* 155 */     if (this.greenInvert.isSelected())
/* 156 */       colors[1] = true;
/*     */     else
/* 158 */       colors[1] = false;
/* 159 */     if (this.blueInvert.isSelected())
/* 160 */       colors[2] = true;
/*     */     else
/* 162 */       colors[2] = false;
/* 163 */     return ColorEnhances.newInvert(colors, img);
/*     */   }
/*     */ 
/*     */   private BufferedImage limit(BufferedImage img)
/*     */   {
/* 171 */     int[] mins = new int[3];
/* 172 */     int[] maxs = new int[3];
/* 173 */     if (this.redLimit.isSelected()) {
/* 174 */       mins[0] = this.redMin.getValue();
/* 175 */       maxs[0] = this.redMax.getValue();
/*     */     } else {
/* 177 */       mins[0] = 0;
/* 178 */       maxs[0] = 256;
/*     */     }
/* 180 */     if (this.greenLimit.isSelected()) {
/* 181 */       mins[1] = this.greenMin.getValue();
/* 182 */       maxs[1] = this.greenMax.getValue();
/*     */     } else {
/* 184 */       mins[1] = 0;
/* 185 */       maxs[1] = 256;
/*     */     }
/* 187 */     if (this.blueLimit.isSelected()) {
/* 188 */       mins[2] = this.blueMin.getValue();
/* 189 */       maxs[2] = this.blueMax.getValue();
/*     */     } else {
/* 191 */       mins[2] = 0;
/* 192 */       maxs[2] = 256;
/*     */     }
/* 194 */     return ColorEnhances.newLimitColor(mins, maxs, img);
/*     */   }
/*     */ 
/*     */   private BufferedImage stretch(BufferedImage img)
/*     */   {
/* 202 */     int[] mins = new int[3];
/* 203 */     int[] maxs = new int[3];
/* 204 */     if (this.redStretch.isSelected()) {
/* 205 */       mins[0] = this.redMin.getValue();
/* 206 */       maxs[0] = this.redMax.getValue();
/*     */     } else {
/* 208 */       mins[0] = 0;
/* 209 */       maxs[0] = 256;
/*     */     }
/* 211 */     if (this.greenStretch.isSelected()) {
/* 212 */       mins[1] = this.greenMin.getValue();
/* 213 */       maxs[1] = this.greenMax.getValue();
/*     */     } else {
/* 215 */       mins[1] = 0;
/* 216 */       maxs[1] = 256;
/*     */     }
/* 218 */     if (this.blueStretch.isSelected()) {
/* 219 */       mins[2] = this.blueMin.getValue();
/* 220 */       maxs[2] = this.blueMax.getValue();
/*     */     } else {
/* 222 */       mins[2] = 0;
/* 223 */       maxs[2] = 256;
/*     */     }
/* 225 */     return ColorEnhances.newStretchColor(mins, maxs, img);
/*     */   }
/*     */ 
/*     */   private BufferedImage off(BufferedImage img)
/*     */   {
/* 231 */     int size = 0;
/* 232 */     if (this.redOff.isSelected())
/* 233 */       size++;
/* 234 */     if (this.greenOff.isSelected())
/* 235 */       size++;
/* 236 */     if (this.blueOff.isSelected()) {
/* 237 */       size++;
/*     */     }
/* 239 */     byte[] color = new byte[size];
/* 240 */     int[] start = new int[size];
/* 241 */     int[] end = new int[size];
/* 242 */     size = 0;
/* 243 */     if (this.redOff.isSelected()) {
/* 244 */       color[size] = 0;
/* 245 */       start[size] = this.redMin.getValue();
/* 246 */       end[size] = this.redMax.getValue();
/* 247 */       size++;
/* 248 */     }if (this.greenOff.isSelected()) {
/* 249 */       color[size] = 1;
/* 250 */       start[size] = this.greenMin.getValue();
/* 251 */       end[size] = this.greenMax.getValue();
/* 252 */       size++;
/* 253 */     }if (this.blueOff.isSelected()) {
/* 254 */       color[size] = 2;
/* 255 */       start[size] = this.blueMin.getValue();
/* 256 */       end[size] = this.blueMax.getValue();
/* 257 */       size++;
/*     */     }
/* 259 */     return ColorEnhances.newOffColor(color, img);
/*     */   }
/*     */ 
/*     */   private void comboBoxChange()
/*     */   {
/* 267 */     switch (this.comboBox.getSelectedIndex()) {
/*     */     case 0:
/* 269 */       enhance();
/* 270 */       break;
/*     */     case 1:
/* 272 */       this.label.setZoomedOriginal(ColorEnhances.grayScale((byte)0, this.label.getZoomedOriginal()));
/* 273 */       break;
/*     */     case 2:
/* 275 */       this.label.setZoomedTool(ColorEnhances.grayScale((byte)1, this.label.getZoomedOriginal()));
/* 276 */       break;
/*     */     case 3:
/* 278 */       this.label.setZoomedTool(ColorEnhances.grayScale((byte)2, this.label.getZoomedOriginal()));
/* 279 */       break;
/*     */     case 4:
/* 281 */       this.label.setZoomedTool(ColorEnhances.grayScale((byte)-1, this.label.getZoomedOriginal()));
/* 282 */       break;
/*     */     case 5:
/* 284 */       this.label.setZoomedTool(ColorEnhances.normalize(new byte[] { 0, 1 }, this.label.getZoomedOriginal()));
/* 285 */       break;
/*     */     case 6:
/* 287 */       this.label.setZoomedTool(ColorEnhances.normalize(new byte[] { 0, 2 }, this.label.getZoomedOriginal()));
/* 288 */       break;
/*     */     case 7:
/* 290 */       this.label.setZoomedTool(ColorEnhances.normalize(new byte[] { 1, 2 }, this.label.getZoomedOriginal()));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void setup() {
/* 295 */     this.rmin = new JTextField("0%");
/* 296 */     this.rmin.setHorizontalAlignment(11);
/* 297 */     this.rmin.setEditable(false);
/* 298 */     this.rmin.setColumns(10);
/* 299 */     this.rmin.setBorder(null);
/* 300 */     this.rmin.setOpaque(false);
/* 301 */     this.rmin.setBounds(0, 242, 43, 20);
/* 302 */     add(this.rmin);
/* 303 */     this.rmax = new JTextField("100%");
/* 304 */     this.rmax.setHorizontalAlignment(11);
/* 305 */     this.rmax.setEditable(false);
/* 306 */     this.rmax.setColumns(10);
/* 307 */     this.rmax.setBorder(null);
/* 308 */     this.rmax.setOpaque(false);
/* 309 */     this.rmax.setBounds(0, 307, 43, 32);
/* 310 */     add(this.rmax);
/* 311 */     this.gmin = new JTextField("0%");
/* 312 */     this.gmin.setHorizontalAlignment(11);
/* 313 */     this.gmin.setEditable(false);
/* 314 */     this.gmin.setColumns(10);
/* 315 */     this.gmin.setBorder(null);
/* 316 */     this.gmin.setOpaque(false);
/* 317 */     this.gmin.setBounds(0, 374, 43, 20);
/* 318 */     add(this.gmin);
/* 319 */     this.gmax = new JTextField("100%");
/* 320 */     this.gmax.setHorizontalAlignment(11);
/* 321 */     this.gmax.setEditable(false);
/* 322 */     this.gmax.setColumns(10);
/* 323 */     this.gmax.setBorder(null);
/* 324 */     this.gmax.setOpaque(false);
/* 325 */     this.gmax.setBounds(0, 439, 43, 20);
/* 326 */     add(this.gmax);
/* 327 */     this.bmin = new JTextField("0%");
/* 328 */     this.bmin.setHorizontalAlignment(11);
/* 329 */     this.bmin.setEditable(false);
/* 330 */     this.bmin.setColumns(10);
/* 331 */     this.bmin.setBorder(null);
/* 332 */     this.bmin.setOpaque(false);
/* 333 */     this.bmin.setBounds(0, 506, 43, 20);
/* 334 */     add(this.bmin);
/* 335 */     this.bmax = new JTextField("100%");
/* 336 */     this.bmax.setHorizontalAlignment(11);
/* 337 */     this.bmax.setEditable(false);
/* 338 */     this.bmax.setColumns(10);
/* 339 */     this.bmax.setBorder(null);
/* 340 */     this.bmax.setOpaque(false);
/* 341 */     this.bmax.setBounds(0, 571, 43, 20);
/* 342 */     add(this.bmax);
/* 343 */     JTextArea txtrModifyTheColors = new JTextArea();
/* 344 */     txtrModifyTheColors.setOpaque(false);
/* 345 */     txtrModifyTheColors.setEditable(false);
/* 346 */     txtrModifyTheColors.setFont(new Font("SansSerif", 1, 14));
/* 347 */     txtrModifyTheColors.setText("Modify the colors of a digital image so it easier to see spatial and spectral (color) relationships.");
/* 348 */     txtrModifyTheColors.setWrapStyleWord(true);
/* 349 */     txtrModifyTheColors.setLineWrap(true);
/* 350 */     txtrModifyTheColors.setBounds(10, 5, 326, 61);
/* 351 */     add(txtrModifyTheColors);
/*     */ 
/* 353 */     JTextArea txtrProcessingLargeImages = new JTextArea();
/* 354 */     txtrProcessingLargeImages.setFont(new Font("SansSerif", 0, 13));
/* 355 */     txtrProcessingLargeImages.setLineWrap(true);
/* 356 */     txtrProcessingLargeImages.setWrapStyleWord(true);
/* 357 */     txtrProcessingLargeImages.setText("Processing large images takes time – be patient or trim the image to make it smaller.");
/* 358 */     txtrProcessingLargeImages.setOpaque(false);
/* 359 */     txtrProcessingLargeImages.setBounds(10, 70, 318, 40);
/* 360 */     add(txtrProcessingLargeImages);
/*     */ 
/* 362 */     JTextArea txtrSelectPredefined = new JTextArea();
/* 363 */     txtrSelectPredefined.setEditable(false);
/* 364 */     txtrSelectPredefined.setOpaque(false);
/* 365 */     txtrSelectPredefined.setText("1) Select Pre-defined Enhancement");
/* 366 */     txtrSelectPredefined.setFont(new Font("SansSerif", 0, 13));
/* 367 */     txtrSelectPredefined.setBounds(10, 110, 318, 22);
/* 368 */     add(txtrSelectPredefined);
/*     */ 
/* 370 */     this.comboBox = new JComboBox();
/* 371 */     this.comboBox.setModel(new DefaultComboBoxModel(new String[] { "Original Image", "Gray Image of Red Intensities", 
/* 372 */       "Gray Image of Green Intensities", "Gray Image of Blue Intensities", "Gray Image of Average Intensities", 
/* 373 */       "Red vs Green (normalized)", "Red vs Blue (normalized)", "Green vs Blue (normalized)" }));
/* 374 */     this.comboBox.setBounds(10, 132, 306, 20);
/* 375 */     add(this.comboBox);
/*     */ 
/* 377 */     JTextArea txtrCustomEnhancement = new JTextArea();
/* 378 */     txtrCustomEnhancement.setText("2) Custom Enhancement");
/* 379 */     txtrCustomEnhancement.setOpaque(false);
/* 380 */     txtrCustomEnhancement.setFont(new Font("SansSerif", 0, 13));
/* 381 */     txtrCustomEnhancement.setEditable(false);
/* 382 */     txtrCustomEnhancement.setBounds(10, 154, 318, 22);
/* 383 */     add(txtrCustomEnhancement);
/*     */ 
/* 385 */     this.txtEnhanceRed = new JTextField();
/* 386 */     this.txtEnhanceRed.setBorder(null);
/* 387 */     this.txtEnhanceRed.setOpaque(false);
/* 388 */     this.txtEnhanceRed.setEditable(false);
/* 389 */     this.txtEnhanceRed.setFont(new Font("Tahoma", 2, 11));
/* 390 */     this.txtEnhanceRed.setText("Enhance Red");
/* 391 */     this.txtEnhanceRed.setHorizontalAlignment(0);
/* 392 */     this.txtEnhanceRed.setBounds(10, 176, 306, 20);
/* 393 */     add(this.txtEnhanceRed);
/* 394 */     this.txtEnhanceRed.setColumns(10);
/*     */ 
/* 396 */     this.redInvert = new JCheckBox("Invert");
/* 397 */     this.redInvert.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 399 */         EnhanceColorsPanel.this.enhance();
/* 400 */         EnhanceColorsPanel.this.comboBox.setSelectedIndex(0);
/*     */       }
/*     */     });
/* 403 */     this.redInvert.setFont(new Font("SansSerif", 0, 10));
/* 404 */     this.redInvert.setBounds(0, 192, 63, 23);
/* 405 */     add(this.redInvert);
/*     */ 
/* 407 */     this.redOff = new JRadioButton("Off");
/* 408 */     this.redOff.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent arg0) {
/* 410 */         EnhanceColorsPanel.this.enhance();
/* 411 */         EnhanceColorsPanel.this.comboBox.setSelectedIndex(0);
/*     */       }
/*     */     });
/* 414 */     this.redOff.setFont(new Font("SansSerif", 0, 11));
/* 415 */     this.redOff.setBounds(63, 194, 70, 23);
/* 416 */     add(this.redOff);
/*     */ 
/* 418 */     this.redLimit = new JRadioButton("Limit");
/* 419 */     this.redLimit.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 421 */         EnhanceColorsPanel.this.enhance();
/* 422 */         EnhanceColorsPanel.this.comboBox.setSelectedIndex(0);
/*     */       }
/*     */     });
/* 425 */     this.redLimit.setSelected(true);
/* 426 */     this.redLimit.setFont(new Font("SansSerif", 0, 11));
/* 427 */     this.redLimit.setBounds(135, 194, 82, 23);
/* 428 */     add(this.redLimit);
/*     */ 
/* 430 */     this.redStretch = new JRadioButton("Stretch");
/* 431 */     this.redStretch.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 433 */         EnhanceColorsPanel.this.enhance();
/* 434 */         EnhanceColorsPanel.this.comboBox.setSelectedIndex(0);
/*     */       }
/*     */     });
/* 437 */     this.redStretch.setFont(new Font("SansSerif", 0, 11));
/* 438 */     this.redStretch.setBounds(219, 194, 81, 23);
/* 439 */     add(this.redStretch);
/*     */ 
/* 442 */     ButtonGroup red = new ButtonGroup();
/* 443 */     red.add(this.redOff);
/* 444 */     red.add(this.redLimit);
/* 445 */     red.add(this.redStretch);
/*     */ 
/* 447 */     this.redMax = new JSlider();
/* 448 */     this.redMax.setMinorTickSpacing(1);
/* 449 */     this.redMax.setMaximum(256);
/* 450 */     this.redMax.setPaintTrack(false);
/* 451 */     this.redMax.setMajorTickSpacing(10);
/* 452 */     this.redMax.setSnapToTicks(true);
/* 453 */     this.redMax.setValue(256);
/* 454 */     this.redMax.setBounds(46, 287, 269, 20);
/* 455 */     add(this.redMax);
/*     */ 
/* 457 */     this.redMin = new JSlider();
/* 458 */     this.redMin.setMinorTickSpacing(1);
/* 459 */     this.redMin.setMaximum(256);
/* 460 */     this.redMin.setPaintTrack(false);
/* 461 */     this.redMin.setMajorTickSpacing(10);
/* 462 */     this.redMin.setSnapToTicks(true);
/* 463 */     this.redMin.setValue(0);
/* 464 */     this.redMin.setBounds(46, 222, 269, 20);
/* 465 */     add(this.redMin);
/*     */ 
/* 467 */     this.redMin.addChangeListener(new ChangeListener() {
/*     */       public void stateChanged(ChangeEvent e) {
/* 469 */         if (EnhanceColorsPanel.this.redMin.getValue() > EnhanceColorsPanel.this.redMax.getValue()) {
/* 470 */           EnhanceColorsPanel.this.redMin.setValue(EnhanceColorsPanel.this.redMax.getValue());
/* 471 */           EnhanceColorsPanel.this.redMin.repaint();
/*     */         }
/* 473 */         EnhanceColorsPanel.this.rmin.setText(100 * EnhanceColorsPanel.this.redMin.getValue() / 256 + "%");
/* 474 */         EnhanceColorsPanel.this.enhance();
/* 475 */         EnhanceColorsPanel.this.comboBox.setSelectedIndex(0);
/*     */       }
/*     */     });
/* 478 */     this.redMax.addChangeListener(new ChangeListener() {
/*     */       public void stateChanged(ChangeEvent e) {
/* 480 */         if (EnhanceColorsPanel.this.redMax.getValue() < EnhanceColorsPanel.this.redMin.getValue()) {
/* 481 */           EnhanceColorsPanel.this.redMax.setValue(EnhanceColorsPanel.this.redMin.getValue());
/* 482 */           EnhanceColorsPanel.this.redMax.repaint();
/*     */         }
/* 484 */         EnhanceColorsPanel.this.rmax.setText(100 * EnhanceColorsPanel.this.redMax.getValue() / 256 + "%");
/* 485 */         EnhanceColorsPanel.this.enhance();
/* 486 */         EnhanceColorsPanel.this.comboBox.setSelectedIndex(0);
/*     */       }
/*     */     });
/* 490 */     this.redColorDist = new JLabel();
/* 491 */     this.redColorDist.setBorder(new LineBorder(new Color(0, 0, 0)));
/* 492 */     this.redColorDist.setBounds(53, 242, 255, 45);
/* 493 */     add(this.redColorDist);
/*     */ 
/* 495 */     this.txtMin = new JTextField();
/* 496 */     this.txtMin.setHorizontalAlignment(11);
/* 497 */     this.txtMin.setEditable(false);
/* 498 */     this.txtMin.setBorder(null);
/* 499 */     this.txtMin.setText("min");
/* 500 */     this.txtMin.setOpaque(false);
/* 501 */     this.txtMin.setBounds(12, 222, 31, 20);
/* 502 */     add(this.txtMin);
/* 503 */     this.txtMin.setColumns(10);
/*     */ 
/* 505 */     this.txtMax = new JTextField();
/* 506 */     this.txtMax.setHorizontalAlignment(11);
/* 507 */     this.txtMax.setText("max");
/* 508 */     this.txtMax.setEditable(false);
/* 509 */     this.txtMax.setColumns(10);
/* 510 */     this.txtMax.setBorder(null);
/* 511 */     this.txtMax.setOpaque(false);
/* 512 */     this.txtMax.setBounds(12, 287, 31, 20);
/* 513 */     add(this.txtMax);
/*     */ 
/* 515 */     this.greenMax = new JSlider();
/* 516 */     this.greenMax.setMinorTickSpacing(1);
/* 517 */     this.greenMax.setMaximum(256);
/* 518 */     this.greenMax.setPaintTrack(false);
/* 519 */     this.greenMax.setMajorTickSpacing(10);
/* 520 */     this.greenMax.setSnapToTicks(true);
/* 521 */     this.greenMax.setValue(256);
/* 522 */     this.greenMax.setBounds(46, 419, 269, 20);
/* 523 */     add(this.greenMax);
/*     */ 
/* 525 */     this.greenColorDist = new JLabel();
/* 526 */     this.greenColorDist.setBorder(new LineBorder(new Color(0, 0, 0)));
/* 527 */     this.greenColorDist.setBounds(53, 374, 255, 45);
/* 528 */     add(this.greenColorDist);
/*     */ 
/* 530 */     this.textField = new JTextField();
/* 531 */     this.textField.setHorizontalAlignment(11);
/* 532 */     this.textField.setText("min");
/* 533 */     this.textField.setEditable(false);
/* 534 */     this.textField.setColumns(10);
/* 535 */     this.textField.setBorder(null);
/* 536 */     this.textField.setOpaque(false);
/* 537 */     this.textField.setBounds(12, 354, 31, 20);
/* 538 */     add(this.textField);
/*     */ 
/* 540 */     this.textField_1 = new JTextField();
/* 541 */     this.textField_1.setHorizontalAlignment(11);
/* 542 */     this.textField_1.setText("max");
/* 543 */     this.textField_1.setEditable(false);
/* 544 */     this.textField_1.setColumns(10);
/* 545 */     this.textField_1.setBorder(null);
/* 546 */     this.textField_1.setOpaque(false);
/* 547 */     this.textField_1.setBounds(12, 419, 31, 20);
/* 548 */     add(this.textField_1);
/*     */ 
/* 550 */     this.greenStretch = new JRadioButton("Stretch");
/* 551 */     this.greenStretch.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 553 */         EnhanceColorsPanel.this.enhance();
/* 554 */         EnhanceColorsPanel.this.comboBox.setSelectedIndex(0);
/*     */       }
/*     */     });
/* 557 */     this.greenStretch.setFont(new Font("SansSerif", 0, 11));
/* 558 */     this.greenStretch.setBounds(219, 326, 81, 23);
/* 559 */     add(this.greenStretch);
/*     */ 
/* 561 */     this.greenLimit = new JRadioButton("Limit");
/* 562 */     this.greenLimit.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 564 */         EnhanceColorsPanel.this.enhance();
/* 565 */         EnhanceColorsPanel.this.comboBox.setSelectedIndex(0);
/*     */       }
/*     */     });
/* 568 */     this.greenLimit.setSelected(true);
/* 569 */     this.greenLimit.setFont(new Font("SansSerif", 0, 11));
/* 570 */     this.greenLimit.setBounds(135, 326, 82, 23);
/* 571 */     add(this.greenLimit);
/*     */ 
/* 573 */     this.txtEnhanceGreen = new JTextField();
/* 574 */     this.txtEnhanceGreen.setText("Enhance Green");
/* 575 */     this.txtEnhanceGreen.setOpaque(false);
/* 576 */     this.txtEnhanceGreen.setHorizontalAlignment(0);
/* 577 */     this.txtEnhanceGreen.setFont(new Font("Tahoma", 2, 11));
/* 578 */     this.txtEnhanceGreen.setEditable(false);
/* 579 */     this.txtEnhanceGreen.setColumns(10);
/* 580 */     this.txtEnhanceGreen.setBorder(null);
/* 581 */     this.txtEnhanceGreen.setBounds(10, 310, 306, 20);
/* 582 */     add(this.txtEnhanceGreen);
/*     */ 
/* 584 */     this.greenOff = new JRadioButton("Off");
/* 585 */     this.greenOff.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 587 */         EnhanceColorsPanel.this.enhance();
/* 588 */         EnhanceColorsPanel.this.comboBox.setSelectedIndex(0);
/*     */       }
/*     */     });
/* 591 */     this.greenOff.setFont(new Font("SansSerif", 0, 11));
/* 592 */     this.greenOff.setBounds(63, 326, 70, 23);
/* 593 */     add(this.greenOff);
/*     */ 
/* 596 */     ButtonGroup green = new ButtonGroup();
/* 597 */     green.add(this.greenOff);
/* 598 */     green.add(this.greenStretch);
/* 599 */     green.add(this.greenLimit);
/*     */ 
/* 601 */     this.greenInvert = new JCheckBox("Invert");
/* 602 */     this.greenInvert.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 604 */         EnhanceColorsPanel.this.enhance();
/* 605 */         EnhanceColorsPanel.this.comboBox.setSelectedIndex(0);
/*     */       }
/*     */     });
/* 608 */     this.greenInvert.setFont(new Font("SansSerif", 0, 11));
/* 609 */     this.greenInvert.setBounds(0, 325, 63, 23);
/* 610 */     add(this.greenInvert);
/*     */ 
/* 612 */     this.greenMin = new JSlider();
/* 613 */     this.greenMin.setMinorTickSpacing(1);
/* 614 */     this.greenMin.setMaximum(256);
/* 615 */     this.greenMin.setPaintTrack(false);
/* 616 */     this.greenMin.setMajorTickSpacing(10);
/* 617 */     this.greenMin.setSnapToTicks(true);
/* 618 */     this.greenMin.setValue(0);
/* 619 */     this.greenMin.setBounds(46, 354, 269, 20);
/* 620 */     add(this.greenMin);
/*     */ 
/* 622 */     this.greenMin.addChangeListener(new ChangeListener() {
/*     */       public void stateChanged(ChangeEvent e) {
/* 624 */         if (EnhanceColorsPanel.this.greenMin.getValue() > EnhanceColorsPanel.this.greenMax.getValue()) {
/* 625 */           EnhanceColorsPanel.this.greenMin.setValue(EnhanceColorsPanel.this.greenMax.getValue());
/* 626 */           EnhanceColorsPanel.this.greenMin.repaint();
/*     */         }
/* 628 */         EnhanceColorsPanel.this.gmin.setText(100 * EnhanceColorsPanel.this.greenMin.getValue() / 256 + "%");
/* 629 */         EnhanceColorsPanel.this.enhance();
/* 630 */         EnhanceColorsPanel.this.comboBox.setSelectedIndex(0);
/*     */       }
/*     */     });
/* 633 */     this.greenMax.addChangeListener(new ChangeListener() {
/*     */       public void stateChanged(ChangeEvent e) {
/* 635 */         if (EnhanceColorsPanel.this.greenMax.getValue() < EnhanceColorsPanel.this.greenMin.getValue()) {
/* 636 */           EnhanceColorsPanel.this.greenMax.setValue(EnhanceColorsPanel.this.greenMin.getValue());
/* 637 */           EnhanceColorsPanel.this.greenMax.repaint();
/*     */         }
/* 639 */         EnhanceColorsPanel.this.gmax.setText(100 * EnhanceColorsPanel.this.greenMax.getValue() / 256 + "%");
/* 640 */         EnhanceColorsPanel.this.enhance();
/* 641 */         EnhanceColorsPanel.this.comboBox.setSelectedIndex(0);
/*     */       }
/*     */     });
/* 645 */     this.blueMax = new JSlider();
/* 646 */     this.blueMax.setMinorTickSpacing(1);
/* 647 */     this.blueMax.setMaximum(256);
/* 648 */     this.blueMax.setPaintTrack(false);
/* 649 */     this.blueMax.setMajorTickSpacing(10);
/* 650 */     this.blueMax.setSnapToTicks(true);
/* 651 */     this.blueMax.setValue(256);
/* 652 */     this.blueMax.setBounds(46, 551, 269, 20);
/* 653 */     add(this.blueMax);
/*     */ 
/* 655 */     this.blueColorDist = new JLabel();
/* 656 */     this.blueColorDist.setBorder(new LineBorder(new Color(0, 0, 0)));
/* 657 */     this.blueColorDist.setBounds(53, 506, 255, 45);
/* 658 */     add(this.blueColorDist);
/*     */ 
/* 660 */     this.textField_3 = new JTextField();
/* 661 */     this.textField_3.setHorizontalAlignment(11);
/* 662 */     this.textField_3.setText("min");
/* 663 */     this.textField_3.setEditable(false);
/* 664 */     this.textField_3.setColumns(10);
/* 665 */     this.textField_3.setBorder(null);
/* 666 */     this.textField_3.setOpaque(false);
/* 667 */     this.textField_3.setBounds(12, 486, 31, 20);
/* 668 */     add(this.textField_3);
/*     */ 
/* 670 */     this.textField_4 = new JTextField();
/* 671 */     this.textField_4.setHorizontalAlignment(11);
/* 672 */     this.textField_4.setText("max");
/* 673 */     this.textField_4.setEditable(false);
/* 674 */     this.textField_4.setColumns(10);
/* 675 */     this.textField_4.setBorder(null);
/* 676 */     this.textField_4.setOpaque(false);
/* 677 */     this.textField_4.setBounds(12, 551, 31, 20);
/* 678 */     add(this.textField_4);
/*     */ 
/* 682 */     this.blueStretch = new JRadioButton("Stretch");
/* 683 */     this.blueStretch.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 685 */         EnhanceColorsPanel.this.enhance();
/* 686 */         EnhanceColorsPanel.this.comboBox.setSelectedIndex(0);
/*     */       }
/*     */     });
/* 689 */     this.blueStretch.setFont(new Font("SansSerif", 0, 11));
/* 690 */     this.blueStretch.setBounds(219, 458, 81, 23);
/* 691 */     add(this.blueStretch);
/*     */ 
/* 693 */     this.blueLimit = new JRadioButton("Limit");
/* 694 */     this.blueLimit.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 696 */         EnhanceColorsPanel.this.enhance();
/* 697 */         EnhanceColorsPanel.this.comboBox.setSelectedIndex(0);
/*     */       }
/*     */     });
/* 700 */     this.blueLimit.setSelected(true);
/* 701 */     this.blueLimit.setFont(new Font("SansSerif", 0, 11));
/* 702 */     this.blueLimit.setBounds(135, 458, 82, 23);
/* 703 */     add(this.blueLimit);
/*     */ 
/* 705 */     this.txtEnhanceBlue = new JTextField();
/* 706 */     this.txtEnhanceBlue.setText("Enhance Blue");
/* 707 */     this.txtEnhanceBlue.setOpaque(false);
/* 708 */     this.txtEnhanceBlue.setHorizontalAlignment(0);
/* 709 */     this.txtEnhanceBlue.setFont(new Font("Tahoma", 2, 11));
/* 710 */     this.txtEnhanceBlue.setEditable(false);
/* 711 */     this.txtEnhanceBlue.setColumns(10);
/* 712 */     this.txtEnhanceBlue.setBorder(null);
/* 713 */     this.txtEnhanceBlue.setBounds(8, 442, 306, 20);
/* 714 */     add(this.txtEnhanceBlue);
/*     */ 
/* 716 */     this.blueOff = new JRadioButton("Off");
/* 717 */     this.blueOff.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 719 */         EnhanceColorsPanel.this.enhance();
/* 720 */         EnhanceColorsPanel.this.comboBox.setSelectedIndex(0);
/*     */       }
/*     */     });
/* 723 */     this.blueOff.setFont(new Font("SansSerif", 0, 11));
/* 724 */     this.blueOff.setBounds(63, 458, 70, 23);
/* 725 */     add(this.blueOff);
/*     */ 
/* 728 */     ButtonGroup blue = new ButtonGroup();
/* 729 */     blue.add(this.blueOff);
/* 730 */     blue.add(this.blueStretch);
/* 731 */     blue.add(this.blueLimit);
/*     */ 
/* 733 */     this.blueInvert = new JCheckBox("Invert");
/* 734 */     this.blueInvert.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 736 */         EnhanceColorsPanel.this.enhance();
/* 737 */         EnhanceColorsPanel.this.comboBox.setSelectedIndex(0);
/*     */       }
/*     */     });
/* 740 */     this.blueInvert.setFont(new Font("SansSerif", 0, 11));
/* 741 */     this.blueInvert.setBounds(0, 458, 63, 23);
/* 742 */     add(this.blueInvert);
/*     */ 
/* 744 */     this.blueMin = new JSlider();
/* 745 */     this.blueMin.setMinorTickSpacing(1);
/* 746 */     this.blueMin.setMaximum(256);
/* 747 */     this.blueMin.setPaintTrack(false);
/* 748 */     this.blueMin.setMajorTickSpacing(10);
/* 749 */     this.blueMin.setSnapToTicks(true);
/* 750 */     this.blueMin.setValue(0);
/* 751 */     this.blueMin.setBounds(47, 486, 269, 20);
/* 752 */     add(this.blueMin);
/*     */ 
/* 754 */     this.blueMin.addChangeListener(new ChangeListener() {
/*     */       public void stateChanged(ChangeEvent e) {
/* 756 */         if (EnhanceColorsPanel.this.blueMin.getValue() > EnhanceColorsPanel.this.blueMax.getValue()) {
/* 757 */           EnhanceColorsPanel.this.blueMin.setValue(EnhanceColorsPanel.this.blueMax.getValue());
/* 758 */           EnhanceColorsPanel.this.blueMin.repaint();
/*     */         }
/* 760 */         EnhanceColorsPanel.this.bmin.setText(100 * EnhanceColorsPanel.this.blueMin.getValue() / 256 + "%");
/* 761 */         EnhanceColorsPanel.this.enhance();
/* 762 */         EnhanceColorsPanel.this.comboBox.setSelectedIndex(0);
/*     */       }
/*     */     });
/* 765 */     this.blueMax.addChangeListener(new ChangeListener() {
/*     */       public void stateChanged(ChangeEvent e) {
/* 767 */         if (EnhanceColorsPanel.this.blueMax.getValue() < EnhanceColorsPanel.this.blueMin.getValue()) {
/* 768 */           EnhanceColorsPanel.this.blueMax.setValue(EnhanceColorsPanel.this.blueMin.getValue());
/* 769 */           EnhanceColorsPanel.this.blueMax.repaint();
/*     */         }
/* 771 */         EnhanceColorsPanel.this.bmax.setText(100 * EnhanceColorsPanel.this.blueMax.getValue() / 256 + "%");
/* 772 */         EnhanceColorsPanel.this.enhance();
/* 773 */         EnhanceColorsPanel.this.comboBox.setSelectedIndex(0);
/*     */       }
/*     */     });
/* 777 */     JButton btnNewButton_1 = new JButton("Use Enhanced Image as Original Image");
/* 778 */     btnNewButton_1.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 780 */         EnhanceColorsPanel.this.label.zoom(100);
/* 781 */         EnhanceColorsPanel.this.entrance.setImage(EnhanceColorsPanel.this.label.getZoomedOriginal());
/* 782 */         EnhanceColorsPanel.this.label.setImage(EnhanceColorsPanel.this.entrance.getImage());
/* 783 */         EnhanceColorsPanel.this.setColorDists();
/* 784 */         EnhanceColorsPanel.this.redMax.setValue(256);
/* 785 */         EnhanceColorsPanel.this.redMin.setValue(0);
/* 786 */         EnhanceColorsPanel.this.redLimit.setSelected(true);
/* 787 */         EnhanceColorsPanel.this.redInvert.setSelected(false);
/* 788 */         EnhanceColorsPanel.this.greenMax.setValue(256);
/* 789 */         EnhanceColorsPanel.this.greenMin.setValue(0);
/* 790 */         EnhanceColorsPanel.this.greenLimit.setSelected(true);
/* 791 */         EnhanceColorsPanel.this.greenInvert.setSelected(false);
/* 792 */         EnhanceColorsPanel.this.blueMax.setValue(256);
/* 793 */         EnhanceColorsPanel.this.blueMin.setValue(0);
/* 794 */         EnhanceColorsPanel.this.blueLimit.setSelected(true);
/* 795 */         EnhanceColorsPanel.this.blueInvert.setSelected(false);
/* 796 */         EnhanceColorsPanel.this.comboBox.setSelectedIndex(0);
/*     */       }
/*     */     });
/* 799 */     btnNewButton_1.setBounds(24, 600, 272, 23);
/* 800 */     add(btnNewButton_1);
/* 801 */     this.comboBox.addItemListener(new ItemListener() {
/*     */       public void itemStateChanged(ItemEvent e) {
/* 803 */         EnhanceColorsPanel.this.comboBoxChange();
/*     */       }
/*     */     });
/*     */   }
/*     */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.EnhanceColorsPanel
 * JD-Core Version:    0.6.2
 */