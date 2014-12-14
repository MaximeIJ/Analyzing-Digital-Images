package org.gss.adi;

import java.util.Arrays;
public class EnhanceColorsMemory extends ZoomMemory {

	private static final int RINT  = 1;
	private static final int GINT  = 2;
	private static final int BINT  = 3;
	private static final int AINT  = 4;
	private static final int RVG  = 5;
	private static final int RVB  = 6;
	private static final int GVB  = 7;

	private int[] R, G, B;
	private int enhancement;
	public EnhanceColorsMemory() {
		super();
		setDefaults();
	}
	public EnhanceColorsMemory(int[] r, int[] g, int[] b, int enhancement) {
		super();
		R = r;
		G = g;
		B = b;
	}
	
	public int[] getR() {
		return R;
	}
	public void setR(int[] r) {
		R = r;
	}
	public int[] getG() {
		return G;
	}
	public void setG(int[] g) {
		G = g;
	}
	public int[] getB() {
		return B;
	}
	public void setB(int[] b) {
		B = b;
	}
	public int getEnhancement() {
		return enhancement;
	}
	public void setEnhancement(int enhancement) {
		this.enhancement = enhancement;
	}

	private void setDefaults(){
		int[] def = {0, 256, 0, 1};
		this.R = this.G = this.B = def;
		this.enhancement = 0;
	}
	
	@Override
	public String toString() {
		return "EnhanceColorsMemory [R=" + Arrays.toString(R) + ", G="
				+ Arrays.toString(G) + ", B=" + Arrays.toString(B)
				+ ", enhancement=" + enhancement + ", getZoomFactor()="
				+ getZoomFactor() + ", getX()=" + getX() + ", getY()=" + getY()
				+ "]";
	}
	public EnhanceColorsMemory(float zoomFactor, int hAlign, int vAlign,
			int[] r, int[] g, int[] b, int enhancement) {
		super(zoomFactor, hAlign, vAlign);
		R = r;
		G = g;
		B = b;
		this.enhancement = enhancement;
	}
	
	




}
