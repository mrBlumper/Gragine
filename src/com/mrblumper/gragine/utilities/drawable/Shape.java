package com.mrblumper.gragine.utilities.drawable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.mrblumper.gragine.core.Gragine;

public class Shape {
	public String shader_name;
	public Map<String, AttributeData> 	attributes_data;
	public Map<String, Object>			uniform_data;
	public float[] matrix = new float[16];
	public String vertex_handle_name;
	public String matrix_handle_name;
	public float[] world_data = new float[5];	//contain pos (0 and 1), scale (2 and 3) and rotation(4)
	public float[] center = new float[2];
	public int mode_render = GLES20.GL_TRIANGLES;
	public IndicesData indices = null;
	public boolean alpha_activated = false;
	
	public void useShapePreset(ShapePresets preset){
		for(Entry<String, AttributeData> entry : attributes_data.entrySet()) {
		    AttributeData data = entry.getValue();
		    if(data != null){
		    	data.clear();
		    }
		}
		if(indices != null)
			indices.clear();
		preset.configure(this);
	}
	protected void p_useIndices(){
		indices = new IndicesData();
	}
	protected void p_disableIndices(){
		indices = null;
	}
	protected void p_addFaceIndices(int n1, int n2, int n3){
		if(indices != null){
			indices.addFace((short) n1, (short) n2, (short)n3);
			indices.computeBuffer();
		}
	}

	protected void p_addVertex(float x, float y){
		attributes_data.get(vertex_handle_name).addVertex(x, y);
		//resetCenterPoint();
	}
	protected void p_addElement(String name, float... element){
		attributes_data.get(name).addVertex(element);
	}
	protected void p_setElement(String name, int where, float... element){
		attributes_data.get(name).addVertexAtPos(where, element);
	}
	protected void p_setAttributeData(String name,int size){
		if(attributes_data.containsKey(name)){
			attributes_data.put(name, new AttributeData(size));
			float[] buffer = new float[size];
			for(int i = 0; i < size; i++){
				buffer[i] = 0.0f;
			}
			AttributeData copy = attributes_data.get(vertex_handle_name);
			int size_max = copy.vertices.size();
			for(int i = 0; i < size_max; i++){
				attributes_data.get(name).addVertex(buffer);
			}
		}
	}
	
	public void calculateMatrix(){
		float[] temp = new float[16];
		Matrix.setIdentityM(temp, 0);
		Matrix.translateM(temp, 0, world_data[0], world_data[1], 0);
		Matrix.rotateM(temp, 0, world_data[4], 0, 0, 1);
		Matrix.translateM(temp, 0, -center[0], -center[1], 0);
		Matrix.scaleM(temp, 0, world_data[2], world_data[3], 0);
		matrix = temp;
	}
	public void calculateTransformedMatrix(float[] projection_view){
		float[] temp_matrix = new float[16];
		Matrix.multiplyMM(temp_matrix, 0, projection_view, 0, matrix, 0);
		setUniformData(matrix_handle_name, temp_matrix);
	}
	
	
	public void setCenterPoint(float x, float y){
		center[0] = x;
		center[1] = y;
		calculateMatrix();
	}
	public void resetCenterPoint(){
		center = attributes_data.get(vertex_handle_name).getCenterPoint();
		calculateMatrix();
	}
	
	public void setPos(float x, float y){
		world_data[0] = x;
		world_data[1] = y;	
		calculateMatrix();
	}
	public void setScale(float x, float y){
		world_data[2] = x;
		world_data[3] = y;	
		calculateMatrix();
	}
	public void setRot(float angle){
		world_data[4] = angle;	
		calculateMatrix();
	}
	public void translate(float x, float y){
		world_data[0] += x;
		world_data[1] += y;		
		calculateMatrix();
	}
	public void rotate(float angle){
		world_data[4] += angle;
		if(world_data[4] >= 360)
			world_data[4] %= 360;
		else if(world_data[4] <= 360){
			world_data[4] = -((-world_data[4])%360);
		}
		calculateMatrix();
	}
	public void scale(float x, float y){
		world_data[2] *= x;
		world_data[3] *= y;
		calculateMatrix();
	}
	
	public void setPosInfo(float[] infos){
		for(int i = 0; i < world_data.length; i++){
			world_data[i] = infos[i];
		}
		calculateMatrix();
	}
	
	public Shape(){
		attributes_data = new HashMap<String, AttributeData>();
		uniform_data = new HashMap<String, Object>();
		Matrix.setIdentityM(matrix, 0);
		world_data[0] = 0;
		world_data[1] = 0;
		world_data[2] = 1;
		world_data[3] = 1;
		world_data[4] = 0;
		this.setCenterPoint(0, 0);
		this.p_useIndices();
	}
	public Shape(String name){
		this();
		setShader(name);		
	}
	public int getVertexCount(){
		return attributes_data.get(vertex_handle_name).getNbrElements();
	}
	public void computeBuffers(){
		Set<Entry<String, AttributeData>> set = attributes_data.entrySet();
		for(Entry<String, AttributeData> key: set){
			AttributeData var = key.getValue();
			if(var.changed){
				var.computeBuffer();
			}
		}
	}
	public void setShader(String name){
		shader_name = name;
		
		String[] attributes = Gragine.ShadersManager.getAttributesShader(name);
		for(String key : attributes){
			attributes_data.put(key, null);
		}
		String[] uniforms = Gragine.ShadersManager.getUniformsShader(name);
		for(String key : uniforms){
			uniform_data.put(key, null);
		}
	}
	public void setUniformData(String name, Object object){
		if(uniform_data.containsKey(name)){
			uniform_data.put(name, object);
		}
	}
	
	public void setVitalInformation(String matrix_name, String vertex_name){
		setUniformData(matrix_name, matrix);
		if(attributes_data.containsKey(vertex_name)){
			attributes_data.put(vertex_name, new AttributeData(2));
		}
		vertex_handle_name = vertex_name;
		matrix_handle_name = matrix_name;
	}
	
	//if return 1, only shader; if return 2, image
	public int changeSomething(Shape previous){
		if(previous == null){
			return 1;
		}
		if(previous.shader_name != shader_name){
			return 1;
		}
		return 0;
	}
}
