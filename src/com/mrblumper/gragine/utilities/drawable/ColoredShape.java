package com.mrblumper.gragine.utilities.drawable;

public class ColoredShape extends EditableShape{
	public ColoredShape(){
		super("gragine_default_simple_color");
		setVitalInformation("uMatrix", "vPosition");
	}
	public ColoredShape(float... color){
		this();
		setColor(color);
	}
	public void setColor(float... color){
		float[] col = new float[4];
		col[0] = color[0];
		col[1] = color[1];
		col[2] = color[2];
		if(color.length < 4){
			col[3] = 1.0f;
		}else{
			col[3] = color[3];
			if(col[3] < 1){
				alpha_activated = true;
			}
		}
		setUniformData("uColor", col);
	}
}
