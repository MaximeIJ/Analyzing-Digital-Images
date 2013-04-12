/*     */ package org.gss.adi.dialogs;
/*     */ 
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import java.awt.Point;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseMotionAdapter;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.PrintStream;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JSlider;
/*     */ import javax.swing.JSpinner;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.SpinnerNumberModel;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
/*     */ import org.gss.adi.Entrance;
/*     */ import org.gss.adi.ZoomPanLabel;
/*     */ import org.gss.adi.tools.ColorTools;
/*     */ 
/*     */ public class TrimDialog extends JDialog
/*     */ {
/*     */   private static final long serialVersionUID = 6548472986155141203L;
/*     */   private ZoomPanLabel label;
/*     */   Integer[] x;
/*     */   Integer[] y;
/*     */   BufferedImage img;
/*     */   private JSlider slider;
/*     */   private JSpinner left;
/*     */   private JSpinner top;
/*     */   private JSpinner width;
/*     */   private JSpinner height;
/*  39 */   private int moving = -1;
/*  40 */   private boolean moused = false;
/*     */   private Entrance entrance;
/*     */   private JTextField txtZoom;
/*  44 */   private TrimDialog me = this;
/*     */   private JTextField origWH;
/*     */   private JTextField txtWidthXHeight;
/*     */ 
/*     */   public TrimDialog(Entrance e)
/*     */   {
/*  52 */     setTitle("Trim Image");
/*  53 */     setBounds(100, 100, 1048, 600);
/*  54 */     getContentPane().setLayout(null);
/*  55 */     this.entrance = e;
/*  56 */     setAlwaysOnTop(true);
/*  57 */     this.img = e.getImage();
/*  58 */     this.x = new Integer[] { Integer.valueOf(0), Integer.valueOf(this.img.getWidth() - 1) };
/*  59 */     this.y = new Integer[] { Integer.valueOf(0), Integer.valueOf(this.img.getHeight() - 1) };
/*  60 */     JTextArea txtrPicturesWithMillions = new JTextArea();
/*  61 */     txtrPicturesWithMillions.setText("Pictures with millions of pixels (megapixels) take much more computer time to analyze than images that are roughly a megapixel. To save time, trim the photo with any of the three options to use only the pixels needed.\r\n\r\nZoom in on the image and draw the rectangle on the image to include the region you wish to trim the image to. There are two option for trimming the image:\r\n\r\n1) Crop the image keeping the image at the currently shown resolution that the image is zoomed to.\r\n\r\n2) Crop the selected region of the image keeping the selected portion at its original resolution and size.\r\n\r\n");
/*  62 */     txtrPicturesWithMillions.setOpaque(false);
/*  63 */     txtrPicturesWithMillions.setEditable(false);
/*  64 */     txtrPicturesWithMillions.setFont(new Font("SansSerif", 0, 13));
/*  65 */     txtrPicturesWithMillions.setWrapStyleWord(true);
/*  66 */     txtrPicturesWithMillions.setLineWrap(true);
/*  67 */     txtrPicturesWithMillions.setBounds(12, 13, 358, 292);
/*  68 */     getContentPane().add(txtrPicturesWithMillions);
/*     */ 
/*  70 */     this.slider = new JSlider();
/*  71 */     this.slider.setSnapToTicks(true);
/*  72 */     this.slider.setMajorTickSpacing(1);
/*  73 */     this.slider.setMinimum(100);
/*  74 */     this.slider.setValue(100);
/*  75 */     this.slider.setMaximum(200);
/*  76 */     this.slider.setBounds(500, 500, 200, 20);
/*  77 */     getContentPane().add(this.slider);
/*     */ 
/*  79 */     this.label = new ZoomPanLabel(this.slider);
/*  80 */     this.label.setBounds(382, 18, 640, 480);
/*  81 */     this.label.setImage(this.img);
/*  82 */     this.slider.addChangeListener(new ChangeListener()
/*     */     {
/*     */       public void stateChanged(ChangeEvent arg0) {
/*  85 */         TrimDialog.this.slide();
/*     */       }
/*     */     });
/*  88 */     this.slider.addMouseListener(new MouseAdapter()
/*     */     {
/*     */       public void mouseReleased(MouseEvent e) {
/*  91 */         TrimDialog.this.slideFinish();
/*     */       }
/*     */     });
/*  94 */     getContentPane().add(this.label);
/*  95 */     this.label.getLabel().addMouseListener(new MouseAdapter()
/*     */     {
/*     */       public void mousePressed(MouseEvent e) {
/*  98 */         TrimDialog.this.mousePress(e);
/*     */       }
/*     */ 
/*     */       public void mouseReleased(MouseEvent e) {
/* 102 */         TrimDialog.this.mouseRelease(e);
/*     */       }
/*     */     });
/* 105 */     this.label.getLabel().addMouseMotionListener(new MouseMotionAdapter()
/*     */     {
/*     */       public void mouseDragged(MouseEvent e) {
/* 108 */         TrimDialog.this.mouseDrag(e);
/*     */       }
/*     */     });
/* 111 */     setup();
/* 112 */     reTool();
/*     */   }
/*     */   private void slide() {
/* 115 */     int z = this.slider.getValue();
/* 116 */     this.label.zoom(z);
/* 117 */     this.txtZoom.setText("Zoom: " + z + "%");
/* 118 */     reTool();
/*     */     int xmin;
/* 120 */     if (this.x[0].intValue() <= this.x[1].intValue())
/* 121 */       xmin = this.x[0].intValue();
/*     */     else
/* 123 */       xmin = this.x[1].intValue();
/*     */     int ymin;
/* 125 */     if (this.y[0].intValue() <= this.y[1].intValue())
/* 126 */       ymin = this.y[0].intValue();
/*     */     else
/* 128 */       ymin = this.y[1].intValue();
/* 129 */     float zf = this.slider.getValue() / 100.0F;
/* 130 */     this.moused = true;
/* 131 */     this.left.setValue(new Integer(Math.round(xmin * zf)));
/* 132 */     this.top.setValue(new Integer(Math.round(ymin * zf)));
/* 133 */     this.width.setValue(new Integer(Math.round(Math.abs(this.x[0].intValue() - this.x[1].intValue()) * zf + zf)));
/* 134 */     this.height.setValue(new Integer(Math.round(Math.abs(this.y[0].intValue() - this.y[1].intValue()) * zf + zf)));
/* 135 */     this.moused = false;
/*     */   }
/*     */   private void slideFinish() {
/* 138 */     int z = this.slider.getValue();
/* 139 */     this.label.qualityZoom(z);
/* 140 */     this.txtZoom.setText("Zoom: " + z + "%");
/* 141 */     reTool();
/*     */     int xmin;
/* 143 */     if (this.x[0].intValue() <= this.x[1].intValue())
/* 144 */       xmin = this.x[0].intValue();
/*     */     else
/* 146 */       xmin = this.x[1].intValue();
/*     */     int ymin;
/* 148 */     if (this.y[0].intValue() <= this.y[1].intValue())
/* 149 */       ymin = this.y[0].intValue();
/*     */     else
/* 151 */       ymin = this.y[1].intValue();
/* 152 */     this.moused = true;
/* 153 */     float zf = this.slider.getValue() / 100.0F;
/* 154 */     this.left.setValue(new Integer(Math.round(xmin * zf)));
/* 155 */     this.top.setValue(new Integer(Math.round(ymin * zf)));
/* 156 */     this.width.setValue(new Integer(Math.round(Math.abs(this.x[0].intValue() - this.x[1].intValue()) * zf + zf)));
/* 157 */     this.height.setValue(new Integer(Math.round(Math.abs(this.y[0].intValue() - this.y[1].intValue()) * zf + zf)));
/* 158 */     this.moused = false;
/*     */   }
/*     */   private void reTool() {
/*     */     try {
/* 162 */       this.label.toolImage(this.x, this.y, this.entrance.getColor(), "Rectangle", this.entrance.getLineWidth(), this.entrance.getCursorStyle()); } catch (Exception e1) {
/* 163 */       e1.printStackTrace();
/*     */     }
/*     */   }
/* 166 */   private int toolContains(int eX, int eY) { for (int i = 0; i < this.x.length; i++) {
/* 167 */       if (ColorTools.linearDist(this.x[i].intValue(), this.y[i].intValue(), eX, eY).doubleValue() < 6.0D) {
/* 168 */         return i;
/*     */       }
/*     */     }
/* 171 */     return -1; }
/*     */ 
/*     */   private void mousePress(MouseEvent e) {
/* 174 */     this.moused = true;
/* 175 */     Point p = this.label.mapToPixel(e.getX(), e.getY());
/* 176 */     int eX = p.x;
/* 177 */     int eY = p.y;
/* 178 */     if ((this.x.length != 0) && (this.x[0] != null)) {
/* 179 */       this.moving = toolContains(eX, eY);
/*     */     }
/* 181 */     if (this.moving > -1) {
/* 182 */       this.x[this.moving] = Integer.valueOf(eX);
/* 183 */       this.y[this.moving] = Integer.valueOf(eY);
/*     */     } else {
/* 185 */       this.x[0] = Integer.valueOf(eX);
/* 186 */       this.y[0] = Integer.valueOf(eY);
/* 187 */       this.x[1] = Integer.valueOf(eX);
/* 188 */       this.y[1] = Integer.valueOf(eY);
/*     */     }
/* 190 */     reTool();
/*     */     int xmin;
/* 192 */     if (this.x[0].intValue() <= this.x[1].intValue())
/* 193 */       xmin = this.x[0].intValue();
/*     */     else
/* 195 */       xmin = this.x[1].intValue();
/*     */     int ymin;
/* 197 */     if (this.y[0].intValue() <= this.y[1].intValue())
/* 198 */       ymin = this.y[0].intValue();
/*     */     else
/* 200 */       ymin = this.y[1].intValue();
/* 201 */     float z = this.slider.getValue() / 100.0F;
/* 202 */     this.left.setValue(new Integer(Math.round(xmin * z)));
/* 203 */     this.top.setValue(new Integer(Math.round(ymin * z)));
/* 204 */     this.width.setValue(new Integer(Math.round(Math.abs(this.x[0].intValue() - this.x[1].intValue()) * z + z)));
/* 205 */     this.height.setValue(new Integer(Math.round(Math.abs(this.y[0].intValue() - this.y[1].intValue()) * z + z)));
/* 206 */     this.origWH.setText(Math.abs(this.x[1].intValue() - this.x[0].intValue()) + 1 + " X " + Math.abs(this.y[1].intValue() - this.y[0].intValue()) + 1);
/*     */   }
/*     */   private void mouseDrag(MouseEvent e) {
/* 209 */     Point p = this.label.mapToPixel(e.getX(), e.getY());
/* 210 */     int eX = p.x;
/* 211 */     int eY = p.y;
/* 212 */     if (eX < 0)
/* 213 */       eX = 0;
/* 214 */     else if (eX >= this.img.getWidth())
/* 215 */       eX = this.img.getWidth() - 1;
/* 216 */     if (eY < 0)
/* 217 */       eY = 0;
/* 218 */     else if (eY >= this.img.getHeight())
/* 219 */       eY = this.img.getHeight() - 1;
/* 220 */     if (this.moving > -1) {
/* 221 */       this.x[this.moving] = Integer.valueOf(eX);
/* 222 */       this.y[this.moving] = Integer.valueOf(eY);
/*     */     } else {
/* 224 */       this.x[1] = Integer.valueOf(eX);
/* 225 */       this.y[1] = Integer.valueOf(eY);
/*     */     }
/* 227 */     reTool();
/*     */     int xmin;
/* 229 */     if (this.x[0].intValue() <= this.x[1].intValue())
/* 230 */       xmin = this.x[0].intValue();
/*     */     else
/* 232 */       xmin = this.x[1].intValue();
/*     */     int ymin;
/* 234 */     if (this.y[0].intValue() <= this.y[1].intValue())
/* 235 */       ymin = this.y[0].intValue();
/*     */     else
/* 237 */       ymin = this.y[1].intValue();
/* 238 */     float z = this.slider.getValue() / 100.0F;
/* 239 */     this.left.setValue(new Integer(Math.round(xmin * z)));
/* 240 */     this.top.setValue(new Integer(Math.round(ymin * z)));
/* 241 */     this.width.setValue(new Integer(Math.round(Math.abs(this.x[0].intValue() - this.x[1].intValue()) * z + z)));
/* 242 */     this.height.setValue(new Integer(Math.round(Math.abs(this.y[0].intValue() - this.y[1].intValue()) * z + z)));
/* 243 */     this.origWH.setText(Math.abs(this.x[1].intValue() - this.x[0].intValue()) + 1 + " X " + Math.abs(this.y[1].intValue() - this.y[0].intValue()) + 1);
/*     */   }
/*     */   private void mouseRelease(MouseEvent e) {
/* 246 */     Point p = this.label.mapToPixel(e.getX(), e.getY());
/* 247 */     int eX = p.x;
/* 248 */     int eY = p.y;
/* 249 */     if (eX < 0)
/* 250 */       eX = 0;
/* 251 */     else if (eX >= this.img.getWidth())
/* 252 */       eX = this.img.getWidth() - 1;
/* 253 */     if (eY < 0)
/* 254 */       eY = 0;
/* 255 */     else if (eY >= this.img.getHeight())
/* 256 */       eY = this.img.getHeight() - 1;
/* 257 */     if (this.moving > -1) {
/* 258 */       this.x[this.moving] = Integer.valueOf(eX);
/* 259 */       this.y[this.moving] = Integer.valueOf(eY);
/*     */     } else {
/* 261 */       this.x[1] = Integer.valueOf(eX);
/* 262 */       this.y[1] = Integer.valueOf(eY);
/*     */     }
/* 264 */     reTool();
/*     */     int xmin;
/* 266 */     if (this.x[0].intValue() <= this.x[1].intValue())
/* 267 */       xmin = this.x[0].intValue();
/*     */     else
/* 269 */       xmin = this.x[1].intValue();
/*     */     int ymin;
/* 271 */     if (this.y[0].intValue() <= this.y[1].intValue())
/* 272 */       ymin = this.y[0].intValue();
/*     */     else
/* 274 */       ymin = this.y[1].intValue();
/* 275 */     float z = this.slider.getValue() / 100.0F;
/* 276 */     this.left.setValue(new Integer(Math.round(xmin * z)));
/* 277 */     this.top.setValue(new Integer(Math.round(ymin * z)));
/* 278 */     this.width.setValue(new Integer(Math.round(Math.abs(this.x[0].intValue() - this.x[1].intValue()) * z + z)));
/* 279 */     this.height.setValue(new Integer(Math.round(Math.abs(this.y[0].intValue() - this.y[1].intValue()) * z + z)));
/* 280 */     this.moused = false;
/* 281 */     this.origWH.setText(Math.abs(this.x[1].intValue() - this.x[0].intValue()) + 1 + " X " + Math.abs(this.y[1].intValue() - this.y[0].intValue()) + 1);
/*     */   }
/*     */ 
/*     */   private void setup() {
/* 285 */     this.txtZoom = new JTextField();
/* 286 */     this.txtZoom.setOpaque(false);
/* 287 */     this.txtZoom.setFont(new Font("SansSerif", 0, 11));
/* 288 */     this.txtZoom.setBorder(null);
/* 289 */     this.txtZoom.setEditable(false);
/* 290 */     this.txtZoom.setText("Zoom:");
/* 291 */     this.txtZoom.setBounds(565, 528, 86, 20);
/* 292 */     getContentPane().add(this.txtZoom);
/* 293 */     this.txtZoom.setText("Zoom: " + this.slider.getValue());
/* 294 */     this.txtZoom.setColumns(10);
/*     */ 
/* 296 */     this.left = new JSpinner();
/* 297 */     this.left.addChangeListener(new SpinAdjust());
/* 298 */     this.left.setModel(new SpinnerNumberModel(0, 0, 99999, 1));
/* 299 */     this.left.setBounds(708, 500, 71, 20);
/* 300 */     getContentPane().add(this.left);
/*     */ 
/* 302 */     this.top = new JSpinner();
/* 303 */     this.top.addChangeListener(new SpinAdjust());
/* 304 */     this.top.setModel(new SpinnerNumberModel(0, 0, 99999, 1));
/* 305 */     this.top.setBounds(789, 500, 71, 20);
/* 306 */     getContentPane().add(this.top);
/*     */ 
/* 308 */     this.width = new JSpinner();
/* 309 */     this.width.addChangeListener(new SpinAdjust());
/* 310 */     this.width.setModel(new SpinnerNumberModel(this.img.getWidth(), 0, 99999, 1));
/* 311 */     this.width.setBounds(870, 500, 71, 20);
/* 312 */     getContentPane().add(this.width);
/*     */ 
/* 314 */     this.height = new JSpinner();
/* 315 */     this.height.addChangeListener(new SpinAdjust());
/* 316 */     this.height.setModel(new SpinnerNumberModel(this.img.getHeight(), 0, 99999, 1));
/* 317 */     this.height.setBounds(951, 500, 71, 20);
/* 318 */     getContentPane().add(this.height);
/*     */ 
/* 320 */     JTextField txtLeft = new JTextField();
/* 321 */     txtLeft.setOpaque(false);
/* 322 */     txtLeft.setEditable(false);
/* 323 */     txtLeft.setBorder(null);
/* 324 */     txtLeft.setFont(new Font("SansSerif", 0, 10));
/* 325 */     txtLeft.setText("Left");
/* 326 */     txtLeft.setBounds(708, 521, 55, 15);
/* 327 */     getContentPane().add(txtLeft);
/* 328 */     txtLeft.setColumns(10);
/*     */ 
/* 330 */     JTextField txtRight = new JTextField();
/* 331 */     txtRight.setOpaque(false);
/* 332 */     txtRight.setText("Top");
/* 333 */     txtRight.setFont(new Font("SansSerif", 0, 10));
/* 334 */     txtRight.setEditable(false);
/* 335 */     txtRight.setColumns(10);
/* 336 */     txtRight.setBorder(null);
/* 337 */     txtRight.setBounds(789, 521, 55, 15);
/* 338 */     getContentPane().add(txtRight);
/*     */ 
/* 340 */     JTextField txtWidth = new JTextField();
/* 341 */     txtWidth.setOpaque(false);
/* 342 */     txtWidth.setText("Width");
/* 343 */     txtWidth.setFont(new Font("SansSerif", 0, 10));
/* 344 */     txtWidth.setEditable(false);
/* 345 */     txtWidth.setColumns(10);
/* 346 */     txtWidth.setBorder(null);
/* 347 */     txtWidth.setBounds(870, 521, 55, 15);
/* 348 */     getContentPane().add(txtWidth);
/*     */ 
/* 350 */     JTextField txtHeight = new JTextField();
/* 351 */     txtHeight.setOpaque(false);
/* 352 */     txtHeight.setText("Height");
/* 353 */     txtHeight.setFont(new Font("SansSerif", 0, 10));
/* 354 */     txtHeight.setEditable(false);
/* 355 */     txtHeight.setColumns(10);
/* 356 */     txtHeight.setBorder(null);
/* 357 */     txtHeight.setBounds(951, 521, 55, 15);
/* 358 */     getContentPane().add(txtHeight);
/*     */ 
/* 360 */     JButton btnNewButton = new JButton("Trim Image to Selected Area with Shown Resolution");
/* 361 */     btnNewButton.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e)
/*     */       {
/*     */         int xmax;
/*     */         int xmin;
/* 364 */         if (TrimDialog.this.x[0].intValue() <= TrimDialog.this.x[1].intValue()) {
/* 365 */           xmin = TrimDialog.this.x[0].intValue();
/* 366 */           xmax = TrimDialog.this.x[1].intValue();
/*     */         } else {
/* 368 */           xmin = TrimDialog.this.x[1].intValue();
/* 369 */           xmax = TrimDialog.this.x[0].intValue();
/*     */         }
/*     */         int ymax;
/*     */         int ymin;
/* 371 */         if (TrimDialog.this.y[0].intValue() <= TrimDialog.this.y[1].intValue()) {
/* 372 */           ymin = TrimDialog.this.y[0].intValue();
/* 373 */           ymax = TrimDialog.this.y[1].intValue();
/*     */         } else {
/* 375 */           ymin = TrimDialog.this.y[1].intValue();
/* 376 */           ymax = TrimDialog.this.y[0].intValue();
/*     */         }
/* 378 */         TrimDialog.this.me.dispose();
/* 379 */         TrimDialog.this.entrance.setImage(TrimDialog.this.img.getSubimage(xmin, ymin, xmax - xmin + 1, ymax - ymin + 1));
/*     */       }
/*     */     });
/* 382 */     btnNewButton.setBounds(12, 316, 358, 32);
/* 383 */     getContentPane().add(btnNewButton);
/*     */ 
/* 385 */     JButton btnTrimImageTo = new JButton("Trim Image to Selected Area with Original Resolution");
/* 386 */     btnTrimImageTo.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent arg0) {
/* 388 */         TrimDialog.this.me.dispose();
/* 389 */         TrimDialog.this.entrance.setImage(TrimDialog.this.label.getZoomedOriginal().getSubimage(((Integer)TrimDialog.this.left.getValue()).intValue(), ((Integer)TrimDialog.this.top.getValue()).intValue(), 
/* 390 */           ((Integer)TrimDialog.this.width.getValue()).intValue(), ((Integer)TrimDialog.this.height.getValue()).intValue()));
/*     */       }
/*     */     });
/* 393 */     btnTrimImageTo.setBounds(12, 359, 358, 32);
/* 394 */     getContentPane().add(btnTrimImageTo);
/*     */ 
/* 396 */     this.origWH = new JTextField();
/* 397 */     this.origWH.setOpaque(false);
/* 398 */     this.origWH.setEditable(false);
/* 399 */     this.origWH.setBorder(null);
/* 400 */     this.origWH.setBounds(371, 517, 116, 20);
/* 401 */     getContentPane().add(this.origWH);
/* 402 */     this.origWH.setColumns(10);
/* 403 */     this.origWH.setText(this.img.getWidth() + " X " + this.img.getHeight());
/*     */ 
/* 405 */     this.txtWidthXHeight = new JTextField();
/* 406 */     this.txtWidthXHeight.setOpaque(false);
/* 407 */     this.txtWidthXHeight.setText("Width X Height on Original");
/* 408 */     this.txtWidthXHeight.setFont(new Font("SansSerif", 0, 10));
/* 409 */     this.txtWidthXHeight.setEditable(false);
/* 410 */     this.txtWidthXHeight.setColumns(10);
/* 411 */     this.txtWidthXHeight.setBorder(null);
/* 412 */     this.txtWidthXHeight.setBounds(371, 537, 139, 15);
/* 413 */     getContentPane().add(this.txtWidthXHeight);
/*     */   }
/*     */   class SpinAdjust implements ChangeListener {
/*     */     SpinAdjust() {
/*     */     }
/* 418 */     public void stateChanged(ChangeEvent e) { if (!TrimDialog.this.moused) {
/* 419 */         BufferedImage zoomed = TrimDialog.this.label.getZoomedOriginal();
/* 420 */         JSpinner spin = (JSpinner)e.getSource();
/* 421 */         if (((Integer)spin.getValue()).intValue() < 0) {
/* 422 */           spin.setValue(new Integer(0));
/*     */         }
/* 424 */         if (spin == TrimDialog.this.left) {
/* 425 */           if (((Integer)spin.getValue()).intValue() >= zoomed.getWidth())
/* 426 */             spin.setValue(new Integer(zoomed.getWidth() - 1));
/*     */         }
/* 428 */         else if ((spin == TrimDialog.this.top) && 
/* 429 */           (((Integer)spin.getValue()).intValue() >= zoomed.getHeight())) {
/* 430 */           spin.setValue(new Integer(zoomed.getHeight() - 1));
/*     */         }
/*     */ 
/* 433 */         if (((Integer)TrimDialog.this.width.getValue()).intValue() + ((Integer)TrimDialog.this.left.getValue()).intValue() > zoomed.getWidth()) {
/* 434 */           TrimDialog.this.width.setValue(Integer.valueOf(zoomed.getWidth() - ((Integer)TrimDialog.this.left.getValue()).intValue()));
/*     */         }
/* 436 */         if (((Integer)TrimDialog.this.height.getValue()).intValue() + ((Integer)TrimDialog.this.top.getValue()).intValue() > zoomed.getHeight()) {
/* 437 */           System.out.println("h");
/* 438 */           TrimDialog.this.height.setValue(Integer.valueOf(zoomed.getHeight() - ((Integer)TrimDialog.this.top.getValue()).intValue()));
/*     */         }
/* 440 */         Point p = TrimDialog.this.label.mapToPixel(((Integer)TrimDialog.this.left.getValue()).intValue(), ((Integer)TrimDialog.this.top.getValue()).intValue());
/* 441 */         Point p2 = TrimDialog.this.label.mapToPixel(((Integer)TrimDialog.this.width.getValue()).intValue(), ((Integer)TrimDialog.this.height.getValue()).intValue());
/* 442 */         TrimDialog.this.x[0] = Integer.valueOf(p.x);
/* 443 */         TrimDialog.this.x[1] = Integer.valueOf(p2.x - 1 + TrimDialog.this.x[0].intValue());
/* 444 */         TrimDialog.this.y[0] = Integer.valueOf(p.y);
/* 445 */         TrimDialog.this.y[1] = Integer.valueOf(p2.y - 1 + TrimDialog.this.y[0].intValue());
/* 446 */         TrimDialog.this.origWH.setText(TrimDialog.this.x[1].intValue() - TrimDialog.this.x[0].intValue() + 1 + " X " + (TrimDialog.this.y[1].intValue() - TrimDialog.this.y[0].intValue() + 1));
/* 447 */         TrimDialog.this.reTool();
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.dialogs.TrimDialog
 * JD-Core Version:    0.6.2
 */