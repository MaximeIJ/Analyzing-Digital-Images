package org.gss.adi;


public class SpacialAnalysisMemory extends ZoomMemory {

	private String imgType, colorType;
	private Integer[] toolX, toolY;
	private int toolType;
	
	
	public SpacialAnalysisMemory() {
		super();
		setDefaults();
	}
	

	private void setDefaults(){
		imgType = "Original";
		colorType = "RGB";
		toolX = new Integer[] { 0,0 };
		toolY = new Integer[] { 0,0 };
		toolType = 0;
	}


	public String getImgType() {
		return imgType;
	}


	public void setImgType(String imgType) {
		this.imgType = imgType;
	}


	public String getColorType() {
		return colorType;
	}


	public void setColorType(String colorType) {
		this.colorType = colorType;
	}


	public int  getToolType() {
		return toolType;
	}


	public void setToolType(int toolType) {
		this.toolType = toolType;
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
