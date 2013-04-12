/*     */ package org.gss.adi.dialogs;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Container;
/*     */ import java.awt.FlowLayout;
/*     */ import java.awt.Font;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ import org.gss.adi.Entrance;
/*     */ import org.gss.adi.NumberField;
/*     */ import org.gss.adi.tools.Measurement;
/*     */ 
/*     */ public class KnownPixelSizeDialog extends JDialog
/*     */ {
/*     */   private static final long serialVersionUID = 4686667822699047326L;
/*  24 */   private final JPanel contentPanel = new JPanel();
/*     */   private JTextField txtLengthOfPixel;
/*     */   private NumberField lengthOfPixel;
/*     */   private JTextField txtUnitOfLength;
/*     */   private JTextField unitOfLength;
/*     */   private Entrance entrance;
/*     */   private JDialog me;
/*     */   private boolean TimeSeries;
/*     */ 
/*     */   public KnownPixelSizeDialog(Entrance e, boolean timeSeries)
/*     */   {
/*  38 */     setModal(true);
/*  39 */     this.me = this;
/*  40 */     setTitle("The Size of Pixels is Known");
/*  41 */     this.entrance = e;
/*  42 */     this.TimeSeries = timeSeries;
/*  43 */     setBounds(100, 100, 617, 488);
/*  44 */     getContentPane().setLayout(new BorderLayout());
/*  45 */     this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
/*  46 */     getContentPane().add(this.contentPanel, "Center");
/*  47 */     this.contentPanel.setLayout(null);
/*     */ 
/*  49 */     JTextArea txtrInTheUppermost = new JTextArea();
/*  50 */     txtrInTheUppermost.setFont(new Font("SansSerif", 0, 13));
/*  51 */     txtrInTheUppermost.setText("In the uppermost white box, type the length a pixel represents in the image.  Type only the number. \r\n\r\nIn the lower white box, type the two letter abbreviation of the unit of the scale. For example, type \"mi\" for miles, \"cm\" for centimeters.  \r\n\r\nClick 'Done' to go back to the main window.  \r\n\r\nIf you make a mistake, run the calibration procedure again by selecting \"Calibrate Length\" in the File menu.");
/*  52 */     txtrInTheUppermost.setOpaque(false);
/*  53 */     txtrInTheUppermost.setEditable(false);
/*  54 */     txtrInTheUppermost.setLineWrap(true);
/*  55 */     txtrInTheUppermost.setWrapStyleWord(true);
/*  56 */     txtrInTheUppermost.setBounds(12, 13, 577, 201);
/*  57 */     this.contentPanel.add(txtrInTheUppermost);
/*     */ 
/*  59 */     this.txtLengthOfPixel = new JTextField();
/*  60 */     this.txtLengthOfPixel.setFont(new Font("SansSerif", 0, 11));
/*  61 */     this.txtLengthOfPixel.setBorder(null);
/*  62 */     this.txtLengthOfPixel.setOpaque(false);
/*  63 */     this.txtLengthOfPixel.setEditable(false);
/*  64 */     this.txtLengthOfPixel.setText("Length of Pixel in Image");
/*  65 */     this.txtLengthOfPixel.setBounds(182, 227, 157, 20);
/*  66 */     this.contentPanel.add(this.txtLengthOfPixel);
/*  67 */     this.txtLengthOfPixel.setColumns(10);
/*     */ 
/*  69 */     this.lengthOfPixel = new NumberField();
/*  70 */     this.lengthOfPixel.setBounds(335, 227, 72, 20);
/*  71 */     this.contentPanel.add(this.lengthOfPixel);
/*  72 */     this.lengthOfPixel.setColumns(10);
/*     */ 
/*  74 */     this.txtUnitOfLength = new JTextField();
/*  75 */     this.txtUnitOfLength.setFont(new Font("SansSerif", 0, 11));
/*  76 */     this.txtUnitOfLength.setText("Unit of Length (2 letters)");
/*  77 */     this.txtUnitOfLength.setOpaque(false);
/*  78 */     this.txtUnitOfLength.setEditable(false);
/*  79 */     this.txtUnitOfLength.setColumns(10);
/*  80 */     this.txtUnitOfLength.setBorder(null);
/*  81 */     this.txtUnitOfLength.setBounds(182, 260, 157, 20);
/*  82 */     this.contentPanel.add(this.txtUnitOfLength);
/*     */ 
/*  84 */     this.unitOfLength = new JTextField();
/*  85 */     this.unitOfLength.setColumns(10);
/*  86 */     this.unitOfLength.setBounds(335, 260, 72, 20);
/*  87 */     this.contentPanel.add(this.unitOfLength);
/*     */ 
/*  89 */     JButton btnNewButton = new JButton("Done");
/*  90 */     btnNewButton.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/*  92 */         if (KnownPixelSizeDialog.this.lengthOfPixel.getText().length() == 0) {
/*  93 */           JOptionPane.showMessageDialog(null, 
/*  94 */             "You must input a pixel length or cancel the pixel calibration.");
/*  95 */         } else if (KnownPixelSizeDialog.this.unitOfLength.getText().length() == 0) {
/*  96 */           JOptionPane.showMessageDialog(null, 
/*  97 */             "You must input a unit of measure or cancel the pixel calibration.");
/*  98 */         } else if (KnownPixelSizeDialog.this.unitOfLength.getText().length() > 2) {
/*  99 */           JOptionPane.showMessageDialog(null, 
/* 100 */             "The unit of measure must be one or two characters.");
/*     */         } else {
/* 102 */           if (KnownPixelSizeDialog.this.TimeSeries)
/* 103 */             KnownPixelSizeDialog.this.entrance.setTimeSeriesMeasurement(new Measurement(new Double(KnownPixelSizeDialog.this.lengthOfPixel.getText()), 
/* 104 */               KnownPixelSizeDialog.this.unitOfLength.getText()));
/*     */           else
/* 106 */             KnownPixelSizeDialog.this.entrance.setMeasurement(new Measurement(new Double(KnownPixelSizeDialog.this.lengthOfPixel.getText()), 
/* 107 */               KnownPixelSizeDialog.this.unitOfLength.getText()));
/* 108 */           KnownPixelSizeDialog.this.me.dispose();
/*     */         }
/*     */       }
/*     */     });
/* 112 */     btnNewButton.setBounds(335, 293, 72, 23);
/* 113 */     this.contentPanel.add(btnNewButton);
/*     */ 
/* 115 */     JTextArea txtrExampleIfUsing = new JTextArea();
/* 116 */     txtrExampleIfUsing.setOpaque(false);
/* 117 */     txtrExampleIfUsing.setEditable(false);
/* 118 */     txtrExampleIfUsing.setFont(new Font("SansSerif", 0, 13));
/* 119 */     txtrExampleIfUsing.setLineWrap(true);
/* 120 */     txtrExampleIfUsing.setWrapStyleWord(true);
/* 121 */     txtrExampleIfUsing.setText("Example: if using Landsat imagery, the pixel size is 28.5 m.\r\n\r\nTip: Since digital images contain many pixels, consider converting to a larger unit, such as convert 28.5 m to 0.0285 km.");
/* 122 */     txtrExampleIfUsing.setBounds(12, 330, 577, 76);
/* 123 */     this.contentPanel.add(txtrExampleIfUsing);
/*     */ 
/* 125 */     JPanel buttonPane = new JPanel();
/* 126 */     buttonPane.setLayout(new FlowLayout(1));
/* 127 */     getContentPane().add(buttonPane, "South");
/*     */ 
/* 129 */     JButton cancelButton = new JButton("Cancel");
/* 130 */     cancelButton.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent e) {
/* 132 */         KnownPixelSizeDialog.this.me.dispose();
/*     */       }
/*     */     });
/* 135 */     cancelButton.setActionCommand("Cancel");
/* 136 */     buttonPane.add(cancelButton);
/*     */   }
/*     */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.dialogs.KnownPixelSizeDialog
 * JD-Core Version:    0.6.2
 */