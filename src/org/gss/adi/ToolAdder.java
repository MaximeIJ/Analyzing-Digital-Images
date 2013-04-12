package org.gss.adi;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.gss.adi.datapanels.DataPanel;
import org.gss.adi.datapanels.FocusPanel;
import org.gss.adi.datapanels.LinePanel;
import org.gss.adi.datapanels.NDVIMaskPolygonPanel;
import org.gss.adi.datapanels.NDVIMaskRectanglePanel;
import org.gss.adi.datapanels.PathPanel;
import org.gss.adi.datapanels.PointGraphPanel;
import org.gss.adi.datapanels.PointTextPanel;
import org.gss.adi.datapanels.PolygonPanel;
import org.gss.adi.datapanels.RectanglePanel;
import org.gss.adi.dialogs.NDVIMaskDialog;
import org.gss.adi.tools.ColorTools;






public class ToolAdder
{
  private JComboBox comboBox;
  private int moving = -1;
  private Integer[] x = { Integer.valueOf(0) };
  private Integer[] y = { Integer.valueOf(0) };
  private boolean polygonComplete = false;
  private boolean pathComplete = false;
  private int numPoints = 0;
  private Entrance entrance;
  private ZoomPanLabel[] Labels;
  private int length;
  private Toolable Panel;
  public DataPanel dataPanel;
  private boolean dataPanelAdded = false;
  
  private NDVIMaskDialog ndvidialog = null;
  
  private int RGB;
  
  private JComboBox enhance;
  private BufferedImage mask = null;
  
  public ToolAdder(JComboBox combobox, Entrance e, ZoomPanLabel[] labels, Toolable panel, BufferedImage msk, int rgb, JComboBox enhancement)
  {
    this.comboBox = combobox;
    this.enhance = enhancement;
    this.entrance = e;
    this.RGB = rgb;
    this.Panel = panel;
    this.Labels = labels;
    this.length = labels.length;
    changeTool();
    this.mask = msk;
    for (int i = 0; i < this.length; i++) {
      if (labels[i].getLabel().getMouseListeners().length < 2) {
        labels[i].getLabel().addMouseListener(this.ma);
        labels[i].getLabel().addMouseMotionListener(this.mma);
      } 
    } 
    this.comboBox.addItemListener(this.il);
  } 
  public Integer[] getX() {
    return this.x;
  } 
  public Integer[] getY() {
    return this.y;
  } 
  public boolean polyComplete() {
    return this.polygonComplete;
  } 
  public void changeTool() {
    if (this.ndvidialog != null)
      this.ndvidialog.dispose(); 
    this.ndvidialog = null;
    this.polygonComplete = false;
    this.pathComplete = false;
    removeTools();
    this.Panel.removePoints();
    String tool = (String)this.comboBox.getSelectedItem();
    this.Panel.removeDataPanel(this.dataPanel);
    if (tool.equals("Point (text)")) {
      createPointTextPanel();
    } else if (tool.equals("Point (graph)")) {
      createPointGraphPanel();
    } else if (tool.equals("Line")) {
      createLinePanel();
      this.Labels[0].getLabel().addMouseListener(this.ma1);
      this.Labels[1].getLabel().addMouseListener(this.ma2);
      if (this.length == 3) {
        this.Labels[2].getLabel().addMouseListener(this.ma3);
      } 
    } else if (tool.equals("Path (multiple point line)")) {
      createPathPanel();
      this.Labels[0].getLabel().addMouseListener(this.ma1);
      this.Labels[1].getLabel().addMouseListener(this.ma2);
      if (this.length == 3) {
        this.Labels[2].getLabel().addMouseListener(this.ma3);
      } 
    } else if (tool.equals("Rectangle")) {
      createRectanglePanel();
      this.Labels[0].getLabel().addMouseListener(this.ma1);
      this.Labels[1].getLabel().addMouseListener(this.ma2);
      if (this.length == 3)
        this.Labels[2].getLabel().addMouseListener(this.ma3); 
    } else if (tool.equals("Polygon")) {
      createPolygonPanel();
      this.Labels[0].getLabel().addMouseListener(this.ma1);
      this.Labels[1].getLabel().addMouseListener(this.ma2);
      if (this.length == 3)
        this.Labels[2].getLabel().addMouseListener(this.ma3); 
    } else if (tool.contains("Rectangle")) {
      this.x = new Integer[] { Integer.valueOf(0), Integer.valueOf(0) };
      this.y = new Integer[] { Integer.valueOf(0), Integer.valueOf(0) };
      createNDVIRectanglePanel();
      this.Labels[0].getLabel().addMouseListener(this.ma1);
      this.Labels[1].getLabel().addMouseListener(this.ma2);
      if (this.length == 3)
        this.Labels[2].getLabel().addMouseListener(this.ma3); 
      this.ndvidialog = new NDVIMaskDialog(this);
      this.ndvidialog.setVisible(true);
    } else if (tool.contains("Polygon")) {
      createNDVIPolygonPanel();
      this.Labels[0].getLabel().addMouseListener(this.ma1);
      this.Labels[1].getLabel().addMouseListener(this.ma2);
      if (this.length == 3)
        this.Labels[2].getLabel().addMouseListener(this.ma3); 
      this.ndvidialog = new NDVIMaskDialog(this);
      this.ndvidialog.setVisible(true);
    } 
    if (this.length < 4)
      this.Panel.setDataPanel(this.dataPanel); 
    ((JPanel)this.Panel).repaint();
  } 
  
  private void createPointTextPanel() {
    int type = this.RGB;
    if (this.enhance.getSelectedIndex() == 8)
      type = 2; 
    if (this.length == 3) {
      this.dataPanel = new PointTextPanel(this.Labels[0].getOriginal(), 
        this.Labels[1].getOriginal(), 
        this.Labels[2].getOriginal(), type);
      this.dataPanel.threePics();
    } else {
      this.dataPanel = new PointTextPanel(this.Labels[0].getOriginal(), 
        this.Labels[1].getOriginal(), null, type);
    } 
  } 
  public void drawTools() {
    for (int i = 0; i < this.length; i++) {
      this.Labels[i].toolImage(this.x, this.y, this.entrance.getColor(), (String)this.comboBox.getSelectedItem(), this.entrance.getLineWidth(), this.entrance.getCursorStyle());
    } 
  } 
  private void createPointGraphPanel() {
    int type = this.RGB;
    if (this.enhance.getSelectedIndex() == 8)
      type = 2; 
    if (this.length == 3) {
      this.dataPanel = new PointGraphPanel(this.Labels[0].getOriginal(), 
        this.Labels[1].getOriginal(), 
        this.Labels[2].getOriginal(), type);
      this.dataPanel.threePics();
    } else {
      this.dataPanel = new PointGraphPanel(this.Labels[0].getOriginal(), 
        this.Labels[1].getOriginal(), null, type);
    } 
  } 
  private void createLinePanel() {
    int type = this.RGB;
    if (this.enhance.getSelectedIndex() == 8)
      type = 2; 
    if (this.length == 3) {
      this.dataPanel = new LinePanel(this.Labels[0].getOriginal(), 
        this.Labels[1].getOriginal(), 
        this.Labels[2].getOriginal(), type);
      this.dataPanel.threePics();
    } else {
      this.dataPanel = new LinePanel(this.Labels[0].getOriginal(), 
        this.Labels[1].getOriginal(), null, type);
    } 
  } 
  private void createPathPanel() {
    int type = this.RGB;
    if (this.enhance.getSelectedIndex() == 8)
      type = 2; 
    if (this.length == 3) {
      this.dataPanel = new PathPanel(this.Labels[0].getOriginal(), 
        this.Labels[1].getOriginal(), 
        this.Labels[2].getOriginal(), type);
      this.dataPanel.threePics();
    } else {
      this.dataPanel = new PathPanel(this.Labels[0].getOriginal(), 
        this.Labels[1].getOriginal(), null, type);
    } 
  } 
  private void createRectanglePanel() {
    int type = this.RGB;
    if (this.enhance.getSelectedIndex() == 8)
      type = 2; 
    if (this.length == 3) {
      this.dataPanel = new RectanglePanel(this.Labels[0].getOriginal(), 
        this.Labels[1].getOriginal(), 
        this.Labels[2].getOriginal(), type);
      this.dataPanel.threePics();
    } else {
      this.dataPanel = new RectanglePanel(this.Labels[0].getOriginal(), 
        this.Labels[1].getOriginal(), null, type);
    } 
  } 
  private void createPolygonPanel() {
    int type = this.RGB;
    if (this.enhance.getSelectedIndex() == 8)
      type = 2; 
    if (this.length == 3) {
      this.dataPanel = new PolygonPanel(this.Labels[0].getOriginal(), 
        this.Labels[1].getOriginal(), 
        this.Labels[2].getOriginal(), type);
      this.dataPanel.threePics();
    } else {
      this.dataPanel = new PolygonPanel(this.Labels[0].getOriginal(), 
        this.Labels[1].getOriginal(), null, type);
    } 
  } 
  private void createNDVIRectanglePanel() {
    int type = this.RGB;
    if (this.enhance.getSelectedIndex() == 8)
      type = 2; 
    if (this.length == 3) {
      this.dataPanel = new NDVIMaskRectanglePanel(this.Labels[0].getOriginal(), 
        this.Labels[1].getOriginal(), 
        this.Labels[2].getOriginal(), type);
      this.dataPanel.threePics();
    } else {
      this.dataPanel = new NDVIMaskRectanglePanel(this.Labels[0].getOriginal(), 
        this.Labels[1].getOriginal(), null, type);
    } 
  } 
  private void createNDVIPolygonPanel() {
    int type = this.RGB;
    if (this.enhance.getSelectedIndex() == 8)
      type = 2; 
    if (this.length == 3) {
      this.dataPanel = new NDVIMaskPolygonPanel(this.Labels[0].getOriginal(), 
        this.Labels[1].getOriginal(), 
        this.Labels[2].getOriginal(), type);
      this.dataPanel.threePics();
    } else {
      this.dataPanel = new NDVIMaskPolygonPanel(this.Labels[0].getOriginal(), 
        this.Labels[1].getOriginal(), null, type);
    } 
  } 
  protected void update() { String tool = (String)this.comboBox.getSelectedItem();
    if (this.Labels.length < 4) {
      if ((!tool.contains("Polygon")) || (this.polygonComplete)) {
        if (this.length == 3)
          this.dataPanel.update3(this.x, this.y); 
        this.dataPanel.update2(this.x, this.y);
        this.dataPanel.update1(this.x, this.y);
      } 
    } else {
      double count1 = 0D;
      double count2 = 0D;
      double count3 = 0D;
      double count4 = 0D;
      double count = 0D;
      BufferedImage img1 = this.Labels[0].getZoomedOriginal();
      BufferedImage img2 = this.Labels[1].getZoomedOriginal();
      BufferedImage img3 = this.Labels[2].getZoomedOriginal();
      BufferedImage img4 = this.Labels[3].getZoomedOriginal();
      if ((this.polygonComplete) && (tool.contains("Polygon"))) {
        int xmin = 9000;
        int xmax = -1;
        int ymin = 9000;
        int ymax = -1;
        int[] X = new int[this.x.length];
        int[] Y = new int[this.y.length];
        for (int i = 0; i < this.x.length; i++) {
          X[i] = this.x[i].intValue();
          if (this.x[i].intValue() > xmax) {
            xmax = this.x[i].intValue();
          } 
          if (this.x[i].intValue() < xmin) {
            xmin = this.x[i].intValue();
          } 
          if (this.y[i].intValue() > ymax) {
            ymax = this.y[i].intValue();
          } 
          
          if (this.y[i].intValue() < ymin) {
            ymin = this.y[i].intValue();
          } 
          Y[i] = this.y[i].intValue();
        } 
        Polygon poly = new Polygon(X, Y, X.length);
        for (int i = xmin; i <= xmax; i++) {
          for (int j = ymin; j <= ymax; j++) {
            if (poly.contains(i, j)) {
              int msk = this.mask.getRGB(i, j);
              if ((msk & 0xFFFFFF) > 16711679) {
                count += 1D;
                if ((img1.getRGB(i, j) & 0xFFFFFF) == 0)
                  count1 += 1D; 
                if ((img2.getRGB(i, j) & 0xFFFFFF) == 0)
                  count2 += 1D; 
                if ((img3.getRGB(i, j) & 0xFFFFFF) == 0)
                  count3 += 1D; 
                if ((img4.getRGB(i, j) & 0xFFFFFF) == 0) {
                  count4 += 1D;
                } 
              } 
            } 
          } 
        } 
      } else if (tool.contains("Rectangle"))
      {
        int xmax;
        
        int xmin;
        if (this.x[0].intValue() <= this.x[1].intValue()) {
          xmin = this.x[0].intValue();
          xmax = this.x[1].intValue();
        } else {
          xmin = this.x[1].intValue();
          xmax = this.x[0].intValue(); } 
        int ymax;
        int ymin;
        if (this.y[0].intValue() <= this.y[1].intValue()) {
          ymin = this.y[0].intValue();
          ymax = this.y[1].intValue();
        } else {
          ymin = this.y[1].intValue();
          ymax = this.y[0].intValue();
        } 
        BufferedImage img = new BufferedImage(472, 285, 1);
        Graphics2D g = img.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 472, 285);
        g.dispose();
        for (int i = xmin; i <= xmax; i++)
          for (int j = ymin; j <= ymax; j++) {
            int msk = this.mask.getRGB(i, j);
            if ((msk & 0xFFFFFF) > 16711679) {
              count += 1D;
              img.setRGB(i, j, img1.getRGB(i, j) & 0xFFFFFF);
              if ((img1.getRGB(i, j) & 0xFFFFFF) == 0) {
                count1 += 1D;
              } 
              
              if ((img2.getRGB(i, j) & 0xFFFFFF) == 0)
                count2 += 1D; 
              if ((img3.getRGB(i, j) & 0xFFFFFF) == 0)
                count3 += 1D; 
              if ((img4.getRGB(i, j) & 0xFFFFFF) == 0) {
                count4 += 1D;
              } 
            } 
          }  
      } 
      DecimalFormat df = new DecimalFormat("#.#");
      this.Panel.growth(new String[] { df.format(100.0D * count1 / count), 
        df.format(100.0D * count2 / count), 
        df.format(100.0D * count3 / count), 
        df.format(100.0D * count4 / count) });
    } 
  } 
  
  private int toolContains(int eX, int eY)
  {
    int i = 0;
    if ((((String)this.comboBox.getSelectedItem()).contains("Polygon")) && (!this.polygonComplete)) {return -1;}
    for (i = 0; 
        
        i < this.x.length; i++) {
      try {
        if (ColorTools.linearDist(this.x[i].intValue()/6, this.y[i].intValue()/6, eX/6, eY/6).doubleValue() < 6.0D) {
          return i;
        } 
      }
      catch (NullPointerException localNullPointerException) {}
    } 
    
    return -1;
  } 
  
  public void removeTools() {
    for (int i = 0; i < this.length; i++) {
      this.Labels[i].setZoomedTool(this.Labels[i].getZoomedOriginal());
    } 
    this.polygonComplete = false;
    this.dataPanelAdded = false;
    this.numPoints = 0;
    String tool = (String)this.comboBox.getSelectedItem();
    if (tool.equals("Point (text)")) {
      this.x = new Integer[1];
      this.y = new Integer[1];
    } else if (tool.equals("Point (graph)")) {
      this.x = new Integer[1];
      this.y = new Integer[1];
    } else if (tool.equals("Line")) {
      this.x = new Integer[2];
      this.y = new Integer[2];
    } else if (tool.contains("Path")) {
      this.x = new Integer[0];
      this.y = new Integer[0];
    } else if (tool.contains("Rectangle")) {
      this.x = new Integer[2];
      this.y = new Integer[2];
    } else if (tool.contains("Polygon")) {
      this.x = new Integer[0];
      this.y = new Integer[0];
    } 
  } 
  
  public void mousePress(MouseEvent e)
  {
    String tool = (String)this.comboBox.getSelectedItem();
    Point p = this.Labels[0].mapToPixel(e.getX(), e.getY());
    int eX = p.x;
    int eY = p.y;
    this.moving = toolContains(eX, eY);
    if (this.moving == -1) {
      if (!this.dataPanelAdded) {
        this.Panel.setDataPanel(this.dataPanel);
        this.dataPanelAdded = true;
      } 
      if ((tool.equals("Point (text)")) || (tool.equals("Point (graph)"))) {
        this.x[0] = Integer.valueOf(eX);
        this.y[0] = Integer.valueOf(eY);
        this.Panel.setPoint1(this.x[0].intValue(), this.y[0].intValue());
      } else if ((tool.equals("Line")) || (tool.contains("Rectangle"))) {
        Integer tmp189_186 = Integer.valueOf(eX);this.x[1] = tmp189_186;this.x[0] = tmp189_186; Integer 
          tmp207_204 = Integer.valueOf(eY);this.y[1] = tmp207_204;this.y[0] = tmp207_204;
        this.Panel.setPoint1(this.x[0].intValue(), this.y[0].intValue());
        this.Panel.setPoint2(this.x[1].intValue(), this.y[1].intValue());
      } else if (tool.equals("Path (multiple point line)")) {
        if (this.pathComplete) {
          this.x = new Integer[0];
          this.y = new Integer[0];
          this.pathComplete = false;
          this.numPoints = 0;
        } 
        this.x = addToArray(this.x, eX);
        this.y = addToArray(this.y, eY);
        this.numPoints += 1;
      } else if (tool.contains("Polygon")) {
        if (this.polygonComplete) {
          this.x = new Integer[0];
          this.y = new Integer[0];
          this.polygonComplete = false;
          this.numPoints = 0;
        } 
        this.x = addToArray(this.x, eX);
        this.y = addToArray(this.y, eY);
        this.numPoints += 1;
      } 
    } else {
      this.x[this.moving] = Integer.valueOf(eX);
      this.y[this.moving] = Integer.valueOf(eY);
      if ((this.polygonComplete) && (this.moving == 0)) {
        this.x[(this.x.length - 1)] = Integer.valueOf(eX);
        this.y[(this.y.length - 1)] = Integer.valueOf(eY);
      } else if ((tool.equals("Point (text)")) || 
        (tool.equals("Point (graph)"))) {
        this.Panel.setPoint1(this.x[0].intValue(), this.y[0].intValue());
      } else if ((tool.equals("Line")) || (tool.contains("Rectangle"))) {
        this.Panel.setPoint1(this.x[0].intValue(), this.y[0].intValue());
        this.Panel.setPoint2(this.x[1].intValue(), this.y[1].intValue());
      } 
    } 
    for (int i = 0; i < this.length; i++) {
      this.Labels[i].toolImage(this.x, this.y, this.entrance.getColor(), tool, this.entrance.getLineWidth(), this.entrance.getCursorStyle());
    } 
    update();
  } 
  
  public void mouseDrag(MouseEvent e) {
    String tool = (String)this.comboBox.getSelectedItem();
    Point p = this.Labels[0].mapToPixel(e.getX(), e.getY());
    int eX = p.x;
    int eY = p.y;
    if (eX >= this.Labels[0].getOriginal().getWidth()) {
      eX = this.Labels[0].getOriginal().getWidth() - 1;
    } else if (eX < 0) {
      eX = 0;
    } 
    if (eY >= this.Labels[0].getOriginal().getHeight()) {
      eY = this.Labels[0].getOriginal().getHeight() - 1;
    } else if (eY < 0) {
      eY = 0;
    } 
    if (this.moving == -1) {
      if ((tool.equals("Point (text)")) || (tool.equals("Point (graph)"))) {
        this.x[0] = Integer.valueOf(eX);
        this.y[0] = Integer.valueOf(eY);
        this.Panel.setPoint1(this.x[0].intValue(), this.y[0].intValue());
      } else if ((tool.equals("Line")) || (tool.contains("Rectangle"))) {
        this.x[1] = Integer.valueOf(eX);
        this.y[1] = Integer.valueOf(eY);
        this.Panel.setPoint1(this.x[0].intValue(), this.y[0].intValue());
        this.Panel.setPoint2(this.x[1].intValue(), this.y[1].intValue());
      } else if (tool.equals("Path (multiple point line)")) {
        this.x[(this.numPoints - 1)] = Integer.valueOf(eX);
        this.y[(this.numPoints - 1)] = Integer.valueOf(eY);
      } else if (tool.contains("Polygon")) {
        this.x[(this.numPoints - 1)] = Integer.valueOf(eX);
        this.y[(this.numPoints - 1)] = Integer.valueOf(eY);
      } 
    } else {
      this.x[this.moving] = Integer.valueOf(eX);
      this.y[this.moving] = Integer.valueOf(eY);
      if ((this.polygonComplete) && (this.moving == 0)) {
        this.x[(this.x.length - 1)] = Integer.valueOf(eX);
        this.y[(this.y.length - 1)] = Integer.valueOf(eY);
      } else if ((tool.equals("Point (text)")) || 
        (tool.equals("Point (graph)"))) {
        this.Panel.setPoint1(this.x[0].intValue(), this.y[0].intValue());
      } else if ((tool.equals("Line")) || (tool.equals("Rectangle"))) {
        this.Panel.setPoint1(this.x[0].intValue(), this.y[0].intValue());
        this.Panel.setPoint2(this.x[1].intValue(), this.y[1].intValue());
      } 
    } 
    for (int i = 0; i < this.length; i++) {
      this.Labels[i].toolImage(this.x, this.y, this.entrance.getColor(), tool, this.entrance.getLineWidth(), this.entrance.getCursorStyle());
    } 
    update();
  } 
  
  public void mouseRelease(MouseEvent e) {
    this.Panel.removePoints();
    String tool = (String)this.comboBox.getSelectedItem();
    Point p = this.Labels[0].mapToPixel(e.getX(), e.getY());
    int eX = p.x;
    int eY = p.y;
    if (eX >= this.Labels[0].getOriginal().getWidth()) {
      eX = this.Labels[0].getOriginal().getWidth() - 1;
    } else if (eX < 0) {
      eX = 0;
    } 
    if (eY >= this.Labels[0].getOriginal().getHeight()) {
      eY = this.Labels[0].getOriginal().getHeight() - 1;
    } else if (eY < 0) {
      eY = 0;
    } 
    if (this.moving == -1) {
      if ((tool.equals("Point (text)")) || (tool.equals("Point (graph)"))) {
        this.x[0] = Integer.valueOf(eX);
        this.y[0] = Integer.valueOf(eY);
        this.Panel.setPoint1(this.x[0].intValue(), this.y[0].intValue());
      } else if ((tool.equals("Line")) || (tool.contains("Rectangle"))) {
        this.x[1] = Integer.valueOf(eX);
        this.y[1] = Integer.valueOf(eY);
        this.Panel.setPoint1(this.x[0].intValue(), this.y[0].intValue());
        this.Panel.setPoint2(this.x[1].intValue(), this.y[1].intValue());
      } else if (tool.equals("Path (multiple point line)")) {
        this.x[(this.numPoints - 1)] = Integer.valueOf(eX);
        this.y[(this.numPoints - 1)] = Integer.valueOf(eY);
      } else if (tool.contains("Polygon")) {
        if (ColorTools.linearDist(this.x[0].intValue()/6, this.y[0].intValue()/6, eX/6, eY/6).doubleValue() < 6.0D) {
          eX = this.x[0].intValue();
          eY = this.y[0].intValue();
        } 
        this.x[(this.numPoints - 1)] = Integer.valueOf(eX);
        this.y[(this.numPoints - 1)] = Integer.valueOf(eY);
        if ((this.x.length != 1) && (this.x[0].intValue() == eX) && (this.y[0].intValue() == eY)) {
          this.polygonComplete = true;
        } 
      } 
    } else {
      this.x[this.moving] = Integer.valueOf(eX);
      this.y[this.moving] = Integer.valueOf(eY);
      if ((this.polygonComplete) && (this.moving == 0)) {
        this.x[(this.x.length - 1)] = Integer.valueOf(eX);
        this.y[(this.y.length - 1)] = Integer.valueOf(eY);
      } else if ((tool.equals("Point (text)")) || 
        (tool.equals("Point (graph)"))) {
        this.Panel.setPoint1(this.x[0].intValue(), this.y[0].intValue());
      } else if ((tool.equals("Line")) || (tool.equals("Rectangle"))) {
        this.Panel.setPoint1(this.x[0].intValue(), this.y[0].intValue());
        this.Panel.setPoint2(this.x[1].intValue(), this.y[1].intValue());
      } 
    } 
    for (int i = 0; i < this.length; i++) {
      this.Labels[i].toolImage(this.x, this.y, this.entrance.getColor(), tool, this.entrance.getLineWidth(), this.entrance.getCursorStyle());
    } 
    update();
  } 
  
  private Integer[] addToArray(Integer[] array, int element) {
    int l = array.length;
    Integer[] result = new Integer[l + 1];
    for (int i = 0; i < l; i++) {
      result[i] = array[i];
    } 
    result[l] = Integer.valueOf(element);
    return result;
  } 
  
  public boolean mvPoint1(int x1, int y1) {
    String tool = (String)this.comboBox.getSelectedItem();
    if ((x1 >= 0) && (y1 >= 0) && (x1 < this.Labels[0].getOriginal().getWidth()) && (y1 < this.Labels[0].getOriginal().getHeight())) {
      this.x[0] = Integer.valueOf(x1);
      this.y[0] = Integer.valueOf(y1);
      update();
      for (int i = 0; i < this.length; i++) {
        this.Labels[i].toolImage(this.x, this.y, this.entrance.getColor(), tool, this.entrance.getLineWidth(), this.entrance.getCursorStyle());
      } 
      return true;
    } 
    this.Panel.setPoint1(this.x[0].intValue(), this.y[0].intValue());
    return false;
  } 
  
  public boolean mvPoint2(int x2, int y2)
  {
    String tool = (String)this.comboBox.getSelectedItem();
    if ((x2 >= 0) && (y2 >= 0) && (x2 < this.Labels[0].getOriginal().getWidth()) && (y2 < this.Labels[0].getOriginal().getHeight())) {
      this.x[1] = Integer.valueOf(x2);
      this.y[1] = Integer.valueOf(y2);
      update();
      for (int i = 0; i < this.length; i++) {
        this.Labels[i].toolImage(this.x, this.y, this.entrance.getColor(), tool, this.entrance.getLineWidth(), this.entrance.getCursorStyle());
      } 
      return true;
    } 
    this.Panel.setPoint2(this.x[1].intValue(), this.y[1].intValue());
    return false;
  } 
  
  private MouseAdapter ma1 = new MouseAdapter()
  {
    public void mouseEntered(MouseEvent e) {
      try {
        ((FocusPanel)ToolAdder.this.dataPanel).clear();
        ((FocusPanel)ToolAdder.this.dataPanel).draw1();
      }
      catch (Exception localException) {}
    } 
    public void mouseExited(MouseEvent e) {
      try {
        ToolAdder.this.update();
      } catch (Exception localException) {}
    }  };
  private MouseAdapter ma2 = new MouseAdapter()
  {
    public void mouseEntered(MouseEvent e) {
      try {
        ((FocusPanel)ToolAdder.this.dataPanel).clear();
        ((FocusPanel)ToolAdder.this.dataPanel).draw2();
      }
      catch (Exception localException) {}
    } 
    public void mouseExited(MouseEvent e) {
      try {
        ToolAdder.this.update();
      } catch (NullPointerException localNullPointerException) {}
    }  };
  private MouseAdapter ma3 = new MouseAdapter()
  {
    public void mouseEntered(MouseEvent e) {
      try {
        ((FocusPanel)ToolAdder.this.dataPanel).clear();
        ((FocusPanel)ToolAdder.this.dataPanel).draw3();
      }
      catch (Exception localException) {}
    } 
    public void mouseExited(MouseEvent e) {
      try {
        ToolAdder.this.update();
      } catch (NullPointerException localNullPointerException) {}
    }  };
  private MouseAdapter ma = new MouseAdapter()
  {
    public void mousePressed(MouseEvent e) {
      if (SwingUtilities.isLeftMouseButton(e)) {
        ToolAdder.this.mousePress(e);
      } 
    } 
    
    public void mouseReleased(MouseEvent e) {
      if (SwingUtilities.isLeftMouseButton(e)) {
        ToolAdder.this.mouseRelease(e);
      } 
    } 
    public void mouseClicked(MouseEvent e) {
      if (e.getClickCount() > 1) {
        ToolAdder.this.pathComplete = true;
      } 
    } 
  };
  private MouseMotionAdapter mma = new MouseMotionAdapter()
  {
    public void mouseDragged(MouseEvent e) {
      if (SwingUtilities.isLeftMouseButton(e))
        ToolAdder.this.mouseDrag(e); 
    } 
  };
  private ItemListener il = new ItemListener()
  {
    public void itemStateChanged(ItemEvent arg0) {
      if (arg0.getStateChange() == 1) {
        ToolAdder.this.changeTool();
      } 
    } 
  };
} 