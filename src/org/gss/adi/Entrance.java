package org.gss.adi;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.gss.adi.dialogs.CalibratePixelDialog;
import org.gss.adi.dialogs.KeepSettingsDialog;
import org.gss.adi.dialogs.SelectTimeSeriesDialog;
import org.gss.adi.dialogs.TrimDialog;
import org.gss.adi.tools.ColorMask;
import org.gss.adi.tools.ConfigFileManager;
import org.gss.adi.tools.Measurement;

public class Entrance
{
	private static MainFrame _mf;
	private Color _color = Color.yellow;
	private float _lineWidth = 1.0F;
	private byte _cursorStyle = 0;
	private BufferedImage _img;
	private String title;
	private BufferedImage _timeSeries1;
	private String title1;
	private BufferedImage _timeSeries2;
	private String title2;
	private BufferedImage _timeSeries3;
	private String title3;
	private byte _timeSeriesType = 0;
	private BufferedImage _enhancedImage;
	private BufferedImage _maskedImage;
	private Measurement _measurement = null;
	private Measurement _timeSeriesMeasurement = null;

	private File _measurementFile = null;

	private ArrayList<String> _recent = new ArrayList();
	private ArrayList<ColorMask> _colorMasks = new ArrayList();
	private String lastDirectory = null;
	private PrinterJob _printerJob = PrinterJob.getPrinterJob();
	private String filename;
	private String absoluteFilename;

	public static void main(String[] args)
			throws Exception
			{
		try
		{
			File swtJar = new File(getArchFilename("resources/swt"));
			addJarToClasspath(swtJar);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		new Entrance();
			}

	public static void addJarToClasspath(File jarFile)
	{
		try
		{
			URL url = jarFile.toURI().toURL();
			URLClassLoader urlClassLoader = (URLClassLoader)ClassLoader.getSystemClassLoader();
			Class urlClass = URLClassLoader.class;
			Method method = urlClass.getDeclaredMethod("addURL", new Class[] { URL.class });
			method.setAccessible(true);
			method.invoke(urlClassLoader, new Object[] { url });
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public static String getArchFilename(String prefix) { return prefix + "_" + getOSName() + "_" + getArchName() + ".jar"; }

	private static String getOSName()
	{
		String osNameProperty = System.getProperty("os.name");
		if (osNameProperty == null) {
			throw new RuntimeException("os.name property is not set");
		}
		osNameProperty = osNameProperty.toLowerCase();

		if (osNameProperty.contains("win"))
			return "win";
		if (osNameProperty.contains("mac"))
			return "mac";
		if ((osNameProperty.contains("linux")) || (osNameProperty.contains("nix"))) {
			return "linux";
		}
		throw new RuntimeException("Unknown OS name: " + osNameProperty);
	}

	private static String getArchName()
	{
		String osArch = System.getProperty("os.arch");
		if ((osArch != null) && (osArch.contains("64"))) {
			return "64";
		}
		return "32";
	}

	public Entrance() {
		ConfigFileManager.createRecentImages();
		ConfigFileManager.usePrefs(this);
		createMainFrame();
	}
	public File openImage(String title){
		File f = SWTFile.openImage(title, this);
		if (f == null){
			System.out.println("NULL");
			return null;
		}
		this.setAbsoluteFilename(f.getAbsolutePath());
		this.filename = f.getName();
		BufferedImage img;
		try {
			img = ImageIO.read(f);
			this.setTitle(f.getName() + " is " + new Integer(img.getWidth()).toString() + " by " + new Integer(img.getHeight()).toString() + " pixels");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ConfigFileManager.addRecentImage(f);
		return f;
	}
	protected void launchTimeSeries() {
		if (getTimeSeries2() == null)
			new SelectTimeSeriesDialog(this).setVisible(true);
		else
			new TimeSeriesPanel(this, getTimeSeries3() != null, this._timeSeriesType);
	}

	public String getFilename() {
		return filename;
	}

	private void createMainFrame() {
		_mf = new MainFrame(this);
	}

	protected void setPane(JPanel pane)
	{
		final JPanel old = _mf.getPane();
		_mf.setPane(pane);
		_mf.getMenu().showSaveMeas(false);
		if(old instanceof TimeSeriesPanel){
			new Thread() {
				public void run() {
					try {
						((TimeSeriesPanel)old).closingSequence(); } catch (Exception localException) {  } 
				}
			}
			.start();
		}
		else
		{
			new Thread() {
				public void run() {
					try {
						((ImagePanel)old).closingSequence(); } catch (Exception localException) {  } 
				}
			}
			.start();
		}
	}
	public JPanel getPane() {
		return _mf.getPane();
	}
	public void updateTool() {
		try { ((Updatable)_mf.getPane()).updateTool(); } catch (Exception localException) {
		}
	}
	public void updatePic() { try { ((Updatable)_mf.getPane()).updatePic(); } catch (Exception localException) { 
		
	} }
	
	public void setImageToEnhanced(){
		this._img = this._enhancedImage;
		this._enhancedImage = null;
	}

	public void setImage(BufferedImage img) {

		this._img = new BufferedImage(img.getWidth(), img.getHeight(), 1);
		this._img.setRGB(0, 0, img.getWidth(), img.getHeight(), img.getRGB(0, 0, img.getWidth(), img.getHeight(), null, 0, img.getWidth()), 0, img.getWidth());
		this._enhancedImage = null;
		this._maskedImage = null;
		if ((this._img != null) && (this._measurement != null) ) {
			if (!KeepSettingsDialog.launch())
			{
				new CalibratePixelDialog(this, Boolean.valueOf(false)).setVisible(true);
			} 
		} else if(this._measurement == null)
			new CalibratePixelDialog(this, Boolean.valueOf(false)).setVisible(true);
		updatePic();
	}
	
	public void setImageTrim(BufferedImage img) {
		this._img = img;
		this._img.setRGB(0, 0, img.getWidth(), img.getHeight(), img.getRGB(0, 0, img.getWidth(), img.getHeight(), null, 0, img.getWidth()), 0, img.getWidth());
		this._enhancedImage = null;
		this._maskedImage = null;
		if(this._img.getWidth() > 1000 || this._img.getHeight() > 1000){
			if(JOptionPane.showConfirmDialog (null, "The image you are uploading is quite large and could cause certain operations to take a long time. Would you like to trim or resize it?","Warning",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
				new TrimDialog(this).setVisible(true);
				return;
			}
		}
		setImage(img);
		updatePic();
	}
	public BufferedImage getImage() {
		return this._img;
	}
	public ADIMenu getMenu() {
		return _mf.getMenu();
	}
	protected void setRecent(ArrayList<String> recent) {
		this._recent = recent;
	}
	protected ArrayList<String> getRecent() {
		return this._recent;
	}
	public void setTimeSeries1(BufferedImage img) {
		this._timeSeries1 = new BufferedImage(img.getWidth(), img.getHeight(), 1);
		this._timeSeries1.setRGB(0, 0, img.getWidth(), img.getHeight(), img.getRGB(0, 0, img.getWidth(), img.getHeight(), null, 0, img.getWidth()), 0, img.getWidth());
	}
	public void setTimeSeries2(BufferedImage img) {
		this._timeSeries2 = new BufferedImage(img.getWidth(), img.getHeight(), 1);
		this._timeSeries2.setRGB(0, 0, img.getWidth(), img.getHeight(), img.getRGB(0, 0, img.getWidth(), img.getHeight(), null, 0, img.getWidth()), 0, img.getWidth());
	}
	public void setTimeSeries3(BufferedImage img) {
		if (img == null) {
			this._timeSeries3 = null;
		} else {
			this._timeSeries3 = new BufferedImage(img.getWidth(), img.getHeight(), 1);
			this._timeSeries3.setRGB(0, 0, img.getWidth(), img.getHeight(), img.getRGB(0, 0, img.getWidth(), img.getHeight(), null, 0, img.getWidth()), 0, img.getWidth());
		}
	}

	public BufferedImage getTimeSeries1() { return this._timeSeries1; }

	protected BufferedImage getTimeSeries2() {
		return this._timeSeries2;
	}
	protected BufferedImage getTimeSeries3() {
		return this._timeSeries3;
	}
	public MainFrame getMainFrame() {
		return _mf;
	}
	public void setColor(Color color) {
		this._color = color;
		ConfigFileManager.setPrefs(this);
	}
	public Color getColor() {
		return this._color;
	}
	public void setLineWidth(float f) {
		this._lineWidth = f;
		ConfigFileManager.setPrefs(this);
	}
	public float getLineWidth() {
		return this._lineWidth;
	}
	public void setCursorStyle(byte style) {
		this._cursorStyle = style;
		ConfigFileManager.setPrefs(this);
	}
	public byte getCursorStyle() {
		return this._cursorStyle;
	}
	public BufferedImage getEnhancedImage() {
		return this._enhancedImage;
	}
	protected void setEnhancedImage(BufferedImage img) {
		this._enhancedImage = img;
	}
	public BufferedImage getMaskedImage() {
		return this._maskedImage;
	}
	protected void setMaskedImage(BufferedImage img) {
		this._maskedImage = img;
	}
	public void setTitle(String s) {
		this.title = s;
	}
	public String getTitle() {
		return this.title;
	}
	public void setTitle1(String s) {
		this.title1 = s;
	}
	public String getTitle1() {
		return this.title1;
	}
	public void setTitle2(String s) {
		this.title2 = s;
	}
	public String getTitle2() {
		return this.title2;
	}
	public void setTitle3(String s) {
		this.title3 = s;
	}
	public String getTitle3() {
		return this.title3;
	}
	public Measurement getMeasurement() {
		return this._measurement;
	}
	public void setMeasurement(Measurement m) {
		this._measurement = m;
	}
	public Measurement getTimeSeriesMeasurement() {
		return this._timeSeriesMeasurement;
	}
	public void setTimeSeriesMeasurement(Measurement m) {
		this._timeSeriesMeasurement = m;
	}
	public byte getTimeSeriesType() {
		return this._timeSeriesType;
	}
	public void setTimeSeriesType(byte b) {
		this._timeSeriesType = b;
	}
	public File getMeasurementFile() {
		return this._measurementFile;
	}
	public void setMeasurementFile(File f) {
		this._measurementFile = f;
	}
	public void addColorMask(ColorMask mask) {
		this._colorMasks.add(mask);
	}
	public ArrayList<ColorMask> getColorMasks() {
		return this._colorMasks;
	}
	public void removeColorMask(ColorMask mask) {
		this._colorMasks.remove(mask);
	}
	public String getLastDirectory() {
		return this.lastDirectory;
	}
	public void setLastDirectory(String dir) {
		this.lastDirectory = dir;
	}
	public PrinterJob getPrinterJob() {
		return this._printerJob;
	}

	public String getAbsoluteFilename() {
		return absoluteFilename;
	}

	public void setAbsoluteFilename(String absoluteFilename) {
		this.absoluteFilename = absoluteFilename;
	}
}

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.Entrance
 * JD-Core Version:    0.6.2
 */