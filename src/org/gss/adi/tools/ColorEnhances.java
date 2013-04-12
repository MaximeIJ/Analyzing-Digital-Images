/*     */ package org.gss.adi.tools;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.image.AffineTransformOp;
/*     */ import java.awt.image.BufferedImage;
/*     */ 
/*     */ public class ColorEnhances
/*     */ {
/*     */   public static final byte BLUE = 2;
/*     */   public static final byte GREEN = 1;
/*     */   public static final byte RED = 0;
/*     */   public static final byte ALL = -1;
/*     */   public static final int RED_MASK = 16711680;
/*     */   public static final int GREEN_MASK = 65280;
/*     */   public static final int BLUE_MASK = 255;
/*     */   public static final int WHITE_MASK = 16777215;
/*     */ 
/*     */   public static BufferedImage[] getColorDistributions(BufferedImage original)
/*     */   {
/*  27 */     int[] redSizes = new int[256];
/*  28 */     int[] greenSizes = new int[256];
/*  29 */     int[] blueSizes = new int[256];
/*  30 */     for (int i = 0; i < original.getWidth(); i++) {
/*  31 */       for (int j = 0; j < original.getHeight(); j++) {
/*  32 */         int[] rgb = ColorTools.rgb(original.getRGB(i, j));
/*  33 */         redSizes[rgb[0]] += 1;
/*  34 */         greenSizes[rgb[1]] += 1;
/*  35 */         blueSizes[rgb[2]] += 1;
/*     */       }
/*     */     }
/*  38 */     int[] redSizesF = filterNoise(redSizes);
/*  39 */     int[] greenSizesF = filterNoise(greenSizes);
/*  40 */     int[] blueSizesF = filterNoise(blueSizes);
/*  41 */     int rMax = 1;
/*  42 */     int gMax = 1;
/*  43 */     int bMax = 1;
/*     */ 
/*  48 */     for (int i = 2; i < 255; i++) {
/*  49 */       if (redSizesF[i] > rMax) rMax = redSizesF[i];
/*  50 */       if (greenSizesF[i] > gMax) gMax = greenSizesF[i];
/*  51 */       if (blueSizesF[i] > bMax) bMax = blueSizesF[i];
/*     */     }
/*  53 */     BufferedImage red = new BufferedImage(
/*  54 */       255, rMax, 1);
/*  55 */     BufferedImage green = new BufferedImage(
/*  56 */       255, gMax, 1);
/*     */ 
/*  58 */     BufferedImage blue = new BufferedImage(
/*  59 */       255, bMax, 1);
/*  60 */     int[] array = new int[red.getWidth() * red.getHeight()];
/*  61 */     for (int i = 0; i < array.length; i++) {
/*  62 */       array[i] = -6250336;
/*     */     }
/*  64 */     red.setRGB(0, 0, red.getWidth(), red.getHeight(), array, 0, red.getWidth());
/*  65 */     Graphics2D rg = red.createGraphics();
/*  66 */     array = new int[green.getWidth() * green.getHeight()];
/*  67 */     for (int i = 0; i < array.length; i++) {
/*  68 */       array[i] = -6250336;
/*     */     }
/*  70 */     green.setRGB(0, 0, green.getWidth(), green.getHeight(), array, 0, green.getWidth());
/*  71 */     array = new int[blue.getWidth() * blue.getHeight()];
/*  72 */     for (int i = 0; i < array.length; i++) {
/*  73 */       array[i] = -6250336;
/*     */     }
/*  75 */     blue.setRGB(0, 0, blue.getWidth(), blue.getHeight(), array, 0, blue.getWidth());
/*  76 */     Graphics2D gg = green.createGraphics();
/*  77 */     Graphics2D bg = blue.createGraphics();
/*  78 */     for (int i = 0; i < 255; i++) {
/*  79 */       rg.setColor(new Color(i << 16));
/*  80 */       rg.drawLine(i, rMax, i, rMax - redSizes[i]);
/*  81 */       gg.setColor(new Color(i << 8));
/*  82 */       gg.drawLine(i, gMax, i, gMax - greenSizes[i]);
/*  83 */       bg.setColor(new Color(i));
/*  84 */       bg.drawLine(i, bMax, i, bMax - blueSizes[i]);
/*     */     }
/*  86 */     rg.dispose();
/*  87 */     red = resize(red);
/*  88 */     green = resize(green);
/*  89 */     blue = resize(blue);
/*  90 */     return new BufferedImage[] { red, green, blue };
/*     */   }
/*     */   private static BufferedImage resize(BufferedImage before) {
/*  93 */     double w = 255.0D / before.getWidth();
/*  94 */     double h = 45.0D / before.getHeight();
/*  95 */     BufferedImage after = new BufferedImage(255, 45, before.getType());
/*  96 */     AffineTransform at = new AffineTransform();
/*  97 */     at.scale(w, h);
/*  98 */     AffineTransformOp scaleOp = 
/*  99 */       new AffineTransformOp(at, 1);
/* 100 */     after = scaleOp.filter(before, after);
/* 101 */     return after;
/*     */   }
/*     */ 
/*     */   public static int[] filterNoise(int[] originalDist)
/*     */   {
/* 111 */     int[] filteredDist = new int[originalDist.length];
/* 112 */     filteredDist[(filteredDist.length - 1)] = originalDist[(filteredDist.length - 2)];
/* 113 */     originalDist[(filteredDist.length - 1)] = originalDist[(filteredDist.length - 2)];
/* 114 */     filteredDist[0] = originalDist[1];
/* 115 */     originalDist[0] = originalDist[1];
/* 116 */     for (int i = 1; i < originalDist.length - 1; i++) {
/* 117 */       if (((originalDist[i] > 1.5D * originalDist[(i - 1)]) && (originalDist[i] > 1.5D * originalDist[(i + 1)])) || (originalDist[i] > 1.75D * originalDist[(i - 1)]) || (originalDist[i] > 1.75D * originalDist[(i + 1)]))
/* 118 */         filteredDist[i] = ((originalDist[(i - 1)] + originalDist[(i + 1)]) / 2);
/*     */       else {
/* 120 */         filteredDist[i] = originalDist[i];
/*     */       }
/*     */     }
/* 123 */     filteredDist[0] = filteredDist[1];
/* 124 */     filteredDist[(filteredDist.length - 1)] = filteredDist[(filteredDist.length - 2)];
/* 125 */     return filteredDist;
/*     */   }
/*     */   public static BufferedImage newInvert(boolean[] colors, BufferedImage img) {
/* 128 */     if (colors.length == 0)
/* 129 */       return img;
/* 130 */     boolean r = colors[0];
/* 131 */     boolean g = colors[1];
/* 132 */     boolean b = colors[2];
/* 133 */     int w = img.getWidth();
/* 134 */     int h = img.getHeight();
/* 135 */     int wh = w * h;
/* 136 */     int[] mapin = new int[wh];
/* 137 */     int[] mapout = new int[wh];
/* 138 */     img.getRGB(0, 0, w, h, mapin, 0, w);
/* 139 */     for (int i = 0; i < wh; i++) {
/* 140 */       int c = mapin[i];
/* 141 */       int result = -16777216;
/* 142 */       if (r)
/* 143 */         result += 16711680 - (c & 0xFF0000);
/*     */       else
/* 145 */         result += (c & 0xFF0000);
/* 146 */       if (g)
/* 147 */         result += 65280 - (c & 0xFF00);
/*     */       else
/* 149 */         result += (c & 0xFF00);
/* 150 */       if (b)
/* 151 */         result += 255 - (c & 0xFF);
/*     */       else
/* 153 */         result += (c & 0xFF);
/* 154 */       mapout[i] = result;
/*     */     }
/* 156 */     BufferedImage out = new BufferedImage(w, h, 1);
/* 157 */     out.setRGB(0, 0, w, h, mapout, 0, w);
/* 158 */     return out;
/*     */   }
/*     */ 
/*     */   public static BufferedImage newLimitColor(int[] start, int[] end, BufferedImage img) {
/* 162 */     int redMin = start[0];
/* 163 */     int redMax = end[0];
/* 164 */     int greenMin = start[1];
/* 165 */     int greenMax = end[1];
/* 166 */     int blueMin = start[2];
/* 167 */     int blueMax = end[2];
/* 168 */     int width = img.getWidth();
/* 169 */     int height = img.getHeight();
/* 170 */     int wh = width * height;
/* 171 */     int[] mapin = new int[wh];
/* 172 */     int[] mapout = new int[wh];
/* 173 */     img.getRGB(0, 0, width, height, mapin, 0, width);
/* 174 */     for (int i = 0; i < wh; i++) {
/* 175 */       int c = mapin[i];
/* 176 */       int r = c >> 16 & 0xFF;
/* 177 */       if ((r < redMin) || (r >= redMax))
/* 178 */         r = 0;
/* 179 */       int g = c >> 8 & 0xFF;
/* 180 */       if ((g < greenMin) || (g >= greenMax))
/* 181 */         g = 0;
/* 182 */       int b = c & 0xFF;
/* 183 */       if ((b < blueMin) || (b >= blueMax))
/* 184 */         b = 0;
/* 185 */       mapout[i] = (-16777216 + (r << 16) + (g << 8) + b);
/*     */     }
/* 187 */     BufferedImage out = new BufferedImage(width, height, 1);
/* 188 */     out.setRGB(0, 0, width, height, mapout, 0, width);
/* 189 */     return out;
/*     */   }
/*     */   public static BufferedImage newStretchColor(int[] start, int[] end, BufferedImage img) {
/* 192 */     int redMin = start[0];
/* 193 */     int redMax = end[0];
/* 194 */     int rDif = redMax - redMin - 1;
/* 195 */     int greenMin = start[1];
/* 196 */     int greenMax = end[1];
/* 197 */     int gDif = greenMax - greenMin - 1;
/* 198 */     int blueMin = start[2];
/* 199 */     int blueMax = end[2];
/* 200 */     int bDif = blueMax - blueMin - 1;
/* 201 */     int width = img.getWidth();
/* 202 */     int height = img.getHeight();
/* 203 */     int wh = width * height;
/* 204 */     int[] mapin = new int[wh];
/* 205 */     int[] mapout = new int[wh];
/* 206 */     img.getRGB(0, 0, width, height, mapin, 0, width);
/* 207 */     for (int i = 0; i < wh; i++) {
/* 208 */       int c = mapin[i];
/* 209 */       int r = c >> 16 & 0xFF;
/* 210 */       if ((r >= redMin) && (r < redMax))
/* 211 */         r = 255 * (r - redMin) / rDif;
/*     */       else
/* 213 */         r = 0;
/* 214 */       int g = c >> 8 & 0xFF;
/* 215 */       if ((g >= greenMin) && (g < greenMax))
/* 216 */         g = 255 * (g - greenMin) / gDif;
/*     */       else
/* 218 */         g = 0;
/* 219 */       int b = c & 0xFF;
/* 220 */       if ((b >= blueMin) && (b < blueMax))
/* 221 */         b = 255 * (b - blueMin) / bDif;
/*     */       else
/* 223 */         b = 0;
/* 224 */       mapout[i] = (-16777216 + (r << 16) + (g << 8) + b);
/*     */     }
/* 226 */     BufferedImage out = new BufferedImage(width, height, 1);
/* 227 */     out.setRGB(0, 0, width, height, mapout, 0, width);
/* 228 */     return out;
/*     */   }
/*     */   public static BufferedImage newOffColor(byte[] color, BufferedImage img) {
/* 231 */     if (color.length == 0) return img;
/* 232 */     int w = img.getWidth();
/* 233 */     int h = img.getHeight();
/* 234 */     int wh = w * h;
/* 235 */     int[] mapin = new int[wh];
/* 236 */     int[] mapout = new int[wh];
/* 237 */     img.getRGB(0, 0, w, h, mapin, 0, w);
/*     */     int mask;
/* 239 */     if (color.length == 3) {
/* 240 */       mask = -16777216;
/*     */     } else {
/* 242 */       mask = -1;
/* 243 */       switch (color[0]) {
/*     */       case 0:
/* 245 */         mask -= 16711680;
/* 246 */         break;
/*     */       case 1:
/* 248 */         mask -= 65280;
/* 249 */         break;
/*     */       case 2:
/* 251 */         mask -= 255;
/*     */       }
/*     */ 
/* 254 */       if (color.length == 
/* 254 */         2) {
/* 255 */         switch (color[1]) {
/*     */         case 1:
/* 257 */           mask -= 65280;
/* 258 */           break;
/*     */         case 2:
/* 260 */           mask -= 255;
/*     */         }
/*     */       }
/*     */     }
/* 264 */     for (int i = 0; i < wh; i++) {
/* 265 */       mapin[i] &= mask;
/*     */     }
/* 267 */     BufferedImage out = new BufferedImage(w, h, 1);
/* 268 */     out.setRGB(0, 0, w, h, mapout, 0, w);
/* 269 */     return out;
/*     */   }
/*     */   public static BufferedImage normalize(byte[] color, BufferedImage img) {
/* 272 */     if (color.length != 2) return img;
/* 273 */     BufferedImage normalized = new BufferedImage(img.getWidth(), img.getHeight(), 1);
/* 274 */     Graphics2D g = normalized.createGraphics();
/* 275 */     g.drawImage(img, 0, 0, null);
/* 276 */     g.dispose();
/* 277 */     int mask1 = 16777215;
/* 278 */     int mask2 = 16777215;
/* 279 */     int shmt1 = 0;
/* 280 */     int shmt2 = 0;
/* 281 */     switch (color[0]) {
/*     */     case 0:
/* 283 */       mask1 = 16711680;
/* 284 */       shmt1 = 16;
/* 285 */       break;
/*     */     case 1:
/* 287 */       mask1 = 65280;
/* 288 */       shmt1 = 8;
/* 289 */       break;
/*     */     case 2:
/* 291 */       mask1 = 255;
/* 292 */       shmt1 = 0;
/*     */     }
/*     */ 
/* 295 */     switch (color[1]) {
/*     */     case 0:
/* 297 */       mask2 = 16711680;
/* 298 */       shmt2 = 16;
/* 299 */       break;
/*     */     case 1:
/* 301 */       mask2 = 65280;
/* 302 */       shmt2 = 8;
/* 303 */       break;
/*     */     case 2:
/* 305 */       mask2 = 255;
/* 306 */       shmt2 = 0;
/*     */     }
/*     */ 
/* 309 */     for (int i = 0; i < img.getWidth(); i++) {
/* 310 */       for (int j = 0; j < img.getHeight(); j++) {
/* 311 */         int value = img.getRGB(i, j);
/* 312 */         float c1 = (value & mask1) >> shmt1;
/* 313 */         float c2 = (value & mask2) >> shmt2;
/* 314 */         value = Math.round(255.0F * (c1 - c2) / (c1 + c2));
/* 315 */         if (value >= 0)
/* 316 */           normalized.setRGB(i, j, value << shmt1);
/*     */         else {
/* 318 */           normalized.setRGB(i, j, Math.abs(value) << shmt2);
/*     */         }
/*     */       }
/*     */     }
/* 322 */     return normalized;
/*     */   }
/*     */   public static BufferedImage grayScale(byte color, BufferedImage img) {
/* 325 */     BufferedImage grayed = new BufferedImage(img.getWidth(), img.getHeight(), 1);
/* 326 */     Graphics2D g = grayed.createGraphics();
/* 327 */     g.drawImage(img, 0, 0, null);
/* 328 */     g.dispose();
/* 329 */     if (color != -1) {
/* 330 */       int mask = 16777215;
/* 331 */       int shmt = 0;
/* 332 */       switch (color) {
/*     */       case 0:
/* 334 */         mask = 16711680;
/* 335 */         shmt = 16;
/* 336 */         break;
/*     */       case 1:
/* 338 */         mask = 65280;
/* 339 */         shmt = 8;
/* 340 */         break;
/*     */       case 2:
/* 342 */         mask = 255;
/* 343 */         shmt = 0;
/*     */       }
/*     */ 
/* 346 */       for (int i = 0; i < img.getWidth(); i++)
/* 347 */         for (int j = 0; j < img.getHeight(); j++) {
/* 348 */           int value = (img.getRGB(i, j) & mask) >> shmt;
/* 349 */           grayed.setRGB(i, j, ColorTools.toRGB(new int[] { value, value, value }));
/*     */         }
/*     */     }
/*     */     else {
/* 353 */       for (int i = 0; i < img.getWidth(); i++) {
/* 354 */         for (int j = 0; j < img.getHeight(); j++) {
/* 355 */           int value = img.getRGB(i, j);
/* 356 */           value = (((value & 0xFF0000) >> 16) + ((value & 0xFF00) >> 8) + (value & 0xFF)) / 3;
/* 357 */           grayed.setRGB(i, j, ColorTools.toRGB(new int[] { value, value, value }));
/*     */         }
/*     */       }
/*     */     }
/* 361 */     return grayed;
/*     */   }
/*     */ 
/*     */   public static Object[] newApplyMask(int[] min, int[] max, BufferedImage imgin) {
/* 365 */     int rmin = min[0];
/* 366 */     int rmax = max[0];
/* 367 */     int gmin = min[1];
/* 368 */     int gmax = max[1];
/* 369 */     int bmin = min[2];
/* 370 */     int bmax = max[2];
/* 371 */     int width = imgin.getWidth();
/* 372 */     int height = imgin.getHeight();
/* 373 */     int wh = width * height;
/* 374 */     int[] mapin = new int[wh];
/* 375 */     int[] mapout = new int[wh];
/* 376 */     imgin.getRGB(0, 0, width, height, mapin, 0, width);
/* 377 */     Integer count = Integer.valueOf(0);
/* 378 */     for (int i = 0; i < wh; i++) {
/* 379 */       int c = mapin[i];
/* 380 */       int r = c >> 16 & 0xFF;
/* 381 */       int g = c >> 8 & 0xFF;
/* 382 */       int b = c & 0xFF;
/* 383 */       if ((r >= rmin) && (r < rmax) && (g >= gmin) && (g < gmax) && (b >= bmin) && (b < bmax)) {
/* 384 */         count = Integer.valueOf(count.intValue() + 1);
/* 385 */         mapout[i] = -16777216;
/*     */       } else {
/* 387 */         mapout[i] = -1;
/*     */       }
/*     */     }
/* 390 */     BufferedImage img = new BufferedImage(width, height, 1);
/* 391 */     img.setRGB(0, 0, width, height, mapout, 0, width);
/* 392 */     return new Object[] { img, count };
/*     */   }
/*     */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.tools.ColorEnhances
 * JD-Core Version:    0.6.2
 */