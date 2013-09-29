package com.mrblumper.gragine.touch;

public class Joystick extends CircleButton {
	private float _fixed_center_x;
	private float _fixed_center_y;
	private float _max_radius;
	private float _x_percent;
	private float _y_percent;
	public float getX(){
		return _x_percent;
	}
	public float getY(){
		return _y_percent;
	}
	public Joystick(float x, float y, float radius_button, float max_radius){
		super(x, y, radius_button);
		_fixed_center_x = x;
		_fixed_center_y = y;
		_max_radius = max_radius;
		this.is_draggable = true;
		this.only_up_event_to_quit = true;
	}
	public void leave(){
		this.x = _fixed_center_x;
		this.y = _fixed_center_y;
		recalculateCenter();
	}
	public void update(float x, float y){
		if(!is_draggable)
			return;
		float t_offset_x = _fixed_center_x - center_x;
		float t_offset_y = _fixed_center_y - center_y;
		//new pos
		float t_x = this.x + (x - center_x);
		float t_y = this.y + (y - center_y); 
		//vector  /200 because if in range, it's between -1 and 1
		float v_x = (t_x - _fixed_center_x) / _max_radius;
		float v_y = (t_y - _fixed_center_y) / _max_radius;
		//length of it
		float length = (float)Math.sqrt(v_x * v_x + v_y * v_y);
		if(Math.abs(length) > 1){
			v_x /= length;
			v_y /= length;
		}
		_x_percent = v_x;
		_y_percent = v_y;
		this.x = this._fixed_center_x + v_x * _max_radius;
		this.y = this._fixed_center_y + v_y * _max_radius;
		recalculateCenter();	
	}
}
