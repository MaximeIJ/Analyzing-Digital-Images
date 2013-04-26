 package org.gss.adi.dialogs;
 
 import java.awt.BorderLayout;
 import java.awt.Component;
 import java.awt.Container;
 import java.awt.FlowLayout;
 import java.awt.Font;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import javax.swing.Box;
 import javax.swing.JButton;
 import javax.swing.JDialog;
 import javax.swing.JPanel;
 import javax.swing.JRootPane;
 import javax.swing.JTextPane;
 import javax.swing.border.EmptyBorder;
 
 public class KeepSettingsDialog extends JDialog
 {
   private static final long serialVersionUID = 8760501318084579383L;
   private final JPanel contentPanel = new JPanel();
   protected JButton okButton;
   protected JButton cancelButton;
   static boolean result;
 
   public static boolean launch()
   {
     final KeepSettingsDialog ksd = new KeepSettingsDialog();
     ksd.okButton.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent arg0) {
         KeepSettingsDialog.result = true;
         ksd.dispose();
       }
     });
     ksd.cancelButton.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent arg0) {
         KeepSettingsDialog.result = false;
         ksd.dispose();
       }
     });
     ksd.setVisible(true);
     return result;
   }
 
   public KeepSettingsDialog()
   {
     setTitle("Keep Settings?");
     setModal(true);
     setBounds(100, 100, 450, 368);
     getContentPane().setLayout(new BorderLayout());
     this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
     getContentPane().add(this.contentPanel, "Center");
     this.contentPanel.setLayout(null);
 
     JTextPane txtpnYouHaveSelected = new JTextPane();
     txtpnYouHaveSelected.setText("You have selected an image that is the same size as the previous one.\r\n\r\nIs this image part of a time series?\r\n\r\nIf yes, click the ' Keep Settings' button.\r\n\r\nThe calibration and spatial measurements will remain the same, and the enhancement and mask settings will be reset, regardless of choice.\r\n\r\nTo reset the calibration, select the ' Calibrate Pixel Size' option in the File Menu.");
     txtpnYouHaveSelected.setEditable(false);
     txtpnYouHaveSelected.setOpaque(false);
     txtpnYouHaveSelected.setFont(new Font("SansSerif", 0, 13));
     txtpnYouHaveSelected.setBounds(10, 11, 414, 275);
     this.contentPanel.add(txtpnYouHaveSelected);
 
     JPanel buttonPane = new JPanel();
     buttonPane.setLayout(new FlowLayout(1));
     getContentPane().add(buttonPane, "South");
 
     this.okButton = new JButton("Keep Settings");
 
     this.okButton.setFont(new Font("SansSerif", 0, 13));
     this.okButton.setActionCommand("OK");
     buttonPane.add(this.okButton);
     getRootPane().setDefaultButton(this.okButton);
 
     Component horizontalStrut = Box.createHorizontalStrut(20);
     buttonPane.add(horizontalStrut);
 
     this.cancelButton = new JButton("Reset Settings");
     this.cancelButton.setFont(new Font("SansSerif", 0, 13));
     this.cancelButton.setActionCommand("Cancel");
     buttonPane.add(this.cancelButton);
   }
 }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.dialogs.KeepSettingsDialog
 * JD-Core Version:    0.6.2
 */