 package org.gss.adi;
 
 import java.awt.Font;
 import java.awt.Rectangle;
 import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

 
 public abstract class ImagePanel extends JPanel
   implements Updatable
 {
   private static final long serialVersionUID = -5463770685053754023L;
   private JTextField textField;
   private Entrance entrance;
   private BufferedImage manipulatedImage;
   private JTextArea textArea;
   private JTextArea textArea_1;
   private JTextArea textArea_2;
   final byte SPATIAL_ANALYSIS = 0;
   final byte ENHANCE_COLORS = 1;
   final byte MASK_COLORS = 2;
   final byte ABOUT = 3;
   private String fileName = "";
 
   JSlider slider = new JSlider();
   ZoomPanLabel label = new ZoomPanLabel(this.slider);
   
   protected static ZoomMemory zoomMemory = new ZoomMemory();
 
   public ImagePanel(Entrance e, boolean overview)
   {
	   try
	     {
	       setLayout(null);
	       this.entrance = e;
	       if ((this.entrance.getImage() == null) && (!overview)) {
	         File f = this.entrance.openImage("Open an image");
	         try {
	           BufferedImage img = ImageIO.read(f);
	           this.entrance.setImageTrim(img);
	           //this.fileName = f.getName();
	          } catch (Exception e1) {
	           e1.printStackTrace();
	        	 String s = "No image has been selected.\nCannot ";
	           switch (getType()) {
	           case 0:
	             s = s + "perform spatial analysis"; break;
	           case 1:
	             s = s + "enhance colors"; break;
	           case 2:
	             s = s + "mask colors";
	           }
	           JOptionPane.showMessageDialog(null, s + " without an image.", null, -1);
	           return;
	         }
	       }
	       this.entrance.setTitle(this.entrance.getFilename() + " is " + new Integer(this.entrance.getImage().getWidth()).toString() + " by " + new Integer(this.entrance.getImage().getHeight()).toString() + " pixels");
	       setup();
	       if (!overview) {
	         this.textField.setText(this.entrance.getTitle());
	         this.textField.repaint();
	       }
	       else {
	         remove(this.textField);
	         remove(this.slider);
	         remove(this.textArea);
	         remove(this.textArea_1);
	         remove(this.textArea_2);
	       }
	       this.entrance.setPane(this); } catch (OutOfMemoryError err) {
	       JOptionPane.showMessageDialog(null, "Java out of memory error. Please use a smaller image or increase\nthe ram allocated to java applications");
	     }
   }
 
   public void updatePic() { setImage(this.entrance.getImage()); }
 
   void zoomFromMemory(ZoomMemory z){
		int f = (int)Math.round((z.getZoomFactor() * 100));
		this.slider.setValue(f);
		this.label.qualityZoom(f);
		this.label.getViewport().setViewPosition(new Point(z.getX(),z.getY()));
		this.textArea.setText("Magnification: " + f + "%");
	}
   
   abstract byte getType();
 
   protected abstract void closingSequence();
 
   public void setImage(BufferedImage img)
   {
	 this.entrance.setTitle(this.entrance.getFilename() + " is " + new Integer(img.getWidth()).toString() + " by " + new Integer(img.getHeight()).toString() + " pixels");
	 this.manipulatedImage = img;
     this.label.setVerticalAlignment(1);
     this.label.setImage(img);
   }
   BufferedImage getManipulatedImage() {
     return this.manipulatedImage;
   }
 
   void slide()
   {
     Integer z = Integer.valueOf(this.slider.getValue());
     try { this.label.zoom(z.intValue()); this.textArea.setText("Magnification: " + z.toString() + "%");
     } catch (Exception localException) {
     }
   }
 
   void slideComplete() {
     Integer z = Integer.valueOf(this.slider.getValue());
     this.label.qualityZoom(z.intValue());
     this.textArea.setText("Magnification: " + z.toString() + "%");
   }
   private void setup() {
     JButton button = new JButton("Spatial Analysis");
     button.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent arg0) {
         new SpatialAnalysisPanel(ImagePanel.this.entrance);
       }
     });
     button.setBounds(337, 11, 158, 30);
     add(button);
 
     JButton button_1 = new JButton("Enhance Colors");
     button_1.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         new EnhanceColorsPanel(ImagePanel.this.entrance);
       }
     });
     button_1.setBounds(497, 11, 158, 30);
     add(button_1);
 
     JButton button_2 = new JButton("Mask Colors");
     button_2.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         new MaskColorsPanel(ImagePanel.this.entrance);
       }
     });
     button_2.setBounds(657, 11, 158, 30);
     add(button_2);
 
     this.label.setBounds(337, 41, 640, 480);
     add(this.label);
 
     JButton button_3 = new JButton("Time Series");
     button_3.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         ImagePanel.this.entrance.launchTimeSeries();
       }
     });
     button_3.setBounds(817, 11, 158, 30);
     add(button_3);
 
     this.textField = new JTextField();
     this.textField.setFont(new Font("SansSerif", 0, 13));
     this.textField.setEditable(false);
     this.textField.setColumns(10);
     this.textField.setBorder(null);
     this.textField.setOpaque(false);
     this.textField.setBounds(337, 531, 640, 20);
     add(this.textField);
 
     this.slider.setMajorTickSpacing(1);
     this.slider.setSnapToTicks(true);
     this.slider.setMinimum(100);
     this.slider.setMaximum(200);
     this.slider.setValue(100);
     this.slider.setBounds(411, 552, 250, 23);
     this.slider.addChangeListener(new ChangeListener()
     {
       public void stateChanged(ChangeEvent e) {
         ImagePanel.this.slide();
       }
     });
     this.slider.addMouseListener(new MouseAdapter()
     {
       public void mouseReleased(MouseEvent e) {
         ImagePanel.this.slideComplete();
       }
     });
     add(this.slider);
 
     this.textArea = new JTextArea();
     this.textArea.setWrapStyleWord(true);
     this.textArea.setText("Magnification: 100%");
     this.textArea.setOpaque(false);
     this.textArea.setLineWrap(true);
     this.textArea.setFont(new Font("SansSerif", 0, 12));
     this.textArea.setEditable(false);
     this.textArea.setBounds(463, 575, 136, 22);
     add(this.textArea);
 
     this.textArea_1 = new JTextArea();
     this.textArea_1.setWrapStyleWord(true);
     this.textArea_1.setText("Zoom out");
     this.textArea_1.setOpaque(false);
     this.textArea_1.setLineWrap(true);
     this.textArea_1.setFont(new Font("SansSerif", 0, 12));
     this.textArea_1.setEditable(false);
     this.textArea_1.setBounds(347, 575, 118, 45);
     add(this.textArea_1);
 
     this.textArea_2 = new JTextArea();
     this.textArea_2.setWrapStyleWord(true);
     this.textArea_2.setText("Zoom in");
     this.textArea_2.setOpaque(false);
     this.textArea_2.setLineWrap(true);
     this.textArea_2.setFont(new Font("SansSerif", 0, 12));
     this.textArea_2.setEditable(false);
     this.textArea_2.setBounds(599, 575, 118, 45);
     add(this.textArea_2);
   }
 }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.ImagePanel
 * JD-Core Version:    0.6.2
 */