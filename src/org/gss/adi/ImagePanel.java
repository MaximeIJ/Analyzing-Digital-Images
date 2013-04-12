/*     */ package org.gss.adi;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import javax.imageio.ImageIO;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JSlider;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
/*     */ 
/*     */ public abstract class ImagePanel extends JPanel
/*     */   implements Updatable
/*     */ {
/*     */   private static final long serialVersionUID = -5463770685053754023L;
/*     */   private JTextField textField;
/*     */   private Entrance entrance;
/*     */   private BufferedImage manipulatedImage;
/*     */   private JTextArea textArea;
/*     */   private JTextArea textArea_1;
/*     */   private JTextArea textArea_2;
/*  40 */   final byte SPATIAL_ANALYSIS = 0;
/*  41 */   final byte ENHANCE_COLORS = 1;
/*  42 */   final byte MASK_COLORS = 2;
/*  43 */   final byte ABOUT = 3;
/*     */ 
/*  45 */   JSlider slider = new JSlider();
/*  46 */   ZoomPanLabel label = new ZoomPanLabel(this.slider);
/*     */ 
/*     */   public ImagePanel(Entrance e, boolean overview)
/*     */   {
/*     */     try
/*     */     {
/*  53 */       setLayout(null);
/*  54 */       this.entrance = e;
/*  55 */       if ((this.entrance.getImage() == null) && (!overview)) {
/*  56 */         File f = this.entrance.openImage("Open an image");
/*     */         try {
/*  58 */           BufferedImage img = ImageIO.read(f);
/*  59 */           this.entrance.setImage(img);
/*  60 */           this.entrance.setTitle(f.getName() + " is " + new Integer(img.getWidth()).toString() + " by " + new Integer(img.getHeight()).toString() + " pixels");
/*     */         } catch (Exception e1) {
/*  62 */           String s = "No image has been selected.\nCannot ";
/*  63 */           switch (getType()) {
/*     */           case 0:
/*  65 */             s = s + "perform spatial analysis"; break;
/*     */           case 1:
/*  67 */             s = s + "enhance colors"; break;
/*     */           case 2:
/*  69 */             s = s + "mask colors";
/*     */           }
/*  71 */           JOptionPane.showMessageDialog(null, s + " without an image.", null, -1);
/*  72 */           return;
/*     */         }
/*     */       }
/*  75 */       setup();
/*  76 */       if (!overview) {
/*  77 */         this.textField.setText(this.entrance.getTitle());
/*  78 */         this.textField.repaint();
/*     */       }
/*     */       else {
/*  81 */         remove(this.textField);
/*  82 */         remove(this.slider);
/*  83 */         remove(this.textArea);
/*  84 */         remove(this.textArea_1);
/*  85 */         remove(this.textArea_2);
/*     */       }
/*  87 */       this.entrance.setPane(this); } catch (OutOfMemoryError err) {
/*  88 */       JOptionPane.showMessageDialog(null, "Java out of memory error. Please use a smaller image or increase\nthe ram allocated to java applications");
/*     */     }
/*     */   }
/*     */ 
/*  92 */   public void updatePic() { setImage(this.entrance.getImage()); }
/*     */ 
/*     */ 
/*     */   abstract byte getType();
/*     */ 
/*     */   protected abstract void closingSequence();
/*     */ 
/*     */   public void setImage(BufferedImage img)
/*     */   {
/* 106 */     this.manipulatedImage = img;
/* 107 */     this.label.setVerticalAlignment(1);
/* 108 */     this.label.setImage(img);
/*     */   }
/*     */   BufferedImage getManipulatedImage() {
/* 111 */     return this.manipulatedImage;
/*     */   }
/*     */ 
/*     */   void slide()
/*     */   {
/* 117 */     Integer z = Integer.valueOf(this.slider.getValue());
/*     */     try { this.label.zoom(z.intValue()); this.textArea.setText("Magnification: " + z.toString() + "%");
/*     */     } catch (Exception localException) {
/*     */     }
/*     */   }
/*     */ 
/*     */   void slideComplete() {
/* 124 */     Integer z = Integer.valueOf(this.slider.getValue());
/* 125 */     this.label.qualityZoom(z.intValue());
/* 126 */     this.textArea.setText("Magnification: " + z.toString() + "%");
/*     */   }
/*     */   private void setup() {
/* 129 */     JButton button = new JButton("Spatial Analysis");
/* 130 */     button.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent arg0) {
/* 132 */         new SpatialAnalysisPanel(ImagePanel.this.entrance);
/*     */       }
/*     */     });
/* 135 */     button.setBounds(337, 11, 158, 30);
/* 136 */     add(button);
/*     */ 
/* 138 */     JButton button_1 = new JButton("Enhance Colors");
/* 139 */     button_1.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 141 */         new EnhanceColorsPanel(ImagePanel.this.entrance);
/*     */       }
/*     */     });
/* 144 */     button_1.setBounds(497, 11, 158, 30);
/* 145 */     add(button_1);
/*     */ 
/* 147 */     JButton button_2 = new JButton("Mask Colors");
/* 148 */     button_2.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 150 */         new MaskColorsPanel(ImagePanel.this.entrance);
/*     */       }
/*     */     });
/* 153 */     button_2.setBounds(657, 11, 158, 30);
/* 154 */     add(button_2);
/*     */ 
/* 156 */     this.label.setBounds(337, 41, 640, 480);
/* 157 */     add(this.label);
/*     */ 
/* 159 */     JButton button_3 = new JButton("Time Series");
/* 160 */     button_3.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 162 */         ImagePanel.this.entrance.launchTimeSeries();
/*     */       }
/*     */     });
/* 165 */     button_3.setBounds(817, 11, 158, 30);
/* 166 */     add(button_3);
/*     */ 
/* 168 */     this.textField = new JTextField();
/* 169 */     this.textField.setFont(new Font("SansSerif", 0, 13));
/* 170 */     this.textField.setEditable(false);
/* 171 */     this.textField.setColumns(10);
/* 172 */     this.textField.setBorder(null);
/* 173 */     this.textField.setOpaque(false);
/* 174 */     this.textField.setBounds(337, 531, 640, 20);
/* 175 */     add(this.textField);
/*     */ 
/* 177 */     this.slider.setMajorTickSpacing(1);
/* 178 */     this.slider.setSnapToTicks(true);
/* 179 */     this.slider.setMinimum(100);
/* 180 */     this.slider.setMaximum(200);
/* 181 */     this.slider.setValue(100);
/* 182 */     this.slider.setBounds(411, 552, 250, 23);
/* 183 */     this.slider.addChangeListener(new ChangeListener()
/*     */     {
/*     */       public void stateChanged(ChangeEvent e) {
/* 186 */         ImagePanel.this.slide();
/*     */       }
/*     */     });
/* 189 */     this.slider.addMouseListener(new MouseAdapter()
/*     */     {
/*     */       public void mouseReleased(MouseEvent e) {
/* 192 */         ImagePanel.this.slideComplete();
/*     */       }
/*     */     });
/* 195 */     add(this.slider);
/*     */ 
/* 197 */     this.textArea = new JTextArea();
/* 198 */     this.textArea.setWrapStyleWord(true);
/* 199 */     this.textArea.setText("Magnification: 100%");
/* 200 */     this.textArea.setOpaque(false);
/* 201 */     this.textArea.setLineWrap(true);
/* 202 */     this.textArea.setFont(new Font("SansSerif", 0, 12));
/* 203 */     this.textArea.setEditable(false);
/* 204 */     this.textArea.setBounds(463, 575, 136, 22);
/* 205 */     add(this.textArea);
/*     */ 
/* 207 */     this.textArea_1 = new JTextArea();
/* 208 */     this.textArea_1.setWrapStyleWord(true);
/* 209 */     this.textArea_1.setText("Zoom out");
/* 210 */     this.textArea_1.setOpaque(false);
/* 211 */     this.textArea_1.setLineWrap(true);
/* 212 */     this.textArea_1.setFont(new Font("SansSerif", 0, 12));
/* 213 */     this.textArea_1.setEditable(false);
/* 214 */     this.textArea_1.setBounds(347, 575, 118, 45);
/* 215 */     add(this.textArea_1);
/*     */ 
/* 217 */     this.textArea_2 = new JTextArea();
/* 218 */     this.textArea_2.setWrapStyleWord(true);
/* 219 */     this.textArea_2.setText("Zoom in");
/* 220 */     this.textArea_2.setOpaque(false);
/* 221 */     this.textArea_2.setLineWrap(true);
/* 222 */     this.textArea_2.setFont(new Font("SansSerif", 0, 12));
/* 223 */     this.textArea_2.setEditable(false);
/* 224 */     this.textArea_2.setBounds(599, 575, 118, 45);
/* 225 */     add(this.textArea_2);
/*     */   }
/*     */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.ImagePanel
 * JD-Core Version:    0.6.2
 */