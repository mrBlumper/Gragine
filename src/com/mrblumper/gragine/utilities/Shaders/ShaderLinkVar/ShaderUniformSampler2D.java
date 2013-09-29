package com.mrblumper.gragine.utilities.Shaders.ShaderLinkVar;

import com.mrblumper.gragine.core.Gragine;
import com.mrblumper.gragine.utilities.drawable.ImageInformations;

import android.opengl.GLES20;

public class ShaderUniformSampler2D extends ShaderUniformLink{
	public boolean send(Object object, int handle){
		ImageInformations obj = (ImageInformations)object;
       	
		GLES20.glActiveTexture(Gragine.convertTextureUnitToGLUnit(obj.unit));
		if(Gragine.texture_units[obj.unit] != obj.id){
		    GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, obj.id);
		    Gragine.texture_units[obj.unit]= obj.id; 
		}
	    GLES20.glUniform1i(obj.handle, obj.unit);
		return true;
	} 
}
