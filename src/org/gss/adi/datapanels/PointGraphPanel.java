/*     */ package org.gss.adi.datapanels;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.image.BufferedImage;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JTextField;
/*     */ import org.gss.adi.tools.ColorTools;
/*     */ 
/*     */ public class PointGraphPanel extends DataPanel
/*     */ {
/*     */   private static final long serialVersionUID = -2780301889215800228L;
/*     */   private JTextField txtColorIntensitiesAt;
/*     */   private JTextField textField;
/*     */   private JTextField textField_1;
/*     */   private JTextField textField_2;
/*     */   private JTextField textField_3;
/*     */   private JTextField textField_4;
/*     */   private JTextField txtPic;
/*     */   private JTextField endPic;
/*     */   private JTextField middlePic;
/*  28 */   private boolean three = false;
/*     */   private int r1;
/*     */   private int r2;
/*     */   private int r3;
/*     */   private int g1;
/*     */   private int g2;
/*     */   private int g3;
/*     */   private int b1;
/*     */   private int b2;
/*     */   private int b3;
/*     */   private JLabel label;
/*     */   private BufferedImage img;
/*     */   private BufferedImage img1;
/*     */   private BufferedImage img2;
/*     */   private BufferedImage img3;
/*     */   private JTextField textField_5;
/*     */   private int RGB;
/*     */ 
/*     */   public PointGraphPanel(BufferedImage image1, BufferedImage image2, BufferedImage image3, int rgb)
/*     */   {
/*  54 */     super(image1, image2, image3, rgb);
/*     */ 
/*  56 */     setup();
/*  57 */     this.RGB = rgb;
/*  58 */     this.img1 = image1;
/*  59 */     this.img2 = image2;
/*  60 */     this.img3 = image3;
/*     */ 
/*  62 */     this.label = new JLabel();
/*  63 */     this.label.setBounds(100, 29, 255, 255);
/*  64 */     add(this.label);
/*     */ 
/*  66 */     this.textField_5 = new JTextField();
/*  67 */     this.textField_5.setText("Intensity");
/*  68 */     this.textField_5.setHorizontalAlignment(0);
/*  69 */     this.textField_5.setForeground(Color.MAGENTA);
/*  70 */     this.textField_5.setFont(new Font("Tahoma", 0, 9));
/*  71 */     this.textField_5.setEditable(false);
/*  72 */     this.textField_5.setColumns(10);
/*  73 */     this.textField_5.setBorder(null);
/*  74 */     this.textField_5.setBounds(37, 10, 53, 16);
/*  75 */     add(this.textField_5);
/*     */ 
/*  77 */     drawAxis();
/*     */   }
/*     */ 
/*     */   private void drawAxis() {
/*  81 */     this.img = new BufferedImage(255, 255, 1);
/*  82 */     Graphics2D g = this.img.createGraphics();
/*  83 */     g.setColor(Color.WHITE);
/*  84 */     g.fillRect(0, 0, 255, 255);
/*  85 */     g.setColor(Color.GRAY);
/*  86 */     g.setStroke(new BasicStroke(1.0F));
/*  87 */     g.drawLine(0, 0, 255, 0);
/*  88 */     g.drawLine(0, 64, 255, 64);
/*  89 */     g.drawLine(0, 126, 255, 126);
/*  90 */     g.drawLine(0, 187, 255, 187);
/*  91 */     g.drawLine(0, 254, 255, 254);
/*  92 */     g.drawLine(0, 0, 0, 255);
/*  93 */     g.drawLine(254, 0, 254, 255);
/*  94 */     if (this.three) {
/*  95 */       g.drawLine(127, 0, 127, 255);
/*     */     }
/*  97 */     g.dispose();
/*  98 */     this.label.setIcon(new ImageIcon(this.img));
/*     */   }
/*     */ 
/*     */   public void update1(Integer[] x, Integer[] y) {
/* 102 */     int[] rgb = ColorTools.rgb(this.img1.getRGB(x[0].intValue(), y[0].intValue()));
/*     */     Color B;
/*     */     Color R;
/*     */     Color G;
/* 106 */     if (this.RGB == 0) {
/* 107 */       R = this.red1;
/* 108 */       G = this.green1;
/* 109 */       B = this.blue1;
/*     */     } else {
/* 111 */       R = this.black1;
/* 112 */       G = this.red1;
/* 113 */       B = this.green1;
/*     */     }
/* 115 */     this.r1 = rgb[0];
/* 116 */     this.g1 = rgb[1];
/* 117 */     this.b1 = rgb[2];
/* 118 */     drawAxis();
/* 119 */     if (this.three) {
/* 120 */       threePics();
/* 121 */       Graphics2D g = this.img.createGraphics();
/* 122 */       g.setStroke(new BasicStroke(2.0F));
/* 123 */       g.setColor(R);
/* 124 */       if (this.RGB < 2) {
/* 125 */         g.drawLine(0, 255 - this.r1, 127, 255 - this.r2);
/* 126 */         g.drawLine(127, 255 - this.r2, 255, 255 - this.r3);
/* 127 */         g.setColor(G);
/* 128 */         g.drawLine(0, 255 - this.g1, 127, 255 - this.g2);
/* 129 */         g.drawLine(127, 255 - this.g2, 255, 255 - this.g3);
/* 130 */         g.setColor(B);
/* 131 */         g.drawLine(0, 255 - this.b1, 127, 255 - this.b2);
/* 132 */         g.drawLine(127, 255 - this.b2, 255, 255 - this.b3);
/*     */       } else {
/* 134 */         g.drawLine(0, 128 - Math.round(126.0F * ColorTools.colorToNDVI(this.img1.getRGB(x[0].intValue(), y[0].intValue()))), 127, 128 - Math.round(126.0F * ColorTools.colorToNDVI(this.img2.getRGB(x[0].intValue(), y[0].intValue()))));
/* 135 */         g.drawLine(127, 128 - Math.round(126.0F * ColorTools.colorToNDVI(this.img2.getRGB(x[0].intValue(), y[0].intValue()))), 255, 128 - Math.round(126.0F * ColorTools.colorToNDVI(this.img3.getRGB(x[0].intValue(), y[0].intValue()))));
/*     */       }
/* 137 */       g.dispose();
/*     */     } else {
/* 139 */       Graphics2D g = this.img.createGraphics();
/* 140 */       g.setStroke(new BasicStroke(2.0F));
/* 141 */       g.setColor(R);
/* 142 */       g.drawLine(0, 255 - this.r1, 255, 255 - this.r2);
/* 143 */       if (this.RGB < 2) {
/* 144 */         g.setColor(G);
/* 145 */         g.drawLine(0, 255 - this.g1, 255, 255 - this.g2);
/* 146 */         g.setColor(B);
/* 147 */         g.drawLine(0, 255 - this.b1, 255, 255 - this.b2);
/*     */       }
/* 149 */       g.dispose();
/*     */     }
/* 151 */     repaint();
/*     */   }
/*     */ 
/*     */   public void update2(Integer[] x, Integer[] y)
/*     */   {
/* 156 */     int[] rgb = ColorTools.rgb(this.img2.getRGB(x[0].intValue(), y[0].intValue()));
/* 157 */     this.r2 = rgb[0];
/* 158 */     this.g2 = rgb[1];
/* 159 */     this.b2 = rgb[2];
/*     */   }
/*     */ 
/*     */   public void update3(Integer[] x, Integer[] y)
/*     */   {
/* 164 */     int[] rgb = ColorTools.rgb(this.img3.getRGB(x[0].intValue(), y[0].intValue()));
/* 165 */     this.r3 = rgb[0];
/* 166 */     this.g3 = rgb[1];
/* 167 */     this.b3 = rgb[2];
/*     */   }
/*     */ 
/*     */   public void threePics()
/*     */   {
/* 172 */     this.three = true;
/* 173 */     this.middlePic.setVisible(true);
/* 174 */     this.middlePic.setText("Pic 2");
/* 175 */     this.endPic.setText("Pic 3");
/* 176 */     Graphics2D g = this.img.createGraphics();
/* 177 */     g.setColor(Color.GRAY);
/* 178 */     g.setStroke(new BasicStroke(1.0F));
/* 179 */     g.drawLine(127, 0, 127, 255);
/* 180 */     g.dispose();
/* 181 */     repaint();
/*     */   }
/*     */   private void setup() {
/* 184 */     this.txtColorIntensitiesAt = new JTextField();
/* 185 */     this.txtColorIntensitiesAt.setOpaque(false);
/* 186 */     this.txtColorIntensitiesAt.setBorder(null);
/* 187 */     this.txtColorIntensitiesAt.setEditable(false);
/* 188 */     this.txtColorIntensitiesAt.setHorizontalAlignment(0);
/* 189 */     this.txtColorIntensitiesAt.setText("Color Intensities at Selected Point on Images");
/* 190 */     this.txtColorIntensitiesAt.setBounds(100, 11, 250, 20);
/* 191 */     add(this.txtColorIntensitiesAt);
/* 192 */     this.txtColorIntensitiesAt.setColumns(10);
/*     */ 
/* 194 */     this.textField = new JTextField();
/* 195 */     this.textField.setFont(new Font("Tahoma", 0, 10));
/* 196 */     this.textField.setBorder(null);
/* 197 */     this.textField.setOpaque(false);
/* 198 */     this.textField.setEditable(false);
/* 199 */     this.textField.setHorizontalAlignment(11);
/* 200 */     this.textField.setText("100%");
/* 201 */     this.textField.setBounds(50, 25, 40, 10);
/* 202 */     add(this.textField);
/* 203 */     this.textField.setColumns(10);
/*     */ 
/* 205 */     this.textField_1 = new JTextField();
/* 206 */     this.textField_1.setText("75%");
/* 207 */     this.textField_1.setOpaque(false);
/* 208 */     this.textField_1.setHorizontalAlignment(11);
/* 209 */     this.textField_1.setFont(new Font("Tahoma", 0, 10));
/* 210 */     this.textField_1.setEditable(false);
/* 211 */     this.textField_1.setColumns(10);
/* 212 */     this.textField_1.setBorder(null);
/* 213 */     this.textField_1.setBounds(50, 87, 40, 10);
/* 214 */     add(this.textField_1);
/*     */ 
/* 216 */     this.textField_2 = new JTextField();
/* 217 */     this.textField_2.setText("50%");
/* 218 */     this.textField_2.setOpaque(false);
/* 219 */     this.textField_2.setHorizontalAlignment(11);
/* 220 */     this.textField_2.setFont(new Font("Tahoma", 0, 10));
/* 221 */     this.textField_2.setEditable(false);
/* 222 */     this.textField_2.setColumns(10);
/* 223 */     this.textField_2.setBorder(null);
/* 224 */     this.textField_2.setBounds(50, 149, 40, 10);
/* 225 */     add(this.textField_2);
/*     */ 
/* 227 */     this.textField_3 = new JTextField();
/* 228 */     this.textField_3.setText("0%");
/* 229 */     this.textField_3.setOpaque(false);
/* 230 */     this.textField_3.setHorizontalAlignment(11);
/* 231 */     this.textField_3.setFont(new Font("Tahoma", 0, 10));
/* 232 */     this.textField_3.setEditable(false);
/* 233 */     this.textField_3.setColumns(10);
/* 234 */     this.textField_3.setBorder(null);
/* 235 */     this.textField_3.setBounds(50, 273, 40, 10);
/* 236 */     add(this.textField_3);
/*     */ 
/* 238 */     this.textField_4 = new JTextField();
/* 239 */     this.textField_4.setText("25%");
/* 240 */     this.textField_4.setOpaque(false);
/* 241 */     this.textField_4.setHorizontalAlignment(11);
/* 242 */     this.textField_4.setFont(new Font("Tahoma", 0, 10));
/* 243 */     this.textField_4.setEditable(false);
/* 244 */     this.textField_4.setColumns(10);
/* 245 */     this.textField_4.setBorder(null);
/* 246 */     this.textField_4.setBounds(50, 211, 40, 10);
/* 247 */     add(this.textField_4);
/*     */ 
/* 249 */     this.txtPic = new JTextField();
/* 250 */     this.txtPic.setText("Pic 1");
/* 251 */     this.txtPic.setOpaque(false);
/* 252 */     this.txtPic.setHorizontalAlignment(0);
/* 253 */     this.txtPic.setFont(new Font("Tahoma", 0, 10));
/* 254 */     this.txtPic.setEditable(false);
/* 255 */     this.txtPic.setColumns(10);
/* 256 */     this.txtPic.setBorder(null);
/* 257 */     this.txtPic.setBounds(80, 285, 40, 10);
/* 258 */     add(this.txtPic);
/*     */ 
/* 260 */     this.endPic = new JTextField();
/* 261 */     this.endPic.setText("Pic 2");
/* 262 */     this.endPic.setOpaque(false);
/* 263 */     this.endPic.setHorizontalAlignment(0);
/* 264 */     this.endPic.setFont(new Font("Tahoma", 0, 10));
/* 265 */     this.endPic.setEditable(false);
/* 266 */     this.endPic.setColumns(10);
/* 267 */     this.endPic.setBorder(null);
/* 268 */     this.endPic.setBounds(330, 285, 40, 10);
/* 269 */     add(this.endPic);
/*     */ 
/* 271 */     this.middlePic = new JTextField();
/* 272 */     this.middlePic.setVisible(false);
/* 273 */     this.middlePic.setOpaque(false);
/* 274 */     this.middlePic.setHorizontalAlignment(0);
/* 275 */     this.middlePic.setFont(new Font("Tahoma", 0, 10));
/* 276 */     this.middlePic.setEditable(false);
/* 277 */     this.middlePic.setColumns(10);
/* 278 */     this.middlePic.setBorder(null);
/* 279 */     this.middlePic.setBounds(205, 285, 40, 10);
/* 280 */     add(this.middlePic);
/*     */   }
/*     */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.datapanels.PointGraphPanel
 * JD-Core Version:    0.6.2
 */