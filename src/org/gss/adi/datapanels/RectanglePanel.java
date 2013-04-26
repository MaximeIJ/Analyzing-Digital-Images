 package org.gss.adi.datapanels;
 
 import java.awt.BasicStroke;
 import java.awt.Color;
 import java.awt.Font;
 import java.awt.Graphics2D;
 import java.awt.image.BufferedImage;
 import java.text.DecimalFormat;
 import javax.swing.ImageIcon;
 import javax.swing.JLabel;
 import javax.swing.JTextField;
 import org.gss.adi.tools.ColorTools;
 
 public class RectanglePanel extends FocusPanel
 {
   private static final long serialVersionUID = -8439296370947390361L;
   private JTextField textField;
   private JTextField txtRelativeFrequencyOf;
   private JTextField textField_2;
   private JTextField textField_3;
   private JTextField textField_4;
   private JTextField textField_5;
   private JLabel label;
   private BufferedImage img;
   private BufferedImage img1;
   private BufferedImage img2;
   private BufferedImage img3;
   private JTextField image1txt;
   private JTextField r1;
   private JTextField g1;
   private JTextField b1;
   private JTextField image2txt;
   private JTextField r2;
   private JTextField g3;
   private JTextField r3;
   private JTextField image3txt;
   private JTextField b2;
   private JTextField g2;
   private JTextField b3;
   private Boolean three = Boolean.valueOf(false);
 
   private int[] redList1 = new int[256];
   private int[] redList2 = new int[256];
   private int[] redList3 = new int[256];
   private int[] greenList1 = new int[256];
   private int[] greenList2 = new int[256];
   private int[] greenList3 = new int[256];
   private int[] blueList1 = new int[256];
   private int[] blueList2 = new int[256];
   private int[] blueList3 = new int[256];
   private float max1;
   private float max2;
   private float max3;
   private JTextField txtMean;
   private JTextField intens1;
   private JTextField intens5;
   private JTextField intens3;
   private JTextField intens2;
   private JTextField intens4;
   private JTextField textField_10;
   private JTextField intensity;
   private int RGB;
 
   public RectanglePanel(BufferedImage image1, BufferedImage image2, BufferedImage image3, int rgb)
   {
     super(image1, image2, image3, rgb);
     this.RGB = rgb;
     this.img1 = image1;
     this.img2 = image2;
     this.img3 = image3;
     setup();
     if (rgb > 0) {
       this.r1.setForeground(this.black1);
       this.g1.setForeground(this.red1);
       this.b1.setForeground(this.green1);
       this.r2.setForeground(this.black2);
       this.g2.setForeground(this.red2);
       this.b2.setForeground(this.green2);
       this.r3.setForeground(this.black3);
       this.g3.setForeground(this.red3);
       this.b3.setForeground(this.green3);
     }
     if (rgb == 2) {
       this.g1.setVisible(false);
       this.b1.setVisible(false);
       this.g2.setVisible(false);
       this.b2.setVisible(false);
       this.g3.setVisible(false);
       this.b3.setVisible(false);
       this.intens1.setText("-1");
       this.intens2.setText("-.5");
       this.intens3.setText("0");
       this.intens4.setText(".5");
       this.intens5.setText("1");
       this.intensity.setText("NDVI value");
     }
   }
 
   public void clear()
   {
     drawAxis();
     this.image1txt.setVisible(false);
     this.r1.setVisible(false);
     this.g1.setVisible(false);
     this.b1.setVisible(false);
     this.image2txt.setVisible(false);
     this.r2.setVisible(false);
     this.g2.setVisible(false);
     this.b2.setVisible(false);
     this.image3txt.setVisible(false);
     this.r3.setVisible(false);
     this.g3.setVisible(false);
     this.b3.setVisible(false);
   }
 
   public void draw1()
   {
     Color B;
     Color R;
     Color G;
     if (this.RGB == 0) {
       R = this.red1;
       G = this.green1;
       B = this.blue1;
     } else {
       R = this.black1;
       G = this.red1;
       B = this.green1;
     }
     Float r = Float.valueOf(0.0F);
     Integer gr = Integer.valueOf(0);
     Integer b = Integer.valueOf(0);
     float size = 0.0F;
     for (int i = 0; i < 255; i++) {
       Graphics2D g = this.img.createGraphics();
       g.setStroke(new BasicStroke(2.0F));
       size += this.redList1[i];
       g.setColor(R);
       g.drawLine(i, 255 - Math.round(255.0F * this.redList1[i] / this.max1), i + 1, 255 - Math.round(255.0F * this.redList1[(i + 1)] / this.max1));
       if (this.RGB < 2) {
         r = Float.valueOf(r.floatValue() + this.redList1[i] * i);
         g.setColor(G);
         gr = Integer.valueOf(gr.intValue() + this.greenList1[i] * i);
         g.drawLine(i, 255 - Math.round(255.0F * this.greenList1[i] / this.max1), i + 1, 255 - Math.round(255.0F * this.greenList1[(i + 1)] / this.max1));
         g.setColor(B);
         b = Integer.valueOf(b.intValue() + this.blueList1[i] * i);
         g.drawLine(i, 255 - Math.round(255.0F * this.blueList1[i] / this.max1), i + 1, 255 - Math.round(255.0F * this.blueList1[(i + 1)] / this.max1));
       } else {
         r = Float.valueOf(r.floatValue() + this.redList1[i] * (i - 127.0F) / 127.0F);
       }
       g.dispose();
     }
     if (size == 0.0F) {
       this.r1.setText("");
       this.g1.setText("");
       this.b1.setText("");
     } else {
       this.r1.setText(this.df.format(r.floatValue() / size));
       this.g1.setText(this.df.format(gr.intValue() / size));
       this.b1.setText(this.df.format(b.intValue() / size));
     }
     this.image1txt.setVisible(true);
     this.r1.setVisible(true);
     if (this.RGB < 2) {
       this.g1.setVisible(true);
       this.b1.setVisible(true);
     }
     repaint();
   }
 
   public void draw2()
   {
     Color B;
     Color R;
     Color G;
     if (this.RGB == 0) {
       R = this.red2;
       G = this.green2;
       B = this.blue2;
     } else {
       R = this.black2;
       G = this.red2;
       B = this.green2;
     }
     Float r = Float.valueOf(0.0F);
     Integer gr = Integer.valueOf(0);
     Integer b = Integer.valueOf(0);
     float size = 0.0F;
     for (int i = 0; i < 255; i++) {
       Graphics2D g = this.img.createGraphics();
       g.setStroke(new BasicStroke(2.0F));
       g.setColor(R);
       g.drawLine(i, 255 - Math.round(255.0F * this.redList2[i] / this.max2), i + 1, 255 - Math.round(255.0F * this.redList2[(i + 1)] / this.max2));
       size += this.redList2[i];
       if (this.RGB < 2) {
         r = Float.valueOf(r.floatValue() + this.redList2[i] * i);
         g.setColor(G);
         gr = Integer.valueOf(gr.intValue() + this.greenList2[i] * i);
         g.drawLine(i, 255 - Math.round(255.0F * this.greenList2[i] / this.max2), i + 1, 255 - Math.round(255.0F * this.greenList2[(i + 1)] / this.max2));
         g.setColor(B);
         b = Integer.valueOf(b.intValue() + this.blueList2[i] * i);
         g.drawLine(i, 255 - Math.round(255.0F * this.blueList2[i] / this.max2), i + 1, 255 - Math.round(255.0F * this.blueList2[(i + 1)] / this.max2));
       } else {
         r = Float.valueOf(r.floatValue() + this.redList2[i] * (i - 127.0F) / 127.0F);
       }
       g.dispose();
     }
     if (size == 0.0F) {
       this.r2.setText("");
       this.g2.setText("");
       this.b2.setText("");
     } else {
       this.r2.setText(this.df.format(r.floatValue() / size));
       this.g2.setText(this.df.format(gr.intValue() / size));
       this.b2.setText(this.df.format(b.intValue() / size));
     }
     this.image2txt.setVisible(true);
     this.r2.setVisible(true);
     if (this.RGB < 2) {
       this.g2.setVisible(true);
       this.b2.setVisible(true);
     }
     repaint();
   }
 
   public void draw3()
   {
     Color B;
     Color R;
     Color G;
     if (this.RGB == 0) {
       R = this.red3;
       G = this.green3;
       B = this.blue3;
     } else {
       R = this.black3;
       G = this.red3;
       B = this.green3;
     }
     Float r = Float.valueOf(0.0F);
     Integer gr = Integer.valueOf(0);
     Integer b = Integer.valueOf(0);
     float size = 0.0F;
     for (int i = 0; i < 255; i++) {
       Graphics2D g = this.img.createGraphics();
       g.setStroke(new BasicStroke(2.0F));
       g.setColor(R);
       g.drawLine(i, 255 - Math.round(255.0F * this.redList3[i] / this.max3), i + 1, 255 - Math.round(255.0F * this.redList3[(i + 1)] / this.max3));
       size += this.redList3[i];
       if (this.RGB < 2) {
         r = Float.valueOf(r.floatValue() + this.redList3[i] * i);
         g.setColor(G);
         gr = Integer.valueOf(gr.intValue() + this.greenList3[i] * i);
         g.drawLine(i, 255 - Math.round(255.0F * this.greenList3[i] / this.max3), i + 1, 255 - Math.round(255.0F * this.greenList3[(i + 1)] / this.max3));
         g.setColor(B);
         b = Integer.valueOf(b.intValue() + this.blueList3[i] * i);
         g.drawLine(i, 255 - Math.round(255.0F * this.blueList3[i] / this.max3), i + 1, 255 - Math.round(255.0F * this.blueList3[(i + 1)] / this.max3));
       } else {
         r = Float.valueOf(r.floatValue() + this.redList3[i] * (i - 127.0F) / 127.0F);
       }
       g.dispose();
     }
     if (size == 0.0F) {
       this.r3.setText("");
       this.g3.setText("");
       this.b3.setText("");
     } else {
       this.r3.setText(this.df.format(r.floatValue() / size));
       this.g3.setText(this.df.format(gr.intValue() / size));
       this.b3.setText(this.df.format(b.intValue() / size));
     }
     this.image3txt.setVisible(true);
     this.r3.setVisible(true);
     if (this.RGB < 2) {
       this.g3.setVisible(true);
       this.b3.setVisible(true);
     }
     repaint();
   }
 
   public void update1(Integer[] x, Integer[] y)
   {
     drawAxis();
     for (int i = 0; i < 256; i++) {
       this.redList1[i] = 0;
       this.redList2[i] = 0;
       this.redList3[i] = 0;
       this.greenList1[i] = 0;
       this.greenList2[i] = 0;
       this.greenList3[i] = 0;
       this.blueList1[i] = 0;
       this.blueList2[i] = 0;
       this.blueList3[i] = 0;
     }
     this.max1 = 0.0F;
     this.max2 = 0.0F;
     this.max3 = 0.0F;
     int xmax;
     int xmin;
     if (x[0].intValue() <= x[1].intValue()) { xmin = x[0].intValue(); xmax = x[1].intValue(); } else { xmin = x[1].intValue(); xmax = x[0].intValue();
     }
     int ymax;
     int ymin;
     if (y[0].intValue() <= y[1].intValue()) { ymin = y[0].intValue(); ymax = y[1].intValue(); } else { ymin = y[1].intValue(); ymax = y[0].intValue(); }
     if (this.three.booleanValue()) {
       threePics();
       for (int i = xmin; i < xmax; i++) {
         for (int j = ymin; j < ymax; j++) {
           int[] rgb = ColorTools.rgb(this.img1.getRGB(i, j));
           if (this.RGB < 2)
           {
             int tmp254_253 = rgb[0];
             int[] tmp254_247 = this.redList1;
             int tmp256_255 = tmp254_247[tmp254_253]; tmp254_247[tmp254_253] = (tmp256_255 + 1); if (tmp256_255 > this.max1) this.max1 = this.redList1[rgb[0]];
             int tmp291_290 = rgb[1];
             int[] tmp291_284 = this.greenList1;
             int tmp293_292 = tmp291_284[tmp291_290]; tmp291_284[tmp291_290] = (tmp293_292 + 1); if (tmp293_292 > this.max1) this.max1 = this.greenList1[rgb[1]];
             int tmp328_327 = rgb[2];
             int[] tmp328_321 = this.blueList1;
             int tmp330_329 = tmp328_321[tmp328_327]; tmp328_321[tmp328_327] = (tmp330_329 + 1); if (tmp330_329 > this.max1) this.max1 = this.blueList1[rgb[2]];
             rgb = ColorTools.rgb(this.img2.getRGB(i, j));
             int tmp381_380 = rgb[0];
             int[] tmp381_374 = this.redList2;
             int tmp383_382 = tmp381_374[tmp381_380]; tmp381_374[tmp381_380] = (tmp383_382 + 1); if (tmp383_382 > this.max2) this.max2 = this.redList2[rgb[0]];
             int tmp418_417 = rgb[1];
             int[] tmp418_411 = this.greenList2;
             int tmp420_419 = tmp418_411[tmp418_417]; tmp418_411[tmp418_417] = (tmp420_419 + 1); if (tmp420_419 > this.max2) this.max2 = this.greenList2[rgb[1]];
             int tmp455_454 = rgb[2];
             int[] tmp455_448 = this.blueList2;
             int tmp457_456 = tmp455_448[tmp455_454]; tmp455_448[tmp455_454] = (tmp457_456 + 1); if (tmp457_456 > this.max2) this.max2 = this.blueList2[rgb[2]];
             rgb = ColorTools.rgb(this.img3.getRGB(i, j));
             int tmp508_507 = rgb[0];
             int[] tmp508_501 = this.redList3;
             int tmp510_509 = tmp508_501[tmp508_507]; tmp508_501[tmp508_507] = (tmp510_509 + 1); if (tmp510_509 > this.max3) this.max3 = this.redList3[rgb[0]];
             int tmp545_544 = rgb[1];
             int[] tmp545_538 = this.greenList3;
             int tmp547_546 = tmp545_538[tmp545_544]; tmp545_538[tmp545_544] = (tmp547_546 + 1); if (tmp547_546 > this.max3) this.max3 = this.greenList3[rgb[1]];
             int tmp582_581 = rgb[2];
             int[] tmp582_575 = this.blueList3;
             int tmp584_583 = tmp582_575[tmp582_581]; tmp582_575[tmp582_581] = (tmp584_583 + 1); if (tmp584_583 > this.max3) this.max3 = this.blueList3[rgb[2]];
           }
           else
           {
             int tmp633_632 = (127 + Math.round(127.0F * ColorTools.colorToNDVI(rgb)));
             int[] tmp633_615 = this.redList1;
             int tmp635_634 = tmp633_615[tmp633_632]; tmp633_615[tmp633_632] = (tmp635_634 + 1); if (tmp635_634 > this.max1)
               this.max1 = this.redList1[(127 + Math.round(127.0F * ColorTools.colorToNDVI(rgb)))];
             rgb = ColorTools.rgb(this.img2.getRGB(i, j));
             int tmp708_707 = (127 + Math.round(127.0F * ColorTools.colorToNDVI(rgb)));
             int[] tmp708_690 = this.redList2;
             int tmp710_709 = tmp708_690[tmp708_707]; tmp708_690[tmp708_707] = (tmp710_709 + 1); if (tmp710_709 > this.max2)
               this.max2 = this.redList2[(127 + Math.round(127.0F * ColorTools.colorToNDVI(rgb)))];
             rgb = ColorTools.rgb(this.img3.getRGB(i, j));
             int tmp783_782 = (127 + Math.round(127.0F * ColorTools.colorToNDVI(rgb)));
             int[] tmp783_765 = this.redList3;
             int tmp785_784 = tmp783_765[tmp783_782]; tmp783_765[tmp783_782] = (tmp785_784 + 1); if (tmp785_784 > this.max3)
               this.max3 = this.redList3[(127 + Math.round(127.0F * ColorTools.colorToNDVI(rgb)))];
           }
         }
       }
       draw1(); draw2(); draw3();
     } else {
       for (int i = xmin; i < xmax; i++) {
         for (int j = ymin; j < ymax; j++) {
           int[] rgb = ColorTools.rgb(this.img1.getRGB(i, j));
           if (this.RGB < 2)
           {
             int tmp903_902 = rgb[0];
             int[] tmp903_896 = this.redList1;
             int tmp905_904 = tmp903_896[tmp903_902]; tmp903_896[tmp903_902] = (tmp905_904 + 1); if (tmp905_904 > this.max1) this.max1 = this.redList1[rgb[0]];
             int tmp940_939 = rgb[1];
             int[] tmp940_933 = this.greenList1;
             int tmp942_941 = tmp940_933[tmp940_939]; tmp940_933[tmp940_939] = (tmp942_941 + 1); if (tmp942_941 > this.max1) this.max1 = this.greenList1[rgb[1]];
             int tmp977_976 = rgb[2];
             int[] tmp977_970 = this.blueList1;
             int tmp979_978 = tmp977_970[tmp977_976]; tmp977_970[tmp977_976] = (tmp979_978 + 1); if (tmp979_978 > this.max1) this.max1 = this.blueList1[rgb[2]];
             rgb = ColorTools.rgb(this.img2.getRGB(i, j));
             int tmp1030_1029 = rgb[0];
             int[] tmp1030_1023 = this.redList2;
             int tmp1032_1031 = tmp1030_1023[tmp1030_1029]; tmp1030_1023[tmp1030_1029] = (tmp1032_1031 + 1); if (tmp1032_1031 > this.max2) this.max2 = this.redList2[rgb[0]];
             int tmp1067_1066 = rgb[1];
             int[] tmp1067_1060 = this.greenList2;
             int tmp1069_1068 = tmp1067_1060[tmp1067_1066]; tmp1067_1060[tmp1067_1066] = (tmp1069_1068 + 1); if (tmp1069_1068 > this.max2) this.max2 = this.greenList2[rgb[1]];
             int tmp1104_1103 = rgb[2];
             int[] tmp1104_1097 = this.blueList2;
             int tmp1106_1105 = tmp1104_1097[tmp1104_1103]; tmp1104_1097[tmp1104_1103] = (tmp1106_1105 + 1); if (tmp1106_1105 > this.max2) this.max2 = this.blueList2[rgb[2]];
           }
           else
           {
             int tmp1155_1154 = (127 + Math.round(127.0F * ColorTools.colorToNDVI(rgb)));
             int[] tmp1155_1137 = this.redList1;
             int tmp1157_1156 = tmp1155_1137[tmp1155_1154]; tmp1155_1137[tmp1155_1154] = (tmp1157_1156 + 1); if (tmp1157_1156 > this.max1)
               this.max1 = this.redList1[(127 + Math.round(127.0F * ColorTools.colorToNDVI(rgb)))];
             rgb = ColorTools.rgb(this.img2.getRGB(i, j));
             int tmp1230_1229 = (127 + Math.round(127.0F * ColorTools.colorToNDVI(rgb)));
             int[] tmp1230_1212 = this.redList2;
             int tmp1232_1231 = tmp1230_1212[tmp1230_1229]; tmp1230_1212[tmp1230_1229] = (tmp1232_1231 + 1); if (tmp1232_1231 > this.max2)
               this.max2 = this.redList2[(127 + Math.round(127.0F * ColorTools.colorToNDVI(rgb)))];
           }
         }
       }
       draw1(); draw2();
     }
   }
 
   public void update2(Integer[] x, Integer[] y)
   {
   }
 
   public void update3(Integer[] x, Integer[] y) {
   }
 
   public void threePics() {
     this.image3txt.setVisible(true);
     this.r3.setVisible(true);
     this.g3.setVisible(true);
     this.b3.setVisible(true);
     this.three = Boolean.valueOf(true);
   }
 
   private void drawAxis() {
     this.img = new BufferedImage(255, 255, 1);
     Graphics2D g = this.img.createGraphics();
     g.setColor(Color.WHITE);
     g.fillRect(0, 0, 255, 255);
     g.setColor(Color.GRAY);
     g.setStroke(new BasicStroke(1.0F));
     g.drawLine(0, 0, 255, 0);
     g.drawLine(0, 64, 255, 64);
     g.drawLine(0, 126, 255, 126);
     g.drawLine(0, 187, 255, 187);
     g.drawLine(0, 254, 255, 254);
     g.drawLine(0, 0, 0, 255);
     g.drawLine(254, 0, 254, 255);
     g.drawLine(64, 0, 64, 255);
     g.drawLine(126, 0, 126, 255);
     g.drawLine(187, 0, 187, 255);
 
     g.dispose();
     this.label.setIcon(new ImageIcon(this.img));
   }
 
   private void setup() {
     this.textField = new JTextField();
     this.textField.setText("1");
     this.textField.setOpaque(false);
     this.textField.setHorizontalAlignment(11);
     this.textField.setFont(new Font("Tahoma", 0, 10));
     this.textField.setEditable(false);
     this.textField.setColumns(10);
     this.textField.setBorder(null);
     this.textField.setBounds(23, 15, 40, 10);
     add(this.textField);
 
     this.txtRelativeFrequencyOf = new JTextField();
     this.txtRelativeFrequencyOf.setFont(new Font("Tahoma", 0, 10));
     this.txtRelativeFrequencyOf.setText("Relative Frequency of Colors Within Selected Area");
     this.txtRelativeFrequencyOf.setOpaque(false);
     this.txtRelativeFrequencyOf.setHorizontalAlignment(0);
     this.txtRelativeFrequencyOf.setEditable(false);
     this.txtRelativeFrequencyOf.setColumns(10);
     this.txtRelativeFrequencyOf.setBorder(null);
     this.txtRelativeFrequencyOf.setBounds(73, 1, 265, 20);
     add(this.txtRelativeFrequencyOf);
 
     this.textField_2 = new JTextField();
     this.textField_2.setText(".75");
     this.textField_2.setOpaque(false);
     this.textField_2.setHorizontalAlignment(11);
     this.textField_2.setFont(new Font("Tahoma", 0, 10));
     this.textField_2.setEditable(false);
     this.textField_2.setColumns(10);
     this.textField_2.setBorder(null);
     this.textField_2.setBounds(23, 77, 40, 10);
     add(this.textField_2);
 
     this.textField_3 = new JTextField();
     this.textField_3.setText(".5");
     this.textField_3.setOpaque(false);
     this.textField_3.setHorizontalAlignment(11);
     this.textField_3.setFont(new Font("Tahoma", 0, 10));
     this.textField_3.setEditable(false);
     this.textField_3.setColumns(10);
     this.textField_3.setBorder(null);
     this.textField_3.setBounds(23, 139, 40, 10);
     add(this.textField_3);
 
     this.textField_4 = new JTextField();
     this.textField_4.setText(".25");
     this.textField_4.setOpaque(false);
     this.textField_4.setHorizontalAlignment(11);
     this.textField_4.setFont(new Font("Tahoma", 0, 10));
     this.textField_4.setEditable(false);
     this.textField_4.setColumns(10);
     this.textField_4.setBorder(null);
     this.textField_4.setBounds(23, 201, 40, 10);
     add(this.textField_4);
 
     this.textField_5 = new JTextField();
     this.textField_5.setText("0");
     this.textField_5.setOpaque(false);
     this.textField_5.setHorizontalAlignment(11);
     this.textField_5.setFont(new Font("Tahoma", 0, 10));
     this.textField_5.setEditable(false);
     this.textField_5.setColumns(10);
     this.textField_5.setBorder(null);
     this.textField_5.setBounds(23, 263, 40, 10);
     add(this.textField_5);
 
     this.label = new JLabel();
     this.label.setBounds(73, 19, 255, 255);
     add(this.label);
 
     this.image1txt = new JTextField();
     this.image1txt.setBorder(null);
     this.image1txt.setOpaque(false);
     this.image1txt.setEditable(false);
     this.image1txt.setHorizontalAlignment(0);
     this.image1txt.setText("Image 1");
     this.image1txt.setBounds(348, 41, 70, 15);
     add(this.image1txt);
     this.image1txt.setColumns(10);
 
     this.r1 = new JTextField();
     this.r1.setForeground(this.red1);
     this.r1.setOpaque(false);
     this.r1.setHorizontalAlignment(0);
     this.r1.setEditable(false);
     this.r1.setColumns(10);
     this.r1.setBorder(null);
     this.r1.setBounds(348, 56, 70, 15);
     add(this.r1);
 
     this.g1 = new JTextField();
     this.g1.setForeground(this.green1);
     this.g1.setOpaque(false);
     this.g1.setHorizontalAlignment(0);
     this.g1.setEditable(false);
     this.g1.setColumns(10);
     this.g1.setBorder(null);
     this.g1.setBounds(348, 71, 70, 15);
     add(this.g1);
 
     this.b1 = new JTextField();
     this.b1.setForeground(this.blue1);
     this.b1.setOpaque(false);
     this.b1.setHorizontalAlignment(0);
     this.b1.setEditable(false);
     this.b1.setColumns(10);
     this.b1.setBorder(null);
     this.b1.setBounds(348, 86, 70, 15);
     add(this.b1);
 
     this.image2txt = new JTextField();
     this.image2txt.setForeground(this.black2);
     this.image2txt.setText("Image 2");
     this.image2txt.setOpaque(false);
     this.image2txt.setHorizontalAlignment(0);
     this.image2txt.setEditable(false);
     this.image2txt.setColumns(10);
     this.image2txt.setBorder(null);
     this.image2txt.setBounds(348, 101, 70, 15);
     add(this.image2txt);
 
     this.r2 = new JTextField();
     this.r2.setForeground(this.red2);
     this.r2.setOpaque(false);
     this.r2.setHorizontalAlignment(0);
     this.r2.setEditable(false);
     this.r2.setColumns(10);
     this.r2.setBorder(null);
     this.r2.setBounds(348, 116, 70, 15);
     add(this.r2);
 
     this.g3 = new JTextField();
     this.g3.setVisible(false);
     this.g3.setForeground(this.green3);
     this.g3.setOpaque(false);
     this.g3.setHorizontalAlignment(0);
     this.g3.setEditable(false);
     this.g3.setColumns(10);
     this.g3.setBorder(null);
     this.g3.setBounds(348, 191, 70, 15);
     add(this.g3);
 
     this.r3 = new JTextField();
     this.r3.setVisible(false);
     this.r3.setForeground(this.red3);
     this.r3.setOpaque(false);
     this.r3.setHorizontalAlignment(0);
     this.r3.setEditable(false);
     this.r3.setColumns(10);
     this.r3.setBorder(null);
     this.r3.setBounds(348, 176, 70, 15);
     add(this.r3);
 
     this.image3txt = new JTextField();
     this.image3txt.setVisible(false);
     this.image3txt.setForeground(this.black3);
     this.image3txt.setText("Image 3");
     this.image3txt.setOpaque(false);
     this.image3txt.setHorizontalAlignment(0);
     this.image3txt.setEditable(false);
     this.image3txt.setColumns(10);
     this.image3txt.setBorder(null);
     this.image3txt.setBounds(348, 161, 70, 15);
     add(this.image3txt);
 
     this.b2 = new JTextField();
     this.b2.setForeground(this.blue2);
     this.b2.setOpaque(false);
     this.b2.setHorizontalAlignment(0);
     this.b2.setEditable(false);
     this.b2.setColumns(10);
     this.b2.setBorder(null);
     this.b2.setBounds(348, 146, 70, 15);
     add(this.b2);
 
     this.g2 = new JTextField();
     this.g2.setForeground(this.green2);
     this.g2.setOpaque(false);
     this.g2.setHorizontalAlignment(0);
     this.g2.setEditable(false);
     this.g2.setColumns(10);
     this.g2.setBorder(null);
     this.g2.setBounds(348, 131, 70, 15);
     add(this.g2);
 
     this.b3 = new JTextField();
     this.b3.setVisible(false);
     this.b3.setForeground(this.blue3);
     this.b3.setOpaque(false);
     this.b3.setHorizontalAlignment(0);
     this.b3.setEditable(false);
     this.b3.setColumns(10);
     this.b3.setBorder(null);
     this.b3.setBounds(348, 206, 70, 15);
     add(this.b3);
 
     this.txtMean = new JTextField();
     this.txtMean.setText("Mean");
     this.txtMean.setOpaque(false);
     this.txtMean.setHorizontalAlignment(0);
     this.txtMean.setEditable(false);
     this.txtMean.setColumns(10);
     this.txtMean.setBorder(null);
     this.txtMean.setBounds(348, 15, 70, 15);
     add(this.txtMean);
 
     this.intens1 = new JTextField();
     this.intens1.setBorder(null);
     this.intens1.setOpaque(false);
     this.intens1.setEditable(false);
     this.intens1.setFont(new Font("Tahoma", 0, 10));
     this.intens1.setHorizontalAlignment(0);
     this.intens1.setText("0%");
     this.intens1.setBounds(58, 275, 30, 15);
     add(this.intens1);
     this.intens1.setColumns(10);
 
     this.intens5 = new JTextField();
     this.intens5.setText("100%");
     this.intens5.setOpaque(false);
     this.intens5.setHorizontalAlignment(0);
     this.intens5.setFont(new Font("Tahoma", 0, 10));
     this.intens5.setEditable(false);
     this.intens5.setColumns(10);
     this.intens5.setBorder(null);
     this.intens5.setBounds(313, 275, 30, 15);
     add(this.intens5);
 
     this.intens3 = new JTextField();
     this.intens3.setText("50%");
     this.intens3.setOpaque(false);
     this.intens3.setHorizontalAlignment(0);
     this.intens3.setFont(new Font("Tahoma", 0, 10));
     this.intens3.setEditable(false);
     this.intens3.setColumns(10);
     this.intens3.setBorder(null);
     this.intens3.setBounds(186, 275, 30, 15);
     add(this.intens3);
 
     this.intens2 = new JTextField();
     this.intens2.setText("25%");
     this.intens2.setOpaque(false);
     this.intens2.setHorizontalAlignment(0);
     this.intens2.setFont(new Font("Tahoma", 0, 10));
     this.intens2.setEditable(false);
     this.intens2.setColumns(10);
     this.intens2.setBorder(null);
     this.intens2.setBounds(122, 275, 30, 15);
     add(this.intens2);
 
     this.intens4 = new JTextField();
     this.intens4.setText("75%");
     this.intens4.setOpaque(false);
     this.intens4.setHorizontalAlignment(0);
     this.intens4.setFont(new Font("Tahoma", 0, 10));
     this.intens4.setEditable(false);
     this.intens4.setColumns(10);
     this.intens4.setBorder(null);
     this.intens4.setBounds(250, 275, 30, 15);
     add(this.intens4);
 
     this.textField_10 = new JTextField();
     this.textField_10.setText("Frequency");
     this.textField_10.setHorizontalAlignment(0);
     this.textField_10.setForeground(Color.MAGENTA);
     this.textField_10.setFont(new Font("Tahoma", 0, 9));
     this.textField_10.setEditable(false);
     this.textField_10.setColumns(10);
     this.textField_10.setBorder(null);
     this.textField_10.setBounds(10, 0, 53, 16);
     add(this.textField_10);
 
     this.intensity = new JTextField();
     this.intensity.setText("Intensity");
     this.intensity.setHorizontalAlignment(0);
     this.intensity.setForeground(Color.MAGENTA);
     this.intensity.setFont(new Font("Tahoma", 0, 9));
     this.intensity.setEditable(false);
     this.intensity.setColumns(10);
     this.intensity.setBorder(null);
     this.intensity.setBounds(348, 272, 53, 16);
     add(this.intensity);
     drawAxis();
   }
 }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.datapanels.RectanglePanel
 * JD-Core Version:    0.6.2
 */