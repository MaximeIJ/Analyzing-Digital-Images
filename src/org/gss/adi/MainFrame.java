 package org.gss.adi;
 
 import java.awt.BorderLayout;
 import java.awt.Dimension;
 import javax.swing.JFrame;
 import javax.swing.JMenu;
 import javax.swing.JMenuItem;
 import javax.swing.JPanel;
 import javax.swing.JScrollBar;
 import javax.swing.JScrollPane;
 import javax.swing.JViewport;
 import javax.swing.border.EmptyBorder;
 
 public class MainFrame extends JFrame
 {
   private static final long serialVersionUID = 1L;
   private JPanel contentPane;
   private ADIMenu menu;
   private JPanel currentPanel;
   private JScrollPane scroller;
   private Entrance entrance;
 
   public MainFrame(Entrance e)
   {
     this.entrance = e;
     setDefaultCloseOperation(3);
     setSize(1100, 725);
     instantiatePane();
     this.menu = new ADIMenu(e);
     setJMenuBar(this.menu);
     setExtendedState(6);
     pack();
     setVisible(true);
   }
 
   protected void instantiatePane()
   {
     this.contentPane = new JPanel();
     this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
     this.contentPane.setLayout(new BorderLayout(0, 0));
     setContentPane(this.contentPane);
 
     this.scroller = new JScrollPane();
     this.scroller.setBorder(null);
     this.scroller.getVerticalScrollBar().setUnitIncrement(16);
     this.contentPane.add(this.scroller, "Center");
     this.currentPanel = new StartPanel(this.entrance);
     this.currentPanel.setPreferredSize(new Dimension(1000, 650));
     this.scroller.setViewportView(this.currentPanel);
     this.scroller.setWheelScrollingEnabled(true);
   }
 
   protected void setPane(JPanel pane)
   {
     try
     {
       this.scroller.remove(this.currentPanel); } catch (Exception localException) {
     }
     this.currentPanel = pane;
     this.currentPanel.setPreferredSize(new Dimension(1050, 653));
     this.menu.applyMask.setVisible(false);
     this.menu.saveNewMask.setVisible(false);
     this.scroller.setViewportView(pane);
   }
 
   protected JPanel getPane()
   {
     return (JPanel)this.scroller.getViewport().getView();
   }
 
   protected JScrollPane getScroll()
   {
     return this.scroller;
   }
 
   public ADIMenu getMenu()
   {
     return this.menu;
   }
 }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.MainFrame
 * JD-Core Version:    0.6.2
 */