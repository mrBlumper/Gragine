package com.mrblumper.gragine.touch;

public class BoxButton extends Button {
	private float width;
	private float height;
	public BoxButton(float x, float y, float width, float height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		recalculateCenter();
	}
	@Override
	public boolean isPointIn(float x, float y){
		if (x >= this.x 
			&& x < this.x + width
			&& y >= this.y
			&& y < this.y + height)
			return true;
		else
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
		this.center_x = this.x + this.width/2;
		this.center_y = this.y + this.height/2;
	}
}
