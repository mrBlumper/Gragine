package com.mrblumper.gragine.utilities.drawable;

import java.util.ArrayList;

import com.mrblumper.gragine.maths.Vector2;

import android.util.Log;

public class ObjectAnimation {
	protected int _frame = 0;
	protected ArrayList<float[]> _moves = new ArrayList<float[]>();
	protected ArrayList<Integer> _offset_times = new ArrayList<Integer>();
	protected ArrayList<float[]> _interpolate = new ArrayList<float[]>();
	//contain pos (0 and 1), scale (2 and 3) and rotation(4)
	public boolean loop = false;
	protected int _end_time = 0;
	protected int _last = 0;
	protected int stack_time = 0;
	
	public ObjectAnimation clone(){
		ObjectAnimation ret = new ObjectAnimation(loop);
		ret._moves = (ArrayList<float[]>) _moves.clone();
		ret._offset_times = (ArrayList<Integer>) _offset_times.clone();
		ret._interpolate = (ArrayList<float[]>) _interpolate.clone();
		ret._end_time = _end_time;
		return ret;
	}
	
	public ObjectAnimation(boolean loop){
		this.loop = loop;
	}
	
	public boolean update(Shape shape, Vector2 base_scale){
		if(_frame == _end_time){
			if(loop){
				_frame = 0;
				_last = 0;
			}else{
				return true;
			}
		}
		//Log.i("animation", _frame + " " + _offset_times.get(_last) + " " + _last);
		if(_frame >= _offset_times.get(_last)){
			_last++;
		}
		shape.translate(_interpolate.get(_last)[0], _interpolate.get(_last)[1]);
		shape.scale(_interpolate.get(_last)[2]/base_scale.x, _interpolate.get(_last)[3]);
		shape.rotate(_interpolate.get(_last)[4]);
		/*int time = _offset_times.get(_last) - ((_last == 0)?0:_offset_times.get(_last-1));
		shape.translate(_moves.get(_last)[0]/time, _moves.get(_last)[1]/time);
		float size_x = 1;
		float temp_size_x = _moves.get(_last)[2];
		if(temp_size_x < 1){
			size_x = 1-(1 - temp_size_x)/time;
		}else if(temp_size_x > 1){
			size_x = 1+(temp_size_x - 1)/time;
		}
		float size_y = 1;
		float temp_size_y = _moves.get(_last)[2];
		if(temp_size_y < 1){
			size_y = 1-(1 - temp_size_y)/time;
		}else if(temp_size_y > 1){
			size_y = 1+(temp_size_y - 1)/time;
		}
		shape.scale(size_x, size_y);
		shape.rotate(_moves.get(_last)[4]/time);*/
		
		_frame++;
		return false;
	}
	
	public void addStep(int period, float x, float y, float rot, float size_x, float size_y){
		if(period == 0)
			return;
		int ecc = period;
		if(_offset_times.size() != 0)
			ecc += _offset_times.get(_offset_times.size()-1);
		_offset_times.add(ecc);
		float[] moves = new float[5];
		moves[0] = x;
		moves[1] = y;
		moves[2] = size_x;
		moves[3] = size_y;
		moves[4] = rot;
		_moves.add(moves);
		
		float[] interpolations = new float[5];
		interpolations[0] = x/period;
		interpolations[1] = y/period;
		interpolations[4] = rot/period;
		

		interpolations[2] = (size_x - 1)/period;
		interpolations[3] = (size_y - 1)/period;
		Log.i("animation", size_x + "  " + size_y + " | " + interpolations[2] + " " + interpolations[3]);
		/*if(size_x < 1){
			interpolations[2] = (1 - size_x)/period;
			//interpolations[2] = 1-(1 - size_x)/period;
		}else{
			interpolations[2] = 2*(size_x - 1)/period;
		}
		if(size_y < 1){
			interpolations[3] = (1 - size_y)/period;
		}else{
			interpolations[3] = 2*(size_y - 1)/period;
		}*/
		/*if(size_x < 1){
			interpolations[2] = 1-(1 - size_x)/period;
		}else{
			interpolations[2] = 1+(size_x - 1)/period;
		}
		if(size_y < 1){
			interpolations[3] = 1-(1 - size_y)/period;
		}else{
			interpolations[3] = 1+(size_y - 1)/period;
		}*/
		_interpolate.add(interpolations);
		
		_end_time += period;
		//Log.i("animation", _end_time+" "+_offset_times.get(_offset_times.size()-1));
	}
}
