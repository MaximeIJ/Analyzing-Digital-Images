package org.gss.adi;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.gss.adi.dialogs.RelationshipMaskDialog;
import org.gss.adi.tools.ColorEnhances;
import org.gss.adi.tools.ColorMask;
import org.gss.adi.tools.ColorTools;
import org.gss.adi.tools.Measurement;

public class MaskColorsPanel extends ImagePanel {
	private static final long serialVersionUID = 878970959446009454L;
	private JTextField txtSelectImageTo;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JRadioButton rdbtnEnhanced;
	private JRadioButton rdbtnOriginal;
	private Entrance entrance;
	private JSlider redMax;
	private JSlider redMin;
	private JCheckBox chckbxRed;
	private JSlider greenMax;
	private JSlider greenMin;
	private JCheckBox chckbxGreen;
	private JSlider blueMax;
	private JSlider blueMin;
	private JCheckBox chckbxBlue;
	private JLabel redDist;
	private JLabel greenDist;
	private JLabel blueDist;
	private JButton btnNewButton_1;
	private JCheckBox showMask = new JCheckBox();

	private Integer[] x = { Integer.valueOf(0), Integer.valueOf(0) };
	private Integer[] y = { Integer.valueOf(0), Integer.valueOf(0) };
	private JTextField pixnum;
	private JTextField area;
	private JTextField rmin;
	private JTextField rmax;
	private JTextField gmin;
	private JTextField gmax;
	private JTextField bmin;
	private JTextField bmax;

	//Statics for memory
	static final MaskColorsMemory memory = new MaskColorsMemory();

	public MaskColorsPanel(Entrance e) {
		super(e, false);
		setLayout(null);
		this.entrance = e;
		if (this.entrance.getImage() == null) {
			return;
		}
		setup();
		BufferedImage enhanced = this.entrance.getEnhancedImage();
		if (enhanced != null) {
			this.label.setImage(enhanced);
			this.rdbtnEnhanced.setSelected(true);
		} else {
			this.label.setImage(this.entrance.getImage());
		}
		setColorDists();
		zoomFromMemory(memory);
		applyMask();
		this.x = memory.getToolX();
		this.y = memory.getToolY();
		if(x[0] != null)
			MaskColorsPanel.this.label.toolImage(MaskColorsPanel.this.x, MaskColorsPanel.this.y, MaskColorsPanel.this.entrance.getColor(), "Rectangle", MaskColorsPanel.this.entrance.getLineWidth(), MaskColorsPanel.this.entrance.getCursorStyle());
		this.entrance.getMenu().saveNewMask.setVisible(true);
		if (this.entrance.getMenu().applyMask.getItemCount() > 0)
			this.entrance.getMenu().applyMask.setVisible(true); 
	}

	public void updateTool() {
	}

	public void updatePic() {
		super.updatePic();
		setColorDists();
	}

	protected void closingSequence() {
		save();
		this.slider.setValue(100);
		this.showMask.setSelected(true);
		applyMask();
		this.entrance.setMaskedImage(this.label.getZoomedOriginal());
	}

	public void save(){
		// Save zoom
		memory.setX(this.label.getHorizontalScrollBar().getValue());
		memory.setY(this.label.getVerticalScrollBar().getValue());
		memory.setZoomFactor(this.label.zoomFactor);

		// Save Color info
		memory.setR(new int[]{this.redMin.getValue(), this.redMax.getValue(), this.chckbxRed.isSelected()? 1:0});
		memory.setG(new int[]{this.greenMin.getValue(), this.greenMax.getValue(), this.chckbxGreen.isSelected()? 1:0});
		memory.setB(new int[]{this.blueMin.getValue(), this.blueMax.getValue(), this.chckbxBlue.isSelected()? 1:0});

		// Save tool
		memory.setToolX(this.x);
		memory.setToolY(this.y);
		
		
	}
	
	byte getType() {
		return 2;
	}

	private void setColorDists() {
		BufferedImage[] dists = ColorEnhances.getColorDistributions(this.label.getZoomedOriginal());
		this.redDist.setIcon(new ImageIcon(dists[0]));
		this.greenDist.setIcon(new ImageIcon(dists[1]));
		this.blueDist.setIcon(new ImageIcon(dists[2]));
	}

	private void applyMask() {
		if (this.showMask.isSelected()) {
			int[] mins = new int[3];
			int[] maxs = new int[3];
			if (this.chckbxRed.isSelected()) {
				mins[0] = this.redMin.getValue();
				maxs[0] = this.redMax.getValue();
			} else {
				mins[0] = 0;
				maxs[0] = 256;
			}
			if (this.chckbxGreen.isSelected()) {
				mins[1] = this.greenMin.getValue();
				maxs[1] = this.greenMax.getValue();
			} else {
				mins[1] = 0;
				maxs[1] = 256;
			}
			if (this.chckbxBlue.isSelected()) {
				mins[2] = this.blueMin.getValue();
				maxs[2] = this.blueMax.getValue();
			} else {
				mins[2] = 0;
				maxs[2] = 256;
			}

			Object[] o = ColorEnhances.newApplyMask(mins, maxs, this.label.getOriginal());
			this.label.setZoomedOriginal(ZoomPanLabel.resize(this.slider.getValue(), (BufferedImage)o[0]));
			//this.label.setImage((BufferedImage)o[0]);
			this.pixnum.setText(((Integer)o[1]).toString() + " pixels masked");
			SpatialAnalysisPanel.maskedPix = (Integer)o[1];
			if (this.entrance.getMeasurement() != null)
				this.area.setText("Area: " + this.entrance.getMeasurement().measure(Double.valueOf(((Integer)o[1]).doubleValue()), false));
		}
	}

	void slideComplete() {
		super.slideComplete();
		applyMask();
	}

	public int getRMin() {
		return this.redMin.getValue();
	}
	public int getRMax() {
		return this.redMax.getValue();
	}
	public int getGMin() {
		return this.greenMin.getValue();
	}
	public int getGMax() {
		return this.greenMax.getValue();
	}
	public int getBMin() {
		return this.blueMin.getValue();
	}
	public int getBMax() {
		return this.blueMax.getValue();
	}
	public boolean getRed() {
		return this.chckbxRed.isSelected();
	}
	public boolean getGreen() {
		return this.chckbxGreen.isSelected();
	}
	public boolean getBlue() {
		return this.chckbxBlue.isSelected();
	}

	public void applyMask(ColorMask mask) {
		this.redMin.setValue(mask.redMin);
		this.redMax.setValue(mask.redMax);
		this.greenMin.setValue(mask.greenMin);
		this.greenMax.setValue(mask.greenMax);
		this.blueMin.setValue(mask.blueMin);
		this.blueMax.setValue(mask.blueMax);
		this.chckbxRed.setSelected(mask.red);
		this.chckbxGreen.setSelected(mask.green);
		this.chckbxBlue.setSelected(mask.blue);
		applyMask();
	}
	private void setup() {
/*		this.rmin = new JTextField("0%");
		this.rmin.setHorizontalAlignment(11);
		this.rmin.setEditable(false);
		this.rmin.setColumns(10);
		this.rmin.setBorder(null);
		this.rmin.setOpaque(false);
		this.rmin.setBounds(0, 345, 43, 20);
		add(this.rmin);
		this.rmax = new JTextField("100%");
		this.rmax.setHorizontalAlignment(11);
		this.rmax.setEditable(false);
		this.rmax.setColumns(10);
		this.rmax.setBorder(null);
		this.rmax.setOpaque(false);
		this.rmax.setBounds(0, 375, 43, 32);
		add(this.rmax);
		this.gmin = new JTextField("0%");
		this.gmin.setHorizontalAlignment(11);
		this.gmin.setEditable(false);
		this.gmin.setColumns(10);
		this.gmin.setBorder(null);
		this.gmin.setOpaque(false);
		this.gmin.setBounds(0, 430, 43, 20);
		add(this.gmin);
		this.gmax = new JTextField("100%");
		this.gmax.setHorizontalAlignment(11);
		this.gmax.setEditable(false);
		this.gmax.setColumns(10);
		this.gmax.setBorder(null);
		this.gmax.setOpaque(false);
		this.gmax.setBounds(0, 460, 43, 20);
		add(this.gmax);
		this.bmin = new JTextField("0%");
		this.bmin.setHorizontalAlignment(11);
		this.bmin.setEditable(false);
		this.bmin.setColumns(10);
		this.bmin.setBorder(null);
		this.bmin.setOpaque(false);
		this.bmin.setBounds(0, 515, 43, 20);
		add(this.bmin);
		this.bmax = new JTextField("100%");
		this.bmax.setHorizontalAlignment(11);
		this.bmax.setEditable(false);
		this.bmax.setColumns(10);
		this.bmax.setBorder(null);
		this.bmax.setOpaque(false);
		this.bmax.setBounds(0, 545, 43, 20);
		add(this.bmax);*/
		JTextArea txtrSelectARange = new JTextArea();
		txtrSelectARange.setEditable(false);
		txtrSelectARange.setFont(new Font("SansSerif", 0, 13));
		txtrSelectARange.setText("Select a range of colors to highlight, or see which pixels meet color relationships between the pixels' red, green, and blue intensities. The masked image will be black and white, in which black pixels passed the color tests, white did not.  \r\nUse the masked image with the rectangle and polygon spatial tools to measure areas of features highlighted with the color masking tests. \r\nClicking and dragging on the image allows you to select a portion of the image's pixel values to use as the mask. Click again to reselect the values.");
		txtrSelectARange.setOpaque(false);
		txtrSelectARange.setWrapStyleWord(true);
		txtrSelectARange.setLineWrap(true);
		txtrSelectARange.setBounds(0, 0, 315, 255);
		add(txtrSelectARange);

		this.txtSelectImageTo = new JTextField();
		this.txtSelectImageTo.setBorder(null);
		this.txtSelectImageTo.setOpaque(false);
		this.txtSelectImageTo.setEditable(false);
		this.txtSelectImageTo.setFont(new Font("Tahoma", 2, 11));
		this.txtSelectImageTo.setText("Select Image to Mask");
		this.txtSelectImageTo.setBounds(92, 242, 120, 20);
		add(this.txtSelectImageTo);
		this.txtSelectImageTo.setColumns(10);

		this.rdbtnOriginal = new JRadioButton("Original");
		this.rdbtnOriginal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MaskColorsPanel.this.label.setImage(MaskColorsPanel.this.entrance.getImage());
				zoomFromMemory(memory);
				MaskColorsPanel.this.setColorDists();
			}
		});
		this.rdbtnOriginal.setSelected(true);
		this.rdbtnOriginal.setBounds(60, 262, 92, 23);
		add(this.rdbtnOriginal);

		this.rdbtnEnhanced = new JRadioButton("Enhanced");
		this.rdbtnEnhanced.setSelected(false);
		this.rdbtnEnhanced.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BufferedImage enhanced = MaskColorsPanel.this.entrance.getEnhancedImage();
				if (enhanced == null) { MaskColorsPanel.this.rdbtnOriginal.setSelected(true); return; }
				MaskColorsPanel.this.label.setImage(enhanced);
				zoomFromMemory(memory);
				MaskColorsPanel.this.setColorDists();
			}
		});
		this.rdbtnEnhanced.setBounds(156, 262, 100, 23);
		add(this.rdbtnEnhanced);

		ButtonGroup bg = new ButtonGroup();
		bg.add(this.rdbtnEnhanced);
		bg.add(this.rdbtnOriginal);

		JTextArea txtrTestSelect = new JTextArea();
		txtrTestSelect.setEditable(false);
		txtrTestSelect.setFont(new Font("SansSerif", 0, 13));
		txtrTestSelect.setBorder(null);
		txtrTestSelect.setOpaque(false);
		txtrTestSelect.setText("Create Simple Mask: Select range of color intensities. \r\nPixels with colors in these ranges are made black.");
		txtrTestSelect.setBounds(0, 282, 360, 43);
		add(txtrTestSelect);

		this.redMin = new JSlider();
		this.redMin.setMinorTickSpacing(1);
		this.redMin.setMaximum(256);
		this.redMin.setUI(new CustomSliderUI(this.redMin, "Min"));
		this.redMin.setSnapToTicks(true);
		this.redMin.setValue(memory.getR()[0]);
		this.redMin.setPaintTrack(false);
		this.redMin.setMajorTickSpacing(10);
		this.redMin.setBounds(50, 325, 269, 20);
		add(this.redMin);

		this.redDist = new JLabel();
		this.redDist.setBorder(new LineBorder(new Color(0, 0, 0)));
		this.redDist.setBounds(57, 345, 256, 45);
		add(this.redDist);

		this.redMax = new JSlider();
		this.redMax.setMinorTickSpacing(1);
		this.redMax.setMaximum(256);
		this.redMax.setUI(new CustomSliderUI(this.redMax, "Max"));
		this.redMax.setSnapToTicks(true);
		this.redMax.setValue(memory.getR()[1]);
		this.redMax.setPaintTrack(false);
		this.redMax.setMajorTickSpacing(10);
		this.redMax.setBounds(50, 390, 269, 20);
		add(this.redMax);

		this.redMin.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (MaskColorsPanel.this.redMin.getValue() > MaskColorsPanel.this.redMax.getValue()) {
					MaskColorsPanel.this.redMin.setValue(MaskColorsPanel.this.redMax.getValue());
					MaskColorsPanel.this.redMin.repaint();
				} else {
					//MaskColorsPanel.this.rmin.setText(100 * MaskColorsPanel.this.redMin.getValue() / 256 + "%");
					MaskColorsPanel.this.applyMask();
				}
			}
		});
		this.redMax.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (MaskColorsPanel.this.redMax.getValue() < MaskColorsPanel.this.redMin.getValue()) {
					MaskColorsPanel.this.redMax.setValue(MaskColorsPanel.this.redMin.getValue());
					MaskColorsPanel.this.redMax.repaint();
				} else {
					//MaskColorsPanel.this.rmax.setText(100 * MaskColorsPanel.this.redMax.getValue() / 256 + "%");
					MaskColorsPanel.this.applyMask();
				}
			}
		});
		/*this.textField = new JTextField();
		this.textField.setHorizontalAlignment(11);
		this.textField.setText("max");
		this.textField.setEditable(false);
		this.textField.setColumns(10);
		this.textField.setBorder(null);
		this.textField.setOpaque(false);
		this.textField.setBounds(6, 390, 41, 20);
		add(this.textField);

		this.textField_1 = new JTextField();
		this.textField_1.setHorizontalAlignment(11);
		this.textField_1.setText("min");
		this.textField_1.setEditable(false);
		this.textField_1.setColumns(10);
		this.textField_1.setBorder(null);
		this.textField_1.setOpaque(false);
		this.textField_1.setBounds(6, 325, 41, 20);
		add(this.textField_1);*/

		this.chckbxRed = new JCheckBox("Red");
		this.chckbxRed.setSelected(true);
		this.chckbxRed.setFont(new Font("SansSerif", 0, 10));
		this.chckbxRed.setBounds(0, 357, 51, 23);
		add(this.chckbxRed);

		this.greenMin = new JSlider();
		this.greenMin.setMinorTickSpacing(1);
		this.greenMin.setMaximum(256);
		this.greenMin.setSnapToTicks(true);
		this.greenMin.setUI(new CustomSliderUI(this.greenMin, "Min"));
		this.greenMin.setValue((memory.getG()[0]));
		this.greenMin.setPaintTrack(false);
		this.greenMin.setMajorTickSpacing(10);
		this.greenMin.setBounds(50, 410, 269, 20);
		add(this.greenMin);
/*
		this.textField_2 = new JTextField();
		this.textField_2.setHorizontalAlignment(11);
		this.textField_2.setText("min");
		this.textField_2.setEditable(false);
		this.textField_2.setColumns(10);
		this.textField_2.setBorder(null);
		this.textField_2.setOpaque(false);
		this.textField_2.setBounds(6, 410, 41, 20);
		add(this.textField_2);

		this.textField_3 = new JTextField();
		this.textField_3.setHorizontalAlignment(11);
		this.textField_3.setText("max");
		this.textField_3.setEditable(false);
		this.textField_3.setColumns(10);
		this.textField_3.setBorder(null);
		this.textField_3.setOpaque(false);
		this.textField_3.setBounds(6, 475, 41, 20);
		add(this.textField_3);*/

		this.greenMax = new JSlider();
		this.greenMax.setMinorTickSpacing(1);
		this.greenMax.setMaximum(256);
		this.greenMax.setUI(new CustomSliderUI(this.greenMax, "Max"));
		this.greenMax.setSnapToTicks(true);
		this.greenMax.setValue((memory.getG()[1]));
		this.greenMax.setPaintTrack(false);
		this.greenMax.setMajorTickSpacing(10);
		this.greenMax.setBounds(50, 475, 269, 20);
		add(this.greenMax);

		this.greenMin.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (MaskColorsPanel.this.greenMin.getValue() > MaskColorsPanel.this.greenMax.getValue()) {
					MaskColorsPanel.this.greenMin.setValue(MaskColorsPanel.this.greenMax.getValue());
					MaskColorsPanel.this.greenMin.repaint();
				} else {
					//MaskColorsPanel.this.gmin.setText(100 * MaskColorsPanel.this.greenMin.getValue() / 256 + "%");
					MaskColorsPanel.this.applyMask();
				}
			}
		});
		this.greenMax.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (MaskColorsPanel.this.greenMax.getValue() < MaskColorsPanel.this.greenMin.getValue()) {
					MaskColorsPanel.this.greenMax.setValue(MaskColorsPanel.this.greenMin.getValue());
					MaskColorsPanel.this.greenMax.repaint();
				} else {
					//MaskColorsPanel.this.gmax.setText(100 * MaskColorsPanel.this.greenMax.getValue() / 256 + "%");
					MaskColorsPanel.this.applyMask();
				}
			}
		});
		this.greenDist = new JLabel();
		this.greenDist.setBorder(new LineBorder(new Color(0, 0, 0)));
		this.greenDist.setBounds(58, 430, 256, 45);
		add(this.greenDist);

		this.chckbxGreen = new JCheckBox("Green");
		this.chckbxGreen.setSelected(true);
		this.chckbxGreen.setFont(new Font("SansSerif", 0, 10));
		this.chckbxGreen.setBounds(1, 442, 57, 23);
		add(this.chckbxGreen);

		this.blueMax = new JSlider();
		this.blueMax.setMinorTickSpacing(1);
		this.blueMax.setMaximum(256);
		this.blueMax.setUI(new CustomSliderUI(this.blueMax, "Max"));
		this.blueMax.setValue((memory.getB()[1]));
		this.blueMax.setPaintTrack(false);
		this.blueMax.setMajorTickSpacing(10);
		this.blueMax.setBounds(50, 560, 269, 20);
		add(this.blueMax);

		/*this.textField_4 = new JTextField();
		this.textField_4.setHorizontalAlignment(11);
		this.textField_4.setText("max");
		this.textField_4.setEditable(false);
		this.textField_4.setColumns(10);
		this.textField_4.setBorder(null);
		this.textField_4.setOpaque(false);
		this.textField_4.setBounds(6, 560, 41, 20);
		add(this.textField_4);

		this.textField_5 = new JTextField();
		this.textField_5.setHorizontalAlignment(11);
		this.textField_5.setText("min");
		this.textField_5.setEditable(false);
		this.textField_5.setColumns(10);
		this.textField_5.setBorder(null);
		this.textField_5.setOpaque(false);
		this.textField_5.setBounds(6, 495, 41, 20);
		add(this.textField_5);
*/
		this.blueMin = new JSlider();
		this.blueMin.setMinorTickSpacing(1);
		this.blueMin.setMaximum(256);
		this.blueMin.setSnapToTicks(true);
		this.blueMin.setUI(new CustomSliderUI(this.blueMin, "Min"));
		this.blueMin.setValue((memory.getB()[0]));
		this.blueMin.setPaintTrack(false);
		this.blueMin.setMajorTickSpacing(10);
		this.blueMin.setBounds(50, 495, 269, 20);
		add(this.blueMin);

		this.blueMin.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (MaskColorsPanel.this.blueMin.getValue() > MaskColorsPanel.this.blueMax.getValue()) {
					MaskColorsPanel.this.blueMin.setValue(MaskColorsPanel.this.blueMax.getValue());
					MaskColorsPanel.this.blueMin.repaint();
				} else {
					//MaskColorsPanel.this.bmin.setText(100 * MaskColorsPanel.this.blueMin.getValue() / 256 + "%");
					MaskColorsPanel.this.applyMask();
				}
			}
		});
		this.blueMax.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (MaskColorsPanel.this.blueMax.getValue() < MaskColorsPanel.this.blueMin.getValue()) {
					MaskColorsPanel.this.blueMax.setValue(MaskColorsPanel.this.blueMin.getValue());
					MaskColorsPanel.this.blueMax.repaint();
				} else {
					//MaskColorsPanel.this.bmax.setText(100 * MaskColorsPanel.this.blueMax.getValue() / 256 + "%");
					MaskColorsPanel.this.applyMask();
				}
			}
		});
		this.blueDist = new JLabel();
		this.blueDist.setBorder(new LineBorder(new Color(0, 0, 0)));
		this.blueDist.setBounds(58, 515, 256, 45);
		add(this.blueDist);

		this.chckbxBlue = new JCheckBox("Blue");
		this.chckbxBlue.setSelected(true);
		this.chckbxBlue.setFont(new Font("SansSerif", 0, 10));
		this.chckbxBlue.setBounds(1, 527, 57, 23);
		add(this.chckbxBlue);

		this.showMask.setText("Show Mask");
		this.showMask.setSelected(false);
		this.showMask.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) {
				if (MaskColorsPanel.this.showMask.isSelected()) {
					MaskColorsPanel.this.applyMask();
					MaskColorsPanel.this.label.showZoomedOriginal();
					MaskColorsPanel.this.pixnum.setVisible(true);
				} else {
					MaskColorsPanel.this.label.setZoomedOriginal(ZoomPanLabel.resize(Math.round(MaskColorsPanel.this.label.zoomFactor * 100.0F), MaskColorsPanel.this.label.getOriginal()));
					MaskColorsPanel.this.pixnum.setVisible(false);
				}MaskColorsPanel.this.label.showZoomedOriginal();
			}
		});
		this.showMask.setBounds(40, 587, 287, 23);
		add(this.showMask);

		this.btnNewButton_1 = new JButton("Create A Relationship Mask");
		this.btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new RelationshipMaskDialog(MaskColorsPanel.this.label).setVisible(true);
			}
		});
		this.btnNewButton_1.setBounds(11, 612, 287, 23);
		add(this.btnNewButton_1);

		this.pixnum = new JTextField();
		this.pixnum.setOpaque(false);
		this.pixnum.setEditable(false);
		this.pixnum.setBorder(null);
		this.pixnum.setFont(new Font("SansSerif", 0, 11));
		this.pixnum.setBounds(850, 550, 191, 20);
		add(this.pixnum);
		this.pixnum.setColumns(10);

		this.area = new JTextField();
		this.area.setBorder(null);
		this.area.setOpaque(false);
		this.area.setFont(new Font("SansSerif", 0, 11));
		this.area.setEditable(false);
		this.area.setBounds(850, 570, 191, 20);
		add(this.area);
		this.area.setColumns(10);

		this.label.getLabel().addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {
			}
			public void mouseEntered(MouseEvent arg0) {
			}
			public void mouseExited(MouseEvent arg0) {
			}

			public void mousePressed(MouseEvent e) {
				Point p = MaskColorsPanel.this.label.mapToPixel(e.getX(), e.getY());
				int eX = p.x;
				int eY = p.y;
				if (eX < 0)
					eX = 0;
				else if (eX >= MaskColorsPanel.this.label.getOriginal().getWidth())
					eX = MaskColorsPanel.this.label.getOriginal().getWidth() - 1;
				if (eY < 0)
					eY = 0;
				else if (eY >= MaskColorsPanel.this.label.getOriginal().getHeight())
					eY = MaskColorsPanel.this.label.getOriginal().getHeight() - 1;
				MaskColorsPanel.this.x[0] = Integer.valueOf(eX);
				MaskColorsPanel.this.x[1] = Integer.valueOf(eX);
				MaskColorsPanel.this.y[0] = Integer.valueOf(eY);
				MaskColorsPanel.this.y[1] = Integer.valueOf(eY);
				MaskColorsPanel.this.label.toolImage(MaskColorsPanel.this.x, MaskColorsPanel.this.y, MaskColorsPanel.this.entrance.getColor(), "Rectangle", MaskColorsPanel.this.entrance.getLineWidth(), MaskColorsPanel.this.entrance.getCursorStyle());
			}

			public void mouseReleased(MouseEvent e) {
				Point p = MaskColorsPanel.this.label.mapToPixel(e.getX(), e.getY());
				int eX = p.x;
				int eY = p.y;
				if (eX < 0)
					eX = 0;
				else if (eX >= MaskColorsPanel.this.label.getOriginal().getWidth())
					eX = MaskColorsPanel.this.label.getOriginal().getWidth() - 1;
				if (eY < 0)
					eY = 0;
				else if (eY >= MaskColorsPanel.this.label.getOriginal().getHeight())
					eY = MaskColorsPanel.this.label.getOriginal().getHeight() - 1;
				MaskColorsPanel.this.x[1] = Integer.valueOf(eX);
				MaskColorsPanel.this.y[1] = Integer.valueOf(eY);
				MaskColorsPanel.this.label.toolImage(MaskColorsPanel.this.x, MaskColorsPanel.this.y, MaskColorsPanel.this.entrance.getColor(), "Rectangle", MaskColorsPanel.this.entrance.getLineWidth(), MaskColorsPanel.this.entrance.getCursorStyle());
				int xmax;
				int xmin;
				if (MaskColorsPanel.this.x[0].intValue() <= MaskColorsPanel.this.x[1].intValue()) {
					xmin = MaskColorsPanel.this.x[0].intValue();
					xmax = MaskColorsPanel.this.x[1].intValue();
				} else {
					xmin = MaskColorsPanel.this.x[1].intValue();
					xmax = MaskColorsPanel.this.x[0].intValue();
				}
				int ymax;
				int ymin;
				if (MaskColorsPanel.this.y[0].intValue() <= MaskColorsPanel.this.y[1].intValue()) {
					ymin = MaskColorsPanel.this.y[0].intValue();
					ymax = MaskColorsPanel.this.y[1].intValue();
				} else {
					ymin = MaskColorsPanel.this.y[1].intValue();
					ymax = MaskColorsPanel.this.y[0].intValue();
				}
				p = MaskColorsPanel.this.label.zoomPixels(xmin, ymin);
				xmin = p.x;
				ymin = p.y;
				p = MaskColorsPanel.this.label.zoomPixels(xmax, ymax);
				xmax = p.x;
				ymax = p.y;
				BufferedImage img = ZoomPanLabel.resize(Math.round(MaskColorsPanel.this.label.zoomFactor * 100.0F), MaskColorsPanel.this.label.getOriginal());
				int w = img.getWidth();
				int h = img.getHeight();
				int[] map = img.getRGB(0, 0, w, h, null, 0, w);
				int rmin = 256;
				int rmax = 0;
				int gmin = 256;
				int gmax = 0;
				int bmin = 256;
				int bmax = 0;

				for (int j = ymin; j < ymax; j++) {
					int jw = j * w;
					for (int i = xmin; i < xmax; i++) {
						int c = map[(i + jw)];
						int[] rgb = ColorTools.rgb(c);

						int r = rgb[0];
						int g = rgb[1];
						int b = rgb[2];
						if (r > rmax) rmax = r;
						if (r < rmin) rmin = r;
						if (g > gmax) gmax = g;
						if (g < gmin) gmin = g;
						if (b > bmax) bmax = b;
						if (b < bmin) bmin = b;
					}
				}
				MaskColorsPanel.this.redMin.setValue(rmin);
				MaskColorsPanel.this.redMax.setValue(rmax + 1);
				MaskColorsPanel.this.greenMin.setValue(gmin);
				MaskColorsPanel.this.greenMax.setValue(gmax + 1);
				MaskColorsPanel.this.blueMin.setValue(bmin);
				MaskColorsPanel.this.blueMax.setValue(bmax + 1);
				MaskColorsPanel.this.showMask.setSelected(true);
				MaskColorsPanel.this.applyMask();
			}
		});
		this.label.getLabel().addMouseMotionListener(new MouseMotionListener()
		{
			public void mouseDragged(MouseEvent e) {
				Point p = MaskColorsPanel.this.label.mapToPixel(e.getX(), e.getY());
				int eX = p.x;
				int eY = p.y;
				if (eX < 0)
					eX = 0;
				else if (eX >= MaskColorsPanel.this.label.getOriginal().getWidth())
					eX = MaskColorsPanel.this.label.getOriginal().getWidth() - 1;
				if (eY < 0)
					eY = 0;
				else if (eY >= MaskColorsPanel.this.label.getOriginal().getHeight())
					eY = MaskColorsPanel.this.label.getOriginal().getHeight() - 1;
				MaskColorsPanel.this.x[1] = Integer.valueOf(eX);
				MaskColorsPanel.this.y[1] = Integer.valueOf(eY);
				MaskColorsPanel.this.label.toolImage(MaskColorsPanel.this.x, MaskColorsPanel.this.y, MaskColorsPanel.this.entrance.getColor(), "Rectangle", MaskColorsPanel.this.entrance.getLineWidth(), MaskColorsPanel.this.entrance.getCursorStyle());
			}

			public void mouseMoved(MouseEvent arg0)
			{
			}
		});
		
		this.chckbxRed.setSelected((memory.getR()[2]==1));
		this.chckbxGreen.setSelected((memory.getG()[2]==1));
		this.chckbxBlue.setSelected((memory.getB()[2]==1));
		rdbtnEnhanced.setSelected(memory.getImgType() == "Enhanced");
		rdbtnOriginal.setSelected(memory.getImgType() == "Original");
		
		this.showMask.setSelected(true);
		
		
		
	}
}