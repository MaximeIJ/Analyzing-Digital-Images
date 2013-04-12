/*     */ package org.gss.adi.datapanels;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.text.DecimalFormat;
/*     */ import javax.swing.JTextField;
/*     */ import org.gss.adi.tools.ColorTools;
/*     */ 
/*     */ public class PointTextPanel extends DataPanel
/*     */ {
/*     */   private static final long serialVersionUID = -6563156837975659150L;
/*     */   private JTextField txtImage;
/*     */   private JTextField txtRed;
/*     */   private JTextField txtGreen;
/*     */   private JTextField txtBlue;
/*     */   private JTextField pic1Red;
/*     */   private JTextField txtPic;
/*     */   private JTextField txtPic_1;
/*     */   private JTextField txtPic_2;
/*     */   private JTextField pic2Red;
/*     */   private JTextField pic3Red;
/*     */   private JTextField pic1Green;
/*     */   private JTextField pic2Green;
/*     */   private JTextField pic3Green;
/*     */   private JTextField pic3Blue;
/*     */   private JTextField pic1Blue;
/*     */   private JTextField pic2Blue;
/*     */   private JTextField txtAverage;
/*     */   private JTextField pic1Avg;
/*     */   private JTextField pic2Avg;
/*     */   private JTextField pic3Avg;
/*     */   private BufferedImage img1;
/*     */   private BufferedImage img2;
/*     */   private BufferedImage img3;
/*     */   private int RGB;
/*     */ 
/*     */   public PointTextPanel(BufferedImage image1, BufferedImage image2, BufferedImage image3, int rgb)
/*     */   {
/*  46 */     super(image1, image2, image3, rgb);
/*  47 */     this.img1 = image1;
/*  48 */     this.img2 = image2;
/*  49 */     this.img3 = image3;
/*  50 */     this.RGB = rgb;
/*  51 */     setLayout(null);
/*  52 */     setup();
/*  53 */     if (rgb != 0) {
/*  54 */       this.pic1Red.setForeground(this.black1);
/*  55 */       this.pic1Green.setForeground(this.red1);
/*  56 */       this.pic1Blue.setForeground(this.green1);
/*  57 */       this.pic2Red.setForeground(this.black2);
/*  58 */       this.pic2Green.setForeground(this.red2);
/*  59 */       this.pic2Blue.setForeground(this.green2);
/*  60 */       this.pic3Red.setForeground(this.black3);
/*  61 */       this.pic3Green.setForeground(this.red3);
/*  62 */       this.pic3Blue.setForeground(this.green3);
/*  63 */       this.txtRed.setText("NIR");
/*  64 */       this.txtRed.setForeground(this.black1);
/*  65 */       this.txtGreen.setText("Red");
/*  66 */       this.txtGreen.setForeground(this.red1);
/*  67 */       this.txtBlue.setText("Green");
/*  68 */       this.txtBlue.setForeground(this.green1);
/*     */     }
/*  70 */     if (rgb == 2) {
/*  71 */       this.pic1Green.setVisible(false);
/*  72 */       this.pic1Blue.setVisible(false);
/*  73 */       this.txtGreen.setVisible(false);
/*  74 */       this.txtBlue.setVisible(false);
/*  75 */       this.pic2Green.setVisible(false);
/*  76 */       this.pic2Blue.setVisible(false);
/*  77 */       this.pic3Green.setVisible(false);
/*  78 */       this.pic3Blue.setVisible(false);
/*  79 */       this.pic1Avg.setVisible(false);
/*  80 */       this.pic2Avg.setVisible(false);
/*  81 */       this.pic3Avg.setVisible(false);
/*  82 */       this.txtRed.setText("NDVI Value");
/*  83 */       this.txtAverage.setVisible(false);
/*     */     }
/*     */   }
/*     */ 
/*  87 */   public void threePics() { this.txtPic_2.setVisible(true);
/*  88 */     this.pic3Red.setVisible(true);
/*  89 */     this.pic3Green.setVisible(true);
/*  90 */     this.pic3Blue.setVisible(true);
/*  91 */     this.pic3Avg.setVisible(true);
/*  92 */     repaint(); }
/*     */ 
/*     */   public void update1(Integer[] x, Integer[] y)
/*     */   {
/*  96 */     int[] rgb = ColorTools.rgb(this.img1.getRGB(x[0].intValue(), y[0].intValue()));
/*  97 */     if (this.RGB == 2) {
/*  98 */       float i = ColorTools.colorToNDVI(rgb);
/*  99 */       this.pic1Red.setText(this.df.format(i));
/*     */     } else {
/* 101 */       this.pic1Red.setText(new Integer(rgb[0]).toString());
/* 102 */       this.pic1Green.setText(new Integer(rgb[1]).toString());
/* 103 */       this.pic1Blue.setText(new Integer(rgb[2]).toString());
/* 104 */       this.pic1Avg.setText(this.df.format((rgb[0] + rgb[1] + rgb[2]) / 3.0D));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void update2(Integer[] x, Integer[] y) {
/* 109 */     int[] rgb = ColorTools.rgb(this.img2.getRGB(x[0].intValue(), y[0].intValue()));
/* 110 */     if (this.RGB == 2) {
/* 111 */       float i = ColorTools.colorToNDVI(rgb);
/* 112 */       this.pic2Red.setText(this.df.format(i));
/*     */     } else {
/* 114 */       this.pic2Red.setText(new Integer(rgb[0]).toString());
/* 115 */       this.pic2Green.setText(new Integer(rgb[1]).toString());
/* 116 */       this.pic2Blue.setText(new Integer(rgb[2]).toString());
/* 117 */       this.pic2Avg.setText(this.df.format((rgb[0] + rgb[1] + rgb[2]) / 3.0D));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void update3(Integer[] x, Integer[] y)
/*     */   {
/* 123 */     int[] rgb = ColorTools.rgb(this.img3.getRGB(x[0].intValue(), y[0].intValue()));
/* 124 */     if (this.RGB == 2) {
/* 125 */       float i = ColorTools.colorToNDVI(rgb);
/* 126 */       this.pic3Red.setText(this.df.format(i));
/*     */     } else {
/* 128 */       this.pic3Red.setText(new Integer(rgb[0]).toString());
/* 129 */       this.pic3Green.setText(new Integer(rgb[1]).toString());
/* 130 */       this.pic3Blue.setText(new Integer(rgb[2]).toString());
/* 131 */       this.pic3Avg.setText(this.df.format((rgb[0] + rgb[1] + rgb[2]) / 3.0D));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void setup() {
/* 136 */     this.txtImage = new JTextField();
/* 137 */     this.txtImage.setText("Image");
/* 138 */     this.txtImage.setBorder(null);
/* 139 */     this.txtImage.setOpaque(false);
/* 140 */     this.txtImage.setFont(new Font("SansSerif", 0, 13));
/* 141 */     this.txtImage.setEditable(false);
/* 142 */     this.txtImage.setBounds(10, 11, 50, 20);
/* 143 */     add(this.txtImage);
/* 144 */     this.txtImage.setColumns(10);
/*     */ 
/* 146 */     this.txtRed = new JTextField();
/* 147 */     this.txtRed.setForeground(Color.RED);
/* 148 */     this.txtRed.setHorizontalAlignment(0);
/* 149 */     this.txtRed.setText("Red");
/* 150 */     this.txtRed.setOpaque(false);
/* 151 */     this.txtRed.setFont(new Font("SansSerif", 0, 13));
/* 152 */     this.txtRed.setEditable(false);
/* 153 */     this.txtRed.setColumns(10);
/* 154 */     this.txtRed.setBorder(null);
/* 155 */     this.txtRed.setBounds(70, 11, 67, 20);
/* 156 */     add(this.txtRed);
/*     */ 
/* 158 */     this.txtGreen = new JTextField();
/* 159 */     this.txtGreen.setForeground(new Color(43520));
/* 160 */     this.txtGreen.setHorizontalAlignment(0);
/* 161 */     this.txtGreen.setText("Green");
/* 162 */     this.txtGreen.setOpaque(false);
/* 163 */     this.txtGreen.setFont(new Font("SansSerif", 0, 13));
/* 164 */     this.txtGreen.setEditable(false);
/* 165 */     this.txtGreen.setColumns(10);
/* 166 */     this.txtGreen.setBorder(null);
/* 167 */     this.txtGreen.setBounds(147, 11, 67, 20);
/* 168 */     add(this.txtGreen);
/*     */ 
/* 170 */     this.txtBlue = new JTextField();
/* 171 */     this.txtBlue.setForeground(new Color(255));
/* 172 */     this.txtBlue.setHorizontalAlignment(0);
/* 173 */     this.txtBlue.setText("Blue");
/* 174 */     this.txtBlue.setOpaque(false);
/* 175 */     this.txtBlue.setFont(new Font("SansSerif", 0, 13));
/* 176 */     this.txtBlue.setEditable(false);
/* 177 */     this.txtBlue.setColumns(10);
/* 178 */     this.txtBlue.setBorder(null);
/* 179 */     this.txtBlue.setBounds(224, 10, 67, 20);
/* 180 */     add(this.txtBlue);
/*     */ 
/* 182 */     this.pic1Red = new JTextField();
/* 183 */     this.pic1Red.setForeground(Color.RED);
/* 184 */     this.pic1Red.setHorizontalAlignment(0);
/* 185 */     this.pic1Red.setOpaque(false);
/* 186 */     this.pic1Red.setFont(new Font("SansSerif", 0, 13));
/* 187 */     this.pic1Red.setEditable(false);
/* 188 */     this.pic1Red.setColumns(10);
/* 189 */     this.pic1Red.setBorder(null);
/* 190 */     this.pic1Red.setBounds(70, 42, 67, 20);
/* 191 */     add(this.pic1Red);
/*     */ 
/* 193 */     this.txtPic = new JTextField();
/* 194 */     this.txtPic.setText("Pic 1");
/* 195 */     this.txtPic.setOpaque(false);
/* 196 */     this.txtPic.setFont(new Font("SansSerif", 0, 13));
/* 197 */     this.txtPic.setEditable(false);
/* 198 */     this.txtPic.setColumns(10);
/* 199 */     this.txtPic.setBorder(null);
/* 200 */     this.txtPic.setBounds(10, 42, 50, 20);
/* 201 */     add(this.txtPic);
/*     */ 
/* 203 */     this.txtPic_1 = new JTextField();
/* 204 */     this.txtPic_1.setForeground(new Color(5263440));
/* 205 */     this.txtPic_1.setText("Pic 2");
/* 206 */     this.txtPic_1.setOpaque(false);
/* 207 */     this.txtPic_1.setFont(new Font("SansSerif", 0, 13));
/* 208 */     this.txtPic_1.setEditable(false);
/* 209 */     this.txtPic_1.setColumns(10);
/* 210 */     this.txtPic_1.setBorder(null);
/* 211 */     this.txtPic_1.setBounds(10, 73, 50, 20);
/* 212 */     add(this.txtPic_1);
/*     */ 
/* 214 */     this.txtPic_2 = new JTextField();
/* 215 */     this.txtPic_2.setVisible(false);
/* 216 */     this.txtPic_2.setForeground(new Color(10526880));
/* 217 */     this.txtPic_2.setText("Pic 3");
/* 218 */     this.txtPic_2.setOpaque(false);
/* 219 */     this.txtPic_2.setFont(new Font("SansSerif", 0, 13));
/* 220 */     this.txtPic_2.setEditable(false);
/* 221 */     this.txtPic_2.setColumns(10);
/* 222 */     this.txtPic_2.setBorder(null);
/* 223 */     this.txtPic_2.setBounds(10, 106, 50, 20);
/* 224 */     add(this.txtPic_2);
/*     */ 
/* 226 */     this.pic2Red = new JTextField();
/* 227 */     this.pic2Red.setForeground(this.red2);
/* 228 */     this.pic2Red.setOpaque(false);
/* 229 */     this.pic2Red.setHorizontalAlignment(0);
/* 230 */     this.pic2Red.setFont(new Font("SansSerif", 0, 13));
/* 231 */     this.pic2Red.setEditable(false);
/* 232 */     this.pic2Red.setColumns(10);
/* 233 */     this.pic2Red.setBorder(null);
/* 234 */     this.pic2Red.setBounds(70, 74, 67, 20);
/* 235 */     add(this.pic2Red);
/*     */ 
/* 237 */     this.pic3Red = new JTextField();
/* 238 */     this.pic3Red.setVisible(false);
/* 239 */     this.pic3Red.setForeground(this.red3);
/* 240 */     this.pic3Red.setOpaque(false);
/* 241 */     this.pic3Red.setHorizontalAlignment(0);
/* 242 */     this.pic3Red.setFont(new Font("SansSerif", 0, 13));
/* 243 */     this.pic3Red.setEditable(false);
/* 244 */     this.pic3Red.setColumns(10);
/* 245 */     this.pic3Red.setBorder(null);
/* 246 */     this.pic3Red.setBounds(70, 107, 67, 20);
/* 247 */     add(this.pic3Red);
/*     */ 
/* 249 */     this.pic1Green = new JTextField();
/* 250 */     this.pic1Green.setForeground(this.green1);
/* 251 */     this.pic1Green.setOpaque(false);
/* 252 */     this.pic1Green.setHorizontalAlignment(0);
/* 253 */     this.pic1Green.setFont(new Font("SansSerif", 0, 13));
/* 254 */     this.pic1Green.setEditable(false);
/* 255 */     this.pic1Green.setColumns(10);
/* 256 */     this.pic1Green.setBorder(null);
/* 257 */     this.pic1Green.setBounds(147, 42, 67, 20);
/* 258 */     add(this.pic1Green);
/*     */ 
/* 260 */     this.pic2Green = new JTextField();
/* 261 */     this.pic2Green.setForeground(this.green2);
/* 262 */     this.pic2Green.setOpaque(false);
/* 263 */     this.pic2Green.setHorizontalAlignment(0);
/* 264 */     this.pic2Green.setFont(new Font("SansSerif", 0, 13));
/* 265 */     this.pic2Green.setEditable(false);
/* 266 */     this.pic2Green.setColumns(10);
/* 267 */     this.pic2Green.setBorder(null);
/* 268 */     this.pic2Green.setBounds(147, 74, 67, 20);
/* 269 */     add(this.pic2Green);
/*     */ 
/* 271 */     this.pic3Green = new JTextField();
/* 272 */     this.pic3Green.setVisible(false);
/* 273 */     this.pic3Green.setForeground(this.green3);
/* 274 */     this.pic3Green.setOpaque(false);
/* 275 */     this.pic3Green.setHorizontalAlignment(0);
/* 276 */     this.pic3Green.setFont(new Font("SansSerif", 0, 13));
/* 277 */     this.pic3Green.setEditable(false);
/* 278 */     this.pic3Green.setColumns(10);
/* 279 */     this.pic3Green.setBorder(null);
/* 280 */     this.pic3Green.setBounds(147, 107, 67, 20);
/* 281 */     add(this.pic3Green);
/*     */ 
/* 283 */     this.pic3Blue = new JTextField();
/* 284 */     this.pic3Blue.setVisible(false);
/* 285 */     this.pic3Blue.setForeground(this.blue3);
/* 286 */     this.pic3Blue.setOpaque(false);
/* 287 */     this.pic3Blue.setHorizontalAlignment(0);
/* 288 */     this.pic3Blue.setFont(new Font("SansSerif", 0, 13));
/* 289 */     this.pic3Blue.setEditable(false);
/* 290 */     this.pic3Blue.setColumns(10);
/* 291 */     this.pic3Blue.setBorder(null);
/* 292 */     this.pic3Blue.setBounds(224, 106, 67, 20);
/* 293 */     add(this.pic3Blue);
/*     */ 
/* 295 */     this.pic1Blue = new JTextField();
/* 296 */     this.pic1Blue.setForeground(Color.BLUE);
/* 297 */     this.pic1Blue.setOpaque(false);
/* 298 */     this.pic1Blue.setHorizontalAlignment(0);
/* 299 */     this.pic1Blue.setFont(new Font("SansSerif", 0, 13));
/* 300 */     this.pic1Blue.setEditable(false);
/* 301 */     this.pic1Blue.setColumns(10);
/* 302 */     this.pic1Blue.setBorder(null);
/* 303 */     this.pic1Blue.setBounds(224, 41, 67, 20);
/* 304 */     add(this.pic1Blue);
/*     */ 
/* 306 */     this.pic2Blue = new JTextField();
/* 307 */     this.pic2Blue.setForeground(this.blue2);
/* 308 */     this.pic2Blue.setOpaque(false);
/* 309 */     this.pic2Blue.setHorizontalAlignment(0);
/* 310 */     this.pic2Blue.setFont(new Font("SansSerif", 0, 13));
/* 311 */     this.pic2Blue.setEditable(false);
/* 312 */     this.pic2Blue.setColumns(10);
/* 313 */     this.pic2Blue.setBorder(null);
/* 314 */     this.pic2Blue.setBounds(224, 73, 67, 20);
/* 315 */     add(this.pic2Blue);
/*     */ 
/* 317 */     this.txtAverage = new JTextField();
/* 318 */     this.txtAverage.setText("Average");
/* 319 */     this.txtAverage.setOpaque(false);
/* 320 */     this.txtAverage.setHorizontalAlignment(0);
/* 321 */     this.txtAverage.setFont(new Font("SansSerif", 0, 13));
/* 322 */     this.txtAverage.setEditable(false);
/* 323 */     this.txtAverage.setColumns(10);
/* 324 */     this.txtAverage.setBorder(null);
/* 325 */     this.txtAverage.setBounds(301, 10, 67, 20);
/* 326 */     add(this.txtAverage);
/*     */ 
/* 328 */     this.pic1Avg = new JTextField();
/* 329 */     this.pic1Avg.setOpaque(false);
/* 330 */     this.pic1Avg.setHorizontalAlignment(0);
/* 331 */     this.pic1Avg.setFont(new Font("SansSerif", 0, 13));
/* 332 */     this.pic1Avg.setEditable(false);
/* 333 */     this.pic1Avg.setColumns(10);
/* 334 */     this.pic1Avg.setBorder(null);
/* 335 */     this.pic1Avg.setBounds(301, 41, 67, 20);
/* 336 */     add(this.pic1Avg);
/*     */ 
/* 338 */     this.pic2Avg = new JTextField();
/* 339 */     this.pic2Avg.setForeground(new Color(5263440));
/* 340 */     this.pic2Avg.setOpaque(false);
/* 341 */     this.pic2Avg.setHorizontalAlignment(0);
/* 342 */     this.pic2Avg.setFont(new Font("SansSerif", 0, 13));
/* 343 */     this.pic2Avg.setEditable(false);
/* 344 */     this.pic2Avg.setColumns(10);
/* 345 */     this.pic2Avg.setBorder(null);
/* 346 */     this.pic2Avg.setBounds(301, 73, 67, 20);
/* 347 */     add(this.pic2Avg);
/*     */ 
/* 349 */     this.pic3Avg = new JTextField();
/* 350 */     this.pic3Avg.setVisible(false);
/* 351 */     this.pic3Avg.setForeground(new Color(8421504));
/* 352 */     this.pic3Avg.setOpaque(false);
/* 353 */     this.pic3Avg.setHorizontalAlignment(0);
/* 354 */     this.pic3Avg.setFont(new Font("SansSerif", 0, 13));
/* 355 */     this.pic3Avg.setEditable(false);
/* 356 */     this.pic3Avg.setColumns(10);
/* 357 */     this.pic3Avg.setBorder(null);
/* 358 */     this.pic3Avg.setBounds(301, 106, 67, 20);
/* 359 */     add(this.pic3Avg);
/*     */   }
/*     */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.datapanels.PointTextPanel
 * JD-Core Version:    0.6.2
 */