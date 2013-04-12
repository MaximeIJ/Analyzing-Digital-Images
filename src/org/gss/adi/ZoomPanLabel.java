/*     */ package org.gss.adi;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.event.AdjustmentEvent;
/*     */ import java.awt.event.AdjustmentListener;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.image.AffineTransformOp;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.util.ArrayList;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JScrollBar;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JSlider;
/*     */ import javax.swing.SwingUtilities;
/*     */ import org.gss.adi.tools.ColorTools;
/*     */ 
/*     */ public class ZoomPanLabel extends JScrollPane
/*     */ {
/*     */   private static final long serialVersionUID = 7210679722402692760L;
/*     */   private BufferedImage zoomedTool;
/*     */   private BufferedImage zoomedOriginal;
/*     */   private BufferedImage original;
/*     */   protected JLabel label;
/*     */   ZoomPanLabel me;
/*     */   private JSlider slider;
/*  45 */   protected ArrayList<ZoomPanLabel> syncd = new ArrayList();
/*     */ 
/*  47 */   protected float zoomFactor = 1.0F;
/*     */ 
/*     */   public ZoomPanLabel(JSlider jslider)
/*     */   {
/*  51 */     this.slider = jslider;
/*  52 */     this.me = this;
/*  53 */     this.label = new JLabel();
/*  54 */     setViewportView(this.label);
/*  55 */     setBorder(null);
/*  56 */     setWheelScrollingEnabled(false);
/*  57 */     this.syncd.add(this);
/*  58 */     setVerticalAlignment(1);
/*  59 */     MousePanner panner = new MousePanner();
/*  60 */     this.label.addMouseMotionListener(panner);
/*  61 */     this.label.addMouseListener(panner);
/*     */   }
/*     */ 
/*     */   public void setSize(int width, int height) {
/*  65 */     super.setSize(width, height);
/*  66 */     if (this.original != null)
/*  67 */       setImage(this.original);
/*     */   }
/*     */ 
/*     */   public void setBounds(int X, int Y, int width, int height)
/*     */   {
/*  72 */     super.setBounds(X, Y, width, height);
/*  73 */     if (this.original != null)
/*  74 */       setImage(this.original);
/*     */   }
/*     */ 
/*     */   public void setImage(BufferedImage img) {
/*  78 */     this.original = img;
/*  79 */     float z = 100.0F;
/*  80 */     if (img.getWidth() > getWidth()) {
/*  81 */       z = 100.0F * getWidth() / img.getWidth();
/*     */     }
/*  83 */     if (100.0D * img.getHeight() / getHeight() > z)
/*  84 */       z = 100.0F * getHeight() / img.getHeight();
/*  85 */     this.zoomedOriginal = resize(Math.round(z), img);
/*  86 */     this.zoomFactor = (new Float(this.zoomedOriginal.getWidth()).floatValue() / new Float(this.original.getWidth()).floatValue());
/*  87 */     this.slider.setMinimum(new Double(Math.floor(this.zoomFactor * 100.0D)).intValue());
/*  88 */     this.slider.setMaximum(400);
/*  89 */     this.slider.setValue(this.slider.getMinimum());
/*  90 */     this.label.setIcon(new ImageIcon(this.zoomedOriginal));
/*     */   }
/*     */   public BufferedImage getZoomedOriginal() {
/*  93 */     return this.zoomedOriginal;
/*     */   }
/*     */   public void setZoomedOriginal(BufferedImage img) {
/*  96 */     this.zoomedOriginal = img;
/*  97 */     this.zoomedTool = null;
/*  98 */     this.label.setIcon(new ImageIcon(this.zoomedOriginal));
/*     */   }
/*     */   public BufferedImage getZoomedTool() {
/* 101 */     return this.zoomedTool;
/*     */   }
/*     */   public void setZoomedTool(BufferedImage img) {
/* 104 */     this.zoomedTool = img;
/* 105 */     this.label.setIcon(new ImageIcon(this.zoomedTool));
/*     */   }
/*     */   public BufferedImage getOriginal() {
/* 108 */     return this.original;
/*     */   }
/*     */ 
/*     */   public void toolImage(Integer[] x, Integer[] y, Color color, String tool, float lineWidth, byte cursorStyle)
/*     */   {
/* 114 */     Integer[] X = new Integer[x.length];
/* 115 */     Integer[] Y = new Integer[y.length];
/* 116 */     for (int i = 0; i < x.length; i++) {
/* 117 */       X[i] = Integer.valueOf(Math.round(x[i].intValue() * this.zoomFactor));
/* 118 */       Y[i] = Integer.valueOf(Math.round(y[i].intValue() * this.zoomFactor));
/*     */     }
/* 120 */     setZoomedTool(ColorTools.cursor(this.zoomedOriginal, X, Y, color, tool, Float.valueOf(lineWidth), cursorStyle));
/*     */   }
/*     */   public void showZoomedOriginal() {
/* 123 */     this.label.setIcon(new ImageIcon(this.zoomedOriginal));
/*     */   }
/*     */   public void showZoomedTool() {
/* 126 */     this.label.setIcon(new ImageIcon(this.zoomedTool));
/*     */   }
/*     */ 
/*     */   public void zoom(int z)
/*     */   {
/* 132 */     this.zoomFactor = (new Float(z).floatValue() / 100.0F);
/* 133 */     this.zoomedOriginal = resize(z, this.original);
/* 134 */     this.label.setIcon(new ImageIcon(this.zoomedOriginal));
/*     */   }
/*     */ 
/*     */   public void qualityZoom(int z)
/*     */   {
/* 141 */     this.zoomFactor = (new Float(z).floatValue() / 100.0F);
/* 142 */     this.zoomedOriginal = resize(z, this.original);
/* 143 */     this.label.setIcon(new ImageIcon(this.zoomedOriginal));
/*     */   }
/*     */   public void setVerticalAlignment(int align) {
/* 146 */     this.label.setVerticalAlignment(align);
/*     */   }
/*     */   public void setHorizontalAlignment(int align) {
/* 149 */     this.label.setVerticalAlignment(align);
/*     */   }
/*     */   protected static BufferedImage resize(int zoom, BufferedImage before) {
/* 152 */     float z = zoom / 100.0F;
/* 153 */     if (z == 0.0F)
/* 154 */       z = 1.0F;
/* 155 */     int w = Math.round(before.getWidth() * z);
/* 156 */     int h = Math.round(before.getHeight() * z);
/* 157 */     BufferedImage after = new BufferedImage(w, h, before.getType());
/* 158 */     AffineTransform at = new AffineTransform();
/* 159 */     at.scale(z, z);
/* 160 */     AffineTransformOp scaleOp = 
/* 161 */       new AffineTransformOp(at, 1);
/* 162 */     after = scaleOp.filter(before, after);
/* 163 */     return after;
/*     */   }
/*     */   public Point mapToPixel(int i, int j) {
/* 166 */     return new Point(Math.round(i / this.zoomFactor), Math.round(j / this.zoomFactor));
/*     */   }
/*     */   public Point zoomPixels(int i, int j) {
/* 169 */     return new Point(Math.round(i * this.zoomFactor), Math.round(j * this.zoomFactor));
/*     */   }
/*     */   public JLabel getLabel() {
/* 172 */     return this.label;
/*     */   }
/*     */   public void sync(ZoomPanLabel label) {
/* 175 */     this.syncd.add(label);
/* 176 */     Synchronizer syn = new Synchronizer(this, label);
/* 177 */     getVerticalScrollBar().addAdjustmentListener(syn);
/* 178 */     getHorizontalScrollBar().addAdjustmentListener(syn);
/* 179 */     label.getVerticalScrollBar().addAdjustmentListener(syn);
/* 180 */     label.getHorizontalScrollBar().addAdjustmentListener(syn);
/* 181 */     ArrayList otherList = label.syncd;
/* 182 */     for (int i = 0; i < this.syncd.size(); i++) {
/* 183 */       ZoomPanLabel item = (ZoomPanLabel)this.syncd.get(i);
/* 184 */       if (!otherList.contains(item)) {
/* 185 */         label.sync(item);
/*     */       }
/*     */     }
/* 188 */     for (int i = 0; i < otherList.size(); i++) {
/* 189 */       ZoomPanLabel item = (ZoomPanLabel)otherList.get(i);
/* 190 */       if (!this.syncd.contains(item))
/* 191 */         this.me.sync(item); 
/*     */     }
/*     */   }
/*     */   class MousePanner extends MouseAdapter {
/*     */     Rectangle rect;
/*     */     Point p;
/*     */ 
/*     */     MousePanner() {  }
/*     */ 
/* 200 */     public void mousePressed(MouseEvent e) { if (SwingUtilities.isRightMouseButton(e)) {
/* 201 */         this.p = e.getPoint();
/* 202 */         this.rect = ZoomPanLabel.this.me.getVisibleRect();
/*     */       } }
/*     */ 
/*     */     public void mouseDragged(MouseEvent e)
/*     */     {
/* 207 */       if (SwingUtilities.isRightMouseButton(e)) {
/* 208 */         Point current = e.getPoint();
/* 209 */         this.rect.x += this.p.x - current.x;
/* 210 */         this.rect.y += this.p.y - current.y;
/* 211 */         for (int i = 0; i < ZoomPanLabel.this.syncd.size(); i++) {
/* 212 */           ((ZoomPanLabel)ZoomPanLabel.this.syncd.get(i)).getLabel().scrollRectToVisible(this.rect);
/*     */         }
/* 214 */         SwingUtilities.invokeLater(new Runnable() {
/*     */           public void run() {
/* 216 */             Rectangle newRect = ZoomPanLabel.this.label.getVisibleRect();
/* 217 */             ZoomPanLabel.MousePanner.this.p.x += newRect.x - ZoomPanLabel.MousePanner.this.rect.x;
/* 218 */             ZoomPanLabel.MousePanner.this.p.y += newRect.y - ZoomPanLabel.MousePanner.this.rect.y;
/* 219 */             ZoomPanLabel.MousePanner.this.rect = newRect; }  } );
/*     */       }
/*     */     }
/*     */   }
/*     */   class Synchronizer implements AdjustmentListener { JScrollBar v1;
/*     */     JScrollBar h1;
/*     */     JScrollBar v2;
/*     */     JScrollBar h2;
/*     */ 
/* 231 */     public Synchronizer(JScrollPane sp1, JScrollPane sp2) { this.v1 = sp1.getVerticalScrollBar();
/* 232 */       this.h1 = sp1.getHorizontalScrollBar();
/* 233 */       this.v2 = sp2.getVerticalScrollBar();
/* 234 */       this.h2 = sp2.getHorizontalScrollBar();
/*     */     }
/*     */ 
/*     */     public void adjustmentValueChanged(AdjustmentEvent e)
/*     */     {
/* 239 */       JScrollBar scrollBar = (JScrollBar)e.getSource();
/* 240 */       int value = scrollBar.getValue();
/* 241 */       JScrollBar target = null;
/*     */ 
/* 243 */       if (scrollBar == this.v1)
/* 244 */         target = this.v2;
/* 245 */       if (scrollBar == this.h1)
/* 246 */         target = this.h2;
/* 247 */       if (scrollBar == this.v2)
/* 248 */         target = this.v1;
/* 249 */       if (scrollBar == this.h2) {
/* 250 */         target = this.h1;
/*     */       }
/* 252 */       target.setValue(value);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.ZoomPanLabel
 * JD-Core Version:    0.6.2
 */