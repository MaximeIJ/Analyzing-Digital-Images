package org.gss.adi;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;

import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicSliderUI;

public class CustomSliderUI extends BasicSliderUI {

    private String type;

    public CustomSliderUI(JSlider slider, String t) {
        super(slider);
        this.type = t;
    }

	@Override
    public void paintThumb(Graphics g) {
		
    	((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
    	this.slider.repaint();
        int textX = 0;
        int value = (100 * this.slider.getValue())/256;
        if(value <= 50)
        	textX = thumbRect.x+10;
        else
        	textX = thumbRect.x-26;
        
        if(type == "Max"){
        	((Graphics2D)g).setColor(new Color(0x6A107F));
        	((Graphics2D)g).fillPolygon(new int[] {thumbRect.x + 2,thumbRect.x+thumbRect.width - 2,thumbRect.x + thumbRect.width/2}, new int[] {thumbRect.y + 5, thumbRect.y + 5, thumbRect.y}, 3);
        }
        else{
        	((Graphics2D)g).setColor(new Color(0xFF8C00));
        	((Graphics2D)g).fillPolygon(new int[] {thumbRect.x + 2,thumbRect.x+thumbRect.width - 2,thumbRect.x + thumbRect.width/2}, new int[] {thumbRect.y + 15, thumbRect.y + 15, thumbRect.y + 20}, 3);
        }
        
        ((Graphics2D)g).drawString(value+"%", textX, 14);
        ((Graphics2D)g).setStroke(new BasicStroke(2));
        ((Graphics2D)g).drawLine(thumbRect.x + thumbRect.width/2, thumbRect.y, thumbRect.x + thumbRect.width/2, thumbRect.y+15);
        ((Graphics2D)g).setColor(new Color(0x33333333, true));
        //((Graphics2D)g).setStroke(new BasicStroke(5));
        ((Graphics2D)g).drawString(type, this.slider.getWidth()/2 - 10, 14);
       //((Graphics2D)g).drawRect(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);

    }
}
