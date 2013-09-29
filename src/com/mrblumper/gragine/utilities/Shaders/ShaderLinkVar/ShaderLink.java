package com.mrblumper.gragine.utilities.Shaders.ShaderLinkVar;

import java.util.Map;
import java.util.HashMap;

public class ShaderLink {
	private Map<String, ShaderUniformLink> _uniforms_link;
	public ShaderLink(){
		_uniforms_link = new HashMap<String, ShaderUniformLink>();
		_uniforms_link.put("int", new ShaderUniformInt());
		_uniforms_link.put("ivec2", new ShaderUniformInt2());
		_uniforms_link.put("ivec3", new ShaderUniformInt3());
		_uniforms_link.put("ivec4", new ShaderUniformInt4());
		_uniforms_link.put("float", new ShaderUniformFloat());
		_uniforms_link.put("vec2", new ShaderUniformFloat2());
		_uniforms_link.put("vec3", new ShaderUniformFloat3());
		_uniforms_link.put("vec4", new ShaderUniformFloat4());
		_uniforms_link.put("mat2", new ShaderUniformMatrix2());
		_uniforms_link.put("mat3", new ShaderUniformMatrix3());
		_uniforms_link.put("mat4", new ShaderUniformMatrix4());
		_uniforms_link.put("sampler2D", new ShaderUniformSampler2D());
	}
	public boolean sendUniform(String type, Object  object, int handle){
		if(!_uniforms_link.containsKey(type))
			return false;
		
		return _uniforms_link.get(type).send(object, handle);
	}
}
