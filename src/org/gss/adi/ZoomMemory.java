package org.gss.adi;

public class ZoomMemory {

	static private float zoomFactor;
	static private int x, y;
	
	protected ZoomMemory(){
		this.zoomFactor = 1;
		this.x = 1;
		this.y = 1;
	}
	
	protected ZoomMemory(float zoomFactor, int hAlign, int vAlign) {
		super();
		this.zoomFactor = zoomFactor;
		this.x = hAlign;
		this.y = vAlign;
	}

	protected float getZoomFactor() {
		return zoomFactor;
	}

	protected void setZoomFactor(float zoomFactor) {
		this.zoomFactor = zoomFactor;
	}

	protected int getX() {
		return x;
	}

	protected void setX(int x) {
		this.x = x;
	}

	protected int getY() {
		return y;
	}

	protected void setY(int y) {
		this.y = y;
	}


	
	
	
}
