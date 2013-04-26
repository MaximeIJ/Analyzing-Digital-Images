 package org.gss.adi.dialogs;
 
 import java.awt.BorderLayout;
 import java.awt.Container;
 import java.awt.Font;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import javax.swing.JButton;
 import javax.swing.JDialog;
 import javax.swing.JPanel;
 import javax.swing.JTextArea;
 import javax.swing.JTextField;
 import javax.swing.border.EmptyBorder;
 import org.gss.adi.Entrance;
 
 public class CalibratePixelDialog extends JDialog
 {
   private static final long serialVersionUID = -6299417962340902177L;
   private final JPanel contentPanel = new JPanel();
   private JTextField txtSelectMethodTo;
   private Entrance entrance;
   private JDialog me;
   private boolean TimeSeries;
 
   public CalibratePixelDialog(Entrance e, Boolean timeSeries)
   {
     setModal(true);
     this.me = this;
     this.entrance = e;
     this.TimeSeries = timeSeries.booleanValue();
     if (timeSeries.booleanValue())
       this.entrance.setTimeSeriesMeasurement(null);
     else
       this.entrance.setMeasurement(null);
     setBounds(100, 100, 630, 294);
     setTitle("Select Method of Pixel Size Calibration");
     setAlwaysOnTop(true);
     getContentPane().setLayout(new BorderLayout());
     this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
     getContentPane().add(this.contentPanel, "Center");
     this.contentPanel.setLayout(null);
 
     this.txtSelectMethodTo = new JTextField();
     this.txtSelectMethodTo.setBorder(null);
     this.txtSelectMethodTo.setEditable(false);
     this.txtSelectMethodTo.setOpaque(false);
     this.txtSelectMethodTo.setFont(new Font("SansSerif", 1, 14));
     this.txtSelectMethodTo.setText("Select Method to Calibrate the Pixel Size");
     this.txtSelectMethodTo.setHorizontalAlignment(0);
     this.txtSelectMethodTo.setBounds(12, 13, 590, 20);
     this.contentPanel.add(this.txtSelectMethodTo);
     this.txtSelectMethodTo.setColumns(10);
 
     JButton btnNewButton = new JButton("Known Pixel Scale");
     btnNewButton.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         CalibratePixelDialog.this.me.dispose();
         KnownPixelSizeDialog dialog = new KnownPixelSizeDialog(CalibratePixelDialog.this.entrance, CalibratePixelDialog.this.TimeSeries);
         dialog.setDefaultCloseOperation(2);
         dialog.setVisible(true);
       }
     });
     btnNewButton.setBounds(12, 45, 180, 25);
     this.contentPanel.add(btnNewButton);
 
     JTextArea txtrClickIfYou = new JTextArea();
     txtrClickIfYou.setFont(new Font("SansSerif", 0, 13));
     txtrClickIfYou.setText("Click if you know the size of the pixels, which is common for orthophotographs from aerial reconnaissance and satellite imagery.");
     txtrClickIfYou.setLineWrap(true);
     txtrClickIfYou.setOpaque(false);
     txtrClickIfYou.setEditable(false);
     txtrClickIfYou.setWrapStyleWord(true);
     txtrClickIfYou.setBounds(204, 46, 398, 46);
     this.contentPanel.add(txtrClickIfYou);
 
     JButton btnScalePresentIn = new JButton("Scale Present in Image");
     btnScalePresentIn.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         CalibratePixelDialog.this.me.dispose();
         ManualCalibrationDialog dialog = new ManualCalibrationDialog(CalibratePixelDialog.this.entrance, CalibratePixelDialog.this.TimeSeries);
         dialog.setDefaultCloseOperation(2);
         dialog.setVisible(true);
       }
     });
     btnScalePresentIn.setBounds(12, 115, 180, 25);
     this.contentPanel.add(btnScalePresentIn);
 
     JTextArea txtrClickIfThere = new JTextArea();
     txtrClickIfThere.setWrapStyleWord(true);
     txtrClickIfThere.setText("Click if there is a linear scale located in the image.  This includes photographed objects of known length (ruler, penny, clipboard, etc.) or a distance scale on digital maps or satellite imagery.");
     txtrClickIfThere.setOpaque(false);
     txtrClickIfThere.setLineWrap(true);
     txtrClickIfThere.setFont(new Font("SansSerif", 0, 13));
/* 100 */     txtrClickIfThere.setEditable(false);
/* 101 */     txtrClickIfThere.setBounds(204, 116, 398, 63);
/* 102 */     this.contentPanel.add(txtrClickIfThere);
 
/* 105 */     JTextArea txtrClickIfThere_1 = new JTextArea();
/* 106 */     txtrClickIfThere_1.setWrapStyleWord(true);
/* 107 */     txtrClickIfThere_1.setText("Click if there is no way to know the size of the pixels in the image.");
/* 108 */     txtrClickIfThere_1.setOpaque(false);
/* 109 */     txtrClickIfThere_1.setLineWrap(true);
/* 110 */     txtrClickIfThere_1.setFont(new Font("SansSerif", 0, 13));
/* 111 */     txtrClickIfThere_1.setEditable(false);
/* 112 */     txtrClickIfThere_1.setBounds(204, 186, 398, 59);
/* 113 */     this.contentPanel.add(txtrClickIfThere_1);
 
/* 116 */     JButton btnNewButton_1 = new JButton("None");
/* 117 */     btnNewButton_1.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
/* 119 */         CalibratePixelDialog.this.me.dispose();
       }
     });
/* 122 */     btnNewButton_1.setBounds(12, 185, 180, 25);
/* 123 */     this.contentPanel.add(btnNewButton_1);
   }
 }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.dialogs.CalibratePixelDialog
 * JD-Core Version:    0.6.2
 */