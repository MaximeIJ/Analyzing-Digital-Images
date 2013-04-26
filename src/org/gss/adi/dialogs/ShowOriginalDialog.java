 package org.gss.adi.dialogs;
 
 import java.awt.BorderLayout;
 import java.awt.Container;
 import java.awt.Dimension;
 import java.awt.FlowLayout;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.awt.image.BufferedImage;
 import javax.swing.JButton;
 import javax.swing.JDialog;
 import javax.swing.JPanel;
 import javax.swing.JSlider;
 import javax.swing.border.EmptyBorder;
 import org.gss.adi.Entrance;
 import org.gss.adi.ZoomPanLabel;
 
 public class ShowOriginalDialog extends JDialog
 {
   private static final long serialVersionUID = 1359586564120820373L;
   private final JPanel contentPanel = new JPanel();
   private final ShowOriginalDialog me = this;
 
   public ShowOriginalDialog(Entrance entrance)
   {
     getContentPane().setLayout(new BorderLayout());
     this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
     getContentPane().add(this.contentPanel, "Center");
     this.contentPanel.setLayout(new FlowLayout(1, 5, 5));
     ZoomPanLabel lblNewLabel = new ZoomPanLabel(new JSlider());
     this.contentPanel.add(lblNewLabel);
     lblNewLabel.setPreferredSize(new Dimension(entrance.getImage().getWidth(), entrance.getImage().getHeight()));
     setBounds(100, 100, lblNewLabel.getPreferredSize().width + 50, lblNewLabel.getPreferredSize().height + 100);
     lblNewLabel.setImage(entrance.getImage());
 
     JPanel buttonPane = new JPanel();
     buttonPane.setLayout(new FlowLayout(1));
     getContentPane().add(buttonPane, "South");
 
     JButton cancelButton = new JButton("Close");
     cancelButton.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         ShowOriginalDialog.this.me.dispose();
       }
     });
     cancelButton.setActionCommand("Cancel");
     buttonPane.add(cancelButton);
   }
 }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.dialogs.ShowOriginalDialog
 * JD-Core Version:    0.6.2
 */