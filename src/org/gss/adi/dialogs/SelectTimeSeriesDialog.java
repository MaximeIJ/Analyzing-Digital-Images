/*     */ package org.gss.adi.dialogs;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Container;
/*     */ import java.awt.FlowLayout;
/*     */ import java.awt.Font;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import javax.imageio.ImageIO;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ import org.gss.adi.Entrance;
/*     */ import org.gss.adi.TimeSeriesPanel;
/*     */ 
/*     */ public class SelectTimeSeriesDialog extends JDialog
/*     */ {
/*     */   private static final long serialVersionUID = -4037973337791171284L;
/*  27 */   private final JPanel contentPanel = new JPanel();
/*     */   private Entrance entrance;
/*     */   private JDialog me;
/*  31 */   private final byte R_G_B = 0;
/*  32 */   private final byte NIR_R_G = 1;
/*     */ 
/*     */   public SelectTimeSeriesDialog(Entrance e)
/*     */   {
/*  38 */     setModal(true);
/*  39 */     this.me = this;
/*  40 */     this.entrance = e;
/*  41 */     setup();
/*     */   }
/*     */   private void success(boolean three) {
/*  44 */     dispose();
/*  45 */     new CalibratePixelDialog(this.entrance, Boolean.valueOf(true)).setVisible(true);
/*  46 */     new TimeSeriesPanel(this.entrance, three, this.entrance.getTimeSeriesType());
/*     */   }
/*     */   private String createTitle(File f, BufferedImage img) {
/*  49 */     return f.getName() + " is " + img.getWidth() + " by " + img.getHeight() + " pixels";
/*     */   }
/*     */   private void setup() {
/*  52 */     setTitle("Select the number and type of time series images to open");
/*  53 */     setBounds(100, 100, 747, 557);
/*  54 */     getContentPane().setLayout(new BorderLayout());
/*  55 */     this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
/*  56 */     getContentPane().add(this.contentPanel, "Center");
/*  57 */     this.contentPanel.setLayout(null);
/*     */ 
/*  59 */     JTextArea txtrOpenOr = new JTextArea();
/*  60 */     txtrOpenOr.setOpaque(false);
/*  61 */     txtrOpenOr.setFont(new Font("SansSerif", 0, 13));
/*  62 */     txtrOpenOr.setText("Open 2 or 3 images from a time series that are the same size (width and height in pixels). The maximum width is 1024 pixels and the maximum height is 768 pixels.\r\n\r\nTip: Use the Trim Function in the Utility Menu above if your images are too large. Trim option 1 or 2 will work best.");
/*  63 */     txtrOpenOr.setWrapStyleWord(true);
/*  64 */     txtrOpenOr.setLineWrap(true);
/*  65 */     txtrOpenOr.setEditable(false);
/*  66 */     txtrOpenOr.setBounds(10, 11, 721, 98);
/*  67 */     this.contentPanel.add(txtrOpenOr);
/*     */ 
/*  69 */     JButton btnNewButton = new JButton("Open 2 Photos/Maps");
/*  70 */     btnNewButton.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent a) {
/*  72 */         SelectTimeSeriesDialog.this.me.setVisible(false);
/*     */         try {
/*  74 */           File f = SelectTimeSeriesDialog.this.entrance.openImage("Open the first time series image");
/*  75 */           BufferedImage img1 = ImageIO.read(f);
/*  76 */           String title1 = SelectTimeSeriesDialog.this.createTitle(f, img1);
/*  77 */           f = SelectTimeSeriesDialog.this.entrance.openImage("Open the second time series image");
/*  78 */           BufferedImage img2 = ImageIO.read(f);
/*  79 */           SelectTimeSeriesDialog.this.entrance.setTimeSeries3(null);
/*  80 */           SelectTimeSeriesDialog.this.entrance.setTitle3("");
/*  81 */           if ((img1.getWidth() == img2.getWidth()) && (img1.getHeight() == img2.getHeight())) {
/*  82 */             SelectTimeSeriesDialog.this.entrance.setTitle1(title1);
/*  83 */             SelectTimeSeriesDialog.this.entrance.setTimeSeries1(img1);
/*  84 */             SelectTimeSeriesDialog.this.entrance.setTitle2(SelectTimeSeriesDialog.this.createTitle(f, img2));
/*  85 */             SelectTimeSeriesDialog.this.entrance.setTimeSeries2(img2);
/*  86 */             SelectTimeSeriesDialog.this.entrance.setTimeSeriesType((byte)0);
/*  87 */             SelectTimeSeriesDialog.this.success(false);
/*     */           } else {
/*  89 */             JOptionPane.showMessageDialog(null, "All images in a time series must be the same size.");
/*  90 */             SelectTimeSeriesDialog.this.me.setVisible(true);
/*     */           }
/*     */         } catch (Exception e) {
/*  93 */           JOptionPane.showMessageDialog(null, "Two images must be opened for time series.", null, -1);
/*  94 */           SelectTimeSeriesDialog.this.me.dispose();
/*     */         }
/*     */       }
/*     */     });
/*  98 */     btnNewButton.setBounds(10, 108, 350, 23);
/*  99 */     this.contentPanel.add(btnNewButton);
/*     */ 
/* 101 */     JButton btnOpenPhotosmaps = new JButton("Open 3 Photos/Maps");
/* 102 */     btnOpenPhotosmaps.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 104 */         SelectTimeSeriesDialog.this.me.setVisible(false);
                  BufferedImage img1;
/*     */         try
/*     */         {
/* 110 */           File f = SelectTimeSeriesDialog.this.entrance.openImage("Open the first time series image");
/* 111 */           img1 = ImageIO.read(f);
/* 112 */           String title1 = SelectTimeSeriesDialog.this.createTitle(f, img1);
/* 113 */           f = SelectTimeSeriesDialog.this.entrance.openImage("Open the second time series image");
/* 114 */           BufferedImage img2 = ImageIO.read(f);
/* 115 */           String title2 = SelectTimeSeriesDialog.this.createTitle(f, img2);
/* 116 */           if ((img1.getWidth() != img2.getWidth()) || (img1.getHeight() != img2.getHeight())) {
/* 117 */             JOptionPane.showMessageDialog(null, "All images in a time series must be the same size.");
/* 118 */             SelectTimeSeriesDialog.this.me.setVisible(true);
/* 119 */             return;
/*     */           }
/* 121 */           SelectTimeSeriesDialog.this.entrance.setTitle1(title1);
/* 122 */           SelectTimeSeriesDialog.this.entrance.setTimeSeries1(img1);
/* 123 */           SelectTimeSeriesDialog.this.entrance.setTitle2(title2);
/* 124 */           SelectTimeSeriesDialog.this.entrance.setTimeSeries2(img2);
/* 125 */           SelectTimeSeriesDialog.this.entrance.setTitle3("");
/* 126 */           SelectTimeSeriesDialog.this.entrance.setTimeSeries3(null);
/* 127 */           SelectTimeSeriesDialog.this.entrance.setTimeSeriesType((byte)0);
/*     */         } catch (Exception e1) {
/* 129 */           JOptionPane.showMessageDialog(null, "Two images must be opened for time series.", null, -1);
/* 130 */           SelectTimeSeriesDialog.this.me.dispose();
/* 131 */           return;
/*     */         }
/*     */         try
/*     */         {
/*     */           String title2;
/*     */           String title1;
/* 134 */           File f = SelectTimeSeriesDialog.this.entrance.openImage("Open the third time series image");
/* 135 */           BufferedImage img = ImageIO.read(f);
/* 136 */           if ((img.getWidth() == img1.getWidth()) && (img.getHeight() == img1.getHeight())) {
/* 137 */             SelectTimeSeriesDialog.this.entrance.setTitle3(SelectTimeSeriesDialog.this.createTitle(f, img));
/* 138 */             SelectTimeSeriesDialog.this.entrance.setTimeSeries3(img);
/* 139 */             SelectTimeSeriesDialog.this.success(true);
/* 140 */             return;
/*     */           }
/* 142 */           JOptionPane.showMessageDialog(null, "All images in a time series must be the same size.");
/* 143 */           SelectTimeSeriesDialog.this.me.setVisible(true);
/* 144 */           return;
/*     */         }
/*     */         catch (Exception e2) {
/* 147 */           SelectTimeSeriesDialog.this.success(false);
/*     */         }
/*     */       }
/*     */     });
/* 151 */     btnOpenPhotosmaps.setBounds(371, 108, 350, 23);
/* 152 */     this.contentPanel.add(btnOpenPhotosmaps);
/*     */ 
/* 154 */     JTextArea txtrOpenOr_1 = new JTextArea();
/* 155 */     txtrOpenOr_1.setOpaque(false);
/* 156 */     txtrOpenOr_1.setEditable(false);
/* 157 */     txtrOpenOr_1.setFont(new Font("SansSerif", 0, 13));
/* 158 */     txtrOpenOr_1.setLineWrap(true);
/* 159 */     txtrOpenOr_1.setWrapStyleWord(true);
/* 160 */     txtrOpenOr_1.setText("Open 2 or 3 images from a time series (free images are avilailable at sources such as Landsat Clic 'N Pic: http://mvh.sr.unh.edu/Landsat/).  \r\n\r\nUse composite images called NRGs or Near Infrared-Red-Green.\r\n\r\nThe maximum width is 1024 pixels and the maximum height is 768 pixels.  \r\n\r\nTip: Use the Trim Function in the Utility Menu if your images are too large.  Trim option 2 will work best.");
/* 161 */     txtrOpenOr_1.setBounds(10, 142, 711, 154);
/* 162 */     this.contentPanel.add(txtrOpenOr_1);
/*     */ 
/* 164 */     JButton btnNewButton_1 = new JButton("Open 2 NIR-Red-Green Satellite Images");
/* 165 */     btnNewButton_1.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/*     */         try {
/* 168 */           SelectTimeSeriesDialog.this.me.setVisible(false);
/* 169 */           File f = SelectTimeSeriesDialog.this.entrance.openImage("Open the first NIR-Red-Green time series image");
/* 170 */           BufferedImage img1 = ImageIO.read(f);
/* 171 */           String title1 = SelectTimeSeriesDialog.this.createTitle(f, img1);
/* 172 */           f = SelectTimeSeriesDialog.this.entrance.openImage("Open the second NIR-Red-Green time series image");
/* 173 */           BufferedImage img2 = ImageIO.read(f);
/* 174 */           if ((img1.getWidth() == img2.getWidth()) && (img1.getHeight() == img2.getHeight())) {
/* 175 */             SelectTimeSeriesDialog.this.entrance.setTimeSeries1(img1);
/* 176 */             SelectTimeSeriesDialog.this.entrance.setTitle1(title1);
/* 177 */             SelectTimeSeriesDialog.this.entrance.setTitle2(SelectTimeSeriesDialog.this.createTitle(f, img2));
/* 178 */             SelectTimeSeriesDialog.this.entrance.setTimeSeries2(img2);
/* 179 */             SelectTimeSeriesDialog.this.entrance.setTitle3("");
/* 180 */             SelectTimeSeriesDialog.this.entrance.setTimeSeries3(null);
/* 181 */             SelectTimeSeriesDialog.this.entrance.setTimeSeriesType((byte)1);
/* 182 */             SelectTimeSeriesDialog.this.success(false);
/*     */           } else {
/* 184 */             JOptionPane.showMessageDialog(null, "All images in a time series must be the same size.");
/* 185 */             SelectTimeSeriesDialog.this.me.setVisible(true);
/*     */           }
/*     */         } catch (IOException e1) {
/* 188 */           JOptionPane.showMessageDialog(null, "Two images must be opened for time series.", null, -1);
/* 189 */           SelectTimeSeriesDialog.this.me.dispose();
/*     */         }
/*     */       }
/*     */     });
/* 193 */     btnNewButton_1.setBounds(10, 307, 350, 23);
/* 194 */     this.contentPanel.add(btnNewButton_1);
/*     */ 
/* 196 */     JButton btnOpenNirredgreen = new JButton("Open 3 NIR-Red-Green Satellite Images");
/* 197 */     btnOpenNirredgreen.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 199 */         SelectTimeSeriesDialog.this.me.setVisible(false);
                  BufferedImage img1;
/*     */         try
/*     */         {
/* 205 */           File f = SelectTimeSeriesDialog.this.entrance.openImage("Open the first NIR-Red-Green Satellite time series image");
/* 206 */           img1 = ImageIO.read(f);
/* 207 */           String title1 = SelectTimeSeriesDialog.this.createTitle(f, img1);
/* 208 */           f = SelectTimeSeriesDialog.this.entrance.openImage("Open the second NIR-Red-Green Satellite time series image");
/* 209 */           BufferedImage img2 = ImageIO.read(f);
/* 210 */           String title2 = SelectTimeSeriesDialog.this.createTitle(f, img2);
/* 211 */           if ((img1.getWidth() != img2.getWidth()) || (img1.getHeight() != img2.getHeight())) {
/* 212 */             JOptionPane.showMessageDialog(null, "All images in a time series must be the same size.");
/* 213 */             SelectTimeSeriesDialog.this.me.setVisible(true);
/* 214 */             return;
/*     */           }
/* 216 */           SelectTimeSeriesDialog.this.entrance.setTitle1(title1);
/* 217 */           SelectTimeSeriesDialog.this.entrance.setTimeSeries1(img1);
/* 218 */           SelectTimeSeriesDialog.this.entrance.setTitle2(title2);
/* 219 */           SelectTimeSeriesDialog.this.entrance.setTimeSeries2(img2);
/* 220 */           SelectTimeSeriesDialog.this.entrance.setTitle3("");
/* 221 */           SelectTimeSeriesDialog.this.entrance.setTimeSeries3(null);
/* 222 */           SelectTimeSeriesDialog.this.entrance.setTimeSeriesType((byte)1);
/*     */         } catch (Exception e1) {
/* 224 */           JOptionPane.showMessageDialog(null, "Two images must be opened for time series.", null, -1);
/* 225 */           SelectTimeSeriesDialog.this.me.dispose();
/* 226 */           return;
/*     */         }
/*     */         try
/*     */         {
/*     */           String title2;
/*     */           String title1;
/*     */           BufferedImage img2;
/* 229 */           File f = SelectTimeSeriesDialog.this.entrance.openImage("Open the third NIR-Red-Green Satellite time series image");
/* 230 */           BufferedImage img = ImageIO.read(f);
/* 231 */           if ((img.getWidth() == img1.getWidth()) && (img.getHeight() == img1.getHeight())) {
/* 232 */             SelectTimeSeriesDialog.this.entrance.setTitle3(SelectTimeSeriesDialog.this.createTitle(f, img));
/* 233 */             SelectTimeSeriesDialog.this.entrance.setTimeSeries3(img);
/* 234 */             SelectTimeSeriesDialog.this.success(true);
/* 235 */             return;
/*     */           }
/* 237 */           JOptionPane.showMessageDialog(null, "All images in a time series must be the same size.");
/* 238 */           SelectTimeSeriesDialog.this.me.setVisible(true);
/* 239 */           return;
/*     */         }
/*     */         catch (Exception e2) {
/* 242 */           SelectTimeSeriesDialog.this.success(false);
/*     */         }
/*     */       }
/*     */     });
/* 246 */     btnOpenNirredgreen.setBounds(371, 307, 350, 23);
/* 247 */     this.contentPanel.add(btnOpenNirredgreen);
/*     */ 
/* 249 */     JTextArea txtrUseImagesFrom = new JTextArea();
/* 250 */     txtrUseImagesFrom.setFont(new Font("SansSerif", 0, 13));
/* 251 */     txtrUseImagesFrom.setEditable(false);
/* 252 */     txtrUseImagesFrom.setLineWrap(true);
/* 253 */     txtrUseImagesFrom.setOpaque(false);
/* 254 */     txtrUseImagesFrom.setWrapStyleWord(true);
/* 255 */     txtrUseImagesFrom.setText("Use images from the USGS Earthshots website: http://earthshots.usgs.gov/. The accompanying articles provide an overview of the events that created the changes in the landscape.\r\n\r\nTip: save the images with the year as the last 4 alphanumerics of the filename.  Example: MtStHelens_1977.jpg.");
/* 256 */     txtrUseImagesFrom.setBounds(10, 341, 711, 81);
/* 257 */     this.contentPanel.add(txtrUseImagesFrom);
/*     */ 
/* 259 */     JButton btnNewButton_2 = new JButton("Open 2 USGS Earthshots Satellite Images");
/* 260 */     btnNewButton_2.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/*     */         try {
/* 263 */           SelectTimeSeriesDialog.this.me.setVisible(false);
/* 264 */           File f = SelectTimeSeriesDialog.this.entrance.openImage("Open the first USGS Earthshots Satellite time series image");
/* 265 */           BufferedImage img1 = ImageIO.read(f);
/* 266 */           if ((img1.getWidth() != 450) || (img1.getHeight() != 324)) {
/* 267 */             JOptionPane.showMessageDialog(null, "USGS Earthshots Satellite images are 450 x 324 pixels.");
/* 268 */             SelectTimeSeriesDialog.this.me.setVisible(true);
/* 269 */             return;
/*     */           }
/* 271 */           String title1 = SelectTimeSeriesDialog.this.createTitle(f, img1);
/* 272 */           f = SelectTimeSeriesDialog.this.entrance.openImage("Open the second USGS Earthshots Satellite time series image");
/* 273 */           BufferedImage img2 = ImageIO.read(f);
/* 274 */           if ((img2.getWidth() == 450) && (img2.getHeight() == 324)) {
/* 275 */             SelectTimeSeriesDialog.this.entrance.setTimeSeries1(img1);
/* 276 */             SelectTimeSeriesDialog.this.entrance.setTitle1(title1);
/* 277 */             SelectTimeSeriesDialog.this.entrance.setTitle2(SelectTimeSeriesDialog.this.createTitle(f, img2));
/* 278 */             SelectTimeSeriesDialog.this.entrance.setTimeSeries2(img2);
/* 279 */             SelectTimeSeriesDialog.this.entrance.setTitle3("");
/* 280 */             SelectTimeSeriesDialog.this.entrance.setTimeSeries3(null);
/* 281 */             SelectTimeSeriesDialog.this.entrance.setTimeSeriesType((byte)1);
/* 282 */             SelectTimeSeriesDialog.this.success(false);
/*     */           } else {
/* 284 */             JOptionPane.showMessageDialog(null, "All images in a time series must be the same size.");
/* 285 */             SelectTimeSeriesDialog.this.me.setVisible(true);
/* 286 */             return;
/*     */           }
/*     */         } catch (IOException e1) {
/* 289 */           JOptionPane.showMessageDialog(null, "Two images must be opened for time series.", null, -1);
/* 290 */           SelectTimeSeriesDialog.this.me.dispose();
/*     */         }
/*     */       }
/*     */     });
/* 294 */     btnNewButton_2.setBounds(10, 433, 350, 23);
/* 295 */     this.contentPanel.add(btnNewButton_2);
/*     */ 
/* 297 */     JButton btnOpenUsgs = new JButton("Open 3 USGS Earthshots Satellite Images");
/* 298 */     btnOpenUsgs.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 300 */         SelectTimeSeriesDialog.this.me.setVisible(false);
/*     */         try
/*     */         {
/* 306 */           File f = SelectTimeSeriesDialog.this.entrance.openImage("Open the first USGS Earthshots Satellite Satellite time series image");
/* 307 */           BufferedImage img1 = ImageIO.read(f);
/* 308 */           if ((img1.getWidth() != 450) || (img1.getHeight() != 324)) {
/* 309 */             JOptionPane.showMessageDialog(null, "USGS Earthshots Satellite images are 450 x 324 pixels.");
/* 310 */             SelectTimeSeriesDialog.this.me.setVisible(true);
/* 311 */             return;
/*     */           }
/* 313 */           String title1 = SelectTimeSeriesDialog.this.createTitle(f, img1);
/* 314 */           f = SelectTimeSeriesDialog.this.entrance.openImage("Open the second USGS Earthshots Satellite Satellite time series image");
/* 315 */           BufferedImage img2 = ImageIO.read(f);
/* 316 */           String title2 = SelectTimeSeriesDialog.this.createTitle(f, img2);
/* 317 */           if ((img2.getWidth() != 450) || (img2.getHeight() != 324)) {
/* 318 */             JOptionPane.showMessageDialog(null, "All images in a time series must be the same size.");
/* 319 */             SelectTimeSeriesDialog.this.me.setVisible(true);
/* 320 */             return;
/*     */           }
/* 322 */           SelectTimeSeriesDialog.this.entrance.setTitle1(title1);
/* 323 */           SelectTimeSeriesDialog.this.entrance.setTimeSeries1(img1);
/* 324 */           SelectTimeSeriesDialog.this.entrance.setTitle2(title2);
/* 325 */           SelectTimeSeriesDialog.this.entrance.setTimeSeries2(img2);
/* 326 */           SelectTimeSeriesDialog.this.entrance.setTitle3("");
/* 327 */           SelectTimeSeriesDialog.this.entrance.setTimeSeries3(null);
/* 328 */           SelectTimeSeriesDialog.this.entrance.setTimeSeriesType((byte)1);
/*     */         } catch (Exception e1) {
/* 330 */           e1.printStackTrace();
/* 331 */           JOptionPane.showMessageDialog(null, "Two images must be opened for time series.", null, -1);
/* 332 */           SelectTimeSeriesDialog.this.me.dispose();
/* 333 */           return;
/*     */         }
/*     */         try
/*     */         {
/*     */           String title2;
/*     */           String title1;
/*     */           BufferedImage img2;
/*     */           BufferedImage img1;
/* 336 */           File f = SelectTimeSeriesDialog.this.entrance.openImage("Open the third USGS Earthshots Satellite Satellite time series image");
/* 337 */           BufferedImage img = ImageIO.read(f);
/* 338 */           if ((img.getWidth() == 450) && (img.getHeight() == 324)) {
/* 339 */             SelectTimeSeriesDialog.this.entrance.setTitle3(SelectTimeSeriesDialog.this.createTitle(f, img));
/* 340 */             SelectTimeSeriesDialog.this.entrance.setTimeSeries3(img);
/* 341 */             SelectTimeSeriesDialog.this.success(true);
/* 342 */             return;
/*     */           }
/* 344 */           JOptionPane.showMessageDialog(null, "All images in a time series must be the same size.");
/* 345 */           SelectTimeSeriesDialog.this.me.setVisible(true);
/* 346 */           return;
/*     */         }
/*     */         catch (Exception e2) {
/* 349 */           SelectTimeSeriesDialog.this.success(false);
/*     */         }
/*     */       }
/*     */     });
/* 353 */     btnOpenUsgs.setBounds(371, 433, 350, 23);
/* 354 */     this.contentPanel.add(btnOpenUsgs);
/*     */ 
/* 356 */     JPanel buttonPane = new JPanel();
/* 357 */     buttonPane.setLayout(new FlowLayout(1));
/* 358 */     getContentPane().add(buttonPane, "South");
/*     */ 
/* 360 */     JButton cancelButton = new JButton("Cancel");
/* 361 */     cancelButton.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 363 */         SelectTimeSeriesDialog.this.me.setVisible(false);
/*     */       }
/*     */     });
/* 366 */     cancelButton.setActionCommand("Cancel");
/* 367 */     buttonPane.add(cancelButton);
/*     */   }
/*     */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.dialogs.SelectTimeSeriesDialog
 * JD-Core Version:    0.6.2
 */