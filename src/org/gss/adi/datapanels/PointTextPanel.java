 package org.gss.adi.datapanels;
 
 import java.awt.Color;
 import java.awt.Font;
 import java.awt.image.BufferedImage;
 import java.text.DecimalFormat;
 import javax.swing.JTextField;
 import org.gss.adi.tools.ColorTools;
 
 public class PointTextPanel extends DataPanel
 {
   private static final long serialVersionUID = -6563156837975659150L;
   private JTextField txtImage;
   private JTextField txtRed;
   private JTextField txtGreen;
   private JTextField txtBlue;
   private JTextField pic1Red;
   private JTextField txtPic;
   private JTextField txtPic_1;
   private JTextField txtPic_2;
   private JTextField pic2Red;
   private JTextField pic3Red;
   private JTextField pic1Green;
   private JTextField pic2Green;
   private JTextField pic3Green;
   private JTextField pic3Blue;
   private JTextField pic1Blue;
   private JTextField pic2Blue;
   private JTextField txtAverage;
   private JTextField pic1Avg;
   private JTextField pic2Avg;
   private JTextField pic3Avg;
   private BufferedImage img1;
   private BufferedImage img2;
   private BufferedImage img3;
   private int RGB;
 
   public PointTextPanel(BufferedImage image1, BufferedImage image2, BufferedImage image3, int rgb)
   {
     super(image1, image2, image3, rgb);
     this.img1 = image1;
     this.img2 = image2;
     this.img3 = image3;
     this.RGB = rgb;
     setLayout(null);
     setup();
     if (rgb != 0) {
       this.pic1Red.setForeground(this.black1);
       this.pic1Green.setForeground(this.red1);
       this.pic1Blue.setForeground(this.green1);
       this.pic2Red.setForeground(this.black2);
       this.pic2Green.setForeground(this.red2);
       this.pic2Blue.setForeground(this.green2);
       this.pic3Red.setForeground(this.black3);
       this.pic3Green.setForeground(this.red3);
       this.pic3Blue.setForeground(this.green3);
       this.txtRed.setText("NIR");
       this.txtRed.setForeground(this.black1);
       this.txtGreen.setText("Red");
       this.txtGreen.setForeground(this.red1);
       this.txtBlue.setText("Green");
       this.txtBlue.setForeground(this.green1);
     }
     if (rgb == 2) {
       this.pic1Green.setVisible(false);
       this.pic1Blue.setVisible(false);
       this.txtGreen.setVisible(false);
       this.txtBlue.setVisible(false);
       this.pic2Green.setVisible(false);
       this.pic2Blue.setVisible(false);
       this.pic3Green.setVisible(false);
       this.pic3Blue.setVisible(false);
       this.pic1Avg.setVisible(false);
       this.pic2Avg.setVisible(false);
       this.pic3Avg.setVisible(false);
       this.txtRed.setText("NDVI Value");
       this.txtAverage.setVisible(false);
     }
   }
 
   public void threePics() { this.txtPic_2.setVisible(true);
     this.pic3Red.setVisible(true);
     this.pic3Green.setVisible(true);
     this.pic3Blue.setVisible(true);
     this.pic3Avg.setVisible(true);
     repaint(); }
 
   public void update1(Integer[] x, Integer[] y)
   {
     int[] rgb = ColorTools.rgb(this.img1.getRGB(x[0].intValue(), y[0].intValue()));
     if (this.RGB == 2) {
       float i = ColorTools.colorToNDVI(rgb);
       this.pic1Red.setText(this.df.format(i));
     } else {
       this.pic1Red.setText(new Integer(rgb[0]).toString());
       this.pic1Green.setText(new Integer(rgb[1]).toString());
       this.pic1Blue.setText(new Integer(rgb[2]).toString());
       this.pic1Avg.setText(this.df.format((rgb[0] + rgb[1] + rgb[2]) / 3.0D));
     }
   }
 
   public void update2(Integer[] x, Integer[] y) {
     int[] rgb = ColorTools.rgb(this.img2.getRGB(x[0].intValue(), y[0].intValue()));
     if (this.RGB == 2) {
       float i = ColorTools.colorToNDVI(rgb);
       this.pic2Red.setText(this.df.format(i));
     } else {
       this.pic2Red.setText(new Integer(rgb[0]).toString());
       this.pic2Green.setText(new Integer(rgb[1]).toString());
       this.pic2Blue.setText(new Integer(rgb[2]).toString());
       this.pic2Avg.setText(this.df.format((rgb[0] + rgb[1] + rgb[2]) / 3.0D));
     }
   }
 
   public void update3(Integer[] x, Integer[] y)
   {
     int[] rgb = ColorTools.rgb(this.img3.getRGB(x[0].intValue(), y[0].intValue()));
     if (this.RGB == 2) {
       float i = ColorTools.colorToNDVI(rgb);
       this.pic3Red.setText(this.df.format(i));
     } else {
       this.pic3Red.setText(new Integer(rgb[0]).toString());
       this.pic3Green.setText(new Integer(rgb[1]).toString());
       this.pic3Blue.setText(new Integer(rgb[2]).toString());
       this.pic3Avg.setText(this.df.format((rgb[0] + rgb[1] + rgb[2]) / 3.0D));
     }
   }
 
   private void setup() {
     this.txtImage = new JTextField();
     this.txtImage.setText("Image");
     this.txtImage.setBorder(null);
     this.txtImage.setOpaque(false);
     this.txtImage.setFont(new Font("SansSerif", 0, 13));
     this.txtImage.setEditable(false);
     this.txtImage.setBounds(10, 11, 50, 20);
     add(this.txtImage);
     this.txtImage.setColumns(10);
 
     this.txtRed = new JTextField();
     this.txtRed.setForeground(Color.RED);
     this.txtRed.setHorizontalAlignment(0);
     this.txtRed.setText("Red");
     this.txtRed.setOpaque(false);
     this.txtRed.setFont(new Font("SansSerif", 0, 13));
     this.txtRed.setEditable(false);
     this.txtRed.setColumns(10);
     this.txtRed.setBorder(null);
     this.txtRed.setBounds(70, 11, 67, 20);
     add(this.txtRed);
 
     this.txtGreen = new JTextField();
     this.txtGreen.setForeground(new Color(43520));
     this.txtGreen.setHorizontalAlignment(0);
     this.txtGreen.setText("Green");
     this.txtGreen.setOpaque(false);
     this.txtGreen.setFont(new Font("SansSerif", 0, 13));
     this.txtGreen.setEditable(false);
     this.txtGreen.setColumns(10);
     this.txtGreen.setBorder(null);
     this.txtGreen.setBounds(147, 11, 67, 20);
     add(this.txtGreen);
 
     this.txtBlue = new JTextField();
     this.txtBlue.setForeground(new Color(255));
     this.txtBlue.setHorizontalAlignment(0);
     this.txtBlue.setText("Blue");
     this.txtBlue.setOpaque(false);
     this.txtBlue.setFont(new Font("SansSerif", 0, 13));
     this.txtBlue.setEditable(false);
     this.txtBlue.setColumns(10);
     this.txtBlue.setBorder(null);
     this.txtBlue.setBounds(224, 10, 67, 20);
     add(this.txtBlue);
 
     this.pic1Red = new JTextField();
     this.pic1Red.setForeground(Color.RED);
     this.pic1Red.setHorizontalAlignment(0);
     this.pic1Red.setOpaque(false);
     this.pic1Red.setFont(new Font("SansSerif", 0, 13));
     this.pic1Red.setEditable(false);
     this.pic1Red.setColumns(10);
     this.pic1Red.setBorder(null);
     this.pic1Red.setBounds(70, 42, 67, 20);
     add(this.pic1Red);
 
     this.txtPic = new JTextField();
     this.txtPic.setText("Pic 1");
     this.txtPic.setOpaque(false);
     this.txtPic.setFont(new Font("SansSerif", 0, 13));
     this.txtPic.setEditable(false);
     this.txtPic.setColumns(10);
     this.txtPic.setBorder(null);
     this.txtPic.setBounds(10, 42, 50, 20);
     add(this.txtPic);
 
     this.txtPic_1 = new JTextField();
     this.txtPic_1.setForeground(new Color(5263440));
     this.txtPic_1.setText("Pic 2");
     this.txtPic_1.setOpaque(false);
     this.txtPic_1.setFont(new Font("SansSerif", 0, 13));
     this.txtPic_1.setEditable(false);
     this.txtPic_1.setColumns(10);
     this.txtPic_1.setBorder(null);
     this.txtPic_1.setBounds(10, 73, 50, 20);
     add(this.txtPic_1);
 
     this.txtPic_2 = new JTextField();
     this.txtPic_2.setVisible(false);
     this.txtPic_2.setForeground(new Color(10526880));
     this.txtPic_2.setText("Pic 3");
     this.txtPic_2.setOpaque(false);
     this.txtPic_2.setFont(new Font("SansSerif", 0, 13));
     this.txtPic_2.setEditable(false);
     this.txtPic_2.setColumns(10);
     this.txtPic_2.setBorder(null);
     this.txtPic_2.setBounds(10, 106, 50, 20);
     add(this.txtPic_2);
 
     this.pic2Red = new JTextField();
     this.pic2Red.setForeground(this.red2);
     this.pic2Red.setOpaque(false);
     this.pic2Red.setHorizontalAlignment(0);
     this.pic2Red.setFont(new Font("SansSerif", 0, 13));
     this.pic2Red.setEditable(false);
     this.pic2Red.setColumns(10);
     this.pic2Red.setBorder(null);
     this.pic2Red.setBounds(70, 74, 67, 20);
     add(this.pic2Red);
 
     this.pic3Red = new JTextField();
     this.pic3Red.setVisible(false);
     this.pic3Red.setForeground(this.red3);
     this.pic3Red.setOpaque(false);
     this.pic3Red.setHorizontalAlignment(0);
     this.pic3Red.setFont(new Font("SansSerif", 0, 13));
     this.pic3Red.setEditable(false);
     this.pic3Red.setColumns(10);
     this.pic3Red.setBorder(null);
     this.pic3Red.setBounds(70, 107, 67, 20);
     add(this.pic3Red);
 
     this.pic1Green = new JTextField();
     this.pic1Green.setForeground(this.green1);
     this.pic1Green.setOpaque(false);
     this.pic1Green.setHorizontalAlignment(0);
     this.pic1Green.setFont(new Font("SansSerif", 0, 13));
     this.pic1Green.setEditable(false);
     this.pic1Green.setColumns(10);
     this.pic1Green.setBorder(null);
     this.pic1Green.setBounds(147, 42, 67, 20);
     add(this.pic1Green);
 
     this.pic2Green = new JTextField();
     this.pic2Green.setForeground(this.green2);
     this.pic2Green.setOpaque(false);
     this.pic2Green.setHorizontalAlignment(0);
     this.pic2Green.setFont(new Font("SansSerif", 0, 13));
     this.pic2Green.setEditable(false);
     this.pic2Green.setColumns(10);
     this.pic2Green.setBorder(null);
     this.pic2Green.setBounds(147, 74, 67, 20);
     add(this.pic2Green);
 
     this.pic3Green = new JTextField();
     this.pic3Green.setVisible(false);
     this.pic3Green.setForeground(this.green3);
     this.pic3Green.setOpaque(false);
     this.pic3Green.setHorizontalAlignment(0);
     this.pic3Green.setFont(new Font("SansSerif", 0, 13));
     this.pic3Green.setEditable(false);
     this.pic3Green.setColumns(10);
     this.pic3Green.setBorder(null);
     this.pic3Green.setBounds(147, 107, 67, 20);
     add(this.pic3Green);
 
     this.pic3Blue = new JTextField();
     this.pic3Blue.setVisible(false);
     this.pic3Blue.setForeground(this.blue3);
     this.pic3Blue.setOpaque(false);
     this.pic3Blue.setHorizontalAlignment(0);
     this.pic3Blue.setFont(new Font("SansSerif", 0, 13));
     this.pic3Blue.setEditable(false);
     this.pic3Blue.setColumns(10);
     this.pic3Blue.setBorder(null);
     this.pic3Blue.setBounds(224, 106, 67, 20);
     add(this.pic3Blue);
 
     this.pic1Blue = new JTextField();
     this.pic1Blue.setForeground(Color.BLUE);
     this.pic1Blue.setOpaque(false);
     this.pic1Blue.setHorizontalAlignment(0);
     this.pic1Blue.setFont(new Font("SansSerif", 0, 13));
     this.pic1Blue.setEditable(false);
     this.pic1Blue.setColumns(10);
     this.pic1Blue.setBorder(null);
     this.pic1Blue.setBounds(224, 41, 67, 20);
     add(this.pic1Blue);
 
     this.pic2Blue = new JTextField();
     this.pic2Blue.setForeground(this.blue2);
     this.pic2Blue.setOpaque(false);
     this.pic2Blue.setHorizontalAlignment(0);
     this.pic2Blue.setFont(new Font("SansSerif", 0, 13));
     this.pic2Blue.setEditable(false);
     this.pic2Blue.setColumns(10);
     this.pic2Blue.setBorder(null);
     this.pic2Blue.setBounds(224, 73, 67, 20);
     add(this.pic2Blue);
 
     this.txtAverage = new JTextField();
     this.txtAverage.setText("Average");
     this.txtAverage.setOpaque(false);
     this.txtAverage.setHorizontalAlignment(0);
     this.txtAverage.setFont(new Font("SansSerif", 0, 13));
     this.txtAverage.setEditable(false);
     this.txtAverage.setColumns(10);
     this.txtAverage.setBorder(null);
     this.txtAverage.setBounds(301, 10, 67, 20);
     add(this.txtAverage);
 
     this.pic1Avg = new JTextField();
     this.pic1Avg.setOpaque(false);
     this.pic1Avg.setHorizontalAlignment(0);
     this.pic1Avg.setFont(new Font("SansSerif", 0, 13));
     this.pic1Avg.setEditable(false);
     this.pic1Avg.setColumns(10);
     this.pic1Avg.setBorder(null);
     this.pic1Avg.setBounds(301, 41, 67, 20);
     add(this.pic1Avg);
 
     this.pic2Avg = new JTextField();
     this.pic2Avg.setForeground(new Color(5263440));
     this.pic2Avg.setOpaque(false);
     this.pic2Avg.setHorizontalAlignment(0);
     this.pic2Avg.setFont(new Font("SansSerif", 0, 13));
     this.pic2Avg.setEditable(false);
     this.pic2Avg.setColumns(10);
     this.pic2Avg.setBorder(null);
     this.pic2Avg.setBounds(301, 73, 67, 20);
     add(this.pic2Avg);
 
     this.pic3Avg = new JTextField();
     this.pic3Avg.setVisible(false);
     this.pic3Avg.setForeground(new Color(8421504));
     this.pic3Avg.setOpaque(false);
     this.pic3Avg.setHorizontalAlignment(0);
     this.pic3Avg.setFont(new Font("SansSerif", 0, 13));
     this.pic3Avg.setEditable(false);
     this.pic3Avg.setColumns(10);
     this.pic3Avg.setBorder(null);
     this.pic3Avg.setBounds(301, 106, 67, 20);
     add(this.pic3Avg);
   }
 }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.datapanels.PointTextPanel
 * JD-Core Version:    0.6.2
 */