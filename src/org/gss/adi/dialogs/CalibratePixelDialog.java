/*     */ package org.gss.adi.dialogs;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ import org.gss.adi.Entrance;
/*     */ 
/*     */ public class CalibratePixelDialog extends JDialog
/*     */ {
/*     */   private static final long serialVersionUID = -6299417962340902177L;
/*  20 */   private final JPanel contentPanel = new JPanel();
/*     */   private JTextField txtSelectMethodTo;
/*     */   private Entrance entrance;
/*     */   private JDialog me;
/*     */   private boolean TimeSeries;
/*     */ 
/*     */   public CalibratePixelDialog(Entrance e, Boolean timeSeries)
/*     */   {
/*  29 */     setModal(true);
/*  30 */     this.me = this;
/*  31 */     this.entrance = e;
/*  32 */     this.TimeSeries = timeSeries.booleanValue();
/*  33 */     if (timeSeries.booleanValue())
/*  34 */       this.entrance.setTimeSeriesMeasurement(null);
/*     */     else
/*  36 */       this.entrance.setMeasurement(null);
/*  37 */     setBounds(100, 100, 630, 294);
/*  38 */     setTitle("Select Method of Pixel Size Calibration");
/*  39 */     setAlwaysOnTop(true);
/*  40 */     getContentPane().setLayout(new BorderLayout());
/*  41 */     this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
/*  42 */     getContentPane().add(this.contentPanel, "Center");
/*  43 */     this.contentPanel.setLayout(null);
/*     */ 
/*  45 */     this.txtSelectMethodTo = new JTextField();
/*  46 */     this.txtSelectMethodTo.setBorder(null);
/*  47 */     this.txtSelectMethodTo.setEditable(false);
/*  48 */     this.txtSelectMethodTo.setOpaque(false);
/*  49 */     this.txtSelectMethodTo.setFont(new Font("SansSerif", 1, 14));
/*  50 */     this.txtSelectMethodTo.setText("Select Method to Calibrate the Pixel Size");
/*  51 */     this.txtSelectMethodTo.setHorizontalAlignment(0);
/*  52 */     this.txtSelectMethodTo.setBounds(12, 13, 590, 20);
/*  53 */     this.contentPanel.add(this.txtSelectMethodTo);
/*  54 */     this.txtSelectMethodTo.setColumns(10);
/*     */ 
/*  57 */     JButton btnNewButton = new JButton("Known Pixel Scale");
/*  58 */     btnNewButton.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/*  60 */         CalibratePixelDialog.this.me.dispose();
/*  61 */         KnownPixelSizeDialog dialog = new KnownPixelSizeDialog(CalibratePixelDialog.this.entrance, CalibratePixelDialog.this.TimeSeries);
/*  62 */         dialog.setDefaultCloseOperation(2);
/*  63 */         dialog.setVisible(true);
/*     */       }
/*     */     });
/*  66 */     btnNewButton.setBounds(12, 45, 180, 25);
/*  67 */     this.contentPanel.add(btnNewButton);
/*     */ 
/*  70 */     JTextArea txtrClickIfYou = new JTextArea();
/*  71 */     txtrClickIfYou.setFont(new Font("SansSerif", 0, 13));
/*  72 */     txtrClickIfYou.setText("Click if you know the size of the pixels, which is common for orthophotographs from aerial reconnaissance and satellite imagery.");
/*  73 */     txtrClickIfYou.setLineWrap(true);
/*  74 */     txtrClickIfYou.setOpaque(false);
/*  75 */     txtrClickIfYou.setEditable(false);
/*  76 */     txtrClickIfYou.setWrapStyleWord(true);
/*  77 */     txtrClickIfYou.setBounds(204, 46, 398, 46);
/*  78 */     this.contentPanel.add(txtrClickIfYou);
/*     */ 
/*  81 */     JButton btnScalePresentIn = new JButton("Scale Present in Image");
/*  82 */     btnScalePresentIn.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/*  84 */         CalibratePixelDialog.this.me.dispose();
/*  85 */         ManualCalibrationDialog dialog = new ManualCalibrationDialog(CalibratePixelDialog.this.entrance, CalibratePixelDialog.this.TimeSeries);
/*  86 */         dialog.setDefaultCloseOperation(2);
/*  87 */         dialog.setVisible(true);
/*     */       }
/*     */     });
/*  90 */     btnScalePresentIn.setBounds(12, 115, 180, 25);
/*  91 */     this.contentPanel.add(btnScalePresentIn);
/*     */ 
/*  94 */     JTextArea txtrClickIfThere = new JTextArea();
/*  95 */     txtrClickIfThere.setWrapStyleWord(true);
/*  96 */     txtrClickIfThere.setText("Click if there is a linear scale located in the image.  This includes photographed objects of known length (ruler, penny, clipboard, etc.) or a distance scale on digital maps or satellite imagery.");
/*  97 */     txtrClickIfThere.setOpaque(false);
/*  98 */     txtrClickIfThere.setLineWrap(true);
/*  99 */     txtrClickIfThere.setFont(new Font("SansSerif", 0, 13));
/* 100 */     txtrClickIfThere.setEditable(false);
/* 101 */     txtrClickIfThere.setBounds(204, 116, 398, 63);
/* 102 */     this.contentPanel.add(txtrClickIfThere);
/*     */ 
/* 105 */     JTextArea txtrClickIfThere_1 = new JTextArea();
/* 106 */     txtrClickIfThere_1.setWrapStyleWord(true);
/* 107 */     txtrClickIfThere_1.setText("Click if there is no way to know the size of the pixels in the image.");
/* 108 */     txtrClickIfThere_1.setOpaque(false);
/* 109 */     txtrClickIfThere_1.setLineWrap(true);
/* 110 */     txtrClickIfThere_1.setFont(new Font("SansSerif", 0, 13));
/* 111 */     txtrClickIfThere_1.setEditable(false);
/* 112 */     txtrClickIfThere_1.setBounds(204, 186, 398, 59);
/* 113 */     this.contentPanel.add(txtrClickIfThere_1);
/*     */ 
/* 116 */     JButton btnNewButton_1 = new JButton("None");
/* 117 */     btnNewButton_1.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 119 */         CalibratePixelDialog.this.me.dispose();
/*     */       }
/*     */     });
/* 122 */     btnNewButton_1.setBounds(12, 185, 180, 25);
/* 123 */     this.contentPanel.add(btnNewButton_1);
/*     */   }
/*     */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.dialogs.CalibratePixelDialog
 * JD-Core Version:    0.6.2
 */