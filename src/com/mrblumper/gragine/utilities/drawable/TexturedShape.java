package com.mrblumper.gragine.utilities.drawable;

import com.mrblumper.gragine.core.Gragine;

public class TexturedShape extends EditableShape {
	public int texture_id = -1;
	public String image_handle_name = "";
	
	public TexturedShape(){
		super("gragine_default_simple_texture");
		setVitalInformation("uMatrix", "vPosition", "uTexture");
		p_setAttributeData("aTexCoordinate", 2);
	}
	public void setTexCoordinate(int where, float... element){
		this.p_setElement("aTexCoordinate", where, element);
	}
	public boolean setTexture(String name){
		int t = Gragine.ImageManager.getImage(name);
		if(t >= 0){
			texture_id = t;
			if(!image_handle_name.equals("")){
				uniform_data.put(image_handle_name, 
						new ImageInformations(texture_id, Gragine.ShadersManager.getShader(shader_name).
								uniform_handles.get(image_handle_name).handle));
				return true;
			}
		}
		return false;
	}
	public void setVitalInformation(String matrix_name, String vertex_name, String image_name){
		super.setVitalInformation(matrix_name, vertex_name);
		image_handle_name = image_name;
	}
}
