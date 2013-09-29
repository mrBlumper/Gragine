package com.mrblumper.gragine.collision;

import com.mrblumper.gragine.maths.Vector2;

public abstract class CollisionShape {
	public float x;
	public float y;
	CollisionShape(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public void move(float x, float y){
		this.x += x;
		this.y += y;
	}
	public abstract boolean pointIn(float x, float y);
	/*public abstract boolean collisionWithSphere(SphereCollide sphere);
	public abstract boolean collisionWithSphere(SphereCollide sphere, Vector2 vec_offset);*/
	/*public abstract boolean collisionWithShape*/
	public boolean collision(CollisionShape shape){return true;};
	public boolean collision(CollisionShape shape, Vector2 offset){return true;};
	/*
	public abstract boolean collision(SphereCollide sphere);
	public abstract boolean collision(SphereCollide sphere, Vector2 vec_offset);
	public abstract boolean collision(ShapeCollide shape);
	public abstract boolean collision(ShapeCollide shape, Vector2 vec_offset);
	 */
}
