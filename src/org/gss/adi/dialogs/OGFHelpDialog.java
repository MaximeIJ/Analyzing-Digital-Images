/*    */ package org.gss.adi.dialogs;
/*    */ 
/*    */ import java.awt.BorderLayout;
/*    */ import java.awt.Container;
/*    */ import java.awt.FlowLayout;
/*    */ import java.awt.Font;
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.ActionListener;
/*    */ import javax.swing.ImageIcon;
/*    */ import javax.swing.JButton;
/*    */ import javax.swing.JDialog;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JPanel;
/*    */ import javax.swing.JTextArea;
/*    */ import javax.swing.border.EmptyBorder;
/*    */ import org.gss.adi.OldGrowthForestPanel;
/*    */ 
/*    */ public class OGFHelpDialog extends JDialog
/*    */ {
/*    */   private static final long serialVersionUID = -7069460513252475253L;
/* 22 */   private final JPanel contentPanel = new JPanel();
/* 23 */   private final OGFHelpDialog me = this;
/*    */ 
/*    */   public OGFHelpDialog()
/*    */   {
/* 28 */     setBounds(100, 100, 1020, 568);
/* 29 */     getContentPane().setLayout(new BorderLayout());
/* 30 */     this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
/* 31 */     getContentPane().add(this.contentPanel, "Center");
/* 32 */     this.contentPanel.setLayout(null);
/*    */ 
/* 34 */     JTextArea txtrWithTheInteractive = new JTextArea();
/* 35 */     txtrWithTheInteractive.setFont(new Font("SansSerif", 0, 12));
/* 36 */     txtrWithTheInteractive.setText("With the interactive analysis features, expore how the aerial extent of old growth forests have changed over time in the United States.\r\n\r\nDefinition of old-growth forests\r\n\"Old-grwoth forests are the fourth and final stage of stand development, following mature forests, in which the forest canopy is generally composed of scattered remaining trees that assumed dominance following natural disturbance along with newly dominant, shade-tolerant trees. Development of an old-growth forest generally takes more than 100 years, with variation depending on forest type. An old-growth forest exhibits these characteristics:\r\n\r\nA watershed-level forest of 5,000 acres or more in size, that has been left undisturbed, and predominantly has trees that are 200 to 1,000 years old;\r\n\r\nThe accumulation of coarse woody debris, snags and canopy gaps created by falen trees; The prescence of an understory consisting of a multilayered combination of seedlings, mature trees, bushes, and other plants, which attribute to a complex stand vegetation pattern;\r\n\r\nThe inclusion of numerous dead trees, both standing and fallen, that provide esential habitat and nutrients to plant and animal forest species;\r\n\r\nAn ecosystem rish in biodiversity, providing habitat for a wide variety of indigenous plant and animal species.\r\n\r\nAn old-growth forest is a unique combination of plants, animals, climate, and soil - an entire ecosystem that cannot be readily re-created.\"\r\n\r\n* From Boise's website (www.bc.com/environment/index.html)");
/* 37 */     txtrWithTheInteractive.setOpaque(false);
/* 38 */     txtrWithTheInteractive.setLineWrap(true);
/* 39 */     txtrWithTheInteractive.setWrapStyleWord(true);
/* 40 */     txtrWithTheInteractive.setEditable(false);
/* 41 */     txtrWithTheInteractive.setBounds(10, 11, 510, 497);
/* 42 */     this.contentPanel.add(txtrWithTheInteractive);
/*    */ 
/* 44 */     JLabel lblNewLabel = new JLabel();
/* 45 */     lblNewLabel.setIcon(new ImageIcon(OldGrowthForestPanel.class.getResource(
/* 46 */       "/resources/graphics/GSS Old Growth Forest/color mask.png")));
/* 47 */     lblNewLabel.setBounds(520, 11, 472, 285);
/* 48 */     this.contentPanel.add(lblNewLabel);
/*    */ 
/* 50 */     JTextArea txtrSelectARectangle = new JTextArea();
/* 51 */     txtrSelectARectangle.setText("Select a rectangle or polygon to see the percent old growth forest within the area.\r\n\r\nTo create a rectangle, clik and drag on one of the maps. Adjust the highlighted corners by changing the x and y values, or clicking and dragging either corner.\r\n\r\nTo create a polygon, click on a map to create corners. To finish the polygon, click on the location of the first corner. Adjust the shape of the polygon by clicking and dragging any corner.\r\n\r\nThe calculations ignore the state boundaries in black, the area outside the contiguous United States (green), and the Great Lakes (blue).");
/* 52 */     txtrSelectARectangle.setOpaque(false);
/* 53 */     txtrSelectARectangle.setEditable(false);
/* 54 */     txtrSelectARectangle.setWrapStyleWord(true);
/* 55 */     txtrSelectARectangle.setLineWrap(true);
/* 56 */     txtrSelectARectangle.setFont(new Font("SansSerif", 0, 12));
/* 57 */     txtrSelectARectangle.setBounds(520, 295, 472, 213);
/* 58 */     this.contentPanel.add(txtrSelectARectangle);
/*    */ 
/* 60 */     JPanel buttonPane = new JPanel();
/* 61 */     buttonPane.setLayout(new FlowLayout(1));
/* 62 */     getContentPane().add(buttonPane, "South");
/*    */ 
/* 64 */     JButton close = new JButton("Close");
/* 65 */     close.addActionListener(new ActionListener() {
/*    */       public void actionPerformed(ActionEvent e) {
/* 67 */         OGFHelpDialog.this.me.dispose();
/*    */       }
/*    */     });
/* 70 */     close.setActionCommand("Cancel");
/* 71 */     buttonPane.add(close);
/*    */   }
/*    */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.dialogs.OGFHelpDialog
 * JD-Core Version:    0.6.2
 */