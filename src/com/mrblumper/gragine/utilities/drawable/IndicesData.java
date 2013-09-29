package com.mrblumper.gragine.utilities.drawable;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import java.util.ArrayList;

public class IndicesData {
	public ArrayList<Short> indices;
	public ShortBuffer data_buffer;
	public boolean changed = false;
	
	
	public IndicesData(){
		indices = new ArrayList<Short>();
	}
	public void clear(){
		changed = false;
		indices.clear();
	}
	public int getNbrElements(){
		return indices.size();
	}
	
	
	public void computeBuffer(){
		short[] shorts = new short[indices.size()];
		for(int i = 0; i < indices.size(); i++)
			shorts[i] = indices.get(i);
		data_buffer = ByteBuffer.allocateDirect(shorts.length * 2)
				.order(ByteOrder.nativeOrder()).asShortBuffer();
		data_buffer.put(shorts).position(0);
		changed = false;
	}
	public void addFace(short... vertex){	
		for(short v : vertex){
			indices.add(v);
		}
		changed = true;
	}
	public void addFaceAtPos(int pos, short... vertex){
		pos = pos * 3;
		for(short v : vertex){
			indices.add(pos, v);
			pos++;
		}
		changed = true;
	}
	public void delFace(int pos){
		for(int i = 0; i < 3; i++){
			indices.remove(pos*3);
		}
		changed = true;
	}
}
