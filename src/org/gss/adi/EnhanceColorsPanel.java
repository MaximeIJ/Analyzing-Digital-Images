package org.gss.adi;

import java.awt.Adjustable;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.util.Dictionary;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.gss.adi.tools.ColorEnhances;

public class EnhanceColorsPanel extends ImagePanel
{
	private static final long serialVersionUID = 1L;
	private JTextField txtEnhanceRed;
	private JTextField txtMin;
	private JTextField txtMax;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField txtEnhanceGreen;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField txtEnhanceBlue;
	private Entrance entrance;
	private JComboBox comboBox;
	private ButtonGroup redGroup, greenGroup, blueGroup;
	private JRadioButton redLimit;
	private JRadioButton redOff;
	private JRadioButton redStretch;
	private JCheckBox redInvert;
	private JSlider redMax;
	private JSlider redMin;
	private JSlider greenMax;
	private JRadioButton greenStretch;
	private JRadioButton greenLimit;
	private JRadioButton greenOff;
	private JSlider greenMin;
	private JCheckBox greenInvert;
	private JSlider blueMax;
	private JRadioButton blueStretch;
	private JRadioButton blueLimit;
	private JRadioButton blueOff;
	private JCheckBox blueInvert;
	private JSlider blueMin;
	private JLabel redColorDist;
	private JLabel greenColorDist;
	private JLabel blueColorDist;
	private JTextField rmin;
	private JTextField rmax;
	private JTextField gmin;
	private JTextField gmax;
	private JTextField bmin;
	private JTextField bmax;

	//Statics for memory
	static final EnhanceColorsMemory memory = new EnhanceColorsMemory();

	public EnhanceColorsPanel(Entrance ent)
	{
		super(ent, false);
		this.entrance = ent;
		if (this.entrance.getImage() == null) {
			return;
		}
		setLayout(null);
		setup();
		this.label.setImage(this.entrance.getImage());
		setColorDists();
		enhance();
		zoomFromMemory(memory);
		comboBoxChange();
	}
	public void updateTool() {
	}

	public void updatePic() {
		super.updatePic();
		setColorDists();
	}

	public void save(){
		// Save zoom
		memory.setX(this.label.getHorizontalScrollBar().getValue());
		memory.setY(this.label.getVerticalScrollBar().getValue());
		memory.setZoomFactor(this.label.zoomFactor);

		// Save EnhanceColor Specifics
		memory.setEnhancement(this.comboBox.getSelectedIndex());

		int rg, gg, bg;
		Enumeration<AbstractButton> re, ge, be;
		rg = 0; 
		re = redGroup.getElements();
		while(re.hasMoreElements()){
			if(re.nextElement().isSelected())
				break;
			rg++;
		}
		gg = 0; 
		ge = greenGroup.getElements();
		while(ge.hasMoreElements()){
			if(ge.nextElement().isSelected())
				break;
			gg++;
		}
		bg = 0; 
		be = blueGroup.getElements();
		while(be.hasMoreElements()){
			if(be.nextElement().isSelected())
				break;
			bg++;
		}

		memory.setR(new int[]{this.redMin.getValue(), this.redMax.getValue(), this.redInvert.isSelected()? 1:0, rg});
		memory.setG(new int[]{this.greenMin.getValue(), this.greenMax.getValue(), this.greenInvert.isSelected()? 1:0, gg});
		memory.setB(new int[]{this.blueMin.getValue(), this.blueMax.getValue(), this.blueInvert.isSelected()? 1:0, bg});

		

	}


	protected void closingSequence() {
		save();
		this.label.qualityZoom(100);
		comboBoxChange();
		if (this.label.getZoomedTool() == null)
			this.entrance.setEnhancedImage(this.label.getOriginal());
		else
			this.entrance.setEnhancedImage(this.label.getZoomedTool());
	}

	byte getType()
	{
		return 1;
	}

	void slideComplete() {
		super.slideComplete();
		comboBoxChange();
	}

	private void setColorDists()
	{
		BufferedImage[] dists = ColorEnhances.getColorDistributions(this.label.getZoomedOriginal());
		this.redColorDist.setIcon(new ImageIcon(dists[0]));
		this.greenColorDist.setIcon(new ImageIcon(dists[1]));
		this.blueColorDist.setIcon(new ImageIcon(dists[2]));
	}

	private void enhance()
	{
		long startTime = System.currentTimeMillis();

		BufferedImage enhanced = ZoomPanLabel.resize(this.slider.getValue(), invert(off(limit(stretch(this.label.getOriginal())))));
		System.out.println("new enhancement took " + (System.currentTimeMillis() - startTime) + " milliseconds to complete.");
		this.label.setZoomedOriginal(enhanced);
	}

	private BufferedImage invert(BufferedImage img)
	{
		boolean[] colors = new boolean[3];
		if (this.redInvert.isSelected())
			colors[0] = true;
		else
			colors[0] = false;
		if (this.greenInvert.isSelected())
			colors[1] = true;
		else
			colors[1] = false;
		if (this.blueInvert.isSelected())
			colors[2] = true;
		else
			colors[2] = false;
		return ColorEnhances.newInvert(colors, img);
	}

	private BufferedImage limit(BufferedImage img)
	{
		int[] mins = new int[3];
		int[] maxs = new int[3];
		if (this.redLimit.isSelected()) {
			mins[0] = this.redMin.getValue();
			maxs[0] = this.redMax.getValue();
		} else {
			mins[0] = 0;
			maxs[0] = 256;
		}
		if (this.greenLimit.isSelected()) {
			mins[1] = this.greenMin.getValue();
			maxs[1] = this.greenMax.getValue();
		} else {
			mins[1] = 0;
			maxs[1] = 256;
		}
		if (this.blueLimit.isSelected()) {
			mins[2] = this.blueMin.getValue();
			maxs[2] = this.blueMax.getValue();
		} else {
			mins[2] = 0;
			maxs[2] = 256;
		}
		return ColorEnhances.newLimitColor(mins, maxs, img);
	}

	private BufferedImage stretch(BufferedImage img)
	{
		int[] mins = new int[3];
		int[] maxs = new int[3];
		if (this.redStretch.isSelected()) {
			mins[0] = this.redMin.getValue();
			maxs[0] = this.redMax.getValue();
		} else {
			mins[0] = 0;
			maxs[0] = 256;
		}
		if (this.greenStretch.isSelected()) {
			mins[1] = this.greenMin.getValue();
			maxs[1] = this.greenMax.getValue();
		} else {
			mins[1] = 0;
			maxs[1] = 256;
		}
		if (this.blueStretch.isSelected()) {
			mins[2] = this.blueMin.getValue();
			maxs[2] = this.blueMax.getValue();
		} else {
			mins[2] = 0;
			maxs[2] = 256;
		}
		return ColorEnhances.newStretchColor(mins, maxs, img);
	}

	private BufferedImage off(BufferedImage img)
	{
		int size = 0;
		if (this.redOff.isSelected())
			size++;
		if (this.greenOff.isSelected())
			size++;
		if (this.blueOff.isSelected()) {
			size++;
		}
		byte[] color = new byte[size];
		int[] start = new int[size];
		int[] end = new int[size];
		size = 0;
		if (this.redOff.isSelected()) {
			color[size] = 0;
			start[size] = this.redMin.getValue();
			end[size] = this.redMax.getValue();
			size++;
		}if (this.greenOff.isSelected()) {
			color[size] = 1;
			start[size] = this.greenMin.getValue();
			end[size] = this.greenMax.getValue();
			size++;
		}if (this.blueOff.isSelected()) {
			color[size] = 2;
			start[size] = this.blueMin.getValue();
			end[size] = this.blueMax.getValue();
			size++;
		}
		return ColorEnhances.newOffColor(color, img);
	}

	private void comboBoxChange()
	{
		switch (this.comboBox.getSelectedIndex()) {
		case 0:
			enhance();
			break;
		case 1:
			this.label.setZoomedTool(ColorEnhances.grayScale((byte)0, this.label.getZoomedOriginal()));
			break;
		case 2:
			this.label.setZoomedTool(ColorEnhances.grayScale((byte)1, this.label.getZoomedOriginal()));
			break;
		case 3:
			this.label.setZoomedTool(ColorEnhances.grayScale((byte)2, this.label.getZoomedOriginal()));
			break;
		case 4:
			this.label.setZoomedTool(ColorEnhances.grayScale((byte)-1, this.label.getZoomedOriginal()));
			break;
		case 5:
			this.label.setZoomedTool(ColorEnhances.normalize(new byte[] { 0, 1 }, this.label.getZoomedOriginal()));
			break;
		case 6:
			this.label.setZoomedTool(ColorEnhances.normalize(new byte[] { 0, 2 }, this.label.getZoomedOriginal()));
			break;
		case 7:
			this.label.setZoomedTool(ColorEnhances.normalize(new byte[] { 1, 2 }, this.label.getZoomedOriginal()));
		}
	}

	private void setup() {
		/*this.rmin = new JTextField((100*memory.getR()[0]/256) + "%");
		this.rmin.setHorizontalAlignment(11);
		this.rmin.setEditable(false);
		this.rmin.setColumns(10);
		this.rmin.setBorder(null);
		this.rmin.setOpaque(false);
		this.rmin.setBounds(0, 242, 43, 20);
		add(this.rmin);
		this.rmax = new JTextField((100*memory.getR()[1]/256) + "%");
		this.rmax.setHorizontalAlignment(11);
		this.rmax.setEditable(false);
		this.rmax.setColumns(10);
		this.rmax.setBorder(null);
		this.rmax.setOpaque(false);
		this.rmax.setBounds(0, 307, 43, 32);
		add(this.rmax);
		this.gmin = new JTextField((100*memory.getG()[0]/256) + "%");
		this.gmin.setHorizontalAlignment(11);
		this.gmin.setEditable(false);
		this.gmin.setColumns(10);
		this.gmin.setBorder(null);
		this.gmin.setOpaque(false);
		this.gmin.setBounds(0, 374, 43, 20);
		add(this.gmin);
		this.gmax = new JTextField((100*memory.getG()[1]/256) + "%");
		this.gmax.setHorizontalAlignment(11);
		this.gmax.setEditable(false);
		this.gmax.setColumns(10);
		this.gmax.setBorder(null);
		this.gmax.setOpaque(false);
		this.gmax.setBounds(0, 439, 43, 20);
		add(this.gmax);
		this.bmin = new JTextField((100*memory.getB()[0]/256) + "%");
		this.bmin.setHorizontalAlignment(11);
		this.bmin.setEditable(false);
		this.bmin.setColumns(10);
		this.bmin.setBorder(null);
		this.bmin.setOpaque(false);
		this.bmin.setBounds(0, 506, 43, 20);
		add(this.bmin);
		this.bmax = new JTextField((100*memory.getB()[1]/256) + "%");
		this.bmax.setHorizontalAlignment(11);
		this.bmax.setEditable(false);
		this.bmax.setColumns(10);
		this.bmax.setBorder(null);
		this.bmax.setOpaque(false);
		this.bmax.setBounds(0, 571, 43, 20);
		add(this.bmax);*/
		JTextArea txtrModifyTheColors = new JTextArea();
		txtrModifyTheColors.setOpaque(false);
		txtrModifyTheColors.setEditable(false);
		txtrModifyTheColors.setFont(new Font("SansSerif", 1, 14));
		txtrModifyTheColors.setText("Modify the colors of a digital image so it easier to see spatial and spectral (color) relationships.");
		txtrModifyTheColors.setWrapStyleWord(true);
		txtrModifyTheColors.setLineWrap(true);
		txtrModifyTheColors.setBounds(10, 5, 326, 61);
		add(txtrModifyTheColors);

		JTextArea txtrProcessingLargeImages = new JTextArea();
		txtrProcessingLargeImages.setFont(new Font("SansSerif", 0, 13));
		txtrProcessingLargeImages.setLineWrap(true);
		txtrProcessingLargeImages.setWrapStyleWord(true);
		txtrProcessingLargeImages.setText("Processing large images takes time – be patient or trim the image to make it smaller.");
		txtrProcessingLargeImages.setOpaque(false);
		txtrProcessingLargeImages.setBounds(10, 70, 318, 40);
		add(txtrProcessingLargeImages);

		JTextArea txtrSelectPredefined = new JTextArea();
		txtrSelectPredefined.setEditable(false);
		txtrSelectPredefined.setOpaque(false);
		txtrSelectPredefined.setText("1) Select Pre-defined Enhancement");
		txtrSelectPredefined.setFont(new Font("SansSerif", 0, 13));
		txtrSelectPredefined.setBounds(10, 110, 318, 22);
		add(txtrSelectPredefined);

		this.comboBox = new JComboBox();
		this.comboBox.setModel(new DefaultComboBoxModel(new String[] { "Original Image", "Gray Image of Red Intensities", 
				"Gray Image of Green Intensities", "Gray Image of Blue Intensities", "Gray Image of Average Intensities", 
				"Red vs Green (normalized)", "Red vs Blue (normalized)", "Green vs Blue (normalized)" }));
		this.comboBox.setBounds(10, 132, 306, 20);
		add(this.comboBox);
		this.comboBox.setSelectedIndex(memory.getEnhancement());

		JTextArea txtrCustomEnhancement = new JTextArea();
		txtrCustomEnhancement.setText("2) Custom Enhancement");
		txtrCustomEnhancement.setOpaque(false);
		txtrCustomEnhancement.setFont(new Font("SansSerif", 0, 13));
		txtrCustomEnhancement.setEditable(false);
		txtrCustomEnhancement.setBounds(10, 154, 318, 22);
		add(txtrCustomEnhancement);

		this.txtEnhanceRed = new JTextField();
		this.txtEnhanceRed.setBorder(null);
		this.txtEnhanceRed.setOpaque(false);
		this.txtEnhanceRed.setEditable(false);
		this.txtEnhanceRed.setFont(new Font("Tahoma", 2, 11));
		this.txtEnhanceRed.setText("Enhance Red");
		this.txtEnhanceRed.setHorizontalAlignment(0);
		this.txtEnhanceRed.setBounds(10, 176, 306, 20);
		add(this.txtEnhanceRed);
		this.txtEnhanceRed.setColumns(10);

		this.redInvert = new JCheckBox("Invert");
		this.redInvert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EnhanceColorsPanel.this.enhance();
				EnhanceColorsPanel.this.comboBox.setSelectedIndex(0);
			}
		});
		this.redInvert.setFont(new Font("SansSerif", 0, 10));
		this.redInvert.setBounds(0, 192, 63, 23);
		add(this.redInvert);
		this.redInvert.setSelected((memory.getR()[2]==1));

		this.redOff = new JRadioButton("Off");
		this.redOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EnhanceColorsPanel.this.enhance();
				EnhanceColorsPanel.this.comboBox.setSelectedIndex(0);
			}
		});
		this.redOff.setFont(new Font("SansSerif", 0, 11));
		this.redOff.setBounds(63, 194, 70, 23);
		add(this.redOff);

		this.redLimit = new JRadioButton("Limit");
		this.redLimit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EnhanceColorsPanel.this.enhance();
				EnhanceColorsPanel.this.comboBox.setSelectedIndex(0);
			}
		});
		this.redLimit.setSelected(true);
		this.redLimit.setFont(new Font("SansSerif", 0, 11));
		this.redLimit.setBounds(135, 194, 82, 23);
		add(this.redLimit);

		this.redStretch = new JRadioButton("Stretch");
		this.redStretch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EnhanceColorsPanel.this.enhance();
				EnhanceColorsPanel.this.comboBox.setSelectedIndex(0);
			}
		});
		this.redStretch.setFont(new Font("SansSerif", 0, 11));
		this.redStretch.setBounds(219, 194, 81, 23);
		add(this.redStretch);

		redGroup = new ButtonGroup();
		redGroup.add(this.redOff);
		redGroup.add(this.redLimit);
		redGroup.add(this.redStretch);
		this.redOff.setSelected((memory.getR()[3]==0));
		this.redLimit.setSelected((memory.getR()[3]==1));
		this.redStretch.setSelected((memory.getR()[3]==2));

		
		UIManager.getLookAndFeelDefaults().put("Slider.paintValue", true);
		UIManager.put("Slider.paintValue", true);
		this.redMax = new JSlider();
		this.redMax.setMinorTickSpacing(1);
		this.redMax.setMaximum(256);
		this.redMax.setPaintTrack(false);
		this.redMax.setMajorTickSpacing(10);
		this.redMax.setUI(new CustomSliderUI(this.redMax, "Max"));
		this.redMax.setSnapToTicks(true);
		this.redMax.setValue(memory.getR()[1]);
		this.redMax.setBounds(46, 287, 269, 20);
		add(this.redMax);

		this.redMin = new JSlider();
		this.redMin.setMinorTickSpacing(1);
		this.redMin.setMaximum(256);
		this.redMin.setPaintTrack(false);
		this.redMin.setMajorTickSpacing(10);
		this.redMin.setUI(new CustomSliderUI(this.redMin, "Min"));
		this.redMin.setSnapToTicks(true);
		this.redMin.setValue(memory.getR()[0]);
		this.redMin.setBounds(46, 222, 269, 20);
		add(this.redMin);
		this.redMin.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (EnhanceColorsPanel.this.redMin.getValue() > EnhanceColorsPanel.this.redMax.getValue()) {
					EnhanceColorsPanel.this.redMin.setValue(EnhanceColorsPanel.this.redMax.getValue());
					EnhanceColorsPanel.this.redMin.repaint();
				}
				//EnhanceColorsPanel.this.rmin.setText(100 * EnhanceColorsPanel.this.redMin.getValue() / 256 + "%");
				EnhanceColorsPanel.this.enhance();
				EnhanceColorsPanel.this.comboBox.setSelectedIndex(0);
			}
		});
		this.redMax.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (EnhanceColorsPanel.this.redMax.getValue() < EnhanceColorsPanel.this.redMin.getValue()) {
					EnhanceColorsPanel.this.redMax.setValue(EnhanceColorsPanel.this.redMin.getValue());
					EnhanceColorsPanel.this.redMax.repaint();
				}
				//EnhanceColorsPanel.this.rmax.setText(100 * EnhanceColorsPanel.this.redMax.getValue() / 256 + "%");
				EnhanceColorsPanel.this.enhance();
				EnhanceColorsPanel.this.comboBox.setSelectedIndex(0);
			}
		});
		this.redColorDist = new JLabel();
		this.redColorDist.setBorder(new LineBorder(new Color(0, 0, 0)));
		this.redColorDist.setBounds(53, 242, 255, 45);
		add(this.redColorDist);

/*		this.txtMin = new JTextField();
		this.txtMin.setHorizontalAlignment(11);
		this.txtMin.setEditable(false);
		this.txtMin.setBorder(null);
		this.txtMin.setText("min");
		this.txtMin.setOpaque(false);
		this.txtMin.setBounds(12, 222, 31, 20);
		add(this.txtMin);
		this.txtMin.setColumns(10);

		this.txtMax = new JTextField();
		this.txtMax.setHorizontalAlignment(11);
		this.txtMax.setText("max");
		this.txtMax.setEditable(false);
		this.txtMax.setColumns(10);
		this.txtMax.setBorder(null);
		this.txtMax.setOpaque(false);
		this.txtMax.setBounds(12, 287, 31, 20);
		add(this.txtMax);*/

		this.greenMax = new JSlider();
		this.greenMax.setMinorTickSpacing(1);
		this.greenMax.setMaximum(256);
		this.greenMax.setPaintTrack(false);
		this.greenMax.setMajorTickSpacing(10);
		this.greenMax.setUI(new CustomSliderUI(this.greenMax, "Max"));
		this.greenMax.setSnapToTicks(true);
		this.greenMax.setValue((memory.getG()[1]));
		this.greenMax.setBounds(46, 419, 269, 20);
		add(this.greenMax);

		this.greenColorDist = new JLabel();
		this.greenColorDist.setBorder(new LineBorder(new Color(0, 0, 0)));
		this.greenColorDist.setBounds(53, 374, 255, 45);
		add(this.greenColorDist);

		/*this.textField = new JTextField();
		this.textField.setHorizontalAlignment(11);
		this.textField.setText("min");
		this.textField.setEditable(false);
		this.textField.setColumns(10);
		this.textField.setBorder(null);
		this.textField.setOpaque(false);
		this.textField.setBounds(12, 354, 31, 20);
		add(this.textField);

		this.textField_1 = new JTextField();
		this.textField_1.setHorizontalAlignment(11);
		this.textField_1.setText("max");
		this.textField_1.setEditable(false);
		this.textField_1.setColumns(10);
		this.textField_1.setBorder(null);
		this.textField_1.setOpaque(false);
		this.textField_1.setBounds(12, 419, 31, 20);
		add(this.textField_1);*/

		this.greenStretch = new JRadioButton("Stretch");
		this.greenStretch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EnhanceColorsPanel.this.enhance();
				EnhanceColorsPanel.this.comboBox.setSelectedIndex(0);
			}
		});
		this.greenStretch.setFont(new Font("SansSerif", 0, 11));
		this.greenStretch.setBounds(219, 326, 81, 23);
		add(this.greenStretch);

		this.greenLimit = new JRadioButton("Limit");
		this.greenLimit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EnhanceColorsPanel.this.enhance();
				EnhanceColorsPanel.this.comboBox.setSelectedIndex(0);
			}
		});
		this.greenLimit.setSelected(true);
		this.greenLimit.setFont(new Font("SansSerif", 0, 11));
		this.greenLimit.setBounds(135, 326, 82, 23);
		add(this.greenLimit);

		this.txtEnhanceGreen = new JTextField();
		this.txtEnhanceGreen.setText("Enhance Green");
		this.txtEnhanceGreen.setOpaque(false);
		this.txtEnhanceGreen.setHorizontalAlignment(0);
		this.txtEnhanceGreen.setFont(new Font("Tahoma", 2, 11));
		this.txtEnhanceGreen.setEditable(false);
		this.txtEnhanceGreen.setColumns(10);
		this.txtEnhanceGreen.setBorder(null);
		this.txtEnhanceGreen.setBounds(10, 310, 306, 20);
		add(this.txtEnhanceGreen);

		this.greenOff = new JRadioButton("Off");
		this.greenOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EnhanceColorsPanel.this.enhance();
				EnhanceColorsPanel.this.comboBox.setSelectedIndex(0);
			}
		});
		this.greenOff.setFont(new Font("SansSerif", 0, 11));
		this.greenOff.setBounds(63, 326, 70, 23);
		add(this.greenOff);

		greenGroup = new ButtonGroup();
		greenGroup.add(this.greenOff);
		greenGroup.add(this.greenLimit);
		greenGroup.add(this.greenStretch);
		this.greenOff.setSelected((memory.getG()[3]==0));
		this.greenLimit.setSelected((memory.getG()[3]==1));
		this.greenStretch.setSelected((memory.getG()[3]==2));

		this.greenInvert = new JCheckBox("Invert");
		this.greenInvert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EnhanceColorsPanel.this.enhance();
				EnhanceColorsPanel.this.comboBox.setSelectedIndex(0);
			}
		});
		this.greenInvert.setFont(new Font("SansSerif", 0, 11));
		this.greenInvert.setBounds(0, 325, 63, 23);
		add(this.greenInvert);
		this.greenInvert.setSelected((memory.getG()[2]==1));

		this.greenMin = new JSlider();
		this.greenMin.setMinorTickSpacing(1);
		this.greenMin.setMaximum(256);
		this.greenMin.setPaintTrack(false);
		this.greenMin.setMajorTickSpacing(10);
		this.greenMin.setSnapToTicks(true);
		this.greenMin.setUI(new CustomSliderUI(this.greenMin, "Min"));
		this.greenMin.setValue((memory.getG()[0]));
		this.greenMin.setBounds(46, 354, 269, 20);
		add(this.greenMin);

		this.greenMin.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (EnhanceColorsPanel.this.greenMin.getValue() > EnhanceColorsPanel.this.greenMax.getValue()) {
					EnhanceColorsPanel.this.greenMin.setValue(EnhanceColorsPanel.this.greenMax.getValue());
					EnhanceColorsPanel.this.greenMin.repaint();
				}
				//EnhanceColorsPanel.this.gmin.setText(100 * EnhanceColorsPanel.this.greenMin.getValue() / 256 + "%");
				EnhanceColorsPanel.this.enhance();
				EnhanceColorsPanel.this.comboBox.setSelectedIndex(0);
			}
		});
		this.greenMax.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (EnhanceColorsPanel.this.greenMax.getValue() < EnhanceColorsPanel.this.greenMin.getValue()) {
					EnhanceColorsPanel.this.greenMax.setValue(EnhanceColorsPanel.this.greenMin.getValue());
					EnhanceColorsPanel.this.greenMax.repaint();
				}
				//EnhanceColorsPanel.this.gmax.setText(100 * EnhanceColorsPanel.this.greenMax.getValue() / 256 + "%");
				EnhanceColorsPanel.this.enhance();
				EnhanceColorsPanel.this.comboBox.setSelectedIndex(0);
			}
		});
		this.blueMax = new JSlider();
		this.blueMax.setMinorTickSpacing(1);
		this.blueMax.setMaximum(256);
		this.blueMax.setPaintTrack(false);
		this.blueMax.setMajorTickSpacing(10);
		this.blueMax.setSnapToTicks(true);
		this.blueMax.setUI(new CustomSliderUI(this.blueMax, "Max"));
		this.blueMax.setValue((memory.getB()[1]));
		this.blueMax.setBounds(46, 551, 269, 20);
		add(this.blueMax);

		this.blueColorDist = new JLabel();
		this.blueColorDist.setBorder(new LineBorder(new Color(0, 0, 0)));
		this.blueColorDist.setBounds(53, 506, 255, 45);
		add(this.blueColorDist);

	/*	this.textField_3 = new JTextField();
		this.textField_3.setHorizontalAlignment(11);
		this.textField_3.setText("min");
		this.textField_3.setEditable(false);
		this.textField_3.setColumns(10);
		this.textField_3.setBorder(null);
		this.textField_3.setOpaque(false);
		this.textField_3.setBounds(12, 486, 31, 20);
		add(this.textField_3);

		this.textField_4 = new JTextField();
		this.textField_4.setHorizontalAlignment(11);
		this.textField_4.setText("max");
		this.textField_4.setEditable(false);
		this.textField_4.setColumns(10);
		this.textField_4.setBorder(null);
		this.textField_4.setOpaque(false);
		this.textField_4.setBounds(12, 551, 31, 20);
		add(this.textField_4);*/

		this.blueStretch = new JRadioButton("Stretch");
		this.blueStretch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EnhanceColorsPanel.this.enhance();
				EnhanceColorsPanel.this.comboBox.setSelectedIndex(0);
			}
		});
		this.blueStretch.setFont(new Font("SansSerif", 0, 11));
		this.blueStretch.setBounds(219, 458, 81, 23);
		add(this.blueStretch);

		this.blueLimit = new JRadioButton("Limit");
		this.blueLimit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EnhanceColorsPanel.this.enhance();
				EnhanceColorsPanel.this.comboBox.setSelectedIndex(0);
			}
		});
		this.blueLimit.setSelected(true);
		this.blueLimit.setFont(new Font("SansSerif", 0, 11));
		this.blueLimit.setBounds(135, 458, 82, 23);
		add(this.blueLimit);

		this.txtEnhanceBlue = new JTextField();
		this.txtEnhanceBlue.setText("Enhance Blue");
		this.txtEnhanceBlue.setOpaque(false);
		this.txtEnhanceBlue.setHorizontalAlignment(0);
		this.txtEnhanceBlue.setFont(new Font("Tahoma", 2, 11));
		this.txtEnhanceBlue.setEditable(false);
		this.txtEnhanceBlue.setColumns(10);
		this.txtEnhanceBlue.setBorder(null);
		this.txtEnhanceBlue.setBounds(8, 442, 306, 20);
		add(this.txtEnhanceBlue);

		this.blueOff = new JRadioButton("Off");
		this.blueOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EnhanceColorsPanel.this.enhance();
				EnhanceColorsPanel.this.comboBox.setSelectedIndex(0);
			}
		});
		this.blueOff.setFont(new Font("SansSerif", 0, 11));
		this.blueOff.setBounds(63, 458, 70, 23);
		add(this.blueOff);

		blueGroup = new ButtonGroup();
		blueGroup.add(this.blueOff);
		blueGroup.add(this.blueLimit);
		blueGroup.add(this.blueStretch);
		this.blueOff.setSelected((memory.getB()[3]==0));
		this.blueLimit.setSelected((memory.getB()[3]==1));
		this.blueStretch.setSelected((memory.getB()[3]==2));

		this.blueInvert = new JCheckBox("Invert");
		this.blueInvert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EnhanceColorsPanel.this.enhance();
				EnhanceColorsPanel.this.comboBox.setSelectedIndex(0);
			}
		});
		this.blueInvert.setFont(new Font("SansSerif", 0, 11));
		this.blueInvert.setBounds(0, 458, 63, 23);
		add(this.blueInvert);
		this.blueInvert.setSelected((memory.getB()[2]==1));

		this.blueMin = new JSlider();
		this.blueMin.setMinorTickSpacing(1);
		this.blueMin.setMaximum(256);
		this.blueMin.setPaintTrack(false);
		this.blueMin.setMajorTickSpacing(10);
		this.blueMin.setSnapToTicks(true);
		this.blueMin.setUI(new CustomSliderUI(this.blueMin, "Min"));
		this.blueMin.setValue((memory.getB()[0]));
		this.blueMin.setBounds(47, 486, 269, 20);
		add(this.blueMin);
		
		// Add all sliders at the end so they're above the color distributions
		
//		add(this.redMax);
//		add(this.redMin);
//		add(this.blueMin);
//		add(this.blueMax);
//		add(this.greenMax);
//		add(this.greenMin);
//		add(this.redMin);
//		add(this.redMax);
		
		
		
		this.blueMin.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (EnhanceColorsPanel.this.blueMin.getValue() > EnhanceColorsPanel.this.blueMax.getValue()) {
					EnhanceColorsPanel.this.blueMin.setValue(EnhanceColorsPanel.this.blueMax.getValue());
					EnhanceColorsPanel.this.blueMin.repaint();
				}
				//EnhanceColorsPanel.this.bmin.setText(100 * EnhanceColorsPanel.this.blueMin.getValue() / 256 + "%");
				EnhanceColorsPanel.this.enhance();
				EnhanceColorsPanel.this.comboBox.setSelectedIndex(0);
			}
		});
		this.blueMax.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (EnhanceColorsPanel.this.blueMax.getValue() < EnhanceColorsPanel.this.blueMin.getValue()) {
					EnhanceColorsPanel.this.blueMax.setValue(EnhanceColorsPanel.this.blueMin.getValue());
					EnhanceColorsPanel.this.blueMax.repaint();
				}
				//EnhanceColorsPanel.this.bmax.setText(100 * EnhanceColorsPanel.this.blueMax.getValue() / 256 + "%");
				EnhanceColorsPanel.this.enhance();
				EnhanceColorsPanel.this.comboBox.setSelectedIndex(0);
			}
		});

		JButton btnNewButton_1 = new JButton("Use Enhanced Image as Original Image");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EnhanceColorsPanel.this.label.zoom(100);
				EnhanceColorsPanel.this.entrance.setImage(EnhanceColorsPanel.this.label.getZoomedOriginal());
				EnhanceColorsPanel.this.label.setImage(EnhanceColorsPanel.this.entrance.getImage());
				EnhanceColorsPanel.this.setColorDists();
				EnhanceColorsPanel.this.redMax.setValue(256);
				EnhanceColorsPanel.this.redMin.setValue(0);
				EnhanceColorsPanel.this.redLimit.setSelected(true);
				EnhanceColorsPanel.this.redInvert.setSelected(false);
				EnhanceColorsPanel.this.greenMax.setValue(256);
				EnhanceColorsPanel.this.greenMin.setValue(0);
				EnhanceColorsPanel.this.greenLimit.setSelected(true);
				EnhanceColorsPanel.this.greenInvert.setSelected(false);
				EnhanceColorsPanel.this.blueMax.setValue(256);
				EnhanceColorsPanel.this.blueMin.setValue(0);
				EnhanceColorsPanel.this.blueLimit.setSelected(true);
				EnhanceColorsPanel.this.blueInvert.setSelected(false);
				EnhanceColorsPanel.this.comboBox.setSelectedIndex(0);
			}
		});
		btnNewButton_1.setBounds(24, 600, 272, 23);
		add(btnNewButton_1);
		this.comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				EnhanceColorsPanel.this.comboBoxChange();
			}
		});
	}
}

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.EnhanceColorsPanel
 * JD-Core Version:    0.6.2
 */