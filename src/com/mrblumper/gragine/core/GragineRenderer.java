package com.mrblumper.gragine.core;

import com.mrblumper.gragine.touch.TouchEvents;
import com.mrblumper.gragine.utilities.Shaders.Shader;
import com.mrblumper.gragine.utilities.Shaders.ShaderVariable;
import com.mrblumper.gragine.utilities.Shaders.ShaderLinkVar.ShaderLink;
import com.mrblumper.gragine.utilities.drawable.AttributeData;
import com.mrblumper.gragine.utilities.drawable.Shape;
import java.util.LinkedList;
import java.util.Map.Entry;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

public class GragineRenderer implements GLSurfaceView.Renderer {
	GragineActivity _activity;
	private LinkedList<Shape> _objects_to_draw;
	private ShaderLink _shader_link = new ShaderLink();
	//aaaaaaaaaa
	private float[] projection_view = new float[16];
	private Shape previous_shape = null;
	private Shader current_shader = null;
	
	public GragineRenderer(Context activity){
		_activity = (GragineActivity)activity;	
		_objects_to_draw = new LinkedList<Shape>();
		
	}
	
	public void draw(Shape current){
	 	if(current.alpha_activated != alpha_activated){
	 		if(alpha_activated){
	 			alpha_activated = false;
	 			GLES20.glDisable (GLES20.GL_BLEND);
	 		}else{
	 			alpha_activated = true;
	 			GLES20.glEnable (GLES20.GL_BLEND);
	 		}
	 	}
	 	current.calculateTransformedMatrix(Gragine.projection_view);
	 	//watching if we must change the shader
	 	if(current.changeSomething(previous_shape) == 1){
	     	GLES20.glUseProgram(Gragine.ShadersManager.getShaderId(current.shader_name));
	     	current_shader = Gragine.ShadersManager.getShader(current.shader_name);
	 	}
	 	//sending the attributes to the gpu
	 	for(Entry<String, AttributeData> entry : current.attributes_data.entrySet()){
	 		AttributeData cur_att = entry.getValue();
	 		String cur_key = entry.getKey();
	 		int handle = current_shader.attribute_handles.get(cur_key).handle;
	 		if(cur_att.changed){
	 			cur_att.computeBuffer();
	 		}
	 		GLES20.glEnableVertexAttribArray(handle);
	 		GLES20.glVertexAttribPointer(handle, cur_att.dimension,
	                 GLES20.GL_FLOAT, false,
	                 0, cur_att.data_buffer);
	 	}
	 	//same but for the uniforms
			for(Entry<String, Object> entry : current.uniform_data.entrySet()){
	 		ShaderVariable cur_var = current_shader.uniform_handles.get(entry.getKey());
	 		_shader_link.sendUniform(cur_var.type, 
						entry.getValue(), cur_var.handle);
	 	}
			//drawing
			if(current.indices == null){
	         GLES20.glDrawArrays(current.mode_render, 0, current.getVertexCount());
			}else{
				GLES20.glDrawElements(current.mode_render, current.indices.getNbrElements(), 
						GLES20.GL_UNSIGNED_SHORT, current.indices.data_buffer);
			}
			//disabling the attributes
			for(Entry<String, AttributeData> entry : current.attributes_data.entrySet()){
	 		GLES20.glDisableVertexAttribArray(current_shader.attribute_handles.get(entry.getKey()).handle);
	 	}		
		previous_shape = current;
		//_objects_to_draw.add(current);
		//object.computeBuffers();
	}

	private float _background_color[] = {0.0f, 0.0f, 0.0f};
	public void setBackgroundColor(float r, float g, float b){
		_background_color[0] = r;
		_background_color[1] = g;
		_background_color[2] = b;

        GLES20.glClearColor(_background_color[0], _background_color[1], _background_color[2], 1.0f);
	}
	public void onSurfaceCreated(GL10 unused, EGLConfig config) {
		//GLES20.glEnable (GLES20.GL_BLEND);
		GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        GLES20.glClearColor(_background_color[0], _background_color[1], _background_color[2], 1.0f);
		Gragine.ShadersManager.reLoad();
		Gragine.ImageManager.reLoad();
        Gragine.create(_activity);
        _activity.onEngineCreation();
	}

	protected float _desired_framerate = 60;
	public void setDesiredFramerate(float framerate){
		_desired_framerate = framerate; 
	}
	protected long _previous_time = SystemClock.uptimeMillis();
	private boolean alpha_activated = false;
    public void onDrawFrame(GL10 unused) {//touche events, begin
    	long present_time = SystemClock.uptimeMillis();
		float framerate = 1.0f/(present_time - _previous_time) * 1000;
		_previous_time = present_time;
		double frames = Math.abs(framerate - _desired_framerate);
    	Log.i("framerate", frames+" ");
		
		TouchEvents.beginFrame();
    	Gragine.camReset();
    	//clear screen
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
    	//thanks to this, we update the game
    	_activity.onUpdate();
        //finally, we updates the events
        TouchEvents.refreshEvent();
        
        
    	/*//touche events, begin
    	TouchEvents.beginFrame();
    	
    	Matrix.setIdentityM(Gragine.camera_view, 0);
    	//thanks to this, we update the game
    	_activity.onUpdate();
    	//clear screen
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        
        
        //calculate the projection view
        float[] projection_view = new float[16];
        Matrix.multiplyMM(projection_view, 0, Gragine.projection, 0, Gragine.camera_view, 0);
        Shape previous = null;
        Shader current_shader = null;
        //looping through all the objects to draw
        while(!_objects_to_draw.isEmpty()){
        	Shape current = _objects_to_draw.removeFirst();
        	if(current.alpha_activated != alpha_activated){
        		if(alpha_activated){
        			alpha_activated = false;
        			GLES20.glDisable (GLES20.GL_BLEND);
        		}else{
        			alpha_activated = true;
        			GLES20.glEnable (GLES20.GL_BLEND);
        		}
        	}
        	current.calculateTransformedMatrix(projection_view);
        	//watching if we must change the shader
        	if(current.changeSomething(previous) == 1){
            	GLES20.glUseProgram(Gragine.ShadersManager.getShaderId(current.shader_name));
            	current_shader = Gragine.ShadersManager.getShader(current.shader_name);
        	}
        	//sending the attributes to the gpu
        	for(Entry<String, AttributeData> entry : current.attributes_data.entrySet()){
        		AttributeData cur_att = entry.getValue();
        		String cur_key = entry.getKey();
        		int handle = current_shader.attribute_handles.get(cur_key).handle;
        		if(cur_att.changed){
        			cur_att.computeBuffer();
        		}
        		GLES20.glEnableVertexAttribArray(handle);
        		GLES20.glVertexAttribPointer(handle, cur_att.dimension,
                        GLES20.GL_FLOAT, false,
                        0, cur_att.data_buffer);
        	}
        	//same but for the uniforms
			for(Entry<String, Object> entry : current.uniform_data.entrySet()){
        		ShaderVariable cur_var = current_shader.uniform_handles.get(entry.getKey());
        		_shader_link.sendUniform(cur_var.type, 
						entry.getValue(), cur_var.handle);
        	}
			//drawing
			if(current.indices == null){
	            GLES20.glDrawArrays(current.mode_render, 0, current.getVertexCount());
			}else{
				GLES20.glDrawElements(current.mode_render, current.indices.getNbrElements(), 
						GLES20.GL_UNSIGNED_SHORT, current.indices.data_buffer);
			}
			//disabling the attributes
			for(Entry<String, AttributeData> entry : current.attributes_data.entrySet()){
        		GLES20.glDisableVertexAttribArray(current_shader.attribute_handles.get(entry.getKey()).handle);
        	}
			
			previous = current;
        }
        
        //finally, we updates the events
        TouchEvents.refreshEvent();//*/
    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);        
        Matrix.orthoM(Gragine.projection, 0, 0, Gragine.width_context, Gragine.height_context, 0, 0, 100000);
    }
}