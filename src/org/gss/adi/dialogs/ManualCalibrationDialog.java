 package org.gss.adi.dialogs;
 
 import java.awt.BorderLayout;
 import java.awt.Color;
 import java.awt.Container;
 import java.awt.Font;
 import java.awt.Point;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.awt.event.MouseAdapter;
 import java.awt.event.MouseEvent;
 import java.awt.event.MouseMotionAdapter;
 import java.awt.image.BufferedImage;
 import java.util.ArrayList;
 import javax.swing.JButton;
 import javax.swing.JDialog;
 import javax.swing.JLabel;
 import javax.swing.JOptionPane;
 import javax.swing.JPanel;
 import javax.swing.JSlider;
 import javax.swing.JSpinner;
 import javax.swing.JTextArea;
 import javax.swing.JTextField;
 import javax.swing.border.EmptyBorder;
 import javax.swing.event.ChangeEvent;
 import javax.swing.event.ChangeListener;
 import org.gss.adi.Entrance;
 import org.gss.adi.NumberField;
 import org.gss.adi.ZoomPanLabel;
 import org.gss.adi.tools.ColorTools;
 import org.gss.adi.tools.Measurement;
 
 public class ManualCalibrationDialog extends JDialog
 {
   private static final long serialVersionUID = 7231708494744502538L;
   private final JPanel contentPanel = new JPanel();
   private JTextField picTitle;
   private JTextField txtLengthOfDrawn;
   private NumberField lengthOfLine;
   private JTextField unitOfLength;
   private JTextField txtUnitOfLength;
   private JTextField txtX;
   private JTextField txtY;
   private JSpinner startX;
   private JSpinner startY;
   private JTextField txtStartOfLine;
   private JTextField txtEndOfLine;
   private JSpinner endX;
   private JSpinner endY;
   private JTextArea textArea_1;
   private Entrance entrance;
   private JDialog me;
   private JSlider slider = new JSlider();
   private ZoomPanLabel label = new ZoomPanLabel(this.slider);
   private boolean TimeSeries;
   Integer[] x = { Integer.valueOf(0), Integer.valueOf(0) };
   Integer[] y = { Integer.valueOf(0), Integer.valueOf(0) };
   private int moving = -1;
 
   public ManualCalibrationDialog(Entrance e, boolean timeSeries)
   {
     setModal(true);
     this.me = this;
     this.TimeSeries = timeSeries;
     this.slider.setFont(new Font("SansSerif", 0, 10));
     this.slider.setValue(0);
     this.slider.setBounds(565, 524, 250, 23);
     this.contentPanel.add(this.slider);
 
     this.label.setBounds(422, 11, 640, 480);
     this.contentPanel.add(this.label);
 
     this.label.getLabel().addMouseListener(new MouseAdapter()
     {
       public void mousePressed(MouseEvent e) {
         ManualCalibrationDialog.this.mousePress(e);
       }
 
       public void mouseReleased(MouseEvent e) {
         ManualCalibrationDialog.this.mouseRelease(e);
       }
     });
     this.label.getLabel().addMouseMotionListener(new MouseMotionAdapter()
     {
       public void mouseDragged(MouseEvent e) {
         ManualCalibrationDialog.this.mouseDrag(e);
       }
     });
     this.slider.addChangeListener(new ChangeListener()
     {
       public void stateChanged(ChangeEvent arg0) {
         Integer z = Integer.valueOf(ManualCalibrationDialog.this.slider.getValue());
/* 100 */         ManualCalibrationDialog.this.label.zoom(z.intValue());
/* 101 */         ManualCalibrationDialog.this.textArea_1.setText("Magnification: " + z.toString() + "%");
       }
     });
/* 104 */     this.slider.addMouseListener(new MouseAdapter()
     {
       public void mouseReleased(MouseEvent e) {
/* 107 */         Integer z = Integer.valueOf(ManualCalibrationDialog.this.slider.getValue());
/* 108 */         ManualCalibrationDialog.this.label.qualityZoom(z.intValue());
/* 109 */         ManualCalibrationDialog.this.textArea_1.setText("Magnification: " + z.toString() + "%");
       }
     });
/* 112 */     setup();
/* 113 */     setTitle("Manually Calibrate the Size of Pixels");
/* 114 */     this.entrance = e;
/* 115 */     setBounds(100, 100, 1096, 688);
/* 116 */     setLocation(25, 25);
/* 117 */     getContentPane().setLayout(new BorderLayout());
/* 118 */     this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
/* 119 */     getContentPane().add(this.contentPanel, "Center");
/* 120 */     this.contentPanel.setLayout(null);
/* 121 */     if (this.TimeSeries)
/* 122 */       this.label.setImage(e.getTimeSeries1());
     else
/* 124 */       this.label.setImage(e.getImage()); 
   }
 
/* 127 */   private int toolContains(int eX, int eY) { for (int i = 0; i < this.x.length; i++)
       try {
/* 129 */         if (ColorTools.linearDist(this.x[i].intValue(), this.y[i].intValue(), eX, eY).doubleValue() < 6.0D)
/* 130 */           return i;
       } catch (NullPointerException localNullPointerException) {
       }
/* 133 */     return -1; }
 
   public void mousePress(MouseEvent e) {
/* 136 */     Point p = this.label.mapToPixel(e.getX(), e.getY());
/* 137 */     int eX = p.x;
/* 138 */     int eY = p.y;
/* 139 */     this.moving = toolContains(eX, eY);
/* 140 */     if (this.moving == -1)
     {
       Integer tmp60_57 = Integer.valueOf(eX); this.x[1] = tmp60_57; this.x[0] = tmp60_57;
       Integer tmp78_75 = Integer.valueOf(eY); this.y[1] = tmp78_75; this.y[0] = tmp78_75;
/* 143 */       setPoint1(this.x[0].intValue(), this.y[0].intValue());
/* 144 */       setPoint2(this.x[1].intValue(), this.y[1].intValue());
     } else {
/* 146 */       this.x[this.moving] = Integer.valueOf(eX);
/* 147 */       this.y[this.moving] = Integer.valueOf(eY);
/* 148 */       setPoint1(this.x[0].intValue(), this.y[0].intValue());
/* 149 */       setPoint2(this.x[1].intValue(), this.y[1].intValue());
     }
/* 151 */     this.label.toolImage(this.x, this.y, this.entrance.getColor(), "Line", this.entrance.getLineWidth(), this.entrance.getCursorStyle());
   }
   public void mouseDrag(MouseEvent e) {
/* 154 */     Point p = this.label.mapToPixel(e.getX(), e.getY());
/* 155 */     int eX = p.x;
/* 156 */     int eY = p.y;
/* 157 */     if (eX >= this.label.getOriginal().getWidth())
/* 158 */       eX = this.label.getOriginal().getWidth() - 1;
/* 159 */     else if (eX < 0) {
/* 160 */       eX = 0;
     }
/* 162 */     if (eY >= this.label.getOriginal().getHeight())
/* 163 */       eY = this.label.getOriginal().getHeight() - 1;
/* 164 */     else if (eY < 0) {
/* 165 */       eY = 0;
     }
/* 167 */     if (this.moving == -1) {
/* 168 */       this.x[1] = Integer.valueOf(eX);
/* 169 */       this.y[1] = Integer.valueOf(eY);
/* 170 */       setPoint1(this.x[0].intValue(), this.y[0].intValue());
/* 171 */       setPoint2(this.x[1].intValue(), this.y[1].intValue());
     } else {
/* 173 */       this.x[this.moving] = Integer.valueOf(eX);
/* 174 */       this.y[this.moving] = Integer.valueOf(eY);
/* 175 */       setPoint1(this.x[0].intValue(), this.y[0].intValue());
/* 176 */       setPoint2(this.x[1].intValue(), this.y[1].intValue());
     }
/* 178 */     this.label.toolImage(this.x, this.y, this.entrance.getColor(), "Line", this.entrance.getLineWidth(), this.entrance.getCursorStyle());
   }
 
   public void mouseRelease(MouseEvent e) {
/* 182 */     Point p = this.label.mapToPixel(e.getX(), e.getY());
/* 183 */     int eX = p.x;
/* 184 */     int eY = p.y;
/* 185 */     if (eX >= this.label.getOriginal().getWidth())
/* 186 */       eX = this.label.getOriginal().getWidth() - 1;
/* 187 */     else if (eX < 0) {
/* 188 */       eX = 0;
     }
/* 190 */     if (eY >= this.label.getOriginal().getHeight())
/* 191 */       eY = this.label.getOriginal().getHeight() - 1;
/* 192 */     else if (eY < 0) {
/* 193 */       eY = 0;
     }
/* 195 */     if (this.moving == -1) {
/* 196 */       this.x[1] = Integer.valueOf(eX);
/* 197 */       this.y[1] = Integer.valueOf(eY);
/* 198 */       setPoint1(this.x[0].intValue(), this.y[0].intValue());
/* 199 */       setPoint2(this.x[1].intValue(), this.y[1].intValue());
     } else {
/* 201 */       this.x[this.moving] = Integer.valueOf(eX);
/* 202 */       this.y[this.moving] = Integer.valueOf(eY);
/* 203 */       setPoint1(this.x[0].intValue(), this.y[0].intValue());
/* 204 */       setPoint2(this.x[1].intValue(), this.y[1].intValue());
     }
/* 206 */     this.label.toolImage(this.x, this.y, this.entrance.getColor(), "Line", this.entrance.getLineWidth(), this.entrance.getCursorStyle());
   }
   public void setPoint1(int x1, int y1) {
/* 209 */     this.startX.setValue(Integer.valueOf(x1));
/* 210 */     this.startY.setValue(Integer.valueOf(y1));
   }
   public void setPoint2(int x2, int y2) {
/* 213 */     this.endX.setValue(Integer.valueOf(x2));
/* 214 */     this.endY.setValue(Integer.valueOf(y2));
   }
   private void setup() {
/* 217 */     JTextArea txtrManuallyCalibrateThe = new JTextArea();
/* 218 */     txtrManuallyCalibrateThe.setOpaque(false);
/* 219 */     txtrManuallyCalibrateThe.setEditable(false);
/* 220 */     txtrManuallyCalibrateThe.setFont(new Font("SansSerif", 0, 13));
/* 221 */     txtrManuallyCalibrateThe.setWrapStyleWord(true);
/* 222 */     txtrManuallyCalibrateThe.setLineWrap(true);
/* 223 */     txtrManuallyCalibrateThe.setText("Manually Calibrate The Pixel Size\r\n\r\n1) Click on the beginning of an object of known length visible in the image.  \r\n\r\n2) Drag to the end of the scale.  Release the mouse.  \r\n\r\nTIP: Draw along as much of the scale as possible.  The longer the line, the more precise the measurement.\r\n\r\nA colored line is drawn on the image.  If the line does not match the scale, either redraw the line or fine tune the start and stop positions of the line with the small arrows next to the x and y positons of the line end points, which are located below the image.\r\n\r\n3) When satisfied with the fit of the line to the scale, enter the length of the scale used in the labeled white box below.  \r\n\r\n4) Enter two letters that represent the unit of the scale being used in the labeled white box below.  For example, type \"in\" for inches and \"cm\" for centimeters.\r\n\r\n5) Click 'Done' when finished.  To re-run the calibration method  click 'Calibrate Length' in the File menu.\r\n\r\nTo cancel, close this window by clicking the 'close' icon in the upper left corner.");
/* 224 */     txtrManuallyCalibrateThe.setBounds(10, 11, 402, 500);
/* 225 */     this.contentPanel.add(txtrManuallyCalibrateThe);
 
/* 227 */     this.picTitle = new JTextField();
/* 228 */     this.picTitle.setBorder(null);
/* 229 */     this.picTitle.setOpaque(false);
/* 230 */     this.picTitle.setEditable(false);
/* 231 */     this.picTitle.setText("Untitled");
/* 232 */     this.picTitle.setBounds(422, 494, 442, 20);
/* 233 */     this.contentPanel.add(this.picTitle);
/* 234 */     this.picTitle.setColumns(10);
 
/* 236 */     this.txtLengthOfDrawn = new JTextField();
/* 237 */     this.txtLengthOfDrawn.setHorizontalAlignment(11);
/* 238 */     this.txtLengthOfDrawn.setBorder(null);
/* 239 */     this.txtLengthOfDrawn.setEditable(false);
/* 240 */     this.txtLengthOfDrawn.setOpaque(false);
/* 241 */     this.txtLengthOfDrawn.setText("Length of Drawn Line");
/* 242 */     this.txtLengthOfDrawn.setBounds(825, 524, 151, 20);
/* 243 */     this.contentPanel.add(this.txtLengthOfDrawn);
/* 244 */     this.txtLengthOfDrawn.setColumns(10);
 
/* 246 */     this.lengthOfLine = new NumberField();
/* 247 */     this.lengthOfLine.setBounds(976, 524, 86, 20);
/* 248 */     this.contentPanel.add(this.lengthOfLine);
/* 249 */     this.lengthOfLine.setColumns(10);
 
/* 251 */     this.unitOfLength = new JTextField();
/* 252 */     this.unitOfLength.setBounds(976, 555, 86, 20);
/* 253 */     this.contentPanel.add(this.unitOfLength);
/* 254 */     this.unitOfLength.setColumns(10);
 
/* 256 */     this.txtUnitOfLength = new JTextField();
/* 257 */     this.txtUnitOfLength.setHorizontalAlignment(11);
/* 258 */     this.txtUnitOfLength.setText("Unit of Length");
/* 259 */     this.txtUnitOfLength.setOpaque(false);
/* 260 */     this.txtUnitOfLength.setEditable(false);
/* 261 */     this.txtUnitOfLength.setColumns(10);
/* 262 */     this.txtUnitOfLength.setBorder(null);
/* 263 */     this.txtUnitOfLength.setBounds(825, 555, 151, 20);
/* 264 */     this.contentPanel.add(this.txtUnitOfLength);
 
/* 266 */     this.txtX = new JTextField();
/* 267 */     this.txtX.setText("X");
/* 268 */     this.txtX.setOpaque(false);
/* 269 */     this.txtX.setHorizontalAlignment(0);
/* 270 */     this.txtX.setEditable(false);
/* 271 */     this.txtX.setColumns(10);
/* 272 */     this.txtX.setBorder(null);
/* 273 */     this.txtX.setBounds(432, 524, 20, 20);
/* 274 */     this.contentPanel.add(this.txtX);
 
/* 276 */     this.txtY = new JTextField();
/* 277 */     this.txtY.setText("Y");
/* 278 */     this.txtY.setOpaque(false);
/* 279 */     this.txtY.setHorizontalAlignment(0);
/* 280 */     this.txtY.setEditable(false);
/* 281 */     this.txtY.setColumns(10);
/* 282 */     this.txtY.setBorder(null);
/* 283 */     this.txtY.setBounds(476, 524, 20, 20);
/* 284 */     this.contentPanel.add(this.txtY);
 
/* 286 */     this.startX = new JSpinner();
/* 287 */     this.startX.setFont(new Font("Tahoma", 0, 10));
/* 288 */     this.startX.setOpaque(false);
/* 289 */     this.startX.setBorder(null);
/* 290 */     this.startX.setBounds(400, 546, 63, 20);
/* 291 */     this.startX.addChangeListener(new ChangeListener()
     {
       public void stateChanged(ChangeEvent e) {
/* 294 */         int t = ((Integer)ManualCalibrationDialog.this.startX.getValue()).intValue();
/* 295 */         if ((t < ManualCalibrationDialog.this.label.getOriginal().getWidth()) && (t >= 0)) {
/* 296 */           ManualCalibrationDialog.this.x[0] = Integer.valueOf(t);
/* 297 */           ManualCalibrationDialog.this.label.toolImage(ManualCalibrationDialog.this.x, ManualCalibrationDialog.this.y, ManualCalibrationDialog.this.entrance.getColor(), "Line", ManualCalibrationDialog.this.entrance.getLineWidth(), ManualCalibrationDialog.this.entrance.getCursorStyle());
         } else {
/* 299 */           ManualCalibrationDialog.this.startX.setValue(ManualCalibrationDialog.this.x[0]);
         }
       }
     });
/* 303 */     this.contentPanel.add(this.startX);
 
/* 305 */     this.startY = new JSpinner();
/* 306 */     this.startY.setFont(new Font("Tahoma", 0, 10));
/* 307 */     this.startY.setOpaque(false);
/* 308 */     this.startY.setBorder(null);
/* 309 */     this.startY.setBounds(465, 546, 63, 20);
/* 310 */     this.startY.addChangeListener(new ChangeListener()
     {
       public void stateChanged(ChangeEvent e) {
/* 313 */         int t = ((Integer)ManualCalibrationDialog.this.startY.getValue()).intValue();
/* 314 */         if ((t < ManualCalibrationDialog.this.label.getOriginal().getHeight()) && (t >= 0)) {
/* 315 */           ManualCalibrationDialog.this.y[0] = Integer.valueOf(t);
/* 316 */           ManualCalibrationDialog.this.label.toolImage(ManualCalibrationDialog.this.x, ManualCalibrationDialog.this.y, ManualCalibrationDialog.this.entrance.getColor(), "Line", ManualCalibrationDialog.this.entrance.getLineWidth(), ManualCalibrationDialog.this.entrance.getCursorStyle());
         } else {
/* 318 */           ManualCalibrationDialog.this.startY.setValue(ManualCalibrationDialog.this.y[0]);
         }
       }
     });
/* 322 */     this.contentPanel.add(this.startY);
 
/* 324 */     this.txtStartOfLine = new JTextField();
/* 325 */     this.txtStartOfLine.setFont(new Font("Tahoma", 0, 10));
/* 326 */     this.txtStartOfLine.setHorizontalAlignment(11);
/* 327 */     this.txtStartOfLine.setBorder(null);
/* 328 */     this.txtStartOfLine.setEditable(false);
/* 329 */     this.txtStartOfLine.setOpaque(false);
/* 330 */     this.txtStartOfLine.setText("Start of Line");
/* 331 */     this.txtStartOfLine.setBounds(312, 546, 86, 20);
/* 332 */     this.contentPanel.add(this.txtStartOfLine);
/* 333 */     this.txtStartOfLine.setColumns(10);
 
/* 335 */     this.txtEndOfLine = new JTextField();
/* 336 */     this.txtEndOfLine.setFont(new Font("Tahoma", 0, 10));
/* 337 */     this.txtEndOfLine.setText("End of Line");
/* 338 */     this.txtEndOfLine.setOpaque(false);
/* 339 */     this.txtEndOfLine.setHorizontalAlignment(11);
/* 340 */     this.txtEndOfLine.setEditable(false);
/* 341 */     this.txtEndOfLine.setColumns(10);
/* 342 */     this.txtEndOfLine.setBorder(null);
/* 343 */     this.txtEndOfLine.setBounds(312, 572, 86, 20);
/* 344 */     this.contentPanel.add(this.txtEndOfLine);
 
/* 346 */     this.endX = new JSpinner();
/* 347 */     this.endX.setFont(new Font("Tahoma", 0, 10));
/* 348 */     this.endX.setOpaque(false);
/* 349 */     this.endX.setBorder(null);
/* 350 */     this.endX.setBounds(400, 572, 63, 20);
/* 351 */     this.endX.addChangeListener(new ChangeListener()
     {
       public void stateChanged(ChangeEvent e) {
/* 354 */         int t = ((Integer)ManualCalibrationDialog.this.endX.getValue()).intValue();
/* 355 */         if ((t < ManualCalibrationDialog.this.label.getOriginal().getWidth()) && (t >= 0)) {
/* 356 */           ManualCalibrationDialog.this.x[1] = Integer.valueOf(t);
/* 357 */           ManualCalibrationDialog.this.label.toolImage(ManualCalibrationDialog.this.x, ManualCalibrationDialog.this.y, ManualCalibrationDialog.this.entrance.getColor(), "Line", ManualCalibrationDialog.this.entrance.getLineWidth(), ManualCalibrationDialog.this.entrance.getCursorStyle());
         } else {
/* 359 */           ManualCalibrationDialog.this.endX.setValue(ManualCalibrationDialog.this.x[1]);
         }
       }
     });
/* 363 */     this.contentPanel.add(this.endX);
 
/* 365 */     this.endY = new JSpinner();
/* 366 */     this.endY.setFont(new Font("Tahoma", 0, 10));
/* 367 */     this.endY.setOpaque(false);
/* 368 */     this.endY.setBorder(null);
/* 369 */     this.endY.setBounds(465, 572, 63, 20);
/* 370 */     this.endY.addChangeListener(new ChangeListener()
     {
       public void stateChanged(ChangeEvent e) {
/* 373 */         int t = ((Integer)ManualCalibrationDialog.this.endY.getValue()).intValue();
/* 374 */         if ((t < ManualCalibrationDialog.this.label.getOriginal().getHeight()) && (t >= 0)) {
/* 375 */           ManualCalibrationDialog.this.y[1] = Integer.valueOf(t);
/* 376 */           ManualCalibrationDialog.this.label.toolImage(ManualCalibrationDialog.this.x, ManualCalibrationDialog.this.y, ManualCalibrationDialog.this.entrance.getColor(), "Line", ManualCalibrationDialog.this.entrance.getLineWidth(), ManualCalibrationDialog.this.entrance.getCursorStyle());
         } else {
/* 378 */           ManualCalibrationDialog.this.endY.setValue(ManualCalibrationDialog.this.y[1]);
         }
       }
     });
/* 382 */     this.contentPanel.add(this.endY);
 
/* 384 */     JTextArea txtrZoomOut_1 = new JTextArea();
/* 385 */     txtrZoomOut_1.setWrapStyleWord(true);
/* 386 */     txtrZoomOut_1.setText("Zoom out");
/* 387 */     txtrZoomOut_1.setOpaque(false);
/* 388 */     txtrZoomOut_1.setLineWrap(true);
/* 389 */     txtrZoomOut_1.setFont(new Font("SansSerif", 0, 12));
/* 390 */     txtrZoomOut_1.setEditable(false);
/* 391 */     txtrZoomOut_1.setBounds(757, 544, 100, 45);
/* 392 */     this.contentPanel.add(txtrZoomOut_1);
 
/* 394 */     this.textArea_1 = new JTextArea();
/* 395 */     this.textArea_1.setWrapStyleWord(true);
/* 396 */     this.textArea_1.setText("Magnification: 100%");
/* 397 */     this.textArea_1.setOpaque(false);
/* 398 */     this.textArea_1.setLineWrap(true);
/* 399 */     this.textArea_1.setFont(new Font("SansSerif", 0, 12));
/* 400 */     this.textArea_1.setEditable(false);
/* 401 */     this.textArea_1.setBounds(617, 541, 130, 22);
/* 402 */     this.contentPanel.add(this.textArea_1);
 
/* 404 */     JTextArea txtrZoomOut = new JTextArea();
/* 405 */     txtrZoomOut.setWrapStyleWord(true);
/* 406 */     txtrZoomOut.setText("Zoom out");
/* 407 */     txtrZoomOut.setOpaque(false);
/* 408 */     txtrZoomOut.setLineWrap(true);
/* 409 */     txtrZoomOut.setFont(new Font("SansSerif", 0, 12));
/* 410 */     txtrZoomOut.setEditable(false);
/* 411 */     txtrZoomOut.setBounds(533, 544, 86, 45);
/* 412 */     this.contentPanel.add(txtrZoomOut);
 
/* 414 */     JTextArea txtrWhenZoomedIn = new JTextArea();
/* 415 */     txtrWhenZoomedIn.setForeground(Color.BLUE);
/* 416 */     txtrWhenZoomedIn.setOpaque(false);
/* 417 */     txtrWhenZoomedIn.setEditable(false);
/* 418 */     txtrWhenZoomedIn.setFont(new Font("SansSerif", 0, 13));
/* 419 */     txtrWhenZoomedIn.setWrapStyleWord(true);
/* 420 */     txtrWhenZoomedIn.setLineWrap(true);
/* 421 */     txtrWhenZoomedIn.setText("When zoomed in, pan around the image by clicking and dragging the image.  Because of the text fields, the keyboard cannot be used to zoom and pan the image.");
/* 422 */     txtrWhenZoomedIn.setBounds(10, 513, 304, 85);
/* 423 */     this.contentPanel.add(txtrWhenZoomedIn);
 
/* 425 */     JButton btnDone = new JButton("Done");
/* 426 */     btnDone.setBounds(945, 614, 100, 30);
/* 427 */     this.contentPanel.add(btnDone);
/* 428 */     btnDone.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
/* 430 */         if (ManualCalibrationDialog.this.unitOfLength.getText().length() == 0) {
/* 431 */           JOptionPane.showMessageDialog(null, "You must input a unit of measure.");
/* 432 */         } else if (ManualCalibrationDialog.this.unitOfLength.getText().length() > 2) {
/* 433 */           JOptionPane.showMessageDialog(null, "The unit of lanegth must be one or two characters");
/* 434 */         } else if (ManualCalibrationDialog.this.lengthOfLine.getText().length() == 0) {
/* 435 */           JOptionPane.showMessageDialog(null, "You must enter the length of the drawn line.");
/* 436 */         } else if ((ManualCalibrationDialog.this.x[0] == null) || (ManualCalibrationDialog.this.x[1] == null)) {
/* 437 */           JOptionPane.showMessageDialog(null, "You must draw a line to measure.");
         } else {
/* 439 */           if (ManualCalibrationDialog.this.TimeSeries)
/* 440 */             ManualCalibrationDialog.this.entrance.setTimeSeriesMeasurement(new Measurement(ColorTools.getLinePixels(ManualCalibrationDialog.this.x, ManualCalibrationDialog.this.y).size(), 
/* 441 */               new Double(ManualCalibrationDialog.this.lengthOfLine.getText()), ManualCalibrationDialog.this.unitOfLength.getText()));
           else
/* 443 */             ManualCalibrationDialog.this.entrance.setMeasurement(new Measurement(ColorTools.getLinePixels(ManualCalibrationDialog.this.x, ManualCalibrationDialog.this.y).size(), 
/* 444 */               new Double(ManualCalibrationDialog.this.lengthOfLine.getText()), ManualCalibrationDialog.this.unitOfLength.getText()));
/* 445 */           ManualCalibrationDialog.this.me.dispose();
         }
       }
     });
/* 449 */     JButton cancelButton = new JButton("Cancel");
/* 450 */     cancelButton.setBounds(834, 614, 100, 30);
/* 451 */     this.contentPanel.add(cancelButton);
/* 452 */     cancelButton.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
/* 454 */         ManualCalibrationDialog.this.me.dispose();
       }
     });
   }
 }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.dialogs.ManualCalibrationDialog
 * JD-Core Version:    0.6.2
 */