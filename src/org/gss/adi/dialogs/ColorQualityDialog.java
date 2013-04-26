 package org.gss.adi.dialogs;
 
 import java.awt.BorderLayout;
 import java.awt.Container;
 import java.awt.FlowLayout;
 import java.awt.Font;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import javax.swing.ImageIcon;
 import javax.swing.JButton;
 import javax.swing.JDialog;
 import javax.swing.JLabel;
 import javax.swing.JPanel;
 import javax.swing.JTextArea;
 import javax.swing.JTextField;
 import javax.swing.border.EmptyBorder;
 
 public class ColorQualityDialog extends JDialog
 {
   private static final long serialVersionUID = -7098016282231336469L;
   private final JPanel contentPanel = new JPanel();
   private JTextField txtYouShouldSee;
   private JTextField txtDistinctRows;
   private ColorQualityDialog me = this;
 
   public ColorQualityDialog()
   {
     setBounds(100, 100, 618, 600);
     getContentPane().setLayout(new BorderLayout());
     this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
     getContentPane().add(this.contentPanel, "Center");
     this.contentPanel.setLayout(null);
 
     this.txtYouShouldSee = new JTextField();
     this.txtYouShouldSee.setOpaque(false);
     this.txtYouShouldSee.setBorder(null);
     this.txtYouShouldSee.setHorizontalAlignment(0);
     this.txtYouShouldSee.setText("You should see 81 distinct color tiles in the image below-");
     this.txtYouShouldSee.setEditable(false);
     this.txtYouShouldSee.setBounds(12, 13, 578, 20);
     this.contentPanel.add(this.txtYouShouldSee);
     this.txtYouShouldSee.setColumns(10);
 
     this.txtDistinctRows = new JTextField();
     this.txtDistinctRows.setOpaque(false);
     this.txtDistinctRows.setText("9 distinct rows and 9 distinct columns");
     this.txtDistinctRows.setHorizontalAlignment(0);
     this.txtDistinctRows.setEditable(false);
     this.txtDistinctRows.setColumns(10);
     this.txtDistinctRows.setBorder(null);
     this.txtDistinctRows.setBounds(12, 42, 578, 20);
     this.contentPanel.add(this.txtDistinctRows);
 
     JLabel lblNewLabel = new JLabel("");
     lblNewLabel.setHorizontalAlignment(0);
     lblNewLabel.setIcon(new ImageIcon(ColorQualityDialog.class.getResource("/resources/colorcheck.png")));
     lblNewLabel.setBounds(12, 65, 578, 356);
     this.contentPanel.add(lblNewLabel);
 
     JTextArea txtrIfNotThe = new JTextArea();
     txtrIfNotThe.setOpaque(false);
     txtrIfNotThe.setEditable(false);
     txtrIfNotThe.setText("If not, the quality of your computer display is not capable of displaying the range of coors required by this software. Since you won't be able to see all of the colors of the activites, the activities may be misleading or confusing. Try to find a computer display that has the needed color reproduction.");
     txtrIfNotThe.setFont(new Font("SansSerif", 0, 13));
     txtrIfNotThe.setWrapStyleWord(true);
     txtrIfNotThe.setLineWrap(true);
     txtrIfNotThe.setBounds(76, 434, 452, 99);
     this.contentPanel.add(txtrIfNotThe);
 
     JPanel buttonPane = new JPanel();
     buttonPane.setLayout(new FlowLayout(2));
     getContentPane().add(buttonPane, "South");
 
     JButton cancelButton = new JButton("Close");
     cancelButton.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent arg0) {
         ColorQualityDialog.this.me.dispose();
       }
     });
     cancelButton.setActionCommand("Cancel");
     buttonPane.add(cancelButton);
   }
 }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.dialogs.ColorQualityDialog
 * JD-Core Version:    0.6.2
 */