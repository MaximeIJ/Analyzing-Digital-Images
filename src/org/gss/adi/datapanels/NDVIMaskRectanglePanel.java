 package org.gss.adi.datapanels;
 
 import java.awt.BasicStroke;
 import java.awt.Color;
 import java.awt.Font;
 import java.awt.Graphics2D;
 import java.awt.image.BufferedImage;
 import java.text.DecimalFormat;
 import javax.swing.ImageIcon;
 import javax.swing.JLabel;
 import javax.swing.JSpinner;
 import javax.swing.JTextField;
 import javax.swing.SpinnerListModel;
 import javax.swing.event.ChangeEvent;
 import javax.swing.event.ChangeListener;
 import org.gss.adi.tools.ColorTools;
 
 public class NDVIMaskRectanglePanel extends DataPanel
 {
   private static final long serialVersionUID = -6420696208280652556L;
   private JTextField textField;
   private JTextField textField_1;
   private JTextField textField_2;
   private JTextField textField_3;
   private JTextField textField_4;
   private JTextField textField_5;
   private JTextField textField_6;
   private JTextField pic1;
   private JTextField pic2;
   private JTextField pic3;
   int pixInRange1 = 0;
   int pixInRange2 = 0;
   int pixInRange3 = 0;
   int totalPix = 0;
   BufferedImage img1;
   BufferedImage img2;
   BufferedImage img3;
   BufferedImage img;
   Float min = Float.valueOf(-1.0F);
   Float max = Float.valueOf(1.0F);
 
   boolean three = true;
   private JLabel label;
   Integer[] X;
   Integer[] Y;
   private JSpinner NDVIMax;
   private JSpinner NDVIMin;
 
   public NDVIMaskRectanglePanel(BufferedImage image1, BufferedImage image2, BufferedImage image3, int rgb)
   {
     super(image1, image2, image3, rgb);
     setLayout(null);
     setup();
     this.img1 = image1;
     this.img2 = image2;
     this.img3 = image3;
     this.three = (image3 != null);
     drawAxis();
     repaint();
     this.NDVIMin.repaint();
     this.NDVIMax.repaint();
 
     add(this.NDVIMin);
     add(this.NDVIMax);
     this.NDVIMin.setValue(this.df.format(-1.0D));
     this.NDVIMax.setValue(this.df.format(1.0D));
   }
   public void clear() {
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
     repaint();
   }
   public void draw1() {
     Graphics2D g = this.img.createGraphics();
     g.setColor(this.black1);
     if (this.three) {
       g.drawLine(0, 255 - Math.round(255.0F * this.pixInRange1 / this.totalPix), 127, 255 - Math.round(255.0F * this.pixInRange2 / this.totalPix));
       g.drawLine(128, 255 - Math.round(255.0F * this.pixInRange2 / this.totalPix), 255, 255 - Math.round(255.0F * this.pixInRange3 / this.totalPix));
     } else {
       g.drawLine(0, 255 - Math.round(255.0F * this.pixInRange1 / this.totalPix), 255, 255 - Math.round(255.0F * this.pixInRange2 / this.totalPix));
     }
     g.dispose();
     repaint();
     this.label.setIcon(new ImageIcon(this.img));
     this.label.repaint();
   }
 
   public void update1(Integer[] x, Integer[] y)
   {
     this.X = x;
     this.Y = y;
     this.totalPix = 0;
     this.pixInRange1 = 0;
     this.pixInRange2 = 0;
     this.pixInRange3 = 0;
     int xmin;
     int xmax;
     if (x[1].intValue() >= x[0].intValue()) {
       xmax = x[1].intValue();
       xmin = x[0].intValue();
     } else {
       xmax = x[0].intValue();
       xmin = x[1].intValue();
     }
     int ymin;
     int ymax;
     if (y[1].intValue() >= y[0].intValue()) {
       ymax = y[1].intValue();
       ymin = y[0].intValue();
     } else {
       ymax = y[0].intValue();
       ymin = y[1].intValue();
     }
     for (int i = xmin; i < xmax; i++) {
       for (int j = ymin; j < ymax; j++) {
         this.totalPix += 1;
         Float min = new Float((String)this.NDVIMin.getValue());
         Float max = new Float((String)this.NDVIMax.getValue());
         float ndvi = ColorTools.colorToNDVI(this.img1.getRGB(i, j));
         if ((ndvi >= min.floatValue()) && (ndvi <= max.floatValue())) {
           this.pixInRange1 += 1;
         }
         ndvi = ColorTools.colorToNDVI(this.img2.getRGB(i, j));
 
         if ((ndvi >= min.floatValue()) && (ndvi <= max.floatValue())) {
           this.pixInRange2 += 1;
         }
         if (this.three) {
           ndvi = ColorTools.colorToNDVI(this.img3.getRGB(i, j));
           if ((ndvi >= min.floatValue()) && (ndvi <= max.floatValue())) {
             this.pixInRange3 += 1;
           }
         }
       }
     }
     clear();
     draw1();
   }
 
   public void update2(Integer[] x, Integer[] y) {
   }
 
   public void update3(Integer[] x, Integer[] y) {
   }
 
   public void threePics() {
     this.pic3.setText("Pic 3");
     this.pic2.setVisible(true);
   }
   public void setMinMax(Float Min, Float Max) {
     this.min = Min;
     this.max = Max;
     this.NDVIMin.setValue(this.df.format(this.min));
     this.NDVIMax.setValue(this.df.format(this.max));
     update1(this.X, this.Y);
   }
   private void setup() {
     this.label = new JLabel();
     this.label.setBounds(73, 19, 255, 255);
     add(this.label);
 
     this.textField = new JTextField();
     this.textField.setText("Relative Frequency of Colors Within Selected Area");
     this.textField.setOpaque(false);
     this.textField.setHorizontalAlignment(0);
     this.textField.setFont(new Font("Tahoma", 0, 10));
     this.textField.setEditable(false);
     this.textField.setColumns(10);
     this.textField.setBorder(null);
     this.textField.setBounds(73, 0, 265, 20);
     add(this.textField);
 
     this.textField_1 = new JTextField();
     this.textField_1.setText("Frequency");
     this.textField_1.setHorizontalAlignment(0);
     this.textField_1.setForeground(Color.MAGENTA);
     this.textField_1.setFont(new Font("Tahoma", 0, 9));
     this.textField_1.setEditable(false);
     this.textField_1.setColumns(10);
     this.textField_1.setBorder(null);
     this.textField_1.setBounds(10, -1, 53, 16);
     add(this.textField_1);
 
     this.textField_2 = new JTextField();
     this.textField_2.setText("100%");
     this.textField_2.setOpaque(false);
     this.textField_2.setHorizontalAlignment(11);
     this.textField_2.setFont(new Font("Tahoma", 0, 10));
     this.textField_2.setEditable(false);
     this.textField_2.setColumns(10);
     this.textField_2.setBorder(null);
     this.textField_2.setBounds(23, 15, 40, 10);
     add(this.textField_2);
 
     this.textField_3 = new JTextField();
     this.textField_3.setText("75%");
     this.textField_3.setOpaque(false);
     this.textField_3.setHorizontalAlignment(11);
     this.textField_3.setFont(new Font("Tahoma", 0, 10));
     this.textField_3.setEditable(false);
     this.textField_3.setColumns(10);
     this.textField_3.setBorder(null);
     this.textField_3.setBounds(23, 77, 40, 10);
     add(this.textField_3);
 
     this.textField_4 = new JTextField();
     this.textField_4.setText("50%");
     this.textField_4.setOpaque(false);
     this.textField_4.setHorizontalAlignment(11);
     this.textField_4.setFont(new Font("Tahoma", 0, 10));
     this.textField_4.setEditable(false);
     this.textField_4.setColumns(10);
     this.textField_4.setBorder(null);
     this.textField_4.setBounds(23, 139, 40, 10);
     add(this.textField_4);
 
     this.textField_5 = new JTextField();
     this.textField_5.setText("25%");
     this.textField_5.setOpaque(false);
     this.textField_5.setHorizontalAlignment(11);
     this.textField_5.setFont(new Font("Tahoma", 0, 10));
     this.textField_5.setEditable(false);
     this.textField_5.setColumns(10);
     this.textField_5.setBorder(null);
     this.textField_5.setBounds(23, 201, 40, 10);
     add(this.textField_5);
 
     this.textField_6 = new JTextField();
     this.textField_6.setText("0%");
     this.textField_6.setOpaque(false);
     this.textField_6.setHorizontalAlignment(11);
     this.textField_6.setFont(new Font("Tahoma", 0, 10));
     this.textField_6.setEditable(false);
     this.textField_6.setColumns(10);
     this.textField_6.setBorder(null);
     this.textField_6.setBounds(23, 263, 40, 10);
     add(this.textField_6);
 
     this.pic1 = new JTextField();
     this.pic1.setText("Pic 1");
     this.pic1.setOpaque(false);
     this.pic1.setHorizontalAlignment(0);
     this.pic1.setFont(new Font("Tahoma", 0, 10));
     this.pic1.setEditable(false);
     this.pic1.setColumns(10);
     this.pic1.setBorder(null);
     this.pic1.setBounds(58, 275, 30, 15);
     add(this.pic1);
 
     this.pic2 = new JTextField();
     this.pic2.setVisible(false);
     this.pic2.setText("Pic 2");
     this.pic2.setOpaque(false);
     this.pic2.setHorizontalAlignment(0);
     this.pic2.setFont(new Font("Tahoma", 0, 10));
     this.pic2.setEditable(false);
     this.pic2.setColumns(10);
     this.pic2.setBorder(null);
     this.pic2.setBounds(186, 275, 30, 15);
     add(this.pic2);
 
     this.pic3 = new JTextField();
     this.pic3.setText("Pic 2");
     this.pic3.setOpaque(false);
     this.pic3.setHorizontalAlignment(0);
     this.pic3.setFont(new Font("Tahoma", 0, 10));
     this.pic3.setEditable(false);
     this.pic3.setColumns(10);
     this.pic3.setBorder(null);
     this.pic3.setBounds(313, 275, 30, 15);
     add(this.pic3);
 
     this.NDVIMax = new JSpinner();
     this.NDVIMax.addChangeListener(new ChangeListener() {
       public void stateChanged(ChangeEvent arg0) {
         try { NDVIMaskRectanglePanel.this.update1(NDVIMaskRectanglePanel.this.X, NDVIMaskRectanglePanel.this.Y); }
         catch (Exception localException)
         {
         }
       }
     });
     String[] s = new String['Ê'];
     for (Float i = Float.valueOf(-1.0F); i.floatValue() <= 1.0F; i = Float.valueOf(i.floatValue() + 0.01F)) {
       s[Math.round(100.0F + i.floatValue() * 100.0F)] = this.df.format(i);
     }
     s[100] = "0";
     this.NDVIMax.setModel(new SpinnerListModel(s));
     this.NDVIMax.setBounds(345, 75, 78, 20);
     add(this.NDVIMax);
 
     this.NDVIMin = new JSpinner();
     this.NDVIMin.addChangeListener(new ChangeListener() {
       public void stateChanged(ChangeEvent arg0) {
         try { NDVIMaskRectanglePanel.this.update1(NDVIMaskRectanglePanel.this.X, NDVIMaskRectanglePanel.this.Y); }
         catch (Exception localException)
         {
         }
       }
     });
     this.NDVIMin.setModel(new SpinnerListModel(s));
     this.NDVIMin.setBounds(345, 129, 78, 20);
     add(this.NDVIMin);
 
     JTextField txtNdviMax = new JTextField();
     txtNdviMax.setBorder(null);
     txtNdviMax.setEditable(false);
     txtNdviMax.setFont(new Font("SansSerif", 0, 10));
     txtNdviMax.setText("NDVI Max");
     txtNdviMax.setBounds(338, 58, 86, 15);
     add(txtNdviMax);
     txtNdviMax.setColumns(10);
 
     JTextField txtNdviMin = new JTextField();
     txtNdviMin.setText("NDVI Min");
     txtNdviMin.setFont(new Font("SansSerif", 0, 10));
     txtNdviMin.setEditable(false);
     txtNdviMin.setColumns(10);
     txtNdviMin.setBorder(null);
     txtNdviMin.setBounds(338, 113, 86, 15);
     add(txtNdviMin);
   }
 }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.datapanels.NDVIMaskRectanglePanel
 * JD-Core Version:    0.6.2
 */