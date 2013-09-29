package com.mrblumper.gragine.core;

import com.mrblumper.gragine.touch.TouchEvents;

import android.os.Bundle;
import android.app.Activity;
import android.app.Application;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

public abstract class GragineActivity extends Activity {
	Application a;
	public GragineGLSurface GLView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        GLView = new GragineGLSurface(this);
        this.setContentView(GLView);         
    }
    
    public void setBackgroundColor(float r, float g, float b){
    	GLView.renderer.setBackgroundColor(r, g, b);
    }
    
    abstract public void onUpdate();
    abstract public void onEngineCreation();
   /* 
    @Override
	public void onPause(){
    	GLView.setVisibility(View.GONE);
		super.onPause();
	}
	@Override 
	public void onWindowFocusChanged(boolean hasFocus){
		super.onWindowFocusChanged(hasFocus);
		if(hasFocus && GLView.getVisibility() == View.GONE){
			GLView.setVisibility(View.VISIBLE);
		}
	}*/
    @Override
    public boolean onTouchEvent(MotionEvent event){  	
    	
    	TouchEvents.update(event);
    	return false;
    }
}
