package com.mrblumper.gragine.collision;

import java.util.ArrayList;

import android.util.Log;

import com.mrblumper.gragine.maths.Vector2;

public class ConcavePolygoneCollide extends ShapeCollide {
	private ArrayList<ShapeCollide> _triangles = new ArrayList<ShapeCollide>();
	public ConcavePolygoneCollide(float x, float y){
		super(x, y);
	}
	public void addConvexPolygone(int... id){
		ShapeCollide shape = new ShapeCollide(0, 0);
		for(int pt : id){
			shape.addPoint(_points.get(pt).clone());
		}
		_triangles.add(shape);
	}
	
	public boolean pointIn(float x, float y){
		for(ShapeCollide triangle : _triangles){
			if(triangle.pointIn(x, y)){
				return true;
			}
		}
		return false;
	}
	
	public boolean collision(ConcavePolygoneCollide polygon){
		for(ShapeCollide triangle : _triangles){
			triangle.x = this.x;
			triangle.y = this.y;
			triangle.center_calculate = this.center_calculate;
			if(polygon.collision(triangle)){			
				return true;
			}
		}
		return false;
	}
	
	public boolean collision(ConcavePolygoneCollide polygon, Vector2 offset){
		boolean collide = false;
		Vector2 temp_offset = new Vector2();
		float dec_x = this.x;
		float dec_y = this.y;
		for(ShapeCollide triangle : _triangles){
			triangle.x = dec_x;
			triangle.y = dec_y;
			triangle.center_calculate = this.center_calculate;
			if(polygon.collision(triangle, temp_offset)){			
				collide = true;
				dec_x -= temp_offset.x;
				dec_y -= temp_offset.y;
			}
		}
		
		offset.x = -this.x + dec_x;
		offset.y = -this.y + dec_y;
		return collide;		
	}
	
	public boolean collision(SphereCollide sphere){
		for(ShapeCollide triangle : _triangles){
			triangle.x = this.x;
			triangle.y = this.y;
			triangle.center_calculate = this.center_calculate;
			if(triangle.collision(sphere)){	
				return true;
			}
		}
		return false;
	}
	
	public boolean collision(SphereCollide sphere, Vector2 offset){		
		boolean collide = false;
		Vector2 temp_offset = new Vector2();
		float dec_x = this.x;
		float dec_y = this.y;
		for(ShapeCollide triangle : _triangles){
			triangle.x = dec_x;
			triangle.y = dec_y;
			triangle.center_calculate = this.center_calculate;
			if(triangle.collision(sphere, temp_offset)){			
				collide = true;
				dec_x += temp_offset.x;
				dec_y += temp_offset.y;
			}
		}
		
		offset.x = -this.x + dec_x;
		offset.y = -this.y + dec_y;
		return collide;
	}
	
	public boolean collision(ShapeCollide shape){
		for(ShapeCollide triangle : _triangles){
			triangle.x = this.x;
			triangle.y = this.y;
			triangle.center_calculate = this.center_calculate;
			if(triangle.collision(shape)){			
				return true;
			}
		}
		return false;
	}
	public boolean collision(ShapeCollide shape, Vector2 offset){
		boolean collide = false;
		Vector2 temp_offset = new Vector2();
		float dec_x = this.x;
		float dec_y = this.y;
		for(ShapeCollide triangle : _triangles){
			triangle.x = dec_x;
			triangle.y = dec_y;
			triangle.center_calculate = this.center_calculate;
			if(triangle.collision(shape, temp_offset)){			
				collide = true;
				dec_x += temp_offset.x;
				dec_y += temp_offset.y;
			}
		}
		
		offset.x = -this.x + dec_x;
		offset.y = -this.y + dec_y;
		return collide;
	}	
}
