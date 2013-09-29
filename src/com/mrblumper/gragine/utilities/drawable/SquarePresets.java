package com.mrblumper.gragine.utilities.drawable;


public class SquarePresets extends ShapePresets {
	private float _width;
	private float _height;
	public SquarePresets(float w, float h){
		_width = w;
		_height = h;
	}
	
	public void configure(Shape object){
		object.p_addVertex(0, 0);
		object.p_addVertex(_width, 0);
		object.p_addVertex(_width, _height);
		object.p_addVertex(0, _height);
		object.p_addFaceIndices(0,  1, 2);
		object.p_addFaceIndices(0, 2, 3);
	}
}
