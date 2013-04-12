/*     */ package org.gss.adi.datapanels;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Point;
/*     */ import java.awt.Polygon;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.ArrayList;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JTextField;
/*     */ import org.gss.adi.tools.ColorTools;
/*     */ 
/*     */ public class PolygonPanel extends FocusPanel
/*     */ {
/*     */   private static final long serialVersionUID = 2233417006392535510L;
/*     */   private JTextField textField;
/*     */   private JTextField txtRelativeFrequencyOf;
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
/*  51 */   private Boolean three = Boolean.valueOf(false);
/*     */ 
/*  53 */   private int[] redList1 = new int[256];
/*  54 */   private int[] redList2 = new int[256];
/*  55 */   private int[] redList3 = new int[256];
/*  56 */   private int[] greenList1 = new int[256];
/*  57 */   private int[] greenList2 = new int[256];
/*  58 */   private int[] greenList3 = new int[256];
/*  59 */   private int[] blueList1 = new int[256];
/*  60 */   private int[] blueList2 = new int[256];
/*  61 */   private int[] blueList3 = new int[256];
/*     */   private float max1;
/*     */   private float max2;
/*     */   private float max3;
/*     */   private JTextField intens1;
/*     */   private JTextField intens2;
/*     */   private JTextField intens3;
/*     */   private JTextField intens4;
/*     */   private JTextField intens5;
/*     */   private JTextField txtMean;
/*     */   private JTextField txtFrequency;
/*     */   private JTextField intensity;
/*     */   private int RGB;
/*     */ 
/*     */   public PolygonPanel(BufferedImage image1, BufferedImage image2, BufferedImage image3, int rgb)
/*     */   {
/*  80 */     super(image1, image2, image3, rgb);
/*  81 */     this.RGB = rgb;
/*  82 */     this.img1 = image1;
/*  83 */     this.img2 = image2;
/*  84 */     this.img3 = image3;
/*  85 */     setup();
/*  86 */     if (rgb != 0) {
/*  87 */       this.r1.setForeground(this.black1);
/*  88 */       this.g1.setForeground(this.red1);
/*  89 */       this.b1.setForeground(this.green1);
/*  90 */       this.r2.setForeground(this.black2);
/*  91 */       this.g2.setForeground(this.red2);
/*  92 */       this.b2.setForeground(this.green2);
/*  93 */       this.r3.setForeground(this.black3);
/*  94 */       this.g3.setForeground(this.red3);
/*  95 */       this.b3.setForeground(this.green3);
/*     */     }
/*  97 */     if (rgb == 2) {
/*  98 */       this.g1.setVisible(false);
/*  99 */       this.b1.setVisible(false);
/* 100 */       this.g2.setVisible(false);
/* 101 */       this.b2.setVisible(false);
/* 102 */       this.g3.setVisible(false);
/* 103 */       this.b3.setVisible(false);
/* 104 */       this.intens1.setText("-1");
/* 105 */       this.intens2.setText("-.5");
/* 106 */       this.intens3.setText("0");
/* 107 */       this.intens4.setText(".5");
/* 108 */       this.intens5.setText("1");
/* 109 */       this.intensity.setText("NDVI value");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 115 */     drawAxis();
/* 116 */     this.image1txt.setVisible(false);
/* 117 */     this.r1.setVisible(false);
/* 118 */     this.g1.setVisible(false);
/* 119 */     this.b1.setVisible(false);
/* 120 */     this.image2txt.setVisible(false);
/* 121 */     this.r2.setVisible(false);
/* 122 */     this.g2.setVisible(false);
/* 123 */     this.b2.setVisible(false);
/* 124 */     this.image3txt.setVisible(false);
/* 125 */     this.r3.setVisible(false);
/* 126 */     this.g3.setVisible(false);
/* 127 */     this.b3.setVisible(false);
/*     */   }
/*     */ 
/*     */   public void draw1()
/*     */   {
/*     */     Color B;
/*     */     Color R;
/*     */     Color G;
/* 135 */     if (this.RGB == 0) {
/* 136 */       R = this.red1;
/* 137 */       G = this.green1;
/* 138 */       B = this.blue1;
/*     */     } else {
/* 140 */       R = this.black1;
/* 141 */       G = this.red1;
/* 142 */       B = this.green1;
/*     */     }
/* 144 */     Float r = Float.valueOf(0.0F);
/* 145 */     Integer gr = Integer.valueOf(0);
/* 146 */     Integer b = Integer.valueOf(0);
/* 147 */     float size = 0.0F;
/* 148 */     for (int i = 0; i < 255; i++) {
/* 149 */       Graphics2D g = this.img.createGraphics();
/* 150 */       g.setStroke(new BasicStroke(2.0F));
/* 151 */       g.setColor(R);
/* 152 */       g.drawLine(i, 255 - Math.round(255.0F * this.redList1[i] / this.max1), i + 1, 255 - Math.round(255.0F * this.redList1[(i + 1)] / this.max1));
/* 153 */       size += this.redList1[i];
/* 154 */       if (this.RGB < 2) {
/* 155 */         r = Float.valueOf(r.floatValue() + this.redList1[i] * i);
/* 156 */         g.setColor(G);
/* 157 */         gr = Integer.valueOf(gr.intValue() + this.greenList1[i] * i);
/* 158 */         g.drawLine(i, 255 - Math.round(255.0F * this.greenList1[i] / this.max1), i + 1, 255 - Math.round(255.0F * this.greenList1[(i + 1)] / this.max1));
/* 159 */         g.setColor(B);
/* 160 */         b = Integer.valueOf(b.intValue() + this.blueList1[i] * i);
/* 161 */         g.drawLine(i, 255 - Math.round(255.0F * this.blueList1[i] / this.max1), i + 1, 255 - Math.round(255.0F * this.blueList1[(i + 1)] / this.max1));
/*     */       } else {
/* 163 */         r = Float.valueOf(r.floatValue() + this.redList1[i] * (i - 127.0F) / 127.0F);
/*     */       }
/* 165 */       g.dispose();
/*     */     }
/* 167 */     if (size == 0.0F) {
/* 168 */       this.r1.setText("");
/* 169 */       this.g1.setText("");
/* 170 */       this.b1.setText("");
/*     */     } else {
/* 172 */       this.r1.setText(this.df.format(r.floatValue() / size));
/* 173 */       this.g1.setText(this.df.format(gr.intValue() / size));
/* 174 */       this.b1.setText(this.df.format(b.intValue() / size));
/*     */     }
/* 176 */     this.image1txt.setVisible(true);
/* 177 */     this.r1.setVisible(true);
/* 178 */     if (this.RGB < 2) {
/* 179 */       this.g1.setVisible(true);
/* 180 */       this.b1.setVisible(true);
/*     */     }
/* 182 */     repaint();
/*     */   }
/*     */ 
/*     */   public void draw2()
/*     */   {
/*     */     Color B;
/*     */     Color R;
/*     */     Color G;
/* 190 */     if (this.RGB == 0) {
/* 191 */       R = this.red2;
/* 192 */       G = this.green2;
/* 193 */       B = this.blue2;
/*     */     } else {
/* 195 */       R = this.black3;
/* 196 */       G = this.red3;
/* 197 */       B = this.green3;
/*     */     }
/* 199 */     Float r = Float.valueOf(0.0F);
/* 200 */     Integer gr = Integer.valueOf(0);
/* 201 */     Integer b = Integer.valueOf(0);
/* 202 */     float size = 0.0F;
/* 203 */     for (int i = 0; i < 255; i++) {
/* 204 */       Graphics2D g = this.img.createGraphics();
/* 205 */       g.setStroke(new BasicStroke(2.0F));
/* 206 */       g.setColor(R);
/* 207 */       g.drawLine(i, 255 - Math.round(255.0F * this.redList2[i] / this.max2), i + 1, 255 - Math.round(255.0F * this.redList2[(i + 1)] / this.max2));
/* 208 */       size += this.redList2[i];
/* 209 */       if (this.RGB < 2) {
/* 210 */         r = Float.valueOf(r.floatValue() + this.redList2[i] * i);
/* 211 */         g.setColor(G);
/* 212 */         gr = Integer.valueOf(gr.intValue() + this.greenList2[i] * i);
/* 213 */         g.drawLine(i, 255 - Math.round(255.0F * this.greenList2[i] / this.max2), i + 1, 255 - Math.round(255.0F * this.greenList2[(i + 1)] / this.max2));
/* 214 */         g.setColor(B);
/* 215 */         b = Integer.valueOf(b.intValue() + this.blueList2[i] * i);
/* 216 */         g.drawLine(i, 255 - Math.round(255.0F * this.blueList2[i] / this.max2), i + 1, 255 - Math.round(255.0F * this.blueList2[(i + 1)] / this.max2));
/*     */       } else {
/* 218 */         r = Float.valueOf(r.floatValue() + this.redList2[i] * (i - 127.0F) / 127.0F);
/*     */       }
/* 220 */       g.dispose();
/*     */     }
/* 222 */     if (size == 0.0F) {
/* 223 */       this.r2.setText("");
/* 224 */       this.g2.setText("");
/* 225 */       this.b2.setText("");
/*     */     } else {
/* 227 */       this.r2.setText(this.df.format(r.floatValue() / size));
/* 228 */       this.g2.setText(this.df.format(gr.intValue() / size));
/* 229 */       this.b2.setText(this.df.format(b.intValue() / size));
/*     */     }
/* 231 */     this.image2txt.setVisible(true);
/* 232 */     this.r2.setVisible(true);
/* 233 */     if (this.RGB < 2) {
/* 234 */       this.g2.setVisible(true);
/* 235 */       this.b2.setVisible(true);
/*     */     }
/* 237 */     repaint();
/*     */   }
/*     */ 
/*     */   public void draw3()
/*     */   {
/*     */     Color B;
/*     */     Color R;
/*     */     Color G;
/* 245 */     if (this.RGB == 0) {
/* 246 */       R = this.red3;
/* 247 */       G = this.green3;
/* 248 */       B = this.blue3;
/*     */     } else {
/* 250 */       R = this.black3;
/* 251 */       G = this.red3;
/* 252 */       B = this.green3;
/*     */     }
/* 254 */     Float r = Float.valueOf(0.0F);
/* 255 */     Integer gr = Integer.valueOf(0);
/* 256 */     Integer b = Integer.valueOf(0);
/* 257 */     float size = 0.0F;
/* 258 */     for (int i = 0; i < 255; i++) {
/* 259 */       Graphics2D g = this.img.createGraphics();
/* 260 */       g.setStroke(new BasicStroke(2.0F));
/* 261 */       g.setColor(R);
/* 262 */       g.drawLine(i, 255 - Math.round(255.0F * this.redList3[i] / this.max3), i + 1, 255 - Math.round(255.0F * this.redList3[(i + 1)] / this.max3));
/* 263 */       size += this.redList3[i];
/* 264 */       if (this.RGB < 2) {
/* 265 */         r = Float.valueOf(r.floatValue() + this.redList3[i] * i);
/* 266 */         g.setColor(G);
/* 267 */         gr = Integer.valueOf(gr.intValue() + this.greenList3[i] * i);
/* 268 */         g.drawLine(i, 255 - Math.round(255.0F * this.greenList3[i] / this.max3), i + 1, 255 - Math.round(255.0F * this.greenList3[(i + 1)] / this.max3));
/* 269 */         g.setColor(B);
/* 270 */         b = Integer.valueOf(b.intValue() + this.blueList3[i] * i);
/* 271 */         g.drawLine(i, 255 - Math.round(255.0F * this.blueList3[i] / this.max3), i + 1, 255 - Math.round(255.0F * this.blueList3[(i + 1)] / this.max3));
/*     */       } else {
/* 273 */         r = Float.valueOf(r.floatValue() + this.redList3[i] * (i - 127.0F) / 127.0F);
/*     */       }
/* 275 */       g.dispose();
/*     */     }
/* 277 */     if (size == 0.0F) {
/* 278 */       this.r3.setText("");
/* 279 */       this.g3.setText("");
/* 280 */       this.b3.setText("");
/*     */     } else {
/* 282 */       this.r3.setText(this.df.format(r.floatValue() / size));
/* 283 */       this.g3.setText(this.df.format(gr.intValue() / size));
/* 284 */       this.b3.setText(this.df.format(b.intValue() / size));
/*     */     }
/* 286 */     this.image3txt.setVisible(true);
/* 287 */     this.r3.setVisible(true);
/* 288 */     if (this.RGB < 2) {
/* 289 */       this.g3.setVisible(true);
/* 290 */       this.b3.setVisible(true);
/*     */     }
/* 292 */     repaint();
/*     */   }
/*     */ 
/*     */   public void update1(Integer[] x, Integer[] y)
/*     */   {
/* 297 */     drawAxis();
/* 298 */     for (int i = 0; i < 256; i++) {
/* 299 */       this.redList1[i] = 0;
/* 300 */       this.redList2[i] = 0;
/* 301 */       this.redList3[i] = 0;
/* 302 */       this.greenList1[i] = 0;
/* 303 */       this.greenList2[i] = 0;
/* 304 */       this.greenList3[i] = 0;
/* 305 */       this.blueList1[i] = 0;
/* 306 */       this.blueList2[i] = 0;
/* 307 */       this.blueList3[i] = 0;
/*     */     }
/* 309 */     this.max1 = 0.0F;
/* 310 */     this.max2 = 0.0F;
/* 311 */     this.max3 = 0.0F;
/* 312 */     ArrayList al = polyArea(x, y);
/* 313 */     if (this.three.booleanValue()) {
/* 314 */       threePics();
/* 315 */       for (int i = 0; i < al.size(); i++) {
/* 316 */         Point p = (Point)al.get(i);
/* 317 */         int[] rgb = ColorTools.rgb(this.img1.getRGB(p.x, p.y));
/* 318 */         if (this.RGB < 2)
/*     */         {
/* 319 */           int tmp173_172 = rgb[0];
/*     */           int[] tmp173_166 = this.redList1;
/*     */           int tmp175_174 = tmp173_166[tmp173_172]; tmp173_166[tmp173_172] = (tmp175_174 + 1); if (tmp175_174 > this.max1) this.max1 = this.redList1[rgb[0]];
/* 320 */           int tmp210_209 = rgb[1];
/*     */           int[] tmp210_203 = this.greenList1;
/*     */           int tmp212_211 = tmp210_203[tmp210_209]; tmp210_203[tmp210_209] = (tmp212_211 + 1); if (tmp212_211 > this.max1) this.max1 = this.greenList1[rgb[1]];
/* 321 */           int tmp247_246 = rgb[2];
/*     */           int[] tmp247_240 = this.blueList1;
/*     */           int tmp249_248 = tmp247_240[tmp247_246]; tmp247_240[tmp247_246] = (tmp249_248 + 1); if (tmp249_248 > this.max1) this.max1 = this.blueList1[rgb[2]];
/* 322 */           rgb = ColorTools.rgb(this.img2.getRGB(p.x, p.y));
/*     */           int tmp306_305 = rgb[0];
/*     */           int[] tmp306_299 = this.redList2;
/*     */           int tmp308_307 = tmp306_299[tmp306_305]; tmp306_299[tmp306_305] = (tmp308_307 + 1); if (tmp308_307 > this.max2) this.max2 = this.redList2[rgb[0]];
/* 324 */           int tmp343_342 = rgb[1];
/*     */           int[] tmp343_336 = this.greenList2;
/*     */           int tmp345_344 = tmp343_336[tmp343_342]; tmp343_336[tmp343_342] = (tmp345_344 + 1); if (tmp345_344 > this.max2) this.max2 = this.greenList2[rgb[1]];
/* 325 */           int tmp380_379 = rgb[2];
/*     */           int[] tmp380_373 = this.blueList2;
/*     */           int tmp382_381 = tmp380_373[tmp380_379]; tmp380_373[tmp380_379] = (tmp382_381 + 1); if (tmp382_381 > this.max2) this.max2 = this.blueList2[rgb[2]];
/* 326 */           rgb = ColorTools.rgb(this.img3.getRGB(p.x, p.y));
/*     */           int tmp439_438 = rgb[0];
/*     */           int[] tmp439_432 = this.redList3;
/*     */           int tmp441_440 = tmp439_432[tmp439_438]; tmp439_432[tmp439_438] = (tmp441_440 + 1); if (tmp441_440 > this.max3) this.max3 = this.redList3[rgb[0]];
/* 328 */           int tmp476_475 = rgb[1];
/*     */           int[] tmp476_469 = this.greenList3;
/*     */           int tmp478_477 = tmp476_469[tmp476_475]; tmp476_469[tmp476_475] = (tmp478_477 + 1); if (tmp478_477 > this.max3) this.max3 = this.greenList3[rgb[1]];
/* 329 */           int tmp513_512 = rgb[2];
/*     */           int[] tmp513_506 = this.blueList3;
/*     */           int tmp515_514 = tmp513_506[tmp513_512]; tmp513_506[tmp513_512] = (tmp515_514 + 1); if (tmp515_514 > this.max3) this.max3 = this.blueList3[rgb[2]];
/*     */         }
/*     */         else
/*     */         {
/*     */           int tmp564_563 = (127 + Math.round(127.0F * ColorTools.colorToNDVI(rgb)));
/*     */           int[] tmp564_546 = this.redList1;
/*     */           int tmp566_565 = tmp564_546[tmp564_563]; tmp564_546[tmp564_563] = (tmp566_565 + 1); if (tmp566_565 > this.max1)
/* 332 */             this.max1 = this.redList1[(127 + Math.round(127.0F * ColorTools.colorToNDVI(rgb)))];
/* 333 */           rgb = ColorTools.rgb(this.img2.getRGB(p.x, p.y));
/*     */           int tmp645_644 = (127 + Math.round(127.0F * ColorTools.colorToNDVI(rgb)));
/*     */           int[] tmp645_627 = this.redList2;
/*     */           int tmp647_646 = tmp645_627[tmp645_644]; tmp645_627[tmp645_644] = (tmp647_646 + 1); if (tmp647_646 > this.max2)
/* 335 */             this.max2 = this.redList2[(127 + Math.round(127.0F * ColorTools.colorToNDVI(rgb)))];
/* 336 */           rgb = ColorTools.rgb(this.img3.getRGB(p.x, p.y));
/*     */           int tmp726_725 = (127 + Math.round(127.0F * ColorTools.colorToNDVI(rgb)));
/*     */           int[] tmp726_708 = this.redList3;
/*     */           int tmp728_727 = tmp726_708[tmp726_725]; tmp726_708[tmp726_725] = (tmp728_727 + 1); if (tmp728_727 > this.max3)
/* 338 */             this.max3 = this.redList3[(127 + Math.round(127.0F * ColorTools.colorToNDVI(rgb)))];
/*     */         }
/*     */       }
/* 341 */       draw1(); draw2(); draw3();
/*     */     } else {
/* 343 */       for (int i = 0; i < al.size(); i++) {
/* 344 */         Point p = (Point)al.get(i);
/* 345 */         int[] rgb = ColorTools.rgb(this.img1.getRGB(p.x, p.y));
/* 346 */         if (this.RGB < 2)
/*     */         {
/* 347 */           int tmp848_847 = rgb[0];
/*     */           int[] tmp848_841 = this.redList1;
/*     */           int tmp850_849 = tmp848_841[tmp848_847]; tmp848_841[tmp848_847] = (tmp850_849 + 1); if (tmp850_849 > this.max1) this.max1 = this.redList1[rgb[0]];
/* 348 */           int tmp885_884 = rgb[1];
/*     */           int[] tmp885_878 = this.greenList1;
/*     */           int tmp887_886 = tmp885_878[tmp885_884]; tmp885_878[tmp885_884] = (tmp887_886 + 1); if (tmp887_886 > this.max1) this.max1 = this.greenList1[rgb[1]];
/* 349 */           int tmp922_921 = rgb[2];
/*     */           int[] tmp922_915 = this.blueList1;
/*     */           int tmp924_923 = tmp922_915[tmp922_921]; tmp922_915[tmp922_921] = (tmp924_923 + 1); if (tmp924_923 > this.max1) this.max1 = this.blueList1[rgb[2]];
/* 350 */           rgb = ColorTools.rgb(this.img2.getRGB(p.x, p.y));
/*     */           int tmp981_980 = rgb[0];
/*     */           int[] tmp981_974 = this.redList2;
/*     */           int tmp983_982 = tmp981_974[tmp981_980]; tmp981_974[tmp981_980] = (tmp983_982 + 1); if (tmp983_982 > this.max2) this.max2 = this.redList2[rgb[0]];
/* 352 */           int tmp1018_1017 = rgb[1];
/*     */           int[] tmp1018_1011 = this.greenList2;
/*     */           int tmp1020_1019 = tmp1018_1011[tmp1018_1017]; tmp1018_1011[tmp1018_1017] = (tmp1020_1019 + 1); if (tmp1020_1019 > this.max2) this.max2 = this.greenList2[rgb[1]];
/* 353 */           int tmp1055_1054 = rgb[2];
/*     */           int[] tmp1055_1048 = this.blueList2;
/*     */           int tmp1057_1056 = tmp1055_1048[tmp1055_1054]; tmp1055_1048[tmp1055_1054] = (tmp1057_1056 + 1); if (tmp1057_1056 > this.max2) this.max2 = this.blueList2[rgb[2]];
/*     */         }
/*     */         else
/*     */         {
/*     */           int tmp1106_1105 = (127 + Math.round(127.0F * ColorTools.colorToNDVI(rgb)));
/*     */           int[] tmp1106_1088 = this.redList1;
/*     */           int tmp1108_1107 = tmp1106_1088[tmp1106_1105]; tmp1106_1088[tmp1106_1105] = (tmp1108_1107 + 1); if (tmp1108_1107 > this.max1)
/* 356 */             this.max1 = this.redList1[(127 + Math.round(127.0F * ColorTools.colorToNDVI(rgb)))];
/* 357 */           rgb = ColorTools.rgb(this.img2.getRGB(p.x, p.y));
/*     */           int tmp1187_1186 = (127 + Math.round(127.0F * ColorTools.colorToNDVI(rgb)));
/*     */           int[] tmp1187_1169 = this.redList2;
/*     */           int tmp1189_1188 = tmp1187_1169[tmp1187_1186]; tmp1187_1169[tmp1187_1186] = (tmp1189_1188 + 1); if (tmp1189_1188 > this.max2)
/* 359 */             this.max2 = this.redList2[(127 + Math.round(127.0F * ColorTools.colorToNDVI(rgb)))];
/*     */         }
/*     */       }
/* 362 */       draw1(); draw2();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void update2(Integer[] x, Integer[] y)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void update3(Integer[] x, Integer[] y) {
/*     */   }
/*     */ 
/*     */   public void threePics() {
/* 374 */     this.image3txt.setVisible(true);
/* 375 */     this.r3.setVisible(true);
/* 376 */     this.g3.setVisible(true);
/* 377 */     this.b3.setVisible(true);
/* 378 */     this.three = Boolean.valueOf(true);
/*     */   }
/*     */ 
/*     */   private void drawAxis() {
/* 382 */     this.img = new BufferedImage(255, 255, 1);
/* 383 */     Graphics2D g = this.img.createGraphics();
/* 384 */     g.setColor(Color.WHITE);
/* 385 */     g.fillRect(0, 0, 255, 255);
/* 386 */     g.setColor(Color.GRAY);
/* 387 */     g.setStroke(new BasicStroke(1.0F));
/* 388 */     g.drawLine(0, 0, 255, 0);
/* 389 */     g.drawLine(0, 64, 255, 64);
/* 390 */     g.drawLine(0, 126, 255, 126);
/* 391 */     g.drawLine(0, 187, 255, 187);
/* 392 */     g.drawLine(0, 254, 255, 254);
/* 393 */     g.drawLine(0, 0, 0, 255);
/* 394 */     g.drawLine(254, 0, 254, 255);
/*     */ 
/* 396 */     g.dispose();
/* 397 */     this.label.setIcon(new ImageIcon(this.img));
/*     */   }
/*     */ 
/*     */   private ArrayList<Point> polyArea(Integer[] x, Integer[] y) {
/* 401 */     int xmax = 0;
/* 402 */     int xmin = 9000;
/* 403 */     int ymax = 0;
/* 404 */     int ymin = 9000;
/* 405 */     int[] X = new int[x.length];
/* 406 */     int[] Y = new int[y.length];
/* 407 */     for (int i = 0; i < x.length; i++) {
/* 408 */       X[i] = x[i].intValue();
/* 409 */       Y[i] = y[i].intValue();
/* 410 */       if (x[i].intValue() > xmax) xmax = x[i].intValue();
/* 411 */       if (x[i].intValue() < xmin) xmin = x[i].intValue();
/* 412 */       if (y[i].intValue() > ymax) ymax = y[i].intValue();
/* 413 */       if (y[i].intValue() < ymin) ymin = y[i].intValue();
/*     */     }
/* 415 */     final ArrayList points1 = new ArrayList();
/* 416 */     ArrayList points2 = new ArrayList();
/* 417 */     final Polygon poly = new Polygon(X, Y, X.length);
/* 418 */     final int start = xmin;
/* 419 */     final int end = xmax / 2;
/* 420 */     final int starty = ymin;
/* 421 */     final int endy = ymax;
/* 422 */     Runnable r1 = new Thread()
/*     */     {
/*     */       public void run() {
/* 425 */         for (int i = start; i <= end; i++)
/* 426 */           for (int j = starty; j <= endy; j++)
/* 427 */             if (poly.contains(i, j))
/*     */             {
/* 429 */               points1.add(new Point(i, j));
/*     */             }
/*     */       }
/*     */     };
/* 435 */     r1.run();
/* 436 */     for (int i = end; i <= xmax; i++) {
/* 437 */       for (int j = ymin; j <= ymax; j++) {
/* 438 */         if (poly.contains(i, j)) {
/* 439 */           points2.add(new Point(i, j));
/*     */         }
/*     */       }
/*     */     }
/* 443 */     points2.addAll(points1);
/* 444 */     return points2;
/*     */   }
/*     */   private void setup() {
/* 447 */     this.textField = new JTextField();
/* 448 */     this.textField.setText("1");
/* 449 */     this.textField.setOpaque(false);
/* 450 */     this.textField.setHorizontalAlignment(11);
/* 451 */     this.textField.setFont(new Font("Tahoma", 0, 10));
/* 452 */     this.textField.setEditable(false);
/* 453 */     this.textField.setColumns(10);
/* 454 */     this.textField.setBorder(null);
/* 455 */     this.textField.setBounds(23, 15, 40, 10);
/* 456 */     add(this.textField);
/*     */ 
/* 458 */     this.txtRelativeFrequencyOf = new JTextField();
/* 459 */     this.txtRelativeFrequencyOf.setFont(new Font("Tahoma", 0, 10));
/* 460 */     this.txtRelativeFrequencyOf.setText("Relative Frequency of Colors Within Selected Area");
/* 461 */     this.txtRelativeFrequencyOf.setOpaque(false);
/* 462 */     this.txtRelativeFrequencyOf.setHorizontalAlignment(0);
/* 463 */     this.txtRelativeFrequencyOf.setEditable(false);
/* 464 */     this.txtRelativeFrequencyOf.setColumns(10);
/* 465 */     this.txtRelativeFrequencyOf.setBorder(null);
/* 466 */     this.txtRelativeFrequencyOf.setBounds(73, 1, 269, 20);
/* 467 */     add(this.txtRelativeFrequencyOf);
/*     */ 
/* 469 */     this.textField_2 = new JTextField();
/* 470 */     this.textField_2.setText(".75");
/* 471 */     this.textField_2.setOpaque(false);
/* 472 */     this.textField_2.setHorizontalAlignment(11);
/* 473 */     this.textField_2.setFont(new Font("Tahoma", 0, 10));
/* 474 */     this.textField_2.setEditable(false);
/* 475 */     this.textField_2.setColumns(10);
/* 476 */     this.textField_2.setBorder(null);
/* 477 */     this.textField_2.setBounds(23, 77, 40, 10);
/* 478 */     add(this.textField_2);
/*     */ 
/* 480 */     this.textField_3 = new JTextField();
/* 481 */     this.textField_3.setText(".5");
/* 482 */     this.textField_3.setOpaque(false);
/* 483 */     this.textField_3.setHorizontalAlignment(11);
/* 484 */     this.textField_3.setFont(new Font("Tahoma", 0, 10));
/* 485 */     this.textField_3.setEditable(false);
/* 486 */     this.textField_3.setColumns(10);
/* 487 */     this.textField_3.setBorder(null);
/* 488 */     this.textField_3.setBounds(23, 139, 40, 10);
/* 489 */     add(this.textField_3);
/*     */ 
/* 491 */     this.textField_4 = new JTextField();
/* 492 */     this.textField_4.setText(".25");
/* 493 */     this.textField_4.setOpaque(false);
/* 494 */     this.textField_4.setHorizontalAlignment(11);
/* 495 */     this.textField_4.setFont(new Font("Tahoma", 0, 10));
/* 496 */     this.textField_4.setEditable(false);
/* 497 */     this.textField_4.setColumns(10);
/* 498 */     this.textField_4.setBorder(null);
/* 499 */     this.textField_4.setBounds(23, 201, 40, 10);
/* 500 */     add(this.textField_4);
/*     */ 
/* 502 */     this.textField_5 = new JTextField();
/* 503 */     this.textField_5.setText("0");
/* 504 */     this.textField_5.setOpaque(false);
/* 505 */     this.textField_5.setHorizontalAlignment(11);
/* 506 */     this.textField_5.setFont(new Font("Tahoma", 0, 10));
/* 507 */     this.textField_5.setEditable(false);
/* 508 */     this.textField_5.setColumns(10);
/* 509 */     this.textField_5.setBorder(null);
/* 510 */     this.textField_5.setBounds(23, 263, 40, 10);
/* 511 */     add(this.textField_5);
/*     */ 
/* 513 */     this.label = new JLabel();
/* 514 */     this.label.setBounds(73, 19, 255, 255);
/* 515 */     add(this.label);
/*     */ 
/* 517 */     this.image1txt = new JTextField();
/* 518 */     this.image1txt.setBorder(null);
/* 519 */     this.image1txt.setOpaque(false);
/* 520 */     this.image1txt.setEditable(false);
/* 521 */     this.image1txt.setHorizontalAlignment(0);
/* 522 */     this.image1txt.setText("Image 1");
/* 523 */     this.image1txt.setBounds(348, 41, 70, 15);
/* 524 */     add(this.image1txt);
/* 525 */     this.image1txt.setColumns(10);
/*     */ 
/* 527 */     this.r1 = new JTextField();
/* 528 */     this.r1.setForeground(this.red1);
/* 529 */     this.r1.setOpaque(false);
/* 530 */     this.r1.setHorizontalAlignment(0);
/* 531 */     this.r1.setEditable(false);
/* 532 */     this.r1.setColumns(10);
/* 533 */     this.r1.setBorder(null);
/* 534 */     this.r1.setBounds(348, 56, 70, 15);
/* 535 */     add(this.r1);
/*     */ 
/* 537 */     this.g1 = new JTextField();
/* 538 */     this.g1.setForeground(this.green1);
/* 539 */     this.g1.setOpaque(false);
/* 540 */     this.g1.setHorizontalAlignment(0);
/* 541 */     this.g1.setEditable(false);
/* 542 */     this.g1.setColumns(10);
/* 543 */     this.g1.setBorder(null);
/* 544 */     this.g1.setBounds(348, 71, 70, 15);
/* 545 */     add(this.g1);
/*     */ 
/* 547 */     this.b1 = new JTextField();
/* 548 */     this.b1.setForeground(this.blue1);
/* 549 */     this.b1.setOpaque(false);
/* 550 */     this.b1.setHorizontalAlignment(0);
/* 551 */     this.b1.setEditable(false);
/* 552 */     this.b1.setColumns(10);
/* 553 */     this.b1.setBorder(null);
/* 554 */     this.b1.setBounds(348, 86, 70, 15);
/* 555 */     add(this.b1);
/*     */ 
/* 557 */     this.image2txt = new JTextField();
/* 558 */     this.image2txt.setForeground(this.black2);
/* 559 */     this.image2txt.setText("Image 2");
/* 560 */     this.image2txt.setOpaque(false);
/* 561 */     this.image2txt.setHorizontalAlignment(0);
/* 562 */     this.image2txt.setEditable(false);
/* 563 */     this.image2txt.setColumns(10);
/* 564 */     this.image2txt.setBorder(null);
/* 565 */     this.image2txt.setBounds(348, 101, 70, 15);
/* 566 */     add(this.image2txt);
/*     */ 
/* 568 */     this.r2 = new JTextField();
/* 569 */     this.r2.setForeground(this.red2);
/* 570 */     this.r2.setOpaque(false);
/* 571 */     this.r2.setHorizontalAlignment(0);
/* 572 */     this.r2.setEditable(false);
/* 573 */     this.r2.setColumns(10);
/* 574 */     this.r2.setBorder(null);
/* 575 */     this.r2.setBounds(348, 116, 70, 15);
/* 576 */     add(this.r2);
/*     */ 
/* 578 */     this.g3 = new JTextField();
/* 579 */     this.g3.setVisible(false);
/* 580 */     this.g3.setForeground(this.green3);
/* 581 */     this.g3.setOpaque(false);
/* 582 */     this.g3.setHorizontalAlignment(0);
/* 583 */     this.g3.setEditable(false);
/* 584 */     this.g3.setColumns(10);
/* 585 */     this.g3.setBorder(null);
/* 586 */     this.g3.setBounds(348, 191, 70, 15);
/* 587 */     add(this.g3);
/*     */ 
/* 589 */     this.r3 = new JTextField();
/* 590 */     this.r3.setVisible(false);
/* 591 */     this.r3.setForeground(this.red3);
/* 592 */     this.r3.setOpaque(false);
/* 593 */     this.r3.setHorizontalAlignment(0);
/* 594 */     this.r3.setEditable(false);
/* 595 */     this.r3.setColumns(10);
/* 596 */     this.r3.setBorder(null);
/* 597 */     this.r3.setBounds(348, 176, 70, 15);
/* 598 */     add(this.r3);
/*     */ 
/* 600 */     this.image3txt = new JTextField();
/* 601 */     this.image3txt.setVisible(false);
/* 602 */     this.image3txt.setForeground(this.black3);
/* 603 */     this.image3txt.setText("Image 3");
/* 604 */     this.image3txt.setOpaque(false);
/* 605 */     this.image3txt.setHorizontalAlignment(0);
/* 606 */     this.image3txt.setEditable(false);
/* 607 */     this.image3txt.setColumns(10);
/* 608 */     this.image3txt.setBorder(null);
/* 609 */     this.image3txt.setBounds(348, 161, 70, 15);
/* 610 */     add(this.image3txt);
/*     */ 
/* 612 */     this.b2 = new JTextField();
/* 613 */     this.b2.setForeground(this.blue2);
/* 614 */     this.b2.setOpaque(false);
/* 615 */     this.b2.setHorizontalAlignment(0);
/* 616 */     this.b2.setEditable(false);
/* 617 */     this.b2.setColumns(10);
/* 618 */     this.b2.setBorder(null);
/* 619 */     this.b2.setBounds(348, 146, 70, 15);
/* 620 */     add(this.b2);
/*     */ 
/* 622 */     this.g2 = new JTextField();
/* 623 */     this.g2.setForeground(this.green2);
/* 624 */     this.g2.setOpaque(false);
/* 625 */     this.g2.setHorizontalAlignment(0);
/* 626 */     this.g2.setEditable(false);
/* 627 */     this.g2.setColumns(10);
/* 628 */     this.g2.setBorder(null);
/* 629 */     this.g2.setBounds(348, 131, 70, 15);
/* 630 */     add(this.g2);
/*     */ 
/* 632 */     this.b3 = new JTextField();
/* 633 */     this.b3.setVisible(false);
/* 634 */     this.b3.setForeground(this.blue3);
/* 635 */     this.b3.setOpaque(false);
/* 636 */     this.b3.setHorizontalAlignment(0);
/* 637 */     this.b3.setEditable(false);
/* 638 */     this.b3.setColumns(10);
/* 639 */     this.b3.setBorder(null);
/* 640 */     this.b3.setBounds(348, 206, 70, 15);
/* 641 */     add(this.b3);
/*     */ 
/* 643 */     this.intens1 = new JTextField();
/* 644 */     this.intens1.setText("0%");
/* 645 */     this.intens1.setOpaque(false);
/* 646 */     this.intens1.setHorizontalAlignment(0);
/* 647 */     this.intens1.setFont(new Font("Tahoma", 0, 10));
/* 648 */     this.intens1.setEditable(false);
/* 649 */     this.intens1.setColumns(10);
/* 650 */     this.intens1.setBorder(null);
/* 651 */     this.intens1.setBounds(57, 275, 30, 15);
/* 652 */     add(this.intens1);
/*     */ 
/* 654 */     this.intens2 = new JTextField();
/* 655 */     this.intens2.setText("25%");
/* 656 */     this.intens2.setOpaque(false);
/* 657 */     this.intens2.setHorizontalAlignment(0);
/* 658 */     this.intens2.setFont(new Font("Tahoma", 0, 10));
/* 659 */     this.intens2.setEditable(false);
/* 660 */     this.intens2.setColumns(10);
/* 661 */     this.intens2.setBorder(null);
/* 662 */     this.intens2.setBounds(121, 275, 30, 15);
/* 663 */     add(this.intens2);
/*     */ 
/* 665 */     this.intens3 = new JTextField();
/* 666 */     this.intens3.setText("50%");
/* 667 */     this.intens3.setOpaque(false);
/* 668 */     this.intens3.setHorizontalAlignment(0);
/* 669 */     this.intens3.setFont(new Font("Tahoma", 0, 10));
/* 670 */     this.intens3.setEditable(false);
/* 671 */     this.intens3.setColumns(10);
/* 672 */     this.intens3.setBorder(null);
/* 673 */     this.intens3.setBounds(185, 275, 30, 15);
/* 674 */     add(this.intens3);
/*     */ 
/* 676 */     this.intens4 = new JTextField();
/* 677 */     this.intens4.setText("75%");
/* 678 */     this.intens4.setOpaque(false);
/* 679 */     this.intens4.setHorizontalAlignment(0);
/* 680 */     this.intens4.setFont(new Font("Tahoma", 0, 10));
/* 681 */     this.intens4.setEditable(false);
/* 682 */     this.intens4.setColumns(10);
/* 683 */     this.intens4.setBorder(null);
/* 684 */     this.intens4.setBounds(249, 275, 30, 15);
/* 685 */     add(this.intens4);
/*     */ 
/* 687 */     this.intens5 = new JTextField();
/* 688 */     this.intens5.setText("100%");
/* 689 */     this.intens5.setOpaque(false);
/* 690 */     this.intens5.setHorizontalAlignment(0);
/* 691 */     this.intens5.setFont(new Font("Tahoma", 0, 10));
/* 692 */     this.intens5.setEditable(false);
/* 693 */     this.intens5.setColumns(10);
/* 694 */     this.intens5.setBorder(null);
/* 695 */     this.intens5.setBounds(312, 275, 30, 15);
/* 696 */     add(this.intens5);
/*     */ 
/* 698 */     this.txtMean = new JTextField();
/* 699 */     this.txtMean.setText("Mean");
/* 700 */     this.txtMean.setOpaque(false);
/* 701 */     this.txtMean.setHorizontalAlignment(0);
/* 702 */     this.txtMean.setEditable(false);
/* 703 */     this.txtMean.setColumns(10);
/* 704 */     this.txtMean.setBorder(null);
/* 705 */     this.txtMean.setBounds(348, 15, 70, 15);
/* 706 */     add(this.txtMean);
/*     */ 
/* 708 */     this.txtFrequency = new JTextField();
/* 709 */     this.txtFrequency.setForeground(Color.MAGENTA);
/* 710 */     this.txtFrequency.setBorder(null);
/* 711 */     this.txtFrequency.setEditable(false);
/* 712 */     this.txtFrequency.setFont(new Font("Tahoma", 0, 9));
/* 713 */     this.txtFrequency.setHorizontalAlignment(0);
/* 714 */     this.txtFrequency.setText("Frequency");
/* 715 */     this.txtFrequency.setBounds(10, 0, 53, 16);
/* 716 */     add(this.txtFrequency);
/* 717 */     this.txtFrequency.setColumns(10);
/*     */ 
/* 719 */     this.intensity = new JTextField();
/* 720 */     this.intensity.setText("Intensity");
/* 721 */     this.intensity.setHorizontalAlignment(0);
/* 722 */     this.intensity.setForeground(Color.MAGENTA);
/* 723 */     this.intensity.setFont(new Font("Tahoma", 0, 9));
/* 724 */     this.intensity.setEditable(false);
/* 725 */     this.intensity.setColumns(10);
/* 726 */     this.intensity.setBorder(null);
/* 727 */     this.intensity.setBounds(348, 272, 53, 16);
/* 728 */     add(this.intensity);
/* 729 */     drawAxis();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.datapanels.PolygonPanel
 * JD-Core Version:    0.6.2
 */