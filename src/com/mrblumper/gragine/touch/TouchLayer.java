package com.mrblumper.gragine.touch;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.util.Log;
import android.view.MotionEvent;

public class TouchLayer {
	private Map<String, Button> _touch_buttons = new HashMap<String, Button>();
	public String name;
	public boolean in_event_interruption = false;
	public boolean can_update = false;
	private Map<String, Integer> _save_state = new HashMap<String, Integer>();
	
	public TouchLayer(String name){
		this.name = name;
	}
	public void refreshEvent(){
		/*if(can_update)
			return;*/

		/*can_update = false;
		for(Entry<String, Integer> entry : _save_state.entrySet()){
    		int state = entry.getValue();
    		Button current = _touch_buttons.get(entry.getKey());
    		if(state == TouchEvents.UP){
    			Log.i("button", "clear "+entry.getKey() + " UP");
    			current.state = TouchEvents.NOT;
    		}else if(state == TouchEvents.DOWN){
    			current.state = TouchEvents.MOVE;
    		}
		}*/
		
		for(Entry<String, Button> entry : _touch_buttons.entrySet()){
    		Button current = entry.getValue();
    		if(current.state == TouchEvents.UP){
    			Log.i("button", "clear "+entry.getKey() + " UP");
    			current.leave();
    			current.state = TouchEvents.NOT;
    		}else if(current.state == TouchEvents.DOWN){
    			current.state = TouchEvents.MOVE;
    		}
		}
	}
	
	public void beginFrame(){
		/*if(!in_event_interruption)
			can_update = true;
		_save_state.clear();
		for(Entry<String, Button> entry : _touch_buttons.entrySet()){
			_save_state.put(entry.getKey(), entry.getValue().state);
		}*/
	}
	
	public void update(float x, float y, int action, boolean inView, int ptr_id){    
		in_event_interruption = true;
		can_update = false;
		if(!inView){
			action = MotionEvent.ACTION_POINTER_UP;
		}
		for(Entry<String, Button> entry : _touch_buttons.entrySet()){
			Button current = entry.getValue();

			if(ptr_id == current.ptr_id)
				current.update(x, y);
			if(current.isPointIn(x,  y)){
				if(ptr_id == current.ptr_id){
					
					if(action == MotionEvent.ACTION_UP
						|| action == MotionEvent.ACTION_POINTER_UP){
		    			Log.i("button", "push "+entry.getKey() + " UP");
						current.state = TouchEvents.UP;
						current.ptr_id = -1;
					}
				}else{
					if(action == MotionEvent.ACTION_DOWN
						|| action == MotionEvent.ACTION_POINTER_DOWN
						|| (action == MotionEvent.ACTION_MOVE
								&& current.drag_to_enter)){
						current.state = TouchEvents.DOWN;	
						current.ptr_id = ptr_id;
					}
				}
			}else{
				if(ptr_id == current.ptr_id){	
					if(!current.only_up_event_to_quit){
						if(current.drag_to_quit){
							current.state = TouchEvents.UP;
							current.ptr_id = -1;
						}else{
							current.state = TouchEvents.NOT;
							current.ptr_id = -1;
						}
					}else{
						if(action == MotionEvent.ACTION_UP
								|| action == MotionEvent.ACTION_POINTER_UP){
				    			Log.i("button", "push "+entry.getKey() + " UP");
								current.state = TouchEvents.UP;
								current.ptr_id = -1;
							}
					}
				}
			}
		}
		in_event_interruption = false;
	}
	
	public void addButton(String key, Button button){
		_touch_buttons.put(key, button);
	}
	public void delButton(String key){
		if(_touch_buttons.containsKey(key)){
			_touch_buttons.remove(key);
		}
	}	
	public int getStateButton(String name){
		if(_touch_buttons.containsKey(name)){
			return _touch_buttons.get(name).state;
		}
		return -1;
	}
}
