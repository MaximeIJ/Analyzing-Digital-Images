 package org.gss.adi.dialogs;
 
 import java.awt.BorderLayout;
 import java.awt.Container;
 import java.awt.Font;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import javax.swing.JButton;
 import javax.swing.JDialog;
 import javax.swing.JPanel;
 import javax.swing.JTextArea;
 import javax.swing.JTextField;
 import javax.swing.border.EmptyBorder;
 import org.gss.adi.NumberField;
 import org.gss.adi.ToolAdder;
 import org.gss.adi.datapanels.NDVIMaskPolygonPanel;
 import org.gss.adi.datapanels.NDVIMaskRectanglePanel;
 
 public class NDVIMaskDialog extends JDialog
 {
   private static final long serialVersionUID = 2215826177255102770L;
   private final JPanel contentPanel = new JPanel();
   private JTextField txtNdviMin;
   private JTextField textField;
   private JTextField txtNdviMax;
   private JTextField textField_2;
   private NDVIMaskDialog me = this;
 
   public NDVIMaskDialog(final ToolAdder ta)
   {
     setBounds(100, 100, 450, 466);
     getContentPane().setLayout(new BorderLayout());
     this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
     getContentPane().add(this.contentPanel, "Center");
     setAlwaysOnTop(true);
     this.contentPanel.setLayout(null);
 
     JTextArea txtrUseTheText = new JTextArea();
     txtrUseTheText.setFont(new Font("SansSerif", 0, 13));
     txtrUseTheText.setText("Use the text boxes below to type the minimum and maximum NDVI values to be used to study land cover change over time.\r\n\r\nNDVI values range between -1.00 and 1.00. Type only numbers in the text boxes below.\r\n\r\nThe percent of pixels between these NDVI values will be automatically calculated for the selected area (rectangle or polygon).\r\n\r\nIf an area hasn't been created, click the \"Use Values\" then draw a rectangle or polygon on one of the images.\r\n\r\nIf an area has been created, click the \"Use Values\" to graph the amount of pixels within the new NDVI values.\r\n\r\nTIP: Keep this window open to quickly change NDVI values. Close the window when done.");
     txtrUseTheText.setOpaque(false);
     txtrUseTheText.setWrapStyleWord(true);
     txtrUseTheText.setLineWrap(true);
     txtrUseTheText.setEditable(false);
     txtrUseTheText.setBounds(12, 13, 410, 327);
     this.contentPanel.add(txtrUseTheText);
 
     this.txtNdviMin = new JTextField();
     this.txtNdviMin.setBorder(null);
     this.txtNdviMin.setEditable(false);
     this.txtNdviMin.setText("NDVI Min");
     this.txtNdviMin.setBounds(22, 353, 86, 20);
     this.contentPanel.add(this.txtNdviMin);
     this.txtNdviMin.setColumns(10);
 
     this.textField = new NumberField();
     this.textField.setHorizontalAlignment(11);
     this.textField.setText("-1");
     this.textField.setColumns(10);
     this.textField.setBounds(120, 353, 60, 20);
     this.contentPanel.add(this.textField);
 
     this.txtNdviMax = new JTextField();
     this.txtNdviMax.setEditable(false);
     this.txtNdviMax.setBorder(null);
     this.txtNdviMax.setText("NDVI Max");
     this.txtNdviMax.setColumns(10);
     this.txtNdviMax.setBounds(218, 353, 86, 20);
     this.contentPanel.add(this.txtNdviMax);
 
     this.textField_2 = new NumberField();
     this.textField_2.setHorizontalAlignment(11);
     this.textField_2.setText("1");
     this.textField_2.setColumns(10);
     this.textField_2.setBounds(316, 353, 60, 20);
     this.contentPanel.add(this.textField_2);
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