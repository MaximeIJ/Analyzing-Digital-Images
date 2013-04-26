 package org.gss.adi;
 
 import java.awt.Font;
 import java.awt.event.KeyAdapter;
 import java.awt.event.KeyEvent;
 import javax.swing.JScrollPane;
 import javax.swing.JTextArea;
 
 public class AutosizeTextArea extends JTextArea
 {
   private static final long serialVersionUID = 2865213997644823135L;
   private JTextArea ta = new JTextArea("D");
 
   public AutosizeTextArea()
   {
     setOpaque(false);
     final JScrollPane scroll = new JScrollPane(this.ta);
     setSize(scroll.getPreferredSize());
     this.ta.setText("");
     setLineWrap(true);
     setWrapStyleWord(true);
     addKeyListener(new KeyAdapter()
     {
       public void keyTyped(KeyEvent e) {
         AutosizeTextArea.this.ta.setText(AutosizeTextArea.this.ta.getText() + e.getKeyChar());
         AutosizeTextArea.this.setSize(scroll.getPreferredSize());
       }
     });
   }
 
   public void setFont(Font f) {
     super.setFont(f);
     if (this.ta != null)
       this.ta.setFont(f);
   }
 
   public void setVisible(boolean visible) {
     super.setVisible(visible);
     if (visible)
       grabFocus();
   }
 }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.AutosizeTextArea
 * JD-Core Version:    0.6.2
 */