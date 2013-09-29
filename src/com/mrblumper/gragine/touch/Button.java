package com.mrblumper.gragine.touch;

public abstract class Button {
	public int state = 0;
	public int ptr_id = -1;
	public float center_x;
	public float center_y;
	public float offset_x;
	public float offset_y;
	protected float x;
	protected float y;
	public boolean drag_to_quit = true;	//if you are going out of a button, you are quitting normaling. Otherwise, mouse up is not enable when leaving it
	public boolean drag_to_enter = true;	//enable to enter in a button by dragging
	public boolean only_up_event_to_quit = false;
	public abstract boolean isPointIn(float x, float y);
	public boolean is_draggable = false;
	public void update(float x, float y){}
	public void leave(){}
	protected void recalculateCenter(){}	
}
