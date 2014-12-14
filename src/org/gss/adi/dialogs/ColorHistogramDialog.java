/*      */ package org.gss.adi.dialogs;
/*      */ 
/*      */ import java.awt.BasicStroke;
/*      */ import java.awt.Color;
/*      */ import java.awt.Container;
import java.awt.Dialog;
/*      */ import java.awt.Font;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Point;
/*      */ import java.awt.Polygon;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.awt.print.PageFormat;
/*      */ import java.awt.print.Printable;
/*      */ import java.awt.print.PrinterException;
/*      */ import java.awt.print.PrinterJob;
/*      */ import java.io.BufferedWriter;
/*      */ import java.io.File;
/*      */ import java.io.FileWriter;
/*      */ import java.io.IOException;
/*      */ import java.text.DecimalFormat;
/*      */ import java.util.ArrayList;
/*      */ import javax.imageio.ImageIO;
/*      */ import javax.swing.ButtonGroup;
/*      */ import javax.swing.ImageIcon;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JDialog;
/*      */ import javax.swing.JFileChooser;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JOptionPane;
/*      */ import javax.swing.JRadioButton;
/*      */ import javax.swing.JTextArea;
/*      */ import javax.swing.JTextField;
/*      */ import javax.swing.filechooser.FileFilter;
/*      */ import org.eclipse.swt.widgets.FileDialog;
/*      */ import org.eclipse.swt.widgets.Shell;
/*      */ import org.gss.adi.Entrance;
import org.gss.adi.SpatialAnalysisPanel;
/*      */ import org.gss.adi.tools.ColorTools;
/*      */ 
/*      */ public class ColorHistogramDialog extends JDialog
/*      */ {
/*      */   private static final long serialVersionUID = -580324425911605384L;
/*   49 */   private final JLabel label = new JLabel();
/*      */ 
/*   51 */   private int[] r = new int[256];
/*   52 */   private int[] g = new int[256];
/*   53 */   private int[] b = new int[256];
/*   54 */   private int[] sr = new int[256];
/*   55 */   private int[] sg = new int[256];
/*   56 */   private int[] sb = new int[256];
/*   51 */   private float[] h = new float[256];
/*   52 */   private float[] s = new float[256];
/*   53 */   private float[] v = new float[256];
/*   54 */   private float[] sh = new float[256];
/*   55 */   private float[] ss = new float[256];
/*   56 */   private float[] sv = new float[256];
/*   57 */   private float count = 0.0F;
/*   58 */   private double max = 0.0F;
/*   59 */   private int totalRed = 0;
/*   60 */   private int totalGreen = 0;
/*   61 */   private int totalBlue = 0;
/*   62 */   private int selectRed = 0;
/*   63 */   private int selectGreen = 0;
/*   64 */   private int selectBlue = 0;
/*   65 */   private float selectCount = 0.0F;
/*   66 */   private double sMax = 0.0F;
/*      */   private boolean hsv;
/*   68 */   private boolean redT = true;
/*   69 */   private boolean redS = true;
/*   70 */   private boolean greenT = true;
/*   71 */   private boolean greenS = true;
/*   72 */   private boolean blueT = true;
/*   73 */   private boolean blueS = true;
/*   74 */   private boolean avgT = true;
/*   75 */   private boolean avgS = true;
private Integer[] tX, tY;
/*      */   private JTextField textField;
/*      */   private JTextField textField_1;
/*      */   private JTextField textField_2;
/*      */   private JTextField textField_3;
/*      */   private JTextField textField_4;
/*      */   private JTextField textField_5;
/*      */   private JTextField textField_6;
/*      */   private JTextField textField_7;
/*      */   private JTextField textField_8;
/*      */   private JTextField textField_9;
/*      */   private JTextField textField_10;
/*      */   private JTextField txtFullImage;
/*      */   private JTextField txtRed;
/*      */   private JTextField txtGreen;
/*      */   private JTextField txtBlue;
/*      */   private JTextField txtAverage;
/*   94 */   private final DecimalFormat df = new DecimalFormat("##.#");
/*      */   private JTextField txtSelectAverage;
/*      */   private JTextField txtSelectBlue;
/*      */   private JTextField txtSelectGreen;
/*      */   private JTextField txtSelectRed;
/*      */   private JTextField txtToolType;
/*      */   private JTextField txtIntensity;
/*      */   private JButton redOn;
/*      */   private JButton greenOn;
/*      */   private JButton blueOn;
/*      */   private JButton avgOn;
/*      */   private JButton redOff;
/*      */   private JButton greenOff;
/*      */   private JButton blueOff;
/*      */   private JButton avgOff;
/*      */   private BufferedImage graph;
/*      */   private JButton sRedOn;
/*      */   private JButton sGreenOn;
/*      */   private JButton sBlueOn;
/*      */   private JButton sAvgOn;
/*      */   private JButton sRedOff;
/*      */   private JButton sGreenOff;
/*      */   private JButton sBlueOff;
/*      */   private JButton sAvgOff;
/*  121 */   private int imgType = 0;
/*      */   private Entrance entrance;
/*  124 */   private final ButtonGroup buttonGroup = new ButtonGroup();
/*      */ 
/*  126 */   boolean area = true;
/*      */   private JTextField txt;
/*      */   private JTextField txtSelectedArea;
/*      */   private JTextField txtColorIntensity;
/*      */ 
/*      */   public ColorHistogramDialog(Entrance e, final String tool, final Integer[] x, final Integer[] y, byte panel, boolean hsv)
/*      */   {
/*  134 */       this.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
				 if (e.getImage() == null) {
/*  135 */       dispose();
/*  136 */       JOptionPane.showMessageDialog(null, "There is no image to graph.", null, -1);
/*      */     }
				 this.hsv = hsv;
				 tX = x;
				 tY = y;
/*  138 */     setup();
/*  139 */     this.entrance = e;
/*  140 */     setLocation(10, 10);
/*  141 */     final JRadioButton rdbtnOriginal = new JRadioButton("Original");
/*  142 */     rdbtnOriginal.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent arg0) {
/*  144 */         ColorHistogramDialog.this.getComponents(ColorHistogramDialog.this.entrance.getImage(), tool, x, y);
/*  145 */         ColorHistogramDialog.this.drawImage();
/*  146 */         ColorHistogramDialog.this.imgType = 0;
/*      */       }
/*      */     });
/*  149 */     this.buttonGroup.add(rdbtnOriginal);
/*  150 */     rdbtnOriginal.setSelected(true);
/*  151 */     rdbtnOriginal.setBounds(162, 580, 100, 23);
/*  152 */     getContentPane().add(rdbtnOriginal);
/*      */ 
/*  154 */     final JRadioButton rdbtnMasked = new JRadioButton("Masked");
/*      */ 
/*  156 */     final JRadioButton rdbtnEnhanced = new JRadioButton("Enhanced");
/*  157 */     rdbtnEnhanced.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  159 */         if (ColorHistogramDialog.this.entrance.getEnhancedImage() != null) {
/*  160 */           ColorHistogramDialog.this.getComponents(ColorHistogramDialog.this.entrance.getEnhancedImage(), tool, x, y);
/*  161 */           ColorHistogramDialog.this.drawImage();
/*  162 */           ColorHistogramDialog.this.imgType = 1;
/*  163 */         } else if (ColorHistogramDialog.this.imgType == 0) {
/*  164 */           rdbtnOriginal.setSelected(true);
/*      */         } else {
/*  166 */           rdbtnMasked.setSelected(true);
/*      */         }
/*      */       }
/*      */     });
/*  170 */     this.buttonGroup.add(rdbtnEnhanced);
/*  171 */     rdbtnEnhanced.setBounds(264, 580, 100, 23);
/*  172 */     getContentPane().add(rdbtnEnhanced);
/*      */ 
/*  174 */     rdbtnMasked.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*  176 */         if (ColorHistogramDialog.this.entrance.getMaskedImage() != null) {
/*  177 */           rdbtnMasked.setSelected(true);
/*  178 */           int w = ColorHistogramDialog.this.entrance.getImage().getWidth();
/*  179 */           int h = ColorHistogramDialog.this.entrance.getImage().getHeight();
/*  180 */           BufferedImage img = new BufferedImage(w, h, ColorHistogramDialog.this.entrance.getImage().getType());
/*  181 */           int[] colors = img.getRGB(0, 0, w, h, null, 0, w);
/*  182 */           int[] origColors = ColorHistogramDialog.this.entrance.getImage().getRGB(0, 0, w, h, null, 0, w);
/*  183 */           int[] maskColors = ColorHistogramDialog.this.entrance.getMaskedImage().getRGB(0, 0, w, h, null, 0, w);
/*  184 */           for (int i = 0; i < origColors.length; i++) {
/*  185 */             origColors[i] &= (maskColors[i] ^ 0xFFFFFFFF);
/*      */           }
/*  187 */           img.setRGB(0, 0, w, h, colors, 0, w);
/*  188 */           ColorHistogramDialog.this.getComponents(img, tool, x, y);
/*  189 */           ColorHistogramDialog.this.drawImage();
/*  190 */           ColorHistogramDialog.this.imgType = 2;
/*  191 */         } else if (ColorHistogramDialog.this.imgType == 0) {
/*  192 */           rdbtnOriginal.setSelected(true);
/*      */         } else {
/*  194 */           rdbtnEnhanced.setSelected(true);
/*      */         }
/*      */       }
/*      */     });
/*  198 */     this.buttonGroup.add(rdbtnMasked);
/*  199 */     rdbtnMasked.setBounds(366, 580, 100, 23);
/*  200 */     getContentPane().add(rdbtnMasked);
/*  201 */     if (panel == 0) {
/*  202 */       rdbtnOriginal.setSelected(true);
/*  203 */       getComponents(this.entrance.getImage(), tool, x, y);
/*  204 */     } else if (panel == 1) {
/*  205 */       rdbtnEnhanced.setSelected(true);
/*  206 */       getComponents(this.entrance.getEnhancedImage(), tool, x, y);
/*  207 */     } else if (panel == 2) {
/*  208 */       rdbtnMasked.setSelected(true);
/*  209 */       int w = this.entrance.getImage().getWidth();
/*  210 */       int h = this.entrance.getImage().getHeight();
/*  211 */       BufferedImage img = new BufferedImage(w, h, this.entrance.getImage().getType());
/*  212 */       int[] colors = img.getRGB(0, 0, w, h, null, 0, w);
/*  213 */       int[] origColors = this.entrance.getImage().getRGB(0, 0, w, h, null, 0, w);
/*  214 */       int[] maskColors = this.entrance.getMaskedImage().getRGB(0, 0, w, h, null, 0, w);
/*  215 */       for (int i = 0; i < origColors.length; i++) {
/*  216 */         origColors[i] &= (maskColors[i] ^ 0xFFFFFFFF);
/*      */       }
/*  218 */       img.setRGB(0, 0, w, h, colors, 0, w);
/*  219 */       getComponents(img, tool, x, y);
/*      */     }
if(tool.contains("Path"))
	this.txtColorIntensity.setText("Relative Position Along Path");
if(tool.contains("Line"))
	this.txtColorIntensity.setText("Relative Position Along Line");

/*  221 */     drawImage();
/*      */   }
/*      */   private void getComponents(BufferedImage img, String tool, Integer[] x, Integer[] y) {
/*  224 */     this.setTxtToolType(tool);
				if ((tool.contains("Line")) || (tool.contains("Path"))) {
/*  225 */       getLineComponents(img, tool, x, y);
/*  226 */       this.area = false;
/*      */     } else {
/*  228 */       this.area = true;
/*  229 */       getAreaComponents(img, tool, x, y);
/*      */     }
/*      */   }
/*      */



private void getLineComponents(BufferedImage img, String tool, Integer[] x,
		Integer[] y) {
	ArrayList al = new ArrayList();
	if (tool.contains("Line"))
		al = ColorTools.getLinePixels(x, y);
	else if (tool.contains("Path")) {
		for (int i = 1; i < x.length; i++) {
			ArrayList temp = ColorTools.getLinePixels(new Integer[] {
					x[(i - 1)], x[i] }, new Integer[] { y[(i - 1)], y[i] });
			for (int j = i == 1 ? 0 : 1; j < temp.size(); j++) {
				al.add(temp.get(j));
			}
		}
	}
	this.r = new int[al.size()];
	this.g = new int[al.size()];
	this.b = new int[al.size()];
	this.h = new float[al.size()];
	this.s = new float[al.size()];
	this.v = new float[al.size()];
	int rsum = 0;
	int gsum = 0;
	int bsum = 0;
	for (int i = 0; i < al.size(); i++) {
		int[] rgb = ColorTools.rgb(img.getRGB(((Point) al.get(i)).x,
				((Point) al.get(i)).y));
		this.r[i] = rgb[0];
		rsum += this.r[i];
		this.g[i] = rgb[1];
		gsum += this.g[i];
		this.b[i] = rgb[2];
		bsum += this.b[i];
		float[] hsv = Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], null);
		this.h[i] = hsv[0] * 100.0F;
		this.s[i] = hsv[1] * 100.0F;
		this.v[i] = hsv[2] * 100.0F;
	}
	this.txtToolType.setVisible(true);
	this.txtSelectRed.setVisible(true);
	this.txtSelectGreen.setVisible(true);
	this.txtSelectBlue.setVisible(true);
	this.txtSelectAverage.setVisible(true);
	if(!this.hsv){
		Double[] rgb = ColorTools.rgbPercent(ColorTools.pixelAvg(img, x, y,
				tool));
		Double avg = Double.valueOf((rgb[0].doubleValue()
				+ rgb[1].doubleValue() + rgb[2].doubleValue()) / 3.0D);
		this.txtSelectRed.setText("Red: " + df.format(rgb[0]) + "%");
		this.txtSelectGreen.setText("Green: " + df.format(rgb[1]) + "%");
		this.txtSelectBlue.setText("Blue: " + df.format(rgb[2]) + "%");
		this.txtSelectAverage.setText("Average: " + df.format(avg) + "%");
	}
	else{
		int[] rgb = ColorTools.pixelAvg(img, x, y, tool);
		float[] hsv = Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], null);
		this.txtSelectRed.setText("Hue: " + df.format(hsv[0] * 100.00F) + "%");
		this.txtSelectGreen.setText("Saturation: " + df.format(hsv[1] * 100.00F) + "%");
		this.txtSelectBlue.setText("Value: " + df.format(hsv[2] * 100.00F) + "%");
		this.txtSelectAverage.setVisible(false);
		this.sAvgOn.setVisible(false);

	}
	this.greenOn.setVisible(false);
	this.redOn.setVisible(false);
	this.blueOn.setVisible(false);
	this.avgOn.setVisible(false);
	this.txt.setVisible(false);
	this.txtRed.setVisible(false);
	this.txtGreen.setVisible(false);
	this.txtBlue.setVisible(false);
	this.txtAverage.setVisible(false);
	this.txtFullImage.setVisible(false);
}













private void getAreaComponents(BufferedImage img, String tool, Integer[] x,
		Integer[] y) {
	this.count = 0.0F;
	this.totalRed = 0;
	this.totalGreen = 0;
	this.totalBlue = 0;
	this.max = 0.0F;
	for (int i = 0; i < 256; i++) {
		this.r[i] = 0;
		this.g[i] = 0;
		this.b[i] = 0;
	}
	for (int i = 0; i < img.getWidth(); i++) {
		for (int j = 0; j < img.getHeight(); j++) {
			int[] rgb = ColorTools.rgb(img.getRGB(i, j));
			this.count += 1.0F;
			this.totalRed += rgb[0];
			int tmp122_121 = rgb[0];
			int[] tmp122_115 = this.r;
			int tmp124_123 = tmp122_115[tmp122_121];
			tmp122_115[tmp122_121] = (tmp124_123 + 1);
			if ((tmp124_123 > this.max) && (rgb[0] > 0))
				this.max = this.r[rgb[0]];
			this.totalGreen += rgb[1];
			int tmp179_178 = rgb[1];
			int[] tmp179_172 = this.g;
			int tmp181_180 = tmp179_172[tmp179_178];
			tmp179_172[tmp179_178] = (tmp181_180 + 1);
			if ((tmp181_180 > this.max) && (rgb[1] > 0))
				this.max = this.g[rgb[1]];
			this.totalBlue += rgb[2];
			int tmp236_235 = rgb[2];
			int[] tmp236_229 = this.b;
			int tmp238_237 = tmp236_229[tmp236_235];
			tmp236_229[tmp236_235] = (tmp238_237 + 1);
			if ((tmp238_237 > this.max) && (rgb[2] > 0))
				this.max = this.b[rgb[2]];
		}
	}
	ArrayList al = new ArrayList();
	if (tool.contains("Rectangle")) {
		for (int i = x[0].intValue(); i < x[1].intValue(); i++) {
			for (int j = y[0].intValue(); j < y[1].intValue(); j++)
				al.add(new Point(i, j));
		}
	} else if (tool.contains("Polygon")) {
		int[] X = new int[x.length];
		int[] Y = new int[y.length];
		for (int i = 0; i < x.length; i++) {
			X[i] = x[i].intValue();
			Y[i] = y[i].intValue();
		}
		Polygon p = new Polygon(X, Y, X.length);
		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight(); j++) {
				if (p.contains(i, j))
					al.add(new Point(i, j));
			}
		}
	}
	this.selectCount = 0.0F;
	for (int i = 0; i < al.size(); i++) {
		Point p = (Point) al.get(i);
		this.selectCount += 1.0F;
		int[] rgb = ColorTools.rgb(img.getRGB(p.x, p.y));
		this.selectRed += rgb[0];
		int tmp605_604 = rgb[0];
		int[] tmp605_598 = this.sr;
		int tmp607_606 = tmp605_598[tmp605_604];
		tmp605_598[tmp605_604] = (tmp607_606 + 1);
		if ((tmp607_606 > this.sMax) && (rgb[0] > 0))
			this.sMax = this.sr[rgb[0]];
		this.selectGreen += rgb[1];
		int tmp662_661 = rgb[1];
		int[] tmp662_655 = this.sg;
		int tmp664_663 = tmp662_655[tmp662_661];
		tmp662_655[tmp662_661] = (tmp664_663 + 1);
		if ((tmp664_663 > this.sMax) && (rgb[1] > 0))
			this.sMax = this.sg[rgb[1]];
		this.selectBlue += rgb[2];
		int tmp719_718 = rgb[2];
		int[] tmp719_712 = this.sb;
		int tmp721_720 = tmp719_712[tmp719_718];
		tmp719_712[tmp719_718] = (tmp721_720 + 1);
		if ((tmp721_720 > this.sMax) && (rgb[2] > 0))
			this.sMax = this.sb[rgb[2]];
	}
	this.txtRed.setText("Red: "
			+ this.df
			.format(100.0F * this.totalRed / (this.count * 255.0F))
			+ "%");
	this.txtGreen.setText("Green: "
			+ this.df.format(100.0F * this.totalGreen
					/ (this.count * 255.0F)) + "%");
	this.txtBlue.setText("Blue: "
			+ this.df.format(100.0F * this.totalBlue
					/ (this.count * 255.0F)) + "%");
	this.txtAverage.setText("Average: "
			+ this.df.format(100.0F
					* (this.totalBlue + this.totalRed + this.totalGreen)
					/ (this.count * 255.0F * 3.0F)) + "%");
	boolean tooled = false;
	if (tool.contains("Line")) {
		this.area = false;
		tooled = true;
		this.txtToolType.setText("Along Line");
	} else if (tool.contains("Rectangle")) {
		tooled = true;
		this.txtToolType.setText("Selected Area");
	} else if (tool.contains("Polygon")) {
		tooled = true;
		this.txtToolType.setText("Selected Area");
	} else if (tool.contains("Path")) {
		this.area = false;
		tooled = true;
		this.txtToolType.setText("Along Path");
	}
	if (tooled) {
		this.txtToolType.setVisible(true);
		this.txtSelectRed.setVisible(true);
		this.txtSelectGreen.setVisible(true);
		this.txtSelectBlue.setVisible(true);
		this.txtSelectAverage.setVisible(true);
		this.txtSelectRed.setText("Red: "
				+ this.df.format(100.0F * this.selectRed
						/ (this.selectCount * 255.0F)) + "%");
		this.txtSelectGreen.setText("Green: "
				+ this.df.format(100.0F * this.selectGreen
						/ (this.selectCount * 255.0F)) + "%");
		this.txtSelectBlue.setText("Blue: "
				+ this.df.format(100.0F * this.selectBlue
						/ (this.selectCount * 255.0F)) + "%");
		this.txtSelectAverage
		.setText("Average: "
				+ this.df
				.format(100.0F
						* (this.selectBlue + this.selectRed + this.selectGreen)
						/ (this.selectCount * 255.0F * 3.0F))
						+ "%");
	} else {
		this.redOn.setVisible(false);
		this.greenOn.setVisible(false);
		this.blueOn.setVisible(false);
		this.avgOn.setVisible(false);
	}
}
/*      */ 
/*  374 */   private void drawImage() { if (this.area)
/*  375 */       drawAreaImage();
/*      */     else
/*  377 */       drawPathImage();
/*      */   }
/*      */ 
/*      */   private void drawPathImage()
/*      */   {
/*  382 */     this.graph = new BufferedImage(552, 330, 1);
/*      */ 
/*  385 */     int delta = 30;
/*  386 */     int val = 10;
/*  387 */     Graphics2D g2d = this.graph.createGraphics();
/*  388 */     g2d.setColor(Color.WHITE);
/*  389 */     g2d.fillRect(0, 0, this.graph.getWidth(), this.graph.getHeight());
/*  390 */     g2d.setColor(Color.GRAY.brighter());
/*  391 */     for (int i = 40; i <= 552; i += 51) {
/*  392 */       g2d.drawLine(i, 20, i, 330);
/*      */     }
/*  394 */     for (int i = 20; i <= 320; i += delta) {
/*  395 */       g2d.drawLine(30, i, 552, i);
/*  396 */       g2d.setColor(Color.BLACK);
/*  397 */       g2d.drawString(new Integer(val * (i - 10) / delta - 3).toString() + "%", 0, 330 - (i - 5));
/*  398 */       g2d.setColor(Color.GRAY.brighter());
/*      */     }
/*  400 */     g2d.drawLine(40, 320, 40, 320);
/*  401 */     g2d.setStroke(new BasicStroke(2.0F));
/*  402 */     g2d.drawLine(40, 320, 552, 320);
/*  403 */     g2d.drawLine(40, 20, 552, 20);
/*  404 */     g2d.drawLine(40, 20, 40, 320);
/*  405 */     g2d.drawLine(551, 20, 551, 320);
if(!this.hsv){
/*  406 */     for (int i = 0; i < this.r.length - 1; i++) {
/*  407 */       if (this.redS) {
/*  408 */         g2d.setColor(Color.RED);
/*  409 */         g2d.drawLine(40 + i * 512 / this.r.length, 320 - this.r[i] * 300/255, 40 + (i + 1) * 512 / this.r.length, 320 - this.r[(i + 1)] * 300/255);
/*      */       }
/*  411 */       if (this.greenS) {
/*  412 */         g2d.setColor(Color.GREEN);
/*  413 */         g2d.drawLine(40 + i * 512 / this.g.length, 320 - this.g[i] * 300/255, 40 + (i + 1) * 512 / this.g.length, 320 - this.g[(i + 1)] * 300/255);
/*      */       }
/*  415 */       if (this.blueS) {
/*  416 */         g2d.setColor(Color.BLUE);
/*  417 */         g2d.drawLine(40 + i * 512 / this.b.length, 320 - this.b[i] * 300/255, 40 + (i + 1) * 512 / this.b.length, 320 - this.b[(i + 1)] * 300/255);
/*      */       }
/*  419 */       if (this.avgS) {
/*  420 */         g2d.setColor(Color.BLACK);
/*  421 */         g2d.drawLine(40 + i * 512 / this.b.length, 320 - (this.b[i] + this.g[i] + this.r[i]) * 100/255, 40 + (i + 1) * 512 / this.b.length, 320 - (this.b[(i + 1)] + this.g[(i + 1)] + this.r[(i + 1)])  * 100/255);
/*      */       }
/*      */     }
}
else{
	
	/*  406 */     for (int i = 0; i < this.h.length - 1; i++) {
		/*  407 */       if (this.redS) {
		/*  408 */         g2d.setColor(Color.MAGENTA);
		/*  409 */         g2d.drawLine(40 + i * 512 / this.h.length,(int) (320 - this.h[i] * 300/255), 40 + (i + 1) * 512 / this.h.length, (int)(320 - this.h[(i + 1)] * 300/255));
		/*      */       }
		/*  411 */       if (this.greenS) {
		/*  412 */         g2d.setColor(Color.ORANGE);
		/*  413 */         g2d.drawLine(40 + i * 512 / this.s.length, (int)(320 - this.s[i] * 300/255), 40 + (i + 1) * 512 / this.s.length, (int)(320 - this.s[(i + 1)] * 300/255));
		/*      */       }
		/*  415 */       if (this.blueS) {
		/*  416 */         g2d.setColor(Color.BLACK);
		/*  417 */         g2d.drawLine(40 + i * 512 / this.v.length, (int)(320 - this.v[i] * 300/255), 40 + (i + 1) * 512 / this.v.length, (int)(320 - this.v[(i + 1)] * 300/255));
		/*      */       }
		/*      */     }
	
	
	
	
}
				g2d.setColor(Color.BLACK);
				g2d.drawString(this.entrance.getTitle().substring(0, this.entrance.getTitle().lastIndexOf("is")), 80, 13);
/*  424 */     g2d.dispose();
/*  425 */     this.label.setIcon(new ImageIcon(this.graph));
/*  426 */     
/*      */   }
/*      */   private void drawAreaImage() {
/*  429 */     this.graph = new BufferedImage(552, 330, 1);
/*      */     double m;
/*  434 */     if ((this.selectCount > 0.0F) && (this.sMax / this.selectCount > this.max / this.count))
/*  435 */       m = this.sMax / this.selectCount;
/*      */     else
/*  437 */       m = this.max / this.count;
/*      */     int top;
/*      */     int delta;
/*      */     int val;
/*  438 */     if (m < 0.05D) {
/*  439 */       val = 1;
/*  440 */       delta = 60;
/*  441 */       top = 5;
/*      */     }
/*      */     else
/*      */     {
/*  442 */       if (m < 0.1D) {
/*  443 */         val = 1;
/*  444 */         delta = 30;
/*  445 */         top = 10;
/*      */       }
/*      */       else
/*      */       {
/*  446 */         if (m < 0.25D) {
/*  447 */           val = 5;
/*  448 */           delta = 60;
/*  449 */           top = 25;
/*      */         }
/*      */         else
/*      */         {
/*  450 */           if (m < 0.5D) {
/*  451 */             val = 10;
/*  452 */             delta = 60;
/*  453 */             top = 50;
/*      */           } else {
/*  455 */             delta = 30;
/*  456 */             val = 10;
/*  457 */             top = 100;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  459 */     Graphics2D g2d = this.graph.createGraphics();
/*  460 */     g2d.setColor(Color.WHITE);
/*  461 */     g2d.fillRect(0, 0, this.graph.getWidth(), this.graph.getHeight());
/*  462 */     g2d.setColor(Color.GRAY.brighter());
/*  463 */     for (int i = 40; i <= 552; i += 51) {
/*  464 */       g2d.drawLine(i, 20, i, 330);
/*      */     }
/*  466 */     for (int i = 20; i <= 320; i += delta) {
/*  467 */       g2d.drawLine(30, i, 552, i);
/*  468 */       g2d.setColor(Color.BLACK);
/*  469 */       g2d.drawString(new Integer(val * (i - 10) / delta).toString() + "%", 0, 330 - (i - 5));
/*  470 */       g2d.setColor(Color.GRAY.brighter());
/*      */     }
/*  472 */     g2d.drawLine(40, 320, 40, 320);
/*  473 */     g2d.setStroke(new BasicStroke(2.0F));
/*  474 */     g2d.drawLine(40, 320, 552, 320);
/*  475 */     g2d.drawLine(40, 20, 552, 20);
/*  476 */     g2d.drawLine(40, 20, 40, 320);
/*  477 */     g2d.drawLine(551, 20, 551, 320);
/*  478 */     float scaler = 300 / top * (100.0F / this.count);
/*  479 */     for (int i = 0; i < 255; i++) {
/*  480 */       if (this.redT) {
/*  481 */         g2d.setColor(ColorTools.red2);
/*  482 */         g2d.drawLine(40 + 2 * i, Math.round(320.0F - this.r[i] * scaler), 42 + 2 * i, Math.round(320.0F - this.r[(i + 1)] * scaler));
/*      */       }
/*  484 */       if (this.greenT) {
/*  485 */         g2d.setColor(ColorTools.green2);
/*  486 */         g2d.drawLine(40 + 2 * i, Math.round(320.0F - this.g[i] * scaler), 42 + 2 * i, Math.round(320.0F - this.g[(i + 1)] * scaler));
/*      */       }
/*  488 */       if (this.blueT) {
/*  489 */         g2d.setColor(ColorTools.blue2);
/*  490 */         g2d.drawLine(40 + 2 * i, Math.round(320.0F - this.b[i] * scaler), 42 + 2 * i, Math.round(320.0F - this.b[(i + 1)] * scaler));
/*      */       }
/*  492 */       if (this.avgT) {
/*  493 */         g2d.setColor(ColorTools.black2);
/*  494 */         g2d.drawLine(40 + 2 * i, Math.round(320.0F - (this.r[i] + this.b[i] + this.g[i]) * scaler / 3.0F), 42 + 2 * i, Math.round(320.0F - (this.r[(i + 1)] + this.b[(i + 1)] + this.g[(i + 1)]) * scaler / 3.0F));
/*      */       }
/*      */     }
/*  497 */     if (this.selectCount != 0.0F) {
/*  498 */       scaler = 300 / top * (100.0F / this.selectCount);
/*  499 */       for (int i = 0; i < 255; i++) {
/*  500 */         if (this.redS) {
/*  501 */           g2d.setColor(ColorTools.red1);
/*  502 */           g2d.drawLine(40 + 2 * i, Math.round(320.0F - this.sr[i] * scaler), 42 + 2 * i, Math.round(320.0F - this.sr[(i + 1)] * scaler));
/*      */         }
/*  504 */         if (this.greenS) {
/*  505 */           g2d.setColor(ColorTools.green1);
/*  506 */           g2d.drawLine(40 + 2 * i, Math.round(320.0F - this.sg[i] * scaler), 42 + 2 * i, Math.round(320.0F - this.sg[(i + 1)] * scaler));
/*      */         }
/*  508 */         if (this.blueS) {
/*  509 */           g2d.setColor(ColorTools.blue1);
/*  510 */           g2d.drawLine(40 + 2 * i, Math.round(320.0F - this.sb[i] * scaler), 42 + 2 * i, Math.round(320.0F - this.sb[(i + 1)] * scaler));
/*      */         }
/*  512 */         if (this.avgS) {
/*  513 */           g2d.setColor(ColorTools.black1);
/*  514 */           g2d.drawLine(40 + 2 * i, Math.round(320.0F - (this.sr[i] + this.sb[i] + this.sg[i]) * scaler / 3.0F), 42 + 2 * i, Math.round(320.0F - (this.sr[(i + 1)] + this.sb[(i + 1)] + this.sg[(i + 1)]) * scaler / 3.0F));
/*      */         }
/*      */       }
/*      */     }
				g2d.setColor(Color.BLACK);
				g2d.drawString(this.entrance.getTitle().substring(0, this.entrance.getTitle().lastIndexOf("is")), 80, 13);
/*  518 */     g2d.dispose();
/*  519 */     this.label.setIcon(new ImageIcon(this.graph));
/*      */   }
/*      */ 
private String toText() {
	String colorType = "RGB";
	if(this.hsv)
		colorType = "HSV";
	String s = "Image\t" + this.entrance.getFilename() + "\t\t"  + colorType;
	if(this.entrance.getMeasurement() != null)
		s = s + "\t\t Scaling Factor \t" + this.entrance.getMeasurement()._distPerPix + " " + this.entrance.getMeasurement()._unitOfMsr;
	s = s + "\n";
	if(this.txtToolType.getText().contains("Line") || this.txtToolType.getText().contains("Rectangle")){
		s = s + "X1 =\t" + tX[0] + "\tY1 =\t" + tY[0] + "\tX2 =\t" + tX[1] + "\tY2 =\t" + tY[1] + "\n";
	}
	if(!this.hsv){
		if(!this.txtToolType.getText().contains("Line") && !this.txtToolType.getText().contains("Path")){
			s = s + "\tFrequencies for Full Image";
			if (this.selectCount != 0.0F)
				s = s + "\t\t\t\t Frequencies " + this.txtToolType.getText() + "\n";
			s = s + "\nColor Intensity (0-255)\tFrequency of Red Intensity\tFrequency of Green Intensity\tFrequency of Blue Intensity\tFrequency of Ave Intensity";

			if (this.selectCount != 0.0F) {
				s = s
						+ "\tFrequency of Red Intensity\tFrequency of Green Intensity\tFrequency of Blue Intensity\tFrequency of Ave Intensity\n";
			}
			for (int i = 0; i < 256; i++) {
				s = s + i + "\t" + this.r[i]/this.count + "\t" + this.g[i]/this.count + "\t" + this.b[i] / this.count + "\t" + (this.r[i] + this.g[i] + this.b[i]) / (3.0F * this.count);
				if (this.selectCount != 0.0F)
					s = s + i + "\t" + this.sr[i] / this.selectCount + "\t" + this.sg[i] / this.selectCount + "\t" + this.sb[i]/this.selectCount + "\t" + (this.sr[i] + this.sg[i] + this.sb[i])/(3.0F * this.selectCount);
				s = s + "\n";
			}
		}
		else{
			s = s + "X \tY \tRed Intensity (%) \tGreen Intensity (%) \tBlue Intensity (%)\n";
			ArrayList al = new ArrayList();
			if (this.txtToolType.getText().contains("Line"))
				al = ColorTools.getLinePixels(tX, tY);
			else{
				for (int i = 1; i < tX.length; i++) {
					ArrayList temp = ColorTools.getLinePixels(new Integer[] {
							tX[(i - 1)], tX[i] }, new Integer[] { tY[(i - 1)], tY[i] });
					for (int j = i == 1 ? 0 : 1; j < temp.size(); j++) {
						al.add(temp.get(j));
					}
				}
			}
			for (int i = 0; i < al.size(); i++) {
				s = s + ((Point) al.get(i)).x + "\t" + ((Point) al.get(i)).y + "\t" + (int)(this.r[i]/2.55D) + "\t" + (int)(this.g[i]/2.55D) + "\t" + (int)(this.b[i]/2.55D) + "\n";
			}
		}
	}
	else{
		if(!this.txtToolType.getText().contains("Line") && !this.txtToolType.getText().contains("Path")){

		}
		else{
			s = s + "X \tY \tHue (%) \tSaturation (%) \tValue (%)\n";
			ArrayList al = new ArrayList();
			if (this.txtToolType.getText().contains("Line"))
				al = ColorTools.getLinePixels(tX, tY);
			else{
				for (int i = 1; i < tX.length; i++) {
					ArrayList temp = ColorTools.getLinePixels(new Integer[] {
							tX[(i - 1)], tX[i] }, new Integer[] { tY[(i - 1)], tY[i] });
					for (int j = i == 1 ? 0 : 1; j < temp.size(); j++) {
						al.add(temp.get(j));
					}
				}
			}
			for (int i = 0; i < al.size(); i++) {
				s = s + ((Point) al.get(i)).x + "\t" + ((Point) al.get(i)).y + "\t" + (int)(this.h[i]) + "\t" + (int)(this.s[i]) + "\t" + (int)(this.v[i]) + "\n";
			}
		}
	}
	return s;
}


public void exportToText(){
	File f;
	/*      */         try {
	/* 1062 */           FileDialog fc = new FileDialog(new Shell(), 8192);
	/* 1063 */           String[] fileTypes = { "Text Files *.txt" };
	/* 1064 */           String[] fileExtensions = { "*.txt" };
	/* 1065 */           fc.setFilterExtensions(fileExtensions);
	/* 1066 */           fc.setFilterNames(fileTypes);
	/* 1067 */           fc.setOverwrite(true);
	/* 1068 */           fc.setFileName("Untitled_Graph.txt");
	/* 1069 */           fc.setText("Export as Text");
	/* 1070 */           fc.open();
	/* 1071 */           f = new File(fc.getFilterPath() + "\\" + fc.getFileName());
	/*      */         }
	/*      */         catch (Throwable t)
	/*      */         {
	/* 1073 */           JFileChooser fc = new JFileChooser();
	/* 1074 */           fc.setSelectedFile(new File("Untitled_Graph.txt"));
	/* 1075 */           fc.setDialogTitle("Export as Text");
	/* 1076 */           fc.setFileFilter(new FileFilter()
	/*      */           {
	/*      */             public boolean accept(File f) {
	/* 1079 */               return (f.getName().endsWith(".txt")) || (f.isDirectory());
	/*      */             }
	/*      */ 
	/*      */             public String getDescription() {
	/* 1083 */               return "Text Files";
	/*      */             }
	/*      */           });
	/* 1086 */           fc.showSaveDialog(null);
	/* 1087 */           f = fc.getSelectedFile();
	/*      */         }
	/*      */         try {
	/* 1090 */           FileWriter fstream = new FileWriter(f);
	/* 1091 */           BufferedWriter out = new BufferedWriter(fstream);
	/* 1092 */           out.write(ColorHistogramDialog.this.toText());
	/* 1093 */           out.close();
	/*      */         }
	/*      */         catch (Exception localException)
	/*      */         {
	/*      */         }
}




/*      */   private void setup() {
/*  543 */     setBounds(100, 100, 795, 667);
/*  544 */     getContentPane().setLayout(null);
/*      */ 
/*  546 */     this.label.setBounds(10, 32, 553, 330);
/*  547 */     getContentPane().add(this.label);
/*      */ 
/*  549 */     this.textField = new JTextField();
/*  550 */     this.textField.setBorder(null);
/*  551 */     this.textField.setEditable(false);
/*  552 */     this.textField.setHorizontalAlignment(0);
/*  553 */     this.textField.setText("0%");
/*  554 */     this.textField.setBounds(30, 367, 40, 20);
/*  555 */     getContentPane().add(this.textField);
/*  556 */     this.textField.setColumns(10);
/*      */ 
/*  558 */     this.textField_1 = new JTextField();
/*  559 */     this.textField_1.setBorder(null);
/*  560 */     this.textField_1.setText("10%");
/*  561 */     this.textField_1.setHorizontalAlignment(0);
/*  562 */     this.textField_1.setEditable(false);
/*  563 */     this.textField_1.setColumns(10);
/*  564 */     this.textField_1.setBounds(81, 367, 40, 20);
/*  565 */     getContentPane().add(this.textField_1);
/*      */ 
/*  567 */     this.textField_2 = new JTextField();
/*  568 */     this.textField_2.setText("20%");
/*  569 */     this.textField_2.setHorizontalAlignment(0);
/*  570 */     this.textField_2.setEditable(false);
/*  571 */     this.textField_2.setColumns(10);
/*  572 */     this.textField_2.setBorder(null);
/*  573 */     this.textField_2.setBounds(132, 367, 40, 20);
/*  574 */     getContentPane().add(this.textField_2);
/*      */ 
/*  576 */     this.textField_3 = new JTextField();
/*  577 */     this.textField_3.setText("30%");
/*  578 */     this.textField_3.setHorizontalAlignment(0);
/*  579 */     this.textField_3.setEditable(false);
/*  580 */     this.textField_3.setColumns(10);
/*  581 */     this.textField_3.setBorder(null);
/*  582 */     this.textField_3.setBounds(183, 367, 40, 20);
/*  583 */     getContentPane().add(this.textField_3);
/*      */ 
/*  585 */     this.textField_4 = new JTextField();
/*  586 */     this.textField_4.setText("40%");
/*  587 */     this.textField_4.setHorizontalAlignment(0);
/*  588 */     this.textField_4.setEditable(false);
/*  589 */     this.textField_4.setColumns(10);
/*  590 */     this.textField_4.setBorder(null);
/*  591 */     this.textField_4.setBounds(234, 367, 40, 20);
/*  592 */     getContentPane().add(this.textField_4);
/*      */ 
/*  594 */     this.textField_5 = new JTextField();
/*  595 */     this.textField_5.setText("50%");
/*  596 */     this.textField_5.setHorizontalAlignment(0);
/*  597 */     this.textField_5.setEditable(false);
/*  598 */     this.textField_5.setColumns(10);
/*  599 */     this.textField_5.setBorder(null);
/*  600 */     this.textField_5.setBounds(285, 367, 40, 20);
/*  601 */     getContentPane().add(this.textField_5);
/*      */ 
/*  603 */     this.textField_6 = new JTextField();
/*  604 */     this.textField_6.setText("60%");
/*  605 */     this.textField_6.setHorizontalAlignment(0);
/*  606 */     this.textField_6.setEditable(false);
/*  607 */     this.textField_6.setColumns(10);
/*  608 */     this.textField_6.setBorder(null);
/*  609 */     this.textField_6.setBounds(336, 367, 40, 20);
/*  610 */     getContentPane().add(this.textField_6);
/*      */ 
/*  612 */     this.textField_7 = new JTextField();
/*  613 */     this.textField_7.setText("70%");
/*  614 */     this.textField_7.setHorizontalAlignment(0);
/*  615 */     this.textField_7.setEditable(false);
/*  616 */     this.textField_7.setColumns(10);
/*  617 */     this.textField_7.setBorder(null);
/*  618 */     this.textField_7.setBounds(387, 367, 40, 20);
/*  619 */     getContentPane().add(this.textField_7);
/*      */ 
/*  621 */     this.textField_8 = new JTextField();
/*  622 */     this.textField_8.setText("80%");
/*  623 */     this.textField_8.setHorizontalAlignment(0);
/*  624 */     this.textField_8.setEditable(false);
/*  625 */     this.textField_8.setColumns(10);
/*  626 */     this.textField_8.setBorder(null);
/*  627 */     this.textField_8.setBounds(438, 367, 40, 20);
/*  628 */     getContentPane().add(this.textField_8);
/*      */ 
/*  630 */     this.textField_9 = new JTextField();
/*  631 */     this.textField_9.setText("90%");
/*  632 */     this.textField_9.setHorizontalAlignment(0);
/*  633 */     this.textField_9.setEditable(false);
/*  634 */     this.textField_9.setColumns(10);
/*  635 */     this.textField_9.setBorder(null);
/*  636 */     this.textField_9.setBounds(489, 367, 40, 20);
/*  637 */     getContentPane().add(this.textField_9);
/*      */ 
/*  639 */     this.textField_10 = new JTextField();
/*  640 */     this.textField_10.setText("100%");
/*  641 */     this.textField_10.setHorizontalAlignment(0);
/*  642 */     this.textField_10.setEditable(false);
/*  643 */     this.textField_10.setColumns(10);
/*  644 */     this.textField_10.setBorder(null);
/*  645 */     this.textField_10.setBounds(540, 367, 40, 20);
/*  646 */     getContentPane().add(this.textField_10);
/*      */ 
/*  648 */     this.txtFullImage = new JTextField();
/*  649 */     this.txtFullImage.setFont(new Font("SansSerif", 1, 11));
/*  650 */     this.txtFullImage.setEditable(false);
/*  651 */     this.txtFullImage.setBorder(null);
/*  652 */     this.txtFullImage.setText("Full Image");
/*  653 */     this.txtFullImage.setBounds(627, 230, 86, 20);
/*  654 */     getContentPane().add(this.txtFullImage);
/*  655 */     this.txtFullImage.setColumns(10);
/*      */ 
/*  657 */     this.txtRed = new JTextField();
/*  658 */     this.txtRed.setText("Red:");
/*  659 */     this.txtRed.setForeground(ColorTools.red2);
/*  660 */     this.txtRed.setFont(new Font("SansSerif", 0, 11));
/*  661 */     this.txtRed.setEditable(false);
/*  662 */     this.txtRed.setColumns(10);
/*  663 */     this.txtRed.setBorder(null);
/*  664 */     this.txtRed.setBounds(627, 252, 86, 20);
/*  665 */     getContentPane().add(this.txtRed);
/*      */ 
/*  667 */     this.txtGreen = new JTextField();
/*  668 */     this.txtGreen.setText("Green:");
/*  669 */     this.txtGreen.setForeground(ColorTools.green2);
/*  670 */     this.txtGreen.setFont(new Font("SansSerif", 0, 11));
/*  671 */     this.txtGreen.setEditable(false);
/*  672 */     this.txtGreen.setColumns(10);
/*  673 */     this.txtGreen.setBorder(null);
/*  674 */     this.txtGreen.setBounds(627, 273, 86, 20);
/*  675 */     getContentPane().add(this.txtGreen);
/*      */ 
/*  677 */     this.txtBlue = new JTextField();
/*  678 */     this.txtBlue.setText("Blue:");
/*  679 */     this.txtBlue.setForeground(ColorTools.blue2);
/*  680 */     this.txtBlue.setFont(new Font("SansSerif", 0, 11));
/*  681 */     this.txtBlue.setEditable(false);
/*  682 */     this.txtBlue.setColumns(10);
/*  683 */     this.txtBlue.setBorder(null);
/*  684 */     this.txtBlue.setBounds(627, 294, 86, 20);
/*  685 */     getContentPane().add(this.txtBlue);
/*      */ 
/*  687 */     this.txtAverage = new JTextField();
/*  688 */     this.txtAverage.setText("Average:");
/*  689 */     this.txtAverage.setForeground(ColorTools.black2);
/*  690 */     this.txtAverage.setFont(new Font("SansSerif", 0, 11));
/*  691 */     this.txtAverage.setEditable(false);
/*  692 */     this.txtAverage.setColumns(10);
/*  693 */     this.txtAverage.setBorder(null);
/*  694 */     this.txtAverage.setBounds(627, 314, 86, 20);
/*  695 */     getContentPane().add(this.txtAverage);
/*      */ 
/*  697 */     this.txtSelectAverage = new JTextField();
/*  698 */     this.txtSelectAverage.setVisible(false);
/*  699 */     this.txtSelectAverage.setText("Average:");
/*  700 */     this.txtSelectAverage.setForeground(ColorTools.black1);
/*  701 */     this.txtSelectAverage.setFont(new Font("SansSerif", 0, 11));
/*  702 */     this.txtSelectAverage.setEditable(false);
/*  703 */     this.txtSelectAverage.setColumns(10);
/*  704 */     this.txtSelectAverage.setBorder(null);
/*  705 */     this.txtSelectAverage.setBounds(627, 199, 86, 20);
/*  706 */     getContentPane().add(this.txtSelectAverage);
/*      */ 
/*  708 */     this.txtSelectBlue = new JTextField();
/*  709 */     this.txtSelectBlue.setVisible(false);
/*  710 */     this.txtSelectBlue.setText("Blue:");
/*  711 */     this.txtSelectBlue.setForeground(ColorTools.blue1);
/*  712 */     this.txtSelectBlue.setFont(new Font("SansSerif", 0, 11));
/*  713 */     this.txtSelectBlue.setEditable(false);
/*  714 */     this.txtSelectBlue.setColumns(10);
/*  715 */     this.txtSelectBlue.setBorder(null);
/*  716 */     this.txtSelectBlue.setBounds(627, 179, 86, 20);
/*  717 */     getContentPane().add(this.txtSelectBlue);
/*      */ 
/*  719 */     this.txtSelectGreen = new JTextField();
/*  720 */     this.txtSelectGreen.setVisible(false);
/*  721 */     this.txtSelectGreen.setText("Green:");
/*  722 */     this.txtSelectGreen.setForeground(ColorTools.green1);
/*  723 */     this.txtSelectGreen.setFont(new Font("SansSerif", 0, 11));
/*  724 */     this.txtSelectGreen.setEditable(false);
/*  725 */     this.txtSelectGreen.setColumns(10);
/*  726 */     this.txtSelectGreen.setBorder(null);
/*  727 */     this.txtSelectGreen.setBounds(627, 158, 86, 20);
/*  728 */     getContentPane().add(this.txtSelectGreen);
/*      */ 
/*  730 */     this.txtSelectRed = new JTextField();
/*  731 */     this.txtSelectRed.setVisible(false);
/*  732 */     this.txtSelectRed.setText("Red:");
/*  733 */     this.txtSelectRed.setForeground(ColorTools.red1);
/*  734 */     this.txtSelectRed.setFont(new Font("SansSerif", 0, 11));
/*  735 */     this.txtSelectRed.setEditable(false);
/*  736 */     this.txtSelectRed.setColumns(10);
/*  737 */     this.txtSelectRed.setBorder(null);
/*  738 */     this.txtSelectRed.setBounds(627, 137, 86, 20);
/*  739 */     getContentPane().add(this.txtSelectRed);
/*      */ 
/*  741 */     this.txtToolType = new JTextField();
/*  742 */     this.txtToolType.setVisible(false);
/*  744 */     this.txtToolType.setFont(new Font("SansSerif", 1, 11));
/*  745 */     this.txtToolType.setEditable(false);
/*  746 */     this.txtToolType.setColumns(10);
/*  747 */     this.txtToolType.setBorder(null);
/*  748 */     this.txtToolType.setBounds(627, 115, 106, 20);
/*  749 */     getContentPane().add(this.txtToolType);
/*  750 */     this.txtIntensity = new JTextField();
/*  751 */     this.txtIntensity.setBorder(null);
/*  752 */     this.txtIntensity.setEditable(false);
/*  753 */     this.txtIntensity.setText("Percent");
/*  754 */     this.txtIntensity.setBounds(10, 11, 86, 20);
/*  755 */     getContentPane().add(this.txtIntensity);
/*  756 */     this.txtIntensity.setColumns(10);
/*      */ 
/*  758 */     this.txtColorIntensity = new JTextField();
/*  759 */     this.txtColorIntensity.setFont(new Font("SansSerif", 1, 14));
/*  760 */     this.txtColorIntensity.setHorizontalAlignment(0);
/*  761 */     this.txtColorIntensity.setText("Color Intensity (0% = No Intensity, 100% = Max Intensity)");
/*  762 */     this.txtColorIntensity.setEditable(false);
/*  763 */     this.txtColorIntensity.setColumns(10);
/*  764 */     this.txtColorIntensity.setBorder(null);
/*  765 */     this.txtColorIntensity.setBounds(30, 398, 555, 20);
/*  766 */     getContentPane().add(this.txtColorIntensity);
/*      */ 
/*  768 */     JTextArea txtrColorHistogramOf = new JTextArea();
/*  769 */     txtrColorHistogramOf.setFont(new Font("SansSerif", 0, 13));
/*  770 */     txtrColorHistogramOf.setLineWrap(true);
/*  771 */     txtrColorHistogramOf.setWrapStyleWord(true);
/*  772 */     txtrColorHistogramOf.setText("Color histogram of currently displayed image and, if an area is selected, the color histogram in a brighter color.\r\n\r\nIf a masked image is being displayed, the color distribution of the image being masked is drawn.");
/*  773 */     txtrColorHistogramOf.setOpaque(false);
/*  774 */     txtrColorHistogramOf.setEditable(false);
/*  775 */     txtrColorHistogramOf.setBounds(30, 429, 573, 92);
/*  776 */     getContentPane().add(txtrColorHistogramOf);
/*      */ 
/*  778 */     this.txt = new JTextField();
/*  779 */     this.txt.setText("Full Image");
/*  780 */     this.txt.setFont(new Font("SansSerif", 1, 11));
/*  781 */     this.txt.setEditable(false);
/*  782 */     this.txt.setColumns(10);
/*  783 */     this.txt.setBorder(null);
/*  784 */     this.txt.setBounds(10, 553, 87, 20);
/*  785 */     getContentPane().add(this.txt);
this.redOn = new JButton("Red On");
this.redOn.addActionListener(new ActionListener() {
	@Override
	public void actionPerformed(ActionEvent e) {
		ColorHistogramDialog.this.redOn.setVisible(false);
		ColorHistogramDialog.this.redOff.setVisible(true);
		ColorHistogramDialog.this.redT = false;
		ColorHistogramDialog.this.drawImage();
		ColorHistogramDialog.this.txtRed.setVisible(false);
	}
});
this.redOn.setForeground(Color.RED);
this.redOn.setBounds(103, 550, 120, 23);
getContentPane().add(this.redOn);

this.greenOn = new JButton("Green On");
this.greenOn.addActionListener(new ActionListener() {
	@Override
	public void actionPerformed(ActionEvent e) {
		ColorHistogramDialog.this.greenOn.setVisible(false);
		ColorHistogramDialog.this.greenOff.setVisible(true);
		ColorHistogramDialog.this.greenT = false;
		ColorHistogramDialog.this.drawImage();
		ColorHistogramDialog.this.txtGreen.setVisible(false);
	}
});
this.greenOn.setForeground(ColorTools.green1);
this.greenOn.setBounds(223, 550, 120, 23);
getContentPane().add(this.greenOn);

this.blueOn = new JButton("Blue On");
this.blueOn.addActionListener(new ActionListener() {
	@Override
	public void actionPerformed(ActionEvent e) {
		ColorHistogramDialog.this.blueOn.setVisible(false);
		ColorHistogramDialog.this.blueOff.setVisible(true);
		ColorHistogramDialog.this.blueT = false;
		ColorHistogramDialog.this.drawImage();
		ColorHistogramDialog.this.txtBlue.setVisible(false);
	}
});
this.blueOn.setForeground(Color.BLUE);
this.blueOn.setBounds(343, 550, 120, 23);
getContentPane().add(this.blueOn);

this.avgOn = new JButton("Average On");
this.avgOn.addActionListener(new ActionListener() {
	@Override
	public void actionPerformed(ActionEvent e) {
		ColorHistogramDialog.this.avgOn.setVisible(false);
		ColorHistogramDialog.this.avgOff.setVisible(true);
		ColorHistogramDialog.this.avgT = false;
		ColorHistogramDialog.this.drawImage();
		ColorHistogramDialog.this.txtAverage.setVisible(false);
	}
});
this.avgOn.setForeground(Color.BLACK);
this.avgOn.setBounds(463, 550, 120, 23);
getContentPane().add(this.avgOn);

this.redOff = new JButton("Red Off");
this.redOff.setVisible(false);
this.redOff.addActionListener(new ActionListener() {
	@Override
	public void actionPerformed(ActionEvent e) {
		ColorHistogramDialog.this.redOn.setVisible(true);
		ColorHistogramDialog.this.redOff.setVisible(false);
		ColorHistogramDialog.this.redT = true;
		ColorHistogramDialog.this.drawImage();
		ColorHistogramDialog.this.txtRed.setVisible(true);
	}
});
this.redOff.setForeground(Color.RED);
this.redOff.setBounds(103, 550, 120, 23);
getContentPane().add(this.redOff);

this.greenOff = new JButton("Green Off");
this.greenOff.setVisible(false);
this.greenOff.addActionListener(new ActionListener() {
	@Override
	public void actionPerformed(ActionEvent e) {
		ColorHistogramDialog.this.greenOn.setVisible(true);
		ColorHistogramDialog.this.greenOff.setVisible(false);
		ColorHistogramDialog.this.greenT = true;
		ColorHistogramDialog.this.drawImage();
		ColorHistogramDialog.this.txtGreen.setVisible(true);
	}
});
this.greenOff.setForeground(new Color(0, 170, 0));
this.greenOff.setBounds(223, 550, 120, 23);
getContentPane().add(this.greenOff);

this.blueOff = new JButton("Blue Off");
this.blueOff.setVisible(false);
this.blueOff.addActionListener(new ActionListener() {
	@Override
	public void actionPerformed(ActionEvent e) {
		ColorHistogramDialog.this.blueOn.setVisible(true);
		ColorHistogramDialog.this.blueOff.setVisible(false);
		ColorHistogramDialog.this.blueT = true;
		ColorHistogramDialog.this.drawImage();
		ColorHistogramDialog.this.txtBlue.setVisible(true);
	}
});
this.blueOff.setForeground(Color.BLUE);
this.blueOff.setBounds(343, 550, 120, 23);
getContentPane().add(this.blueOff);

this.avgOff = new JButton("Average Off");
this.avgOff.setVisible(false);
this.avgOff.addActionListener(new ActionListener() {
	@Override
	public void actionPerformed(ActionEvent e) {
		ColorHistogramDialog.this.avgOn.setVisible(true);
		ColorHistogramDialog.this.avgOff.setVisible(false);
		ColorHistogramDialog.this.avgT = true;
		ColorHistogramDialog.this.drawImage();
		ColorHistogramDialog.this.txtAverage.setVisible(true);
	}
});
this.avgOff.setForeground(Color.BLACK);
this.avgOff.setBounds(463, 550, 120, 23);
getContentPane().add(this.avgOff);

this.sRedOn = new JButton("Red On");
this.sRedOn.addActionListener(new ActionListener() {
	@Override
	public void actionPerformed(ActionEvent arg0) {
		ColorHistogramDialog.this.sRedOn.setVisible(false);
		ColorHistogramDialog.this.sRedOff.setVisible(true);
		ColorHistogramDialog.this.redS = false;
		ColorHistogramDialog.this.drawImage();
		ColorHistogramDialog.this.txtSelectRed.setVisible(false);
	}
});
this.sRedOn.setForeground(Color.RED);
this.sRedOn.setBounds(103, 519, 120, 23);
getContentPane().add(this.sRedOn);

this.sGreenOn = new JButton("Green On");
this.sGreenOn.addActionListener(new ActionListener() {
	@Override
	public void actionPerformed(ActionEvent arg0) {
		ColorHistogramDialog.this.sGreenOn.setVisible(false);
		ColorHistogramDialog.this.sGreenOff.setVisible(true);
		ColorHistogramDialog.this.greenS = false;
		ColorHistogramDialog.this.drawImage();
		ColorHistogramDialog.this.txtSelectGreen.setVisible(false);
	}
});
this.sGreenOn.setForeground(new Color(0, 170, 0));
this.sGreenOn.setBounds(223, 519, 120, 23);
getContentPane().add(this.sGreenOn);

this.sBlueOn = new JButton("Blue On");
this.sBlueOn.addActionListener(new ActionListener() {
	@Override
	public void actionPerformed(ActionEvent arg0) {
		ColorHistogramDialog.this.sBlueOn.setVisible(false);
		ColorHistogramDialog.this.sBlueOff.setVisible(true);
		ColorHistogramDialog.this.blueS = false;
		ColorHistogramDialog.this.drawImage();
		ColorHistogramDialog.this.txtSelectBlue.setVisible(false);
	}
});
this.sBlueOn.setForeground(Color.BLUE);
this.sBlueOn.setBounds(343, 519, 120, 23);
getContentPane().add(this.sBlueOn);

this.sAvgOn = new JButton("Average On");
this.sAvgOn.addActionListener(new ActionListener() {
	@Override
	public void actionPerformed(ActionEvent arg0) {
		ColorHistogramDialog.this.sAvgOn.setVisible(false);
		ColorHistogramDialog.this.sAvgOff.setVisible(true);
		ColorHistogramDialog.this.avgS = false;
		ColorHistogramDialog.this.drawImage();
		ColorHistogramDialog.this.txtSelectAverage.setVisible(false);
	}
});
this.sAvgOn.setForeground(Color.BLACK);
this.sAvgOn.setBounds(463, 519, 120, 23);
getContentPane().add(this.sAvgOn);

this.sRedOff = new JButton("Red Off");
this.sRedOff.addActionListener(new ActionListener() {
	@Override
	public void actionPerformed(ActionEvent arg0) {
		ColorHistogramDialog.this.sRedOn.setVisible(true);
		ColorHistogramDialog.this.sRedOff.setVisible(false);
		ColorHistogramDialog.this.redS = true;
		ColorHistogramDialog.this.drawImage();
		ColorHistogramDialog.this.txtSelectRed.setVisible(true);
	}
});
this.sRedOff.setVisible(false);
this.sRedOff.setForeground(Color.RED);
this.sRedOff.setBounds(103, 519, 120, 23);
getContentPane().add(this.sRedOff);

this.sGreenOff = new JButton("Green Off");
this.sGreenOff.addActionListener(new ActionListener() {
	@Override
	public void actionPerformed(ActionEvent arg0) {
		ColorHistogramDialog.this.sGreenOn.setVisible(true);
		ColorHistogramDialog.this.sGreenOff.setVisible(false);
		ColorHistogramDialog.this.greenS = true;
		ColorHistogramDialog.this.drawImage();
		ColorHistogramDialog.this.txtSelectGreen.setVisible(true);
	}
});
this.sGreenOff.setVisible(false);
this.sGreenOff.setForeground(new Color(0, 170, 0));
this.sGreenOff.setBounds(223, 519, 120, 23);
getContentPane().add(this.sGreenOff);

this.sBlueOff = new JButton("Blue Off");
this.sBlueOff.addActionListener(new ActionListener() {
	@Override
	public void actionPerformed(ActionEvent arg0) {
		ColorHistogramDialog.this.sBlueOn.setVisible(true);
		ColorHistogramDialog.this.sBlueOff.setVisible(false);
		ColorHistogramDialog.this.blueS = true;
		ColorHistogramDialog.this.drawImage();
		ColorHistogramDialog.this.txtSelectBlue.setVisible(true);
	}
});
this.sBlueOff.setVisible(false);
this.sBlueOff.setForeground(Color.BLUE);
this.sBlueOff.setBounds(343, 519, 120, 23);
getContentPane().add(this.sBlueOff);

this.sAvgOff = new JButton("Average Off");
this.sAvgOff.addActionListener(new ActionListener() {
	@Override
	public void actionPerformed(ActionEvent arg0) {
		ColorHistogramDialog.this.sAvgOn.setVisible(true);
		ColorHistogramDialog.this.sAvgOff.setVisible(false);
		ColorHistogramDialog.this.avgS = true;
		ColorHistogramDialog.this.drawImage();
		ColorHistogramDialog.this.txtSelectAverage.setVisible(true);
	}
});
this.sAvgOff.setVisible(false);
this.sAvgOff.setForeground(Color.BLACK);
this.sAvgOff.setBounds(463, 519, 120, 23);
getContentPane().add(this.sAvgOff);

if(this.hsv)
{
	this.sRedOn.setText("Hue on");
	this.sRedOn.setForeground(Color.MAGENTA);
	this.sRedOff.setText("Hue off");
	this.sRedOff.setForeground(Color.MAGENTA);
	this.txtSelectRed.setForeground(Color.MAGENTA);
	this.sGreenOn.setText("Saturation on");
	this.sGreenOn.setForeground(Color.ORANGE.darker());
	this.sGreenOff.setText("Hue off");
	this.sGreenOff.setForeground(Color.ORANGE.darker());
	this.txtSelectGreen.setForeground(Color.ORANGE.darker());
	this.sBlueOn.setText("Value on");
	this.sBlueOn.setForeground(Color.BLACK);
	this.sBlueOff.setText("Value off");
	this.sBlueOff.setForeground(Color.BLACK);
	this.txtSelectBlue.setForeground(Color.BLACK);
	
}
/*      */ 
/* 1019 */     this.txtSelectedArea = new JTextField();
/* 1020 */     this.txtSelectedArea.setText("Selected Area");
/* 1021 */     this.txtSelectedArea.setFont(new Font("SansSerif", 1, 11));
/* 1022 */     this.txtSelectedArea.setEditable(false);
/* 1023 */     this.txtSelectedArea.setColumns(10);
/* 1024 */     this.txtSelectedArea.setBorder(null);
/* 1025 */     this.txtSelectedArea.setBounds(10, 522, 87, 20);
/* 1026 */     getContentPane().add(this.txtSelectedArea);
/*      */ 
/* 1028 */     JButton btnPrintGraph = new JButton("Print Graph");
/* 1029 */     btnPrintGraph.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent arg0) {
/* 1031 */         PrinterJob pj = ColorHistogramDialog.this.entrance.getPrinterJob();
/* 1032 */         pj.setPrintable(new Printable()
/*      */         {
/*      */           public int print(Graphics graphics, PageFormat pf, int pageIndex) throws PrinterException
/*      */           {
/* 1036 */             if (pageIndex > 0) {
/* 1037 */               return 1;
/*      */             }
/* 1039 */             Graphics2D g2d = (Graphics2D)graphics;
/* 1040 */             g2d.translate(pf.getImageableX(), pf.getImageableY());
/* 1041 */             g2d.drawImage(ColorHistogramDialog.this.graph, 0, 0, null);
/* 1042 */             return 0;
/*      */           }
/*      */         });
/* 1045 */         if (pj.printDialog())
/*      */           try {
/* 1047 */             pj.print();
/*      */           } catch (PrinterException e1) {
/* 1049 */             e1.printStackTrace();
/*      */           }
/*      */       }
/*      */     });
/* 1054 */     btnPrintGraph.setBounds(613, 550, 120, 23);
/* 1055 */     getContentPane().add(btnPrintGraph);
/*      */ 
/* 1057 */     JButton btnExportAsText = new JButton("Export as Text");
/* 1058 */     btnExportAsText.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*      */         ColorHistogramDialog.this.exportToText();
/*      */       }
/*      */     });
/* 1097 */     btnExportAsText.setBounds(613, 519, 120, 23);
/* 1098 */     getContentPane().add(btnExportAsText);
/*      */ 
/* 1100 */     JButton btnExportAsJpg = new JButton("Export as JPG");
/* 1101 */     btnExportAsJpg.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*      */         File f;
/*      */         try {
/* 1105 */           FileDialog fc = new FileDialog(new Shell(), 8192);
/* 1106 */           String[] fileTypes = { "Images *.jpg" };
/* 1107 */           String[] fileExtensions = { "*.jpg" };
/* 1108 */           fc.setFilterExtensions(fileExtensions);
/* 1109 */           fc.setFilterNames(fileTypes);
/* 1110 */           fc.setOverwrite(true);
/* 1111 */           fc.setFileName("Untitled_Graph.jpg");
/* 1112 */           fc.setText("Export as JPG");
/* 1113 */           fc.open();
/* 1114 */           f = new File(fc.getFilterPath() + "\\" + fc.getFileName());
/*      */         }
/*      */         catch (Throwable t)
/*      */         {
/* 1116 */           JFileChooser fc = new JFileChooser();
/* 1117 */           fc.setSelectedFile(new File("Untitled_Graph.jpg"));
/* 1118 */           fc.setDialogTitle("Export as JPG");
/* 1119 */           fc.setFileFilter(new FileFilter()
/*      */           {
/*      */             public boolean accept(File f) {
/* 1122 */               return (f.getName().endsWith(".jpg")) || (f.isDirectory());
/*      */             }
/*      */ 
/*      */             public String getDescription() {
/* 1126 */               return "JPG Images";
/*      */             }
/*      */           });
/* 1129 */           fc.showSaveDialog(null);
/* 1130 */           f = fc.getSelectedFile();
/*      */         }
/*      */         try {
/* 1133 */           if (f != null)
/* 1134 */             ImageIO.write(ColorHistogramDialog.this.graph, "jpg", f);
/*      */         }
/*      */         catch (IOException localIOException)
/*      */         {
/*      */         }
/*      */       }
/*      */     });
/* 1138 */     btnExportAsJpg.setBounds(613, 485, 120, 23);
/* 1139 */     getContentPane().add(btnExportAsJpg);
/*      */   }

			private void setTxtToolType(String tool){
				/*  341 */     if (tool.contains("Line")) {
				/*  344 */       this.txtToolType.setText("Along Line");
				/*  345 */     } else if (tool.contains("Rectangle")) {
				/*  347 */       this.txtToolType.setText("Selected Area");
				/*  348 */     } else if (tool.contains("Polygon")) {
				/*  350 */       this.txtToolType.setText("Selected Area");
				/*  351 */     } else if (tool.contains("Path")) {
				/*  354 */       this.txtToolType.setText("Along Path");
				/*      */     }
			}


/*      */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.dialogs.ColorHistogramDialog
 * JD-Core Version:    0.6.2
 */