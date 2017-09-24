package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;


public class Player extends Sprite {

	private float yVel;
	private float xVel;
	private DIRECTION dir; 
	

	
	public Player(Texture spriteSheet, int srcX, int srcY, int width, int height, float x, float y){
		super(spriteSheet, srcX, srcY, width, height);
		super.setPosition(x, y);
		yVel = 0;
		xVel = 0;
		dir = DIRECTION.DOWN;
	}
	public enum DIRECTION{
		UP,DOWN,LEFT,RIGHT
	}
	public float getyVel() {
		return yVel;
	}
	public void setyVel(float yVel) {
		this.yVel = yVel;
	}
	public float getxVel() {
		return xVel;
	}
	public void setxVel(float xVel) {
		this.xVel = xVel;
	}
	public DIRECTION getDir() {
		return dir;
	}
	public void setDir(DIRECTION dir) {
		this.dir = dir;
	}

}
