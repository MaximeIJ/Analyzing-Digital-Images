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
/*    */ import javax.swing.JTextField;
/*    */ import javax.swing.border.EmptyBorder;
/*    */ 
/*    */ public class ColorQualityDialog extends JDialog
/*    */ {
/*    */   private static final long serialVersionUID = -7098016282231336469L;
/* 22 */   private final JPanel contentPanel = new JPanel();
/*    */   private JTextField txtYouShouldSee;
/*    */   private JTextField txtDistinctRows;
/* 25 */   private ColorQualityDialog me = this;
/*    */ 
/*    */   public ColorQualityDialog()
/*    */   {
/* 31 */     setBounds(100, 100, 618, 600);
/* 32 */     getContentPane().setLayout(new BorderLayout());
/* 33 */     this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
/* 34 */     getContentPane().add(this.contentPanel, "Center");
/* 35 */     this.contentPanel.setLayout(null);
/*    */ 
/* 37 */     this.txtYouShouldSee = new JTextField();
/* 38 */     this.txtYouShouldSee.setOpaque(false);
/* 39 */     this.txtYouShouldSee.setBorder(null);
/* 40 */     this.txtYouShouldSee.setHorizontalAlignment(0);
/* 41 */     this.txtYouShouldSee.setText("You should see 81 distinct color tiles in the image below-");
/* 42 */     this.txtYouShouldSee.setEditable(false);
/* 43 */     this.txtYouShouldSee.setBounds(12, 13, 578, 20);
/* 44 */     this.contentPanel.add(this.txtYouShouldSee);
/* 45 */     this.txtYouShouldSee.setColumns(10);
/*    */ 
/* 48 */     this.txtDistinctRows = new JTextField();
/* 49 */     this.txtDistinctRows.setOpaque(false);
/* 50 */     this.txtDistinctRows.setText("9 distinct rows and 9 distinct columns");
/* 51 */     this.txtDistinctRows.setHorizontalAlignment(0);
/* 52 */     this.txtDistinctRows.setEditable(false);
/* 53 */     this.txtDistinctRows.setColumns(10);
/* 54 */     this.txtDistinctRows.setBorder(null);
/* 55 */     this.txtDistinctRows.setBounds(12, 42, 578, 20);
/* 56 */     this.contentPanel.add(this.txtDistinctRows);
/*    */ 
/* 59 */     JLabel lblNewLabel = new JLabel("");
/* 60 */     lblNewLabel.setHorizontalAlignment(0);
/* 61 */     lblNewLabel.setIcon(new ImageIcon(ColorQualityDialog.class.getResource("/resources/colorcheck.png")));
/* 62 */     lblNewLabel.setBounds(12, 65, 578, 356);
/* 63 */     this.contentPanel.add(lblNewLabel);
/*    */ 
/* 66 */     JTextArea txtrIfNotThe = new JTextArea();
/* 67 */     txtrIfNotThe.setOpaque(false);
/* 68 */     txtrIfNotThe.setEditable(false);
/* 69 */     txtrIfNotThe.setText("If not, the quality of your computer display is not capable of displaying the range of coors required by this software. Since you won't be able to see all of the colors of the activites, the activities may be misleading or confusing. Try to find a computer display that has the needed color reproduction.");
/* 70 */     txtrIfNotThe.setFont(new Font("SansSerif", 0, 13));
/* 71 */     txtrIfNotThe.setWrapStyleWord(true);
/* 72 */     txtrIfNotThe.setLineWrap(true);
/* 73 */     txtrIfNotThe.setBounds(76, 434, 452, 99);
/* 74 */     this.contentPanel.add(txtrIfNotThe);
/*    */ 
/* 77 */     JPanel buttonPane = new JPanel();
/* 78 */     buttonPane.setLayout(new FlowLayout(2));
/* 79 */     getContentPane().add(buttonPane, "South");
/*    */ 
/* 81 */     JButton cancelButton = new JButton("Close");
/* 82 */     cancelButton.addActionListener(new ActionListener() {
/*    */       public void actionPerformed(ActionEvent arg0) {
/* 84 */         ColorQualityDialog.this.me.dispose();
/*    */       }
/*    */     });
/* 87 */     cancelButton.setActionCommand("Cancel");
/* 88 */     buttonPane.add(cancelButton);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.dialogs.ColorQualityDialog
 * JD-Core Version:    0.6.2
 */