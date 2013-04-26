 package org.gss.adi.dialogs;
 
 import java.awt.Container;
 import java.awt.Font;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.awt.image.BufferedImage;
 import java.io.IOException;
 import java.net.MalformedURLException;
 import java.net.URL;
 import javax.imageio.ImageIO;
 import javax.swing.JButton;
 import javax.swing.JDialog;
 import javax.swing.JOptionPane;
 import javax.swing.JTextField;
 import org.gss.adi.Entrance;
 import org.gss.adi.Updatable;
 
 public class OpenImageFromURL extends JDialog
 {
   private static final long serialVersionUID = 7441043699183274678L;
   private JTextField textField;
   private Entrance entrance;
   public static final byte IMAGE = 0;
   public static final byte TIME_SERIES1 = 1;
   public static final byte TIME_SERIES2 = 2;
   public static final byte TIME_SERIES3 = 3;
 
   public OpenImageFromURL(Entrance e, final byte imgType)
   {
     setTitle("Upload an Image from a URL");
     setBounds(100, 100, 450, 120);
     getContentPane().setLayout(null);
     setAlwaysOnTop(true);
     this.entrance = e;
     this.textField = new JTextField();
     this.textField.setBounds(10, 16, 414, 20);
     getContentPane().add(this.textField);
     this.textField.setColumns(10);
 
     JTextField txtUrl = new JTextField();
     txtUrl.setOpaque(false);
     txtUrl.setBorder(null);
     txtUrl.setEditable(false);
     txtUrl.setFont(new Font("SansSerif", 0, 10));
     txtUrl.setText("URL");
     txtUrl.setBounds(10, 0, 86, 15);
     getContentPane().add(txtUrl);
     txtUrl.setColumns(10);
 
     JButton btnCancel = new JButton("Cancel");
     btnCancel.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         OpenImageFromURL.this.dispose();
       }
     });
     btnCancel.setBounds(335, 47, 89, 23);
     getContentPane().add(btnCancel);
 
     JButton btnUpload = new JButton("Upload");
     btnUpload.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent arg0) {
         try {
           URL url = new URL(OpenImageFromURL.this.textField.getText());
           BufferedImage img = ImageIO.read(url);
           switch (imgType) {
           case 0:
             OpenImageFromURL.this.entrance.setImage(img);
             OpenImageFromURL.this.entrance.setTitle(url.toString());
             break;
           case 1:
             OpenImageFromURL.this.entrance.setTimeSeries1(img);
             OpenImageFromURL.this.entrance.setTitle1(url.toString());
             break;
           case 2:
             OpenImageFromURL.this.entrance.setTimeSeries2(img);
             OpenImageFromURL.this.entrance.setTitle2(url.toString());
             break;
           case 3:
             OpenImageFromURL.this.entrance.setTimeSeries3(img);
             OpenImageFromURL.this.entrance.setTitle3(url.toString());
           }
 
           if ((OpenImageFromURL.this.entrance.getPane() instanceof Updatable))
             ((Updatable)OpenImageFromURL.this.entrance.getPane()).updatePic();
           OpenImageFromURL.this.dispose();
         } catch (MalformedURLException e) {
           JOptionPane.showMessageDialog(null, "URL is malformed.", null, -1);
         } catch (IOException e) {
           JOptionPane.showMessageDialog(null, "URL does not specify an image.", null, -1);
         }
       }
     });
     btnUpload.setBounds(236, 47, 89, 23);
     getContentPane().add(btnUpload);
   }
 }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.dialogs.OpenImageFromURL
 * JD-Core Version:    0.6.2
 */