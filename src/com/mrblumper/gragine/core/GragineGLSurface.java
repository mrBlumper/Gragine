package com.mrblumper.gragine.core;

import android.content.Context;
import android.graphics.Rect;
import android.opengl.GLSurfaceView;
import android.view.View;

public class GragineGLSurface extends GLSurfaceView {
	public GragineRenderer renderer;
	public GragineGLSurface(Context activity){
        super(activity);
		renderer = new GragineRenderer(activity);	
		this.setEGLContextClientVersion(2);
		this.setPreserveEGLContextOnPause(true);
		this.setRenderer(renderer);
		
	}
	
	@Override
	public void onPause(){
		this.setVisibility(View.GONE);
		super.onPause();
	}
	@Override 
	public void onWindowFocusChanged(boolean hasFocus){
		super.onWindowFocusChanged(hasFocus);
		if(hasFocus && this.getVisibility() == View.GONE){
			this.setVisibility(View.VISIBLE);
		}
	}
	private Rect outRect = new Rect();
    private int[] location = new int[2];
	public boolean inViewBounds(int x, int y){
        this.getDrawingRect(outRect);
        this.getLocationOnScreen(location);
        outRect.offset(location[0], location[1]);
        return outRect.contains(x, y);
    }
}
