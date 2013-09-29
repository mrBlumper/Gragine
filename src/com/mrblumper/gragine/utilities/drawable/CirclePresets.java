package com.mrblumper.gragine.utilities.drawable;


public class CirclePresets extends ShapePresets {
	private float radius;
	private float nbr_step;
	public CirclePresets(float radius, float nbr_step){
		this.radius = radius;
		if(nbr_step > 360)
			nbr_step = 360;
		this.nbr_step = nbr_step;
		
	}
	
	public void configure(Shape object){
		float step = 360 / nbr_step;
		object.p_addVertex(0, 0);
		for(int angle = 0; angle < 360; angle += step){
			float radians = angle * 3.14f / 180.0f - 3.14f/nbr_step + 3.14f/2.0f;
			object.p_addVertex((float)Math.cos(radians) * radius, (float)Math.sin(radians) * radius);
		}
		int size = object.attributes_data.get(object.vertex_handle_name).getNbrElements();
		for(int i = 2; i < size; i++){
			object.p_addFaceIndices(0, (i-1), (i));
		}
		object.p_addFaceIndices(0, 1, size-1);
	}
}
