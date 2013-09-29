package com.mrblumper.gragine.touch;

import java.util.ArrayDeque;
import java.util.LinkedList;

import com.mrblumper.gragine.core.Gragine;
import android.view.MotionEvent;

public class TouchEvents {
	public static final int UP = 3;
	public static final int MOVE = 2;
	public static final int DOWN = 1;
	public static final int NOT = 0;
	public static TouchLayer touch_layer = new TouchLayer("default");
	public static ArrayDeque <MotionEvent> _wait_events = new ArrayDeque <MotionEvent>();
	
	
	protected TouchEvents(){}
	
	public static void refreshEvent(){
		touch_layer.refreshEvent();
	}
	
	public static void beginFrame(){
		if(!_wait_events.isEmpty()){
			action(_wait_events.removeFirst());
		}
		touch_layer.beginFrame();
	}
	
	
	public static void setTouchLayer(TouchLayer layer){
		touch_layer = layer;
	}
	
	public static void action(MotionEvent event){
		int pointerCount = event.getPointerCount();    	
    	for (int i = 0; i < pointerCount; i++) 	{
    		float x = event.getX(i);
    		float y = event.getY(i);
        	touch_layer.update(x * Gragine.ratio_width,
						  	y * Gragine.ratio_height, 
						  	event.getActionMasked(),
						  	Gragine.activity.GLView.inViewBounds((int)x, (int)y),
						  	event.getPointerId(i));
    		
    		
    	}
	}
	
	public static void update(MotionEvent event){
_wait_events.add(event);
		/*int pointerCount = event.getPointerCount();    	
    	for (int i = 0; i < pointerCount; i++) 	{
    		float x = event.getX(i);
    		float y = event.getY(i);
        	touch_layer.update(x * Gragine.ratio_width,
						  	y * Gragine.ratio_height, 
						  	event.getActionMasked(),
						  	Gragine.activity.GLView.inViewBounds((int)x, (int)y),
						  	event.getPointerId(i));
    		
    		
    	}*/
	}
}
