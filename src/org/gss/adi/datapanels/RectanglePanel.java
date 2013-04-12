/*     */ package org.gss.adi.datapanels;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.text.DecimalFormat;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JTextField;
/*     */ import org.gss.adi.tools.ColorTools;
/*     */ 
/*     */ public class RectanglePanel extends FocusPanel
/*     */ {
/*     */   private static final long serialVersionUID = -8439296370947390361L;
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
/*  48 */   private Boolean three = Boolean.valueOf(false);
/*     */ 
/*  50 */   private int[] redList1 = new int[256];
/*  51 */   private int[] redList2 = new int[256];
/*  52 */   private int[] redList3 = new int[256];
/*  53 */   private int[] greenList1 = new int[256];
/*  54 */   private int[] greenList2 = new int[256];
/*  55 */   private int[] greenList3 = new int[256];
/*  56 */   private int[] blueList1 = new int[256];
/*  57 */   private int[] blueList2 = new int[256];
/*  58 */   private int[] blueList3 = new int[256];
/*     */   private float max1;
/*     */   private float max2;
/*     */   private float max3;
/*     */   private JTextField txtMean;
/*     */   private JTextField intens1;
/*     */   private JTextField intens5;
/*     */   private JTextField intens3;
/*     */   private JTextField intens2;
/*     */   private JTextField intens4;
/*     */   private JTextField textField_10;
/*     */   private JTextField intensity;
/*     */   private int RGB;
/*     */ 
/*     */   public RectanglePanel(BufferedImage image1, BufferedImage image2, BufferedImage image3, int rgb)
/*     */   {
/*  76 */     super(image1, image2, image3, rgb);
/*  77 */     this.RGB = rgb;
/*  78 */     this.img1 = image1;
/*  79 */     this.img2 = image2;
/*  80 */     this.img3 = image3;
/*  81 */     setup();
/*  82 */     if (rgb > 0) {
/*  83 */       this.r1.setForeground(this.black1);
/*  84 */       this.g1.setForeground(this.red1);
/*  85 */       this.b1.setForeground(this.green1);
/*  86 */       this.r2.setForeground(this.black2);
/*  87 */       this.g2.setForeground(this.red2);
/*  88 */       this.b2.setForeground(this.green2);
/*  89 */       this.r3.setForeground(this.black3);
/*  90 */       this.g3.setForeground(this.red3);
/*  91 */       this.b3.setForeground(this.green3);
/*     */     }
/*  93 */     if (rgb == 2) {
/*  94 */       this.g1.setVisible(false);
/*  95 */       this.b1.setVisible(false);
/*  96 */       this.g2.setVisible(false);
/*  97 */       this.b2.setVisible(false);
/*  98 */       this.g3.setVisible(false);
/*  99 */       this.b3.setVisible(false);
/* 100 */       this.intens1.setText("-1");
/* 101 */       this.intens2.setText("-.5");
/* 102 */       this.intens3.setText("0");
/* 103 */       this.intens4.setText(".5");
/* 104 */       this.intens5.setText("1");
/* 105 */       this.intensity.setText("NDVI value");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 111 */     drawAxis();
/* 112 */     this.image1txt.setVisible(false);
/* 113 */     this.r1.setVisible(false);
/* 114 */     this.g1.setVisible(false);
/* 115 */     this.b1.setVisible(false);
/* 116 */     this.image2txt.setVisible(false);
/* 117 */     this.r2.setVisible(false);
/* 118 */     this.g2.setVisible(false);
/* 119 */     this.b2.setVisible(false);
/* 120 */     this.image3txt.setVisible(false);
/* 121 */     this.r3.setVisible(false);
/* 122 */     this.g3.setVisible(false);
/* 123 */     this.b3.setVisible(false);
/*     */   }
/*     */ 
/*     */   public void draw1()
/*     */   {
/*     */     Color B;
/*     */     Color R;
/*     */     Color G;
/* 131 */     if (this.RGB == 0) {
/* 132 */       R = this.red1;
/* 133 */       G = this.green1;
/* 134 */       B = this.blue1;
/*     */     } else {
/* 136 */       R = this.black1;
/* 137 */       G = this.red1;
/* 138 */       B = this.green1;
/*     */     }
/* 140 */     Float r = Float.valueOf(0.0F);
/* 141 */     Integer gr = Integer.valueOf(0);
/* 142 */     Integer b = Integer.valueOf(0);
/* 143 */     float size = 0.0F;
/* 144 */     for (int i = 0; i < 255; i++) {
/* 145 */       Graphics2D g = this.img.createGraphics();
/* 146 */       g.setStroke(new BasicStroke(2.0F));
/* 147 */       size += this.redList1[i];
/* 148 */       g.setColor(R);
/* 149 */       g.drawLine(i, 255 - Math.round(255.0F * this.redList1[i] / this.max1), i + 1, 255 - Math.round(255.0F * this.redList1[(i + 1)] / this.max1));
/* 150 */       if (this.RGB < 2) {
/* 151 */         r = Float.valueOf(r.floatValue() + this.redList1[i] * i);
/* 152 */         g.setColor(G);
/* 153 */         gr = Integer.valueOf(gr.intValue() + this.greenList1[i] * i);
/* 154 */         g.drawLine(i, 255 - Math.round(255.0F * this.greenList1[i] / this.max1), i + 1, 255 - Math.round(255.0F * this.greenList1[(i + 1)] / this.max1));
/* 155 */         g.setColor(B);
/* 156 */         b = Integer.valueOf(b.intValue() + this.blueList1[i] * i);
/* 157 */         g.drawLine(i, 255 - Math.round(255.0F * this.blueList1[i] / this.max1), i + 1, 255 - Math.round(255.0F * this.blueList1[(i + 1)] / this.max1));
/*     */       } else {
/* 159 */         r = Float.valueOf(r.floatValue() + this.redList1[i] * (i - 127.0F) / 127.0F);
/*     */       }
/* 161 */       g.dispose();
/*     */     }
/* 163 */     if (size == 0.0F) {
/* 164 */       this.r1.setText("");
/* 165 */       this.g1.setText("");
/* 166 */       this.b1.setText("");
/*     */     } else {
/* 168 */       this.r1.setText(this.df.format(r.floatValue() / size));
/* 169 */       this.g1.setText(this.df.format(gr.intValue() / size));
/* 170 */       this.b1.setText(this.df.format(b.intValue() / size));
/*     */     }
/* 172 */     this.image1txt.setVisible(true);
/* 173 */     this.r1.setVisible(true);
/* 174 */     if (this.RGB < 2) {
/* 175 */       this.g1.setVisible(true);
/* 176 */       this.b1.setVisible(true);
/*     */     }
/* 178 */     repaint();
/*     */   }
/*     */ 
/*     */   public void draw2()
/*     */   {
/*     */     Color B;
/*     */     Color R;
/*     */     Color G;
/* 186 */     if (this.RGB == 0) {
/* 187 */       R = this.red2;
/* 188 */       G = this.green2;
/* 189 */       B = this.blue2;
/*     */     } else {
/* 191 */       R = this.black2;
/* 192 */       G = this.red2;
/* 193 */       B = this.green2;
/*     */     }
/* 195 */     Float r = Float.valueOf(0.0F);
/* 196 */     Integer gr = Integer.valueOf(0);
/* 197 */     Integer b = Integer.valueOf(0);
/* 198 */     float size = 0.0F;
/* 199 */     for (int i = 0; i < 255; i++) {
/* 200 */       Graphics2D g = this.img.createGraphics();
/* 201 */       g.setStroke(new BasicStroke(2.0F));
/* 202 */       g.setColor(R);
/* 203 */       g.drawLine(i, 255 - Math.round(255.0F * this.redList2[i] / this.max2), i + 1, 255 - Math.round(255.0F * this.redList2[(i + 1)] / this.max2));
/* 204 */       size += this.redList2[i];
/* 205 */       if (this.RGB < 2) {
/* 206 */         r = Float.valueOf(r.floatValue() + this.redList2[i] * i);
/* 207 */         g.setColor(G);
/* 208 */         gr = Integer.valueOf(gr.intValue() + this.greenList2[i] * i);
/* 209 */         g.drawLine(i, 255 - Math.round(255.0F * this.greenList2[i] / this.max2), i + 1, 255 - Math.round(255.0F * this.greenList2[(i + 1)] / this.max2));
/* 210 */         g.setColor(B);
/* 211 */         b = Integer.valueOf(b.intValue() + this.blueList2[i] * i);
/* 212 */         g.drawLine(i, 255 - Math.round(255.0F * this.blueList2[i] / this.max2), i + 1, 255 - Math.round(255.0F * this.blueList2[(i + 1)] / this.max2));
/*     */       } else {
/* 214 */         r = Float.valueOf(r.floatValue() + this.redList2[i] * (i - 127.0F) / 127.0F);
/*     */       }
/* 216 */       g.dispose();
/*     */     }
/* 218 */     if (size == 0.0F) {
/* 219 */       this.r2.setText("");
/* 220 */       this.g2.setText("");
/* 221 */       this.b2.setText("");
/*     */     } else {
/* 223 */       this.r2.setText(this.df.format(r.floatValue() / size));
/* 224 */       this.g2.setText(this.df.format(gr.intValue() / size));
/* 225 */       this.b2.setText(this.df.format(b.intValue() / size));
/*     */     }
/* 227 */     this.image2txt.setVisible(true);
/* 228 */     this.r2.setVisible(true);
/* 229 */     if (this.RGB < 2) {
/* 230 */       this.g2.setVisible(true);
/* 231 */       this.b2.setVisible(true);
/*     */     }
/* 233 */     repaint();
/*     */   }
/*     */ 
/*     */   public void draw3()
/*     */   {
/*     */     Color B;
/*     */     Color R;
/*     */     Color G;
/* 241 */     if (this.RGB == 0) {
/* 242 */       R = this.red3;
/* 243 */       G = this.green3;
/* 244 */       B = this.blue3;
/*     */     } else {
/* 246 */       R = this.black3;
/* 247 */       G = this.red3;
/* 248 */       B = this.green3;
/*     */     }
/* 250 */     Float r = Float.valueOf(0.0F);
/* 251 */     Integer gr = Integer.valueOf(0);
/* 252 */     Integer b = Integer.valueOf(0);
/* 253 */     float size = 0.0F;
/* 254 */     for (int i = 0; i < 255; i++) {
/* 255 */       Graphics2D g = this.img.createGraphics();
/* 256 */       g.setStroke(new BasicStroke(2.0F));
/* 257 */       g.setColor(R);
/* 258 */       g.drawLine(i, 255 - Math.round(255.0F * this.redList3[i] / this.max3), i + 1, 255 - Math.round(255.0F * this.redList3[(i + 1)] / this.max3));
/* 259 */       size += this.redList3[i];
/* 260 */       if (this.RGB < 2) {
/* 261 */         r = Float.valueOf(r.floatValue() + this.redList3[i] * i);
/* 262 */         g.setColor(G);
/* 263 */         gr = Integer.valueOf(gr.intValue() + this.greenList3[i] * i);
/* 264 */         g.drawLine(i, 255 - Math.round(255.0F * this.greenList3[i] / this.max3), i + 1, 255 - Math.round(255.0F * this.greenList3[(i + 1)] / this.max3));
/* 265 */         g.setColor(B);
/* 266 */         b = Integer.valueOf(b.intValue() + this.blueList3[i] * i);
/* 267 */         g.drawLine(i, 255 - Math.round(255.0F * this.blueList3[i] / this.max3), i + 1, 255 - Math.round(255.0F * this.blueList3[(i + 1)] / this.max3));
/*     */       } else {
/* 269 */         r = Float.valueOf(r.floatValue() + this.redList3[i] * (i - 127.0F) / 127.0F);
/*     */       }
/* 271 */       g.dispose();
/*     */     }
/* 273 */     if (size == 0.0F) {
/* 274 */       this.r3.setText("");
/* 275 */       this.g3.setText("");
/* 276 */       this.b3.setText("");
/*     */     } else {
/* 278 */       this.r3.setText(this.df.format(r.floatValue() / size));
/* 279 */       this.g3.setText(this.df.format(gr.intValue() / size));
/* 280 */       this.b3.setText(this.df.format(b.intValue() / size));
/*     */     }
/* 282 */     this.image3txt.setVisible(true);
/* 283 */     this.r3.setVisible(true);
/* 284 */     if (this.RGB < 2) {
/* 285 */       this.g3.setVisible(true);
/* 286 */       this.b3.setVisible(true);
/*     */     }
/* 288 */     repaint();
/*     */   }
/*     */ 
/*     */   public void update1(Integer[] x, Integer[] y)
/*     */   {
/* 293 */     drawAxis();
/* 294 */     for (int i = 0; i < 256; i++) {
/* 295 */       this.redList1[i] = 0;
/* 296 */       this.redList2[i] = 0;
/* 297 */       this.redList3[i] = 0;
/* 298 */       this.greenList1[i] = 0;
/* 299 */       this.greenList2[i] = 0;
/* 300 */       this.greenList3[i] = 0;
/* 301 */       this.blueList1[i] = 0;
/* 302 */       this.blueList2[i] = 0;
/* 303 */       this.blueList3[i] = 0;
/*     */     }
/* 305 */     this.max1 = 0.0F;
/* 306 */     this.max2 = 0.0F;
/* 307 */     this.max3 = 0.0F;
/*     */     int xmax;
/*     */     int xmin;
/* 312 */     if (x[0].intValue() <= x[1].intValue()) { xmin = x[0].intValue(); xmax = x[1].intValue(); } else { xmin = x[1].intValue(); xmax = x[0].intValue();
/*     */     }
/* 313 */     int ymax;
/*     */     int ymin;
/* 313 */     if (y[0].intValue() <= y[1].intValue()) { ymin = y[0].intValue(); ymax = y[1].intValue(); } else { ymin = y[1].intValue(); ymax = y[0].intValue(); }
/* 314 */     if (this.three.booleanValue()) {
/* 315 */       threePics();
/* 316 */       for (int i = xmin; i < xmax; i++) {
/* 317 */         for (int j = ymin; j < ymax; j++) {
/* 318 */           int[] rgb = ColorTools.rgb(this.img1.getRGB(i, j));
/* 319 */           if (this.RGB < 2)
/*     */           {
/* 320 */             int tmp254_253 = rgb[0];
/*     */             int[] tmp254_247 = this.redList1;
/*     */             int tmp256_255 = tmp254_247[tmp254_253]; tmp254_247[tmp254_253] = (tmp256_255 + 1); if (tmp256_255 > this.max1) this.max1 = this.redList1[rgb[0]];
/* 321 */             int tmp291_290 = rgb[1];
/*     */             int[] tmp291_284 = this.greenList1;
/*     */             int tmp293_292 = tmp291_284[tmp291_290]; tmp291_284[tmp291_290] = (tmp293_292 + 1); if (tmp293_292 > this.max1) this.max1 = this.greenList1[rgb[1]];
/* 322 */             int tmp328_327 = rgb[2];
/*     */             int[] tmp328_321 = this.blueList1;
/*     */             int tmp330_329 = tmp328_321[tmp328_327]; tmp328_321[tmp328_327] = (tmp330_329 + 1); if (tmp330_329 > this.max1) this.max1 = this.blueList1[rgb[2]];
/* 323 */             rgb = ColorTools.rgb(this.img2.getRGB(i, j));
/*     */             int tmp381_380 = rgb[0];
/*     */             int[] tmp381_374 = this.redList2;
/*     */             int tmp383_382 = tmp381_374[tmp381_380]; tmp381_374[tmp381_380] = (tmp383_382 + 1); if (tmp383_382 > this.max2) this.max2 = this.redList2[rgb[0]];
/* 325 */             int tmp418_417 = rgb[1];
/*     */             int[] tmp418_411 = this.greenList2;
/*     */             int tmp420_419 = tmp418_411[tmp418_417]; tmp418_411[tmp418_417] = (tmp420_419 + 1); if (tmp420_419 > this.max2) this.max2 = this.greenList2[rgb[1]];
/* 326 */             int tmp455_454 = rgb[2];
/*     */             int[] tmp455_448 = this.blueList2;
/*     */             int tmp457_456 = tmp455_448[tmp455_454]; tmp455_448[tmp455_454] = (tmp457_456 + 1); if (tmp457_456 > this.max2) this.max2 = this.blueList2[rgb[2]];
/* 327 */             rgb = ColorTools.rgb(this.img3.getRGB(i, j));
/*     */             int tmp508_507 = rgb[0];
/*     */             int[] tmp508_501 = this.redList3;
/*     */             int tmp510_509 = tmp508_501[tmp508_507]; tmp508_501[tmp508_507] = (tmp510_509 + 1); if (tmp510_509 > this.max3) this.max3 = this.redList3[rgb[0]];
/* 329 */             int tmp545_544 = rgb[1];
/*     */             int[] tmp545_538 = this.greenList3;
/*     */             int tmp547_546 = tmp545_538[tmp545_544]; tmp545_538[tmp545_544] = (tmp547_546 + 1); if (tmp547_546 > this.max3) this.max3 = this.greenList3[rgb[1]];
/* 330 */             int tmp582_581 = rgb[2];
/*     */             int[] tmp582_575 = this.blueList3;
/*     */             int tmp584_583 = tmp582_575[tmp582_581]; tmp582_575[tmp582_581] = (tmp584_583 + 1); if (tmp584_583 > this.max3) this.max3 = this.blueList3[rgb[2]];
/*     */           }
/*     */           else
/*     */           {
/*     */             int tmp633_632 = (127 + Math.round(127.0F * ColorTools.colorToNDVI(rgb)));
/*     */             int[] tmp633_615 = this.redList1;
/*     */             int tmp635_634 = tmp633_615[tmp633_632]; tmp633_615[tmp633_632] = (tmp635_634 + 1); if (tmp635_634 > this.max1)
/* 333 */               this.max1 = this.redList1[(127 + Math.round(127.0F * ColorTools.colorToNDVI(rgb)))];
/* 334 */             rgb = ColorTools.rgb(this.img2.getRGB(i, j));
/*     */             int tmp708_707 = (127 + Math.round(127.0F * ColorTools.colorToNDVI(rgb)));
/*     */             int[] tmp708_690 = this.redList2;
/*     */             int tmp710_709 = tmp708_690[tmp708_707]; tmp708_690[tmp708_707] = (tmp710_709 + 1); if (tmp710_709 > this.max2)
/* 336 */               this.max2 = this.redList2[(127 + Math.round(127.0F * ColorTools.colorToNDVI(rgb)))];
/* 337 */             rgb = ColorTools.rgb(this.img3.getRGB(i, j));
/*     */             int tmp783_782 = (127 + Math.round(127.0F * ColorTools.colorToNDVI(rgb)));
/*     */             int[] tmp783_765 = this.redList3;
/*     */             int tmp785_784 = tmp783_765[tmp783_782]; tmp783_765[tmp783_782] = (tmp785_784 + 1); if (tmp785_784 > this.max3)
/* 339 */               this.max3 = this.redList3[(127 + Math.round(127.0F * ColorTools.colorToNDVI(rgb)))];
/*     */           }
/*     */         }
/*     */       }
/* 343 */       draw1(); draw2(); draw3();
/*     */     } else {
/* 345 */       for (int i = xmin; i < xmax; i++) {
/* 346 */         for (int j = ymin; j < ymax; j++) {
/* 347 */           int[] rgb = ColorTools.rgb(this.img1.getRGB(i, j));
/* 348 */           if (this.RGB < 2)
/*     */           {
/* 349 */             int tmp903_902 = rgb[0];
/*     */             int[] tmp903_896 = this.redList1;
/*     */             int tmp905_904 = tmp903_896[tmp903_902]; tmp903_896[tmp903_902] = (tmp905_904 + 1); if (tmp905_904 > this.max1) this.max1 = this.redList1[rgb[0]];
/* 350 */             int tmp940_939 = rgb[1];
/*     */             int[] tmp940_933 = this.greenList1;
/*     */             int tmp942_941 = tmp940_933[tmp940_939]; tmp940_933[tmp940_939] = (tmp942_941 + 1); if (tmp942_941 > this.max1) this.max1 = this.greenList1[rgb[1]];
/* 351 */             int tmp977_976 = rgb[2];
/*     */             int[] tmp977_970 = this.blueList1;
/*     */             int tmp979_978 = tmp977_970[tmp977_976]; tmp977_970[tmp977_976] = (tmp979_978 + 1); if (tmp979_978 > this.max1) this.max1 = this.blueList1[rgb[2]];
/* 352 */             rgb = ColorTools.rgb(this.img2.getRGB(i, j));
/*     */             int tmp1030_1029 = rgb[0];
/*     */             int[] tmp1030_1023 = this.redList2;
/*     */             int tmp1032_1031 = tmp1030_1023[tmp1030_1029]; tmp1030_1023[tmp1030_1029] = (tmp1032_1031 + 1); if (tmp1032_1031 > this.max2) this.max2 = this.redList2[rgb[0]];
/* 354 */             int tmp1067_1066 = rgb[1];
/*     */             int[] tmp1067_1060 = this.greenList2;
/*     */             int tmp1069_1068 = tmp1067_1060[tmp1067_1066]; tmp1067_1060[tmp1067_1066] = (tmp1069_1068 + 1); if (tmp1069_1068 > this.max2) this.max2 = this.greenList2[rgb[1]];
/* 355 */             int tmp1104_1103 = rgb[2];
/*     */             int[] tmp1104_1097 = this.blueList2;
/*     */             int tmp1106_1105 = tmp1104_1097[tmp1104_1103]; tmp1104_1097[tmp1104_1103] = (tmp1106_1105 + 1); if (tmp1106_1105 > this.max2) this.max2 = this.blueList2[rgb[2]];
/*     */           }
/*     */           else
/*     */           {
/*     */             int tmp1155_1154 = (127 + Math.round(127.0F * ColorTools.colorToNDVI(rgb)));
/*     */             int[] tmp1155_1137 = this.redList1;
/*     */             int tmp1157_1156 = tmp1155_1137[tmp1155_1154]; tmp1155_1137[tmp1155_1154] = (tmp1157_1156 + 1); if (tmp1157_1156 > this.max1)
/* 358 */               this.max1 = this.redList1[(127 + Math.round(127.0F * ColorTools.colorToNDVI(rgb)))];
/* 359 */             rgb = ColorTools.rgb(this.img2.getRGB(i, j));
/*     */             int tmp1230_1229 = (127 + Math.round(127.0F * ColorTools.colorToNDVI(rgb)));
/*     */             int[] tmp1230_1212 = this.redList2;
/*     */             int tmp1232_1231 = tmp1230_1212[tmp1230_1229]; tmp1230_1212[tmp1230_1229] = (tmp1232_1231 + 1); if (tmp1232_1231 > this.max2)
/* 361 */               this.max2 = this.redList2[(127 + Math.round(127.0F * ColorTools.colorToNDVI(rgb)))];
/*     */           }
/*     */         }
/*     */       }
/* 365 */       draw1(); draw2();
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
/* 377 */     this.image3txt.setVisible(true);
/* 378 */     this.r3.setVisible(true);
/* 379 */     this.g3.setVisible(true);
/* 380 */     this.b3.setVisible(true);
/* 381 */     this.three = Boolean.valueOf(true);
/*     */   }
/*     */ 
/*     */   private void drawAxis() {
/* 385 */     this.img = new BufferedImage(255, 255, 1);
/* 386 */     Graphics2D g = this.img.createGraphics();
/* 387 */     g.setColor(Color.WHITE);
/* 388 */     g.fillRect(0, 0, 255, 255);
/* 389 */     g.setColor(Color.GRAY);
/* 390 */     g.setStroke(new BasicStroke(1.0F));
/* 391 */     g.drawLine(0, 0, 255, 0);
/* 392 */     g.drawLine(0, 64, 255, 64);
/* 393 */     g.drawLine(0, 126, 255, 126);
/* 394 */     g.drawLine(0, 187, 255, 187);
/* 395 */     g.drawLine(0, 254, 255, 254);
/* 396 */     g.drawLine(0, 0, 0, 255);
/* 397 */     g.drawLine(254, 0, 254, 255);
/* 398 */     g.drawLine(64, 0, 64, 255);
/* 399 */     g.drawLine(126, 0, 126, 255);
/* 400 */     g.drawLine(187, 0, 187, 255);
/*     */ 
/* 402 */     g.dispose();
/* 403 */     this.label.setIcon(new ImageIcon(this.img));
/*     */   }
/*     */ 
/*     */   private void setup() {
/* 407 */     this.textField = new JTextField();
/* 408 */     this.textField.setText("1");
/* 409 */     this.textField.setOpaque(false);
/* 410 */     this.textField.setHorizontalAlignment(11);
/* 411 */     this.textField.setFont(new Font("Tahoma", 0, 10));
/* 412 */     this.textField.setEditable(false);
/* 413 */     this.textField.setColumns(10);
/* 414 */     this.textField.setBorder(null);
/* 415 */     this.textField.setBounds(23, 15, 40, 10);
/* 416 */     add(this.textField);
/*     */ 
/* 418 */     this.txtRelativeFrequencyOf = new JTextField();
/* 419 */     this.txtRelativeFrequencyOf.setFont(new Font("Tahoma", 0, 10));
/* 420 */     this.txtRelativeFrequencyOf.setText("Relative Frequency of Colors Within Selected Area");
/* 421 */     this.txtRelativeFrequencyOf.setOpaque(false);
/* 422 */     this.txtRelativeFrequencyOf.setHorizontalAlignment(0);
/* 423 */     this.txtRelativeFrequencyOf.setEditable(false);
/* 424 */     this.txtRelativeFrequencyOf.setColumns(10);
/* 425 */     this.txtRelativeFrequencyOf.setBorder(null);
/* 426 */     this.txtRelativeFrequencyOf.setBounds(73, 1, 265, 20);
/* 427 */     add(this.txtRelativeFrequencyOf);
/*     */ 
/* 429 */     this.textField_2 = new JTextField();
/* 430 */     this.textField_2.setText(".75");
/* 431 */     this.textField_2.setOpaque(false);
/* 432 */     this.textField_2.setHorizontalAlignment(11);
/* 433 */     this.textField_2.setFont(new Font("Tahoma", 0, 10));
/* 434 */     this.textField_2.setEditable(false);
/* 435 */     this.textField_2.setColumns(10);
/* 436 */     this.textField_2.setBorder(null);
/* 437 */     this.textField_2.setBounds(23, 77, 40, 10);
/* 438 */     add(this.textField_2);
/*     */ 
/* 440 */     this.textField_3 = new JTextField();
/* 441 */     this.textField_3.setText(".5");
/* 442 */     this.textField_3.setOpaque(false);
/* 443 */     this.textField_3.setHorizontalAlignment(11);
/* 444 */     this.textField_3.setFont(new Font("Tahoma", 0, 10));
/* 445 */     this.textField_3.setEditable(false);
/* 446 */     this.textField_3.setColumns(10);
/* 447 */     this.textField_3.setBorder(null);
/* 448 */     this.textField_3.setBounds(23, 139, 40, 10);
/* 449 */     add(this.textField_3);
/*     */ 
/* 451 */     this.textField_4 = new JTextField();
/* 452 */     this.textField_4.setText(".25");
/* 453 */     this.textField_4.setOpaque(false);
/* 454 */     this.textField_4.setHorizontalAlignment(11);
/* 455 */     this.textField_4.setFont(new Font("Tahoma", 0, 10));
/* 456 */     this.textField_4.setEditable(false);
/* 457 */     this.textField_4.setColumns(10);
/* 458 */     this.textField_4.setBorder(null);
/* 459 */     this.textField_4.setBounds(23, 201, 40, 10);
/* 460 */     add(this.textField_4);
/*     */ 
/* 462 */     this.textField_5 = new JTextField();
/* 463 */     this.textField_5.setText("0");
/* 464 */     this.textField_5.setOpaque(false);
/* 465 */     this.textField_5.setHorizontalAlignment(11);
/* 466 */     this.textField_5.setFont(new Font("Tahoma", 0, 10));
/* 467 */     this.textField_5.setEditable(false);
/* 468 */     this.textField_5.setColumns(10);
/* 469 */     this.textField_5.setBorder(null);
/* 470 */     this.textField_5.setBounds(23, 263, 40, 10);
/* 471 */     add(this.textField_5);
/*     */ 
/* 473 */     this.label = new JLabel();
/* 474 */     this.label.setBounds(73, 19, 255, 255);
/* 475 */     add(this.label);
/*     */ 
/* 477 */     this.image1txt = new JTextField();
/* 478 */     this.image1txt.setBorder(null);
/* 479 */     this.image1txt.setOpaque(false);
/* 480 */     this.image1txt.setEditable(false);
/* 481 */     this.image1txt.setHorizontalAlignment(0);
/* 482 */     this.image1txt.setText("Image 1");
/* 483 */     this.image1txt.setBounds(348, 41, 70, 15);
/* 484 */     add(this.image1txt);
/* 485 */     this.image1txt.setColumns(10);
/*     */ 
/* 487 */     this.r1 = new JTextField();
/* 488 */     this.r1.setForeground(this.red1);
/* 489 */     this.r1.setOpaque(false);
/* 490 */     this.r1.setHorizontalAlignment(0);
/* 491 */     this.r1.setEditable(false);
/* 492 */     this.r1.setColumns(10);
/* 493 */     this.r1.setBorder(null);
/* 494 */     this.r1.setBounds(348, 56, 70, 15);
/* 495 */     add(this.r1);
/*     */ 
/* 497 */     this.g1 = new JTextField();
/* 498 */     this.g1.setForeground(this.green1);
/* 499 */     this.g1.setOpaque(false);
/* 500 */     this.g1.setHorizontalAlignment(0);
/* 501 */     this.g1.setEditable(false);
/* 502 */     this.g1.setColumns(10);
/* 503 */     this.g1.setBorder(null);
/* 504 */     this.g1.setBounds(348, 71, 70, 15);
/* 505 */     add(this.g1);
/*     */ 
/* 507 */     this.b1 = new JTextField();
/* 508 */     this.b1.setForeground(this.blue1);
/* 509 */     this.b1.setOpaque(false);
/* 510 */     this.b1.setHorizontalAlignment(0);
/* 511 */     this.b1.setEditable(false);
/* 512 */     this.b1.setColumns(10);
/* 513 */     this.b1.setBorder(null);
/* 514 */     this.b1.setBounds(348, 86, 70, 15);
/* 515 */     add(this.b1);
/*     */ 
/* 517 */     this.image2txt = new JTextField();
/* 518 */     this.image2txt.setForeground(this.black2);
/* 519 */     this.image2txt.setText("Image 2");
/* 520 */     this.image2txt.setOpaque(false);
/* 521 */     this.image2txt.setHorizontalAlignment(0);
/* 522 */     this.image2txt.setEditable(false);
/* 523 */     this.image2txt.setColumns(10);
/* 524 */     this.image2txt.setBorder(null);
/* 525 */     this.image2txt.setBounds(348, 101, 70, 15);
/* 526 */     add(this.image2txt);
/*     */ 
/* 528 */     this.r2 = new JTextField();
/* 529 */     this.r2.setForeground(this.red2);
/* 530 */     this.r2.setOpaque(false);
/* 531 */     this.r2.setHorizontalAlignment(0);
/* 532 */     this.r2.setEditable(false);
/* 533 */     this.r2.setColumns(10);
/* 534 */     this.r2.setBorder(null);
/* 535 */     this.r2.setBounds(348, 116, 70, 15);
/* 536 */     add(this.r2);
/*     */ 
/* 538 */     this.g3 = new JTextField();
/* 539 */     this.g3.setVisible(false);
/* 540 */     this.g3.setForeground(this.green3);
/* 541 */     this.g3.setOpaque(false);
/* 542 */     this.g3.setHorizontalAlignment(0);
/* 543 */     this.g3.setEditable(false);
/* 544 */     this.g3.setColumns(10);
/* 545 */     this.g3.setBorder(null);
/* 546 */     this.g3.setBounds(348, 191, 70, 15);
/* 547 */     add(this.g3);
/*     */ 
/* 549 */     this.r3 = new JTextField();
/* 550 */     this.r3.setVisible(false);
/* 551 */     this.r3.setForeground(this.red3);
/* 552 */     this.r3.setOpaque(false);
/* 553 */     this.r3.setHorizontalAlignment(0);
/* 554 */     this.r3.setEditable(false);
/* 555 */     this.r3.setColumns(10);
/* 556 */     this.r3.setBorder(null);
/* 557 */     this.r3.setBounds(348, 176, 70, 15);
/* 558 */     add(this.r3);
/*     */ 
/* 560 */     this.image3txt = new JTextField();
/* 561 */     this.image3txt.setVisible(false);
/* 562 */     this.image3txt.setForeground(this.black3);
/* 563 */     this.image3txt.setText("Image 3");
/* 564 */     this.image3txt.setOpaque(false);
/* 565 */     this.image3txt.setHorizontalAlignment(0);
/* 566 */     this.image3txt.setEditable(false);
/* 567 */     this.image3txt.setColumns(10);
/* 568 */     this.image3txt.setBorder(null);
/* 569 */     this.image3txt.setBounds(348, 161, 70, 15);
/* 570 */     add(this.image3txt);
/*     */ 
/* 572 */     this.b2 = new JTextField();
/* 573 */     this.b2.setForeground(this.blue2);
/* 574 */     this.b2.setOpaque(false);
/* 575 */     this.b2.setHorizontalAlignment(0);
/* 576 */     this.b2.setEditable(false);
/* 577 */     this.b2.setColumns(10);
/* 578 */     this.b2.setBorder(null);
/* 579 */     this.b2.setBounds(348, 146, 70, 15);
/* 580 */     add(this.b2);
/*     */ 
/* 582 */     this.g2 = new JTextField();
/* 583 */     this.g2.setForeground(this.green2);
/* 584 */     this.g2.setOpaque(false);
/* 585 */     this.g2.setHorizontalAlignment(0);
/* 586 */     this.g2.setEditable(false);
/* 587 */     this.g2.setColumns(10);
/* 588 */     this.g2.setBorder(null);
/* 589 */     this.g2.setBounds(348, 131, 70, 15);
/* 590 */     add(this.g2);
/*     */ 
/* 592 */     this.b3 = new JTextField();
/* 593 */     this.b3.setVisible(false);
/* 594 */     this.b3.setForeground(this.blue3);
/* 595 */     this.b3.setOpaque(false);
/* 596 */     this.b3.setHorizontalAlignment(0);
/* 597 */     this.b3.setEditable(false);
/* 598 */     this.b3.setColumns(10);
/* 599 */     this.b3.setBorder(null);
/* 600 */     this.b3.setBounds(348, 206, 70, 15);
/* 601 */     add(this.b3);
/*     */ 
/* 603 */     this.txtMean = new JTextField();
/* 604 */     this.txtMean.setText("Mean");
/* 605 */     this.txtMean.setOpaque(false);
/* 606 */     this.txtMean.setHorizontalAlignment(0);
/* 607 */     this.txtMean.setEditable(false);
/* 608 */     this.txtMean.setColumns(10);
/* 609 */     this.txtMean.setBorder(null);
/* 610 */     this.txtMean.setBounds(348, 15, 70, 15);
/* 611 */     add(this.txtMean);
/*     */ 
/* 613 */     this.intens1 = new JTextField();
/* 614 */     this.intens1.setBorder(null);
/* 615 */     this.intens1.setOpaque(false);
/* 616 */     this.intens1.setEditable(false);
/* 617 */     this.intens1.setFont(new Font("Tahoma", 0, 10));
/* 618 */     this.intens1.setHorizontalAlignment(0);
/* 619 */     this.intens1.setText("0%");
/* 620 */     this.intens1.setBounds(58, 275, 30, 15);
/* 621 */     add(this.intens1);
/* 622 */     this.intens1.setColumns(10);
/*     */ 
/* 624 */     this.intens5 = new JTextField();
/* 625 */     this.intens5.setText("100%");
/* 626 */     this.intens5.setOpaque(false);
/* 627 */     this.intens5.setHorizontalAlignment(0);
/* 628 */     this.intens5.setFont(new Font("Tahoma", 0, 10));
/* 629 */     this.intens5.setEditable(false);
/* 630 */     this.intens5.setColumns(10);
/* 631 */     this.intens5.setBorder(null);
/* 632 */     this.intens5.setBounds(313, 275, 30, 15);
/* 633 */     add(this.intens5);
/*     */ 
/* 635 */     this.intens3 = new JTextField();
/* 636 */     this.intens3.setText("50%");
/* 637 */     this.intens3.setOpaque(false);
/* 638 */     this.intens3.setHorizontalAlignment(0);
/* 639 */     this.intens3.setFont(new Font("Tahoma", 0, 10));
/* 640 */     this.intens3.setEditable(false);
/* 641 */     this.intens3.setColumns(10);
/* 642 */     this.intens3.setBorder(null);
/* 643 */     this.intens3.setBounds(186, 275, 30, 15);
/* 644 */     add(this.intens3);
/*     */ 
/* 646 */     this.intens2 = new JTextField();
/* 647 */     this.intens2.setText("25%");
/* 648 */     this.intens2.setOpaque(false);
/* 649 */     this.intens2.setHorizontalAlignment(0);
/* 650 */     this.intens2.setFont(new Font("Tahoma", 0, 10));
/* 651 */     this.intens2.setEditable(false);
/* 652 */     this.intens2.setColumns(10);
/* 653 */     this.intens2.setBorder(null);
/* 654 */     this.intens2.setBounds(122, 275, 30, 15);
/* 655 */     add(this.intens2);
/*     */ 
/* 657 */     this.intens4 = new JTextField();
/* 658 */     this.intens4.setText("75%");
/* 659 */     this.intens4.setOpaque(false);
/* 660 */     this.intens4.setHorizontalAlignment(0);
/* 661 */     this.intens4.setFont(new Font("Tahoma", 0, 10));
/* 662 */     this.intens4.setEditable(false);
/* 663 */     this.intens4.setColumns(10);
/* 664 */     this.intens4.setBorder(null);
/* 665 */     this.intens4.setBounds(250, 275, 30, 15);
/* 666 */     add(this.intens4);
/*     */ 
/* 668 */     this.textField_10 = new JTextField();
/* 669 */     this.textField_10.setText("Frequency");
/* 670 */     this.textField_10.setHorizontalAlignment(0);
/* 671 */     this.textField_10.setForeground(Color.MAGENTA);
/* 672 */     this.textField_10.setFont(new Font("Tahoma", 0, 9));
/* 673 */     this.textField_10.setEditable(false);
/* 674 */     this.textField_10.setColumns(10);
/* 675 */     this.textField_10.setBorder(null);
/* 676 */     this.textField_10.setBounds(10, 0, 53, 16);
/* 677 */     add(this.textField_10);
/*     */ 
/* 679 */     this.intensity = new JTextField();
/* 680 */     this.intensity.setText("Intensity");
/* 681 */     this.intensity.setHorizontalAlignment(0);
/* 682 */     this.intensity.setForeground(Color.MAGENTA);
/* 683 */     this.intensity.setFont(new Font("Tahoma", 0, 9));
/* 684 */     this.intensity.setEditable(false);
/* 685 */     this.intensity.setColumns(10);
/* 686 */     this.intensity.setBorder(null);
/* 687 */     this.intensity.setBounds(348, 272, 53, 16);
/* 688 */     add(this.intensity);
/* 689 */     drawAxis();
/*     */   }
/*     */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.datapanels.RectanglePanel
 * JD-Core Version:    0.6.2
 */