package com.mrblumper.gragine.utilities.drawable;

import android.util.Log;

public class AnimationWrapper {
	protected ObjectAnimation _animation;
	protected int _frame = 0;
	protected int _last = 0;
	protected int stack_time = 0;
	protected float _last_size_x = 1;
	protected float _last_size_y = 1;
	public AnimationWrapper(ObjectAnimation animation){
		_animation = animation;
	}
	public boolean update(Shape shape){
		if(_frame == _animation._end_time){
			if(_animation.loop){
				_frame = 0;
				_last = 0;
				_last_size_x = 1;
				_last_size_y = 1;
			}else{
				return true;
			}
		}
		//Log.i("animation", _frame + " " + _animation._offset_times.get(_last) + " " + _last);
		if(_frame >= _animation._offset_times.get(_last)){
			_last++;
			_last_size_x = 1;
			_last_size_y = 1;
		}
		float time = _frame - ((_last == 0)?0:_animation._offset_times.get(_last-1));
		//if(time < 0) time = 0;
		shape.translate(_animation._interpolate.get(_last)[0], 
				_animation._interpolate.get(_last)[1]);
		float scale_factor_x = 1 + time * _animation._interpolate.get(_last)[2];
		float scale_factor_y = 1 + time * _animation._interpolate.get(_last)[3];
		shape.scale((1 + time * _animation._interpolate.get(_last)[2]) / (1 + (time-1) * _animation._interpolate.get(_last)[2]), 
				(1 + time * _animation._interpolate.get(_last)[3]) / (1 + (time-1) * _animation._interpolate.get(_last)[3]));
		Log.i("animation", scale_factor_x / _last_size_x +" "+scale_factor_y / _last_size_y +" | "+_last_size_x + " " + _last_size_y);

		/*_last_size_x *= scale_factor_x / _last_size_x;
		_last_size_y *= scale_factor_y / _last_size_y;*/
		_last_size_x += _animation._interpolate.get(_last)[2];
		_last_size_y += _animation._interpolate.get(_last)[3];
		//Log.i("animation", _last_size_x + " " + _last_size_y);
		shape.rotate(_animation._interpolate.get(_last)[4]);
		_frame++;
		return false;
	}
}
