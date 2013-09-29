package com.mrblumper.gragine;

import java.util.HashMap;
import java.util.Map;

import com.mrblumper.gragine.collision.BoxCollide;
import com.mrblumper.gragine.collision.CollisionInfo;
import com.mrblumper.gragine.collision.ConcavePolygoneCollide;
import com.mrblumper.gragine.collision.ShapeCollide;
import com.mrblumper.gragine.collision.SphereCollide;
import com.mrblumper.gragine.core.Gragine;
import com.mrblumper.gragine.core.GragineActivity;
import com.mrblumper.gragine.maths.Vector2;
import com.mrblumper.gragine.touch.BoxButton;
import com.mrblumper.gragine.touch.Button;
import com.mrblumper.gragine.touch.Joystick;
import com.mrblumper.gragine.touch.TouchEvents;
import com.mrblumper.gragine.touch.TouchLayer;
import com.mrblumper.gragine.utilities.drawable.CirclePresets;
import com.mrblumper.gragine.utilities.drawable.ColoredShape;
import com.mrblumper.gragine.utilities.drawable.EditableShape;
import com.mrblumper.gragine.utilities.drawable.LineShape;
import com.mrblumper.gragine.utilities.drawable.ObjectAnimation;
import com.mrblumper.gragine.utilities.drawable.Sprite;
import com.mrblumper.gragine.utilities.drawable.SquarePresets;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;

public class MainActivity extends GragineActivity {
	public float color = 0.0f;
	public EditableShape shape;	
	public ColoredShape shape_simpler;
	public ColoredShape shape_indices;
	public Sprite shape_texture;
	public Sprite shape_texture2;
	public Sprite shape_texture3;
	public Sprite button_arrow_right;
	public Sprite button_arrow_left;
	public Sprite button_jump;
	public Sprite background;
	public Sprite character;
	public Button bu_right;
	public Button bu_left;
	public Button bu_jump;
	public Joystick joystick;
	public ColoredShape joystick_color;
	public ColoredShape joystick_zone_color;
	public float[] color_shape = new float[4];
	public TouchLayer touch_layer = new TouchLayer("layer");
	
	public SphereCollide static_sphere;
	public SphereCollide moving_sphere;
	public ColoredShape static_sphere_shape;
	public ColoredShape moving_sphere_shape;
	public ShapeCollide static_box;
	public ShapeCollide moving_box;
	public ColoredShape static_box_shape;
	public ColoredShape moving_box_shape;
	public ConcavePolygoneCollide moving_concave;
	//public BoxCollide moving_concave;
	public ColoredShape moving_concave_shape;
	public ConcavePolygoneCollide static_concave;
	public ColoredShape static_concave_shape;
	public ObjectAnimation animation;
	long previous_time;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		compteur = 0;
	}
	public void onUpdate(){
		Gragine.AnimationManager.update();
		if(touch_layer.getStateButton("joystick") > 0){
			joystick_color.setColor(0, 1, 0);
			moving_box.x += joystick.getX() * 10;
			moving_box.y += joystick.getY() * 10;
			moving_box_shape.translate(joystick.getX() * 10, joystick.getY() * 10);//*/
			moving_sphere.x += joystick.getX() * 10;
			moving_sphere.y += joystick.getY() * 10;
			moving_sphere_shape.translate(joystick.getX() * 10, joystick.getY() * 10);//*/
			moving_concave.x += joystick.getX() * 10;
			moving_concave.y += joystick.getY() * 10;
			moving_concave_shape.translate(joystick.getX() * 10, joystick.getY() * 10);//*/
		
		
		}else{
			joystick_color.setColor(1, 0, 0);
		}
		
		Vector2 offset = new Vector2();/*
		if(moving_sphere.collision(static_sphere, offset)){
			static_sphere_shape.setColor(0, 1, 0);
			Log.i("offset", offset.x + " " + offset.y);
			moving_sphere.x += offset.x;
			moving_sphere.y += offset.y;
			moving_sphere_shape.translate(+offset.x, +offset.y);
		}else{
			static_sphere_shape.setColor(1, 0, 0);
		}*/
		/*if(static_box.collision(moving_sphere, offset)){
			static_box_shape.setColor(0, 1, 0);
			Log.i("offset", offset.x + " " + offset.y);
			moving_sphere.x -= offset.x;
			moving_sphere.y -= offset.y;
			moving_sphere_shape.translate(-offset.x, -offset.y);
		}else{
			static_sphere_shape.setColor(1, 0, 0);
		}*/
		
		
		if(moving_concave.collision(static_concave, offset)){
			static_concave_shape.setColor(0, 1, 0);
			moving_concave.x += offset.x;
			moving_concave.y += offset.y;
			moving_concave_shape.translate(+offset.x, +offset.y);
		}else{
			static_concave_shape.setColor(1, 0, 0);
		}
		
		
		/*
		if(moving_sphere.collision(static_box, offset)){
			static_box_shape.setColor(0, 1, 0);
			Log.i("offset", offset.x + " " + offset.y);
			moving_sphere.x += offset.x;
			moving_sphere.y += offset.y;
			moving_sphere_shape.translate(+offset.x, +offset.y);
		}else{
			static_box_shape.setColor(1, 0, 0);
		}*/
		
	
		/*if(moving_box.collision(static_sphere, offset)){
			static_sphere_shape.setColor(0, 1, 0);
			Log.i("offset", offset.x + " " + offset.y);
			moving_box.x += offset.x;
			moving_box.y += offset.y;
			moving_box_shape.translate(+offset.x, +offset.y);
		}else{
			static_sphere_shape.setColor(1, 0, 0);
		}*/
		/*if(moving_box.collision(static_box, offset)){
			static_box_shape.setColor(0, 1, 0);
			Log.i("offset", offset.x + " " + offset.y);
			moving_box.x += offset.x;
			moving_box.y += offset.y;
			moving_box_shape.translate(+offset.x, +offset.y);
		}else{
			static_box_shape.setColor(1, 0, 0);
		}*/
    	joystick_color.setPos(joystick.center_x, joystick.center_y);
		
		Gragine.draw(background);
    	/*Gragine.draw(joystick_zone_color);
    	Gragine.draw(joystick_color);
    	Gragine.draw(static_sphere_shape);
    	Gragine.draw(moving_sphere_shape);*/
		
    	//Gragine.draw(static_sphere_shape);
    	Gragine.draw(static_box_shape);
    	//Gragine.draw(moving_box_shape);
    	//Gragine.draw(moving_sphere_shape);
    	Gragine.draw(moving_concave_shape);
    	Gragine.draw(static_concave_shape);
		LineShape line = new LineShape(0, 0, 1);
		line.addVertex(0, 0);
		line.addVertex(1000, 400);
		line.addVertex(500, 600);
		//line.setCenterPoint(0, 0);
    	Gragine.draw(line);
    	Gragine.draw(joystick_zone_color);
    	Gragine.draw(joystick_color);
    	
    	long now = SystemClock.elapsedRealtime();
		long framerate = (now - previous_time);
		//Log.i("framerate",(double) 1/framerate +"");
		previous_time = now;
    }
	
	public void onEngineCreation(){
		animation = new ObjectAnimation(true);
		animation.addStep(100, 0, 0, 0, 2, 2);
		animation.addStep(100, 0, 0, 0, 0.5f, 0.5f);
		
		this.setBackgroundColor(1, 1, 1);
		Gragine.setContextSize(1280, 736);
    	Gragine.ImageManager.loadImage("arrow", R.drawable.arrow);
    	Gragine.ImageManager.loadImage("perso", R.drawable.perso);
    	Gragine.ImageManager.loadImage("terrain", R.drawable.terrain);
    	Gragine.ImageManager.loadImage("button_arrow_hover", R.drawable.button_arrow_hover);
    	Gragine.ImageManager.loadImage("button_arrow_repose", R.drawable.button_arrow_repose);
    	Gragine.ImageManager.loadImage("button_jump_hover", R.drawable.button_jump_hover);
    	Gragine.ImageManager.loadImage("button_jump_repose", R.drawable.button_jump_repose);
    	

    	background = new Sprite(1280, 1280, "terrain");
    	background.setCenterPoint(0, 0);
    	joystick_color = new ColoredShape(1, 0, 0);
    	joystick_color .useShapePreset(new CirclePresets(50, 360));
    //	joystick_color.resetCenterPoint();
    	joystick_zone_color = new ColoredShape(1, 1, 1, 0.5f);
    	joystick_zone_color .useShapePreset(new CirclePresets(100, 360));
    //	joystick_zone_color.resetCenterPoint();
    	
    	joystick = new Joystick(100, 500, 50, 100);
    	joystick_color.setPos(joystick.center_x, joystick.center_y);
    	joystick_zone_color.setPos(joystick.center_x, joystick.center_y);
    	touch_layer.addButton("joystick", joystick);
    	
    	
    	static_concave_shape = new ColoredShape(0, 1, 1);
    	static_concave_shape.addVertex(0, 0);
    	static_concave_shape.addVertex(600, 0);
    	static_concave_shape.addVertex(600, 600);
    	static_concave_shape.addVertex(0, 600);
    	static_concave_shape.addVertex(100, 100);
    	static_concave_shape.addVertex(500, 100);//
    	static_concave_shape.addVertex(500, 500);
    	static_concave_shape.addVertex(100, 500);//
    	static_concave_shape.addFaceIndices(0, 1, 4);
    	static_concave_shape.addFaceIndices(4, 5, 1);
    	static_concave_shape.addFaceIndices(6, 1, 5);
    	static_concave_shape.addFaceIndices(1, 2, 6);
    	static_concave_shape.addFaceIndices(7, 6, 2);
    	static_concave_shape.addFaceIndices(2, 3, 7);
    	static_concave_shape.addFaceIndices(3, 7, 4);
    	static_concave_shape.addFaceIndices(0, 3, 4);
    	static_concave_shape.setPos(50, 50);
    	static_concave = new ConcavePolygoneCollide(50, 50);
    	static_concave.addPoint(0, 0);
    	static_concave.addPoint(600, 0);
    	static_concave.addPoint(600, 600);
    	static_concave.addPoint(0, 600);
    	static_concave.addPoint(100, 100);
    	static_concave.addPoint(500, 100);//
    	static_concave.addPoint(500, 500);
    	static_concave.addPoint(100, 500);
      	static_concave.addConvexPolygone(0, 1, 4, 5);
      	static_concave.addConvexPolygone(1, 2, 5, 6);
      	static_concave.addConvexPolygone(2, 3, 7, 6);
      	static_concave.addConvexPolygone(3, 7, 4, 0);
    	
      	moving_concave_shape = new ColoredShape(0, 1, 1);
    	moving_concave_shape.addVertex(0, 0);
    	moving_concave_shape.addVertex(50, 0);
    	moving_concave_shape.addVertex(50, 50);
    	moving_concave_shape.addVertex(0, 50);
    	moving_concave_shape.addFaceIndices(2, 0, 1);
    	moving_concave_shape.addFaceIndices(0, 2, 3);
    	moving_concave_shape.setPos(150,  150);
    	//moving_concave = new BoxCollide(150,  150, 50, 50);
    	moving_concave = new ConcavePolygoneCollide(150, 150);
      	moving_concave.addPoint(0, 0);
    	moving_concave.addPoint(50, 0);
    	moving_concave.addPoint(50, 50);
    	moving_concave.addPoint(0, 50);
    	moving_concave.addConvexPolygone(0, 1, 2, 3);
    	//moving_concave.triangulate();
    	
    	
    	moving_box = new ShapeCollide(0, 0);
    	moving_box.addPoint(500, 0);
    	moving_box.addPoint(500, 500);
    	moving_box.addPoint(0, 500);
    	moving_box.addPoint(0, 0);
    	moving_box_shape = new ColoredShape(0, 1, 1);
    	moving_box_shape.useShapePreset(new SquarePresets(500, 500));
  
    	
    	static_box = new ShapeCollide(550, 350);
       	static_box.addPoint(200, 0);
    	static_box.addPoint(200,200);
    	static_box.addPoint(0, 200);
    	static_box.addPoint(0, 0);
    	static_box_shape = new ColoredShape(1, 0, 0);
    	static_box_shape.useShapePreset(new SquarePresets(200, 200));
    	static_box_shape.setPos(550, 350);
    	
    	moving_sphere = new SphereCollide(50, 0, 0);
    	moving_sphere_shape = new ColoredShape(0, 1, 1);
    	moving_sphere_shape.useShapePreset(new CirclePresets(50, 360));
    	moving_sphere_shape.resetCenterPoint();
    	
    	static_sphere = new SphereCollide(100, 550, 350);
    	static_sphere_shape = new ColoredShape(1, 0, 0);
    	static_sphere_shape.useShapePreset(new CirclePresets(100, 360));
    	static_sphere_shape.setPos(550, 350);
    	static_sphere_shape.resetCenterPoint();
    	Gragine.AnimationManager.add(static_box_shape, animation);
    	//*/
    	/*
    	shape_texture = new Sprite(128, 128, "arrow");
    	shape_texture.translate(800, 64);
    	
    	shape_texture2 = new Sprite(192, 192, "perso");
    	shape_texture2.translate(800, 400);
    	shape_texture3 = new Sprite(128, 128, "perso");
    	shape_texture3.translate(200, 400);*/

    	/*shape_indices = new ColoredShape();
    	shape_indices.setColor(0, 1, 1);
    	shape_indices.useShapePreset(new CirclePresets(100, 5));
    	shape_indices.setPos(500, 250);
    	shape_simpler = new ColoredShape();
    	shape_simpler.setColor(1, 0, 0);
    	shape_simpler.useShapePreset(new SquarePresets(100, 100));*/
    	
		/*shape = new EditableShape("color");
		shape.setVitalInformation("uMatrix", "vPosition");
		shape.disableIndices();
		shape.addVertex(0, 0);
		shape.addVertex(100, 0);
		shape.addVertex(0, 700);
		shape.addVertex(1000, 500);
		shape.addVertex(800, 600);
    	shape.translate(500, 250);
    	shape.setAttributeData("vColor", 4);
    	shape.setElement("vColor", 0, 1, 0, 0, 1);
    	shape.setElement("vColor", 1, 0, 1, 0, 1);
    	shape.setElement("vColor", 2, 0, 0, 1, 1);
    	shape.setElement("vColor", 3, 0, 0, 0, 1);
    	shape.setElement("vColor", 4, 1, 1, 1, 1);
    	shape.mode_render = GLES20.GL_TRIANGLE_STRIP;*/
    	
    	/*BoxButton temp = new BoxButton(0, 0, 500, 500);
    	temp.drag_to_enter = false;
    	temp.drag_to_quit = false;
    	TouchEvents.touch_layer.addButton("test", temp);
    	TouchEvents.touch_layer.addButton("test2", new BoxButton(500, 0, 500, 500));*/
    	
    	
    	TouchEvents.setTouchLayer(touch_layer);

			
	}
	
	public int compteur;
	public boolean touch = false;/*
    @Override
    public boolean onTouchEvent(MotionEvent event){  	
    	
    	TouchEvents.update(event);
    	return false;
    }*/
}
