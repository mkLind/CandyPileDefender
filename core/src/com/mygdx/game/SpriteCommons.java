package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
/*
 * Class that has all the common aspects of a sprite like coordinates, velocities, proportions and bounding rectangle (For collisions)
 */
public class SpriteCommons {
	
private float x;
private float y;
private float targetX;
private float targetY;
private float xVel;
private float yVel;
private int width;
private int height;
private int HP;
private Rectangle hitbox;
private Texture texture;
private boolean isColliding;
private long timeSinceCollision;

private int id;
private boolean isHit;

// inactivity timer after attacking etc.
private int timeoutTimer;

public SpriteCommons(int width, int height, float x, float y,float xVel, float yVel, int HP){
	this.x = x;
	this.y = y;
	this.width  = width;
	this.height = height;
	this.xVel = xVel;
	this.yVel = yVel;
	this.HP = HP;
	hitbox = new Rectangle(x, y, width, height);
	timeoutTimer = 0;
	isColliding = false;
	targetX = 0;
	targetY = 0;
	timeSinceCollision = 0;
	id = 0;
	isHit = false;
	
}

public SpriteCommons(int width, int height, float x, float y,float xVel, float yVel){
	this.x = x;
	this.y = y;
	this.width  = width;
	this.height = height;
	this.xVel = xVel;
	this.yVel = yVel;
	hitbox = new Rectangle(x, y, width, height);
	timeoutTimer = 0;
	
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public long getTimeSinceCollision() {
	return timeSinceCollision;
}

public void setTimeSinceCollision(long timeSinceCollision) {
	this.timeSinceCollision = timeSinceCollision;
}

public float getTargetX() {
	return targetX;
}

public void setTargetX(float targetX) {
	this.targetX = targetX;
}

public float getTargetY() {
	return targetY;
}

public void setTargetY(float targetY) {
	this.targetY = targetY;
}

public boolean isColliding() {
	return isColliding;
}

public void setColliding(boolean isColliding) {
	this.isColliding = isColliding;
}

public void setHitbox(Rectangle hitbox) {
	this.hitbox = hitbox;
}

public void setTexture(Texture texture) {
	this.texture = texture;
}

public TextureRegion getCurrentFrame( float time){	
	return null;
	
}

public Texture getTexture() {
	return texture;
}

public Rectangle getHitbox() {
	return hitbox;
}

public void setHitboxS(Rectangle hitbox) {
	this.hitbox = hitbox;
}

public void updateHitbox() {
	hitbox.setX(getX());
	hitbox.setY(getY());
}
public void moveHitbox(float x, float y) {
	hitbox.setX(x);
	hitbox.setY(y);
}
public float getX() {
	return x;
}

public void setX(float x) {
	hitbox.setX(x);
	this.x = x;
}

public float getY() {
	return y;
}

public void setY(float y) {
	hitbox.setY(y);
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
public int getHP() {
    return HP;
}

public void setHP(int HP) {
    this.HP = HP;
}

public int getTimeoutTimer() {
	return timeoutTimer;
}
public void setTimeoutTimer(int timeoutTimer) {
	this.timeoutTimer = timeoutTimer;
}
public Vector2 investigatePath(Rectangle obstacle) {
	
	
	float futureX = (x + width/2) +   xVel*30;
	float futureY = (y + height/2)  +  yVel*30;
	
	
		Rectangle tmp = new Rectangle(obstacle);
		tmp.setX(tmp.getX()-tmp.getWidth()*0.75f);
		tmp.setY(tmp.getY()-tmp.getHeight()*0.75f);
		
		tmp.setWidth(tmp.getWidth() + tmp.getWidth()*0.75f);
		tmp.setHeight(tmp.getHeight() + tmp.getHeight()*0.75f);
		
		if(tmp.contains(futureX,futureY)) {
			// calculates a target point around the pile
			while(tmp.contains(futureX,futureY)){
				
				if(Math.abs(tmp.getX() - futureX) <Math.abs((tmp.getX() + tmp.getWidth()) - futureX)) {
					futureX --;
				}else {
					
					futureX++;
				}
				if(Math.abs(tmp.getY() - futureY) <Math.abs((tmp.getY() + tmp.getHeight()) - futureY)) {
					futureY --;
				}else {
					
					futureY ++;
				}
				
				
			}
			
		}
		
		
	
	
	return new Vector2(futureX, futureY);
	
	
	
	
}

public void setIsHit(boolean hit) {
	this.isHit = hit;
}

public boolean getIsHit() {
	return isHit;
	}
public boolean getCollisionForecast(Rectangle obstacle) {
	float futureX = x + xVel*30;
	float futureY = y + yVel*30;
	
	boolean aboutToCollide = false;
	if(obstacle.contains(futureX,futureY)) {
		aboutToCollide = true;
	}
	return aboutToCollide;
}


}