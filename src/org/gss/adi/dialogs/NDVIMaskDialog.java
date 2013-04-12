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
/*     */ import org.gss.adi.NumberField;
/*     */ import org.gss.adi.ToolAdder;
/*     */ import org.gss.adi.datapanels.NDVIMaskPolygonPanel;
/*     */ import org.gss.adi.datapanels.NDVIMaskRectanglePanel;
/*     */ 
/*     */ public class NDVIMaskDialog extends JDialog
/*     */ {
/*     */   private static final long serialVersionUID = 2215826177255102770L;
/*  26 */   private final JPanel contentPanel = new JPanel();
/*     */   private JTextField txtNdviMin;
/*     */   private JTextField textField;
/*     */   private JTextField txtNdviMax;
/*     */   private JTextField textField_2;
/*  31 */   private NDVIMaskDialog me = this;
/*     */ 
/*     */   public NDVIMaskDialog(final ToolAdder ta)
/*     */   {
/*  37 */     setBounds(100, 100, 450, 466);
/*  38 */     getContentPane().setLayout(new BorderLayout());
/*  39 */     this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
/*  40 */     getContentPane().add(this.contentPanel, "Center");
/*  41 */     setAlwaysOnTop(true);
/*  42 */     this.contentPanel.setLayout(null);
/*     */ 
/*  44 */     JTextArea txtrUseTheText = new JTextArea();
/*  45 */     txtrUseTheText.setFont(new Font("SansSerif", 0, 13));
/*  46 */     txtrUseTheText.setText("Use the text boxes below to type the minimum and maximum NDVI values to be used to study land cover change over time.\r\n\r\nNDVI values range between -1.00 and 1.00. Type only numbers in the text boxes below.\r\n\r\nThe percent of pixels between these NDVI values will be automatically calculated for the selected area (rectangle or polygon).\r\n\r\nIf an area hasn't been created, click the \"Use Values\" then draw a rectangle or polygon on one of the images.\r\n\r\nIf an area has been created, click the \"Use Values\" to graph the amount of pixels within the new NDVI values.\r\n\r\nTIP: Keep this window open to quickly change NDVI values. Close the window when done.");
/*  47 */     txtrUseTheText.setOpaque(false);
/*  48 */     txtrUseTheText.setWrapStyleWord(true);
/*  49 */     txtrUseTheText.setLineWrap(true);
/*  50 */     txtrUseTheText.setEditable(false);
/*  51 */     txtrUseTheText.setBounds(12, 13, 410, 327);
/*  52 */     this.contentPanel.add(txtrUseTheText);
/*     */ 
/*  55 */     this.txtNdviMin = new JTextField();
/*  56 */     this.txtNdviMin.setBorder(null);
/*  57 */     this.txtNdviMin.setEditable(false);
/*  58 */     this.txtNdviMin.setText("NDVI Min");
/*  59 */     this.txtNdviMin.setBounds(22, 353, 86, 20);
/*  60 */     this.contentPanel.add(this.txtNdviMin);
/*  61 */     this.txtNdviMin.setColumns(10);
/*     */ 
/*  64 */     this.textField = new NumberField();
/*  65 */     this.textField.setHorizontalAlignment(11);
/*  66 */     this.textField.setText("-1");
/*  67 */     this.textField.setColumns(10);
/*  68 */     this.textField.setBounds(120, 353, 60, 20);
/*  69 */     this.contentPanel.add(this.textField);
/*     */ 
/*  72 */     this.txtNdviMax = new JTextField();
/*  73 */     this.txtNdviMax.setEditable(false);
/*  74 */     this.txtNdviMax.setBorder(null);
/*  75 */     this.txtNdviMax.setText("NDVI Max");
/*  76 */     this.txtNdviMax.setColumns(10);
/*  77 */     this.txtNdviMax.setBounds(218, 353, 86, 20);
/*  78 */     this.contentPanel.add(this.txtNdviMax);
/*     */ 
/*  81 */     this.textField_2 = new NumberField();
/*  82 */     this.textField_2.setHorizontalAlignment(11);
/*  83 */     this.textField_2.setText("1");
/*  84 */     this.textField_2.setColumns(10);
/*  85 */     this.textField_2.setBounds(316, 353, 60, 20);
/*  86 */     this.contentPanel.add(this.textField_2);
/*     */ 
/*  89 */     JButton btnNewButton = new JButton("Use Values");
/*  90 */     btnNewButton.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent arg0) {
/*     */         try { ((NDVIMaskRectanglePanel)ta.dataPanel).setMinMax(new Float(NDVIMaskDialog.this.textField.getText()), 
/*  93 */             new Float(NDVIMaskDialog.this.textField_2.getText()));
/*  94 */           ((NDVIMaskRectanglePanel)ta.dataPanel).repaint(); } catch (Exception localException) {
/*     */         }try { ((NDVIMaskPolygonPanel)ta.dataPanel).setMinMax(new Float(NDVIMaskDialog.this.textField.getText()), 
/*  96 */             new Float(NDVIMaskDialog.this.textField_2.getText()));
/*  97 */           ((NDVIMaskRectanglePanel)ta.dataPanel).repaint();
/*     */         }
/*     */         catch (Exception localException1)
/*     */         {
/*     */         }
/*     */       }
/*     */     });
/* 101 */     btnNewButton.setBounds(32, 386, 148, 25);
/* 102 */     this.contentPanel.add(btnNewButton);
/*     */ 
/* 104 */     JButton btnClose = new JButton("Close");
/* 105 */     btnClose.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent arg0) {
/* 107 */         NDVIMaskDialog.this.me.dispose();
/*     */       }
/*     */     });
/* 110 */     btnClose.setBounds(218, 387, 148, 25);
/* 111 */     this.contentPanel.add(btnClose);
/*     */   }
/*     */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.dialogs.NDVIMaskDialog
 * JD-Core Version:    0.6.2
 */