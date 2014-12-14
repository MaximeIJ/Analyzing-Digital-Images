 package org.gss.adi.dialogs;
 
 import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.gss.adi.tools.MeasurementSaver;
 
 public class MeasurementDialog extends JDialog
 {
   private static final long serialVersionUID = 6635147074158358004L;
   private final JPanel pan = new JPanel();
   private JTextField tool;
   private JTextField lengthArea;
   private JTextField numPix;
   private JTextField x1;
   private JTextField y1;
   private JTextField x2;
   private JTextField y2;
   private JTextField colorScheme;
   private JTextField avgR;
   private JTextField maxR;
   private JTextField minR;
   private JTextField stdR;
   private JTextField avgG;
   private JTextField maxG;
   private JTextField minG;
   private JTextField stdG;
   private JTextField avgB;
   private JTextField maxB;
   private JTextField minB;
   private JTextField stdB;
   private JTextField data;
   private JTextField comment;
   private JButton cancel;
   private JButton save;
   private MeasurementDialog me = this;
   private JTextField redDesc;
   private JTextField greenDesc;
   private JTextField blueDesc;
   private JTextField imageName;
 
   public MeasurementDialog(boolean editable, String scalingFactor, String scalingUnit, String selectedTool, String la, String num, String[] coords, String scheme, String[] reds, String[] greens, String[] blues, String imgN)
   {
     setBounds(100, 100, 593, 336);
     setAlwaysOnTop(true);
     getContentPane().setLayout(new BorderLayout());
     this.pan.setBorder(new EmptyBorder(5, 5, 5, 5));
     getContentPane().add(this.pan, "Center");
     this.pan.setLayout(null);
 
     setup();
 
     if (selectedTool.contains("Pixel")) {
       this.lengthArea.setVisible(false);
       this.x2.setVisible(false);
       this.y2.setVisible(false);
       this.stdR.setVisible(false);
       this.stdG.setVisible(false);
       this.stdB.setVisible(false);
       this.avgR.setVisible(false);
       this.avgG.setVisible(false);
       this.avgB.setVisible(false);
       this.minR.setVisible(false);
       this.minG.setVisible(false);
       this.minB.setVisible(false);
       this.redDesc.setText("Red:");
       this.greenDesc.setText("Green:");
       this.blueDesc.setText("Blue:");
     } else if ((selectedTool.contains("Polygon"))){
    	 this.lengthArea.setText("Area: "); 
    	 this.x1.setVisible(false);
         this.y1.setVisible(false);
         this.x2.setVisible(false);
         this.y2.setVisible(false);
     }
     else if ((selectedTool.contains("Path"))) {
    	 this.lengthArea.setText("Length: "); 
    	 this.x1.setVisible(false);
       this.y1.setVisible(false);
       this.x2.setVisible(false);
       this.y2.setVisible(false);
     } else if (selectedTool.contains("Angle")) {
       this.numPix.setText("Angle = " + coords[0] + " degrees");
       this.lengthArea.setVisible(false);
       this.x1.setVisible(false);
       this.y1.setVisible(false);
       this.x2.setVisible(false);
       this.y2.setVisible(false);
       this.redDesc.setVisible(false);
       this.greenDesc.setVisible(false);
       this.blueDesc.setVisible(false);
       this.avgR.setVisible(false);
       this.maxR.setVisible(false);
       this.minR.setVisible(false);
       this.stdR.setVisible(false);
/* 100 */       this.avgG.setVisible(false);
/* 101 */       this.maxG.setVisible(false);
/* 102 */       this.minG.setVisible(false);
/* 103 */       this.stdG.setVisible(false);
/* 104 */       this.avgB.setVisible(false);
/* 105 */       this.maxB.setVisible(false);
/* 106 */       this.minB.setVisible(false);
/* 107 */       this.stdB.setVisible(false);
     }
     else if(selectedTool.contains("Line"))
    	 this.lengthArea.setText("Length: "); 
     else if(selectedTool.contains("Rectangle"))
    	 this.lengthArea.setText("Area: ");
/* 109 */     if (la.equals("0")) {
/* 110 */       this.lengthArea.setVisible(false);
     }
/* 112 */     append(this.tool, selectedTool);
/* 113 */     append(this.lengthArea, la);
/* 114 */     if (!selectedTool.contains("Angle"))
/* 115 */       append(this.numPix, num);
/* 116 */     append(this.x1, coords[0]);
/* 117 */     append(this.y1, coords[1]);
/* 118 */     append(this.x2, coords[2]);
/* 119 */     append(this.y2, coords[3]);
/* 120 */     append(this.colorScheme, scheme);
/* 121 */     this.avgR.setText(reds[0]);
/* 122 */     this.maxR.setText(reds[1]);
/* 123 */     this.minR.setText(reds[2]);
/* 124 */     this.stdR.setText(reds[3]);
/* 125 */     this.avgG.setText(greens[0]);
/* 126 */     this.maxG.setText(greens[1]);
/* 127 */     this.minG.setText(greens[2]);
/* 128 */     this.stdG.setText(greens[3]);
/* 129 */     this.avgB.setText(blues[0]);
/* 130 */     this.maxB.setText(blues[1]);
/* 131 */     this.minB.setText(blues[2]);
/* 132 */     this.stdB.setText(blues[3]);
 
/* 134 */     this.cancel = new JButton("Cancel");
/* 135 */     this.cancel.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent arg0) {
/* 138 */         MeasurementDialog.this.me.dispose();
       }
     });
/* 141 */     this.cancel.setBounds(479, 260, 89, 23);
/* 142 */     this.pan.add(this.cancel);
 
/* 144 */     this.save = new JButton("Save");
/* 145 */     this.save.addActionListener(new ActionListener() {
       public void actionPerformed(ActionEvent e) {
/* 147 */         MeasurementSaver.comment[0] = MeasurementDialog.this.comment.getText();
/* 148 */         MeasurementSaver.userData[0] = MeasurementDialog.this.data.getText();
/* 149 */         MeasurementDialog.this.me.dispose();
       }
     });
/* 152 */     this.save.setBounds(383, 260, 89, 23);
/* 153 */     this.pan.add(this.save);
/* 154 */     if (!editable) {
/* 155 */       this.comment.setEditable(false);
/* 156 */       this.comment.setBorder(null);
/* 157 */       this.comment.setText(MeasurementSaver.comment[0]);
/* 158 */       this.data.setEditable(false);
/* 159 */       this.data.setBorder(null);
/* 160 */       this.data.setText(MeasurementSaver.userData[0]);
/* 161 */       this.save.setVisible(false);
/* 162 */       this.cancel.setText("Close");
     }
this.imageName = new JTextField(imgN);
this.imageName.setBounds(10, 260, 350, 23);
this.imageName.setEditable(false);
this.imageName.setBorder(null);
this.pan.add(this.imageName);


   }
 
/* 166 */   private void append(JTextField field, String text) { field.setText(field.getText() + text); }
 
   private void setup() {
/* 169 */     this.tool = new JTextField();
/* 170 */     this.tool.setBorder(null);
/* 171 */     this.tool.setEditable(false);
/* 172 */     this.tool.setText("Tool:");
/* 173 */     this.tool.setBounds(10, 11, 174, 20);
/* 174 */     this.pan.add(this.tool);
/* 175 */     this.tool.setColumns(10);
 
/* 177 */     this.lengthArea = new JTextField();
/* 178 */     this.lengthArea.setBorder(null);
/* 179 */     this.lengthArea.setEditable(false);
/* 181 */     this.lengthArea.setBounds(194, 11, 174, 20);
/* 182 */     this.pan.add(this.lengthArea);
/* 183 */     this.lengthArea.setColumns(10);
 
/* 185 */     this.numPix = new JTextField();
/* 186 */     this.numPix.setBorder(null);
/* 187 */     this.numPix.setEditable(false);
/* 188 */     this.numPix.setText("Number of Pixels: ");
/* 189 */     this.numPix.setBounds(10, 42, 174, 20);
/* 190 */     this.pan.add(this.numPix);
/* 191 */     this.numPix.setColumns(10);
 
/* 193 */     this.x1 = new JTextField();
/* 194 */     this.x1.setBorder(null);
/* 195 */     this.x1.setEditable(false);
/* 196 */     this.x1.setText("X1 = ");
/* 197 */     this.x1.setBounds(194, 42, 86, 20);
/* 198 */     this.pan.add(this.x1);
/* 199 */     this.x1.setColumns(10);
 
/* 201 */     this.y1 = new JTextField();
/* 202 */     this.y1.setBorder(null);
/* 203 */     this.y1.setEditable(false);
/* 204 */     this.y1.setText("Y1 = ");
/* 205 */     this.y1.setColumns(10);
/* 206 */     this.y1.setBounds(290, 42, 86, 20);
/* 207 */     this.pan.add(this.y1);
 
/* 209 */     this.x2 = new JTextField();
/* 210 */     this.x2.setBorder(null);
/* 211 */     this.x2.setEditable(false);
/* 212 */     this.x2.setText("X2 = ");
/* 213 */     this.x2.setColumns(10);
/* 214 */     this.x2.setBounds(386, 42, 86, 20);
/* 215 */     this.pan.add(this.x2);
 
/* 217 */     this.y2 = new JTextField();
/* 218 */     this.y2.setBorder(null);
/* 219 */     this.y2.setEditable(false);
/* 220 */     this.y2.setText("Y2 = ");
/* 221 */     this.y2.setColumns(10);
/* 222 */     this.y2.setBounds(482, 42, 86, 20);
/* 223 */     this.pan.add(this.y2);
 
/* 225 */     this.colorScheme = new JTextField();
/* 226 */     this.colorScheme.setBorder(null);
/* 227 */     this.colorScheme.setEditable(false);
/* 228 */     this.colorScheme.setText("Color Scheme: ");
/* 229 */     this.colorScheme.setBounds(386, 11, 182, 20);
/* 230 */     this.pan.add(this.colorScheme);
/* 231 */     this.colorScheme.setColumns(10);
 
/* 233 */     this.redDesc = new JTextField();
/* 234 */     this.redDesc.setBorder(null);
/* 235 */     this.redDesc.setEditable(false);
/* 236 */     this.redDesc.setText("Red Avg/Max/Min/StdDev:");
/* 237 */     this.redDesc.setBounds(10, 73, 174, 20);
/* 238 */     this.pan.add(this.redDesc);
/* 239 */     this.redDesc.setColumns(10);
 
/* 241 */     this.avgR = new JTextField();
/* 242 */     this.avgR.setBorder(null);
/* 243 */     this.avgR.setEditable(false);
/* 244 */     this.avgR.setBounds(194, 73, 86, 20);
/* 245 */     this.pan.add(this.avgR);
/* 246 */     this.avgR.setColumns(10);
 
/* 248 */     this.maxR = new JTextField();
/* 249 */     this.maxR.setBorder(null);
/* 250 */     this.maxR.setEditable(false);
/* 251 */     this.maxR.setBounds(290, 73, 86, 20);
/* 252 */     this.pan.add(this.maxR);
/* 253 */     this.maxR.setColumns(10);
 
/* 255 */     this.minR = new JTextField();
/* 256 */     this.minR.setBorder(null);
/* 257 */     this.minR.setEditable(false);
/* 258 */     this.minR.setBounds(386, 73, 86, 20);
/* 259 */     this.pan.add(this.minR);
/* 260 */     this.minR.setColumns(10);
 
/* 262 */     this.stdR = new JTextField();
/* 263 */     this.stdR.setBorder(null);
/* 264 */     this.stdR.setEditable(false);
/* 265 */     this.stdR.setBounds(482, 73, 86, 20);
/* 266 */     this.pan.add(this.stdR);
/* 267 */     this.stdR.setColumns(10);
 
/* 269 */     this.greenDesc = new JTextField();
/* 270 */     this.greenDesc.setBorder(null);
/* 271 */     this.greenDesc.setEditable(false);
/* 272 */     this.greenDesc.setText("Green Avg/Max/Min/StdDev:");
/* 273 */     this.greenDesc.setColumns(10);
/* 274 */     this.greenDesc.setBounds(10, 104, 174, 20);
/* 275 */     this.pan.add(this.greenDesc);
 
/* 277 */     this.avgG = new JTextField();
/* 278 */     this.avgG.setBorder(null);
/* 279 */     this.avgG.setEditable(false);
/* 280 */     this.avgG.setColumns(10);
/* 281 */     this.avgG.setBounds(194, 104, 86, 20);
/* 282 */     this.pan.add(this.avgG);
 
/* 284 */     this.maxG = new JTextField();
/* 285 */     this.maxG.setBorder(null);
/* 286 */     this.maxG.setEditable(false);
/* 287 */     this.maxG.setColumns(10);
/* 288 */     this.maxG.setBounds(290, 104, 86, 20);
/* 289 */     this.pan.add(this.maxG);
 
/* 291 */     this.minG = new JTextField();
/* 292 */     this.minG.setBorder(null);
/* 293 */     this.minG.setEditable(false);
/* 294 */     this.minG.setColumns(10);
/* 295 */     this.minG.setBounds(386, 104, 86, 20);
/* 296 */     this.pan.add(this.minG);
 
/* 298 */     this.stdG = new JTextField();
/* 299 */     this.stdG.setBorder(null);
/* 300 */     this.stdG.setEditable(false);
/* 301 */     this.stdG.setColumns(10);
/* 302 */     this.stdG.setBounds(482, 104, 86, 20);
/* 303 */     this.pan.add(this.stdG);
 
/* 305 */     this.blueDesc = new JTextField();
/* 306 */     this.blueDesc.setBorder(null);
/* 307 */     this.blueDesc.setEditable(false);
/* 308 */     this.blueDesc.setText("Blue Avg/Max/Min/StdDev:");
/* 309 */     this.blueDesc.setColumns(10);
/* 310 */     this.blueDesc.setBounds(10, 135, 174, 20);
/* 311 */     this.pan.add(this.blueDesc);
 
/* 313 */     this.avgB = new JTextField();
/* 314 */     this.avgB.setBorder(null);
/* 315 */     this.avgB.setEditable(false);
/* 316 */     this.avgB.setColumns(10);
/* 317 */     this.avgB.setBounds(194, 135, 86, 20);
/* 318 */     this.pan.add(this.avgB);
 
/* 320 */     this.maxB = new JTextField();
/* 321 */     this.maxB.setBorder(null);
/* 322 */     this.maxB.setEditable(false);
/* 323 */     this.maxB.setColumns(10);
/* 324 */     this.maxB.setBounds(290, 135, 86, 20);
/* 325 */     this.pan.add(this.maxB);
 
/* 327 */     this.minB = new JTextField();
/* 328 */     this.minB.setBorder(null);
/* 329 */     this.minB.setEditable(false);
/* 330 */     this.minB.setColumns(10);
/* 331 */     this.minB.setBounds(386, 135, 86, 20);
/* 332 */     this.pan.add(this.minB);
 
/* 334 */     this.stdB = new JTextField();
/* 335 */     this.stdB.setBorder(null);
/* 336 */     this.stdB.setEditable(false);
/* 337 */     this.stdB.setColumns(10);
/* 338 */     this.stdB.setBounds(482, 135, 86, 20);
/* 339 */     this.pan.add(this.stdB);
 
/* 341 */     JTextField txtAditionalDataTo = new JTextField();
/* 342 */     txtAditionalDataTo.setFont(new Font("SansSerif", 0, 10));
/* 343 */     txtAditionalDataTo.setBorder(null);
/* 344 */     txtAditionalDataTo.setEditable(false);
/* 345 */     txtAditionalDataTo.setText("Aditional Data");
/* 346 */     txtAditionalDataTo.setBounds(10, 166, 174, 15);
/* 347 */     this.pan.add(txtAditionalDataTo);
/* 348 */     txtAditionalDataTo.setColumns(10);
 
/* 350 */     this.data = new JTextField();
/* 351 */     this.data.setBounds(10, 182, 558, 20);
/* 352 */     this.pan.add(this.data);
/* 353 */     this.data.setColumns(10);
 
/* 355 */     JTextField txtComment = new JTextField();
/* 356 */     txtComment.setText("Comment");
/* 357 */     txtComment.setFont(new Font("SansSerif", 0, 10));
/* 358 */     txtComment.setEditable(false);
/* 359 */     txtComment.setColumns(10);
/* 360 */     txtComment.setBorder(null);
/* 361 */     txtComment.setBounds(10, 213, 174, 15);
/* 362 */     this.pan.add(txtComment);
 
/* 364 */     this.comment = new JTextField();
/* 365 */     this.comment.setColumns(10);
/* 366 */     this.comment.setBounds(10, 229, 558, 20);
/* 367 */     this.pan.add(this.comment);

			 




   }
 }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.dialogs.MeasurementDialog
 * JD-Core Version:    0.6.2
 */