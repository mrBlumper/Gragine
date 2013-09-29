package com.mrblumper.gragine.utilities;

import com.mrblumper.gragine.core.Gragine;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

public class Image {
	public int id;
	public Bitmap bitmap;
	public Image(final int ressource){
		loadBitmap(ressource);
		loadInOpengl();
	}
	public void loadBitmap(final int ressource){	
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	 	options.inScaled = false;
	 	this.bitmap = BitmapFactory.decodeResource(Gragine.activity.getResources(), ressource, options);	
	}
	public void loadInOpengl(){
		final int[] textureHandle = new int[1];	
		GLES20.glGenTextures(1, textureHandle, 0);	
	    if (textureHandle[0] != 0){	  
	        // Bind to the texture in OpenGL 
	        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);		 
	        // Set filtering, can change
	        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
	        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);		 
	        // Load the bitmap into the bound texture.
	        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);		 
	        this.id = textureHandle[0];
	    }else{
	    	throw new RuntimeException("Error loading texture.");
	    }
	}
}
