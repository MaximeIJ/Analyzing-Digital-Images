 package org.gss.adi.datapanels;
 
 import java.awt.BasicStroke;
 import java.awt.Color;
 import java.awt.Font;
 import java.awt.Graphics2D;
 import java.awt.Point;
 import java.awt.image.BufferedImage;
 import java.text.DecimalFormat;
 import java.util.ArrayList;
 import javax.swing.ImageIcon;
 import javax.swing.JLabel;
 import javax.swing.JTextField;
 import org.gss.adi.tools.ColorTools;
 
 public class LinePanel extends FocusPanel
 {
   private static final long serialVersionUID = 1415235793445540119L;
   private JTextField textField;
   private JTextField txtColorIntensitiesAlong;
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
   Integer[] x;
   Integer[] y;
   private Boolean three = Boolean.valueOf(false);
 
   private ArrayList<Point> redList1 = new ArrayList();
   private ArrayList<Point> redList2 = new ArrayList();
   private ArrayList<Point> redList3 = new ArrayList();
   private ArrayList<Point> greenList1 = new ArrayList();
   private ArrayList<Point> greenList2 = new ArrayList();
   private ArrayList<Point> greenList3 = new ArrayList();
   private ArrayList<Point> blueList1 = new ArrayList();
   private ArrayList<Point> blueList2 = new ArrayList();
   private ArrayList<Point> blueList3 = new ArrayList();
   private JTextField txtMean;
   private JTextField textField_1;
   private int RGB;
 
   public LinePanel(BufferedImage image1, BufferedImage image2, BufferedImage image3, int rgb)
   {
     super(image1, image2, image3, rgb);
     this.RGB = rgb;
     this.img1 = image1;
     this.img2 = image2;
     this.img3 = image3;
 
     setup();
     drawAxis();
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
       this.textField.setText("1");
       this.textField_2.setText(".5");
       this.textField_3.setText("0");
       this.textField_4.setText("-.5");
       this.textField_5.setText("-1");
     }
   }
 
   private void drawAxis() { this.img = new BufferedImage(255, 255, 1);
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
     this.label.setIcon(new ImageIcon(this.img)); }
 
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
 
   public void update1(Integer[] X, Integer[] Y)
   {
     this.x = X;
     this.y = Y;
     drawAxis();
     ArrayList al = ColorTools.getLinePixels(this.x, this.y);
     float scale = 255.0F / al.size();
     this.redList1 = new ArrayList();
     this.redList2 = new ArrayList();
     this.redList3 = new ArrayList();
     this.greenList1 = new ArrayList();
     this.greenList2 = new ArrayList();
     this.greenList3 = new ArrayList();
     this.blueList1 = new ArrayList();
     this.blueList2 = new ArrayList();
     this.blueList3 = new ArrayList();
     if (this.three.booleanValue()) {
       threePics();
       for (int i = 0; i < al.size(); i++) {
         int xVal = Math.round(scale * i);
         Point p = (Point)al.get(i);
         int[] rgb = ColorTools.rgb(this.img1.getRGB(p.x, p.y));
         this.redList1.add(new Point(xVal, rgb[0]));
         this.greenList1.add(new Point(xVal, rgb[1]));
         this.blueList1.add(new Point(xVal, rgb[2]));
         rgb = ColorTools.rgb(this.img2.getRGB(p.x, p.y));
         this.redList2.add(new Point(xVal, rgb[0]));
         this.greenList2.add(new Point(xVal, rgb[1]));
         this.blueList2.add(new Point(xVal, rgb[2]));
         rgb = ColorTools.rgb(this.img3.getRGB(p.x, p.y));
         this.redList3.add(new Point(xVal, rgb[0]));
         this.greenList3.add(new Point(xVal, rgb[1]));
         this.blueList3.add(new Point(xVal, rgb[2]));
       }
       draw1(); draw2(); draw3();
     } else {
       for (int i = 0; i < al.size(); i++) {
         int xVal = Math.round(scale * i);
         Point p = (Point)al.get(i);
         int[] rgb = ColorTools.rgb(this.img1.getRGB(p.x, p.y));
         this.redList1.add(new Point(xVal, rgb[0]));
         this.greenList1.add(new Point(xVal, rgb[1]));
         this.blueList1.add(new Point(xVal, rgb[2]));
         rgb = ColorTools.rgb(this.img2.getRGB(p.x, p.y));
         this.redList2.add(new Point(xVal, rgb[0]));
         this.greenList2.add(new Point(xVal, rgb[1]));
         this.blueList2.add(new Point(xVal, rgb[2]));
       }
       draw1(); draw2();
     }
     repaint();
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
     Float size = Float.valueOf(this.redList1.size() - 1.0F);
     ArrayList al = ColorTools.getLinePixels(this.x, this.y);
     for (int i = 0; i < size.floatValue(); i++) {
       Graphics2D g = this.img.createGraphics();
       g.setStroke(new BasicStroke(2.0F));
       g.setColor(R);
       Point p = (Point)this.redList1.get(i);
       if (this.RGB < 2) {
         g.drawLine(p.x, 255 - p.y, ((Point)this.redList1.get(i + 1)).x, 255 - ((Point)this.redList1.get(i + 1)).y);
         r = Float.valueOf(r.floatValue() + p.y);
         g.setColor(G);
         p = (Point)this.greenList1.get(i);
         g.drawLine(p.x, 255 - p.y, ((Point)this.greenList1.get(i + 1)).x, 255 - ((Point)this.greenList1.get(i + 1)).y);
         gr = Integer.valueOf(gr.intValue() + p.y);
         g.setColor(B);
         p = (Point)this.blueList1.get(i);
         g.drawLine(p.x, 255 - p.y, ((Point)this.blueList1.get(i + 1)).x, 255 - ((Point)this.blueList1.get(i + 1)).y);
         b = Integer.valueOf(b.intValue() + p.y);
       } else {
         r = Float.valueOf(r.floatValue() + ColorTools.colorToNDVI(this.img1.getRGB(((Point)al.get(i)).x, ((Point)al.get(i)).y)));
         g.drawLine(p.x, 128 - Math.round(126.0F * ColorTools.colorToNDVI(this.img1.getRGB(((Point)al.get(i)).x, ((Point)al.get(i)).y))), ((Point)this.redList1.get(i + 1)).x, 128 - Math.round(126.0F * ColorTools.colorToNDVI(this.img1.getRGB(((Point)al.get(i + 1)).x, ((Point)al.get(i + 1)).y))));
       }
       g.dispose();
     }
     if (size.floatValue() == 0.0F) {
       this.r1.setText("");
       this.g1.setText("");
       this.b1.setText("");
     } else {
       this.r1.setText(this.df.format(r.floatValue() / size.floatValue()));
       this.g1.setText(this.df.format(gr.intValue() / size.floatValue()));
       this.b1.setText(this.df.format(b.intValue() / size.floatValue()));
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
     Float size = Float.valueOf(this.redList2.size() - 1.0F);
     ArrayList al = ColorTools.getLinePixels(this.x, this.y);
     for (int i = 0; i < size.floatValue(); i++) {
       Graphics2D g = this.img.createGraphics();
       g.setStroke(new BasicStroke(2.0F));
       g.setColor(R);
       Point p = (Point)this.redList2.get(i);
       if (this.RGB < 2) {
         g.drawLine(p.x, 255 - p.y, ((Point)this.redList2.get(i + 1)).x, 255 - ((Point)this.redList2.get(i + 1)).y);
         r = Float.valueOf(r.floatValue() + p.y);
         g.setColor(G);
         p = (Point)this.greenList2.get(i);
         g.drawLine(p.x, 255 - p.y, ((Point)this.greenList2.get(i + 1)).x, 255 - ((Point)this.greenList2.get(i + 1)).y);
         gr = Integer.valueOf(gr.intValue() + p.y);
         g.setColor(B);
         p = (Point)this.blueList2.get(i);
         g.drawLine(p.x, 255 - p.y, ((Point)this.blueList2.get(i + 1)).x, 255 - ((Point)this.blueList2.get(i + 1)).y);
         b = Integer.valueOf(b.intValue() + p.y);
       } else {
         r = Float.valueOf(r.floatValue() + ColorTools.colorToNDVI(this.img2.getRGB(((Point)al.get(i)).x, ((Point)al.get(i)).y)));
         g.drawLine(p.x, 128 - Math.round(126.0F * ColorTools.colorToNDVI(this.img2.getRGB(((Point)al.get(i)).x, ((Point)al.get(i)).y))), ((Point)this.redList2.get(i + 1)).x, 128 - Math.round(126.0F * ColorTools.colorToNDVI(this.img2.getRGB(((Point)al.get(i + 1)).x, ((Point)al.get(i + 1)).y))));
       }
       g.dispose();
     }
     if (size.floatValue() == 0.0F) {
       this.r2.setText("");
       this.g2.setText("");
       this.b2.setText("");
     } else {
       this.r2.setText(this.df.format(r.floatValue() / size.floatValue()));
       this.g2.setText(this.df.format(gr.intValue() / size.floatValue()));
       this.b2.setText(this.df.format(b.intValue() / size.floatValue()));
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
     Float gr = Float.valueOf(0.0F);
     Float b = Float.valueOf(0.0F);
     Float size = Float.valueOf(this.redList3.size() - 1.0F);
     ArrayList al = ColorTools.getLinePixels(this.x, this.y);
     for (int i = 0; i < size.floatValue(); i++) {
       Graphics2D g = this.img.createGraphics();
       g.setStroke(new BasicStroke(2.0F));
       g.setColor(R);
       Point p = (Point)this.redList3.get(i);
       if (this.RGB < 2) {
         g.drawLine(p.x, 255 - p.y, ((Point)this.redList3.get(i + 1)).x, 255 - ((Point)this.redList3.get(i + 1)).y);
         r = Float.valueOf(r.floatValue() + p.y);
         g.setColor(G);
         p = (Point)this.greenList3.get(i);
         g.drawLine(p.x, 255 - p.y, ((Point)this.greenList3.get(i + 1)).x, 255 - ((Point)this.greenList3.get(i + 1)).y);
         gr = Float.valueOf(gr.floatValue() + p.y);
         g.setColor(B);
         p = (Point)this.blueList3.get(i);
         g.drawLine(p.x, 255 - p.y, ((Point)this.blueList3.get(i + 1)).x, 255 - ((Point)this.blueList3.get(i + 1)).y);
         b = Float.valueOf(b.floatValue() + p.y);
       } else {
         r = Float.valueOf(r.floatValue() + ColorTools.colorToNDVI(this.img3.getRGB(((Point)al.get(i)).x, ((Point)al.get(i)).y)));
         g.drawLine(p.x, 128 - Math.round(126.0F * ColorTools.colorToNDVI(this.img3.getRGB(((Point)al.get(i)).x, ((Point)al.get(i)).y))), ((Point)this.redList3.get(i + 1)).x, 128 - Math.round(126.0F * ColorTools.colorToNDVI(this.img3.getRGB(((Point)al.get(i + 1)).x, ((Point)al.get(i + 1)).y))));
       }
       g.dispose();
     }
     if (size.floatValue() == 0.0F) {
       this.r3.setText("");
       this.g3.setText("");
       this.b3.setText("");
     } else {
       this.r3.setText(this.df.format(r.floatValue() / size.floatValue()));
       this.g3.setText(this.df.format(gr.floatValue() / size.floatValue()));
       this.b3.setText(this.df.format(b.floatValue() / size.floatValue()));
     }
     this.image3txt.setVisible(true);
     this.r3.setVisible(true);
     if (this.RGB < 2) {
       this.g3.setVisible(true);
       this.b3.setVisible(true);
     }
     repaint();
   }
 
   public void update2(Integer[] x, Integer[] y) {
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
 
   private void setup() {
     this.textField = new JTextField();
     this.textField.setText("100%");
     this.textField.setOpaque(false);
     this.textField.setHorizontalAlignment(11);
     this.textField.setFont(new Font("Tahoma", 0, 10));
     this.textField.setEditable(false);
     this.textField.setColumns(10);
     this.textField.setBorder(null);
     this.textField.setBounds(23, 25, 40, 10);
     add(this.textField);
 
     this.txtColorIntensitiesAlong = new JTextField();
     this.txtColorIntensitiesAlong.setText("Color Intensities Along Line");
     this.txtColorIntensitiesAlong.setOpaque(false);
     this.txtColorIntensitiesAlong.setHorizontalAlignment(0);
     this.txtColorIntensitiesAlong.setEditable(false);
     this.txtColorIntensitiesAlong.setColumns(10);
     this.txtColorIntensitiesAlong.setBorder(null);
     this.txtColorIntensitiesAlong.setBounds(73, 11, 250, 20);
     add(this.txtColorIntensitiesAlong);
 
     this.textField_2 = new JTextField();
     this.textField_2.setText("75%");
     this.textField_2.setOpaque(false);
     this.textField_2.setHorizontalAlignment(11);
     this.textField_2.setFont(new Font("Tahoma", 0, 10));
     this.textField_2.setEditable(false);
     this.textField_2.setColumns(10);
     this.textField_2.setBorder(null);
     this.textField_2.setBounds(23, 87, 40, 10);
     add(this.textField_2);
 
     this.textField_3 = new JTextField();
     this.textField_3.setText("50%");
     this.textField_3.setOpaque(false);
     this.textField_3.setHorizontalAlignment(11);
     this.textField_3.setFont(new Font("Tahoma", 0, 10));
     this.textField_3.setEditable(false);
     this.textField_3.setColumns(10);
     this.textField_3.setBorder(null);
     this.textField_3.setBounds(23, 149, 40, 10);
     add(this.textField_3);
 
     this.textField_4 = new JTextField();
     this.textField_4.setText("25%");
     this.textField_4.setOpaque(false);
     this.textField_4.setHorizontalAlignment(11);
     this.textField_4.setFont(new Font("Tahoma", 0, 10));
     this.textField_4.setEditable(false);
     this.textField_4.setColumns(10);
     this.textField_4.setBorder(null);
     this.textField_4.setBounds(23, 211, 40, 10);
     add(this.textField_4);
 
     this.textField_5 = new JTextField();
     this.textField_5.setText("0%");
     this.textField_5.setOpaque(false);
     this.textField_5.setHorizontalAlignment(11);
     this.textField_5.setFont(new Font("Tahoma", 0, 10));
     this.textField_5.setEditable(false);
     this.textField_5.setColumns(10);
     this.textField_5.setBorder(null);
     this.textField_5.setBounds(23, 273, 40, 10);
     add(this.textField_5);
 
     this.label = new JLabel();
     this.label.setBounds(73, 29, 255, 255);
     add(this.label);
 
     this.image1txt = new JTextField();
     this.image1txt.setBorder(null);
     this.image1txt.setOpaque(false);
     this.image1txt.setEditable(false);
     this.image1txt.setHorizontalAlignment(0);
     this.image1txt.setText("Image 1");
     this.image1txt.setBounds(348, 53, 70, 15);
     add(this.image1txt);
     this.image1txt.setColumns(10);
 
     this.r1 = new JTextField();
     this.r1.setForeground(this.red1);
     this.r1.setOpaque(false);
     this.r1.setHorizontalAlignment(0);
     this.r1.setEditable(false);
     this.r1.setColumns(10);
     this.r1.setBorder(null);
     this.r1.setBounds(348, 68, 70, 15);
     add(this.r1);
 
     this.g1 = new JTextField();
     this.g1.setForeground(this.green1);
     this.g1.setOpaque(false);
     this.g1.setHorizontalAlignment(0);
     this.g1.setEditable(false);
     this.g1.setColumns(10);
     this.g1.setBorder(null);
     this.g1.setBounds(348, 83, 70, 15);
     add(this.g1);
 
     this.b1 = new JTextField();
     this.b1.setForeground(this.blue1);
     this.b1.setOpaque(false);
     this.b1.setHorizontalAlignment(0);
     this.b1.setEditable(false);
     this.b1.setColumns(10);
     this.b1.setBorder(null);
     this.b1.setBounds(348, 98, 70, 15);
     add(this.b1);
 
     this.image2txt = new JTextField();
     this.image2txt.setForeground(this.black2);
     this.image2txt.setText("Image 2");
     this.image2txt.setOpaque(false);
     this.image2txt.setHorizontalAlignment(0);
     this.image2txt.setEditable(false);
     this.image2txt.setColumns(10);
     this.image2txt.setBorder(null);
     this.image2txt.setBounds(348, 113, 70, 15);
     add(this.image2txt);
 
     this.r2 = new JTextField();
     this.r2.setForeground(this.red2);
     this.r2.setOpaque(false);
     this.r2.setHorizontalAlignment(0);
     this.r2.setEditable(false);
     this.r2.setColumns(10);
     this.r2.setBorder(null);
     this.r2.setBounds(348, 128, 70, 15);
     add(this.r2);
 
     this.g3 = new JTextField();
     this.g3.setVisible(false);
     this.g3.setForeground(this.green3);
     this.g3.setOpaque(false);
     this.g3.setHorizontalAlignment(0);
     this.g3.setEditable(false);
     this.g3.setColumns(10);
     this.g3.setBorder(null);
     this.g3.setBounds(348, 203, 70, 15);
     add(this.g3);
 
     this.r3 = new JTextField();
     this.r3.setVisible(false);
     this.r3.setForeground(this.red3);
     this.r3.setOpaque(false);
     this.r3.setHorizontalAlignment(0);
     this.r3.setEditable(false);
     this.r3.setColumns(10);
     this.r3.setBorder(null);
     this.r3.setBounds(348, 188, 70, 15);
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
     this.image3txt.setBounds(348, 173, 70, 15);
     add(this.image3txt);
 
     this.b2 = new JTextField();
     this.b2.setForeground(this.blue2);
     this.b2.setOpaque(false);
     this.b2.setHorizontalAlignment(0);
     this.b2.setEditable(false);
     this.b2.setColumns(10);
     this.b2.setBorder(null);
     this.b2.setBounds(348, 158, 70, 15);
     add(this.b2);
 
     this.g2 = new JTextField();
     this.g2.setForeground(this.green2);
     this.g2.setOpaque(false);
     this.g2.setHorizontalAlignment(0);
     this.g2.setEditable(false);
     this.g2.setColumns(10);
     this.g2.setBorder(null);
     this.g2.setBounds(348, 143, 70, 15);
     add(this.g2);
 
     this.b3 = new JTextField();
     this.b3.setVisible(false);
     this.b3.setForeground(this.blue3);
     this.b3.setOpaque(false);
     this.b3.setHorizontalAlignment(0);
     this.b3.setEditable(false);
     this.b3.setColumns(10);
     this.b3.setBorder(null);
     this.b3.setBounds(348, 218, 70, 15);
     add(this.b3);
 
     this.txtMean = new JTextField();
     this.txtMean.setText("Mean");
     this.txtMean.setOpaque(false);
     this.txtMean.setHorizontalAlignment(0);
     this.txtMean.setEditable(false);
     this.txtMean.setColumns(10);
     this.txtMean.setBorder(null);
     this.txtMean.setBounds(348, 25, 70, 15);
     add(this.txtMean);
 
     this.textField_1 = new JTextField();
     this.textField_1.setText("Intensity");
     this.textField_1.setHorizontalAlignment(0);
     this.textField_1.setForeground(Color.MAGENTA);
     this.textField_1.setFont(new Font("Tahoma", 0, 9));
     this.textField_1.setEditable(false);
     this.textField_1.setColumns(10);
     this.textField_1.setBorder(null);
     this.textField_1.setBounds(10, 10, 53, 16);
     add(this.textField_1);
     drawAxis();
   }
 }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.datapanels.LinePanel
 * JD-Core Version:    0.6.2
 */