package org.gss.adi;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import org.gss.adi.tools.ColorTools;

public class ZoomPanLabel extends JScrollPane
{
	private static final long serialVersionUID = 7210679722402692760L;
	private BufferedImage zoomedTool;
	private BufferedImage zoomedOriginal;
	private BufferedImage original;
	protected JLabel label;
	ZoomPanLabel me;
	private JSlider slider;
	protected ArrayList<ZoomPanLabel> syncd = new ArrayList();

	protected float zoomFactor = 1.0F;

	private boolean adjusting = false;

	public ZoomPanLabel(JSlider jslider)
	{
		this.slider = jslider;
		this.me = this;
		this.label = new JLabel();
		setViewportView(this.label);
		setBorder(null);
		setWheelScrollingEnabled(false);
		this.syncd.add(this);
		setVerticalAlignment(1);
		MousePanner panner = new MousePanner();
		this.label.addMouseMotionListener(panner);
		this.label.addMouseListener(panner);
	}

	public void setSize(int width, int height) {
		super.setSize(width, height);
		if (this.original != null)
			setImage(this.original);
	}

	public void setBounds(int X, int Y, int width, int height)
	{
		super.setBounds(X, Y, width, height);
		if (this.original != null)
			setImage(this.original);
	}

	public void setImage(BufferedImage img) {
		this.original = img;
		float z = 100.0F;
		if (img.getWidth() > getWidth()) {
			z = 100.0F * getWidth() / img.getWidth();
		}
		if (100.0D * img.getHeight() / getHeight() > z)
			z = 100.0F * getHeight() / img.getHeight();
		this.zoomedOriginal = resize(Math.round(z), img);
		this.zoomFactor = (new Float(this.zoomedOriginal.getWidth()).floatValue() / new Float(this.original.getWidth()).floatValue());
		this.slider.setMinimum(new Double(Math.floor(this.zoomFactor * 100.0D)).intValue());
		this.slider.setMaximum(400);
		this.slider.setValue(this.slider.getMinimum());
		this.label.setIcon(new ImageIcon(this.zoomedOriginal));
	}
	public BufferedImage getZoomedOriginal() {
		return this.zoomedOriginal;
	}
	public void setZoomedOriginal(BufferedImage img) {

		this.zoomedOriginal = img;
		this.zoomedTool = null;
		this.label.setIcon(new ImageIcon(this.zoomedOriginal));
	}
	public BufferedImage getZoomedTool() {
		return this.zoomedTool;
	}
	public void setZoomedTool(BufferedImage img) {
		this.zoomedTool = img;
		this.label.setIcon(new ImageIcon(this.zoomedTool));
	}
	public BufferedImage getOriginal() {
		return this.original;
	}

	public void toolImage(Integer[] x, Integer[] y, Color color, String tool, float lineWidth, byte cursorStyle)
	{
		Integer[] X = new Integer[x.length];
		Integer[] Y = new Integer[y.length];
		for (int i = 0; i < x.length; i++) {
			X[i] = Integer.valueOf(Math.round(x[i].intValue() * this.zoomFactor));
			Y[i] = Integer.valueOf(Math.round(y[i].intValue() * this.zoomFactor));
		}
		setZoomedTool(ColorTools.cursor(this.zoomedOriginal, X, Y, color, tool, Float.valueOf(lineWidth), cursorStyle));
	}
	public void showZoomedOriginal() {
		this.label.setIcon(new ImageIcon(this.zoomedOriginal));
	}
	public void showZoomedTool() {
		this.label.setIcon(new ImageIcon(this.zoomedTool));
	}

	public void zoom(int z)
	{
		this.zoomFactor = (new Float(z).floatValue() / 100.0F);
		this.zoomedOriginal = resize(z, this.original);
		this.label.setIcon(new ImageIcon(this.zoomedOriginal));
	}

	public void qualityZoom(int z)
	{
		this.zoomFactor = (new Float(z).floatValue() / 100.0F);
		this.zoomedOriginal = resize(z, this.original);
		this.label.setIcon(new ImageIcon(this.zoomedOriginal));
	}
	public void setVerticalAlignment(int align) {
		this.label.setVerticalAlignment(align);
	}
	public void setHorizontalAlignment(int align) {
		this.label.setVerticalAlignment(align);
	}
	protected static BufferedImage resize(int zoom, BufferedImage before) {
		float z = zoom / 100.0F;
		if (z == 0.0F)
			z = 1.0F;
		int w = Math.round(before.getWidth() * z);
		int h = Math.round(before.getHeight() * z);
		BufferedImage after = new BufferedImage(w, h, before.getType());
		AffineTransform at = new AffineTransform();
		at.scale(z, z);
		AffineTransformOp scaleOp = 
				new AffineTransformOp(at, 1);
		after = scaleOp.filter(before, after);
		return after;
	}
	public Point mapToPixel(int i, int j) {
		return new Point(Math.round(i / this.zoomFactor), Math.round(j / this.zoomFactor));
	}
	public Point zoomPixels(int i, int j) {
		return new Point(Math.round(i * this.zoomFactor), Math.round(j * this.zoomFactor));
	}
	public JLabel getLabel() {
		return this.label;
	}
	public void sync(ZoomPanLabel label) {
		this.syncd.add(label);
		Synchronizer syn = new Synchronizer(this, label);
		getVerticalScrollBar().addAdjustmentListener(syn);
		getHorizontalScrollBar().addAdjustmentListener(syn);
		label.getVerticalScrollBar().addAdjustmentListener(syn);
		label.getHorizontalScrollBar().addAdjustmentListener(syn);
		ArrayList otherList = label.syncd;
		for (int i = 0; i < this.syncd.size(); i++) {
			ZoomPanLabel item = (ZoomPanLabel)this.syncd.get(i);
			if (!otherList.contains(item)) {
				label.sync(item);
			}
		}
		for (int i = 0; i < otherList.size(); i++) {
			ZoomPanLabel item = (ZoomPanLabel)otherList.get(i);
			if (!this.syncd.contains(item))
				this.me.sync(item); 
		}
	}



	public boolean isAdjusting() {
		return adjusting;
	}

	public void setAdjusting(boolean adjusting) {
		this.adjusting = adjusting;
	}

	public void setSyncAdjusting(boolean adjusting) {
		this.setAdjusting(adjusting);
		for (int i = 0; i < this.syncd.size(); i++) {
			if(((ZoomPanLabel)this.syncd.get(i)).isAdjusting() != adjusting)
				((ZoomPanLabel)this.syncd.get(i)).setSyncAdjusting(adjusting);

		}
	}


	//Synchronized panning to set x,y
	public void pan(int x, int y){
		this.setSyncAdjusting(true);
		getHorizontalScrollBar().setValue(x);
		getVerticalScrollBar().setValue(y);
		for (int i = 0; i < this.syncd.size(); i++) {
			((ZoomPanLabel)this.syncd.get(i)).getHorizontalScrollBar().setValue(x);
			((ZoomPanLabel)this.syncd.get(i)).getVerticalScrollBar().setValue(y);
		}
	}

	class MousePanner extends MouseAdapter {
		Rectangle rect;
		Point p;

		MousePanner() {  }

		public void mousePressed(MouseEvent e) { if (SwingUtilities.isRightMouseButton(e)) {
			this.p = e.getPoint();
			this.rect = ZoomPanLabel.this.me.getVisibleRect();
		} }

		public void mouseDragged(MouseEvent e)
		{
			if (SwingUtilities.isRightMouseButton(e)) {
				Point current = e.getPoint();
				this.rect.x += this.p.x - current.x;
				this.rect.y += this.p.y - current.y;        
				for (int i = 0; i < ZoomPanLabel.this.syncd.size(); i++) {
					((ZoomPanLabel)ZoomPanLabel.this.syncd.get(i)).getLabel().scrollRectToVisible(this.rect);
				}
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						Rectangle newRect = ZoomPanLabel.this.label.getVisibleRect();
						ZoomPanLabel.MousePanner.this.p.x += newRect.x - ZoomPanLabel.MousePanner.this.rect.x;
						ZoomPanLabel.MousePanner.this.p.y += newRect.y - ZoomPanLabel.MousePanner.this.rect.y;
						ZoomPanLabel.MousePanner.this.rect = newRect; }  } );
			}
		}
	}
	class Synchronizer implements AdjustmentListener { JScrollBar v1;
	JScrollBar h1;
	JScrollBar v2;
	JScrollBar h2;

	public Synchronizer(JScrollPane sp1, JScrollPane sp2) { this.v1 = sp1.getVerticalScrollBar();
	this.h1 = sp1.getHorizontalScrollBar();
	this.v2 = sp2.getVerticalScrollBar();
	this.h2 = sp2.getHorizontalScrollBar();
	}

	public void adjustmentValueChanged(AdjustmentEvent e)
	{
		if(!e.getValueIsAdjusting() || true){
			JScrollBar scrollBar = (JScrollBar)e.getSource();
			int value = scrollBar.getValue();
			String who = "";
			JScrollBar target = null;
			if (scrollBar == this.v1){
				target = this.v2;
				who = "v1";
			}
			if (scrollBar == this.h1){
				who = "h1";
				target = this.h2;
			}
			if (scrollBar == this.v2){
				target = this.v1;
				who = "v2";
			}
			if (scrollBar == this.h2) {
				target = this.h1;
				who = "h2";
			}
			if(!adjusting){
				target.setValue(value);
			}
		}
	}
	}
}

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.ZoomPanLabel
 * JD-Core Version:    0.6.2
 */