 package org.gss.adi;
 
 import java.awt.Color;
 import java.awt.event.FocusAdapter;
 import java.awt.event.FocusEvent;
 import java.awt.event.KeyAdapter;
 import java.awt.event.KeyEvent;
 import javax.swing.JTextField;
 
 public class NumberField extends JTextField
 {
   private static final long serialVersionUID = 7549564262539070041L;
   private NumberField me;
 
   public NumberField()
   {
     this.me = this;
     addFocusListener(new FocusAdapter()
     {
       public void focusLost(FocusEvent e) {
         NumberField.this.me.setEditable(true);
         if (NumberField.this.me.getText().equals(""))
           NumberField.this.me.setText("0");
       }
     });
     addKeyListener(new KeyAdapter()
     {
       public void keyTyped(KeyEvent k) {
         char ch = k.getKeyChar();
         NumberField.this.me.setEditable(true);
         if ((ch != '0') && (ch != '1') && (ch != '2') && (ch != '3') && (ch != '4') && (ch != '5') && (ch != '6') && 
           (ch != '7') && (ch != '8') && (ch != '9') && (ch != '.') && (ch != '-')) {
           NumberField.this.me.setEditable(false);
           NumberField.this.me.setBackground(Color.white);
         } else if ((NumberField.this.me.getText().equals("0")) && (ch != '.')) {
           NumberField.this.me.setText("");
         }
       }
 
       public void keyReleased(KeyEvent k) {
         NumberField.this.me.setEditable(true);
       }
     });
   }
 }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.NumberField
 * JD-Core Version:    0.6.2
 */