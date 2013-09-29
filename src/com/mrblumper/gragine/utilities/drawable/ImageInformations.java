package com.mrblumper.gragine.utilities.drawable;

public class ImageInformations {
	public int id;
	public int handle;
	public int unit;
	public ImageInformations(int id, int handle, int unit){
		this.id = id;
		this.handle = handle;
		this.unit = unit;
	}
	public ImageInformations(int id, int handle){
		this(id, handle, 0);
	}
}
