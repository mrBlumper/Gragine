package com.mrblumper.gragine.utilities.Shaders;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import android.opengl.GLES20;
import android.util.Log;

public class Shader {
	public int program_id;
	public Map<String, ShaderVariable> uniform_handles;
	public Map<String, ShaderVariable> attribute_handles;
	public String vertex_source;
	public String fragment_source;
	
	public Shader(){
		uniform_handles = new HashMap<String, ShaderVariable>();
		attribute_handles = new HashMap<String, ShaderVariable>();
	}
	
	public Shader(String vertex, String fragment){
		this();
		createShader(vertex, fragment);
	}
	public Shader(InputStream vertex, InputStream fragment){
		this();
		createShader(vertex, fragment);
	}
	public void recreate(){
		createShader(vertex_source, fragment_source);
	}

	public void createShader(InputStream vertex, InputStream fragment){
		createShader(loadShaderFromFile(vertex), loadShaderFromFile(fragment));
	}
	
	public void createShader(String vertex_code, String fragment_code){
		vertex_source = vertex_code;
		fragment_source = fragment_code;
		uniform_handles.clear();
		attribute_handles.clear();
		vertex_code = parseStringLine(vertex_code);
		fragment_code = parseStringLine(fragment_code);
		int vertex_handle = compileShader(GLES20.GL_VERTEX_SHADER, vertex_code);
		int fragment_handle = compileShader(GLES20.GL_FRAGMENT_SHADER, fragment_code);
		detectVariableFromSource(vertex_code);
		detectVariableFromSource(fragment_code);
		program_id = createAndLinkProgram(vertex_handle, fragment_handle, getListOfKeyFromMap(attribute_handles));
		setHandles();
	}	

	private void setHandles(){
		Set<Entry<String, ShaderVariable>> set = attribute_handles.entrySet();
		for(Entry<String, ShaderVariable> key: set){
			ShaderVariable var = key.getValue();
			var.handle = GLES20.glGetAttribLocation(program_id, key.getKey());
			key.setValue(var);
		}
		set = uniform_handles.entrySet();
		for(Entry<String, ShaderVariable> key: set){
			ShaderVariable var = key.getValue();
			var.handle = GLES20.glGetUniformLocation(program_id, key.getKey());
			key.setValue(var);
		}
	}
	public String[] getListOfKeyFromMap(Map<String, ShaderVariable> map){
		String[] ret = new String[map.size()];
		Set<String> keys_set = map.keySet();
		int index = 0;
		for(String key : keys_set){
			ret[index] = key;
			index++;
		}
		return ret;
	}
	
	

	/**
	 * Load the code of a shader
	 * @param file the inputstream of the shader to load
	 * @return String, the code of the shader
	 */
	private String loadShaderFromFile(InputStream file){
		Scanner scanner = new Scanner(file);
		String code = "";
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();		
			code += line + " ";
		}
		return code;
	}
	
	private String parseStringLine(String line){
		for(int i = line.length()-1; i >= 0; i--){
		    if(line.substring(i, i+1).equalsIgnoreCase(";")){
				line = line.substring(0, i) + " " + line.substring(i, i+1) + " " + line.substring(i+1);
			}else if(  line.substring(i, i+1).equalsIgnoreCase("\t")
					|| line.substring(i, i+1).equalsIgnoreCase("\n")){
				line = line.substring(0, i) + " " + line.substring(i+1);
			}
		}
		return line;
	}
	
	/**
	 * Must be called after shader's compilation to detect errors.	 * 
	 * Method detecting automatically the attributes and the uniforms variables of the shader	
	 * 
	 * @param source String representing the shader's code
	 */
	private void detectVariableFromSource(String source){
		String[] tokens = source.split(" ");
		String previous_type = "none";
		String type = "";
		boolean skip_token = false;		
		for(String token : tokens){
			if(skip_token){
				skip_token = false;
				type = token;
			}else{
				if(!previous_type.equalsIgnoreCase("none")){
					if(previous_type.equalsIgnoreCase("uniform")){						
						uniform_handles.put(token, new ShaderVariable(type));
					}else if(previous_type.equalsIgnoreCase("attribute")){
						attribute_handles.put(token, new ShaderVariable(type));
					}
					previous_type = "none";
				}
			}
			if(token.equalsIgnoreCase("uniform") || token.equalsIgnoreCase("attribute")){
				previous_type = token.toLowerCase(Locale.getDefault());
				skip_token = true;
			}
		}
	}
	
	private int compileShader(final int shaderType, final String shaderSource){
		int shaderHandle = GLES20.glCreateShader(shaderType);
		
	    if (shaderHandle != 0) {			
			// Pass in the shader source.
			GLES20.glShaderSource(shaderHandle, shaderSource);
			// Compile the shader.
			GLES20.glCompileShader(shaderHandle);
			// Get the compilation status.
			final int[] compileStatus = new int[1];
			GLES20.glGetShaderiv(shaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
			// If the compilation failed, delete the shader.
			if (compileStatus[0] == 0){
				Log.e("COMPILATION ERROR", "Error compiling shader: " + GLES20.glGetShaderInfoLog(shaderHandle));
				GLES20.glDeleteShader(shaderHandle);
				shaderHandle = 0;
			}
		}else{			
			throw new RuntimeException("Error creating shader.");
		}
		return shaderHandle;
	}	

	/**
	 * Helper function to compile and link a program.
	 * 
	 * @param vertexShaderHandle An OpenGL handle to an already-compiled vertex shader.
	 * @param fragmentShaderHandle An OpenGL handle to an already-compiled fragment shader.
	 * @param attributes Attributes that need to be bound to the program.
	 * @return An OpenGL handle to the program.
	 */
	private int createAndLinkProgram(final int vertexShaderHandle, final int fragmentShaderHandle, final String[] attributes){
		int programHandle = GLES20.glCreateProgram();
		if (programHandle != 0){
			// Bind the vertex shader to the program.
			GLES20.glAttachShader(programHandle, vertexShaderHandle);			

			// Bind the fragment shader to the program.
			GLES20.glAttachShader(programHandle, fragmentShaderHandle);

			// Bind attributes
			if (attributes != null){
				final int size = attributes.length;
				for (int i = 0; i < size; i++){
					GLES20.glBindAttribLocation(programHandle, i, attributes[i]);
				}						
			}

			// Link the two shaders together into a program.
			GLES20.glLinkProgram(programHandle);

			// Get the link status.
			final int[] linkStatus = new int[1];
			GLES20.glGetProgramiv(programHandle, GLES20.GL_LINK_STATUS, linkStatus, 0);

			// If the link failed, delete the program.
			if (linkStatus[0] == 0){				
				Log.e("", "Error compiling program: " + GLES20.glGetProgramInfoLog(programHandle));
				GLES20.glDeleteProgram(programHandle);
				programHandle = 0;
			}
		}

		if (programHandle == 0)	{
			throw new RuntimeException("Error creating program.");
		}

		return programHandle;
	}
}