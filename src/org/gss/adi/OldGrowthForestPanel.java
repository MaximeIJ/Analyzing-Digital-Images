package org.gss.adi;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.PrintStream;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicArrowButton;

import org.gss.adi.dialogs.OGFGraphDialog;
import org.gss.adi.dialogs.OGFHelpDialog;
import org.gss.adi.tools.ColorTools;

public class OldGrowthForestPanel extends JPanel
implements Toolable
{
	private static final long serialVersionUID = 6917266928380728707L;
	private JTextField txtAnalysis;
	private Entrance entrance;
	private ZoomPanLabel img1;
	private ZoomPanLabel img2;
	private ZoomPanLabel img3;
	private ZoomPanLabel img4;
	private JTextArea x1,y1,x2,y2;
	private JTextArea pt1;
	private JTextArea pt2;
	private JButton sXP, sXM, sYP, sYM, eXP, eXM, eYP, eYM;
	private JComboBox comboBox;
	private ToolAdder ta;
	private JTextField txtPercentOldGrowth;
	private JTextField textField;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_1;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField txtSelectedArea;
	private JTextField sa1620;
	private JTextField sa1850;
	private JTextField sa1926;
	private JTextField sa1990;

	public OldGrowthForestPanel(Entrance ent, boolean three)
	{
		removeAll();
		setLayout(null);
		this.entrance = ent;
		this.entrance.setPane(this);
		setup();
		setImages();
		try {
			BufferedImage img = ImageIO.read(OldGrowthForestPanel.class.getResource("/resources/graphics/GSS Old Growth Forest/color mask.png"));
			this.ta = new ToolAdder(this.comboBox, this.entrance, new ZoomPanLabel[] { this.img1, this.img2, this.img3, this.img4 }, this, img, 0, new JComboBox()); } catch (Exception localException) {
			}
		this.entrance = ent;
		this.entrance.setPane(this);
	}

	private void setImages()
	{
		addImage(1, new ImageIcon(OldGrowthForestPanel.class.getResource("/resources/graphics/GSS Old Growth Forest/NWV-p29-Forest_1620d.png")));
		addImage(2, new ImageIcon(OldGrowthForestPanel.class.getResource("/resources/graphics/GSS Old Growth Forest/NWV-p29-Forest_1850c.png")));
		addImage(3, new ImageIcon(OldGrowthForestPanel.class.getResource("/resources/graphics/GSS Old Growth Forest/NWV-p29-Forest_1926a.png")));
		addImage(4, new ImageIcon(OldGrowthForestPanel.class.getResource("/resources/graphics/GSS Old Growth Forest/NWV-p29-Forest_1990a.png")));
	}
	private void addImage(int i, ImageIcon img) {
		try {
			switch (i) {
			case 1:
				this.img1.setImage(ColorTools.IconToBuffered(img));
				break;
			case 2:
				this.img2.setImage(ColorTools.IconToBuffered(img));
				break;
			case 3:
				this.img3.setImage(ColorTools.IconToBuffered(img));
				break;
			case 4:
				this.img4.setImage(ColorTools.IconToBuffered(img));
			}
		}
		catch (Exception e) {
			System.out.println("on image " + i);
			e.printStackTrace();
		}
	}

	public void removeDataPanel(JPanel d) {
	}

	public void setDataPanel(JPanel d) {
	}

	public void setPoint1(int x, int y) { 
		this.x1.setVisible(true);
		this.y1.setVisible(true);
		this.pt1.setVisible(true);
		this.x1.setText(""+Integer.valueOf(x));
		this.y1.setText(""+Integer.valueOf(y));
		this.sXP.setVisible(true);
		this.sXM.setVisible(true);
		this.sYM.setVisible(true);
		this.sYP.setVisible(true);
	}


	public void setPoint2(int x, int y)
	{
		this.x2.setVisible(true);
		this.y2.setVisible(true);
		this.pt2.setVisible(true);
		this.x2.setText(""+Integer.valueOf(x));
		this.y2.setText(""+Integer.valueOf(y));
		this.eXP.setVisible(true);
		this.eXM.setVisible(true);
		this.eYM.setVisible(true);
		this.eYP.setVisible(true);
	}

	public void removePoints()
	{
		this.x1.setVisible(false);
		this.y1.setVisible(false);
		this.pt1.setVisible(false);
		this.x2.setVisible(false);
		this.y2.setVisible(false);
		this.pt2.setVisible(false);
		this.txtSelectedArea.setVisible(false);
		this.sa1620.setVisible(false);
		this.sa1850.setVisible(false);
		this.sa1926.setVisible(false);
		this.sa1990.setVisible(false);
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
		this.sa1620.setText(s[0] + "%");
		this.sa1850.setText(s[1] + "%");
		this.sa1926.setText(s[2] + "%");
		this.sa1990.setText(s[3] + "%");
		this.txtSelectedArea.setVisible(true);
		this.sa1620.setVisible(true);
		this.sa1850.setVisible(true);
		this.sa1926.setVisible(true);
		this.sa1990.setVisible(true);
	}
	private void setup() {
		this.comboBox = new JComboBox();
		this.comboBox.setModel(new DefaultComboBoxModel(new String[] { "Rectangle", "Polygon" }));
		this.comboBox.setToolTipText("Point (text)\r\nPoint (graph)\r\nLine\r\nPath (multiple point line)\r\nRectangle\r\nPolygon");
		this.comboBox.setBounds(482, 574, 144, 20);
		add(this.comboBox);

		JSlider useless = new JSlider();
		this.img1 = new ZoomPanLabel(useless);
		this.img1.setVerticalAlignment(1);
		this.img1.setBounds(10, 0, 472, 285);
		add(this.img1);

		this.img2 = new ZoomPanLabel(useless);
		this.img2.setVerticalAlignment(1);
		this.img2.setBounds(482, 0, 472, 285);
		add(this.img2);

		this.img3 = new ZoomPanLabel(useless);
		this.img3.setVerticalAlignment(1);
		this.img3.setBounds(10, 285, 472, 285);
		add(this.img3);

		this.img4 = new ZoomPanLabel(useless);
		this.img4.setVerticalAlignment(1);
		this.img4.setBounds(482, 285, 472, 285);
		add(this.img4);

		this.img4.sync(this.img3);
		this.img4.sync(this.img2);
		this.img4.sync(this.img1);

		this.txtAnalysis = new JTextField();
		this.txtAnalysis.setEditable(false);
		this.txtAnalysis.setText("Analysis");
		this.txtAnalysis.setOpaque(false);
		this.txtAnalysis.setColumns(10);
		this.txtAnalysis.setBorder(null);
		this.txtAnalysis.setBounds(429, 574, 53, 20);
		add(this.txtAnalysis);

		this.pt1 = new JTextArea();
		this.pt1.setRows(2);
		this.pt1.setFont(new Font("SansSerif", Font.BOLD, 15));
		this.pt1.setForeground(new Color(0xFF8C00));
		this.pt1.setText("Start");
		this.pt1.setOpaque(false);
		this.pt1.setWrapStyleWord(true);
		this.pt1.setLineWrap(true);
		this.pt1.setBounds(680, 574, 58, 27);
		add(this.pt1);

		this.pt2 = new JTextArea();
		this.pt2.setRows(2);
		this.pt2.setWrapStyleWord(true);
		this.pt2.setForeground(new Color(0x8A309F));
		this.pt2.setText("End");
		this.pt2.setOpaque(false);
		this.pt2.setLineWrap(true);
		this.pt2.setFont(new Font("SansSerif", Font.BOLD, 15));
		this.pt2.setBounds(800, 574, 58, 27);
		add(this.pt2);


		this.x1 = new JTextArea();
		this.x1.setOpaque(false);
		this.x1.setFont(new Font("SansSerif", 0, 11));
		this.x1.setBounds(735, 610, 32, 20);
		add(this.x1);

		this.y1 = new JTextArea();
		this.y1.setOpaque(false);
		this.y1.setFont(new Font("SansSerif", 0, 11));
		this.y1.setBounds(735, 630, 32, 20);
		add(this.y1);


		this.x2 = new JTextArea();
		this.x2.setOpaque(false);
		this.x2.setFont(new Font("SansSerif", 0, 11));
		this.x2.setBounds(855, 610, 32, 20);
		add(this.x2);

		this.y2 = new JTextArea();
		this.y2.setOpaque(false);
		this.y2.setFont(new Font("SansSerif", 0, 11));
		this.y2.setBounds(855, 630, 32, 20);
		add(this.y2);



		this.sXP = new BasicArrowButton(BasicArrowButton.EAST, new Color(0xFF8C00), new Color(0x9F4C00), new Color(0x8F3C00), new Color(0xFF9C10));
		sXP.setBounds(702, 616, 16, 16);
		sXP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				int current = Integer.parseInt(OldGrowthForestPanel.this.x1.getText());
				if (current < OldGrowthForestPanel.this.img1.getOriginal().getWidth()) {
					current++;
					OldGrowthForestPanel.this.x1.setText("" + current);
					OldGrowthForestPanel.this.ta.mvPoint1(Integer.parseInt(OldGrowthForestPanel.this.x1.getText()), Integer.parseInt(OldGrowthForestPanel.this.y1.getText()));
				}
			}
		});
		add(this.sXP);
		
		this.sXM = new BasicArrowButton(BasicArrowButton.WEST, new Color(0xFF8C00), new Color(0x9F4C00), new Color(0x8F3C00), new Color(0xFF9C10));
		sXM.setBounds(670, 616, 16, 16);
		sXM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				int current = Integer.parseInt(OldGrowthForestPanel.this.x1.getText());
				if (current > 0) {
					current--;
					OldGrowthForestPanel.this.x1.setText("" + current);
					OldGrowthForestPanel.this.ta.mvPoint1(Integer.parseInt(OldGrowthForestPanel.this.x1.getText()), Integer.parseInt(OldGrowthForestPanel.this.y1.getText()));
				}
			}
		});
		add(this.sXM);

		this.sYP = new BasicArrowButton(BasicArrowButton.NORTH, new Color(0xFF8C00), new Color(0x9F4C00), new Color(0x8F3C00), new Color(0xFF9C10));
		sYP.setBounds(686, 600, 16, 16);
		sYP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				int current = Integer.parseInt(OldGrowthForestPanel.this.y1.getText());
				if (current > 0) {
					current--;
					OldGrowthForestPanel.this.y1.setText("" + current);
					OldGrowthForestPanel.this.ta.mvPoint1(Integer.parseInt(OldGrowthForestPanel.this.x1.getText()), Integer.parseInt(OldGrowthForestPanel.this.y1.getText()));
				}
			}
		});
		add(this.sYP);

		this.sYM = new BasicArrowButton(BasicArrowButton.SOUTH, new Color(0xFF8C00), new Color(0x9F4C00), new Color(0x8F3C00), new Color(0xFF9C10));
		sYM.setBounds(686, 632, 16, 16);
		sYM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				int current = Integer.parseInt(OldGrowthForestPanel.this.y1.getText());
				if (current < OldGrowthForestPanel.this.img1.getOriginal().getHeight()) {
					current++;
					OldGrowthForestPanel.this.y1.setText("" + current);
					OldGrowthForestPanel.this.ta.mvPoint1(Integer.parseInt(OldGrowthForestPanel.this.x1.getText()), Integer.parseInt(OldGrowthForestPanel.this.y1.getText()));
				}
			}
		});
		add(this.sYM);

		
		
		
		
		this.eXP = new BasicArrowButton(BasicArrowButton.EAST, new Color(0x8A309F), new Color(0x2A0030), new Color(0x10000F), new Color(0xAA50BF));
		eXP.setBounds(822, 616, 16, 16);
		eXP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				int current = Integer.parseInt(OldGrowthForestPanel.this.x2.getText());
				if (current < OldGrowthForestPanel.this.img1.getOriginal().getWidth()) {
					current++;
					OldGrowthForestPanel.this.x2.setText("" + current);
					OldGrowthForestPanel.this.ta.mvPoint2(Integer.parseInt(OldGrowthForestPanel.this.x2.getText()), Integer.parseInt(OldGrowthForestPanel.this.y2.getText()));
				}
			}
		});
		add(this.eXP);
		
		this.eXM = new BasicArrowButton(BasicArrowButton.WEST, new Color(0x8A309F), new Color(0x2A0030), new Color(0x10000F), new Color(0xAA50BF));
		eXM.setBounds(790, 616, 16, 16);
		eXM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				int current = Integer.parseInt(OldGrowthForestPanel.this.x2.getText());
				if (current > 0) {
					current--;
					OldGrowthForestPanel.this.x2.setText("" + current);
					OldGrowthForestPanel.this.ta.mvPoint2(Integer.parseInt(OldGrowthForestPanel.this.x2.getText()), Integer.parseInt(OldGrowthForestPanel.this.y2.getText()));
				}
			}
		});
		add(this.eXM);

		this.eYP = new BasicArrowButton(BasicArrowButton.NORTH, new Color(0x8A309F), new Color(0x2A0030), new Color(0x10000F), new Color(0xAA50BF));
		eYP.setBounds(806, 600, 16, 16);
		eYP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				int current = Integer.parseInt(OldGrowthForestPanel.this.y2.getText());
				if (current > 0) {
					current--;
					OldGrowthForestPanel.this.y2.setText("" + current);
					OldGrowthForestPanel.this.ta.mvPoint2(Integer.parseInt(OldGrowthForestPanel.this.x2.getText()), Integer.parseInt(OldGrowthForestPanel.this.y2.getText()));
				}
			}
		});
		add(this.eYP);

		this.eYM = new BasicArrowButton(BasicArrowButton.SOUTH, new Color(0x8A309F), new Color(0x2A0030), new Color(0x10000F), new Color(0xAA50BF));
		eYM.setBounds(806, 632, 16, 16);
		eYM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				int current = Integer.parseInt(OldGrowthForestPanel.this.y2.getText());
				if (current < OldGrowthForestPanel.this.img1.getOriginal().getHeight()) {
					current++;
					OldGrowthForestPanel.this.y2.setText("" + current);
					OldGrowthForestPanel.this.ta.mvPoint2(Integer.parseInt(OldGrowthForestPanel.this.x2.getText()), Integer.parseInt(OldGrowthForestPanel.this.y2.getText()));
				}
			}
		});
		add(this.eYM);



		this.sXP.setVisible(false);
		this.sXM.setVisible(false);
		this.sYM.setVisible(false);
		this.sYP.setVisible(false);
		this.eXP.setVisible(false);
		this.eXM.setVisible(false);
		this.eYM.setVisible(false);
		this.eYP.setVisible(false);

		
		
		
		

		/*this.x1 = new JSpinner();
		this.x1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				OldGrowthForestPanel.this.ta.mvPoint1(((Integer)OldGrowthForestPanel.this.x1.getValue()).intValue(), ((Integer)OldGrowthForestPanel.this.y1.getValue()).intValue());
			}
		});
		this.x1.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		this.x1.setOpaque(false);
		this.x1.setFont(new Font("SansSerif", 0, 11));
		this.x1.setBounds(965, 410, 72, 20);
		add(this.x1);

		this.y1 = new JSpinner();
		this.y1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				OldGrowthForestPanel.this.ta.mvPoint1(((Integer)OldGrowthForestPanel.this.x1.getValue()).intValue(), ((Integer)OldGrowthForestPanel.this.y1.getValue()).intValue());
			}
		});
		this.y1.setOpaque(false);
		this.y1.setFont(new Font("SansSerif", 0, 11));
		this.y1.setBounds(965, 431, 72, 20);
		add(this.y1);


		this.x2 = new JSpinner();
		this.x2.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				OldGrowthForestPanel.this.ta.mvPoint2(((Integer)OldGrowthForestPanel.this.x2.getValue()).intValue(), ((Integer)OldGrowthForestPanel.this.y2.getValue()).intValue());
			}
		});
		this.x2.setOpaque(false);
		this.x2.setFont(new Font("SansSerif", 0, 11));
		this.x2.setBounds(965, 497, 72, 20);
		add(this.x2);

		this.y2 = new JSpinner();
		this.y2.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				OldGrowthForestPanel.this.ta.mvPoint2(((Integer)OldGrowthForestPanel.this.x2.getValue()).intValue(), ((Integer)OldGrowthForestPanel.this.y2.getValue()).intValue());
			}
		});
		this.y2.setOpaque(false);
		this.y2.setFont(new Font("SansSerif", 0, 11));
		this.y2.setBounds(965, 517, 72, 20);
		add(this.y2);*/

		this.txtPercentOldGrowth = new JTextField();
		this.txtPercentOldGrowth.setOpaque(false);
		this.txtPercentOldGrowth.setBorder(null);
		this.txtPercentOldGrowth.setEditable(false);
		this.txtPercentOldGrowth.setHorizontalAlignment(0);
		this.txtPercentOldGrowth.setText("Percent Old Growth Forest");
		this.txtPercentOldGrowth.setBounds(10, 579, 176, 15);
		add(this.txtPercentOldGrowth);
		this.txtPercentOldGrowth.setColumns(10);

		this.textField = new JTextField();
		this.textField.setOpaque(false);
		this.textField.setText("1620");
		this.textField.setHorizontalAlignment(0);
		this.textField.setEditable(false);
		this.textField.setBorder(null);
		this.textField.setBounds(52, 595, 47, 15);
		add(this.textField);
		this.textField.setColumns(10);

		this.textField_2 = new JTextField();
		this.textField_2.setOpaque(false);
		this.textField_2.setText("1850");
		this.textField_2.setHorizontalAlignment(0);
		this.textField_2.setEditable(false);
		this.textField_2.setColumns(10);
		this.textField_2.setBorder(null);
		this.textField_2.setBounds(52, 610, 47, 15);
		add(this.textField_2);

		this.textField_3 = new JTextField();
		this.textField_3.setOpaque(false);
		this.textField_3.setText("1926");
		this.textField_3.setHorizontalAlignment(0);
		this.textField_3.setEditable(false);
		this.textField_3.setColumns(10);
		this.textField_3.setBorder(null);
		this.textField_3.setBounds(52, 625, 47, 15);
		add(this.textField_3);

		this.textField_4 = new JTextField();
		this.textField_4.setOpaque(false);
		this.textField_4.setText("1990");
		this.textField_4.setHorizontalAlignment(0);
		this.textField_4.setEditable(false);
		this.textField_4.setColumns(10);
		this.textField_4.setBorder(null);
		this.textField_4.setBounds(52, 640, 47, 15);
		add(this.textField_4);

		this.textField_1 = new JTextField();
		this.textField_1.setOpaque(false);
		this.textField_1.setText("2%");
		this.textField_1.setHorizontalAlignment(0);
		this.textField_1.setEditable(false);
		this.textField_1.setColumns(10);
		this.textField_1.setBorder(null);
		this.textField_1.setBounds(126, 640, 47, 15);
		add(this.textField_1);

		this.textField_5 = new JTextField();
		this.textField_5.setOpaque(false);
		this.textField_5.setText("10.2%");
		this.textField_5.setHorizontalAlignment(0);
		this.textField_5.setEditable(false);
		this.textField_5.setColumns(10);
		this.textField_5.setBorder(null);
		this.textField_5.setBounds(126, 625, 47, 15);
		add(this.textField_5);

		this.textField_6 = new JTextField();
		this.textField_6.setOpaque(false);
		this.textField_6.setText("40.2%");
		this.textField_6.setHorizontalAlignment(0);
		this.textField_6.setEditable(false);
		this.textField_6.setColumns(10);
		this.textField_6.setBorder(null);
		this.textField_6.setBounds(126, 610, 47, 15);
		add(this.textField_6);

		this.textField_7 = new JTextField();
		this.textField_7.setOpaque(false);
		this.textField_7.setText("48.4%");
		this.textField_7.setHorizontalAlignment(0);
		this.textField_7.setEditable(false);
		this.textField_7.setColumns(10);
		this.textField_7.setBorder(null);
		this.textField_7.setBounds(126, 595, 47, 15);
		add(this.textField_7);

		this.txtSelectedArea = new JTextField();
		this.txtSelectedArea.setOpaque(false);
		this.txtSelectedArea.setVisible(false);
		this.txtSelectedArea.setText("Selected Area");
		this.txtSelectedArea.setHorizontalAlignment(0);
		this.txtSelectedArea.setEditable(false);
		this.txtSelectedArea.setColumns(10);
		this.txtSelectedArea.setBorder(null);
		this.txtSelectedArea.setBounds(195, 579, 101, 15);
		add(this.txtSelectedArea);

		this.sa1620 = new JTextField();
		this.sa1620.setOpaque(false);
		this.sa1620.setVisible(false);
		this.sa1620.setHorizontalAlignment(0);
		this.sa1620.setEditable(false);
		this.sa1620.setColumns(10);
		this.sa1620.setBorder(null);
		this.sa1620.setBounds(223, 595, 47, 15);
		add(this.sa1620);

		this.sa1850 = new JTextField();
		this.sa1850.setOpaque(false);
		this.sa1850.setVisible(false);
		this.sa1850.setHorizontalAlignment(0);
		this.sa1850.setEditable(false);
		this.sa1850.setColumns(10);
		this.sa1850.setBorder(null);
		this.sa1850.setBounds(223, 610, 47, 15);
		add(this.sa1850);

		this.sa1926 = new JTextField();
		this.sa1926.setOpaque(false);
		this.sa1926.setVisible(false);
		this.sa1926.setHorizontalAlignment(0);
		this.sa1926.setEditable(false);
		this.sa1926.setColumns(10);
		this.sa1926.setBorder(null);
		this.sa1926.setBounds(223, 625, 47, 15);
		add(this.sa1926);

		this.sa1990 = new JTextField();
		this.sa1990.setOpaque(false);
		this.sa1990.setVisible(false);
		this.sa1990.setHorizontalAlignment(0);
		this.sa1990.setEditable(false);
		this.sa1990.setColumns(10);
		this.sa1990.setBorder(null);
		this.sa1990.setBounds(223, 640, 47, 15);
		add(this.sa1990);

		JButton btnGraphData = new JButton("Graph Data");
		btnGraphData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String s = OldGrowthForestPanel.this.sa1620.getText();
					Float[] f = new Float[4];
					f[0] = Float.valueOf(Float.parseFloat(s.substring(0, s.length() - 1)));
					s = OldGrowthForestPanel.this.sa1850.getText();
					f[1] = Float.valueOf(Float.parseFloat(s.substring(0, s.length() - 1)));
					s = OldGrowthForestPanel.this.sa1926.getText();
					f[2] = Float.valueOf(Float.parseFloat(s.substring(0, s.length() - 1)));
					s = OldGrowthForestPanel.this.sa1990.getText();
					f[3] = Float.valueOf(Float.parseFloat(s.substring(0, s.length() - 1)));
					new OGFGraphDialog(f);
				}
				catch (Exception e) {
					new OGFGraphDialog(new Float[1]);
				}
			}
		});
		btnGraphData.setBounds(306, 579, 113, 23);
		add(btnGraphData);

		JButton btnHelp = new JButton("Help");
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new OGFHelpDialog().setVisible(true);
			}
		});
		btnHelp.setBounds(306, 606, 113, 23);
		add(btnHelp);
	}
}

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.OldGrowthForestPanel
 * JD-Core Version:    0.6.2
 */