package org.gss.adi;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicArrowButton;

import org.gss.adi.dialogs.ManualCalibrationDialog;
import org.gss.adi.tools.ColorTools;
import org.gss.adi.tools.Measurement;













public class SpatialAnalysisPanel
extends ImagePanel
{
	private static final long serialVersionUID = 1072335486015080775L;
	public static Integer maskedPix;
	private double maskedPixAvg = 0;
	protected Integer[] x = new Integer[2];
	protected Integer[] y = new Integer[2];
	private int numPoints = 0;
	private boolean polygonComplete = false;
	private boolean pathComplete = false;
	private int moving = -1;

	protected JComboBox comboBox;

	private JTextField pixNumDesc;

	private JTextField pixNum;

	private JTextArea txtrIntensity;
	private JTextArea txtrIntensitiesOfColors;
	private JTextField txtAngle;
	private JTextField angle;
	private JButton btnShowTool;
	private JButton btnHideTool;
	private JTextField txtSelectVersionOf;
	private JTextField txtPixelPosition;
	private JTextField txtAdjust;
	private JTextField txtX;
	private JTextField txtY;
	private JTextField startX;
	private JTextField startY;
	private JTextField endY;
	private JTextField endX;
	private JTextField txtColor;
	private JTextField txtRed;
	private JTextField txtGreen;
	private JTextField txtBlue;
	private JTextField txtAverageColor;
	private JTextField redIntens;
	private JTextField greenIntens;
	private JTextField blueIntens;
	private JTextField avgIntens;
	private JTextField lengthArea;
	private JTextField txtLengthArea;
	private JRadioButton RGB;
	JRadioButton HSV;
	JRadioButton masked;
	JRadioButton original;
	JRadioButton enhanced;
	private Entrance entrance;
	private String colorScheme = "RGB";

	DecimalFormat df = new DecimalFormat("###.##");


	private int imgType = 0;
	private boolean show = true;
	private BasicArrowButton sXP;
	private BasicArrowButton sXM;
	private BasicArrowButton sYP;
	private BasicArrowButton sYM;
	private BasicArrowButton eXP;
	private BasicArrowButton eXM;
	private BasicArrowButton eYP;
	private BasicArrowButton eYM;

	//Statics for memory
	private static final SpacialAnalysisMemory memory = new SpacialAnalysisMemory();

	public SpatialAnalysisPanel(Entrance e)
	{
		super(e, false);
		setBorder(null);
		setLayout(null);
		this.entrance = e;
		if (this.entrance.getImage() == null) {
			return;
		} 
		this.label.setImage(this.entrance.getImage());
		setup();
		//this.label.setImage(this.entrance.getImage());
		this.entrance.getMenu().showSaveMeas(true);
		zoomFromMemory(zoomMemory);

		// Load tool after zoom is restored
		this.comboBox.setSelectedIndex(getMemory().getToolType());
		//Load tool points
		this.x = getMemory().getToolX();
		this.y = getMemory().getToolY();
		if(getMemory().getToolType() != 0)
			this.valueChange();
	} 
	public Object[] getInfo() {
		return new Object[] { this.comboBox.getSelectedItem(), this.x, this.y };
	} 

	public void save(){
		// Save zoom
		zoomMemory.setX(this.label.getHorizontalScrollBar().getValue());
		zoomMemory.setY(this.label.getVerticalScrollBar().getValue());
		zoomMemory.setZoomFactor(this.label.zoomFactor);

		// Radio buttons
		if(original.isSelected())
			getMemory().setImgType("Original");
		else if(enhanced.isSelected())
			getMemory().setImgType("Enhanced");
		else
			getMemory().setImgType("Masked");

		if(RGB.isSelected())
			getMemory().setColorType("RGB");
		else
			getMemory().setColorType("HSV");

		// Tool 
		// Type
		getMemory().setToolType(comboBox.getSelectedIndex());

		//Coordinates
		getMemory().setToolX(this.x);
		getMemory().setToolY(this.y);

	}

	private int toolContains(int eX, int eY)
	{
		int i = 0;
		if ((this.comboBox.getSelectedItem().equals("Polygon Tool")) && (!this.polygonComplete)) {return -1;}
		for (i = 0; 

				i < this.x.length; i++) {
			if (ColorTools.linearDist(this.x[i].intValue(), this.y[i].intValue(), eX, eY).doubleValue() < 6.0D) {
				return i;
			} 
		} 
		return -1;
	} 

	public void updatePic() {
		super.updatePic();
		new SpatialAnalysisPanel(this.entrance);
	} 

	public void updateTool() {
		if ((this.x.length > 0) && (this.x[0] != null)) {
			this.label.toolImage(this.x, this.y, this.entrance.getColor(), (String)this.comboBox.getSelectedItem(), this.entrance.getLineWidth(), this.entrance.getCursorStyle());
		} 
	} 

	protected void closingSequence() {
		save();
		System.out.println("Just saved zoom factor " + zoomMemory.getZoomFactor() + " SpatialAnalysis CLOSING SEQ");
	}

	byte getType() {
		return 0;
	} 

	void slide() {
		super.slide();
		if (this.show) {
			updateTool();
		} 
	} 
	void slideComplete() {
		super.slideComplete();
		if (this.show) {
			updateTool();
		} 
	} 

	public String getTool()
	{
		return (String)this.comboBox.getSelectedItem();
	} 

	public String getColorScheme()
	{
		return this.colorScheme;
	} 


	private void valueChange()
	{
		String tool = (String)this.comboBox.getSelectedItem();
		this.label.toolImage(this.x, this.y, this.entrance.getColor(), tool, this.entrance.getLineWidth(), this.entrance.getCursorStyle());
		DecimalFormat df = new DecimalFormat("###.##");
		Double d;
		if ((!tool.contains("Select")) && ((!tool.equals("Polygon Tool")) || (this.polygonComplete)))
			if (this.RGB.isSelected()) {
				Double[] rgb = ColorTools.rgbPercent(ColorTools.pixelAvg(this.label.getOriginal(), this.x, this.y, tool));
				Double avg = Double.valueOf((rgb[0].doubleValue() + rgb[1].doubleValue() + rgb[2].doubleValue()) / 3.0D);
				this.maskedPixAvg = avg;
				this.redIntens.setText(df.format(rgb[0]));
				this.greenIntens.setText(df.format(rgb[1]));
				this.blueIntens.setText(df.format(rgb[2]));
				this.avgIntens.setText(df.format(avg));
			} else {
				int[] rgb = ColorTools.pixelAvg(this.label.getOriginal(), this.x, this.y, tool);
				float[] hsv = Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], null);
				this.redIntens.setText(df.format(hsv[0] * 100.0F));
				this.greenIntens.setText(df.format(hsv[1] * 100.0F));
				this.blueIntens.setText(df.format(hsv[2] * 100.0F));
			} 
		if (tool.equals("Line Tool")) {
			if(!this.masked.isSelected()){
				d = (double)ColorTools.getLinePixels(this.x, this.y).size();
			}
			else{
				d = Math.floor((double)(ColorTools.getLinePixels(this.x, this.y).size()*(100-this.maskedPixAvg)/100.0D));
			}
			this.pixNum.setText(df.format(d));
			Measurement m = this.entrance.getMeasurement();
			if (m != null)
				this.lengthArea.setText(m.measure(d, true)); 
		} else if (tool.equals("Rectangle Tool")) {
			if(!this.masked.isSelected()){
				d = new Double(Math.abs(this.x[0].intValue() - this.x[1].intValue()) * Math.abs(this.y[0].intValue() - this.y[1].intValue()));
			}
			else{
				d = Math.floor((double)(Math.abs(this.x[0].intValue() - this.x[1].intValue()) * Math.abs(this.y[0].intValue() - this.y[1].intValue())*(100-this.maskedPixAvg)/100.0D));
			}
			this.pixNum.setText(d.toString());
			Measurement m = this.entrance.getMeasurement();
			if (m != null)
				this.lengthArea.setText(m.measure(d, false)); 
		} else if ((tool.equals("Path Tool (multiple points)")) && (this.x.length > 1)) {
			if(!this.masked.isSelected()){
				d = ColorTools.pathDist(this.x, this.y);
			}
			else{
				d = Math.floor((double)((ColorTools.pathDist(this.x, this.y))*(100-this.maskedPixAvg)/100.0D));
				
			}
			this.pixNum.setText(df.format(d));
			Measurement m = this.entrance.getMeasurement();
			if (m != null)
				this.lengthArea.setText(m.measure(d, true)); 
		} else if ((tool.equals("Polygon Tool")) && (this.polygonComplete)) {
			if(!this.masked.isSelected()){
				d = new Double(ColorTools.polyArea(this.x, this.y).intValue());
			}
			else{
				d =  Math.floor((double)((ColorTools.polyArea(this.x, this.y).intValue())*(100-this.maskedPixAvg)/100.0D));
			}
			this.pixNum.setText(d.toString());
			Measurement m = this.entrance.getMeasurement();
			if (m != null)
				this.lengthArea.setText(m.measure(d, false)); 
		} 
		 
		try {
			setPt1(this.x[0].intValue(), this.y[0].intValue()); } catch (Exception localException) {}
		try { setPt2(this.x[1].intValue(), this.y[1].intValue());
		}
		catch (Exception localException1) {}
	} 

	private void changeTool()
	{
		this.polygonComplete = false;
		this.pathComplete = false;
		this.label.setZoomedTool(this.label.getZoomedOriginal());
		this.angle.setText("");
		this.pixNum.setText("");
		this.redIntens.setText("");
		this.greenIntens.setText("");
		this.blueIntens.setText("");
		this.avgIntens.setText("");
		this.lengthArea.setText("");

		this.btnShowTool.setVisible(false);
		this.btnHideTool.setVisible(true);
		String s = (String)this.comboBox.getSelectedItem();
		this.x = new Integer[2];
		this.y = new Integer[2];
		this.txtAngle.setVisible(false);
		this.angle.setVisible(false);
		this.pixNum.setVisible(false);
		this.pixNumDesc.setVisible(false);
		this.txtPixelPosition.setVisible(false);
		this.txtAdjust.setVisible(false);
		this.txtX.setVisible(false);
		this.txtY.setVisible(false);
		this.startX.setVisible(false);
		this.startY.setVisible(false);
		this.endX.setVisible(false);
		this.endY.setVisible(false);
		this.txtColor.setVisible(false);
		this.txtrIntensity.setVisible(false);
		this.txtRed.setVisible(false);
		this.redIntens.setVisible(false);
		this.txtGreen.setVisible(false);
		this.greenIntens.setVisible(false);
		this.txtBlue.setVisible(false);
		this.blueIntens.setVisible(false);
		this.txtAverageColor.setVisible(false);
		this.avgIntens.setVisible(false);
		this.txtrIntensitiesOfColors.setVisible(false);
		this.lengthArea.setVisible(false);
		this.txtLengthArea.setVisible(false);

		this.sXP.setVisible(false);
		this.sXM.setVisible(false);
		this.eXP.setVisible(false);
		this.eXM.setVisible(false);
		this.sYP.setVisible(false);
		this.sYM.setVisible(false);
		this.eYP.setVisible(false);
		this.eYM.setVisible(false);

		if (!s.equals("Select Measurement Tool"))
		{
			if (s.equals("Pixel Tool")) {
				this.txtPixelPosition.setVisible(true);
				this.txtAdjust.setVisible(true);
				this.txtX.setVisible(true);
				this.txtY.setVisible(true);
				this.startX.setVisible(true);
				this.startY.setVisible(true);
				this.sXP.setVisible(true);
				this.sXM.setVisible(true);
				this.sYP.setVisible(true);
				this.sYM.setVisible(true);
				this.txtColor.setVisible(true);
				this.txtrIntensity.setVisible(true);
				this.txtRed.setVisible(true);
				this.redIntens.setVisible(true);
				this.txtGreen.setVisible(true);
				this.greenIntens.setVisible(true);
				this.txtBlue.setVisible(true);
				this.blueIntens.setVisible(true);
				this.txtAverageColor.setVisible(true);
				this.avgIntens.setVisible(true);
				this.txtrIntensitiesOfColors.setVisible(true);
			} else if (s.equals("Angle Tool")) {
				this.x = new Integer[] { Integer.valueOf(-1), Integer.valueOf(-1), Integer.valueOf(-1) };
				this.y = new Integer[] { Integer.valueOf(-1), Integer.valueOf(-1), Integer.valueOf(-1) };
				this.numPoints = 0;
				this.txtAngle.setVisible(true);
			} else if (s.equals("Path Tool (multiple points)")) {
				this.x = new Integer[0];
				this.y = new Integer[0];
				this.numPoints = 0;
				this.pixNum.setVisible(true);
				this.pixNumDesc.setVisible(true);
				this.txtColor.setVisible(true);
				this.txtrIntensity.setVisible(true);
				this.txtRed.setVisible(true);
				this.redIntens.setVisible(true);
				this.txtGreen.setVisible(true);
				this.greenIntens.setVisible(true);
				this.txtBlue.setVisible(true);
				this.blueIntens.setVisible(true);
				this.txtAverageColor.setVisible(true);
				this.avgIntens.setVisible(true);
				this.txtrIntensitiesOfColors.setVisible(true);
				if (this.entrance.getMeasurement() != null) {
					this.lengthArea.setVisible(true);
					this.txtLengthArea.setVisible(true);
					this.txtLengthArea.setText("Length:");
				} 
			} else if (s.equals("Polygon Tool")) {
				this.x = new Integer[0];
				this.y = new Integer[0];
				this.numPoints = 0;
				this.pixNum.setVisible(true);
				this.pixNumDesc.setVisible(true);
				this.txtColor.setVisible(true);
				this.txtrIntensity.setVisible(true);
				this.txtRed.setVisible(true);
				this.redIntens.setVisible(true);
				this.txtGreen.setVisible(true);
				this.greenIntens.setVisible(true);
				this.txtBlue.setVisible(true);
				this.blueIntens.setVisible(true);
				this.txtAverageColor.setVisible(true);
				this.avgIntens.setVisible(true);
				this.txtrIntensitiesOfColors.setVisible(true);
				if (this.entrance.getMeasurement() != null) {
					this.lengthArea.setVisible(true);
					this.txtLengthArea.setVisible(true);
					if(this.masked.isSelected()){
						this.txtLengthArea.setText("Masked area:");
						this.pixNumDesc.setText("# of Masked Pixels");
					}
					else{
						this.txtLengthArea.setText("Area:");
						this.pixNumDesc.setText("# of Pixels");
					}
				} 
			} else {
				this.pixNum.setVisible(true);
				this.pixNumDesc.setVisible(true);
				this.txtPixelPosition.setVisible(true);
				this.txtAdjust.setVisible(true);
				this.txtX.setVisible(true);
				this.txtY.setVisible(true);
				this.startX.setVisible(true);
				this.startY.setVisible(true);
				this.endX.setVisible(true);
				this.endY.setVisible(true);
				this.txtColor.setVisible(true);
				this.txtrIntensity.setVisible(true);
				this.txtRed.setVisible(true);
				this.redIntens.setVisible(true);
				this.txtGreen.setVisible(true);
				this.greenIntens.setVisible(true);
				this.txtBlue.setVisible(true);
				this.blueIntens.setVisible(true);
				this.txtAverageColor.setVisible(true);
				this.avgIntens.setVisible(true);
				this.txtrIntensitiesOfColors.setVisible(true);

				this.sXP.setVisible(true);
				this.sXM.setVisible(true);
				this.eXP.setVisible(true);
				this.eXM.setVisible(true);
				this.sYP.setVisible(true);
				this.sYM.setVisible(true);
				this.eYP.setVisible(true);
				this.eYM.setVisible(true);
			}  } 
		if (s.contains("Line")) {
			if (this.entrance.getMeasurement() != null) {
				this.lengthArea.setVisible(true);
				this.txtLengthArea.setVisible(true);
				this.txtLengthArea.setText("Length:");
			} 
		} else if ((s.contains("Rectangle")) && 
				(this.entrance.getMeasurement() != null)) {
			this.lengthArea.setVisible(true);
			this.txtLengthArea.setVisible(true);
			if(this.masked.isSelected()){
				this.txtLengthArea.setText("Masked area:");
				this.pixNumDesc.setText("# of Masked Pixels");
			}
			else{
				this.txtLengthArea.setText("Area:");
				this.pixNumDesc.setText("# of Pixels");
			}
		} 
	} 



	private void eraseTool()
	{
		changeTool();
	} 


	private void hideTool()
	{
		this.label.setZoomedTool(this.label.getZoomedOriginal());
		this.btnHideTool.setVisible(false);
		this.btnShowTool.setVisible(true);
		this.show = false;
	} 

	private void showTool()
	{
		try {
			this.label.toolImage(this.x, this.y, this.entrance.getColor(), (String)this.comboBox.getSelectedItem(), this.entrance.getLineWidth(), this.entrance.getCursorStyle());
		} catch (Exception localException) {}
		this.btnHideTool.setVisible(true);
		this.btnShowTool.setVisible(false);
		this.show = true;
	} 
	public void updatePixSize() {
		this.lengthArea.setVisible(true);
		this.txtLengthArea.setVisible(true);
		String tool = (String)this.comboBox.getSelectedItem();
		if (this.x[1] != null) {
			if (tool.equals("Line Tool")) {
				Double d = (double)ColorTools.getLinePixels(this.x, this.y).size();
				this.pixNum.setText(this.df.format(d));
				Measurement m = this.entrance.getMeasurement();
				if (m != null)
					this.lengthArea.setText(m.measure(d, true)); 
			} else if (tool.equals("Rectangle Tool")) {
				Double d = new Double(Math.abs(this.x[0].intValue() - this.x[1].intValue()) * Math.abs(this.y[0].intValue() - this.y[1].intValue()));
				this.pixNum.setText(d.toString());
				Measurement m = this.entrance.getMeasurement();
				if (m != null)
					this.lengthArea.setText(m.measure(d, false)); 
			} else if ((tool.equals("Path Tool (multiple points)")) && (this.x.length > 1)) {
				Double d = ColorTools.pathDist(this.x, this.y);
				this.pixNum.setText(this.df.format(d));
				Measurement m = this.entrance.getMeasurement();
				if (m != null)
					this.lengthArea.setText(m.measure(d, true)); 
			} else if ((tool.equals("Polygon Tool")) && (this.polygonComplete)) {
				Double d = new Double(ColorTools.polyArea(this.x, this.y).intValue());
				this.pixNum.setText(d.toString());
				Measurement m = this.entrance.getMeasurement();
				if (m != null) {
					this.lengthArea.setText(m.measure(d, false));
				} 
			} 
		} 
	} 

	private void RGBorHSV(boolean b) {
		if (b) {
			this.txtRed.setText("Red (%)");
			this.txtGreen.setText("Green (%)");
			this.txtBlue.setText("Blue (%)");
			this.txtAverageColor.setText("Average Color (%)");
			this.txtColor.setText("Color");
			this.colorScheme = "RGB";
		} else {
			this.txtRed.setText("Hue");
			this.txtGreen.setText("Saturation");
			this.txtBlue.setText("Value");
			this.txtAverageColor.setText("");
			this.txtColor.setText("");
			this.colorScheme = "HSV";
		} 
	} 


	private void mousePress(MouseEvent e)
	{
		String tool = (String)this.comboBox.getSelectedItem();
		Point p = this.label.mapToPixel(e.getX(), e.getY());
		int eX = p.x;
		int eY = p.y;
		this.btnShowTool.setVisible(false);
		this.btnHideTool.setVisible(true);
		if ((this.x.length != 0) && (this.x[0] != null)) {
			this.moving = toolContains(eX, eY);
		} 
		if (this.moving > -1) {
			this.x[this.moving] = Integer.valueOf(eX);
			this.y[this.moving] = Integer.valueOf(eY);
			if ((this.polygonComplete) && (this.moving == 0)) {
				this.x[(this.x.length - 1)] = Integer.valueOf(eX);
				this.y[(this.y.length - 1)] = Integer.valueOf(eY);
			} 
		}
		else if (tool.equals("Path Tool (multiple points)")) {
			if (this.pathComplete) {
				this.x = new Integer[] { Integer.valueOf(eX) };
				this.y = new Integer[] { Integer.valueOf(eY) };
				this.pathComplete = false;
			} else {
				Integer[] tempX = new Integer[this.x.length + 1];
				Integer[] tempY = new Integer[this.y.length + 1];
				for (int i = 0; 
						i < this.x.length; i++) {
					tempX[i] = this.x[i];
					tempY[i] = this.y[i];
				} 
				tempX[this.y.length] = Integer.valueOf(eX);
				tempY[this.y.length] = Integer.valueOf(eY);
				this.x = tempX;
				this.y = tempY;
			} 
		} else if (tool.equals("Polygon Tool")) {
			if ((this.x.length > 1) && (this.x[0] == this.x[(this.x.length - 1)])) {
				this.x = new Integer[] { Integer.valueOf(eX) };
				this.y = new Integer[] { Integer.valueOf(eY) };
				this.polygonComplete = false;
				this.angle.setText("");
				this.pixNum.setText("");
				this.lengthArea.setText("");
				this.redIntens.setText("");
				this.greenIntens.setText("");
				this.blueIntens.setText("");
				this.avgIntens.setText("");
			} else {
				Integer[] tempX = new Integer[this.x.length + 1];
				Integer[] tempY = new Integer[this.y.length + 1];
				for (int i = 0; 
						i < this.x.length; i++) {
					tempX[i] = this.x[i];
					tempY[i] = this.y[i];
				} 
				tempX[this.y.length] = Integer.valueOf(eX);
				tempY[this.y.length] = Integer.valueOf(eY);
				this.x = tempX;
				this.y = tempY;
			} 
		} else if (tool.equals("Angle Tool")) {
			switch (this.numPoints) {
			case 0: 
				this.angle.setVisible(false);
				this.x[0] = Integer.valueOf(eX);
				this.y[0] = Integer.valueOf(eY);
				this.x[1] = Integer.valueOf(-1);
				this.y[1] = Integer.valueOf(-1);
				this.x[2] = Integer.valueOf(-1);
				this.y[2] = Integer.valueOf(-1);
				break;
			case 1: 
				this.x[1] = Integer.valueOf(eX);
				this.y[1] = Integer.valueOf(eY);
				break;
			case 2: 
				this.x[2] = Integer.valueOf(eX);
				this.y[2] = Integer.valueOf(eY);
				this.angle.setVisible(true);
				DecimalFormat df = new DecimalFormat("###.##");
				this.angle.setText(df.format(ColorTools.angle(this.x, this.y)));
			default: 
				break; } 
		} else {
			this.x[0] = Integer.valueOf(eX);
			this.y[0] = Integer.valueOf(eY);
			this.x[1] = Integer.valueOf(eX);
			this.y[1] = Integer.valueOf(eY);
		} 
		try
		{
			this.label.toolImage(this.x, this.y, this.entrance.getColor(), tool, this.entrance.getLineWidth(), this.entrance.getCursorStyle());
		} catch (Exception e1) { e1.printStackTrace();
		} 
		if (tool.equals("Line Tool")) {
			Double d = (double)ColorTools.getLinePixels(this.x, this.y).size();this.pixNum.setText(this.df.format(d));
			Measurement m = this.entrance.getMeasurement();
			if (m != null)
				this.lengthArea.setText(m.measure(d, true)); 
		} else if (tool.equals("Rectangle Tool")) {
			this.pixNum.setText(new Integer(Math.abs(this.x[0].intValue() - this.x[1].intValue()) * Math.abs(this.y[0].intValue() - this.y[1].intValue())).toString());
		} else if ((tool.equals("Path Tool (multiple points)")) && (this.x.length > 1)) {
			this.pixNum.setText(this.df.format(ColorTools.pathDist(this.x, this.y)));
		} else if ((tool.equals("Polygon Tool")) && (this.polygonComplete)) {
			this.pixNum.setText(ColorTools.polyArea(this.x, this.y).toString());
		} 
		if ((!tool.contains("Select")) && ((!tool.equals("Polygon Tool")) || (this.polygonComplete))) { int[] rgbi;
		if (this.masked.isSelected()) {
			rgbi = ColorTools.maskedPixelAverage(this.label.getOriginal(), this.entrance.getImage(), this.x, this.y, tool);
		} else {
			rgbi = ColorTools.pixelAvg(this.label.getOriginal(), this.x, this.y, tool);
		} 
		if (this.RGB.isSelected()) {
			Double[] rgb = ColorTools.rgbPercent(rgbi);
			Double avg = Double.valueOf((rgb[0].doubleValue() + rgb[1].doubleValue() + rgb[2].doubleValue()) / 3.0D);
			//this.maskedPixAvg = avg;
			this.redIntens.setText(this.df.format(rgb[0]));
			this.greenIntens.setText(this.df.format(rgb[1]));
			this.blueIntens.setText(this.df.format(rgb[2]));
			this.avgIntens.setText(this.df.format(avg));
		} else {
			float[] hsv = Color.RGBtoHSB(rgbi[0], rgbi[1], rgbi[2], null);
			this.redIntens.setText(this.df.format(hsv[0] * 100.0F));
			this.greenIntens.setText(this.df.format(hsv[1] * 100.0F));
			this.blueIntens.setText(this.df.format(hsv[2] * 100.0F));
		} 
		} 
		if ((tool.equals("Pixel Tool")) || (tool.equals("Line Tool")) || (tool.equals("Rectangle Tool")))
			setPt1(this.x[0].intValue(), this.y[0].intValue()); 
		if ((tool.equals("Line Tool")) || (tool.equals("Rectangle Tool"))) {
			setPt2(this.x[1].intValue(), this.y[1].intValue());
		} 
	} 

	private void mouseRelease(MouseEvent e)
	{
		String tool = (String)this.comboBox.getSelectedItem();
		Point p = this.label.mapToPixel(e.getX(), e.getY());
		int eX = p.x;
		int eY = p.y;
		if (eX < 0) {
			eX = 0;
		} else if (eX >= this.label.getOriginal().getWidth())
			eX = this.label.getOriginal().getWidth() - 1; 
		if (eY < 0) {
			eY = 0;
		} else if (eY >= this.label.getOriginal().getHeight())
			eY = this.label.getOriginal().getHeight() - 1; 
		if (this.moving > -1) {
			this.x[this.moving] = Integer.valueOf(eX);
			this.y[this.moving] = Integer.valueOf(eY);
			if ((this.polygonComplete) && (this.moving == 0)) {
				this.x[(this.x.length - 1)] = this.x[0];
				this.y[(this.y.length - 1)] = this.y[0];
			} 
		}
		else if (tool.equals("Pixel Tool")) {
			this.x[0] = Integer.valueOf(eX);
			this.y[0] = Integer.valueOf(eY);
		} else if (tool.equals("Angle Tool")) {
			switch (this.numPoints) {
			case 0: 
				this.x[0] = Integer.valueOf(eX);
				this.y[0] = Integer.valueOf(eY);
				this.x[1] = Integer.valueOf(-1);
				this.y[1] = Integer.valueOf(-1);
				this.x[2] = Integer.valueOf(-1);
				this.y[2] = Integer.valueOf(-1);
				this.numPoints += 1;
				break;
			case 1: 
				this.x[1] = Integer.valueOf(eX);
				this.y[1] = Integer.valueOf(eY);
				this.numPoints += 1;
				break;
			case 2: 
				this.x[2] = Integer.valueOf(eX);
				this.y[2] = Integer.valueOf(eY);
				DecimalFormat df = new DecimalFormat("###.##");
				this.angle.setText(df.format(ColorTools.angle(this.x, this.y)));
				this.numPoints = 0;
			default: 
				break; } 
		} else if (tool.equals("Path Tool (multiple points)")) {
			this.x[(this.x.length - 1)] = Integer.valueOf(eX);
			this.y[(this.y.length - 1)] = Integer.valueOf(eY);
		} else if (tool.equals("Polygon Tool")) {
			if ((ColorTools.linearDist(this.x[0].intValue(), this.y[0].intValue(), eX, eY).doubleValue() < 6.0D) && (this.x.length > 1)) {
				this.x[(this.x.length - 1)] = this.x[0];
				this.y[(this.y.length - 1)] = this.y[0];
				this.polygonComplete = true;
			} else {
				this.x[(this.x.length - 1)] = Integer.valueOf(eX);
				this.y[(this.y.length - 1)] = Integer.valueOf(eY);
			} 
		} else {
			this.x[1] = Integer.valueOf(eX);
			this.y[1] = Integer.valueOf(eY);
		} 

		DecimalFormat df = new DecimalFormat("###.##");
		if (tool.equals("Line Tool")) {
			Double d = (double)ColorTools.getLinePixels(this.x, this.y).size();
			this.pixNum.setText(df.format(d));
			Measurement m = this.entrance.getMeasurement();
			if (m != null)
				this.lengthArea.setText(m.measure(d, true)); 
		} else if (tool.equals("Rectangle Tool")) {
			this.pixNum.setText(new Integer(Math.abs(this.x[0].intValue() - this.x[1].intValue()) * Math.abs(this.y[0].intValue() - this.y[1].intValue())).toString());
		} else if ((tool.equals("Path Tool (multiple points)")) && (this.x.length > 1)) {
			this.pixNum.setText(df.format(ColorTools.pathDist(this.x, this.y)));
		} else if ((tool.equals("Polygon Tool")) && (this.polygonComplete)) {
			this.pixNum.setText(ColorTools.polyArea(this.x, this.y).toString());
		} 
		if ((!tool.contains("Select")) && ((!tool.equals("Polygon Tool")) || (this.polygonComplete))) { int[] rgbi;
		if (this.masked.isSelected()) {
			rgbi = ColorTools.maskedPixelAverage(this.label.getOriginal(), this.entrance.getImage(), this.x, this.y, tool);
		} else {
			rgbi = ColorTools.pixelAvg(this.label.getOriginal(), this.x, this.y, tool);
		} 
		if (this.RGB.isSelected()) {
			Double[] rgb = ColorTools.rgbPercent(rgbi);
			Double avg = Double.valueOf((rgb[0].doubleValue() + rgb[1].doubleValue() + rgb[2].doubleValue()) / 3.0D);
			//this.maskedPixAvg = avg;
			this.redIntens.setText(df.format(rgb[0]));
			this.greenIntens.setText(df.format(rgb[1]));
			this.blueIntens.setText(df.format(rgb[2]));
			this.avgIntens.setText(df.format(avg));
		} else {
			float[] hsv = Color.RGBtoHSB(rgbi[0], rgbi[1], rgbi[2], null);
			this.redIntens.setText(df.format(hsv[0] * 100.0F));
			this.greenIntens.setText(df.format(hsv[1] * 100.0F));
			this.blueIntens.setText(df.format(hsv[2] * 100.0F));
		} 
		} 
		if ((tool.equals("Pixel Tool")) || (tool.equals("Line Tool")) || (tool.equals("Rectangle Tool")))
			setPt1(this.x[0].intValue(), this.y[0].intValue()); 
		if ((tool.equals("Line Tool")) || (tool.equals("Rectangle Tool")))
			setPt2(this.x[1].intValue(), this.y[1].intValue()); 
		if ((tool.equals("Polygon Tool")) && (this.polygonComplete)) {
			Double d = new Double(ColorTools.polyArea(this.x, this.y).intValue());
			this.pixNum.setText(d.toString());
			Measurement m = this.entrance.getMeasurement();
			if (m != null)
				this.lengthArea.setText(m.measure(d, false)); 
		} 
		this.label.toolImage(this.x, this.y, this.entrance.getColor(), tool, this.entrance.getLineWidth(), this.entrance.getCursorStyle());
		this.moving = -1;
		valueChange();
	} 


	private void mouseDrag(MouseEvent e)
	{
		String tool = (String)this.comboBox.getSelectedItem();
		Point p = this.label.mapToPixel(e.getX(), e.getY());
		int eX = p.x;
		int eY = p.y;
		if (eX < 0) {
			eX = 0;
		} else if (eX >= this.label.getOriginal().getWidth())
			eX = this.label.getOriginal().getWidth() - 1; 
		if (eY < 0) {
			eY = 0;
		} else if (eY >= this.label.getOriginal().getHeight())
			eY = this.label.getOriginal().getHeight() - 1; 
		if (this.moving > -1) {
			this.x[this.moving] = Integer.valueOf(eX);
			this.y[this.moving] = Integer.valueOf(eY);
			if ((this.polygonComplete) && (this.moving == 0)) {
				this.x[(this.x.length - 1)] = this.x[0];
				this.y[(this.y.length - 1)] = this.y[0];
			} 
			if ((this.x.length == 3) && (this.x[2].intValue() != -1)) {
				DecimalFormat df = new DecimalFormat("###.##");
				this.angle.setText(df.format(ColorTools.angle(this.x, this.y)));
			} 
		}
		else if (tool.equals("Pixel Tool")) {
			this.x[0] = Integer.valueOf(eX);
			this.y[0] = Integer.valueOf(eY);
			this.startX.setText("" + this.x[0]);
			this.startY.setText("" + this.y[0]);
		} else if (tool.equals("Angle Tool")) {
			switch (this.numPoints) {
			case 0: 
				this.x[0] = Integer.valueOf(eX);
				this.y[0] = Integer.valueOf(eY);
				this.x[1] = Integer.valueOf(-1);
				this.y[1] = Integer.valueOf(-1);
				this.x[2] = Integer.valueOf(-1);
				this.y[2] = Integer.valueOf(-1);
				break;
			case 1: 
				this.x[1] = Integer.valueOf(eX);
				this.y[1] = Integer.valueOf(eY);
				break;
			case 2: 
				this.x[2] = Integer.valueOf(eX);
				this.y[2] = Integer.valueOf(eY);
				DecimalFormat df = new DecimalFormat("###.##");
				this.angle.setText(df.format(ColorTools.angle(this.x, this.y)));
			default: 
				break; } 
		} else if ((tool.equals("Path Tool (multiple points)")) || (tool.equals("Polygon Tool"))) {
			this.x[(this.x.length - 1)] = Integer.valueOf(eX);
			this.y[(this.y.length - 1)] = Integer.valueOf(eY);
		} else {
			this.x[1] = Integer.valueOf(eX);
			this.y[1] = Integer.valueOf(eY);
		} 

		DecimalFormat df = new DecimalFormat("###.##");
		if (tool.equals("Line Tool")) {
			Double d = (double)ColorTools.getLinePixels(this.x, this.y).size();
			this.pixNum.setText(df.format(d));
			Measurement m = this.entrance.getMeasurement();
			if (m != null)
				this.lengthArea.setText(m.measure(d, true)); 
		} else if (tool.equals("Rectangle Tool")) {
			Double d = new Double(Math.abs(this.x[0].intValue() - this.x[1].intValue()) * Math.abs(this.y[0].intValue() - this.y[1].intValue()));
			this.pixNum.setText(d.toString());
			Measurement m = this.entrance.getMeasurement();
			if (m != null)
				this.lengthArea.setText(m.measure(d, false)); 
		} else if ((tool.equals("Path Tool (multiple points)")) && (this.x.length > 1)) {
			Double d = ColorTools.pathDist(this.x, this.y);
			this.pixNum.setText(df.format(d));
			Measurement m = this.entrance.getMeasurement();
			if (m != null)
				this.lengthArea.setText(m.measure(d, true)); 
		} else if ((tool.equals("Polygon Tool")) && (this.polygonComplete)) {
			Double d = new Double(ColorTools.polyArea(this.x, this.y).intValue());
			this.pixNum.setText(d.toString());
			Measurement m = this.entrance.getMeasurement();
			if (m != null)
				this.lengthArea.setText(m.measure(d, false)); 
		} 
		if ((!tool.contains("Select")) && ((!tool.equals("Polygon Tool")) || (this.polygonComplete))) { int[] rgbi;
		if (this.masked.isSelected()) {
			rgbi = ColorTools.maskedPixelAverage(this.label.getOriginal(), this.entrance.getImage(), this.x, this.y, tool);
		} else {
			rgbi = ColorTools.pixelAvg(this.label.getOriginal(), this.x, this.y, tool);
		} 
		if (this.RGB.isSelected()) {
			Double[] rgb = ColorTools.rgbPercent(rgbi);
			Double avg = Double.valueOf((rgb[0].doubleValue() + rgb[1].doubleValue() + rgb[2].doubleValue()) / 3.0D);
			this.redIntens.setText(df.format(rgb[0]));
			this.greenIntens.setText(df.format(rgb[1]));
			this.blueIntens.setText(df.format(rgb[2]));
			this.avgIntens.setText(df.format(avg));
		} else {
			float[] hsv = Color.RGBtoHSB(rgbi[0], rgbi[1], rgbi[2], null);
			this.redIntens.setText(df.format(hsv[0] * 100.0F));
			this.greenIntens.setText(df.format(hsv[1] * 100.0F));
			this.blueIntens.setText(df.format(hsv[2] * 100.0F));
			this.avgIntens.setText("");
		} 
		} 
		if ((tool.equals("Pixel Tool")) || (tool.equals("Line Tool")) || (tool.equals("Rectangle Tool")))
			setPt1(this.x[0].intValue(), this.y[0].intValue()); 
		if ((tool.equals("Line Tool")) || (tool.equals("Rectangle Tool")))
			setPt2(this.x[1].intValue(), this.y[1].intValue()); 
		this.label.toolImage(this.x, this.y, this.entrance.getColor(), tool, this.entrance.getLineWidth(), this.entrance.getCursorStyle());
	} 



	private void setPt1(int x, int y)
	{
		this.startX.setText("" + Integer.valueOf(x));
		this.startY.setText("" + Integer.valueOf(y));
	} 

	private void setPt2(int x, int y)
	{
		try
		{
			this.endX.setText("" + Integer.valueOf(x));
			this.endY.setText("" + Integer.valueOf(y));
		} catch (Exception localException) {}
	} 
	
	private void setMaskedText(){
		if(this.masked.isSelected()){
			this.txtLengthArea.setText("Masked area:");
			this.pixNumDesc.setText("# of Masked Pixels");
		}
		else{
			this.txtLengthArea.setText("Area:");
			this.pixNumDesc.setText("# of Pixels");
		}
	}
	
	private void setup() {
		JTextArea txtrSpatialToolsMeasure = new JTextArea();
		txtrSpatialToolsMeasure.setEditable(false);
		txtrSpatialToolsMeasure.setFont(new Font("SansSerif", 1, 14));
		txtrSpatialToolsMeasure.setText("Spatial tools measure the color and size of features in digital images.  ");
		txtrSpatialToolsMeasure.setWrapStyleWord(true);
		txtrSpatialToolsMeasure.setLineWrap(true);
		txtrSpatialToolsMeasure.setOpaque(false);
		txtrSpatialToolsMeasure.setBounds(10, 11, 270, 45);
		add(txtrSpatialToolsMeasure);

		this.txtSelectVersionOf = new JTextField();
		this.txtSelectVersionOf.setBorder(null);
		this.txtSelectVersionOf.setEditable(false);
		this.txtSelectVersionOf.setFont(new Font("Tahoma", 2, 11));
		this.txtSelectVersionOf.setText("Select Version of Image to View and Analyze");
		this.txtSelectVersionOf.setBounds(20, 67, 222, 20);
		this.txtSelectVersionOf.setOpaque(false);
		add(this.txtSelectVersionOf);
		this.txtSelectVersionOf.setColumns(10);

		this.original = new JRadioButton("Original");
		this.original.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) {
				SpatialAnalysisPanel.this.save();
				SpatialAnalysisPanel.this.label.setImage(SpatialAnalysisPanel.this.entrance.getImage());
				zoomFromMemory(memory);
				SpatialAnalysisPanel.this.imgType = 0;
				setMaskedText();
				valueChange();
				//SpatialAnalysisPanel.this.zoomFromMemory(SpatialAnalysisPanel.zoomMemory);
				try {
					SpatialAnalysisPanel.this.label.toolImage(SpatialAnalysisPanel.this.x, SpatialAnalysisPanel.this.y, SpatialAnalysisPanel.this.entrance.getColor(), (String)SpatialAnalysisPanel.this.comboBox.getSelectedItem(), SpatialAnalysisPanel.this.entrance.getLineWidth(), SpatialAnalysisPanel.this.entrance.getCursorStyle());
				} catch (Exception localException) {}
			} 
		});
		this.original.setSelected(false);
		this.original.setFont(new Font("Tahoma", 0, 11));
		this.original.setBounds(10, 94, 76, 23);
		add(this.original);

		this.enhanced = new JRadioButton("Enhanced");
		this.enhanced.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) {
				SpatialAnalysisPanel.this.save();
				BufferedImage img = SpatialAnalysisPanel.this.entrance.getEnhancedImage();
				if (img == null) {
					if (SpatialAnalysisPanel.this.imgType == 0) {
						SpatialAnalysisPanel.this.original.setSelected(true);
					} else
						SpatialAnalysisPanel.this.masked.setSelected(true); 
				} else {
					SpatialAnalysisPanel.this.label.setImage(img);
					zoomFromMemory(memory);
					SpatialAnalysisPanel.this.imgType = 1;
				} 
				setMaskedText();
				valueChange();
				//SpatialAnalysisPanel.this.zoomFromMemory(SpatialAnalysisPanel.zoomMemory);
				try {
					SpatialAnalysisPanel.this.label.toolImage(SpatialAnalysisPanel.this.x, SpatialAnalysisPanel.this.y, SpatialAnalysisPanel.this.entrance.getColor(), (String)SpatialAnalysisPanel.this.comboBox.getSelectedItem(), SpatialAnalysisPanel.this.entrance.getLineWidth(), SpatialAnalysisPanel.this.entrance.getCursorStyle());
				} catch (Exception localException) {}
			} 
		});
		this.enhanced.setFont(new Font("Tahoma", 0, 11));
		this.enhanced.setBounds(88, 94, 91, 23);
		this.enhanced.setSelected(false);
		add(this.enhanced);

		this.masked = new JRadioButton("Masked");
		this.masked.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) {
				SpatialAnalysisPanel.this.save();
				BufferedImage img = SpatialAnalysisPanel.this.entrance.getMaskedImage();
				if (img == null) {
					if (SpatialAnalysisPanel.this.imgType == 0) {
						SpatialAnalysisPanel.this.original.setSelected(true);
					} else
						SpatialAnalysisPanel.this.enhanced.setSelected(true); 
				} else{
					SpatialAnalysisPanel.this.label.setImage(img); 
					//System.out.println("Masked!");
					zoomFromMemory(memory);
				}
				setMaskedText();
				valueChange();
				//SpatialAnalysisPanel.this.zoomFromMemory(SpatialAnalysisPanel.zoomMemory);
				try {
					SpatialAnalysisPanel.this.label.toolImage(SpatialAnalysisPanel.this.x, SpatialAnalysisPanel.this.y, SpatialAnalysisPanel.this.entrance.getColor(), (String)SpatialAnalysisPanel.this.comboBox.getSelectedItem(), SpatialAnalysisPanel.this.entrance.getLineWidth(), SpatialAnalysisPanel.this.entrance.getCursorStyle());
				} catch (Exception localException) {}
			} 
		});
		this.masked.setFont(new Font("Tahoma", 0, 11));
		this.masked.setBounds(181, 94, 76, 23);
		this.masked.setSelected(false);
		add(this.masked);


		ButtonGroup bg = new ButtonGroup();
		bg.add(this.masked);
		bg.add(this.enhanced);
		bg.add(this.original);

		String[] t = { "Select Measurement Tool", "Pixel Tool", "Line Tool", "Path Tool (multiple points)", "Rectangle Tool", "Polygon Tool", "Angle Tool" };
		this.comboBox = new JComboBox(t);
		this.comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				SpatialAnalysisPanel.this.changeTool();
			} 
		});
		this.comboBox.setModel(new DefaultComboBoxModel(new String[] { "Select Measurement Tool", "Pixel Tool", "Line Tool", "Path Tool (multiple points)", "Rectangle Tool", "Polygon Tool", "Angle Tool" }));
		this.comboBox.setName("Select Measurement Tool");
		this.comboBox.setBounds(20, 124, 222, 20);
		add(this.comboBox);

		JTextArea txtrSelectASpatial = new JTextArea();
		txtrSelectASpatial.setFont(new Font("SansSerif", 0, 13));
		txtrSelectASpatial.setText("Select a spatial analysis tool above.");
		txtrSelectASpatial.setWrapStyleWord(true);
		txtrSelectASpatial.setLineWrap(true);
		txtrSelectASpatial.setOpaque(false);
		txtrSelectASpatial.setEditable(false);
		txtrSelectASpatial.setBounds(0, 155, 260, 25);
		add(txtrSelectASpatial);
		this.RGB = new JRadioButton("RGB");
		this.RGB.setSelected(true);
		this.RGB.setBounds(10, 180, 80, 20);
		add(this.RGB);
		this.HSV = new JRadioButton("HSV");
		this.HSV.setBounds(90, 180, 80, 20);
		add(this.HSV);
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(this.RGB);
		buttonGroup.add(this.HSV);
		this.RGB.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) {
				if (SpatialAnalysisPanel.this.txtRed.getText().equals("Hue")) {
					SpatialAnalysisPanel.this.RGBorHSV(true);
					if (!SpatialAnalysisPanel.this.redIntens.getText().equals("")) {
						int rgb = Color.HSBtoRGB(Float.parseFloat(SpatialAnalysisPanel.this.redIntens.getText()) / 100.0F, 
								Float.parseFloat(SpatialAnalysisPanel.this.greenIntens.getText()) / 100.0F, 
								Float.parseFloat(SpatialAnalysisPanel.this.blueIntens.getText()) / 100.0F);
						SpatialAnalysisPanel.this.redIntens.setText(SpatialAnalysisPanel.this.df.format((rgb >> 16 & 0xFF) * 100.0F / 255.0F));
						SpatialAnalysisPanel.this.greenIntens.setText(SpatialAnalysisPanel.this.df.format((rgb >> 8 & 0xFF) * 100.0F / 255.0F));
						SpatialAnalysisPanel.this.blueIntens.setText(SpatialAnalysisPanel.this.df.format((rgb & 0xFF) * 100.0F / 255.0F));
						SpatialAnalysisPanel.this.avgIntens.setText(SpatialAnalysisPanel.this.df.format(((rgb >> 16 & 0xFF) + (rgb >> 8 & 0xFF) + (rgb & 0xFF)) * 100.0F / 255.0F / 3.0F));
					} 
				} 
			} 
		});
		this.HSV.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) {
				if (SpatialAnalysisPanel.this.txtRed.getText().equals("Red (%)")) {
					SpatialAnalysisPanel.this.RGBorHSV(false);
					if (!SpatialAnalysisPanel.this.redIntens.getText().equals("")) {
						float[] hsv = Color.RGBtoHSB(Math.round(Float.parseFloat(SpatialAnalysisPanel.this.redIntens.getText()) * 255.0F / 100.0F), 
								Math.round(Float.parseFloat(SpatialAnalysisPanel.this.greenIntens.getText()) * 255.0F / 100.0F), 
								Math.round(Float.parseFloat(SpatialAnalysisPanel.this.blueIntens.getText()) * 255.0F / 100.0F), null);
						SpatialAnalysisPanel.this.redIntens.setText(SpatialAnalysisPanel.this.df.format(hsv[0] * 100.0F));
						SpatialAnalysisPanel.this.greenIntens.setText(SpatialAnalysisPanel.this.df.format(hsv[1] * 100.0F));
						SpatialAnalysisPanel.this.blueIntens.setText(SpatialAnalysisPanel.this.df.format(hsv[2] * 100.0F));
						SpatialAnalysisPanel.this.avgIntens.setText("");
					} 
				} 
			} 
		});
		this.btnHideTool = new JButton("Hide Tool");
		this.btnHideTool.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SpatialAnalysisPanel.this.hideTool();
			} 
		});
		this.btnHideTool.setFont(new Font("Tahoma", 0, 10));
		this.btnHideTool.setBounds(186, 220, 94, 20);
		add(this.btnHideTool);

		JButton btnNewButton_1 = new JButton("Erase Tool");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SpatialAnalysisPanel.this.eraseTool();
			} 
		});
		btnNewButton_1.setFont(new Font("Tahoma", 0, 10));
		btnNewButton_1.setBounds(186, 241, 94, 20);
		add(btnNewButton_1);

		JButton blue = new JButton();
		blue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SpatialAnalysisPanel.this.entrance.setColor(Color.blue);
				SpatialAnalysisPanel.this.entrance.getMenu().setColor((byte)1);
				try {
					SpatialAnalysisPanel.this.label.toolImage(SpatialAnalysisPanel.this.x, SpatialAnalysisPanel.this.y, SpatialAnalysisPanel.this.entrance.getColor(), (String)SpatialAnalysisPanel.this.comboBox.getSelectedItem(), SpatialAnalysisPanel.this.entrance.getLineWidth(), SpatialAnalysisPanel.this.entrance.getCursorStyle());
				} catch (Exception localException) {}
			} 
		});
		blue.setIcon(new ImageIcon(SpatialAnalysisPanel.class.getResource("/resources/colors/blue.png")));
		blue.setBounds(143, 220, 20, 20);
		add(blue);

		JButton green = new JButton();
		green.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SpatialAnalysisPanel.this.entrance.setColor(Color.green);
				SpatialAnalysisPanel.this.entrance.getMenu().setColor((byte)4);
				try {
					SpatialAnalysisPanel.this.label.toolImage(SpatialAnalysisPanel.this.x, SpatialAnalysisPanel.this.y, SpatialAnalysisPanel.this.entrance.getColor(), (String)SpatialAnalysisPanel.this.comboBox.getSelectedItem(), SpatialAnalysisPanel.this.entrance.getLineWidth(), SpatialAnalysisPanel.this.entrance.getCursorStyle());
				} catch (Exception localException) {}
			} 
		});
		green.setIcon(new ImageIcon(SpatialAnalysisPanel.class.getResource("/resources/colors/green.png")));
		green.setBounds(123, 220, 20, 20);
		add(green);

		JButton red = new JButton();
		red.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SpatialAnalysisPanel.this.entrance.setColor(Color.red);
				SpatialAnalysisPanel.this.entrance.getMenu().setColor((byte)7);
				try {
					SpatialAnalysisPanel.this.label.toolImage(SpatialAnalysisPanel.this.x, SpatialAnalysisPanel.this.y, SpatialAnalysisPanel.this.entrance.getColor(), (String)SpatialAnalysisPanel.this.comboBox.getSelectedItem(), SpatialAnalysisPanel.this.entrance.getLineWidth(), SpatialAnalysisPanel.this.entrance.getCursorStyle());
				} catch (Exception localException) {}
			} 
		});
		red.setIcon(new ImageIcon(SpatialAnalysisPanel.class.getResource("/resources/colors/red.png")));
		red.setBounds(103, 220, 20, 20);
		add(red);

		JButton black = new JButton();
		black.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SpatialAnalysisPanel.this.entrance.setColor(Color.black);
				SpatialAnalysisPanel.this.entrance.getMenu().setColor((byte)0);
				try {
					SpatialAnalysisPanel.this.label.toolImage(SpatialAnalysisPanel.this.x, SpatialAnalysisPanel.this.y, SpatialAnalysisPanel.this.entrance.getColor(), (String)SpatialAnalysisPanel.this.comboBox.getSelectedItem(), SpatialAnalysisPanel.this.entrance.getLineWidth(), SpatialAnalysisPanel.this.entrance.getCursorStyle());
				} catch (Exception localException) {}
			} 
		});
		black.setIcon(new ImageIcon(SpatialAnalysisPanel.class.getResource("/resources/colors/black.png")));
		black.setForeground(Color.BLACK);
		black.setBackground(Color.WHITE);
		black.setBounds(83, 220, 20, 20);
		add(black);

		JButton yellow = new JButton();
		yellow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SpatialAnalysisPanel.this.entrance.setColor(Color.yellow);
				SpatialAnalysisPanel.this.entrance.getMenu().setColor((byte)9);
				try {
					SpatialAnalysisPanel.this.label.toolImage(SpatialAnalysisPanel.this.x, SpatialAnalysisPanel.this.y, SpatialAnalysisPanel.this.entrance.getColor(), (String)SpatialAnalysisPanel.this.comboBox.getSelectedItem(), SpatialAnalysisPanel.this.entrance.getLineWidth(), SpatialAnalysisPanel.this.entrance.getCursorStyle());
				} catch (Exception localException) {}
			} 
		});
		yellow.setIcon(new ImageIcon(SpatialAnalysisPanel.class.getResource("/resources/colors/yellow.png")));
		yellow.setBounds(83, 240, 20, 20);
		add(yellow);

		JButton magenta = new JButton();
		magenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SpatialAnalysisPanel.this.entrance.setColor(Color.magenta);
				SpatialAnalysisPanel.this.entrance.getMenu().setColor((byte)5);
				try {
					SpatialAnalysisPanel.this.label.toolImage(SpatialAnalysisPanel.this.x, SpatialAnalysisPanel.this.y, SpatialAnalysisPanel.this.entrance.getColor(), (String)SpatialAnalysisPanel.this.comboBox.getSelectedItem(), SpatialAnalysisPanel.this.entrance.getLineWidth(), SpatialAnalysisPanel.this.entrance.getCursorStyle());
				} catch (Exception localException) {}
			} 
		});
		magenta.setIcon(new ImageIcon(SpatialAnalysisPanel.class.getResource("/resources/colors/magenta.png")));
		magenta.setBounds(103, 240, 20, 20);
		add(magenta);

		JButton cyan = new JButton();
		cyan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SpatialAnalysisPanel.this.entrance.setColor(Color.cyan);
				SpatialAnalysisPanel.this.entrance.getMenu().setColor((byte)2);
				try {
					SpatialAnalysisPanel.this.label.toolImage(SpatialAnalysisPanel.this.x, SpatialAnalysisPanel.this.y, SpatialAnalysisPanel.this.entrance.getColor(), (String)SpatialAnalysisPanel.this.comboBox.getSelectedItem(), SpatialAnalysisPanel.this.entrance.getLineWidth(), SpatialAnalysisPanel.this.entrance.getCursorStyle());
				} catch (Exception localException) {}
			} 
		});
		cyan.setIcon(new ImageIcon(SpatialAnalysisPanel.class.getResource("/resources/colors/cyan.png")));
		cyan.setBounds(123, 240, 20, 20);
		add(cyan);

		JButton white = new JButton();
		white.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SpatialAnalysisPanel.this.entrance.setColor(Color.white);
				SpatialAnalysisPanel.this.entrance.getMenu().setColor((byte)8);
				try {
					SpatialAnalysisPanel.this.label.toolImage(SpatialAnalysisPanel.this.x, SpatialAnalysisPanel.this.y, SpatialAnalysisPanel.this.entrance.getColor(), (String)SpatialAnalysisPanel.this.comboBox.getSelectedItem(), SpatialAnalysisPanel.this.entrance.getLineWidth(), SpatialAnalysisPanel.this.entrance.getCursorStyle());
				} catch (Exception localException) {}
			} 
		});
		white.setIcon(new ImageIcon(SpatialAnalysisPanel.class.getResource("/resources/colors/white.png")));
		white.setBounds(143, 240, 20, 20);
		add(white);

		JButton grey = new JButton();
		grey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SpatialAnalysisPanel.this.entrance.setColor(Color.gray);
				SpatialAnalysisPanel.this.entrance.getMenu().setColor((byte)3);
				try {
					SpatialAnalysisPanel.this.label.toolImage(SpatialAnalysisPanel.this.x, SpatialAnalysisPanel.this.y, SpatialAnalysisPanel.this.entrance.getColor(), (String)SpatialAnalysisPanel.this.comboBox.getSelectedItem(), SpatialAnalysisPanel.this.entrance.getLineWidth(), SpatialAnalysisPanel.this.entrance.getCursorStyle());
				} catch (Exception localException) {}
			} 
		});
		grey.setIcon(new ImageIcon(SpatialAnalysisPanel.class.getResource("/resources/colors/grey.png")));
		grey.setBounds(163, 220, 20, 20);
		add(grey);

		JButton orange = new JButton();
		orange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SpatialAnalysisPanel.this.entrance.setColor(Color.orange);
				SpatialAnalysisPanel.this.entrance.getMenu().setColor((byte)6);
				try {
					SpatialAnalysisPanel.this.label.toolImage(SpatialAnalysisPanel.this.x, SpatialAnalysisPanel.this.y, SpatialAnalysisPanel.this.entrance.getColor(), (String)SpatialAnalysisPanel.this.comboBox.getSelectedItem(), SpatialAnalysisPanel.this.entrance.getLineWidth(), SpatialAnalysisPanel.this.entrance.getCursorStyle());
				} catch (Exception localException) {}
			} 
		});
		orange.setIcon(new ImageIcon(SpatialAnalysisPanel.class.getResource("/resources/colors/orange.png")));
		orange.setBounds(163, 240, 20, 20);
		add(orange);

		JTextArea txtrSelectToolColor = new JTextArea();
		txtrSelectToolColor.setFont(new Font("SansSerif", 0, 13));
		txtrSelectToolColor.setOpaque(false);
		txtrSelectToolColor.setWrapStyleWord(true);
		txtrSelectToolColor.setLineWrap(true);
		txtrSelectToolColor.setText("Select color of tool");
		txtrSelectToolColor.setBounds(10, 220, 73, 58);
		add(txtrSelectToolColor);


		JLabel lblNewLabel = this.label.getLabel();
		lblNewLabel.addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					SpatialAnalysisPanel.this.mousePress(e);
				} 
			} 
			public void mouseReleased(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					SpatialAnalysisPanel.this.mouseRelease(e);
				} 
			} 
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() > 1) {
					SpatialAnalysisPanel.this.pathComplete = true;
				}

			} 
		});
		lblNewLabel.addMouseMotionListener(new MouseMotionAdapter()
		{
			public void mouseDragged(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					SpatialAnalysisPanel.this.mouseDrag(e);
				} 
			} 
		});
		this.txtPixelPosition = new JTextField();
		this.txtPixelPosition.setVisible(false);
		this.txtPixelPosition.setBorder(null);
		this.txtPixelPosition.setOpaque(false);
		this.txtPixelPosition.setEditable(false);
		this.txtPixelPosition.setText("Pixel Position");
		this.txtPixelPosition.setBounds(100, 282, 90, 20);
		add(this.txtPixelPosition);
		this.txtPixelPosition.setColumns(10);

		this.txtAdjust = new JTextField();
		this.txtAdjust.setVisible(false);
		this.txtAdjust.setText("Adjust");
		this.txtAdjust.setOpaque(false);
		this.txtAdjust.setEditable(false);
		this.txtAdjust.setBorder(null);
		this.txtAdjust.setBounds(199, 282, 58, 20);
		add(this.txtAdjust);
		this.txtAdjust.setColumns(10);

		this.txtX = new JTextField();
		this.txtX.setVisible(false);
		this.txtX.setFont(new Font("SansSerif", 0, 13));
		this.txtX.setBorder(null);
		this.txtX.setEditable(false);
		this.txtX.setOpaque(false);
		this.txtX.setText("X");
		this.txtX.setBounds(143, 301, 14, 20);
		add(this.txtX);
		this.txtX.setColumns(10);

		this.txtY = new JTextField();
		this.txtY.setVisible(false);
		this.txtY.setFont(new Font("SansSerif", 0, 13));
		this.txtY.setOpaque(false);
		this.txtY.setEditable(false);
		this.txtY.setBorder(null);
		this.txtY.setText("Y");
		this.txtY.setBounds(193, 301, 20, 20);
		add(this.txtY);
		this.txtY.setColumns(10);



		this.endX = new JTextField();
		this.endX.setVisible(false);
		this.endX.setFont(new Font("Tahoma", 0, 14));
		this.endX.setOpaque(false);
		this.endX.setBorder(null);
		this.endX.setText("0");
		this.endX.setBounds(136, 370, 60, 20);
		add(this.endX);

		this.startX = new JTextField();
		this.startX.setVisible(false);
		this.startX.setFont(new Font("Tahoma", 0, 14));
		this.startX.setOpaque(false);
		this.startX.setBorder(null);
		this.startX.setText("0");
		this.startX.setBounds(136, 318, 60, 20);
		add(this.startX);

		this.endY = new JTextField();
		this.endY.setVisible(false);
		this.endY.setFont(new Font("Tahoma", 0, 14));
		this.endY.setOpaque(false);
		this.endY.setBorder(null);
		this.endY.setText("0");
		this.endY.setBounds(197, 370, 60, 20);
		add(this.endY);

		this.startY = new JTextField();
		this.startY.setVisible(false);
		this.startY.setFont(new Font("Tahoma", 0, 14));
		this.startY.setOpaque(false);
		this.startY.setBorder(null);
		this.startY.setText("0");
		this.startY.setBounds(197, 318, 60, 20);
		add(this.startY);


		this.sXP = new BasicArrowButton(BasicArrowButton.EAST, new Color(0xFF8C00), new Color(0x9F4C00), new Color(0x8F3C00), new Color(0xFF9C10));
		sXP.setBounds(62, 318, 16, 16);
		sXP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				int t = Integer.parseInt(startX.getText());
				if ((t < SpatialAnalysisPanel.this.label.getOriginal().getWidth())) {
					t++;
					SpatialAnalysisPanel.this.startX.setText("" + t);
					SpatialAnalysisPanel.this.x[0] = Integer.valueOf(t);
					SpatialAnalysisPanel.this.valueChange();
				} 
				SpatialAnalysisPanel.this.valueChange();
			}
		});
		add(sXP);
		this.sXM = new BasicArrowButton(BasicArrowButton.WEST, new Color(0xFF8C00), new Color(0x9F4C00), new Color(0x8F3C00), new Color(0xFF9C10));
		sXM.setBounds(30, 318, 16, 16);
		sXM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				int t = Integer.parseInt(startX.getText());
				if (t > 0) {
					t--;
					SpatialAnalysisPanel.this.startX.setText("" + t);
					SpatialAnalysisPanel.this.x[0] = Integer.valueOf(t);
					SpatialAnalysisPanel.this.valueChange();
				} 
				SpatialAnalysisPanel.this.valueChange();
			}
		});
		add(sXM);
		this.sYP = new BasicArrowButton(BasicArrowButton.SOUTH, new Color(0xFF8C00), new Color(0x9F4C00), new Color(0x8F3C00), new Color(0xFF9C10));
		sYP.setBounds(46, 334, 16, 16);
		sYP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				int t = Integer.parseInt(startY.getText());
				if ((t < SpatialAnalysisPanel.this.label.getOriginal().getHeight())) {
					t++;
					SpatialAnalysisPanel.this.startY.setText("" + t);
					SpatialAnalysisPanel.this.y[0] = Integer.valueOf(t);
					SpatialAnalysisPanel.this.valueChange();
				} 
				SpatialAnalysisPanel.this.valueChange();
			}
		});
		add(sYP);
		this.sYM = new BasicArrowButton(BasicArrowButton.NORTH, new Color(0xFF8C00), new Color(0x9F4C00), new Color(0x8F3C00), new Color(0xFF9C10));
		sYM.setBounds(46, 302, 16, 16);
		sYM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				int t = Integer.parseInt(startY.getText());
				if (t > 0) {
					t--;
					SpatialAnalysisPanel.this.startY.setText("" + t);
					SpatialAnalysisPanel.this.y[0] = Integer.valueOf(t);
					SpatialAnalysisPanel.this.valueChange();
				} 
				SpatialAnalysisPanel.this.valueChange();
			}
		});
		add(sYM);

		this.eXP = new BasicArrowButton(BasicArrowButton.EAST, new Color(0x8A309F), new Color(0x2A0030), new Color(0x10000F), new Color(0xAA50BF));
		eXP.setBounds(62, 370, 16, 16);
		eXP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				int t = Integer.parseInt(endX.getText());
				if ((t < SpatialAnalysisPanel.this.label.getOriginal().getWidth())) {
					t++;
					SpatialAnalysisPanel.this.endX.setText("" + t);
					SpatialAnalysisPanel.this.x[1] = Integer.valueOf(t);
					SpatialAnalysisPanel.this.valueChange();
				} 
				SpatialAnalysisPanel.this.valueChange();
			}
		});
		add(eXP);
		this.eXM = new BasicArrowButton(BasicArrowButton.WEST, new Color(0x8A309F), new Color(0x2A0030), new Color(0x10000F), new Color(0xAA50BF));
		eXM.setBounds(30, 370, 16, 16);
		eXM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				int t = Integer.parseInt(endX.getText());
				if (t > 0) {
					t--;
					SpatialAnalysisPanel.this.endX.setText("" + t);
					SpatialAnalysisPanel.this.x[1] = Integer.valueOf(t);
					SpatialAnalysisPanel.this.valueChange();
				} 
				SpatialAnalysisPanel.this.valueChange();
			}
		});
		add(eXM);
		this.eYP = new BasicArrowButton(BasicArrowButton.SOUTH, new Color(0x8A309F), new Color(0x2A0030), new Color(0x10000F), new Color(0xAA50BF));
		eYP.setBounds(46, 386, 16, 16);
		eYP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				int t = Integer.parseInt(endY.getText());
				if ((t < SpatialAnalysisPanel.this.label.getOriginal().getHeight())) {
					t++;
					SpatialAnalysisPanel.this.endY.setText("" + t);
					SpatialAnalysisPanel.this.y[1] = Integer.valueOf(t);
					SpatialAnalysisPanel.this.valueChange();
				} 
				SpatialAnalysisPanel.this.valueChange();
			}
		});
		add(eYP);
		this.eYM = new BasicArrowButton(BasicArrowButton.NORTH, new Color(0x8A309F), new Color(0x2A0030), new Color(0x10000F), new Color(0xAA50BF));
		eYM.setBounds(46, 354, 16, 16);
		eYM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				int t = Integer.parseInt(endY.getText());
				if (t > 0) {
					t--;
					SpatialAnalysisPanel.this.endY.setText("" + t);
					SpatialAnalysisPanel.this.y[1] = Integer.valueOf(t);
					SpatialAnalysisPanel.this.valueChange();
				} 
				SpatialAnalysisPanel.this.valueChange();
			}
		});
		add(eYM);


		this.sXP.setVisible(false);
		this.sXM.setVisible(false);
		this.eXP.setVisible(false);
		this.eXM.setVisible(false);
		this.sYP.setVisible(false);
		this.sYM.setVisible(false);
		this.eYP.setVisible(false);
		this.eYM.setVisible(false);


































		/*		this.startX = new JSpinner();
		this.startX.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		this.startX.setVisible(false);
		this.startX.setFont(new Font("SansSerif", 0, 11));
		this.startX.setOpaque(false);
		this.startX.setBorder(null);
		this.startX.setBounds(116, 318, 60, 20);
		this.startX.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e) {
				int t = ((Integer)((JSpinner)e.getSource()).getValue()).intValue();
				if ((t < SpatialAnalysisPanel.this.label.getOriginal().getWidth()) && (t >= 0)) {
					SpatialAnalysisPanel.this.x[0] = Integer.valueOf(t);
					SpatialAnalysisPanel.this.valueChange();
				} 
				SpatialAnalysisPanel.this.valueChange();
			} 
		});
		add(this.startX);

		this.startY = new JSpinner();
		this.startY.setVisible(false);
		this.startY.setFont(new Font("SansSerif", 0, 11));
		this.startY.setBorder(null);
		this.startY.setBounds(177, 318, 60, 20);
		this.startY.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e) {
				int t = ((Integer)((JSpinner)e.getSource()).getValue()).intValue();
				if ((t < SpatialAnalysisPanel.this.label.getOriginal().getHeight()) && (t >= 0)) {
					SpatialAnalysisPanel.this.y[0] = Integer.valueOf(t);
					SpatialAnalysisPanel.this.valueChange();
				} 
				SpatialAnalysisPanel.this.valueChange();
			} 
		});
		add(this.startY);

		this.endY = new JSpinner();
		this.endY.setVisible(false);
		this.endY.setFont(new Font("SansSerif", 0, 11));
		this.endY.setBorder(null);
		this.endY.setBounds(177, 357, 60, 20);
		this.endY.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e) {
				int t = ((Integer)((JSpinner)e.getSource()).getValue()).intValue();
				if ((t < SpatialAnalysisPanel.this.label.getOriginal().getHeight()) && (t >= 0)) {
					SpatialAnalysisPanel.this.y[1] = Integer.valueOf(t);
					SpatialAnalysisPanel.this.valueChange();
				} 
				SpatialAnalysisPanel.this.valueChange();
			} 
		});
		add(this.endY);

		this.endX = new JSpinner();
		this.endX.setVisible(false);
		this.endX.setFont(new Font("SansSerif", 0, 11));
		this.endX.setBorder(null);
		this.endX.setBounds(116, 357, 60, 20);
		this.endX.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e) {
				int t = ((Integer)((JSpinner)e.getSource()).getValue()).intValue();
				if ((t < SpatialAnalysisPanel.this.label.getOriginal().getWidth()) && (t >= 0)) {
					SpatialAnalysisPanel.this.x[1] = Integer.valueOf(t);
					SpatialAnalysisPanel.this.valueChange();
				} 
				SpatialAnalysisPanel.this.valueChange();
			} 
		});
		add(this.endX);*/

		this.txtrIntensity = new JTextArea();
		this.txtrIntensity.setVisible(false);
		this.txtrIntensity.setText("Intensity [%]");
		this.txtrIntensity.setOpaque(false);
		this.txtrIntensity.setFont(new Font("SansSerif", 0, 13));
		this.txtrIntensity.setEditable(false);
		this.txtrIntensity.setBounds(133, 453, 89, 20);
		add(this.txtrIntensity);

		this.txtColor = new JTextField();
		this.txtColor.setVisible(false);
		this.txtColor.setFont(new Font("SansSerif", 0, 13));
		this.txtColor.setBorder(null);
		this.txtColor.setEditable(false);
		this.txtColor.setHorizontalAlignment(4);
		this.txtColor.setOpaque(false);
		this.txtColor.setText("Color");
		this.txtColor.setBounds(39, 454, 86, 20);
		add(this.txtColor);
		this.txtColor.setColumns(10);

		this.txtRed = new JTextField();
		this.txtRed.setVisible(false);
		this.txtRed.setText("Red (%)");
		this.txtRed.setOpaque(false);
		this.txtRed.setHorizontalAlignment(4);
		this.txtRed.setFont(new Font("SansSerif", 0, 13));
		this.txtRed.setEditable(false);
		this.txtRed.setColumns(10);
		this.txtRed.setBorder(null);
		this.txtRed.setBounds(39, 473, 86, 20);
		add(this.txtRed);

		this.txtGreen = new JTextField();
		this.txtGreen.setVisible(false);
		this.txtGreen.setText("Green (%)");
		this.txtGreen.setOpaque(false);
		this.txtGreen.setHorizontalAlignment(4);
		this.txtGreen.setFont(new Font("SansSerif", 0, 13));
		this.txtGreen.setEditable(false);
		this.txtGreen.setColumns(10);
		this.txtGreen.setBorder(null);
		this.txtGreen.setBounds(39, 488, 86, 20);
		add(this.txtGreen);

		this.txtBlue = new JTextField();
		this.txtBlue.setVisible(false);
		this.txtBlue.setText("Blue (%)");
		this.txtBlue.setOpaque(false);
		this.txtBlue.setHorizontalAlignment(4);
		this.txtBlue.setFont(new Font("SansSerif", 0, 13));
		this.txtBlue.setEditable(false);
		this.txtBlue.setColumns(10);
		this.txtBlue.setBorder(null);
		this.txtBlue.setBounds(39, 504, 86, 20);
		add(this.txtBlue);

		this.txtAverageColor = new JTextField();
		this.txtAverageColor.setVisible(false);
		this.txtAverageColor.setText("Average Color");
		this.txtAverageColor.setOpaque(false);
		this.txtAverageColor.setHorizontalAlignment(4);
		this.txtAverageColor.setFont(new Font("SansSerif", 0, 13));
		this.txtAverageColor.setEditable(false);
		this.txtAverageColor.setColumns(10);
		this.txtAverageColor.setBorder(null);
		this.txtAverageColor.setBounds(20, 521, 105, 20);
		add(this.txtAverageColor);

		this.redIntens = new JTextField();
		this.redIntens.setVisible(false);
		this.redIntens.setBorder(null);
		this.redIntens.setEditable(false);
		this.redIntens.setFont(new Font("SansSerif", 0, 13));
		this.redIntens.setHorizontalAlignment(0);
		this.redIntens.setOpaque(false);
		this.redIntens.setText("0");
		this.redIntens.setBounds(145, 473, 46, 20);
		add(this.redIntens);
		this.redIntens.setColumns(10);

		this.greenIntens = new JTextField();
		this.greenIntens.setVisible(false);
		this.greenIntens.setText("0");
		this.greenIntens.setOpaque(false);
		this.greenIntens.setHorizontalAlignment(0);
		this.greenIntens.setFont(new Font("SansSerif", 0, 13));
		this.greenIntens.setEditable(false);
		this.greenIntens.setColumns(10);
		this.greenIntens.setBorder(null);
		this.greenIntens.setBounds(145, 489, 46, 20);
		add(this.greenIntens);

		this.blueIntens = new JTextField();
		this.blueIntens.setVisible(false);
		this.blueIntens.setText("0");
		this.blueIntens.setOpaque(false);
		this.blueIntens.setHorizontalAlignment(0);
		this.blueIntens.setFont(new Font("SansSerif", 0, 13));
		this.blueIntens.setEditable(false);
		this.blueIntens.setColumns(10);
		this.blueIntens.setBorder(null);
		this.blueIntens.setBounds(145, 505, 46, 20);
		add(this.blueIntens);

		this.avgIntens = new JTextField();
		this.avgIntens.setVisible(false);
		this.avgIntens.setText("0");
		this.avgIntens.setOpaque(false);
		this.avgIntens.setHorizontalAlignment(0);
		this.avgIntens.setFont(new Font("SansSerif", 0, 13));
		this.avgIntens.setEditable(false);
		this.avgIntens.setColumns(10);
		this.avgIntens.setBorder(null);
		this.avgIntens.setBounds(145, 522, 46, 20);
		add(this.avgIntens);

		this.txtrIntensitiesOfColors = new JTextArea();
		this.txtrIntensitiesOfColors.setVisible(false);
		this.txtrIntensitiesOfColors.setEditable(false);
		this.txtrIntensitiesOfColors.setFont(new Font("SansSerif", 0, 13));
		this.txtrIntensitiesOfColors.setLineWrap(true);
		this.txtrIntensitiesOfColors.setOpaque(false);
		this.txtrIntensitiesOfColors.setWrapStyleWord(true);
		this.txtrIntensitiesOfColors.setText("Intensities of colors range from 0%, meaning none of the color is present, to 100%, when maximum color is present.");
		this.txtrIntensitiesOfColors.setBounds(20, 564, 202, 79);
		add(this.txtrIntensitiesOfColors);
		this.pixNumDesc = new JTextField();
		this.pixNumDesc.setVisible(false);
		this.pixNumDesc.setText("Number of Pixels");
		this.pixNumDesc.setOpaque(false);
		this.pixNumDesc.setHorizontalAlignment(4);
		this.pixNumDesc.setFont(new Font("SansSerif", 0, 13));
		this.pixNumDesc.setEditable(false);
		this.pixNumDesc.setColumns(10);
		this.pixNumDesc.setBorder(null);
		this.pixNumDesc.setBounds(8, 412, 115, 20);
		add(this.pixNumDesc);

		this.pixNum = new JTextField();
		this.pixNum.setVisible(false);
		this.pixNum.setText("0");
		this.pixNum.setOpaque(false);
		this.pixNum.setHorizontalAlignment(2);
		this.pixNum.setFont(new Font("SansSerif", 0, 13));
		this.pixNum.setEditable(false);
		this.pixNum.setColumns(10);
		this.pixNum.setBorder(null);
		this.pixNum.setBounds(143, 412, 70, 20);
		add(this.pixNum);

		this.lengthArea = new JTextField();
		this.lengthArea.setVisible(false);
		this.lengthArea.setOpaque(false);
		this.lengthArea.setHorizontalAlignment(2);
		this.lengthArea.setFont(new Font("SansSerif", 0, 13));
		this.lengthArea.setEditable(false);
		this.lengthArea.setColumns(10);
		this.lengthArea.setBorder(null);
		this.lengthArea.setBounds(143, 435, 150, 20);
		add(this.lengthArea);

		this.txtLengthArea = new JTextField();
		this.txtLengthArea.setVisible(false);
		this.txtLengthArea.setOpaque(false);
		this.txtLengthArea.setHorizontalAlignment(4);
		this.txtLengthArea.setFont(new Font("SansSerif", 0, 13));
		this.txtLengthArea.setEditable(false);
		this.txtLengthArea.setColumns(10);
		this.txtLengthArea.setBorder(null);
		this.txtLengthArea.setBounds(8, 435, 115, 20);
		add(this.txtLengthArea);

		this.txtAngle = new JTextField();
		this.txtAngle.setVisible(false);
		this.txtAngle.setBorder(null);
		this.txtAngle.setOpaque(false);
		this.txtAngle.setFont(new Font("SansSerif", 0, 13));
		this.txtAngle.setHorizontalAlignment(11);
		this.txtAngle.setEditable(false);
		this.txtAngle.setText("Angle (degrees)");
		this.txtAngle.setBounds(20, 442, 105, 20);
		add(this.txtAngle);
		this.txtAngle.setColumns(10);

		this.angle = new JTextField();
		this.angle.setVisible(false);
		this.angle.setBorder(null);
		this.angle.setEditable(false);
		this.angle.setOpaque(false);
		this.angle.setFont(new Font("SansSerif", 0, 13));
		this.angle.setHorizontalAlignment(0);
		this.angle.setText("0");
		this.angle.setBounds(143, 442, 47, 20);
		add(this.angle);
		this.angle.setColumns(10);

		this.btnShowTool = new JButton("Show Tool");
		this.btnShowTool.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SpatialAnalysisPanel.this.showTool();
			} 
		});
		this.btnShowTool.setVisible(false);
		this.btnShowTool.setFont(new Font("Tahoma", 0, 10));
		this.btnShowTool.setBounds(186, 220, 94, 20);
		add(this.btnShowTool);

		this.original.setSelected(true);
		this.masked.setSelected(false);
		this.enhanced.setSelected(false);

		this.RGB.setSelected(getMemory().getColorType() == "RGB");
		this.HSV.setSelected(getMemory().getColorType() == "HSV");



	}
	public static SpacialAnalysisMemory getMemory() {
		return memory;
	} 
	
	public Integer[] getTX(){
		return x;
	}
	public Integer[] getTY(){
		return y;
	}
	public JComboBox getComboBox(){
		return comboBox;
	}
	
	
} 