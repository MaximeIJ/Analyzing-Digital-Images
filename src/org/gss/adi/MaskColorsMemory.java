package org.gss.adi;

import java.util.Arrays;

public class MaskColorsMemory extends ZoomMemory {

	private int[] R, G, B;
	private Integer[] toolX, toolY;
	private String imgType;
	public MaskColorsMemory() {
		super();
		setDefaults();
	}
	
	private void setDefaults(){
		int[] def = {0, 256, 1};
		this.R = this.G = this.B = def;
		this.imgType = "Original";
		toolX = new Integer[2];
		toolY = new Integer[2];
	}
	
	@Override
	public String toString() {
		return "EnhanceColorsMemory [R=" + Arrays.toString(R) + ", G="
				+ Arrays.toString(G) + ", B=" + Arrays.toString(B)
				+ ", getZoomFactor()="
				+ getZoomFactor() + ", getX()=" + getX() + ", getY()=" + getY()
				+ "]";
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
	public String getImgType() {
		return imgType;
	}
	public void setImgType(String imgType) {
		this.imgType = imgType;
	}

	public Integer[] getToolX() {
		return toolX;
	}

	public void setToolX(Integer[] toolX) {
		this.toolX = toolX;
	}

	public Integer[] getToolY() {
		return toolY;
	}

	public void setToolY(Integer[] toolY) {
		this.toolY = toolY;
	}
	
}
