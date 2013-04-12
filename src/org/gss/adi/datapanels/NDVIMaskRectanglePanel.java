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
/*     */ import javax.swing.JSpinner;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.SpinnerListModel;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
/*     */ import org.gss.adi.tools.ColorTools;
/*     */ 
/*     */ public class NDVIMaskRectanglePanel extends DataPanel
/*     */ {
/*     */   private static final long serialVersionUID = -6420696208280652556L;
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
/*  45 */   Float min = Float.valueOf(-1.0F);
/*  46 */   Float max = Float.valueOf(1.0F);
/*     */ 
/*  48 */   boolean three = true;
/*     */   private JLabel label;
/*     */   Integer[] X;
/*     */   Integer[] Y;
/*     */   private JSpinner NDVIMax;
/*     */   private JSpinner NDVIMin;
/*     */ 
/*     */   public NDVIMaskRectanglePanel(BufferedImage image1, BufferedImage image2, BufferedImage image3, int rgb)
/*     */   {
/*  61 */     super(image1, image2, image3, rgb);
/*  62 */     setLayout(null);
/*  63 */     setup();
/*  64 */     this.img1 = image1;
/*  65 */     this.img2 = image2;
/*  66 */     this.img3 = image3;
/*  67 */     this.three = (image3 != null);
/*  68 */     drawAxis();
/*  69 */     repaint();
/*  70 */     this.NDVIMin.repaint();
/*  71 */     this.NDVIMax.repaint();
/*     */ 
/*  73 */     add(this.NDVIMin);
/*  74 */     add(this.NDVIMax);
/*  75 */     this.NDVIMin.setValue(this.df.format(-1.0D));
/*  76 */     this.NDVIMax.setValue(this.df.format(1.0D));
/*     */   }
/*     */   public void clear() {
/*  79 */     drawAxis();
/*     */   }
/*     */   private void drawAxis() {
/*  82 */     this.img = new BufferedImage(255, 255, 1);
/*  83 */     Graphics2D g = this.img.createGraphics();
/*  84 */     g.setColor(Color.WHITE);
/*  85 */     g.fillRect(0, 0, 255, 255);
/*  86 */     g.setColor(Color.GRAY);
/*  87 */     g.setStroke(new BasicStroke(1.0F));
/*  88 */     g.drawLine(0, 0, 255, 0);
/*  89 */     g.drawLine(0, 64, 255, 64);
/*  90 */     g.drawLine(0, 126, 255, 126);
/*  91 */     g.drawLine(0, 187, 255, 187);
/*  92 */     g.drawLine(0, 254, 255, 254);
/*  93 */     g.drawLine(0, 0, 0, 255);
/*  94 */     g.drawLine(254, 0, 254, 255);
/*  95 */     if (this.three) {
/*  96 */       g.drawLine(127, 0, 127, 255);
/*     */     }
/*  98 */     g.dispose();
/*  99 */     this.label.setIcon(new ImageIcon(this.img));
/* 100 */     repaint();
/*     */   }
/*     */   public void draw1() {
/* 103 */     Graphics2D g = this.img.createGraphics();
/* 104 */     g.setColor(this.black1);
/* 105 */     if (this.three) {
/* 106 */       g.drawLine(0, 255 - Math.round(255.0F * this.pixInRange1 / this.totalPix), 127, 255 - Math.round(255.0F * this.pixInRange2 / this.totalPix));
/* 107 */       g.drawLine(128, 255 - Math.round(255.0F * this.pixInRange2 / this.totalPix), 255, 255 - Math.round(255.0F * this.pixInRange3 / this.totalPix));
/*     */     } else {
/* 109 */       g.drawLine(0, 255 - Math.round(255.0F * this.pixInRange1 / this.totalPix), 255, 255 - Math.round(255.0F * this.pixInRange2 / this.totalPix));
/*     */     }
/* 111 */     g.dispose();
/* 112 */     repaint();
/* 113 */     this.label.setIcon(new ImageIcon(this.img));
/* 114 */     this.label.repaint();
/*     */   }
/*     */ 
/*     */   public void update1(Integer[] x, Integer[] y)
/*     */   {
/* 119 */     this.X = x;
/* 120 */     this.Y = y;
/* 121 */     this.totalPix = 0;
/* 122 */     this.pixInRange1 = 0;
/* 123 */     this.pixInRange2 = 0;
/* 124 */     this.pixInRange3 = 0;
/*     */     int xmin;
/*     */     int xmax;
/* 129 */     if (x[1].intValue() >= x[0].intValue()) {
/* 130 */       xmax = x[1].intValue();
/* 131 */       xmin = x[0].intValue();
/*     */     } else {
/* 133 */       xmax = x[0].intValue();
/* 134 */       xmin = x[1].intValue();
/*     */     }
/*     */     int ymin;
/*     */     int ymax;
/* 136 */     if (y[1].intValue() >= y[0].intValue()) {
/* 137 */       ymax = y[1].intValue();
/* 138 */       ymin = y[0].intValue();
/*     */     } else {
/* 140 */       ymax = y[0].intValue();
/* 141 */       ymin = y[1].intValue();
/*     */     }
/* 143 */     for (int i = xmin; i < xmax; i++) {
/* 144 */       for (int j = ymin; j < ymax; j++) {
/* 145 */         this.totalPix += 1;
/* 146 */         Float min = new Float((String)this.NDVIMin.getValue());
/* 147 */         Float max = new Float((String)this.NDVIMax.getValue());
/* 148 */         float ndvi = ColorTools.colorToNDVI(this.img1.getRGB(i, j));
/* 149 */         if ((ndvi >= min.floatValue()) && (ndvi <= max.floatValue())) {
/* 150 */           this.pixInRange1 += 1;
/*     */         }
/* 152 */         ndvi = ColorTools.colorToNDVI(this.img2.getRGB(i, j));
/*     */ 
/* 154 */         if ((ndvi >= min.floatValue()) && (ndvi <= max.floatValue())) {
/* 155 */           this.pixInRange2 += 1;
/*     */         }
/* 157 */         if (this.three) {
/* 158 */           ndvi = ColorTools.colorToNDVI(this.img3.getRGB(i, j));
/* 159 */           if ((ndvi >= min.floatValue()) && (ndvi <= max.floatValue())) {
/* 160 */             this.pixInRange3 += 1;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 165 */     clear();
/* 166 */     draw1();
/*     */   }
/*     */ 
/*     */   public void update2(Integer[] x, Integer[] y) {
/*     */   }
/*     */ 
/*     */   public void update3(Integer[] x, Integer[] y) {
/*     */   }
/*     */ 
/*     */   public void threePics() {
/* 176 */     this.pic3.setText("Pic 3");
/* 177 */     this.pic2.setVisible(true);
/*     */   }
/*     */   public void setMinMax(Float Min, Float Max) {
/* 180 */     this.min = Min;
/* 181 */     this.max = Max;
/* 182 */     this.NDVIMin.setValue(this.df.format(this.min));
/* 183 */     this.NDVIMax.setValue(this.df.format(this.max));
/* 184 */     update1(this.X, this.Y);
/*     */   }
/*     */   private void setup() {
/* 187 */     this.label = new JLabel();
/* 188 */     this.label.setBounds(73, 19, 255, 255);
/* 189 */     add(this.label);
/*     */ 
/* 191 */     this.textField = new JTextField();
/* 192 */     this.textField.setText("Relative Frequency of Colors Within Selected Area");
/* 193 */     this.textField.setOpaque(false);
/* 194 */     this.textField.setHorizontalAlignment(0);
/* 195 */     this.textField.setFont(new Font("Tahoma", 0, 10));
/* 196 */     this.textField.setEditable(false);
/* 197 */     this.textField.setColumns(10);
/* 198 */     this.textField.setBorder(null);
/* 199 */     this.textField.setBounds(73, 0, 265, 20);
/* 200 */     add(this.textField);
/*     */ 
/* 202 */     this.textField_1 = new JTextField();
/* 203 */     this.textField_1.setText("Frequency");
/* 204 */     this.textField_1.setHorizontalAlignment(0);
/* 205 */     this.textField_1.setForeground(Color.MAGENTA);
/* 206 */     this.textField_1.setFont(new Font("Tahoma", 0, 9));
/* 207 */     this.textField_1.setEditable(false);
/* 208 */     this.textField_1.setColumns(10);
/* 209 */     this.textField_1.setBorder(null);
/* 210 */     this.textField_1.setBounds(10, -1, 53, 16);
/* 211 */     add(this.textField_1);
/*     */ 
/* 213 */     this.textField_2 = new JTextField();
/* 214 */     this.textField_2.setText("100%");
/* 215 */     this.textField_2.setOpaque(false);
/* 216 */     this.textField_2.setHorizontalAlignment(11);
/* 217 */     this.textField_2.setFont(new Font("Tahoma", 0, 10));
/* 218 */     this.textField_2.setEditable(false);
/* 219 */     this.textField_2.setColumns(10);
/* 220 */     this.textField_2.setBorder(null);
/* 221 */     this.textField_2.setBounds(23, 15, 40, 10);
/* 222 */     add(this.textField_2);
/*     */ 
/* 224 */     this.textField_3 = new JTextField();
/* 225 */     this.textField_3.setText("75%");
/* 226 */     this.textField_3.setOpaque(false);
/* 227 */     this.textField_3.setHorizontalAlignment(11);
/* 228 */     this.textField_3.setFont(new Font("Tahoma", 0, 10));
/* 229 */     this.textField_3.setEditable(false);
/* 230 */     this.textField_3.setColumns(10);
/* 231 */     this.textField_3.setBorder(null);
/* 232 */     this.textField_3.setBounds(23, 77, 40, 10);
/* 233 */     add(this.textField_3);
/*     */ 
/* 235 */     this.textField_4 = new JTextField();
/* 236 */     this.textField_4.setText("50%");
/* 237 */     this.textField_4.setOpaque(false);
/* 238 */     this.textField_4.setHorizontalAlignment(11);
/* 239 */     this.textField_4.setFont(new Font("Tahoma", 0, 10));
/* 240 */     this.textField_4.setEditable(false);
/* 241 */     this.textField_4.setColumns(10);
/* 242 */     this.textField_4.setBorder(null);
/* 243 */     this.textField_4.setBounds(23, 139, 40, 10);
/* 244 */     add(this.textField_4);
/*     */ 
/* 246 */     this.textField_5 = new JTextField();
/* 247 */     this.textField_5.setText("25%");
/* 248 */     this.textField_5.setOpaque(false);
/* 249 */     this.textField_5.setHorizontalAlignment(11);
/* 250 */     this.textField_5.setFont(new Font("Tahoma", 0, 10));
/* 251 */     this.textField_5.setEditable(false);
/* 252 */     this.textField_5.setColumns(10);
/* 253 */     this.textField_5.setBorder(null);
/* 254 */     this.textField_5.setBounds(23, 201, 40, 10);
/* 255 */     add(this.textField_5);
/*     */ 
/* 257 */     this.textField_6 = new JTextField();
/* 258 */     this.textField_6.setText("0%");
/* 259 */     this.textField_6.setOpaque(false);
/* 260 */     this.textField_6.setHorizontalAlignment(11);
/* 261 */     this.textField_6.setFont(new Font("Tahoma", 0, 10));
/* 262 */     this.textField_6.setEditable(false);
/* 263 */     this.textField_6.setColumns(10);
/* 264 */     this.textField_6.setBorder(null);
/* 265 */     this.textField_6.setBounds(23, 263, 40, 10);
/* 266 */     add(this.textField_6);
/*     */ 
/* 268 */     this.pic1 = new JTextField();
/* 269 */     this.pic1.setText("Pic 1");
/* 270 */     this.pic1.setOpaque(false);
/* 271 */     this.pic1.setHorizontalAlignment(0);
/* 272 */     this.pic1.setFont(new Font("Tahoma", 0, 10));
/* 273 */     this.pic1.setEditable(false);
/* 274 */     this.pic1.setColumns(10);
/* 275 */     this.pic1.setBorder(null);
/* 276 */     this.pic1.setBounds(58, 275, 30, 15);
/* 277 */     add(this.pic1);
/*     */ 
/* 279 */     this.pic2 = new JTextField();
/* 280 */     this.pic2.setVisible(false);
/* 281 */     this.pic2.setText("Pic 2");
/* 282 */     this.pic2.setOpaque(false);
/* 283 */     this.pic2.setHorizontalAlignment(0);
/* 284 */     this.pic2.setFont(new Font("Tahoma", 0, 10));
/* 285 */     this.pic2.setEditable(false);
/* 286 */     this.pic2.setColumns(10);
/* 287 */     this.pic2.setBorder(null);
/* 288 */     this.pic2.setBounds(186, 275, 30, 15);
/* 289 */     add(this.pic2);
/*     */ 
/* 291 */     this.pic3 = new JTextField();
/* 292 */     this.pic3.setText("Pic 2");
/* 293 */     this.pic3.setOpaque(false);
/* 294 */     this.pic3.setHorizontalAlignment(0);
/* 295 */     this.pic3.setFont(new Font("Tahoma", 0, 10));
/* 296 */     this.pic3.setEditable(false);
/* 297 */     this.pic3.setColumns(10);
/* 298 */     this.pic3.setBorder(null);
/* 299 */     this.pic3.setBounds(313, 275, 30, 15);
/* 300 */     add(this.pic3);
/*     */ 
/* 302 */     this.NDVIMax = new JSpinner();
/* 303 */     this.NDVIMax.addChangeListener(new ChangeListener() {
/*     */       public void stateChanged(ChangeEvent arg0) {
/*     */         try { NDVIMaskRectanglePanel.this.update1(NDVIMaskRectanglePanel.this.X, NDVIMaskRectanglePanel.this.Y); }
/*     */         catch (Exception localException)
/*     */         {
/*     */         }
/*     */       }
/*     */     });
/* 308 */     String[] s = new String['Ê'];
/* 309 */     for (Float i = Float.valueOf(-1.0F); i.floatValue() <= 1.0F; i = Float.valueOf(i.floatValue() + 0.01F)) {
/* 310 */       s[Math.round(100.0F + i.floatValue() * 100.0F)] = this.df.format(i);
/*     */     }
/* 312 */     s[100] = "0";
/* 313 */     this.NDVIMax.setModel(new SpinnerListModel(s));
/* 314 */     this.NDVIMax.setBounds(345, 75, 78, 20);
/* 315 */     add(this.NDVIMax);
/*     */ 
/* 317 */     this.NDVIMin = new JSpinner();
/* 318 */     this.NDVIMin.addChangeListener(new ChangeListener() {
/*     */       public void stateChanged(ChangeEvent arg0) {
/*     */         try { NDVIMaskRectanglePanel.this.update1(NDVIMaskRectanglePanel.this.X, NDVIMaskRectanglePanel.this.Y); }
/*     */         catch (Exception localException)
/*     */         {
/*     */         }
/*     */       }
/*     */     });
/* 323 */     this.NDVIMin.setModel(new SpinnerListModel(s));
/* 324 */     this.NDVIMin.setBounds(345, 129, 78, 20);
/* 325 */     add(this.NDVIMin);
/*     */ 
/* 327 */     JTextField txtNdviMax = new JTextField();
/* 328 */     txtNdviMax.setBorder(null);
/* 329 */     txtNdviMax.setEditable(false);
/* 330 */     txtNdviMax.setFont(new Font("SansSerif", 0, 10));
/* 331 */     txtNdviMax.setText("NDVI Max");
/* 332 */     txtNdviMax.setBounds(338, 58, 86, 15);
/* 333 */     add(txtNdviMax);
/* 334 */     txtNdviMax.setColumns(10);
/*     */ 
/* 336 */     JTextField txtNdviMin = new JTextField();
/* 337 */     txtNdviMin.setText("NDVI Min");
/* 338 */     txtNdviMin.setFont(new Font("SansSerif", 0, 10));
/* 339 */     txtNdviMin.setEditable(false);
/* 340 */     txtNdviMin.setColumns(10);
/* 341 */     txtNdviMin.setBorder(null);
/* 342 */     txtNdviMin.setBounds(338, 113, 86, 15);
/* 343 */     add(txtNdviMin);
/*     */   }
/*     */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.datapanels.NDVIMaskRectanglePanel
 * JD-Core Version:    0.6.2
 */