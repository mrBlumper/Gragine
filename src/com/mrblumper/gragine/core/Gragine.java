package com.mrblumper.gragine.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.DisplayMetrics;
import android.util.Log;

import com.mrblumper.gragine.maths.Vector2;
import com.mrblumper.gragine.utilities.Image;
import com.mrblumper.gragine.utilities.Shaders.Shader;
import com.mrblumper.gragine.utilities.Shaders.ShaderVariable;
import com.mrblumper.gragine.utilities.drawable.AnimationWrapper;
import com.mrblumper.gragine.utilities.drawable.ObjectAnimation;
import com.mrblumper.gragine.utilities.drawable.Shape;

public class Gragine {
	public static int GR_NBR_VERTEX_COMPOUDS = 2;
	public static GragineActivity activity;
	protected static float[] projection = new float[16];
	protected static float[] camera_view = new float[16];
	public static int[] texture_units = new int[8];
	public static float width_context = 640;
	public static float height_context = 360;
	public static float width_window = 640;
	public static float height_window = 360;
	public static float ratio_width = 1;
	public static float ratio_height = 1;
	protected static float[] projection_view = new float[16];

	public static boolean _has_begin = false;
	public static float[] world_data = new float[5];	//contain pos (0 and 1), scale (2 and 3) and rotation(4)

	public static void calculateMatrix(){
		float[] temp = new float[16];
		Matrix.setIdentityM(camera_view, 0);
		Matrix.translateM(camera_view, 0, world_data[0], world_data[1], 0);
		Matrix.rotateM(camera_view, 0, world_data[4], 0, 0, 1);		
		Matrix.scaleM(camera_view, 0, world_data[2], world_data[3], 0);		
		Matrix.multiplyMM(projection_view, 0, projection, 0, camera_view, 0);
	}
	public static void camReset(){
		world_data[0] = 0;
		world_data[1] = 0;
		world_data[2] = 1;
		world_data[3] = 1;
		world_data[4] = 0;
		calculateMatrix();
	}
	public static void camSetPos(float x, float y){
		world_data[0] = x;
		world_data[1] = y;	
		calculateMatrix();
	}
	public static void camSetScale(float x, float y){
		world_data[2] = x;
		world_data[3] = y;	
		calculateMatrix();
	}
	public static void camSetRot(float angle){
		world_data[4] = angle;	
		calculateMatrix();
	}
	public static void camTranslate(float x, float y){
		world_data[0] += x;
		world_data[1] += y;		
		calculateMatrix();
	}
	public static void camRotate(float angle){
		world_data[4] += angle;
		if(world_data[4] >= 360)
			world_data[4] %= 360;
		else if(world_data[4] <= 360){
			world_data[4] = -((-world_data[4])%360);
		}
		calculateMatrix();
	}
	public static void camScale(float x, float y){
		world_data[2] *= x;
		world_data[3] *= y;
		calculateMatrix();
	}
	
	public static void calculateRatio(){
		ratio_height = height_context / height_window;
		ratio_width = width_context / width_window;
	}
	public static void setContextSize(float w, float h){
		width_context = w;
		height_context = h;
		calculateRatio();
	}
	
	protected Gragine(){}
	
	public static void draw(Shape object){
		activity.GLView.renderer.draw(object);
	}
	public static void firstCreation(){
		if(_has_begin)
			return;
		_has_begin = true;
		camReset();
		Log.i("creation", "first");
	}
	public static void create(GragineActivity act){
		Log.i("creation","aa");
		firstCreation();
		activity = act;
		for(int i = 0; i < texture_units.length; i++){
			texture_units[i] = -1;
		}
		
		//load defaults shaders;
		DefaultShaders.load();
	
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		width_window = metrics.widthPixels;
		height_window = metrics.heightPixels;
		calculateRatio();
	}
	
	public static int convertTextureUnitToGLUnit(int unit){
		return GLES20.GL_TEXTURE0 + unit;
	}
	
	public static class AnimationManager{
		protected AnimationManager(){}
		static private ArrayList<Shape> _shapes = new ArrayList<Shape>();
		static private ArrayList<AnimationWrapper> _animations = new ArrayList<AnimationWrapper>();
		static private ArrayList<Vector2> _base_scale = new ArrayList<Vector2>();
		static public void update(){
			for(int i = _shapes.size() - 1; i >= 0; i--){
				boolean to_erase = _animations.get(i).update(_shapes.get(i));
				if(to_erase){
					_shapes.remove(i);
					_animations.remove(i);
					_base_scale.remove(i);
				}
			}
		}
		static public void add(Shape shape, ObjectAnimation animation){
			_shapes.add(shape);
			_animations.add(new AnimationWrapper(animation));
			_base_scale.add(new Vector2(1, 1));
		}
		/*static private ArrayList<Shape> _shapes = new ArrayList<Shape>();
		static private ArrayList<ObjectAnimation> _animations = new ArrayList<ObjectAnimation>();
		static private ArrayList<Vector2> _base_scale = new ArrayList<Vector2>();
		static public void update(){
			for(int i = _shapes.size() - 1; i >= 0; i--){
				boolean to_erase = _animations.get(i).update(_shapes.get(i), _base_scale.get(i));
				if(to_erase){
					_shapes.remove(i);
					_animations.remove(i);
					_base_scale.remove(i);
				}
			}
		}
		static public void add(Shape shape, ObjectAnimation animation){
			_shapes.add(shape);
			_animations.add(animation.clone());
			_base_scale.add(new Vector2(1, 1));
		}*/
	
	}
	
	public static class ShadersManager {
		static private Map<String, Shader> _shaders = new HashMap<String, Shader>();
				
		protected ShadersManager(){}
		
		public static void reLoad(){
			for(Entry<String, Shader> entry : _shaders.entrySet()) {
				Shader data = entry.getValue();
			    data.recreate();
			}
		}
		public static void addShaderFromFile(String name, String path_vertex, String path_fragment){
			if(_shaders.containsKey(name))
				return;
			try {
			    InputStream vertex = activity.getAssets().open(path_vertex);
			    InputStream fragment = activity.getAssets().open(path_fragment);
				_shaders.put(name, new Shader(vertex, fragment));
			} catch (final IOException e) {
			    e.printStackTrace();
			}		
		}
		public static void addShaderFromString(String name, String vertex, String fragment){
			if(_shaders.containsKey(name))
				return;
			_shaders.put(name, new Shader(vertex, fragment));
		}
		public static int getShaderId(String name){
			if(_shaders.containsKey(name))
				return _shaders.get(name).program_id;
			return 0;
		}
		public static Shader getShader(String name){
			if(_shaders.containsKey(name))
				return _shaders.get(name);
			return null;
		}
		public static String[] getAttributesShader(String name){
			if(!_shaders.containsKey(name))
				return new String[0];
			Shader shader = _shaders.get(name);
			String[] keys = new String[shader.attribute_handles.size()];

			Iterator<Map.Entry<String, ShaderVariable>> it = shader.attribute_handles.entrySet().iterator(); 
			int i = 0;
			while(it.hasNext()){
			   keys[i] = it.next().getKey();
			   i++;
			}
			return keys;
		}
		public static String[] getUniformsShader(String name){
			if(!_shaders.containsKey(name))
				return new String[0];
			Shader shader = _shaders.get(name);
			String[] keys = new String[shader.uniform_handles.size()];
			Iterator<Map.Entry<String, ShaderVariable>> it = shader.uniform_handles.entrySet().iterator(); 
			int i = 0;
			while(it.hasNext()){
			   keys[i] = it.next().getKey();
			   i++;
			}
			return keys;
		}
		
	}
	
	public static class ImageManager {
		static private Map<String, Image> _images = new HashMap<String, Image>();
				
		protected ImageManager(){}
		
		public static void reLoad(){
			for(Entry<String, Image> entry : _images.entrySet()) {
				Image data = entry.getValue();
			    data.loadInOpengl();
			}
		}
		public static void loadImage(String name, final int ressource){
			if(_images.containsKey(name))
				return;
			Image image = new Image(ressource);
			if(image.id != 0){
				_images.put(name, image);
			}			
			
		}
		public static int getImage(String name){
			if(_images.containsKey(name))
				return _images.get(name).id;
			return -1;
		}
		
	}
	
	public static class DefaultShaders{
		protected DefaultShaders(){}
		public static void load(){
			ShadersManager.addShaderFromString("gragine_default_simple_color",
					vert_simple_color, frag_simple_color);
			ShadersManager.addShaderFromString("gragine_default_color",
					vert_complex_color, frag_complex_color);
			ShadersManager.addShaderFromString("gragine_default_simple_texture",
					vert_simple_texture, frag_simple_texture);
		}
		public static String vert_simple_color = "attribute vec4 vPosition;"
				+"uniform mat4 uMatrix;"
				+"void main() {"
				+"gl_Position = uMatrix * vPosition;}";
		public static String frag_simple_color = "precision mediump float;"
				+"uniform vec4 uColor;"
				+"void main() {"
				+"gl_FragColor = uColor;}";
		public static String vert_complex_color = "attribute vec4 vPosition;"
				+"uniform mat4 uMatrix;"
				+"attribute vec4 vColor;"
				+"varying vec4 v_color;"
				+"void main() {"
				+"v_color = vColor;"
				+"gl_Position = uMatrix * vPosition;}        ";
		public static String frag_complex_color = "precision mediump float;"
				+"varying vec4 v_color;"
				+"void main() {"
				+"gl_FragColor = vec4(1.0, 0.0, 1.0, 1.0);"
				+"gl_FragColor = v_color;}";
		public static String vert_simple_texture = "attribute vec4 vPosition;"
				+"uniform mat4 uMatrix;"
				+"attribute vec2 aTexCoordinate;"
				+"varying vec2 v_TexCoordinate;"
				+"void main() {"
				+"v_TexCoordinate = aTexCoordinate;"
				+"gl_Position = uMatrix * vPosition;}        ";
		public static String frag_simple_texture = "precision mediump float;"
				+"uniform sampler2D uTexture;"
				+"varying vec2 v_TexCoordinate;"
				+"void main() {"
				+"gl_FragColor =  texture2D(uTexture, v_TexCoordinate);}";
	}
}
