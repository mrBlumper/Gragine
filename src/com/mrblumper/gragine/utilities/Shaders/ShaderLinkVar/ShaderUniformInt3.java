package com.mrblumper.gragine.utilities.Shaders.ShaderLinkVar;

import android.opengl.GLES20;

public class ShaderUniformInt3 extends ShaderUniformLink{
	public boolean send(Object object, int handle){
		int[] obj = (int[])object;
		if(obj.length < 3)
			return false;
		GLES20.glUniform3f(handle, obj[0], obj[1], obj[2]);
		return true;
	}
}
