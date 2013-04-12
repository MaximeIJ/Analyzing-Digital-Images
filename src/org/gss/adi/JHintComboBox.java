/*    */ package org.gss.adi;
/*    */ 
/*    */ import java.awt.event.ItemEvent;
/*    */ import java.awt.event.ItemListener;
/*    */ import javax.swing.ComboBoxModel;
/*    */ import javax.swing.DefaultComboBoxModel;
/*    */ import javax.swing.JComboBox;
/*    */ 
/*    */ public class JHintComboBox extends JComboBox
/*    */ {
/*    */   private static final long serialVersionUID = 5618653271783580377L;
/*    */   private JHintComboBox me;
/* 20 */   private boolean hintPresent = true;
/*    */ 
/*    */   public JHintComboBox() {
/* 23 */     this.me = this;
/* 24 */     addItemListener(new ItemListener() {
/*    */       public void itemStateChanged(ItemEvent arg0) {
/* 26 */         ComboBoxModel model = JHintComboBox.this.me.getModel();
/* 27 */         int index = JHintComboBox.this.me.getSelectedIndex();
/* 28 */         String[] strings = new String[model.getSize() - 1];
/* 29 */         for (int i = 1; i < model.getSize(); i++) {
/* 30 */           strings[(i - 1)] = ((String)model.getElementAt(i));
/*    */         }
/* 32 */         JHintComboBox.this.me.setModel(new DefaultComboBoxModel(strings));
/* 33 */         JHintComboBox.this.me.removeItemListener(this);
/* 34 */         JHintComboBox.this.me.setSelectedIndex(index - 1);
/* 35 */         JHintComboBox.this.hintPresent = false;
/*    */       } } );
/*    */   }
/*    */ 
/*    */   public boolean hinting() {
/* 40 */     return this.hintPresent;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.JHintComboBox
 * JD-Core Version:    0.6.2
 */