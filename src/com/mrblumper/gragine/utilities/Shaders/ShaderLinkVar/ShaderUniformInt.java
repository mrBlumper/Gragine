package com.mrblumper.gragine.utilities.Shaders.ShaderLinkVar;

import android.opengl.GLES20;

public class ShaderUniformInt extends ShaderUniformLink{
	public boolean send(Object object, int handle){
		GLES20.glUniform1f(handle, (Integer)object);
		return true;
	}
}
