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
/*     */ public class LinePanel extends FocusPanel
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
/*     */   Integer[] x;
/*     */   Integer[] y;
/*  54 */   private Boolean three = Boolean.valueOf(false);
/*     */ 
/*  56 */   private ArrayList<Point> redList1 = new ArrayList();
/*  57 */   private ArrayList<Point> redList2 = new ArrayList();
/*  58 */   private ArrayList<Point> redList3 = new ArrayList();
/*  59 */   private ArrayList<Point> greenList1 = new ArrayList();
/*  60 */   private ArrayList<Point> greenList2 = new ArrayList();
/*  61 */   private ArrayList<Point> greenList3 = new ArrayList();
/*  62 */   private ArrayList<Point> blueList1 = new ArrayList();
/*  63 */   private ArrayList<Point> blueList2 = new ArrayList();
/*  64 */   private ArrayList<Point> blueList3 = new ArrayList();
/*     */   private JTextField txtMean;
/*     */   private JTextField textField_1;
/*     */   private int RGB;
/*     */ 
/*     */   public LinePanel(BufferedImage image1, BufferedImage image2, BufferedImage image3, int rgb)
/*     */   {
/*  73 */     super(image1, image2, image3, rgb);
/*  74 */     this.RGB = rgb;
/*  75 */     this.img1 = image1;
/*  76 */     this.img2 = image2;
/*  77 */     this.img3 = image3;
/*     */ 
/*  79 */     setup();
/*  80 */     drawAxis();
/*  81 */     if (rgb > 0) {
/*  82 */       this.r1.setForeground(this.black1);
/*  83 */       this.g1.setForeground(this.red1);
/*  84 */       this.b1.setForeground(this.green1);
/*  85 */       this.r2.setForeground(this.black2);
/*  86 */       this.g2.setForeground(this.red2);
/*  87 */       this.b2.setForeground(this.green2);
/*  88 */       this.r3.setForeground(this.black3);
/*  89 */       this.g3.setForeground(this.red3);
/*  90 */       this.b3.setForeground(this.green3);
/*     */     }
/*  92 */     if (rgb == 2) {
/*  93 */       this.g1.setVisible(false);
/*  94 */       this.b1.setVisible(false);
/*  95 */       this.g2.setVisible(false);
/*  96 */       this.b2.setVisible(false);
/*  97 */       this.g3.setVisible(false);
/*  98 */       this.b3.setVisible(false);
/*  99 */       this.textField.setText("1");
/* 100 */       this.textField_2.setText(".5");
/* 101 */       this.textField_3.setText("0");
/* 102 */       this.textField_4.setText("-.5");
/* 103 */       this.textField_5.setText("-1");
/*     */     }
/*     */   }
/*     */ 
/* 107 */   private void drawAxis() { this.img = new BufferedImage(255, 255, 1);
/* 108 */     Graphics2D g = this.img.createGraphics();
/* 109 */     g.setColor(Color.WHITE);
/* 110 */     g.fillRect(0, 0, 255, 255);
/* 111 */     g.setColor(Color.GRAY);
/* 112 */     g.setStroke(new BasicStroke(1.0F));
/* 113 */     g.drawLine(0, 0, 255, 0);
/* 114 */     g.drawLine(0, 64, 255, 64);
/* 115 */     g.drawLine(0, 126, 255, 126);
/* 116 */     g.drawLine(0, 187, 255, 187);
/* 117 */     g.drawLine(0, 254, 255, 254);
/* 118 */     g.drawLine(0, 0, 0, 255);
/* 119 */     g.drawLine(254, 0, 254, 255);
/*     */ 
/* 121 */     g.dispose();
/* 122 */     this.label.setIcon(new ImageIcon(this.img)); }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 126 */     drawAxis();
/* 127 */     this.image1txt.setVisible(false);
/* 128 */     this.r1.setVisible(false);
/* 129 */     this.g1.setVisible(false);
/* 130 */     this.b1.setVisible(false);
/* 131 */     this.image2txt.setVisible(false);
/* 132 */     this.r2.setVisible(false);
/* 133 */     this.g2.setVisible(false);
/* 134 */     this.b2.setVisible(false);
/* 135 */     this.image3txt.setVisible(false);
/* 136 */     this.r3.setVisible(false);
/* 137 */     this.g3.setVisible(false);
/* 138 */     this.b3.setVisible(false);
/*     */   }
/*     */ 
/*     */   public void update1(Integer[] X, Integer[] Y)
/*     */   {
/* 143 */     this.x = X;
/* 144 */     this.y = Y;
/* 145 */     drawAxis();
/* 146 */     ArrayList al = ColorTools.getLinePixels(this.x, this.y);
/* 147 */     float scale = 255.0F / al.size();
/* 148 */     this.redList1 = new ArrayList();
/* 149 */     this.redList2 = new ArrayList();
/* 150 */     this.redList3 = new ArrayList();
/* 151 */     this.greenList1 = new ArrayList();
/* 152 */     this.greenList2 = new ArrayList();
/* 153 */     this.greenList3 = new ArrayList();
/* 154 */     this.blueList1 = new ArrayList();
/* 155 */     this.blueList2 = new ArrayList();
/* 156 */     this.blueList3 = new ArrayList();
/* 157 */     if (this.three.booleanValue()) {
/* 158 */       threePics();
/* 159 */       for (int i = 0; i < al.size(); i++) {
/* 160 */         int xVal = Math.round(scale * i);
/* 161 */         Point p = (Point)al.get(i);
/* 162 */         int[] rgb = ColorTools.rgb(this.img1.getRGB(p.x, p.y));
/* 163 */         this.redList1.add(new Point(xVal, rgb[0]));
/* 164 */         this.greenList1.add(new Point(xVal, rgb[1]));
/* 165 */         this.blueList1.add(new Point(xVal, rgb[2]));
/* 166 */         rgb = ColorTools.rgb(this.img2.getRGB(p.x, p.y));
/* 167 */         this.redList2.add(new Point(xVal, rgb[0]));
/* 168 */         this.greenList2.add(new Point(xVal, rgb[1]));
/* 169 */         this.blueList2.add(new Point(xVal, rgb[2]));
/* 170 */         rgb = ColorTools.rgb(this.img3.getRGB(p.x, p.y));
/* 171 */         this.redList3.add(new Point(xVal, rgb[0]));
/* 172 */         this.greenList3.add(new Point(xVal, rgb[1]));
/* 173 */         this.blueList3.add(new Point(xVal, rgb[2]));
/*     */       }
/* 175 */       draw1(); draw2(); draw3();
/*     */     } else {
/* 177 */       for (int i = 0; i < al.size(); i++) {
/* 178 */         int xVal = Math.round(scale * i);
/* 179 */         Point p = (Point)al.get(i);
/* 180 */         int[] rgb = ColorTools.rgb(this.img1.getRGB(p.x, p.y));
/* 181 */         this.redList1.add(new Point(xVal, rgb[0]));
/* 182 */         this.greenList1.add(new Point(xVal, rgb[1]));
/* 183 */         this.blueList1.add(new Point(xVal, rgb[2]));
/* 184 */         rgb = ColorTools.rgb(this.img2.getRGB(p.x, p.y));
/* 185 */         this.redList2.add(new Point(xVal, rgb[0]));
/* 186 */         this.greenList2.add(new Point(xVal, rgb[1]));
/* 187 */         this.blueList2.add(new Point(xVal, rgb[2]));
/*     */       }
/* 189 */       draw1(); draw2();
/*     */     }
/* 191 */     repaint();
/*     */   }
/*     */ 
/*     */   public void draw1()
/*     */   {
/*     */     Color B;
/*     */     Color R;
/*     */     Color G;
/* 197 */     if (this.RGB == 0) {
/* 198 */       R = this.red1;
/* 199 */       G = this.green1;
/* 200 */       B = this.blue1;
/*     */     } else {
/* 202 */       R = this.black1;
/* 203 */       G = this.red1;
/* 204 */       B = this.green1;
/*     */     }
/* 206 */     Float r = Float.valueOf(0.0F);
/* 207 */     Integer gr = Integer.valueOf(0);
/* 208 */     Integer b = Integer.valueOf(0);
/* 209 */     Float size = Float.valueOf(this.redList1.size() - 1.0F);
/* 210 */     ArrayList al = ColorTools.getLinePixels(this.x, this.y);
/* 211 */     for (int i = 0; i < size.floatValue(); i++) {
/* 212 */       Graphics2D g = this.img.createGraphics();
/* 213 */       g.setStroke(new BasicStroke(2.0F));
/* 214 */       g.setColor(R);
/* 215 */       Point p = (Point)this.redList1.get(i);
/* 216 */       if (this.RGB < 2) {
/* 217 */         g.drawLine(p.x, 255 - p.y, ((Point)this.redList1.get(i + 1)).x, 255 - ((Point)this.redList1.get(i + 1)).y);
/* 218 */         r = Float.valueOf(r.floatValue() + p.y);
/* 219 */         g.setColor(G);
/* 220 */         p = (Point)this.greenList1.get(i);
/* 221 */         g.drawLine(p.x, 255 - p.y, ((Point)this.greenList1.get(i + 1)).x, 255 - ((Point)this.greenList1.get(i + 1)).y);
/* 222 */         gr = Integer.valueOf(gr.intValue() + p.y);
/* 223 */         g.setColor(B);
/* 224 */         p = (Point)this.blueList1.get(i);
/* 225 */         g.drawLine(p.x, 255 - p.y, ((Point)this.blueList1.get(i + 1)).x, 255 - ((Point)this.blueList1.get(i + 1)).y);
/* 226 */         b = Integer.valueOf(b.intValue() + p.y);
/*     */       } else {
/* 228 */         r = Float.valueOf(r.floatValue() + ColorTools.colorToNDVI(this.img1.getRGB(((Point)al.get(i)).x, ((Point)al.get(i)).y)));
/* 229 */         g.drawLine(p.x, 128 - Math.round(126.0F * ColorTools.colorToNDVI(this.img1.getRGB(((Point)al.get(i)).x, ((Point)al.get(i)).y))), ((Point)this.redList1.get(i + 1)).x, 128 - Math.round(126.0F * ColorTools.colorToNDVI(this.img1.getRGB(((Point)al.get(i + 1)).x, ((Point)al.get(i + 1)).y))));
/*     */       }
/* 231 */       g.dispose();
/*     */     }
/* 233 */     if (size.floatValue() == 0.0F) {
/* 234 */       this.r1.setText("");
/* 235 */       this.g1.setText("");
/* 236 */       this.b1.setText("");
/*     */     } else {
/* 238 */       this.r1.setText(this.df.format(r.floatValue() / size.floatValue()));
/* 239 */       this.g1.setText(this.df.format(gr.intValue() / size.floatValue()));
/* 240 */       this.b1.setText(this.df.format(b.intValue() / size.floatValue()));
/*     */     }
/* 242 */     this.image1txt.setVisible(true);
/* 243 */     this.r1.setVisible(true);
/* 244 */     if (this.RGB < 2) {
/* 245 */       this.g1.setVisible(true);
/* 246 */       this.b1.setVisible(true);
/*     */     }
/* 248 */     repaint();
/*     */   }
/*     */ 
/*     */   public void draw2()
/*     */   {
/*     */     Color B;
/*     */     Color R;
/*     */     Color G;
/* 254 */     if (this.RGB == 0) {
/* 255 */       R = this.red2;
/* 256 */       G = this.green2;
/* 257 */       B = this.blue2;
/*     */     } else {
/* 259 */       R = this.black2;
/* 260 */       G = this.red2;
/* 261 */       B = this.green2;
/*     */     }
/* 263 */     Float r = Float.valueOf(0.0F);
/* 264 */     Integer gr = Integer.valueOf(0);
/* 265 */     Integer b = Integer.valueOf(0);
/* 266 */     Float size = Float.valueOf(this.redList2.size() - 1.0F);
/* 267 */     ArrayList al = ColorTools.getLinePixels(this.x, this.y);
/* 268 */     for (int i = 0; i < size.floatValue(); i++) {
/* 269 */       Graphics2D g = this.img.createGraphics();
/* 270 */       g.setStroke(new BasicStroke(2.0F));
/* 271 */       g.setColor(R);
/* 272 */       Point p = (Point)this.redList2.get(i);
/* 273 */       if (this.RGB < 2) {
/* 274 */         g.drawLine(p.x, 255 - p.y, ((Point)this.redList2.get(i + 1)).x, 255 - ((Point)this.redList2.get(i + 1)).y);
/* 275 */         r = Float.valueOf(r.floatValue() + p.y);
/* 276 */         g.setColor(G);
/* 277 */         p = (Point)this.greenList2.get(i);
/* 278 */         g.drawLine(p.x, 255 - p.y, ((Point)this.greenList2.get(i + 1)).x, 255 - ((Point)this.greenList2.get(i + 1)).y);
/* 279 */         gr = Integer.valueOf(gr.intValue() + p.y);
/* 280 */         g.setColor(B);
/* 281 */         p = (Point)this.blueList2.get(i);
/* 282 */         g.drawLine(p.x, 255 - p.y, ((Point)this.blueList2.get(i + 1)).x, 255 - ((Point)this.blueList2.get(i + 1)).y);
/* 283 */         b = Integer.valueOf(b.intValue() + p.y);
/*     */       } else {
/* 285 */         r = Float.valueOf(r.floatValue() + ColorTools.colorToNDVI(this.img2.getRGB(((Point)al.get(i)).x, ((Point)al.get(i)).y)));
/* 286 */         g.drawLine(p.x, 128 - Math.round(126.0F * ColorTools.colorToNDVI(this.img2.getRGB(((Point)al.get(i)).x, ((Point)al.get(i)).y))), ((Point)this.redList2.get(i + 1)).x, 128 - Math.round(126.0F * ColorTools.colorToNDVI(this.img2.getRGB(((Point)al.get(i + 1)).x, ((Point)al.get(i + 1)).y))));
/*     */       }
/* 288 */       g.dispose();
/*     */     }
/* 290 */     if (size.floatValue() == 0.0F) {
/* 291 */       this.r2.setText("");
/* 292 */       this.g2.setText("");
/* 293 */       this.b2.setText("");
/*     */     } else {
/* 295 */       this.r2.setText(this.df.format(r.floatValue() / size.floatValue()));
/* 296 */       this.g2.setText(this.df.format(gr.intValue() / size.floatValue()));
/* 297 */       this.b2.setText(this.df.format(b.intValue() / size.floatValue()));
/*     */     }
/* 299 */     this.image2txt.setVisible(true);
/* 300 */     this.r2.setVisible(true);
/* 301 */     if (this.RGB < 2) {
/* 302 */       this.g2.setVisible(true);
/* 303 */       this.b2.setVisible(true);
/*     */     }
/* 305 */     repaint();
/*     */   }
/*     */ 
/*     */   public void draw3()
/*     */   {
/*     */     Color B;
/*     */     Color R;
/*     */     Color G;
/* 311 */     if (this.RGB == 0) {
/* 312 */       R = this.red3;
/* 313 */       G = this.green3;
/* 314 */       B = this.blue3;
/*     */     } else {
/* 316 */       R = this.black3;
/* 317 */       G = this.red3;
/* 318 */       B = this.green3;
/*     */     }
/* 320 */     Float r = Float.valueOf(0.0F);
/* 321 */     Float gr = Float.valueOf(0.0F);
/* 322 */     Float b = Float.valueOf(0.0F);
/* 323 */     Float size = Float.valueOf(this.redList3.size() - 1.0F);
/* 324 */     ArrayList al = ColorTools.getLinePixels(this.x, this.y);
/* 325 */     for (int i = 0; i < size.floatValue(); i++) {
/* 326 */       Graphics2D g = this.img.createGraphics();
/* 327 */       g.setStroke(new BasicStroke(2.0F));
/* 328 */       g.setColor(R);
/* 329 */       Point p = (Point)this.redList3.get(i);
/* 330 */       if (this.RGB < 2) {
/* 331 */         g.drawLine(p.x, 255 - p.y, ((Point)this.redList3.get(i + 1)).x, 255 - ((Point)this.redList3.get(i + 1)).y);
/* 332 */         r = Float.valueOf(r.floatValue() + p.y);
/* 333 */         g.setColor(G);
/* 334 */         p = (Point)this.greenList3.get(i);
/* 335 */         g.drawLine(p.x, 255 - p.y, ((Point)this.greenList3.get(i + 1)).x, 255 - ((Point)this.greenList3.get(i + 1)).y);
/* 336 */         gr = Float.valueOf(gr.floatValue() + p.y);
/* 337 */         g.setColor(B);
/* 338 */         p = (Point)this.blueList3.get(i);
/* 339 */         g.drawLine(p.x, 255 - p.y, ((Point)this.blueList3.get(i + 1)).x, 255 - ((Point)this.blueList3.get(i + 1)).y);
/* 340 */         b = Float.valueOf(b.floatValue() + p.y);
/*     */       } else {
/* 342 */         r = Float.valueOf(r.floatValue() + ColorTools.colorToNDVI(this.img3.getRGB(((Point)al.get(i)).x, ((Point)al.get(i)).y)));
/* 343 */         g.drawLine(p.x, 128 - Math.round(126.0F * ColorTools.colorToNDVI(this.img3.getRGB(((Point)al.get(i)).x, ((Point)al.get(i)).y))), ((Point)this.redList3.get(i + 1)).x, 128 - Math.round(126.0F * ColorTools.colorToNDVI(this.img3.getRGB(((Point)al.get(i + 1)).x, ((Point)al.get(i + 1)).y))));
/*     */       }
/* 345 */       g.dispose();
/*     */     }
/* 347 */     if (size.floatValue() == 0.0F) {
/* 348 */       this.r3.setText("");
/* 349 */       this.g3.setText("");
/* 350 */       this.b3.setText("");
/*     */     } else {
/* 352 */       this.r3.setText(this.df.format(r.floatValue() / size.floatValue()));
/* 353 */       this.g3.setText(this.df.format(gr.floatValue() / size.floatValue()));
/* 354 */       this.b3.setText(this.df.format(b.floatValue() / size.floatValue()));
/*     */     }
/* 356 */     this.image3txt.setVisible(true);
/* 357 */     this.r3.setVisible(true);
/* 358 */     if (this.RGB < 2) {
/* 359 */       this.g3.setVisible(true);
/* 360 */       this.b3.setVisible(true);
/*     */     }
/* 362 */     repaint();
/*     */   }
/*     */ 
/*     */   public void update2(Integer[] x, Integer[] y) {
/*     */   }
/*     */ 
/*     */   public void update3(Integer[] x, Integer[] y) {
/*     */   }
/*     */ 
/*     */   public void threePics() {
/* 372 */     this.image3txt.setVisible(true);
/* 373 */     this.r3.setVisible(true);
/* 374 */     this.g3.setVisible(true);
/* 375 */     this.b3.setVisible(true);
/* 376 */     this.three = Boolean.valueOf(true);
/*     */   }
/*     */ 
/*     */   private void setup() {
/* 380 */     this.textField = new JTextField();
/* 381 */     this.textField.setText("100%");
/* 382 */     this.textField.setOpaque(false);
/* 383 */     this.textField.setHorizontalAlignment(11);
/* 384 */     this.textField.setFont(new Font("Tahoma", 0, 10));
/* 385 */     this.textField.setEditable(false);
/* 386 */     this.textField.setColumns(10);
/* 387 */     this.textField.setBorder(null);
/* 388 */     this.textField.setBounds(23, 25, 40, 10);
/* 389 */     add(this.textField);
/*     */ 
/* 391 */     this.txtColorIntensitiesAlong = new JTextField();
/* 392 */     this.txtColorIntensitiesAlong.setText("Color Intensities Along Line");
/* 393 */     this.txtColorIntensitiesAlong.setOpaque(false);
/* 394 */     this.txtColorIntensitiesAlong.setHorizontalAlignment(0);
/* 395 */     this.txtColorIntensitiesAlong.setEditable(false);
/* 396 */     this.txtColorIntensitiesAlong.setColumns(10);
/* 397 */     this.txtColorIntensitiesAlong.setBorder(null);
/* 398 */     this.txtColorIntensitiesAlong.setBounds(73, 11, 250, 20);
/* 399 */     add(this.txtColorIntensitiesAlong);
/*     */ 
/* 401 */     this.textField_2 = new JTextField();
/* 402 */     this.textField_2.setText("75%");
/* 403 */     this.textField_2.setOpaque(false);
/* 404 */     this.textField_2.setHorizontalAlignment(11);
/* 405 */     this.textField_2.setFont(new Font("Tahoma", 0, 10));
/* 406 */     this.textField_2.setEditable(false);
/* 407 */     this.textField_2.setColumns(10);
/* 408 */     this.textField_2.setBorder(null);
/* 409 */     this.textField_2.setBounds(23, 87, 40, 10);
/* 410 */     add(this.textField_2);
/*     */ 
/* 412 */     this.textField_3 = new JTextField();
/* 413 */     this.textField_3.setText("50%");
/* 414 */     this.textField_3.setOpaque(false);
/* 415 */     this.textField_3.setHorizontalAlignment(11);
/* 416 */     this.textField_3.setFont(new Font("Tahoma", 0, 10));
/* 417 */     this.textField_3.setEditable(false);
/* 418 */     this.textField_3.setColumns(10);
/* 419 */     this.textField_3.setBorder(null);
/* 420 */     this.textField_3.setBounds(23, 149, 40, 10);
/* 421 */     add(this.textField_3);
/*     */ 
/* 423 */     this.textField_4 = new JTextField();
/* 424 */     this.textField_4.setText("25%");
/* 425 */     this.textField_4.setOpaque(false);
/* 426 */     this.textField_4.setHorizontalAlignment(11);
/* 427 */     this.textField_4.setFont(new Font("Tahoma", 0, 10));
/* 428 */     this.textField_4.setEditable(false);
/* 429 */     this.textField_4.setColumns(10);
/* 430 */     this.textField_4.setBorder(null);
/* 431 */     this.textField_4.setBounds(23, 211, 40, 10);
/* 432 */     add(this.textField_4);
/*     */ 
/* 434 */     this.textField_5 = new JTextField();
/* 435 */     this.textField_5.setText("0%");
/* 436 */     this.textField_5.setOpaque(false);
/* 437 */     this.textField_5.setHorizontalAlignment(11);
/* 438 */     this.textField_5.setFont(new Font("Tahoma", 0, 10));
/* 439 */     this.textField_5.setEditable(false);
/* 440 */     this.textField_5.setColumns(10);
/* 441 */     this.textField_5.setBorder(null);
/* 442 */     this.textField_5.setBounds(23, 273, 40, 10);
/* 443 */     add(this.textField_5);
/*     */ 
/* 445 */     this.label = new JLabel();
/* 446 */     this.label.setBounds(73, 29, 255, 255);
/* 447 */     add(this.label);
/*     */ 
/* 449 */     this.image1txt = new JTextField();
/* 450 */     this.image1txt.setBorder(null);
/* 451 */     this.image1txt.setOpaque(false);
/* 452 */     this.image1txt.setEditable(false);
/* 453 */     this.image1txt.setHorizontalAlignment(0);
/* 454 */     this.image1txt.setText("Image 1");
/* 455 */     this.image1txt.setBounds(348, 53, 70, 15);
/* 456 */     add(this.image1txt);
/* 457 */     this.image1txt.setColumns(10);
/*     */ 
/* 459 */     this.r1 = new JTextField();
/* 460 */     this.r1.setForeground(this.red1);
/* 461 */     this.r1.setOpaque(false);
/* 462 */     this.r1.setHorizontalAlignment(0);
/* 463 */     this.r1.setEditable(false);
/* 464 */     this.r1.setColumns(10);
/* 465 */     this.r1.setBorder(null);
/* 466 */     this.r1.setBounds(348, 68, 70, 15);
/* 467 */     add(this.r1);
/*     */ 
/* 469 */     this.g1 = new JTextField();
/* 470 */     this.g1.setForeground(this.green1);
/* 471 */     this.g1.setOpaque(false);
/* 472 */     this.g1.setHorizontalAlignment(0);
/* 473 */     this.g1.setEditable(false);
/* 474 */     this.g1.setColumns(10);
/* 475 */     this.g1.setBorder(null);
/* 476 */     this.g1.setBounds(348, 83, 70, 15);
/* 477 */     add(this.g1);
/*     */ 
/* 479 */     this.b1 = new JTextField();
/* 480 */     this.b1.setForeground(this.blue1);
/* 481 */     this.b1.setOpaque(false);
/* 482 */     this.b1.setHorizontalAlignment(0);
/* 483 */     this.b1.setEditable(false);
/* 484 */     this.b1.setColumns(10);
/* 485 */     this.b1.setBorder(null);
/* 486 */     this.b1.setBounds(348, 98, 70, 15);
/* 487 */     add(this.b1);
/*     */ 
/* 489 */     this.image2txt = new JTextField();
/* 490 */     this.image2txt.setForeground(this.black2);
/* 491 */     this.image2txt.setText("Image 2");
/* 492 */     this.image2txt.setOpaque(false);
/* 493 */     this.image2txt.setHorizontalAlignment(0);
/* 494 */     this.image2txt.setEditable(false);
/* 495 */     this.image2txt.setColumns(10);
/* 496 */     this.image2txt.setBorder(null);
/* 497 */     this.image2txt.setBounds(348, 113, 70, 15);
/* 498 */     add(this.image2txt);
/*     */ 
/* 500 */     this.r2 = new JTextField();
/* 501 */     this.r2.setForeground(this.red2);
/* 502 */     this.r2.setOpaque(false);
/* 503 */     this.r2.setHorizontalAlignment(0);
/* 504 */     this.r2.setEditable(false);
/* 505 */     this.r2.setColumns(10);
/* 506 */     this.r2.setBorder(null);
/* 507 */     this.r2.setBounds(348, 128, 70, 15);
/* 508 */     add(this.r2);
/*     */ 
/* 510 */     this.g3 = new JTextField();
/* 511 */     this.g3.setVisible(false);
/* 512 */     this.g3.setForeground(this.green3);
/* 513 */     this.g3.setOpaque(false);
/* 514 */     this.g3.setHorizontalAlignment(0);
/* 515 */     this.g3.setEditable(false);
/* 516 */     this.g3.setColumns(10);
/* 517 */     this.g3.setBorder(null);
/* 518 */     this.g3.setBounds(348, 203, 70, 15);
/* 519 */     add(this.g3);
/*     */ 
/* 521 */     this.r3 = new JTextField();
/* 522 */     this.r3.setVisible(false);
/* 523 */     this.r3.setForeground(this.red3);
/* 524 */     this.r3.setOpaque(false);
/* 525 */     this.r3.setHorizontalAlignment(0);
/* 526 */     this.r3.setEditable(false);
/* 527 */     this.r3.setColumns(10);
/* 528 */     this.r3.setBorder(null);
/* 529 */     this.r3.setBounds(348, 188, 70, 15);
/* 530 */     add(this.r3);
/*     */ 
/* 532 */     this.image3txt = new JTextField();
/* 533 */     this.image3txt.setVisible(false);
/* 534 */     this.image3txt.setForeground(this.black3);
/* 535 */     this.image3txt.setText("Image 3");
/* 536 */     this.image3txt.setOpaque(false);
/* 537 */     this.image3txt.setHorizontalAlignment(0);
/* 538 */     this.image3txt.setEditable(false);
/* 539 */     this.image3txt.setColumns(10);
/* 540 */     this.image3txt.setBorder(null);
/* 541 */     this.image3txt.setBounds(348, 173, 70, 15);
/* 542 */     add(this.image3txt);
/*     */ 
/* 544 */     this.b2 = new JTextField();
/* 545 */     this.b2.setForeground(this.blue2);
/* 546 */     this.b2.setOpaque(false);
/* 547 */     this.b2.setHorizontalAlignment(0);
/* 548 */     this.b2.setEditable(false);
/* 549 */     this.b2.setColumns(10);
/* 550 */     this.b2.setBorder(null);
/* 551 */     this.b2.setBounds(348, 158, 70, 15);
/* 552 */     add(this.b2);
/*     */ 
/* 554 */     this.g2 = new JTextField();
/* 555 */     this.g2.setForeground(this.green2);
/* 556 */     this.g2.setOpaque(false);
/* 557 */     this.g2.setHorizontalAlignment(0);
/* 558 */     this.g2.setEditable(false);
/* 559 */     this.g2.setColumns(10);
/* 560 */     this.g2.setBorder(null);
/* 561 */     this.g2.setBounds(348, 143, 70, 15);
/* 562 */     add(this.g2);
/*     */ 
/* 564 */     this.b3 = new JTextField();
/* 565 */     this.b3.setVisible(false);
/* 566 */     this.b3.setForeground(this.blue3);
/* 567 */     this.b3.setOpaque(false);
/* 568 */     this.b3.setHorizontalAlignment(0);
/* 569 */     this.b3.setEditable(false);
/* 570 */     this.b3.setColumns(10);
/* 571 */     this.b3.setBorder(null);
/* 572 */     this.b3.setBounds(348, 218, 70, 15);
/* 573 */     add(this.b3);
/*     */ 
/* 575 */     this.txtMean = new JTextField();
/* 576 */     this.txtMean.setText("Mean");
/* 577 */     this.txtMean.setOpaque(false);
/* 578 */     this.txtMean.setHorizontalAlignment(0);
/* 579 */     this.txtMean.setEditable(false);
/* 580 */     this.txtMean.setColumns(10);
/* 581 */     this.txtMean.setBorder(null);
/* 582 */     this.txtMean.setBounds(348, 25, 70, 15);
/* 583 */     add(this.txtMean);
/*     */ 
/* 585 */     this.textField_1 = new JTextField();
/* 586 */     this.textField_1.setText("Intensity");
/* 587 */     this.textField_1.setHorizontalAlignment(0);
/* 588 */     this.textField_1.setForeground(Color.MAGENTA);
/* 589 */     this.textField_1.setFont(new Font("Tahoma", 0, 9));
/* 590 */     this.textField_1.setEditable(false);
/* 591 */     this.textField_1.setColumns(10);
/* 592 */     this.textField_1.setBorder(null);
/* 593 */     this.textField_1.setBounds(10, 10, 53, 16);
/* 594 */     add(this.textField_1);
/* 595 */     drawAxis();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.datapanels.LinePanel
 * JD-Core Version:    0.6.2
 */