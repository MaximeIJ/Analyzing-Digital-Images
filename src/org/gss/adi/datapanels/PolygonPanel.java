 package org.gss.adi.datapanels;
 
 import java.awt.BasicStroke;
 import java.awt.Color;
 import java.awt.Font;
 import java.awt.Graphics2D;
 import java.awt.Point;
 import java.awt.Polygon;
 import java.awt.image.BufferedImage;
 import java.text.DecimalFormat;
 import java.util.ArrayList;
 import javax.swing.ImageIcon;
 import javax.swing.JLabel;
 import javax.swing.JTextField;
 import org.gss.adi.tools.ColorTools;
 
 public class PolygonPanel extends FocusPanel
 {
   private static final long serialVersionUID = 2233417006392535510L;
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
   private JTextField intens1;
   private JTextField intens2;
   private JTextField intens3;
   private JTextField intens4;
   private JTextField intens5;
   private JTextField txtMean;
   private JTextField txtFrequency;
   private JTextField intensity;
   private int RGB;
 
   public PolygonPanel(BufferedImage image1, BufferedImage image2, BufferedImage image3, int rgb)
   {
     super(image1, image2, image3, rgb);
     this.RGB = rgb;
     this.img1 = image1;
     this.img2 = image2;
     this.img3 = image3;
     setup();
     if (rgb != 0) {
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
       g.setColor(R);
       g.drawLine(i, 255 - Math.round(255.0F * this.redList1[i] / this.max1), i + 1, 255 - Math.round(255.0F * this.redList1[(i + 1)] / this.max1));
       size += this.redList1[i];
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
     ArrayList al = polyArea(x, y);
     if (this.three.booleanValue()) {
       threePics();
       for (int i = 0; i < al.size(); i++) {
         Point p = (Point)al.get(i);
         int[] rgb = ColorTools.rgb(this.img1.getRGB(p.x, p.y));
         if (this.RGB < 2)
         {
           int tmp173_172 = rgb[0];
           int[] tmp173_166 = this.redList1;
           int tmp175_174 = tmp173_166[tmp173_172]; tmp173_166[tmp173_172] = (tmp175_174 + 1); if (tmp175_174 > this.max1) this.max1 = this.redList1[rgb[0]];
           int tmp210_209 = rgb[1];
           int[] tmp210_203 = this.greenList1;
           int tmp212_211 = tmp210_203[tmp210_209]; tmp210_203[tmp210_209] = (tmp212_211 + 1); if (tmp212_211 > this.max1) this.max1 = this.greenList1[rgb[1]];
           int tmp247_246 = rgb[2];
           int[] tmp247_240 = this.blueList1;
           int tmp249_248 = tmp247_240[tmp247_246]; tmp247_240[tmp247_246] = (tmp249_248 + 1); if (tmp249_248 > this.max1) this.max1 = this.blueList1[rgb[2]];
           rgb = ColorTools.rgb(this.img2.getRGB(p.x, p.y));
           int tmp306_305 = rgb[0];
           int[] tmp306_299 = this.redList2;
           int tmp308_307 = tmp306_299[tmp306_305]; tmp306_299[tmp306_305] = (tmp308_307 + 1); if (tmp308_307 > this.max2) this.max2 = this.redList2[rgb[0]];
           int tmp343_342 = rgb[1];
           int[] tmp343_336 = this.greenList2;
           int tmp345_344 = tmp343_336[tmp343_342]; tmp343_336[tmp343_342] = (tmp345_344 + 1); if (tmp345_344 > this.max2) this.max2 = this.greenList2[rgb[1]];
           int tmp380_379 = rgb[2];
           int[] tmp380_373 = this.blueList2;
           int tmp382_381 = tmp380_373[tmp380_379]; tmp380_373[tmp380_379] = (tmp382_381 + 1); if (tmp382_381 > this.max2) this.max2 = this.blueList2[rgb[2]];
           rgb = ColorTools.rgb(this.img3.getRGB(p.x, p.y));
           int tmp439_438 = rgb[0];
           int[] tmp439_432 = this.redList3;
           int tmp441_440 = tmp439_432[tmp439_438]; tmp439_432[tmp439_438] = (tmp441_440 + 1); if (tmp441_440 > this.max3) this.max3 = this.redList3[rgb[0]];
           int tmp476_475 = rgb[1];
           int[] tmp476_469 = this.greenList3;
           int tmp478_477 = tmp476_469[tmp476_475]; tmp476_469[tmp476_475] = (tmp478_477 + 1); if (tmp478_477 > this.max3) this.max3 = this.greenList3[rgb[1]];
           int tmp513_512 = rgb[2];
           int[] tmp513_506 = this.blueList3;
           int tmp515_514 = tmp513_506[tmp513_512]; tmp513_506[tmp513_512] = (tmp515_514 + 1); if (tmp515_514 > this.max3) this.max3 = this.blueList3[rgb[2]];
         }
         else
         {
           int tmp564_563 = (127 + Math.round(127.0F * ColorTools.colorToNDVI(rgb)));
           int[] tmp564_546 = this.redList1;
           int tmp566_565 = tmp564_546[tmp564_563]; tmp564_546[tmp564_563] = (tmp566_565 + 1); if (tmp566_565 > this.max1)
             this.max1 = this.redList1[(127 + Math.round(127.0F * ColorTools.colorToNDVI(rgb)))];
           rgb = ColorTools.rgb(this.img2.getRGB(p.x, p.y));
           int tmp645_644 = (127 + Math.round(127.0F * ColorTools.colorToNDVI(rgb)));
           int[] tmp645_627 = this.redList2;
           int tmp647_646 = tmp645_627[tmp645_644]; tmp645_627[tmp645_644] = (tmp647_646 + 1); if (tmp647_646 > this.max2)
             this.max2 = this.redList2[(127 + Math.round(127.0F * ColorTools.colorToNDVI(rgb)))];
           rgb = ColorTools.rgb(this.img3.getRGB(p.x, p.y));
           int tmp726_725 = (127 + Math.round(127.0F * ColorTools.colorToNDVI(rgb)));
           int[] tmp726_708 = this.redList3;
           int tmp728_727 = tmp726_708[tmp726_725]; tmp726_708[tmp726_725] = (tmp728_727 + 1); if (tmp728_727 > this.max3)
             this.max3 = this.redList3[(127 + Math.round(127.0F * ColorTools.colorToNDVI(rgb)))];
         }
       }
       draw1(); draw2(); draw3();
     } else {
       for (int i = 0; i < al.size(); i++) {
         Point p = (Point)al.get(i);
         int[] rgb = ColorTools.rgb(this.img1.getRGB(p.x, p.y));
         if (this.RGB < 2)
         {
           int tmp848_847 = rgb[0];
           int[] tmp848_841 = this.redList1;
           int tmp850_849 = tmp848_841[tmp848_847]; tmp848_841[tmp848_847] = (tmp850_849 + 1); if (tmp850_849 > this.max1) this.max1 = this.redList1[rgb[0]];
           int tmp885_884 = rgb[1];
           int[] tmp885_878 = this.greenList1;
           int tmp887_886 = tmp885_878[tmp885_884]; tmp885_878[tmp885_884] = (tmp887_886 + 1); if (tmp887_886 > this.max1) this.max1 = this.greenList1[rgb[1]];
           int tmp922_921 = rgb[2];
           int[] tmp922_915 = this.blueList1;
           int tmp924_923 = tmp922_915[tmp922_921]; tmp922_915[tmp922_921] = (tmp924_923 + 1); if (tmp924_923 > this.max1) this.max1 = this.blueList1[rgb[2]];
           rgb = ColorTools.rgb(this.img2.getRGB(p.x, p.y));
           int tmp981_980 = rgb[0];
           int[] tmp981_974 = this.redList2;
           int tmp983_982 = tmp981_974[tmp981_980]; tmp981_974[tmp981_980] = (tmp983_982 + 1); if (tmp983_982 > this.max2) this.max2 = this.redList2[rgb[0]];
           int tmp1018_1017 = rgb[1];
           int[] tmp1018_1011 = this.greenList2;
           int tmp1020_1019 = tmp1018_1011[tmp1018_1017]; tmp1018_1011[tmp1018_1017] = (tmp1020_1019 + 1); if (tmp1020_1019 > this.max2) this.max2 = this.greenList2[rgb[1]];
           int tmp1055_1054 = rgb[2];
           int[] tmp1055_1048 = this.blueList2;
           int tmp1057_1056 = tmp1055_1048[tmp1055_1054]; tmp1055_1048[tmp1055_1054] = (tmp1057_1056 + 1); if (tmp1057_1056 > this.max2) this.max2 = this.blueList2[rgb[2]];
         }
         else
         {
           int tmp1106_1105 = (127 + Math.round(127.0F * ColorTools.colorToNDVI(rgb)));
           int[] tmp1106_1088 = this.redList1;
           int tmp1108_1107 = tmp1106_1088[tmp1106_1105]; tmp1106_1088[tmp1106_1105] = (tmp1108_1107 + 1); if (tmp1108_1107 > this.max1)
             this.max1 = this.redList1[(127 + Math.round(127.0F * ColorTools.colorToNDVI(rgb)))];
           rgb = ColorTools.rgb(this.img2.getRGB(p.x, p.y));
           int tmp1187_1186 = (127 + Math.round(127.0F * ColorTools.colorToNDVI(rgb)));
           int[] tmp1187_1169 = this.redList2;
           int tmp1189_1188 = tmp1187_1169[tmp1187_1186]; tmp1187_1169[tmp1187_1186] = (tmp1189_1188 + 1); if (tmp1189_1188 > this.max2)
             this.max2 = this.redList2[(127 + Math.round(127.0F * ColorTools.colorToNDVI(rgb)))];
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
 
     g.dispose();
     this.label.setIcon(new ImageIcon(this.img));
   }
 
   private ArrayList<Point> polyArea(Integer[] x, Integer[] y) {
     int xmax = 0;
     int xmin = 9000;
     int ymax = 0;
     int ymin = 9000;
     int[] X = new int[x.length];
     int[] Y = new int[y.length];
     for (int i = 0; i < x.length; i++) {
       X[i] = x[i].intValue();
       Y[i] = y[i].intValue();
       if (x[i].intValue() > xmax) xmax = x[i].intValue();
       if (x[i].intValue() < xmin) xmin = x[i].intValue();
       if (y[i].intValue() > ymax) ymax = y[i].intValue();
       if (y[i].intValue() < ymin) ymin = y[i].intValue();
     }
     final ArrayList points1 = new ArrayList();
     ArrayList points2 = new ArrayList();
     final Polygon poly = new Polygon(X, Y, X.length);
     final int start = xmin;
     final int end = xmax / 2;
     final int starty = ymin;
     final int endy = ymax;
     Runnable r1 = new Thread()
     {
       public void run() {
         for (int i = start; i <= end; i++)
           for (int j = starty; j <= endy; j++)
             if (poly.contains(i, j))
             {
               points1.add(new Point(i, j));
             }
       }
     };
     r1.run();
     for (int i = end; i <= xmax; i++) {
       for (int j = ymin; j <= ymax; j++) {
         if (poly.contains(i, j)) {
           points2.add(new Point(i, j));
         }
       }
     }
     points2.addAll(points1);
     return points2;
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
     this.txtRelativeFrequencyOf.setBounds(73, 1, 269, 20);
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
 
     this.intens1 = new JTextField();
     this.intens1.setText("0%");
     this.intens1.setOpaque(false);
     this.intens1.setHorizontalAlignment(0);
     this.intens1.setFont(new Font("Tahoma", 0, 10));
     this.intens1.setEditable(false);
     this.intens1.setColumns(10);
     this.intens1.setBorder(null);
     this.intens1.setBounds(57, 275, 30, 15);
     add(this.intens1);
 
     this.intens2 = new JTextField();
     this.intens2.setText("25%");
     this.intens2.setOpaque(false);
     this.intens2.setHorizontalAlignment(0);
     this.intens2.setFont(new Font("Tahoma", 0, 10));
     this.intens2.setEditable(false);
     this.intens2.setColumns(10);
     this.intens2.setBorder(null);
     this.intens2.setBounds(121, 275, 30, 15);
     add(this.intens2);
 
     this.intens3 = new JTextField();
     this.intens3.setText("50%");
     this.intens3.setOpaque(false);
     this.intens3.setHorizontalAlignment(0);
     this.intens3.setFont(new Font("Tahoma", 0, 10));
     this.intens3.setEditable(false);
     this.intens3.setColumns(10);
     this.intens3.setBorder(null);
     this.intens3.setBounds(185, 275, 30, 15);
     add(this.intens3);
 
     this.intens4 = new JTextField();
     this.intens4.setText("75%");
     this.intens4.setOpaque(false);
     this.intens4.setHorizontalAlignment(0);
     this.intens4.setFont(new Font("Tahoma", 0, 10));
     this.intens4.setEditable(false);
     this.intens4.setColumns(10);
     this.intens4.setBorder(null);
     this.intens4.setBounds(249, 275, 30, 15);
     add(this.intens4);
 
     this.intens5 = new JTextField();
     this.intens5.setText("100%");
     this.intens5.setOpaque(false);
     this.intens5.setHorizontalAlignment(0);
     this.intens5.setFont(new Font("Tahoma", 0, 10));
     this.intens5.setEditable(false);
     this.intens5.setColumns(10);
     this.intens5.setBorder(null);
     this.intens5.setBounds(312, 275, 30, 15);
     add(this.intens5);
 
     this.txtMean = new JTextField();
     this.txtMean.setText("Mean");
     this.txtMean.setOpaque(false);
     this.txtMean.setHorizontalAlignment(0);
     this.txtMean.setEditable(false);
     this.txtMean.setColumns(10);
     this.txtMean.setBorder(null);
     this.txtMean.setBounds(348, 15, 70, 15);
     add(this.txtMean);
 
     this.txtFrequency = new JTextField();
     this.txtFrequency.setForeground(Color.MAGENTA);
     this.txtFrequency.setBorder(null);
     this.txtFrequency.setEditable(false);
     this.txtFrequency.setFont(new Font("Tahoma", 0, 9));
     this.txtFrequency.setHorizontalAlignment(0);
     this.txtFrequency.setText("Frequency");
     this.txtFrequency.setBounds(10, 0, 53, 16);
     add(this.txtFrequency);
     this.txtFrequency.setColumns(10);
 
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
 * Qualified Name:     org.gss.adi.datapanels.PolygonPanel
 * JD-Core Version:    0.6.2
 */