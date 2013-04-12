/*    */ package org.gss.adi;
/*    */ 
/*    */ import java.awt.Font;
/*    */ import java.awt.event.KeyAdapter;
/*    */ import java.awt.event.KeyEvent;
/*    */ import javax.swing.JScrollPane;
/*    */ import javax.swing.JTextArea;
/*    */ 
/*    */ public class AutosizeTextArea extends JTextArea
/*    */ {
/*    */   private static final long serialVersionUID = 2865213997644823135L;
/* 20 */   private JTextArea ta = new JTextArea("D");
/*    */ 
/*    */   public AutosizeTextArea()
/*    */   {
/* 24 */     setOpaque(false);
/* 25 */     final JScrollPane scroll = new JScrollPane(this.ta);
/* 26 */     setSize(scroll.getPreferredSize());
/* 27 */     this.ta.setText("");
/* 28 */     setLineWrap(true);
/* 29 */     setWrapStyleWord(true);
/* 30 */     addKeyListener(new KeyAdapter()
/*    */     {
/*    */       public void keyTyped(KeyEvent e) {
/* 33 */         AutosizeTextArea.this.ta.setText(AutosizeTextArea.this.ta.getText() + e.getKeyChar());
/* 34 */         AutosizeTextArea.this.setSize(scroll.getPreferredSize());
/*    */       }
/*    */     });
/*    */   }
/*    */ 
/*    */   public void setFont(Font f) {
/* 40 */     super.setFont(f);
/* 41 */     if (this.ta != null)
/* 42 */       this.ta.setFont(f);
/*    */   }
/*    */ 
/*    */   public void setVisible(boolean visible) {
/* 46 */     super.setVisible(visible);
/* 47 */     if (visible)
/* 48 */       grabFocus();
/*    */   }
/*    */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.AutosizeTextArea
 * JD-Core Version:    0.6.2
 */