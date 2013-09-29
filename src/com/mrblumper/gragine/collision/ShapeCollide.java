package com.mrblumper.gragine.collision;

import java.util.ArrayList;

import android.util.Log;

import com.mrblumper.gragine.maths.Vector2;

public class ShapeCollide extends CollisionShape{
	protected ArrayList<Vector2> _points = new ArrayList<Vector2>();
	protected Vector2 real_center = new Vector2(0, 0);
	protected Vector2 center_calculate = new Vector2(0, 0);
	protected int total_nb = 0;
	
	public boolean pointIn(float x, float y){
		x -= this.x;
		y -= this.y;
		Vector2 temp_previous_this = this._points.get(this._points.size() - 1);
		for(Vector2 current : _points){
			Vector2 D = new Vector2();
			Vector2 T = new Vector2();
			D.x = current.x - temp_previous_this.x;
			D.y = current.y - temp_previous_this.y;
			T.x = x - temp_previous_this.x;
			T.y = y - temp_previous_this.y;
			float d = D.x * T.y - D.y * T.x;
			if(d < 0)
				return false;
			temp_previous_this = current;
		}
		return true;
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
	
	public boolean collision(ShapeCollide shape, Vector2 vec_offset){		
		ArrayList<Vector2> axis = new ArrayList<Vector2>(); 
	
	    Vector2 temp_previous_this = this._points.get(this._points.size() - 1);
	    for(Vector2 current : this._points){
	    	Vector2 E = Vector2.sub(current, temp_previous_this);
	    	axis.add(new Vector2(-E.y, E.x));
	    
	    	if (axisSeparatePolygons(axis.get(axis.size()-1), this, shape)) 
	            return false; 
	    	temp_previous_this = current;
	    }
	    
	    Vector2 temp_previous_shape = shape._points.get(shape._points.size() - 1);
	    for(Vector2 current : shape._points){
	    	Vector2 E = Vector2.sub(current, temp_previous_shape);
	    	axis.add(new Vector2(-E.y, E.x));
	    
	    	if (axisSeparatePolygons(axis.get(axis.size()-1), this, shape)) 
	            return false; 
	    	temp_previous_shape = current;
	    }
	    
	  
	    Vector2 mtd = findMTD(axis);
	    // makes sure the push vector is pushing A away from B 
	   // Vector2 D = new Vector2(this.x - shape.x, this.y - shape.y); 
	    Vector2 D = Vector2.sub(new Vector2(this.real_center.x + this.x, this.real_center.y + this.y), 
	    		new Vector2(shape.real_center.x + shape.x, shape.real_center.y + shape.y));
	    
	    if (D.dotProduct(mtd) < 0.0f)
	    	mtd.mult(-1);
	    mtd.mult(1.01f);
	    vec_offset.x = mtd.x;
	    vec_offset.y = mtd.y;
	    return true;
	}
	private Vector2 findMTD(ArrayList<Vector2> vector) 
	{ 
	    Vector2 MTD = vector.get(0); 
	    float mind2 = MTD.dotProduct(MTD); 
	    vector.remove(0);
	    for(Vector2 temp : vector){
	    	float d2 = temp.lengthPow();
	    //	d2 = Vector2.crossProduct(temp, temp);
	    	if(d2 < mind2){
	    		mind2 = d2;
	    		MTD = temp;
	    	}
	    }
	    return MTD; 
	}
	
	public boolean collision(ShapeCollide shape){
	    Vector2 temp_previous_this = this._points.get(this._points.size() - 1);
	    for(Vector2 current : this._points){
	    	Vector2 E = Vector2.sub(current, temp_previous_this);
	    	if (axisSeparatePolygons(new Vector2(-E.y, E.x), this, shape)) 
	            return false; 
	    	temp_previous_this = current;
	    }
	    
	    Vector2 temp_previous_shape = shape._points.get(shape._points.size() - 1);
	    for(Vector2 current : shape._points){
	    	Vector2 E = Vector2.sub(current, temp_previous_shape);
	     	if (axisSeparatePolygons(new Vector2(-E.y, E.x), this, shape)) 
	            return false; 
	    	temp_previous_shape = current;
	    }
	    return true;
	}
	
	private boolean axisSeparatePolygons(Vector2 axis, ShapeCollide A, ShapeCollide B) 
	{ 
	    IntervalInfo inter_a = new IntervalInfo(0, 0);
	    IntervalInfo inter_b = new IntervalInfo(0, 0);
	    calculateInterval(axis, A, inter_a); 
	    calculateInterval(axis, B, inter_b); 

	    if (inter_a.min > inter_b.max || inter_b.min > inter_a.max) 
	        return true; 

	    // find the interval overlap 
	    float d0 = inter_a.max - inter_b.min; 
	    float d1 = inter_b.max - inter_a.min; 
	    float depth = (d0 < d1)? d0 : d1; 

	    // convert the separation axis into a push vector (re-normalise
	    // the axis and multiply by interval overlap) 
	    float axis_length_squared = axis.dotProduct(axis); 

	    axis.mult(depth / axis_length_squared);
	    return false; 
	}
	
	private void calculateInterval(Vector2 axis, ShapeCollide P, IntervalInfo inter)
	{ 
	    float d = axis.dotProduct(new Vector2(P._points.get(0).x + P.x, P._points.get(0).y + P.y)); 
	    inter.min = d;
	    inter.max = d;
	    for(Vector2 vert : P._points){
	    	Vector2 temp = new Vector2(vert.x + P.x, vert.y + P.y);
	    	d = axis.dotProduct(temp);
	    	if(d < inter.min)
	    		inter.min = d;
	    	else
	    		if(d > inter.max)
	    			inter.max = d;
	    }
	}
	
	class IntervalInfo{
		float min;
		float max;
		public IntervalInfo(float i, float a){
			this.min = i;
			this.max = a;
		}
	}
	private float distancePointSegment(Vector2 V, Vector2 W, Vector2 P){
		Vector2 VW = Vector2.sub(W, V);
		float l2 = VW.lengthPow();
		if(l2 == 0){
			Vector2 d = Vector2.sub(V, P);
			return d.lengthPow();
		}
		Vector2 VP = Vector2.sub(P, V);
		float t = Vector2.dotProduct(VP, VW) / l2;
		if(t < 0){
			return VP.lengthPow();
		} else if ( t > 1.0){
			Vector2 PW = Vector2.sub(W, P);
			return PW.lengthPow();
		}
		Vector2 projection = Vector2.add(V, Vector2.mult(VW, t));
		Vector2 dist = Vector2.sub(projection, P);
		return dist.lengthPow();
	}
	public boolean collision(SphereCollide sphere){
		if(pointIn(sphere.x, sphere.y))
			return true;
		Vector2 pos = new Vector2(sphere.x - this.x, sphere.y - this.y);
		float length_pow = sphere.radius * sphere.radius;
		float min_distance = 999999999;
		boolean collide = false;
		Vector2 temp_previous_this = this._points.get(this._points.size() - 1);
		for(Vector2 current : this._points){
	    	float distance = distancePointSegment(current, temp_previous_this, pos);
	    	
	    	if(distance <= length_pow){
	    		collide = true;
	    		if(distance <= min_distance){
	    			min_distance = distance;
	    		}
	    	}
	    	temp_previous_this = current;
	    }
		return collide;
	}
	public boolean collision(SphereCollide sphere, Vector2 vec_offset){
		if(pointIn(sphere.x, sphere.y))
				return true;
		Vector2 pos = new Vector2(sphere.x- this.x, sphere.y - this.y);
		float length_pow = sphere.radius * sphere.radius;
		float min_distance = 999999999;
		boolean collide = false;
		Vector2 temp_previous_this = this._points.get(this._points.size() - 1);

		for(Vector2 current : this._points){
	    	float distance = distancePointSegment(current, temp_previous_this, pos);
	    	
	    	if(distance <= length_pow){
	    		collide = true;
	    		if(distance <= min_distance){
	    			min_distance = distance;
	    		}
	    	}
	    	temp_previous_this = current;
	    }
		if(collide){
			vec_offset.x = this.x + this.real_center.x - sphere.x;
			vec_offset.y = this.y + this.real_center.y - sphere.y;
			vec_offset.normalize();
			float desired_length = (float) (sphere.radius - Math.sqrt(min_distance));
			vec_offset.mult(desired_length+1);
		}
		return collide;
	}
	
	
	public ShapeCollide(float x, float y){
		super(x, y);
	}
	
	public void setPositionToCenter(){		
		Vector2 temp = new Vector2();
		for(Vector2 v : _points){
			temp.add(v);
		}
		temp.div(_points.size());
		this.x = temp.x;
		this.y = temp.y;
	}
	
	public void addPoint(float x, float y){
		Vector2 vec = new Vector2(x, y);
		addPoint(vec);
	}
	public void addPoint(Vector2 vec){
		_points.add(vec);
		center_calculate.add(vec);
		total_nb++;
		real_center = center_calculate.clone();
		real_center.div(total_nb);
	}
}
