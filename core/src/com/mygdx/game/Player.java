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
	
	

	
	public Player(Texture spriteSheet, int width, int height, float x, float y){
		super(spriteSheet);
		super.setPosition(x, y);
		super.setRegionWidth(width);
		super.setRegionHeight(height);
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
	public void setCurrentFrame( float time){
		if(this.dir == DIRECTION.UP){
			super.setRegion((Texture)animations.getAnimation(1).getKeyFrame(time));
		}
		if(this.dir == DIRECTION.DOWN){
			super.setRegion((Texture)animations.getAnimation(0).getKeyFrame(time));
				}
		if(this.dir == DIRECTION.LEFT){
			super.setRegion((Texture)animations.getAnimation(3).getKeyFrame(time));
		}
		if(this.dir == DIRECTION.RIGHT){
			super.setRegion((Texture)animations.getAnimation(2).getKeyFrame(time));	
		}
		
		
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
