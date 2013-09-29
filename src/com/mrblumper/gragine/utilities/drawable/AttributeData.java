package com.mrblumper.gragine.utilities.drawable;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

public class AttributeData {
	public ArrayList<Float> vertices;
	public FloatBuffer data_buffer;
	public boolean changed = false;
	
	private float[] center_calculate;	
	public int dimension;
	
	public AttributeData(int dim){
		dimension = dim;
		vertices = new ArrayList<Float>();
		center_calculate = new float[dimension];
	}
	
	public void clear(){
		changed = false;
		vertices.clear();
		//data_buffer.clear();
		for(int i = 0; i < dimension; i++){
			center_calculate[i] = 0;
		}
	}
	public int getNbrElements(){
		return vertices.size() / dimension;
	}
	
	public float[] getCenterPoint(){
		float[] center = new float[dimension];
		int nbr = getNbrElements();
		for(int i = 0; i < dimension; i++){
			center[i] = center_calculate[i] / (float)nbr;
		}
		return center;
	}
	
	public void computeBuffer(){
		float[] floats = new float[vertices.size()];
		for(int i = 0; i < vertices.size(); i++)
			floats[i] = vertices.get(i);
		data_buffer = ByteBuffer.allocateDirect(floats.length * 4)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();							
		data_buffer.put(floats).position(0);	
		changed = false;
	}
	public void setDimension(int d){
		dimension = d;
	}
	
	public void addVertex(float... vertex){
		if(vertex.length != dimension)
			return;
		int i = 0;
		for(float v : vertex){
			vertices.add(v);
			center_calculate[i] += v;
			i++;
		}
		changed = true;
	}
	public void addVertexAtPos(int pos, float... vertex){
		pos = pos * dimension;
		if(vertex.length != dimension)
			return;
		int i = 0;
		for(float v : vertex){
			vertices.add(pos, v);
			center_calculate[i] += v;
			pos++;
			i++;
		}
		changed = true;
	}
	public void delVertex(int pos){
		for(int i = 0; i < dimension; i++){
			center_calculate[i] -= vertices.get(i);
			vertices.remove(pos*dimension);
		}
		changed = true;
	}
}
