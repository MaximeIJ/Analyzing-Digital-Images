package org.gss.adi;

import java.util.Arrays;

public class TimeSeriesMemory extends ZoomMemory {


	private Integer[] toolX, toolY;
	private int toolType, enhancementType;
	
	public TimeSeriesMemory() {
		super();
		setDefaults();
	}
	private void setDefaults(){
		toolX = new Integer[] { 0, 0 };
		toolY = new Integer[] { 0, 0 };
		toolType = enhancementType = 0;
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
	public int getToolType() {
		return toolType;
	}
	public void setToolType(int toolType) {
		this.toolType = toolType;
	}
	public int getEnhancementType() {
		return enhancementType;
	}
	public void setEnhancementType(int enhancementType) {
		this.enhancementType = enhancementType;
	}
	@Override
	public String toString() {
		return "TimeSeriesMemory [toolX=" + Arrays.toString(toolX) + ", toolY="
				+ Arrays.toString(toolY) + ", toolType=" + toolType
				+ ", enhancementType=" + enhancementType + ", getZoomFactor()="
				+ getZoomFactor() + ", getX()=" + getX() + ", getY()=" + getY()
				+ "]";
	}
	
	
	
	

	
	




}
