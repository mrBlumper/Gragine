package com.mrblumper.gragine.utilities.Shaders.ShaderLinkVar;

import android.opengl.GLES20;

public class ShaderUniformMatrix4 extends ShaderUniformLink{
	public boolean send(Object object, int handle){
		float[] obj = (float[])object;
		GLES20.glUniformMatrix4fv(handle, 1, false, obj, 0);
		return true;
	}
}