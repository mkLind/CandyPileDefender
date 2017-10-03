package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;

public class SpriteCommons {
	
private float x;
private float y;
private float xVel;
private float yVel;
private int width;
private int height;
private Rectangle r;

public SpriteCommons(int width, int height, float x, float y,float xVel, float yVel){
	this.x  = x;
	this.y = y;
	this.width  = width;
	this.height = height;
	this.xVel = xVel;
	this.yVel = yVel;
	r = new Rectangle(x, y, width, height);
}

public Rectangle getR() {
	return r;
}

public void setR(Rectangle r) {
	this.r = r;
}

public float getX() {
	return x;
}

public void setX(float x) {
	r.setX(x);
	this.x = x;
}

public float getY() {
	return y;
}

public void setY(float y) {
	r.setY(y);
	this.y = y;
}

public float getxVel() {
	return xVel;
}

public void setxVel(float xVel) {
	this.xVel = xVel;
}

public float getyVel() {
	return yVel;
}

public void setyVel(float yVel) {
	this.yVel = yVel;
}

public int getWidth() {
	return width;
}

public void setWidth(int width) {
	this.width = width;
}

public int getHeight() {
	return height;
}

public void setHeight(int height) {
	this.height = height;
}

}
