/*    */ package org.gss.adi.dialogs;
/*    */ 
/*    */ import java.awt.BorderLayout;
/*    */ import java.awt.Container;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.FlowLayout;
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.ActionListener;
/*    */ import java.awt.image.BufferedImage;
/*    */ import javax.swing.JButton;
/*    */ import javax.swing.JDialog;
/*    */ import javax.swing.JPanel;
/*    */ import javax.swing.JSlider;
/*    */ import javax.swing.border.EmptyBorder;
/*    */ import org.gss.adi.Entrance;
/*    */ import org.gss.adi.ZoomPanLabel;
/*    */ 
/*    */ public class ShowOriginalDialog extends JDialog
/*    */ {
/*    */   private static final long serialVersionUID = 1359586564120820373L;
/* 21 */   private final JPanel contentPanel = new JPanel();
/* 22 */   private final ShowOriginalDialog me = this;
/*    */ 
/*    */   public ShowOriginalDialog(Entrance entrance)
/*    */   {
/* 28 */     getContentPane().setLayout(new BorderLayout());
/* 29 */     this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
/* 30 */     getContentPane().add(this.contentPanel, "Center");
/* 31 */     this.contentPanel.setLayout(new FlowLayout(1, 5, 5));
/* 32 */     ZoomPanLabel lblNewLabel = new ZoomPanLabel(new JSlider());
/* 33 */     this.contentPanel.add(lblNewLabel);
/* 34 */     lblNewLabel.setPreferredSize(new Dimension(entrance.getImage().getWidth(), entrance.getImage().getHeight()));
/* 35 */     setBounds(100, 100, lblNewLabel.getPreferredSize().width + 50, lblNewLabel.getPreferredSize().height + 100);
/* 36 */     lblNewLabel.setImage(entrance.getImage());
/*    */ 
/* 39 */     JPanel buttonPane = new JPanel();
/* 40 */     buttonPane.setLayout(new FlowLayout(1));
/* 41 */     getContentPane().add(buttonPane, "South");
/*    */ 
/* 43 */     JButton cancelButton = new JButton("Close");
/* 44 */     cancelButton.addActionListener(new ActionListener() {
/*    */       public void actionPerformed(ActionEvent e) {
/* 46 */         ShowOriginalDialog.this.me.dispose();
/*    */       }
/*    */     });
/* 49 */     cancelButton.setActionCommand("Cancel");
/* 50 */     buttonPane.add(cancelButton);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.dialogs.ShowOriginalDialog
 * JD-Core Version:    0.6.2
 */