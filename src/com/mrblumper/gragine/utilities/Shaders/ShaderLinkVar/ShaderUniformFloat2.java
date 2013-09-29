package com.mrblumper.gragine.utilities.Shaders.ShaderLinkVar;

import android.opengl.GLES20;

public class ShaderUniformFloat2 extends ShaderUniformLink{
	public boolean send(Object object, int handle){
		float[] obj = (float[])object;
		if(obj.length < 2)
			return false;
		GLES20.glUniform2f(handle, obj[0], obj[1]);
		return true;
	}
}
