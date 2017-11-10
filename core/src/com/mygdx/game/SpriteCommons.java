package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
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



}