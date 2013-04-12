/*     */ package org.gss.adi.dialogs;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Point;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.FocusAdapter;
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseMotionAdapter;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.print.PageFormat;
/*     */ import java.awt.print.Printable;
/*     */ import java.awt.print.PrinterException;
/*     */ import java.awt.print.PrinterJob;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import javax.imageio.ImageIO;
/*     */ import javax.swing.ButtonGroup;
/*     */ import javax.swing.JCheckBoxMenuItem;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuBar;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JRadioButtonMenuItem;
/*     */ import javax.swing.JSeparator;
/*     */ import javax.swing.JSlider;
/*     */ import javax.swing.JSpinner;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.KeyStroke;
/*     */ import javax.swing.SpinnerNumberModel;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
/*     */ import org.eclipse.swt.widgets.Shell;
/*     */ import org.gss.adi.AutosizeTextArea;
/*     */ import org.gss.adi.Entrance;
/*     */ import org.gss.adi.ImageFilter;
/*     */ import org.gss.adi.ZoomPanLabel;
/*     */ 
/*     */ public class AnnotateImageDialog extends JDialog
/*     */ {
/*     */   private static final long serialVersionUID = -1592278417190635024L;
/*  57 */   private ArrayList<BufferedImage> imgHistory = new ArrayList();
/*  58 */   private int historyIndex = 0;
/*     */   private Entrance entrance;
/*     */   private ZoomPanLabel label;
/*     */   public static final byte IMAGE = 0;
/*     */   public static final byte ENHANCED = 1;
/*     */   public static final byte MASKED = 2;
/*     */   private Color color;
/*     */   private int width;
/*     */   private int height;
/*     */   private BufferedImage currentImage;
/*  72 */   private byte tool = 0;
/*     */   private static final byte BRUSH = 0;
/*     */   private static final byte LINE = 1;
/*     */   private static final byte RECTANGLE = 2;
/*     */   private static final byte TEXT = 3;
/*  78 */   private byte size = 1;
/*     */   int startx;
/*     */   int starty;
/*  83 */   private final ButtonGroup buttonGroup = new ButtonGroup();
/*     */   private JRadioButtonMenuItem black;
/*     */   private JRadioButtonMenuItem blue;
/*     */   private JRadioButtonMenuItem cyan;
/*     */   private JRadioButtonMenuItem gray;
/*     */   private JRadioButtonMenuItem green;
/*     */   private JRadioButtonMenuItem magenta;
/*     */   private JRadioButtonMenuItem orange;
/*     */   private JRadioButtonMenuItem red;
/*     */   private JRadioButtonMenuItem white;
/*     */   private JRadioButtonMenuItem yellow;
/*  94 */   private final ButtonGroup buttonGroup_1 = new ButtonGroup();
/*  95 */   private final ButtonGroup buttonGroup_2 = new ButtonGroup();
/*     */   private JRadioButtonMenuItem brush;
/*     */   private JRadioButtonMenuItem line;
/*     */   private JRadioButtonMenuItem rectangle;
/*     */   private JRadioButtonMenuItem size1;
/*     */   private JRadioButtonMenuItem size2;
/*     */   private JRadioButtonMenuItem size3;
/*     */   private JRadioButtonMenuItem rdbtnmntmText;
/*     */   private JTextArea text;
/*     */   private JMenu mnFont;
/*     */   private JMenu mnSize;
/*     */   private JRadioButtonMenuItem trebuchet;
/*     */   private JRadioButtonMenuItem arial;
/*     */   private JRadioButtonMenuItem calibri;
/*     */   private JRadioButtonMenuItem arBerkeley;
/*     */   private JRadioButtonMenuItem georgia;
/* 111 */   private final ButtonGroup buttonGroup_3 = new ButtonGroup();
/*     */   private JRadioButtonMenuItem sansSerrif;
/*     */   private JRadioButtonMenuItem timesNewRoman;
/*     */   private JSpinner spinner;
/*     */   private JCheckBoxMenuItem bold;
/* 116 */   JMenuItem mntmRedo = new JMenuItem("Redo");
/* 117 */   JMenuItem mntmUndo = new JMenuItem("Undo");
/*     */   private JMenu mnFile;
/*     */   private JMenuItem mntmPrint;
/*     */ 
/*     */   public AnnotateImageDialog(Entrance e)
/*     */   {
/* 124 */     String[] option = new String[0];
/* 125 */     boolean onlyOriginal = false;
/* 126 */     if ((e.getEnhancedImage() != null) && (e.getMaskedImage() != null))
/* 127 */       option = new String[] { "Annotate Original Image", "Annotate Enhanced Image", "Annotate Masked Image" };
/* 128 */     else if (e.getEnhancedImage() != null)
/* 129 */       option = new String[] { "Annotate Original Image", "Annotate Enhance Image" };
/* 130 */     else if (e.getMaskedImage() != null)
/* 131 */       option = new String[] { "Annotate Original Image", "Annotate Masked Image" };
/*     */     else
/* 133 */       onlyOriginal = true;
/* 134 */     int imgType = 0;
/* 135 */     if (!onlyOriginal)
/* 136 */       imgType = JOptionPane.showOptionDialog(null, null, null, 0, -1, null, option, Integer.valueOf(0));
/* 137 */     setBounds(100, 100, 678, 642);
/* 138 */     getContentPane().setLayout(null);
/* 139 */     this.entrance = e;
/* 140 */     setup();
/* 141 */     switch (imgType) {
/*     */     case 0:
/* 143 */       this.label.setImage(this.entrance.getImage());
/* 144 */       break;
/*     */     case 1:
/* 146 */       this.label.setImage(this.entrance.getEnhancedImage());
/* 147 */       break;
/*     */     case 2:
/* 149 */       this.label.setImage(this.entrance.getMaskedImage());
/*     */     }
/*     */ 
/* 152 */     setAlwaysOnTop(true);
/* 153 */     this.label.setImage(this.label.getZoomedOriginal());
/* 154 */     this.width = this.label.getZoomedOriginal().getWidth();
/* 155 */     this.height = this.label.getZoomedOriginal().getHeight();
/* 156 */     this.color = this.entrance.getColor();
/* 157 */     if (this.color.equals(Color.BLACK))
/* 158 */       this.black.setSelected(true);
/* 159 */     else if (this.color.equals(Color.BLUE))
/* 160 */       this.blue.setSelected(true);
/* 161 */     else if (this.color.equals(Color.CYAN))
/* 162 */       this.cyan.setSelected(true);
/* 163 */     else if (this.color.equals(Color.GRAY))
/* 164 */       this.gray.setSelected(true);
/* 165 */     else if (this.color.equals(Color.GREEN))
/* 166 */       this.green.setSelected(true);
/* 167 */     else if (this.color.equals(Color.MAGENTA))
/* 168 */       this.magenta.setSelected(true);
/* 169 */     else if (this.color.equals(Color.ORANGE))
/* 170 */       this.orange.setSelected(true);
/* 171 */     else if (this.color.equals(Color.RED))
/* 172 */       this.red.setSelected(true);
/* 173 */     else if (this.color.equals(Color.WHITE))
/* 174 */       this.white.setSelected(true);
/* 175 */     else if (this.color.equals(Color.YELLOW))
/* 176 */       this.yellow.setSelected(true);
/* 177 */     this.text.setForeground(this.color);
/* 178 */     BufferedImage img = this.label.getZoomedOriginal();
/* 179 */     Graphics g = img.getGraphics();
/* 180 */     g.drawString("Testin", 0, 0);
/* 181 */     g.dispose();
/* 182 */     this.label.setZoomedOriginal(img);
/* 183 */     this.imgHistory.add(this.label.getZoomedOriginal());
/*     */   }
/*     */   private void mousePress(MouseEvent e) {
/* 186 */     this.label.grabFocus();
/* 187 */     Point p = this.label.mapToPixel(e.getX(), e.getY());
/* 188 */     int x = p.x;
/* 189 */     int y = p.y;
/* 190 */     this.currentImage = new BufferedImage(this.width, this.height, 1);
/* 191 */     Graphics2D g = this.currentImage.createGraphics();
/* 192 */     g.drawImage(this.label.getZoomedOriginal(), 0, 0, null, null);
/* 193 */     g.setColor(this.color);
/* 194 */     this.startx = x;
/* 195 */     this.starty = y;
/* 196 */     g.setStroke(new BasicStroke(this.size));
/* 197 */     if (this.tool == 0) {
/* 198 */       g.drawLine(x - this.size / 2, y - this.size / 2, x + this.size / 2, y + this.size / 2);
/* 199 */       this.label.setZoomedOriginal(this.currentImage);
/* 200 */     } else if (this.tool == 1) {
/* 201 */       g.drawLine(x, y, x, y);
/* 202 */     } else if (this.tool == 2) {
/* 203 */       g.drawLine(x, y, x, y);
/*     */     }
/* 205 */     g.dispose();
/* 206 */     this.label.setZoomedTool(this.currentImage);
/*     */   }
/*     */   private void mouseDrag(MouseEvent e) {
/* 209 */     Point p = this.label.mapToPixel(e.getX(), e.getY());
/* 210 */     int x = p.x;
/* 211 */     int y = p.y;
/* 212 */     this.currentImage = new BufferedImage(this.width, this.height, 1);
/* 213 */     Graphics2D g = this.currentImage.createGraphics();
/* 214 */     g.drawImage(this.label.getZoomedOriginal(), 0, 0, null, null);
/* 215 */     g.setColor(this.color);
/* 216 */     g.setStroke(new BasicStroke(this.size));
/* 217 */     if (this.tool == 0) {
/* 218 */       g.drawLine(this.startx, this.starty, x, y);
/* 219 */       this.startx = x;
/* 220 */       this.starty = y;
/* 221 */       this.label.setZoomedOriginal(this.currentImage);
/* 222 */     } else if (this.tool == 1) {
/* 223 */       g.drawLine(this.startx, this.starty, x, y);
/* 224 */     } else if (this.tool == 2)
/*     */     {
/*     */       int xmin;
/* 226 */       if (this.startx <= x)
/* 227 */         xmin = this.startx;
/*     */       else
/* 229 */         xmin = x;
/*     */       int ymin;
/* 230 */       if (this.starty <= y)
/* 231 */         ymin = this.starty;
/* 232 */       else ymin = y;
/* 233 */       g.drawRect(xmin, ymin, Math.abs(x - this.startx), Math.abs(y - this.starty));
/*     */     } else {
/* 235 */       this.text.setLocation(this.label.getX() + e.getX(), this.label.getY() + e.getY());
/* 236 */       this.text.setVisible(true);
/*     */     }
/* 238 */     g.dispose();
/* 239 */     this.label.setZoomedTool(this.currentImage);
/*     */   }
/*     */   private void mouseRelease(MouseEvent e) {
/* 242 */     Point p = this.label.mapToPixel(e.getX(), e.getY());
/* 243 */     int x = p.x;
/* 244 */     int y = p.y;
/* 245 */     this.currentImage = new BufferedImage(this.width, this.height, 1);
/* 246 */     Graphics2D g = this.currentImage.createGraphics();
/* 247 */     g.drawImage(this.label.getZoomedOriginal(), 0, 0, null, null);
/* 248 */     g.setColor(this.color);
/* 249 */     g.setStroke(new BasicStroke(this.size));
/* 250 */     if (this.tool == 0) {
/* 251 */       g.drawLine(this.startx, this.starty, x, y);
/* 252 */       this.startx = x;
/* 253 */       this.starty = y;
/* 254 */     } else if (this.tool == 1) {
/* 255 */       g.drawLine(this.startx, this.starty, x, y);
/* 256 */     } else if (this.tool == 2)
/*     */     {
/*     */       int xmin;
/* 258 */       if (this.startx <= x)
/* 259 */         xmin = this.startx;
/*     */       else
/* 261 */         xmin = x;
/*     */       int ymin;
/* 262 */       if (this.starty <= y)
/* 263 */         ymin = this.starty;
/* 264 */       else ymin = y;
/* 265 */       g.drawRect(xmin, ymin, Math.abs(x - this.startx), Math.abs(y - this.starty));
/*     */     } else {
/* 267 */       this.text.setLocation(this.label.getX() + e.getX(), this.label.getY() + e.getY());
/* 268 */       this.text.setVisible(true);
/*     */     }
/* 270 */     g.dispose();
/* 271 */     this.label.setZoomedOriginal(this.currentImage);
/* 272 */     this.historyIndex += 1;
/* 273 */     for (int i = this.historyIndex; i < this.imgHistory.size(); i++) {
/* 274 */       this.imgHistory.remove(this.historyIndex);
/*     */     }
/* 276 */     this.imgHistory.add(this.currentImage);
/* 277 */     this.mntmRedo.setVisible(false);
/* 278 */     this.mntmUndo.setVisible(true);
/*     */   }
/*     */   private void setup() {
/* 281 */     this.label = new ZoomPanLabel(new JSlider());
/* 282 */     this.label.getLabel().addMouseMotionListener(new MouseMotionAdapter()
/*     */     {
/*     */       public void mouseDragged(MouseEvent e) {
/* 285 */         AnnotateImageDialog.this.mouseDrag(e);
/*     */       }
/*     */     });
/* 288 */     this.label.getLabel().addMouseListener(new MouseAdapter()
/*     */     {
/*     */       public void mousePressed(MouseEvent e) {
/* 291 */         AnnotateImageDialog.this.mousePress(e);
/*     */       }
/*     */ 
/*     */       public void mouseReleased(MouseEvent e) {
/* 295 */         AnnotateImageDialog.this.mouseRelease(e);
/*     */       }
/*     */     });
/* 299 */     this.text = new AutosizeTextArea();
/* 300 */     this.text.setVisible(false);
/* 301 */     this.text.setFont(new Font("SansSerif", 0, 13));
/* 302 */     this.text.addFocusListener(new FocusAdapter()
/*     */     {
/*     */       public void focusLost(FocusEvent e) {
/* 305 */         AnnotateImageDialog.this.text.setVisible(false);
/* 306 */         BufferedImage img = AnnotateImageDialog.this.label.getZoomedTool();
/* 307 */         Graphics2D g = img.createGraphics();
/* 308 */         g.setColor(AnnotateImageDialog.this.color);
/* 309 */         g.setFont(AnnotateImageDialog.this.text.getFont());
/* 310 */         g.drawString(AnnotateImageDialog.this.text.getText(), AnnotateImageDialog.this.text.getX() - AnnotateImageDialog.this.label.getX(), AnnotateImageDialog.this.text.getY() - AnnotateImageDialog.this.label.getY() + 3 * AnnotateImageDialog.this.text.getHeight() / 4);
/* 311 */         g.dispose();
/* 312 */         AnnotateImageDialog.this.label.setZoomedOriginal(img);
/* 313 */         AnnotateImageDialog.this.text.setText("");
/*     */       }
/*     */     });
/* 316 */     this.text.setLocation(10, 10);
/*     */ 
/* 318 */     getContentPane().add(this.text);
/* 319 */     this.label.setBounds(10, 11, 640, 480);
/* 320 */     getContentPane().add(this.label);
/*     */ 
/* 322 */     JMenuBar menuBar = new JMenuBar();
/* 323 */     setJMenuBar(menuBar);
/*     */ 
/* 325 */     this.mnFile = new JMenu("File");
/* 326 */     menuBar.add(this.mnFile);
/*     */ 
/* 328 */     JMenuItem mntmSave = new JMenuItem("Save");
/* 329 */     mntmSave.addActionListener(new ActionListener() {
/* 332 */       public void actionPerformed(ActionEvent e) { int i = 0;
/*     */         File f;
/*     */         try {
/* 334 */           org.eclipse.swt.widgets.FileDialog fc = new org.eclipse.swt.widgets.FileDialog(new Shell(), 8192);
/* 335 */           String[] fileTypes = { "JPG", "PNG", "JPEG", "BMP", "GIF" };
/* 336 */           String[] fileExtensions = { "*.jpg", "*.png", "*.jpeg", "*.bmp", "*.gif" };
/* 337 */           fc.setFilterExtensions(fileExtensions);
/* 338 */           fc.setFilterNames(fileTypes);
/* 339 */           fc.setFileName("New_Image.jpg");
/* 340 */           fc.setOverwrite(true);
/* 341 */           fc.setText("Save Image");
/* 342 */           String path = fc.open();
/* 343 */           if (path == null)
/* 344 */             return;
/* 345 */           f = new File(path);
/* 346 */           i = fc.getFilterIndex();
/*     */         } catch (Throwable t) {
/* 348 */           java.awt.FileDialog fc = new java.awt.FileDialog(AnnotateImageDialog.this.entrance.getMainFrame());
/* 349 */           fc.setFilenameFilter(new ImageFilter());
/* 350 */           fc.setMode(1);
/* 351 */           if (AnnotateImageDialog.this.entrance.getLastDirectory() != null)
/* 352 */             fc.setDirectory(AnnotateImageDialog.this.entrance.getLastDirectory());
/* 353 */           fc.setVisible(true);
/* 354 */           String s = fc.getFile();
/* 355 */           if (s == null)
/* 356 */             return;
/* 357 */           f = new File(fc.getDirectory() + s);
/*     */         }
/*     */         try {
/* 360 */           if (f != null) {
/* 361 */             String s = f.getName();
/* 362 */             if (!s.contains(".")) {
/* 363 */               switch (i) {
/*     */               case 0:
/* 365 */                 s = s + ".jpg";
/* 366 */                 break;
/*     */               case 1:
/* 368 */                 s = s + ".png";
/* 369 */                 break;
/*     */               case 2:
/* 371 */                 s = s + ".jpeg";
/* 372 */                 break;
/*     */               case 3:
/* 374 */                 s = s + ".bmp";
/* 375 */                 break;
/*     */               case 4:
/* 377 */                 s = s + ".gif";
/* 378 */                 break;
/*     */               case 5:
/* 380 */                 s = s + ".wbmp";
/*     */               }
/*     */             }
/*     */ 
/* 384 */             ImageIO.write(AnnotateImageDialog.this.currentImage, s.substring(s.lastIndexOf('.') + 1), f);
/*     */           }
/*     */         }
/*     */         catch (IOException localIOException)
/*     */         {
/*     */         }
/*     */       }
/*     */     });
/* 390 */     this.mnFile.add(mntmSave);
/*     */ 
/* 392 */     this.mntmPrint = new JMenuItem("Print");
/* 393 */     this.mntmPrint.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 395 */         PrinterJob pj = PrinterJob.getPrinterJob();
/* 396 */         pj.setPrintable(new Printable()
/*     */         {
/*     */           public int print(Graphics graphics, PageFormat pf, int pageIndex) throws PrinterException
/*     */           {
/* 400 */             if (pageIndex > 0) {
/* 401 */               return 1;
/*     */             }
/* 403 */             Graphics2D g2d = (Graphics2D)graphics;
/* 404 */             g2d.translate(pf.getImageableX(), pf.getImageableY());
/* 405 */             g2d.drawImage(AnnotateImageDialog.this.currentImage, 0, 0, null);
/* 406 */             return 0;
/*     */           }
/*     */         });
/* 409 */         if (pj.printDialog())
/*     */           try {
/* 411 */             pj.print();
/*     */           } catch (PrinterException e1) {
/* 413 */             e1.printStackTrace();
/*     */           }
/*     */       }
/*     */     });
/* 418 */     this.mnFile.add(this.mntmPrint);
/*     */ 
/* 420 */     JMenu mnTool = new JMenu("Tool");
/* 421 */     menuBar.add(mnTool);
/*     */ 
/* 423 */     JMenu mnColor = new JMenu("Color");
/* 424 */     mnTool.add(mnColor);
/*     */ 
/* 426 */     this.black = new JRadioButtonMenuItem("Black");
/* 427 */     this.black.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 429 */         AnnotateImageDialog.this.black.setSelected(true);
/* 430 */         AnnotateImageDialog.this.color = Color.BLACK;
/* 431 */         AnnotateImageDialog.this.text.setForeground(AnnotateImageDialog.this.color);
/*     */       }
/*     */     });
/* 434 */     this.buttonGroup.add(this.black);
/* 435 */     mnColor.add(this.black);
/*     */ 
/* 437 */     this.blue = new JRadioButtonMenuItem("Blue");
/* 438 */     this.blue.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 440 */         AnnotateImageDialog.this.blue.setSelected(true);
/* 441 */         AnnotateImageDialog.this.color = Color.BLUE;
/* 442 */         AnnotateImageDialog.this.text.setForeground(AnnotateImageDialog.this.color);
/*     */       }
/*     */     });
/* 445 */     this.buttonGroup.add(this.blue);
/* 446 */     mnColor.add(this.blue);
/*     */ 
/* 448 */     this.cyan = new JRadioButtonMenuItem("Cyan");
/* 449 */     this.cyan.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 451 */         AnnotateImageDialog.this.cyan.setSelected(true);
/* 452 */         AnnotateImageDialog.this.color = Color.CYAN;
/* 453 */         AnnotateImageDialog.this.text.setForeground(AnnotateImageDialog.this.color);
/*     */       }
/*     */     });
/* 456 */     this.buttonGroup.add(this.cyan);
/* 457 */     mnColor.add(this.cyan);
/*     */ 
/* 459 */     this.gray = new JRadioButtonMenuItem("Gray");
/* 460 */     this.gray.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 462 */         AnnotateImageDialog.this.gray.setSelected(true);
/* 463 */         AnnotateImageDialog.this.color = Color.GRAY;
/* 464 */         AnnotateImageDialog.this.text.setForeground(AnnotateImageDialog.this.color);
/*     */       }
/*     */     });
/* 467 */     this.buttonGroup.add(this.gray);
/* 468 */     mnColor.add(this.gray);
/*     */ 
/* 470 */     this.green = new JRadioButtonMenuItem("Green");
/* 471 */     this.green.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 473 */         AnnotateImageDialog.this.green.setSelected(true);
/* 474 */         AnnotateImageDialog.this.color = Color.GREEN;
/* 475 */         AnnotateImageDialog.this.text.setForeground(AnnotateImageDialog.this.color);
/*     */       }
/*     */     });
/* 478 */     this.buttonGroup.add(this.green);
/* 479 */     mnColor.add(this.green);
/*     */ 
/* 481 */     JSeparator separator = new JSeparator();
/* 482 */     mnColor.add(separator);
/*     */ 
/* 484 */     this.magenta = new JRadioButtonMenuItem("Magenta");
/* 485 */     this.magenta.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 487 */         AnnotateImageDialog.this.magenta.setSelected(true);
/* 488 */         AnnotateImageDialog.this.color = Color.MAGENTA;
/* 489 */         AnnotateImageDialog.this.text.setForeground(AnnotateImageDialog.this.color);
/*     */       }
/*     */     });
/* 492 */     this.buttonGroup.add(this.magenta);
/* 493 */     mnColor.add(this.magenta);
/*     */ 
/* 495 */     this.orange = new JRadioButtonMenuItem("Orange");
/* 496 */     this.orange.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 498 */         AnnotateImageDialog.this.orange.setSelected(true);
/* 499 */         AnnotateImageDialog.this.color = Color.ORANGE;
/* 500 */         AnnotateImageDialog.this.text.setForeground(AnnotateImageDialog.this.color);
/*     */       }
/*     */     });
/* 503 */     this.buttonGroup.add(this.orange);
/* 504 */     mnColor.add(this.orange);
/*     */ 
/* 506 */     this.red = new JRadioButtonMenuItem("Red");
/* 507 */     this.red.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 509 */         AnnotateImageDialog.this.red.setSelected(true);
/* 510 */         AnnotateImageDialog.this.color = Color.RED;
/* 511 */         AnnotateImageDialog.this.text.setForeground(AnnotateImageDialog.this.color);
/*     */       }
/*     */     });
/* 514 */     this.buttonGroup.add(this.red);
/* 515 */     mnColor.add(this.red);
/*     */ 
/* 517 */     this.white = new JRadioButtonMenuItem("White");
/* 518 */     this.white.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 520 */         AnnotateImageDialog.this.white.setSelected(true);
/* 521 */         AnnotateImageDialog.this.color = Color.WHITE;
/* 522 */         AnnotateImageDialog.this.text.setForeground(AnnotateImageDialog.this.color);
/*     */       }
/*     */     });
/* 525 */     this.buttonGroup.add(this.white);
/* 526 */     mnColor.add(this.white);
/*     */ 
/* 528 */     this.yellow = new JRadioButtonMenuItem("Yellow");
/* 529 */     this.yellow.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 531 */         AnnotateImageDialog.this.yellow.setSelected(true);
/* 532 */         AnnotateImageDialog.this.color = Color.YELLOW;
/* 533 */         AnnotateImageDialog.this.text.setForeground(AnnotateImageDialog.this.color);
/*     */       }
/*     */     });
/* 536 */     this.buttonGroup.add(this.yellow);
/* 537 */     mnColor.add(this.yellow);
/*     */ 
/* 539 */     JMenu mnToolSize = new JMenu("Tool Size");
/* 540 */     mnTool.add(mnToolSize);
/*     */ 
/* 542 */     this.size1 = new JRadioButtonMenuItem("1");
/* 543 */     this.size1.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 545 */         AnnotateImageDialog.this.size1.setSelected(true);
/* 546 */         AnnotateImageDialog.this.size = 1;
/*     */       }
/*     */     });
/* 549 */     this.size1.setSelected(true);
/* 550 */     this.buttonGroup_1.add(this.size1);
/* 551 */     mnToolSize.add(this.size1);
/*     */ 
/* 553 */     this.size2 = new JRadioButtonMenuItem("2");
/* 554 */     this.size2.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 556 */         AnnotateImageDialog.this.size2.setSelected(true);
/* 557 */         AnnotateImageDialog.this.size = 2;
/*     */       }
/*     */     });
/* 560 */     this.buttonGroup_1.add(this.size2);
/* 561 */     mnToolSize.add(this.size2);
/*     */ 
/* 563 */     this.size3 = new JRadioButtonMenuItem("3");
/* 564 */     this.size3.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 566 */         AnnotateImageDialog.this.size3.setSelected(true);
/* 567 */         AnnotateImageDialog.this.size = 3;
/*     */       }
/*     */     });
/* 570 */     this.buttonGroup_1.add(this.size3);
/* 571 */     mnToolSize.add(this.size3);
/*     */ 
/* 573 */     this.brush = new JRadioButtonMenuItem("Brush");
/* 574 */     this.brush.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 576 */         AnnotateImageDialog.this.brush.setSelected(true);
/* 577 */         AnnotateImageDialog.this.tool = 0;
/*     */       }
/*     */     });
/* 580 */     this.buttonGroup_2.add(this.brush);
/* 581 */     this.brush.setSelected(true);
/* 582 */     mnTool.add(this.brush);
/*     */ 
/* 584 */     this.line = new JRadioButtonMenuItem("Line");
/* 585 */     this.line.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 587 */         AnnotateImageDialog.this.line.setSelected(true);
/* 588 */         AnnotateImageDialog.this.tool = 1;
/*     */       }
/*     */     });
/* 591 */     this.buttonGroup_2.add(this.line);
/* 592 */     mnTool.add(this.line);
/*     */ 
/* 594 */     this.rectangle = new JRadioButtonMenuItem("Rectangle");
/* 595 */     this.rectangle.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 597 */         AnnotateImageDialog.this.rectangle.setSelected(true);
/* 598 */         AnnotateImageDialog.this.tool = 2;
/*     */       }
/*     */     });
/* 601 */     this.buttonGroup_2.add(this.rectangle);
/* 602 */     mnTool.add(this.rectangle);
/*     */ 
/* 604 */     this.rdbtnmntmText = new JRadioButtonMenuItem("Text");
/* 605 */     this.rdbtnmntmText.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 607 */         AnnotateImageDialog.this.tool = 3;
/*     */       }
/*     */     });
/* 610 */     this.buttonGroup_2.add(this.rdbtnmntmText);
/* 611 */     mnTool.add(this.rdbtnmntmText);
/*     */ 
/* 613 */     JMenu mnEdit = new JMenu("Edit");
/* 614 */     menuBar.add(mnEdit);
/*     */ 
/* 617 */     this.mntmUndo.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 619 */         if (AnnotateImageDialog.this.historyIndex >= 0) {
/* 620 */           AnnotateImageDialog.this.historyIndex -= 1;
/* 621 */           AnnotateImageDialog.this.label.setZoomedOriginal((BufferedImage)AnnotateImageDialog.this.imgHistory.get(AnnotateImageDialog.this.historyIndex));
/* 622 */           AnnotateImageDialog.this.mntmRedo.setVisible(true);
/*     */         }
/* 624 */         if (AnnotateImageDialog.this.historyIndex <= 0)
/* 625 */           AnnotateImageDialog.this.mntmUndo.setVisible(false);
/*     */       }
/*     */     });
/* 629 */     this.mntmUndo.setAccelerator(KeyStroke.getKeyStroke(90, 2));
/* 630 */     mnEdit.add(this.mntmUndo);
/*     */ 
/* 633 */     this.mntmRedo.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 635 */         if (AnnotateImageDialog.this.historyIndex < AnnotateImageDialog.this.imgHistory.size() - 1) {
/* 636 */           AnnotateImageDialog.this.historyIndex += 1;
/* 637 */           AnnotateImageDialog.this.label.setZoomedOriginal((BufferedImage)AnnotateImageDialog.this.imgHistory.get(AnnotateImageDialog.this.historyIndex));
/* 638 */           AnnotateImageDialog.this.mntmUndo.setVisible(true);
/*     */         }
/* 640 */         if (AnnotateImageDialog.this.historyIndex >= AnnotateImageDialog.this.imgHistory.size() - 1) {
/* 641 */           AnnotateImageDialog.this.mntmRedo.setVisible(false);
/*     */         }
/* 643 */         AnnotateImageDialog.this.repaint();
/*     */       }
/*     */     });
/* 646 */     this.mntmRedo.setAccelerator(KeyStroke.getKeyStroke(89, 2));
/* 647 */     mnEdit.add(this.mntmRedo);
/*     */ 
/* 649 */     this.mnFont = new JMenu("Font");
/* 650 */     menuBar.add(this.mnFont);
/*     */ 
/* 652 */     JMenu mnFont_1 = new JMenu("Font");
/* 653 */     this.mnFont.add(mnFont_1);
/*     */ 
/* 665 */     this.arBerkeley = new JRadioButtonMenuItem("AR Berkeley");
/* 666 */     this.arBerkeley.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e)
/*     */       {
/*     */         int ftype;
/* 658 */         if (AnnotateImageDialog.this.bold.isSelected())
/* 659 */           ftype = 1;
/*     */         else
/* 661 */           ftype = 0;
/* 662 */         AnnotateImageDialog.this.text.setFont(new Font(((JRadioButtonMenuItem)e.getSource()).getText(), ftype, ((Integer)AnnotateImageDialog.this.spinner.getValue()).intValue()));
/*     */       }
/*     */     });
/* 667 */     this.buttonGroup_3.add(this.arBerkeley);
/* 668 */     mnFont_1.add(this.arBerkeley);
/*     */ 
/* 670 */     this.arial = new JRadioButtonMenuItem("Arial");
/* 671 */     this.arial.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e)
/*     */       {
/*     */         int ftype;
/* 658 */         if (AnnotateImageDialog.this.bold.isSelected())
/* 659 */           ftype = 1;
/*     */         else
/* 661 */           ftype = 0;
/* 662 */         AnnotateImageDialog.this.text.setFont(new Font(((JRadioButtonMenuItem)e.getSource()).getText(), ftype, ((Integer)AnnotateImageDialog.this.spinner.getValue()).intValue()));
/*     */       }
/*     */     });
/* 672 */     this.buttonGroup_3.add(this.arial);
/* 673 */     mnFont_1.add(this.arial);
/*     */ 
/* 675 */     this.calibri = new JRadioButtonMenuItem("Calibri");
/* 676 */     this.calibri.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e)
/*     */       {
/*     */         int ftype;
/* 658 */         if (AnnotateImageDialog.this.bold.isSelected())
/* 659 */           ftype = 1;
/*     */         else
/* 661 */           ftype = 0;
/* 662 */         AnnotateImageDialog.this.text.setFont(new Font(((JRadioButtonMenuItem)e.getSource()).getText(), ftype, ((Integer)AnnotateImageDialog.this.spinner.getValue()).intValue()));
/*     */       }
/*     */     });
/* 677 */     this.buttonGroup_3.add(this.calibri);
/* 678 */     this.calibri.setFont(new Font("Segoe UI", 0, 12));
/* 679 */     mnFont_1.add(this.calibri);
/*     */ 
/* 681 */     this.georgia = new JRadioButtonMenuItem("Georgia");
/* 682 */     this.georgia.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e)
/*     */       {
/*     */         int ftype;
/* 658 */         if (AnnotateImageDialog.this.bold.isSelected())
/* 659 */           ftype = 1;
/*     */         else
/* 661 */           ftype = 0;
/* 662 */         AnnotateImageDialog.this.text.setFont(new Font(((JRadioButtonMenuItem)e.getSource()).getText(), ftype, ((Integer)AnnotateImageDialog.this.spinner.getValue()).intValue()));
/*     */       }
/*     */     });
/* 683 */     this.buttonGroup_3.add(this.georgia);
/* 684 */     mnFont_1.add(this.georgia);
/*     */ 
/* 686 */     this.sansSerrif = new JRadioButtonMenuItem("Sans Serrif");
/* 687 */     this.sansSerrif.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e)
/*     */       {
/*     */         int ftype;
/* 658 */         if (AnnotateImageDialog.this.bold.isSelected())
/* 659 */           ftype = 1;
/*     */         else
/* 661 */           ftype = 0;
/* 662 */         AnnotateImageDialog.this.text.setFont(new Font(((JRadioButtonMenuItem)e.getSource()).getText(), ftype, ((Integer)AnnotateImageDialog.this.spinner.getValue()).intValue()));
/*     */       }
/*     */     });
/* 688 */     this.buttonGroup_3.add(this.sansSerrif);
/* 689 */     mnFont_1.add(this.sansSerrif);
/*     */ 
/* 691 */     this.timesNewRoman = new JRadioButtonMenuItem("Times New Roman");
/* 692 */     this.timesNewRoman.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e)
/*     */       {
/*     */         int ftype;
/* 658 */         if (AnnotateImageDialog.this.bold.isSelected())
/* 659 */           ftype = 1;
/*     */         else
/* 661 */           ftype = 0;
/* 662 */         AnnotateImageDialog.this.text.setFont(new Font(((JRadioButtonMenuItem)e.getSource()).getText(), ftype, ((Integer)AnnotateImageDialog.this.spinner.getValue()).intValue()));
/*     */       }
/*     */     });
/* 693 */     this.buttonGroup_3.add(this.timesNewRoman);
/* 694 */     this.timesNewRoman.setSelected(true);
/* 695 */     mnFont_1.add(this.timesNewRoman);
/*     */ 
/* 697 */     this.trebuchet = new JRadioButtonMenuItem("Trebuchet MS");
/* 698 */     this.trebuchet.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e)
/*     */       {
/*     */         int ftype;
/* 658 */         if (AnnotateImageDialog.this.bold.isSelected())
/* 659 */           ftype = 1;
/*     */         else
/* 661 */           ftype = 0;
/* 662 */         AnnotateImageDialog.this.text.setFont(new Font(((JRadioButtonMenuItem)e.getSource()).getText(), ftype, ((Integer)AnnotateImageDialog.this.spinner.getValue()).intValue()));
/*     */       }
/*     */     });
/* 699 */     this.buttonGroup_3.add(this.trebuchet);
/* 700 */     mnFont_1.add(this.trebuchet);
/*     */ 
/* 702 */     this.mnSize = new JMenu("Size");
/* 703 */     this.mnFont.add(this.mnSize);
/*     */ 
/* 705 */     this.spinner = new JSpinner();
/* 706 */     this.spinner.addChangeListener(new ChangeListener()
/*     */     {
/*     */       public void stateChanged(ChangeEvent arg0)
/*     */       {
/*     */         int ftype;
/* 709 */         if (AnnotateImageDialog.this.bold.isSelected())
/* 710 */           ftype = 1;
/*     */         else
/* 712 */           ftype = 0;
/* 713 */         AnnotateImageDialog.this.text.setFont(new Font(AnnotateImageDialog.this.text.getFont().getFontName(), ftype, ((Integer)AnnotateImageDialog.this.spinner.getValue()).intValue()));
/*     */       }
/*     */     });
/* 716 */     this.spinner.setModel(new SpinnerNumberModel(12, 1, 98, 1));
/* 717 */     this.mnSize.add(this.spinner);
/*     */ 
/* 719 */     this.bold = new JCheckBoxMenuItem("Bold");
/* 720 */     this.bold.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e)
/*     */       {
/*     */         int ftype;
/* 723 */         if (AnnotateImageDialog.this.bold.isSelected())
/* 724 */           ftype = 1;
/*     */         else
/* 726 */           ftype = 0;
/* 727 */         AnnotateImageDialog.this.text.setFont(new Font(AnnotateImageDialog.this.text.getFont().getFontName(), ftype, ((Integer)AnnotateImageDialog.this.spinner.getValue()).intValue()));
/*     */       }
/*     */     });
/* 730 */     this.mnFont.add(this.bold);
/*     */   }
/*     */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.dialogs.AnnotateImageDialog
 * JD-Core Version:    0.6.2
 */