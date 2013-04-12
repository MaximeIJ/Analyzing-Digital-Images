/*     */ package org.gss.adi.datapanels;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Point;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.ArrayList;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JTextField;
/*     */ import org.gss.adi.tools.ColorTools;
/*     */ 
/*     */ public class PathPanel extends FocusPanel
/*     */ {
/*     */   private static final long serialVersionUID = 1415235793445540119L;
/*     */   private JTextField textField;
/*     */   private JTextField txtColorIntensitiesAlong;
/*     */   private JTextField textField_2;
/*     */   private JTextField textField_3;
/*     */   private JTextField textField_4;
/*     */   private JTextField textField_5;
/*     */   private JLabel label;
/*     */   private BufferedImage img;
/*     */   private BufferedImage img1;
/*     */   private BufferedImage img2;
/*     */   private BufferedImage img3;
/*     */   private JTextField image1txt;
/*     */   private JTextField r1;
/*     */   private JTextField g1;
/*     */   private JTextField b1;
/*     */   private JTextField image2txt;
/*     */   private JTextField r2;
/*     */   private JTextField g3;
/*     */   private JTextField r3;
/*     */   private JTextField image3txt;
/*     */   private JTextField b2;
/*     */   private JTextField g2;
/*     */   private JTextField b3;
/*  50 */   private Boolean three = Boolean.valueOf(false);
/*     */ 
/*  52 */   private ArrayList<Point> redList1 = new ArrayList();
/*  53 */   private ArrayList<Point> redList2 = new ArrayList();
/*  54 */   private ArrayList<Point> redList3 = new ArrayList();
/*  55 */   private ArrayList<Point> greenList1 = new ArrayList();
/*  56 */   private ArrayList<Point> greenList2 = new ArrayList();
/*  57 */   private ArrayList<Point> greenList3 = new ArrayList();
/*  58 */   private ArrayList<Point> blueList1 = new ArrayList();
/*  59 */   private ArrayList<Point> blueList2 = new ArrayList();
/*  60 */   private ArrayList<Point> blueList3 = new ArrayList();
/*     */   private JTextField txtMean;
/*     */   private JTextField textField_1;
/*     */   private Integer[] x;
/*     */   private Integer[] y;
/*     */   private int RGB;
/*     */ 
/*     */   public PathPanel(BufferedImage image1, BufferedImage image2, BufferedImage image3, int rgb)
/*     */   {
/*  71 */     super(image1, image2, image3, rgb);
/*  72 */     this.RGB = rgb;
/*  73 */     this.img1 = image1;
/*  74 */     this.img2 = image2;
/*  75 */     this.img3 = image3;
/*  76 */     setup();
/*  77 */     if (rgb > 0) {
/*  78 */       this.r1.setForeground(this.black1);
/*  79 */       this.g1.setForeground(this.red1);
/*  80 */       this.b1.setForeground(this.green1);
/*  81 */       this.r2.setForeground(this.black2);
/*  82 */       this.g2.setForeground(this.red2);
/*  83 */       this.b2.setForeground(this.green2);
/*  84 */       this.r3.setForeground(this.black3);
/*  85 */       this.g3.setForeground(this.red3);
/*  86 */       this.b3.setForeground(this.green3);
/*     */     }
/*  88 */     if (rgb == 2) {
/*  89 */       this.textField_1.setText("NDVI value");
/*  90 */       this.g1.setVisible(false);
/*  91 */       this.b1.setVisible(false);
/*  92 */       this.g2.setVisible(false);
/*  93 */       this.b2.setVisible(false);
/*  94 */       this.g3.setVisible(false);
/*  95 */       this.b3.setVisible(false);
/*  96 */       this.textField.setText("1");
/*  97 */       this.textField_2.setText(".5");
/*  98 */       this.textField_3.setText("0");
/*  99 */       this.textField_4.setText("-.5");
/* 100 */       this.textField_5.setText("-1");
/*     */     }
/* 102 */     drawAxis();
/*     */   }
/*     */ 
/*     */   private void drawAxis() {
/* 106 */     this.img = new BufferedImage(255, 255, 1);
/* 107 */     Graphics2D g = this.img.createGraphics();
/* 108 */     g.setColor(Color.WHITE);
/* 109 */     g.fillRect(0, 0, 255, 255);
/* 110 */     g.setColor(Color.GRAY);
/* 111 */     g.setStroke(new BasicStroke(1.0F));
/* 112 */     g.drawLine(0, 0, 255, 0);
/* 113 */     g.drawLine(0, 64, 255, 64);
/* 114 */     g.drawLine(0, 126, 255, 126);
/* 115 */     g.drawLine(0, 187, 255, 187);
/* 116 */     g.drawLine(0, 254, 255, 254);
/* 117 */     g.drawLine(0, 0, 0, 255);
/* 118 */     g.drawLine(254, 0, 254, 255);
/*     */ 
/* 120 */     g.dispose();
/* 121 */     this.label.setIcon(new ImageIcon(this.img));
/*     */   }
/*     */ 
/*     */   public void clear() {
/* 125 */     drawAxis();
/* 126 */     this.image1txt.setVisible(false);
/* 127 */     this.r1.setVisible(false);
/* 128 */     this.g1.setVisible(false);
/* 129 */     this.b1.setVisible(false);
/* 130 */     this.image2txt.setVisible(false);
/* 131 */     this.r2.setVisible(false);
/* 132 */     this.g2.setVisible(false);
/* 133 */     this.b2.setVisible(false);
/* 134 */     this.image3txt.setVisible(false);
/* 135 */     this.r3.setVisible(false);
/* 136 */     this.g3.setVisible(false);
/* 137 */     this.b3.setVisible(false);
/*     */   }
/*     */ 
/*     */   public void update1(Integer[] X, Integer[] Y)
/*     */   {
/* 142 */     drawAxis();
/* 143 */     this.x = X;
/* 144 */     this.y = Y;
/* 145 */     this.redList1 = new ArrayList();
/* 146 */     this.redList2 = new ArrayList();
/* 147 */     this.redList3 = new ArrayList();
/* 148 */     this.greenList1 = new ArrayList();
/* 149 */     this.greenList2 = new ArrayList();
/* 150 */     this.greenList3 = new ArrayList();
/* 151 */     this.blueList1 = new ArrayList();
/* 152 */     this.blueList2 = new ArrayList();
/* 153 */     this.blueList3 = new ArrayList();
/* 154 */     if (this.three.booleanValue()) {
/* 155 */       threePics();
/* 156 */       int total = 0;
/* 157 */       for (int j = 0; j < this.x.length - 1; j++) {
/* 158 */         total += ColorTools.getLinePixels(new Integer[] { this.x[j], this.x[(j + 1)] }, new Integer[] { this.y[j], this.y[(j + 1)] }).size();
/*     */       }
/* 160 */       float scale = 255.0F / total;
/* 161 */       total = 0;
/* 162 */       for (int j = 0; j < this.x.length - 1; j++) {
/* 163 */         ArrayList al = ColorTools.getLinePixels(new Integer[] { this.x[j], this.x[(j + 1)] }, new Integer[] { this.y[j], this.y[(j + 1)] });
/* 164 */         for (int i = 0; i < al.size(); i++) {
/* 165 */           int xVal = Math.round(scale * total);
/* 166 */           Point p = (Point)al.get(i);
/* 167 */           int[] rgb = ColorTools.rgb(this.img1.getRGB(p.x, p.y));
/* 168 */           this.redList1.add(new Point(xVal, 255 - rgb[0]));
/* 169 */           this.greenList1.add(new Point(xVal, 255 - rgb[1]));
/* 170 */           this.blueList1.add(new Point(xVal, 255 - rgb[2]));
/* 171 */           rgb = ColorTools.rgb(this.img2.getRGB(p.x, p.y));
/* 172 */           this.redList2.add(new Point(xVal, 255 - rgb[0]));
/* 173 */           this.greenList2.add(new Point(xVal, 255 - rgb[1]));
/* 174 */           this.blueList2.add(new Point(xVal, 255 - rgb[2]));
/* 175 */           rgb = ColorTools.rgb(this.img3.getRGB(p.x, p.y));
/* 176 */           this.redList3.add(new Point(xVal, 255 - rgb[0]));
/* 177 */           this.greenList3.add(new Point(xVal, 255 - rgb[1]));
/* 178 */           this.blueList3.add(new Point(xVal, 255 - rgb[2]));
/* 179 */           total++;
/*     */         }
/*     */       }
/* 182 */       draw1(); draw2(); draw3();
/*     */     } else {
/* 184 */       int total = 0;
/* 185 */       for (int j = 0; j < this.x.length - 1; j++) {
/* 186 */         total += ColorTools.getLinePixels(new Integer[] { this.x[j], this.x[(j + 1)] }, new Integer[] { this.y[j], this.y[(j + 1)] }).size();
/*     */       }
/* 188 */       float scale = 255.0F / total;
/* 189 */       total = 0;
/* 190 */       for (int j = 0; j < this.x.length - 1; j++) {
/* 191 */         ArrayList al = ColorTools.getLinePixels(new Integer[] { this.x[j], this.x[(j + 1)] }, new Integer[] { this.y[j], this.y[(j + 1)] });
/* 192 */         for (int i = 0; i < al.size(); i++) {
/* 193 */           int xVal = Math.round(scale * total);
/* 194 */           Point p = (Point)al.get(i);
/* 195 */           int[] rgb = ColorTools.rgb(this.img1.getRGB(p.x, p.y));
/* 196 */           this.redList1.add(new Point(xVal, 255 - rgb[0]));
/* 197 */           this.greenList1.add(new Point(xVal, 255 - rgb[1]));
/* 198 */           this.blueList1.add(new Point(xVal, 255 - rgb[2]));
/* 199 */           rgb = ColorTools.rgb(this.img2.getRGB(p.x, p.y));
/* 200 */           this.redList2.add(new Point(xVal, 255 - rgb[0]));
/* 201 */           this.greenList2.add(new Point(xVal, 255 - rgb[1]));
/* 202 */           this.blueList2.add(new Point(xVal, 255 - rgb[2]));
/* 203 */           total++;
/*     */         }
/*     */       }
/* 206 */       draw1(); draw2();
/*     */     }
/* 208 */     repaint();
/*     */   }
/*     */ 
/*     */   public void draw1()
/*     */   {
/*     */     Color B;
/*     */     Color R;
/*     */     Color G;
/* 215 */     if (this.RGB == 0) {
/* 216 */       R = this.red1;
/* 217 */       G = this.green1;
/* 218 */       B = this.blue1;
/*     */     } else {
/* 220 */       R = this.black1;
/* 221 */       G = this.red1;
/* 222 */       B = this.green1;
/*     */     }
/* 224 */     Float r = Float.valueOf(0.0F);
/* 225 */     Integer gr = Integer.valueOf(0);
/* 226 */     Integer b = Integer.valueOf(0);
/* 227 */     ArrayList al = new ArrayList();
/* 228 */     for (int i = 0; i < this.x.length - 1; i++) {
/* 229 */       al.addAll(ColorTools.getLinePixels(new Integer[] { this.x[i], this.x[(i + 1)] }, new Integer[] { this.y[i], this.y[(i + 1)] }));
/*     */     }
/* 231 */     Float size = Float.valueOf(this.redList1.size() - 1.0F);
/* 232 */     for (int i = 0; i < size.floatValue(); i++) {
/* 233 */       Graphics2D g = this.img.createGraphics();
/* 234 */       g.setStroke(new BasicStroke(2.0F));
/* 235 */       g.setColor(R);
/* 236 */       Point p = (Point)this.redList1.get(i);
/* 237 */       if (this.RGB < 2) {
/* 238 */         g.drawLine(p.x, p.y, ((Point)this.redList1.get(i + 1)).x, ((Point)this.redList1.get(i + 1)).y);
/* 239 */         r = Float.valueOf(r.floatValue() + p.y);
/* 240 */         g.setColor(G);
/* 241 */         p = (Point)this.greenList1.get(i);
/* 242 */         g.drawLine(p.x, p.y, ((Point)this.greenList1.get(i + 1)).x, ((Point)this.greenList1.get(i + 1)).y);
/* 243 */         gr = Integer.valueOf(gr.intValue() + p.y);
/* 244 */         g.setColor(B);
/* 245 */         p = (Point)this.blueList1.get(i);
/* 246 */         g.drawLine(p.x, p.y, ((Point)this.blueList1.get(i + 1)).x, ((Point)this.blueList1.get(i + 1)).y);
/* 247 */         b = Integer.valueOf(b.intValue() + p.y);
/*     */       } else {
/* 249 */         r = Float.valueOf(r.floatValue() + ColorTools.colorToNDVI(this.img1.getRGB(((Point)al.get(i)).x, ((Point)al.get(i)).y)));
/* 250 */         g.drawLine(p.x, 128 - Math.round(126.0F * ColorTools.colorToNDVI(this.img1.getRGB(((Point)al.get(i)).x, ((Point)al.get(i)).y))), ((Point)this.redList1.get(i + 1)).x, 128 - Math.round(126.0F * ColorTools.colorToNDVI(this.img1.getRGB(((Point)al.get(i + 1)).x, ((Point)al.get(i + 1)).y))));
/*     */       }
/* 252 */       g.dispose();
/*     */     }
/* 254 */     if (size.floatValue() == 0.0F) {
/* 255 */       this.r1.setText("");
/* 256 */       this.g1.setText("");
/* 257 */       this.b1.setText("");
/*     */     } else {
/* 259 */       this.r1.setText(this.df.format(r.floatValue() / size.floatValue()));
/* 260 */       this.g1.setText(this.df.format(gr.intValue() / size.floatValue()));
/* 261 */       this.b1.setText(this.df.format(b.intValue() / size.floatValue()));
/*     */     }
/* 263 */     this.image1txt.setVisible(true);
/* 264 */     this.r1.setVisible(true);
/* 265 */     if (this.RGB < 2) {
/* 266 */       this.g1.setVisible(true);
/* 267 */       this.b1.setVisible(true);
/*     */     }
/* 269 */     repaint();
/*     */   }
/*     */ 
/*     */   public void draw2()
/*     */   {
/*     */     Color B;
/*     */     Color R;
/*     */     Color G;
/* 275 */     if (this.RGB == 0) {
/* 276 */       R = this.red2;
/* 277 */       G = this.green2;
/* 278 */       B = this.blue2;
/*     */     } else {
/* 280 */       R = this.black2;
/* 281 */       G = this.red2;
/* 282 */       B = this.green2;
/*     */     }
/* 284 */     Float r = Float.valueOf(0.0F);
/* 285 */     Integer gr = Integer.valueOf(0);
/* 286 */     Integer b = Integer.valueOf(0);
/* 287 */     ArrayList al = new ArrayList();
/* 288 */     for (int i = 0; i < this.x.length - 1; i++) {
/* 289 */       al.addAll(ColorTools.getLinePixels(new Integer[] { this.x[i], this.x[(i + 1)] }, new Integer[] { this.y[i], this.y[(i + 1)] }));
/*     */     }
/* 291 */     Float size = Float.valueOf(this.redList2.size() - 1.0F);
/* 292 */     for (int i = 0; i < size.floatValue(); i++) {
/* 293 */       Graphics2D g = this.img.createGraphics();
/* 294 */       g.setStroke(new BasicStroke(2.0F));
/* 295 */       g.setColor(R);
/* 296 */       Point p = (Point)this.redList2.get(i);
/* 297 */       if (this.RGB < 2) {
/* 298 */         g.drawLine(p.x, p.y, ((Point)this.redList2.get(i + 1)).x, ((Point)this.redList2.get(i + 1)).y);
/* 299 */         r = Float.valueOf(r.floatValue() + p.y);
/* 300 */         g.setColor(G);
/* 301 */         p = (Point)this.greenList2.get(i);
/* 302 */         g.drawLine(p.x, p.y, ((Point)this.greenList2.get(i + 1)).x, ((Point)this.greenList2.get(i + 1)).y);
/* 303 */         gr = Integer.valueOf(gr.intValue() + p.y);
/* 304 */         g.setColor(B);
/* 305 */         p = (Point)this.blueList2.get(i);
/* 306 */         g.drawLine(p.x, p.y, ((Point)this.blueList2.get(i + 1)).x, ((Point)this.blueList2.get(i + 1)).y);
/* 307 */         b = Integer.valueOf(b.intValue() + p.y);
/*     */       } else {
/* 309 */         r = Float.valueOf(r.floatValue() + ColorTools.colorToNDVI(this.img2.getRGB(((Point)al.get(i)).x, ((Point)al.get(i)).y)));
/* 310 */         g.drawLine(p.x, 128 - Math.round(126.0F * ColorTools.colorToNDVI(this.img2.getRGB(((Point)al.get(i)).x, ((Point)al.get(i)).y))), ((Point)this.redList2.get(i + 1)).x, 128 - Math.round(126.0F * ColorTools.colorToNDVI(this.img2.getRGB(((Point)al.get(i + 1)).x, ((Point)al.get(i + 1)).y))));
/*     */       }
/* 312 */       g.dispose();
/*     */     }
/* 314 */     if (size.floatValue() == 0.0F) {
/* 315 */       this.r2.setText("");
/* 316 */       this.g2.setText("");
/* 317 */       this.b2.setText("");
/*     */     } else {
/* 319 */       this.r2.setText(this.df.format(r.floatValue() / size.floatValue()));
/* 320 */       this.g2.setText(this.df.format(gr.intValue() / size.floatValue()));
/* 321 */       this.b2.setText(this.df.format(b.intValue() / size.floatValue()));
/*     */     }
/* 323 */     this.image2txt.setVisible(true);
/* 324 */     this.r2.setVisible(true);
/* 325 */     if (this.RGB < 2) {
/* 326 */       this.g2.setVisible(true);
/* 327 */       this.b2.setVisible(true);
/*     */     }
/* 329 */     repaint();
/*     */   }
/*     */ 
/*     */   public void draw3()
/*     */   {
/*     */     Color B;
/*     */     Color R;
/*     */     Color G;
/* 335 */     if (this.RGB == 0) {
/* 336 */       R = this.red3;
/* 337 */       G = this.green3;
/* 338 */       B = this.blue3;
/*     */     } else {
/* 340 */       R = this.black3;
/* 341 */       G = this.red3;
/* 342 */       B = this.green3;
/*     */     }
/* 344 */     Float r = Float.valueOf(0.0F);
/* 345 */     Integer gr = Integer.valueOf(0);
/* 346 */     Integer b = Integer.valueOf(0);
/* 347 */     ArrayList al = new ArrayList();
/* 348 */     for (int i = 0; i < this.x.length - 1; i++) {
/* 349 */       al.addAll(ColorTools.getLinePixels(new Integer[] { this.x[i], this.x[(i + 1)] }, new Integer[] { this.y[i], this.y[(i + 1)] }));
/*     */     }
/* 351 */     Float size = Float.valueOf(this.redList3.size() - 1.0F);
/* 352 */     for (int i = 0; i < size.floatValue(); i++) {
/* 353 */       Graphics2D g = this.img.createGraphics();
/* 354 */       g.setStroke(new BasicStroke(2.0F));
/* 355 */       g.setColor(R);
/* 356 */       Point p = (Point)this.redList3.get(i);
/* 357 */       if (this.RGB < 2) {
/* 358 */         g.drawLine(p.x, p.y, ((Point)this.redList3.get(i + 1)).x, ((Point)this.redList3.get(i + 1)).y);
/* 359 */         r = Float.valueOf(r.floatValue() + p.y);
/* 360 */         g.setColor(G);
/* 361 */         p = (Point)this.greenList3.get(i);
/* 362 */         g.drawLine(p.x, p.y, ((Point)this.greenList3.get(i + 1)).x, ((Point)this.greenList3.get(i + 1)).y);
/* 363 */         gr = Integer.valueOf(gr.intValue() + p.y);
/* 364 */         g.setColor(B);
/* 365 */         p = (Point)this.blueList3.get(i);
/* 366 */         g.drawLine(p.x, p.y, ((Point)this.blueList3.get(i + 1)).x, ((Point)this.blueList3.get(i + 1)).y);
/* 367 */         b = Integer.valueOf(b.intValue() + p.y);
/*     */       } else {
/* 369 */         r = Float.valueOf(r.floatValue() + ColorTools.colorToNDVI(this.img3.getRGB(((Point)al.get(i)).x, ((Point)al.get(i)).y)));
/* 370 */         g.drawLine(p.x, 128 - Math.round(126.0F * ColorTools.colorToNDVI(this.img3.getRGB(((Point)al.get(i)).x, ((Point)al.get(i)).y))), ((Point)this.redList3.get(i + 1)).x, 128 - Math.round(126.0F * ColorTools.colorToNDVI(this.img3.getRGB(((Point)al.get(i + 1)).x, ((Point)al.get(i + 1)).y))));
/*     */       }
/* 372 */       g.dispose();
/*     */     }
/* 374 */     if (size.floatValue() == 0.0F) {
/* 375 */       this.r3.setText("");
/* 376 */       this.g3.setText("");
/* 377 */       this.b3.setText("");
/*     */     } else {
/* 379 */       this.r3.setText(this.df.format(r.floatValue() / size.floatValue()));
/* 380 */       this.g3.setText(this.df.format(gr.intValue() / size.floatValue()));
/* 381 */       this.b3.setText(this.df.format(b.intValue() / size.floatValue()));
/*     */     }
/* 383 */     this.image3txt.setVisible(true);
/* 384 */     this.r3.setVisible(true);
/* 385 */     if (this.RGB < 2) {
/* 386 */       this.g3.setVisible(true);
/* 387 */       this.b3.setVisible(true);
/*     */     }
/* 389 */     repaint();
/*     */   }
/*     */ 
/*     */   public void update2(Integer[] x, Integer[] y) {
/*     */   }
/*     */ 
/*     */   public void update3(Integer[] x, Integer[] y) {
/*     */   }
/*     */ 
/*     */   public void threePics() {
/* 399 */     this.image3txt.setVisible(true);
/* 400 */     this.r3.setVisible(true);
/* 401 */     this.g3.setVisible(true);
/* 402 */     this.b3.setVisible(true);
/* 403 */     this.three = Boolean.valueOf(true);
/*     */   }
/*     */ 
/*     */   private void setup() {
/* 407 */     this.textField = new JTextField();
/* 408 */     this.textField.setText("100%");
/* 409 */     this.textField.setOpaque(false);
/* 410 */     this.textField.setHorizontalAlignment(11);
/* 411 */     this.textField.setFont(new Font("Tahoma", 0, 10));
/* 412 */     this.textField.setEditable(false);
/* 413 */     this.textField.setColumns(10);
/* 414 */     this.textField.setBorder(null);
/* 415 */     this.textField.setBounds(23, 25, 40, 10);
/* 416 */     add(this.textField);
/*     */ 
/* 418 */     this.txtColorIntensitiesAlong = new JTextField();
/* 419 */     this.txtColorIntensitiesAlong.setText("Color Intensities Along Path");
/* 420 */     this.txtColorIntensitiesAlong.setOpaque(false);
/* 421 */     this.txtColorIntensitiesAlong.setHorizontalAlignment(0);
/* 422 */     this.txtColorIntensitiesAlong.setEditable(false);
/* 423 */     this.txtColorIntensitiesAlong.setColumns(10);
/* 424 */     this.txtColorIntensitiesAlong.setBorder(null);
/* 425 */     this.txtColorIntensitiesAlong.setBounds(73, 11, 250, 20);
/* 426 */     add(this.txtColorIntensitiesAlong);
/*     */ 
/* 428 */     this.textField_2 = new JTextField();
/* 429 */     this.textField_2.setText("75%");
/* 430 */     this.textField_2.setOpaque(false);
/* 431 */     this.textField_2.setHorizontalAlignment(11);
/* 432 */     this.textField_2.setFont(new Font("Tahoma", 0, 10));
/* 433 */     this.textField_2.setEditable(false);
/* 434 */     this.textField_2.setColumns(10);
/* 435 */     this.textField_2.setBorder(null);
/* 436 */     this.textField_2.setBounds(23, 87, 40, 10);
/* 437 */     add(this.textField_2);
/*     */ 
/* 439 */     this.textField_3 = new JTextField();
/* 440 */     this.textField_3.setText("50%");
/* 441 */     this.textField_3.setOpaque(false);
/* 442 */     this.textField_3.setHorizontalAlignment(11);
/* 443 */     this.textField_3.setFont(new Font("Tahoma", 0, 10));
/* 444 */     this.textField_3.setEditable(false);
/* 445 */     this.textField_3.setColumns(10);
/* 446 */     this.textField_3.setBorder(null);
/* 447 */     this.textField_3.setBounds(23, 149, 40, 10);
/* 448 */     add(this.textField_3);
/*     */ 
/* 450 */     this.textField_4 = new JTextField();
/* 451 */     this.textField_4.setText("25%");
/* 452 */     this.textField_4.setOpaque(false);
/* 453 */     this.textField_4.setHorizontalAlignment(11);
/* 454 */     this.textField_4.setFont(new Font("Tahoma", 0, 10));
/* 455 */     this.textField_4.setEditable(false);
/* 456 */     this.textField_4.setColumns(10);
/* 457 */     this.textField_4.setBorder(null);
/* 458 */     this.textField_4.setBounds(23, 211, 40, 10);
/* 459 */     add(this.textField_4);
/*     */ 
/* 461 */     this.textField_5 = new JTextField();
/* 462 */     this.textField_5.setText("0%");
/* 463 */     this.textField_5.setOpaque(false);
/* 464 */     this.textField_5.setHorizontalAlignment(11);
/* 465 */     this.textField_5.setFont(new Font("Tahoma", 0, 10));
/* 466 */     this.textField_5.setEditable(false);
/* 467 */     this.textField_5.setColumns(10);
/* 468 */     this.textField_5.setBorder(null);
/* 469 */     this.textField_5.setBounds(23, 273, 40, 10);
/* 470 */     add(this.textField_5);
/*     */ 
/* 472 */     this.label = new JLabel();
/* 473 */     this.label.setBounds(73, 29, 255, 255);
/* 474 */     add(this.label);
/*     */ 
/* 476 */     this.image1txt = new JTextField();
/* 477 */     this.image1txt.setBorder(null);
/* 478 */     this.image1txt.setOpaque(false);
/* 479 */     this.image1txt.setEditable(false);
/* 480 */     this.image1txt.setHorizontalAlignment(0);
/* 481 */     this.image1txt.setText("Image 1");
/* 482 */     this.image1txt.setBounds(348, 51, 70, 15);
/* 483 */     add(this.image1txt);
/* 484 */     this.image1txt.setColumns(10);
/*     */ 
/* 486 */     this.r1 = new JTextField();
/* 487 */     this.r1.setForeground(this.red1);
/* 488 */     this.r1.setOpaque(false);
/* 489 */     this.r1.setHorizontalAlignment(0);
/* 490 */     this.r1.setEditable(false);
/* 491 */     this.r1.setColumns(10);
/* 492 */     this.r1.setBorder(null);
/* 493 */     this.r1.setBounds(348, 66, 70, 15);
/* 494 */     add(this.r1);
/*     */ 
/* 496 */     this.g1 = new JTextField();
/* 497 */     this.g1.setForeground(this.green1);
/* 498 */     this.g1.setOpaque(false);
/* 499 */     this.g1.setHorizontalAlignment(0);
/* 500 */     this.g1.setEditable(false);
/* 501 */     this.g1.setColumns(10);
/* 502 */     this.g1.setBorder(null);
/* 503 */     this.g1.setBounds(348, 81, 70, 15);
/* 504 */     add(this.g1);
/*     */ 
/* 506 */     this.b1 = new JTextField();
/* 507 */     this.b1.setForeground(this.blue1);
/* 508 */     this.b1.setOpaque(false);
/* 509 */     this.b1.setHorizontalAlignment(0);
/* 510 */     this.b1.setEditable(false);
/* 511 */     this.b1.setColumns(10);
/* 512 */     this.b1.setBorder(null);
/* 513 */     this.b1.setBounds(348, 96, 70, 15);
/* 514 */     add(this.b1);
/*     */ 
/* 516 */     this.image2txt = new JTextField();
/* 517 */     this.image2txt.setForeground(this.black2);
/* 518 */     this.image2txt.setText("Image 2");
/* 519 */     this.image2txt.setOpaque(false);
/* 520 */     this.image2txt.setHorizontalAlignment(0);
/* 521 */     this.image2txt.setEditable(false);
/* 522 */     this.image2txt.setColumns(10);
/* 523 */     this.image2txt.setBorder(null);
/* 524 */     this.image2txt.setBounds(348, 111, 70, 15);
/* 525 */     add(this.image2txt);
/*     */ 
/* 527 */     this.r2 = new JTextField();
/* 528 */     this.r2.setForeground(this.red2);
/* 529 */     this.r2.setOpaque(false);
/* 530 */     this.r2.setHorizontalAlignment(0);
/* 531 */     this.r2.setEditable(false);
/* 532 */     this.r2.setColumns(10);
/* 533 */     this.r2.setBorder(null);
/* 534 */     this.r2.setBounds(348, 126, 70, 15);
/* 535 */     add(this.r2);
/*     */ 
/* 537 */     this.g3 = new JTextField();
/* 538 */     this.g3.setVisible(false);
/* 539 */     this.g3.setForeground(this.green3);
/* 540 */     this.g3.setOpaque(false);
/* 541 */     this.g3.setHorizontalAlignment(0);
/* 542 */     this.g3.setEditable(false);
/* 543 */     this.g3.setColumns(10);
/* 544 */     this.g3.setBorder(null);
/* 545 */     this.g3.setBounds(348, 201, 70, 15);
/* 546 */     add(this.g3);
/*     */ 
/* 548 */     this.r3 = new JTextField();
/* 549 */     this.r3.setVisible(false);
/* 550 */     this.r3.setForeground(this.red3);
/* 551 */     this.r3.setOpaque(false);
/* 552 */     this.r3.setHorizontalAlignment(0);
/* 553 */     this.r3.setEditable(false);
/* 554 */     this.r3.setColumns(10);
/* 555 */     this.r3.setBorder(null);
/* 556 */     this.r3.setBounds(348, 186, 70, 15);
/* 557 */     add(this.r3);
/*     */ 
/* 559 */     this.image3txt = new JTextField();
/* 560 */     this.image3txt.setVisible(false);
/* 561 */     this.image3txt.setForeground(this.black3);
/* 562 */     this.image3txt.setText("Image 3");
/* 563 */     this.image3txt.setOpaque(false);
/* 564 */     this.image3txt.setHorizontalAlignment(0);
/* 565 */     this.image3txt.setEditable(false);
/* 566 */     this.image3txt.setColumns(10);
/* 567 */     this.image3txt.setBorder(null);
/* 568 */     this.image3txt.setBounds(348, 171, 70, 15);
/* 569 */     add(this.image3txt);
/*     */ 
/* 571 */     this.b2 = new JTextField();
/* 572 */     this.b2.setForeground(this.blue2);
/* 573 */     this.b2.setOpaque(false);
/* 574 */     this.b2.setHorizontalAlignment(0);
/* 575 */     this.b2.setEditable(false);
/* 576 */     this.b2.setColumns(10);
/* 577 */     this.b2.setBorder(null);
/* 578 */     this.b2.setBounds(348, 156, 70, 15);
/* 579 */     add(this.b2);
/*     */ 
/* 581 */     this.g2 = new JTextField();
/* 582 */     this.g2.setForeground(this.green2);
/* 583 */     this.g2.setOpaque(false);
/* 584 */     this.g2.setHorizontalAlignment(0);
/* 585 */     this.g2.setEditable(false);
/* 586 */     this.g2.setColumns(10);
/* 587 */     this.g2.setBorder(null);
/* 588 */     this.g2.setBounds(348, 141, 70, 15);
/* 589 */     add(this.g2);
/*     */ 
/* 591 */     this.b3 = new JTextField();
/* 592 */     this.b3.setVisible(false);
/* 593 */     this.b3.setForeground(this.blue3);
/* 594 */     this.b3.setOpaque(false);
/* 595 */     this.b3.setHorizontalAlignment(0);
/* 596 */     this.b3.setEditable(false);
/* 597 */     this.b3.setColumns(10);
/* 598 */     this.b3.setBorder(null);
/* 599 */     this.b3.setBounds(348, 216, 70, 15);
/* 600 */     add(this.b3);
/*     */ 
/* 602 */     this.txtMean = new JTextField();
/* 603 */     this.txtMean.setText("Mean");
/* 604 */     this.txtMean.setOpaque(false);
/* 605 */     this.txtMean.setHorizontalAlignment(0);
/* 606 */     this.txtMean.setEditable(false);
/* 607 */     this.txtMean.setColumns(10);
/* 608 */     this.txtMean.setBorder(null);
/* 609 */     this.txtMean.setBounds(348, 25, 70, 15);
/* 610 */     add(this.txtMean);
/*     */ 
/* 612 */     this.textField_1 = new JTextField();
/* 613 */     this.textField_1.setText("Intensity");
/* 614 */     this.textField_1.setHorizontalAlignment(0);
/* 615 */     this.textField_1.setForeground(Color.MAGENTA);
/* 616 */     this.textField_1.setFont(new Font("Tahoma", 0, 9));
/* 617 */     this.textField_1.setEditable(false);
/* 618 */     this.textField_1.setColumns(10);
/* 619 */     this.textField_1.setBorder(null);
/* 620 */     this.textField_1.setBounds(10, 10, 53, 16);
/* 621 */     add(this.textField_1);
/* 622 */     drawAxis();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.datapanels.PathPanel
 * JD-Core Version:    0.6.2
 */