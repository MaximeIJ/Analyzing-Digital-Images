/*    */ package org.gss.adi.dialogs;
/*    */ 
/*    */ import java.awt.Container;
/*    */ import java.awt.Font;
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.ActionListener;
/*    */ import javax.swing.JButton;
/*    */ import javax.swing.JDialog;
/*    */ import javax.swing.JTextField;
/*    */ import org.gss.adi.ADIMenu;
/*    */ import org.gss.adi.Entrance;
/*    */ import org.gss.adi.MaskColorsPanel;
/*    */ import org.gss.adi.tools.ColorMask;
/*    */ 
/*    */ public class SaveNewMaskDialog extends JDialog
/*    */ {
/*    */   private static final long serialVersionUID = -1431706648038715900L;
/*    */   private JTextField name;
/*    */   private JButton save;
/*    */   private Entrance entrance;
/* 23 */   private SaveNewMaskDialog me = this;
/*    */ 
/*    */   public SaveNewMaskDialog(Entrance e)
/*    */   {
/* 28 */     setTitle("New Mask");
/* 29 */     setAlwaysOnTop(true);
/* 30 */     setBounds(100, 100, 238, 147);
/* 31 */     getContentPane().setLayout(null);
/* 32 */     this.entrance = e;
/* 33 */     JTextField txtTypeTheName = new JTextField();
/* 34 */     txtTypeTheName.setFont(new Font("SansSerif", 0, 11));
/* 35 */     txtTypeTheName.setBorder(null);
/* 36 */     txtTypeTheName.setHorizontalAlignment(0);
/* 37 */     txtTypeTheName.setText("Type the name of the new color mask.");
/* 38 */     txtTypeTheName.setEditable(false);
/* 39 */     txtTypeTheName.setBounds(0, 11, 222, 20);
/* 40 */     getContentPane().add(txtTypeTheName);
/* 41 */     txtTypeTheName.setColumns(10);
/*    */ 
/* 43 */     this.name = new JTextField();
/* 44 */     this.name.setBounds(20, 42, 182, 20);
/* 45 */     getContentPane().add(this.name);
/* 46 */     this.name.setColumns(10);
/*    */ 
/* 48 */     this.save = new JButton("Save");
/* 49 */     this.save.addActionListener(new ActionListener() {
/*    */       public void actionPerformed(ActionEvent e) {
/* 51 */         MaskColorsPanel p = (MaskColorsPanel)SaveNewMaskDialog.this.entrance.getPane();
/* 52 */         ColorMask mask = new ColorMask(p.getRMin(), p.getRMax(), p.getGMin(), 
/* 53 */           p.getGMax(), p.getBMin(), p.getBMax(), p.getRed(), p.getGreen(), p.getBlue());
/* 54 */         SaveNewMaskDialog.this.entrance.getMenu().addMask(mask, SaveNewMaskDialog.this.name.getText());
/* 55 */         SaveNewMaskDialog.this.me.dispose();
/*    */       }
/*    */     });
/* 58 */     this.save.setBounds(20, 73, 89, 23);
/* 59 */     getContentPane().add(this.save);
/*    */ 
/* 61 */     JButton cancel = new JButton("Cancel");
/* 62 */     cancel.addActionListener(new ActionListener() {
/*    */       public void actionPerformed(ActionEvent e) {
/* 64 */         SaveNewMaskDialog.this.me.dispose();
/*    */       }
/*    */     });
/* 67 */     cancel.setBounds(117, 73, 85, 23);
/* 68 */     getContentPane().add(cancel);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.dialogs.SaveNewMaskDialog
 * JD-Core Version:    0.6.2
 */