 package org.gss.adi.dialogs;
 
 import java.awt.Container;
 import java.awt.Font;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import javax.swing.JButton;
 import javax.swing.JDialog;
 import javax.swing.JTextField;
 import org.gss.adi.ADIMenu;
 import org.gss.adi.Entrance;
 import org.gss.adi.MaskColorsPanel;
 import org.gss.adi.tools.ColorMask;
 
 public class SaveNewMaskDialog extends JDialog
 {
   private static final long serialVersionUID = -1431706648038715900L;
   private JTextField name;
   private JButton save;
   private Entrance entrance;
   private SaveNewMaskDialog me = this;
 
   public SaveNewMaskDialog(Entrance e)
   {
     setTitle("New Mask");
     setAlwaysOnTop(true);
     setBounds(100, 100, 238, 147);
     getContentPane().setLayout(null);
     this.entrance = e;
     JTextField txtTypeTheName = new JTextField();
     txtTypeTheName.setFont(new Font("SansSerif", 0, 11));
     txtTypeTheName.setBorder(null);
     txtTypeTheName.setHorizontalAlignment(0);
     txtTypeTheName.setText("Type the name of the new color mask.");
     txtTypeTheName.setEditable(false);
     txtTypeTheName.setBounds(0, 11, 222, 20);
     getContentPane().add(txtTypeTheName);
     txtTypeTheName.setColumns(10);
 
     this.name = new JTextField();
     this.name.setBounds(20, 42, 182, 20);
     getContentPane().add(this.name);
     this.name.setColumns(10);
 
     this.save = new JButton("Save");
     this.save.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         MaskColorsPanel p = (MaskColorsPanel)SaveNewMaskDialog.this.entrance.getPane();
         ColorMask mask = new ColorMask(p.getRMin(), p.getRMax(), p.getGMin(), 
           p.getGMax(), p.getBMin(), p.getBMax(), p.getRed(), p.getGreen(), p.getBlue());
         SaveNewMaskDialog.this.entrance.getMenu().addMask(mask, SaveNewMaskDialog.this.name.getText());
         SaveNewMaskDialog.this.me.dispose();
       }
     });
     this.save.setBounds(20, 73, 89, 23);
     getContentPane().add(this.save);
 
     JButton cancel = new JButton("Cancel");
     cancel.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         SaveNewMaskDialog.this.me.dispose();
       }
     });
     cancel.setBounds(117, 73, 85, 23);
     getContentPane().add(cancel);
   }
 }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.dialogs.SaveNewMaskDialog
 * JD-Core Version:    0.6.2
 */