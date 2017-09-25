package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;

 // sprite.setRegion(animation.getKeyFrame(time));

public class Player extends Sprite {

	private float yVel;
	private float xVel;
	private DIRECTION dir; 
	private Animator animations;
	

	
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
	// Sets all the animations for the player
	public void setAnimations(int row, int column, float frametime, Texture spritesheet){
		animations = new Animator(row, column, frametime, spritesheet);
	}
	/*
	 * Method for changing the frame of current animation. 
	 * @param row = which row from the spritesheet is used
	 * @param time = used to determine which frame of the animation to use
	 */
	public void setCurrentFrame(int row, int time){
		super.setRegion((Texture)animations.getAnimation(row).getKeyFrame(time));
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
