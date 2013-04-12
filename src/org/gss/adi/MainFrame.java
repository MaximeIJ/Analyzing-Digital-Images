/*    */ package org.gss.adi;
/*    */ 
/*    */ import java.awt.BorderLayout;
/*    */ import java.awt.Dimension;
/*    */ import javax.swing.JFrame;
/*    */ import javax.swing.JMenu;
/*    */ import javax.swing.JMenuItem;
/*    */ import javax.swing.JPanel;
/*    */ import javax.swing.JScrollBar;
/*    */ import javax.swing.JScrollPane;
/*    */ import javax.swing.JViewport;
/*    */ import javax.swing.border.EmptyBorder;
/*    */ 
/*    */ public class MainFrame extends JFrame
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private JPanel contentPane;
/*    */   private ADIMenu menu;
/*    */   private JPanel currentPanel;
/*    */   private JScrollPane scroller;
/*    */   private Entrance entrance;
/*    */ 
/*    */   public MainFrame(Entrance e)
/*    */   {
/* 28 */     this.entrance = e;
/* 29 */     setDefaultCloseOperation(3);
/* 30 */     setSize(1100, 725);
/* 31 */     instantiatePane();
/* 32 */     this.menu = new ADIMenu(e);
/* 33 */     setJMenuBar(this.menu);
/* 34 */     setExtendedState(6);
/* 35 */     setVisible(true);
/*    */   }
/*    */ 
/*    */   protected void instantiatePane()
/*    */   {
/* 42 */     this.contentPane = new JPanel();
/* 43 */     this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
/* 44 */     this.contentPane.setLayout(new BorderLayout(0, 0));
/* 45 */     setContentPane(this.contentPane);
/*    */ 
/* 47 */     this.scroller = new JScrollPane();
/* 48 */     this.scroller.setBorder(null);
/* 49 */     this.scroller.getVerticalScrollBar().setUnitIncrement(16);
/* 50 */     this.contentPane.add(this.scroller, "Center");
/* 51 */     this.currentPanel = new StartPanel(this.entrance);
/* 52 */     this.currentPanel.setPreferredSize(new Dimension(1000, 650));
/* 53 */     this.scroller.setViewportView(this.currentPanel);
/* 54 */     this.scroller.setWheelScrollingEnabled(true);
/*    */   }
/*    */ 
/*    */   protected void setPane(JPanel pane)
/*    */   {
/*    */     try
/*    */     {
/* 62 */       this.scroller.remove(this.currentPanel); } catch (Exception localException) {
/*    */     }
/* 64 */     this.currentPanel = pane;
/* 65 */     this.currentPanel.setPreferredSize(new Dimension(1050, 653));
/* 66 */     this.menu.applyMask.setVisible(false);
/* 67 */     this.menu.saveNewMask.setVisible(false);
/* 68 */     this.scroller.setViewportView(pane);
/*    */   }
/*    */ 
/*    */   protected JPanel getPane()
/*    */   {
/* 75 */     return (JPanel)this.scroller.getViewport().getView();
/*    */   }
/*    */ 
/*    */   protected JScrollPane getScroll()
/*    */   {
/* 82 */     return this.scroller;
/*    */   }
/*    */ 
/*    */   public ADIMenu getMenu()
/*    */   {
/* 89 */     return this.menu;
/*    */   }
/*    */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.MainFrame
 * JD-Core Version:    0.6.2
 */