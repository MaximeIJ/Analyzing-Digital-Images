/*     */ package org.gss.adi.dialogs;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Container;
/*     */ import java.awt.FlowLayout;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.text.DecimalFormat;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.border.EmptyBorder;
/*     */ 
/*     */ public class OGFGraphDialog extends JDialog
/*     */ {
/*     */   private static final long serialVersionUID = -5351161149192148538L;
/*  26 */   private final JPanel contentPanel = new JPanel();
/*  27 */   private JDialog me = this;
/*     */   private JTextField txtAmountOfOld;
/*     */   private JTextField txtPercent;
/*     */   private JTextField textField;
/*     */   private JTextField textField_1;
/*     */   private JTextField textField_2;
/*     */   private JTextField textField_3;
/*     */   private JTextField textField_4;
/*     */   private JTextField textField_5;
/*     */   private JTextField textField_6;
/*     */   private JTextField textField_7;
/*     */   private JTextField textField_8;
/*     */   private JTextField textField_9;
/*     */   private JTextField textField_10;
/*     */   private JTextField textField_11;
/*     */   private JTextField textField_12;
/*     */   private JTextField textField_13;
/*     */   private JTextField textField_14;
/*     */   private JTextField textField_15;
/*     */   private JTextField txtYear;
/*     */   private JTextField txtTotalMap;
/*     */   private JTextField textField_16;
/*     */   private JTextField textField_17;
/*     */   private JTextField textField_18;
/*     */   private JTextField textField_19;
/*     */   private JTextField textField_20;
/*     */   private JTextField textField_21;
/*     */   private JTextField textField_22;
/*     */   private JTextField textField_23;
/*     */   private JTextField p1990;
/*     */   private JTextField sa4;
/*     */   private JTextField sa3;
/*     */   private JTextField sa2;
/*     */   private JTextField sa1;
/*     */   private JTextField sa5;
/*     */   private JTextField p1620;
/*     */   private JTextField p1850;
/*     */   private JTextField p1926;
/*     */ 
/*     */   public OGFGraphDialog(Float[] percentages)
/*     */   {
/*  70 */     setVisible(true);
/*  71 */     setBounds(100, 100, 600, 568);
/*  72 */     getContentPane().setLayout(new BorderLayout());
/*  73 */     this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
/*  74 */     getContentPane().add(this.contentPanel, "Center");
/*  75 */     this.contentPanel.setLayout(null);
/*  76 */     setAlwaysOnTop(true);
/*     */ 
/*  78 */     setup();
/*     */ 
/*  80 */     BufferedImage img = new BufferedImage(410, 410, 2);
/*  81 */     Graphics2D g = img.createGraphics();
/*  82 */     g.setColor(Color.WHITE);
/*  83 */     g.fillRect(11, 0, 410, 400);
/*  84 */     g.setColor(Color.BLACK);
/*  85 */     g.drawLine(0, 1, 11, 1);
/*  86 */     g.drawLine(0, 40, 11, 40);
/*  87 */     g.drawLine(0, 80, 11, 80);
/*  88 */     g.drawLine(0, 120, 11, 120);
/*  89 */     g.drawLine(0, 160, 11, 160);
/*  90 */     g.drawLine(0, 200, 11, 200);
/*  91 */     g.drawLine(0, 240, 11, 240);
/*  92 */     g.drawLine(0, 280, 11, 280);
/*  93 */     g.drawLine(0, 320, 11, 320);
/*  94 */     g.drawLine(0, 360, 11, 360);
/*  95 */     g.drawLine(0, 398, 11, 398);
/*  96 */     g.drawLine(11, 400, 11, 410);
/*  97 */     g.drawLine(60, 400, 60, 410);
/*  98 */     g.drawLine(110, 400, 110, 410);
/*  99 */     g.drawLine(160, 400, 160, 410);
/* 100 */     g.drawLine(210, 400, 210, 410);
/* 101 */     g.drawLine(260, 400, 260, 410);
/* 102 */     g.drawLine(310, 400, 310, 410);
/* 103 */     g.drawLine(360, 400, 360, 410);
/* 104 */     g.drawLine(408, 400, 408, 410);
/* 105 */     g.setStroke(new BasicStroke(2.0F));
/* 106 */     g.setColor(Color.BLACK);
/* 107 */     g.drawLine(11, 399, 410, 399);
/* 108 */     g.drawLine(11, 0, 11, 400);
/* 109 */     g.drawLine(409, 0, 409, 400);
/* 110 */     g.drawLine(11, 1, 410, 1);
/* 111 */     g.setColor(Color.BLUE);
/* 112 */     g.drawLine(30, percentLocation(48.400002F), 260, percentLocation(40.200001F));
/* 113 */     g.drawLine(260, percentLocation(40.200001F), 336, percentLocation(10.2F));
/* 114 */     g.drawLine(336, percentLocation(10.2F), 400, percentLocation(2.0F));
/* 115 */     if (percentages[0] == null) {
/* 116 */       this.sa1.setVisible(false); this.sa2.setVisible(false); this.sa3.setVisible(false); this.sa4.setVisible(false); this.sa5.setVisible(false);
/*     */     } else {
/* 118 */       DecimalFormat df = new DecimalFormat("##.#");
/* 119 */       this.p1620.setText(df.format(percentages[0]) + "%");
/* 120 */       this.p1850.setText(df.format(percentages[1]) + "%");
/* 121 */       this.p1926.setText(df.format(percentages[2]) + "%");
/* 122 */       this.p1990.setText(df.format(percentages[3]) + "%");
/* 123 */       g.setColor(Color.RED);
/* 124 */       g.drawLine(30, percentLocation(percentages[0].floatValue()), 260, percentLocation(percentages[1].floatValue()));
/* 125 */       g.drawLine(260, percentLocation(percentages[1].floatValue()), 336, percentLocation(percentages[2].floatValue()));
/* 126 */       g.drawLine(336, percentLocation(percentages[2].floatValue()), 400, percentLocation(percentages[3].floatValue()));
/*     */     }
/* 128 */     g.dispose();
/*     */ 
/* 130 */     JLabel lblNewLabel = new JLabel();
/* 131 */     lblNewLabel.setBounds(56, 40, 410, 410);
/* 132 */     lblNewLabel.setIcon(new ImageIcon(img));
/* 133 */     this.contentPanel.add(lblNewLabel);
/*     */   }
/*     */   private int percentLocation(float d) {
/* 136 */     return Math.round(400.0F - 4.0F * d);
/*     */   }
/*     */   private void setup() {
/* 139 */     this.txtAmountOfOld = new JTextField();
/* 140 */     this.txtAmountOfOld.setOpaque(false);
/* 141 */     this.txtAmountOfOld.setFont(new Font("SansSerif", 1, 13));
/* 142 */     this.txtAmountOfOld.setBorder(null);
/* 143 */     this.txtAmountOfOld.setEditable(false);
/* 144 */     this.txtAmountOfOld.setHorizontalAlignment(0);
/* 145 */     this.txtAmountOfOld.setText("Amount of Old Growth Forest in the US");
/* 146 */     this.txtAmountOfOld.setBounds(56, 11, 410, 20);
/* 147 */     this.contentPanel.add(this.txtAmountOfOld);
/* 148 */     this.txtAmountOfOld.setColumns(10);
/*     */ 
/* 150 */     this.txtPercent = new JTextField();
/* 151 */     this.txtPercent.setOpaque(false);
/* 152 */     this.txtPercent.setHorizontalAlignment(0);
/* 153 */     this.txtPercent.setForeground(Color.MAGENTA);
/* 154 */     this.txtPercent.setFont(new Font("Tahoma", 0, 10));
/* 155 */     this.txtPercent.setEditable(false);
/* 156 */     this.txtPercent.setBorder(null);
/* 157 */     this.txtPercent.setText("Percent");
/* 158 */     this.txtPercent.setBounds(0, 12, 60, 20);
/* 159 */     this.contentPanel.add(this.txtPercent);
/* 160 */     this.txtPercent.setColumns(10);
/*     */ 
/* 162 */     this.textField = new JTextField();
/* 163 */     this.textField.setOpaque(false);
/* 164 */     this.textField.setBorder(null);
/* 165 */     this.textField.setFont(new Font("Tahoma", 0, 10));
/* 166 */     this.textField.setEditable(false);
/* 167 */     this.textField.setHorizontalAlignment(11);
/* 168 */     this.textField.setText("90%");
/* 169 */     this.textField.setBounds(10, 70, 40, 20);
/* 170 */     this.contentPanel.add(this.textField);
/* 171 */     this.textField.setColumns(10);
/*     */ 
/* 173 */     this.textField_1 = new JTextField();
/* 174 */     this.textField_1.setOpaque(false);
/* 175 */     this.textField_1.setText("80%");
/* 176 */     this.textField_1.setHorizontalAlignment(11);
/* 177 */     this.textField_1.setFont(new Font("Tahoma", 0, 10));
/* 178 */     this.textField_1.setEditable(false);
/* 179 */     this.textField_1.setColumns(10);
/* 180 */     this.textField_1.setBorder(null);
/* 181 */     this.textField_1.setBounds(10, 110, 40, 20);
/* 182 */     this.contentPanel.add(this.textField_1);
/*     */ 
/* 184 */     this.textField_2 = new JTextField();
/* 185 */     this.textField_2.setOpaque(false);
/* 186 */     this.textField_2.setText("70%");
/* 187 */     this.textField_2.setHorizontalAlignment(11);
/* 188 */     this.textField_2.setFont(new Font("Tahoma", 0, 10));
/* 189 */     this.textField_2.setEditable(false);
/* 190 */     this.textField_2.setColumns(10);
/* 191 */     this.textField_2.setBorder(null);
/* 192 */     this.textField_2.setBounds(10, 150, 40, 20);
/* 193 */     this.contentPanel.add(this.textField_2);
/*     */ 
/* 195 */     this.textField_3 = new JTextField();
/* 196 */     this.textField_3.setOpaque(false);
/* 197 */     this.textField_3.setText("60%");
/* 198 */     this.textField_3.setHorizontalAlignment(11);
/* 199 */     this.textField_3.setFont(new Font("Tahoma", 0, 10));
/* 200 */     this.textField_3.setEditable(false);
/* 201 */     this.textField_3.setColumns(10);
/* 202 */     this.textField_3.setBorder(null);
/* 203 */     this.textField_3.setBounds(10, 190, 40, 20);
/* 204 */     this.contentPanel.add(this.textField_3);
/*     */ 
/* 206 */     this.textField_4 = new JTextField();
/* 207 */     this.textField_4.setOpaque(false);
/* 208 */     this.textField_4.setText("50%");
/* 209 */     this.textField_4.setHorizontalAlignment(11);
/* 210 */     this.textField_4.setFont(new Font("Tahoma", 0, 10));
/* 211 */     this.textField_4.setEditable(false);
/* 212 */     this.textField_4.setColumns(10);
/* 213 */     this.textField_4.setBorder(null);
/* 214 */     this.textField_4.setBounds(10, 230, 40, 20);
/* 215 */     this.contentPanel.add(this.textField_4);
/*     */ 
/* 217 */     this.textField_5 = new JTextField();
/* 218 */     this.textField_5.setOpaque(false);
/* 219 */     this.textField_5.setText("40%");
/* 220 */     this.textField_5.setHorizontalAlignment(11);
/* 221 */     this.textField_5.setFont(new Font("Tahoma", 0, 10));
/* 222 */     this.textField_5.setEditable(false);
/* 223 */     this.textField_5.setColumns(10);
/* 224 */     this.textField_5.setBorder(null);
/* 225 */     this.textField_5.setBounds(10, 270, 40, 20);
/* 226 */     this.contentPanel.add(this.textField_5);
/*     */ 
/* 228 */     this.textField_6 = new JTextField();
/* 229 */     this.textField_6.setOpaque(false);
/* 230 */     this.textField_6.setText("30%");
/* 231 */     this.textField_6.setHorizontalAlignment(11);
/* 232 */     this.textField_6.setFont(new Font("Tahoma", 0, 10));
/* 233 */     this.textField_6.setEditable(false);
/* 234 */     this.textField_6.setColumns(10);
/* 235 */     this.textField_6.setBorder(null);
/* 236 */     this.textField_6.setBounds(10, 310, 40, 20);
/* 237 */     this.contentPanel.add(this.textField_6);
/*     */ 
/* 239 */     this.textField_7 = new JTextField();
/* 240 */     this.textField_7.setOpaque(false);
/* 241 */     this.textField_7.setText("20%");
/* 242 */     this.textField_7.setHorizontalAlignment(11);
/* 243 */     this.textField_7.setFont(new Font("Tahoma", 0, 10));
/* 244 */     this.textField_7.setEditable(false);
/* 245 */     this.textField_7.setColumns(10);
/* 246 */     this.textField_7.setBorder(null);
/* 247 */     this.textField_7.setBounds(10, 350, 40, 20);
/* 248 */     this.contentPanel.add(this.textField_7);
/*     */ 
/* 250 */     this.textField_8 = new JTextField();
/* 251 */     this.textField_8.setOpaque(false);
/* 252 */     this.textField_8.setText("10%");
/* 253 */     this.textField_8.setHorizontalAlignment(11);
/* 254 */     this.textField_8.setFont(new Font("Tahoma", 0, 10));
/* 255 */     this.textField_8.setEditable(false);
/* 256 */     this.textField_8.setColumns(10);
/* 257 */     this.textField_8.setBorder(null);
/* 258 */     this.textField_8.setBounds(10, 390, 40, 20);
/* 259 */     this.contentPanel.add(this.textField_8);
/*     */ 
/* 261 */     this.textField_9 = new JTextField();
/* 262 */     this.textField_9.setOpaque(false);
/* 263 */     this.textField_9.setText("0%");
/* 264 */     this.textField_9.setHorizontalAlignment(11);
/* 265 */     this.textField_9.setFont(new Font("Tahoma", 0, 10));
/* 266 */     this.textField_9.setEditable(false);
/* 267 */     this.textField_9.setColumns(10);
/* 268 */     this.textField_9.setBorder(null);
/* 269 */     this.textField_9.setBounds(10, 430, 40, 20);
/* 270 */     this.contentPanel.add(this.textField_9);
/*     */ 
/* 272 */     this.textField_10 = new JTextField();
/* 273 */     this.textField_10.setOpaque(false);
/* 274 */     this.textField_10.setText("100%");
/* 275 */     this.textField_10.setHorizontalAlignment(11);
/* 276 */     this.textField_10.setFont(new Font("Tahoma", 0, 10));
/* 277 */     this.textField_10.setEditable(false);
/* 278 */     this.textField_10.setColumns(10);
/* 279 */     this.textField_10.setBorder(null);
/* 280 */     this.textField_10.setBounds(10, 30, 40, 20);
/* 281 */     this.contentPanel.add(this.textField_10);
/*     */ 
/* 283 */     this.textField_11 = new JTextField();
/* 284 */     this.textField_11.setOpaque(false);
/* 285 */     this.textField_11.setText("1600");
/* 286 */     this.textField_11.setHorizontalAlignment(0);
/* 287 */     this.textField_11.setFont(new Font("Tahoma", 0, 10));
/* 288 */     this.textField_11.setEditable(false);
/* 289 */     this.textField_11.setColumns(10);
/* 290 */     this.textField_11.setBorder(null);
/* 291 */     this.textField_11.setBounds(46, 451, 40, 20);
/* 292 */     this.contentPanel.add(this.textField_11);
/*     */ 
/* 294 */     this.textField_12 = new JTextField();
/* 295 */     this.textField_12.setOpaque(false);
/* 296 */     this.textField_12.setText("1700");
/* 297 */     this.textField_12.setHorizontalAlignment(0);
/* 298 */     this.textField_12.setFont(new Font("Tahoma", 0, 10));
/* 299 */     this.textField_12.setEditable(false);
/* 300 */     this.textField_12.setColumns(10);
/* 301 */     this.textField_12.setBorder(null);
/* 302 */     this.textField_12.setBounds(146, 450, 40, 20);
/* 303 */     this.contentPanel.add(this.textField_12);
/*     */ 
/* 305 */     this.textField_13 = new JTextField();
/* 306 */     this.textField_13.setOpaque(false);
/* 307 */     this.textField_13.setText("1800");
/* 308 */     this.textField_13.setHorizontalAlignment(0);
/* 309 */     this.textField_13.setFont(new Font("Tahoma", 0, 10));
/* 310 */     this.textField_13.setEditable(false);
/* 311 */     this.textField_13.setColumns(10);
/* 312 */     this.textField_13.setBorder(null);
/* 313 */     this.textField_13.setBounds(246, 450, 40, 20);
/* 314 */     this.contentPanel.add(this.textField_13);
/*     */ 
/* 316 */     this.textField_14 = new JTextField();
/* 317 */     this.textField_14.setOpaque(false);
/* 318 */     this.textField_14.setText("1900");
/* 319 */     this.textField_14.setHorizontalAlignment(0);
/* 320 */     this.textField_14.setFont(new Font("Tahoma", 0, 10));
/* 321 */     this.textField_14.setEditable(false);
/* 322 */     this.textField_14.setColumns(10);
/* 323 */     this.textField_14.setBorder(null);
/* 324 */     this.textField_14.setBounds(346, 450, 40, 20);
/* 325 */     this.contentPanel.add(this.textField_14);
/*     */ 
/* 327 */     this.textField_15 = new JTextField();
/* 328 */     this.textField_15.setOpaque(false);
/* 329 */     this.textField_15.setText("2000");
/* 330 */     this.textField_15.setHorizontalAlignment(0);
/* 331 */     this.textField_15.setFont(new Font("Tahoma", 0, 10));
/* 332 */     this.textField_15.setEditable(false);
/* 333 */     this.textField_15.setColumns(10);
/* 334 */     this.textField_15.setBorder(null);
/* 335 */     this.textField_15.setBounds(446, 450, 40, 20);
/* 336 */     this.contentPanel.add(this.textField_15);
/*     */ 
/* 338 */     this.txtYear = new JTextField();
/* 339 */     this.txtYear.setOpaque(false);
/* 340 */     this.txtYear.setText("Year");
/* 341 */     this.txtYear.setHorizontalAlignment(0);
/* 342 */     this.txtYear.setForeground(Color.MAGENTA);
/* 343 */     this.txtYear.setFont(new Font("Tahoma", 0, 10));
/* 344 */     this.txtYear.setEditable(false);
/* 345 */     this.txtYear.setColumns(10);
/* 346 */     this.txtYear.setBorder(null);
/* 347 */     this.txtYear.setBounds(474, 466, 50, 20);
/* 348 */     this.contentPanel.add(this.txtYear);
/*     */ 
/* 350 */     this.txtTotalMap = new JTextField();
/* 351 */     this.txtTotalMap.setOpaque(false);
/* 352 */     this.txtTotalMap.setFont(new Font("Tahoma", 1, 11));
/* 353 */     this.txtTotalMap.setBorder(null);
/* 354 */     this.txtTotalMap.setEditable(false);
/* 355 */     this.txtTotalMap.setHorizontalAlignment(0);
/* 356 */     this.txtTotalMap.setText("Total Map");
/* 357 */     this.txtTotalMap.setBounds(489, 40, 80, 20);
/* 358 */     this.contentPanel.add(this.txtTotalMap);
/* 359 */     this.txtTotalMap.setColumns(10);
/*     */ 
/* 361 */     this.textField_16 = new JTextField();
/* 362 */     this.textField_16.setOpaque(false);
/* 363 */     this.textField_16.setForeground(Color.BLUE);
/* 364 */     this.textField_16.setHorizontalAlignment(0);
/* 365 */     this.textField_16.setText("1620");
/* 366 */     this.textField_16.setEditable(false);
/* 367 */     this.textField_16.setBorder(null);
/* 368 */     this.textField_16.setBounds(476, 60, 50, 15);
/* 369 */     this.contentPanel.add(this.textField_16);
/* 370 */     this.textField_16.setColumns(10);
/*     */ 
/* 372 */     this.textField_17 = new JTextField();
/* 373 */     this.textField_17.setOpaque(false);
/* 374 */     this.textField_17.setForeground(Color.BLUE);
/* 375 */     this.textField_17.setText("48.4%");
/* 376 */     this.textField_17.setHorizontalAlignment(0);
/* 377 */     this.textField_17.setEditable(false);
/* 378 */     this.textField_17.setColumns(10);
/* 379 */     this.textField_17.setBorder(null);
/* 380 */     this.textField_17.setBounds(526, 60, 58, 15);
/* 381 */     this.contentPanel.add(this.textField_17);
/*     */ 
/* 383 */     this.textField_18 = new JTextField();
/* 384 */     this.textField_18.setOpaque(false);
/* 385 */     this.textField_18.setForeground(Color.BLUE);
/* 386 */     this.textField_18.setText("1850");
/* 387 */     this.textField_18.setHorizontalAlignment(0);
/* 388 */     this.textField_18.setEditable(false);
/* 389 */     this.textField_18.setColumns(10);
/* 390 */     this.textField_18.setBorder(null);
/* 391 */     this.textField_18.setBounds(476, 75, 50, 15);
/* 392 */     this.contentPanel.add(this.textField_18);
/*     */ 
/* 394 */     this.textField_19 = new JTextField();
/* 395 */     this.textField_19.setOpaque(false);
/* 396 */     this.textField_19.setForeground(Color.BLUE);
/* 397 */     this.textField_19.setText("40.2%");
/* 398 */     this.textField_19.setHorizontalAlignment(0);
/* 399 */     this.textField_19.setEditable(false);
/* 400 */     this.textField_19.setColumns(10);
/* 401 */     this.textField_19.setBorder(null);
/* 402 */     this.textField_19.setBounds(526, 75, 58, 15);
/* 403 */     this.contentPanel.add(this.textField_19);
/*     */ 
/* 405 */     this.textField_20 = new JTextField();
/* 406 */     this.textField_20.setOpaque(false);
/* 407 */     this.textField_20.setForeground(Color.BLUE);
/* 408 */     this.textField_20.setText("1990");
/* 409 */     this.textField_20.setHorizontalAlignment(0);
/* 410 */     this.textField_20.setEditable(false);
/* 411 */     this.textField_20.setColumns(10);
/* 412 */     this.textField_20.setBorder(null);
/* 413 */     this.textField_20.setBounds(476, 105, 50, 15);
/* 414 */     this.contentPanel.add(this.textField_20);
/*     */ 
/* 416 */     this.textField_21 = new JTextField();
/* 417 */     this.textField_21.setOpaque(false);
/* 418 */     this.textField_21.setForeground(Color.BLUE);
/* 419 */     this.textField_21.setText("2%");
/* 420 */     this.textField_21.setHorizontalAlignment(0);
/* 421 */     this.textField_21.setEditable(false);
/* 422 */     this.textField_21.setColumns(10);
/* 423 */     this.textField_21.setBorder(null);
/* 424 */     this.textField_21.setBounds(526, 105, 58, 15);
/* 425 */     this.contentPanel.add(this.textField_21);
/*     */ 
/* 427 */     this.textField_22 = new JTextField();
/* 428 */     this.textField_22.setOpaque(false);
/* 429 */     this.textField_22.setForeground(Color.BLUE);
/* 430 */     this.textField_22.setText("10.2%");
/* 431 */     this.textField_22.setHorizontalAlignment(0);
/* 432 */     this.textField_22.setEditable(false);
/* 433 */     this.textField_22.setColumns(10);
/* 434 */     this.textField_22.setBorder(null);
/* 435 */     this.textField_22.setBounds(526, 90, 58, 15);
/* 436 */     this.contentPanel.add(this.textField_22);
/*     */ 
/* 438 */     this.textField_23 = new JTextField();
/* 439 */     this.textField_23.setOpaque(false);
/* 440 */     this.textField_23.setForeground(Color.BLUE);
/* 441 */     this.textField_23.setText("1926");
/* 442 */     this.textField_23.setHorizontalAlignment(0);
/* 443 */     this.textField_23.setEditable(false);
/* 444 */     this.textField_23.setColumns(10);
/* 445 */     this.textField_23.setBorder(null);
/* 446 */     this.textField_23.setBounds(476, 90, 50, 15);
/* 447 */     this.contentPanel.add(this.textField_23);
/*     */ 
/* 449 */     this.p1990 = new JTextField();
/* 450 */     this.p1990.setOpaque(false);
/* 451 */     this.p1990.setForeground(Color.RED);
/* 452 */     this.p1990.setHorizontalAlignment(0);
/* 453 */     this.p1990.setEditable(false);
/* 454 */     this.p1990.setColumns(10);
/* 455 */     this.p1990.setBorder(null);
/* 456 */     this.p1990.setBounds(526, 195, 58, 15);
/* 457 */     this.contentPanel.add(this.p1990);
/*     */ 
/* 459 */     this.sa4 = new JTextField();
/* 460 */     this.sa4.setOpaque(false);
/* 461 */     this.sa4.setForeground(Color.RED);
/* 462 */     this.sa4.setText("1990");
/* 463 */     this.sa4.setHorizontalAlignment(0);
/* 464 */     this.sa4.setEditable(false);
/* 465 */     this.sa4.setColumns(10);
/* 466 */     this.sa4.setBorder(null);
/* 467 */     this.sa4.setBounds(476, 195, 50, 15);
/* 468 */     this.contentPanel.add(this.sa4);
/*     */ 
/* 470 */     this.sa3 = new JTextField();
/* 471 */     this.sa3.setOpaque(false);
/* 472 */     this.sa3.setForeground(Color.RED);
/* 473 */     this.sa3.setText("1926");
/* 474 */     this.sa3.setHorizontalAlignment(0);
/* 475 */     this.sa3.setEditable(false);
/* 476 */     this.sa3.setColumns(10);
/* 477 */     this.sa3.setBorder(null);
/* 478 */     this.sa3.setBounds(476, 180, 50, 15);
/* 479 */     this.contentPanel.add(this.sa3);
/*     */ 
/* 481 */     this.sa2 = new JTextField();
/* 482 */     this.sa2.setOpaque(false);
/* 483 */     this.sa2.setForeground(Color.RED);
/* 484 */     this.sa2.setText("1850");
/* 485 */     this.sa2.setHorizontalAlignment(0);
/* 486 */     this.sa2.setEditable(false);
/* 487 */     this.sa2.setColumns(10);
/* 488 */     this.sa2.setBorder(null);
/* 489 */     this.sa2.setBounds(476, 165, 50, 15);
/* 490 */     this.contentPanel.add(this.sa2);
/*     */ 
/* 492 */     this.sa1 = new JTextField();
/* 493 */     this.sa1.setOpaque(false);
/* 494 */     this.sa1.setForeground(Color.RED);
/* 495 */     this.sa1.setText("1620");
/* 496 */     this.sa1.setHorizontalAlignment(0);
/* 497 */     this.sa1.setEditable(false);
/* 498 */     this.sa1.setColumns(10);
/* 499 */     this.sa1.setBorder(null);
/* 500 */     this.sa1.setBounds(476, 150, 50, 15);
/* 501 */     this.contentPanel.add(this.sa1);
/*     */ 
/* 503 */     this.sa5 = new JTextField();
/* 504 */     this.sa5.setOpaque(false);
/* 505 */     this.sa5.setFont(new Font("Tahoma", 1, 11));
/* 506 */     this.sa5.setText("Selected Area");
/* 507 */     this.sa5.setHorizontalAlignment(0);
/* 508 */     this.sa5.setEditable(false);
/* 509 */     this.sa5.setColumns(10);
/* 510 */     this.sa5.setBorder(null);
/* 511 */     this.sa5.setBounds(479, 130, 100, 20);
/* 512 */     this.contentPanel.add(this.sa5);
/*     */ 
/* 514 */     this.p1620 = new JTextField();
/* 515 */     this.p1620.setOpaque(false);
/* 516 */     this.p1620.setForeground(Color.RED);
/* 517 */     this.p1620.setHorizontalAlignment(0);
/* 518 */     this.p1620.setEditable(false);
/* 519 */     this.p1620.setColumns(10);
/* 520 */     this.p1620.setBorder(null);
/* 521 */     this.p1620.setBounds(526, 150, 58, 15);
/* 522 */     this.contentPanel.add(this.p1620);
/*     */ 
/* 524 */     this.p1850 = new JTextField();
/* 525 */     this.p1850.setOpaque(false);
/* 526 */     this.p1850.setForeground(Color.RED);
/* 527 */     this.p1850.setHorizontalAlignment(0);
/* 528 */     this.p1850.setEditable(false);
/* 529 */     this.p1850.setColumns(10);
/* 530 */     this.p1850.setBorder(null);
/* 531 */     this.p1850.setBounds(526, 165, 58, 15);
/* 532 */     this.contentPanel.add(this.p1850);
/*     */ 
/* 534 */     this.p1926 = new JTextField();
/* 535 */     this.p1926.setOpaque(false);
/* 536 */     this.p1926.setForeground(Color.RED);
/* 537 */     this.p1926.setHorizontalAlignment(0);
/* 538 */     this.p1926.setEditable(false);
/* 539 */     this.p1926.setColumns(10);
/* 540 */     this.p1926.setBorder(null);
/* 541 */     this.p1926.setBounds(526, 180, 58, 15);
/* 542 */     this.contentPanel.add(this.p1926);
/*     */ 
/* 544 */     JPanel buttonPane = new JPanel();
/* 545 */     buttonPane.setLayout(new FlowLayout(1));
/* 546 */     getContentPane().add(buttonPane, "South");
/*     */ 
/* 548 */     JButton cancelButton = new JButton("Close");
/* 549 */     cancelButton.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent arg0) {
/* 551 */         OGFGraphDialog.this.me.dispose();
/*     */       }
/*     */     });
/* 554 */     cancelButton.setActionCommand("Cancel");
/* 555 */     buttonPane.add(cancelButton);
/*     */   }
/*     */ }

/* Location:           C:\Users\Jordan\Downloads\ADIjava\AnalyzingDigitalImages.jar
 * Qualified Name:     org.gss.adi.dialogs.OGFGraphDialog
 * JD-Core Version:    0.6.2
 */