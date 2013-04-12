/*     */ package org.gss.adi;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Point;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseListener;
/*     */ import java.awt.event.MouseMotionListener;
/*     */ import java.awt.image.BufferedImage;
/*     */ import javax.swing.ButtonGroup;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JRadioButton;
/*     */ import javax.swing.JSlider;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.border.LineBorder;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
/*     */ import org.gss.adi.dialogs.RelationshipMaskDialog;
/*     */ import org.gss.adi.tools.ColorEnhances;
/*     */ import org.gss.adi.tools.ColorMask;
/*     */ import org.gss.adi.tools.ColorTools;
/*     */ import org.gss.adi.tools.Measurement;
/*     */ 
/*     */ public class MaskColorsPanel extends ImagePanel
/*     */ {
/*     */   private static final long serialVersionUID = 878970959446009454L;
/*     */   private JTextField txtSelectImageTo;
/*     */   private JTextField textField;
/*     */   private JTextField textField_1;
/*     */   private JTextField textField_2;
/*     */   private JTextField textField_3;
/*     */   private JTextField textField_4;
/*     */   private JTextField textField_5;
/*     */   private JRadioButton rdbtnEnhanced;
/*     */   private JRadioButton rdbtnOriginal;
/*     */   private Entrance entrance;
/*     */   private JSlider redMax;
/*     */   private JSlider redMin;
/*     */   private JCheckBox chckbxRed;
/*     */   private JSlider greenMax;
/*     */   private JSlider greenMin;
/*     */   private JCheckBox chckbxGreen;
/*     */   private JSlider blueMax;
/*     */   private JSlider blueMin;
/*     */   private JCheckBox chckbxBlue;
/*     */   private JLabel redDist;
/*     */   private JLabel greenDist;
/*     */   private JLabel blueDist;
/*     */   private JButton btnNewButton_1;
/*  73 */   private JCheckBox showMask = new JCheckBox();
/*     */ 
/*  75 */   private Integer[] x = { Integer.valueOf(0), Integer.valueOf(0) };
/*  76 */   private Integer[] y = { Integer.valueOf(0), Integer.valueOf(0) };
/*     */   private JTextField pixnum;
/*     */   private JTextField area;
/*     */   private JTextField rmin;
/*     */   private JTextField rmax;
/*     */   private JTextField gmin;
/*     */   private JTextField gmax;
/*     */   private JTextField bmin;
/*     */   private JTextField bmax;
/*     */ 
/*     */   public MaskColorsPanel(Entrance e)
/*     */   {
/*  91 */     super(e, false);
/*  92 */     setLayout(null);
/*  93 */     this.entrance = e;
/*  94 */     if (this.entrance.getImage() == null) {
/*  95 */       return;
/*     */     }
/*  97 */     setup();
/*  98 */     BufferedImage enhanced = this.entrance.getEnhancedImage();
/*  99 */     if (enhanced != null) {
/* 100 */       this.label.setImage(enhanced);
/* 101 */       this.rdbtnEnhanced.setSelected(true);
/*     */     } else {
/* 103 */       this.label.setImage(this.entrance.getImage());
/*     */     }
/* 105 */     setColorDists();
/*     */ 
/* 107 */     this.entrance.getMenu().saveNewMask.setVisible(true);
/* 108 */     if (this.entrance.getMenu().applyMask.getItemCount() > 0)
/* 109 */       this.entrance.getMenu().applyMask.setVisible(true); 
/*     */   }
/*     */ 
/*     */   public void updateTool() {
/*     */   }
/*     */ 
/* 115 */   public void updatePic() { super.updatePic();
/* 116 */     setColorDists(); }
/*     */ 
/*     */   protected void closingSequence()
/*     */   {
/* 120 */     this.slider.setValue(100);
/* 121 */     this.showMask.setSelected(true);
/* 122 */     applyMask();
/* 123 */     this.entrance.setMaskedImage(this.label.getZoomedOriginal());
/*     */   }
/*     */ 
/*     */   byte getType() {
/* 127 */     return 2;
/*     */   }
/*     */ 
/*     */   private void setColorDists()
/*     */   {
/* 133 */     BufferedImage[] dists = ColorEnhances.getColorDistributions(this.label.getZoomedOriginal());
/* 134 */     this.redDist.setIcon(new ImageIcon(dists[0]));
/* 135 */     this.greenDist.setIcon(new ImageIcon(dists[1]));
/* 136 */     this.blueDist.setIcon(new ImageIcon(dists[2]));
/*     */   }
/*     */ 
/*     */   private void applyMask()
/*     */   {
/* 144 */     if (this.showMask.isSelected()) {
/* 145 */       int[] mins = new int[3];
/* 146 */       int[] maxs = new int[3];
/* 147 */       if (this.chckbxRed.isSelected()) {
/* 148 */         mins[0] = this.redMin.getValue();
/* 149 */         maxs[0] = this.redMax.getValue();
/*     */       } else {
/* 151 */         mins[0] = 0;
/* 152 */         maxs[0] = 256;
/*     */       }
/* 154 */       if (this.chckbxGreen.isSelected()) {
/* 155 */         mins[1] = this.greenMin.getValue();
/* 156 */         maxs[1] = this.greenMax.getValue();
/*     */       } else {
/* 158 */         mins[1] = 0;
/* 159 */         maxs[1] = 256;
/*     */       }
/* 161 */       if (this.chckbxBlue.isSelected()) {
/* 162 */         mins[2] = this.blueMin.getValue();
/* 163 */         maxs[2] = this.blueMax.getValue();
/*     */       } else {
/* 165 */         mins[2] = 0;
/* 166 */         maxs[2] = 256;
/*     */       }
/*     */ 
/* 169 */       Object[] o = ColorEnhances.newApplyMask(mins, maxs, this.label.getOriginal());
/* 170 */       this.label.setZoomedOriginal(ZoomPanLabel.resize(this.slider.getValue(), (BufferedImage)o[0]));
/* 171 */       this.pixnum.setText(((Integer)o[1]).toString() + " pixels masked");
/* 172 */       if (this.entrance.getMeasurement() != null)
/* 173 */         this.area.setText("Area: " + this.entrance.getMeasurement().measure(Double.valueOf(((Integer)o[1]).doubleValue()), false));
/*     */     }
/*     */   }
/*     */ 
/*     */   void slideComplete()
/*     */   {
/* 179 */     super.slideComplete();
/* 180 */     applyMask();
/*     */   }
/*     */ 
/*     */   public int getRMin() {
/* 184 */     return this.redMin.getValue();
/*     */   }
/*     */   public int getRMax() {
/* 187 */     return this.redMax.getValue();
/*     */   }
/*     */   public int getGMin() {
/* 190 */     return this.greenMin.getValue();
/*     */   }
/*     */   public int getGMax() {
/* 193 */     return this.greenMax.getValue();
/*     */   }
/*     */   public int getBMin() {
/* 196 */     return this.blueMin.getValue();
/*     */   }
/*     */   public int getBMax() {
/* 199 */     return this.blueMax.getValue();
/*     */   }
/*     */   public boolean getRed() {
/* 202 */     return this.chckbxRed.isSelected();
/*     */   }
/*     */   public boolean getGreen() {
/* 205 */     return this.chckbxGreen.isSelected();
/*     */   }
/*     */   public boolean getBlue() {
/* 208 */     return this.chckbxBlue.isSelected();
/*     */   }
/*     */ 
/*     */   public void applyMask(ColorMask mask)
/*     */   {
/* 214 */     this.redMin.setValue(mask.redMin);
/* 215 */     this.redMax.setValue(mask.redMax);
/* 216 */     this.greenMin.setValue(mask.greenMin);
/* 217 */     this.greenMax.setValue(mask.greenMax);
/* 218 */     this.blueMin.setValue(mask.blueMin);
/* 219 */     this.blueMax.setValue(mask.blueMax);
/* 220 */     this.chckbxRed.setSelected(mask.red);
/* 221 */     this.chckbxGreen.setSelected(mask.green);
/* 222 */     this.chckbxBlue.setSelected(mask.blue);
/* 223 */     applyMask();
/*     */   }
/*     */   private void setup() {
/* 226 */     this.rmin = new JTextField("0%");
/* 227 */     this.rmin.setHorizontalAlignment(11);
/* 228 */     this.rmin.setEditable(false);
/* 229 */     this.rmin.setColumns(10);
/* 230 */     this.rmin.setBorder(null);
/* 231 */     this.rmin.setOpaque(false);
/* 232 */     this.rmin.setBounds(0, 345, 43, 20);
/* 233 */     add(this.rmin);
/* 234 */     this.rmax = new JTextField("100%");
/* 235 */     this.rmax.setHorizontalAlignment(11);
/* 236 */     this.rmax.setEditable(false);
/* 237 */     this.rmax.setColumns(10);
/* 238 */     this.rmax.setBorder(null);
/* 239 */     this.rmax.setOpaque(false);
/* 240 */     this.rmax.setBounds(0, 375, 43, 32);
/* 241 */     add(this.rmax);
/* 242 */     this.gmin = new JTextField("0%");
/* 243 */     this.gmin.setHorizontalAlignment(11);
/* 244 */     this.gmin.setEditable(false);
/* 245 */     this.gmin.setColumns(10);
/* 246 */     this.gmin.setBorder(null);
/* 247 */     this.gmin.setOpaque(false);
/* 248 */     this.gmin.setBounds(0, 430, 43, 20);
/* 249 */     add(this.gmin);
/* 250 */     this.gmax = new JTextField("100%");
/* 251 */     this.gmax.setHorizontalAlignment(11);
/* 252 */     this.gmax.setEditable(false);
/* 253 */     this.gmax.setColumns(10);
/* 254 */     this.gmax.setBorder(null);
/* 255 */     this.gmax.setOpaque(false);
/* 256 */     this.gmax.setBounds(0, 460, 43, 20);
/* 257 */     add(this.gmax);
/* 258 */     this.bmin = new JTextField("0%");
/* 259 */     this.bmin.setHorizontalAlignment(11);
/* 260 */     this.bmin.setEditable(false);
/* 261 */     this.bmin.setColumns(10);
/* 262 */     this.bmin.setBorder(null);
/* 263 */     this.bmin.setOpaque(false);
/* 264 */     this.bmin.setBounds(0, 515, 43, 20);
/* 265 */     add(this.bmin);
/* 266 */     this.bmax = new JTextField("100%");
/* 267 */     this.bmax.setHorizontalAlignment(11);
/* 268 */     this.bmax.setEditable(false);
/* 269 */     this.bmax.setColumns(10);
/* 270 */     this.bmax.setBorder(null);
/* 271 */     this.bmax.setOpaque(false);
/* 272 */     this.bmax.setBounds(0, 545, 43, 20);
/* 273 */     add(this.bmax);
/* 274 */     JTextArea txtrSelectARange = new JTextArea();
/* 275 */     txtrSelectARange.setEditable(false);
/* 276 */     txtrSelectARange.setFont(new Font("SansSerif", 0, 13));
/* 277 */     txtrSelectARange.setText("Select a range of colors to highlight, or see which pixels meet color relationships between the pixels' red, green, and blue intensities. The masked image will be black and white, in which black pixels passed the color tests, white did not.  \r\nUse the masked image with the rectangle and polygon spatial tools to measure areas of features highlighted with the color masking tests. \r\nClicking and dragging on the image allows you to select a portion of the image's pixel values to use as the mask. Click again to reselect the values.");
/* 278 */     txtrSelectARange.setOpaque(false);
/* 279 */     txtrSelectARange.setWrapStyleWord(true);
/* 280 */     txtrSelectARange.setLineWrap(true);
/* 281 */     txtrSelectARange.setBounds(0, 0, 341, 231);
/* 282 */     add(txtrSelectARange);
/*     */ 
/* 284 */     this.txtSelectImageTo = new JTextField();
/* 285 */     this.txtSelectImageTo.setBorder(null);
/* 286 */     this.txtSelectImageTo.setOpaque(false);
/* 287 */     this.txtSelectImageTo.setEditable(false);
/* 288 */     this.txtSelectImageTo.setFont(new Font("Tahoma", 2, 11));
/* 289 */     this.txtSelectImageTo.setText("Select Image to Mask");
/* 290 */     this.txtSelectImageTo.setBounds(92, 242, 120, 20);
/* 291 */     add(this.txtSelectImageTo);
/* 292 */     this.txtSelectImageTo.setColumns(10);
/*     */ 
/* 294 */     this.rdbtnOriginal = new JRadioButton("Original");
/* 295 */     this.rdbtnOriginal.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent arg0) {
/* 297 */         MaskColorsPanel.this.label.setImage(MaskColorsPanel.this.entrance.getImage());
/* 298 */         MaskColorsPanel.this.setColorDists();
/*     */       }
/*     */     });
/* 301 */     this.rdbtnOriginal.setSelected(true);
/* 302 */     this.rdbtnOriginal.setBounds(60, 262, 92, 23);
/* 303 */     add(this.rdbtnOriginal);
/*     */ 
/* 305 */     this.rdbtnEnhanced = new JRadioButton("Enhanced");
/* 306 */     this.rdbtnEnhanced.setSelected(false);
/* 307 */     this.rdbtnEnhanced.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent arg0) {
/* 309 */         BufferedImage enhanced = MaskColorsPanel.this.entrance.getEnhancedImage();
/* 310 */         if (enhanced == null) { MaskColorsPanel.this.rdbtnOriginal.setSelected(true); return; }
/* 311 */         MaskColorsPanel.this.label.setImage(enhanced);
/* 312 */         MaskColorsPanel.this.setColorDists();
/*     */       }
/*     */     });
/* 315 */     this.rdbtnEnhanced.setBounds(156, 262, 100, 23);
/* 316 */     add(this.rdbtnEnhanced);
/*     */ 
/* 318 */     ButtonGroup bg = new ButtonGroup();
/* 319 */     bg.add(this.rdbtnEnhanced);
/* 320 */     bg.add(this.rdbtnOriginal);
/*     */ 
/* 322 */     JTextArea txtrTestSelect = new JTextArea();
/* 323 */     txtrTestSelect.setEditable(false);
/* 324 */     txtrTestSelect.setFont(new Font("SansSerif", 0, 13));
/* 325 */     txtrTestSelect.setBorder(null);
/* 326 */     txtrTestSelect.setOpaque(false);
/* 327 */     txtrTestSelect.setText("Create Simple Mask: Select range of color intensities. \r\nPixels with colors in these ranges are made black.");
/* 328 */     txtrTestSelect.setBounds(0, 282, 360, 43);
/* 329 */     add(txtrTestSelect);
/*     */ 
/* 331 */     this.redMin = new JSlider();
/* 332 */     this.redMin.setMinorTickSpacing(1);
/* 333 */     this.redMin.setMaximum(256);
/* 334 */     this.redMin.setValue(0);
/* 335 */     this.redMin.setSnapToTicks(true);
/* 336 */     this.redMin.setPaintTrack(false);
/* 337 */     this.redMin.setMajorTickSpacing(10);
/* 338 */     this.redMin.setBounds(50, 325, 269, 20);
/* 339 */     add(this.redMin);
/*     */ 
/* 341 */     this.redDist = new JLabel();
/* 342 */     this.redDist.setBorder(new LineBorder(new Color(0, 0, 0)));
/* 343 */     this.redDist.setBounds(57, 345, 256, 45);
/* 344 */     add(this.redDist);
/*     */ 
/* 346 */     this.redMax = new JSlider();
/* 347 */     this.redMax.setMinorTickSpacing(1);
/* 348 */     this.redMax.setMaximum(256);
/* 349 */     this.redMax.setValue(256);
/* 350 */     this.redMax.setSnapToTicks(true);
/* 351 */     this.redMax.setPaintTrack(false);
/* 352 */     this.redMax.setMajorTickSpacing(10);
/* 353 */     this.redMax.setBounds(50, 390, 269, 20);
/* 354 */     add(this.redMax);
/*     */ 
/* 356 */     this.redMin.addChangeListener(new ChangeListener() {
/*     */       public void stateChanged(ChangeEvent e) {
/* 358 */         if (MaskColorsPanel.this.redMin.getValue() > MaskColorsPanel.this.redMax.getValue()) {
/* 359 */           MaskColorsPanel.this.redMin.setValue(MaskColorsPanel.this.redMax.getValue());
/* 360 */           MaskColorsPanel.this.redMin.repaint();
/*     */         } else {
/* 362 */           MaskColorsPanel.this.rmin.setText(100 * MaskColorsPanel.this.redMin.getValue() / 256 + "%");
/* 363 */           MaskColorsPanel.this.applyMask();
/*     */         }
/*     */       }
/*     */     });
/* 367 */     this.redMax.addChangeListener(new ChangeListener() {
/*     */       public void stateChanged(ChangeEvent e) {
/* 369 */         if (MaskColorsPanel.this.redMax.getValue() < MaskColorsPanel.this.redMin.getValue()) {
/* 370 */           MaskColorsPanel.this.redMax.setValue(MaskColorsPanel.this.redMin.getValue());
/* 371 */           MaskColorsPanel.this.redMax.repaint();
/*     */         } else {
/* 373 */           MaskColorsPanel.this.rmax.setText(100 * MaskColorsPanel.this.redMax.getValue() / 256 + "%");
/* 374 */           MaskColorsPanel.this.applyMask();
/*     */         }
/*     */       }
/*     */     });
/* 379 */     this.textField = new JTextField();
/* 380 */     this.textField.setHorizontalAlignment(11);
/* 381 */     this.textField.setText("max");
/* 382 */     this.textField.setEditable(false);
/* 383 */     this.textField.setColumns(10);
/* 384 */     this.textField.setBorder(null);
/* 385 */     this.textField.setOpaque(false);
/* 386 */     this.textField.setBounds(6, 390, 41, 20);
/* 387 */     add(this.textField);
/*     */ 
/* 389 */     this.textField_1 = new JTextField();
/* 390 */     this.textField_1.setHorizontalAlignment(11);
/* 391 */     this.textField_1.setText("min");
/* 392 */     this.textField_1.setEditable(false);
/* 393 */     this.textField_1.setColumns(10);
/* 394 */     this.textField_1.setBorder(null);
/* 395 */     this.textField_1.setOpaque(false);
/* 396 */     this.textField_1.setBounds(6, 325, 41, 20);
/* 397 */     add(this.textField_1);
/*     */ 
/* 399 */     this.chckbxRed = new JCheckBox("Red");
/* 400 */     this.chckbxRed.setSelected(true);
/* 401 */     this.chckbxRed.setFont(new Font("SansSerif", 0, 10));
/* 402 */     this.chckbxRed.setBounds(0, 357, 51, 23);
/* 403 */     add(this.chckbxRed);
/*     */ 
/* 405 */     this.greenMin = new JSlider();
/* 406 */     this.greenMin.setMinorTickSpacing(1);
/* 407 */     this.greenMin.setMaximum(256);
/* 408 */     this.greenMin.setValue(0);
/* 409 */     this.greenMin.setSnapToTicks(true);
/* 410 */     this.greenMin.setPaintTrack(false);
/* 411 */     this.greenMin.setMajorTickSpacing(10);
/* 412 */     this.greenMin.setBounds(50, 410, 269, 20);
/* 413 */     add(this.greenMin);
/*     */ 
/* 415 */     this.textField_2 = new JTextField();
/* 416 */     this.textField_2.setHorizontalAlignment(11);
/* 417 */     this.textField_2.setText("min");
/* 418 */     this.textField_2.setEditable(false);
/* 419 */     this.textField_2.setColumns(10);
/* 420 */     this.textField_2.setBorder(null);
/* 421 */     this.textField_2.setOpaque(false);
/* 422 */     this.textField_2.setBounds(6, 410, 41, 20);
/* 423 */     add(this.textField_2);
/*     */ 
/* 425 */     this.textField_3 = new JTextField();
/* 426 */     this.textField_3.setHorizontalAlignment(11);
/* 427 */     this.textField_3.setText("max");
/* 428 */     this.textField_3.setEditable(false);
/* 429 */     this.textField_3.setColumns(10);
/* 430 */     this.textField_3.setBorder(null);
/* 431 */     this.textField_3.setOpaque(false);
/* 432 */     this.textField_3.setBounds(6, 475, 41, 20);
/* 433 */     add(this.textField_3);
/*     */ 
/* 435 */     this.greenMax = new JSlider();
/* 436 */     this.greenMax.setMinorTickSpacing(1);
/* 437 */     this.greenMax.setMaximum(256);
/* 438 */     this.greenMax.setValue(256);
/* 439 */     this.greenMax.setSnapToTicks(true);
/* 440 */     this.greenMax.setPaintTrack(false);
/* 441 */     this.greenMax.setMajorTickSpacing(10);
/* 442 */     this.greenMax.setBounds(50, 475, 269, 20);
/* 443 */     add(this.greenMax);
/*     */ 
/* 445 */     this.greenMin.addChangeListener(new ChangeListener() {
/*     */       public void stateChanged(ChangeEvent e) {
/* 447 */         if (MaskColorsPanel.this.greenMin.getValue() > MaskColorsPanel.this.greenMax.getValue()) {
/* 448 */           MaskColorsPanel.this.greenMin.setValue(MaskColorsPanel.this.greenMax.getValue());
/* 449 */           MaskColorsPanel.this.greenMin.repaint();
/*     */         } else {
/* 451 */           MaskColorsPanel.this.gmin.setText(100 * MaskColorsPanel.this.greenMin.getValue() / 256 + "%");
/* 452 */           MaskColorsPanel.this.applyMask();
/*     */         }
/*     */       }
/*     */     });
/* 456 */     this.greenMax.addChangeListener(new ChangeListener() {
/*     */       public void stateChanged(ChangeEvent e) {
/* 458 */         if (MaskColorsPanel.this.greenMax.getValue() < MaskColorsPanel.this.greenMin.getValue()) {
/* 459 */           MaskColorsPanel.this.greenMax.setValue(MaskColorsPanel.this.greenMin.getValue());
/* 460 */           MaskColorsPanel.this.greenMax.repaint();
/*     */         } else {
/* 462 */           MaskColorsPanel.this.gmax.setText(100 * MaskColorsPanel.this.greenMax.getValue() / 256 + "%");
/* 463 */           MaskColorsPanel.this.applyMask();
/*     */         }
/*     */       }
/*     */     });
/* 468 */     this.greenDist = new JLabel();
/* 469 */     this.greenDist.setBorder(new LineBorder(new Color(0, 0, 0)));
/* 470 */     this.greenDist.setBounds(58, 430, 256, 45);
/* 471 */     add(this.greenDist);
/*     */ 
/* 473 */     this.chckbxGreen = new JCheckBox("Green");
/* 474 */     this.chckbxGreen.setSelected(true);
/* 475 */     this.chckbxGreen.setFont(new Font("SansSerif", 0, 10));
/* 476 */     this.chckbxGreen.setBounds(1, 442, 57, 23);
/* 477 */     add(this.chckbxGreen);
/*     */ 
/* 479 */     this.blueMax = new JSlider();
/* 480 */     this.blueMax.setMinorTickSpacing(1);
/* 481 */     this.blueMax.setMaximum(256);
/* 482 */     this.blueMax.setValue(256);
/* 483 */     this.blueMax.setSnapToTicks(true);
/* 484 */     this.blueMax.setPaintTrack(false);
/* 485 */     this.blueMax.setMajorTickSpacing(10);
/* 486 */     this.blueMax.setBounds(50, 560, 269, 20);
/* 487 */     add(this.blueMax);
/*     */ 
/* 489 */     this.textField_4 = new JTextField();
/* 490 */     this.textField_4.setHorizontalAlignment(11);
/* 491 */     this.textField_4.setText("max");
/* 492 */     this.textField_4.setEditable(false);
/* 493 */     this.textField_4.setColumns(10);
/* 494 */     this.textField_4.setBorder(null);
/* 495 */     this.textField_4.setOpaque(false);
/* 496 */     this.textField_4.setBounds(6, 560, 41, 20);
/* 497 */     add(this.textField_4);
/*     */ 
/* 499 */     this.textField_5 = new JTextField();
/* 500 */     this.textField_5.setHorizontalAlignment(11);
/* 501 */     this.textField_5.setText("min");
/* 502 */     this.textField_5.setEditable(false);
/* 503 */     this.textField_5.setColumns(10);
/* 504 */     this.textField_5.setBorder(null);
/* 505 */     this.textField_5.setOpaque(false);
/* 506 */     this.textField_5.setBounds(6, 495, 41, 20);
/* 507 */     add(this.textField_5);
/*     */ 
/* 509 */     this.blueMin = new JSlider();
/* 510 */     this.blueMin.setMinorTickSpacing(1);
/* 511 */     this.blueMin.setMaximum(256);
/* 512 */     this.blueMin.setValue(0);
/* 513 */     this.blueMin.setSnapToTicks(true);
/* 514 */     this.blueMin.setPaintTrack(false);
/* 515 */     this.blueMin.setMajorTickSpacing(10);
/* 516 */     this.blueMin.setBounds(50, 495, 269, 20);
/* 517 */     add(this.blueMin);
/*     */ 
/* 519 */     this.blueMin.addChangeListener(new ChangeListener() {
/*     */       public void stateChanged(ChangeEvent e) {
/* 521 */         if (MaskColorsPanel.this.blueMin.getValue() > MaskColorsPanel.this.blueMax.getValue()) {
/* 522 */           MaskColorsPanel.this.blueMin.setValue(MaskColorsPanel.this.blueMax.getValue());
/* 523 */           MaskColorsPanel.this.blueMin.repaint();
/*     */         } else {
/* 525 */           MaskColorsPanel.this.bmin.setText(100 * MaskColorsPanel.this.blueMin.getValue() / 256 + "%");
/* 526 */           MaskColorsPanel.this.applyMask();
/*     */         }
/*     */       }
/*     */     });
/* 530 */     this.blueMax.addChangeListener(new ChangeListener() {
/*     */       public void stateChanged(ChangeEvent e) {
/* 532 */         if (MaskColorsPanel.this.blueMax.getValue() < MaskColorsPanel.this.blueMin.getValue()) {
/* 533 */           MaskColorsPanel.this.blueMax.setValue(MaskColorsPanel.this.blueMin.getValue());
/* 534 */           MaskColorsPanel.this.blueMax.repaint();
/*     */         } else {
/* 536 */           MaskColorsPanel.this.bmax.setText(100 * MaskColorsPanel.this.blueMax.getValue() / 256 + "%");
/* 537 */           MaskColorsPanel.this.applyMask();
/*     */         }
/*     */       }
/*     */     });
/* 542 */     this.blueDist = new JLabel();
/* 543 */     this.blueDist.setBorder(new LineBorder(new Color(0, 0, 0)));
/* 544 */     this.blueDist.setBounds(58, 515, 256, 45);
/* 545 */     add(this.blueDist);
/*     */ 
/* 547 */     this.chckbxBlue = new JCheckBox("Blue");
/* 548 */     this.chckbxBlue.setSelected(true);
/* 549 */     this.chckbxBlue.setFont(new Font("SansSerif", 0, 10));
/* 550 */     this.chckbxBlue.setBounds(1, 527, 57, 23);
/* 551 */     add(this.chckbxBlue);
/*     */ 
/* 553 */     this.showMask.setText("Show Mask");
/* 554 */     this.showMask.setSelected(true);
/* 555 */     this.showMask.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent arg0) {
/* 558 */         if (MaskColorsPanel.this.showMask.isSelected()) {
/* 559 */           MaskColorsPanel.this.applyMask();
/* 560 */           MaskColorsPanel.this.label.showZoomedOriginal();
/*     */         } else {
/* 562 */           MaskColorsPanel.this.label.setZoomedOriginal(ZoomPanLabel.resize(Math.round(MaskColorsPanel.this.label.zoomFactor * 100.0F), MaskColorsPanel.this.label.getOriginal()));
/* 563 */         }MaskColorsPanel.this.label.showZoomedOriginal();
/*     */       }
/*     */     });
/* 566 */     this.showMask.setBounds(40, 587, 287, 23);
/* 567 */     add(this.showMask);
/*     */ 
/* 569 */     this.btnNewButton_1 = new JButton("Create A Relationship Mask");
/* 570 */     this.btnNewButton_1.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 572 */         new RelationshipMaskDialog(MaskColorsPanel.this.label).setVisible(true);
/*     */       }
/*     */     });
/* 575 */     this.btnNewButton_1.setBounds(11, 612, 287, 23);
/* 576 */     add(this.btnNewButton_1);
/*     */ 
/* 578 */     this.pixnum = new JTextField();
/* 579 */     this.pixnum.setOpaque(false);
/* 580 */     this.pixnum.setEditable(false);
/* 581 */     this.pixnum.setBorder(null);
/* 582 */     this.pixnum.setFont(new Font("SansSerif", 0, 11));
/* 583 */     this.pixnum.setBounds(850, 550, 191, 20);
/* 584 */     add(this.pixnum);
/* 585 */     this.pixnum.setColumns(10);
/*     */ 
/* 587 */     this.area = new JTextField();
/* 588 */     this.area.setBorder(null);
/* 589 */     this.area.setOpaque(false);
/* 590 */     this.area.setFont(new Font("SansSerif", 0, 11));
/* 591 */     this.area.setEditable(false);
/* 592 */     this.area.setBounds(850, 570, 191, 20);
/* 593 */     add(this.area);
/* 594 */     this.area.setColumns(10);
/*     */ 
/* 596 */     this.label.getLabel().addMouseListener(new MouseListener() {
/*     */       public void mouseClicked(MouseEvent arg0) {
/*     */       }
/*     */       public void mouseEntered(MouseEvent arg0) {
/*     */       }
/*     */       public void mouseExited(MouseEvent arg0) {
/*     */       }
/*     */ 
/*     */       public void mousePressed(MouseEvent e) {
/* 605 */         Point p = MaskColorsPanel.this.label.mapToPixel(e.getX(), e.getY());
/* 606 */         int eX = p.x;
/* 607 */         int eY = p.y;
/* 608 */         if (eX < 0)
/* 609 */           eX = 0;
/* 610 */         else if (eX >= MaskColorsPanel.this.label.getOriginal().getWidth())
/* 611 */           eX = MaskColorsPanel.this.label.getOriginal().getWidth() - 1;
/* 612 */         if (eY < 0)
/* 613 */           eY = 0;
/* 614 */         else if (eY >= MaskColorsPanel.this.label.getOriginal().getHeight())
/* 615 */           eY = MaskColorsPanel.this.label.getOriginal().getHeight() - 1;
/* 616 */         MaskColorsPanel.this.x[0] = Integer.valueOf(eX);
/* 617 */         MaskColorsPanel.this.x[1] = Integer.valueOf(eX);
/* 618 */         MaskColorsPanel.this.y[0] = Integer.valueOf(eY);
/* 619 */         MaskColorsPanel.this.y[1] = Integer.valueOf(eY);
/* 620 */         MaskColorsPanel.this.label.toolImage(MaskColorsPanel.this.x, MaskColorsPanel.this.y, MaskColorsPanel.this.entrance.getColor(), "Rectangle", MaskColorsPanel.this.entrance.getLineWidth(), MaskColorsPanel.this.entrance.getCursorStyle());
/*     */       }
/*     */ 
/*     */       public void mouseReleased(MouseEvent e) {
/* 624 */         Point p = MaskColorsPanel.this.label.mapToPixel(e.getX(), e.getY());
/* 625 */         int eX = p.x;
/* 626 */         int eY = p.y;
/* 627 */         if (eX < 0)
/* 628 */           eX = 0;
/* 629 */         else if (eX >= MaskColorsPanel.this.label.getOriginal().getWidth())
/* 630 */           eX = MaskColorsPanel.this.label.getOriginal().getWidth() - 1;
/* 631 */         if (eY < 0)
/* 632 */           eY = 0;
/* 633 */         else if (eY >= MaskColorsPanel.this.label.getOriginal().getHeight())
/* 634 */           eY = MaskColorsPanel.this.label.getOriginal().getHeight() - 1;
/* 635 */         MaskColorsPanel.this.x[1] = Integer.valueOf(eX);
/* 636 */         MaskColorsPanel.this.y[1] = Integer.valueOf(eY);
/* 637 */         MaskColorsPanel.this.label.toolImage(MaskColorsPanel.this.x, MaskColorsPanel.this.y, MaskColorsPanel.this.entrance.getColor(), "Rectangle", MaskColorsPanel.this.entrance.getLineWidth(), MaskColorsPanel.this.entrance.getCursorStyle());
/*     */         int xmax;
                  int xmin;
/* 639 */         if (MaskColorsPanel.this.x[0].intValue() <= MaskColorsPanel.this.x[1].intValue()) {
/* 640 */           xmin = MaskColorsPanel.this.x[0].intValue();
/* 641 */           xmax = MaskColorsPanel.this.x[1].intValue();
/*     */         } else {
/* 643 */           xmin = MaskColorsPanel.this.x[1].intValue();
/* 644 */           xmax = MaskColorsPanel.this.x[0].intValue();
/*     */         }
/*     */         int ymax;
                  int ymin;
/* 646 */         if (MaskColorsPanel.this.y[0].intValue() <= MaskColorsPanel.this.y[1].intValue()) {
/* 647 */           ymin = MaskColorsPanel.this.y[0].intValue();
/* 648 */           ymax = MaskColorsPanel.this.y[1].intValue();
/*     */         } else {
/* 650 */           ymin = MaskColorsPanel.this.y[1].intValue();
/* 651 */           ymax = MaskColorsPanel.this.y[0].intValue();
/*     */         }
/* 653 */         p = MaskColorsPanel.this.label.zoomPixels(xmin, ymin);
/* 654 */         xmin = p.x;
/* 655 */         ymin = p.y;
/* 656 */         p = MaskColorsPanel.this.label.zoomPixels(xmax, ymax);
/* 657 */         xmax = p.x;
/* 658 */         ymax = p.y;
/* 659 */         BufferedImage img = ZoomPanLabel.resize(Math.round(MaskColorsPanel.this.label.zoomFactor * 100.0F), MaskColorsPanel.this.label.getOriginal());
/* 660 */         int w = img.getWidth();
/* 661 */         int h = img.getHeight();
/* 662 */         int[] map = img.getRGB(0, 0, w, h, null, 0, w);
/* 663 */         int rmin = 256;
/* 664 */         int rmax = 0;
/* 665 */         int gmin = 256;
/* 666 */         int gmax = 0;
/* 667 */         int bmin = 256;
/* 668 */         int bmax = 0;
/*     */ 
/* 670 */         for (int j = ymin; j < ymax; j++) {
/* 671 */           int jw = j * w;
/* 672 */           for (int i = xmin; i < xmax; i++) {
/* 673 */             int c = map[(i + jw)];
/* 674 */             int[] rgb = ColorTools.rgb(c);
/*     */ 
/* 677 */             int r = rgb[0];
/* 678 */             int g = rgb[1];
/* 679 */             int b = rgb[2];
/* 680 */             if (r > rmax) rmax = r;
/* 681 */             if (r < rmin) rmin = r;
/* 682 */             if (g > gmax) gmax = g;
/* 683 */             if (g < gmin) gmin = g;
/* 684 */             if (b > bmax) bmax = b;
/* 685 */             if (b < bmin) bmin = b;
/*     */           }
/*     */         }
/* 688 */         MaskColorsPanel.this.redMin.setValue(rmin);
/* 689 */         MaskColorsPanel.this.redMax.setValue(rmax + 1);
/* 690 */         MaskColorsPanel.this.greenMin.setValue(gmin);
/* 691 */         MaskColorsPanel.this.greenMax.setValue(gmax + 1);
/* 692 */         MaskColorsPanel.this.blueMin.setValue(bmin);
/* 693 */         MaskColorsPanel.this.blueMax.setValue(bmax + 1);
/* 694 */         MaskColorsPanel.this.applyMask();
/*     */       }
/*     */     });
/* 697 */     this.label.getLabel().addMouseMotionListener(new MouseMotionListener()
/*     */     {
/*     */       public void mouseDragged(MouseEvent e) {
/* 700 */         Point p = MaskColorsPanel.this.label.mapToPixel(e.getX(), e.getY());
/* 701 */         int eX = p.x;
/* 702 */         int eY = p.y;
/* 703 */         if (eX < 0)
/* 704 */           eX = 0;
/* 705 */         else if (eX >= MaskColorsPanel.this.label.getOriginal().getWidth())
/* 706 */           eX = MaskColorsPanel.this.label.getOriginal().getWidth() - 1;
/* 707 */         if (eY < 0)
/* 708 */           eY = 0;
/* 709 */         else if (eY >= MaskColorsPanel.this.label.getOriginal().getHeight())
/* 710 */           eY = MaskColorsPanel.this.label.getOriginal().getHeight() - 1;
/* 711 */         MaskColorsPanel.this.x[1] = Integer.valueOf(eX);
/* 712 */         MaskColorsPanel.this.y[1] = Integer.valueOf(eY);
/* 713 */         MaskColorsPanel.this.label.toolImage(MaskColorsPanel.this.x, MaskColorsPanel.this.y, MaskColorsPanel.this.entrance.getColor(), "Rectangle", MaskColorsPanel.this.entrance.getLineWidth(), MaskColorsPanel.this.entrance.getCursorStyle());
/*     */       }
/*     */ 
/*     */       public void mouseMoved(MouseEvent arg0)
/*     */       {
/*     */       }
/*     */     });
/*     */   }
/*     */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.MaskColorsPanel
 * JD-Core Version:    0.6.2
 */