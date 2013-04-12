/*     */ package org.gss.adi.tools;
/*     */ 
/*     */ import java.text.DecimalFormat;
/*     */ 
/*     */ public class Measurement
/*     */ {
/*     */   public Double _distPerPix;
/*     */   public String _unitOfMsr;
/*     */ 
/*     */   public Measurement(int numPixels, Double distance, String unitOfMsr)
/*     */   {
/*  16 */     this._distPerPix = Double.valueOf(distance.doubleValue() / numPixels);
/*  17 */     this._unitOfMsr = unitOfMsr;
/*     */   }
/*     */   public Measurement(Double distPerPix, String unitOfMsr) {
/*  20 */     this._distPerPix = distPerPix;
/*  21 */     this._unitOfMsr = unitOfMsr;
/*     */   }
/*     */   public String measure(Double numPixels, boolean length) {
/*  24 */     if (length) {
/*  25 */       String unit = this._unitOfMsr;
/*  26 */       double d = this._distPerPix.doubleValue() * numPixels.doubleValue();
/*  27 */       boolean small = false;
/*  28 */       boolean large = false;
/*  29 */       if (d > 100.0D)
/*  30 */         large = true;
/*  31 */       while (large) {
/*  32 */         if (unit.endsWith("m")) {
/*  33 */           if (unit.equals("mm")) {
/*  34 */             d /= 10.0D;
/*  35 */             unit = "cm";
/*  36 */             if (d <= 100.0D)
/*  37 */               large = false;
/*  38 */           } else if (unit.equals("cm")) {
/*  39 */             d /= 100.0D;
/*  40 */             unit = "m";
/*  41 */             if (d <= 100.0D)
/*  42 */               large = false;
/*  43 */           } else if (unit.equals("m")) {
/*  44 */             d /= 1000.0D;
/*  45 */             unit = "km";
/*  46 */             if (d <= 100.0D)
/*  47 */               large = false;
/*     */           } else {
/*  49 */             large = false;
/*     */           }
/*  51 */         } else if (unit.equals("in")) {
/*  52 */           d /= 12.0D;
/*  53 */           unit = "ft";
/*  54 */           if (d <= 100.0D)
/*  55 */             large = false;
/*  56 */         } else if (unit.equals("ft")) {
/*  57 */           d /= 3.0D;
/*  58 */           unit = "yd";
/*  59 */           if (d <= 100.0D)
/*  60 */             large = false;
/*  61 */         } else if (unit.equals("yd")) {
/*  62 */           d /= 1760.0D;
/*  63 */           unit = "mi";
/*  64 */           if (d <= 100.0D)
/*  65 */             large = false;
/*     */         } else {
/*  67 */           large = false;
/*     */         }
/*     */       }
/*  70 */       if (d < 1.0D)
/*  71 */         small = true;
/*  72 */       while (small) {
/*  73 */         if (unit.endsWith("m")) {
/*  74 */           if (unit.equals("km")) {
/*  75 */             d *= 1000.0D;
/*  76 */             unit = "m";
/*  77 */             if (d >= 1.0D)
/*  78 */               small = false;
/*  79 */           } else if (unit.equals("m")) {
/*  80 */             d *= 100.0D;
/*  81 */             unit = "cm";
/*  82 */             if (d >= 1.0D)
/*  83 */               small = false;
/*  84 */           } else if (unit.equals("cm")) {
/*  85 */             d *= 10.0D;
/*  86 */             unit = "mm";
/*  87 */             if (d >= 1.0D)
/*  88 */               small = false;
/*     */           } else {
/*  90 */             small = false;
/*     */           }
/*  92 */         } else if (unit.equals("mi")) {
/*  93 */           d *= 1760.0D;
/*  94 */           unit = "yd";
/*  95 */           if (d >= 1.0D)
/*  96 */             small = false;
/*  97 */         } else if (unit.equals("yd")) {
/*  98 */           d *= 3.0D;
/*  99 */           unit = "ft";
/* 100 */           if (d >= 1.0D)
/* 101 */             small = false;
/* 102 */         } else if (unit.equals("ft")) {
/* 103 */           d *= 12.0D;
/* 104 */           unit = "in";
/* 105 */           if (d >= 1.0D)
/* 106 */             small = false;
/*     */         } else {
/* 108 */           small = false;
/*     */         }
/*     */       }
/* 111 */       DecimalFormat df = new DecimalFormat("##.##");
/* 112 */       return df.format(d) + " " + unit;
/*     */     }
/* 114 */     DecimalFormat df = new DecimalFormat("##.##");
/* 115 */     double d = this._distPerPix.doubleValue() * this._distPerPix.doubleValue() * numPixels.doubleValue();
/* 116 */     return df.format(d) + " " + this._unitOfMsr + "*" + this._unitOfMsr;
/*     */   }
/*     */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.tools.Measurement
 * JD-Core Version:    0.6.2
 */