/*    */ package org.gss.adi.dialogs;
/*    */ 
/*    */ import java.awt.BorderLayout;
/*    */ import java.awt.Component;
/*    */ import java.awt.Container;
/*    */ import java.awt.FlowLayout;
/*    */ import java.awt.Font;
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.ActionListener;
/*    */ import javax.swing.Box;
/*    */ import javax.swing.JButton;
/*    */ import javax.swing.JDialog;
/*    */ import javax.swing.JPanel;
/*    */ import javax.swing.JRootPane;
/*    */ import javax.swing.JTextPane;
/*    */ import javax.swing.border.EmptyBorder;
/*    */ 
/*    */ public class KeepSettingsDialog extends JDialog
/*    */ {
/*    */   private static final long serialVersionUID = 8760501318084579383L;
/* 20 */   private final JPanel contentPanel = new JPanel();
/*    */   protected JButton okButton;
/*    */   protected JButton cancelButton;
/*    */   static boolean result;
/*    */ 
/*    */   public static boolean launch()
/*    */   {
/* 26 */     final KeepSettingsDialog ksd = new KeepSettingsDialog();
/* 27 */     ksd.okButton.addActionListener(new ActionListener() {
/*    */       public void actionPerformed(ActionEvent arg0) {
/* 29 */         KeepSettingsDialog.result = true;
/* 30 */         ksd.dispose();
/*    */       }
/*    */     });
/* 33 */     ksd.cancelButton.addActionListener(new ActionListener() {
/*    */       public void actionPerformed(ActionEvent arg0) {
/* 35 */         KeepSettingsDialog.result = false;
/* 36 */         ksd.dispose();
/*    */       }
/*    */     });
/* 39 */     ksd.setVisible(true);
/* 40 */     return result;
/*    */   }
/*    */ 
/*    */   public KeepSettingsDialog()
/*    */   {
/* 46 */     setTitle("Keep Settings?");
/* 47 */     setModal(true);
/* 48 */     setBounds(100, 100, 450, 368);
/* 49 */     getContentPane().setLayout(new BorderLayout());
/* 50 */     this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
/* 51 */     getContentPane().add(this.contentPanel, "Center");
/* 52 */     this.contentPanel.setLayout(null);
/*    */ 
/* 54 */     JTextPane txtpnYouHaveSelected = new JTextPane();
/* 55 */     txtpnYouHaveSelected.setText("You have selected an image that is the same size as the previous one.\r\n\r\nIs this image part of a time series?\r\n\r\nIf yes, click the ' Keep Settings' button.\r\n\r\nThe calibration and spatial measurements will remain the same, and the enhancement and mask settings will be reset, regardless of choice.\r\n\r\nTo reset the calibration, select the ' Calibrate Pixel Size' option in the File Menu.");
/* 56 */     txtpnYouHaveSelected.setEditable(false);
/* 57 */     txtpnYouHaveSelected.setOpaque(false);
/* 58 */     txtpnYouHaveSelected.setFont(new Font("SansSerif", 0, 13));
/* 59 */     txtpnYouHaveSelected.setBounds(10, 11, 414, 275);
/* 60 */     this.contentPanel.add(txtpnYouHaveSelected);
/*    */ 
/* 62 */     JPanel buttonPane = new JPanel();
/* 63 */     buttonPane.setLayout(new FlowLayout(1));
/* 64 */     getContentPane().add(buttonPane, "South");
/*    */ 
/* 66 */     this.okButton = new JButton("Keep Settings");
/*    */ 
/* 68 */     this.okButton.setFont(new Font("SansSerif", 0, 13));
/* 69 */     this.okButton.setActionCommand("OK");
/* 70 */     buttonPane.add(this.okButton);
/* 71 */     getRootPane().setDefaultButton(this.okButton);
/*    */ 
/* 74 */     Component horizontalStrut = Box.createHorizontalStrut(20);
/* 75 */     buttonPane.add(horizontalStrut);
/*    */ 
/* 77 */     this.cancelButton = new JButton("Reset Settings");
/* 78 */     this.cancelButton.setFont(new Font("SansSerif", 0, 13));
/* 79 */     this.cancelButton.setActionCommand("Cancel");
/* 80 */     buttonPane.add(this.cancelButton);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.dialogs.KeepSettingsDialog
 * JD-Core Version:    0.6.2
 */