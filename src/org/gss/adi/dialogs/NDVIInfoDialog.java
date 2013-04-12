/*    */ package org.gss.adi.dialogs;
/*    */ 
/*    */ import java.awt.BorderLayout;
/*    */ import java.awt.Container;
/*    */ import java.awt.FlowLayout;
/*    */ import java.awt.Font;
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.ActionListener;
/*    */ import javax.swing.ImageIcon;
/*    */ import javax.swing.JButton;
/*    */ import javax.swing.JDialog;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JPanel;
/*    */ import javax.swing.JTextArea;
/*    */ import javax.swing.border.EmptyBorder;
/*    */ 
/*    */ public class NDVIInfoDialog extends JDialog
/*    */ {
/*    */   private static final long serialVersionUID = 3780550264082780296L;
/* 20 */   private final JPanel contentPanel = new JPanel();
/* 21 */   private final NDVIInfoDialog me = this;
/*    */ 
/*    */   public NDVIInfoDialog()
/*    */   {
/* 27 */     setBounds(100, 100, 515, 607);
/* 28 */     setAlwaysOnTop(true);
/* 29 */     getContentPane().setLayout(new BorderLayout());
/* 30 */     this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
/* 31 */     getContentPane().add(this.contentPanel, "Center");
/* 32 */     this.contentPanel.setLayout(null);
/*    */ 
/* 34 */     JLabel lblNewLabel = new JLabel("");
/* 35 */     lblNewLabel.setIcon(new ImageIcon(NDVIInfoDialog.class.getResource("/resources/NDVIchartLarge.png")));
/* 36 */     lblNewLabel.setBounds(10, 11, 103, 514);
/* 37 */     this.contentPanel.add(lblNewLabel);
/*    */ 
/* 40 */     JTextArea txtrAVegetaionIndex = new JTextArea();
/* 41 */     txtrAVegetaionIndex.setOpaque(false);
/* 42 */     txtrAVegetaionIndex.setFont(new Font("SansSerif", 0, 13));
/* 43 */     txtrAVegetaionIndex.setEditable(false);
/* 44 */     txtrAVegetaionIndex.setText("A Vegetaion Index is a measure of the amount of vegetation covering the Earth's surface. Healthy vegetation absorbs visible light, especially red light, and reflects much of the infared. A simple Vegetaion Index is based on the difference between these intensities. Another index, NDVI, provides more consistent identification of vegetation.\r\n\r\nNDVI stands for \"Normalized Difference Vegetation Index\", which means that the difference between the intensities of the reflected infared and visible red light are divided by the sum of the intensities of the two light measurements:\r\n\r\n(NIR intensity - Red intensity) / (NIR intensity + Red intensity)\r\n\r\nThis mathematical manipulation tends to compensate for areas experiencing hazy sunshine compared to those in clear skies, or uneven lighting conditions due to hills and valleys.\r\n\r\nDens, healthy vegetaion produces NDVI values newar +1.0.\r\n\r\nBare soil and rock reflect similar levels of infared and red light, so these surfaces produce NDVI values near 0.\r\n\r\nClouds, water, and snow reflect more visible light than infared, which is the opposite of vegetation, and so produce NDVI values near -1.0.");
/* 45 */     txtrAVegetaionIndex.setLineWrap(true);
/* 46 */     txtrAVegetaionIndex.setWrapStyleWord(true);
/* 47 */     txtrAVegetaionIndex.setBounds(123, 29, 366, 487);
/* 48 */     this.contentPanel.add(txtrAVegetaionIndex);
/*    */ 
/* 50 */     JPanel buttonPane = new JPanel();
/* 51 */     buttonPane.setLayout(new FlowLayout(1));
/* 52 */     getContentPane().add(buttonPane, "South");
/*    */ 
/* 54 */     JButton cancelButton = new JButton("Cancel");
/* 55 */     cancelButton.addActionListener(new ActionListener() {
/*    */       public void actionPerformed(ActionEvent arg0) {
/* 57 */         NDVIInfoDialog.this.me.dispose();
/*    */       }
/*    */     });
/* 60 */     cancelButton.setActionCommand("Cancel");
/* 61 */     buttonPane.add(cancelButton);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.dialogs.NDVIInfoDialog
 * JD-Core Version:    0.6.2
 */