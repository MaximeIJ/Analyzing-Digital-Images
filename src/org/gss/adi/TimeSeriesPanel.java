/*     */ package org.gss.adi;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.ItemEvent;
/*     */ import java.awt.event.ItemListener;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseMotionAdapter;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.text.DecimalFormat;
/*     */ import javax.swing.DefaultComboBoxModel;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JSlider;
/*     */ import javax.swing.JSpinner;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.SpinnerNumberModel;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
/*     */ import org.gss.adi.dialogs.NDVIInfoDialog;
/*     */ import org.gss.adi.tools.ColorEnhances;
/*     */ import org.gss.adi.tools.ColorTools;
/*     */ import org.gss.adi.tools.Measurement;
/*     */ 
/*     */ public class TimeSeriesPanel extends JPanel
/*     */   implements Toolable, Updatable
/*     */ {
/*     */   private static final long serialVersionUID = 6917266928380728707L;
/*     */   private JTextField img3labl;
/*     */   private JTextField img1labl;
/*     */   private JTextField img2labl;
/*     */   private JTextField txtAnalysis;
/*     */   private JTextField txtEnhancement;
/*     */   private Entrance entrance;
/*     */   private JSlider slider;
/*     */   JComboBox comboBox_1;
/*     */   private JPanel dataPanel;
/*     */   private ZoomPanLabel img1;
/*     */   private ZoomPanLabel img2;
/*     */   private ZoomPanLabel img3;
/*     */   private BufferedImage currentImage1;
/*     */   private BufferedImage currentImage2;
/*     */   private BufferedImage currentImage3;
/*     */   private JComboBox comboBox;
/*     */   private JTextField textField;
/*     */   private boolean Three;
/*     */   private JSpinner x1;
/*     */   private JSpinner y1;
/*     */   private JSpinner x2;
/*     */   private JSpinner y2;
/*     */   private JTextArea pt1;
/*     */   private JTextArea pt2;
/*     */   private JTextArea txtNumPixels;
/*     */   private JTextArea lengthArea;
/*     */   private JTextArea numPixels;
/*     */   private JTextArea txtLengthArea;
/*     */   private ToolAdder ta;
/*     */   private JLabel ndviChart;
/*  84 */   private int dataPanelType = 0;
/*     */   private byte Type;
/*     */   private JCheckBox displayTitles;
/*     */ 
/*     */   public TimeSeriesPanel(Entrance ent, boolean three, byte type)
/*     */   {
/*     */     try
/*     */     {
/*  92 */       this.Three = three;
/*  93 */       setLayout(null);
/*  94 */       this.Type = type;
/*  95 */       this.entrance = ent;
/*  96 */       this.entrance.setPane(this);
/*  97 */       setup(type);
/*  98 */       setImages(three);
/*  99 */       if (type == 0)
/* 100 */         this.dataPanelType = 0;
/*     */       else
/* 102 */         this.dataPanelType = 1;
/* 103 */       if (three)
/* 104 */         this.ta = new ToolAdder(this.comboBox, this.entrance, new ZoomPanLabel[] { this.img1, this.img2, this.img3 }, this, null, this.dataPanelType, this.comboBox_1);
/*     */       else {
/* 106 */         this.ta = new ToolAdder(this.comboBox, this.entrance, new ZoomPanLabel[] { this.img1, this.img2 }, this, null, this.dataPanelType, this.comboBox_1);
/*     */       }
/* 108 */       this.img1.getLabel().addMouseListener(new pixelMouse());
/* 109 */       this.img1.getLabel().addMouseMotionListener(new pixelDrag());
/* 110 */       this.img2.getLabel().addMouseListener(new pixelMouse());
/* 111 */       this.img2.getLabel().addMouseMotionListener(new pixelDrag());
/* 112 */       if (three) {
/* 113 */         this.img3.getLabel().addMouseListener(new pixelMouse());
/* 114 */         this.img3.getLabel().addMouseMotionListener(new pixelDrag());
/*     */       }
/*     */     } catch (OutOfMemoryError e) { JOptionPane.showMessageDialog(null, "Java out of memory error. Please use a smaller image or increase\nthe ram allocated to java applications"); }
/*     */   }
/*     */ 
/*     */   private void setImages(boolean three) {
/* 120 */     this.img1.setImage(this.entrance.getTimeSeries1());
/* 121 */     this.img2.setImage(this.entrance.getTimeSeries2());
/* 122 */     this.currentImage1 = this.entrance.getTimeSeries1();
/* 123 */     this.currentImage2 = this.entrance.getTimeSeries2();
/* 124 */     this.img1labl.setText(this.entrance.getTitle1());
/* 125 */     this.img2labl.setText(this.entrance.getTitle2());
/* 126 */     this.img1labl.setForeground(this.entrance.getColor());
/* 127 */     this.img2labl.setForeground(this.entrance.getColor());
/* 128 */     if (three) {
/* 129 */       this.img3.setImage(this.entrance.getTimeSeries3());
/* 130 */       this.currentImage3 = this.entrance.getTimeSeries3();
/* 131 */       this.img3labl.setText(this.entrance.getTitle3());
/* 132 */       this.img3labl.setForeground(this.entrance.getColor());
/*     */     }
/*     */   }
/*     */ 
/*     */   private void titlesToFront(boolean b)
/*     */   {
/* 139 */     this.img1labl.setVisible(b);
/* 140 */     this.img2labl.setVisible(b);
/* 141 */     this.img3labl.setVisible(b);
/* 142 */     this.img1labl.repaint();
/* 143 */     this.img2labl.repaint();
/* 144 */     this.img3labl.repaint();
/*     */   }
/*     */ 
/*     */   private void handlePixels()
/*     */   {
              Double d;
              Measurement m;
              Integer[] x;
              Integer[] y;
/* 150 */     DecimalFormat df = new DecimalFormat("##.##");
/* 151 */     switch (this.comboBox.getSelectedIndex()) {
/*     */     case 0:
/* 153 */       break;
/*     */     case 1:
/* 155 */       break;
/*     */     case 2:
/* 157 */       d = ColorTools.linearDist(((Integer)this.x1.getValue()).intValue(), ((Integer)this.y1.getValue()).intValue(), ((Integer)this.x2.getValue()).intValue(), ((Integer)this.y2.getValue()).intValue());
/* 158 */       this.numPixels.setText(df.format(d));
/* 159 */       m = this.entrance.getTimeSeriesMeasurement();
/* 160 */       if (m != null)
/* 161 */         this.lengthArea.setText(m.measure(d, true));
/* 162 */       break;
/*     */     case 3:
/* 164 */       d = ColorTools.pathDist(this.ta.getX(), this.ta.getY());
/* 165 */       this.numPixels.setText(df.format(d));
/* 166 */       m = this.entrance.getTimeSeriesMeasurement();
/* 167 */       if (m != null)
/* 168 */         this.lengthArea.setText(m.measure(d, true));
/* 169 */       break;
/*     */     case 4:
/* 171 */       x = this.ta.getX();
/* 172 */       y = this.ta.getY();
/* 173 */       Integer i = new Integer(Math.abs(x[0].intValue() - x[1].intValue()) * Math.abs(y[0].intValue() - y[1].intValue()));
/* 174 */       this.numPixels.setText(i.toString());
/* 175 */       m = this.entrance.getTimeSeriesMeasurement();
/* 176 */       if (m != null)
/* 177 */         this.lengthArea.setText(m.measure(new Double(i.intValue()), false));
/* 178 */       break;
/*     */     case 5:
/* 180 */       if (this.ta.polyComplete()) {
/* 181 */         x = this.ta.getX();
/* 182 */         y = this.ta.getY();
/* 183 */         i = new Integer(ColorTools.polyArea(x, y).intValue());
/* 184 */         this.numPixels.setText(i.toString());
/* 185 */         m = this.entrance.getTimeSeriesMeasurement();
/* 186 */         if (m != null)
/* 187 */           this.lengthArea.setText(m.measure(new Double(i.intValue()), false)); 
/*     */       }
/*     */       break;
/*     */     }
/*     */   }
/*     */ 
/* 193 */   public void updatePixSize() { this.txtLengthArea.setVisible(true);
/* 194 */     this.lengthArea.setVisible(true);
/* 195 */     if ((this.numPixels.getText() != null) && (!this.numPixels.getText().equals(""))) {
/* 196 */       String tool = (String)this.comboBox.getSelectedItem();
/* 197 */       Measurement m = this.entrance.getTimeSeriesMeasurement();
/* 198 */       if ((tool.contains("Line")) || (tool.contains("Path")))
/* 199 */         this.lengthArea.setText(m.measure(Double.valueOf(Double.parseDouble(this.numPixels.getText())), true));
/* 200 */       else if ((tool.contains("Rectangle")) || (tool.contains("Polygon")))
/* 201 */         this.lengthArea.setText(m.measure(Double.valueOf(Double.parseDouble(this.numPixels.getText())), false));
/*     */     } }
/*     */ 
/*     */   public void updateTool()
/*     */   {
/* 206 */     this.img1labl.setForeground(this.entrance.getColor());
/* 207 */     this.img2labl.setForeground(this.entrance.getColor());
/* 208 */     this.img3labl.setForeground(this.entrance.getColor());
/*     */   }
/*     */ 
/*     */   public void updatePic() {
/*     */   }
/*     */ 
/*     */   public void setDataPanel(JPanel panel) {
/* 215 */     panel.setLocation(0, 0);
/* 216 */     panel.setSize(450, 300);
/* 217 */     panel.setPreferredSize(new Dimension(450, 300));
/* 218 */     this.dataPanel.add(panel);
/* 219 */     panel.revalidate();
/* 220 */     panel.repaint();
/* 221 */     this.dataPanel.revalidate();
/* 222 */     this.dataPanel.repaint();
/* 223 */     repaint();
/*     */   }
/*     */ 
/*     */   public void removeDataPanel(JPanel panel) {
/* 227 */     if (panel != null) {
/* 228 */       this.dataPanel.remove(panel);
/* 229 */       repaint();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setPoint1(int x, int y) {
/* 234 */     this.x1.setVisible(true);
/* 235 */     this.y1.setVisible(true);
/* 236 */     this.pt1.setVisible(true);
/* 237 */     this.x1.setValue(Integer.valueOf(x));
/* 238 */     this.y1.setValue(Integer.valueOf(y));
/*     */   }
/*     */ 
/*     */   public void setPoint2(int x, int y) {
/* 242 */     this.x2.setVisible(true);
/* 243 */     this.y2.setVisible(true);
/* 244 */     this.pt2.setVisible(true);
/* 245 */     this.x2.setValue(Integer.valueOf(x));
/* 246 */     this.y2.setValue(Integer.valueOf(y));
/*     */   }
/*     */ 
/*     */   public void removePoints() {
/* 250 */     this.x1.setVisible(false);
/* 251 */     this.y1.setVisible(false);
/* 252 */     this.pt1.setVisible(false);
/* 253 */     this.x2.setVisible(false);
/* 254 */     this.y2.setVisible(false);
/* 255 */     this.pt2.setVisible(false);
/*     */   }
/*     */   public void growth(String[] s) {
/*     */   }
/*     */   private void setup(byte type) {
/* 260 */     this.comboBox = new JComboBox();
/* 261 */     this.comboBox.addItemListener(new ItemListener() {
/*     */       public void itemStateChanged(ItemEvent arg0) {
/* 263 */         TimeSeriesPanel.this.numPixels.setText("");
/* 264 */         TimeSeriesPanel.this.lengthArea.setText("");
/* 265 */         switch (TimeSeriesPanel.this.comboBox.getSelectedIndex())
/*     */         {
/*     */         case 0:
/*     */         case 1:
/* 269 */           TimeSeriesPanel.this.txtNumPixels.setVisible(false);
/* 270 */           TimeSeriesPanel.this.numPixels.setVisible(false);
/* 271 */           if (TimeSeriesPanel.this.entrance.getTimeSeriesMeasurement() != null) {
/* 272 */             TimeSeriesPanel.this.txtLengthArea.setVisible(false);
/* 273 */             TimeSeriesPanel.this.lengthArea.setVisible(false);
/*     */           }
/* 275 */           break;
/*     */         case 2:
/*     */         case 3:
/*     */         case 4:
/*     */         case 5:
/* 283 */           TimeSeriesPanel.this.txtNumPixels.setVisible(true);
/* 284 */           TimeSeriesPanel.this.numPixels.setVisible(true);
/* 285 */           if (TimeSeriesPanel.this.entrance.getTimeSeriesMeasurement() != null) {
/* 286 */             TimeSeriesPanel.this.txtLengthArea.setVisible(true);
/* 287 */             TimeSeriesPanel.this.lengthArea.setVisible(true);
/*     */           }
/*     */           break;
/*     */         }
/*     */       }
/*     */     });
/* 293 */     this.comboBox.setModel(new DefaultComboBoxModel(new String[] { "Point (text)", "Point (graph)", "Line", "Path (multiple point line)", "Rectangle", "Polygon" }));
/* 294 */     this.comboBox.setToolTipText("Point (text)\r\nPoint (graph)\r\nLine\r\nPath (multiple point line)\r\nRectangle\r\nPolygon");
/* 295 */     this.comboBox.setBounds(516, 328, 144, 20);
/* 296 */     add(this.comboBox);
/*     */ 
/* 298 */     this.slider = new JSlider();
/* 299 */     this.slider.setOrientation(1);
/* 300 */     this.slider.addChangeListener(new ChangeListener() {
/*     */       public void stateChanged(ChangeEvent e) {
/* 302 */         int z = TimeSeriesPanel.this.slider.getValue();
/*     */         try {
/* 304 */           TimeSeriesPanel.this.img1.zoom(z);
/* 305 */           TimeSeriesPanel.this.img2.zoom(z);
/* 306 */           if (TimeSeriesPanel.this.Three)
/* 307 */             TimeSeriesPanel.this.img3.zoom(z);
/* 308 */           TimeSeriesPanel.this.ta.drawTools();
/* 309 */           TimeSeriesPanel.this.titlesToFront(TimeSeriesPanel.this.displayTitles.isSelected());
/*     */         }
/*     */         catch (Exception localException)
/*     */         {
/*     */         }
/*     */       }
/*     */     });
/* 313 */     this.slider.addMouseListener(new MouseAdapter()
/*     */     {
/*     */       public void mouseReleased(MouseEvent e) {
/* 316 */         int z = TimeSeriesPanel.this.slider.getValue();
/*     */         try {
/* 318 */           TimeSeriesPanel.this.img1.qualityZoom(z);
/* 319 */           TimeSeriesPanel.this.img2.qualityZoom(z);
/* 320 */           if (TimeSeriesPanel.this.Three)
/* 321 */             TimeSeriesPanel.this.img3.qualityZoom(z);
/* 322 */           TimeSeriesPanel.this.ta.drawTools();
/* 323 */           TimeSeriesPanel.this.titlesToFront(TimeSeriesPanel.this.displayTitles.isSelected());
/*     */         }
/*     */         catch (Exception localException)
/*     */         {
/*     */         }
/*     */       }
/*     */     });
/* 328 */     this.img1labl = new JTextField();
/* 329 */     this.img1labl.setOpaque(false);
/* 330 */     this.img1labl.setEditable(false);
/* 331 */     this.img1labl.setColumns(10);
/* 332 */     this.img1labl.setBorder(null);
/* 333 */     this.img1labl.setBounds(10, 0, 450, 14);
/* 334 */     add(this.img1labl);
/* 335 */     this.slider.setMinimum(100);
/* 336 */     this.slider.setMaximum(200);
/* 337 */     this.slider.setMajorTickSpacing(2);
/* 338 */     this.slider.setSnapToTicks(true);
/* 339 */     this.slider.setValue(0);
/* 340 */     this.slider.setBounds(930, 14, 25, 292);
/* 341 */     add(this.slider);
/*     */ 
/* 343 */     this.img1 = new ZoomPanLabel(this.slider);
/* 344 */     this.img1.setBounds(10, 0, 450, 324);
/* 345 */     add(this.img1);
/*     */ 
/* 347 */     this.img2labl = new JTextField();
/* 348 */     this.img2labl.setOpaque(false);
/* 349 */     this.img2labl.setEditable(false);
/* 350 */     this.img2labl.setColumns(10);
/* 351 */     this.img2labl.setBorder(null);
/* 352 */     this.img2labl.setBounds(470, 0, 450, 14);
/* 353 */     add(this.img2labl);
/*     */ 
/* 355 */     this.img2 = new ZoomPanLabel(this.slider);
/* 356 */     this.img2.setBounds(470, 0, 450, 324);
/* 357 */     add(this.img2);
/*     */ 
/* 359 */     this.img3labl = new JTextField();
/* 360 */     this.img3labl.setBorder(null);
/* 361 */     this.img3labl.setOpaque(false);
/* 362 */     this.img3labl.setEditable(false);
/* 363 */     this.img3labl.setBounds(20, 342, 440, 14);
/* 364 */     add(this.img3labl);
/* 365 */     this.img3labl.setColumns(10);
/*     */ 
/* 367 */     this.img3 = new ZoomPanLabel(this.slider);
/* 368 */     this.img3.setBounds(10, 328, 450, 324);
/* 369 */     add(this.img3);
/*     */ 
/* 371 */     this.img3.sync(this.img2);
/* 372 */     this.img3.sync(this.img1);
/*     */ 
/* 374 */     this.txtAnalysis = new JTextField();
/* 375 */     this.txtAnalysis.setFont(new Font("SansSerif", 0, 10));
/* 376 */     this.txtAnalysis.setEditable(false);
/* 377 */     this.txtAnalysis.setText("Analysis");
/* 378 */     this.txtAnalysis.setOpaque(false);
/* 379 */     this.txtAnalysis.setColumns(10);
/* 380 */     this.txtAnalysis.setBorder(null);
/* 381 */     this.txtAnalysis.setBounds(470, 328, 47, 20);
/* 382 */     add(this.txtAnalysis);
/*     */ 
/* 384 */     this.txtEnhancement = new JTextField();
/* 385 */     this.txtEnhancement.setFont(new Font("SansSerif", 0, 10));
/* 386 */     this.txtEnhancement.setEditable(false);
/* 387 */     this.txtEnhancement.setText("Enhancement");
/* 388 */     this.txtEnhancement.setOpaque(false);
/* 389 */     this.txtEnhancement.setColumns(10);
/* 390 */     this.txtEnhancement.setBorder(null);
/* 391 */     this.txtEnhancement.setBounds(662, 328, 95, 20);
/* 392 */     add(this.txtEnhancement);
/*     */ 
/* 394 */     this.comboBox_1 = new JComboBox();
/* 395 */     this.comboBox_1.addItemListener(new ItemListener() {
/*     */       public void itemStateChanged(ItemEvent e) {
/* 397 */         if (TimeSeriesPanel.this.Type == 0)
/* 398 */           TimeSeriesPanel.this.dataPanelType = 0;
/*     */         else
/* 400 */           TimeSeriesPanel.this.dataPanelType = 1;
/*     */         try {
/* 402 */           TimeSeriesPanel.this.ta.changeTool();
/*     */         } catch (Exception x) {
/* 404 */           x.printStackTrace();
/*     */         }
/* 406 */         TimeSeriesPanel.this.ndviChart.setVisible(false);
/* 407 */         TimeSeriesPanel.this.comboBox.setModel(new DefaultComboBoxModel(new String[] { "Point (text)", "Point (graph)", "Line", "Path (multiple point line)", "Rectangle", "Polygon" }));
/* 408 */         TimeSeriesPanel.this.comboBox.setToolTipText("Point (text)\r\nPoint (graph)\r\nLine\r\nPath (multiple point line)\r\nRectangle\r\nPolygon");
/* 409 */         switch (((JComboBox)e.getSource()).getSelectedIndex()) {
/*     */         case 0:
/* 411 */           TimeSeriesPanel.this.setImages(TimeSeriesPanel.this.Three);
/* 412 */           break;
/*     */         case 1:
/* 414 */           TimeSeriesPanel.this.setImages(TimeSeriesPanel.this.Three);
/* 415 */           TimeSeriesPanel.this.img1.setImage(ColorEnhances.grayScale((byte)0, TimeSeriesPanel.this.currentImage1));
/* 416 */           TimeSeriesPanel.this.img2.setImage(ColorEnhances.grayScale((byte)0, TimeSeriesPanel.this.currentImage2));
/* 417 */           if (TimeSeriesPanel.this.Three) TimeSeriesPanel.this.img3.setImage(ColorEnhances.grayScale((byte)0, TimeSeriesPanel.this.currentImage3));
/* 418 */           break;
/*     */         case 2:
/* 420 */           TimeSeriesPanel.this.setImages(TimeSeriesPanel.this.Three);
/* 421 */           TimeSeriesPanel.this.img1.setImage(ColorEnhances.grayScale((byte)1, TimeSeriesPanel.this.currentImage1));
/* 422 */           TimeSeriesPanel.this.img2.setImage(ColorEnhances.grayScale((byte)1, TimeSeriesPanel.this.currentImage2));
/* 423 */           if (TimeSeriesPanel.this.Three) TimeSeriesPanel.this.img3.setImage(ColorEnhances.grayScale((byte)1, TimeSeriesPanel.this.currentImage3));
/* 424 */           break;
/*     */         case 3:
/* 426 */           TimeSeriesPanel.this.setImages(TimeSeriesPanel.this.Three);
/* 427 */           TimeSeriesPanel.this.img1.setImage(ColorEnhances.grayScale((byte)2, TimeSeriesPanel.this.currentImage1));
/* 428 */           TimeSeriesPanel.this.img2.setImage(ColorEnhances.grayScale((byte)2, TimeSeriesPanel.this.currentImage2));
/* 429 */           if (TimeSeriesPanel.this.Three) TimeSeriesPanel.this.img3.setImage(ColorEnhances.grayScale((byte)2, TimeSeriesPanel.this.currentImage3));
/* 430 */           break;
/*     */         case 4:
/* 432 */           TimeSeriesPanel.this.setImages(TimeSeriesPanel.this.Three);
/* 433 */           TimeSeriesPanel.this.img1.setImage(ColorEnhances.grayScale((byte)-1, TimeSeriesPanel.this.currentImage1));
/* 434 */           TimeSeriesPanel.this.img2.setImage(ColorEnhances.grayScale((byte)-1, TimeSeriesPanel.this.currentImage2));
/* 435 */           if (TimeSeriesPanel.this.Three) TimeSeriesPanel.this.img3.setImage(ColorEnhances.grayScale((byte)-1, TimeSeriesPanel.this.currentImage3));
/* 436 */           break;
/*     */         case 5:
/* 438 */           TimeSeriesPanel.this.setImages(TimeSeriesPanel.this.Three);
/* 439 */           TimeSeriesPanel.this.img1.setImage(ColorEnhances.normalize(new byte[] { 0, 1 }, TimeSeriesPanel.this.currentImage1));
/* 440 */           TimeSeriesPanel.this.img2.setImage(ColorEnhances.normalize(new byte[] { 0, 1 }, TimeSeriesPanel.this.currentImage2));
/* 441 */           if (TimeSeriesPanel.this.Three) TimeSeriesPanel.this.img3.setImage(ColorEnhances.normalize(new byte[] { 0, 1 }, TimeSeriesPanel.this.currentImage3));
/* 442 */           break;
/*     */         case 6:
/* 444 */           TimeSeriesPanel.this.setImages(TimeSeriesPanel.this.Three);
/* 445 */           TimeSeriesPanel.this.img1.setImage(ColorEnhances.normalize(new byte[] { 0, 2 }, TimeSeriesPanel.this.currentImage1));
/* 446 */           TimeSeriesPanel.this.img2.setImage(ColorEnhances.normalize(new byte[] { 0, 2 }, TimeSeriesPanel.this.currentImage2));
/* 447 */           if (TimeSeriesPanel.this.Three) TimeSeriesPanel.this.img3.setImage(ColorEnhances.normalize(new byte[] { 0, 2 }, TimeSeriesPanel.this.currentImage3));
/* 448 */           break;
/*     */         case 7:
/* 450 */           TimeSeriesPanel.this.setImages(TimeSeriesPanel.this.Three);
/* 451 */           TimeSeriesPanel.this.img1.setImage(ColorEnhances.normalize(new byte[] { 2, 1 }, TimeSeriesPanel.this.currentImage1));
/* 452 */           TimeSeriesPanel.this.img2.setImage(ColorEnhances.normalize(new byte[] { 2, 1 }, TimeSeriesPanel.this.currentImage2));
/* 453 */           if (TimeSeriesPanel.this.Three) TimeSeriesPanel.this.img3.setImage(ColorEnhances.normalize(new byte[] { 2, 1 }, TimeSeriesPanel.this.currentImage3));
/* 454 */           break;
/*     */         case 8:
/* 456 */           TimeSeriesPanel.this.comboBox.setModel(new DefaultComboBoxModel(new String[] { "Point (text)", "Point (graph)", "Line", "Path (multiple point line)", "Rectangle", "Polygon", "Rectangle with NDVI Mask", "Polygon with NDVI Mask" }));
/* 457 */           TimeSeriesPanel.this.comboBox.setToolTipText("Point (text)\r\nPoint (graph)\r\nLine\r\nPath (multiple point line)\r\nRectangle\r\nPolygon\r\nRectangle with NDVI Mask\r\nPolygon with NDVI Mask");
/* 458 */           TimeSeriesPanel.this.dataPanelType = 2;
/* 459 */           TimeSeriesPanel.this.ndviChart.setVisible(true);
/* 460 */           TimeSeriesPanel.this.setImages(TimeSeriesPanel.this.Three);
/* 461 */           TimeSeriesPanel.this.img1.setImage(ColorTools.NDVIImage(TimeSeriesPanel.this.currentImage1));
/* 462 */           TimeSeriesPanel.this.img2.setImage(ColorTools.NDVIImage(TimeSeriesPanel.this.currentImage2));
/* 463 */           if (TimeSeriesPanel.this.Three) TimeSeriesPanel.this.img3.setImage(ColorTools.NDVIImage(TimeSeriesPanel.this.currentImage3));
/*     */           break;
/*     */         }
/*     */       }
/*     */     });
/* 469 */     if (type == 0)
/* 470 */       this.comboBox_1.setModel(new DefaultComboBoxModel(new String[] { "RGB", "Red as gray", "Green as gray", "Blue as gray", "Average as gray", "Red vs Green (normalized)", "Red vs Blue (normalized)", "Green vs Blue (normalized)" }));
/*     */     else
/* 472 */       this.comboBox_1.setModel(new DefaultComboBoxModel(new String[] { "NIR + Red + Green", "NIR as gray", "Red as gray", "Green as gray", "Average as gray", "(NIR-Red)/(NIR+Red)", "(NIR-Green)/(NIR+Green)", "(Red-Green)/(Red+Green)", "NDVI" }));
/* 473 */     this.comboBox_1.setBounds(753, 328, 166, 20);
/* 474 */     add(this.comboBox_1);
/*     */ 
/* 476 */     this.pt1 = new JTextArea();
/* 477 */     this.pt1.setRows(2);
/* 478 */     this.pt1.setFont(new Font("SansSerif", 0, 13));
/* 479 */     this.pt1.setText("Pos1");
/* 480 */     this.pt1.setOpaque(false);
/* 481 */     this.pt1.setWrapStyleWord(true);
/* 482 */     this.pt1.setLineWrap(true);
/* 483 */     this.pt1.setBounds(954, 363, 58, 47);
/* 484 */     add(this.pt1);
/*     */ 
/* 486 */     this.x1 = new JSpinner();
/* 487 */     this.x1.addChangeListener(new ChangeListener() {
/*     */       public void stateChanged(ChangeEvent arg0) {
/* 489 */         TimeSeriesPanel.this.ta.mvPoint1(((Integer)TimeSeriesPanel.this.x1.getValue()).intValue(), ((Integer)TimeSeriesPanel.this.y1.getValue()).intValue());
/*     */       }
/*     */     });
/* 492 */     this.x1.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
/* 493 */     this.x1.setOpaque(false);
/* 494 */     this.x1.setFont(new Font("SansSerif", 0, 11));
/* 495 */     this.x1.setBounds(954, 410, 72, 20);
/* 496 */     add(this.x1);
/*     */ 
/* 498 */     this.y1 = new JSpinner();
/* 499 */     this.y1.addChangeListener(new ChangeListener() {
/*     */       public void stateChanged(ChangeEvent arg0) {
/* 501 */         TimeSeriesPanel.this.ta.mvPoint1(((Integer)TimeSeriesPanel.this.x1.getValue()).intValue(), ((Integer)TimeSeriesPanel.this.y1.getValue()).intValue());
/*     */       }
/*     */     });
/* 504 */     this.y1.setOpaque(false);
/* 505 */     this.y1.setFont(new Font("SansSerif", 0, 11));
/* 506 */     this.y1.setBounds(954, 431, 72, 20);
/* 507 */     add(this.y1);
/*     */ 
/* 509 */     this.pt2 = new JTextArea();
/* 510 */     this.pt2.setRows(2);
/* 511 */     this.pt2.setWrapStyleWord(true);
/* 512 */     this.pt2.setText("Pos2");
/* 513 */     this.pt2.setOpaque(false);
/* 514 */     this.pt2.setLineWrap(true);
/* 515 */     this.pt2.setFont(new Font("SansSerif", 0, 13));
/* 516 */     this.pt2.setBounds(954, 450, 58, 47);
/* 517 */     add(this.pt2);
/*     */ 
/* 519 */     this.x2 = new JSpinner();
/* 520 */     this.x2.addChangeListener(new ChangeListener() {
/*     */       public void stateChanged(ChangeEvent arg0) {
/* 522 */         TimeSeriesPanel.this.ta.mvPoint2(((Integer)TimeSeriesPanel.this.x2.getValue()).intValue(), ((Integer)TimeSeriesPanel.this.y2.getValue()).intValue());
/*     */       }
/*     */     });
/* 525 */     this.x2.setOpaque(false);
/* 526 */     this.x2.setFont(new Font("SansSerif", 0, 11));
/* 527 */     this.x2.setBounds(954, 497, 72, 20);
/* 528 */     add(this.x2);
/*     */ 
/* 530 */     this.y2 = new JSpinner();
/* 531 */     this.y2.addChangeListener(new ChangeListener() {
/*     */       public void stateChanged(ChangeEvent arg0) {
/* 533 */         TimeSeriesPanel.this.ta.mvPoint2(((Integer)TimeSeriesPanel.this.x2.getValue()).intValue(), ((Integer)TimeSeriesPanel.this.y2.getValue()).intValue());
/*     */       }
/*     */     });
/* 536 */     this.y2.setOpaque(false);
/* 537 */     this.y2.setFont(new Font("SansSerif", 0, 11));
/* 538 */     this.y2.setBounds(954, 517, 72, 20);
/* 539 */     add(this.y2);
/*     */ 
/* 541 */     this.txtLengthArea = new JTextArea();
/* 542 */     this.txtLengthArea.setVisible(false);
/* 543 */     this.txtLengthArea.setEditable(false);
/* 544 */     this.txtLengthArea.setText("Length/Area");
/* 545 */     this.txtLengthArea.setOpaque(false);
/* 546 */     this.txtLengthArea.setLineWrap(true);
/* 547 */     this.txtLengthArea.setWrapStyleWord(true);
/* 548 */     this.txtLengthArea.setFont(new Font("SansSerif", 0, 9));
/* 549 */     this.txtLengthArea.setBounds(954, 537, 72, 20);
/* 550 */     add(this.txtLengthArea);
/*     */ 
/* 552 */     this.txtNumPixels = new JTextArea();
/* 553 */     this.txtNumPixels.setVisible(false);
/* 554 */     this.txtNumPixels.setEditable(false);
/* 555 */     this.txtNumPixels.setWrapStyleWord(true);
/* 556 */     this.txtNumPixels.setText("Number of Pixels");
/* 557 */     this.txtNumPixels.setOpaque(false);
/* 558 */     this.txtNumPixels.setLineWrap(true);
/* 559 */     this.txtNumPixels.setFont(new Font("SansSerif", 0, 9));
/* 560 */     this.txtNumPixels.setBounds(954, 589, 72, 28);
/* 561 */     add(this.txtNumPixels);
/*     */ 
/* 563 */     this.lengthArea = new JTextArea();
/* 564 */     this.lengthArea.setVisible(false);
/* 565 */     this.lengthArea.setOpaque(false);
/* 566 */     this.lengthArea.setWrapStyleWord(true);
/* 567 */     this.lengthArea.setLineWrap(true);
/* 568 */     this.lengthArea.setEditable(false);
/* 569 */     this.lengthArea.setBounds(953, 555, 73, 23);
/* 570 */     add(this.lengthArea);
/*     */ 
/* 572 */     this.numPixels = new JTextArea();
/* 573 */     this.numPixels.setVisible(false);
/* 574 */     this.numPixels.setOpaque(false);
/* 575 */     this.numPixels.setWrapStyleWord(true);
/* 576 */     this.numPixels.setLineWrap(true);
/* 577 */     this.numPixels.setEditable(false);
/* 578 */     this.numPixels.setBounds(954, 620, 72, 23);
/* 579 */     add(this.numPixels);
/*     */ 
/* 581 */     this.dataPanel = new JPanel();
/* 582 */     this.dataPanel.setBorder(null);
/* 583 */     this.dataPanel.setBounds(470, 352, 450, 300);
/* 584 */     add(this.dataPanel);
/* 585 */     this.dataPanel.setLayout(null);
/*     */ 
/* 587 */     this.textField = new JTextField();
/* 588 */     this.textField.setText("Zoom");
/* 589 */     this.textField.setOpaque(false);
/* 590 */     this.textField.setEditable(false);
/* 591 */     this.textField.setColumns(10);
/* 592 */     this.textField.setBorder(null);
/* 593 */     this.textField.setBounds(929, 307, 41, 17);
/* 594 */     add(this.textField);
/*     */ 
/* 596 */     this.ndviChart = new JLabel("");
/* 597 */     this.ndviChart.addMouseListener(new MouseAdapter()
/*     */     {
/*     */       public void mousePressed(MouseEvent arg0) {
/* 600 */         new NDVIInfoDialog().setVisible(true);
/*     */       }
/*     */     });
/* 603 */     this.ndviChart.setVisible(false);
/* 604 */     this.ndviChart.setIcon(new ImageIcon(TimeSeriesPanel.class.getResource("/resources/NDVIchart.gif")));
/* 605 */     this.ndviChart.setBounds(980, 11, 46, 295);
/* 606 */     add(this.ndviChart);
/*     */ 
/* 608 */     this.displayTitles = new JCheckBox("Display Titles");
/* 609 */     this.displayTitles.setSelected(true);
/* 610 */     this.displayTitles.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 612 */         TimeSeriesPanel.this.titlesToFront(TimeSeriesPanel.this.displayTitles.isSelected());
/*     */       }
/*     */     });
/* 615 */     this.displayTitles.setBounds(930, 328, 126, 23);
/* 616 */     add(this.displayTitles);
/*     */   }
/*     */ 
/*     */   class pixelDrag extends MouseMotionAdapter
/*     */   {
/*     */     pixelDrag()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void mouseDragged(MouseEvent e)
/*     */     {
/* 632 */       TimeSeriesPanel.this.handlePixels();
/*     */     }
/*     */   }
/*     */ 
/*     */   class pixelMouse extends MouseAdapter
/*     */   {
/*     */     pixelMouse()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void mousePressed(MouseEvent e)
/*     */     {
/* 622 */       TimeSeriesPanel.this.handlePixels();
/*     */     }
/*     */ 
/*     */     public void mouseReleased(MouseEvent e) {
/* 626 */       TimeSeriesPanel.this.handlePixels();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.TimeSeriesPanel
 * JD-Core Version:    0.6.2
 */