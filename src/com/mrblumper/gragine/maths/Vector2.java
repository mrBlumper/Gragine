package com.mrblumper.gragine.maths;

public class Vector2 {
	public float x;
	public float y;
	public Vector2(float x, float y){
		this.x = x;
		this.y = y;
	}
	public Vector2 clone(){
		return new Vector2(this.x, this.y);
	}
	public Vector2(){
		this(0.0f, 0.0f);
	}
	public Vector2(Vector2 p, Vector2 p2){
		this(p2.x - p.x, p2.y - p.y);
	}
	public Vector2(float x, float y, float x2, float y2){
		this(x2 - x, y2 - y);
	}

	
	public float lengthPow(){
		return x*x + y*y;
	}
	public float length(){
		return (float) Math.sqrt(x * x + y * y);
	}
	
	public void normalize(){
		float l = length();
		if(l != 0.0f){
			div(l);
		}
	}
	
	public void add(Vector2 vec){
		x += vec.x;
		y += vec.y;
	}
	public void sub(Vector2 vec){
		x -= vec.x;
		y -= vec.y;
	}
	public void mult(float k){
		x *= k;
		y *= k;
	}
	public void div(float k){
		x /= k;
		y /= k;
	}
	
	public float dotProduct(Vector2 vec){
		return x * vec.x + y * vec.y;
	}
	
	
	public static float dotProduct(Vector2 vec1, Vector2 vec2){
		return vec1.x * vec2.x + vec1.y * vec2.y;
	}
	public static Vector2 sub(Vector2 vec1, Vector2 vec2){
		return new Vector2(vec1.x - vec2.x,
						vec1.y - vec2.y);
	}
	public static Vector2 add(Vector2 vec1, Vector2 vec2){
		return new Vector2(vec1.x + vec2.x,
						vec1.y + vec2.y);
	}
	public static Vector2 mult(Vector2 vec1, float k){
		return new Vector2(vec1.x * k,
						vec1.y * k);
	}
	public static Vector2 div(Vector2 vec1, float k){
		return new Vector2(vec1.x / k,
						vec1.y / k);
	}
	public static float crossProduct(Vector2 vec1, Vector2 vec2){
		return (vec1.x * vec2.y)- (vec1.y * vec2.x); 
	}
	
	public static float length(Vector2 a, Vector2 b){
		return (float) Math.sqrt((b.x - a.x) * (b.x - a.x) + (b.y - a.y) * (b.y - a.y));
	}
	public static float lengthPow(Vector2 a, Vector2 b){
		return (b.x - a.x) * (b.x - a.x) + (b.y - a.y) * (b.y - a.y);
	}

}
