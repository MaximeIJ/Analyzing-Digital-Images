 package org.gss.adi.datapanels;
 
 import java.awt.BasicStroke;
 import java.awt.Color;
 import java.awt.Font;
 import java.awt.Graphics2D;
 import java.awt.image.BufferedImage;
 import javax.swing.ImageIcon;
 import javax.swing.JLabel;
 import javax.swing.JTextField;
 import org.gss.adi.tools.ColorTools;
 
 public class PointGraphPanel extends DataPanel
 {
   private static final long serialVersionUID = -2780301889215800228L;
   private JTextField txtColorIntensitiesAt;
   private JTextField textField;
   private JTextField textField_1;
   private JTextField textField_2;
   private JTextField textField_3;
   private JTextField textField_4;
   private JTextField txtPic;
   private JTextField endPic;
   private JTextField middlePic;
   private boolean three = false;
   private int r1;
   private int r2;
   private int r3;
   private int g1;
   private int g2;
   private int g3;
   private int b1;
   private int b2;
   private int b3;
   private JLabel label;
   private BufferedImage img;
   private BufferedImage img1;
   private BufferedImage img2;
   private BufferedImage img3;
   private JTextField textField_5;
   private int RGB;
 
   public PointGraphPanel(BufferedImage image1, BufferedImage image2, BufferedImage image3, int rgb)
   {
     super(image1, image2, image3, rgb);
 
     setup();
     this.RGB = rgb;
     this.img1 = image1;
     this.img2 = image2;
     this.img3 = image3;
 
     this.label = new JLabel();
     this.label.setBounds(100, 29, 255, 255);
     add(this.label);
 
     this.textField_5 = new JTextField();
     this.textField_5.setText("Intensity");
     this.textField_5.setHorizontalAlignment(0);
     this.textField_5.setForeground(Color.MAGENTA);
     this.textField_5.setFont(new Font("Tahoma", 0, 9));
     this.textField_5.setEditable(false);
     this.textField_5.setColumns(10);
     this.textField_5.setBorder(null);
     this.textField_5.setBounds(37, 10, 53, 16);
     add(this.textField_5);
 
     drawAxis();
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
     if (this.three) {
       g.drawLine(127, 0, 127, 255);
     }
     g.dispose();
     this.label.setIcon(new ImageIcon(this.img));
   }
 
   public void update1(Integer[] x, Integer[] y) {
     int[] rgb = ColorTools.rgb(this.img1.getRGB(x[0].intValue(), y[0].intValue()));
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
     this.r1 = rgb[0];
     this.g1 = rgb[1];
     this.b1 = rgb[2];
     drawAxis();
     if (this.three) {
       threePics();
       Graphics2D g = this.img.createGraphics();
       g.setStroke(new BasicStroke(2.0F));
       g.setColor(R);
       if (this.RGB < 2) {
         g.drawLine(0, 255 - this.r1, 127, 255 - this.r2);
         g.drawLine(127, 255 - this.r2, 255, 255 - this.r3);
         g.setColor(G);
         g.drawLine(0, 255 - this.g1, 127, 255 - this.g2);
         g.drawLine(127, 255 - this.g2, 255, 255 - this.g3);
         g.setColor(B);
         g.drawLine(0, 255 - this.b1, 127, 255 - this.b2);
         g.drawLine(127, 255 - this.b2, 255, 255 - this.b3);
       } else {
         g.drawLine(0, 128 - Math.round(126.0F * ColorTools.colorToNDVI(this.img1.getRGB(x[0].intValue(), y[0].intValue()))), 127, 128 - Math.round(126.0F * ColorTools.colorToNDVI(this.img2.getRGB(x[0].intValue(), y[0].intValue()))));
         g.drawLine(127, 128 - Math.round(126.0F * ColorTools.colorToNDVI(this.img2.getRGB(x[0].intValue(), y[0].intValue()))), 255, 128 - Math.round(126.0F * ColorTools.colorToNDVI(this.img3.getRGB(x[0].intValue(), y[0].intValue()))));
       }
       g.dispose();
     } else {
       Graphics2D g = this.img.createGraphics();
       g.setStroke(new BasicStroke(2.0F));
       g.setColor(R);
       g.drawLine(0, 255 - this.r1, 255, 255 - this.r2);
       if (this.RGB < 2) {
         g.setColor(G);
         g.drawLine(0, 255 - this.g1, 255, 255 - this.g2);
         g.setColor(B);
         g.drawLine(0, 255 - this.b1, 255, 255 - this.b2);
       }
       g.dispose();
     }
     repaint();
   }
 
   public void update2(Integer[] x, Integer[] y)
   {
     int[] rgb = ColorTools.rgb(this.img2.getRGB(x[0].intValue(), y[0].intValue()));
     this.r2 = rgb[0];
     this.g2 = rgb[1];
     this.b2 = rgb[2];
   }
 
   public void update3(Integer[] x, Integer[] y)
   {
     int[] rgb = ColorTools.rgb(this.img3.getRGB(x[0].intValue(), y[0].intValue()));
     this.r3 = rgb[0];
     this.g3 = rgb[1];
     this.b3 = rgb[2];
   }
 
   public void threePics()
   {
     this.three = true;
     this.middlePic.setVisible(true);
     this.middlePic.setText("Pic 2");
     this.endPic.setText("Pic 3");
     Graphics2D g = this.img.createGraphics();
     g.setColor(Color.GRAY);
     g.setStroke(new BasicStroke(1.0F));
     g.drawLine(127, 0, 127, 255);
     g.dispose();
     repaint();
   }
   private void setup() {
     this.txtColorIntensitiesAt = new JTextField();
     this.txtColorIntensitiesAt.setOpaque(false);
     this.txtColorIntensitiesAt.setBorder(null);
     this.txtColorIntensitiesAt.setEditable(false);
     this.txtColorIntensitiesAt.setHorizontalAlignment(0);
     this.txtColorIntensitiesAt.setText("Color Intensities at Selected Point on Images");
     this.txtColorIntensitiesAt.setBounds(100, 11, 250, 20);
     add(this.txtColorIntensitiesAt);
     this.txtColorIntensitiesAt.setColumns(10);
 
     this.textField = new JTextField();
     this.textField.setFont(new Font("Tahoma", 0, 10));
     this.textField.setBorder(null);
     this.textField.setOpaque(false);
     this.textField.setEditable(false);
     this.textField.setHorizontalAlignment(11);
     this.textField.setText("100%");
     this.textField.setBounds(50, 25, 40, 10);
     add(this.textField);
     this.textField.setColumns(10);
 
     this.textField_1 = new JTextField();
     this.textField_1.setText("75%");
     this.textField_1.setOpaque(false);
     this.textField_1.setHorizontalAlignment(11);
     this.textField_1.setFont(new Font("Tahoma", 0, 10));
     this.textField_1.setEditable(false);
     this.textField_1.setColumns(10);
     this.textField_1.setBorder(null);
     this.textField_1.setBounds(50, 87, 40, 10);
     add(this.textField_1);
 
     this.textField_2 = new JTextField();
     this.textField_2.setText("50%");
     this.textField_2.setOpaque(false);
     this.textField_2.setHorizontalAlignment(11);
     this.textField_2.setFont(new Font("Tahoma", 0, 10));
     this.textField_2.setEditable(false);
     this.textField_2.setColumns(10);
     this.textField_2.setBorder(null);
     this.textField_2.setBounds(50, 149, 40, 10);
     add(this.textField_2);
 
     this.textField_3 = new JTextField();
     this.textField_3.setText("0%");
     this.textField_3.setOpaque(false);
     this.textField_3.setHorizontalAlignment(11);
     this.textField_3.setFont(new Font("Tahoma", 0, 10));
     this.textField_3.setEditable(false);
     this.textField_3.setColumns(10);
     this.textField_3.setBorder(null);
     this.textField_3.setBounds(50, 273, 40, 10);
     add(this.textField_3);
 
     this.textField_4 = new JTextField();
     this.textField_4.setText("25%");
     this.textField_4.setOpaque(false);
     this.textField_4.setHorizontalAlignment(11);
     this.textField_4.setFont(new Font("Tahoma", 0, 10));
     this.textField_4.setEditable(false);
     this.textField_4.setColumns(10);
     this.textField_4.setBorder(null);
     this.textField_4.setBounds(50, 211, 40, 10);
     add(this.textField_4);
 
     this.txtPic = new JTextField();
     this.txtPic.setText("Pic 1");
     this.txtPic.setOpaque(false);
     this.txtPic.setHorizontalAlignment(0);
     this.txtPic.setFont(new Font("Tahoma", 0, 10));
     this.txtPic.setEditable(false);
     this.txtPic.setColumns(10);
     this.txtPic.setBorder(null);
     this.txtPic.setBounds(80, 285, 40, 10);
     add(this.txtPic);
 
     this.endPic = new JTextField();
     this.endPic.setText("Pic 2");
     this.endPic.setOpaque(false);
     this.endPic.setHorizontalAlignment(0);
     this.endPic.setFont(new Font("Tahoma", 0, 10));
     this.endPic.setEditable(false);
     this.endPic.setColumns(10);
     this.endPic.setBorder(null);
     this.endPic.setBounds(330, 285, 40, 10);
     add(this.endPic);
 
     this.middlePic = new JTextField();
     this.middlePic.setVisible(false);
     this.middlePic.setOpaque(false);
     this.middlePic.setHorizontalAlignment(0);
     this.middlePic.setFont(new Font("Tahoma", 0, 10));
     this.middlePic.setEditable(false);
     this.middlePic.setColumns(10);
     this.middlePic.setBorder(null);
     this.middlePic.setBounds(205, 285, 40, 10);
     add(this.middlePic);
   }
 }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.datapanels.PointGraphPanel
 * JD-Core Version:    0.6.2
 */