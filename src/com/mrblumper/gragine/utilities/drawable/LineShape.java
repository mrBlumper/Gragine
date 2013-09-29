package com.mrblumper.gragine.utilities.drawable;

import android.opengl.GLES20;

public class LineShape extends ColoredShape {
	public LineShape(float... color){
		super(color);
		unLoop();
		this.disableIndices();
	}
	public void loop(){
		this.mode_render = GLES20.GL_LINE_LOOP;
	}
	public void unLoop(){
		this.mode_render = GLES20.GL_LINE_STRIP;
	}
}
