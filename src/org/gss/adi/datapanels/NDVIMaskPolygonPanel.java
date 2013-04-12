/*     */ package org.gss.adi.datapanels;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Polygon;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.text.DecimalFormat;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JSpinner;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.SpinnerListModel;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
/*     */ import org.gss.adi.tools.ColorTools;
/*     */ 
/*     */ public class NDVIMaskPolygonPanel extends DataPanel
/*     */ {
/*     */   private static final long serialVersionUID = -6047646122444948574L;
/*     */   private JTextField textField;
/*     */   private JTextField textField_1;
/*     */   private JTextField textField_2;
/*     */   private JTextField textField_3;
/*     */   private JTextField textField_4;
/*     */   private JTextField textField_5;
/*     */   private JTextField textField_6;
/*     */   private JTextField pic1;
/*     */   private JTextField pic2;
/*     */   private JTextField pic3;
/*  35 */   int pixInRange1 = 0;
/*  36 */   int pixInRange2 = 0;
/*  37 */   int pixInRange3 = 0;
/*  38 */   int totalPix = 0;
/*     */   BufferedImage img1;
/*     */   BufferedImage img2;
/*     */   BufferedImage img3;
/*     */   BufferedImage img;
/*  45 */   boolean three = true;
/*     */   JSpinner NDVIMax;
/*     */   JSpinner NDVIMin;
/*     */   private JLabel label;
/*     */   Integer[] X;
/*     */   Integer[] Y;
/*     */ 
/*     */   public NDVIMaskPolygonPanel(BufferedImage image1, BufferedImage image2, BufferedImage image3, int rgb)
/*     */   {
/*  59 */     super(image1, image2, image3, rgb);
/*  60 */     setLayout(null);
/*  61 */     setup();
/*  62 */     this.img1 = image1;
/*  63 */     this.img2 = image2;
/*  64 */     this.img3 = image3;
/*  65 */     this.three = (image3 != null);
/*  66 */     drawAxis();
/*  67 */     repaint();
/*  68 */     this.NDVIMin.repaint();
/*  69 */     this.NDVIMax.repaint();
/*     */ 
/*  71 */     add(this.NDVIMin);
/*  72 */     add(this.NDVIMax);
/*  73 */     this.NDVIMin.setValue(this.df.format(-1.0D));
/*  74 */     this.NDVIMax.setValue(this.df.format(1.0D));
/*     */   }
/*     */   public void clear() {
/*  77 */     drawAxis();
/*     */   }
/*     */   private void drawAxis() {
/*  80 */     this.img = new BufferedImage(255, 255, 1);
/*  81 */     Graphics2D g = this.img.createGraphics();
/*  82 */     g.setColor(Color.WHITE);
/*  83 */     g.fillRect(0, 0, 255, 255);
/*  84 */     g.setColor(Color.GRAY);
/*  85 */     g.setStroke(new BasicStroke(1.0F));
/*  86 */     g.drawLine(0, 0, 255, 0);
/*  87 */     g.drawLine(0, 64, 255, 64);
/*  88 */     g.drawLine(0, 126, 255, 126);
/*  89 */     g.drawLine(0, 187, 255, 187);
/*  90 */     g.drawLine(0, 254, 255, 254);
/*  91 */     g.drawLine(0, 0, 0, 255);
/*  92 */     g.drawLine(254, 0, 254, 255);
/*  93 */     if (this.three) {
/*  94 */       g.drawLine(127, 0, 127, 255);
/*     */     }
/*  96 */     g.dispose();
/*  97 */     this.label.setIcon(new ImageIcon(this.img));
/*  98 */     repaint();
/*     */   }
/*     */   public void draw1() {
/* 101 */     Graphics2D g = this.img.createGraphics();
/* 102 */     g.setColor(this.black1);
/* 103 */     if (this.three) {
/* 104 */       g.drawLine(0, 255 - Math.round(255.0F * this.pixInRange1 / this.totalPix), 127, 255 - Math.round(255.0F * this.pixInRange2 / this.totalPix));
/* 105 */       g.drawLine(128, 255 - Math.round(255.0F * this.pixInRange2 / this.totalPix), 255, 255 - Math.round(255.0F * this.pixInRange3 / this.totalPix));
/*     */     } else {
/* 107 */       g.drawLine(0, 255 - Math.round(255.0F * this.pixInRange1 / this.totalPix), 255, 255 - Math.round(255.0F * this.pixInRange2 / this.totalPix));
/*     */     }
/* 109 */     g.dispose();
/* 110 */     repaint();
/* 111 */     this.label.setIcon(new ImageIcon(this.img));
/* 112 */     this.label.repaint();
/*     */   }
/*     */ 
/*     */   public void update1(Integer[] x, Integer[] y) {
/* 116 */     this.X = x;
/* 117 */     this.Y = y;
/* 118 */     this.totalPix = 0;
/* 119 */     this.pixInRange1 = 0;
/* 120 */     this.pixInRange2 = 0;
/* 121 */     this.pixInRange3 = 0;
/* 122 */     int[] px = new int[x.length];
/* 123 */     int[] py = new int[y.length];
/* 124 */     for (int i = 0; i < x.length; i++) {
/* 125 */       px[i] = x[i].intValue();
/* 126 */       py[i] = y[i].intValue();
/*     */     }
/* 128 */     Polygon poly = new Polygon(px, py, px.length);
/* 129 */     for (int i = 0; i < this.img1.getWidth(); i++) {
/* 130 */       for (int j = 0; j < this.img.getHeight(); j++) {
/* 131 */         if (poly.contains(i, j)) {
/* 132 */           this.totalPix += 1;
/* 133 */           float ndvi = ColorTools.colorToNDVI(this.img1.getRGB(i, j));
/* 134 */           Float min = new Float((String)this.NDVIMin.getValue());
/* 135 */           Float max = new Float((String)this.NDVIMax.getValue());
/* 136 */           if ((ndvi >= min.floatValue()) && (ndvi <= max.floatValue())) {
/* 137 */             this.pixInRange1 += 1;
/*     */           }
/* 139 */           ndvi = ColorTools.colorToNDVI(this.img2.getRGB(i, j));
/* 140 */           if ((ndvi >= min.floatValue()) && (ndvi <= max.floatValue())) {
/* 141 */             this.pixInRange2 += 1;
/*     */           }
/* 143 */           if (this.three) {
/* 144 */             ndvi = ColorTools.colorToNDVI(this.img3.getRGB(i, j));
/* 145 */             if ((ndvi >= min.floatValue()) && (ndvi <= max.floatValue())) {
/* 146 */               this.pixInRange3 += 1;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 152 */     clear();
/* 153 */     draw1();
/* 154 */     repaint();
/*     */   }
/*     */   public void update2(Integer[] x, Integer[] y) {
/*     */   }
/*     */ 
/*     */   public void update3(Integer[] x, Integer[] y) {
/*     */   }
/*     */ 
/*     */   public void threePics() {
/* 163 */     this.pic3.setText("Pic 3");
/* 164 */     this.pic2.setVisible(true);
/*     */   }
/*     */   public void setMinMax(Float min, Float max) {
/* 167 */     this.NDVIMin.setValue(this.df.format(min));
/* 168 */     this.NDVIMax.setValue(this.df.format(max));
/* 169 */     update1(this.X, this.Y);
/*     */   }
/*     */   private void setup() {
/* 172 */     this.label = new JLabel();
/* 173 */     this.label.setBounds(73, 19, 255, 255);
/* 174 */     add(this.label);
/*     */ 
/* 176 */     this.textField = new JTextField();
/* 177 */     this.textField.setText("Relative Frequency of Colors Within Selected Area");
/* 178 */     this.textField.setOpaque(false);
/* 179 */     this.textField.setHorizontalAlignment(0);
/* 180 */     this.textField.setFont(new Font("Tahoma", 0, 10));
/* 181 */     this.textField.setEditable(false);
/* 182 */     this.textField.setColumns(10);
/* 183 */     this.textField.setBorder(null);
/* 184 */     this.textField.setBounds(73, 0, 265, 20);
/* 185 */     add(this.textField);
/*     */ 
/* 187 */     this.textField_1 = new JTextField();
/* 188 */     this.textField_1.setText("Frequency");
/* 189 */     this.textField_1.setHorizontalAlignment(0);
/* 190 */     this.textField_1.setForeground(Color.MAGENTA);
/* 191 */     this.textField_1.setFont(new Font("Tahoma", 0, 9));
/* 192 */     this.textField_1.setEditable(false);
/* 193 */     this.textField_1.setColumns(10);
/* 194 */     this.textField_1.setBorder(null);
/* 195 */     this.textField_1.setBounds(10, -1, 53, 16);
/* 196 */     add(this.textField_1);
/*     */ 
/* 198 */     this.textField_2 = new JTextField();
/* 199 */     this.textField_2.setText("100%");
/* 200 */     this.textField_2.setOpaque(false);
/* 201 */     this.textField_2.setHorizontalAlignment(11);
/* 202 */     this.textField_2.setFont(new Font("Tahoma", 0, 10));
/* 203 */     this.textField_2.setEditable(false);
/* 204 */     this.textField_2.setColumns(10);
/* 205 */     this.textField_2.setBorder(null);
/* 206 */     this.textField_2.setBounds(23, 15, 40, 10);
/* 207 */     add(this.textField_2);
/*     */ 
/* 209 */     this.textField_3 = new JTextField();
/* 210 */     this.textField_3.setText("75%");
/* 211 */     this.textField_3.setOpaque(false);
/* 212 */     this.textField_3.setHorizontalAlignment(11);
/* 213 */     this.textField_3.setFont(new Font("Tahoma", 0, 10));
/* 214 */     this.textField_3.setEditable(false);
/* 215 */     this.textField_3.setColumns(10);
/* 216 */     this.textField_3.setBorder(null);
/* 217 */     this.textField_3.setBounds(23, 77, 40, 10);
/* 218 */     add(this.textField_3);
/*     */ 
/* 220 */     this.textField_4 = new JTextField();
/* 221 */     this.textField_4.setText("50%");
/* 222 */     this.textField_4.setOpaque(false);
/* 223 */     this.textField_4.setHorizontalAlignment(11);
/* 224 */     this.textField_4.setFont(new Font("Tahoma", 0, 10));
/* 225 */     this.textField_4.setEditable(false);
/* 226 */     this.textField_4.setColumns(10);
/* 227 */     this.textField_4.setBorder(null);
/* 228 */     this.textField_4.setBounds(23, 139, 40, 10);
/* 229 */     add(this.textField_4);
/*     */ 
/* 231 */     this.textField_5 = new JTextField();
/* 232 */     this.textField_5.setText("25%");
/* 233 */     this.textField_5.setOpaque(false);
/* 234 */     this.textField_5.setHorizontalAlignment(11);
/* 235 */     this.textField_5.setFont(new Font("Tahoma", 0, 10));
/* 236 */     this.textField_5.setEditable(false);
/* 237 */     this.textField_5.setColumns(10);
/* 238 */     this.textField_5.setBorder(null);
/* 239 */     this.textField_5.setBounds(23, 201, 40, 10);
/* 240 */     add(this.textField_5);
/*     */ 
/* 242 */     this.textField_6 = new JTextField();
/* 243 */     this.textField_6.setText("0%");
/* 244 */     this.textField_6.setOpaque(false);
/* 245 */     this.textField_6.setHorizontalAlignment(11);
/* 246 */     this.textField_6.setFont(new Font("Tahoma", 0, 10));
/* 247 */     this.textField_6.setEditable(false);
/* 248 */     this.textField_6.setColumns(10);
/* 249 */     this.textField_6.setBorder(null);
/* 250 */     this.textField_6.setBounds(23, 263, 40, 10);
/* 251 */     add(this.textField_6);
/*     */ 
/* 253 */     this.pic1 = new JTextField();
/* 254 */     this.pic1.setText("Pic 1");
/* 255 */     this.pic1.setOpaque(false);
/* 256 */     this.pic1.setHorizontalAlignment(0);
/* 257 */     this.pic1.setFont(new Font("Tahoma", 0, 10));
/* 258 */     this.pic1.setEditable(false);
/* 259 */     this.pic1.setColumns(10);
/* 260 */     this.pic1.setBorder(null);
/* 261 */     this.pic1.setBounds(58, 275, 30, 15);
/* 262 */     add(this.pic1);
/*     */ 
/* 264 */     this.pic2 = new JTextField();
/* 265 */     this.pic2.setVisible(false);
/* 266 */     this.pic2.setText("Pic 2");
/* 267 */     this.pic2.setOpaque(false);
/* 268 */     this.pic2.setHorizontalAlignment(0);
/* 269 */     this.pic2.setFont(new Font("Tahoma", 0, 10));
/* 270 */     this.pic2.setEditable(false);
/* 271 */     this.pic2.setColumns(10);
/* 272 */     this.pic2.setBorder(null);
/* 273 */     this.pic2.setBounds(186, 275, 30, 15);
/* 274 */     add(this.pic2);
/*     */ 
/* 276 */     this.pic3 = new JTextField();
/* 277 */     this.pic3.setText("Pic 2");
/* 278 */     this.pic3.setOpaque(false);
/* 279 */     this.pic3.setHorizontalAlignment(0);
/* 280 */     this.pic3.setFont(new Font("Tahoma", 0, 10));
/* 281 */     this.pic3.setEditable(false);
/* 282 */     this.pic3.setColumns(10);
/* 283 */     this.pic3.setBorder(null);
/* 284 */     this.pic3.setBounds(313, 275, 30, 15);
/* 285 */     add(this.pic3);
/*     */ 
/* 287 */     this.NDVIMax = new JSpinner();
/* 288 */     this.NDVIMax.addChangeListener(new ChangeListener() {
/*     */       public void stateChanged(ChangeEvent arg0) {
/*     */         try { NDVIMaskPolygonPanel.this.update1(NDVIMaskPolygonPanel.this.X, NDVIMaskPolygonPanel.this.Y); }
/*     */         catch (Exception localException)
/*     */         {
/*     */         }
/*     */       }
/*     */     });
/* 293 */     String[] s = new String['Ê'];
/* 294 */     for (Float i = Float.valueOf(-1.0F); i.floatValue() <= 1.0F; i = Float.valueOf(i.floatValue() + 0.01F)) {
/* 295 */       s[Math.round(100.0F + i.floatValue() * 100.0F)] = this.df.format(i);
/*     */     }
/* 297 */     s[100] = "0";
/* 298 */     this.NDVIMax.setModel(new SpinnerListModel(s));
/* 299 */     this.NDVIMax.setBounds(345, 75, 78, 20);
/* 300 */     add(this.NDVIMax);
/*     */ 
/* 302 */     this.NDVIMin = new JSpinner();
/* 303 */     this.NDVIMin.addChangeListener(new ChangeListener() {
/*     */       public void stateChanged(ChangeEvent arg0) {
/*     */         try { NDVIMaskPolygonPanel.this.update1(NDVIMaskPolygonPanel.this.X, NDVIMaskPolygonPanel.this.Y); }
/*     */         catch (Exception localException)
/*     */         {
/*     */         }
/*     */       }
/*     */     });
/* 308 */     this.NDVIMin.setModel(new SpinnerListModel(s));
/* 309 */     this.NDVIMin.setBounds(345, 129, 78, 20);
/* 310 */     add(this.NDVIMin);
/*     */ 
/* 312 */     JTextField txtNdviMax = new JTextField();
/* 313 */     txtNdviMax.setBorder(null);
/* 314 */     txtNdviMax.setEditable(false);
/* 315 */     txtNdviMax.setFont(new Font("SansSerif", 0, 10));
/* 316 */     txtNdviMax.setText("NDVI Max");
/* 317 */     txtNdviMax.setBounds(338, 58, 86, 15);
/* 318 */     add(txtNdviMax);
/* 319 */     txtNdviMax.setColumns(10);
/*     */ 
/* 321 */     JTextField txtNdviMin = new JTextField();
/* 322 */     txtNdviMin.setText("NDVI Min");
/* 323 */     txtNdviMin.setFont(new Font("SansSerif", 0, 10));
/* 324 */     txtNdviMin.setEditable(false);
/* 325 */     txtNdviMin.setColumns(10);
/* 326 */     txtNdviMin.setBorder(null);
/* 327 */     txtNdviMin.setBounds(338, 113, 86, 15);
/* 328 */     add(txtNdviMin);
/*     */   }
/*     */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.datapanels.NDVIMaskPolygonPanel
 * JD-Core Version:    0.6.2
 */