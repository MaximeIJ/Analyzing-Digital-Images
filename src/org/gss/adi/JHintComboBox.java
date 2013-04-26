 package org.gss.adi;
 
 import java.awt.event.ItemEvent;
 import java.awt.event.ItemListener;
 import javax.swing.ComboBoxModel;
 import javax.swing.DefaultComboBoxModel;
 import javax.swing.JComboBox;
 
 public class JHintComboBox extends JComboBox
 {
   private static final long serialVersionUID = 5618653271783580377L;
   private JHintComboBox me;
   private boolean hintPresent = true;
 
   public JHintComboBox() {
     this.me = this;
     addItemListener(new ItemListener() {
       public void itemStateChanged(ItemEvent arg0) {
         ComboBoxModel model = JHintComboBox.this.me.getModel();
         int index = JHintComboBox.this.me.getSelectedIndex();
         String[] strings = new String[model.getSize() - 1];
         for (int i = 1; i < model.getSize(); i++) {
           strings[(i - 1)] = ((String)model.getElementAt(i));
         }
         JHintComboBox.this.me.setModel(new DefaultComboBoxModel(strings));
         JHintComboBox.this.me.removeItemListener(this);
         JHintComboBox.this.me.setSelectedIndex(index - 1);
         JHintComboBox.this.hintPresent = false;
       } } );
   }
 
   public boolean hinting() {
     return this.hintPresent;
   }
 }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.JHintComboBox
 * JD-Core Version:    0.6.2
 */