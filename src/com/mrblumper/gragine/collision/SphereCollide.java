package com.mrblumper.gragine.collision;

import android.util.Log;

import com.mrblumper.gragine.maths.Vector2;

public class SphereCollide extends CollisionShape {
	public float radius;
	
	public boolean pointIn(float x, float y){
		float t_x = this.x - x;
		float t_y = this.y - y;
		if(t_x * t_x + t_y * t_y <= radius * radius)
			return true;
		return false;
	}
	
	public SphereCollide(float radius, float x, float y){
		super(x, y);
		this.radius = radius;
	}
	
	public boolean collision(SphereCollide sphere){
		Vector2 between = new Vector2(x, y, sphere.x, sphere.y);
		float length = between.length();
		
		if(length <= radius + sphere.radius){
			return true;
		}
		return false;
	}
	public boolean collision(SphereCollide sphere, Vector2 vec_offset){
		Vector2 between = new Vector2(x, y, sphere.x, sphere.y);
		float length = between.length();
		if(length <= radius + sphere.radius){
			vec_offset.x = this.x - sphere.x;
			vec_offset.y = this.y - sphere.y;
			vec_offset.normalize();
			float desired_length = this.radius + sphere.radius - length;
			vec_offset.mult(desired_length + 1);
			return true;
		}
		return false;
	}
	
	public boolean collision(ShapeCollide shape){
		return shape.collision(this);
	}
	public boolean collision(ShapeCollide shape, Vector2 vec_offset){
		if(shape.collision(this, vec_offset)){
			vec_offset.x *= -1;
			vec_offset.y *= -1;
			return true;
		}
		return false;
	}
	public boolean collision(ConcavePolygoneCollide polygon){
		return polygon.collision(this);
	}
	public boolean collision(ConcavePolygoneCollide polygon, Vector2 vec_offset){
		if(polygon.collision(this, vec_offset)){
			vec_offset.x *= -1;
			vec_offset.y *= -1;
			return true;
		}
		return false;
	}
}
