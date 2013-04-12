/*     */ package org.gss.adi;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.print.PrinterJob;
/*     */ import java.io.File;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.net.URLClassLoader;
/*     */ import java.util.ArrayList;
/*     */ import javax.swing.JPanel;
/*     */ import org.gss.adi.dialogs.CalibratePixelDialog;
/*     */ import org.gss.adi.dialogs.KeepSettingsDialog;
/*     */ import org.gss.adi.dialogs.SelectTimeSeriesDialog;
/*     */ import org.gss.adi.tools.ColorMask;
/*     */ import org.gss.adi.tools.ConfigFileManager;
/*     */ import org.gss.adi.tools.Measurement;
/*     */ 
/*     */ public class Entrance
/*     */ {
/*     */   private static MainFrame _mf;
/* 306 */   private Color _color = Color.yellow;
/* 307 */   private float _lineWidth = 1.0F;
/* 308 */   private byte _cursorStyle = 0;
/*     */   private BufferedImage _img;
/*     */   private String title;
/*     */   private BufferedImage _timeSeries1;
/*     */   private String title1;
/*     */   private BufferedImage _timeSeries2;
/*     */   private String title2;
/*     */   private BufferedImage _timeSeries3;
/*     */   private String title3;
/* 319 */   private byte _timeSeriesType = 0;
/*     */   private BufferedImage _enhancedImage;
/*     */   private BufferedImage _maskedImage;
/* 324 */   private Measurement _measurement = null;
/* 325 */   private Measurement _timeSeriesMeasurement = null;
/*     */ 
/* 327 */   private File _measurementFile = null;
/*     */ 
/* 329 */   private ArrayList<String> _recent = new ArrayList();
/* 330 */   private ArrayList<ColorMask> _colorMasks = new ArrayList();
/* 331 */   private String lastDirectory = null;
/* 332 */   private PrinterJob _printerJob = PrinterJob.getPrinterJob();
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws Exception
/*     */   {
/*     */     try
/*     */     {
/*  49 */       File swtJar = new File(getArchFilename("resources/swt"));
/*  50 */       addJarToClasspath(swtJar);
/*     */     } catch (Exception e1) {
/*  52 */       e1.printStackTrace();
/*     */     }
/*  54 */     new Entrance();
/*     */   }
/*     */ 
/*     */   public static void addJarToClasspath(File jarFile)
/*     */   {
/*     */     try
/*     */     {
/*  62 */       URL url = jarFile.toURI().toURL();
/*  63 */       URLClassLoader urlClassLoader = (URLClassLoader)ClassLoader.getSystemClassLoader();
/*  64 */       Class urlClass = URLClassLoader.class;
/*  65 */       Method method = urlClass.getDeclaredMethod("addURL", new Class[] { URL.class });
/*  66 */       method.setAccessible(true);
/*  67 */       method.invoke(urlClassLoader, new Object[] { url });
/*     */     } catch (Throwable t) {
/*  69 */       t.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*  73 */   public static String getArchFilename(String prefix) { return prefix + "_" + getOSName() + "_" + getArchName() + ".jar"; }
/*     */ 
/*     */   private static String getOSName()
/*     */   {
/*  77 */     String osNameProperty = System.getProperty("os.name");
/*  78 */     if (osNameProperty == null) {
/*  79 */       throw new RuntimeException("os.name property is not set");
/*     */     }
/*  81 */     osNameProperty = osNameProperty.toLowerCase();
/*     */ 
/*  83 */     if (osNameProperty.contains("win"))
/*  84 */       return "win";
/*  85 */     if (osNameProperty.contains("mac"))
/*  86 */       return "mac";
/*  87 */     if ((osNameProperty.contains("linux")) || (osNameProperty.contains("nix"))) {
/*  88 */       return "linux";
/*     */     }
/*  90 */     throw new RuntimeException("Unknown OS name: " + osNameProperty);
/*     */   }
/*     */ 
/*     */   private static String getArchName()
/*     */   {
/*  95 */     String osArch = System.getProperty("os.arch");
/*  96 */     if ((osArch != null) && (osArch.contains("64"))) {
/*  97 */       return "64";
/*     */     }
/*  99 */     return "32";
/*     */   }
/*     */ 
/*     */   public Entrance() {
/* 103 */     ConfigFileManager.createRecentImages();
/* 104 */     ConfigFileManager.usePrefs(this);
/* 105 */     createMainFrame();
/*     */   }
/*     */   public File openImage(String title) {
/* 108 */     File f = SWTFile.openImage(title, this);
/* 109 */     if (f == null)
/* 110 */       return null;
/* 111 */     ConfigFileManager.addRecentImage(f);
/* 112 */     return f;
/*     */   }
/*     */   protected void launchTimeSeries() {
/* 115 */     if (getTimeSeries2() == null)
/* 116 */       new SelectTimeSeriesDialog(this).setVisible(true);
/*     */     else
/* 118 */       new TimeSeriesPanel(this, getTimeSeries3() != null, this._timeSeriesType);
/*     */   }
/*     */ 
/*     */   private void createMainFrame() {
/* 122 */     _mf = new MainFrame(this);
/*     */   }
/*     */ 
/*     */   protected void setPane(JPanel pane)
/*     */   {
/* 131 */     final JPanel old = _mf.getPane();
/* 132 */     _mf.setPane(pane);
/* 133 */     _mf.getMenu().showSaveMeas(false);
/* 134 */     new Thread() {
/*     */       public void run() {
/*     */         try {
/* 137 */           ((ImagePanel)old).closingSequence(); } catch (Exception localException) {  } 
/*     */       }
/*     */     }
/* 139 */     .start();
/*     */   }
/*     */   public JPanel getPane() {
/* 142 */     return _mf.getPane();
/*     */   }
/*     */   public void updateTool() {
/*     */     try { ((Updatable)_mf.getPane()).updateTool(); } catch (Exception localException) {
/*     */     }
/*     */   }
/*     */   public void updatePic() { try { ((Updatable)_mf.getPane()).updatePic(); } catch (Exception localException) {
/*     */     } }
/*     */ 
/*     */   public void setImage(BufferedImage img) {
/* 152 */     this._img = new BufferedImage(img.getWidth(), img.getHeight(), 1);
/* 153 */     this._img.setRGB(0, 0, img.getWidth(), img.getHeight(), img.getRGB(0, 0, img.getWidth(), img.getHeight(), null, 0, img.getWidth()), 0, img.getWidth());
/* 154 */     this._enhancedImage = null;
/* 155 */     this._maskedImage = null;
/* 156 */     if ((this._img != null) && (this._measurement != null) && (img.getHeight() == this._img.getHeight()) && (img.getWidth() == this._img.getWidth())) {
/* 157 */       if (!KeepSettingsDialog.launch())
/*     */       {
/* 160 */         new CalibratePixelDialog(this, Boolean.valueOf(false)).setVisible(true);
/*     */       }
/*     */     } else new CalibratePixelDialog(this, Boolean.valueOf(false)).setVisible(true);
/*     */ 
/* 165 */     updatePic();
/*     */   }
/*     */   public BufferedImage getImage() {
/* 168 */     return this._img;
/*     */   }
/*     */   public ADIMenu getMenu() {
/* 171 */     return _mf.getMenu();
/*     */   }
/*     */   protected void setRecent(ArrayList<String> recent) {
/* 174 */     this._recent = recent;
/*     */   }
/*     */   protected ArrayList<String> getRecent() {
/* 177 */     return this._recent;
/*     */   }
/*     */   public void setTimeSeries1(BufferedImage img) {
/* 180 */     this._timeSeries1 = new BufferedImage(img.getWidth(), img.getHeight(), 1);
/* 181 */     this._timeSeries1.setRGB(0, 0, img.getWidth(), img.getHeight(), img.getRGB(0, 0, img.getWidth(), img.getHeight(), null, 0, img.getWidth()), 0, img.getWidth());
/*     */   }
/*     */   public void setTimeSeries2(BufferedImage img) {
/* 184 */     this._timeSeries2 = new BufferedImage(img.getWidth(), img.getHeight(), 1);
/* 185 */     this._timeSeries2.setRGB(0, 0, img.getWidth(), img.getHeight(), img.getRGB(0, 0, img.getWidth(), img.getHeight(), null, 0, img.getWidth()), 0, img.getWidth());
/*     */   }
/*     */   public void setTimeSeries3(BufferedImage img) {
/* 188 */     if (img == null) {
/* 189 */       this._timeSeries3 = null;
/*     */     } else {
/* 191 */       this._timeSeries3 = new BufferedImage(img.getWidth(), img.getHeight(), 1);
/* 192 */       this._timeSeries3.setRGB(0, 0, img.getWidth(), img.getHeight(), img.getRGB(0, 0, img.getWidth(), img.getHeight(), null, 0, img.getWidth()), 0, img.getWidth());
/*     */     }
/*     */   }
/*     */ 
/* 196 */   public BufferedImage getTimeSeries1() { return this._timeSeries1; }
/*     */ 
/*     */   protected BufferedImage getTimeSeries2() {
/* 199 */     return this._timeSeries2;
/*     */   }
/*     */   protected BufferedImage getTimeSeries3() {
/* 202 */     return this._timeSeries3;
/*     */   }
/*     */   public MainFrame getMainFrame() {
/* 205 */     return _mf;
/*     */   }
/*     */   public void setColor(Color color) {
/* 208 */     this._color = color;
/* 209 */     ConfigFileManager.setPrefs(this);
/*     */   }
/*     */   public Color getColor() {
/* 212 */     return this._color;
/*     */   }
/*     */   public void setLineWidth(float f) {
/* 215 */     this._lineWidth = f;
/* 216 */     ConfigFileManager.setPrefs(this);
/*     */   }
/*     */   public float getLineWidth() {
/* 219 */     return this._lineWidth;
/*     */   }
/*     */   public void setCursorStyle(byte style) {
/* 222 */     this._cursorStyle = style;
/* 223 */     ConfigFileManager.setPrefs(this);
/*     */   }
/*     */   public byte getCursorStyle() {
/* 226 */     return this._cursorStyle;
/*     */   }
/*     */   public BufferedImage getEnhancedImage() {
/* 229 */     return this._enhancedImage;
/*     */   }
/*     */   protected void setEnhancedImage(BufferedImage img) {
/* 232 */     this._enhancedImage = img;
/*     */   }
/*     */   public BufferedImage getMaskedImage() {
/* 235 */     return this._maskedImage;
/*     */   }
/*     */   protected void setMaskedImage(BufferedImage img) {
/* 238 */     this._maskedImage = img;
/*     */   }
/*     */   public void setTitle(String s) {
/* 241 */     this.title = s;
/*     */   }
/*     */   public String getTitle() {
/* 244 */     return this.title;
/*     */   }
/*     */   public void setTitle1(String s) {
/* 247 */     this.title1 = s;
/*     */   }
/*     */   public String getTitle1() {
/* 250 */     return this.title1;
/*     */   }
/*     */   public void setTitle2(String s) {
/* 253 */     this.title2 = s;
/*     */   }
/*     */   public String getTitle2() {
/* 256 */     return this.title2;
/*     */   }
/*     */   public void setTitle3(String s) {
/* 259 */     this.title3 = s;
/*     */   }
/*     */   public String getTitle3() {
/* 262 */     return this.title3;
/*     */   }
/*     */   public Measurement getMeasurement() {
/* 265 */     return this._measurement;
/*     */   }
/*     */   public void setMeasurement(Measurement m) {
/* 268 */     this._measurement = m;
/*     */   }
/*     */   public Measurement getTimeSeriesMeasurement() {
/* 271 */     return this._timeSeriesMeasurement;
/*     */   }
/*     */   public void setTimeSeriesMeasurement(Measurement m) {
/* 274 */     this._timeSeriesMeasurement = m;
/*     */   }
/*     */   public byte getTimeSeriesType() {
/* 277 */     return this._timeSeriesType;
/*     */   }
/*     */   public void setTimeSeriesType(byte b) {
/* 280 */     this._timeSeriesType = b;
/*     */   }
/*     */   public File getMeasurementFile() {
/* 283 */     return this._measurementFile;
/*     */   }
/*     */   public void setMeasurementFile(File f) {
/* 286 */     this._measurementFile = f;
/*     */   }
/*     */   public void addColorMask(ColorMask mask) {
/* 289 */     this._colorMasks.add(mask);
/*     */   }
/*     */   public ArrayList<ColorMask> getColorMasks() {
/* 292 */     return this._colorMasks;
/*     */   }
/*     */   public void removeColorMask(ColorMask mask) {
/* 295 */     this._colorMasks.remove(mask);
/*     */   }
/*     */   public String getLastDirectory() {
/* 298 */     return this.lastDirectory;
/*     */   }
/*     */   public void setLastDirectory(String dir) {
/* 301 */     this.lastDirectory = dir;
/*     */   }
/*     */   public PrinterJob getPrinterJob() {
/* 304 */     return this._printerJob;
/*     */   }
/*     */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.Entrance
 * JD-Core Version:    0.6.2
 */