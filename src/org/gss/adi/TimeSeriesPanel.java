package org.gss.adi;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicArrowButton;

import org.gss.adi.dialogs.ManualCalibrationDialog;
import org.gss.adi.dialogs.NDVIInfoDialog;
import org.gss.adi.tools.ColorEnhances;
import org.gss.adi.tools.ColorTools;
import org.gss.adi.tools.Measurement;

public class TimeSeriesPanel extends JPanel
implements Toolable, Updatable
{
	private static final long serialVersionUID = 6917266928380728707L;
	private JTextField img3labl;
	private JTextField img1labl;
	private JTextField img2labl;
	private JTextField txtAnalysis;
	private JTextField txtEnhancement;
	private Entrance entrance;
	private JSlider slider;
	private JPanel dataPanel;
	private ZoomPanLabel img1;
	private ZoomPanLabel img2;
	private ZoomPanLabel img3;
	private BufferedImage currentImage1;
	private BufferedImage currentImage2;
	private BufferedImage currentImage3;
	private JComboBox comboBox, comboBox_1;
	private JTextField textField;
	private boolean Three;
	private JTextArea x1,y1,x2,y2;
	private JTextArea pt1;
	private JTextArea pt2;
	private JTextArea txtNumPixels;
	private JTextArea lengthArea;
	private JTextArea numPixels;
	private JTextArea txtLengthArea;
	private ToolAdder ta;
	private JLabel ndviChart;
	private int dataPanelType = 0;
	private byte Type;
	private JCheckBox displayTitles;
	private JButton sXP, sXM, sYP, sYM, eXP, eXM, eYP, eYM;

	//Statics for memory
	static final TimeSeriesMemory memory = new TimeSeriesMemory();


	public TimeSeriesPanel(Entrance ent, boolean three, byte type)
	{
		try
		{
			this.Three = three;
			setLayout(null);
			this.Type = type;
			this.entrance = ent;
			this.entrance.setPane(this);
			setup(type);
			setImages(three);
			if (type == 0)
				this.dataPanelType = 0;
			else
				this.dataPanelType = 1;
			if (three)
				this.ta = new ToolAdder(this.comboBox, this.entrance, new ZoomPanLabel[] { this.img1, this.img2, this.img3 }, this, null, this.dataPanelType, this.comboBox_1);
			else {
				this.ta = new ToolAdder(this.comboBox, this.entrance, new ZoomPanLabel[] { this.img1, this.img2 }, this, null, this.dataPanelType, this.comboBox_1);
			}
			zoomFromMemory(memory, three);
			
			// Load tool after zoom is restored
			if(memory.getToolType() != 0){
				this.comboBox.setSelectedIndex(memory.getToolType());
			}
			else{
				TimeSeriesPanel.this.setPoint1(TimeSeriesPanel.memory.getToolX()[0], TimeSeriesPanel.memory.getToolY()[0]);
			}
			
			//Load tool points
			this.ta.setX(memory.getToolX());
			this.ta.setY(memory.getToolY());

			// Redraw tool
			if(memory.getToolX()[0] != null)
				this.ta.drawTools();



			this.img1.getLabel().addMouseListener(new pixelMouse());
			this.img1.getLabel().addMouseMotionListener(new pixelDrag());
			this.img2.getLabel().addMouseListener(new pixelMouse());
			this.img2.getLabel().addMouseMotionListener(new pixelDrag());
			if (three) {
				this.img3.getLabel().addMouseListener(new pixelMouse());
				this.img3.getLabel().addMouseMotionListener(new pixelDrag());
			}
		} catch (OutOfMemoryError e) { JOptionPane.showMessageDialog(null, "Java out of memory error. Please use a smaller image or increase\nthe ram allocated to java applications"); }


		handlePixels();
		this.ta.update();
		this.img1.pan(memory.getX(), memory.getY());
		this.img1.setSyncAdjusting(false);
	}

	private void zoomFromMemory(ZoomMemory z, boolean three){
		int f = (int)(z.getZoomFactor() * 100.0);
		this.slider.setValue(f);
		titlesToFront(this.displayTitles.isSelected());
	}

	public void save(){
		// Save zoom
		memory.setX(this.img1.getHorizontalScrollBar().getValue());
		memory.setY(this.img1.getVerticalScrollBar().getValue());
		memory.setZoomFactor(this.img1.zoomFactor);

		// Save EnhanceColor Specifics
		memory.setEnhancementType(this.comboBox_1.getSelectedIndex());

		// Save ToolType
		memory.setToolType(this.comboBox.getSelectedIndex());

		// Save tool points
		memory.setToolX(this.ta.getX());
		memory.setToolY(this.ta.getY());
	}

	protected void closingSequence() {
		save();
	}

	private void setImages(boolean three) {
		this.img1.setImage(this.entrance.getTimeSeries1());
		this.img2.setImage(this.entrance.getTimeSeries2());
		this.currentImage1 = this.entrance.getTimeSeries1();
		this.currentImage2 = this.entrance.getTimeSeries2();
		this.img1labl.setText(this.entrance.getTitle1());
		this.img2labl.setText(this.entrance.getTitle2());
		this.img1labl.setForeground(this.entrance.getColor());
		this.img2labl.setForeground(this.entrance.getColor());
		if (three) {
			this.img3.setImage(this.entrance.getTimeSeries3());
			this.currentImage3 = this.entrance.getTimeSeries3();
			this.img3labl.setText(this.entrance.getTitle3());
			this.img3labl.setForeground(this.entrance.getColor());
		}
	}

	private void titlesToFront(boolean b)
	{
		this.img1labl.setVisible(b);
		this.img2labl.setVisible(b);
		this.img3labl.setVisible(b);
		this.img1labl.repaint();
		this.img2labl.repaint();
		this.img3labl.repaint();
	}

	private void handlePixels()
	{
		Double d;
		Measurement m;
		Integer[] x;
		Integer[] y;
		DecimalFormat df = new DecimalFormat("##.##");
		switch (this.comboBox.getSelectedIndex()) {
		case 0:
			break;
		case 1:
			break;
		case 2:
			d = ColorTools.linearDist(Integer.parseInt(this.x1.getText()), Integer.parseInt(this.y1.getText()), Integer.parseInt(this.x2.getText()), Integer.parseInt(this.y2.getText()));
			this.numPixels.setText(df.format(d));
			m = this.entrance.getTimeSeriesMeasurement();
			if (m != null)
				this.lengthArea.setText(m.measure(d, true));
			break;
		case 3:
			d = ColorTools.pathDist(this.ta.getX(), this.ta.getY());
			this.numPixels.setText(df.format(d));
			m = this.entrance.getTimeSeriesMeasurement();
			if (m != null)
				this.lengthArea.setText(m.measure(d, true));
			break;
		case 4:
			x = this.ta.getX();
			y = this.ta.getY();
			Integer i = new Integer(Math.abs(x[0].intValue() - x[1].intValue()) * Math.abs(y[0].intValue() - y[1].intValue()));
			this.numPixels.setText(i.toString());
			m = this.entrance.getTimeSeriesMeasurement();
			if (m != null)
				this.lengthArea.setText(m.measure(new Double(i.intValue()), false));
			break;
		case 5:
			if (this.ta.polyComplete()) {
				x = this.ta.getX();
				y = this.ta.getY();
				i = new Integer(ColorTools.polyArea(x, y).intValue());
				this.numPixels.setText(i.toString());
				m = this.entrance.getTimeSeriesMeasurement();
				if (m != null)
					this.lengthArea.setText(m.measure(new Double(i.intValue()), false)); 
			}
			break;
		}
	}

	public void updatePixSize() { this.txtLengthArea.setVisible(true);
	this.lengthArea.setVisible(true);
	if ((this.numPixels.getText() != null) && (!this.numPixels.getText().equals(""))) {
		String tool = (String)this.comboBox.getSelectedItem();
		Measurement m = this.entrance.getTimeSeriesMeasurement();
		if ((tool.contains("Line")) || (tool.contains("Path")))
			this.lengthArea.setText(m.measure(Double.valueOf(Double.parseDouble(this.numPixels.getText())), true));
		else if ((tool.contains("Rectangle")) || (tool.contains("Polygon")))
			this.lengthArea.setText(m.measure(Double.valueOf(Double.parseDouble(this.numPixels.getText())), false));
	} }

	public void updateTool()
	{
		this.img1labl.setForeground(this.entrance.getColor());
		this.img2labl.setForeground(this.entrance.getColor());
		this.img3labl.setForeground(this.entrance.getColor());
	}

	public void updatePic() {
	}

	public void setDataPanel(JPanel panel) {
		panel.setLocation(0, 0);
		panel.setSize(450, 300);
		panel.setPreferredSize(new Dimension(450, 300));
		this.dataPanel.add(panel);
		panel.revalidate();
		panel.repaint();
		this.dataPanel.revalidate();
		this.dataPanel.repaint();
		repaint();
	}

	public void removeDataPanel(JPanel panel) {
		if (panel != null) {
			this.dataPanel.remove(panel);
			repaint();
		}
	}

	public void setPoint1(int x, int y) {
		this.x1.setVisible(true);
		this.y1.setVisible(true);
		this.pt1.setVisible(true);
		this.x1.setText("" + Integer.valueOf(x));
		this.y1.setText("" + Integer.valueOf(y));
		this.sXP.setVisible(true);
		this.sXM.setVisible(true);
		this.sYM.setVisible(true);
		this.sYP.setVisible(true);

	}

	public void setPoint2(int x, int y) {
		this.x2.setVisible(true);
		this.y2.setVisible(true);
		this.pt2.setVisible(true);
		this.x2.setText("" + Integer.valueOf(x));
		this.y2.setText("" + Integer.valueOf(y));
		this.eXP.setVisible(true);
		this.eXM.setVisible(true);
		this.eYM.setVisible(true);
		this.eYP.setVisible(true);
	}

	public void removePoints() {
		this.x1.setVisible(false);
		this.y1.setVisible(false);
		this.pt1.setVisible(false);
		this.x2.setVisible(false);
		this.y2.setVisible(false);
		this.pt2.setVisible(false);
		this.sXP.setVisible(false);
		this.sXM.setVisible(false);
		this.sYM.setVisible(false);
		this.sYP.setVisible(false);
		this.eXP.setVisible(false);
		this.eXM.setVisible(false);
		this.eYM.setVisible(false);
		this.eYP.setVisible(false);
	}
	public void growth(String[] s) {
	}
	private void setup(byte type) {
		this.comboBox = new JComboBox();
		this.comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				TimeSeriesPanel.this.numPixels.setText("");
				TimeSeriesPanel.this.lengthArea.setText("");
				switch (TimeSeriesPanel.this.comboBox.getSelectedIndex())
				{
				case 0:
				case 1:
					TimeSeriesPanel.this.txtNumPixels.setVisible(false);
					TimeSeriesPanel.this.numPixels.setVisible(false);
					if (TimeSeriesPanel.this.entrance.getTimeSeriesMeasurement() != null) {
						TimeSeriesPanel.this.txtLengthArea.setVisible(false);
						TimeSeriesPanel.this.lengthArea.setVisible(false);
					}
					TimeSeriesPanel.this.setPoint1(TimeSeriesPanel.memory.getToolX()[0], TimeSeriesPanel.memory.getToolY()[0]);
					break;
				case 2:
					TimeSeriesPanel.this.setPoint1(TimeSeriesPanel.memory.getToolX()[0], TimeSeriesPanel.memory.getToolY()[0]);
					TimeSeriesPanel.this.setPoint2(TimeSeriesPanel.memory.getToolX()[1], TimeSeriesPanel.memory.getToolY()[1]);
					System.out.println("Case 2");
					break;
				case 3:
					TimeSeriesPanel.this.txtNumPixels.setVisible(true);
					TimeSeriesPanel.this.numPixels.setVisible(true);
					if (TimeSeriesPanel.this.entrance.getTimeSeriesMeasurement() != null) {
						TimeSeriesPanel.this.txtLengthArea.setVisible(true);
						TimeSeriesPanel.this.lengthArea.setVisible(true);
					}
					break;
				case 4:
					TimeSeriesPanel.this.setPoint2(TimeSeriesPanel.memory.getToolX()[1], TimeSeriesPanel.memory.getToolY()[1]);
					TimeSeriesPanel.this.setPoint1(TimeSeriesPanel.memory.getToolX()[0], TimeSeriesPanel.memory.getToolY()[0]);
				case 5:
					TimeSeriesPanel.this.txtNumPixels.setVisible(true);
					TimeSeriesPanel.this.numPixels.setVisible(true);
					if (TimeSeriesPanel.this.entrance.getTimeSeriesMeasurement() != null) {
						TimeSeriesPanel.this.txtLengthArea.setVisible(true);
						TimeSeriesPanel.this.lengthArea.setVisible(true);
					}
					break;
				}
			}
		});
		this.comboBox.setModel(new DefaultComboBoxModel(new String[] { "Point (text)", "Point (graph)", "Line", "Path (multiple point line)", "Rectangle", "Polygon" }));
		this.comboBox.setToolTipText("Point (text)\r\nPoint (graph)\r\nLine\r\nPath (multiple point line)\r\nRectangle\r\nPolygon");
		this.comboBox.setBounds(516, 328, 144, 20);
		add(this.comboBox);

		this.slider = new JSlider();
		this.slider.setOrientation(1);
		this.slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int z = TimeSeriesPanel.this.slider.getValue();
				try {
					TimeSeriesPanel.this.img1.zoom(z);
					TimeSeriesPanel.this.img2.zoom(z);
					if (TimeSeriesPanel.this.Three)
						TimeSeriesPanel.this.img3.zoom(z);
					TimeSeriesPanel.this.ta.drawTools();
					TimeSeriesPanel.this.titlesToFront(TimeSeriesPanel.this.displayTitles.isSelected());
				}
				catch (Exception localException)
				{
				}
			}
		});
		this.slider.addMouseListener(new MouseAdapter()
		{
			public void mouseReleased(MouseEvent e) {
				int z = TimeSeriesPanel.this.slider.getValue();
				try {
					TimeSeriesPanel.this.img1.qualityZoom(z);
					TimeSeriesPanel.this.img2.qualityZoom(z);
					if (TimeSeriesPanel.this.Three)
						TimeSeriesPanel.this.img3.qualityZoom(z);
					TimeSeriesPanel.this.ta.drawTools();
					TimeSeriesPanel.this.titlesToFront(TimeSeriesPanel.this.displayTitles.isSelected());
				}
				catch (Exception localException)
				{
				}
			}
		});
		this.img1labl = new JTextField();
		this.img1labl.setOpaque(false);
		this.img1labl.setEditable(false);
		this.img1labl.setColumns(10);
		this.img1labl.setBorder(null);
		this.img1labl.setBounds(10, 0, 450, 14);
		add(this.img1labl);
		this.slider.setMinimum(100);
		this.slider.setMaximum(200);
		this.slider.setMajorTickSpacing(2);
		this.slider.setSnapToTicks(true);
		this.slider.setValue(0);
		this.slider.setBounds(930, 14, 25, 292);
		add(this.slider);

		this.img1 = new ZoomPanLabel(this.slider);
		this.img1.setBounds(10, 0, 450, 324);
		add(this.img1);

		this.img2labl = new JTextField();
		this.img2labl.setOpaque(false);
		this.img2labl.setEditable(false);
		this.img2labl.setColumns(10);
		this.img2labl.setBorder(null);
		this.img2labl.setBounds(470, 0, 450, 14);
		add(this.img2labl);

		this.img2 = new ZoomPanLabel(this.slider);
		this.img2.setBounds(470, 0, 450, 324);
		add(this.img2);

		this.img3labl = new JTextField();
		this.img3labl.setBorder(null);
		this.img3labl.setOpaque(false);
		this.img3labl.setEditable(false);
		this.img3labl.setBounds(20, 342, 440, 14);
		add(this.img3labl);
		this.img3labl.setColumns(10);

		this.img3 = new ZoomPanLabel(this.slider);
		this.img3.setBounds(10, 328, 450, 324);
		add(this.img3);



		this.img1.sync(this.img2);
		if(this.Three){
			this.img3.sync(this.img1);
		}

		this.txtAnalysis = new JTextField();
		this.txtAnalysis.setFont(new Font("SansSerif", 0, 10));
		this.txtAnalysis.setEditable(false);
		this.txtAnalysis.setText("Analysis");
		this.txtAnalysis.setOpaque(false);
		this.txtAnalysis.setColumns(10);
		this.txtAnalysis.setBorder(null);
		this.txtAnalysis.setBounds(470, 328, 47, 20);
		add(this.txtAnalysis);

		this.txtEnhancement = new JTextField();
		this.txtEnhancement.setFont(new Font("SansSerif", 0, 10));
		this.txtEnhancement.setEditable(false);
		this.txtEnhancement.setText("Enhancement");
		this.txtEnhancement.setOpaque(false);
		this.txtEnhancement.setColumns(10);
		this.txtEnhancement.setBorder(null);
		this.txtEnhancement.setBounds(662, 328, 95, 20);
		add(this.txtEnhancement);

		this.comboBox_1 = new JComboBox();
		this.comboBox_1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (TimeSeriesPanel.this.Type == 0)
					TimeSeriesPanel.this.dataPanelType = 0;
				else
					TimeSeriesPanel.this.dataPanelType = 1;
				try {
					TimeSeriesPanel.this.ta.changeTool();
				} catch (Exception x) {
					x.printStackTrace();
				}
				TimeSeriesPanel.this.ndviChart.setVisible(false);
				TimeSeriesPanel.this.comboBox.setModel(new DefaultComboBoxModel(new String[] { "Point (text)", "Point (graph)", "Line", "Path (multiple point line)", "Rectangle", "Polygon" }));
				TimeSeriesPanel.this.comboBox.setToolTipText("Point (text)\r\nPoint (graph)\r\nLine\r\nPath (multiple point line)\r\nRectangle\r\nPolygon");
				switch (((JComboBox)e.getSource()).getSelectedIndex()) {
				case 0:
					TimeSeriesPanel.this.setImages(TimeSeriesPanel.this.Three);
					break;
				case 1:
					TimeSeriesPanel.this.setImages(TimeSeriesPanel.this.Three);
					TimeSeriesPanel.this.img1.setImage(ColorEnhances.grayScale((byte)0, TimeSeriesPanel.this.currentImage1));
					TimeSeriesPanel.this.img2.setImage(ColorEnhances.grayScale((byte)0, TimeSeriesPanel.this.currentImage2));
					if (TimeSeriesPanel.this.Three) TimeSeriesPanel.this.img3.setImage(ColorEnhances.grayScale((byte)0, TimeSeriesPanel.this.currentImage3));
					break;
				case 2:
					TimeSeriesPanel.this.setImages(TimeSeriesPanel.this.Three);
					TimeSeriesPanel.this.img1.setImage(ColorEnhances.grayScale((byte)1, TimeSeriesPanel.this.currentImage1));
					TimeSeriesPanel.this.img2.setImage(ColorEnhances.grayScale((byte)1, TimeSeriesPanel.this.currentImage2));
					if (TimeSeriesPanel.this.Three) TimeSeriesPanel.this.img3.setImage(ColorEnhances.grayScale((byte)1, TimeSeriesPanel.this.currentImage3));
					break;
				case 3:
					TimeSeriesPanel.this.setImages(TimeSeriesPanel.this.Three);
					TimeSeriesPanel.this.img1.setImage(ColorEnhances.grayScale((byte)2, TimeSeriesPanel.this.currentImage1));
					TimeSeriesPanel.this.img2.setImage(ColorEnhances.grayScale((byte)2, TimeSeriesPanel.this.currentImage2));
					if (TimeSeriesPanel.this.Three) TimeSeriesPanel.this.img3.setImage(ColorEnhances.grayScale((byte)2, TimeSeriesPanel.this.currentImage3));
					break;
				case 4:
					TimeSeriesPanel.this.setImages(TimeSeriesPanel.this.Three);
					TimeSeriesPanel.this.img1.setImage(ColorEnhances.grayScale((byte)-1, TimeSeriesPanel.this.currentImage1));
					TimeSeriesPanel.this.img2.setImage(ColorEnhances.grayScale((byte)-1, TimeSeriesPanel.this.currentImage2));
					if (TimeSeriesPanel.this.Three) TimeSeriesPanel.this.img3.setImage(ColorEnhances.grayScale((byte)-1, TimeSeriesPanel.this.currentImage3));
					break;
				case 5:
					TimeSeriesPanel.this.setImages(TimeSeriesPanel.this.Three);
					TimeSeriesPanel.this.img1.setImage(ColorEnhances.normalize(new byte[] { 0, 1 }, TimeSeriesPanel.this.currentImage1));
					TimeSeriesPanel.this.img2.setImage(ColorEnhances.normalize(new byte[] { 0, 1 }, TimeSeriesPanel.this.currentImage2));
					if (TimeSeriesPanel.this.Three) TimeSeriesPanel.this.img3.setImage(ColorEnhances.normalize(new byte[] { 0, 1 }, TimeSeriesPanel.this.currentImage3));
					break;
				case 6:
					TimeSeriesPanel.this.setImages(TimeSeriesPanel.this.Three);
					TimeSeriesPanel.this.img1.setImage(ColorEnhances.normalize(new byte[] { 0, 2 }, TimeSeriesPanel.this.currentImage1));
					TimeSeriesPanel.this.img2.setImage(ColorEnhances.normalize(new byte[] { 0, 2 }, TimeSeriesPanel.this.currentImage2));
					if (TimeSeriesPanel.this.Three) TimeSeriesPanel.this.img3.setImage(ColorEnhances.normalize(new byte[] { 0, 2 }, TimeSeriesPanel.this.currentImage3));
					break;
				case 7:
					TimeSeriesPanel.this.setImages(TimeSeriesPanel.this.Three);
					TimeSeriesPanel.this.img1.setImage(ColorEnhances.normalize(new byte[] { 2, 1 }, TimeSeriesPanel.this.currentImage1));
					TimeSeriesPanel.this.img2.setImage(ColorEnhances.normalize(new byte[] { 2, 1 }, TimeSeriesPanel.this.currentImage2));
					if (TimeSeriesPanel.this.Three) TimeSeriesPanel.this.img3.setImage(ColorEnhances.normalize(new byte[] { 2, 1 }, TimeSeriesPanel.this.currentImage3));
					break;
				case 8:
					TimeSeriesPanel.this.comboBox.setModel(new DefaultComboBoxModel(new String[] { "Point (text)", "Point (graph)", "Line", "Path (multiple point line)", "Rectangle", "Polygon", "Rectangle with NDVI Mask", "Polygon with NDVI Mask" }));
					TimeSeriesPanel.this.comboBox.setToolTipText("Point (text)\r\nPoint (graph)\r\nLine\r\nPath (multiple point line)\r\nRectangle\r\nPolygon\r\nRectangle with NDVI Mask\r\nPolygon with NDVI Mask");
					TimeSeriesPanel.this.dataPanelType = 2;
					TimeSeriesPanel.this.ndviChart.setVisible(true);
					TimeSeriesPanel.this.setImages(TimeSeriesPanel.this.Three);
					TimeSeriesPanel.this.img1.setImage(ColorTools.NDVIImage(TimeSeriesPanel.this.currentImage1));
					TimeSeriesPanel.this.img2.setImage(ColorTools.NDVIImage(TimeSeriesPanel.this.currentImage2));
					if (TimeSeriesPanel.this.Three) TimeSeriesPanel.this.img3.setImage(ColorTools.NDVIImage(TimeSeriesPanel.this.currentImage3));
					break;
				}
			}
		});
		if (type == 0)
			this.comboBox_1.setModel(new DefaultComboBoxModel(new String[] { "RGB", "Red as gray", "Green as gray", "Blue as gray", "Average as gray", "Red vs Green (normalized)", "Red vs Blue (normalized)", "Green vs Blue (normalized)" }));
		else
			this.comboBox_1.setModel(new DefaultComboBoxModel(new String[] { "NIR + Red + Green", "NIR as gray", "Red as gray", "Green as gray", "Average as gray", "(NIR-Red)/(NIR+Red)", "(NIR-Green)/(NIR+Green)", "(Red-Green)/(Red+Green)", "NDVI" }));
		this.comboBox_1.setBounds(753, 328, 166, 20);
		add(this.comboBox_1);




		this.x1 = new JTextArea();
		this.x1.setOpaque(false);
		this.x1.setText("" + 0);
		this.x1.setFont(new Font("SansSerif", 0, 11));
		this.x1.setBounds(999, 390, 32, 20);
		add(this.x1);

		this.y1 = new JTextArea();
		this.y1.setOpaque(false);
		this.y1.setText("" + 0);
		this.y1.setFont(new Font("SansSerif", 0, 11));
		this.y1.setBounds(999, 411, 32, 20);
		add(this.y1);


		this.x2 = new JTextArea();
		this.x2.setOpaque(false);
		this.x2.setText("" + 0);
		this.x2.setFont(new Font("SansSerif", 0, 11));
		this.x2.setBounds(999, 477, 32, 20);
		add(this.x2);

		this.y2 = new JTextArea();
		this.y2.setOpaque(false);
		this.y2.setText("" + 0);
		this.y2.setFont(new Font("SansSerif", 0, 11));
		this.y2.setBounds(999, 497, 32, 20);
		add(this.y2);





		this.sXP = new BasicArrowButton(BasicArrowButton.EAST, new Color(0xFF8C00), new Color(0x9F4C00), new Color(0x8F3C00), new Color(0xFF9C10));
		sXP.setBounds(970, 400, 16, 16);
		sXP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				int current = Integer.parseInt(TimeSeriesPanel.this.x1.getText());
				if (current < TimeSeriesPanel.this.img1.getOriginal().getWidth()) {
					current++;
					TimeSeriesPanel.this.x1.setText("" + current);
					TimeSeriesPanel.this.ta.mvPoint1(Integer.parseInt(TimeSeriesPanel.this.x1.getText()), Integer.parseInt(TimeSeriesPanel.this.y1.getText()));
				}
			}
		});
		add(this.sXP);
		
		this.sXM = new BasicArrowButton(BasicArrowButton.WEST, new Color(0xFF8C00), new Color(0x9F4C00), new Color(0x8F3C00), new Color(0xFF9C10));
		sXM.setBounds(938, 400, 16, 16);
		sXM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				int current = Integer.parseInt(TimeSeriesPanel.this.x1.getText());
				if (current > 0) {
					current--;
					TimeSeriesPanel.this.x1.setText("" + current);
					TimeSeriesPanel.this.ta.mvPoint1(Integer.parseInt(TimeSeriesPanel.this.x1.getText()), Integer.parseInt(TimeSeriesPanel.this.y1.getText()));
				}
			}
		});
		add(this.sXM);

		this.sYP = new BasicArrowButton(BasicArrowButton.NORTH, new Color(0xFF8C00), new Color(0x9F4C00), new Color(0x8F3C00), new Color(0xFF9C10));
		sYP.setBounds(954, 384, 16, 16);
		sYP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				int current = Integer.parseInt(TimeSeriesPanel.this.y1.getText());
				if (current > 0) {
					current--;
					TimeSeriesPanel.this.y1.setText("" + current);
					TimeSeriesPanel.this.ta.mvPoint1(Integer.parseInt(TimeSeriesPanel.this.x1.getText()), Integer.parseInt(TimeSeriesPanel.this.y1.getText()));
				}
			}
		});
		add(this.sYP);

		this.sYM = new BasicArrowButton(BasicArrowButton.SOUTH, new Color(0xFF8C00), new Color(0x9F4C00), new Color(0x8F3C00), new Color(0xFF9C10));
		sYM.setBounds(954, 416, 16, 16);
		sYM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				int current = Integer.parseInt(TimeSeriesPanel.this.y1.getText());
				if (current < TimeSeriesPanel.this.img1.getOriginal().getHeight()) {
					current++;
					TimeSeriesPanel.this.y1.setText("" + current);
					TimeSeriesPanel.this.ta.mvPoint1(Integer.parseInt(TimeSeriesPanel.this.x1.getText()), Integer.parseInt(TimeSeriesPanel.this.y1.getText()));
				}
			}
		});
		add(this.sYM);

		
		
		
		
		this.eXP = new BasicArrowButton(BasicArrowButton.EAST, new Color(0x8A309F), new Color(0x2A0030), new Color(0x10000F), new Color(0xAA50BF));
		eXP.setBounds(970, 491, 16, 16);
		eXP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				int current = Integer.parseInt(TimeSeriesPanel.this.x2.getText());
				if (current < TimeSeriesPanel.this.img1.getOriginal().getWidth()) {
					current++;
					TimeSeriesPanel.this.x2.setText("" + current);
					TimeSeriesPanel.this.ta.mvPoint2(Integer.parseInt(TimeSeriesPanel.this.x2.getText()), Integer.parseInt(TimeSeriesPanel.this.y2.getText()));
				}
			}
		});
		add(this.eXP);
		
		this.eXM = new BasicArrowButton(BasicArrowButton.WEST, new Color(0x8A309F), new Color(0x2A0030), new Color(0x10000F), new Color(0xAA50BF));
		eXM.setBounds(938, 491, 16, 16);
		eXM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				int current = Integer.parseInt(TimeSeriesPanel.this.x2.getText());
				if (current > 0) {
					current--;
					TimeSeriesPanel.this.x2.setText("" + current);
					TimeSeriesPanel.this.ta.mvPoint2(Integer.parseInt(TimeSeriesPanel.this.x2.getText()), Integer.parseInt(TimeSeriesPanel.this.y2.getText()));
				}
			}
		});
		add(this.eXM);

		this.eYP = new BasicArrowButton(BasicArrowButton.NORTH, new Color(0x8A309F), new Color(0x2A0030), new Color(0x10000F), new Color(0xAA50BF));
		eYP.setBounds(954, 475, 16, 16);
		eYP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				int current = Integer.parseInt(TimeSeriesPanel.this.y2.getText());
				if (current > 0) {
					current--;
					TimeSeriesPanel.this.y2.setText("" + current);
					TimeSeriesPanel.this.ta.mvPoint2(Integer.parseInt(TimeSeriesPanel.this.x2.getText()), Integer.parseInt(TimeSeriesPanel.this.y2.getText()));
				}
			}
		});
		add(this.eYP);

		this.eYM = new BasicArrowButton(BasicArrowButton.SOUTH, new Color(0x8A309F), new Color(0x2A0030), new Color(0x10000F), new Color(0xAA50BF));
		eYM.setBounds(954, 507, 16, 16);
		eYM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				int current = Integer.parseInt(TimeSeriesPanel.this.y2.getText());
				if (current < TimeSeriesPanel.this.img1.getOriginal().getHeight()) {
					current++;
					TimeSeriesPanel.this.y2.setText("" + current);
					TimeSeriesPanel.this.ta.mvPoint2(Integer.parseInt(TimeSeriesPanel.this.x2.getText()), Integer.parseInt(TimeSeriesPanel.this.y2.getText()));
				}
			}
		});
		add(this.eYM);






		this.pt1 = new JTextArea();
		this.pt1.setRows(2);
		this.pt1.setFont(new Font("SansSerif", Font.BOLD, 15));
		this.pt1.setForeground(new Color(0xFF8C00));
		this.pt1.setText("Start");
		this.pt1.setOpaque(false);
		this.pt1.setWrapStyleWord(true);
		this.pt1.setLineWrap(true);
		this.pt1.setBounds(969, 359, 58, 27);
		add(this.pt1);

		this.pt2 = new JTextArea();
		this.pt2.setRows(2);
		this.pt2.setWrapStyleWord(true);
		this.pt2.setForeground(new Color(0x8A309F));
		this.pt2.setText("End");
		this.pt2.setOpaque(false);
		this.pt2.setLineWrap(true);
		this.pt2.setFont(new Font("SansSerif", Font.BOLD, 15));
		this.pt2.setBounds(969, 450, 58, 27);
		add(this.pt2);

		

		/* this.x1 = new JSpinner();
     this.x1.addChangeListener(new ChangeListener() {
       public void stateChanged(ChangeEvent arg0) {
         TimeSeriesPanel.this.ta.mvPoint1(((Integer)TimeSeriesPanel.this.x1.getValue()).intValue(), ((Integer)TimeSeriesPanel.this.y1.getValue()).intValue());
       }
     });
     this.x1.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
     this.x1.setOpaque(false);
     this.x1.setFont(new Font("SansSerif", 0, 11));
     this.x1.setBounds(954, 410, 72, 20);
     add(this.x1);

     this.y1 = new JSpinner();
     this.y1.addChangeListener(new ChangeListener() {
       public void stateChanged(ChangeEvent arg0) {
         TimeSeriesPanel.this.ta.mvPoint1(((Integer)TimeSeriesPanel.this.x1.getValue()).intValue(), ((Integer)TimeSeriesPanel.this.y1.getValue()).intValue());
       }
     });
     this.y1.setOpaque(false);
     this.y1.setFont(new Font("SansSerif", 0, 11));
     this.y1.setBounds(954, 431, 72, 20);
     add(this.y1);
		 */

		/*
     this.x2 = new JSpinner();
     this.x2.addChangeListener(new ChangeListener() {
       public void stateChanged(ChangeEvent arg0) {
         TimeSeriesPanel.this.ta.mvPoint2(((Integer)TimeSeriesPanel.this.x2.getValue()).intValue(), ((Integer)TimeSeriesPanel.this.y2.getValue()).intValue());
       }
     });
     this.x2.setOpaque(false);
     this.x2.setFont(new Font("SansSerif", 0, 11));
     this.x2.setBounds(954, 497, 72, 20);
     add(this.x2);

     this.y2 = new JSpinner();
     this.y2.addChangeListener(new ChangeListener() {
       public void stateChanged(ChangeEvent arg0) {
         TimeSeriesPanel.this.ta.mvPoint2(((Integer)TimeSeriesPanel.this.x2.getValue()).intValue(), ((Integer)TimeSeriesPanel.this.y2.getValue()).intValue());
       }
     });
     this.y2.setOpaque(false);
     this.y2.setFont(new Font("SansSerif", 0, 11));
     this.y2.setBounds(954, 517, 72, 20);
     add(this.y2);*/























		this.txtLengthArea = new JTextArea();
		this.txtLengthArea.setVisible(false);
		this.txtLengthArea.setEditable(false);
		this.txtLengthArea.setText("Length/Area");
		this.txtLengthArea.setOpaque(false);
		this.txtLengthArea.setLineWrap(true);
		this.txtLengthArea.setWrapStyleWord(true);
		this.txtLengthArea.setFont(new Font("SansSerif", 0, 9));
		this.txtLengthArea.setBounds(954, 537, 72, 20);
		add(this.txtLengthArea);

		this.txtNumPixels = new JTextArea();
		this.txtNumPixels.setVisible(false);
		this.txtNumPixels.setEditable(false);
		this.txtNumPixels.setWrapStyleWord(true);
		this.txtNumPixels.setText("Number of Pixels");
		this.txtNumPixels.setOpaque(false);
		this.txtNumPixels.setLineWrap(true);
		this.txtNumPixels.setFont(new Font("SansSerif", 0, 9));
		this.txtNumPixels.setBounds(954, 589, 72, 28);
		add(this.txtNumPixels);

		this.lengthArea = new JTextArea();
		this.lengthArea.setVisible(false);
		this.lengthArea.setOpaque(false);
		this.lengthArea.setWrapStyleWord(true);
		this.lengthArea.setLineWrap(true);
		this.lengthArea.setEditable(false);
		this.lengthArea.setBounds(953, 555, 73, 23);
		add(this.lengthArea);

		this.numPixels = new JTextArea();
		this.numPixels.setVisible(false);
		this.numPixels.setOpaque(false);
		this.numPixels.setWrapStyleWord(true);
		this.numPixels.setLineWrap(true);
		this.numPixels.setEditable(false);
		this.numPixels.setBounds(954, 620, 72, 23);
		add(this.numPixels);

		this.dataPanel = new JPanel();
		this.dataPanel.setBorder(null);
		this.dataPanel.setBounds(470, 352, 450, 300);
		add(this.dataPanel);
		this.dataPanel.setLayout(null);

		this.textField = new JTextField();
		this.textField.setText("Zoom");
		this.textField.setOpaque(false);
		this.textField.setEditable(false);
		this.textField.setColumns(10);
		this.textField.setBorder(null);
		this.textField.setBounds(929, 307, 41, 17);
		add(this.textField);

		this.ndviChart = new JLabel("");
		this.ndviChart.addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent arg0) {
				new NDVIInfoDialog().setVisible(true);
			}
		});
		this.ndviChart.setVisible(false);
		this.ndviChart.setIcon(new ImageIcon(TimeSeriesPanel.class.getResource("/resources/NDVIchart.gif")));
		this.ndviChart.setBounds(980, 11, 46, 295);
		add(this.ndviChart);

		this.displayTitles = new JCheckBox("Display Titles");
		this.displayTitles.setSelected(true);
		this.displayTitles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TimeSeriesPanel.this.titlesToFront(TimeSeriesPanel.this.displayTitles.isSelected());
			}
		});
		this.displayTitles.setBounds(930, 328, 126, 23);
		add(this.displayTitles);
	}

	class pixelDrag extends MouseMotionAdapter
	{
		pixelDrag()
		{
		}

		public void mouseDragged(MouseEvent e)
		{
			TimeSeriesPanel.this.handlePixels();
		}
	}

	class pixelMouse extends MouseAdapter
	{
		pixelMouse()
		{
		}

		public void mousePressed(MouseEvent e)
		{
			TimeSeriesPanel.this.handlePixels();
		}

		public void mouseReleased(MouseEvent e) {
			TimeSeriesPanel.this.handlePixels();
		}
	}
}

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.TimeSeriesPanel
 * JD-Core Version:    0.6.2
 */