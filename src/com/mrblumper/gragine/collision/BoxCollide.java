package com.mrblumper.gragine.collision;

import android.util.Log;

public class BoxCollide extends ShapeCollide{
	protected float _width;
	protected float _height;
	protected float _top_x;
	protected float _top_y;
	/***
	 * NOT WORKING!
	 * @param x
	 * @param y
	 * @param top_x
	 * @param top_y
	 * @param width
	 * @param height
	 */
	public BoxCollide(float x, float y, float top_x, float top_y, float width, float height){
		super(x, y);
		this._width = width;
		this._height = height;
		this._top_x = top_x;
		this._top_y = top_y;
		//constructShape();
	}
	public BoxCollide(float top_x, float top_y, float width, float height){		
		this(0, 0, top_x, top_y, width, height);
	}
	public void setTopX(float x){
		_top_x = x;
		constructShape();
	}
	public void setTopY(float y){
		_top_y = y;
		constructShape();
	}
	public void setWidth(float width){
		_width = width;
		constructShape();
	}
	public void setHeight(float height){
		_height = height;
		constructShape();
	}
	protected void constructShape(){
		_points.clear();
		addPoint(_width, _top_y);
		addPoint(_width, _top_y + _height);
		addPoint(_top_x, _top_y + _height);
		addPoint(_top_x, _top_y);
	}
	
	public boolean collision(BoxCollide square){
		if((square.x >= this.x + this._width)      // trop à droite
			|| (square.x + square._width <= this.x) // trop à gauche
			|| (square.y >= this.y + this._height) // trop en bas
			|| (square.y + square._height <= this.y))  // trop en haut
			return false; 
		else
			return true; 
	}
	
	public boolean pointIn(float x, float y){
		if (x >= this.x 
			&& x < this.x + this._width
			&& y >= this.y 
			&& y < this.y + this._height)
			return true;
		else
			return false;
	}
}
