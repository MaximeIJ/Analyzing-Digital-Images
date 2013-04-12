/*     */ package org.gss.adi.tools;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.GraphicsConfiguration;
/*     */ import java.awt.GraphicsDevice;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.HeadlessException;
/*     */ import java.awt.Image;
/*     */ import java.awt.Point;
/*     */ import java.awt.Polygon;
/*     */ import java.awt.Shape;
          import java.awt.geom.Ellipse2D;
          import java.awt.geom.Rectangle2D;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import javax.swing.ImageIcon;
/*     */ 
/*     */ public class ColorTools
/*     */ {
/*  30 */   public static final Color red1 = new Color(16711680);
/*  31 */   public static final Color red2 = new Color(16732240);
/*  32 */   public static final Color red3 = new Color(16748688);
/*  33 */   public static final Color green1 = new Color(43520);
/*  34 */   public static final Color green2 = new Color(5286480);
/*  35 */   public static final Color green3 = new Color(9489552);
/*  36 */   public static final Color blue1 = new Color(255);
/*  37 */   public static final Color blue2 = new Color(5263615);
/*  38 */   public static final Color blue3 = new Color(9474303);
/*  39 */   public static final Color black1 = new Color(0);
/*  40 */   public static final Color black2 = new Color(4210752);
/*  41 */   public static final Color black3 = new Color(7368816);
/*     */   public static final byte CIRCLE = 0;
/*     */   public static final byte FILLED_CIRCLE = 1;
/*     */   public static final byte RECTANGLE = 2;
/*     */   public static final byte FILLED_RECTANGLE = 3;
/*     */   public static final byte NONE = 4;
/*     */ 
/*     */   public static BufferedImage IconToBuffered(ImageIcon img)
/*     */   {
/*  48 */     Image image = img.getImage();
/*  49 */     BufferedImage bimage = null;
/*  50 */     GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
/*     */     try {
/*  52 */       int transparency = 1;
/*  53 */       GraphicsDevice gs = ge.getDefaultScreenDevice();
/*  54 */       GraphicsConfiguration gc = gs.getDefaultConfiguration();
/*  55 */       bimage = gc.createCompatibleImage(
/*  56 */         image.getWidth(null), image.getHeight(null), transparency);
/*     */     }
/*     */     catch (HeadlessException localHeadlessException) {
/*     */     }
/*  60 */     if (bimage == null) {
/*  61 */       int type = 1;
/*  62 */       bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
/*     */     }
/*  64 */     Graphics g = bimage.createGraphics();
/*  65 */     g.drawImage(image, 0, 0, null);
/*  66 */     g.dispose();
/*  67 */     return bimage;
/*     */   }
/*     */ 
/*     */   public static int[] rgb(int i)
/*     */   {
/*  76 */     int blue = i & 0xFF;
/*  77 */     int green = i >> 8 & 0xFF;
/*  78 */     int red = i >> 16 & 0xFF;
/*  79 */     int[] rgb = { red, green, blue };
/*  80 */     return rgb;
/*     */   }
/*     */   public static int toRGB(int[] i) {
/*  83 */     int rgb = 0;
/*  84 */     rgb += (i[0] << 16);
/*  85 */     rgb += (i[1] << 8);
/*  86 */     rgb += i[2];
/*  87 */     return rgb;
/*     */   }
/*     */   public static Double[] rgbPercent(int[] i) {
/*  90 */     Double r = new Double(i[0]);
/*  91 */     Double g = new Double(i[1]);
/*  92 */     Double b = new Double(i[2]);
/*  93 */     Double total = Double.valueOf(r.doubleValue() + g.doubleValue() + b.doubleValue());
/*  94 */     r = Double.valueOf(r.doubleValue() / 2.55D);
/*  95 */     g = Double.valueOf(g.doubleValue() / 2.55D);
/*  96 */     b = Double.valueOf(b.doubleValue() / 2.55D);
/*  97 */     total = Double.valueOf(total.doubleValue() / 7.65D);
/*  98 */     Double[] rgbp = { r, g, b, total };
/*  99 */     return rgbp;
/*     */   }
/*     */ 
/*     */   public static ArrayList<Point> getLinePixels(Integer[] x, Integer[] y)
/*     */   {
/* 110 */     ArrayList coords = new ArrayList();
/* 111 */     Double m = Double.valueOf((y[1].doubleValue() - y[0].doubleValue()) / (x[1].doubleValue() - x[0].doubleValue()));
/* 112 */     if (Math.abs(y[1].doubleValue() - y[0].doubleValue()) / Math.abs(x[1].doubleValue() - x[0].doubleValue()) <= 1.0D) {
/* 113 */       if (x[1].intValue() >= x[0].intValue()) {
/* 114 */         for (int i = 0; i <= x[1].intValue() - x[0].intValue(); i++)
/* 115 */           coords.add(new Point(i + x[0].intValue(), y[0].intValue() + new Double(Math.round(i * m.doubleValue())).intValue()));
/*     */       }
/*     */       else
/* 118 */         for (int i = 0; i <= x[0].intValue() - x[1].intValue(); i++)
/* 119 */           coords.add(new Point(i + x[1].intValue(), y[1].intValue() + new Double(Math.round(i * m.doubleValue())).intValue()));
/*     */     }
/*     */     else
/*     */     {
/* 123 */       m = Double.valueOf(1.0D / m.doubleValue());
/* 124 */       if (y[1].intValue() >= y[0].intValue()) {
/* 125 */         for (int i = 0; i <= y[1].intValue() - y[0].intValue(); i++)
/* 126 */           coords.add(new Point(x[0].intValue() + new Double(Math.round(i * m.doubleValue())).intValue(), i + y[0].intValue()));
/*     */       }
/*     */       else {
/* 129 */         for (int i = 0; i <= y[0].intValue() - y[1].intValue(); i++) {
/* 130 */           coords.add(new Point(x[1].intValue() + new Double(Math.round(i * m.doubleValue())).intValue(), i + y[1].intValue()));
/*     */         }
/*     */       }
/*     */     }
/* 134 */     return coords;
/*     */   }
/*     */ 
/*     */   public static int[] pixelAvg(BufferedImage original, Integer[] x, Integer[] y, String tool)
/*     */   {
/* 146 */     if (tool.equals("Pixel Tool"))
/* 147 */       return rgb(original.getRGB(x[0].intValue(), y[0].intValue()));
/* 148 */     if (tool.equals("Line Tool")) {
/* 149 */       ArrayList line = getLinePixels(x, y);
/* 150 */       int red = 0;
/* 151 */       int green = 0;
/* 152 */       int blue = 0;
/* 153 */       for (int i = 0; i < line.size(); i++) {
/* 154 */         int[] rgb = rgb(original.getRGB(((Point)line.get(i)).x, ((Point)line.get(i)).y));
/* 155 */         red += rgb[0];
/* 156 */         green += rgb[1];
/* 157 */         blue += rgb[2];
/*     */       }
/* 159 */       red /= line.size();
/* 160 */       green /= line.size();
/* 161 */       blue /= line.size();
/* 162 */       int[] rgb = { red, green, blue };
/* 163 */       return rgb;
/* 164 */     }if (tool.equals("Path Tool (multiple points)")) {
/* 165 */       int red = 0;
/* 166 */       int green = 0;
/* 167 */       int blue = 0;
                int i = 0;
/* 168 */       for (; 
/* 169 */         i + 1 < x.length; i++) {
/* 170 */         int[] rgb = pixelAvg(original, new Integer[] { x[i], x[(i + 1)] }, new Integer[] { y[i], y[(i + 1)] }, "Line Tool");
/* 171 */         red += rgb[0];
/* 172 */         green += rgb[1];
/* 173 */         blue += rgb[2];
/*     */       }
/* 175 */       red /= (i + 1);
/* 176 */       green /= (i + 1);
/* 177 */       blue /= (i + 1);
/* 178 */       return new int[] { red, green, blue };
/* 179 */     }if (tool.contains("Rectangle")) {
/* 180 */       int red = 0;
/* 181 */       int green = 0;
/* 182 */       int blue = 0;
/*     */       int xmax;
/*     */       int xmin;
/* 187 */       if (x[0].intValue() <= x[1].intValue()) {
/* 188 */         xmin = x[0].intValue();
/* 189 */         xmax = x[1].intValue();
/*     */       } else {
/* 191 */         xmin = x[1].intValue();
/* 192 */         xmax = x[0].intValue();
/*     */       }
/*     */       int ymax;
/*     */       int ymin;
/* 194 */       if (y[0].intValue() <= y[1].intValue()) {
/* 195 */         ymin = y[0].intValue();
/* 196 */         ymax = y[1].intValue();
/*     */       } else {
/* 198 */         ymin = y[1].intValue();
/* 199 */         ymax = y[0].intValue();
/*     */       }
/* 201 */       int count = 0;
/* 202 */       for (int i = xmin; i <= xmax; i++) {
/* 203 */         for (int j = ymin; j <= ymax; j++) {
/* 204 */           count++;
/* 205 */           int[] rgb = rgb(original.getRGB(i, j));
/* 206 */           red += rgb[0];
/* 207 */           green += rgb[1];
/* 208 */           blue += rgb[2];
/*     */         }
/*     */       }
/* 211 */       if (count != 0) {
/* 212 */         red /= count;
/* 213 */         green /= count;
/* 214 */         blue /= count;
/* 215 */         int[] rgb = { red, green, blue };
/* 216 */         return rgb;
/*     */       }
/* 218 */       return new int[3];
/*     */     }
/* 220 */     if (tool.contains("Polygon")) {
/* 221 */       int xmin = 9000;
/* 222 */       int xmax = 0;
/* 223 */       int ymin = 9000;
/* 224 */       int ymax = 0;
/* 225 */       int[] X = new int[x.length];
/* 226 */       int[] Y = new int[y.length];
/* 227 */       for (int i = 0; i < x.length; i++) {
/* 228 */         X[i] = x[i].intValue();
/* 229 */         if (x[i].intValue() > xmax) xmax = x[i].intValue();
/* 230 */         if (x[i].intValue() < xmin) xmin = x[i].intValue();
/* 231 */         if (y[i].intValue() > ymax) ymax = y[i].intValue();
/* 232 */         if (y[i].intValue() < ymin) ymin = y[i].intValue();
/* 233 */         Y[i] = y[i].intValue();
/*     */       }
/* 235 */       int red = 0;
/* 236 */       int green = 0;
/* 237 */       int blue = 0;
/* 238 */       int count = 0;
/* 239 */       Polygon poly = new Polygon(X, Y, X.length);
/* 240 */       for (int i = xmin; i <= xmax; i++) {
/* 241 */         for (int j = ymin; j <= ymax; j++) {
/* 242 */           if (poly.contains(i, j)) {
/* 243 */             count++;
/* 244 */             int[] rgb = rgb(original.getRGB(i, j));
/* 245 */             red += rgb[0];
/* 246 */             green += rgb[1];
/* 247 */             blue += rgb[2];
/*     */           }
/*     */         }
/*     */       }
/* 251 */       if (count != 0) {
/* 252 */         red /= count;
/* 253 */         green /= count;
/* 254 */         blue /= count;
/* 255 */         return new int[] { red, green, blue };
/*     */       }
/* 257 */       return new int[3];
/*     */     }
/* 259 */     if (tool.equals("Angle Tool")) {
/* 260 */       return new int[3];
/*     */     }
/* 262 */     return null;
/*     */   }
/*     */   public static int[] maskedPixelAverage(BufferedImage mask, BufferedImage original, Integer[] x, Integer[] y, String tool) {
/* 265 */     if (tool.equals("Pixel Tool")) {
/* 266 */       int[] rgb = rgb(original.getRGB(x[0].intValue(), y[0].intValue()));
/* 267 */       for (int i = 0; i < 3; i++) {
/* 268 */         rgb[i] &= (mask.getRGB(x[0].intValue(), y[0].intValue()) ^ 0xFFFFFFFF);
/*     */       }
/* 270 */       return rgb;
/* 271 */     }if (tool.equals("Line Tool")) {
/* 272 */       ArrayList line = getLinePixels(x, y);
/* 273 */       int red = 0;
/* 274 */       int green = 0;
/* 275 */       int blue = 0;
/* 276 */       int count = 0;
/* 277 */       for (int i = 0; i < line.size(); i++) {
/* 278 */         if ((mask.getRGB(((Point)line.get(i)).x, ((Point)line.get(i)).y) & 0xFFFFFF) == 0) {
/* 279 */           int[] rgb = rgb(original.getRGB(((Point)line.get(i)).x, ((Point)line.get(i)).y));
/* 280 */           red += rgb[0];
/* 281 */           green += rgb[1];
/* 282 */           blue += rgb[2];
/* 283 */           count++;
/*     */         }
/*     */       }
/* 286 */       red /= count;
/* 287 */       green /= count;
/* 288 */       blue /= count;
/* 289 */       int[] rgb = { red, green, blue };
/* 290 */       return rgb;
/* 291 */     }if (tool.equals("Path Tool (multiple points)")) {
/* 292 */       int red = 0;
/* 293 */       int green = 0;
/* 294 */       int blue = 0;
                int i = 0;
/* 295 */       for (; 
/* 296 */         i + 1 < x.length; i++) {
/* 297 */         int[] rgb = maskedPixelAverage(mask, original, new Integer[] { x[i], x[(i + 1)] }, new Integer[] { y[i], y[(i + 1)] }, "Line Tool");
/* 298 */         red += rgb[0];
/* 299 */         green += rgb[1];
/* 300 */         blue += rgb[2];
/*     */       }
/* 302 */       red /= (i + 1);
/* 303 */       green /= (i + 1);
/* 304 */       blue /= (i + 1);
/* 305 */       return new int[] { red, green, blue };
/* 306 */     }if (tool.contains("Rectangle")) {
/* 307 */       int red = 0;
/* 308 */       int green = 0;
/* 309 */       int blue = 0;
/*     */       int xmax;
/*     */       int xmin;
/* 314 */       if (x[0].intValue() <= x[1].intValue()) {
/* 315 */         xmin = x[0].intValue();
/* 316 */         xmax = x[1].intValue();
/*     */       } else {
/* 318 */         xmin = x[1].intValue();
/* 319 */         xmax = x[0].intValue();
/*     */       }
/*     */       int ymax;
/*     */       int ymin;
/* 321 */       if (y[0].intValue() <= y[1].intValue()) {
/* 322 */         ymin = y[0].intValue();
/* 323 */         ymax = y[1].intValue();
/*     */       } else {
/* 325 */         ymin = y[1].intValue();
/* 326 */         ymax = y[0].intValue();
/*     */       }
/* 328 */       int count = 0;
/* 329 */       for (int i = xmin; i <= xmax; i++) {
/* 330 */         for (int j = ymin; j <= ymax; j++) {
/* 331 */           if ((mask.getRGB(i, j) & 0xFFFFFF) == 0) {
/* 332 */             count++;
/* 333 */             int[] rgb = rgb(original.getRGB(i, j));
/* 334 */             red += rgb[0];
/* 335 */             green += rgb[1];
/* 336 */             blue += rgb[2];
/*     */           }
/*     */         }
/*     */       }
/* 340 */       if (count != 0) {
/* 341 */         red /= count;
/* 342 */         green /= count;
/* 343 */         blue /= count;
/* 344 */         int[] rgb = { red, green, blue };
/* 345 */         return rgb;
/*     */       }
/* 347 */       return new int[3];
/*     */     }
/* 349 */     if (tool.contains("Polygon")) {
/* 350 */       int xmin = 9000;
/* 351 */       int xmax = 0;
/* 352 */       int ymin = 9000;
/* 353 */       int ymax = 0;
/* 354 */       int[] X = new int[x.length];
/* 355 */       int[] Y = new int[y.length];
/* 356 */       for (int i = 0; i < x.length; i++) {
/* 357 */         X[i] = x[i].intValue();
/* 358 */         if (x[i].intValue() > xmax) xmax = x[i].intValue();
/* 359 */         if (x[i].intValue() < xmin) xmin = x[i].intValue();
/* 360 */         if (y[i].intValue() > ymax) ymax = y[i].intValue();
/* 361 */         if (y[i].intValue() < ymin) ymin = y[i].intValue();
/* 362 */         Y[i] = y[i].intValue();
/*     */       }
/* 364 */       int red = 0;
/* 365 */       int green = 0;
/* 366 */       int blue = 0;
/* 367 */       int count = 0;
/* 368 */       Polygon poly = new Polygon(X, Y, X.length);
/* 369 */       for (int i = xmin; i <= xmax; i++) {
/* 370 */         for (int j = ymin; j <= ymax; j++) {
/* 371 */           if ((poly.contains(i, j)) && 
/* 372 */             ((mask.getRGB(i, j) & 0xFFFFFF) == 0)) {
/* 373 */             count++;
/* 374 */             int[] rgb = rgb(original.getRGB(i, j));
/* 375 */             red += rgb[0];
/* 376 */             green += rgb[1];
/* 377 */             blue += rgb[2];
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 382 */       if (count != 0) {
/* 383 */         red /= count;
/* 384 */         green /= count;
/* 385 */         blue /= count;
/* 386 */         return new int[] { red, green, blue };
/*     */       }
/* 388 */       return new int[3];
/*     */     }
/* 390 */     if (tool.equals("Angle Tool")) {
/* 391 */       return new int[3];
/*     */     }
/* 393 */     return null;
/*     */   }
/*     */ 
/*     */   public static BufferedImage cursor(BufferedImage original, Integer[] x, Integer[] y, Color color, String tool, Float lineWidth, byte cursorStyle)
/*     */   {
/* 405 */     int w = original.getWidth();
/* 406 */     int h = original.getHeight();
/* 407 */     BufferedImage img = new BufferedImage(
/* 408 */       w, h, 1);
/* 409 */     Graphics2D g2d = img.createGraphics();
/* 410 */     g2d.drawImage(original, 0, 0, null);
/* 411 */     g2d.setPaint(color);
/* 412 */     g2d.setStroke(new BasicStroke(lineWidth.floatValue()));
/* 413 */     if ((tool.equals("Pixel Tool")) || (tool.contains("Point"))) {
/* 414 */       drawCursorPoint(img, lineWidth.floatValue(), cursorStyle, x[0], y[0], color);
/* 415 */       g2d.drawLine(x[0].intValue(), y[0].intValue() + 7, x[0].intValue(), y[0].intValue() - 7);
/* 416 */       g2d.drawLine(x[0].intValue() + 7, y[0].intValue(), x[0].intValue() - 7, y[0].intValue());
/* 417 */     } else if (tool.contains("Line")) {
/* 418 */       g2d.drawLine(x[0].intValue(), y[0].intValue(), x[1].intValue(), y[1].intValue());
/* 419 */       drawCursorPoint(img, lineWidth.floatValue(), cursorStyle, x[0], y[0], color);
/* 420 */       drawCursorPoint(img, lineWidth.floatValue(), cursorStyle, x[1], y[1], color);
/* 421 */     } else if (tool.contains("Path")) {
/* 422 */       for (int i = 0; i + 1 < x.length; i++) {
/* 423 */         drawCursorPoint(img, lineWidth.floatValue(), cursorStyle, x[i], y[i], color);
/* 424 */         g2d.drawLine(x[i].intValue(), y[i].intValue(), x[(i + 1)].intValue(), y[(i + 1)].intValue());
/*     */       }
/* 426 */       drawCursorPoint(img, lineWidth.floatValue(), cursorStyle, x[(x.length - 1)], y[(x.length - 1)], color);
/* 427 */     } else if (tool.contains("Rectangle")) {
/* 428 */       drawCursorPoint(img, lineWidth.floatValue(), cursorStyle, x[0], y[0], color);
/* 429 */       drawCursorPoint(img, lineWidth.floatValue(), cursorStyle, x[1], y[1], color);
/* 430 */       g2d.drawLine(x[0].intValue(), y[0].intValue(), x[1].intValue(), y[0].intValue());
/* 431 */       g2d.drawLine(x[1].intValue(), y[0].intValue(), x[1].intValue(), y[1].intValue());
/* 432 */       g2d.drawLine(x[1].intValue(), y[1].intValue(), x[0].intValue(), y[1].intValue());
/* 433 */       g2d.drawLine(x[0].intValue(), y[1].intValue(), x[0].intValue(), y[0].intValue());
/* 434 */     } else if (tool.contains("Polygon")) {
/* 435 */       boolean completed = false;
/* 436 */       for (int i = 0; i + 1 < x.length; i++) {
/* 437 */         drawCursorPoint(img, lineWidth.floatValue(), cursorStyle, x[i], y[i], color);
/* 438 */         if (linearDist(x[0].intValue(), y[0].intValue(), x[(i + 1)].intValue(), y[(i + 1)].intValue()).doubleValue() < 6.0D) {
/* 439 */           g2d.drawLine(x[i].intValue(), y[i].intValue(), x[0].intValue(), y[0].intValue());
/* 440 */           completed = true;
/* 441 */           break;
/*     */         }
/* 443 */         g2d.drawLine(x[i].intValue(), y[i].intValue(), x[(i + 1)].intValue(), y[(i + 1)].intValue());
/*     */       }
/*     */ 
/* 446 */       if (!completed)
/* 447 */         drawCursorPoint(img, lineWidth.floatValue(), cursorStyle, x[(x.length - 1)], y[(x.length - 1)], color);
/*     */     }
/* 449 */     else if (tool.equals("Angle Tool")) {
/* 450 */       drawCursorPoint(img, lineWidth.floatValue(), cursorStyle, x[0], y[0], color);
/* 451 */       if (x[1].intValue() >= 0) {
/* 452 */         System.out.println("x[1] != -1");
/* 453 */         drawCursorPoint(img, lineWidth.floatValue(), cursorStyle, x[1], y[1], color);
/* 454 */         g2d.drawLine(x[0].intValue(), y[0].intValue(), x[1].intValue(), y[1].intValue());
/*     */       }
/* 456 */       if (x[2].intValue() >= 0) {
/* 457 */         drawCursorPoint(img, lineWidth.floatValue(), cursorStyle, x[2], y[2], color);
/* 458 */         g2d.drawLine(x[1].intValue(), y[1].intValue(), x[2].intValue(), y[2].intValue());
/*     */       }
/*     */     }
/* 461 */     g2d.dispose();
/* 462 */     return img;
/*     */   }
/*     */   private static void drawCursorPoint(BufferedImage img, float lineWidth, byte cursorStyle, Integer x, Integer y, Color c) {
/* 465 */     Graphics2D g = img.createGraphics();
/* 466 */     g.setColor(c);
/* 467 */     switch (cursorStyle) {
/*     */     case 0:
/* 469 */       Shape cursor = new Ellipse2D.Float(x.intValue() - 3.0F, y.intValue() - 3.0F, 6.0F, 6.0F);
/* 470 */       g.draw(cursor);
/* 471 */       break;
/*     */     case 1:
/* 473 */       cursor = new Ellipse2D.Float(x.intValue() - 3.0F, y.intValue() - 3.0F, 6.0F, 6.0F);
/* 474 */       g.fill(cursor);
/* 475 */       break;
/*     */     case 2:
/* 477 */       cursor = new Rectangle2D.Float(x.intValue() - 3.0F, y.intValue() - 3.0F, 6.0F, 6.0F);
/* 478 */       g.draw(cursor);
/* 479 */       break;
/*     */     case 3:
/* 481 */       cursor = new Rectangle2D.Float(x.intValue() - 3.0F, y.intValue() - 3.0F, 6.0F, 6.0F);
/* 482 */       g.fill(cursor);
/* 483 */       break;
/*     */     case 4:
/*     */     }
/*     */ 
/* 487 */     g.dispose();
/*     */   }
/*     */ 
/*     */   public static Double angle(Integer[] x, Integer[] y)
/*     */   {
/* 497 */     Double a = linearDist(x[0].intValue(), y[0].intValue(), x[1].intValue(), y[1].intValue());
/* 498 */     Double b = linearDist(x[1].intValue(), y[1].intValue(), x[2].intValue(), y[2].intValue());
/* 499 */     Double c = linearDist(x[0].intValue(), y[0].intValue(), x[2].intValue(), y[2].intValue());
/* 500 */     return Double.valueOf(180.0D * Math.acos((Math.pow(a.doubleValue(), 2.0D) + Math.pow(b.doubleValue(), 2.0D) - Math.pow(c.doubleValue(), 2.0D)) / (2.0D * a.doubleValue() * b.doubleValue())) / 3.141592653589793D);
/*     */   }
/*     */ 
/*     */   public static Double linearDist(int x0, int y0, int x1, int y1)
/*     */   {
/* 512 */     double xlen = Math.pow(Math.abs(x0 - x1), 2.0D);
/* 513 */     double ylen = Math.pow(Math.abs(y0 - y1), 2.0D);
/* 514 */     return Double.valueOf(Math.sqrt(xlen + ylen));
/*     */   }
/*     */   public static Double pathDist(Integer[] x, Integer[] y) {
/* 517 */     Double distance = Double.valueOf(0.0D);
/* 518 */     for (int i = 0; i + 1 < x.length; i++) {
/* 519 */       distance = Double.valueOf(distance.doubleValue() + linearDist(x[i].intValue(), y[i].intValue(), x[(i + 1)].intValue(), y[(i + 1)].intValue()).doubleValue());
/*     */     }
/* 521 */     return distance;
/*     */   }
/*     */   public static Integer polyArea(Integer[] x, Integer[] y) {
/* 524 */     int xmin = 1000;
/* 525 */     int xmax = -1;
/* 526 */     int ymin = 1000;
/* 527 */     int ymax = -1;
/* 528 */     int[] X = new int[x.length];
/* 529 */     int[] Y = new int[y.length];
/* 530 */     for (int i = 0; i < x.length; i++) {
/* 531 */       X[i] = x[i].intValue();
/* 532 */       if (x[i].intValue() > xmax) xmax = x[i].intValue();
/* 533 */       if (x[i].intValue() < xmin) xmin = x[i].intValue();
/* 534 */       if (y[i].intValue() > ymax) ymax = y[i].intValue();
/* 535 */       if (y[i].intValue() < ymin) ymin = y[i].intValue();
/* 536 */       Y[i] = y[i].intValue();
/*     */     }
/* 538 */     int count = 0;
/* 539 */     Polygon poly = new Polygon(X, Y, X.length);
/* 540 */     for (int i = xmin; i <= xmax; i++) {
/* 541 */       for (int j = ymin; j <= ymax; j++) {
/* 542 */         if (poly.contains(i, j)) {
/* 543 */           count++;
/*     */         }
/*     */       }
/*     */     }
/* 547 */     return Integer.valueOf(count);
/*     */   }
/*     */   public static BufferedImage NDVIImage(BufferedImage img) {
/* 550 */     BufferedImage ndvi = new BufferedImage(img.getWidth(), img.getHeight(), 1);
/* 551 */     for (int i = 0; i < img.getWidth(); i++) {
/* 552 */       for (int j = 0; j < img.getHeight(); j++) {
/* 553 */         int[] rgb = rgb(img.getRGB(i, j));
/* 554 */         if (rgb[0] + rgb[1] > 0)
/* 555 */           ndvi.setRGB(i, j, NDVIColor((rgb[0] - rgb[1]) * 1.0F / (rgb[0] * 1.0F + rgb[1])));
/*     */         else
/* 557 */           ndvi.setRGB(i, j, NDVIColor(-1.0F));
/*     */       }
/*     */     }
/* 560 */     return ndvi;
/*     */   }
/*     */   public static int NDVIColor(float i) {
/* 563 */     int r = 0;
/* 564 */     int g = 0;
/* 565 */     int b = 0;
/* 566 */     if (i > 0.8D) {
/* 567 */       g = 255;
/* 568 */       r = Math.round(255.0F - 1275.0F * (i - 0.8F));
/* 569 */     } else if (i > 0.6D) {
/* 570 */       r = 255;
/* 571 */       g = Math.round(1275.0F * (i - 0.6F));
/* 572 */     } else if (i > 0.4D) {
/* 573 */       r = 255;
/* 574 */       b = Math.round(255.0F - 1275.0F * (i - 0.4F));
/* 575 */     } else if (i > 0.2D) {
/* 576 */       b = 255;
/* 577 */       r = Math.round(1275.0F * (i - 0.2F));
/* 578 */     } else if (i > 0.0F) {
/* 579 */       b = 255;
/* 580 */       g = Math.round(255.0F - 1275.0F * i);
/*     */     } else {
/* 582 */       r = g = b = Math.round(255.0F - 255.0F * (i + 1.0F));
/*     */     }
/* 584 */     return toRGB(new int[] { r, g, b });
/*     */   }
/*     */   public static float colorToNDVI(int[] rgb) {
/* 587 */     if ((rgb[0] == rgb[1]) && (rgb[0] == rgb[2]))
/* 588 */       return rgb[0] / -255.0F;
/* 589 */     if (rgb[0] == 255) {
/* 590 */       if (rgb[1] == 0) {
/* 591 */         return (rgb[2] - 255 - 510.0F) / -1275.0F;
/*     */       }
/* 593 */       return (rgb[1] + 765.0F) / 1275.0F;
/*     */     }
/* 595 */     if (rgb[2] == 255) {
/* 596 */       if (rgb[0] == 0) {
/* 597 */         return (rgb[1] - 255) / -1275.0F;
/*     */       }
/* 599 */       return (rgb[0] + 255.0F) / 1275.0F;
/*     */     }
/*     */ 
/* 602 */     return (rgb[0] - 255 - 1020.0F) / -1275.0F;
/*     */   }
/*     */ 
/*     */   public static float colorToNDVI(int i) {
/* 606 */     int[] rgb = rgb(i);
/* 607 */     if ((rgb[0] == rgb[1]) && (rgb[0] == rgb[2]))
/* 608 */       return rgb[0] / -255.0F;
/* 609 */     if (rgb[0] == 255) {
/* 610 */       if (rgb[1] == 0) {
/* 611 */         return (rgb[2] - 255 - 510.0F) / -1275.0F;
/*     */       }
/* 613 */       return (rgb[1] + 765.0F) / 1275.0F;
/*     */     }
/* 615 */     if (rgb[2] == 255) {
/* 616 */       if (rgb[0] == 0) {
/* 617 */         return (rgb[1] - 255) / -1275.0F;
/*     */       }
/* 619 */       return (rgb[0] + 255.0F) / 1275.0F;
/*     */     }
/*     */ 
/* 622 */     return (rgb[0] - 255 - 1020.0F) / -1275.0F;
/*     */   }
/*     */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.tools.ColorTools
 * JD-Core Version:    0.6.2
 */