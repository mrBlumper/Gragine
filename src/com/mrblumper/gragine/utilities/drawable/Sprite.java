package com.mrblumper.gragine.utilities.drawable;

import com.mrblumper.gragine.core.Gragine;

public class Sprite extends Shape{
	public int texture_id = -1;
	public Sprite(int width, int height){
		super("gragine_default_simple_texture");
		setVitalInformation("uMatrix", "vPosition");
		useShapePreset(new SquarePresets(width, height));
		p_setAttributeData("aTexCoordinate", 2);
		setSubRect(0, 0, 1, 1);
	}
	public Sprite(int width, int height, String image){
		this(width, height, image, true);
	}
	public Sprite(int width, int height, String image, boolean alpha){
		this(width, height);
		setTexture(image);
		this.alpha_activated = alpha;
	}
	public void setSubRect(float top, float left, float bottom, float right){
		p_setElement("aTexCoordinate", 0, left, top);
		p_setElement("aTexCoordinate", 1, right, top);
		p_setElement("aTexCoordinate", 2, right, bottom);
		p_setElement("aTexCoordinate", 3, 0, bottom);
	}
	public boolean setTexture(String name){
		int t = Gragine.ImageManager.getImage(name);
		if(t >= 0){
			texture_id = t;
			uniform_data.put("uTexture", 
				new ImageInformations(texture_id, Gragine.ShadersManager.getShader(shader_name).
						uniform_handles.get("uTexture").handle));
			return true;			
		}
		return false;
	}
}
