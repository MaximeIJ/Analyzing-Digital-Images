/*     */ package org.gss.adi.dialogs;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.image.BufferedImage;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.DefaultComboBoxModel;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JRootPane;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ import net.miginfocom.swing.MigLayout;
/*     */ import org.gss.adi.JHintComboBox;
/*     */ import org.gss.adi.NumberField;
/*     */ import org.gss.adi.ZoomPanLabel;
/*     */ 
/*     */ public class RelationshipMaskDialog extends JDialog
/*     */ {
/*     */   private static final long serialVersionUID = -1418097925456491899L;
/*  34 */   private final JPanel contentPanel = new JPanel();
/*     */   private JTextField txtX;
/*     */   private JTextField textField_1;
/*     */   private JTextField textField_3;
/*     */   private JTextField color1mult;
/*     */   private JTextField color2mult;
/*     */   private JTextField color3mult;
/*     */   private JHintComboBox colorBox1;
/*     */   private JHintComboBox colorBox2;
/*     */   private JHintComboBox colorBox3;
/*     */   private JComboBox operation;
/*     */   private JComboBox equiv;
/*     */   private RelationshipMaskDialog me;
/*     */   private ZoomPanLabel label;
/*  54 */   private final int ONE = 0;
/*  55 */   private final int RED = 1;
/*  56 */   private final int GREEN = 2;
/*  57 */   private final int BLUE = 3;
/*     */ 
/*     */   public RelationshipMaskDialog(ZoomPanLabel l)
/*     */   {
/*  63 */     this.label = l;
/*  64 */     this.me = this;
/*  65 */     setAlwaysOnTop(true);
/*  66 */     setup();
/*     */   }
/*     */   private void apply() {
/*  69 */     if ((this.colorBox1.hinting()) || (this.colorBox2.hinting()) || (this.colorBox3.hinting())) {
/*  70 */       JOptionPane.showMessageDialog(null, "Select a choice for each of the color boxes. You may select a \"1\" as a constant.", null, 2);
/*  71 */       return;
/*     */     }
/*  73 */     int color1 = this.colorBox1.getSelectedIndex();
/*  74 */     int color2 = this.colorBox2.getSelectedIndex();
/*  75 */     int color3 = this.colorBox3.getSelectedIndex();
/*  76 */     BufferedImage original = this.label.getZoomedOriginal();
/*  77 */     BufferedImage img = new BufferedImage(original.getWidth(), original.getHeight(), 1);
/*  78 */     Graphics2D g = img.createGraphics();
/*  79 */     g.setColor(Color.WHITE);
/*  80 */     g.fillRect(0, 0, img.getWidth(), img.getHeight());
/*  81 */     g.dispose();
/*  82 */     if ((color1 == 0) && (color2 == 0) && (color3 == 0)) {
/*  83 */       JOptionPane.showMessageDialog(null, "At least one color must be used.", null, 2);
/*  84 */       return;
/*     */     }
/*  86 */     Executable firstColor = setExecutable(color1);
/*  87 */     Executable secondColor = setExecutable(color2);
/*  88 */     Executable thirdColor = setExecutable(color3);
/*  89 */     if (this.operation.getSelectedIndex() < 4) {
/*  90 */       Executable op = setOperation();
/*  91 */       Executable equivalency = setEquivalency();
/*  92 */       for (int i = 0; i < img.getWidth(); i++) {
/*  93 */         for (int j = 0; j < img.getHeight(); j++) {
/*  94 */           Color c = new Color(original.getRGB(i, j));
/*  95 */           Double d1 = Double.valueOf(((Double)firstColor.execute(c)).doubleValue() * Double.parseDouble(this.color1mult.getText()));
/*  96 */           Double d2 = Double.valueOf(((Double)secondColor.execute(c)).doubleValue() * Double.parseDouble(this.color2mult.getText()));
/*  97 */           Double d3 = Double.valueOf(((Double)thirdColor.execute(c)).doubleValue() * Double.parseDouble(this.color3mult.getText()));
/*  98 */           Double result = (Double)op.execute(d1, d2);
/*  99 */           if (((Boolean)equivalency.execute(result, d3)).booleanValue())
/* 100 */             img.setRGB(i, j, 0);
/*     */         }
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 106 */       Executable equivalency = new Executable()
/*     */       {
/*     */         public Boolean execute(Double item, Double range, Double compareTo, Double Null) {
/* 109 */           if ((item.doubleValue() <= compareTo.doubleValue() + range.doubleValue()) && (item.doubleValue() >= compareTo.doubleValue() - range.doubleValue())) return Boolean.valueOf(true); return Boolean.valueOf(false);
/*     */         }
/*     */       };
/* 112 */       for (int i = 0; i < img.getWidth(); i++) {
/* 113 */         for (int j = 0; j < img.getHeight(); j++) {
/* 114 */           Color c = new Color(original.getRGB(i, j));
/* 115 */           Double d1 = Double.valueOf(((Double)firstColor.execute(c)).doubleValue() * Double.parseDouble(this.color1mult.getText()));
/* 116 */           Double d2 = Double.valueOf(((Double)secondColor.execute(c)).doubleValue() * Double.parseDouble(this.color2mult.getText()));
/* 117 */           Double d3 = Double.valueOf(((Double)thirdColor.execute(c)).doubleValue() * Double.parseDouble(this.color3mult.getText()));
/* 118 */           if (((Boolean)equivalency.execute(d1, d2, d3, null)).booleanValue()) {
/* 119 */             img.setRGB(i, j, 0);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 124 */     this.label.setZoomedTool(img);
/* 125 */     this.me.dispose();
/*     */   }
/*     */ 
/*     */   private Executable<Boolean, Double, Double> setEquivalency() {
/* 129 */     switch (this.equiv.getSelectedIndex()) {
/*     */     case 0:
/* 131 */       return new Executable()
/*     */       {
/*     */         public Boolean execute(Double d1, Double d2) {
/* 134 */           if (d1.doubleValue() == d2.doubleValue()) return Boolean.valueOf(true); return Boolean.valueOf(false);
/*     */         }
/*     */       };
/*     */     case 1:
/* 138 */       return new Executable()
/*     */       {
/*     */         public Boolean execute(Double d1, Double d2) {
/* 141 */           if (d1.doubleValue() < d2.doubleValue()) return Boolean.valueOf(true); return Boolean.valueOf(false);
/*     */         }
/*     */       };
/*     */     case 2:
/* 145 */       return new Executable()
/*     */       {
/*     */         public Boolean execute(Double d1, Double d2) {
/* 148 */           if (d1.doubleValue() <= d2.doubleValue()) return Boolean.valueOf(true); return Boolean.valueOf(false);
/*     */         }
/*     */       };
/*     */     case 3:
/* 152 */       return new Executable()
/*     */       {
/*     */         public Boolean execute(Double d1, Double d2) {
/* 155 */           if (d1.doubleValue() > d2.doubleValue()) return Boolean.valueOf(true); return Boolean.valueOf(false);
/*     */         }
/*     */       };
/*     */     case 4:
/* 159 */       return new Executable()
/*     */       {
/*     */         public Boolean execute(Double d1, Double d2) {
/* 162 */           if (d1.doubleValue() >= d2.doubleValue()) return Boolean.valueOf(true); return Boolean.valueOf(false);
/*     */         }
/*     */       };
/*     */     }
/* 166 */     return null;
/*     */   }
/*     */   private Executable<Double, Double, Double> setOperation() {
/* 169 */     switch (this.operation.getSelectedIndex()) {
/*     */     case 0:
/* 171 */       return new Executable()
/*     */       {
/*     */         public Double execute(Double d1, Double d2) {
/* 174 */           return Double.valueOf(d1.doubleValue() + d2.doubleValue());
/*     */         }
/*     */       };
/*     */     case 1:
/* 178 */       return new Executable()
/*     */       {
/*     */         public Double execute(Double d1, Double d2) {
/* 181 */           return Double.valueOf(d1.doubleValue() - d2.doubleValue());
/*     */         }
/*     */       };
/*     */     case 2:
/* 185 */       return new Executable()
/*     */       {
/*     */         public Double execute(Double d1, Double d2) {
/* 188 */           return Double.valueOf(d1.doubleValue() * d2.doubleValue());
/*     */         }
/*     */       };
/*     */     case 3:
/* 192 */       return new Executable()
/*     */       {
/*     */         public Double execute(Double d1, Double d2) {
/* 195 */           return Double.valueOf(d1.doubleValue() / d2.doubleValue());
/*     */         }
/*     */       };
/*     */     }
/* 199 */     return null;
/*     */   }
/*     */   private Executable<Double, Color, Void> setExecutable(int color) {
/* 202 */     switch (color) {
/*     */     case 0:
/* 204 */       return new Executable()
/*     */       {
/*     */         public Double execute(Color c) {
/* 207 */           return new Double(1.0D);
/*     */         }
/*     */       };
/*     */     case 1:
/* 211 */       return new Executable()
/*     */       {
/*     */         public Double execute(Color c) {
/* 214 */           return new Double(c.getRed());
/*     */         }
/*     */       };
/*     */     case 2:
/* 218 */       return new Executable()
/*     */       {
/*     */         public Double execute(Color c) {
/* 221 */           return new Double(c.getGreen());
/*     */         }
/*     */       };
/*     */     case 3:
/* 225 */       return new Executable()
/*     */       {
/*     */         public Double execute(Color c) {
/* 228 */           return new Double(c.getBlue());
/*     */         }
/*     */       };
/*     */     }
/* 232 */     return null;
/*     */   }
/*     */   private void setup() {
/* 235 */     setResizable(false);
/* 236 */     setTitle("Create a Mask based on Relationships between Colors of a Digital Image");
/* 237 */     setBounds(100, 100, 900, 270);
/* 238 */     getContentPane().setLayout(new BorderLayout());
/* 239 */     this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
/* 240 */     getContentPane().add(this.contentPanel, "Center");
/* 241 */     this.contentPanel.setLayout(null);
/*     */ 
/* 243 */     JTextArea txtrCreateColorRelationships = new JTextArea();
/* 244 */     txtrCreateColorRelationships.setOpaque(false);
/* 245 */     txtrCreateColorRelationships.setFont(new Font("SansSerif", 0, 13));
/* 246 */     txtrCreateColorRelationships.setEditable(false);
/* 247 */     txtrCreateColorRelationships.setText("Create color relationships to identify spatial and spectral relationships in digital images.\r\n\r\nThis is a slow process, and for large digital images, it can be dramatically slow.  Consider trimming the size of the digital image to identify the best relationship before applying to the original, full-size image.");
/* 248 */     txtrCreateColorRelationships.setLineWrap(true);
/* 249 */     txtrCreateColorRelationships.setWrapStyleWord(true);
/* 250 */     txtrCreateColorRelationships.setBounds(10, 11, 429, 144);
/* 251 */     this.contentPanel.add(txtrCreateColorRelationships);
/*     */ 
/* 253 */     JTextArea txtrExampleShowThe = new JTextArea();
/* 254 */     txtrExampleShowThe.setOpaque(false);
/* 255 */     txtrExampleShowThe.setEditable(false);
/* 256 */     txtrExampleShowThe.setFont(new Font("SansSerif", 0, 13));
/* 257 */     txtrExampleShowThe.setText("Example: Show the pixels with more than twice as much red as blue:\r\n\r\n1 x Red + 0 x None >= 2 x Blue  OR  1 x Red * 0.5 x Blue >= 1 x None\r\n\r\nExample: Show the pixels where the red values are within 5 of green:\r\n\r\n1 x Red +/- 5 x None = 1 x Green");
/* 258 */     txtrExampleShowThe.setLineWrap(true);
/* 259 */     txtrExampleShowThe.setWrapStyleWord(true);
/* 260 */     txtrExampleShowThe.setBounds(440, 11, 454, 144);
/* 261 */     this.contentPanel.add(txtrExampleShowThe);
/*     */ 
/* 263 */     this.color1mult = new NumberField();
/* 264 */     this.color1mult.setFont(new Font("SansSerif", 0, 11));
/* 265 */     this.color1mult.setHorizontalAlignment(0);
/* 266 */     this.color1mult.setText("1");
/* 267 */     this.color1mult.setBounds(41, 156, 50, 20);
/* 268 */     this.contentPanel.add(this.color1mult);
/* 269 */     this.color1mult.setColumns(10);
/*     */ 
/* 271 */     this.txtX = new JTextField();
/* 272 */     this.txtX.setFont(new Font("SansSerif", 0, 11));
/* 273 */     this.txtX.setOpaque(false);
/* 274 */     this.txtX.setBorder(null);
/* 275 */     this.txtX.setEditable(false);
/* 276 */     this.txtX.setHorizontalAlignment(0);
/* 277 */     this.txtX.setText("X");
/* 278 */     this.txtX.setBounds(91, 156, 20, 20);
/* 279 */     this.contentPanel.add(this.txtX);
/* 280 */     this.txtX.setColumns(10);
/*     */ 
/* 282 */     this.colorBox1 = new JHintComboBox();
/* 283 */     this.colorBox1.setFont(new Font("SansSerif", 0, 11));
/* 284 */     this.colorBox1.setName("asd");
/*     */ 
/* 286 */     this.colorBox1.setModel(new DefaultComboBoxModel(new String[] { "Choose Color", "1 (constant)", "Red", "Green", "Blue" }));
/* 287 */     this.colorBox1.setBounds(110, 155, 125, 20);
/* 288 */     this.contentPanel.add(this.colorBox1);
/*     */ 
/* 290 */     this.operation = new JComboBox();
/* 291 */     this.operation.setFont(new Font("SansSerif", 0, 11));
/* 292 */     this.operation.setModel(new DefaultComboBoxModel(new String[] { "+ (Add)", "- (Minus)", "x (Multiply)", "/ (Divide)", "+/-" }));
/* 293 */     this.operation.setBounds(245, 156, 121, 20);
/* 294 */     this.contentPanel.add(this.operation);
/*     */ 
/* 296 */     this.colorBox2 = new JHintComboBox();
/* 297 */     this.colorBox2.setFont(new Font("SansSerif", 0, 11));
/* 298 */     this.colorBox2.setModel(new DefaultComboBoxModel(new String[] { "Choose Color", "1 (constant)", "Red", "Green", "Blue" }));
/* 299 */     this.colorBox2.setBounds(446, 156, 110, 20);
/* 300 */     this.contentPanel.add(this.colorBox2);
/*     */ 
/* 302 */     this.textField_1 = new JTextField();
/* 303 */     this.textField_1.setFont(new Font("SansSerif", 0, 11));
/* 304 */     this.textField_1.setText("X");
/* 305 */     this.textField_1.setOpaque(false);
/* 306 */     this.textField_1.setHorizontalAlignment(0);
/* 307 */     this.textField_1.setEditable(false);
/* 308 */     this.textField_1.setColumns(10);
/* 309 */     this.textField_1.setBorder(null);
/* 310 */     this.textField_1.setBounds(426, 156, 20, 20);
/* 311 */     this.contentPanel.add(this.textField_1);
/*     */ 
/* 313 */     this.color2mult = new NumberField();
/* 314 */     this.color2mult.setFont(new Font("SansSerif", 0, 11));
/* 315 */     this.color2mult.setText("0");
/* 316 */     this.color2mult.setHorizontalAlignment(0);
/* 317 */     this.color2mult.setColumns(10);
/* 318 */     this.color2mult.setBounds(376, 156, 50, 20);
/* 319 */     this.contentPanel.add(this.color2mult);
/*     */ 
/* 321 */     this.equiv = new JComboBox();
/* 322 */     this.equiv.setFont(new Font("SansSerif", 0, 11));
/* 323 */     this.equiv.setModel(new DefaultComboBoxModel(new String[] { "=", "<", "<=", ">", ">=" }));
/* 324 */     this.equiv.setBounds(566, 156, 70, 20);
/* 325 */     this.contentPanel.add(this.equiv);
/*     */ 
/* 327 */     this.colorBox3 = new JHintComboBox();
/* 328 */     this.colorBox3.setFont(new Font("SansSerif", 0, 11));
/* 329 */     this.colorBox3.setModel(new DefaultComboBoxModel(new String[] { "Choose Color", "1 (constant)", "Red", "Green", "Blue" }));
/* 330 */     this.colorBox3.setBounds(716, 156, 133, 20);
/* 331 */     this.contentPanel.add(this.colorBox3);
/*     */ 
/* 333 */     this.textField_3 = new JTextField();
/* 334 */     this.textField_3.setFont(new Font("SansSerif", 0, 11));
/* 335 */     this.textField_3.setText("X");
/* 336 */     this.textField_3.setOpaque(false);
/* 337 */     this.textField_3.setHorizontalAlignment(0);
/* 338 */     this.textField_3.setEditable(false);
/* 339 */     this.textField_3.setColumns(10);
/* 340 */     this.textField_3.setBorder(null);
/* 341 */     this.textField_3.setBounds(696, 156, 20, 20);
/* 342 */     this.contentPanel.add(this.textField_3);
/*     */ 
/* 344 */     this.color3mult = new NumberField();
/* 345 */     this.color3mult.setFont(new Font("SansSerif", 0, 11));
/* 346 */     this.color3mult.setText("1");
/* 347 */     this.color3mult.setHorizontalAlignment(0);
/* 348 */     this.color3mult.setColumns(10);
/* 349 */     this.color3mult.setBounds(646, 156, 50, 20);
/* 350 */     this.contentPanel.add(this.color3mult);
/*     */ 
/* 352 */     JPanel buttonPane = new JPanel();
/* 353 */     getContentPane().add(buttonPane, "South");
/* 354 */     buttonPane.setLayout(new MigLayout("", "[496.00,grow][216.00][47px][65px]", "[23px,grow]"));
/*     */ 
/* 356 */     JTextArea txtrNoteToIncrease = new JTextArea();
/* 357 */     txtrNoteToIncrease.setForeground(Color.RED);
/* 358 */     txtrNoteToIncrease.setEditable(false);
/* 359 */     txtrNoteToIncrease.setOpaque(false);
/* 360 */     txtrNoteToIncrease.setFont(new Font("SansSerif", 0, 13));
/* 361 */     txtrNoteToIncrease.setText("NOTE: To increase the flexibility for this tool, pixel values range from 0-255 rather than the previous 0-100%, respectively.");
/* 362 */     txtrNoteToIncrease.setLineWrap(true);
/* 363 */     txtrNoteToIncrease.setWrapStyleWord(true);
/* 364 */     buttonPane.add(txtrNoteToIncrease, "cell 0 0,grow");
/*     */ 
/* 366 */     Component horizontalGlue = Box.createHorizontalGlue();
/* 367 */     buttonPane.add(horizontalGlue, "cell 1 0");
/*     */ 
/* 369 */     JButton okButton = new JButton("Apply Relationship Mask");
/* 370 */     okButton.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent arg0) {
/* 372 */         RelationshipMaskDialog.this.apply();
/*     */       }
/*     */     });
/* 375 */     okButton.setActionCommand("OK");
/* 376 */     buttonPane.add(okButton, "cell 2 0,alignx left,aligny center");
/* 377 */     getRootPane().setDefaultButton(okButton);
/*     */ 
/* 380 */     JButton cancelButton = new JButton("Cancel and Close Window");
/* 381 */     cancelButton.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent arg0) {
/* 383 */         RelationshipMaskDialog.this.me.dispose();
/*     */       }
/*     */     });
/* 386 */     cancelButton.setActionCommand("Cancel");
/* 387 */     buttonPane.add(cancelButton, "cell 3 0,alignx left,aligny center");
/*     */   }
/*     */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.dialogs.RelationshipMaskDialog
 * JD-Core Version:    0.6.2
 */