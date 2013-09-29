package com.mrblumper.gragine.utilities.Shaders.ShaderLinkVar;

import android.opengl.GLES20;

public class ShaderUniformFloat4 extends ShaderUniformLink{
	public boolean send(Object object, int handle){
		float[] obj = (float[])object;
		if(obj.length < 4)
			return false;
		GLES20.glUniform4f(handle, obj[0], obj[1], obj[2], obj[3]);
		return true;
	}
}

