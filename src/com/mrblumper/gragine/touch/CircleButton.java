package com.mrblumper.gragine.touch;

public class CircleButton extends Button {
	private float radius;
	public CircleButton(float x, float y, float radius){
		this.x = x;
		this.y = y;
		this.radius = radius;
		recalculateCenter();		
	}
	@Override
	public boolean isPointIn(float x, float y){
		float tx = this.x - x;
		float ty = this.y - y;
		if(tx * tx + ty * ty <= this.radius * this.radius){
			return true;
		}
		return false;
	}
	@Override
	public void update(float x, float y){
		if(!is_draggable)
			return;
		offset_x = x - center_x;
		offset_y = y - center_y;
		this.x += offset_x;
		this.y += offset_y;
		recalculateCenter();		
	}
	
	protected void recalculateCenter(){
		this.center_x = this.x;
		this.center_y = this.y;
	}
}
