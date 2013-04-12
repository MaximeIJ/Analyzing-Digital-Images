/*     */ package org.gss.adi;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.PrintStream;
/*     */ import javax.imageio.ImageIO;
/*     */ import javax.swing.DefaultComboBoxModel;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JSlider;
/*     */ import javax.swing.JSpinner;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.SpinnerNumberModel;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
/*     */ import org.gss.adi.dialogs.OGFGraphDialog;
/*     */ import org.gss.adi.dialogs.OGFHelpDialog;
/*     */ import org.gss.adi.tools.ColorTools;
/*     */ 
/*     */ public class OldGrowthForestPanel extends JPanel
/*     */   implements Toolable
/*     */ {
/*     */   private static final long serialVersionUID = 6917266928380728707L;
/*     */   private JTextField txtAnalysis;
/*     */   private Entrance entrance;
/*     */   private ZoomPanLabel img1;
/*     */   private ZoomPanLabel img2;
/*     */   private ZoomPanLabel img3;
/*     */   private ZoomPanLabel img4;
/*     */   private JSpinner x1;
/*     */   private JSpinner y1;
/*     */   private JSpinner x2;
/*     */   private JSpinner y2;
/*     */   private JTextArea pt1;
/*     */   private JTextArea pt2;
/*     */   private JComboBox comboBox;
/*     */   private ToolAdder ta;
/*     */   private JTextField txtPercentOldGrowth;
/*     */   private JTextField textField;
/*     */   private JTextField textField_2;
/*     */   private JTextField textField_3;
/*     */   private JTextField textField_4;
/*     */   private JTextField textField_1;
/*     */   private JTextField textField_5;
/*     */   private JTextField textField_6;
/*     */   private JTextField textField_7;
/*     */   private JTextField txtSelectedArea;
/*     */   private JTextField sa1620;
/*     */   private JTextField sa1850;
/*     */   private JTextField sa1926;
/*     */   private JTextField sa1990;
/*     */ 
/*     */   public OldGrowthForestPanel(Entrance ent, boolean three)
/*     */   {
/*  70 */     removeAll();
/*  71 */     setLayout(null);
/*  72 */     this.entrance = ent;
/*  73 */     this.entrance.setPane(this);
/*  74 */     setup();
/*  75 */     setImages();
/*     */     try {
/*  77 */       BufferedImage img = ImageIO.read(OldGrowthForestPanel.class.getResource("/resources/graphics/GSS Old Growth Forest/color mask.png"));
/*  78 */       this.ta = new ToolAdder(this.comboBox, this.entrance, new ZoomPanLabel[] { this.img1, this.img2, this.img3, this.img4 }, this, img, 0, new JComboBox()); } catch (Exception localException) {
/*     */     }
/*  80 */     this.entrance = ent;
/*  81 */     this.entrance.setPane(this);
/*     */   }
/*     */ 
/*     */   private void setImages()
/*     */   {
/*  86 */     addImage(1, new ImageIcon(OldGrowthForestPanel.class.getResource("/resources/graphics/GSS Old Growth Forest/NWV-p29-Forest_1620d.png")));
/*  87 */     addImage(2, new ImageIcon(OldGrowthForestPanel.class.getResource("/resources/graphics/GSS Old Growth Forest/NWV-p29-Forest_1850c.png")));
/*  88 */     addImage(3, new ImageIcon(OldGrowthForestPanel.class.getResource("/resources/graphics/GSS Old Growth Forest/NWV-p29-Forest_1926a.png")));
/*  89 */     addImage(4, new ImageIcon(OldGrowthForestPanel.class.getResource("/resources/graphics/GSS Old Growth Forest/NWV-p29-Forest_1990a.png")));
/*     */   }
/*     */   private void addImage(int i, ImageIcon img) {
/*     */     try {
/*  93 */       switch (i) {
/*     */       case 1:
/*  95 */         this.img1.setImage(ColorTools.IconToBuffered(img));
/*  96 */         break;
/*     */       case 2:
/*  98 */         this.img2.setImage(ColorTools.IconToBuffered(img));
/*  99 */         break;
/*     */       case 3:
/* 101 */         this.img3.setImage(ColorTools.IconToBuffered(img));
/* 102 */         break;
/*     */       case 4:
/* 104 */         this.img4.setImage(ColorTools.IconToBuffered(img));
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 108 */       System.out.println("on image " + i);
/* 109 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void removeDataPanel(JPanel d) {
/*     */   }
/*     */ 
/*     */   public void setDataPanel(JPanel d) {
/*     */   }
/*     */ 
/* 119 */   public void setPoint1(int x, int y) { this.x1.setVisible(true);
/* 120 */     this.y1.setVisible(true);
/* 121 */     this.pt1.setVisible(true);
/* 122 */     this.x1.setValue(Integer.valueOf(x));
/* 123 */     this.y1.setValue(Integer.valueOf(y)); }
/*     */ 
/*     */   public void setPoint2(int x, int y)
/*     */   {
/* 127 */     this.x2.setVisible(true);
/* 128 */     this.y2.setVisible(true);
/* 129 */     this.pt2.setVisible(true);
/* 130 */     this.x2.setValue(Integer.valueOf(x));
/* 131 */     this.y2.setValue(Integer.valueOf(y));
/*     */   }
/*     */ 
/*     */   public void removePoints()
/*     */   {
/* 136 */     this.x1.setVisible(false);
/* 137 */     this.y1.setVisible(false);
/* 138 */     this.pt1.setVisible(false);
/* 139 */     this.x2.setVisible(false);
/* 140 */     this.y2.setVisible(false);
/* 141 */     this.pt2.setVisible(false);
/* 142 */     this.txtSelectedArea.setVisible(false);
/* 143 */     this.sa1620.setVisible(false);
/* 144 */     this.sa1850.setVisible(false);
/* 145 */     this.sa1926.setVisible(false);
/* 146 */     this.sa1990.setVisible(false);
/*     */   }
/*     */ 
/*     */   public void growth(String[] s) {
/* 150 */     this.sa1620.setText(s[0] + "%");
/* 151 */     this.sa1850.setText(s[1] + "%");
/* 152 */     this.sa1926.setText(s[2] + "%");
/* 153 */     this.sa1990.setText(s[3] + "%");
/* 154 */     this.txtSelectedArea.setVisible(true);
/* 155 */     this.sa1620.setVisible(true);
/* 156 */     this.sa1850.setVisible(true);
/* 157 */     this.sa1926.setVisible(true);
/* 158 */     this.sa1990.setVisible(true);
/*     */   }
/*     */   private void setup() {
/* 161 */     this.comboBox = new JComboBox();
/* 162 */     this.comboBox.setModel(new DefaultComboBoxModel(new String[] { "Rectangle", "Polygon" }));
/* 163 */     this.comboBox.setToolTipText("Point (text)\r\nPoint (graph)\r\nLine\r\nPath (multiple point line)\r\nRectangle\r\nPolygon");
/* 164 */     this.comboBox.setBounds(482, 574, 144, 20);
/* 165 */     add(this.comboBox);
/*     */ 
/* 167 */     JSlider useless = new JSlider();
/* 168 */     this.img1 = new ZoomPanLabel(useless);
/* 169 */     this.img1.setVerticalAlignment(1);
/* 170 */     this.img1.setBounds(10, 0, 472, 285);
/* 171 */     add(this.img1);
/*     */ 
/* 173 */     this.img2 = new ZoomPanLabel(useless);
/* 174 */     this.img2.setVerticalAlignment(1);
/* 175 */     this.img2.setBounds(482, 0, 472, 285);
/* 176 */     add(this.img2);
/*     */ 
/* 178 */     this.img3 = new ZoomPanLabel(useless);
/* 179 */     this.img3.setVerticalAlignment(1);
/* 180 */     this.img3.setBounds(10, 285, 472, 285);
/* 181 */     add(this.img3);
/*     */ 
/* 183 */     this.img4 = new ZoomPanLabel(useless);
/* 184 */     this.img4.setVerticalAlignment(1);
/* 185 */     this.img4.setBounds(482, 285, 472, 285);
/* 186 */     add(this.img4);
/*     */ 
/* 188 */     this.img4.sync(this.img3);
/* 189 */     this.img4.sync(this.img2);
/* 190 */     this.img4.sync(this.img1);
/*     */ 
/* 192 */     this.txtAnalysis = new JTextField();
/* 193 */     this.txtAnalysis.setEditable(false);
/* 194 */     this.txtAnalysis.setText("Analysis");
/* 195 */     this.txtAnalysis.setOpaque(false);
/* 196 */     this.txtAnalysis.setColumns(10);
/* 197 */     this.txtAnalysis.setBorder(null);
/* 198 */     this.txtAnalysis.setBounds(429, 574, 53, 20);
/* 199 */     add(this.txtAnalysis);
/*     */ 
/* 201 */     this.pt1 = new JTextArea();
/* 202 */     this.pt1.setRows(2);
/* 203 */     this.pt1.setFont(new Font("SansSerif", 0, 13));
/* 204 */     this.pt1.setText("Pos1");
/* 205 */     this.pt1.setOpaque(false);
/* 206 */     this.pt1.setWrapStyleWord(true);
/* 207 */     this.pt1.setLineWrap(true);
/* 208 */     this.pt1.setBounds(965, 363, 58, 47);
/* 209 */     add(this.pt1);
/*     */ 
/* 211 */     this.x1 = new JSpinner();
/* 212 */     this.x1.addChangeListener(new ChangeListener() {
/*     */       public void stateChanged(ChangeEvent arg0) {
/* 214 */         OldGrowthForestPanel.this.ta.mvPoint1(((Integer)OldGrowthForestPanel.this.x1.getValue()).intValue(), ((Integer)OldGrowthForestPanel.this.y1.getValue()).intValue());
/*     */       }
/*     */     });
/* 217 */     this.x1.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
/* 218 */     this.x1.setOpaque(false);
/* 219 */     this.x1.setFont(new Font("SansSerif", 0, 11));
/* 220 */     this.x1.setBounds(965, 410, 72, 20);
/* 221 */     add(this.x1);
/*     */ 
/* 223 */     this.y1 = new JSpinner();
/* 224 */     this.y1.addChangeListener(new ChangeListener() {
/*     */       public void stateChanged(ChangeEvent arg0) {
/* 226 */         OldGrowthForestPanel.this.ta.mvPoint1(((Integer)OldGrowthForestPanel.this.x1.getValue()).intValue(), ((Integer)OldGrowthForestPanel.this.y1.getValue()).intValue());
/*     */       }
/*     */     });
/* 229 */     this.y1.setOpaque(false);
/* 230 */     this.y1.setFont(new Font("SansSerif", 0, 11));
/* 231 */     this.y1.setBounds(965, 431, 72, 20);
/* 232 */     add(this.y1);
/*     */ 
/* 234 */     this.pt2 = new JTextArea();
/* 235 */     this.pt2.setRows(2);
/* 236 */     this.pt2.setWrapStyleWord(true);
/* 237 */     this.pt2.setText("Pos2");
/* 238 */     this.pt2.setOpaque(false);
/* 239 */     this.pt2.setLineWrap(true);
/* 240 */     this.pt2.setFont(new Font("SansSerif", 0, 13));
/* 241 */     this.pt2.setBounds(965, 450, 58, 47);
/* 242 */     add(this.pt2);
/*     */ 
/* 244 */     this.x2 = new JSpinner();
/* 245 */     this.x2.addChangeListener(new ChangeListener() {
/*     */       public void stateChanged(ChangeEvent e) {
/* 247 */         OldGrowthForestPanel.this.ta.mvPoint2(((Integer)OldGrowthForestPanel.this.x2.getValue()).intValue(), ((Integer)OldGrowthForestPanel.this.y2.getValue()).intValue());
/*     */       }
/*     */     });
/* 250 */     this.x2.setOpaque(false);
/* 251 */     this.x2.setFont(new Font("SansSerif", 0, 11));
/* 252 */     this.x2.setBounds(965, 497, 72, 20);
/* 253 */     add(this.x2);
/*     */ 
/* 255 */     this.y2 = new JSpinner();
/* 256 */     this.y2.addChangeListener(new ChangeListener() {
/*     */       public void stateChanged(ChangeEvent arg0) {
/* 258 */         OldGrowthForestPanel.this.ta.mvPoint2(((Integer)OldGrowthForestPanel.this.x2.getValue()).intValue(), ((Integer)OldGrowthForestPanel.this.y2.getValue()).intValue());
/*     */       }
/*     */     });
/* 261 */     this.y2.setOpaque(false);
/* 262 */     this.y2.setFont(new Font("SansSerif", 0, 11));
/* 263 */     this.y2.setBounds(965, 517, 72, 20);
/* 264 */     add(this.y2);
/*     */ 
/* 266 */     this.txtPercentOldGrowth = new JTextField();
/* 267 */     this.txtPercentOldGrowth.setOpaque(false);
/* 268 */     this.txtPercentOldGrowth.setBorder(null);
/* 269 */     this.txtPercentOldGrowth.setEditable(false);
/* 270 */     this.txtPercentOldGrowth.setHorizontalAlignment(0);
/* 271 */     this.txtPercentOldGrowth.setText("Percent Old Growth Forest");
/* 272 */     this.txtPercentOldGrowth.setBounds(10, 579, 176, 15);
/* 273 */     add(this.txtPercentOldGrowth);
/* 274 */     this.txtPercentOldGrowth.setColumns(10);
/*     */ 
/* 276 */     this.textField = new JTextField();
/* 277 */     this.textField.setOpaque(false);
/* 278 */     this.textField.setText("1620");
/* 279 */     this.textField.setHorizontalAlignment(0);
/* 280 */     this.textField.setEditable(false);
/* 281 */     this.textField.setBorder(null);
/* 282 */     this.textField.setBounds(52, 595, 47, 15);
/* 283 */     add(this.textField);
/* 284 */     this.textField.setColumns(10);
/*     */ 
/* 286 */     this.textField_2 = new JTextField();
/* 287 */     this.textField_2.setOpaque(false);
/* 288 */     this.textField_2.setText("1850");
/* 289 */     this.textField_2.setHorizontalAlignment(0);
/* 290 */     this.textField_2.setEditable(false);
/* 291 */     this.textField_2.setColumns(10);
/* 292 */     this.textField_2.setBorder(null);
/* 293 */     this.textField_2.setBounds(52, 610, 47, 15);
/* 294 */     add(this.textField_2);
/*     */ 
/* 296 */     this.textField_3 = new JTextField();
/* 297 */     this.textField_3.setOpaque(false);
/* 298 */     this.textField_3.setText("1926");
/* 299 */     this.textField_3.setHorizontalAlignment(0);
/* 300 */     this.textField_3.setEditable(false);
/* 301 */     this.textField_3.setColumns(10);
/* 302 */     this.textField_3.setBorder(null);
/* 303 */     this.textField_3.setBounds(52, 625, 47, 15);
/* 304 */     add(this.textField_3);
/*     */ 
/* 306 */     this.textField_4 = new JTextField();
/* 307 */     this.textField_4.setOpaque(false);
/* 308 */     this.textField_4.setText("1990");
/* 309 */     this.textField_4.setHorizontalAlignment(0);
/* 310 */     this.textField_4.setEditable(false);
/* 311 */     this.textField_4.setColumns(10);
/* 312 */     this.textField_4.setBorder(null);
/* 313 */     this.textField_4.setBounds(52, 640, 47, 15);
/* 314 */     add(this.textField_4);
/*     */ 
/* 316 */     this.textField_1 = new JTextField();
/* 317 */     this.textField_1.setOpaque(false);
/* 318 */     this.textField_1.setText("2%");
/* 319 */     this.textField_1.setHorizontalAlignment(0);
/* 320 */     this.textField_1.setEditable(false);
/* 321 */     this.textField_1.setColumns(10);
/* 322 */     this.textField_1.setBorder(null);
/* 323 */     this.textField_1.setBounds(126, 640, 47, 15);
/* 324 */     add(this.textField_1);
/*     */ 
/* 326 */     this.textField_5 = new JTextField();
/* 327 */     this.textField_5.setOpaque(false);
/* 328 */     this.textField_5.setText("10.2%");
/* 329 */     this.textField_5.setHorizontalAlignment(0);
/* 330 */     this.textField_5.setEditable(false);
/* 331 */     this.textField_5.setColumns(10);
/* 332 */     this.textField_5.setBorder(null);
/* 333 */     this.textField_5.setBounds(126, 625, 47, 15);
/* 334 */     add(this.textField_5);
/*     */ 
/* 336 */     this.textField_6 = new JTextField();
/* 337 */     this.textField_6.setOpaque(false);
/* 338 */     this.textField_6.setText("40.2%");
/* 339 */     this.textField_6.setHorizontalAlignment(0);
/* 340 */     this.textField_6.setEditable(false);
/* 341 */     this.textField_6.setColumns(10);
/* 342 */     this.textField_6.setBorder(null);
/* 343 */     this.textField_6.setBounds(126, 610, 47, 15);
/* 344 */     add(this.textField_6);
/*     */ 
/* 346 */     this.textField_7 = new JTextField();
/* 347 */     this.textField_7.setOpaque(false);
/* 348 */     this.textField_7.setText("48.4%");
/* 349 */     this.textField_7.setHorizontalAlignment(0);
/* 350 */     this.textField_7.setEditable(false);
/* 351 */     this.textField_7.setColumns(10);
/* 352 */     this.textField_7.setBorder(null);
/* 353 */     this.textField_7.setBounds(126, 595, 47, 15);
/* 354 */     add(this.textField_7);
/*     */ 
/* 356 */     this.txtSelectedArea = new JTextField();
/* 357 */     this.txtSelectedArea.setOpaque(false);
/* 358 */     this.txtSelectedArea.setVisible(false);
/* 359 */     this.txtSelectedArea.setText("Selected Area");
/* 360 */     this.txtSelectedArea.setHorizontalAlignment(0);
/* 361 */     this.txtSelectedArea.setEditable(false);
/* 362 */     this.txtSelectedArea.setColumns(10);
/* 363 */     this.txtSelectedArea.setBorder(null);
/* 364 */     this.txtSelectedArea.setBounds(195, 579, 101, 15);
/* 365 */     add(this.txtSelectedArea);
/*     */ 
/* 367 */     this.sa1620 = new JTextField();
/* 368 */     this.sa1620.setOpaque(false);
/* 369 */     this.sa1620.setVisible(false);
/* 370 */     this.sa1620.setHorizontalAlignment(0);
/* 371 */     this.sa1620.setEditable(false);
/* 372 */     this.sa1620.setColumns(10);
/* 373 */     this.sa1620.setBorder(null);
/* 374 */     this.sa1620.setBounds(223, 595, 47, 15);
/* 375 */     add(this.sa1620);
/*     */ 
/* 377 */     this.sa1850 = new JTextField();
/* 378 */     this.sa1850.setOpaque(false);
/* 379 */     this.sa1850.setVisible(false);
/* 380 */     this.sa1850.setHorizontalAlignment(0);
/* 381 */     this.sa1850.setEditable(false);
/* 382 */     this.sa1850.setColumns(10);
/* 383 */     this.sa1850.setBorder(null);
/* 384 */     this.sa1850.setBounds(223, 610, 47, 15);
/* 385 */     add(this.sa1850);
/*     */ 
/* 387 */     this.sa1926 = new JTextField();
/* 388 */     this.sa1926.setOpaque(false);
/* 389 */     this.sa1926.setVisible(false);
/* 390 */     this.sa1926.setHorizontalAlignment(0);
/* 391 */     this.sa1926.setEditable(false);
/* 392 */     this.sa1926.setColumns(10);
/* 393 */     this.sa1926.setBorder(null);
/* 394 */     this.sa1926.setBounds(223, 625, 47, 15);
/* 395 */     add(this.sa1926);
/*     */ 
/* 397 */     this.sa1990 = new JTextField();
/* 398 */     this.sa1990.setOpaque(false);
/* 399 */     this.sa1990.setVisible(false);
/* 400 */     this.sa1990.setHorizontalAlignment(0);
/* 401 */     this.sa1990.setEditable(false);
/* 402 */     this.sa1990.setColumns(10);
/* 403 */     this.sa1990.setBorder(null);
/* 404 */     this.sa1990.setBounds(223, 640, 47, 15);
/* 405 */     add(this.sa1990);
/*     */ 
/* 407 */     JButton btnGraphData = new JButton("Graph Data");
/* 408 */     btnGraphData.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent arg0) {
/*     */         try {
/* 411 */           String s = OldGrowthForestPanel.this.sa1620.getText();
/* 412 */           Float[] f = new Float[4];
/* 413 */           f[0] = Float.valueOf(Float.parseFloat(s.substring(0, s.length() - 1)));
/* 414 */           s = OldGrowthForestPanel.this.sa1850.getText();
/* 415 */           f[1] = Float.valueOf(Float.parseFloat(s.substring(0, s.length() - 1)));
/* 416 */           s = OldGrowthForestPanel.this.sa1926.getText();
/* 417 */           f[2] = Float.valueOf(Float.parseFloat(s.substring(0, s.length() - 1)));
/* 418 */           s = OldGrowthForestPanel.this.sa1990.getText();
/* 419 */           f[3] = Float.valueOf(Float.parseFloat(s.substring(0, s.length() - 1)));
/* 420 */           new OGFGraphDialog(f);
/*     */         }
/*     */         catch (Exception e) {
/* 423 */           new OGFGraphDialog(new Float[1]);
/*     */         }
/*     */       }
/*     */     });
/* 428 */     btnGraphData.setBounds(306, 579, 113, 23);
/* 429 */     add(btnGraphData);
/*     */ 
/* 431 */     JButton btnHelp = new JButton("Help");
/* 432 */     btnHelp.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent arg0) {
/* 434 */         new OGFHelpDialog().setVisible(true);
/*     */       }
/*     */     });
/* 437 */     btnHelp.setBounds(306, 606, 113, 23);
/* 438 */     add(btnHelp);
/*     */   }
/*     */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.OldGrowthForestPanel
 * JD-Core Version:    0.6.2
 */