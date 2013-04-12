/*    */ package org.gss.adi;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.event.FocusAdapter;
/*    */ import java.awt.event.FocusEvent;
/*    */ import java.awt.event.KeyAdapter;
/*    */ import java.awt.event.KeyEvent;
/*    */ import javax.swing.JTextField;
/*    */ 
/*    */ public class NumberField extends JTextField
/*    */ {
/*    */   private static final long serialVersionUID = 7549564262539070041L;
/*    */   private NumberField me;
/*    */ 
/*    */   public NumberField()
/*    */   {
/* 22 */     this.me = this;
/* 23 */     addFocusListener(new FocusAdapter()
/*    */     {
/*    */       public void focusLost(FocusEvent e) {
/* 26 */         NumberField.this.me.setEditable(true);
/* 27 */         if (NumberField.this.me.getText().equals(""))
/* 28 */           NumberField.this.me.setText("0");
/*    */       }
/*    */     });
/* 32 */     addKeyListener(new KeyAdapter()
/*    */     {
/*    */       public void keyTyped(KeyEvent k) {
/* 35 */         char ch = k.getKeyChar();
/* 36 */         NumberField.this.me.setEditable(true);
/* 37 */         if ((ch != '0') && (ch != '1') && (ch != '2') && (ch != '3') && (ch != '4') && (ch != '5') && (ch != '6') && 
/* 38 */           (ch != '7') && (ch != '8') && (ch != '9') && (ch != '.') && (ch != '-')) {
/* 39 */           NumberField.this.me.setEditable(false);
/* 40 */           NumberField.this.me.setBackground(Color.white);
/* 41 */         } else if ((NumberField.this.me.getText().equals("0")) && (ch != '.')) {
/* 42 */           NumberField.this.me.setText("");
/*    */         }
/*    */       }
/*    */ 
/*    */       public void keyReleased(KeyEvent k) {
/* 47 */         NumberField.this.me.setEditable(true);
/*    */       }
/*    */     });
/*    */   }
/*    */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.NumberField
 * JD-Core Version:    0.6.2
 */