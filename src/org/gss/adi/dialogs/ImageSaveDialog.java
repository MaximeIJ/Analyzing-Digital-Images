 package org.gss.adi.dialogs;
 
 import java.awt.BorderLayout;
 import java.awt.Container;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.io.File;
 import java.io.IOException;
 import javax.imageio.ImageIO;
 import javax.swing.ButtonGroup;
 import javax.swing.JButton;
 import javax.swing.JDialog;
 import javax.swing.JPanel;
 import javax.swing.JRadioButton;
 import javax.swing.JRootPane;
 import javax.swing.JSlider;
 import javax.swing.border.EmptyBorder;
 import org.eclipse.swt.widgets.Shell;
 import org.gss.adi.Entrance;
 import org.gss.adi.ImageFilter;
 import org.gss.adi.SpatialAnalysisPanel;
 import org.gss.adi.ZoomPanLabel;
 
 public class ImageSaveDialog extends JDialog
 {
   private static final long serialVersionUID = 7639515995551902541L;
   private final JPanel contentPanel = new JPanel();
   private Entrance entrance;
   private final ButtonGroup buttonGroup = new ButtonGroup();
   private JRadioButton rdbtnOriginal;
   private JRadioButton rdbtnEnhanced;
   private JRadioButton rdbtnMasked;
   private ImageSaveDialog me = this;
   private int imgType = 0;
   private boolean spatial = false;
   private String tool = "";
   private Integer[] x;
   private Integer[] y;
 
   public ImageSaveDialog(Entrance e)
   {
     this.entrance = e;
     setAlwaysOnTop(true);
     setBounds(100, 100, 674, 570);
     getContentPane().setLayout(new BorderLayout());
     this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
     getContentPane().add(this.contentPanel, "Center");
     this.contentPanel.setLayout(null);
 
     if ((this.entrance.getPane() instanceof SpatialAnalysisPanel)) {
       SpatialAnalysisPanel panel = (SpatialAnalysisPanel)this.entrance.getPane();
       Object[] stuff = panel.getInfo();
       if (!((String)stuff[0]).contains("Select")) {
         this.spatial = true;
         this.tool = ((String)stuff[0]);
         this.x = ((Integer[])stuff[1]);
         this.y = ((Integer[])stuff[2]);
       }
     }
 
     final ZoomPanLabel lblNewLabel = new ZoomPanLabel(new JSlider());
     lblNewLabel.setBounds(10, 10, 640, 480);
     this.contentPanel.add(lblNewLabel);
     lblNewLabel.setImage(this.entrance.getImage());
     if (this.spatial) {
       lblNewLabel.toolImage(this.x, this.y, this.entrance.getColor(), this.tool, this.entrance.getLineWidth(), this.entrance.getCursorStyle());
     }
     this.rdbtnOriginal = new JRadioButton("Original");
     this.rdbtnOriginal.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         lblNewLabel.setImage(ImageSaveDialog.this.entrance.getImage());
         if (ImageSaveDialog.this.spatial)
           lblNewLabel.toolImage(ImageSaveDialog.this.x, ImageSaveDialog.this.y, ImageSaveDialog.this.entrance.getColor(), ImageSaveDialog.this.tool, ImageSaveDialog.this.entrance.getLineWidth(), ImageSaveDialog.this.entrance.getCursorStyle());
         ImageSaveDialog.this.imgType = 0;
       }
     });
     this.rdbtnOriginal.setSelected(true);
     this.buttonGroup.add(this.rdbtnOriginal);
     this.rdbtnOriginal.setBounds(109, 497, 109, 23);
     this.contentPanel.add(this.rdbtnOriginal);
 
     this.rdbtnEnhanced = new JRadioButton("Enhanced");
     this.rdbtnEnhanced.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
         if (ImageSaveDialog.this.entrance.getEnhancedImage() != null) {
           lblNewLabel.setImage(ImageSaveDialog.this.entrance.getEnhancedImage());
           if (ImageSaveDialog.this.spatial)
             lblNewLabel.toolImage(ImageSaveDialog.this.x, ImageSaveDialog.this.y, ImageSaveDialog.this.entrance.getColor(), ImageSaveDialog.this.tool, ImageSaveDialog.this.entrance.getLineWidth(), ImageSaveDialog.this.entrance.getCursorStyle());
           ImageSaveDialog.this.imgType = 1;
         } else if (ImageSaveDialog.this.imgType == 0) {
           ImageSaveDialog.this.rdbtnOriginal.setSelected(true);
         } else {
           ImageSaveDialog.this.rdbtnMasked.setSelected(true);
         }
       }
     });
/* 100 */     this.buttonGroup.add(this.rdbtnEnhanced);
/* 101 */     this.rdbtnEnhanced.setBounds(220, 497, 109, 23);
/* 102 */     this.contentPanel.add(this.rdbtnEnhanced);
 
/* 104 */     this.rdbtnMasked = new JRadioButton("Masked");
/* 105 */     this.rdbtnMasked.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
/* 107 */         if (ImageSaveDialog.this.entrance.getMaskedImage() != null) {
/* 108 */           lblNewLabel.setImage(ImageSaveDialog.this.entrance.getMaskedImage());
/* 109 */           if (ImageSaveDialog.this.spatial)
/* 110 */             lblNewLabel.toolImage(ImageSaveDialog.this.x, ImageSaveDialog.this.y, ImageSaveDialog.this.entrance.getColor(), ImageSaveDialog.this.tool, ImageSaveDialog.this.entrance.getLineWidth(), ImageSaveDialog.this.entrance.getCursorStyle());
/* 111 */           ImageSaveDialog.this.imgType = 2;
/* 112 */         } else if (ImageSaveDialog.this.imgType == 0) {
/* 113 */           ImageSaveDialog.this.rdbtnOriginal.setSelected(true);
         } else {
/* 115 */           ImageSaveDialog.this.rdbtnEnhanced.setSelected(true);
         }
       }
     });
/* 119 */     this.buttonGroup.add(this.rdbtnMasked);
/* 120 */     this.rdbtnMasked.setBounds(331, 497, 109, 23);
/* 121 */     this.contentPanel.add(this.rdbtnMasked);
 
/* 123 */     JButton okButton = new JButton("Save");
/* 124 */     okButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { ImageSaveDialog.this.me.dispose();
 
/* 128 */         int i = 0;
         File f;
         try { org.eclipse.swt.widgets.FileDialog fc = new org.eclipse.swt.widgets.FileDialog(new Shell(), 8192);
/* 131 */           String[] fileTypes = { "JPG", "PNG", "JPEG", "BMP", "GIF" };
/* 132 */           String[] fileExtensions = { "*.jpg", "*.png", "*.jpeg", "*.bmp", "*.gif" };
/* 133 */           fc.setFilterExtensions(fileExtensions);
/* 134 */           fc.setFilterNames(fileTypes);
/* 135 */           fc.setFileName("New_Image.jpg");
/* 136 */           fc.setOverwrite(true);
/* 137 */           fc.setText("Save Image");
/* 138 */           String path = fc.open();
/* 139 */           if (path == null)
/* 140 */             return;
/* 141 */           f = new File(path);
/* 142 */           i = fc.getFilterIndex();
         } catch (Throwable t) {
/* 144 */           java.awt.FileDialog fc = new java.awt.FileDialog(ImageSaveDialog.this.entrance.getMainFrame());
/* 145 */           fc.setFilenameFilter(new ImageFilter());
/* 146 */           fc.setMode(1);
/* 147 */           if (ImageSaveDialog.this.entrance.getLastDirectory() != null)
/* 148 */             fc.setDirectory(ImageSaveDialog.this.entrance.getLastDirectory());
/* 149 */           fc.setVisible(true);
/* 150 */           String s = fc.getFile();
/* 151 */           if (s == null)
/* 152 */             return;
/* 153 */           f = new File(fc.getDirectory() + s);
         }
         try {
/* 156 */           if (f != null) {
/* 157 */             String s = f.getName();
/* 158 */             if (!s.contains(".")) {
/* 159 */               switch (i) {
               case 0:
/* 161 */                 s = s + ".jpg";
/* 162 */                 break;
               case 1:
/* 164 */                 s = s + ".png";
/* 165 */                 break;
               case 2:
/* 167 */                 s = s + ".jpeg";
/* 168 */                 break;
               case 3:
/* 170 */                 s = s + ".bmp";
/* 171 */                 break;
               case 4:
/* 173 */                 s = s + ".gif";
/* 174 */                 break;
               case 5:
/* 176 */                 s = s + ".wbmp";
               }
             }
 
/* 180 */             ImageIO.write(lblNewLabel.getZoomedTool(), s.substring(s.lastIndexOf('.') + 1), f);
           }
         }
         catch (IOException localIOException)
         {
         }
       }
     });
/* 186 */     okButton.setBounds(460, 497, 90, 23);
/* 187 */     this.contentPanel.add(okButton);
/* 188 */     okButton.setActionCommand("OK");
/* 189 */     getRootPane().setDefaultButton(okButton);
 
/* 191 */     JButton cancelButton = new JButton("Cancel");
/* 192 */     cancelButton.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
/* 194 */         ImageSaveDialog.this.me.dispose();
       }
     });
/* 197 */     cancelButton.setBounds(560, 497, 90, 23);
/* 198 */     this.contentPanel.add(cancelButton);
/* 199 */     cancelButton.setActionCommand("Cancel");
   }
 }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.dialogs.ImageSaveDialog
 * JD-Core Version:    0.6.2
 */