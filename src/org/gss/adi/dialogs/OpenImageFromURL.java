/*    */ package org.gss.adi.dialogs;
/*    */ 
/*    */ import java.awt.Container;
/*    */ import java.awt.Font;
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.ActionListener;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.IOException;
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URL;
/*    */ import javax.imageio.ImageIO;
/*    */ import javax.swing.JButton;
/*    */ import javax.swing.JDialog;
/*    */ import javax.swing.JOptionPane;
/*    */ import javax.swing.JTextField;
/*    */ import org.gss.adi.Entrance;
/*    */ import org.gss.adi.Updatable;
/*    */ 
/*    */ public class OpenImageFromURL extends JDialog
/*    */ {
/*    */   private static final long serialVersionUID = 7441043699183274678L;
/*    */   private JTextField textField;
/*    */   private Entrance entrance;
/*    */   public static final byte IMAGE = 0;
/*    */   public static final byte TIME_SERIES1 = 1;
/*    */   public static final byte TIME_SERIES2 = 2;
/*    */   public static final byte TIME_SERIES3 = 3;
/*    */ 
/*    */   public OpenImageFromURL(Entrance e, final byte imgType)
/*    */   {
/* 34 */     setTitle("Upload an Image from a URL");
/* 35 */     setBounds(100, 100, 450, 120);
/* 36 */     getContentPane().setLayout(null);
/* 37 */     setAlwaysOnTop(true);
/* 38 */     this.entrance = e;
/* 39 */     this.textField = new JTextField();
/* 40 */     this.textField.setBounds(10, 16, 414, 20);
/* 41 */     getContentPane().add(this.textField);
/* 42 */     this.textField.setColumns(10);
/*    */ 
/* 44 */     JTextField txtUrl = new JTextField();
/* 45 */     txtUrl.setOpaque(false);
/* 46 */     txtUrl.setBorder(null);
/* 47 */     txtUrl.setEditable(false);
/* 48 */     txtUrl.setFont(new Font("SansSerif", 0, 10));
/* 49 */     txtUrl.setText("URL");
/* 50 */     txtUrl.setBounds(10, 0, 86, 15);
/* 51 */     getContentPane().add(txtUrl);
/* 52 */     txtUrl.setColumns(10);
/*    */ 
/* 54 */     JButton btnCancel = new JButton("Cancel");
/* 55 */     btnCancel.addActionListener(new ActionListener() {
/*    */       public void actionPerformed(ActionEvent e) {
/* 57 */         OpenImageFromURL.this.dispose();
/*    */       }
/*    */     });
/* 60 */     btnCancel.setBounds(335, 47, 89, 23);
/* 61 */     getContentPane().add(btnCancel);
/*    */ 
/* 63 */     JButton btnUpload = new JButton("Upload");
/* 64 */     btnUpload.addActionListener(new ActionListener() {
/*    */       public void actionPerformed(ActionEvent arg0) {
/*    */         try {
/* 67 */           URL url = new URL(OpenImageFromURL.this.textField.getText());
/* 68 */           BufferedImage img = ImageIO.read(url);
/* 69 */           switch (imgType) {
/*    */           case 0:
/* 71 */             OpenImageFromURL.this.entrance.setImage(img);
/* 72 */             OpenImageFromURL.this.entrance.setTitle(url.toString());
/* 73 */             break;
/*    */           case 1:
/* 75 */             OpenImageFromURL.this.entrance.setTimeSeries1(img);
/* 76 */             OpenImageFromURL.this.entrance.setTitle1(url.toString());
/* 77 */             break;
/*    */           case 2:
/* 79 */             OpenImageFromURL.this.entrance.setTimeSeries2(img);
/* 80 */             OpenImageFromURL.this.entrance.setTitle2(url.toString());
/* 81 */             break;
/*    */           case 3:
/* 83 */             OpenImageFromURL.this.entrance.setTimeSeries3(img);
/* 84 */             OpenImageFromURL.this.entrance.setTitle3(url.toString());
/*    */           }
/*    */ 
/* 87 */           if ((OpenImageFromURL.this.entrance.getPane() instanceof Updatable))
/* 88 */             ((Updatable)OpenImageFromURL.this.entrance.getPane()).updatePic();
/* 89 */           OpenImageFromURL.this.dispose();
/*    */         } catch (MalformedURLException e) {
/* 91 */           JOptionPane.showMessageDialog(null, "URL is malformed.", null, -1);
/*    */         } catch (IOException e) {
/* 93 */           JOptionPane.showMessageDialog(null, "URL does not specify an image.", null, -1);
/*    */         }
/*    */       }
/*    */     });
/* 97 */     btnUpload.setBounds(236, 47, 89, 23);
/* 98 */     getContentPane().add(btnUpload);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.dialogs.OpenImageFromURL
 * JD-Core Version:    0.6.2
 */