package com.mrblumper.gragine.utilities.drawable;

public class EditableShape extends Shape {
	public EditableShape(String name){
		super(name);
	}
	public void useIndices(){
		this.p_useIndices();
	}
	public void disableIndices(){
		this.p_disableIndices();
	}
	public void addFaceIndices(int n1, int n2, int n3){
		this.p_addFaceIndices(n1,  n2,  n3);
	}

	public void addVertex(float x, float y){
		this.p_addVertex(x, y);
	}
	public void addElement(String name, float... element){
		this.p_addElement(name, element);
	}
	public void setElement(String name, int where, float... element){
		this.p_setElement(name, where, element);
	}
	public void setAttributeData(String name,int size){
		this.p_setAttributeData(name, size);
	}
}
